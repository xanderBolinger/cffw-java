package Actions;

import Unit.Unit;
import Trooper.Trooper;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Conflict.GameWindow;
import HexGrid.CalculateLOS;
import Hexes.Building;
import Hexes.Hex;
import Items.Weapons;

public class Spot implements Serializable {
	// Unit spotted unit
	public Unit spottedUnit;
	// Spotting unit belonging to the spotter  
	public Unit spotterUnit; 
	
	// Spotted Units 
	public ArrayList<Unit> spottedUnits = new ArrayList<Unit>();
	
	// Units spotted individuals
	public ArrayList<Trooper> spottedIndividuals = new ArrayList<Trooper>();

	public int success;
	public int successesRoll; 
	public int targetNumber;
	public double PCSize; 
	public int SLM; 
	
	public String visibilityModifications = ""; 
	public String resultsString = ""; 
	
	
	private transient Unit canSpotSpotterUnit;
	
	// Basic constructor 
	public Spot() {
		
	}
	
	
	// Gets speed
	// Gets concealment
	// Gets behavior
	// Gets skill diff
	// Makes calculations
	// Margin 10
	// Stores spotted individuals in the spotted modifier array
	public Spot(Unit spotterUnit, int xCord, int yCord, Trooper spotter, String scanArea, String visibility, ArrayList<Unit> initiativeOrder, GameWindow game) {
		//System.out.println("Multi Unit Hex Spot");
		int skillMod = 0; 
		
		/*int roll3 = new Random().nextInt(100) + 1; 
		
		if(roll3 <= spotter.spotListen) {
			int margin = spotter.spotListen - roll3; 
			
			skillMod = 1; 
			
			skillMod += margin / 10; 
			
			
		}*/
		
		
		
		this.spotterUnit = spotterUnit;
		
		//System.out.println("\n\nSpotting\n\n");

		// Gets target units 
		ArrayList<Unit> targets = new ArrayList<Unit>();

		for(Unit unit : initiativeOrder) {
			
			if(!unit.side.equals(spotterUnit) && xCord == unit.X && yCord == unit.Y) {
				targets.add(unit);
			}
			
		}
		
		if(targets.size() <= 0) {
			//game.conflictLog.addNewLine("No units in target hex.");
			return;
		}
		
		this.spottedUnit = null;
		
		// Size of target unit
		int size = 0;
		
		for(Unit unit : targets) {
			boolean sameHex = spotterUnit.X == unit.X && spotterUnit.Y == unit.Y;
			
			for(Trooper trooper : unit.individuals) {
				if(sameHex && trooper.alive && trooper.conscious) {
					size++; 
					continue;
				}
				
				if(!trooper.HD && trooper.alive && trooper.conscious)
					size++; 
			}
			
		}
		
		//System.out.println("Target Unit Size: "+size);
		
		// Speed
		int speedModTarget = 0;

		int speedModSpotter = 0;
	
		ArrayList<Trooper> targetTroopers = new ArrayList<Trooper>();
		
		for(Unit unit : targets) {
			boolean sameHex = spotterUnit.X == unit.X && spotterUnit.Y == unit.Y;
			if(unit.getSize() > 0) {
				for(Trooper trooper : unit.getTroopers()) {
					if(sameHex && trooper.alive && trooper.conscious) {
						targetTroopers.add(trooper);
						continue;
					}
					
					if(!trooper.HD && trooper.alive && trooper.conscious)
						targetTroopers.add(trooper);
					
				}
				
			}
			
		}
		
		
		
		// Concealment
		String concealment = targets.get(0).concealment;
		int concealmentMod = getConcealmentMod(concealment);
		if(concealmentMod < Building.buildingConcealmentMod && Building.majorityEmbarked(game, targetTroopers)) {
			concealmentMod = Building.buildingConcealmentMod; 
		}
		
		// Behavior

		// Range
		int rangeMod = getRangeMod(targets.get(0), spotterUnit);

		// Skill
		// int skillMod = getSkillMod(spotter, targetUnit);

		// Calculation
		
		//System.out.println("speedModTarget: "+speedModTarget);
		//System.out.println("speedModSpotter: "+speedModSpotter);
		//System.out.println("concealmentMod: "+concealmentMod);
		//System.out.println("rangeMod: "+rangeMod);
		
		int targetSizeMod = getTargetSizeMod(targetTroopers);
		
		int visibilityMod = 0; 
		
		if(spotter.thermalVision) {
			//System.out.println("Thermal vision: -7");
			visibilityMod -= 7;
			visibilityModifications += "Thermal Vision(-7); ";
		}
		
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
				
		if(visibility.equals("Good Visibility")) {
			visibilityMod += 0; 
		} else if(visibility.equals("Dusk")) {
			visibilityMod += 2; 
		} else if(visibility.equals("Night - Full Moon")) {
			visibilityMod += 3; 
		} else if(visibility.equals("Night - Half Moon")) {
			visibilityMod += 4; 
		} else if(visibility.equals("Night - No Moon")) {
			visibilityMod += 7; 
		} else if(visibility.equals("Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 3; 
		} else if(visibility.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 4; 
		} else if(visibility.equals("Night - Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 5; 
		} else if(visibility.equals("No Visibility - Heavy Fog - White Out")) {
			visibilityMod += 7; 
		}
		
		Unit unit = new Unit();
		unit.X = xCord; 
		unit.Y = yCord; 
		
		if(visibility.substring(0, 4).equals("Night")) {
			
			// Checks if any target units have a IR laser or flashlight on 
			boolean flashLight = false; 
			boolean IRlaser = false; 
			
			for(Unit spottedUnit : game.initiativeOrder) {
				
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
		
		int passes = getSuccess(size, speedModTarget, speedModSpotter, concealmentMod, rangeMod, scanArea, visibilityMod, skillMod, targetSizeMod);
		//System.out.println("Passes: "+passes);
		//System.out.println("Target Number: "+targetNumber);

		ArrayList<Integer> rolls = new ArrayList<Integer>();
		
		// Determines spotted individuals
		if (passes > 0) {
			Random rand = new Random();
			
			int roll;
			boolean duplicate = false;
			
			if (passes > targetTroopers.size()) {
				passes = targetTroopers.size();
			}

			// Sets spotted individuals and unit
			for (int i = 0; i < passes; i++) {
				roll = rand.nextInt(size);
				int spottingDiff = targetTroopers.get(roll).spottingDifficulty;
				if(targetTroopers.get(roll).firedTracers) {
					spottingDiff -= 20; 
				}
				
				/*System.out.println("\nRoll: "+roll);
				System.out.println("Rolls:");*/
				for(int x = 0; x < rolls.size(); x++) {
					//System.out.println("\nRoll"+x+": "+rolls.get(x));
				}
				
				/*System.out.println("\nNew Trooper:");
				System.out.println("Spotting Diff: "+spottingDiff);
				System.out.println("HD: "+targetTroopers.get(roll).HD);
				System.out.println("Unspottable: "+targetTroopers.get(roll).unspottable);
				System.out.println("Alive: "+targetTroopers.get(roll).alive);
				System.out.println("Conscious: "+targetTroopers.get(roll).conscious);
				System.out.println("\n");*/
				if (rolls.contains(roll) || targetTroopers.get(roll).unspottable || successesRoll + spottingDiff >= targetNumber || !targetTroopers.get(roll).alive || !targetTroopers.get(roll).conscious) {
					duplicate = true;
				} else {
					rolls.add(roll);
				}
				// Checks for duplicates and if the individual can be spotted 
				
				ArrayList<Integer> loopCounter = new ArrayList<Integer>();
				
				while (duplicate) {	
					//System.out.println("Duplicate == true");
					int roll2 = rand.nextInt(size);
					spottingDiff = targetTroopers.get(roll2).spottingDifficulty;
					if(targetTroopers.get(roll2).firedTracers) {
						spottingDiff -= 20; 
					}
					/*System.out.println("\nNew Duplicate:");
					System.out.println("Spotting Diff: "+spottingDiff);
					System.out.println("HD: "+targetTroopers.get(roll).HD);
					System.out.println("Unspottable: "+targetTroopers.get(roll).unspottable);
					System.out.println("Alive: "+targetTroopers.get(roll).alive);
					System.out.println("Conscious: "+targetTroopers.get(roll).conscious);
					System.out.println("\n");*/
					
					if (rolls.contains(roll2) || targetTroopers.get(roll2).unspottable || successesRoll + spottingDiff >= targetNumber 
							|| !targetTroopers.get(roll2).alive || !targetTroopers.get(roll2).conscious) {
						duplicate = true;
					} else {
						rolls.add(roll2);
						duplicate = false;
					}
					
					if(loopCounter.size() >= size) {
						//System.out.println("Loop counter break.");
						break;
					}
					
				}
			}

		}
		
		// Checks for individuals possibly spotted by their tracers 
		for(int i = 0; i < targets.size(); i++) {
			//System.out.println("successesRoll: "+successesRoll);
			if(targetTroopers.get(i).firedTracers && targetNumber - successesRoll >= -20 && targetNumber - successesRoll < 0) {
				if(addSpottedIndividual(i, targetTroopers, rolls) > -1) {
					spottedIndividuals.add(targetTroopers.get(i));
				}
			}

		}

		// Sets spotted individuals and unit
		//System.out.println("\nRolls: "+rolls.size());
		for (int i = 0; i < rolls.size(); i++) {
			//targetTroopers.get(rolls.get(i)).number = rolls.get(i) + 1;
			spottedIndividuals.add(targetTroopers.get(rolls.get(i)));
			
			spottedUnits.add(targetTroopers.get(rolls.get(i)).returnTrooperUnit(game));
			
		}
		
		/*for(int i = 0; i < spottedIndividuals.size();i ++) {
			//System.out.println("\n Added Trooper 2: "+spottedIndividuals.get(i).number+" "+spottedIndividuals.get(i).name);
		}*/
		
	}

	// Not used, not needed with new spotting 
	// Gets speed
	// Gets concealment
	// Gets behavior
	// Gets skill diff
	// Makes calculations
	// Margin 10
	// Stores spotted individuals in the spotted modifier array
	public void SpotHex(Unit spotterUnit, int xCord, int yCord, Trooper spotter, String scanArea, String visibility, ArrayList<Unit> initiativeOrder, GameWindow game) {
		//System.out.println("Multi Unit Hex Spot");
		int skillMod = 0; 
		
		
		/*int roll3 = new Random().nextInt(100) + 1; 
		
		if(roll3 <= spotter.spotListen) {
			int margin = spotter.spotListen - roll3; 
			
			skillMod = 1; 
			
			skillMod += margin / 10; 
			
			
		}*/
		
		
		
		this.spotterUnit = spotterUnit;
		
		//System.out.println("\n\nSpotting\n\n");

		// Gets target units 
		
		ArrayList<Unit> targets = new ArrayList<Unit>();

		for(Unit unit : initiativeOrder) {
			
			if(!unit.side.equals(spotterUnit) && xCord == unit.X && yCord == unit.Y) {
				targets.add(unit);
			}
			
		}
		
		if(targets.size() <= 0) {
			//game.conflictLog.addNewLine("No units in target hex.");
			return;
		}
		
		this.spottedUnit = null;
		
		// Size of target unit
		int size = 0;
		
		for(Unit unit : targets) {
			for(Trooper trooper : unit.individuals) {
				boolean sameHex = spotterUnit.X == unit.X && spotterUnit.Y == unit.Y;
				if(sameHex && trooper.alive && trooper.conscious) {
					size++;
					continue; 
				}
				
				if(!trooper.HD && trooper.alive && trooper.conscious)
					size++; 
				
			}
		}
		
		//System.out.println("Target Unit Size: "+size);
		
		// Speed
		int speedModTarget = 0;

		int speedModSpotter = 0;
		
	
		ArrayList<Trooper> targetTroopers = new ArrayList<Trooper>();
		
		for(Unit unit : targets) {
			
			if(unit.getSize() > 0) {
				for(Trooper trooper : unit.getTroopers()) {
					boolean sameHex = spotterUnit.X == unit.X && spotterUnit.Y == unit.Y;
					if(sameHex && trooper.alive && trooper.conscious) {
						targetTroopers.add(trooper);
						continue; 
					}
					
					if(!trooper.HD && trooper.alive && trooper.conscious)
						targetTroopers.add(trooper);
					
				}
				
			}
			
			
		}
		
		int targetSizeMod = getTargetSizeMod(targetTroopers);
		
		// Concealment
		String concealment = targets.get(0).concealment;
		int concealmentMod = getConcealmentMod(concealment);
		if(concealmentMod < Building.buildingConcealmentMod && Building.majorityEmbarked(game, targetTroopers)) {
			concealmentMod = Building.buildingConcealmentMod; 
		}
		// Behavior
		

		// Range
		int rangeMod = getRangeMod(targets.get(0), spotterUnit);

		// Skill
		// int skillMod = getSkillMod(spotter, targetUnit);

		// Calculation
		
		//System.out.println("speedModTarget: "+speedModTarget);
		//System.out.println("speedModSpotter: "+speedModSpotter);
		//System.out.println("concealmentMod: "+concealmentMod);
		//System.out.println("rangeMod: "+rangeMod);
		
		int visibilityMod = 0; 
		
		if(spotter.thermalVision) {
			//System.out.println("Thermal vision: -7");
			visibilityMod -= 7;
			visibilityModifications += "Thermal Vision(-7); ";
		}
		
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
				
		if(visibility.equals("Good Visibility")) {
			visibilityMod += 0; 
		} else if(visibility.equals("Dusk")) {
			visibilityMod += 2; 
		} else if(visibility.equals("Night - Full Moon")) {
			visibilityMod += 3; 
		} else if(visibility.equals("Night - Half Moon")) {
			visibilityMod += 4; 
		} else if(visibility.equals("Night - No Moon")) {
			visibilityMod += 7; 
		} else if(visibility.equals("Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 3; 
		} else if(visibility.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 4; 
		} else if(visibility.equals("Night - Smoke/Fog/Haze/Overcast")) {
			visibilityMod += 5; 
		} else if(visibility.equals("No Visibility - Heavy Fog - White Out")) {
			visibilityMod += 7; 
		}
		
		
		Unit unit = new Unit();
		unit.X = xCord; 
		unit.Y = yCord; 
		
		
		
		
		if(visibility.substring(0, 4).equals("Night")) {
			
			// Checks if any target units have a IR laser or flashlight on 
			boolean flashLight = false; 
			boolean IRlaser = false; 
			
			for(Unit spottedUnit : game.initiativeOrder) {
				
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
		
		
		
		
		int passes = getSuccess(size, speedModTarget, speedModSpotter, concealmentMod, rangeMod, scanArea, visibilityMod, skillMod, targetSizeMod);
		//System.out.println("Passes: "+passes);
		//System.out.println("Target Number: "+targetNumber);

		ArrayList<Integer> rolls = new ArrayList<Integer>();

		// Determines spotted individuals
		if (passes > 0) {
			Random rand = new Random();
			
			int roll;
			boolean duplicate = false;
			
			if (passes > targetTroopers.size()) {
				passes = targetTroopers.size();
			}

			// Sets spotted individuals and unit
			for (int i = 0; i < passes; i++) {
				roll = rand.nextInt(size);
				int spottingDiff = targetTroopers.get(roll).spottingDifficulty;
				if(targetTroopers.get(roll).firedTracers) {
					spottingDiff -= 20; 
				}
				
				if(spottedAll(spotter, targetTroopers, successesRoll, spottingDiff, targetNumber)) {
					break; 
				}
				
				/*System.out.println("\nRoll: "+roll);
				System.out.println("Rolls:");*/
				/*for(int x = 0; x < rolls.size(); x++) {
					System.out.println("\nRoll"+x+": "+rolls.get(x));
				}*/
				
				/*System.out.println("\nNew Trooper:");
				System.out.println("Spotting Diff: "+spottingDiff);
				System.out.println("HD: "+targetTroopers.get(roll).HD);
				System.out.println("Unspottable: "+targetTroopers.get(roll).unspottable);
				System.out.println("Alive: "+targetTroopers.get(roll).alive);
				System.out.println("Conscious: "+targetTroopers.get(roll).conscious);
				System.out.println("\n");*/
				if (rolls.contains(roll) || targetTroopers.get(roll).unspottable || successesRoll + spottingDiff >= targetNumber || !targetTroopers.get(roll).alive || !targetTroopers.get(roll).conscious) {
					duplicate = true;
				} else {
					rolls.add(roll);
				}
				// Checks for duplicates and if the individual can be spotted 
				
				ArrayList<Integer> loopCounter = new ArrayList<Integer>();
				
				while (duplicate) {	
					//System.out.println("Duplicate == true");
					int roll2 = rand.nextInt(size);
					spottingDiff = targetTroopers.get(roll2).spottingDifficulty;
					if(targetTroopers.get(roll2).firedTracers) {
						spottingDiff -= 20; 
					}
					if(spottedAll(spotter, targetTroopers, successesRoll, spottingDiff, targetNumber)) {
						break; 
					}
					/*System.out.println("\nNew Duplicate:");
					System.out.println("Spotting Diff: "+spottingDiff);
					System.out.println("HD: "+targetTroopers.get(roll).HD);
					System.out.println("Unspottable: "+targetTroopers.get(roll).unspottable);
					System.out.println("Alive: "+targetTroopers.get(roll).alive);
					System.out.println("Conscious: "+targetTroopers.get(roll).conscious);
					System.out.println("\n");*/
					
					if (rolls.contains(roll2) || targetTroopers.get(roll2).unspottable || successesRoll + spottingDiff >= targetNumber 
							|| !targetTroopers.get(roll2).alive 
							|| !targetTroopers.get(roll2).conscious) {
						duplicate = true;
					} else {
						rolls.add(roll2);
						duplicate = false;
					}
					
					
					if(loopCounter.size() >= size) {
						//System.out.println("Loop counter break, spot action");
						break;
					}
					
				}
			}


		}
		
		// Checks for individuals possibly spotted by their tracers 
		for(int i = 0; i < targetTroopers.size(); i++) {
			//System.out.println("successesRoll: "+successesRoll);
			if(targetTroopers.get(i).firedTracers && targetNumber - successesRoll >= -20 && targetNumber - successesRoll < 0) {
				if(addSpottedIndividual(i, targetTroopers, rolls) > -1) {
					spottedIndividuals.add(targetTroopers.get(i));
				}
			}
			
		}

		// Sets spotted individuals and unit
		System.out.println("\nRolls: "+rolls.size());
		for (int i = 0; i < rolls.size(); i++) {
			//targetTroopers.get(rolls.get(i)).number = rolls.get(i) + 1;
			spottedIndividuals.add(targetTroopers.get(rolls.get(i)));
		}
		
		/*for(int i = 0; i < spottedIndividuals.size();i ++) {
			System.out.println("\n Added Trooper 2: "+spottedIndividuals.get(i).number+" "+spottedIndividuals.get(i).name);
		}*/

		
	}

	// Gets speed
	// Gets concealment
	// Gets behavior
	// Gets skill diff
	// Makes calculations
	// Margin 10
	// Stores spotted individuals in the spotted modifier array
	public Spot(GameWindow gameWindow, Unit spotterUnit, Unit targetUnit, Trooper spotter, String scanArea, String visibility, ArrayList<Unit> initOrder, GameWindow game) {
		// Checks if multiple units in hex 
		
		//System.out.println("Spot Passing 1, Spotter Unit: "+spotterUnit.callsign+", Target Unit: "+targetUnit.callsign+", X: "
			//	+targetUnit.X+", Y: "+targetUnit.Y);
		
		/*for(Unit unit : initOrder) {
			if(!unit.compareTo(targetUnit) && unit.side.equals(targetUnit.side) && targetUnit.X == unit.X && targetUnit.Y == unit.Y) {
				System.out.println("Multi Hex Spot: Unit: "+unit.callsign+", X: "+unit.X+", Y: "+unit.Y);
				SpotHex(spotterUnit, targetUnit.X, targetUnit.Y, spotter, scanArea, visibility, initOrder, game);
				return; 
			}
		}*/
		
		//System.out.println("Spot Passing 2");
		
		// IDK if this makes it too easy 
		int skillTestMod = 0; 
		
		
		int roll3 = new Random().nextInt(100) + 1; 
		
		if(roll3 <= spotter.getSkill("Spot/Listen")) {
			int margin = spotter.getSkill("Spot/Listen") - roll3; 
			
			skillTestMod = 1; 
			
			skillTestMod += margin / 10; 
			
			
		} else {
			int margin = spotter.getSkill("Spot/Listen") - roll3; 
			skillTestMod--; 
			skillTestMod += margin / 10; 
		}
		
		if(skillTestMod >= 5)
			skillTestMod = 3; 
		else if(skillTestMod <= -5)
			skillTestMod = -3;
		else 
			skillTestMod /= 2;
		
		this.spotterUnit = spotterUnit;
		
		//System.out.println("\n\nSpotting\n\n");

		// Gets target unit troopers
		ArrayList<Trooper> targets = new ArrayList<>();
		
		boolean sameHex = spotterUnit.X == targetUnit.X && spotterUnit.Y == targetUnit.Y;
		for(Trooper trooper : targetUnit.individuals) {
			if(sameHex && trooper.alive && trooper.conscious) {
				targets.add(trooper);
				continue;
			}
			
			if(!trooper.HD && trooper.alive && trooper.conscious)
				targets.add(trooper);
			
		}
		

		// Size of target unit
		
		int size = targets.size();

		
		
		
		
		int targetSizeMod = getTargetSizeMod(targets); 
		
		
		
		
		
		//System.out.println("Target Unit Size: "+size);
		
		// Speed
		String targetUnitSpeed = targetUnit.speed;
		int speedModTarget = getSpeedModTarget(targetUnitSpeed);
		String spotterUnitSpeed = spotterUnit.speed;
		int speedModSpotter = getSpeedModSpotter(spotterUnitSpeed);

		// Concealment
		String concealment = "None";
		
		
		int concealmentValue = CalculateLOS.getConcelamentValue(spotterUnit, targetUnit);
		
		if (concealmentValue == 1) {
			concealment = "Level 1";
		} else if (concealmentValue == 2) {
			concealment = "Level 2";
		}else if (concealmentValue == 3) {
			concealment = "Level 3";
		}else if (concealmentValue == 4) {
			concealment = "Level 4";
		}else if (concealmentValue >= 5) {
			concealment = "Level 5";
		} 
		
		int concealmentMod = getConcealmentMod(concealment);
		
		if(concealmentMod < Building.buildingConcealmentMod && Building.majorityEmbarked(gameWindow, targetUnit.individuals)) {
			concealmentMod = Building.buildingConcealmentMod; 
		}
		
		// Behavior
		//

		int hexDiff = GameWindow.hexDif(targetUnit, spotterUnit);
		
		// Range
		int rangeMod = getRangeMod(targetUnit, spotterUnit);

		// Skill
		// int skillMod = getSkillMod(spotter, targetUnit);

		// Calculation
		
		//System.out.println("speedModTarget: "+speedModTarget);
		//System.out.println("speedModSpotter: "+speedModSpotter);
		//System.out.println("concealmentMod: "+concealmentMod);
		//System.out.println("rangeMod: "+rangeMod);
		
		int visibilityMod = 0; 
		
		if(spotter.thermalVision) {
			//System.out.println("Thermal vision: -7");
			visibilityMod -= 7;
			visibilityModifications += "Thermal Vision(-7); ";
		}
		
		
		Weapons wep = new Weapons();
		wep = wep.findWeapon(spotter.wep);
		
		if(spotter.magnification > 0 || wep.magnification > 0) {
			
			int spotMagnification = 0;
			
			if(spotter.magnification > wep.magnification)
				spotMagnification = spotter.magnification;
			else 
				spotMagnification = wep.magnification;
			
			if(spotMagnification <= 4) {
				visibilityModifications += "Magnification less than 4(-5); ";
				visibilityMod -= 2; 
			} else if(spotMagnification <= 8) {
				visibilityModifications += "Magnification less than 8(-5); ";
				visibilityMod -= 3; 
			} else if(spotMagnification <= 12) {
				visibilityModifications += "Magnification less than 12(-5); ";
				visibilityMod -= 4; 
			} else if(spotMagnification <= 24) {
				visibilityModifications += "Magnification less than 24(-5); ";
				visibilityMod -= 5; 
			} else {
				visibilityModifications += "Greater Magnification than 24(-6); ";
				visibilityMod -= 6; 
			}
			
		}
		
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
				
		if(visibility.equals("Good Visibility")) {
			visibilityModifications += "Good Visibility(0); ";
			visibilityMod += 0; 
		} else if(visibility.equals("Dusk")) {
			visibilityModifications += "Dusk(2); ";
			visibilityMod += 2; 
		} else if(visibility.equals("Night - Full Moon")) {
			visibilityModifications += "Night - Full Moon(3); ";
			visibilityMod += 3; 
		} else if(visibility.equals("Night - Half Moon")) {
			visibilityModifications += "Night - Half Moon(4); ";
			visibilityMod += 4; 
		} else if(visibility.equals("Night - No Moon")) {
			visibilityModifications += "Night - No Moon(7); ";
			visibilityMod += 7; 
		} else if(visibility.equals("Smoke/Fog/Haze/Overcast")) {
			visibilityModifications += "Smoke/Fog/Haze/Overcast(3)";
			visibilityMod += 3; 
		} else if(visibility.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
			visibilityModifications += "Dusk - Smoke/Fog/Haze/Overcast(4); ";
			visibilityMod += 4; 
		} else if(visibility.equals("Night - Smoke/Fog/Haze/Overcast")) {
			visibilityModifications += "Night - Smoke/Fog/Haze/Overcast(5); ";
			visibilityMod += 5; 
		} else if(visibility.equals("No Visibility - Heavy Fog - White Out")) {
			visibilityModifications += "No Visibility - Heavy Fog - White Out(7); ";
			visibilityMod += 7; 
		}

		if(visibility.substring(0, 4).equals("Night") || visibility.substring(0, 3).equals("Dusk")) {
			
			// Checks if any target units have a IR laser or flashlight on 
			boolean flashLight = false; 
			boolean IRlaser = false; 
			
			for(Unit spottedUnit : game.initiativeOrder) {
				
				if(spottedUnit.X == targetUnit.X && spottedUnit.Y == targetUnit.Y) {
					
					for(Trooper trooper : spottedUnit.individuals) {
						
						if(trooper.HD || !trooper.alive || !trooper.conscious)
							continue; 
						
						if(trooper.weaponIRLaserOn)
							IRlaser = true; 
						
						if(trooper.weaponLightOn) 
							flashLight = true; 
						
					}
					
					
				}
				
			}
			
			
			if(flashLight) {
				visibilityModifications += "Target Using Flash Light(-5); ";
				visibilityMod -= 5; 
			}
			
			if(spotter.nightVisionInUse) {
				
				if(spotter.nightVisionEffectiveness == 1) {
					visibilityModifications += "NVGs(-1); ";
					visibilityMod -= 1; 
				} else if(spotter.nightVisionEffectiveness == 2) {
					visibilityModifications += "NVGs(-2); ";
					visibilityMod -= 2; 
				} else if(spotter.nightVisionEffectiveness == 3) {
					visibilityMod -= 3; 
					visibilityModifications += "NVGs(-3); ";
				} else if(spotter.nightVisionEffectiveness == 4) {
					visibilityMod -= 4; 
					visibilityModifications += "NVGs(-4); ";
				} else if(spotter.nightVisionEffectiveness == 5) {
					visibilityMod -= 5;
					visibilityModifications += "NVGs(-5); ";
				}
				
				if(IRlaser)
					visibilityMod -= 4; 
				
			} else if(!spotter.nightVisionInUse && spotter.weaponLightOn && GameWindow.hexDif(spotterUnit, targetUnit) <= 1) {
				visibilityModifications += "Inside Flashlight Range(-4); ";
				visibilityMod -= 4; 
			}
			
		}
		
		//System.out.println("visibilityMod: "+visibilityMod);
		int passes = getSuccess(size, speedModTarget, speedModSpotter, concealmentMod, rangeMod, scanArea, visibilityMod, skillTestMod, targetSizeMod);
		//System.out.println("Passes: "+passes);
		//System.out.println("Target Number: "+targetNumber);
		
		ArrayList<Integer> rolls = new ArrayList<Integer>();
		
		// Determines spotted individuals
		if (passes > 0) {
			Random rand = new Random();
			
			int roll;
			boolean duplicate = false;
			
			if (passes > targets.size()) {
				passes = targets.size();
			}
			
			// Sets spotted individuals and unit
			for (int i = 0; i < passes; i++) {
				roll = rand.nextInt(size);
				int spottingDiff = targets.get(roll).spottingDifficulty;
				visibilityModifications += "Spotting Diff("+spottingDiff+"); ";
				if(targets.get(roll).firedTracers) {
					visibilityModifications += "Target Fired Tracers(-20%); ";
					spottingDiff -= 20; 
				}
				
				if(spottedAll(spotter, targetUnit, successesRoll, spottingDiff, targetNumber, rolls)) {
					break; 
				}
				
				/*System.out.println("\nRoll: "+roll);
				System.out.println("Rolls:");
				for(int x = 0; x < rolls.size(); x++) {
					System.out.println("\nRoll"+x+": "+rolls.get(x));
				}*/
				
				//System.out.println("Spot Passed Loop 1");
				/*System.out.println("\nNew Trooper:");
				System.out.println("Spotting Diff: "+spottingDiff);
				System.out.println("HD: "+targets.get(roll).HD);
				System.out.println("Unspottable: "+targets.get(roll).unspottable);
				System.out.println("Alive: "+targets.get(roll).alive);
				System.out.println("Conscious: "+targets.get(roll).conscious);
				System.out.println("\n");*/
				this.canSpotSpotterUnit = spotterUnit;
				if (rolls.contains(roll) || !canSpot(targets.get(roll),  successesRoll, spottingDiff, targetNumber)) {
					duplicate = true;
				} else {
					//newSpottedTroopers.add(targets.get(rolls.get(roll)));
					rolls.add(roll);
					//spottedIndividuals.add(targets.get(rolls.get(roll)));
				}
				// Checks for duplicates and if the individual can be spotted 
								
				//System.out.println("Spot Passed Loop 2");
				
				ArrayList<Integer> loopCounter = new ArrayList<Integer>();
				
				while (duplicate) {	
					
					if(spottedAll(spotter, targetUnit, successesRoll, spottingDiff, targetNumber, rolls)) {
						break; 
					}
					
					//System.out.println("Spot Passed Loop 3");
					
					//System.out.println("Duplicate == true");
					int roll2 = rand.nextInt(size);
					//System.out.println("Roll2: "+roll2);
					spottingDiff = targets.get(roll2).spottingDifficulty;
					visibilityModifications += "Spotting Diff("+spottingDiff+"); ";
					if(targets.get(roll2).firedTracers) {
						visibilityModifications += "Target Fired Tracers(-20%); ";
						spottingDiff -= 20; 
					}
					
					/*System.out.println("\nNew Duplicate:");
					System.out.println("Spotting Diff: "+spottingDiff);
					System.out.println("HD: "+targets.get(roll).HD);
					System.out.println("Unspottable: "+targets.get(roll).unspottable);
					System.out.println("Alive: "+targets.get(roll).alive);
					System.out.println("Conscious: "+targets.get(roll).conscious);
					System.out.println("\n");*/
					
					//System.out.println("Trooper: "+targets.get(roll2));
					//System.out.println("Can Spot: "+canSpot(targets.get(roll2),  successesRoll, spottingDiff, targetNumber)+", Contains: "+rolls.contains(roll2));
					
					this.canSpotSpotterUnit = spotterUnit;
					if (rolls.contains(roll2) || !canSpot(targets.get(roll2),  successesRoll, spottingDiff, targetNumber)) {
						duplicate = true;
					} else {
						//newSpottedTroopers.add(targets.get(rolls.get(roll2)));
						rolls.add(roll2);
						//spottedIndividuals.add(targets.get(rolls.get(roll2)));
						duplicate = false;
						break; 
					}

					if(loopCounter.size() >= size) {
						//System.out.println("Loop counter break, spot action.");
						break;
					}
					
					//System.out.println("Spot Passed Loop 4");
				}
				
				//System.out.println("Spot Passed Loop 5");
			}

			
			
			
			

		}
		
		//System.out.println("Spot Passing 3");
		
		// Checks for individuals possibly spotted by their tracers 
		for(int i = 0; i < targets.size(); i++) {
			//System.out.println("successesRoll: "+successesRoll);
			if(targets.get(i).firedTracers && targetNumber - successesRoll >= -20 && targetNumber - successesRoll < 0) {
				if(addSpottedIndividual(i, targets, rolls) > -1) {
					spottedIndividuals.add(targets.get(i));
				}
			}
			
		}

		// Sets spotted individuals and unit
		//System.out.println("\nRolls: "+rolls.size());
		for (int i = 0; i < rolls.size(); i++) {
				//targets.get(rolls.get(i)).number = rolls.get(i) + 1;
				spottedIndividuals.add(targets.get(rolls.get(i)));
		}
		
		for(int i = 0; i < spottedIndividuals.size();i ++) {
			//System.out.println("\n Added Trooper: "+spottedIndividuals.get(i));
		}
		
		// Sets spotted unit
		this.spottedUnit = targetUnit;
		
		//System.out.println("Spot Passing 4");
		
		
		resultsString += "Target Size: "+size+", Average PC Size: "+PCSize+", Target Unit Speed: "+targetUnitSpeed+", Spotter Unit Speed: "+spotterUnitSpeed+
				", Target Concealment: "+concealment+", Hex Range: "+hexDiff+"\n"+"Visibility Modifications: "+visibilityModifications+"\n PC Spot Modifiers: "+
				"Skill Test Mod: "+skillTestMod+", Target Size Mod: "+targetSizeMod+", Target Speed Mod: "+speedModTarget+", Spotter Speed Mod: "+speedModSpotter
				+", Concealment Mod: "+concealmentMod+", Range Mod: "+rangeMod+", Visibility Mod: "+visibilityMod+"\n"+"SLM: "+SLM;
		
		
	}

	
	// Returns true if the trooper can be spotted 
	public boolean canSpot(Trooper trooper, int successesRoll, int spottingDiff, int targetNumber) {
		boolean sameHex = false;
		if(canSpotSpotterUnit != null) {
			sameHex = canSpotSpotterUnit.X == trooper.returnTrooperUnit(GameWindow.gameWindow).X &&
					canSpotSpotterUnit.Y == trooper.returnTrooperUnit(GameWindow.gameWindow).Y;
		}
		
		if((trooper.HD && !sameHex) || trooper.unspottable || successesRoll - spottingDiff >= targetNumber 
				|| !trooper.alive || !trooper.conscious) {
			return false; 
		} else 
			return true; 
		
	}
	
	// Returns true if a trooper is already spotted 
	public boolean alreadySpotted(Trooper spotter, Trooper targetTrooper, Unit targetUnit, ArrayList<Integer> newSpottedTroopers) {
		
		for(Spot spotAction : spotter.spotted) {
			/*System.out.println("Spotted Action: "+spotAction.spottedIndividuals.contains(targetTrooper)+
					", Already New Spotted Trooper: "+alreadyNewSpottedTrooper(targetUnit, newSpottedTroopers, targetTrooper));*/
			if(spotAction.spottedIndividuals.contains(targetTrooper) || alreadyNewSpottedTrooper(targetUnit, newSpottedTroopers, targetTrooper))
				return true; 
			
		}
		
		return false; 
	
	}
	
	
	public boolean alreadyNewSpottedTrooper(Unit targetUnit, ArrayList<Integer> newSpottedTroopers, Trooper targetTrooper) {
		
		for(Integer i : newSpottedTroopers) {
			
			if(targetTrooper.compareTo(targetUnit.individuals.get(i)))
				return true; 
			
		}
		
		return false; 
	}
	
	// Returns true if a trooper has spotted all of a unit 
	public boolean spottedAll(Trooper spotter, Unit targetUnit, int successesRoll, int spottingDiff, int targetNumber, ArrayList<Integer> newSpottedTroopers) {
		
		boolean spottedAll = true; 
		
		// Checks if every individual in target unit is inside a spotted individuals list or cannot be spotted 
		for(Trooper trooper : targetUnit.individuals) {
			//System.out.println("Already Spotted: "+alreadySpotted(spotter, trooper, targetUnit, newSpottedTroopers)+", Can Spot: "+canSpot(trooper, successesRoll, spottingDiff, targetNumber));
			canSpotSpotterUnit = spotter.returnTrooperUnit(GameWindow.gameWindow);
			if(!canSpot( trooper, successesRoll, spottingDiff, targetNumber) || alreadySpotted(spotter, trooper, targetUnit, newSpottedTroopers)) 
				continue; 
			else 
				spottedAll = false; 
		}
		
		//System.out.println("Spotted All: "+spottedAll);
		return spottedAll; 
		
	} 
	
	// Returns true if a trooper has spotted all of a unit 
	public boolean spottedAll(Trooper spotter, ArrayList<Trooper> targetTroopers, int successesRoll, int spottingDiff, int targetNumber) {
		
		boolean spottedAll = true; 
		
		// Checks if every individual in target unit is inside a spotted individuals list or cannot be spotted 
		for(Trooper trooper : targetTroopers) {
			canSpotSpotterUnit = spotter.returnTrooperUnit(GameWindow.gameWindow);
			if(!canSpot(trooper, successesRoll, spottingDiff, targetNumber) || alreadySpotted(spotter, trooper, null, null)) 
				continue; 
			else 
				spottedAll = false; 
		}
		
		return spottedAll; 
		
	} 
	
	public int addSpottedIndividual(int roll, ArrayList<Trooper> targets, ArrayList<Integer> rolls) {
		int spottingDiff = targets.get(roll).spottingDifficulty;
		if(targets.get(roll).firedTracers) {
			//System.out.println("Pass Tracers");
			spottingDiff -= 20; 
		}
		
		//System.out.println("Rolls contains test: "+rolls.contains(roll));

		for(int x = 0; x < rolls.size(); x++) {
			//System.out.println("\nRoll"+x+": "+rolls.get(x));
		}
		
		
		if (rolls.contains(roll) || targets.get(roll).HD || targets.get(roll).unspottable || successesRoll + spottingDiff >= targetNumber || !targets.get(roll).alive || !targets.get(roll).conscious) {
			//System.out.println("Add spotted individual fail, -1, spot action smh");
			return -1;
		} else {
			//System.out.println("Pass123");
			return roll;
		}

		
	}
	
	// Checks with unit speed and assigns a PC value to it
	// Crawl 1/3 hex
	// Walk 1 hex
	// Rush 2 hex
	public int getSpeedModTarget(String speed) {
		int mod = 0;

		if (speed.equals("Walk")) {
			mod = -6;
		} else if (speed.equals("Crawl")) {
			mod = -1;
		} else if (speed.equals("Rush")) {
			mod = -7;
		}

		return mod;
	}

	public int getSpeedModSpotter(String speed) {
		int mod = 0;

		if (speed.equals("Walk")) {
			mod = +2;
		} else if (speed.equals("Crawl")) {
			mod = +3;
		} else if (speed.equals("Rush")) {
			mod = +5;
		}

		return mod;
	}

	// Gets the concealment level and assigns the value to it
	public int getConcealmentMod(String concealment) {
		int mod = 0;

		if (concealment.equals("Level 1")) {
			mod = 1;
		} else if (concealment.equals("Level 2")) {
			mod = 3;
		} else if (concealment.equals("Level 3")) {
			mod = 4;
		} else if (concealment.equals("Level 4")) {
			mod = 5;
		} else if (concealment.equals("Level 5")) {
			mod = 8;
		}

		return mod;

	}

	
	
	
	
	public int getTargetSizeMod(ArrayList<Trooper> targets) {
		PCSize = 0; 
		for(Trooper targetTrooper : targets) {
			
			if(targetTrooper.stance.equals("Standing")) {
				//System.out.println("Standing");
				PCSize += targetTrooper.PCSize / 1; 
			} else if(targetTrooper.stance.equals("Crouching")) {
				//System.out.println("Crouching");
				PCSize += targetTrooper.PCSize / 1.25;
			} else {
				//System.out.println("Prone");
				PCSize += targetTrooper.PCSize / 2; 
			}
			
		}
		
		PCSize /= targets.size();
		
		if(PCSize <= -8) {
			return 8; 
		} else if(PCSize <= -3) {
			return 6; 
		} else if(PCSize <= 0) {
			return 4; 
		} else if(PCSize <= 2) {
			return 3; 
		} else if(PCSize <= 4) {
			return 2; 
		} else if(PCSize <= 6) {
			return 1; 
		} else if(PCSize <= 8) {
			return 0; 
		} else if(PCSize <= 10) {
			return -1; 
		} else if(PCSize <= 14) {
			return -2; 
		} else if(PCSize <= 20) {
			return -5; 
		} else if(PCSize <= 26) {
			return -7; 
		} else if(PCSize <= 32) {
			return -10; 
		} else if(PCSize <= 40) {
			return -12; 
		}
		
		return 0; 
	}

	// Compares the diff to the PC range chart
	// Gets mod
	public int getRangeMod(Unit target, Unit spotter) {
		int hexes = GameWindow.hexDif(target, spotter);

		int mod = 0;
		int yards = hexes * GameWindow.hexSize;
		int pcHexes = yards / 2;
		
		//System.out.println("Hexes: " + hexes + "; Yards: " + yards + "; pcHexes: " + pcHexes);
		// Range array

		ArrayList<ArrayList<Integer>> rangeMods = new ArrayList<ArrayList<Integer>>(19);
		for (int i = 1; i <= 19; i++) {
			rangeMods.add(new ArrayList<Integer>());
		}

		rangeMods.get(0).add(0, 2);
		rangeMods.get(1).add(0, 3);
		rangeMods.get(2).add(0, 4);
		rangeMods.get(3).add(0, 6);
		rangeMods.get(4).add(0, 8);
		rangeMods.get(5).add(0, 11);
		rangeMods.get(6).add(0, 16);
		rangeMods.get(7).add(0, 23);
		rangeMods.get(8).add(0, 32);
		rangeMods.get(9).add(0, 45);
		rangeMods.get(10).add(0, 64);
		rangeMods.get(11).add(0, 90);
		rangeMods.get(12).add(0, 130);
		rangeMods.get(13).add(0, 180);
		rangeMods.get(14).add(0, 260);
		rangeMods.get(15).add(0, 360);
		rangeMods.get(16).add(0, 510);
		rangeMods.get(17).add(0, 720);
		rangeMods.get(18).add(0, 1020);

		rangeMods.get(0).add(1, 1);
		rangeMods.get(1).add(1, 2);
		rangeMods.get(2).add(1, 3);
		rangeMods.get(3).add(1, 4);
		rangeMods.get(4).add(1, 5);
		rangeMods.get(5).add(1, 6);
		rangeMods.get(6).add(1, 7);
		rangeMods.get(7).add(1, 8);
		rangeMods.get(8).add(1, 9);
		rangeMods.get(9).add(1, 10);
		rangeMods.get(10).add(1, 11);
		rangeMods.get(11).add(1, 12);
		rangeMods.get(12).add(1, 13);
		rangeMods.get(13).add(1, 14);
		rangeMods.get(14).add(1, 15);
		rangeMods.get(15).add(1, 16);
		rangeMods.get(16).add(1, 17);
		rangeMods.get(17).add(1, 18);
		rangeMods.get(18).add(1, 19);

		//System.out.println("range mods array: " + rangeMods.toString());

		mod = search(rangeMods, pcHexes);

		return mod;
	}

	public int search(ArrayList<ArrayList<Integer>> arr, int target) {
		int value = 0;
		//System.out.println("Target: " + target);
		//System.out.println("Array: " + arr.toString());
		int temp1;
		int temp2;
		for (int i = 1; i <= arr.size() - 1; i++) {
			temp1 = arr.get(i).get(0);
			temp2 = arr.get(i - 1).get(0);
			if (target <= temp1 && target >= temp2) {
				value = arr.get(i).get(1);
				//System.out.println("Value: " + value);
			}

		}

		return value;

	}

	public int searchSecond(ArrayList<ArrayList<Integer>> arr, int target) {
		target--;
		int value = 0;
		//System.out.println("Target: " + target);
		//System.out.println("Array: " + arr.toString());
		int temp1;
		int temp2;
		for (int i = 1; i <= arr.size() - 1; i++) {
			temp1 = arr.get(i).get(1);
			temp2 = arr.get(i - 1).get(1);
			if (target <= temp1 && target >= temp2) {
				value = arr.get(i).get(0);
				//System.out.println("Value: " + value);
			}

		}

		return value;

	}

	
	
	public int spotActionSpottingChance; 
	public int spotActionDiceRoll; 
	
	// Calculates target number
	// Rolls dice
	// Returns success
	public int getSuccess(int size, int speedModTarget, int speedModSpotter, int concealmentMod, int rangeMod,
			String scanArea, int visibilityMod, int skillMod, int targetSizeMod) {
		
		
		//System.out.println("Skill Mod: "+skillMod);
		
		int success = 0;
		//System.out.println("speedModTarget: " + speedModTarget + "; speedModSpotter: " + speedModSpotter
		//		+ "; concealmentMod: " + concealmentMod + "; rangeMod: " + rangeMod + "scanArea: " + scanArea);
		SLM = speedModTarget + speedModSpotter + concealmentMod + rangeMod + visibilityMod - skillMod + targetSizeMod;

		if(concealmentMod == 1) {
			concealmentMod = 1; 
		} else if(concealmentMod == 2) {
			concealmentMod = 3;
		} else if(concealmentMod == 3) {
			concealmentMod = 5;
		} else if(concealmentMod == 4) {
			concealmentMod = 8;
		} else if(concealmentMod == 5) {
			concealmentMod = 9;
		}
		
		ArrayList<ArrayList<Integer>> SpottingTable = new ArrayList<ArrayList<Integer>>(19);
		for (int i = 1; i <= 19; i++) {
			SpottingTable.add(new ArrayList<Integer>());
		}

		if (scanArea.equals("60 Degrees")) {
			SpottingTable.get(0).add(0, 100);
			SpottingTable.get(1).add(0, 100);
			SpottingTable.get(2).add(0, 100);
			SpottingTable.get(3).add(0, 100);
			SpottingTable.get(4).add(0, 80);
			SpottingTable.get(5).add(0, 58);
			SpottingTable.get(6).add(0, 40);
			SpottingTable.get(7).add(0, 28);
			SpottingTable.get(8).add(0, 20);
			SpottingTable.get(9).add(0, 14);
			SpottingTable.get(10).add(0, 10);
			SpottingTable.get(11).add(0, 7);
			SpottingTable.get(12).add(0, 5);
			SpottingTable.get(13).add(0, 4);
			SpottingTable.get(14).add(0, 3);
			SpottingTable.get(15).add(0, 1);
			SpottingTable.get(16).add(0, 1);
			SpottingTable.get(17).add(0, 0);
			SpottingTable.get(18).add(0, 0);

		} else if (scanArea.equals("180 Degrees")) {
			SpottingTable.get(0).add(0, 100);
			SpottingTable.get(1).add(0, 100);
			SpottingTable.get(2).add(0, 83);
			SpottingTable.get(3).add(0, 56);
			SpottingTable.get(4).add(0, 42);
			SpottingTable.get(5).add(0, 30);
			SpottingTable.get(6).add(0, 21);
			SpottingTable.get(7).add(0, 14);
			SpottingTable.get(8).add(0, 10);
			SpottingTable.get(9).add(0, 7);
			SpottingTable.get(10).add(0, 5);
			SpottingTable.get(11).add(0, 4);
			SpottingTable.get(12).add(0, 3);
			SpottingTable.get(13).add(0, 2);
			SpottingTable.get(14).add(0, 1);
			SpottingTable.get(15).add(0, 1);
			SpottingTable.get(16).add(0, 0);
			SpottingTable.get(17).add(0, 0);
			SpottingTable.get(18).add(0, 0);

		} else {
			SpottingTable.get(0).add(0, 100);
			SpottingTable.get(1).add(0, 100);
			SpottingTable.get(2).add(0, 100);
			SpottingTable.get(3).add(0, 75);
			SpottingTable.get(4).add(0, 60);
			SpottingTable.get(5).add(0, 48);
			SpottingTable.get(6).add(0, 39);
			SpottingTable.get(7).add(0, 32);
			SpottingTable.get(8).add(0, 28);
			SpottingTable.get(9).add(0, 24);
			SpottingTable.get(10).add(0, 21);
			SpottingTable.get(11).add(0, 18);
			SpottingTable.get(12).add(0, 16);
			SpottingTable.get(13).add(0, 14);
			SpottingTable.get(14).add(0, 12);
			SpottingTable.get(15).add(0, 7);
			SpottingTable.get(16).add(0, 4);
			SpottingTable.get(17).add(0, 2);
			SpottingTable.get(18).add(0, 2);
		}

		SpottingTable.get(0).add(1, 1);
		SpottingTable.get(1).add(1, 2);
		SpottingTable.get(2).add(1, 3);
		SpottingTable.get(3).add(1, 4);
		SpottingTable.get(4).add(1, 5);
		SpottingTable.get(5).add(1, 6);
		SpottingTable.get(6).add(1, 7);
		SpottingTable.get(7).add(1, 8);
		SpottingTable.get(8).add(1, 9);
		SpottingTable.get(9).add(1, 10);
		SpottingTable.get(10).add(1, 11);
		SpottingTable.get(11).add(1, 12);
		SpottingTable.get(12).add(1, 13);
		SpottingTable.get(13).add(1, 14);
		SpottingTable.get(14).add(1, 15);
		SpottingTable.get(15).add(1, 16);
		SpottingTable.get(16).add(1, 17);
		SpottingTable.get(17).add(1, 18);
		SpottingTable.get(18).add(1, 19);

		//System.out.println("Spotting Table: " + SpottingTable.toString());
		//System.out.println("Spotting Modifier: " + SLM);
		int spottingChance = searchSecond(SpottingTable, SLM);

		//System.out.println("Spot Chance: " + spottingChance);

		if (SLM <= 1) {
			spottingChance = 100;
		} else if (SLM > 19) {
			spottingChance = 0;
		}

		// Table two, 11C Group Spotting Table
		if (size > 2) {
			
			
			if(size > 24)
				size = 24; 
			
			try {
				FileInputStream excelFile = new FileInputStream(new File(System.getProperty("user.dir")+"\\groupspottingtable.xlsx"));
				Workbook workbook = new XSSFWorkbook(excelFile);
				org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);
				
				int col = 0; 
				int row = 0; 
				for(Cell cell : worksheet.getRow(0)) {
					
					if(cell.getCellType() == CellType.NUMERIC) {
						
						if(size <= cell.getNumericCellValue()) {
							col = cell.getColumnIndex();
							break; 
						}
						
					}
					
				}
				
				for(int i = 1; i < 18; i++) {
					//System.out.println("spottingChance1: "+spottingChance);
					//System.out.println("CellSC: "+worksheet.getRow(i).getCell(0).getNumericCellValue());
					if(spottingChance <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
						row = i; 
						break; 
					}
					
					
				}
				//System.out.println("Row: "+row);
				//System.out.println("Col: "+col);
				spottingChance = (int) worksheet.getRow(row).getCell(col).getNumericCellValue();
				//System.out.println("Group Spotting Chance: "+spottingChance);
				
				
				workbook.close();
				excelFile.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}

		// Roll and success calculations

		Random rand = new Random();

		int roll = rand.nextInt(100) + 1;
		spotActionDiceRoll = roll; 
		successesRoll = roll; 
		targetNumber = spottingChance;
		spotActionSpottingChance = spottingChance;
		if (roll <= spottingChance) {
			success++;
		}
		int margin = spottingChance - roll;
		//System.out.println("Margin: " + margin);
		success += margin / 10;
		//System.out.println("Success: " + success);
		this.success = success;
		return success;
	}

	public void displayResultsQueue(GameWindow gameWindow, Spot spotAction) {
		
		// Print results
				gameWindow.conflictLog.addNewLineToQueue("Spotted: \n"
						+ "Results: " + resultsString + "\n" 
						+ "Target Number: " + spotActionSpottingChance + " Roll: " + spotActionDiceRoll 
						+ "\nSuccess: "
						+ spotAction.success + "\n" + spotAction.spottedIndividuals.toString());
		
	}
	
	public void displayResults(GameWindow gameWindow, Spot spotAction) {
		
		// Print results
				gameWindow.conflictLog.addNewLine("Spotted: \n"
						+ "Results: " + resultsString + "\n" 
						+ "Target Number: " + spotActionSpottingChance + " Roll: " + spotActionDiceRoll 
						+ "\nSuccess: "
						+ spotAction.success + "\n" + spotAction.spottedIndividuals.toString());
		
	}
	
}
