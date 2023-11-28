package Items;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import Hexes.Hex;
import Injuries.Injuries;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class FlameDamageCalculator {
	
	public static void FlameHex(int x, int y, FlameThrower flamer, int charges) {
		var units = GameWindow.gameWindow.getUnitsInHex("None", x, y);
		
		for(var unit : units) {
			flameUnit(flamer, charges,unit);
		}
		
	}
	
	public static void burnUnitOnEnterHex(Unit unit, Hex enteredHex) {
		
		if(enteredHex == null) {
			System.err.println("Entered hex is null for burn unit on enter hex");
			return;
		}
		
		var smokeStats = GameWindow.gameWindow.game
				.smoke.getSmokeAtHex(new Cord(enteredHex.xCord, enteredHex.yCord));
		
		for(var stat : smokeStats)
			if(stat.wp != null)
				FlameDamageCalculator.flameUnit(stat.wp.flameDamage, 1, unit);
		
	}
	
	public static void flameUnit(FlameThrower flamer, int charges, Unit unit) {
		for(var trooper : unit.individuals) {
			var dist = DiceRoller.roll(0, 10);
			int damage = 0;
			if(dist == 0) 
				damage = CalculateFlameDamage(flamer.contact*charges, trooper, flamer.ionCloud);
			else if(dist == 1)
				damage = CalculateFlameDamage(flamer.inHex*charges, trooper, flamer.ionCloud);
			else if(dist == 2)
				damage = CalculateFlameDamage(flamer.adjacent*charges, trooper, flamer.ionCloud);
			
			
			
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Flamer: "+flamer.flamerType+", Charges: "+charges+", "
					+ (damage > 0 ? "hit "+trooper.number+" "+trooper.name+" for "+damage+" damage. dist: "+dist
							: "missed "+trooper.number+" "+trooper.name+", dist: "+dist)+", ION: "+flamer.ionCloud);
			if(damage > 0)
				ApplyFlameDamage(damage, trooper);
		}
		
		unit.suppression += unit.suppression + 5 < 100 ? 0 : 5;
		unit.organization -= unit.organization - 5 < 0 ? 0 : 5;
	}
	
	public static int CalculateFlameDamage(int baseDamage, Trooper target, boolean ion) {
		if(target.entirelyMechanical && ion)
			return baseDamage;
		else if(ion)
			return 0;
		
		var armor = target.armor;
		
		var percentUncovered = armor.percentUncovered();
		
		if(target.personalShield != null) {
			int tempDamage = baseDamage;
			baseDamage -= target.personalShield.currentShieldStrength * 200;
			target.personalShield.currentShieldStrength -= tempDamage / 200;
		}
		
		if(armor == null) {
			return baseDamage;
		}
		
		if(percentUncovered > 80 && armor.shield) {
			int tempDamage = baseDamage;
			baseDamage -= armor.currentShieldValue * 200;
			armor.currentShieldValue -= tempDamage / 200;
		} 
		
		if(target.entirelyMechanical || armor.vaccumSealed)
			baseDamage /= 25;
		else if(percentUncovered == 0)
			baseDamage /= 4;
		else if(percentUncovered >= 90)
			baseDamage /= 3; 
		else if(percentUncovered >= 75)
			baseDamage /= 2;
		
		return baseDamage; 
		
	}
	
	public static void ApplyFlameDamage(int damage, Trooper target) {
		if(damage <= 0)
			return;
		
		target.injured(GameWindow.gameWindow.conflictLog, new Injuries(damage, "Flame Damage", false, null), 
				GameWindow.gameWindow.game, GameWindow.gameWindow);
		
	}
	
}	
