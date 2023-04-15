package Injuries;

import java.util.ArrayList;

import Artillery.Artillery.Shell;
import Conflict.GameWindow;
import Conflict.InjuryLog;
import Items.PCAmmo;
import Items.Weapons;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;
import UtilityClasses.PCUtility;

public class Explosion {

	Weapons weapon; 
	public int totalBC = 0; 
	public int totalIonDamage = 0;
	public int totalShrapHits = 0; 
	public PCAmmo pcAmmo;
	public Shell shell; 
	
	// Troopers not to be exploded 
	public ArrayList<Trooper> excludeTroopers = new ArrayList<>();
	
	public Explosion(Shell shell) {
		this.shell = shell;
	}
	
	public Explosion(PCAmmo pcAmmo) {
		this.pcAmmo = pcAmmo;
	}
	
	public Explosion(String wepName) {
		weapon = new Weapons().findWeapon(wepName);
	}
	
	public Explosion(Weapons weapon) {
		this.weapon = weapon;
	}
	
	// Resolves all damage related to an explosion in a hex 
	// Randomly determines distance 
	// If a friendly side should be exempt from the explosion such as in grenade tosses, allows specification of friendly side 
	public void explodeHex(int x, int y, String friendlySide) {
		System.out.println("Explode Hex");
		ArrayList<Unit> targetUnits = GameWindow.gameWindow.getUnitsInHexExcludingSide(friendlySide, x, y);
		
		for(Unit unit : targetUnits) {
			
			for(Trooper trooper : unit.individuals) {
				
				if(!excludeTroopers.contains(trooper) && !trooper.inBuilding(GameWindow.gameWindow)) {
					int roll1 = DiceRoller.randInt(0, 10);
					int roll2 = DiceRoller.randInt(0, 10);
					explodeTrooper(trooper, (roll1 < roll2 ? roll1 : roll2));
				}
						
			}
			
			unit.suppression += 10; 
			unit.organization -= 10; 
			
			if(unit.suppression > 100)
				unit.suppression = 100; 
			
			if(unit.organization < 0)
				unit.organization = 0; 
			
		}
	
		explodeAdjacentHexes(x, y, friendlySide);
		
	}
	
	public void explodeAdjacentHexes(int x, int y, String friendlySide) {
		/*-1,+1
		-1,0
		-1,-1
		0,-1
		+1,0
		0,+1*/
		explodeAdjacentHex(x-1, y+1, friendlySide);
		explodeAdjacentHex(x-1, y, friendlySide);
		explodeAdjacentHex(x-1, y-1, friendlySide);
		explodeAdjacentHex(x, y-1, friendlySide);
		explodeAdjacentHex(x+1, y, friendlySide);
		explodeAdjacentHex(x, y+1, friendlySide);
	}
	
	public void explodeAdjacentHex(int x, int y, String friendlySide) {
		ArrayList<Unit> targetUnits = GameWindow.gameWindow.getUnitsInHexExcludingSide(friendlySide, x, y);
		
		for(Unit unit : targetUnits) {
			
			for(Trooper trooper : unit.individuals) {
				
				if(!excludeTroopers.contains(trooper) && !trooper.inBuilding(GameWindow.gameWindow)) {
					
					
					int roll1 = DiceRoller.randInt(0, 5);
					int roll2 = DiceRoller.randInt(0, 5);
					
					int roll3 = DiceRoller.randInt(0, 10);
					int roll4 = DiceRoller.randInt(0, 10);
					explodeTrooper(trooper, (roll1 < roll2 ? roll1 : roll2) + (roll3 < roll4 ? roll3 : roll4));
				}
						
			}
			
			unit.suppression += 3; 
			unit.organization -= 3; 
			
			if(unit.suppression > 100)
				unit.suppression = 100; 
			
			if(unit.organization < 0)
				unit.organization = 0; 
			
		}
	}
	
