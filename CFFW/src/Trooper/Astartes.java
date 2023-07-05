package Trooper;

import java.util.Random;

import Items.Item.ItemType;

public class Astartes {

	public void input(Trooper trooper, String input) throws Exception {
		trooper.conscious = true;
		trooper.alive = true;
		trooper.arms = 2;
		trooper.legs = 2;
		trooper.disabledArms = 0;
		trooper.disabledLegs = 0;
		trooper.CloseCombat = false;
		trooper.armor.Phase1CloneArmor();
		trooper.criticalTime = 0;
		trooper.nightVision = true;
		trooper.nightVisionEffectiveness = 3;

		TLHStats attributes = new TLHStats(4, 4, 0, 4, 4, 4, 4);
		trooper.str = attributes.str;
		trooper.wit = attributes.wit;
		trooper.soc = attributes.soc;
		trooper.wil = attributes.wil;
		trooper.per = attributes.per;
		trooper.hlt = attributes.hlt;
		trooper.agi = attributes.agi;

		if (input.equals("Astartes")) { // Squad Leader
			// Creates attributes

			trooper.rank = "Sergeant";
			trooper.designation = "Squad Leader";
			trooper.wep = "DC15A";

			trooper.inventory.addItems(ItemType.DC15A, 1);
			trooper.inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			trooper.inventory.addItems(ItemType.ClassAThermalDetonator, 1);
			trooper.inventory.addItems(ItemType.Nacht5SmokeGrenade, 2);

		}

		// Pack mule
		trooper.encumberanceModifier -= 20;

		trooper.inventory.setEncumberance();

		if (trooper.encumberance < 0) {
			trooper.encumberance = 5;
		}

		// According to the Jango Fett clone template special rule, sets the minimum
		// health to ten
		if (trooper.hlt < 18) {
			trooper.hlt = 18;
		}

		if (trooper.agi < 18)
			trooper.agi = 18;

		if (trooper.wit < 15)
			trooper.wit = 15;

		// Creates skills
		// Stores attributes in an array for the skill attr parameter
		trooper.skills = getAstartesSkills(trooper);
		
		// Create and set individual stats
		IndividualStats individual = new IndividualStats(trooper.combatActions, trooper.sal, trooper.skills.getSkill("Pistol").value,
				trooper.skills.getSkill("Rifle").value, trooper.skills.getSkill("Launcher").value, trooper.skills.getSkill("Heavy").value,
				trooper.skills.getSkill("Subgun").value, true);

		
		
		trooper.name = generateAstartesName();
		trooper.P1 = individual.P1;
		trooper.P2 = individual.P2;
		// Sets identifier
		trooper.identifier = trooper.identifier();


	}

	public Skills getAstartesSkills(Trooper trooper) {
		Skills skills = new Skills(trooper);
		
		skills.superSoldier();
		
		return skills;
	}
	
	public static String generateAstartesName() {
		String[] array = { "Ajax" };
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	
	
	
}