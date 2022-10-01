package Actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Conflict.GameWindow;
import Hexes.Building.Room;
import Hexes.Hex;
import Injuries.Injuries;
import Items.Weapons;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;
import UtilityClasses.PCUtility;

public class TargetedFire implements Serializable {
	private String path = System.getProperty("user.dir");
	private int hits = 0;
	public int TN = 0;
	private int shots = 0;

	public boolean calledShot = false; 
	public int possibleShots;
	public int shotsTaken;
	public int PCHits = 0;
	public int maxAim; 
	public int spentAimTime = 0;
	//public int spentCA = 0;
	public int fullAutoROF = 0; 
	public int allottedCA = 0;
	public int spentCA = 0; 
	public int sabCount = 0; 
	public int timeToReaction = 0;
	public int ALMSum;	
	public int rangeALM;
	public int visibilityALM;
	public int aim;
	public int stanceMod;
	public int sizeALM; 
	public int EAL = 0; 
	public int BA;
	public int speedALM; 
	public int rawAimMod;
	public int weaponConditionMod; 
	public int rangeInPCHexes;
	public GameWindow game; 
	public boolean consecutiveShots = false; 
	public int EALBonus;
	public int percentBonus;
	public Unit targetUnit; 
	public Trooper shooterTrooper; 
	public Trooper targetTrooper; 
	public Weapons weapon; 
	
	public String fullAutoResults = ""; 
	
