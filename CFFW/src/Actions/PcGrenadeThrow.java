package Actions;

import Conflict.GameWindow;
import Conflict.InjuryLog;
import Hexes.Building.Room;
import Explosion.Explosion;
import Items.Weapons;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;
import UtilityClasses.PCUtility;

public class PcGrenadeThrow {

	private int throwerSl; 
	private Trooper thrower; 
	private Trooper target; 
	private Weapons grenade; 
	
	// Stored values for logging results 
	private int hexSizeALM = 12; 
	private int grenadeAimALM = -10; 
	private int visibilityALM; 
	private int rangeALM; 
	private int rollBonus; 
	private int ealBonus; 
	
	// Grenade Aim ALM always -10 
	// Apply thrower throw SL
	// Hex target size 12 ALM
	public PcGrenadeThrow(Trooper thrower, Trooper target, String grenadeName, int rollBonus, int ealBonus) {
		
		throwerSl = (thrower.skills.getSkill("Fighter").value + thrower.skills.getSkill("Throw").value) / 12;
		this.thrower = thrower;
		this.target = target; 
		this.rollBonus = rollBonus; 
		this.ealBonus = ealBonus; 
		grenade = new Weapons().findWeapon(grenadeName);
		
	}
	
		
	public void tossIntoRoom(Room room, boolean fromOutside, int floorNum, int x, int y, String buildingName) {
		Unit throwerUnit = GameWindow.findTrooperUnit(thrower);
		Explosion explosion = new Explosion(grenade);
		InjuryLog.InjuryLog.addAlreadyInjured(room);
		
		// Roll to see if target hit
		if(fromOutside) {
			
			double adjacent=6, opposite=9*floorNum, hypotenuse;          
	        hypotenuse= Math.sqrt((adjacent*adjacent)+(opposite*opposite));
	        
			rangeALM = PCUtility.findRangeALM((int) hypotenuse / 6);
			
			// Hex Size ALM + Grenade Aim Time ALM + Visibility Mod ALM + thrower SL		
			visibilityALM = 0;
			int EAL = hexSizeALM + grenadeAimALM + visibilityALM + throwerSl + rangeALM + ealBonus;
			
			int roll = DiceRoller.roll(0, 99);
			int tn = PCUtility.getOddsOfHitting(true, EAL);
			String rslt = "\nRoll: "+roll+", TN: "+tn+", EAL: "+EAL+", "+"Range ALM: "+rangeALM+", Hex Size ALM: "+hexSizeALM+", Visibilty ALM:"
					+ " "+visibilityALM+", Aim ALM: "+grenadeAimALM+", SL: "+throwerSl+", Roll Bonus: "+rollBonus+", EAL Bonus: "+ealBonus+"\n\n";
			
			if(roll - rollBonus <= tn) {
				GameWindow.addTrooperEntryToLog(thrower, "Grenade throw into building: "+buildingName+" hex "+x+":"+y+" HIT"+", Room Occupants: "+room.occupants.size()+rslt);
		
				// Explode Room 
				for(Trooper target : room.occupants) {
					explosion.explodeTrooper(target, DiceRoller.roll(0, room.diameter));
				}
				
			} else {
				
				// Explode Hex 
				GameWindow.addTrooperEntryToLog(thrower, "Grenade throw into building: "+buildingName+" hex "+x+":"+y+" MISS"+", "+rslt);
				explosion.explodeHex(x, y, throwerUnit.side);
			}

			
		} else {
			GameWindow.addTrooperEntryToLog(thrower, "Grenade throw into room in building: "+buildingName+" hex "+x+":"+y);
			// Explode Room 
			for(Trooper target : room.occupants) {
				//System.out.println("Explode Trooper");
				explosion.explodeTrooper(target, DiceRoller.roll(0, room.diameter));
			}
		}
		
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("GREANDE EXPLOSION\n"+"Total BC Dealt: "+explosion.totalBC+"\n"+"Total Shrap Hits: "+explosion.totalShrapHits+"\n");
		InjuryLog.InjuryLog.printResultsToLog();
	}
	
	// Greande toss method 
	// Called after grenade throw method is created 
	// Takes x and y cord for target hex, 
	// If a target for the through is specified, uses the target unit's x and y cords instead 
	public void toss(int x, int y) {
		
		Unit throwerUnit = GameWindow.findTrooperUnit(thrower);
		Explosion explosion = new Explosion(grenade);
		InjuryLog.InjuryLog.addAlreadyInjured(x, y);
		
		// Roll to see if target hit
		if(target != null) {
			
			Unit targetUnit = GameWindow.findTrooperUnit(target);
			int hexDiff = GameWindow.hexDif(targetUnit, throwerUnit);
			int rangePCHexes;
			
			if(hexDiff == 0) {
				rangePCHexes = DiceRoller.roll(5, 10);
			} else {
				rangePCHexes = hexDiff * 20; 
			}
			rangeALM = PCUtility.findRangeALM(rangePCHexes);
			
			// Hex Size ALM + Grenade Aim Time ALM + Visibility Mod ALM + thrower SL		
			visibilityALM = PCUtility.findVisibiltyALM(targetUnit, thrower, 10);
			int EAL = hexSizeALM + grenadeAimALM + visibilityALM + throwerSl + rangeALM + ealBonus;
			
			x = targetUnit.X;
			y = targetUnit.Y;
			
			int roll = DiceRoller.roll(0, 99);
			int tn = PCUtility.getOddsOfHitting(true, EAL);
			String rslt = "Roll: "+roll+", TN: "+tn+", EAL: "+EAL+", "+"Range ALM: "+rangeALM+" Hex Size ALM: "+hexSizeALM+", Visibilty ALM:"
					+ " "+visibilityALM+", Aim ALM: "+grenadeAimALM+", SL: "+throwerSl+", Roll Bonus: "+rollBonus+", EAL Bonus: "+ealBonus+"\n\n";
			
			if(roll - rollBonus <= tn) {
				explosion.explodeTrooper(target, 0);
				GameWindow.addTrooperEntryToLog(thrower, "Grenade throw into hex "+x+":"+y+" HIT: "+GameWindow.getLogHead(target)+", "+rslt);
			} else {
				GameWindow.addTrooperEntryToLog(thrower, "Grenade throw into hex "+x+":"+y+" MISS: "+GameWindow.getLogHead(target)+", "+rslt);
			}
			
			explosion.excludeTroopers.add(target);
			
		} else {
			GameWindow.addTrooperEntryToLog(thrower, "Grenade throw into hex"+x+":"+y+"\n\n");
		}
		
		
		explosion.explodeHex(x, y, throwerUnit.side);
		
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("GREANDE EXPLOSION\n"+"Total BC Dealt: "+explosion.totalBC+"\n"+"Total Shrap Hits: "+explosion.totalShrapHits+"\n");
		InjuryLog.InjuryLog.printResultsToLog();
	}
	

	
	
	
	
	
	
}