	// Called on each trooper in a hex that contains an explosion 
	// Called from explode hex 
	// Determines distance from explosion, applies shrap hits and BC damage 
	// Increaes total number of shrap and bc hit count for conflict log 
	public void explodeTrooper(Trooper target, int rangePCHexes) {
		
		String bshc; 
		int bc; 
		
		if(weapon != null) {
			if(getExplsoionRangeColumn(rangePCHexes) >= weapon.bc.size()) {
				bshc = "0";
				bc = 0;
			} else {
				bshc = weapon.bshc.get(getExplsoionRangeColumn(rangePCHexes)); 
				bc = weapon.bc.get(getExplsoionRangeColumn(rangePCHexes));				
			}
		} else if(pcAmmo != null && pcAmmo.ordnance) {
			if(getOrdnanceRangeColumn(rangePCHexes) >= pcAmmo.bc.size()) {
				bshc = "0";
				bc = 0;
			} else {
				bshc = pcAmmo.bshc.get(getOrdnanceRangeColumn(rangePCHexes)); 
				bc = pcAmmo.bc.get(getOrdnanceRangeColumn(rangePCHexes));				
			}
		} else if(pcAmmo != null){
			if(getExplsoionRangeColumn(rangePCHexes) >= pcAmmo.bc.size()) {
				bshc = "0";
				bc = 0;
			} else {
				bshc = pcAmmo.bshc.get(getExplsoionRangeColumn(rangePCHexes)); 
				bc = pcAmmo.bc.get(getExplsoionRangeColumn(rangePCHexes));				
			}
		} else if(shell != null) {
			if(getExplsoionRangeColumn(rangePCHexes) >= shell.bc.size()) {
				bshc = "0";
				bc = 0;
			} else {
				bshc = shell.bshc.get(getExplsoionRangeColumn(rangePCHexes)); 
				bc = shell.bc.get(getExplsoionRangeColumn(rangePCHexes));				
			}
		} else {
			GameWindow.gameWindow.conflictLog.addNewLine("Explode Trooper failed. No valid weapon.");
			return;
		}
		
		int shrapHits = 0; 
		
		String bshcRslts = "";
		if(bshc.contains("*")) {
			String newBshc = bshc.replace("*", "");
			shrapHits = Integer.parseInt(newBshc);
		} else {
			int roll = DiceRoller.randInt(0, 99);
			if(roll <= Integer.parseInt(bshc)) {
				shrapHits++; 
			}
			
			bshcRslts = ", Roll: "+roll; 
			
		}
		
		if(target.inCover)
			bc /= 2;
		if(PCUtility.armorCoverage(target) && bc > target.armor.bPF && target.entirelyMechanical) 
			bc /= 4;
		else if(PCUtility.armorCoverage(target) && bc > target.armor.bPF) 
			bc /= 2;
		else if(PCUtility.armorCoverage(target) && bc <= target.armor.bPF) {
			bc /= 100;
		}
		
		//GameWindow.gameWindow.conflictLog.addNewLineToQueue(GameWindow.getLogHead(target)+", Distance PC Hexes: "+rangePCHexes+", Explosion BC Damage: "+bc+", BSHC: "+bshc+bshcRslts);
			
		int ionDmg = 0;
		if(weapon != null && weapon.ionWeapon) {
			ionDmg = weapon.ionDamage.get(getExplsoionRangeColumn(rangePCHexes));
			
		}  else if(pcAmmo != null && pcAmmo.ionDamage.size() > 0) {
			ionDmg = pcAmmo.ionDamage.get(getExplsoionRangeColumn(rangePCHexes));
		} else if(shell != null && shell.ionDamage.size() > 0) {
			ionDmg = shell.ionDamage.get(getExplsoionRangeColumn(rangePCHexes));
		}
		
		if(target.inCover)
			ionDmg /= 2;
		
		if(ionDmg > 0 && target.entirelyMechanical) {
			GameWindow.gameWindow.conflictLog.addToLineInQueue(", Ion Damage: "+ionDmg);
			target.physicalDamage += ionDmg; 
			totalIonDamage += ionDmg;
		}
		
		for(int i = 0; i < shrapHits; i++) {
			shrapHit(target, getExplsoionRangeColumn(rangePCHexes));
		}
		
		if(target.personalShield != null && bc > target.personalShield.currentShieldStrength) {
			bc -= target.personalShield.currentShieldStrength;
			target.personalShield.currentShieldStrength = 0; 
			totalBC += bc;
		} else if (target.personalShield != null && bc < target.personalShield.currentShieldStrength) {
			 target.personalShield.currentShieldStrength -= bc;
			bc = 0; 
		} else {
			// Applies blast damage
			target.physicalDamage += bc; 
			totalBC += bc; 		
			target.injured(GameWindow.gameWindow.conflictLog, new Injuries(bc, "Blast Damage", false, null), GameWindow.gameWindow.game, GameWindow.gameWindow);
		}
		
		totalShrapHits += shrapHits;
		
		String rslts = GameWindow.getLogHead(target)
				+", Distance PC Hexes: "+rangePCHexes+", Explosion BC Damage: "+bc+", BSHC: "
				+bshc+bshcRslts;
		
		if(totalIonDamage > 0)
			rslts += ", Ion Damage: "+totalIonDamage;
		
		if(target.personalShield != null) 
			rslts += ", Shield Strength: "+target.personalShield.currentShieldStrength;
		
		
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue(rslts);
		InjuryLog.InjuryLog.addTrooper(target);
		
		target.calculateInjury(GameWindow.gameWindow, GameWindow.gameWindow.conflictLog);
	}
	
