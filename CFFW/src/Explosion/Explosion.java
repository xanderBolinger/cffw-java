package Explosion;

import java.util.ArrayList;

import Artillery.Shell;
import Conflict.GameWindow;
import Conflict.InjuryLog;
import Conflict.SmokeStats;
import Conflict.SmokeStats.SmokeType;
import CorditeExpansion.Cord;
import Injuries.Injuries;
import Injuries.ResolveHits;
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
		
		if(GameWindow.gameWindow != null && GameWindow.gameWindow.game.shieldManager.shieldedHex(x, y)) {
			return;
		}
		
		if((pcAmmo != null && pcAmmo.smoke == true) || 
				(weapon != null && weapon.type.equals("Grenade") &&  weapon.pcAmmoTypes != null && weapon.pcAmmoTypes.size() > 0 &&
				weapon.pcAmmoTypes.get(0).smoke) ||
				(shell != null && shell.smoke)) {
			System.out.println("Explode Hex Smoke");
			
			SmokeType smokeType = weapon != null ? weapon.pcAmmoTypes.get(0).smokeType : (pcAmmo != null ? pcAmmo.smokeType : shell.smokeType);
			
			GameWindow.gameWindow.game.smoke.deploySmoke(new Cord(x,y), new SmokeStats(smokeType));
			return;
		}
		
		ArrayList<Unit> targetUnits = GameWindow.gameWindow.getUnitsInHexExcludingSide(friendlySide, x, y);
		
		
		for(Unit unit : targetUnits) {
			
			for(Trooper trooper : unit.individuals) {
				
				if(!excludeTroopers.contains(trooper)) {
					int roll1 = DiceRoller.roll(0, 10);
					//int roll2 = DiceRoller.roll(0, 10);
					explodeTrooper(trooper, /*(roll1 < roll2 ? roll2 : roll1)*/roll1);
				}
						
			}
			
			if((pcAmmo != null && pcAmmo.clusterMunition)
					|| (shell != null && shell.pcAmmo != null && shell.pcAmmo.clusterMunition))
			
			unit.suppression += 10; 
			unit.organization -= 10; 
			
			if(unit.suppression > 100)
				unit.suppression = 100; 
			
			if(unit.organization < 0)
				unit.organization = 0; 
			
		}
	
		if(pcAmmo != null)
			ExplodeDistantHexes.explodeDistantHexes(pcAmmo, x, y, this);
		else if(shell != null && shell.pcAmmo != null)
			ExplodeDistantHexes.explodeDistantHexes(shell.pcAmmo, x, y, this);
	}
	// Called on each trooper in a hex that contains an explosion 
	// Called from explode hex 
	// Determines distance from explosion, applies shrap hits and BC damage 
	// Increaes total number of shrap and bc hit count for conflict log 
	public void explodeTrooper(Trooper target, int rangePCHexes) {
		System.out.println("Explode Trooper: "+target.name+", Range: "+rangePCHexes);
		String bshc; 
		int bc; 
		
		if(weapon != null) {
			var pcAmmo = weapon.pcAmmoTypes.size() > 0 ? weapon.pcAmmoTypes.get(0) : null;
			var rangeCol = getExplsoionRangeColumn(rangePCHexes);
			if(rangeCol >= weapon.bc.size() && (pcAmmo == null || pcAmmo.rangeList.length == 0)) {
				bshc = "0";
				bc = 0;
				System.out.println("Get weapons zero, bshc: "+bshc+", bc: "+bc
						+", range col: "+rangeCol);
			} else if(rangeCol >= weapon.bc.size() && (pcAmmo != null && pcAmmo.rangeList.length == 0)) {
				var data = pcAmmo.getExplosiveData(rangePCHexes);
				bshc = data.bshc;
				bc = data.bc;
				System.out.println("Get weapons data, bshc: "+bshc+", bc: "+bc
						+", range col: "+rangeCol);
			} else {
				bshc = weapon.bshc.get(rangeCol); 
				bc = weapon.bc.get(rangeCol);	
				System.out.println("Get weapons range col, bshc: "+bshc+", bc: "+bc
						+", range col: "+rangeCol);
			}
		} else if(pcAmmo != null && pcAmmo.ordnance) {
			var rangeCol = getOrdnanceRangeColumn(rangePCHexes);
			if(rangeCol >= pcAmmo.bc.size()
					&& pcAmmo.rangeList.length == 0) {
				bshc = "0";
				bc = 0;
				System.out.println("Get ordnance zero, range col: "+rangeCol);
			} else if(rangeCol >= pcAmmo.bc.size()) {
				var data = pcAmmo.getExplosiveData(rangePCHexes);
				bshc = data.bshc;
				bc = data.bc;
				System.out.println("Get ordnance data, bshc: "+bshc+", bc: "+bc
						+", range col: "+rangeCol);
			} else {
				bshc = pcAmmo.bshc.get(rangeCol); 
				bc = pcAmmo.bc.get(rangeCol);	
				System.out.println("Get ordnance range column, bshc: "+bshc+", bc: "+bc
						+", range col: "+rangeCol);
			}
		} else if(pcAmmo != null){
			if(getExplsoionRangeColumn(rangePCHexes) >= pcAmmo.bc.size()
					&& pcAmmo.rangeList.length == 0) {
				bshc = "0";
				bc = 0;
			} else if(getExplsoionRangeColumn(rangePCHexes) >= pcAmmo.bc.size()) {
				var data = pcAmmo.getExplosiveData(rangePCHexes);
				bshc = data.bshc;
				bc = data.bc;
			} else {
				bshc = pcAmmo.bshc.get(getExplsoionRangeColumn(rangePCHexes)); 
				bc = pcAmmo.bc.get(getExplsoionRangeColumn(rangePCHexes));				
			}
		} else if(shell != null) {
			var rangeCol = getOrdnanceRangeColumn(rangePCHexes);
			if(rangeCol >= shell.bc.size() && shell.pcAmmo == null) {
				bshc = "0";
				bc = 0;
			} 
			else if(rangeCol >= shell.bc.size()) {
				var data = shell.pcAmmo.getExplosiveData(rangePCHexes);
				bshc = data.bshc;
				bc = data.bc;
			}
			else {
				bshc = shell.bshc.get(rangeCol); 
				bc = shell.bc.get(rangeCol);				
			}
		} else {
			System.err.println("Explode Trooper failed. No valid weapon.");
			return;
		}
		
		int shrapHits = 0; 
		
		String bshcRslts = "";
		if(bshc.contains("*")) {
			String newBshc = bshc.replace("*", "");
			shrapHits = Integer.parseInt(newBshc);
		} else {
			int roll = DiceRoller.roll(0, 99);
			if(roll <= Integer.parseInt(bshc)) {
				shrapHits++; 
			}
			
			bshcRslts = ", Roll: "+roll; 
			
		}
		
		bc = blastConcussionModifiers(bc, target, rangePCHexes);
		//GameWindow.gameWindow.conflictLog.addNewLineToQueue(GameWindow.getLogHead(target)+", Distance PC Hexes: "+rangePCHexes+", Explosion BC Damage: "+bc+", BSHC: "+bshc+bshcRslts);
			
		int ionDmg = 0;
		var rangeCol = getExplsoionRangeColumn(rangePCHexes);
		
		if(weapon != null) {
			if(weapon.ionWeapon && rangeCol < weapon.ionDamage.size())
				ionDmg = weapon.ionDamage.get(rangeCol);
			else if(weapon.ionWeapon && weapon.pcAmmoTypes.size() > 0) {
				var data = weapon.pcAmmoTypes.get(0).getExplosiveData(rangePCHexes);
				ionDmg = data.ion;
			} 
		}  else if(pcAmmo != null && pcAmmo.ionDamage.size() > 0 && rangePCHexes <= 
		(pcAmmo.ordnance ? 10 : 8)) {
			ionDmg = pcAmmo.ionDamage.get(getExplsoionRangeColumn(rangePCHexes));
		} else if(pcAmmo != null && pcAmmo.ionDamage.size() > 0) {
			ionDmg = pcAmmo.getExplosiveData(rangePCHexes).ion;
		}
		else if(shell != null && shell.ionDamage.size() > 0
				&& getOrdnanceRangeColumn(rangePCHexes) < shell.ionDamage.size()) {
			ionDmg = shell.ionDamage.get(getOrdnanceRangeColumn(rangePCHexes));
		} else if(shell != null && shell.pcAmmo != null) {
			ionDmg = shell.pcAmmo.getExplosiveData(rangePCHexes).ion;
		}
		
		if(target.inCover)
			ionDmg /= 2;
		
		if(ionDmg > 0 && target.entirelyMechanical) {
			GameWindow.gameWindow.conflictLog.addToLineInQueue(", Ion Damage: "+ionDmg);
			target.physicalDamage += ionDmg; 
			totalIonDamage += ionDmg;
		}
		
		for(int i = 0; i < shrapHits; i++) {
			shrapHit(target, rangePCHexes);
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
	
	private int blastConcussionModifiers(int bc, Trooper target, int range) {
		
		if(PCUtility.armorCoverage(target) && bc > target.armor.bPF && target.entirelyMechanical) 
			bc /= 4;
		else if(PCUtility.armorCoverage(target) && bc > target.armor.bPF) 
			bc /= 2;
		else if(PCUtility.armorCoverage(target) && bc <= target.armor.bPF) {
			bc /= 100;
		}
		
		var unit = target.returnTrooperUnit(GameWindow.gameWindow);
		var fortificationLevel = GameWindow.gameWindow.game.fortifications.getTrenchesLevel(new Cord(unit.X,unit.Y));
		
		if((!target.HD && !target.inCover) || !target.inCover)
			return bc;
		else if(!target.HD && target.inCover && !target.inBuilding(GameWindow.gameWindow) && fortificationLevel == 0)
			return (int)((double)bc / 1.5);
		
		double modifiedBc = bc;
		
		// dug outs 
		if(fortificationLevel == 3 && target.HD) {
			return bc / 500;
		} else if(fortificationLevel == 4 && target.HD) {
			return bc / 1000;
		}
		
		// ground burst
		if((shell != null && !shell.airBurst) || (pcAmmo != null && !pcAmmo.airBurst)) {
			// shell could land in fox hole, PC rules say there is a 14 percent chance the shell lands inside the fox hole or trench
			if(range == 0)
				return bc;
			else if(!target.HD)
				return bc / 2;
			else 
				return (int)( modifiedBc * 0.01);
		} 
		
		// air burst 
		if((fortificationLevel == 1 || fortificationLevel == 2) && !target.inBuilding(GameWindow.gameWindow)) {
			
			if(range == 0) {
				modifiedBc *= 3;
			} else if(range == 1) {
				modifiedBc *= 3;
			} else if(range == 2) {
				modifiedBc *= 2.3;
			} else if(range == 3) {
				modifiedBc *= 0.7;
			} else if(range == 4) {
				modifiedBc *= 0.5;
			} else if(range == 5) {
				modifiedBc *= 0.4;
			} else if(range <= 7) {
				modifiedBc *= 0.3;
			} else if(range <= 13) {
				modifiedBc *= 0.2;
			} else if(range <= 25) {
				modifiedBc *= 0.1;
			} else if(range <= 60) {
				modifiedBc *= 0.05;
			} else if(range >= 61) {
				modifiedBc *= 0.01;
			}
			
		} else if(fortificationLevel >= 3 || target.inBuilding(GameWindow.gameWindow)) {
			
			if(range == 0) {
				modifiedBc *= 3;
			} else if(range == 1) {
				modifiedBc *= 2.3;
			} else if(range == 2) {
				modifiedBc *= 0.8;
			} else if(range == 3) {
				modifiedBc *= 0.4;
			} else if(range == 4) {
				modifiedBc *= 0.3;
			} else if(range == 5) {
				modifiedBc *= 0.2;
			} else if(range <= 7) {
				modifiedBc *= 0.1;
			} else if(range <= 13) {
				modifiedBc *= 0.1;
			} else if(range <= 25) {
				modifiedBc *= 0.05;
			} else if(range <= 60) {
				modifiedBc *= 0.05;
			} else if(range >= 61) {
				modifiedBc *= 0.01;
			}
			
		}
		
		return (int)modifiedBc;
	}

	// Called when a trooper is hit by shrapnel 
	public void shrapHit(Trooper target, int rangePCHexes) {
		
		ResolveHits resolveHits = new ResolveHits(target, weapon);
		
		var explosionRangeColumn = getExplsoionRangeColumn(rangePCHexes);
		
		int of = 0; 
		
		if(target.inCover)
			of = 1; 
		int pen, dc; 
		
		if(weapon != null
				|| (pcAmmo != null && rangePCHexes <= (pcAmmo.ordnance ? 10 : 8))) {
			pen = pcAmmo == null ? weapon.pen.get(explosionRangeColumn) : pcAmmo.pen.get(explosionRangeColumn);
			dc = pcAmmo == null ? weapon.dc.get(explosionRangeColumn) : pcAmmo.dc.get(explosionRangeColumn);			
		
		} 
		else if(pcAmmo != null) {
			var data = pcAmmo.getExplosiveData(rangePCHexes);
			pen = data.pen;
			dc = data.dc;
		}
		else if(shell != null) {
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
		else if(rangePCHexes <= 8)
			return 5; 
		else 
			return 6;
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
