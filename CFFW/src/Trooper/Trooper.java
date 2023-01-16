package Trooper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Actions.Spot;
import CharacterBuilder.Ability;
import Conflict.ConflictLog;
import Conflict.Game;
import Conflict.GameWindow;
import Injuries.Injuries;
import Conflict.OpenUnit;
import CorditeExpansionStatBlock.StatBlock;
import FatigueSystem.FatigueSystem;
import Hexes.Building;
import Hexes.Hex;
import Injuries.ManualInjury;
import Injuries.ResolveHits;
import Items.Armor;
import Items.Armor.ArmorType;
import Items.Container.ContainerType;
import Items.Inventory;
import Items.Item.ItemType;
import Items.PersonalShield.ShieldType;
import Trooper.Trooper.BaseSpeed;
import Trooper.Trooper.MaximumSpeed;
import Items.PersonalShield;
import Items.Weapons;
import Unit.Unit;
import UtilityClasses.PCUtility;
import UtilityClasses.TrooperUtility;

public class Trooper implements Serializable {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private String path = System.getProperty("user.dir") + "\\";
	// private String path = "X:\\OneDrive\\OneDrive - Colostate\\Xander
	// Personal\\Code\\eclipse-workspace\\CFFW\\";
	// Callsign Name Rank Designations Vet P1 P2 Injuries Wep Ammo RWS Spot Camo
	// Stealth Leadership Tactics Kills Equipment Accomodations STR INT SOC WIL PER
	// HTL AGI Pistol Rifle Launcher Heavy Subgun SAL ISF IT A# DALM CA CAPI KO
	// Balance Climb Composure Dodge Endurance Expression Grapple Hold Jump/Leap
	// Lift/Pull Resist Pain Search Spot/Listen Stealth Calm Other Diplomacy Barter
	// Command Tactics Det. Motives Intimidate Persuade Digi. Systems Long Gun
	// Pistol Launcher Heavy Subgun Explosives First Aid Navigation Swim Throw

	// Skill Level
	public String weaponPercent;
	public int sl;
	public int encumberance = 0;
	public int carryingCapacity = 0;
	// Individual Stats
	// Set in this file
	public String rank;
	public String designation;
	public String faction;
	public String vet;
	public ArrayList<Injuries> injuries = new ArrayList<Injuries>();
	public Skills skills;
	public boolean conscious;
	public int incapacitationTime;
	public int timeUnconscious;
	public boolean mortallyWounded;
	public int timeMortallyWounded;
	public int physicianSkill;
	public boolean stabalized;
	public boolean alive;
	public int physicalDamage;
	public int ionDamage;
	public boolean zombie = false;
	public boolean entirelyMechanical = false;
	public ArrayList<String> mechanicalZones = new ArrayList<String>();
	public int criticalTime = 0;
	// in 20 second increments
	public int timePassed = 0;
	public int recoveryRoll = 0;
	public int recoveryRollMod;
	public int aidMod;
	public boolean recivingFirstAid;
	public boolean recoveryMade = false;
	public int arms;
	public int legs;
	public int disabledArms;
	public int disabledLegs;
	public boolean unspottable;
	public int spottingDifficulty;
	public boolean firedTracers;
	public boolean firedWeapons;
	public boolean reacted;
	public int size; // Amount of percent added or subtracted from the attack
	public double PCSize = 2;
	public String wep;
	public Armor armor = new Armor();
	public PersonalShield personalShield;
	// public ArrayList<Armor> armorTypes = new ArrayList<>();
	public String meleeWep;
	public int ammo;
	public int kills;
	public String eqiupment;
	public String accomodations;
	// Notes is not set until it is used
	public String notes;
	// Set by individual stats class
	public String name;
	public String identifier;
	public int P1;
	public int P2;
	public int spentPhase1;
	public int spentPhase2;
	public ArrayList<Spot> spotted = new ArrayList<Spot>();
	public int number = 0;
	public boolean HD;
	public String stance = "Standing";
	public boolean inCover = false;
	public boolean manualStance = false;
	public boolean nightVision = false;
	public boolean nightVisionInUse = false;
	public int nightVisionEffectiveness = 0;
	public boolean thermalVision = false;
	public boolean thermalVisionInUse = false;
	public boolean weaponLightOn = false;
	public boolean weaponLaserOn = false;
	public boolean weaponIRLaserOn = false;
	public int thermalVisionEffectiveness = 0;

	public boolean CloseCombat;
	public int rangeInPCHexes;
	public Hashtable<Trooper, Integer> pcRanges = new Hashtable<Trooper, Integer>();

	public Hashtable<Trooper, Integer> storedAimTime = new Hashtable<Trooper, Integer>();

	// Stats
	public int str;
	public int wis;
	public int wit;
	public int soc;
	public int wil;
	public int per;
	public int hlt;
	public int agi;

	// Abilities
	public ArrayList<Ability> abilities = new ArrayList<Ability>();
	
	public int characterPointTotal;
	public int spentCharacterPoints;
	

	// PC stats

	public int sal;
	public int isf;
	public int init;
	public int actions;
	public int DALM;
	public int combatActions;
	public int KO;

	public String input;
	
	// Simplified damage and wound stats
	public int hp;
	public int currentHP;
	public int armorLegacy = 0;
	public int headArmor = 0;
	public int armArmor = 0;
	public int legArmor = 0;
	public int shields;
	public int currentShields;
	public int shieldChance;

	// Veterancy
	public int veterancy;
	public FatigueSystem fatigueSystem;
	public BaseSpeed baseSpeed;
	public MaximumSpeed maximumSpeed;
	
	public int magnification = 0;

	// Close combat
	public boolean inCloseCombat = false;
	public boolean attackingInCloseCombat = false;
	public boolean defendingInCloseCombat = false;
	public Trooper closeCombatTarget = null;
	public int adaptabilityFactor;

	public Inventory inventory = new Inventory(this);
	public int encumberanceModifier = 0;
	
	public StatBlock ceStatBlock;
	
	public class MaximumSpeed implements Serializable {

		Trooper trooper;

		public MaximumSpeed(Trooper trooper) {
			this.trooper = trooper;
		}

		public double get() {
			return TrooperUtility.maximumSpeed(encumberance, trooper);
		}

	}
	
	public class BaseSpeed implements Serializable {

		Trooper trooper;

		public BaseSpeed(Trooper trooper) {
			this.trooper = trooper;
		}

		public double get() {
			return TrooperUtility.baseSpeed(encumberance, trooper);
		}

	}
	
	public Trooper() {
		inventory = new Inventory(this);
	}

	public Trooper(String name) {
		inventory.addContainer(ContainerType.Belt);
		
		this.name = name; 
		this.identifier = identifier();
		
		setBasicStats();
		calculateAttributes();
		calculateSkills();
		
		baseSpeed = new BaseSpeed(this);
		fatigueSystem = new FatigueSystem(this);
		setCombatStats(this);
		
		designation = "None"; 
		wep = "None";
		weaponPercent = "0";
		
	}

