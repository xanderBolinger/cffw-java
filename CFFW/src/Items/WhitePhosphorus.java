package Items;

import java.util.ArrayList;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import UtilityClasses.DiceRoller;

public class WhitePhosphorus {

	public enum WPType {
		Mortar60mm,Mortar81mm,Mortar120mm,Shell155mm,WpGrenade
	}
	
	// C 1 2 3 4 5 6 7 8 9
	public WPType wpType;
	
	ArrayList<String> baseWPHitChance;
	ArrayList<Integer> fragmentPd;
	FlameThrower flameDamage;
	
	public WhitePhosphorus(WPType wpType) {
		baseWPHitChance = new ArrayList<String>();
		fragmentPd = new ArrayList<Integer>();
		this.wpType = wpType;
		
		switch(wpType) {
		
		case WpGrenade:
			break;
		
		case Mortar120mm:
			
			baseWPHitChance.add("*38");
			baseWPHitChance.add("*9");
			baseWPHitChance.add("*2");
			baseWPHitChance.add("59");
			baseWPHitChance.add("26");
			baseWPHitChance.add("15");
			baseWPHitChance.add("9");
			baseWPHitChance.add("7");
			baseWPHitChance.add("5");
			baseWPHitChance.add("4");
			
			fragmentPd.add(427);
			fragmentPd.add(424);
			fragmentPd.add(419);
			fragmentPd.add(408);
			fragmentPd.add(396);
			fragmentPd.add(384);
			fragmentPd.add(371);
			fragmentPd.add(357);
			fragmentPd.add(343);
			fragmentPd.add(328);
			
			flameDamage = new FlameThrower();
			flameDamage.contact = 235000;
			flameDamage.inHex = 10000;
			flameDamage.adjacent = 458;
			
			break;
		case Mortar60mm:
			break;
		case Mortar81mm:
			break;
		case Shell155mm:
			break;
		default:
			System.err.println("WP Type not found for type: "+wpType);
			break;
		
		}
		
	}
	
	public void deployHex(Cord cord) {
		
		GameWindow.gameWindow.conflictLog
			.addNewLine("WP Deployed Hex: "+cord+", Type: "+wpType);
		var units = GameWindow.gameWindow.getUnitsInHex("None",
				cord.xCord, cord.yCord);
		
		for(var unit : units) {
			
			for(var trooper : unit.individuals) {
				
				var roll = DiceRoller.roll(0, 9);
				var bshc = baseWPHitChance.get(roll);
				var hits = fragmentHits(bshc);
				var pd = fragmentPd.get(roll);

				GameWindow.gameWindow.conflictLog
				.addNewLine("WP Fragment Trooper: "+unit.callsign+" "
						+ trooper.number + " "+trooper.name+", Hits: "
						+hits+", BSHC: "+bshc+", PD: "+pd);
				
				for(int i = 0; i < hits; i++) {
					var dmg = FlameDamageCalculator
							.CalculateFlameDamage(pd, trooper);
					FlameDamageCalculator.ApplyFlameDamage(dmg, trooper);
				}
				
			}
			
			unit.suppression += unit.suppression + 5 < 100 ? 0 : 5;
			unit.organization -= unit.organization - 5 < 0 ? 0 : 5;
			
		}
		
		
		flameHex(cord);
	}
	
	private int fragmentHits(String bshc) {
		int shrapHits = 0; 
		
		if(bshc.contains("*")) {
			String newBshc = bshc.replace("*", "");
			shrapHits = Integer.parseInt(newBshc);
		} else {
			int roll = DiceRoller.roll(0, 99);
			if(roll <= Integer.parseInt(bshc)) {
				shrapHits++; 
			}
		}
		
		return shrapHits;
	}
	
	public void flameHex(Cord cord) {
		
		GameWindow.gameWindow.conflictLog
			.addNewLine("WP Flame Hex: "+cord+", Type: "+wpType);
		
		FlameDamageCalculator.FlameHex(cord.xCord, cord.yCord, 
				flameDamage, 1);
		
	}
	
	
}
