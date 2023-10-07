package Trooper.Factions;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComboBox;

import Company.Formation.LeaderType;
import Items.Item.ItemType;
import Trooper.IndividualStats;
import Trooper.Skills;
import Trooper.TLHStats;
import Trooper.Trooper;
import Trooper.Factions.FactionManager.FactionType;

public class Astartes extends Faction {

	public Astartes() {
		super(FactionType.Astartes, "Astartes");
		
	}
	
	public void squadInput(JComboBox dropDown) {
		
		dropDown.addItem("Rifle Team");
		
	}

	public void individualInput(JComboBox comboBox) {
		comboBox.removeAllItems();
		comboBox.addItem("Empty");

		comboBox.addItem("Astartes");
	}
	
	public void setTrooper(Trooper trooper, String input) throws Exception {
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
		
		
		TLHStats attributes = new TLHStats(11, 8, 0, 8, 8, 11, 8);
		trooper.str = attributes.str;
		trooper.wit = attributes.wit;
		trooper.soc = attributes.soc;
		trooper.wil = attributes.wil;
		trooper.per = attributes.per;
		trooper.hlt = attributes.hlt;
		trooper.agi = attributes.agi;
		
		
		if (input.equals("Astartes")) { // Squad Leader
			// Creates attributes

			trooper.rank = "Marine";
			trooper.designation = "Astartes";
			trooper.wep = "Bolter";
			trooper.ammo = 120;
			trooper.inventory.addItems(ItemType.Bolter, 1);
			trooper.inventory.addItems(ItemType.Bolter, ItemType.SmallArmsAmmo, 5);
			trooper.inventory.addItems(ItemType.AstartesFragGrenade, 2);
			trooper.inventory.addItems(ItemType.KrakGrenade, 2);
			trooper.inventory.addItems(ItemType.Nacht5SmokeGrenade, 1);

		} else {
			throw new Exception("Invalid Astartes Input for input: "+input);
		}

		// Pack mule
		trooper.encumberanceModifier -= 20;

		trooper.inventory.setEncumberance();

		trooper.meleeCombatSkillLevel = 8;
		trooper.meleeWep = "Vibroknife";
		
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
		trooper.skills.setSkills();
		
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
		int attr[] = { trooper.str, trooper.wit, trooper.soc, trooper.wil, 
				trooper.per, 
				trooper.hlt, 
				trooper.agi };
		
		Skills skills = new Skills(trooper,attr);
		
		skills.superSoldier();
		
		return skills;
	}
	
	private static String generateAstartesName() {
		String[] array = { "Ajax" };
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	public void createSquad(String squad, ArrayList<Trooper> individuals) throws Exception {

		if(squad.equals("Rifle Team")) {
			var t = new Trooper("Astartes", factionName);
			t.leaderType = LeaderType.SL;
			
			individuals.add(t);
			individuals.add(new Trooper("Astartes", factionName));
			individuals.add(new Trooper("Astartes", factionName));
			individuals.add(new Trooper("Astartes", factionName));
		}
		
	}
	
	
}