	public Trooper(String input, String faction) {
		inventory.addContainer(ContainerType.Belt);
		System.out.println("New Trooper, input: "+input+", faction: "+faction);

		if (faction.equals("Clone Trooper Phase 1")) {
			this.faction = "Clone Trooper Phase 1";
			try {
				cloneTrooperPhase1(input);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (faction.equals("CIS Battle Droid")) {
			// Should I add faction here, like above?
			try {
				cisBattleDroid(input);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (faction.equals("UNSC")) {
			this.faction = "UNSC";
			UNSC(input);

		} else if (faction.equals("Covenant")) {
			this.faction = "Covenant";
			covenant(input);
		} else if (faction.equals("Cordite Expansion")) {
			System.out.println("Pass Cordite Expansion");
			this.faction = "Cordite Expansion";
			corditeExpansion(input);

		}

		this.input = input;
		baseSpeed = new BaseSpeed(this);
		maximumSpeed = new MaximumSpeed(this);
		fatigueSystem = new FatigueSystem(this);
		setCombatStats(this);
	}

	public void corditeExpansion(String input) {
		this.kills = 0;
		this.veterancy = 0;
		this.physicalDamage = 0;
		this.conscious = true;
		this.alive = true;
		this.arms = 2;
		this.legs = 2;
		this.disabledArms = 0;
		this.disabledLegs = 0;
		this.armorLegacy = 14;
		this.CloseCombat = false;
		this.physicalDamage = 0;
		this.KO = 0;

		TLHStats attributes = new TLHStats(0, 0, 0, 0, 0, 0, 0);
		this.str = attributes.str;
		this.wit = attributes.wit;
		this.soc = attributes.soc;
		this.wil = attributes.wil;
		this.per = attributes.per;
		this.hlt = attributes.hlt;
		this.agi = attributes.agi;

		if (input.equals("Untrained")) {
			this.rank = "Untrained";
			this.designation = "Untrained";
		} else if (input.equals("Militia")) {
			System.out.println("Pass Militia 1");
			this.rank = "Militia";
			this.designation = "Militia";
		} else if (input.equals("Green")) {
			this.rank = "Green";
			this.designation = "Green";
		} else if (input.equals("Line")) {
			this.rank = "Line";
			this.designation = "Line";
		} else if (input.equals("Crack")) {
			this.rank = "Crack";
			this.designation = "Crack";
		} else if (input.equals("Elite")) {
			this.rank = "Elite";
			this.designation = "Elite";
		}

		this.vet = "";
		this.wep = "";
		this.meleeWep = "";
		this.ammo = 1000;
		this.eqiupment = "";
		this.accomodations = "";
		this.encumberance = 0;

		// Creates skills
		// Stores attributes in an array for the skill attr parameter
		int attr[] = { str, wit, soc, wil, per, hlt, agi };
		skills(input, attr);

		// Create and set individual stats
		IndividualStats individual = new IndividualStats(this.combatActions, sal, skills.getSkill("Pistol").value, 
				skills.getSkill("Rifle").value, 
				skills.getSkill("Launcher").value, 
				skills.getSkill("Heavy").value,
				skills.getSkill("Subgun").value, true);
		this.name = individual.name;
		this.P1 = individual.P1;
		this.P2 = individual.P2;


		// Sets identifier
		this.identifier = identifier();
		// Sets HD
		this.HD = false;
		// Sets max HP

		// According to the Jango Fett clone template special rule, sets the minimum
		// health to ten
		if (hlt < 10) {
			this.hlt = 10;
		}

		this.hp = hlt;
		this.legArmor = 0;
		this.armArmor = 0;
		this.headArmor = 0;
		this.armorLegacy = 0;
		// Sets current HP
		this.currentHP = hp;

		this.armor = new Armor(ArmorType.NONE);

		if (this.str <= 6) {
			this.carryingCapacity = 40;
		} else if (this.str <= 10) {
			this.carryingCapacity = 60;
		} else if (this.str == 11) {
			this.carryingCapacity = 70;
		} else if (this.str <= 13) {
			this.carryingCapacity = 75;
		} else if (this.str <= 16) {
			this.carryingCapacity = 100;
		} else {
			this.carryingCapacity = 120;
		}

	}

	// TODO: Add armor, add KO, add PD, encum, source book
	public void covenant(String input) {
		this.kills = 0;
		this.veterancy = 0;
		this.physicalDamage = 0;
		this.conscious = true;
		this.alive = true;
		this.arms = 2;
		this.legs = 2;
		this.disabledArms = 0;
		this.disabledLegs = 0;
		this.armorLegacy = 14;
		this.CloseCombat = false;
		this.physicalDamage = 0;
		this.KO = 0;

		if (input.equals("Elite Minor - Lance Leader")) { // Squad Leader
			// Creates attributes
			TLHStats attributes = new TLHStats(4, 2, 0, 3, 2, 4, 2);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Lance Leader";
			this.designation = "Squad Leader";
			this.vet = "";
			this.wep = "Type-51 Carbine";
			this.ammo = 144;
			this.eqiupment = "Type 51 Carbine, 16lb. Eight, 18 charge magazines, 16lb. Elite Minor Armor, 30lb. 2 Plasma Grenade, 4lb.";
			this.encumberance = 66;
			this.accomodations = "";
			this.armorLegacy = 25;
			this.size = 5;
			this.name = "Elite";
			this.currentShields = 150;
			this.shields = 150;
			this.shieldChance = 100;
			armor = new Armor(ArmorType.ELITE);
			personalShield = new PersonalShield(ShieldType.ELITEMINOR);
			this.PCSize = 2.7;

		} else if (input.equals("Grunt Minor")) {
			// Creates attributes
			TLHStats attributes = new TLHStats(-2, -2, -1, -1, -1, -1, -2);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Minor";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "Type-25 Rifle";
			this.ammo = 200;
			this.eqiupment = "Type-25 Rifle, 12lb. One 100 shot battery, 1lb. Grunt Armor, 20lb. 2 Plasma Grenade, 4lb.";
			this.encumberance = 37;
			armor = new Armor(ArmorType.GRUNT);
			this.accomodations = "";
			this.size = -5;
			this.name = "Grunt";
			input = "Grunt Minor";
			this.PCSize = 1.8;
		} else if (input.equals("Grunt Minor - Rifle Variant")) {
			// Creates attributes
			TLHStats attributes = new TLHStats(-2, -2, -1, -1, -1, -1, -2);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Minor";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "Type-25 Rifle";
			this.ammo = 200;
			this.eqiupment = "Type-25 Rifle, 12lb. One 100 shot battery, 1lb. Grunt Armor, 20lb. 2 Plasma Grenade, 4lb.";
			this.encumberance = 37;
			armor = new Armor(ArmorType.GRUNT);
			this.accomodations = "";
			this.size = -5;
			this.name = "Grunt";
			input = "Grunt Minor";
			this.PCSize = 1.8;
		} else if (input.equals("Grunt Minor - Carbine Variant")) {
			// Creates attributes
			TLHStats attributes = new TLHStats(-2, -2, -1, -1, -1, -1, -2);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Minor";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "Type-51 Carbine";
			this.ammo = 144;
			this.eqiupment = "Type 51 Carbine, 16lb. Eight, 18 charge magazines, 16lb. Grunt Armor, 20lb. 2 Plasma Grenade, 4lb.";
			this.encumberance = 56;
			armor = new Armor(ArmorType.GRUNT);
			this.accomodations = "";
			this.size = -5;
			this.name = "Grunt";
			input = "Grunt Minor";
			this.PCSize = 1.8;
		} else if (input.equals("Grunt Minor - Pistol Variant")) {
			// Creates attributes
			TLHStats attributes = new TLHStats(-2, -2, -1, -1, -1, -1, -2);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Minor";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "Type-25 Pistol";
			this.ammo = 200;
			this.eqiupment = "Type-25 Pistol, 8lb. One 100 shot battery, 1lb. Grunt Armor, 20lb. 2 Plasma Grenade, 4lb.";
			this.encumberance = 33;
			armor = new Armor(ArmorType.GRUNT);
			this.accomodations = "";
			this.size = -5;
			this.name = "Grunt";
			input = "Grunt Minor";
			this.PCSize = 1.8;
		} else if (input.equals("Grunt Conscript")) {
			// Creates attributes
			TLHStats attributes = new TLHStats(-2, -2, -1, -1, -1, -1, -2);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Conscript";
			this.designation = "Conscript";
			this.vet = "";
			this.wep = "Type-25 Pistol";
			this.ammo = 100;
			this.eqiupment = "Type-25 Pistol, 8lb. One 100 shot battery, 1lb. 1 Plasma Grenade, 2lb.";
			this.encumberance = 11;
			armor = new Armor(ArmorType.NONE);
			this.accomodations = "";
			this.size = -5;
			this.name = "Grunt";
			this.PCSize = 1.8;
		} else if (input.equals("Jackal Minor - Marksman")) {
			// Creates attributes
			TLHStats attributes = new TLHStats(1, 1, 2, 1, 4, 1, 2);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Minor";
			this.designation = "Marksman";
			this.vet = "";
			this.wep = "Type-51 Carbine";
			this.ammo = 144;
			this.eqiupment = "Type 51 Carbine, 16lb. Eight, 18 charge magazines, 16lb. Jackal Armor, 20lb. 2 Plasma Grenade, 4lb.";
			this.encumberance = 56;
			armor = new Armor(ArmorType.JACKAL);
			this.accomodations = "";
			this.size = -5;
			this.name = "Jackal";
		} else if (input.equals("Jackal Minor - Carbine Variant")) {
			input = "Jackal Minor - Marksman";
			// Creates attributes
			TLHStats attributes = new TLHStats(1, 1, 2, 1, 4, 1, 2);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Minor";
			this.designation = "Marksman";
			this.vet = "";
			this.wep = "Type-51 Carbine";
			this.ammo = 144;
			this.eqiupment = "Type 51 Carbine, 16lb. Eight, 18 charge magazines, 16lb. Jackal Armor, 20lb. 2 Plasma Grenade, 4lb.";
			this.encumberance = 56;
			armor = new Armor(ArmorType.JACKAL);
			this.accomodations = "";
			this.size = -5;
			this.name = "Jackal";
		} else if (input.equals("Jackal Minor")) {
			// Creates attributes
			TLHStats attributes = new TLHStats(1, 1, 2, 1, 4, 1, 2);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Minor";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "Type-25 Pistol";
			this.ammo = 200;
			this.eqiupment = "Type-25 Pistol, 8lb. One 100 shot battery, 1lb. Jacakal Armor, 20lb. 2 Plasma Grenade, 4lb. Shield belt, 6lb.";
			this.encumberance = 39;
			armor = new Armor(ArmorType.JACKAL);
			personalShield = new PersonalShield(ShieldType.DCR1RifleShield);
			this.accomodations = "";
			this.size = -5;
			this.name = "Jackal";
			this.currentShields = 400;
			this.shields = 400;
			this.shieldChance = 65;
			input = "Jackal Minor";
		} else if (input.equals("Jackal Minor - Pistol Variant")) {
			// Creates attributes
			TLHStats attributes = new TLHStats(1, 1, 2, 1, 4, 1, 2);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Minor";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "Type-25 Pistol";
			this.ammo = 200;
			this.eqiupment = "Type-25 Pistol, 8lb. One 100 shot battery, 1lb. Jacakal Armor, 20lb. 2 Plasma Grenade, 4lb. Shield belt, 6lb.";
			this.encumberance = 39;
			armor = new Armor(ArmorType.JACKAL);
			personalShield = new PersonalShield(ShieldType.DCR1RifleShield);
			this.accomodations = "";
			this.size = -5;
			this.name = "Jackal";
			this.currentShields = 400;
			this.shields = 400;
			this.shieldChance = 65;
			input = "Jackal Minor";
		} else if (input.equals("Jackal Minor - Sniper Variant")) {
			// Creates attributes
			TLHStats attributes = new TLHStats(1, 1, 2, 1, 4, 1, 2);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Minor";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "Type-50 SRS";
			this.ammo = 80;
			this.eqiupment = "Type-50 SRS, 65lb. Ten 8 shot cartridges, 10lb. Jacakal Armor, 20lb. 2 Plasma Grenade, 4lb.";
			this.encumberance = 39;
			armor = new Armor(ArmorType.JACKAL);
			this.accomodations = "";
			this.size = -5;
			this.name = "Jackal";
			this.currentShields = 400;
			this.shields = 400;
			this.shieldChance = 65;
			input = "Jackal Minor - Marksman";
		}

		// Creates skills
		// Stores attributes in an array for the skill attr parameter
		int attr[] = { str, wit, soc, wil, per, hlt, agi };
		skills(input, attr);

		// Create and set individual stats
		// Create and set individual stats
		IndividualStats individual = new IndividualStats(this.combatActions, sal, skills.getSkill("Pistol").value, 
				skills.getSkill("Rifle").value, 
				skills.getSkill("Launcher").value, 
				skills.getSkill("Heavy").value,
				skills.getSkill("Subgun").value, true);

		this.P1 = individual.P1;
		this.P2 = individual.P2;


		// Sets identifier
		this.identifier = identifier();
		// Sets HD
		this.HD = false;
		// Sets max HP
		this.hp = hlt;

		// Sets knockout value
		this.KO += this.wil * this.hlt;

		// Sets current HP
		this.currentHP = hp;

		if (this.str <= 6) {
			this.carryingCapacity = 40;
		} else if (this.str <= 10) {
			this.carryingCapacity = 60;
		} else if (this.str == 11) {
			this.carryingCapacity = 70;
		} else if (this.str <= 13) {
			this.carryingCapacity = 75;
		} else if (this.str <= 16) {
			this.carryingCapacity = 100;
		} else {
			this.carryingCapacity = 120;
		}
	}

	// TODO: Add armor, add KO, add PD, encum, source book
	public void UNSC(String input) {
		this.kills = 0;
		this.veterancy = 0;
		this.physicalDamage = 0;
		this.conscious = true;
		this.alive = true;
		this.arms = 2;
		this.legs = 2;
		this.disabledArms = 0;
		this.disabledLegs = 0;
		this.CloseCombat = false;

		TLHStats attributes = new TLHStats(2, 1, 0, 1, 1, 2, 1);

		if (input.equals("Squad Leader")) { // Squad Leader
			this.rank = "Sergeant";
			this.designation = "Squad Leader";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 256;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 8, 32 Round Magazines, 14lb (-2 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		} else if (input.equals("Rifleman")) { // Rifleman
			this.rank = "Trooper";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 256;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 8, 32 Round Magazines, 14lb (-2 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		} else if (input.equals("Rifleman++")) { // Rifleman++
			this.rank = "Corporal";
			this.designation = "Rifleman++";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 256;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 8, 32 Round Magazines, 14lb (-2 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		} else if (input.equals("Autorifleman")) { // Autorifleman
			this.rank = "Trooper";
			this.designation = "Autorifleman";
			this.vet = "";
			this.wep = "M739 SAW";
			this.ammo = 144;
			this.eqiupment = "M739 SAW, 19lb loaded (otherwise 14). 2 72rnd Drums--M118, FMJ-AP 4 lb (-4 from gun mag). UNSC Marine Armor, 17lb. 1 M9 Fragmentation Grenade, 1lb.";
			this.accomodations = "";
			this.encumberance = 51;
			this.magnification = 6;
		} else if (input.equals("Assistant Autorifleman")) { // Assistant Autorifleman
			// Creates attributes
			this.rank = "Trooper";
			this.designation = "Assistant Autorifleman";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 256;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 8, 32 Round Magazines, 14lb (-2 from gun mag). UNSC Marine Armor, 17lb.  2 72rnd Drums--M118, FMJ-AP 8 lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 51;
			this.magnification = 6;
		} else if (input.equals("Ammo Bearer")) { // Ammo Bearer
			this.rank = "Trooper";
			this.designation = "Ammo Bearer";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 384;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 12, 32 Round Magazines, 22lb (-2 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 51;
			this.magnification = 6;
		} else if (input.equals("Marksman")) { // Marksman
			this.rank = "Trooper";
			this.designation = "Marksman";
			this.vet = "";
			this.wep = "M392 DMR";
			this.ammo = 120;
			this.eqiupment = "M392 DMR, 11 lb loaded (otherwise 10). M118 FMJ-AP, 8 Magazines, 7lb (-1 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 37;
			this.magnification = 6;
		} else if (input.equals("Combat Life Saver")) { // Combat Life Saver
			this.rank = "Trooper";
			this.designation = "Combat Life Saver";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 256;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 8, 32 Round Magazines, 14lb (-2 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		} else if (input.equals("EOD")) { // EOD
			this.rank = "Trooper";
			this.designation = "EOD";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 256;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 8, 32 Round Magazines, 14lb (-2 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		} else if (input.equals("AT Specialist")) { // AT Specialist
			this.rank = "Trooper";
			this.designation = "AT Specialist";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 256;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 8, 32 Round Magazines, 14lb (-2 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		} else if (input.equals("Assistant AT specialist")) { // Assistant AT specialist
			this.rank = "Trooper";
			this.designation = "Assistant AT specialist";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 256;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 8, 32 Round Magazines, 14lb (-2 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
			;
		} else if (input.equals("Ranger")) { // Ranger
			this.rank = "Trooper";
			this.designation = "Ranger";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 256;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 8, 32 Round Magazines, 14lb (-2 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		} else if (input.equals("Platoon Sergeant")) { // Platoon Sergeant
			this.rank = "Sergeant First Class";
			this.designation = "Platoon Sergeant ";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 256;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 8, 32 Round Magazines, 14lb (-2 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		} else if (input.equals("Captain")) { // Captain
			// Creates attributes
			this.rank = "Captain";
			this.designation = "Captain";
			this.vet = "";
			this.wep = "MA37";
			this.ammo = 256;
			this.eqiupment = "MA37, 11lb loaded (9 otherwise). 8, 32 Round Magazines, 14lb (-2 from gun mag). UNSC Marine Armor, 17lb. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		} else if (input.equals("ODST - Rifleman")) {
			// Creates attributes
			attributes = new TLHStats(2, 2, 0, 2, 2, 2, 2);
			this.rank = "Trooper";
			this.designation = "ODST - Rifleman";
			this.vet = "";
			this.wep = "BR55";
			this.ammo = 256;
			this.eqiupment = "BR55, 10lb loaded (8 otherwise). 8, 32 Round Magazines, 14lb. ODST Armor, 17lbs. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		} else if (input.equals("Rifleman BR")) { // Rifleman
			this.rank = "Trooper";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "BR55";
			this.ammo = 256;
			this.eqiupment = "BR55, 10lb loaded (8 otherwise). 8, 32 Round Magazines, 14lb. ODST Armor, 17lbs. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		} else if (input.equals("Rifleman++ BR")) { // Rifleman++
			// Creates attributes
			this.rank = "Corporal";
			this.designation = "Rifleman++";
			this.vet = "";
			this.wep = "BR55";
			this.ammo = 256;
			this.eqiupment = "BR55, 10lb loaded (8 otherwise). 8, 32 Round Magazines, 14lb. ODST Armor, 17lbs. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;

		} else if (input.equals("Combat Life Saver BR")) { // Combat Life Saver
			// Creates attributes
			this.rank = "Trooper";
			this.designation = "Combat Life Saver";
			this.vet = "";
			this.wep = "BR55";
			this.ammo = 256;
			this.eqiupment = "BR55, 10lb loaded (8 otherwise). 8, 32 Round Magazines, 14lb. ODST Armor, 17lbs. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;

		} else if (input.equals("Squad Leader BR")) { // Squad Leader
			// Creates attributes
			this.rank = "Sergeant";
			this.designation = "Squad Leader";
			this.vet = "";
			this.wep = "BR55";
			this.ammo = 256;
			this.eqiupment = "BR55, 10lb loaded (8 otherwise). 8, 32 Round Magazines, 14lb. ODST Armor, 17lbs. 2 M9 Fragmentation Grenade, 2lb.";
			this.accomodations = "";
			this.encumberance = 43;
			this.magnification = 6;
		}

		this.str = attributes.str;
		this.wit = attributes.wit;
		this.soc = attributes.soc;
		this.wil = attributes.wil;
		this.per = attributes.per;
		this.hlt = attributes.hlt;
		this.agi = attributes.agi;

		// Creates skills
		// Stores attributes in an array for the skill attr parameter
		int attr[] = { str, wit, soc, wil, per, hlt, agi };
		skills(input, attr);

		// Create and set individual stats
		// Create and set individual stats
		IndividualStats individual = new IndividualStats(this.combatActions, sal, skills.getSkill("Pistol").value, 
				skills.getSkill("Rifle").value, 
				skills.getSkill("Launcher").value, 
				skills.getSkill("Heavy").value,
				skills.getSkill("Subgun").value, true);
		
		this.name = individual.name;
		this.P1 = individual.P1;
		this.P2 = individual.P2;

		// Sets identifier
		this.identifier = identifier();
		// Sets HD
		this.HD = false;
		// Sets max HP
		this.hp = hlt;
		this.armorLegacy = 20;
		// Sets current HP
		this.currentHP = hp;

		this.armor = new Armor();

		if (input.contains("ODST")) {
			this.armor.odst();
		} else {
			this.armor.unscMarine();
		}

		if (this.str <= 6) {
			this.carryingCapacity = 40;
		} else if (this.str <= 10) {
			this.carryingCapacity = 60;
		} else if (this.str == 11) {
			this.carryingCapacity = 70;
		} else if (this.str <= 13) {
			this.carryingCapacity = 75;
		} else if (this.str <= 16) {
			this.carryingCapacity = 100;
		} else {
			this.carryingCapacity = 120;
		}

	}

	public void cloneTrooperPhase1(String input) throws Exception {
		this.kills = 0;
		this.veterancy = 0;
		this.physicalDamage = 0;
		this.conscious = true;
		this.alive = true;
		this.arms = 2;
		this.legs = 2;
		this.disabledArms = 0;
		this.disabledLegs = 0;
		this.CloseCombat = false;
		this.armor.Phase1CloneArmor();
		this.criticalTime = 0;

		TLHStats attributes = new TLHStats(2, 2, -2, 2, 1, 1, 2);

		// in 20 second increments
		this.timePassed = 0;
		this.recoveryRoll = 0;
		if (input.equals("Clone Squad Leader")) { // Squad Leader
			// Creates attributes

			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Sergeant";
			this.designation = "Squad Leader";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A+DC40, 12lbs(14lbs loaded), Hands[10x1xN/A].\n2 BlasTech DCA1, 4lbs, Belt Magnitized[1x1x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\n10 DC40 HEAT Rounds, 2lbs, Belt Magnitized[0.75x0.75x0.75].\n"
					+ "4 DC40 White Smoke Rounds, 0.5lbs, Belt Magnitized[0.75x0.75x0.75].\n1 DC40 Red Smoke Rounds, 2lbs, Belt Magnitized[0.75x0.75x0.75].\n"
					+ "1 DC40 Blue Smoke Rounds, 0.5lbs, Belt Magnitized[0.75x0.75x0.75].\nNightview Macrobinoculars, 2lbs, Belt Magnitized[1x1x0.3].\nPhase 1 Armor, 35lbs.\n"
					+ "1 DC40 Yellow Smoke Rounds, 0.5lbs, Belt Magnitized[0.75x0.75x0.75].\n";
			this.accomodations = "";
			this.encumberance = 67;
			this.magnification = 24;
			
			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);
			inventory.addItems(ItemType.DC40, 1);
			inventory.addItems(ItemType.DC40, ItemType.HEAT, 10);

		} else if (input.equals("Clone Rifleman")) { // Rifleman
			// Creates attributes

			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Trooper";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A, 12lbs(14lbs loaded), Hands[10x1xN/A].\n2 BlasTech DCA1, 4lbs, Belt Magnitized[1x1x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\nPhase 1 Armor, 35lbs.";
			this.accomodations = "";
			this.encumberance = 55;
			
			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);

		} else if (input.equals("Clone Rifleman++")) { // Rifleman++
			// Creates attributes
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Corporal";
			this.designation = "Rifleman++";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A, 12lbs(14lbs loaded), Hands[10x1xN/A].\n2 BlasTech DCA1, 4lbs, Belt Magnitized[1x1x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\nPhase 1 Armor, 35lbs.\n";
			this.accomodations = "";
			this.encumberance = 55;
			
			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);

		} else if (input.equals("Clone Autorifleman")) { // Autorifleman
			// Creates attributes
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Trooper";
			this.designation = "Autorifleman";
			this.vet = "";
			this.wep = "Z6";
			this.meleeWep = "Vibroknife";
			this.ammo = 100;
			this.eqiupment = "Z6, 20lbs(24lbs loaded), Hands[14x2xN/A].\nPhase 1 Armor, 35lbs.";
			this.accomodations = "";
			this.encumberance = 59;

			inventory.addItems(ItemType.Z6, 1);
			inventory.addItems(ItemType.Z6, ItemType.SmallArmsAmmo, 1);
			
		} else if (input.equals("Clone Assistant Autorifleman")) { // Assistant Autorifleman
			// Creates attributes
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Trooper";
			this.designation = "Assistant Autorifleman";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A, 12lbs(14lbs loaded), Hands[10x1xN/A].\n2 BlasTech DCA1, 4lbs, Belt Magnitized[1x1x0.5].\n2 BlasTech R1, 8lbs, Belt Magnitized[1.5x1.5x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\nPhase 1 Armor, 35lbs.\n";
			this.accomodations = "";
			this.encumberance = 63;
			
			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.Z6, ItemType.SmallArmsAmmo, 2);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);

		} else if (input.equals("Clone Ammo Bearer")) { // Ammo Bearer
			// Creates attributes
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Trooper";
			this.designation = "Ammo Bearer";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A, 12lbs(14lbs loaded), Hands[10x1xN/A].\n8 BlasTech DCA1, 16lbs, Belt Magnitized[1x1x0.5].\n4 Class-A Thermal Detonators, 8lbs, Belt Magnitized[0.75x0.75x0.75].\nPhase 1 Armor, 35lbs.\n";
			this.accomodations = "";
			this.encumberance = 73;
			
			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 9);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);

		} else if (input.equals("Clone Marksman")) { // Marksman

			// Creates attributes
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Trooper";
			this.designation = "Marksman";
			this.vet = "";
			this.wep = "DC15X";
			this.meleeWep = "Vibroknife";
			this.ammo = 40;
			this.eqiupment = "DC-15X, 15lbs(17lbs loaded), Hands[15x1xN/A].\n3 BlasTech DCA1, 6lbs, Belt Magnitized[1x1x0.5].\nNightview Macrobinoculars, 2lbs, Belt Magnitized[1x1x0.3].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\nPhase 1 Armor, 35lbs.\n";
			this.accomodations = "";
			this.encumberance = 60;
			this.magnification = 24;
			
			inventory.addItems(ItemType.DC15X, 1);
			inventory.addItems(ItemType.DC15X, ItemType.SmallArmsAmmo, 4);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);

		} else if (input.equals("Clone Combat Life Saver")) { // Combat Life Saver
			// Creates attributes
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Trooper";
			this.designation = "Combat Life Saver";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A, 12lbs(14lbs loaded), Hands[10x1xN/A].\n2 BlasTech DCA1, 4lbs, Belt Magnitized[1x1x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\nPhase 1 Armor, 35lbs.\n";
			this.accomodations = "";
			this.encumberance = 55;
			
			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);

		} else if (input.equals("EOD")) { // EOD
			// Creates attributes
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Trooper";
			this.designation = "EOD";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A, 12lbs(14lbs loaded), Hands[10x1xN/A].\n2 BlasTech DCA1, 4lbs, Belt Magnitized[1x1x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\nPhase 1 Armor, 35lbs.\n";
			this.accomodations = "";
			this.encumberance = 55;
			
			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);

		} else if (input.equals("Clone AT Specialist")) { // AT Specialist
			// Creates attributes
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Trooper";
			this.designation = "AT Specialist";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A, 12lbs(14lbs loaded), Hands[10x1xN/A].\nRPS6, 20lbs, Sling on Back[15x2x1].\nHEAA, 5lbs, Clipped On Back[4x0.75x0.75].\nHEDP, 5lbs, Clipped On Back[4x0.75x0.75].\nPower Packs, 4lbs.\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\nPhase 1 Armor, 35lbs.\n";
			this.accomodations = "";
			this.encumberance = 85;

			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.RPS6, 1);
			inventory.addItems(ItemType.RPS6, ItemType.HEAT, 2);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);
			
		} else if (input.equals("Clone Assistant AT Specialist")) { // Assistant AT specialist
			// Creates attributes
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Trooper";
			this.designation = "Assistant AT Specialist";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A, 12lbs(14lbs loaded), Hands[10x1xN/A].\n2 HEAA, 10lbs, In Back Pack Main Space[4x0.75x0.75].\n2 HEDP, 10lbs, In Back Pack Second Space[4x0.75x0.75].\n2 BlasTech DCA1, 4lbs, Belt Magnitized[1x1x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\nPhase 1 Armor, 35lbs.\nPhase 1 Clone Trooper Back Pack.";
			this.accomodations = "";
			this.encumberance = 75;
			
			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.RPS6, ItemType.HEAT, 4);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);

		} else if (input.equals("Ranger")) { // Ranger

			// Creates attributes
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Trooper";
			this.designation = "Ranger";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A, 12lbs(14lbs loaded), Hands[10x1xN/A].\n2 BlasTech DCA1, 4lbs, Belt Magnitized[1x1x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\n Phase 1 Armor, 35lbs.\n";
			this.accomodations = "";
			this.encumberance = 55;
			
			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);

		} else if (input.equals("Platoon Sergeant")) { // Platoon Sergeant
			// Creates attributes
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Sergeant First Class";
			this.designation = "Platoon Sergeant ";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A, 12lbs(14lbs loaded), Hands[10x1xN/A].\n2 BlasTech DCA1, 4lbs, Belt Magnitized[1x1x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\nNightview Macrobinoculars, 2lbs, Belt Magnitized[1x1x0.3].\nPhase 1 Armor, 35lbs.";
			this.accomodations = "";
			this.encumberance = 57;
			this.magnification = 24;
			
			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);

		} else if (input.equals("Captain")) { // Captain
			// Creates attributes
			attributes = new TLHStats(2, 3, 1, 2, 1, 2, 3);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Captain";
			this.designation = "Captain";
			this.vet = "";
			this.wep = "DC15A";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "DC-15A+DC40, 12lbs(14lbs loaded), Hands[10x1xN/A].\n2 BlasTech DCA1, 4lbs, Belt Magnitized[1x1x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\n10 DC40 HEAT Rounds, 2lbs, Belt Magnitized[0.75x0.75x0.75].\n"
					+ "4 DC40 White Smoke Rounds, 0.5lbs, Belt Magnitized[0.75x0.75x0.75].\n1 DC40 Red Smoke Rounds, 2lbs, Belt Magnitized[0.75x0.75x0.75].\n"
					+ "1 DC40 Blue Smoke Rounds, 0.5lbs, Belt Magnitized[0.75x0.75x0.75].\nNightview Macrobinoculars, 2lbs, Belt Magnitized[1x1x0.3].\nPhase 1 Armor, 35lbs.\n"
					+ "1 DC40 Yellow Smoke Rounds, 0.5lbs, Belt Magnitized[0.75x0.75x0.75].\n";
			this.accomodations = "";
			this.encumberance = 67;
			this.magnification = 24;
			
			inventory.addItems(ItemType.DC15A, 1);
			inventory.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);
			inventory.addItems(ItemType.DC40, 1);
			inventory.addItems(ItemType.DC40, ItemType.HEAT, 10);

		} else if (input.equals("ARC Trooper")) { // ARC Trooper
			// Creates attributes
			attributes = new TLHStats(3, 4, -1, 2, 2, 2, 3);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "ARC Trooper";
			this.designation = "ARC Trooper";
			this.vet = "";
			this.wep = "Westar M5";
			this.meleeWep = "Vibroknife";
			this.ammo = 250;
			this.eqiupment = "Westar M5, 10lbs(12lbs loaded), Hands[10x1xN/A].\n4 BlasTech DCA1, 8lbs, Belt Magnitized[1x1x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\nArc Trooper Phase 1 Armor, 35lbs.";
			this.accomodations = "";
			this.encumberance = 57;
			this.armor = new Armor();
			this.armor.Phase1ARC();
			
			inventory.addItems(ItemType.M5, 1);
			inventory.addItems(ItemType.M5, ItemType.SmallArmsAmmo, 3);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);

		} else if (input.equals("Republic Commando")) { // ARC Trooper
			// Creates attributes
			attributes = new TLHStats(3, 2, -1, 2, 2, 3, 3);
			this.str = attributes.str;
			this.wit = attributes.wit;
			this.soc = attributes.soc;
			this.wil = attributes.wil;
			this.per = attributes.per;
			this.hlt = attributes.hlt;
			this.agi = attributes.agi;

			this.rank = "Republic Commando";
			this.designation = "Republic Commando";
			this.vet = "";
			this.wep = "DC17m";
			this.meleeWep = "Vibroknife";
			this.ammo = 250;
			this.eqiupment = "DC17m, 8lbs(10lbs loaded), Hands[10x1xN/A].\nDC17m Launcher, 4lbs, Belt Magnitized[1x1x0.5].\nDC17m Sniper, 4lbs, Belt Magnitized[1x1x0.5].\n4 DC17m Sniper Cartridges(5)(5)(5)(5), 4lbs, Belt Magnitized[1x1x0.5].\\4 BlasTech DCA1, 8lbs, Belt Magnitized[1x1x0.5].\nClass-A Thermal Detonator, 2lbs, Belt Magnitized[0.75x0.75x0.75].\n2 HE Grenades, 2 HEAT Grenades, 8lbs, Belt Magnitized[0.75x0.75x0.75].\\nKatarn Armor, 35lbs.";
			this.accomodations = "";
			this.encumberance = 75;
			this.armor.katarnArmor();
			
			inventory.addItems(ItemType.DC17M, 1);
			inventory.addItems(ItemType.DC17MSniper, 1);
			inventory.addItems(ItemType.DC17MRocket, 1);
			inventory.addItems(ItemType.DC17M, ItemType.SmallArmsAmmo, 5);
			//inventory.addItems(ItemType.DC17MRocket, ItemType.SmallArmsAmmo, 1);
			inventory.addItems(ItemType.DC17MSniper, ItemType.SmallArmsAmmo, 4);
			inventory.addItems(ItemType.DC17MRocket, ItemType.HEAT, 2);
			inventory.addItems(ItemType.DC17MRocket, ItemType.HE, 2);
			inventory.addItems(ItemType.ClassAThermalDetonator, 1);
		} else {
			throw new Exception("Invalid Trooper Input.");
		}

		// Pack mule 
		this.encumberanceModifier -= 20; 
		
		inventory.setEncumberance();
		
		if(this.encumberance < 0) {
			this.encumberance = 5; 
		}
		
		// According to the Jango Fett clone template special rule, sets the minimum
		// health to ten
		if (hlt < 10) {
			this.hlt = 10;
		}

				
		// Creates skills
		// Stores attributes in an array for the skill attr parameter
		int attr[] = { str, wit, soc, wil, per, hlt, agi };
		skills(input, attr);

		// Create and set individual stats
		IndividualStats individual = new IndividualStats(this.combatActions, sal, skills.getSkill("Pistol").value, 
				skills.getSkill("Rifle").value, 
				skills.getSkill("Launcher").value, 
				skills.getSkill("Heavy").value,
				skills.getSkill("Subgun").value, true);
		
		this.name = individual.name;
		this.P1 = individual.P1;
		this.P2 = individual.P2;


		// Sets identifier
		this.identifier = identifier();
		// Sets HD
		this.HD = false;
		// Sets max HP

		
		this.hp = hlt;
		this.legArmor = 44;
		this.armArmor = 44;
		this.headArmor = 44;
		this.armorLegacy = 88;
		// Sets current HP
		this.currentHP = hp;

		if (this.str <= 6) {
			this.carryingCapacity = 40;
		} else if (this.str <= 10) {
			this.carryingCapacity = 60;
		} else if (this.str == 11) {
			this.carryingCapacity = 70;
		} else if (this.str <= 13) {
			this.carryingCapacity = 75;
		} else if (this.str <= 16) {
			this.carryingCapacity = 100;
		} else {
			this.carryingCapacity = 120;
		}
	}

	/*
	 * B1 Seargeant B1 Rifleman B1 Marksman B1 Autorifleman B1 Assistant
	 * Autorifleman B1 AT B1 Assistant AT B2 Wristlaser
	 */
	public void cisBattleDroid(String input) throws Exception {
		this.kills = 0;
		this.veterancy = 0;
		this.physicalDamage = 0;
		this.conscious = true;
		this.alive = true;
		this.hp = 10;
		this.legArmor = 35;
		this.armArmor = 35;
		this.headArmor = 35;
		this.armorLegacy = 70;
		this.arms = 2;
		this.legs = 2;
		this.disabledArms = 0;
		this.disabledLegs = 0;
		this.CloseCombat = false;
		this.entirelyMechanical = true;
		// B1 Sergeant
		if (input.equals("B1 Squad Leader")) {
			// Sets name
			this.name = "B1";

			// Creates attributes
			this.str = 12;
			this.wit = 10;
			this.soc = 5;
			this.wil = 10;
			this.per = 10;
			this.hlt = 10;
			this.agi = 12;

			this.rank = "Sergeant";
			this.designation = "Squad Leader";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n2 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\nNightview Macrobinoculars, 2lbs, Belt Magnitized[1x1x0.3].\nSelf, 20lbs.\n";
			this.accomodations = "";
			this.encumberance = 36;
			this.magnification = 24;
			
			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 3);
			
			// B1 Rifleman
		} else if (input.equals("B1 Rifleman")) {
			// Sets name
			this.name = "B1";

			// Creates attributes
			this.str = 12;
			this.wit = 10;
			this.soc = 5;
			this.wil = 10;
			this.per = 10;
			this.hlt = 10;
			this.agi = 12;

			this.rank = "Battledroid";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n2 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 34;

			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 3);
			
			// B1 Marksman
		} else if (input.equals("B1 Ammo Bearer")) {
			// Sets name
			this.name = "B1";

			// Creates attributes
			this.str = 12;
			this.wit = 10;
			this.soc = 5;
			this.wil = 10;
			this.per = 10;
			this.hlt = 10;
			this.agi = 12;

			this.rank = "Battledroid";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n8 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 34;

			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 9);
			
			// B1 Marksman
		} else if (input.equals("B1 Marksman")) {
			// Sets name
			this.name = "B1";

			// Creates attributes
			this.str = 12;
			this.wit = 10;
			this.soc = 5;
			this.wil = 10;
			this.per = 10;
			this.hlt = 10;
			this.agi = 12;

			this.rank = "Battledroid";
			this.designation = "Marksman";
			this.vet = "";
			this.wep = "E5S";
			this.meleeWep = "Vibroknife";
			this.ammo = 50;
			this.eqiupment = "E-5S, 14lbs(16lbs loaded), Hands[12x1xN/A].\n4 Baktoid Armor Workshop S11, 8lbs, Belt Magnitized[1x1x0.5].\nNightview Macrobinoculars, 2lbs, Belt Magnitized[1x1x0.3].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 46;
			this.magnification = 24;
			
			inventory.addItems(ItemType.E5S, 1);
			inventory.addItems(ItemType.E5S, ItemType.SmallArmsAmmo, 4);
			
			// Autorifleman
		} else if (input.equals("B1 Autorifleman")) {
			// Sets name
			this.name = "B1";

			// Creates attributes
			this.str = 12;
			this.wit = 10;
			this.soc = 5;
			this.wil = 10;
			this.per = 10;
			this.hlt = 10;
			this.agi = 12;

			this.rank = "Battledroid";
			this.designation = "Autorifleman";
			this.vet = "";
			this.wep = "E5C";
			this.meleeWep = "Vibroknife";
			this.ammo = 180;
			this.eqiupment = "E-5C, 23lbs(29lbs loaded), Hands[12x1xN/A].\n2 Baktoid Armor Workshop S11, 8lbs, Belt Magnitized[1x1x0.5].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 57;

			inventory.addItems(ItemType.E5C, 1);
			inventory.addItems(ItemType.E5C, ItemType.SmallArmsAmmo, 2);
			
			// Assistant Autorifleman
		} else if (input.equals("B1 Assistant Autorifleman")) {
			// Sets name
			this.name = "B1";

			// Creates attributes
			this.str = 12;
			this.wit = 10;
			this.soc = 5;
			this.wil = 10;
			this.per = 10;
			this.hlt = 10;
			this.agi = 12;

			this.rank = "Battledroid";
			this.designation = "Assistant Autorifleman";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n4 Baktoid Armor Workshop D11, 16lbs, Belt Magnitized[1x1x0.5].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 50;

			
			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 1);
			inventory.addItems(ItemType.E5C, ItemType.SmallArmsAmmo, 4);
			
			// AT Specalist
		} else if (input.equals("B1 AT Specialist")) {
			// Sets name
			this.name = "B1";

			// Creates attributes
			this.str = 12;
			this.wit = 10;
			this.soc = 5;
			this.wil = 10;
			this.per = 10;
			this.hlt = 10;
			this.agi = 12;

			this.rank = "Battledroid";
			this.designation = "AT Specalist";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n2 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\nRPS6, 20lbs, Sling on Back[15x2x1] HEAA, 5lbs, Clipped On Back[4x0.75x0.75].\nHEDP, 5lbs, Clipped On Back[4x0.75x0.75].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 64;

			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 1);
			inventory.addItems(ItemType.RPS6, 1);
			inventory.addItems(ItemType.RPS6, ItemType.HE, 1);
			inventory.addItems(ItemType.RPS6, ItemType.HEAT, 1);
			
			// AT Assistant
		} else if (input.equals("B1 Assistant AT Specialist")) {
			// Sets name
			this.name = "B1";

			// Creates attributes
			this.str = 12;
			this.wit = 10;
			this.soc = 5;
			this.wil = 10;
			this.per = 10;
			this.hlt = 10;
			this.agi = 12;

			this.rank = "Battledroid";
			this.designation = "Assistant AT Specalist";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n2 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\nHEAA, 5lbs, Clipped On Back[4x0.75x0.75].\nHEDP, 5lbs, Clipped On Back[4x0.75x0.75].\nSelf, 20lbs.\nTrade Federation B1 Satchel, 1lbs.";
			this.accomodations = "";
			this.encumberance = 45;
			
			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 2);
			inventory.addItems(ItemType.RPS6, ItemType.HE, 2);
			inventory.addItems(ItemType.RPS6, ItemType.HEAT, 2);

		} else if (input.equals("B2")) {
			// Sets name
			this.name = "B2";

			// Creates attributes
			this.str = 14;
			this.wit = 5;
			this.soc = 5;
			this.wil = 10;
			this.per = 10;
			this.hlt = 14;
			this.agi = 13;

			this.rank = "Battledroid";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5+B2 Wrist Rocket(integrated, 2 HE, 2 HEAT), 8lbs(14lbs loaded), [N/A].\n2 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\nSelf, 80lbs.";
			this.accomodations = "";
			this.encumberance = 98;

			this.hp = 14;
			this.armorLegacy = 100;
			this.legArmor = 50;
			this.armArmor = 50;
			this.headArmor = 50;
			encumberanceModifier = -20;
			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 2);

		} else if (input.equals("Commando Droid Squad Leader")) {
			// Sets name
			this.name = "Commando Droid";

			// Creates attributes
			this.str = 14;
			this.wit = 12;
			this.soc = 8;
			this.wil = 12;
			this.per = 12;
			this.hlt = 12;
			this.agi = 14;

			this.rank = "Sergeant";
			this.designation = "Squad Leader";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n2 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\nNightview Macrobinoculars, 2lbs, Belt Magnitized[1x1x0.3].\nSelf, 20lbs.\n";
			this.accomodations = "";
			this.encumberance = 36;
			this.magnification = 24;
			
			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 2);
			
			// B1 Rifleman
		} else if (input.equals("Commando Droid Rifleman")) {
			// Sets name
			this.name = "Commando Droid";

			// Creates attributes
			this.str = 14;
			this.wit = 12;
			this.soc = 8;
			this.wil = 12;
			this.per = 12;
			this.hlt = 12;
			this.agi = 14;

			this.rank = "Commando Droid";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n2 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 34;

			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 2);
			
			// B1 Marksman
		} else if (input.equals("Commando Droid Ammo Bearer")) {
			// Sets name
			this.name = "Commando Droid";

			// Creates attributes
			this.str = 14;
			this.wit = 12;
			this.soc = 8;
			this.wil = 12;
			this.per = 12;
			this.hlt = 12;
			this.agi = 14;

			this.rank = "Commando Droid";
			this.designation = "Rifleman";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n8 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 34;
			
			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 9);

			// B1 Marksman
		} else if (input.equals("Commando Droid Marksman")) {
			// Sets name
			this.name = "Commando Droid";

			// Creates attributes
			this.str = 14;
			this.wit = 12;
			this.soc = 8;
			this.wil = 12;
			this.per = 12;
			this.hlt = 12;
			this.agi = 14;

			this.rank = "Commando Droid";
			this.designation = "Marksman";
			this.vet = "";
			this.wep = "E5S";
			this.meleeWep = "Vibroknife";
			this.ammo = 50;
			this.eqiupment = "E-5S, 14lbs(16lbs loaded), Hands[12x1xN/A].\n4 Baktoid Armor Workshop S11, 8lbs, Belt Magnitized[1x1x0.5].\nNightview Macrobinoculars, 2lbs, Belt Magnitized[1x1x0.3].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 46;
			this.magnification = 24;

			inventory.addItems(ItemType.E5S, 1);
			inventory.addItems(ItemType.E5S, ItemType.SmallArmsAmmo, 4);
			
			// Autorifleman
		} else if (input.equals("Commando Droid Autorifleman")) {
			// Sets name
			this.name = "Commando Droid";

			// Creates attributes
			this.str = 14;
			this.wit = 12;
			this.soc = 8;
			this.wil = 12;
			this.per = 12;
			this.hlt = 12;
			this.agi = 14;

			this.rank = "Commando Droid";
			this.designation = "Autorifleman";
			this.vet = "";
			this.wep = "E5C";
			this.meleeWep = "Vibroknife";
			this.ammo = 180;
			this.eqiupment = "E-5C, 23lbs(29lbs loaded), Hands[12x1xN/A].\n2 Baktoid Armor Workshop S11, 8lbs, Belt Magnitized[1x1x0.5].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 57;

			inventory.addItems(ItemType.E5C, 1);
			inventory.addItems(ItemType.E5C, ItemType.SmallArmsAmmo, 2);
			
			// Assistant Autorifleman
		} else if (input.equals("Commando Droid Assistant Autorifleman")) {
			// Sets name
			this.name = "Commando Droid";

			// Creates attributes
			this.str = 14;
			this.wit = 12;
			this.soc = 8;
			this.wil = 12;
			this.per = 12;
			this.hlt = 12;
			this.agi = 14;

			this.rank = "Commando Droid";
			this.designation = "Assistant Autorifleman";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n4 Baktoid Armor Workshop D11, 16lbs, Belt Magnitized[1x1x0.5].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 50;

			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 1);
			inventory.addItems(ItemType.E5C, ItemType.SmallArmsAmmo, 4);
			
			
			// AT Specalist
		} else if (input.equals("Commando Droid AT Specialist")) {
			// Sets name
			this.name = "Commando Droid";

			// Creates attributes
			this.str = 14;
			this.wit = 12;
			this.soc = 8;
			this.wil = 12;
			this.per = 12;
			this.hlt = 12;
			this.agi = 14;

			this.rank = "Commando Droid";
			this.designation = "AT Specalist";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n2 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\nRPS6, 20lbs, Sling on Back[15x2x1] HEAA, 5lbs, Clipped On Back[4x0.75x0.75].\nHEDP, 5lbs, Clipped On Back[4x0.75x0.75].\nSelf, 20lbs.";
			this.accomodations = "";
			this.encumberance = 64;

			
			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 1);
			inventory.addItems(ItemType.RPS6, 1);
			inventory.addItems(ItemType.RPS6, ItemType.HE, 1);
			inventory.addItems(ItemType.RPS6, ItemType.HEAT, 1);
			
			// AT Assistant
		} else if (input.equals("Commando Droid Assistant AT Specialist")) {
			// Sets name
			this.name = "Commando Droid";

			// Creates attributes
			this.str = 14;
			this.wit = 12;
			this.soc = 8;
			this.wil = 12;
			this.per = 12;
			this.hlt = 12;
			this.agi = 14;

			this.rank = "Commando Droid";
			this.designation = "Assistant AT Specalist";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Vibroknife";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\n2 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\nHEAA, 5lbs, Clipped On Back[4x0.75x0.75].\nHEDP, 5lbs, Clipped On Back[4x0.75x0.75].\nSelf, 20lbs.\nTrade Federation B1 Satchel, 1lbs.";
			this.accomodations = "";
			this.encumberance = 45;
			
			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 2);
			inventory.addItems(ItemType.RPS6, ItemType.HE, 2);
			inventory.addItems(ItemType.RPS6, ItemType.HEAT, 2);

		} else if (input.equals("Magma Guard")) {
			// Sets name
			this.name = "Magma Guard";

			// Creates attributes
			this.str = 16;
			this.wit = 13;
			this.soc = 8;
			this.wil = 12;
			this.per = 12;
			this.hlt = 13;
			this.agi = 16;

			this.rank = "Magma Guard";
			this.designation = "Magma Guard";
			this.vet = "";
			this.wep = "E5";
			this.meleeWep = "Electrostaff";
			this.ammo = 150;
			this.eqiupment = "E-5, 8lbs(10lbs loaded), Hands[10x1xN/A].\\n2 Baktoid Armor Workshop S11, 4lbs, Belt Magnitized[1x1x0.5].\\nSelf, 30lbs.";
			this.accomodations = "";
			this.encumberance = 55;

			inventory.addItems(ItemType.E5, 1);
			inventory.addItems(ItemType.E5, ItemType.SmallArmsAmmo, 2);
			
		}

		if (this.name.equals("B1")) {
			this.KO = 150;
			this.armor = new Armor();
			this.armor.b1Armor();
		} else if (this.name.equals("B2")) {
			this.KO = 250;
			this.armor = new Armor();
			this.armor.b2Armor();
		} else if (this.name.equals("Commando Droid")) {
			this.KO = 185;
			this.armor = new Armor();
			this.armor.commandoDroidArmor();
		} else if (this.name.equals("Magma Guard")) {
			this.KO = 215;
			this.armor = new Armor();
			this.armor.magmaGuard();
		}
		
		inventory.setEncumberance();
		
		// Creates skills
		// Stores attributes in an array for the skill attr parameter
		int attr[] = { str, wit, soc, wil, per, hlt, agi };
		skills(input, attr);

		// Create and set individual stats
		IndividualStats individual = new IndividualStats(this.combatActions, sal, skills.getSkill("Pistol").value, 
				skills.getSkill("Rifle").value, 
				skills.getSkill("Launcher").value, 
				skills.getSkill("Heavy").value,
				skills.getSkill("Subgun").value, true);
		
		this.P1 = individual.P1;
		this.P2 = individual.P2;

		// Sets identifier
		this.identifier = identifier();
		// Sets HD
		this.HD = false;
		// Sets current HP
		this.currentHP = this.hp;

		

		if (this.str <= 6) {
			this.carryingCapacity = 40;
		} else if (this.str <= 10) {
			this.carryingCapacity = 60;
		} else if (this.str == 11) {
			this.carryingCapacity = 70;
		} else if (this.str <= 13) {
			this.carryingCapacity = 75;
		} else if (this.str <= 16) {
			this.carryingCapacity = 100;
		} else {
			this.carryingCapacity = 120;
		}

	}

	// Uses attr array and input number to generate skills
	// Sets class values of skills
	public void skills(String input, int attr[]) {
		Skills skillsList = null;
		try {
			skillsList = new Skills(input, attr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.skills = skillsList;
	}

	// Create PC stats
	public void setPCStats() {
		// Create PC stats
		new PCStats(this);
	}

	public void generateStats(int str, int wit, int soc, int wil, int per, int hlt, int agi) {
		TLHStats attributes = new TLHStats(str, wit, soc, wil, per, hlt, agi);
		this.str = attributes.str;
		this.wit = attributes.wit;
		this.soc = attributes.soc;
		this.wil = attributes.wil;
		this.per = attributes.per;
		this.hlt = attributes.hlt;
		this.agi = attributes.agi;
	}

	// Checks current HP level
	// Tests if the targetis killed , or if the target is incapacitated
	public void injuredHp(int damage, String hitLocation, ConflictLog log, Game game) {
		Random rand = new Random();
		int TN = skills.getSkill("Endurance").value;

		// Perform check for consciousness
		if (currentHP < hp) {

			int roll = rand.nextInt(100) + 1;

			if (roll > TN) {

				if (mortallyWounded) {
					alive = false;
					log.addToLine(":: Death");
				} else {
					conscious = false;
					log.addToLine(":: Unconscious");
				}

			}

		}

		// Performs death test
		if (currentHP < -1 * hp) {
			int roll = rand.nextInt(100) + 1;

			if (roll > TN) {

				if (mortallyWounded) {
					alive = false;
					log.addToLine(":: Death");
				} else if (roll > TN + 10) {
					alive = false;
					log.addToLine(":: Death");
				} else {
					conscious = false;
					log.addToLine(":: Unconscious");
					mortallyWounded = true;
					log.addToLine(":: Mortaly Wounded");
				}

			}
		}

		if (alive) {
			// Checks if out right killed
			if (currentHP <= -5 * hp) {
				int roll = rand.nextInt(100) + 1;

				if (roll > TN) {
					if (mortallyWounded) {
						alive = false;
						log.addToLine(":: Death");
					} else if (roll > TN + 10) {
						alive = false;
						log.addToLine(":: Death");
					} else {
						conscious = false;
						log.addToLine(":: Unconscious");
						mortallyWounded = true;
						log.addToLine(":: Mortaly Wounded");
					}
				}

				// Checks for major wound
			} else if (damage >= hp / 2) {

				if (hitLocation.equals("arms")) {
					if (disabledArms < arms) {
						disabledArms++;
						log.addToLine(":: Disabled Arm");
					}

				} else if (hitLocation.equals("legs")) {
					if (disabledLegs < legs) {
						disabledLegs++;
						log.addToLine(":: Disabled Leg");
					}
				}

				log.addToLine(":: Major Wound");

				int roll = rand.nextInt(100) + 1;

				// Stunning causes the character to lose one AP
				if (roll > TN) {
					log.addToLine(":: Stunned");

					if (game.getPhase() == 1) {
						if (spentPhase1 < game.getCurrentAction() && game.getCurrentAction() <= P1) {
							spentPhase1++;
						}
					} else {
						if (spentPhase2 < game.getCurrentAction() && game.getCurrentAction() <= P2) {
							spentPhase2++;
						}
					}
				}

			}
		}

	}

	// method called when trooper is outright killed from an injury
	public void dead(Unit returnedTrooperUnit, ConflictLog log) {
		alive = false;
		if (log != null)
			log.addToLineInQueue(":: Dead");

		// Apply death
		int unitSize = returnedTrooperUnit.getSize();
		int moraleLoss = 100 / unitSize;
		if (returnedTrooperUnit.organization - 5 < 1) {
			returnedTrooperUnit.organization = 0;
		} else {
			returnedTrooperUnit.organization -= 5;
		}

		if (returnedTrooperUnit.moral - (moraleLoss / 2) <= 0) {
			returnedTrooperUnit.moral = 0;
		} else {
			returnedTrooperUnit.moral -= moraleLoss / 2;
		}

	}

	// Called whenever a trooper is injured
	// Tests if it is outright killed
	// Rolls incapacitaiton test
	public void injured(ConflictLog log, Injuries injury, Game game, GameWindow gameWindow) {

		physicalDamage = physicalDamage + injury.pd;
		injuries.add(injury);

		/*
		 * Zones that have disabled: Mouth, Neck Spine, Shoulder, Arm Flesh, Arm Bone,
		 * Elbow, Forearm Flesh, Forearm Bone, Hand, Base of Neck, Heart, Liver - Spine,
		 * Spine, Thigh Flesh, Thigh Bone, Knee, Shin Flesh, Shin Bone, Ankle - Foot
		 */

	
		
		log.addToLineInQueue(", " + injury.location + ", injury pd: " + injury.pd + ", total pd: " + physicalDamage);

		if (injury.disabled) {

			if (injury.location.equals("Neck Spine") || injury.location.equals("Liver - Spine")
					|| injury.location.equals("Spine")) {
				log.addToLineInQueue(":: Spine Disabled");
				disabledArms = arms;
				disabledLegs = legs;
				conscious = false;
			} else if (injury.location.equals("Heart")) {
				conscious = false;
				alive = false;
				log.addToLineInQueue(":: Heart Disabled");
			} else if (injury.location.equals("Shoulder") || injury.location.equals("Arm Flesh")
					|| injury.location.equals("Arm Bone") || injury.location.equals("Elbow")
					|| injury.location.equals("Forearm Flesh") || injury.location.equals("Forearm Bone")
					|| injury.location.equals("Hand")) {
				disabledArms++;
				log.addToLineInQueue(":: Disabled Arm");
			} else if (injury.location.equals("Thigh Flesh") || injury.location.equals("Thigh Bone")
					|| injury.location.equals("Knee") || injury.location.equals("Shin Flesh")
					|| injury.location.equals("Shin Bone") || injury.location.equals("Ankle - Foot")) {
				disabledLegs++;
				log.addToLineInQueue(":: Disabled Leg");
			}

		}

		Random rand = new Random();
		int TN = 0;

		// Rolls incapacitation test
		if (physicalDamage > KO * 5) {
			TN = 60;
			P2--;
		} else if (physicalDamage > KO * 4) {
			TN = 26;
		} else if (physicalDamage > KO * 3) {
			TN = 13;
		} else if (physicalDamage > KO * 2) {
			TN = 12;
		} else {
			TN = 0;
		}

		if (physicalDamage >= KO) {
			log.addToLineInQueue(":: Stunned");

			if (game.getPhase() == 1) {
				if (spentPhase1 < game.getCurrentAction() && game.getCurrentAction() <= P1) {
					spentPhase1++;
				}
			} else {
				if (spentPhase2 < game.getCurrentAction() && game.getCurrentAction() <= P2) {
					spentPhase2++;
				}
			}
		}

		int roll = rand.nextInt(100) + 1;

		Unit returnedTrooperUnit = returnTrooperUnit(gameWindow);

		if (physicalDamage >= 12000) {
			dead(returnedTrooperUnit, log);

			// Structural integrity rules
			// See SW - CFFW
		} else if (entirelyMechanical) {

			if (injury.pd > KO * 2) {

				if (physicalDamage >= KO * 5) {
					if (roll <= 33)
						dead(returnedTrooperUnit, log);
				} else if (physicalDamage >= KO * 10) {
					if (roll <= 88)
						dead(returnedTrooperUnit, log);
				} else if (physicalDamage >= KO * 15) {
					if (roll <= 95)
						dead(returnedTrooperUnit, log);
				}
				incapacitationTime = 4320;

			}

		} else if (roll < TN) {
			conscious = false;
			log.addToLineInQueue(":: Unconscious");
			// Apply death
			int unitSize = returnedTrooperUnit.getSize();
			int moraleLoss = 100 / unitSize;
			if (returnedTrooperUnit.organization - 5 < 1) {
				returnedTrooperUnit.organization = 0;
			} else {
				returnedTrooperUnit.organization -= 5;
			}

			if (returnedTrooperUnit.moral - (moraleLoss / 2) <= 0) {
				returnedTrooperUnit.moral = 0;
			} else {
				returnedTrooperUnit.moral -= moraleLoss / 2;
			}

			// Sets incapacitation time
			FileInputStream excelFile;
			try {
				excelFile = new FileInputStream(new File(path + "incapacitationtime.xlsx"));
				Workbook workbook = new XSSFWorkbook(excelFile);
				org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);

				int randomNumber = rand.nextInt(10);
				// System.out.println("RNd10: "+randomNumber);
				int tempPD = physicalDamage;
				if (tempPD > 1000)
					tempPD = 1000;

				int pdRow = -1;
				for (int i = 2; i < 11; i++) {

					if (tempPD <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
						pdRow = i;
						break;
					}

				}

				// System.out.println("pdRow: "+pdRow);
				int col = -1;

				for (Cell cell : worksheet.getRow(1)) {
					if (cell.getCellType() == CellType.NUMERIC) {
						if (randomNumber <= cell.getNumericCellValue()) {
							col = cell.getColumnIndex();
							break;
						}
					}

				}

				// System.out.println("col: "+col);
				Cell cell = worksheet.getRow(pdRow).getCell(col);
				String value = cell.getStringCellValue();
				int timeInteger = 0;

				if (value.length() == 2) {

					timeInteger = Integer.parseInt(value.substring(0, 1));

					if (value.charAt(1) == 'p') {
						timeInteger = timeInteger / 10;
					} else if (value.charAt(1) == 'm') {
						timeInteger = timeInteger * 3;
					} else if (value.charAt(1) == 'h') {
						timeInteger = timeInteger * 180;
					} else if (value.charAt(1) == 'd') {
						timeInteger = timeInteger * 4320;
					}

				} else {
					timeInteger = Integer.parseInt(value.substring(0, 2));

					if (value.charAt(2) == 'p') {
						timeInteger = timeInteger / 10;
					} else if (value.charAt(1) == 'm') {
						timeInteger = timeInteger * 3;
					} else if (value.charAt(1) == 'h') {
						timeInteger = timeInteger * 180;
					} else if (value.charAt(1) == 'd') {
						timeInteger = timeInteger * 4320;
					}
				}

				incapacitationTime = timeInteger;

				excelFile.close();
				workbook.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			// No moral or organization loss from being mechanical

			int unitSize = returnedTrooperUnit.getSize();
			int moraleLoss = 100 / unitSize;
			if (returnedTrooperUnit.organization - 1 < 1) {
				returnedTrooperUnit.organization = 0;
			} else {
				returnedTrooperUnit.organization -= 1;
			}

			if (returnedTrooperUnit.moral - moraleLoss / 5 < 1) {
				returnedTrooperUnit.moral = 0;
			} else {
				returnedTrooperUnit.moral -= moraleLoss / 5;
			}
		}

	}

	public void stunned(Game game, ConflictLog log) {
		log.addToLineInQueue(":: Stunned");

		if (game.getPhase() == 1) {
			if (spentPhase1 < game.getCurrentAction() && game.getCurrentAction() <= P1) {
				spentPhase1++;
			}
		} else {
			if (spentPhase2 < game.getCurrentAction() && game.getCurrentAction() <= P2) {
				spentPhase2++;
			}
		}
	}

	public void removeInjury(int index, ConflictLog log, GameWindow game) {

		physicalDamage -= injuries.get(index).pd;

		injuries.remove(index);

		calculateInjury(game, log);

	}

	public void calculateInjury(GameWindow game, ConflictLog log) {
		if (physicalDamage <= 0) {

			criticalTime = 0;
			recoveryRoll = 0;
			timePassed = 0;
			return;
		} else if (physicalDamage > 16000) {
			physicalDamage = 16000;
			criticalTime = 0;
			recoveryRoll = -1;
			recoveryRollMod = 0;
			alive = false; 
			conscious = false; 
			advanceTime(game, log);
			return;
		}

		try {
			// System.out.println("Path: "+path);
			FileInputStream excelFile = new FileInputStream(new File(path + "aid.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);

			int woundRow = 0;
			woundRow -= aidMod;
			for (int i = 2; i < 41; i++) {
				if (physicalDamage <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					woundRow += i;
					break;
				}
			}

			if (woundRow < 2) {
				woundRow = 2;
			}

			// System.out.println("WR: "+woundRow+" PD: "+physicalDamage);
			if (recivingFirstAid) {
				recoveryRoll = (int) worksheet.getRow(woundRow).getCell(4).getNumericCellValue() + recoveryRollMod;

				String ctp = worksheet.getRow(woundRow).getCell(3).getStringCellValue();
				// System.out.println("CTP: "+ctp);

				if (ctp.length() == 2) {

					int value = Character.getNumericValue(ctp.charAt(0));

					if (ctp.charAt(1) == 'h') {
						value = value * 120;
					} else if (ctp.charAt(1) == 'm') {
						value = value * 2;
					} else if (ctp.charAt(1) == 'p') {
						value = value * 2;
						value = value / 30;
					} else if (ctp.charAt(1) == 'd') {
						value = value * 2880;
					}

					if (value <= 0)
						advanceTime(game, log);
					// System.out.println("CTP Value: "+value);
					criticalTime = value;

				} else {
					int value = Integer.parseInt(ctp.substring(0, 2));

					if (ctp.charAt(2) == 'h') {
						value = value * 180;
					} else if (ctp.charAt(2) == 'm') {
						value = value * 3;
					} else if (ctp.charAt(2) == 'p') {
						value = value / 10;
					} else if (ctp.charAt(2) == 'd') {
						value = value * 4320;
					}
					// System.out.println("CTP Value: "+value);
					criticalTime = value;
				}

			} else {

				recoveryRoll = (int) worksheet.getRow(woundRow).getCell(2).getNumericCellValue() + recoveryRollMod;

				String ctp = worksheet.getRow(woundRow).getCell(1).getStringCellValue();
				// System.out.println("CTP No First Aid: "+ctp);

				if (ctp.length() == 2) {

					int value = Character.getNumericValue(ctp.charAt(0));

					if (ctp.charAt(1) == 'h') {
						value = value * 180;
					} else if (ctp.charAt(1) == 'm') {
						value = value * 3;
					} else if (ctp.charAt(1) == 'p') {
						value = value / 10;
					} else if (ctp.charAt(1) == 'd') {
						value = value * 4320;
					}
					// System.out.println("CTP Value: "+value);
					criticalTime = value;

				} else {
					int value = Integer.parseInt(ctp.substring(0, 2));

					if (ctp.charAt(2) == 'h') {
						value = value * 180;
					} else if (ctp.charAt(2) == 'm') {
						value = value * 3;
					} else if (ctp.charAt(2) == 'p') {
						value = value / 10;
					} else if (ctp.charAt(2) == 'd') {
						value = value * 4320;
					}
					// System.out.println("CTP Value: "+value);
					criticalTime = value;
				}

			}

			excelFile.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void advanceTime(GameWindow game, ConflictLog log) {
		if (physicalDamage <= 0 || !alive)
			return;

		timePassed++;
		if (timePassed >= criticalTime && recoveryMade == false && !entirelyMechanical) {

			Random rand = new Random();

			int roll = rand.nextInt(100);

			if (roll - recoveryRollMod > recoveryRoll) {

				if (game != null) {
					Unit unit = returnTrooperUnit(game);

					// Apply death
					int unitSize = unit.getSize();
					int moraleLoss = 100 / unitSize;
					if (unit.organization - 5 < 1) {
						unit.organization = 0;
					} else {
						unit.organization -= 5;
					}

					if (unit.moral - (moraleLoss / 2) <= 0) {
						unit.moral = 0;
					} else {
						unit.moral -= moraleLoss / 2;
					}
				}

				if (log != null)
					log.addNewLineToQueue(
							number + " " + name + ": died to their wounds. RR: " + recoveryRoll + ", Roll: " + roll);

				alive = false;

			} else {
				if (log != null)
					log.addNewLineToQueue(
							number + " " + name + ": has stabilized. RR: " + recoveryRoll + ", Roll: " + roll);
				recoveryRoll = -1;
				criticalTime = -1;
				timePassed = -1;
				recoveryMade = true;
			}

		}

	}

	public String identifier() {
		int count = 10;
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	// Compares identifers of trooper
	public boolean compareTo(Trooper otherTrooper) {
		if (this.identifier.equals(otherTrooper.identifier)) {
			return true;
		} else {
			return false;
		}
	}

	// Loops through all spot actions, creates an arraylist that stores all targets
	// Returns an array list of individuals spotted by this trooper
	public ArrayList<Trooper> returnTargets() {
		ArrayList<Trooper> targets = new ArrayList<>();

		for (int i = 0; i < spotted.size(); i++) {

			for (Trooper targetTrooper : spotted.get(i).spottedIndividuals)
				targets.add(targetTrooper);

		}

		return targets;
	}

	// Returns a target string
	public String toTarget(GameWindow window) {
		return findTargetUnit(window) + ":: " + number + ":: " + name;
	}

	// Loops through units in initiaitive order
	// Looks for target unit
	public String findTargetUnit(GameWindow window) {

		String callsign = "Unknown";

		for (Unit unit : window.initiativeOrder) {

			for (Trooper trooper : unit.getTroopers()) {
				if (trooper.compareTo(this)) {
					callsign = unit.callsign;
				}
			}

		}

		return callsign;
	}

	public Unit returnTrooperUnit(GameWindow window) {

		// System.out.println("Conflict: weather: "+window.visibility);
		for (Unit unit : window.initiativeOrder) {

			for (Trooper trooper : unit.getTroopers()) {
				if (trooper.compareTo(this)) {
					return unit;
				}
			}

		}

		return window.initiativeOrder.get(0);
	}

	public void hunkerDown(GameWindow game) {

		spotted.clear();
		HD = true;

		for (Unit unit : game.initiativeOrder) {

			for (Trooper trooper : unit.individuals) {

				for (Spot spot : trooper.spotted) {

					if (spot.spottedIndividuals.contains(this)) {

						spot.spottedIndividuals.remove(this);

					}

				}

			}

		}

	}

	public boolean canAct(Game game) {

		boolean canAct = false;

		if (game.getPhase() == 1) {
			if (spentPhase1 < P1) {
				canAct = true;
			}
		} else {
			if (spentPhase2 < P2) {
				canAct = true;
			}
		}

		return canAct;

	}

	public boolean inBuilding(GameWindow gameWindow) {

		Unit unit = returnTrooperUnit(gameWindow);
		Hex hex = gameWindow.findHex(unit.X, unit.Y);

		if (hex == null)
			return false;

		if (hex.getTrooperBuilding(this) != null) {
			return true;
		}

		return false;
	}

	public Building getBuilding() {

		Unit unit = returnTrooperUnit(GameWindow.gameWindow);
		Hex hex = GameWindow.gameWindow.findHex(unit.X, unit.Y);

		if (hex == null)
			return null;

		if (hex.getTrooperBuilding(this) != null) {
			return hex.getTrooperBuilding(this);
		}

		return null;

	}

	public String toStringEquipped() {
		String trooper = "";

		trooper += number + ";";

		trooper += " EQUIPPED::";

		if (disabledLegs > 1) {
			trooper += " IMOBALIZED::";
		} else if (disabledLegs > 0) {
			trooper += " CRIP-LEG::";
		}

		if (!conscious) {
			trooper += " UNCONSCIOUS::";
		}

		trooper += "PD: " + physicalDamage + "; P1: " + spentPhase1 + "/" + P1 + "; P2: " + spentPhase2 + "/" + P2
				+ "::   " + name + "; Rank: " + rank;

		trooper += "; Role: " + designation + "; Rifle: " + skills.getSkill("Rifle").value + "; Heavy: " + skills.getSkill("Heavy").value + "; Command:" + skills.getSkill("Command").value
				+ "; Weapon: " + wep + "; Ammo: " + ammo;

		return trooper;
	}

	public void setCombatStats(Trooper individual) {
		if(maximumSpeed == null) {
			baseSpeed = new BaseSpeed(this);
			maximumSpeed = new MaximumSpeed(this);
			fatigueSystem = new FatigueSystem(this);
		}
 		
		new PCStats(individual);
		individual.setCombatActions(combatActions);

	}

	public void setCombatActions(int combatActions) {
		this.combatActions = combatActions;
		int actionPoints = 0;
		if (combatActions <= 1) {
			actionPoints = 2;
		} else if (combatActions <= 2) {
			actionPoints = 2;
		} else if (combatActions <= 3) {
			actionPoints = 3;
		} else if (combatActions <= 4) {
			actionPoints = 4;
		} else if (combatActions <= 5) {
			actionPoints = 4;
		} else if (combatActions <= 6) {
			actionPoints = 4;
		} else if (combatActions <= 7) {
			actionPoints = 5;
		} else if (combatActions <= 8) {
			actionPoints = 6;
		} else if (combatActions <= 9) {
			actionPoints = 8;
		} else if (combatActions <= 10) {
			actionPoints = 10;
		} else {
			actionPoints = 10;
		}

		if (actionPoints % 2 == 0) {
			this.P1 = actionPoints / 2;
			this.P2 = actionPoints / 2;
		} else {
			this.P1 = actionPoints / 2;
			this.P2 = actionPoints / 2 + 1;
		}

		adaptabilityFactor = 1 + (skills.getSkill("Fighter").value / 10 / 2);

	}

	public String toStringImproved(Game game) {
		String rslt = "";

		rslt += number + "; " + name + " ";
		rslt += "Role: " + designation + " ";
		if (game != null) {
			if (game.getPhase() == 1) {
				if (spentPhase1 >= P1 || spentPhase1 >= game.getCurrentAction())
					rslt = "Exhausted: " + rslt;
			} else {
				if (spentPhase2 >= P2 || spentPhase2 >= game.getCurrentAction())
					rslt = "Exhausted: " + rslt;
			}
		}

		if (!alive) {
			rslt += "DEAD: ";
		} else if (!conscious) {
			rslt += "UNCONSCIOUS: ";
		}
		
		if (HD) {
			rslt += "HUNKERED DOWN: ";
		}

		if (inCover) {
			rslt += "IN COVER: ";
		}
		
		if (disabledLegs > 1) {
			rslt += "IMOBALIZED: ";
		} else if (disabledLegs > 0) {
			rslt += "CRIP-LEG: ";
		}

		if (personalShield != null)
			rslt += "CSS: " + personalShield.currentShieldStrength + " ";

		if (physicalDamage > 0)
			rslt += "PD: " + physicalDamage + ", ";

		if (ionDamage > 0)
			rslt += "ID: " + ionDamage + ", ";

		rslt += "P1: " + spentPhase1 + "/" + P1 + ", P2: " + spentPhase2 + "/" + P2 + " ";
		// rslt += "CA: "+spentCA + "/" + CA + ", ";

		ArrayList<Trooper> spottedTroopers = new ArrayList<>();

		for (Spot spot : spotted) {

			for (Trooper spottedTrooper : spot.spottedIndividuals) {
				if (!spottedTrooper.HD && spottedTrooper.alive && spottedTrooper.conscious
						&& !spottedTroopers.contains(spottedTrooper))
					spottedTroopers.add(spottedTrooper);
			}

		}

		int spottedCount = spottedTroopers.size();

		rslt += "SC: " + spottedCount + ", ";
		rslt += weaponPercent + "%, SL: " + sl + ", ";
		rslt += "Ammo: " + ammo + ", ";
		rslt += "Weapon: " + wep;

		int average = skills.getSkill("Command").value + skills.getSkill("Fighter").value;
		
		rslt += " Command Avg/SL: "+(average/2)+"/"+PCUtility.getSL(average);
		
		return rslt;
	}

	public String toStringImprovedEquipped(Game game) {
		String rslt = "";

		rslt += number + "; " + name + " ";
		rslt += "Role: " + designation + " ";
		rslt += "EQUIPPED: ";
		if (game.getPhase() == 1) {
			if (spentPhase1 >= P1 || spentPhase1 >= game.getCurrentAction())
				rslt = "Exhausted: " + rslt;
		} else {
			if (spentPhase2 >= P2 || spentPhase2 >= game.getCurrentAction())
				rslt = "Exhausted: " + rslt;
		}

		if (HD) {
			rslt += "HUNKERED DOWN: ";
		}

		if (inCover) {
			rslt += "IN COVER: ";
		}

		if (disabledLegs > 1) {
			rslt += "IMOBALIZED: ";
		} else if (disabledLegs > 0) {
			rslt += "CRIP-LEG: ";
		}

		if (!alive) {
			rslt += "DEAD: ";
		}
		if (!conscious) {
			rslt += "UNCONSCIOUS: ";
		}

		if (personalShield != null)
			rslt += "CSS: " + personalShield.currentShieldStrength + " ";

		if (physicalDamage > 0)
			rslt += "PD: " + physicalDamage + ", ";

		if (ionDamage > 0)
			rslt += "ID: " + ionDamage + ", ";
		rslt += "P1: " + spentPhase1 + "/" + P1 + ", P2: " + spentPhase2 + "/" + P2 + " ";
		// rslt += "CA: "+spentCA + "/" + CA + ", ";

		ArrayList<Trooper> spottedTroopers = new ArrayList<>();

		for (Spot spot : spotted) {

			for (Trooper spottedTrooper : spot.spottedIndividuals) {
				if (!spottedTrooper.HD && spottedTrooper.alive && spottedTrooper.conscious
						&& !spottedTroopers.contains(spottedTrooper))
					spottedTroopers.add(spottedTrooper);
			}

		}

		int spottedCount = spottedTroopers.size();

		rslt += "SC: " + spottedCount + ", ";
		rslt += new Weapons().findWeapon(wep).type+": "+weaponPercent + "%, SL: " + sl + ", ";
		rslt += "Ammo: " + ammo + ", ";
		rslt += "Weapon: " + wep;

		return rslt;
	}
	
	public void applyHit(Weapons weapon, int distanceToTrooper) {
		
		System.out.println("Trooper Hit");
		
		GameWindow gameWindow = GameWindow.gameWindow;
		Trooper targetTrooper = this; 
		
		ResolveHits resolveHits = new ResolveHits(targetTrooper, 1, weapon, 
				gameWindow.conflictLog, targetTrooper.returnTrooperUnit(gameWindow), null, gameWindow);
		resolveHits.distanceToTarget = distanceToTrooper;

		if (targetTrooper.returnTrooperUnit(gameWindow).suppression + 1 < 100) {
			targetTrooper.returnTrooperUnit(gameWindow).suppression++;
		} else {
			targetTrooper.returnTrooperUnit(gameWindow).suppression = 100;
		}
		if (targetTrooper.returnTrooperUnit(gameWindow).organization - 1 > 0) {
			targetTrooper.returnTrooperUnit(gameWindow).organization--;
		} else {
			targetTrooper.returnTrooperUnit(gameWindow).organization = 0;
		}
		
		resolveHits.performCalculations(gameWindow.game, gameWindow.conflictLog);
		calculateInjury(gameWindow, gameWindow.conflictLog);
	}

	// Sets the basic stats for a newly created character
	public void setBasicStats() {

		this.characterPointTotal = 0;
		this.spentCharacterPoints = 0;

	}

	// Calculates and sets the value of all attributes
	public void calculateAttributes() {

		this.str = rolld3d4DropOne();
		this.wit = rolld3d4DropOne();
		this.wis = rolld3d4DropOne();
		this.soc = rolld3d4DropOne();
		this.wil = rolld3d4DropOne();
		this.per = rolld3d4DropOne();
		this.hlt = rolld3d4DropOne();
		this.agi = rolld3d4DropOne();

	}
	
	public void calculateSkills() {
		skills = new Skills(this);
	}
	
	public void recalculateSkills() {
		
		ArrayList<Skill> recalculatedSkills = new ArrayList<>();
		
		for(Skill skill : skills.skills) {
			int attr1 = getAttribute(skill.baseAttribute);
			int attr2 = getAttribute(skill.supportingAttribute);

			Skill newSkill = new Skill(skill.name, skill.rank, skill.value, attr1, attr2, skill.baseAttribute,
					skill.supportingAttribute, skill.supported, skill.trainingValue, skill.increasedValue, skill.type);
		
			recalculatedSkills.add(newSkill);
		}
		
		skills.skills = recalculatedSkills;  
		
	}
	
	// Returns attribute value from attribute name
	public int getAttribute(String attr) {

		attr = attr.toLowerCase();

		if (attr.equals("str")) {
			return str;
		} else if (attr.equals("int")) {
			return wit;
		} else if (attr.equals("wis")) {
			return wis;
		} else if (attr.equals("soc")) {
			return soc;
		} else if (attr.equals("wil")) {
			return wil;
		} else if (attr.equals("per")) {
			return per;
		} else if (attr.equals("htl")) {
			return hlt;
		} else if (attr.equals("agl")) {
			return agi;
		} else {
			return -1;
		}

	}
	
	public int getSkill(String skillName) {
		return skills.getSkill(skillName).value;
	}
	
	// Calculates stats and returns a string
	public String exportStats(int encum) {

		int slPistol = (getSkill("Pistol") / 10) % 10 + (getSkill("Fighter") / 10) % 10;
		int slRifle = (getSkill("Rifle") / 10) % 10 + (getSkill("Fighter") / 10) % 10;
		int slBow = (getSkill("Bow") + getSkill("Fighter")) / 12; // 0
		//int slCrossbow = (getSkill("Crossbow") + getSkill("Fighter")) / 12; // 21

		int fighterTensPlace = (getSkill("Fighter") / 10) % 10;
		int slMelee = fighterTensPlace + (getSkill("Dodge") / 10) % 10;

		int AF = 1 + fighterTensPlace / 2;

		double bSpeed = TrooperUtility.baseSpeed(encum, this);
		double mSpeed = TrooperUtility.maximumSpeed(encum, this);
		int meleeISF = slMelee + (wit / 3);
		int rifleISF = slRifle + (wit / 3);
		int pistolISF = slPistol + (wit / 3);
		int bowISF = slBow + (wit / 3);
		//int crossbowISF = slCrossbow + (wit / 3);
		int dAlmMelee = TrooperUtility.defensiveALM(meleeISF);
		int dAlmPistol = TrooperUtility.defensiveALM(pistolISF);
		int dAlmRifle = TrooperUtility.defensiveALM(rifleISF);
		int dAlmBow = TrooperUtility.defensiveALM(bowISF);
		//int dAlmCrossbow = TrooperUtility.defensiveALM(crossbowISF);
		int meleeCA = TrooperUtility.calculateCA(mSpeed, meleeISF);
		int pistolCA = TrooperUtility.calculateCA(mSpeed, pistolISF);
		int rifleCA = TrooperUtility.calculateCA(mSpeed, rifleISF);
		int bowCA = TrooperUtility.calculateCA(mSpeed, bowISF);
		//int crossbowCA = TrooperUtility.calculateCA(mSpeed, crossbowISF);
		int KO = TrooperUtility.getKO(this);

		int leadershipSkillFactor = (getSkill("Command")) / 3 + (getSkill("Tactics") / 3);
		int stam = (getSkill("Endurance") / 10) % 10 + (wil / 10) % 10;
		int FPP = (getSkill("Endurance") / 10) % 10 + (hlt / 10) % 10;

		System.out.println("Melee SAL: " + slMelee);
		System.out.println("Pistol SAL: " + slPistol);
		System.out.println("Rifle SAL: " + slRifle);
		System.out.println("Bow SAL: " + slBow);
		//System.out.println("Crossbow SAL: " + slCrossbow);
		System.out.println("Melee ISF: " + meleeISF);
		System.out.println("Rifle ISF: " + rifleISF);
		System.out.println("Pistol ISF: " + pistolISF);
		System.out.println("Bow ISF: " + bowISF);
		//System.out.println("Crossbow ISF: " + crossbowISF);
		System.out.println("Base Speed: " + bSpeed);
		System.out.println("Maximum Speed: " + mSpeed);
		System.out.println("dAlmMelee: " + dAlmMelee);
		System.out.println("dAlmPistol: " + dAlmPistol);
		System.out.println("dAlmRifle: " + dAlmRifle);
		System.out.println("dAlmBow: " + dAlmBow);
		//System.out.println("dAlmCrossbow: " + dAlmCrossbow);
		System.out.println("Melee CA: " + meleeCA);
		System.out.println("Pistol CA: " + pistolCA);
		System.out.println("Rifle CA: " + rifleCA);
		System.out.println("Bow CA: " + bowCA);
		//System.out.println("Crossbow CA: " + crossbowCA);
		System.out.println("KO: " + KO);
		System.out.println("");
		System.out.println("AF: " + AF);
		System.out.println("Melee CoAC: " + AF * meleeCA);
		System.out.println("Pistol CoAC: " + AF * pistolCA);
		System.out.println("Rifle CoAC: " + AF * rifleCA);
		System.out.println("Bow CoAC: " + AF * bowCA);
		//System.out.println("Crossbow CoAC: " + AF * crossbowCA);
		System.out.println("Coolness Under Fire(CUF): " + (getSkill("Fighter") + getSkill("Composure")) / 2);
		System.out.println("Leadership Skill Factor(LSF): " + leadershipSkillFactor);
		System.out.println("Command Time(CT): " + TrooperUtility.calculateCT(leadershipSkillFactor));
		System.out.println(
				"AV(Analectic Value): " + (int) ((int) (getSkill("Endurance") / 3 * (wil / 3) / 2) * (bSpeed / 2)));
		/*
		 * System.out.println("Stamina(ST): " + stam);
		 * System.out.println("Fatigue Point Progress(FPP): " + FPP);
		 */

		System.out.println("");
		System.out.println("TROS");
		int x1 = per / 9;
		int x2 = agi / 9;
		int x3 = x1 + x2;
		int x4 = x3 / 2;
		System.out.println("Reflexes: " + x4);
		System.out.println("PROF: ");

		return "";
	}

	// Roll 4d6, pick three
	public int rolld3d4DropOne() {

		Random rand = new Random();
		int sum = 0;

		// Rolls 4 dice
		ArrayList<Integer> rolls = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			int roll = rand.nextInt(4) + 1;
			rolls.add(roll);
		}

		// Finds the lowest value
		/*
		 * int lowest = rolls.get(0); int lowestIndex = 0; for(int i = 0; i <
		 * rolls.size(); i++) { if(rolls.get(i) < lowest) { lowest = rolls.get(i);
		 * lowestIndex = i; } }
		 * 
		 * // Removes lowest value rolls.remove(lowestIndex);
		 */

		// Creates sum out of the remaining three
		for (int i = 0; i < rolls.size(); i++) {
			sum += rolls.get(i);
		}

		return sum + 4;
	}
	
	
	public String toStringCe() {
		String rslt = name + ": "; 
		
		if (!alive) {
			rslt += "DEAD: ";
		} else if (!conscious) {
			rslt += "UNCONSCIOUS: ";
		}
		
		if (HD) {
			rslt += "HUNKERED DOWN: ";
		}

		if (inCover) {
			rslt += "IN COVER: ";
		}
		
		if (disabledLegs > 1) {
			rslt += "IMOBALIZED: ";
		} else if (disabledLegs > 0) {
			rslt += "CRIP-LEG: ";
		}

		if (personalShield != null)
			rslt += "CSS: " + personalShield.currentShieldStrength + " ";

		if (physicalDamage > 0)
			rslt += "PD: " + physicalDamage + ", ";

		if (ionDamage > 0)
			rslt += "ID: " + ionDamage + ", ";
		
		ArrayList<Trooper> spottedTroopers = new ArrayList<>();

		for (Spot spot : spotted) {

			for (Trooper spottedTrooper : spot.spottedIndividuals) {
				if (!spottedTrooper.HD && spottedTrooper.alive && spottedTrooper.conscious
						&& !spottedTroopers.contains(spottedTrooper))
					spottedTroopers.add(spottedTrooper);
			}

		}

		int spottedCount = spottedTroopers.size();

		rslt += "SC: " + spottedCount + ", ";
		rslt += weaponPercent + "%, SL: " + sl + ", ";
		rslt += "Ammo: " + ammo + ", ";
		rslt += "Weapon: " + wep;

		int average = skills.getSkill("Command").value + skills.getSkill("Fighter").value;
		
		rslt += " Command Avg/SL: "+(average/2)+"/"+PCUtility.getSL(average);
		
		return rslt; 
	}
	
	// Takes parameters from trooper class and returns string
	@Override
	public String toString() {
		String trooper = "";

		trooper += number + ";";

		if (!conscious) {
			trooper += " UNCONSCIOUS::";
		} else if(!alive) {
			trooper += " DEAD::";
		}
		
		if (HD) {
			trooper += " HUNKERED DOWN; ";
		}

		if (inCover) {
			trooper += " IN COVER; ";
		}

		if (disabledLegs > 1) {
			trooper += " IMOBALIZED::";
		} else if (disabledLegs > 0) {
			trooper += " CRIP-LEG::";
		}

		

		trooper += "PD: " + physicalDamage + "; P1: " + spentPhase1 + "/" + P1 + "; P2: " + spentPhase2 + "/" + P2
				+ "::   " + name + "; Rank: " + rank;

		trooper += "; Role: " + designation + "; Rifle: " + skills.getSkill("Rifle").value + "; Heavy: " + skills.getSkill("Heavy").value + "; Command:" + skills.getSkill("Command").value
				+ "; Weapon: " + wep + "; Ammo: " + ammo;

		return trooper;
	}

	/*
	 * public int getPD() { int pd = 0;
	 * 
	 * for(Injuries injury : injuries) { pd += injury.pd; }
	 * 
	 * return pd; }
	 */

}