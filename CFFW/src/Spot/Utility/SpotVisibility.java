package Spot.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Conflict.GameWindow;
import Items.Weapons;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class SpotVisibility {

	public static String visibilityModifications;

	public static int getThermalMod(Trooper spotter, int distanceYards, ArrayList<Unit> spotableUnits) {

		boolean shrouded = true;

		for (var unit : spotableUnits) {
			for (var trooper : unit.individuals) {
				for (var item : trooper.inventory.getItemsArray()) {
					if (!item.thermalShroud)
						shrouded = false;
				}
			}
		}

		if (shrouded) {
			visibilityModifications += "Thermal Vision(Shrouded, 0); ";
			return 0;
		}

		int visibilityMod = 0;

		for (var item : spotter.inventory.getItemsArray()) {

			if (item.thermalOptic && item.maxThermalRangeYards >= distanceYards && item.thermalValue < visibilityMod) {
				visibilityMod = item.thermalValue;
			}

		}

		visibilityModifications += "Thermal Vision(" + visibilityMod + "); ";
		return visibilityMod;
	}

	public static int getTracerMod(ArrayList<Unit> spotableUnits) {

		for (var unit : spotableUnits) {
			for (var trooper : unit.individuals) {
				if (trooper.firedTracers) {
					var val = GameWindow.gameWindow.visibility.toLowerCase().contains("night") ? -15 : -12;
					visibilityModifications += "Tracer Mod: "+val+"; ";
					return val;
				}
			}
		}

		visibilityModifications += "Tracer Mod: 0; ";
		return 0;
	}

	public static int getMagnificationMod(Trooper spotter) {
		int visibilityMod = 0;

		int spotMagnification = spotter.getMagnification();
		
		if(spotMagnification <= 2) {
			visibilityModifications += "Magnification less than or equal to 2(0 mod); ";
		}
		else if (spotMagnification <= 4) {
			visibilityModifications += "Magnification less than or equal to 4(-2); ";
			visibilityMod -= 2;
		} else if (spotMagnification <= 8) {
			visibilityModifications += "Magnification less than or equal to 8(-3); ";
			visibilityMod -= 3;
		} else if (spotMagnification <= 12) {
			visibilityModifications += "Magnification less than or equal to 12(-4); ";
			visibilityMod -= 4;
		} else if (spotMagnification <= 24) {
			visibilityModifications += "Magnification less than or equal to 24(-5); ";
			visibilityMod -= 5;
		} else {
			visibilityModifications += "Greater Magnification than or equal to 24(-6); ";
			visibilityMod -= 6;
		}

		return visibilityMod;
	}

	public static int getMagnificationMod(int spotMagnification) {
		if (spotMagnification <= 4) {
			return -2;
		} else if (spotMagnification <= 8) {
			return -3;
		} else if (spotMagnification <= 12) {
			return -4;
		} else if (spotMagnification <= 24) {
			return -5;
		} else {
			return -6;
		}
	}
	
	public static int getWeatherMod(String weather) {
		int visibilityMod = 0;

		/*
		 * Good Visibility Dusk Night - Full Moon Night - Half Moon Night - No Moon
		 * Smoke/Fog/Haze/Overcast Dusk - Smoke/Fog/Haze/Overcast Night -
		 * Smoke/Fog/Haze/Overcast
		 */

		if (weather.equals("Good Visibility")) {
			visibilityMod += 0;
		} else if (weather.equals("Dusk")) {
			visibilityMod += 2;
		} else if (weather.equals("Night - Full Moon")) {
			visibilityMod += 3;
		} else if (weather.equals("Night - Half Moon")) {
			visibilityMod += 4;
		} else if (weather.equals("Night - No Moon")) {
			visibilityMod += 7;
		} else if (weather.equals("Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 3;
		} else if (weather.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 4;
		} else if (weather.equals("Night - Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 5;
		} else if (weather.equals("No Visibility - Heavy Fog - White Out")) {
			visibilityMod += 7;
		}

		return visibilityMod;
	}

	public static int getNightVisionEffectiveness(Trooper spotter) {
		int nightVisionEffectiveness = spotter.armor == null || !spotter.armor.nightVision ? 0 : spotter.armor.nightVisionEffectiveness; 
		for(var item : spotter.inventory.getItemsArray()) {
			if(item.nightVision && item.nightVisionEffectiveness > nightVisionEffectiveness)
				nightVisionEffectiveness = item.nightVisionEffectiveness;
		}
		
		return nightVisionEffectiveness;
	}
	
	public static int getNightTimeMods(Trooper spotter, Unit spotterUnit, int xCord, int yCord, String weather,
			ArrayList<Unit> spotableUnits) {

		int visibilityMod = 0;

		Unit unit = new Unit();
		unit.X = xCord;
		unit.Y = yCord;

		if (!weather.substring(0, 4).equals("Night")) {
			spotter.nightVisionInUse = false;
			spotter.weaponLightOn = false; 
			spotter.weaponIRLaserOn = false;
			return 0;
		}
		
		int nightVisionEffectiveness = getNightVisionEffectiveness(spotter);
		if(nightVisionEffectiveness > 0) {
			spotter.nightVisionInUse = true;
			spotter.nightVision = true;
			spotter.nightVisionEffectiveness = nightVisionEffectiveness;
		} else {
			spotter.nightVisionInUse = false;
			spotter.nightVision = false;
		}
		
		// Checks if any target units have a IR laser or flashlight on
		boolean targetsUsingflashLight = false;
		boolean targetsUsingIRlaser = false;

		for (Unit spottedUnit : spotableUnits) {

			for (Trooper trooper : spottedUnit.individuals) {
				if (trooper.weaponIRLaserOn || trooper.weaponLaserOn)
					targetsUsingIRlaser = true;
				if (trooper.weaponLightOn)
					targetsUsingflashLight = true;
			}

		}

		if (targetsUsingflashLight)
			visibilityMod -= 5;
		if(targetsUsingIRlaser && nightVisionEffectiveness > 0)
			visibilityMod -= 5;

		if (spotter.nightVisionInUse) {
			visibilityMod -= getWeatherMod(weather) < nightVisionEffectiveness ? 
					getWeatherMod(weather) : nightVisionEffectiveness;
		} else if (!spotter.nightVisionInUse && spotter.weaponLightOn && GameWindow.hexDif(spotterUnit, unit) <= 1) {
			visibilityMod -= 3;
		}

		visibilityModifications += "Night Time Modifiers: ";
		return visibilityMod;

	}

	
	
	public static int armorModifier(ArrayList<Unit> spotableUnits) {
		int mod = 0;
		int count = 0;

		for (var unit : spotableUnits) {
			for (var trooper : unit.individuals) {
				mod += trooper.armor != null ? trooper.armor.visibilityModifier : 0;
				count++;
			}
		}

		mod /= count;

		visibilityModifications += "Armor Visibility Mod: " + mod + "; ";

		return mod;
	}

	public static int camoMod(ArrayList<Unit> spotableUnits) {
		int mod = 0;
		int count = 0;

		for (var unit : spotableUnits) {
			for (var trooper : unit.individuals) {
				mod += trooper.spottingDifficulty;
				mod += trooper.armor != null ? trooper.armor.camo : 0;
				for (var item : trooper.inventory.getItemsArray()) {
					if (item.camouflage)
						mod += item.camoMod;
				}

				count++;
			}
		}

		mod /= count;
		visibilityModifications += "Camo Mod: " + mod + "; ";

		return mod;
	}

	public static int stealthFieldMod(ArrayList<Unit> spotableUnits) {
		int mod = 0;
		int count = 0;

		for (var unit : spotableUnits) {
			for (var trooper : unit.getTroopers()) {
				for (var item : trooper.inventory.getItemsArray()) {
					if (!item.cloakField || (!unit.speed.equals("None") && item.movementDisabled)) {
						count++;
						continue;
					}

					count++;
					mod += item.cloakSlm / (trooper.firedWeapons ? 2 : 1);
				}
			}
		}

		mod /= count;
		visibilityModifications += "Stealth Field Mod: " + mod + "; ";
		return mod;
	}

	public static int getVisibilityMod(Trooper spotter, Unit spotterUnit, String weather, int xCord, int yCord,
			ArrayList<Unit> spotableUnits) {

		visibilityModifications = "";

		int visibilityMod = 0;
		visibilityMod += getThermalMod(spotter, GameWindow.hexDif(xCord, yCord, spotterUnit) * 20, spotableUnits);
		visibilityMod += getMagnificationMod(spotter);
		visibilityMod += getWeatherMod(weather);
		visibilityMod += getNightTimeMods(spotter, spotterUnit, xCord, yCord, weather, spotableUnits);
		visibilityMod += armorModifier(spotableUnits);
		visibilityMod += getTracerMod(spotableUnits);
		visibilityMod += camoMod(spotableUnits);
		visibilityMod += stealthFieldMod(spotableUnits);

		return visibilityMod;
	}

	
	
}