	// Called when a trooper is hit by shrapnel 
	public void shrapHit(Trooper target, int explosionRangeColumn) {
		
		ResolveHits resolveHits = new ResolveHits(target, weapon);
		
		int of = 0; 
		
		if(target.inCover)
			of = 1; 
		int pen, dc; 
		
		if(pcAmmo != null || weapon != null) {
			pen = pcAmmo == null ? weapon.pen.get(explosionRangeColumn) : pcAmmo.pen.get(explosionRangeColumn);
			dc = pcAmmo == null ? weapon.dc.get(explosionRangeColumn) : pcAmmo.dc.get(explosionRangeColumn);			
		} else if(shell != null) {
			pen = shell.pen.get(explosionRangeColumn);
			dc = shell.dc.get(explosionRangeColumn);
		} else {
			GameWindow.gameWindow.conflictLog.addNewLine("");
			return; 
		}
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue(GameWindow.getLogHead(target)+", sharp hit, pen: "+pen+", dc: "+dc);
		
		Injuries injury = resolveHits.getPCHitsManual(pen, dc, of);
		
		if(injury != null) {
			target.injured(GameWindow.gameWindow.conflictLog, injury, GameWindow.gameWindow.game, GameWindow.gameWindow);
			InjuryLog.InjuryLog.addTrooper(target);
			target.calculateInjury(GameWindow.gameWindow, GameWindow.gameWindow.conflictLog);
		}
		
		// Padding
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("");
	}
	
	// Called when a trooper is hit by a projectile such as an rpg rocket
	public void explosiveImpact(Trooper target, PCAmmo pcAmmo, Weapons weapon) {
		
		ResolveHits resolveHits = new ResolveHits(target, weapon);
		int of = 0; 
		if(target.inCover)
			of = 1; 
		
		int pen = pcAmmo.impactPen;
		int dc = pcAmmo.impactDc;
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue(GameWindow.getLogHead(target)+", impact hit, pen: "+pen+", dc: "+dc);
		
		Injuries injury = resolveHits.getPCHitsManual(pen, dc, of);
		resolveHits.weapon = weapon; 
		resolveHits.weapon.energyWeapon = pcAmmo.energyWeapon;
		
		if(injury != null) {
			target.injured(GameWindow.gameWindow.conflictLog, injury, GameWindow.gameWindow.game, GameWindow.gameWindow);
			InjuryLog.InjuryLog.addTrooper(target);
			target.calculateInjury(GameWindow.gameWindow, GameWindow.gameWindow.conflictLog);
		}
		
		// Padding
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("");
	}
	
	
	// Explosion ranges 
	// 0 1 2 3 5 10 
	// Returns col for pc explosive weapon based on passed range in pc hexes 
	public static int getExplsoionRangeColumn(int rangePCHexes) {
		if(rangePCHexes <= 0)
			return 0;
		else if(rangePCHexes <= 1)
			return 1; 
		else if(rangePCHexes <= 2)
			return 2; 
		else if(rangePCHexes <= 3)
			return 3; 
		else if(rangePCHexes <= 5)
			return 4; 
		else 
			return 5; 
	}
	
	// Ordnance ranges 
	// 
	// 
	public int getOrdnanceRangeColumn(int rangePCHexes) {
		if(rangePCHexes <= 0)
			return 0;
		else if(rangePCHexes <= 1)
			return 1; 
		else if(rangePCHexes <= 2)
			return 2; 
		else if(rangePCHexes <= 3)
			return 3; 
		else if(rangePCHexes <= 4)
			return 4; 
		else if(rangePCHexes <= 5)
			return 5; 
		else if(rangePCHexes <= 6)
			return 6; 
		else if(rangePCHexes <= 8)
			return 7; 
		else if(rangePCHexes <= 10)
			return 8; 
		else 
			return 9; 
	}
	
}
