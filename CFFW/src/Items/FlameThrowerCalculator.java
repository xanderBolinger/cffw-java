package Items;

import Conflict.GameWindow;
import Hexes.Hex;
import Injuries.Injuries;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;

public class FlameThrowerCalculator {
	
	public static void FlameHex(int x, int y, FlameThrower flamer, int charges) {
		var units = GameWindow.gameWindow.getUnitsInHex("None", x, y);
		
		for(var unit : units) {
			for(var trooper : unit.individuals) {
				var dist = DiceRoller.roll(0, 10);
				int damage = 0;
				if(dist == 0) 
					damage = CalculateFlameDamage(flamer.contact*charges, trooper);
				else if(dist == 1)
					damage = CalculateFlameDamage(flamer.inHex*charges, trooper);
				else if(dist == 2)
					damage = CalculateFlameDamage(flamer.adjacent*charges, trooper);
				
				if(damage > 0)
					ApplyFlameDamage(damage, trooper);
				
				GameWindow.gameWindow.conflictLog.addNewLineToQueue("Flamer: "+flamer.flamerType+", Charges: "+charges+", "
						+ (damage > 0 ? "hit "+trooper.number+" "+trooper.name+" for "+damage+" damage. dist: "+dist
								: "missed "+trooper.number+" "+trooper.name+", dist: "+dist));
				
			}
		}
		
	}
	
	private static int CalculateFlameDamage(int baseDamage, Trooper target) {
		
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
		
		if(percentUncovered == 0)
			baseDamage /= 4;
		else if(percentUncovered >= 90)
			baseDamage /= 3; 
		else if(percentUncovered >= 75)
			baseDamage /= 2;
		
		if(target.entirelyMechanical)
			baseDamage /= 10;
		
		return baseDamage; 
		
	}
	
	private static void ApplyFlameDamage(int damage, Trooper target) {
		
		target.injured(GameWindow.gameWindow.conflictLog, new Injuries(damage, "Flame Damage", false, null), 
				GameWindow.gameWindow.game, GameWindow.gameWindow);
		
	}
	
}	
