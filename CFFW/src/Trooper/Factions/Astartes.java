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
		dropDown.addItem("Command Team");
		dropDown.addItem("Rifle Team");
		dropDown.addItem("Heavy Rifle Team");
		dropDown.addItem("Launcher Rifle Team");
		dropDown.addItem("Mixed Rifle Team");
	}

	public void individualInput(JComboBox comboBox) {
		comboBox.removeAllItems();
		comboBox.addItem("Empty");
		comboBox.addItem("Astartes");
		comboBox.addItem("Astartes Sergeant");
		comboBox.addItem("Astartes Standard");
		comboBox.addItem("Astartes Heavy Bolter");
		comboBox.addItem("Astartes Missile Launcher");
	}
	
	public void setTrooper(Trooper trooper, String input) throws Exception {
		trooper.conscious = true;
		trooper.alive = true;
		trooper.arms = 2;
		trooper.legs = 2;
		trooper.disabledArms = 0;
		trooper.disabledLegs = 0;
		trooper.CloseCombat = false;
		trooper.armor.mkviiiErrant();
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
		
		trooper.meleeCombatSkillLevel = 8;
		trooper.meleeWep = "Vibroknife";
		
		if (input.equals("Astartes")) {
			Rifleman(trooper);
		} else if (input.equals("Astartes Heavy Bolter")) {
			HeavyBolter(trooper);
		} else if(input.equals("Astartes Missile Launcher")) {
			MissileLauncher(trooper);
		} else if(input.equals("Astartes Sergeant")) {
			Sergeant(trooper);
		} else if(input.equals("Astartes Standard")) {
			Standard(trooper);
		} else {
			throw new Exception("Invalid Astartes Input for input: "+input);
		}

		trooper.encumberanceModifier -= 200;

		trooper.inventory.setEncumberance();

		
		if (trooper.encumberance < 0) {
			trooper.encumberance = 5;
		}

		if (trooper.hlt < 18) {
			trooper.hlt = 18;
		}

		if (trooper.agi < 18)
			trooper.agi = 18;

		if (trooper.wit < 15)
			trooper.wit = 15;

		trooper.skills = getAstartesSkills(trooper);
		trooper.skills.setSkills();
		
		IndividualStats individual = new IndividualStats(trooper.combatActions, trooper.sal, trooper.skills.getSkill("Pistol").value,
				trooper.skills.getSkill("Rifle").value, trooper.skills.getSkill("Launcher").value, trooper.skills.getSkill("Heavy").value,
				trooper.skills.getSkill("Subgun").value, true);
		
		trooper.name = generateAstartesName();
		trooper.P1 = individual.P1;
		trooper.P2 = individual.P2;
		trooper.identifier = trooper.identifier();
	}
	
	public void Standard(Trooper trooper) throws Exception {
		trooper.rank = "Marine";
		trooper.designation = "Astartes Standard";
		trooper.wep = "MKVb Bolter";
		trooper.ammo = 120;
		trooper.inventory.addItems(ItemType.MkvbBolter, 1);
		trooper.inventory.addItems(ItemType.MkvbBolter, ItemType.SmallArmsAmmo, 5);
		trooper.inventory.addItems(ItemType.AstartesFragGrenade, 2);
		trooper.inventory.addItems(ItemType.KrakGrenade, 2);
		trooper.inventory.addItems(ItemType.Nacht5SmokeGrenade, 1);
		trooper.inventory.addItems(ItemType.AstartesStandard, 1);
	}
	
	public void Sergeant(Trooper trooper) throws Exception {
		trooper.rank = "Marine";
		trooper.designation = "Astartes Sergeant";
		trooper.wep = "MKIII Ultima Pattern";
		trooper.ammo = 120;
		trooper.inventory.addItems(ItemType.MKIIIUltimaPattern, 1);
		trooper.inventory.addItems(ItemType.MKIIIUltimaPattern, ItemType.SmallArmsAmmo, 5);
		trooper.inventory.addItems(ItemType.AstartesFragGrenade, 2);
		trooper.inventory.addItems(ItemType.KrakGrenade, 2);
		trooper.inventory.addItems(ItemType.Nacht5SmokeGrenade, 1);
		trooper.meleeWep = "Chain Sword";
	}
	
	public void Rifleman(Trooper trooper) throws Exception {
		trooper.rank = "Marine";
		trooper.designation = "Astartes";
		trooper.wep = "MKVb Bolter";
		trooper.ammo = 120;
		trooper.inventory.addItems(ItemType.MkvbBolter, 1);
		trooper.inventory.addItems(ItemType.MkvbBolter, ItemType.SmallArmsAmmo, 5);
		trooper.inventory.addItems(ItemType.AstartesFragGrenade, 2);
		trooper.inventory.addItems(ItemType.KrakGrenade, 2);
		trooper.inventory.addItems(ItemType.Nacht5SmokeGrenade, 1);
	}
	
	public void HeavyBolter(Trooper trooper) throws Exception {
		trooper.rank = "Marine";
		trooper.designation = "Astartes Heavy Bolter";
		trooper.wep = "MKIV Heavy Bolter";
		trooper.ammo = 200;
		trooper.inventory.addItems(ItemType.MkivHeavyBolter, 1);
		trooper.inventory.addItems(ItemType.MkivHeavyBolter, ItemType.SmallArmsAmmo, 1);
		trooper.inventory.addItems(ItemType.AstartesFragGrenade, 2);
		trooper.inventory.addItems(ItemType.KrakGrenade, 2);
		trooper.inventory.addItems(ItemType.Nacht5SmokeGrenade, 1);
	}

	public void MissileLauncher(Trooper trooper) throws Exception {
		trooper.rank = "Marine";
		trooper.designation = "Astartes Missile Launcher";
		trooper.wep = "MKVb Bolter";
		trooper.ammo = 80;
		trooper.inventory.addItems(ItemType.MkvbBolter, 1);
		trooper.inventory.addItems(ItemType.MkvbBolter, ItemType.SmallArmsAmmo, 3);
		trooper.inventory.addItems(ItemType.SoundStrikePattern, 1);
		trooper.inventory.addItems(ItemType.SoundStrikePattern, ItemType.Krak, 2);
		trooper.inventory.addItems(ItemType.SoundStrikePattern, ItemType.Frag, 2);
		trooper.inventory.addItems(ItemType.AstartesFragGrenade, 1);
		trooper.inventory.addItems(ItemType.KrakGrenade, 1);
		trooper.inventory.addItems(ItemType.Nacht5SmokeGrenade, 1);
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

		if(squad.equals("Command Team")) {
			var t = new Trooper("Astartes Sergeant", factionName);
			t.leaderType = LeaderType.SL;
			individuals.add(t);
			individuals.add(new Trooper("Astartes Standard", factionName));
			individuals.add(new Trooper("Astartes", factionName));
			individuals.add(new Trooper("Astartes", factionName));
		} else if(squad.equals("Rifle Team")) {
			var t = new Trooper("Astartes", factionName);
			t.leaderType = LeaderType.SL;
			individuals.add(t);
			individuals.add(new Trooper("Astartes", factionName));
			individuals.add(new Trooper("Astartes", factionName));
			individuals.add(new Trooper("Astartes", factionName));
		} else if(squad.equals("Heavy Rifle Team")) {
			var t = new Trooper("Astartes", factionName);
			t.leaderType = LeaderType.SL;
			individuals.add(t);
			individuals.add(new Trooper("Astartes", factionName));
			individuals.add(new Trooper("Astartes", factionName));
			individuals.add(new Trooper("Astartes Heavy Bolter", factionName));
		} else if(squad.equals("Mixed Rifle Team")) {
			var t = new Trooper("Astartes", factionName);
			t.leaderType = LeaderType.SL;
			individuals.add(t);
			individuals.add(new Trooper("Astartes", factionName));
			individuals.add(new Trooper("Astartes Missile Launcher", factionName));
			individuals.add(new Trooper("Astartes Heavy Bolter", factionName));
		} else if(squad.equals("Launcher Rifle Team")) {
			var t = new Trooper("Astartes", factionName);
			t.leaderType = LeaderType.SL;
			individuals.add(t);
			individuals.add(new Trooper("Astartes", factionName));
			individuals.add(new Trooper("Astartes Missile Launcher", factionName));
			individuals.add(new Trooper("Astartes Missile Launcher", factionName));
		}
		
	}
	
}
