package Trooper.Factions;

import java.util.ArrayList;

import javax.swing.JComboBox;

import Company.Formation.LeaderType;
import Items.Item.ItemType;
import Trooper.IndividualStats;
import Trooper.Skills;
import Trooper.TLHStats;
import Trooper.Trooper;
import Trooper.Factions.FactionManager.FactionType;

public class UnitedNationsSpaceCommand extends Faction {

	public UnitedNationsSpaceCommand() {
		super(FactionType.UnitedNationsSpaceCommand, "United Nations Space Command");
	}

	@Override
	public void squadInput(JComboBox combo) {
		// TODO Auto-generated method stub
		combo.addItem("Rifle Team");
	}

	@Override
	public void individualInput(JComboBox combo) {
		// TODO Auto-generated method stub
		combo.addItem("Team Leader");
		combo.addItem("Rifleman");
		combo.addItem("Autorifleman");
		combo.addItem("Marksman");
	}

	@Override
	public void setTrooper(Trooper trooper, String trooperType) throws Exception {
		trooper.conscious = true;
		trooper.alive = true;
		trooper.arms = 2;
		trooper.legs = 2;
		trooper.disabledArms = 0;
		trooper.disabledLegs = 0;
		trooper.CloseCombat = false;
		trooper.armor.unscMarine();
		trooper.criticalTime = 0;
		trooper.nightVision = true;
		trooper.nightVisionEffectiveness = 3;
		
		TLHStats attributes = new TLHStats(1, 1, 0, 1, 0, 0, 1);
		trooper.str = attributes.str;
		trooper.wit = attributes.wit;
		trooper.soc = attributes.soc;
		trooper.wil = attributes.wil;
		trooper.per = attributes.per;
		trooper.hlt = attributes.hlt;
		trooper.agi = attributes.agi;
		
		trooper.meleeCombatSkillLevel = 6;
		trooper.meleeWep = "Vibroknife";
		
		int attr[] = { trooper.str, trooper.wit, trooper.soc, trooper.wil, 
				trooper.per, 
				trooper.hlt, 
				trooper.agi };
		
		Skills skills = new Skills(trooper,attr);
		skills.line();
		
		trooper.skills = skills;
		
		if (trooperType.equals("Team Leader")) {
			TeamLeader(trooper);
		} else if (trooperType.equals("Rifleman")) {
			Rifleman(trooper);
		} else if (trooperType.equals("Autorifleman")) {
			Autorifleman(trooper);
		} else if (trooperType.equals("Marksman")) {
			Marksman(trooper);
		} else {
			throw new Exception("Invalid UNSC Input for input: "+trooperType);
		}

		trooper.encumberanceModifier -= 20;

		trooper.inventory.setEncumberance();
		
		trooper.skills.setSkills();
		
		if (trooper.encumberance < 0) {
			trooper.encumberance = 5;
		}

		if (trooper.hlt < 10) {
			trooper.hlt = 10;
		}

		if (trooper.agi < 10)
			trooper.agi = 10;
		
		IndividualStats individual = new IndividualStats(trooper.combatActions, trooper.sal, trooper.skills.getSkill("Pistol").value,
				trooper.skills.getSkill("Rifle").value, trooper.skills.getSkill("Launcher").value, trooper.skills.getSkill("Heavy").value,
				trooper.skills.getSkill("Subgun").value, false);
		trooper.name = individual.name;
		trooper.P1 = individual.P1;
		trooper.P2 = individual.P2;
		trooper.identifier = trooper.identifier();
	}

	private void TeamLeader(Trooper trooper) throws Exception {
		trooper.rank = "Corporal";
		trooper.designation = "Team Leader";
		trooper.vet = "";
		trooper.wep = "MA37";
		trooper.meleeWep = "Vibroknife";
		trooper.ammo = 192;
		trooper.inventory.addItems(ItemType.MA37, 1);
		trooper.inventory.addItems(ItemType.MA37, ItemType.SmallArmsAmmo, 6);
		trooper.inventory.addItems(ItemType.M9Frag, 1);
		trooper.skills.lineSquadLeader();
	}
	
	private void Rifleman(Trooper trooper) throws Exception {
		trooper.rank = "Private";
		trooper.designation = "Rifleman";
		trooper.vet = "";
		trooper.wep = "MA37";
		trooper.meleeWep = "Vibroknife";
		trooper.ammo = 192;
		trooper.inventory.addItems(ItemType.MA37, 1);
		trooper.inventory.addItems(ItemType.MA37, ItemType.SmallArmsAmmo, 6);
		trooper.inventory.addItems(ItemType.M9Frag, 1);
		trooper.skills.line();
	}
	
	private void Marksman(Trooper trooper) throws Exception {
		trooper.rank = "Lance Corporal";
		trooper.designation = "Marksman";
		trooper.vet = "";
		trooper.wep = "M392 DMR";
		trooper.meleeWep = "Vibroknife";
		trooper.ammo = 120;
		trooper.inventory.addItems(ItemType.M392, 1);
		trooper.inventory.addItems(ItemType.M392, ItemType.SmallArmsAmmo, 8);
		trooper.inventory.addItems(ItemType.M9Frag, 1);
		trooper.skills.lineMarksman();
	}
	
	private void Autorifleman(Trooper trooper) throws Exception {
		trooper.rank = "Private";
		trooper.designation = "Autorifleman";
		trooper.vet = "";
		trooper.wep = "M739 SAW";
		trooper.meleeWep = "Vibroknife";
		trooper.ammo = 225;
		trooper.inventory.addItems(ItemType.M739, 1);
		trooper.inventory.addItems(ItemType.M739, ItemType.SmallArmsAmmo, 3);
		trooper.inventory.addItems(ItemType.M9Frag, 1);
		trooper.skills.lineAutorifleman();
	}
	
	@Override
	public void createSquad(String squad, ArrayList<Trooper> individuals) throws Exception {
		if(squad.equals("Rifle Team")) {
			var t = new Trooper("Team Leader", factionName);
			t.leaderType = LeaderType.SL;
			individuals.add(t);
			individuals.add(new Trooper("Rifleman", factionName));
			individuals.add(new Trooper("Autorifleman", factionName));
			individuals.add(new Trooper("Marksman", factionName));
		}
	}

}
