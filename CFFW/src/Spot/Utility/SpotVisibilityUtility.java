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

public class SpotVisibilityUtility {

	
	public static int getThermalMod(Trooper spotter, String visibilityModifications) {
		int visibilityMod = 0;
		
		if(spotter.thermalVision) {
			//System.out.println("Thermal vision: -7");
			visibilityMod -= 7;
			visibilityModifications += "Thermal Vision(-7); ";
		}
		
		return visibilityMod;
	}
	
	public static int getMagnificationMod(Trooper spotter, String visibilityModifications) {
		int visibilityMod = 0; 
		
		Weapons wep = new Weapons();
		wep = wep.findWeapon(spotter.wep);
		
		if(spotter.magnification > 0 || wep.magnification > 0) {
			
			int spotMagnification = 0;
			
			if(spotter.magnification > wep.magnification)
				spotMagnification = spotter.magnification;
			else 
				spotMagnification = wep.magnification;
			
			if(spotMagnification < 4) {
				visibilityModifications += "Magnification less than 4(-5); ";
				visibilityMod -= 2; 
			} else if(spotMagnification < 8) {
				visibilityModifications += "Magnification less than 8(-5); ";
				visibilityMod -= 3; 
			} else if(spotMagnification < 12) {
				visibilityModifications += "Magnification less than 12(-5); ";
				visibilityMod -= 4; 
			} else if(spotMagnification < 24) {
				visibilityModifications += "Magnification less than 24(-5); ";
				visibilityMod -= 5; 
			} else {
				visibilityModifications += "Greater Magnification than 24(-6); ";
				visibilityMod -= 6; 
			}
			
		}
		
		return visibilityMod;
	}
	
	public static int getWeatherMod(String weather) {
		int visibilityMod = 0;
		
		/*
		Good Visibility
		Dusk
		Night - Full Moon 
		Night - Half Moon
		Night - No Moon
		Smoke/Fog/Haze/Overcast
		Dusk - Smoke/Fog/Haze/Overcast
		Night - Smoke/Fog/Haze/Overcast
		*/
				
		if(weather.equals("Good Visibility")) {
			visibilityMod += 0; 
		} else if(weather.equals("Dusk")) {
			visibilityMod += 2; 
		} else if(weather.equals("Night - Full Moon")) {
			visibilityMod += 3; 
		} else if(weather.equals("Night - Half Moon")) {
			visibilityMod += 4; 
		} else if(weather.equals("Night - No Moon")) {
			visibilityMod += 7; 
		} else if(weather.equals("Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 3; 
		} else if(weather.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 4; 
		} else if(weather.equals("Night - Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 5; 
		} else if(weather.equals("No Visibility - Heavy Fog - White Out")) {
			visibilityMod += 7; 
		}
		
		return visibilityMod;
	}
	
	public static int getNightTimeMods(Trooper spotter, Unit spotterUnit, int xCord, int yCord, String weather, ArrayList<Unit> initiativeOrder) {
		
		int visibilityMod = 0;
		
		Unit unit = new Unit();
		unit.X = xCord; 
		unit.Y = yCord; 
		
		if(weather.substring(0, 4).equals("Night")) {
			
			// Checks if any target units have a IR laser or flashlight on 
			boolean flashLight = false; 
			boolean IRlaser = false; 
			
			for(Unit spottedUnit : initiativeOrder) {
				
				if(spottedUnit.X == xCord && spottedUnit.Y == yCord) {
					
					for(Trooper trooper : spottedUnit.individuals) {
						
						if(trooper.weaponLightOn)
							IRlaser = true; 
						
						if(trooper.weaponLightOn) 
							flashLight = true; 
						
					}
					
				}
				
			}
			
			if(flashLight)
				visibilityMod -= 5; 
			
			if(spotter.nightVisionInUse) {
				
				if(spotter.nightVisionEffectiveness == 1) {
					visibilityMod -= 1; 
				} else if(spotter.nightVisionEffectiveness == 2) {
					visibilityMod -= 2; 
				} else if(spotter.nightVisionEffectiveness == 3) {
					visibilityMod -= 3; 
				} else if(spotter.nightVisionEffectiveness == 4) {
					visibilityMod -= 4; 
				}
				
				if(IRlaser)
					visibilityMod -= 4; 
				
			} else if(!spotter.nightVisionInUse && spotter.weaponLightOn && GameWindow.hexDif(spotterUnit, unit) <= 1) {
				visibilityMod -= 4; 
			}
			
		}
		
		return visibilityMod;
		
	}
	
	public static int getVisibilityMod(Trooper spotter, Unit spotterUnit, String visibilityModifications, String weather, int xCord, int yCord, 
			ArrayList<Unit> initiativeOrder) {
		int visibilityMod = 0;
		visibilityMod += SpotVisibilityUtility.getThermalMod(spotter, visibilityModifications);
		visibilityMod += SpotVisibilityUtility.getMagnificationMod(spotter, visibilityModifications);
		visibilityMod += SpotVisibilityUtility.getWeatherMod(weather);
		visibilityMod += SpotVisibilityUtility.getNightTimeMods(spotter, spotterUnit, xCord, yCord, weather, initiativeOrder);
		return visibilityMod;
	}
	
	
	
	
	
	
}
