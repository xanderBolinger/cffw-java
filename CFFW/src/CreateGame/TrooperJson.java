package CreateGame;

import java.util.ArrayList;

import CharacterBuilder.Ability;
import Items.Armor;
import Items.Inventory;
import Items.PersonalShield;
import Items.Armor.ArmorType;
import Items.Container;
import Trooper.IndividualStats;
import Trooper.Skill;
import Trooper.Skills;
import Trooper.Trooper;

public class TrooperJson {

	public ArrayList<Ability> abilities;
	public Skills skills;
	public String input;
	public String wep; 
	public Armor armor; 
	public PersonalShield personalShield;
	public String identifier;
	
	// Stats
	public int str;
	public int wis;
	public int wit;
	public int soc;
	public int wil;
	public int per;
	public int hlt;
	public int agi;
	
	public ArrayList<Container> containers;
	public int encumberanceModifier = 0;
	
	public String rank;
	public String role;
	public String vet; 
	public int legs;
	public int arms;
	public int ammo; 
	public String name;

	public TrooperJson(Trooper trooper) {
		abilities = trooper.abilities;
		skills = trooper.skills;
		input = trooper.input;
		wep = trooper.wep;
		armor = trooper.armor;
		personalShield = trooper.personalShield;
		identifier = trooper.identifier;
		
		str = trooper.str;
		wis = trooper.wis; 
		wit = trooper.wit;
		soc = trooper.soc;
		wil = trooper.wil;
		per = trooper.per;
		hlt = trooper.hlt;
		agi = trooper.agi;
		
		containers = trooper.inventory.containers;
		encumberanceModifier = trooper.encumberanceModifier;
	
		rank = trooper.rank;
		role = trooper.designation;
		vet = trooper.vet;
		legs = trooper.legs;
		arms = trooper.arms;
		ammo = trooper.ammo; 
		name = trooper.name;
	}
	
	public Trooper getTrooper() {
		Trooper trooper = new Trooper();
		trooper.alive = true; 
		trooper.conscious = true;
		trooper.skills = skills;
		trooper.abilities = abilities;
		trooper.armor = armor;
		trooper.personalShield = personalShield;
		trooper.str = str;
		trooper.wis = wis; 
		trooper.wit = wit;
		trooper.soc = soc;
		trooper.wil = wil;
		trooper.per = per;
		trooper.hlt = hlt;
		trooper.agi = agi;
		trooper.inventory.containers = containers;
		trooper.encumberanceModifier = encumberanceModifier;
		trooper.wep = wep;
		trooper.name = name; 
		trooper.ammo = ammo;
		
		int attr[] = { trooper.str, trooper.wit, trooper.soc, trooper.wil, trooper.per, 
				trooper.hlt, trooper.agi };
		
		trooper.skills(input, attr);

		IndividualStats individual = new IndividualStats(trooper.combatActions, trooper.sal,
				skills.getSkill("Pistol").value, 
				trooper.skills.getSkill("Rifle").value, 
				trooper.skills.getSkill("Launcher").value, 
				trooper.skills.getSkill("Heavy").value,
				trooper.skills.getSkill("Subgun").value, true);
		trooper.name = individual.name;
		trooper.P1 = individual.P1;
		trooper.P2 = individual.P2;

		trooper.identifier = identifier;
		trooper.HD = false;

		trooper.rank = rank;
		trooper.designation = role;
		trooper.vet = vet;
		trooper.legs = legs;
		trooper.arms = arms;
		
		if (trooper.str <= 6) {
			trooper.carryingCapacity = 40;
		} else if (trooper.str <= 10) {
			trooper.carryingCapacity = 60;
		} else if (trooper.str == 11) {
			trooper.carryingCapacity = 70;
		} else if (trooper.str <= 13) {
			trooper.carryingCapacity = 75;
		} else if (trooper.str <= 16) {
			trooper.carryingCapacity = 100;
		} else {
			trooper.carryingCapacity = 120;
		}
		
		trooper.setCombatStats(trooper);
		
		return trooper;
	}
	
}