	public TargetedFire(Trooper shooterTrooper, Trooper targetTrooper, Unit shooterUnit , Unit targetUnit, GameWindow game, int manualAim, int combatActions, int EALBonus, int percentBonus, int manualRange, String weaponName) {
		
		//System.out.println("Calculating EAL: ");
		
		if(shooterTrooper.closeCombatTarget != null 
				&& shooterTrooper.closeCombatTarget.compareTo(targetTrooper)) {
			
			Hex hex = game.findHex(shooterUnit.X, shooterUnit.Y);
			
			int shooterRoom = hex.getTrooperBuilding(shooterTrooper).getTrooperRoomNumber(shooterTrooper);
			int targetRoom = hex.getTrooperBuilding(targetTrooper).getTrooperRoomNumber(targetTrooper);
			int diameter = hex.getTrooperBuilding(shooterTrooper).getTrooperRoom(shooterTrooper).diameter;
			
			if(shooterRoom == targetRoom) {
				//System.out.println("Rooms equal. using room range.");
				
				//manualRange = new Random().nextInt(shooterRoom.diameter) + 1; 
				manualRange = diameter;
			} else {
				//System.out.println("Rooms not euqal. using hall range.");
				manualRange = diameter * 2; 
			}
			
		}
		
		this.maxAim = manualAim;
		this.weapon = new Weapons().findWeapon(weaponName);
		this.game = game; 
		this.EALBonus = EALBonus; 
		this.percentBonus = percentBonus; 
		
		this.ALMSum += EALBonus; 
		this.EALBonus += EALBonus; 
		//System.out.println("EAL Bonus: "+EALBonus);
		//combatActions += combatActions; 
		
		this.allottedCA = combatActions; 
		this.targetTrooper = targetTrooper; 
		this.shooterTrooper = shooterTrooper; 
		this.targetUnit = targetUnit; 
		//System.out.println("Manual Range: "+manualRange);
		
		if(manualRange == 0)
			this.rangeInPCHexes = GameWindow.hexDif(targetUnit, shooterUnit) * (GameWindow.hexSize / 2); 
		else 
			this.rangeInPCHexes = manualRange;
		
		//System.out.println("Original Hex Diff: "+rangeInPCHexes);
		int EAL = 0; 		
		
		if(this.rangeInPCHexes == 0) {
			
			//System.out.println("     Close Combat Calculations:");
			if(shooterTrooper.pcRanges.containsKey(targetTrooper)) {
				rangeInPCHexes = shooterTrooper.pcRanges.get(targetTrooper);
			} else if(targetTrooper.pcRanges.containsKey(shooterTrooper)) {
				rangeInPCHexes = targetTrooper.pcRanges.get(shooterTrooper);
			} else {
				int range = new Random().nextInt(10) + 1; 
				shooterTrooper.pcRanges.put(targetTrooper, range);
				rangeInPCHexes = range; 
			}
			
			//System.out.println("           PC Hexes: "+rangeInPCHexes);
		}
		
		// Looking at light 
		if(game.visibility.substring(0, 4).equals("Night") && targetTrooper.weaponLightOn && rangeInPCHexes <= 10) {
			//System.out.println("Looking Into Light EAL -8");
			EAL -= 8; 
		}
		
		if(shooterTrooper.weaponLaserOn && rangeInPCHexes <= 10) {
			//System.out.println("Laser On EAL +2");
			EAL += 2; 
		} else if(shooterTrooper.weaponIRLaserOn && rangeInPCHexes <= 25 && shooterTrooper.nightVisionInUse) {
			//System.out.println("Weapon IR Laser On EAL +2");
			EAL += 2; 
		}
		
		int rangeALM = PCUtility.findRangeALM(rangeInPCHexes); 

		targetUnit.setStance(targetTrooper, game);
		String stance = targetTrooper.stance;
	
		sizeALM = findSizeALM(stance, targetTrooper.PCSize);
		
		if(weapon.type.equals("Launcher")) {
			sizeALM = 12; 
		}
		
		if(targetTrooper.inCover)
			sizeALM = 0; 
		
		//System.out.println("Size ALM: "+sizeALM);
		
		speedALM = findSpeedALM(targetUnit.speed, rangeInPCHexes, targetTrooper.number, targetUnit, targetTrooper);
		int visibilityALM = PCUtility.findVisibiltyALM(targetUnit, shooterTrooper, rangeInPCHexes); 
		
		// TODO: add penalty for looking into light and using night vision and looking into a light 


		Weapons wep = weapon; 
		
		//System.out.println("troooper wep: "+shooterTrooper.wep+", Wep: "+wep.name);
		
		if(wep.fullAutoROF > 0)
			fullAutoROF = wep.fullAutoROF;
		
		// Finds Size ALM
		// Finds Speed ALM 
		// Finds Visibility ALM 
		//System.out.println("Range EAL: "+rangeALM);
		//System.out.println("sizeALM EAL: "+sizeALM+", Target Stance: "+targetTrooper.stance);
		//System.out.println("visibilityALM EAL: "+visibilityALM);
		int aim = 0;
		int stanceMod = 0;
		//System.out.println("Base Aim EAL: "+aim);
		
		EAL = EAL + rangeALM + sizeALM + speedALM + visibilityALM; 
		
		if(shooterTrooper.inCover || shooterTrooper.stance == "Prone" && !wep.staticWeapon ) {
			stanceMod += wep.bipod;
			// Prone, prone not fully implemented. 
			//System.out.println("EAL Bipod: +"+wep.bipod);
			stanceMod += 6; 
			//System.out.println("Prone EAL: +6");
		} else if(shooterTrooper.stance == "Crouched") {
			//System.out.println("Crouched EAL: +3");
			stanceMod += 3; 
		}
		
		// Tripod/bipod mounted static weapon 
		if(wep.staticWeapon && wep.assembled) {
			//System.out.println("Static Wep: +5");
			stanceMod += 5; 
		}
		
		int maxAim = 0;
		if(shooterTrooper.disabledArms > 1) {
			if(1 < maxAim)
				maxAim = 1; 
		} else if(shooterTrooper.disabledArms > 0) {
			if(maxAim != 1 && maxAim != 2) {
				if(3 < maxAim)
					maxAim = 3; 
			}
			
		}
		
		if(shooterTrooper.arms - shooterTrooper.disabledArms < 2) {
			
			if(wep.type.equals("Pistol") || wep.type.equals("Subgun")) {
				EAL -= 4;
			} else {
				EAL -= 7;
			}
			
		}
		
		if(shooterTrooper.nightVisionInUse) {
			if(maxAim > shooterTrooper.nightVisionEffectiveness)
				maxAim = shooterTrooper.nightVisionEffectiveness;
		}
		
		if(shooterTrooper.thermalVisionInUse) {
			if(maxAim > 7)
				maxAim = 7; 
		}
		
		//System.out.println("EAL Before Aiming: "+EAL);
		//System.out.println("Max Aim Before Aiming: "+maxAim);
		// IF EAL satisfactory, perform shot, otherwise increase 
		
		if(shooterTrooper.storedAimTime.containsKey(targetTrooper)) {
			
			spentAimTime = shooterTrooper.storedAimTime.get(targetTrooper);
			if(maxAim > 0)
				spentAimTime += maxAim;
			
			//System.out.println("Stored EAL: "+aim(shooterTrooper, spentAimTime, wep));
			aim += aim(shooterTrooper, spentAimTime, wep);
		} else if(manualAim == 0)  {
			aim += weaponConditionsMod(wep, 0);
			aim += aim(shooterTrooper, 0, wep);
		} else if(manualAim > -1) {
			for(int i = 0; i < manualAim; i++) {
				spentAimTime++; 
				spentCA++; 
				
				if(maxAim > 0 && spentAimTime > maxAim)
					break;
				
				if(i != 0) {
					aim -= aim(shooterTrooper, spentAimTime - 1, wep);
					aim -= weaponConditionsMod(wep, spentAimTime - 1);
				}
				
				aim += weaponConditionsMod(wep, spentAimTime);
				aim += aim(shooterTrooper, spentAimTime, wep);
				//System.out.println("Aim EAL: "+weaponConditionsMod(wep, spentAimTime)+ aim(shooterTrooper, spentAimTime, wep));
				
			}
		} else {
			int count = 0; 
			while(EAL < 17) {
				
				//System.out.println("Aim EAL: "+EAL);
				spentAimTime++; 
				spentCA++; 
				
				if(maxAim > 0 && spentAimTime > maxAim)
					break;
				
				if(count != 0) {
					aim -= aim(shooterTrooper, spentAimTime - 1, wep);
					aim -= weaponConditionsMod(wep, spentAimTime - 1);
				}
				count++; 
				aim -= weaponConditionsMod(wep, spentAimTime - 1);
				aim += weaponConditionsMod(wep, spentAimTime);
				//System.out.println("Aim EAL: "+weaponConditionsMod(wep, spentAimTime));
				aim += aim(shooterTrooper, spentAimTime, wep);
				if(spentAimTime == wep.aimTime.size() - 1 || possibleShots(wep.name) == 1) {
					break; 
				}
				
			}
			
		}
		//System.out.println("Weapon modifications EAL: "+weaponConditionsMod(wep, spentAimTime));
		aim += weaponConditionsMod(wep, spentAimTime);
		
		
		EAL += stanceMod + aim + EALBonus; 
		// WTF is this line 
		this.ALMSum = EAL - sizeALM;
		//System.out.println("ALM(omitting size): "+this.ALMSum);
		this.EAL = EAL; 
		//System.out.println("EAL(including size): "+this.EAL);
		this.BA = wep.getBA(rangeInPCHexes);
		//System.out.println("BA: "+this.BA);
		this.possibleShots = possibleShots(wep.name);
		this.rangeALM = rangeALM;
		this.visibilityALM = visibilityALM;
		this.aim = aim;
		this.stanceMod = stanceMod;
		//System.out.println("Possible Shots: "+this.possibleShots);
		setTargetNumber();
		timeToReaction(targetTrooper, targetUnit);
		
		/*System.out.println("AIM ALM: "+aim(shooterTrooper, spentAimTime, wep)+", Weapon ALM Mod: "+weaponConditionsMod(wep, spentAimTime));
		System.out.println("EAL: "+EAL+", BA:"+BA+", Range ALM: "+rangeALM+", Size ALM: "+sizeALM+", Speed ALM: "+speedALM+" Visibility ALM: "+visibilityALM+" Aim ALM: "+aim+" Stance Mod EAL: "+stanceMod+" EAL Bonus: "+EALBonus);
		
		System.out.println("MaxAim: "+this.maxAim);
		System.out.println("spentAimTime: "+this.spentAimTime);
		System.out.println("ALMSum: "+this.ALMSum);
		System.out.println("EAL: "+this.EAL);
		System.out.println("BA: "+this.BA);
		System.out.println("rangeInPCHexes: "+this.rangeInPCHexes);*/
	}

	

	
	
