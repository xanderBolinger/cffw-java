package Items;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import Items.FlameThrower.FlameThrowerType;
import UtilityClasses.DiceRoller;

public class WhitePhosphorus implements Serializable {

	public enum WPType implements Serializable {
		Mortar60mm,Mortar81mm,Mortar120mm,Shell155mm,WpGrenade,
		Mortar60mmIon,Mortar81mmIon,Mortar120mmIon,Shell155mmIon,WpGrenadeIon
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
		case WpGrenadeIon:
			processWpGrenade();
			flameDamage.ionCloud = true;
			break;
		case Mortar120mmIon:
			processMortar120mm();
			flameDamage.ionCloud = true;
			break;
		case Mortar60mmIon:
			processMortar60mm();
			flameDamage.ionCloud = true;
			break;
		case Mortar81mmIon:
			processMortar81mm();
			flameDamage.ionCloud = true;
			break;
		case Shell155mmIon:
			processShell155mm();
			flameDamage.ionCloud = true;
			break;
		case WpGrenade:
			processWpGrenade();
			flameDamage.defoliateChance = 25;
			break;
		case Mortar120mm:
			processMortar120mm();
			flameDamage.defoliateChance = 45;
			break;
		case Mortar60mm:
			processMortar60mm();
			flameDamage.defoliateChance = 25;
			break;
		case Mortar81mm:
			processMortar81mm();
			flameDamage.defoliateChance = 35;
			break;
		case Shell155mm:
			processShell155mm();
			flameDamage.defoliateChance = 50;
			break;
		default:
			System.err.println("WP Type not found for type: "+wpType);
			break;
		
		}
		
	}
	
	private void processWpGrenade() {
	    baseWPHitChance.add("*30");
	    baseWPHitChance.add("*4");
	    baseWPHitChance.add("*4");
	    baseWPHitChance.add("24");
	    baseWPHitChance.add("10");
	    baseWPHitChance.add("5");
	    baseWPHitChance.add("3");
	    baseWPHitChance.add("2");
	    baseWPHitChance.add("1");
	    baseWPHitChance.add("0");
	    
	    fragmentPd.add(48);
	    fragmentPd.add(43);
	    fragmentPd.add(40);
	    fragmentPd.add(35);
	    fragmentPd.add(32);
	    fragmentPd.add(29);
	    fragmentPd.add(27);
	    fragmentPd.add(25);
	    fragmentPd.add(22);
	    fragmentPd.add(20);
	    
	    flameDamage = new FlameThrower(FlameThrowerType.WP);
	    flameDamage.contact = 10000;
	    flameDamage.inHex = 450;
	    flameDamage.adjacent = 20;
	}

	private void processMortar120mm() {
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
		
		flameDamage = new FlameThrower(FlameThrowerType.WP);
		flameDamage.contact = 235000;
		flameDamage.inHex = 10000;
		flameDamage.adjacent = 458;
	}

	private void processMortar60mm() {
		baseWPHitChance.add("*23");
		baseWPHitChance.add("*6");
		baseWPHitChance.add("*1");
		baseWPHitChance.add("35");
		baseWPHitChance.add("16");
		baseWPHitChance.add("9");
		baseWPHitChance.add("6");
		baseWPHitChance.add("0");
		baseWPHitChance.add("0");
		baseWPHitChance.add("0");
		
		fragmentPd.add(13);
		fragmentPd.add(13);
		fragmentPd.add(12);
		fragmentPd.add(11);
		fragmentPd.add(9);
		fragmentPd.add(6);
		fragmentPd.add(0);
		fragmentPd.add(0);
		fragmentPd.add(0);
		fragmentPd.add(0);
		
		flameDamage = new FlameThrower(FlameThrowerType.WP);
		flameDamage.contact = 6900;
		flameDamage.inHex = 304;
		flameDamage.adjacent = 13;
	}

	private void processMortar81mm() {
		baseWPHitChance.add("*24");
		baseWPHitChance.add("*6");
		baseWPHitChance.add("*1");
		baseWPHitChance.add("37");
		baseWPHitChance.add("17");
		baseWPHitChance.add("9");
		baseWPHitChance.add("6");
		baseWPHitChance.add("4");
		baseWPHitChance.add("3");
		baseWPHitChance.add("2");
		
		fragmentPd.add(75);
		fragmentPd.add(74);
		fragmentPd.add(73);
		fragmentPd.add(69);
		fragmentPd.add(65);
		fragmentPd.add(60);
		fragmentPd.add(55);
		fragmentPd.add(50);
		fragmentPd.add(45);
		fragmentPd.add(40);
		
		flameDamage = new FlameThrower(FlameThrowerType.WP);
		flameDamage.contact = 26000;
		flameDamage.inHex = 1200;
		flameDamage.adjacent = 51;
	}

	private void processShell155mm() {
		baseWPHitChance.add("*98");
		baseWPHitChance.add("*24");
		baseWPHitChance.add("*6");
		baseWPHitChance.add("*2");
		baseWPHitChance.add("68");
		baseWPHitChance.add("38");
		baseWPHitChance.add("24");
		baseWPHitChance.add("17");
		baseWPHitChance.add("12");
		baseWPHitChance.add("10");
		
		fragmentPd.add(1200);
		fragmentPd.add(1200);
		fragmentPd.add(1200);
		fragmentPd.add(1100);
		fragmentPd.add(1100);
		fragmentPd.add(1100);
		fragmentPd.add(1100);
		fragmentPd.add(1000);
		fragmentPd.add(1000);
		fragmentPd.add(900);
		
		flameDamage = new FlameThrower(FlameThrowerType.WP);
		flameDamage.contact = 2000000;
		flameDamage.inHex = 106000;
		flameDamage.adjacent = 4700;
	}
	
	public void deployHex(Cord cord) {
		
		GameWindow.gameWindow.conflictLog
			.addNewLineToQueue("WP Deployed Hex: "+cord+", Type: "+wpType);
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
						+hits+", BSHC: "+bshc+", PD: "+pd+", ION: "+flameDamage.ionCloud);
				
				for(int i = 0; i < hits; i++) {
					var dmg = FlameDamageCalculator
							.CalculateFlameDamage(pd, trooper, flameDamage.ionCloud);
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
			.addNewLineToQueue("WP Flame Hex: "+cord+", Type: "+wpType);
		
		FlameDamageCalculator.FlameHex(cord.xCord, cord.yCord, 
				flameDamage, 1);
		
		if(flameDamage.ionCloud)
			return;
		
		var roll = DiceRoller.roll(0, 99);
		if(roll <= flameDamage.defoliateChance) {
			var hex = GameWindow.gameWindow.findHex(cord.xCord, cord.yCord);			
			hex.concealment--;
			hex.flameMarkers.addMarker();
		}
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Defoliate, chance: "+flameDamage.defoliateChance
				+", roll: "+roll);
		
	}
	
	
}
