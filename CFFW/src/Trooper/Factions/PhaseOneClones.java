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

public class PhaseOneClones extends Faction {

	public PhaseOneClones() {
		super(FactionType.PhaseOneClones, "Phase One Clones");
	}
	
	public void squadInput(JComboBox dropDown) {
		/*dropDown.addItem("Command Team");
		dropDown.addItem("Rifle Team");
		dropDown.addItem("Heavy Rifle Team");
		dropDown.addItem("Launcher Rifle Team");
		dropDown.addItem("Mixed Rifle Team");*/
	}

	public void individualInput(JComboBox comboBox) {
		comboBox.removeAllItems();
		comboBox.addItem("Empty");
		comboBox.addItem("Clone Crewman");
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
		
		TLHStats attributes = new TLHStats(2, 2, -2, 2, 1, 1, 2);
		trooper.str = attributes.str;
		trooper.wit = attributes.wit;
		trooper.soc = attributes.soc;
		trooper.wil = attributes.wil;
		trooper.per = attributes.per;
		trooper.hlt = attributes.hlt;
		trooper.agi = attributes.agi;
		
		trooper.meleeCombatSkillLevel = 8;
		trooper.meleeWep = "Vibroknife";
		
		int attr[] = { trooper.str, trooper.wit, trooper.soc, trooper.wil, 
				trooper.per, 
				trooper.hlt, 
				trooper.agi };
		
		Skills skills = new Skills(trooper,attr);
		skills.line();
		
		trooper.skills = skills;
		
		if (input.equals("Clone Crewman")) {
			CloneCrewman(trooper);
		} else {
			throw new Exception("Invalid Astartes Input for input: "+input);
		}

		trooper.encumberanceModifier -= 20;

		trooper.inventory.setEncumberance();
		
		trooper.skills.setSkills();
		
		if (trooper.encumberance < 0) {
			trooper.encumberance = 5;
		}

		// According to the Jango Fett clone template special rule, sets the minimum
		// health to ten
		if (trooper.hlt < 12) {
			trooper.hlt = 12;
		}

		if (trooper.agi < 12)
			trooper.agi = 12;

		if (trooper.wit < 11)
			trooper.wit = 11;
		
		IndividualStats individual = new IndividualStats(trooper.combatActions, trooper.sal, trooper.skills.getSkill("Pistol").value,
				trooper.skills.getSkill("Rifle").value, trooper.skills.getSkill("Launcher").value, trooper.skills.getSkill("Heavy").value,
				trooper.skills.getSkill("Subgun").value, false);
		trooper.name = individual.name;
		trooper.P1 = individual.P1;
		trooper.P2 = individual.P2;
		trooper.identifier = trooper.identifier();
	}
	
	private void CloneCrewman(Trooper trooper) throws Exception {
		trooper.rank = "Crewman";
		trooper.designation = "Crewman";
		trooper.vet = "";
		trooper.wep = "DC15S";
		trooper.meleeWep = "Vibroknife";
		trooper.ammo = 100;
		trooper.inventory.addItems(ItemType.DC15S, 1);
		trooper.inventory.addItems(ItemType.DC15S, ItemType.SmallArmsAmmo, 2);
		trooper.skills.cloneCrewman();;
	}

	public void createSquad(String squad, ArrayList<Trooper> individuals) throws Exception {

		/*if(squad.equals("TX130 Crew")) {
			var t = new Trooper("Astartes Sergeant", factionName);
			t.leaderType = LeaderType.SL;
			individuals.add(t);
			individuals.add(new Trooper("Astartes Standard", factionName));
			individuals.add(new Trooper("Astartes", factionName));
			individuals.add(new Trooper("Astartes", factionName));
		} */
		
	}
	
}