	// Set full auto values 
	public void setFullAuto() {
	
		this.possibleShots = possibleShotsFullAuto(); 
		
		
	}
	
	// Determines time to reaction 
	public void timeToReaction(Trooper targetTrooper, Unit targetUnit) {
		
		int fighterSkill = targetTrooper.fighter;
		int supp  = targetUnit.suppression;
		
		int TN = fighterSkill + supp; 
		
		int roll = new Random().nextInt(100) + 1; 
		
		int margin = TN - roll; 
		int failure = 0;
		
		if(margin >= 0) {
			failure++; 
			
			failure += Math.abs(margin) / 10;
			
		}
		
		if(failure < 1)
			failure = 1; 
		
		if(timeToReaction == 0) {
			timeToReaction = failure; 
		}
		
		
	}
	
	// Returns number of shots that could be fired 
	public int possibleShots(String wepName) {
		
		Weapons wep = weapon; 
		
		int possibleShots = allottedCA - spentCA;
		
		if(wep.boltPump)
			possibleShots--; 
		
		setTargetNumber();
		return possibleShots; 
	} 
	
	// Returns number of shots that could be fired 
	// Full auto 
	public int possibleShotsFullAuto() {
				
		int possibleShots = allottedCA - spentCA;
		
		setFullAutoTargetNumber();
		return possibleShots; 
	} 
	
