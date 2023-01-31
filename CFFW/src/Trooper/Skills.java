package Trooper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Skills implements Serializable {

	// Skills
	public ArrayList<Skill> skills = new ArrayList<Skill>();

	// Balance Climb Composure Dodge Endurance Expression Grapple Hold Jump/Leap
	// Lift/Pull Resist Pain Search Spot/Listen Stealth Calm Other Diplomacy Barter
	// Command Tactics Det. Motives Intimidate Persuade Digi. Systems Long Gun
	// Pistol Launcher Heavy Subgun Explosives First Aid Navigation Swim Throw
	// Skills
	public int ballance;
	public int bow;
	public int climb;
	public int composure;
	public int dodge;
	public int deception;
	public int endurance;
	public int expression;
	public int grapple;
	public int hold;
	public int intuition;
	public int investigation;
	public int jump;
	public int lift;
	public int resistPain;
	public int search;
	public int spotListen;
	public int speed;
	public int stealth;
	public int camouflage;
	public int calm;
	public int diplomacy;
	public int barter;
	public int command;
	public int tactics;
	public int detMotives;
	public int intimidate;
	public int persuade;
	public int digiSystems;
	public int pistol;
	public int heavy;
	public int subgun;
	public int launcher;
	public int rifle;
	public int explosives;
	public int advancedMedicine; // New
	public int firstAid;
	public int navigation;
	public int swim;
	public int Throw;

	/* CE Skills */
	public int cleanOperations;
	public int covertMovement;
	public int fighter;
	public int recoilControl;
	public int triggerDiscipline;
	public int reloadDrills;
	public int silentOperations;

	// BEAR
	public int akSystems;
	public int assualtOperations;
	public int authority;
	public int rawPower;

	// USEC
	public int arSystems;
	public int longRangeOptics;
	public int negotiations;
	public int smallUnitTactics;

	// Additional expert skills
	public int craft;
	public int pilot;
	public int animalHandling;
	public int ride;
	public int science;
	public int survival;

	// Attributes
	// Attributes
	public int str;
	public int wit;
	public int wis;
	public int soc;
	public int wil;
	public int per;
	public int htl;
	public int agi;

	// Attribute modifiers
	public int strMod;
	public int witMod;
	public int wisMod;
	public int socMod;
	public int wilMod;
	public int perMod;
	public int htlMod;
	public int agiMod;

	public Skills(Trooper trooper) {
		getAttr(trooper);
		calculateSkills();

		// Add skills to skills array
		skills.add(new Skill("Ballance", 0, ballance, "AGL", "wil", false, 0, 0, "Basic"));
		skills.add(new Skill("Camouflage", 0, camouflage, "INT", "per", false, 0, 0, "Basic"));
		skills.add(new Skill("Climb", 0, climb, "STR", "agl", false, 0, 0, "Basic"));
		skills.add(new Skill("Composure", 0, composure, "WIL", "htl", false, 0, 0, "Basic"));
		skills.add(new Skill("Deception", 0, deception, "SOC", "int", false, 0, 0, "Basic"));
		skills.add(new Skill("Dodge", 0, dodge, "AGL", "str", false, 0, 0, "Basic"));
		skills.add(new Skill("Endurance", 0, endurance, "STR", "wil", false, 0, 0, "Basic"));
		skills.add(new Skill("Expression", 0, expression, "HTL", "per", false, 0, 0, "Basic"));
		skills.add(new Skill("Grapple", 0, grapple, "STR", "agl", false, 0, 0, "Basic"));
		skills.add(new Skill("Hold", 0, hold, "STR", "agl", false, 0, 0, "Basic"));
		skills.add(new Skill("Intuition", 0, intuition, "WIL", "per", false, 0, 0, "Basic"));
		skills.add(new Skill("Jump/Leap", 0, jump, "STR", "per", false, 0, 0, "Basic"));
		skills.add(new Skill("Lift/Pull", 0, lift, "STR", "int", false, 0, 0, "Basic"));
		skills.add(new Skill("Resist Pain", 0, resistPain, "HTL", "wil", false, 0, 0, "Basic"));
		skills.add(new Skill("Search", 0, search, "PER", "int", false, 0, 0, "Basic"));
		skills.add(new Skill("Spot/Listen", 0, spotListen, "PER", "int", false, 0, 0, "Basic"));
		skills.add(new Skill("Speed", 0, speed, "AGL", "htl", false, 0, 0, "Basic"));
		skills.add(new Skill("Stealth", 0, stealth, "AGL", "int", false, 0, 0, "Basic"));

		skills.add(new Skill("Bow", 0, bow, "AGL", "str", false, 0, 0, "Trained"));
		skills.add(new Skill("Calm Other", 0, calm, "SOC", "int", false, 0, 0, "Trained"));
		skills.add(new Skill("Diplomacy", 0, diplomacy, "SOC", "int", false, 0, 0, "Trained"));
		skills.add(new Skill("Explosives", 0, explosives, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Barter", 0, barter, "SOC", "int", false, 0, 0, "Trained"));
		skills.add(new Skill("Command", 0, command, "INT", "soc", false, 0, 0, "Trained"));
		skills.add(new Skill("Tactics", 0, tactics, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Determine Motives", 0, detMotives, "SOC", "int", false, 0, 0, "Trained"));
		skills.add(new Skill("Intimidate", 0, intimidate, "SOC", "str", false, 0, 0, "Trained"));
		skills.add(new Skill("Investigation", 0, investigation, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Persuade", 0, persuade, "SOC", "int", false, 0, 0, "Trained"));
		skills.add(new Skill("Performance", 0, 0, soc, per, "SOC", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Writing", 0, 0, wit, soc, "INT", "soc", false, 0, 0, "Trained"));
		skills.add(new Skill("Digi. Systems", 0, digiSystems, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Rifle", 0, rifle, "AGL", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Heavy", 0, heavy, "AGL", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Subgun", 0, subgun, "AGL", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Launcher", 0, launcher, "AGL", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Pistol", 0, pistol, "AGL", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Cross Bow", 0, 0, agi, per, "AGL", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Fighter", 0, fighter, "STR", "wil", false, 0, 0, "Trained"));
		skills.add(new Skill("First Aid", 0, firstAid, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Navigation", 0, navigation, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Swim", 0, swim, "STR", "htl", false, 0, 0, "Trained"));
		skills.add(new Skill("Throw", 0, Throw, "AGL", "str", false, 0, 0, "Trained"));
		
		skills.add(new Skill("Knowledge Street Wise", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained"));
		skills.add(new Skill("Knowledge Political", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained"));
		skills.add(new Skill("Knowledge Nature", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained"));
		skills.add(new Skill("Knowledge Military", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained"));
		skills.add(new Skill("Knowledge Historical", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained"));
		skills.add(new Skill("Knowledge Folklore", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained"));
		skills.add(new Skill("Knowledge Bestial", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained"));
		skills.add(new Skill("Knowledge Magical", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained"));
		skills.add(new Skill("Knowledge Alchemical", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained"));
		

		skills.add(new Skill("Advanced Medicine", 0, advancedMedicine, "INT", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Craft/Construct/Engineer", 0, craft, "INT", "str", false, 0, 0, "Expert"));
		skills.add(new Skill("Pilot", 0, pilot, "AGL", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Animal Handling", 0, animalHandling, "SOC", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Ride", 0, ride, "AGL", "str", false, 0, 0, "Expert"));
		skills.add(new Skill("Science", 0, science, "INT", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Survival", 0, survival, "INT", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Clean Operations", 0, cleanOperations, "AGL", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Covert Movement", 0, covertMovement, "AGL", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Recoil Control", 0, recoilControl, "STR", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Reload Drills", 0, reloadDrills, "AGL", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Trigger Discipline", 0, silentOperations, "INT", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Silent Operations", 0, akSystems, "AGL", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("AK Systems", 0, akSystems, "AGL", "str", false, 0, 0, "Expert"));
		skills.add(new Skill("Assault Operations", 0, assualtOperations, "STR", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Authority", 0, authority, "STR", "soc", false, 0, 0, "Expert"));
		skills.add(new Skill("Raw Power", 0, rawPower, "STR", "htl", false, 0, 0, "Expert"));
		skills.add(new Skill("AR Systems", 0, arSystems, "AGL", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Long Range Optics", 0, longRangeOptics, "PER", "agl", false, 0, 0, "Expert"));
		skills.add(new Skill("Negotiations", 0, negotiations, "SOC", "int", false, 0, 0, "Expert"));
		skills.add(new Skill("Small Unit Tactics", 0, smallUnitTactics, "INT", "per", false, 0, 0, "Expert"));
		
		Skill preservation = new Skill("Preservation", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill abjuration = new Skill("Abjuration", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill alteration = new Skill("Alteration", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill conjuration = new Skill("Conjuration", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill divination = new Skill("Divination", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill enchantment = new Skill("Enchantment", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill illusion = new Skill("Illusion", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill invocation = new Skill("Invocation", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill planar = new Skill("Planar", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill necromancy = new Skill("Necromancy", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill wildmagic = new Skill("Wild Magic", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill elemental = new Skill("Elemental", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill enviromental = new Skill("Enviromental", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill psionics = new Skill("Psionics", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill runic = new Skill("Runic", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill alchemical = new Skill("Alchemical", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		/* Magic */
		skills.add(preservation);
		skills.add(abjuration);
		skills.add(alteration);
		skills.add(conjuration);
		skills.add(divination);
		skills.add(enchantment);
		skills.add(illusion);
		skills.add(invocation);
		skills.add(planar);
		skills.add(necromancy);
		skills.add(wildmagic);
		skills.add(elemental);
		skills.add(enviromental);
		skills.add(psionics);
		skills.add(runic);
		skills.add(alchemical);
	} 
	
	
	
	public Skills(String input, int attr[]) throws Exception {

		getAttr(attr);
		calculateSkills();

		// Add skills to skills array
		skills.add(new Skill("Ballance", 0, ballance, "AGL", "wil", false, 0, 0, "Basic"));
		skills.add(new Skill("Camouflage", 0, camouflage, "INT", "per", false, 0, 0, "Basic"));
		skills.add(new Skill("Climb", 0, climb, "STR", "agl", false, 0, 0, "Basic"));
		skills.add(new Skill("Composure", 0, composure, "WIL", "htl", false, 0, 0, "Basic"));
		skills.add(new Skill("Deception", 0, deception, "SOC", "int", false, 0, 0, "Basic"));
		skills.add(new Skill("Dodge", 0, dodge, "AGL", "str", false, 0, 0, "Basic"));
		skills.add(new Skill("Endurance", 0, endurance, "STR", "wil", false, 0, 0, "Basic"));
		skills.add(new Skill("Expression", 0, expression, "HTL", "per", false, 0, 0, "Basic"));
		skills.add(new Skill("Grapple", 0, grapple, "STR", "agl", false, 0, 0, "Basic"));
		skills.add(new Skill("Hold", 0, hold, "STR", "agl", false, 0, 0, "Basic"));
		skills.add(new Skill("Intuition", 0, intuition, "WIL", "per", false, 0, 0, "Basic"));
		skills.add(new Skill("Jump/Leap", 0, jump, "STR", "per", false, 0, 0, "Basic"));
		skills.add(new Skill("Lift/Pull", 0, lift, "STR", "int", false, 0, 0, "Basic"));
		skills.add(new Skill("Resist Pain", 0, resistPain, "HTL", "wil", false, 0, 0, "Basic"));
		skills.add(new Skill("Search", 0, search, "PER", "int", false, 0, 0, "Basic"));
		skills.add(new Skill("Spot/Listen", 0, spotListen, "PER", "int", false, 0, 0, "Basic"));
		skills.add(new Skill("Speed", 0, speed, "AGL", "htl", false, 0, 0, "Basic"));
		skills.add(new Skill("Stealth", 0, stealth, "AGL", "int", false, 0, 0, "Basic"));

		skills.add(new Skill("Bow", 0, bow, "AGL", "str", false, 0, 0, "Trained"));
		skills.add(new Skill("Calm Other", 0, calm, "SOC", "int", false, 0, 0, "Trained"));
		skills.add(new Skill("Diplomacy", 0, diplomacy, "SOC", "int", false, 0, 0, "Trained"));
		skills.add(new Skill("Explosives", 0, explosives, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Barter", 0, barter, "SOC", "int", false, 0, 0, "Trained"));
		skills.add(new Skill("Command", 0, command, "INT", "soc", false, 0, 0, "Trained"));
		skills.add(new Skill("Tactics", 0, tactics, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Determine Motives", 0, detMotives, "SOC", "int", false, 0, 0, "Trained"));
		skills.add(new Skill("Intimidate", 0, intimidate, "SOC", "str", false, 0, 0, "Trained"));
		skills.add(new Skill("Investigation", 0, investigation, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Persuade", 0, persuade, "SOC", "int", false, 0, 0, "Trained"));
		skills.add(new Skill("Digi. Systems", 0, digiSystems, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Rifle", 0, rifle, "AGL", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Heavy", 0, heavy, "AGL", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Subgun", 0, subgun, "AGL", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Launcher", 0, launcher, "AGL", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Pistol", 0, pistol, "AGL", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Fighter", 0, fighter, "STR", "wil", false, 0, 0, "Trained"));
		skills.add(new Skill("First Aid", 0, firstAid, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Navigation", 0, navigation, "INT", "per", false, 0, 0, "Trained"));
		skills.add(new Skill("Swim", 0, swim, "STR", "htl", false, 0, 0, "Trained"));
		skills.add(new Skill("Throw", 0, Throw, "AGL", "str", false, 0, 0, "Trained"));

		skills.add(new Skill("Advanced Medicine", 0, advancedMedicine, "INT", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Craft/Construct/Engineer", 0, craft, "INT", "str", false, 0, 0, "Expert"));
		skills.add(new Skill("Pilot", 0, pilot, "AGL", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Animal Handling", 0, animalHandling, "SOC", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Ride", 0, ride, "AGL", "str", false, 0, 0, "Expert"));
		skills.add(new Skill("Science", 0, science, "INT", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Survival", 0, survival, "INT", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Clean Operations", 0, cleanOperations, "AGL", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Covert Movement", 0, covertMovement, "AGL", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Recoil Control", 0, recoilControl, "STR", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Reload Drills", 0, reloadDrills, "AGL", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Trigger Discipline", 0, silentOperations, "INT", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Silent Operations", 0, akSystems, "AGL", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("AK Systems", 0, akSystems, "AGL", "str", false, 0, 0, "Expert"));
		skills.add(new Skill("Assault Operations", 0, assualtOperations, "STR", "wil", false, 0, 0, "Expert"));
		skills.add(new Skill("Authority", 0, authority, "STR", "soc", false, 0, 0, "Expert"));
		skills.add(new Skill("Raw Power", 0, rawPower, "STR", "htl", false, 0, 0, "Expert"));
		skills.add(new Skill("AR Systems", 0, arSystems, "AGL", "per", false, 0, 0, "Expert"));
		skills.add(new Skill("Long Range Optics", 0, longRangeOptics, "PER", "agl", false, 0, 0, "Expert"));
		skills.add(new Skill("Negotiations", 0, negotiations, "SOC", "int", false, 0, 0, "Expert"));
		skills.add(new Skill("Small Unit Tactics", 0, smallUnitTactics, "INT", "per", false, 0, 0, "Expert"));

				
		if (input.equals("Squad Leader")) { // Squad Leader
			line();
			lineSquadLeader();
		} else if (input.equals("Rifleman")) { // Rifleman
			line();
		} else if (input.equals("Rifleman++")) { // Rifleman++
			crack();
		} else if (input.equals("Autorifleman")) { // Autorifleman
			line();
			lineAutorifleman();
		} else if (input.equals("Assistant Autorifleman")) { // Assistant Autorifleman
			line();
			lineAutorifleman();
		} else if (input.equals("Ammo Bearer")) { // Ammo Bearer
			// Adds skill dice levels to the skills
			// Soldier Training
			line();
		} else if (input.equals("Marksman")) { // Marksman
			line();
			lineMarksman();
		} else if (input.equals("Combat Life Saver")) { // Combat Life Saver
			line();
			lineMedic();
		} else if (input.equals("AT Specialist")) { // AT Specialist
			line();
			lineAtSpecalist();

		} else if (input.equals("Assistant AT specialist")) { // Assistant AT specialist
			// Adds skill dice levels to the skills
			// Soldier Training
			line();
			lineAtSpecalist();

		} else if (input.equals("Clone Squad Leader")) { // Squad Leader
			crack();
			crackSquadLeader();
		} else if (input.equals("Clone Rifleman")) { // Rifleman
			crack();
		} else if (input.equals("Clone Rifleman++")) { // Rifleman++
			crack();
		} else if (input.equals("Clone Autorifleman")) { // Autorifleman
			crack();
			crackAutorifleman();
		} else if (input.equals("Clone Assistant Autorifleman")) { // Assistant Autorifleman
			crack();
			crackAutorifleman();
		} else if (input.equals("Clone Ammo Bearer")) { // Ammo Bearer
			// Adds skill dice levels to the skills
			// Soldier Training
			crack();
		} else if (input.equals("Clone Marksman")) { // Marksman
			crack();
			crackMarksman();
			//line();
			//lineMarksman();
			
		} else if (input.equals("Clone Combat Life Saver")) { // Combat Life Saver
			crack();
			crackMedic();
		} else if (input.equals("Clone AT Specialist")) { // AT Specialist
			crack();
			crackAtSpecalist();

		} else if (input.equals("Clone Assistant AT Specialist")) { // Assistant AT specialist
			// Adds skill dice levels to the skills
			// Soldier Training
			crack();
			crackAtSpecalist();

		} else if (input.equals("EOD")) { // EOD
			// Adds skill dice levels to the skills
			// Soldier Training
			getSkill("Climb").supported = true;
			getSkill("Climb").trainingValue = 2;
			getSkill("Climb").rankUp();

			getSkill("Dodge").supported = true;
			getSkill("Dodge").trainingValue = 2;
			getSkill("Dodge").rankUp();

			getSkill("Endurance").supported = true;
			getSkill("Endurance").trainingValue = 3;
			getSkill("Endurance").rankUp();
			getSkill("Endurance").rankUp();
			getSkill("Endurance").rankUp();

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").trainingValue = 2;
			getSkill("Spot/Listen").rankUp();
			getSkill("Spot/Listen").rankUp();
			getSkill("Spot/Listen").rankUp();

			getSkill("Stealth").supported = true;
			getSkill("Stealth").trainingValue = 2;
			getSkill("Stealth").rankUp();
			getSkill("Stealth").rankUp();

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").trainingValue = 2;
			getSkill("Camouflage").rankUp();
			getSkill("Camouflage").rankUp();

			getSkill("Rifle").supported = true;
			getSkill("Rifle").trainingValue = 4;
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();

			getSkill("Subgun").supported = true;
			getSkill("Subgun").trainingValue = 3;
			getSkill("Subgun").rankUp();
			getSkill("Subgun").rankUp();

			getSkill("Heavy").supported = true;
			getSkill("Heavy").trainingValue = 3;
			getSkill("Heavy").rankUp();
			getSkill("Heavy").rankUp();

			getSkill("Launcher").supported = true;
			getSkill("Launcher").trainingValue = 3;
			getSkill("Launcher").rankUp();
			getSkill("Launcher").rankUp();

			getSkill("Pistol").supported = true;
			getSkill("Pistol").trainingValue = 3;
			getSkill("Pistol").rankUp();
			getSkill("Pistol").rankUp();

			getSkill("Explosives").supported = true;
			getSkill("Explosives").trainingValue = 4;
			getSkill("Explosives").rankUp();
			getSkill("Explosives").rankUp();
			getSkill("Explosives").rankUp();

			getSkill("Fighter").supported = true;
			getSkill("Fighter").trainingValue = 4;
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();

			getSkill("First Aid").supported = true;
			getSkill("First Aid").trainingValue = 2;
			getSkill("First Aid").rankUp();
			getSkill("First Aid").rankUp();

			getSkill("Navigation").supported = true;
			getSkill("Navigation").trainingValue = 2;
			getSkill("Navigation").rankUp();

			getSkill("Throw").supported = true;
			getSkill("Throw").trainingValue = 2;
			getSkill("Throw").rankUp();
			getSkill("Throw").rankUp();

		} else if (input.equals("Ranger")) { // Ranger
			// Adds skill dice levels to the skills
			// Soldier Training
			getSkill("Climb").supported = true;
			getSkill("Climb").trainingValue = 2;
			getSkill("Climb").rankUp();

			getSkill("Dodge").supported = true;
			getSkill("Dodge").trainingValue = 3;
			getSkill("Dodge").rankUp();
			getSkill("Dodge").rankUp();
			getSkill("Dodge").rankUp();

			getSkill("Endurance").supported = true;
			getSkill("Endurance").trainingValue = 4;
			getSkill("Endurance").rankUp();
			getSkill("Endurance").rankUp();
			getSkill("Endurance").rankUp();

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").trainingValue = 4;
			getSkill("Spot/Listen").rankUp();
			getSkill("Spot/Listen").rankUp();
			getSkill("Spot/Listen").rankUp();

			getSkill("Stealth").supported = true;
			getSkill("Stealth").trainingValue = 4;
			getSkill("Stealth").rankUp();
			getSkill("Stealth").rankUp();
			getSkill("Stealth").rankUp();

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").trainingValue = 4;
			getSkill("Camouflage").rankUp();
			getSkill("Camouflage").rankUp();
			getSkill("Camouflage").rankUp();

			getSkill("Rifle").supported = true;
			getSkill("Rifle").trainingValue = 4;
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();

			getSkill("Subgun").supported = true;
			getSkill("Subgun").trainingValue = 3;
			getSkill("Subgun").rankUp();
			getSkill("Subgun").rankUp();

			getSkill("Heavy").supported = true;
			getSkill("Heavy").trainingValue = 3;
			getSkill("Heavy").rankUp();
			getSkill("Heavy").rankUp();

			getSkill("Launcher").supported = true;
			getSkill("Launcher").trainingValue = 3;
			getSkill("Launcher").rankUp();
			getSkill("Launcher").rankUp();

			getSkill("Pistol").supported = true;
			getSkill("Pistol").trainingValue = 3;
			getSkill("Pistol").rankUp();
			getSkill("Pistol").rankUp();

			getSkill("Explosives").supported = true;
			getSkill("Explosives").trainingValue = 4;
			getSkill("Explosives").rankUp();
			getSkill("Explosives").rankUp();
			getSkill("Explosives").rankUp();

			getSkill("Fighter").supported = true;
			getSkill("Fighter").trainingValue = 4;
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();

			getSkill("First Aid").supported = true;
			getSkill("First Aid").trainingValue = 2;
			getSkill("First Aid").rankUp();
			getSkill("First Aid").rankUp();

			getSkill("Navigation").supported = true;
			getSkill("Navigation").trainingValue = 2;
			getSkill("Navigation").rankUp();

			getSkill("Throw").supported = true;
			getSkill("Throw").trainingValue = 2;
			getSkill("Throw").rankUp();
			getSkill("Throw").rankUp();

		} else if (input.equals("Platoon Sergeant")) { // Platoon Leader
			// Adds skill dice levels to the skills
			// Squad Leader Training
			getSkill("Small Unit Tactics").supported = true;
			getSkill("Small Unit Tactics").trainingValue = 3;
			getSkill("Small Unit Tactics").rankUp();
			getSkill("Small Unit Tactics").rankUp();
			getSkill("Small Unit Tactics").rankUp();

			getSkill("Tactics").supported = true;
			getSkill("Tactics").trainingValue = 3;
			getSkill("Tactics").rankUp();
			getSkill("Tactics").rankUp();
			getSkill("Tactics").rankUp();

			getSkill("Command").supported = true;
			getSkill("Command").trainingValue = 3;
			getSkill("Command").rankUp();
			getSkill("Command").rankUp();
			getSkill("Command").rankUp();

			// Soldier Training
			getSkill("Climb").supported = true;
			getSkill("Climb").trainingValue = 2;
			getSkill("Climb").rankUp();

			getSkill("Dodge").supported = true;
			getSkill("Dodge").trainingValue = 2;
			getSkill("Dodge").rankUp();

			getSkill("Endurance").supported = true;
			getSkill("Endurance").trainingValue = 3;
			getSkill("Endurance").rankUp();
			getSkill("Endurance").rankUp();
			getSkill("Endurance").rankUp();

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").trainingValue = 2;
			getSkill("Spot/Listen").rankUp();
			getSkill("Spot/Listen").rankUp();
			getSkill("Spot/Listen").rankUp();

			getSkill("Stealth").supported = true;
			getSkill("Stealth").trainingValue = 2;
			getSkill("Stealth").rankUp();
			getSkill("Stealth").rankUp();

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").trainingValue = 2;
			getSkill("Camouflage").rankUp();
			getSkill("Camouflage").rankUp();

			getSkill("Rifle").supported = true;
			getSkill("Rifle").trainingValue = 4;
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();

			getSkill("Subgun").supported = true;
			getSkill("Subgun").trainingValue = 3;
			getSkill("Subgun").rankUp();
			getSkill("Subgun").rankUp();

			getSkill("Heavy").supported = true;
			getSkill("Heavy").trainingValue = 3;
			getSkill("Heavy").rankUp();
			getSkill("Heavy").rankUp();

			getSkill("Launcher").supported = true;
			getSkill("Launcher").trainingValue = 3;
			getSkill("Launcher").rankUp();
			getSkill("Launcher").rankUp();

			getSkill("Pistol").supported = true;
			getSkill("Pistol").trainingValue = 3;
			getSkill("Pistol").rankUp();
			getSkill("Pistol").rankUp();

			getSkill("Explosives").supported = true;
			getSkill("Explosives").trainingValue = 3;
			getSkill("Explosives").rankUp();
			getSkill("Explosives").rankUp();

			getSkill("Fighter").supported = true;
			getSkill("Fighter").trainingValue = 4;
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();

			getSkill("First Aid").supported = true;
			getSkill("First Aid").trainingValue = 2;
			getSkill("First Aid").rankUp();
			getSkill("First Aid").rankUp();

			getSkill("Navigation").supported = true;
			getSkill("Navigation").trainingValue = 3;
			getSkill("Navigation").rankUp();
			getSkill("Navigation").rankUp();
			getSkill("Navigation").rankUp();

			getSkill("Throw").supported = true;
			getSkill("Throw").trainingValue = 2;
			getSkill("Throw").rankUp();
			getSkill("Throw").rankUp();
			getSkill("Throw").rankUp();
		} else if (input.equals("Captain")) { // Platoon Leader
			// Adds skill dice levels to the skills
			// Squad Leader Training
			getSkill("Small Unit Tactics").supported = true;
			getSkill("Small Unit Tactics").trainingValue = 3;
			getSkill("Small Unit Tactics").rankUp();
			getSkill("Small Unit Tactics").rankUp();
			getSkill("Small Unit Tactics").rankUp();
			getSkill("Small Unit Tactics").rankUp();

			getSkill("Tactics").supported = true;
			getSkill("Tactics").trainingValue = 4;
			getSkill("Tactics").rankUp();
			getSkill("Tactics").rankUp();
			getSkill("Tactics").rankUp();
			getSkill("Tactics").rankUp();

			getSkill("Command").supported = true;
			getSkill("Command").trainingValue = 4;
			getSkill("Command").rankUp();
			getSkill("Command").rankUp();
			getSkill("Command").rankUp();
			getSkill("Command").rankUp();

			// Soldier Training
			getSkill("Climb").supported = true;
			getSkill("Climb").trainingValue = 2;
			getSkill("Climb").rankUp();

			getSkill("Dodge").supported = true;
			getSkill("Dodge").trainingValue = 2;
			getSkill("Dodge").rankUp();

			getSkill("Endurance").supported = true;
			getSkill("Endurance").trainingValue = 4;
			getSkill("Endurance").rankUp();
			getSkill("Endurance").rankUp();
			getSkill("Endurance").rankUp();

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").trainingValue = 4;
			getSkill("Spot/Listen").rankUp();
			getSkill("Spot/Listen").rankUp();
			getSkill("Spot/Listen").rankUp();

			getSkill("Stealth").supported = true;
			getSkill("Stealth").trainingValue = 3;
			getSkill("Stealth").rankUp();
			getSkill("Stealth").rankUp();

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").trainingValue = 3;
			getSkill("Camouflage").rankUp();
			getSkill("Camouflage").rankUp();

			getSkill("Rifle").supported = true;
			getSkill("Rifle").trainingValue = 4;
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();
			getSkill("Rifle").rankUp();

			getSkill("Subgun").supported = true;
			getSkill("Subgun").trainingValue = 3;
			getSkill("Subgun").rankUp();
			getSkill("Subgun").rankUp();

			getSkill("Heavy").supported = true;
			getSkill("Heavy").trainingValue = 3;
			getSkill("Heavy").rankUp();
			getSkill("Heavy").rankUp();

			getSkill("Launcher").supported = true;
			getSkill("Launcher").trainingValue = 3;
			getSkill("Launcher").rankUp();
			getSkill("Launcher").rankUp();

			getSkill("Pistol").supported = true;
			getSkill("Pistol").trainingValue = 3;
			getSkill("Pistol").rankUp();
			getSkill("Pistol").rankUp();

			getSkill("Explosives").supported = true;
			getSkill("Explosives").trainingValue = 3;
			getSkill("Explosives").rankUp();
			getSkill("Explosives").rankUp();
			getSkill("Explosives").rankUp();

			getSkill("Fighter").supported = true;
			getSkill("Fighter").trainingValue = 4;
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();
			getSkill("Fighter").rankUp();

			getSkill("First Aid").supported = true;
			getSkill("First Aid").trainingValue = 3;
			getSkill("First Aid").rankUp();
			getSkill("First Aid").rankUp();
			getSkill("First Aid").rankUp();

			getSkill("Navigation").supported = true;
			getSkill("Navigation").trainingValue = 4;
			getSkill("Navigation").rankUp();
			getSkill("Navigation").rankUp();
			getSkill("Navigation").rankUp();

			getSkill("Throw").supported = true;
			getSkill("Throw").trainingValue = 3;
			getSkill("Throw").rankUp();
			getSkill("Throw").rankUp();
			getSkill("Throw").rankUp();
		} else if (input.equals("Republic Commando") || input.equals("ARC Trooper")) { // RC or Arc
			System.out.println("Commando pass");
			elite();
		} else if (input.equals("Jackal Minor - Marksman")) {
			line();
			lineMarksman();
		} else if (input.equals("Jackal Minor")) {
			line();
		} else if (input.equals("Grunt Minor")) { // Rifleman
			militia();
		} else if (input.equals("Grunt Conscript")) { // Rifleman
			untrained();
		} else if (input.equals("Elite Minor - Lance Leader")) { // Squad Leader
			crack();
			crackSquadLeader();
		} else if (input.equals("ODST - Rifleman")) { // ODST Rifleman
			crack();
		} else if (input.equals("B1 Squad Leader")) {

			// Squad Leader Training
			getSkill("Small Unit Tactics").supported = true;
			getSkill("Small Unit Tactics").value = 65;

			getSkill("Tactics").supported = true;
			getSkill("Tactics").value = 60;

			getSkill("Command").supported = true;
			getSkill("Tactics").value = 60;

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 60;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 65;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 55;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 55;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 55;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 55;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 55;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 60;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 55;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 55;

		} else if (input.equals("B1 Rifleman")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 60;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 60;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 50;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 50;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 50;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 50;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 55;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 55;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 55;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 55;

		} else if (input.equals("B1 Marksman")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 65;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 65;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 50;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 50;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 50;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 50;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 50;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 55;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 55;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 55;

		} else if (input.equals("B1 AT Specialist")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 60;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 60;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 50;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 50;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 70;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 55;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 55;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 55;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 55;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 55;

		} else if (input.equals("B1 Assistant AT Specialist")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 60;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 60;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 50;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 50;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 65;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 50;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 55;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 55;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 55;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 55;

		} else if (input.equals("B1 Autorifleman")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 65;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 50;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 50;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 60;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 50;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 50;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 55;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 55;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 55;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 55;

		} else if (input.equals("B1 Assistant Autorifleman")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 65;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 55;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 55;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 60;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 55;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 55;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 55;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 55;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 55;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 55;

		} else if (input.equals("B2")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 85;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 60;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 65;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 55;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 55;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 55;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 55;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 55;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 60;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 55;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 55;

		} else if (input.equals("Commando Droid Squad Leader")) {

			// Squad Leader Training
			getSkill("Small Unit Tactics").supported = true;
			getSkill("Small Unit Tactics").value = 75;

			getSkill("Tactics").supported = true;
			getSkill("Tactics").value = 70;

			getSkill("Command").supported = true;
			getSkill("Command").value = 70;

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 65;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 70;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 70;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 70;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 70;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 70;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 70;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 70;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 65;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 65;

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").value = 65;

			getSkill("Stealth").supported = true;
			getSkill("Stealth").value = 65;

		} else if (input.equals("Commando Droid Rifleman")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 65;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 70;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 70;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 70;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 70;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 70;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 70;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 70;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 65;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 65;

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").value = 65;

			getSkill("Stealth").supported = true;
			getSkill("Stealth").value = 65;

		} else if (input.equals("Commando Droid Marksman")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 65;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 75;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 70;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 70;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 70;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 70;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 70;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 70;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 65;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 65;

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").value = 65;

			getSkill("Stealth").supported = true;
			getSkill("Stealth").value = 65;

		} else if (input.equals("Commando Droid AT Specalist")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 65;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 70;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 70;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 70;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 70;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 70;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 70;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 70;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 65;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 65;

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").value = 65;

			getSkill("Stealth").supported = true;
			getSkill("Stealth").value = 65;

		} else if (input.equals("Commando Droid AT Assistant")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 65;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 70;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 70;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 70;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 70;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 70;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 70;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 70;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 65;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 65;

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").value = 65;

			getSkill("Stealth").supported = true;
			getSkill("Stealth").value = 65;

		} else if (input.equals("Commando Droid Autorifleman")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 65;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 70;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 70;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 70;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 70;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 70;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 70;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 70;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 65;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 65;

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").value = 65;

			getSkill("Stealth").supported = true;
			getSkill("Stealth").value = 65;

		} else if (input.equals("Commando Droid Assistant Autorifleman")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 65;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 70;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 70;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 70;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 70;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 70;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 70;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 70;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 65;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 65;

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").value = 65;

			getSkill("Stealth").supported = true;
			getSkill("Stealth").value = 65;

		} else if (input.equals("Magma Guard")) {

			// Soldier Training
			getSkill("Endurance").supported = true;
			getSkill("Endurance").value = 70;

			getSkill("Spot/Listen").supported = true;
			getSkill("Spot/Listen").value = 65;

			getSkill("Rifle").supported = true;
			getSkill("Rifle").value = 75;

			getSkill("Subgun").supported = true;
			getSkill("Subgun").value = 70;

			getSkill("Heavy").supported = true;
			getSkill("Heavy").value = 70;

			getSkill("Launcher").supported = true;
			getSkill("Launcher").value = 70;

			getSkill("Pistol").supported = true;
			getSkill("Pistol").value = 70;

			getSkill("Explosives").supported = true;
			getSkill("Explosives").value = 70;

			getSkill("Fighter").supported = true;
			getSkill("Fighter").value = 75;

			getSkill("Navigation").supported = true;
			getSkill("Navigation").value = 65;

			getSkill("Throw").supported = true;
			getSkill("Throw").value = 65;

			getSkill("Camouflage").supported = true;
			getSkill("Camouflage").value = 65;

			getSkill("Stealth").supported = true;
			getSkill("Stealth").value = 65;

		} else if (input.equals("Untrained")) {
			untrained();
		} else if (input.equals("Militia")) {
			System.out.println("Pass Militia 2");
			militia();
		} else if (input.equals("Green")) {
			green();
		} else if (input.equals("Line")) {
			line();
		} else if (input.equals("Crack")) {
			crack();
		} else if (input.equals("Elite")) {
			elite();
		} else {
			System.out.println("WARNING during indiviudal creation: individual input was not valid.");
			throw new Exception("Input not a valid designation.");
		}

		setSkills();

	}
	
	
	public void lineSquadLeader() {
		// Squad Leader Training
		Skill smallUnitTactics = getSkill("Small Unit Tactics");
		smallUnitTactics.supported = true;
		smallUnitTactics.newTrainingValue(2);
		smallUnitTactics.rankUpTo(3);
		
		Skill tactics = getSkill("Tactics");
		tactics.supported = true;
		tactics.newTrainingValue(2);
		tactics.rankUpTo(3);
		
		Skill command = getSkill("Command");
		command.supported = true;
		command.newTrainingValue(2);
		command.rankUpTo(3);

	}
	
	public void crackSquadLeader() {
		// Squad Leader Training
		Skill smallUnitTactics = getSkill("Small Unit Tactics");
		smallUnitTactics.supported = true;
		smallUnitTactics.newTrainingValue(3);
		smallUnitTactics.rankUpTo(3);
		
		Skill tactics = getSkill("Tactics");
		tactics.supported = true;
		tactics.newTrainingValue(3);
		tactics.rankUpTo(3);
		
		Skill command = getSkill("Command");
		command.supported = true;
		command.newTrainingValue(3);
		command.rankUpTo(3);

	}
	
	public void lineAutorifleman() {
		Skill skill = getSkill("Heavy");
		skill.newTrainingValue(3);
		skill.rankUpTo(4);
	}
	
	public void lineAtSpecalist() {
		Skill skill = getSkill("Launcher");
		skill.newTrainingValue(3);
		skill.rankUpTo(4);
	}
		
	public void lineMedic() {
		Skill skill = getSkill("First Aid");
		skill.newTrainingValue(3);
		skill.rankUpTo(4);
	}
	
	public void lineMarksman() {
		Skill skill = getSkill("Rifle");
		skill.newTrainingValue(3);
		skill.rankUpTo(4);
	}
	
	public void crackAutorifleman() {
		Skill skill = getSkill("Heavy");
		skill.newTrainingValue(5);
		skill.rankUpTo(4);
	}
	
	public void crackAtSpecalist() {
		Skill skill = getSkill("Launcher");
		skill.newTrainingValue(5);
		skill.rankUpTo(4);
	}
		
	public void crackMedic() {
		Skill skill = getSkill("First Aid");
		skill.newTrainingValue(4);
		skill.rankUpTo(4);
	}
	
	public void crackMarksman() {
		Skill skill = getSkill("Rifle");
		skill.newTrainingValue(5);
		skill.rankUpTo(4);
	}
	
	public void untrained() {
		// Soldier Training
		/*getSkill("Climb").supported = true;
		getSkill("Climb").trainingValue = 1;
		getSkill("Climb").rankUp();

		getSkill("Dodge").supported = true;
		getSkill("Dodge").trainingValue = 1;
		getSkill("Dodge").rankUp();

		getSkill("Endurance").supported = true;
		getSkill("Endurance").trainingValue = 1;
		getSkill("Endurance").rankUp();

		getSkill("Spot/Listen").supported = true;
		getSkill("Spot/Listen").trainingValue = 1;
		getSkill("Spot/Listen").rankUp();

		getSkill("Stealth").supported = true;
		getSkill("Stealth").trainingValue = 1;
		getSkill("Stealth").rankUp();

		getSkill("Camouflage").supported = true;
		getSkill("Camouflage").trainingValue = 1;
		getSkill("Camouflage").rankUp();

		getSkill("Rifle").supported = true;
		getSkill("Rifle").trainingValue = 1;
		getSkill("Rifle").rankUp();

		getSkill("Subgun").supported = true;
		getSkill("Subgun").trainingValue = 1;
		getSkill("Subgun").rankUp();

		getSkill("Pistol").supported = true;
		getSkill("Pistol").trainingValue = 1;
		getSkill("Pistol").rankUp();

		getSkill("Fighter").supported = true;
		getSkill("Fighter").trainingValue = 1;
		getSkill("Fighter").rankUp();

		getSkill("Speed").supported = true;
		getSkill("Speed").trainingValue = 1;
		getSkill("Speed").rankUp();

		getSkill("Throw").supported = true;
		getSkill("Throw").trainingValue = 1;
		getSkill("Throw").rankUp();*/
	}
	
	public void militia() {
		// Soldier Training
		getSkill("Climb").supported = true;
		getSkill("Climb").trainingValue = 1;
		getSkill("Climb").rankUp();

		getSkill("Dodge").supported = true;
		getSkill("Dodge").trainingValue = 1;
		getSkill("Dodge").rankUp();

		getSkill("Endurance").supported = true;
		getSkill("Endurance").trainingValue = 1;
		getSkill("Endurance").rankUp();


		getSkill("Spot/Listen").supported = true;
		getSkill("Spot/Listen").trainingValue = 1;
		getSkill("Spot/Listen").rankUp();

		getSkill("Stealth").supported = true;
		getSkill("Stealth").trainingValue = 1;
		getSkill("Stealth").rankUp();

		getSkill("Camouflage").supported = true;
		getSkill("Camouflage").trainingValue = 1;
		getSkill("Camouflage").rankUp();

		getSkill("Rifle").supported = true;
		getSkill("Rifle").trainingValue = 1;
		getSkill("Rifle").rankUp();


		getSkill("Subgun").supported = true;
		getSkill("Subgun").trainingValue = 1;
		getSkill("Subgun").rankUp();

		getSkill("Pistol").supported = true;
		getSkill("Pistol").trainingValue = 1;
		getSkill("Pistol").rankUp();

		getSkill("Explosives").supported = true;
		getSkill("Explosives").trainingValue = 1;
		getSkill("Explosives").rankUp();

		getSkill("Fighter").supported = true;
		getSkill("Fighter").trainingValue = 2;
		getSkill("Fighter").rankUp();

		getSkill("Speed").supported = true;
		getSkill("Speed").trainingValue = 1;
		getSkill("Speed").rankUp();

		getSkill("First Aid").supported = true;
		getSkill("First Aid").trainingValue = 1;
		getSkill("First Aid").rankUp();

		getSkill("Navigation").supported = true;
		getSkill("Navigation").trainingValue = 1;
		getSkill("Navigation").rankUp();

		getSkill("Throw").supported = true;
		getSkill("Throw").trainingValue = 1;
		getSkill("Throw").rankUp();
	}

	public void green() {
		// Soldier Training
		getSkill("Climb").supported = true;
		getSkill("Climb").trainingValue = 1;
		getSkill("Climb").rankUp();

		getSkill("Dodge").supported = true;
		getSkill("Dodge").trainingValue = 1;
		getSkill("Dodge").rankUp();

		getSkill("Endurance").supported = true;
		getSkill("Endurance").trainingValue = 2;
		getSkill("Endurance").rankUp();

		getSkill("Spot/Listen").supported = true;
		getSkill("Spot/Listen").trainingValue = 1;
		getSkill("Spot/Listen").rankUp();

		getSkill("Stealth").supported = true;
		getSkill("Stealth").trainingValue = 1;
		getSkill("Stealth").rankUp();

		getSkill("Camouflage").supported = true;
		getSkill("Camouflage").trainingValue = 1;
		getSkill("Camouflage").rankUp();

		getSkill("Rifle").supported = true;
		getSkill("Rifle").trainingValue = 2;
		getSkill("Rifle").rankUp();

		getSkill("Subgun").supported = true;
		getSkill("Subgun").trainingValue = 2;
		getSkill("Subgun").rankUp();

		getSkill("Heavy").supported = true;
		getSkill("Heavy").trainingValue = 1;
		getSkill("Heavy").rankUp();

		getSkill("Launcher").supported = true;
		getSkill("Launcher").trainingValue = 1;
		getSkill("Launcher").rankUp();

		getSkill("Pistol").supported = true;
		getSkill("Pistol").trainingValue = 2;
		getSkill("Pistol").rankUp();

		getSkill("Explosives").supported = true;
		getSkill("Explosives").trainingValue = 2;
		getSkill("Explosives").rankUp();

		getSkill("Fighter").supported = true;
		getSkill("Fighter").trainingValue = 2;
		getSkill("Fighter").rankUp();

		getSkill("Speed").supported = true;
		getSkill("Speed").trainingValue = 2;
		getSkill("Speed").rankUp();

		getSkill("First Aid").supported = true;
		getSkill("First Aid").trainingValue = 2;
		getSkill("First Aid").rankUp();

		getSkill("Navigation").supported = true;
		getSkill("Navigation").trainingValue = 2;
		getSkill("Navigation").rankUp();

		getSkill("Throw").supported = true;
		getSkill("Throw").trainingValue = 2;
		getSkill("Throw").rankUp();
	}
	
	public void line() {
		// Soldier Training
		getSkill("Climb").supported = true;
		getSkill("Climb").trainingValue = 2;
		getSkill("Climb").rankUp();
		getSkill("Climb").rankUp();

		getSkill("Dodge").supported = true;
		getSkill("Dodge").trainingValue = 2;
		getSkill("Dodge").rankUp();
		getSkill("Dodge").rankUp();

		getSkill("Endurance").supported = true;
		getSkill("Endurance").trainingValue = 2;
		getSkill("Endurance").rankUp();
		getSkill("Endurance").rankUp();

		getSkill("Spot/Listen").supported = true;
		getSkill("Spot/Listen").trainingValue = 2;
		getSkill("Spot/Listen").rankUp();
		getSkill("Spot/Listen").rankUp();

		getSkill("Stealth").supported = true;
		getSkill("Stealth").trainingValue = 2;
		getSkill("Stealth").rankUp();
		getSkill("Stealth").rankUp();

		getSkill("Camouflage").supported = true;
		getSkill("Camouflage").trainingValue = 2;
		getSkill("Camouflage").rankUp();
		getSkill("Camouflage").rankUp();

		getSkill("Rifle").supported = true;
		getSkill("Rifle").trainingValue = 2;
		getSkill("Rifle").rankUp();
		getSkill("Rifle").rankUp();
		getSkill("Rifle").rankUp();

		getSkill("Subgun").supported = true;
		getSkill("Subgun").trainingValue = 2;
		getSkill("Subgun").rankUp();
		getSkill("Subgun").rankUp();
		getSkill("Subgun").rankUp();

		getSkill("Heavy").supported = true;
		getSkill("Heavy").trainingValue = 2;
		getSkill("Heavy").rankUp();
		getSkill("Heavy").rankUp();

		getSkill("Launcher").supported = true;
		getSkill("Launcher").trainingValue = 2;
		getSkill("Launcher").rankUp();
		getSkill("Launcher").rankUp();

		getSkill("Pistol").supported = true;
		getSkill("Pistol").trainingValue = 2;
		getSkill("Pistol").rankUp();
		getSkill("Pistol").rankUp();
		getSkill("Pistol").rankUp();

		getSkill("Explosives").supported = true;
		getSkill("Explosives").trainingValue = 2;
		getSkill("Explosives").rankUp();
		getSkill("Explosives").rankUp();

		getSkill("Fighter").supported = true;
		getSkill("Fighter").trainingValue = 2;
		getSkill("Fighter").rankUp();
		getSkill("Fighter").rankUp();
		getSkill("Fighter").rankUp();

		getSkill("Speed").supported = true;
		getSkill("Speed").trainingValue = 2;
		getSkill("Speed").rankUp();
		getSkill("Speed").rankUp();
		getSkill("Speed").rankUp();

		getSkill("First Aid").supported = true;
		getSkill("First Aid").trainingValue = 2;
		getSkill("First Aid").rankUp();
		getSkill("First Aid").rankUp();
		getSkill("First Aid").rankUp();

		getSkill("Navigation").supported = true;
		getSkill("Navigation").trainingValue = 2;
		getSkill("Navigation").rankUp();
		getSkill("Navigation").rankUp();

		getSkill("Throw").supported = true;
		getSkill("Throw").trainingValue = 2;
		getSkill("Throw").rankUp();
		getSkill("Throw").rankUp();
		getSkill("Throw").rankUp();
	}

	public void crack() {
		getSkill("Small Unit Tactics").supported = true;
		getSkill("Small Unit Tactics").trainingValue = 1;
		getSkill("Small Unit Tactics").rankUp();

		getSkill("Tactics").supported = true;
		getSkill("Tactics").trainingValue = 1;
		getSkill("Tactics").rankUp();

		getSkill("Command").supported = true;
		getSkill("Command").trainingValue = 1;
		getSkill("Command").rankUp();

		// Soldier Training
		getSkill("Climb").supported = true;
		getSkill("Climb").trainingValue = 3;
		getSkill("Climb").rankUp();
		getSkill("Climb").rankUp();

		getSkill("Dodge").supported = true;
		getSkill("Dodge").trainingValue = 3;
		getSkill("Dodge").rankUp();
		getSkill("Dodge").rankUp();

		getSkill("Endurance").supported = true;
		getSkill("Endurance").trainingValue = 3;
		getSkill("Endurance").rankUp();
		getSkill("Endurance").rankUp();
		getSkill("Endurance").rankUp();

		getSkill("Spot/Listen").supported = true;
		getSkill("Spot/Listen").trainingValue = 3;
		getSkill("Spot/Listen").rankUp();
		getSkill("Spot/Listen").rankUp();
		getSkill("Spot/Listen").rankUp();

		getSkill("Stealth").supported = true;
		getSkill("Stealth").trainingValue = 3;
		getSkill("Stealth").rankUp();
		getSkill("Stealth").rankUp();

		getSkill("Camouflage").supported = true;
		getSkill("Camouflage").trainingValue = 3;
		getSkill("Camouflage").rankUp();
		getSkill("Camouflage").rankUp();

		getSkill("Rifle").supported = true;
		getSkill("Rifle").trainingValue = 3;
		getSkill("Rifle").rankUp();
		getSkill("Rifle").rankUp();
		getSkill("Rifle").rankUp();
		getSkill("Rifle").rankUp();

		getSkill("Subgun").supported = true;
		getSkill("Subgun").trainingValue = 3;
		getSkill("Subgun").rankUp();
		getSkill("Subgun").rankUp();
		getSkill("Subgun").rankUp();

		getSkill("Heavy").supported = true;
		getSkill("Heavy").trainingValue = 3;
		getSkill("Heavy").rankUp();
		getSkill("Heavy").rankUp();

		getSkill("Launcher").supported = true;
		getSkill("Launcher").trainingValue = 3;
		getSkill("Launcher").rankUp();
		getSkill("Launcher").rankUp();

		getSkill("Pistol").supported = true;
		getSkill("Pistol").trainingValue = 3;
		getSkill("Pistol").rankUp();
		getSkill("Pistol").rankUp();
		getSkill("Pistol").rankUp();

		getSkill("Explosives").supported = true;
		getSkill("Explosives").trainingValue = 2;
		getSkill("Explosives").rankUp();

		getSkill("Fighter").supported = true;
		getSkill("Fighter").trainingValue = 3;
		getSkill("Fighter").rankUp();
		getSkill("Fighter").rankUp();
		getSkill("Fighter").rankUp();
		getSkill("Fighter").rankUp();

		getSkill("First Aid").supported = true;
		getSkill("First Aid").trainingValue = 3;
		getSkill("First Aid").rankUp();
		getSkill("First Aid").rankUp();
		getSkill("First Aid").rankUp();

		getSkill("Navigation").supported = true;
		getSkill("Navigation").trainingValue = 3;
		getSkill("Navigation").rankUp();
		getSkill("Navigation").rankUp();

		getSkill("Throw").supported = true;
		getSkill("Throw").trainingValue = 3;
		getSkill("Throw").rankUp();
		getSkill("Throw").rankUp();
		getSkill("Throw").rankUp();
	}

	public void elite() {
		getSkill("Climb").supported = true;
		getSkill("Climb").trainingValue = 3;
		getSkill("Climb").rankUp();
		getSkill("Climb").rankUp();
		getSkill("Climb").rankUp();

		getSkill("Dodge").supported = true;
		getSkill("Dodge").trainingValue = 3;
		getSkill("Dodge").rankUp();
		getSkill("Dodge").rankUp();
		getSkill("Dodge").rankUp();

		getSkill("Endurance").supported = true;
		getSkill("Endurance").trainingValue = 4;
		getSkill("Endurance").rankUp();
		getSkill("Endurance").rankUp();
		getSkill("Endurance").rankUp();
		getSkill("Endurance").rankUp();

		getSkill("Spot/Listen").supported = true;
		getSkill("Spot/Listen").trainingValue = 4;
		getSkill("Spot/Listen").rankUp();
		getSkill("Spot/Listen").rankUp();
		getSkill("Spot/Listen").rankUp();
		getSkill("Spot/Listen").rankUp();

		getSkill("Stealth").supported = true;
		getSkill("Stealth").trainingValue = 4;
		getSkill("Stealth").rankUp();
		getSkill("Stealth").rankUp();
		getSkill("Stealth").rankUp();

		getSkill("Camouflage").supported = true;
		getSkill("Camouflage").trainingValue = 4;
		getSkill("Camouflage").rankUp();
		getSkill("Camouflage").rankUp();
		getSkill("Camouflage").rankUp();

		getSkill("Rifle").supported = true;
		getSkill("Rifle").trainingValue = 5;
		getSkill("Rifle").rankUp();
		getSkill("Rifle").rankUp();
		getSkill("Rifle").rankUp();
		getSkill("Rifle").rankUp();

		getSkill("Subgun").supported = true;
		getSkill("Subgun").trainingValue = 4;
		getSkill("Subgun").rankUp();
		getSkill("Subgun").rankUp();
		getSkill("Subgun").rankUp();
		getSkill("Subgun").rankUp();

		getSkill("Heavy").supported = true;
		getSkill("Heavy").trainingValue = 4;
		getSkill("Heavy").rankUp();
		getSkill("Heavy").rankUp();
		getSkill("Heavy").rankUp();

		getSkill("Launcher").supported = true;
		getSkill("Launcher").trainingValue = 4;
		getSkill("Launcher").rankUp();
		getSkill("Launcher").rankUp();
		getSkill("Launcher").rankUp();

		getSkill("Pistol").supported = true;
		getSkill("Pistol").trainingValue = 4;
		getSkill("Pistol").rankUp();
		getSkill("Pistol").rankUp();
		getSkill("Pistol").rankUp();
		getSkill("Pistol").rankUp();

		getSkill("Explosives").supported = true;
		getSkill("Explosives").trainingValue = 4;
		getSkill("Explosives").rankUp();
		getSkill("Explosives").rankUp();

		getSkill("Fighter").supported = true;
		getSkill("Fighter").trainingValue = 5;
		getSkill("Fighter").rankUp();
		getSkill("Fighter").rankUp();
		getSkill("Fighter").rankUp();
		getSkill("Fighter").rankUp();

		getSkill("First Aid").supported = true;
		getSkill("First Aid").trainingValue = 3;
		getSkill("First Aid").rankUp();
		getSkill("First Aid").rankUp();
		getSkill("First Aid").rankUp();

		getSkill("Navigation").supported = true;
		getSkill("Navigation").trainingValue = 3;
		getSkill("Navigation").rankUp();
		getSkill("Navigation").rankUp();
		getSkill("Navigation").rankUp();

		getSkill("Throw").supported = true;
		getSkill("Throw").trainingValue = 3;
		getSkill("Throw").rankUp();
		getSkill("Throw").rankUp();
		getSkill("Throw").rankUp();
	}

	// Finds skill from skill array list
	// Returns skill
	public Skill getSkill(String name) {

		for (Skill skill : skills) {
			if (skill.name.equals(name))
				return skill;
		}

		System.err.println("SKILL NOT FOUND. Get Skill in skills. String name not valid: ["+name+"]");
		return null;
	}

	public void setSkills() {
		ballance = getSkill("Ballance").value;

		// Sets skill values
		this.ballance = getSkill("Ballance").value;
		this.bow = getSkill("Bow").value;
		this.climb = getSkill("Climb").value;
		this.composure = getSkill("Composure").value;
		this.dodge = getSkill("Dodge").value;
		this.endurance = getSkill("Endurance").value;
		this.deception = getSkill("Deception").value;
		this.expression = getSkill("Expression").value;
		this.grapple = getSkill("Grapple").value;
		this.hold = getSkill("Hold").value;
		this.intuition = getSkill("Intuition").value;
		this.investigation = getSkill("Investigation").value;
		this.jump = getSkill("Jump/Leap").value;
		this.lift = getSkill("Lift/Pull").value;
		this.resistPain = getSkill("Resist Pain").value;
		this.search = getSkill("Search").value;
		this.spotListen = getSkill("Spot/Listen").value;
		this.stealth = getSkill("Stealth").value;
		this.camouflage = getSkill("Camouflage").value;
		this.calm = getSkill("Calm Other").value;
		this.diplomacy = getSkill("Diplomacy").value;
		this.barter = getSkill("Barter").value;
		this.command = getSkill("Command").value;
		this.tactics = getSkill("Tactics").value;
		this.detMotives = getSkill("Determine Motives").value;
		this.intimidate = getSkill("Intimidate").value;
		this.persuade = getSkill("Persuade").value;
		this.digiSystems = getSkill("Digi. Systems").value;
		this.pistol = getSkill("Pistol").value;
		this.heavy = getSkill("Heavy").value;
		this.subgun = getSkill("Subgun").value;
		this.launcher = getSkill("Launcher").value;
		this.rifle = getSkill("Rifle").value;
		this.explosives = getSkill("Explosives").value;
		this.firstAid = getSkill("First Aid").value;
		this.advancedMedicine = getSkill("Advanced Medicine").value;
		this.navigation = getSkill("Navigation").value;
		this.swim = getSkill("Swim").value;
		this.Throw = getSkill("Throw").value;

		this.cleanOperations = getSkill("Clean Operations").value;
		this.covertMovement = getSkill("Covert Movement").value;
		this.fighter = getSkill("Fighter").value;
		this.recoilControl = getSkill("Recoil Control").value;
		this.triggerDiscipline = getSkill("Trigger Discipline").value;
		this.reloadDrills = getSkill("Reload Drills").value;
		this.silentOperations = getSkill("Silent Operations").value;

		this.akSystems = getSkill("AK Systems").value;
		this.assualtOperations = getSkill("Assault Operations").value;
		this.authority = getSkill("Authority").value;
		this.rawPower = getSkill("Raw Power").value;

		this.arSystems = getSkill("AR Systems").value;
		this.longRangeOptics = getSkill("Long Range Optics").value;
		this.negotiations = getSkill("Negotiations").value;
		this.smallUnitTactics = getSkill("Small Unit Tactics").value;

		this.craft = getSkill("Craft/Construct/Engineer").value;
		this.pilot = getSkill("Pilot").value;
		this.animalHandling = getSkill("Animal Handling").value;
		this.ride = getSkill("Ride").value;
		this.science = getSkill("Science").value;
		this.survival = getSkill("Survival").value;
	}

	// Sets the value of all the skills
	// Adds the base attribute and the attribute modifier
	// Sets the value of the skills public variables
	public void calculateSkills() {
		ballance = per + witMod;
		bow = agi + strMod;
		climb = str + witMod;
		composure = wit + htlMod;
		dodge = agi + strMod;
		endurance = str + witMod;
		deception = soc + socMod;
		expression = htl + perMod;
		grapple = str + agiMod;
		hold = str + agiMod;
		jump = str + perMod;
		lift = str + witMod;
		resistPain = wit + strMod;
		search = per + witMod;
		spotListen = per + witMod;
		stealth = per + witMod;
		camouflage = per + witMod;
		calm = htl + strMod;
		diplomacy = htl + witMod;
		barter = soc + witMod;
		command = wit + socMod;
		tactics = wit + perMod;
		detMotives = soc + witMod;
		intimidate = soc + witMod;
		investigation = wit + perMod;
		intuition = wil + perMod;
		persuade = soc + witMod;
		digiSystems = wit + perMod;
		pistol = agi + perMod;
		heavy = agi + perMod;
		subgun = agi + perMod;
		launcher = agi + perMod;
		rifle = agi + perMod;
		explosives = wit + perMod;
		advancedMedicine = wit + strMod;
		firstAid = wit + perMod;
		navigation = wit + perMod;
		swim = str + htlMod;
		Throw = per + strMod;

		cleanOperations = agi + perMod;
		covertMovement = agi + perMod;
		fighter = str + wilMod;
		recoilControl = str + agiMod;
		triggerDiscipline = fighter + rifle / 2;
		reloadDrills = agi + perMod;
		silentOperations = agi + perMod;

		akSystems = str + perMod;
		assualtOperations = str + wilMod;
		authority = str + socMod;
		rawPower = str + htlMod;

		arSystems = agi + perMod;
		longRangeOptics = per + agiMod;
		negotiations = soc + witMod;
		smallUnitTactics = wit + perMod;

		craft = wit + strMod;
		pilot = agi + perMod;
		animalHandling = soc + wilMod;
		ride = agi + strMod;
		science = wit + perMod;
		survival = agi + perMod;

	}

	// Gets the attributes and attribute modifiers used in the calculatsion of
	// skills
	public void getAttr(int attr[]) {

		// Attributes
		int str = attr[0] * 3;
		int wit = attr[1] * 3;
		int soc = attr[2] * 3;
		int wil = attr[3] * 3;
		int per = attr[4] * 3;
		int htl = attr[5] * 3;
		int agi = attr[6] * 3;

		// Attribute modifiers
		int strMod;
		int witMod;
		int socMod;
		int wilMod;
		int perMod;
		int htlMod;
		int agiMod;

		if (str < 10) {
			strMod = str;
		} else {
			strMod = Integer.parseInt(Integer.toString(str).substring(0, 1));
		}

		if (wit < 10) {
			witMod = wit;
		} else {
			witMod = Integer.parseInt(Integer.toString(wit).substring(0, 1));
		}

		if (soc < 10) {
			socMod = soc;
		} else {
			socMod = Integer.parseInt(Integer.toString(soc).substring(0, 1));
		}

		if (wil < 10) {
			wilMod = wil;
		} else {
			wilMod = Integer.parseInt(Integer.toString(wil).substring(0, 1));
		}

		if (per < 10) {
			perMod = per;
		} else {
			perMod = Integer.parseInt(Integer.toString(per).substring(0, 1));
		}

		if (htl < 10) {
			htlMod = htl;
		} else {
			htlMod = Integer.parseInt(Integer.toString(htl).substring(0, 1));
		}

		if (agi < 10) {
			agiMod = agi;
		} else {
			agiMod = Integer.parseInt(Integer.toString(agi).substring(0, 1));
		}

		// Sets class attr vareiables
		this.str = str;
		this.wit = wit;
		this.soc = soc;
		this.wil = wil;
		this.per = per;
		this.htl = htl;
		this.agi = agi;

		this.strMod = strMod;
		this.witMod = witMod;
		this.socMod = socMod;
		this.wilMod = wilMod;
		this.perMod = perMod;
		this.htlMod = htlMod;
		this.agiMod = agiMod;
	}
	
	public void getAttr(Trooper trooper) {

		// Attributes
		int str = trooper.str;
		int wit = trooper.wit;
		int wis = trooper.wis;
		int soc = trooper.soc;
		int wil = trooper.wil;
		int per = trooper.per;
		int htl = trooper.hlt;
		int agi = trooper.agi;

		// Attribute modifiers
		int strMod;
		int witMod;
		int wisMod;
		int socMod;
		int wilMod;
		int perMod;
		int htlMod;
		int agiMod;

		if (str < 10) {
			strMod = str;
		} else {
			strMod = Integer.parseInt(Integer.toString(str).substring(0, 1));
		}

		if (wit < 10) {
			witMod = wit;
		} else {
			witMod = Integer.parseInt(Integer.toString(wit).substring(0, 1));
		}
		
		if (wis < 10) {
			wisMod = wis;
		} else {
			wisMod = Integer.parseInt(Integer.toString(wis).substring(0, 1));
		}

		if (soc < 10) {
			socMod = soc;
		} else {
			socMod = Integer.parseInt(Integer.toString(soc).substring(0, 1));
		}

		if (wil < 10) {
			wilMod = wil;
		} else {
			wilMod = Integer.parseInt(Integer.toString(wil).substring(0, 1));
		}

		if (per < 10) {
			perMod = per;
		} else {
			perMod = Integer.parseInt(Integer.toString(per).substring(0, 1));
		}

		if (htl < 10) {
			htlMod = htl;
		} else {
			htlMod = Integer.parseInt(Integer.toString(htl).substring(0, 1));
		}

		if (agi < 10) {
			agiMod = agi;
		} else {
			agiMod = Integer.parseInt(Integer.toString(agi).substring(0, 1));
		}

		// Sets class attr vareiables
		this.str = str;
		this.wit = wit;
		this.wis = wis;
		this.soc = soc;
		this.wil = wil;
		this.per = per;
		this.htl = htl;
		this.agi = agi;

		this.strMod = strMod;
		this.witMod = witMod;
		this.wisMod = wisMod;
		this.socMod = socMod;
		this.wilMod = wilMod;
		this.perMod = perMod;
		this.htlMod = htlMod;
		this.agiMod = agiMod;
	}

	// Rolls d6, generates a number between 1 and 6
	// Rolls multiple times equal to variable rolls
	// Adds rolls into sum and returns sum
	public static int rollD6(int rolls) {
		Random rand = new Random();

		int sum = 0;
		for (int i = 0; i < rolls; i++) {
			sum = sum + rand.nextInt(6) + 1;
		}
		return sum;
	}
}