	// Sets Target Number 
	public void setTargetNumber() {
		int EAL = this.EAL;
		//System.out.println("setTargetNumber");
		//System.out.println("BA: "+BA);
		//System.out.println("EAL: "+EAL);
		//System.out.println("ALMSum"+ALMSum);
		if(BA < ALMSum) {
			EAL = BA; 
		} 

		TN = 0;
		
		
		FileInputStream excelFile;
		try {
			
			//System.out.println("File Path: "+path+"\\PC Hit Calc Xlsxs\\EAL.xlsx");
			
			excelFile = new FileInputStream(new File(path+"\\PC Hit Calc Xlsxs\\EAL.xlsx"));
			
			/*excelFile = new FileInputStream(new File("C:\\Users\\Xander\\OneDrive - Colostate\\Xander Personal\\Code\\eclipse-workspace\\CFFW\\PC Hit Calc Xlsxs\\"
					+ "EAL.xlsx"));*/
			Workbook workbook = new XSSFWorkbook(excelFile);
		    Sheet worksheet = workbook.getSheetAt(0);
		    
		    int row = 0; 
		    for(int i = 1; i < 40; i++) {
		    	//System.out.println("EAL: "+EAL); 
		    	//System.out.println("EAL Row: "+worksheet.getRow(i).getCell(0).getNumericCellValue()); 
		    	if(EAL >= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
		    		//System.out.println("EAL Row: "+worksheet.getRow(i).getCell(0).getNumericCellValue()); 
		    		row = i; 
		    		break; 
		    	}
		    		
		    	
		    }
		    
		    TN = (int) worksheet.getRow(row).getCell(1).getNumericCellValue();
		    //System.out.println("DEBUG TN: "+TN);
		    Unit shooterUnit = shooterTrooper.returnTrooperUnit(game);
		    TN -= shooterUnit.suppression;
		    TN -= targetTrooper.DALM;
		    TN -= (shooterTrooper.physicalDamage / 100) * 4;
		    //System.out.println("TN: "+TN+", Suppression mod: "+targetUnit.suppression+", DALM: "+targetTrooper.DALM);
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
	
	// Sets Target Number 
	public void setFullAutoTargetNumber() {
		int EAL = this.EAL;
				
		//System.out.println("setFullAutoTargetNumber");
		//System.out.println("BA: "+BA);
		//System.out.println("EAL: "+EAL);
		//System.out.println("ALMSum"+ALMSum);
		if(BA < ALMSum) {
			EAL = BA; 
		} 

		TN = 0;
		
		FileInputStream excelFile;
		try {
			excelFile = new FileInputStream(new File(path+"\\PC Hit Calc Xlsxs\\"
					+ "EAL.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
		    Sheet worksheet = workbook.getSheetAt(0);
		    
		    int row = 0; 
		    for(int i = 1; i < 40; i++) {
		    	//System.out.println("EAL: "+EAL); 
		    	//System.out.println("EAL Row: "+worksheet.getRow(i).getCell(0).getNumericCellValue()); 
		    	if(EAL >= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
		    		//System.out.println("EAL Row: "+worksheet.getRow(i).getCell(0).getNumericCellValue()); 
		    		row = i; 
		    		break; 
		    	}
		    		
		    	
		    }
		    
		    TN = (int) worksheet.getRow(row).getCell(2).getNumericCellValue();
		    //System.out.println("DEBUG TN: "+TN);
		    TN -= shooterTrooper.returnTrooperUnit(game).suppression / 2;
		    TN -= targetTrooper.DALM;
		    TN -= (shooterTrooper.physicalDamage / 100) * 4;
		    //System.out.println("Full Auto TN: "+TN);
		    excelFile.close();
		    workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		double minimumArc = weapon.getMA(rangeInPCHexes); 
    	String ma = Double.toString(minimumArc);

		try {
			//System.out.println("Full Auto Hits Pass 1");
	    	excelFile = new FileInputStream(new File(path+"\\PC Hit Calc Xlsxs\\"
					+ "automaticfire.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
		    Sheet worksheet = workbook.getSheetAt(0);
		    
		    int row = 0; 
		    int col = 0; 
		    for(int i = 1; i < 20; i++) {
		    	
		    	if(minimumArc <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
		    		
		    		row = i; 
		    		//System.out.println("Full Auto Hits Pass 2");
		    		break; 
		    	}
		    	
		    }
		    
		    for(int i = 1; i < 14; i++) {
		    	
		    	if(fullAutoROF <= worksheet.getRow(0).getCell(i).getNumericCellValue()) {
		    		col = i; 
		    		//System.out.println("Full Auto Hits Pass 3");
		    		break; 
		    	}
		    	
		    }
		    
		   /* String rowCell = Integer.toString(row);
		    String columnCell = Integer.toString(col);*/
		    
		    String result = ""; 
		  
		    
		    if(worksheet.getRow(row).getCell(col).getCellType() == CellType.NUMERIC) {
		    	
		    	int secondTN = (int) worksheet.getRow(row).getCell(col).getNumericCellValue();
		    	fullAutoResults = Integer.toString(secondTN);

		    	
		    } else {
		    	
		    	result = worksheet.getRow(row).getCell(col).getStringCellValue();
		    	fullAutoResults = result; 

		    }

		    
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
	
	// Full auto burst 
	public void fullAutoBurst(boolean sab) {
		String wepName = weapon.name;
		setFullAutoTargetNumber();
		String ma = "";
		String cellResults = "";
		String rowCell = "";
		String columnCell = "";
		int roll = new Random().nextInt(100);
		
		int fatiguePenalty = 0; 
		
		for(int i = 0; i < shooterTrooper.fatigueSystem.fatiguePoints.get(); i++) {
			fatiguePenalty += DiceRoller.d6_exploding();
		}
		
		roll += fatiguePenalty; 
		
		roll -= percentBonus; 
		if(roll <= TN) {
	    	// Hit 
	    	
	    	//game.conflictLog.addNewLine("Full Auto Elv. Hit From: ");
	    	
	    	
	    	double minimumArc = new Weapons().findWeapon(wepName).getMA(rangeInPCHexes); 
	    	ma = Double.toString(minimumArc);
	    	FileInputStream excelFile;
			try {
				//System.out.println("Full Auto Hits Pass 1");
		    	excelFile = new FileInputStream(new File(path+"\\PC Hit Calc Xlsxs\\"
						+ "automaticfire.xlsx"));
				Workbook workbook = new XSSFWorkbook(excelFile);
			    Sheet worksheet = workbook.getSheetAt(0);
			    
			    int row = 0; 
			    int col = 0; 
			    for(int i = 1; i < 20; i++) {
			    	
			    	if(minimumArc <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
			    		
			    		row = i; 
			    		//System.out.println("Full Auto Hits Pass 2");
			    		break; 
			    	}
			    	
			    }
			    
			    for(int i = 1; i < 14; i++) {
			    	
			    	if(fullAutoROF <= worksheet.getRow(0).getCell(i).getNumericCellValue()) {
			    		col = i; 
			    		//System.out.println("Full Auto Hits Pass 3");
			    		break; 
			    	}
			    	
			    }
			    
			    rowCell = Integer.toString(row);
			    columnCell = Integer.toString(col);
			    
			    String result = ""; 
			   
			    int hits = 0; 
			    int secondTN = 0; 
			    
			    if(worksheet.getRow(row).getCell(col).getCellType() == CellType.NUMERIC) {
			    	
			    	secondTN = (int) worksheet.getRow(row).getCell(col).getNumericCellValue();
			    	//System.out.println("Full Auto Hits Pass 4 Second TN: "+secondTN);
			    	int secondRoll = new Random().nextInt(100) + 1; 
			    	//System.out.println("Second Roll: "+secondRoll);
			    	
			    	cellResults = Integer.toString(secondTN);
			    	
			    	if(secondRoll <= secondTN)
			    		hits++; 
			    	
			    	if(hits > 0) {
			    		game.conflictLog.addNewLineToQueue("Full Auto Shots Hit From: "+shooterTrooper.number+" "+shooterTrooper.name
				    			+", to "+targetUnit.side+":: "+targetUnit.callsign+", "+targetTrooper.number+" "+targetTrooper.name+" 1st Roll: "+roll+" 1st TN: "+TN+
				    			" Second Roll: "+secondRoll+" Second TN: "+secondTN+", Fatigue Penalty: "+fatiguePenalty+", Hits: "+hits);
			    	} else {
			    		game.conflictLog.addNewLineToQueue("Full Auto Shots Miss From: "+shooterTrooper.number+" "+shooterTrooper.name
				    			+", to "+targetUnit.side+":: "+targetUnit.callsign+", "+targetTrooper.number+" "+targetTrooper.name+" 1st Roll: "+roll+" 1st TN: "+TN+
				    			" Second Roll: "+secondRoll+" Second TN: "+secondTN+", Fatigue Penalty: "+fatiguePenalty);
			    	}
			    	
			    } else {
			    	
			    	result = worksheet.getRow(row).getCell(col).getStringCellValue();
			    	cellResults = result; 
			    	//System.out.println("Full Auto Hits Pass 5 Results: "+result);
			    	if(result.charAt(0) == '*') {
				    	hits = Integer.parseInt(result.substring(1));
				    }
			    	
			    	game.conflictLog.addNewLineToQueue("Full Auto Hits From: "+shooterTrooper.number+" "+shooterTrooper.name
			    			+", to "+targetUnit.side+":: "+targetUnit.callsign+", "+targetTrooper.number+" "+targetTrooper.name+" Table Result: "+result+", Hits: "+hits);
			    }

			    if(hits > 0) {
			    	targetUnit.suppression += hits; 
			    	PCHits = hits; 
			    } 
			    
			    game.conflictLog.addNewLineToQueue("Weapon: "+wepName+", MA: "+ma+", Full Auto ROF: "+fullAutoROF+", Cell Results: "+cellResults+" Row: "+rowCell+", Column: "+columnCell);
			    
			    game.conflictLog.addNewLineToQueue("Full Auto Burst EAL: "+EAL+" Shot ALM(Omitting Size ALM): "+ALMSum+", EAL: "+EAL+", BA:"+BA+", Range ALM: "
						+rangeALM+", Aim ALM: "+aim+", Raw Aim:"+rawAimMod+", Weapon Mod ALM: "+weaponConditionMod+", Shooter SL: "+
						shooterTrooper.sl+", Size ALM: "+sizeALM+", Speed ALM: "+speedALM+", Visibility ALM: "+visibilityALM+", Stance Mod EAL: "
						+stanceMod+", EAL Bonus: "+EALBonus+"\n");
			   
			    //System.out.println("Full Auto Shots Hits: "+hits);
			    
			    workbook.close();
			    excelFile.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    } else {
	    	double minimumArc = new Weapons().findWeapon(wepName).getMA(rangeInPCHexes); 
	    	ma = Double.toString(minimumArc);
	    	game.conflictLog.addNewLineToQueue("Weapon: "+wepName+", MA: "+ma+", Full Auto ROF: "+fullAutoROF+", Cell Results: "+cellResults+" Row: "+rowCell+", Column: "+columnCell);
	    	
	    	// Miss
	    	game.conflictLog.addNewLineToQueue("Full Auto Burst Miss From: "+shooterTrooper.number+" "+shooterTrooper.name
	    			+", to "+targetUnit.side+":: "+targetUnit.callsign+", "+targetTrooper.number+" "+targetTrooper.name+" Roll: "+roll+" Elevation TN: "+TN+", Fatigue Penalty: "+fatiguePenalty);
	    	
	    	game.conflictLog.addNewLineToQueue("Full Auto Burst EAL: "+EAL+" Shot ALM(Omitting Size ALM): "+ALMSum+", EAL: "+EAL+", BA:"+BA+", Range ALM: "
					+rangeALM+", Aim ALM: "+aim+", Raw Aim:"+rawAimMod+", Weapon Mod ALM: "+weaponConditionMod+", Shooter SL: "+
					shooterTrooper.sl+", Size ALM: "+sizeALM+", Speed ALM: "+speedALM+", Visibility ALM: "+visibilityALM+", Stance Mod EAL: "
					+stanceMod+", EAL Bonus: "+EALBonus+"\n");
	    }
		
		if(sab) {
			spentCA++; 
			sabCount++; 
			EAL -= new Weapons().findWeapon(wepName).sab;
			ALMSum -= new Weapons().findWeapon(wepName).sab;
		} else {
			spentCA++; 
			spentCA++; 

			for(int i = 0; i < sabCount; i++) {
				
				EAL += new Weapons().findWeapon(wepName).sab;
				ALMSum += new Weapons().findWeapon(wepName).sab;
				
			}
			
			sabCount = 0; 
		}
		
		
		Weapons wep = weapon; 
		
		if(wep.tracers) {
			shooterTrooper.firedTracers = true; 
 		}
		shooterTrooper.firedWeapons = true; 
		
		shotsTaken++; 
		
		
	}
	
	
	public ArrayList<Integer> calledShotBounds = new ArrayList<Integer>();
	
	public void setCalledShotBounds(int calledShotTarget) {
		
		//System.out.println("Inside set called shot bounds");
		
		int sheetIndex = calledShotTarget - 1; 
		
		if(sheetIndex <= -1)
			return; 
		
		try {
			
	           FileInputStream excelFile = new FileInputStream(new File(path+"\\hitlocationzones.xlsx"));
	           Workbook workbook = new XSSFWorkbook(excelFile);
	           org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(sheetIndex);
	           
	          // System.out.println("Work book 1");
	           
	   		   int ss = 0; 
	   		   
		       for(int i = 1; i < 25; i++) {
		    	   
		    	   Row row = worksheet.getRow(i);
		    	   
		    	   Cell myCell = row.getCell(1);
		    	   
		    	   if(myCell.getCellType() == CellType.NUMERIC) {
		    		   
		    		   //System.out.println("Cell: "+myCell.getNumericCellValue());
		    		   
					   if(ALMSum <= myCell.getNumericCellValue()) {
						   //System.out.println("ALMSum: "+ALMSum);
						   //System.out.println("calledShotBounds: "+calledShotBounds.size());
						   
						   Cell low1 = row.getCell(4);
						   Cell high1 = row.getCell(5); 
						   Cell low2 = row.getCell(6);
						   Cell high2 = row.getCell(7);
						   
						   
						   
						   //System.out.println("Get Cells");
						   
						   int lowBound1;
						   int lowBound2; 
						   int highBound1; 
						   int highBound2; 
						   
						   if(low1.getCellType() != CellType.NUMERIC) {
							  // System.out.println("Pass 1");
							   lowBound1 = -1; 
						   } else {
							  // System.out.println("Pass 2");
							   lowBound1 = (int) low1.getNumericCellValue();
						   }
						   
						   if(low2.getCellType() != CellType.NUMERIC) {
							  // System.out.println("Pass 3");
							   lowBound2 = -1; 
						   } else {
							  // System.out.println("Pass 4");
							   lowBound2 = (int) low2.getNumericCellValue();
						   }
						   
						   if(high1.getCellType() != CellType.NUMERIC) {
							  // System.out.println("Pass 5");
							   highBound1 = -1; 
						   } else {
							  // System.out.println("Pass 6");
							   highBound1 = (int) high1.getNumericCellValue();
						   }
						   
						   if(high2.getCellType() != CellType.NUMERIC) {
							  // System.out.println("Pass 7");
							   highBound2 = -1; 
						   } else {
							   //System.out.println("Pass 8");
							   highBound2 = (int) high2.getNumericCellValue();
						   }
						  
						   
						  /* System.out.println("bounds: "+lowBound1+" "+lowBound2+" "+
								   highBound1 + " "+ highBound2);*/
						   
						   
						   calledShotBounds.add(lowBound1);
						   calledShotBounds.add(highBound1);
						   calledShotBounds.add(lowBound2);
						   calledShotBounds.add(highBound2);
						   
						   ss = (int) row.getCell(2).getNumericCellValue();
						  // System.out.println("SS: "+ss);
						   //System.out.println("calledShotBounds: "+calledShotBounds.size());
						   break; 
					   }
				   }
		    	
		    	  // System.out.println("i: "+i);
		    	   
	   		   }
		       
		       //System.out.println("Work book 2");
		       
		       if(sizeALM < ss)
		    	   ss = sizeALM; 
		       
		       EAL = ALMSum + ss; 
		       
		       if(calledShotBounds.get(2) < 1)
		    	   calledShotBounds.set(2, -1);
		       
	    	   if(calledShotBounds.get(3) < 1)
		    	   calledShotBounds.set(3, -1);
	           
	    	   workbook.close();
	    	   
	       } catch (FileNotFoundException e) {
	           e.printStackTrace();
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
		
		
	}
	
	public int tfRoll; 
	// Performs a single fire shot 	
	public void shot(int targetZone) {
		
		//System.out.println("Inside Shot");
		
		// HEAD -3 EAL 
		// Mid Body +5 EAL 
		// Legs +3 EAL 
		if(!targetTrooper.inCover) {
			
			if(targetZone == 1) {
				EAL -= 3; 
				calledShot = true; 
				
				setCalledShotBounds(targetZone);
				
			}
			else if(targetZone == 2) {
				EAL += 5;
				calledShot = true; 
				setCalledShotBounds(targetZone);
				
			}
			else if(targetZone == 3) {
				EAL += 3;
				calledShot = true; 
				setCalledShotBounds(targetZone);
				
			}
			
			
		} else if(targetZone > 0) {
			calledShot = true; 
			setCalledShotBounds(targetZone);
			//System.out.println("Leaving Set Called Shot Bounds");
		}
		
		
		setTargetNumber();
		spentCA++; 
		
		tfRoll = new Random().nextInt(100);
		
		int fatiguePenalty = 0; 
		
		for(int i = 0; i < shooterTrooper.fatigueSystem.fatiguePoints.get(); i++) {
			fatiguePenalty += DiceRoller.d6_exploding();
		}
		
		tfRoll += fatiguePenalty; 
		
		tfRoll -= percentBonus;
		
		if(weapon.type.contains("Launcher")) {
			game.conflictLog.addNewLineToQueue("Fired Launcher, EAL: "+EAL+" Shot ALM(Omitting Size ALM): "+ALMSum+" TN: "+TN+" Roll: "+tfRoll
					+" Percent Bonus: "+percentBonus+ " Fatigue Penalty: "+fatiguePenalty+", EAL: "+EAL+", BA:"+BA+", Range ALM: "
					+rangeALM+", Aim ALM: "+aim+", Raw Aim:"+rawAimMod+", Weapon Mod ALM: "+weaponConditionMod+", Shooter SL: "+
					shooterTrooper.sl+", Size ALM: "+sizeALM+", Speed ALM: "+speedALM+", Visibility ALM: "+visibilityALM+", Stance Mod EAL: "
					+stanceMod+", EAL Bonus: "+EALBonus+"\n");
		} else {
			game.conflictLog.addNewLineToQueue("Shot EAL: "+EAL+" Shot ALM(Omitting Size ALM): "+ALMSum+" TN: "+TN+" Roll: "+tfRoll
					+" Percent Bonus: "+percentBonus+ " Fatigue Penalty: "+fatiguePenalty+", EAL: "+EAL+", BA:"+BA+", Range ALM: "
					+rangeALM+", Aim ALM: "+aim+", Raw Aim:"+rawAimMod+", Weapon Mod ALM: "+weaponConditionMod+", Shooter SL: "+
					shooterTrooper.sl+", Size ALM: "+sizeALM+", Speed ALM: "+speedALM+", Visibility ALM: "+visibilityALM+", Stance Mod EAL: "
					+stanceMod+", EAL Bonus: "+EALBonus+"\n");
		}
		
		if(tfRoll <= TN) {
	    	// Hit 
	    	game.conflictLog.addNewLineToQueue("Hit From: "+shooterTrooper.number+" "+shooterTrooper.name
	    			+", to "+targetUnit.side+":: "+targetUnit.callsign+", "+targetTrooper.number+" "+targetTrooper.name);
	    	//game.conflictLog.addNewLineToQueue("Single Hit From: ");
	    	PCHits++;
	    	targetUnit.suppression++;
	    } else {
	    	//game.conflictLog.addNewLineToQueue("Single Miss From: ");
	    	// Miss
	    	game.conflictLog.addNewLineToQueue("Miss From: "+shooterTrooper.number+" "+shooterTrooper.name
	    			+", to "+targetUnit.side+":: "+targetUnit.callsign+", "+targetTrooper.number+" "+targetTrooper.name);
	    }
		
		
		shotsTaken++; 
		
		Weapons wep = weapon; 
		
		if(wep.tracers) {
			shooterTrooper.firedTracers = true; 
		}
		
		shooterTrooper.firedWeapons = true; 
		
		/*spentAimTime++; 
		aimAction(EAL, wepName, shooterTrooper);*/
	}
	
	// Additional aim point action 
	public void aimAction(int EAL, String wepName, Trooper shooterTrooper) {
		System.out.println("Weird aim action being used");
		spentCA++; 
		
		Weapons wep = weapon;
		
		
	    EAL -= aim(shooterTrooper, spentAimTime - 1, wep);
		EAL -= weaponConditionsMod(wep, spentAimTime - 1);
		EAL += weaponConditionsMod(wep, spentAimTime);
		EAL += aim(shooterTrooper, spentAimTime, wep);
		
		aim = 0; 
		aim += aim(shooterTrooper, spentAimTime, wep);
		aim += weaponConditionsMod(wep, spentAimTime);
		
		ALMSum -= aim(shooterTrooper, spentAimTime - 1, wep);
		ALMSum -= weaponConditionsMod(wep, spentAimTime - 1);
		ALMSum += weaponConditionsMod(wep, spentAimTime);
		ALMSum += aim(shooterTrooper, spentAimTime, wep);
		
		this.EAL = EAL;  
	}
	
	// Applies various weapon modifications 
	// TODO: hex buildings, in door light on or off, transparent building allows natural light through 
	public int weaponConditionsMod(Weapons wep, int spentAimTime) {
		
		int bonus = 0; 
		
		/*if(!wep.scopeMagnification.equals("")) {
			
			if(wep.scopeMagnification.equals("4-6x")) {
				bonus += wep.getScopeBonus(spentAimTime);
			} else if(wep.scopeMagnification.equals("4-12x")) {
				bonus += wep.getScopeBonus(spentAimTime);
			} else if(wep.scopeMagnification.equals("4-24x")) {
				bonus += wep.getScopeBonus(spentAimTime);
			}
			
			
		}*/
		
		weaponConditionMod = bonus; 
		
		//System.out.println("Weapon Scope Mod: "+wep.getScopeBonus(spentAimTime));
		return bonus;
	}
	
	
	// Aims the shooters weapon
	// Returns aim EAL 
	public int aim(Trooper shooterTrooper, int spentAimTime, Weapons wep) {
		if(spentAimTime < 0)
			return 0; 
		
		int sl = shooterTrooper.sl;
		
		int size =  wep.aimTime.size();
		
		 
		
		//System.out.println("Aim weapon: "+wep.name+", SL: "+sl);
		
		if(spentAimTime >= size) {
			//System.out.println("Aim Result: "+wep.aimTime.get(size - 1)+" + "+sl+" = "+(wep.aimTime.get(size - 1) + sl));
			rawAimMod = wep.aimTime.get( size - 1);
			return wep.aimTime.get( size - 1) + sl;
		} else {
			//System.out.println("Aim Result: "+wep.aimTime.get(spentAimTime)+" + "+sl+" = "+(wep.aimTime.get(spentAimTime) + sl));
			rawAimMod = wep.aimTime.get(spentAimTime);
			return wep.aimTime.get(spentAimTime) + sl;
		}
		
		
	} 
	
	
	
	// Takes unit speed
	// If walking, takes action and determines which units are moving 
	// Returns speed ALM 
	public int findSpeedALM(String speed, int rangeInHexes, int trooperNumber, Unit targetUnit, Trooper targetTrooper) {
		
		int speedALM = 0; 
		
				
		if(speed.equals("None")) {
			return 0; 
		} else if(speed.equals("Rush")) {
			if(rangeInHexes <= 10) {
				
				if(maxAim != 1)
					maxAim = 2;
				speedALM = -10; 
			} else if(rangeInHexes <= 20) {
				if(maxAim != 1)
					maxAim = 2; 
				speedALM = -8;
			} else if(rangeInHexes <= 40) {
				speedALM = -6; 
			} else {
				speedALM = -5; 
			}
			
			
			
		} else {
			
			// Checks if individual is moving; 
			int action = game.game.getCurrentAction(); 
			int trooperMovingInAction = 0; 
	
			
			
			int count = 1; 
			for(Trooper trooper : targetUnit.individuals) {
				
				if(count == 1) {
					if(trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 1; 
						break; 
					}
						
				} else if(count == 2) {
					if(trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 2;
						break; 
					}
				} else {
					if(trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 3;
						break; 
					}
					count = 0; 
				}
				
				count++; 
				
					
			}
			
			if(action == 1) {
				if(trooperMovingInAction == 1) {
					speedALM = findSpeedALM(rangeInHexes);
				}
			} else if(action == 2 )  {
				if(trooperMovingInAction == 2) {
					speedALM = findSpeedALM(rangeInHexes);
				}
			} else if(action == 3) {
				if(trooperMovingInAction == 3) {
					speedALM = findSpeedALM(rangeInHexes);
				}
			} else {
			
				int roll = new Random().nextInt(3) + 1; 
				if(trooperMovingInAction == roll) {
					speedALM = findSpeedALM(rangeInHexes);
				}
				
			}
			
			
			
			
		}
		
		
		
		return speedALM; 
		
	}
	
	// Returns single HPI 
	public int findSpeedALM(int rangeInHexes) {
		
		if(rangeInHexes <= 10) {
			return -8; 
		} else if(rangeInHexes <= 20) {
			return -6;
		}  else {
			return -5; 
		}
		
		
	}
	
	
	// Takes trooper PC size in yards 
	// Modifies trooper size from stance 
	// Finds size ALM 
	// Returns size ALM 
	public int findSizeALM(String stance, double size) {
		
		if(stance.equals("Standing")) {
			//System.out.println("Standing");
			size = size / 1; 
		} else if(stance.equals("Crouching")) {
			//System.out.println("Crouching");
			size = size / 1.25;
		} else {
			//System.out.println("Prone");
			size = size / 2; 
		}
		
		int sizeALM = 0;
		
		// Finds range ALM 
		FileInputStream excelFile;
		try {
			excelFile = new FileInputStream(new File(path+"\\PC Hit Calc Xlsxs\\"
					+ "size.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
		    Sheet worksheet = workbook.getSheetAt(0);
				
		    int row = 0; 
		    
		    for(int i = 1; i <53; i++) {
		    	
		    	if(size <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
		    		row = i; 
		    		break; 
		    	}
		    	
		    }
		    
		    sizeALM = (int) worksheet.getRow(row).getCell(1).getNumericCellValue();
		    workbook.close();
		    excelFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Range ALM: "+sizeALM);
		return sizeALM;
		
	}
	
	
	
	public TargetedFire(int RWS, int bonus, Weapons weapon, int shots, Trooper target, Trooper shooter, Unit targetUnit, Unit shooterUnit, boolean suppressiveFire) {
		int rangeInYards = GameWindow.hexDif(targetUnit, shooterUnit) * GameWindow.hexSize;
		int rangeMod = getRangeMod(rangeInYards);
		int concealmentMod = getConcealmentMod(targetUnit);
		int getSpeedMod = getSpeedMod(targetUnit);
		
		bonus -= shooterUnit.suppression / 2;
		
		if(shooter.disabledArms > 0) {
			RWS = RWS / 2;
		} else if (shooter.disabledArms > 1) {
			RWS = RWS / 3; 
		}
				
		// Suppressive fire is at +30 for target size being a full hex 
		// Supp target size does not matter 
		// Weapon accuracy and magnification matter less that is why it is at half 
		
		// Targeted fire is at +10 for being able to aim longer 
		// Target size matters 
		// Full weapon bonus applies 
		if(suppressiveFire) {
			rangeMod = rangeMod / 2;
			concealmentMod = concealmentMod / 2; 
			bonus += weapon.weaponBonus * 0.5;
			this.TN = RWS + bonus + rangeMod + concealmentMod + getSpeedMod + 30;
			if(this.TN < 5)
				this.TN = 5; 
			else if(this.TN > 50)
				this.TN = 50; 
		} else {
			bonus += target.size;
			bonus += weapon.weaponBonus;
			
			// Applies -20 for rushing 
			if(shooterUnit.speed.equals("Rush")) {
				bonus -= 20;
			}
			
			this.TN = RWS + bonus + rangeMod + concealmentMod + getSpeedMod + 10;
		}
		
		
		
		this.shots = shots;
		
		/*System.out.println("\n\nCalculating Shots \n\n");
		System.out.println("range in yards: "+rangeInYards);
		System.out.println("Range Mod: "+rangeMod);
		System.out.println("Concealment Mod: "+concealmentMod);
		System.out.println("Get Speed Mod: "+getSpeedMod);
		System.out.println("Size: "+target.size);
		System.out.println("Bonus: "+bonus);
		System.out.println("RWS: "+RWS);
		System.out.println("TN: "+this.TN);
		System.out.println("Weapon: "+weapon);*/
		calculateShots();
	}



	// Takes range in yards and returns GURPS percentage penalty
	public int getRangeMod(int rangeInYards) {
		int rangeMod = 0;

		ArrayList<ArrayList<Integer>> rangeMods = new ArrayList<ArrayList<Integer>>(20);
		for (int i = 1; i <= 20; i++) {
			rangeMods.add(new ArrayList<Integer>());
		}

		// 3 yards to 15
		rangeMods.get(0).add(0, 3);
		rangeMods.get(1).add(0, 5);
		rangeMods.get(2).add(0, 7);
		rangeMods.get(3).add(0, 10);
		rangeMods.get(4).add(0, 15);

		// 20 to 100 yards
		rangeMods.get(5).add(0, 20);
		rangeMods.get(6).add(0, 30);
		rangeMods.get(7).add(0, 50);
		rangeMods.get(8).add(0, 70);
		rangeMods.get(9).add(0, 100);

		// 150 to 700 yards
		rangeMods.get(10).add(0, 150);
		rangeMods.get(11).add(0, 200);
		rangeMods.get(12).add(0, 300);
		rangeMods.get(13).add(0, 500);
		rangeMods.get(14).add(0, 700);

		// 1000 to 5000 yards
		rangeMods.get(15).add(0, 1000);
		rangeMods.get(16).add(0, 1500);
		rangeMods.get(17).add(0, 2000);
		rangeMods.get(18).add(0, 3000);
		rangeMods.get(19).add(0, 5000);

		// Penalties 
		
		// 3 yards to 15
		rangeMods.get(0).add(1, -1);
		rangeMods.get(1).add(1, -2);
		rangeMods.get(2).add(1, -3);
		rangeMods.get(3).add(1, -4);
		rangeMods.get(4).add(1, -5);

		// 20 to 100 yards
		rangeMods.get(5).add(1, -6);
		rangeMods.get(6).add(1, -7);
		rangeMods.get(7).add(1, -8);
		rangeMods.get(8).add(1, -9);
		rangeMods.get(9).add(1, -10);

		// 150 to 700 yards
		rangeMods.get(10).add(1, -11);
		rangeMods.get(11).add(1, -12);
		rangeMods.get(12).add(1, -13);
		rangeMods.get(13).add(1, -14);
		rangeMods.get(14).add(1, -15);

		// 1000 to 5000 yards
		rangeMods.get(15).add(1, -16);
		rangeMods.get(16).add(1, -17);
		rangeMods.get(17).add(1, -18);
		rangeMods.get(18).add(1, -19);
		rangeMods.get(19).add(1, -20);

		
		rangeMod = search(rangeMods, rangeInYards);
		if(rangeMod == 0) {
			rangeMod = -1;
		}
		//System.out.println("RANGE MOD: "+rangeMod);
		
		rangeMod = rangeMod * 5; 
		
		return rangeMod;
	}

	// Gets concealment penalty
	public int getConcealmentMod(Unit targetUnit) {
		int mod = 0;
		String concealment = targetUnit.concealment;
		if (concealment.equals("Level 1")) {
			mod = -10;
		} else if (concealment.equals("Level 2")) {
			mod = -20;
		} else if (concealment.equals("Level 3")) {
			mod = -30;
		} else if (concealment.equals("Level 4")) {
			mod = -40;
		} else if (concealment.equals("Level 5")) {
			mod = -50;
		}

		return mod;
	}

	// Gets speed penalty
	public int getSpeedMod(Unit targetUnit) {
		int mod = 0;
		String speed = targetUnit.speed;
		if (speed.equals("Walk")) {
			mod = -5;
		} else if (speed.equals("Crawl")) {
			mod = 0;
		} else if (speed.equals("Rush")) {
			mod = -10;
		}

		return mod;
	}

	// Get target number
	public int getTN() {
		return TN;
	}

	// Get hits
	public int getHits() {
		return hits;
	}

	// Get hits
	public void calculateShots() {
		Random rand = new Random();
		for (int i = 0; i < shots; i++) {
			int roll = rand.nextInt(100) + 1;
			if (roll <= TN) {
				hits++;
				//targetUnit.suppression++;
			}
		}
	}

	// Searches an array of a table
	// Returns the item crossrefereanced in the table
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
				value = arr.get(i - 1).get(1);
				//System.out.println("Value: " + value);
			}

		}

		return value;

	}

}
