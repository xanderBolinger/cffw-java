import java.io.Serializable;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Character implements Serializable {

	String name;

	public int characterPointTotal;
	public int spentCharacterPoints;

	// Attributes
	public int str;
	public int wit;
	public int wis;
	public int soc;
	public int wil;
	public int per;
	public int htl;
	public int agl;

	// Abilities
	ArrayList<Ability> abilities = new ArrayList<Ability>();

	// Skills
	ArrayList<Skill> basicSkills = new ArrayList<Skill>();
	ArrayList<Skill> trainedSkills = new ArrayList<Skill>();
	ArrayList<Skill> expertSkills = new ArrayList<Skill>();

	// Stats
	public int combatSkillPistol;
	public int combatSkillRifle;
	public int encumberance;
	public int baseSpeed;
	public int maximumSpeed;
	public int rangedSAL;
	public int meleeSAL;
	public int ISF;
	public int initiative;
	public int defensiveSkill;
	public int combatSkillActionsRanged;
	public int combatSkillActionsMelee;
	public int KO;

	// TROS
	public int reflexes;
	public int combatPool;

	// CE
	public int courseOfActionsCapacity;
	public int adaptabilityFactor;
	public int coolnessUnderFire;
	public int leadershipSkillFactor;
	public int commandTime;
	public int stamina;
	public int fatiguePointProgress;

	// NOtes 
	public String notes; 
	
	// Stats
	public double encum;

	public Character() {

	}

	// Constructor
	public Character(String name, int characterPointTotal, int spentCharacterPoints, int str, int wit, int wis, int soc,
			int wil, int per, int htl, int agl, ArrayList<Ability> abilities, ArrayList<Skill> basicSkills,
			ArrayList<Skill> trainedSkills, ArrayList<Skill> expertSkills, int combatSkillPistol, int combatSkillRifle,
			int encumberance, int baseSpeed, int maximumSpeed, int rangedSAL, int meleeSAL, int ISF, int initiative,
			int defensiveSkill, int combatSkillActionsRanged, int combatSkillActionsMelee, int KO, int reflexes,
			int combatPool, int courseOfActionsCapacity, int adaptabilityFactor, int coolnessUnderFire,
			int leadershipSkillFactor, int commandTime, int stamina, int fatiguePointProgress) {

		this.name = name;
		this.characterPointTotal = characterPointTotal;
		this.spentCharacterPoints = spentCharacterPoints;
		this.str = str;
		this.wit = wit;
		this.wis = wis;
		this.soc = soc;
		this.wil = wil;
		this.per = per;
		this.htl = htl;
		this.agl = agl;
		this.abilities = abilities;
		this.basicSkills = basicSkills;
		this.trainedSkills = trainedSkills;
		this.expertSkills = expertSkills;
		this.combatSkillPistol = combatSkillPistol;
		this.combatSkillRifle = combatSkillRifle;
		this.encumberance = encumberance;
		this.baseSpeed = baseSpeed;
		this.maximumSpeed = maximumSpeed;
		this.rangedSAL = rangedSAL;
		this.meleeSAL = meleeSAL;
		this.ISF = ISF;
		this.initiative = initiative;
		this.defensiveSkill = defensiveSkill;
		this.combatSkillActionsRanged = combatSkillActionsRanged;
		this.combatSkillActionsMelee = combatSkillActionsMelee;
		this.KO = KO;
		this.reflexes = reflexes;
		this.combatPool = combatPool;
		this.courseOfActionsCapacity = courseOfActionsCapacity;
		this.adaptabilityFactor = adaptabilityFactor;
		this.coolnessUnderFire = coolnessUnderFire;
		this.leadershipSkillFactor = leadershipSkillFactor;
		this.commandTime = commandTime;
		this.stamina = stamina;
		this.fatiguePointProgress = fatiguePointProgress;

	}

	// Creates a new character
	// Calls appropriate methods
	public Character createCharacter() {

		Character character = new Character(name, characterPointTotal, spentCharacterPoints, str, wit, wis, soc, wil,
				per, htl, agl, abilities, basicSkills, trainedSkills, expertSkills, combatSkillPistol, combatSkillRifle,
				encumberance, baseSpeed, maximumSpeed, rangedSAL, meleeSAL, ISF, initiative, defensiveSkill,
				combatSkillActionsRanged, combatSkillActionsMelee, KO, reflexes, combatPool, courseOfActionsCapacity,
				adaptabilityFactor, coolnessUnderFire, leadershipSkillFactor, commandTime, stamina,
				fatiguePointProgress);

		return character;
	}

	// Sets the basic stats for a newly created character
	public void setBasicStats() {

		this.characterPointTotal = 25;
		this.spentCharacterPoints = 0;

	}

	// Calculates and sets the value of all attributes
	public void calculateAttributes() {

		this.str = 3 * rolld3d4DropOne();
		this.wit = 3 * rolld3d4DropOne();
		this.wis = 3 * rolld3d4DropOne();
		this.soc = 3 * rolld3d4DropOne();
		this.wil = 3 * rolld3d4DropOne();
		this.per = 3 * rolld3d4DropOne();
		this.htl = 3 * rolld3d4DropOne();
		this.agl = 3 * rolld3d4DropOne();

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
			return htl;
		} else if (attr.equals("agl")) {
			return agl;
		} else {
			return -1;
		}

	}

	// Re-Calculate Skills
	// Re-calculates skills with current values
	public void recalculateSkills() {

		ArrayList<Skill> basicSkills2 = new ArrayList<Skill>();
		ArrayList<Skill> trainedSkills2 = new ArrayList<Skill>();
		ArrayList<Skill> expertSkills2 = new ArrayList<Skill>();

		for (int i = 0; i < basicSkills.size(); i++) {

			Skill skill = basicSkills.get(i);

			int attr1 = getAttribute(skill.baseAttribute);
			int attr2 = getAttribute(skill.supportingAttribute);

			Skill newSkill = new Skill(skill.name, skill.rank, skill.value, attr1, attr2, skill.baseAttribute,
					skill.supportingAttribute, skill.supported, skill.trainingValue, skill.increasedValue, skill.type);

			basicSkills2.add(newSkill);

		}

		for (int i = 0; i < trainedSkills.size(); i++) {

			Skill skill = trainedSkills.get(i);

			int attr1 = getAttribute(skill.baseAttribute);
			int attr2 = getAttribute(skill.supportingAttribute);

			Skill newSkill = new Skill(skill.name, skill.rank, skill.value, attr1, attr2, skill.baseAttribute,
					skill.supportingAttribute, skill.supported, skill.trainingValue, skill.increasedValue, skill.type);

			trainedSkills2.add(newSkill);

		}

		for (int i = 0; i < expertSkills.size(); i++) {

			Skill skill = expertSkills.get(i);

			int attr1 = getAttribute(skill.baseAttribute);
			int attr2 = getAttribute(skill.supportingAttribute);

			Skill newSkill = new Skill(skill.name, skill.rank, skill.value, attr1, attr2, skill.baseAttribute,
					skill.supportingAttribute, skill.supported, skill.trainingValue, skill.increasedValue, skill.type);

			expertSkills2.add(newSkill);

		}

		basicSkills = basicSkills2;
		trainedSkills = trainedSkills2;
		expertSkills = expertSkills2;

	}

	// Calculate Skills
	public void calculateSkills() {

		basicSkills = new ArrayList<Skill>();
		trainedSkills = new ArrayList<Skill>();
		expertSkills = new ArrayList<Skill>();

		// Skill(String name, int rank, int value, int attr1, int attr2, String
		// baseAttribute, String supportingAttribute, boolean supported, int
		// trainingValue, int increasedValue, String type)
		// Create basic skills
		Skill ballance = new Skill("Ballance", 0, 0, agl, wil, "AGL", "wil", false, 0, 0, "Basic");
		Skill camouflage = new Skill("Camouflage", 0, 0, wit, per, "INT", "per", false, 0, 0, "Basic");
		Skill climb = new Skill("Climb", 0, 0, str, agl, "STR", "agl", false, 0, 0, "Basic");
		Skill composure = new Skill("Composure", 0, 0, wil, htl, "WIL", "htl", false, 0, 0, "Basic");
		Skill deception = new Skill("Deception", 0, 0, soc, wit, "SOC", "int", false, 0, 0, "Basic");
		Skill dodge = new Skill("Dodge", 0, 0, agl, str, "AGL", "str", false, 0, 0, "Basic");
		Skill endurance = new Skill("Endurance", 0, 0, str, wil, "STR", "wil", false, 0, 0, "Basic");
		Skill expression = new Skill("Expression", 0, 0, htl, per, "HTL", "per", false, 0, 0, "Basic");
		Skill grapple = new Skill("Grapple", 0, 0, str, agl, "STR", "agl", false, 0, 0, "Basic");
		Skill hold = new Skill("Hold", 0, 0, str, agl, "STR", "agl", false, 0, 0, "Basic");
		Skill intuition = new Skill("Intuition", 0, 0, wis, per, "WIS", "per", false, 0, 0, "Basic");
		Skill jumpLeap = new Skill("Jump/Leap", 0, 0, str, per, "STR", "per", false, 0, 0, "Basic");
		Skill liftPull = new Skill("Lift/Pull", 0, 0, str, wit, "STR", "int", false, 0, 0, "Basic");
		Skill resistPain = new Skill("Resist Pain", 0, 0, htl, wil, "HTL", "wil", false, 0, 0, "Basic");
		Skill search = new Skill("Search", 0, 0, per, wit, "PER", "int", false, 0, 0, "Basic");
		Skill spotListen = new Skill("Spot/Listen", 0, 0, per, wit, "PER", "int", false, 0, 0, "Basic");
		Skill speed = new Skill("Speed", 0, 0, agl, htl, "AGL", "htl", false, 0, 0, "Basic");
		Skill stealth = new Skill("Stealth", 0, 0, agl, wit, "AGL", "int", false, 0, 0, "Basic");

		// Add skills to skills array
		basicSkills.add(ballance);
		basicSkills.add(camouflage);
		basicSkills.add(climb);
		basicSkills.add(composure);
		basicSkills.add(deception);
		basicSkills.add(dodge);
		basicSkills.add(endurance);
		basicSkills.add(expression);
		basicSkills.add(grapple);
		basicSkills.add(hold);
		basicSkills.add(intuition);
		basicSkills.add(jumpLeap);
		basicSkills.add(liftPull);
		basicSkills.add(resistPain);
		basicSkills.add(search);
		basicSkills.add(spotListen);
		basicSkills.add(speed);
		basicSkills.add(stealth);
		/*
		 * Ballance(AGL/WIL), 53% Camouflage(INT/per), 45% Climb(STR/AGL), 47%
		 * Composure(WIL/HTL), 30% Deception(SOC/INT), 25% Dodge(AGL/STR), 55%
		 * Endurance(STR/WIL), 44%{+1} Expression(HTL/PER), 37% Grapple(STR/AGL), 47%
		 * Hold(STR/AGL), 47% Intuition(WIL/PER), 31% Jump/Leap(STR/PER), 46%
		 * Lift/Pull(STR/INT), 46% Resist Pain(HTL/WIL), 35% Search(PER/INT), 49%{+3}
		 * Spot/Listen(PER/INT), 49% Speed(AGL/HTL), 54% Stealth(AGL/INT), 55%{+1}
		 */

		// Create Trained Skills
		Skill bow = new Skill("Bow", 0, 0, agl, str, "AGL", "str", false, 0, 0, "Trained");
		Skill calmOther = new Skill("Calm Other", 0, 0, soc, wit, "SOC", "int", false, 0, 0, "Trained");
		Skill diplomacy = new Skill("Diplomacy", 0, 0, soc, wit, "SOC", "int", false, 0, 0, "Trained");
		Skill explosives = new Skill("Explosives", 0, 0, wit, per, "INT", "per", false, 0, 0, "Trained");
		Skill barter = new Skill("Barter", 0, 0, soc, wit, "SOC", "int", false, 0, 0, "Trained");
		Skill command = new Skill("Command", 0, 0, wit, soc, "INT", "soc", false, 0, 0, "Trained");
		Skill tactics = new Skill("Tactics", 0, 0, wit, per, "INT", "per", false, 0, 0, "Trained");
		Skill detMotives = new Skill("Det. Motives", 0, 0, soc, wis, "SOC", "wis", false, 0, 0, "Trained");
		Skill intimidate = new Skill("Intimidate", 0, 0, soc, str, "SOC", "str", false, 0, 0, "Trained");
		Skill investigation = new Skill("Investigation", 0, 0, wit, per, "INT", "per", false, 0, 0, "Trained");
		Skill persuade = new Skill("Persuade", 0, 0, soc, wit, "SOC", "int", false, 0, 0, "Trained");
		Skill performance = new Skill("Performance", 0, 0, soc, per, "SOC", "per", false, 0, 0, "Trained");
		Skill writing = new Skill("Writing", 0, 0, wit, soc, "INT", "soc", false, 0, 0, "Trained");
		Skill digiSystems = new Skill("Digi. Systems", 0, 0, wit, per, "INT", "per", false, 0, 0, "Trained");
		Skill rifle = new Skill("Firearm Rifle", 0, 0, agl, per, "AGL", "per", false, 0, 0, "Trained");
		Skill heavy = new Skill("Firearm Heavy", 0, 0, agl, per, "AGL", "per", false, 0, 0, "Trained");
		Skill launcher = new Skill("Firearm Launcher", 0, 0, agl, per, "AGL", "per", false, 0, 0, "Trained");
		Skill pistol = new Skill("Firearm Pistol", 0, 0, agl, per, "AGL", "per", false, 0, 0, "Trained");
		Skill fighter = new Skill("Fighter", 0, 0, str, wil, "STR", "wil", false, 0, 0, "Trained");
		Skill firstAid = new Skill("First Aid", 0, 0, wit, per, "INT", "per", false, 0, 0, "Trained");
		Skill navigation = new Skill("Navigation", 0, 0, wit, per, "INT", "per", false, 0, 0, "Trained");
		Skill swim = new Skill("Swim", 0, 0, str, htl, "STR", "htl", false, 0, 0, "Trained");
		Skill toss = new Skill("Throw", 0, 0, agl, str, "AGL", "str", false, 0, 0, "Trained");
		Skill crossbow = new Skill("Cross Bow", 0, 0, agl, per, "AGL", "per", false, 0, 0, "Trained");
		Skill knowledgeStreetwise = new Skill("Knowledge Street Wise", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained");
		Skill knowledgePolitical = new Skill("Knowledge Political", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained");
		Skill knowledgeNatural = new Skill("Knowledge Nature", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained");
		Skill knowledgeMilitary = new Skill("Knowledge Military", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained");
		Skill knowledgeHistorical = new Skill("Knowledge Historical", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained");
		Skill knowledgeFolklore = new Skill("Knowledge Folklore", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained");
		Skill knowledgeBestial = new Skill("Knowledge Bestial", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained");
		Skill knowledgeMagical = new Skill("Knowledge Magical", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained");
		Skill knowledgeAlchemical = new Skill("Knowledge Alchemical", 0, 0, wit, wis, "INT", "wis", false, 0, 0, "Trained");
		
		trainedSkills.add(bow);
		trainedSkills.add(calmOther);
		trainedSkills.add(diplomacy);
		trainedSkills.add(explosives);
		trainedSkills.add(barter);
		trainedSkills.add(command);
		trainedSkills.add(tactics);
		trainedSkills.add(detMotives);
		trainedSkills.add(intimidate);
		trainedSkills.add(investigation);
		trainedSkills.add(persuade);
		trainedSkills.add(performance);
		trainedSkills.add(writing);
		trainedSkills.add(digiSystems);
		trainedSkills.add(rifle);
		trainedSkills.add(heavy);
		trainedSkills.add(launcher);
		trainedSkills.add(pistol);
		trainedSkills.add(fighter);
		trainedSkills.add(firstAid);
		trainedSkills.add(navigation);
		trainedSkills.add(swim);
		trainedSkills.add(toss);
		trainedSkills.add(crossbow);
		trainedSkills.add(knowledgeStreetwise);
		trainedSkills.add(knowledgePolitical);
		trainedSkills.add(knowledgeNatural);
		trainedSkills.add(knowledgeMilitary);
		trainedSkills.add(knowledgeHistorical);
		trainedSkills.add(knowledgeFolklore);
		trainedSkills.add(knowledgeBestial);
		trainedSkills.add(knowledgeMagical);
		trainedSkills.add(knowledgeAlchemical);
		
		/*
		 * Bow(AGL/STR), 55% Calm Other(SOC/INT), 25% Diplomacy(SOC/INT), 25%
		 * Explosives(INT/PER), 49% Barter(SOC/INT), 25% Command(INT/SOC), 47%
		 * Tactics(INT/PER), 49% Det. Motives(SOC/INT), 25% Intimidate(SOC/INT), 25%
		 * Investigation(INT/PER), 49% Persuade(SOC/INT), 25% Digi. Systems(INT/PER),
		 * 49% Firearm Rifle(AGL/PER), 77%(3)[+22]{+2} Firearm Heavy(AGL/PER), 55%
		 * Firearm Launcher(AGL/PER), 55% Firearm Pistol(AGL/PER), 74%(3)[+19]{+2}
		 * Fighter(STR/WIL), 61%(3)[+17]{+2} First Aid(INT/PER), 49%{+1}
		 * Navigation(INT/PER), 49% Swim(STR/HTL), 45% Throw(AGL/STR), 55%
		 */

		// Expert Skills
		Skill advancedMedicine = new Skill("Advanced Medicine", 0, 0, wit, per, "INT", "per", false, 0, 0, "Expert");
		Skill craft = new Skill("Craft/Construct/Engineer", 0, 0, wit, str, "INT", "str", false, 0, 0, "Expert");
		Skill pilot = new Skill("Pilot", 0, 0, agl, per, "AGL", "per", false, 0, 0, "Expert");
		Skill animalHandling = new Skill("Animal Handling", 0, 0, soc, wil, "SOC", "wil", false, 0, 0, "Expert");
		Skill ride = new Skill("Ride", 0, 0, agl, str, "AGL", "str", false, 0, 0, "Expert");
		Skill science = new Skill("Science", 0, 0, wit, per, "INT", "per", false, 0, 0, "Expert");
		Skill survival = new Skill("Survival", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill cleanOperations = new Skill("Clean Operations", 0, 0, agl, per, "AGL", "per", false, 0, 0, "Expert");
		Skill covertMovement = new Skill("Covert Movement", 0, 0, agl, wil, "AGL", "wil", false, 0, 0, "Expert");
		Skill recoilControl = new Skill("Recoil Control", 0, 0, str, wil, "STR", "wil", false, 0, 0, "Expert");
		Skill reloadDrills = new Skill("Reload Drills", 0, 0, agl, per, "AGL", "per", false, 0, 0, "Expert");
		Skill triggerDiscipline = new Skill("Trigger Discipline", 0, 0, wit, wil, "INT", "wil", false, 0, 0, "Expert");
		Skill silentOperations = new Skill("Silent Operations", 0, 0, agl, per, "AGL", "per", false, 0, 0, "Expert");
		Skill akSystems = new Skill("AK Systems", 0, 0, agl, str, "AGL", "str", false, 0, 0, "Expert");
		Skill assaultOperations = new Skill("Assault Operations", 0, 0, str, wil, "STR", "wil", false, 0, 0, "Expert");
		Skill authority = new Skill("Authority", 0, 0, str, soc, "STR", "soc", false, 0, 0, "Expert");
		Skill rawPower = new Skill("Raw Power", 0, 0, str, htl, "STR", "htl", false, 0, 0, "Expert");
		Skill arSystems = new Skill("AR Systems", 0, 0, agl, per, "AGL", "per", false, 0, 0, "Expert");
		Skill longRangeOptics = new Skill("Long Range Optics", 0, 0, per, agl, "PER", "agl", false, 0, 0, "Expert");
		Skill negotiations = new Skill("Negotiations", 0, 0, soc, wit, "SOC", "int", false, 0, 0, "Expert");
		Skill smallUnitTactics = new Skill("Small Unit Tactics", 0, 0, wit, per, "INT", "per", false, 0, 0, "Expert");
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

		expertSkills.add(advancedMedicine);
		expertSkills.add(craft);
		expertSkills.add(pilot);
		expertSkills.add(animalHandling);
		expertSkills.add(ride);
		expertSkills.add(science);
		expertSkills.add(survival);
		expertSkills.add(cleanOperations);
		expertSkills.add(covertMovement);
		expertSkills.add(recoilControl);
		expertSkills.add(reloadDrills);
		expertSkills.add(silentOperations);
		expertSkills.add(akSystems);
		expertSkills.add(assaultOperations);
		expertSkills.add(authority);
		expertSkills.add(rawPower);
		expertSkills.add(arSystems);
		expertSkills.add(longRangeOptics);
		expertSkills.add(negotiations);
		expertSkills.add(smallUnitTactics);
		/* Magic */
		expertSkills.add(preservation);
		expertSkills.add(abjuration);
		expertSkills.add(alteration);
		expertSkills.add(conjuration);
		expertSkills.add(divination);
		expertSkills.add(enchantment);
		expertSkills.add(illusion);
		expertSkills.add(invocation);
		expertSkills.add(planar);
		expertSkills.add(necromancy);
		expertSkills.add(wildmagic);
		expertSkills.add(elemental);
		expertSkills.add(enviromental);
		expertSkills.add(psionics);
		expertSkills.add(runic);
		expertSkills.add(alchemical);
		/*
		 * Advanced Medicine(INT/PER), 49% Craft/Construct/Engineer(INT/STR), 49%
		 * Pilot(AGL/PER), 55% Animal Handling(SOC/WIL), 23% Ride(AGL/STR), 55%
		 * Science(INT/PER), 49% Survival(INT/WIL), 47% Clean Operations(AGL/PER),
		 * 60%(1)[+5]{+1} Covert Movement(AGL/WIL), 53% Recoil Control(STR/WIL), 44%
		 * Trigger Discipline(Fighter+Compusure)/2, 43% Reload Drills(AGL/PER), 55%
		 * Silent Operations(AGL/PER), 55% AK Systems(AGL/STR), 55% Assault
		 * Operations(STR/WIL), 44% Authority(STR/SOC), 44% Raw Power(STR/HTL), 45% AR
		 * Systems(AGL/PER), 55% Long Range Optics(PER/AGL), 50% Negotiations(SOC/INT),
		 * 25% Small Unit Tactics(INT/PER), 49%
		 */

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

	public void exportSkills() {

		for (int i = 0; i < basicSkills.size(); i++) {
			System.out.println(basicSkills.get(i).returnSkill());
		}
		for (int i = 0; i < trainedSkills.size(); i++) {
			System.out.println(trainedSkills.get(i).returnSkill());
		}
		for (int i = 0; i < expertSkills.size(); i++) {
			System.out.println(expertSkills.get(i).returnSkill());
		}

	}

	// Exports character to word document
	public void exportCharacter() {

		System.out.println(name);
		System.out.println("STR: " + str);
		System.out.println("INT: " + wit);
		System.out.println("WIS: " + wis);
		System.out.println("SOC: " + soc);
		System.out.println("WIL: " + wil);
		System.out.println("PER: " + per);
		System.out.println("HTL: " + htl);
		System.out.println("AGL: " + agl);
		exportSkills();
	}
	
	
	/*public int roundToNearestTen(int val) {
		return (int) Math.round(((double)val / 10.0)); 
	}*/

	
	
	// Calculates stats and returns a string
	public String exportStats(int encum) {

		
		int slPistol = (trainedSkills.get(17).value + trainedSkills.get(18).value) / 12; // 17
		int slRifle = (trainedSkills.get(14).value + trainedSkills.get(18).value) / 12; // 14
		int slBow = (trainedSkills.get(0).value + trainedSkills.get(18).value) / 12; // 0
		int slCrossbow = (trainedSkills.get(21).value + trainedSkills.get(18).value) / 12; // 21

		int fighterTensPlace = (trainedSkills.get(18).value / 10) % 10;
		int slMelee =  (trainedSkills.get(18).value + basicSkills.get(5).value) / 12;
		
		int AF = 1 + fighterTensPlace / 2;
		
		double bSpeed = baseSpeed(encum);
		double mSpeed = maximumSpeed(encum);
		int meleeISF = slMelee + (wit / 3);
		int rifleISF = slRifle + (wit / 3);
		int pistolISF = slPistol + (wit / 3);
		int bowISF = slBow + (wit / 3);
		int crossbowISF = slCrossbow + (wit / 3);
		int dAlmMelee = defensiveALM(meleeISF);
		int dAlmPistol = defensiveALM(pistolISF);
		int dAlmRifle = defensiveALM(rifleISF);
		int dAlmBow = defensiveALM(bowISF);
		int dAlmCrossbow = defensiveALM(crossbowISF);
		int meleeCA = calculateCA(mSpeed, meleeISF);
		int pistolCA = calculateCA(mSpeed, pistolISF);
		int rifleCA = calculateCA(mSpeed, rifleISF);
		int bowCA = calculateCA(mSpeed, bowISF);
		int crossbowCA = calculateCA(mSpeed, crossbowISF);
		int KO = trainedSkills.get(18).value / 6 * (htl / 6);

		int leadershipSkillFactor = (trainedSkills.get(5).value) / 3 + (trainedSkills.get(6).value / 3);
		int stam = (basicSkills.get(6).value / 10) % 10 + (wil / 10) % 10;
		int FPP = (basicSkills.get(6).value / 10) % 10 + (htl / 10) % 10;

		System.out.println("Melee SAL: " + slMelee);
		System.out.println("Pistol SAL: " + slPistol);
		System.out.println("Rifle SAL: " + slRifle);
		System.out.println("Bow SAL: " + slBow);
		System.out.println("Crossbow SAL: " + slCrossbow);
		System.out.println("Melee ISF: " + meleeISF);
		System.out.println("Rifle ISF: " + rifleISF);
		System.out.println("Pistol ISF: " + pistolISF);
		System.out.println("Bow ISF: " + bowISF);
		System.out.println("Crossbow ISF: " + crossbowISF);
		System.out.println("Base Speed: " + bSpeed);
		System.out.println("Maximum Speed: " + mSpeed);
		System.out.println("dAlmMelee: " + dAlmMelee);
		System.out.println("dAlmPistol: " + dAlmPistol);
		System.out.println("dAlmRifle: " + dAlmRifle);
		System.out.println("dAlmBow: " + dAlmBow);
		System.out.println("dAlmCrossbow: " + dAlmCrossbow);
		System.out.println("Melee CA: " + meleeCA);
		System.out.println("Pistol CA: " + pistolCA);
		System.out.println("Rifle CA: " + rifleCA);
		System.out.println("Bow CA: " + bowCA);
		System.out.println("Crossbow CA: " + crossbowCA);
		System.out.println("KO: " + KO);
		System.out.println("");
		System.out.println("AF: " + AF);
		System.out.println("Melee CoAC: " + AF * meleeCA);
		System.out.println("Pistol CoAC: " + AF * pistolCA);
		System.out.println("Rifle CoAC: " + AF * rifleCA);
		System.out.println("Bow CoAC: " + AF * bowCA);
		System.out.println("Crossbow CoAC: " + AF * crossbowCA);
		System.out
				.println("Coolness Under Fire(CUF): " + (trainedSkills.get(18).value + basicSkills.get(3).value) / 2);
		System.out.println("Leadership Skill Factor(LSF): " + leadershipSkillFactor);
		System.out.println("Command Time(CT): " + calculateCT(leadershipSkillFactor));
		System.out.println("AV(Analectic Value): " + (int)((int) (basicSkills.get(6).value / 3 * (wil / 3) / 2) * (bSpeed / 2)));
		/*System.out.println("Stamina(ST): " + stam);
		System.out.println("Fatigue Point Progress(FPP): " + FPP);*/

		System.out.println("");
		System.out.println("TROS");
		int x1 = per / 9;
		int x2 = agl / 9;
		int x3 = x1 + x2;
		int x4 = x3 / 2;
		System.out.println("Reflexes: " + x4);
		System.out.println("PROF: ");

		return "";
	}

	public int calculateCT(int LSF) {

		int CT = 0;

		int column = 0;
		try {

			FileInputStream excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\Leadership.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			if (LSF <= 10) {

				column = 1;

			} else {

				for (int i = 1; i < 15; i++) {
					if (LSF < worksheet.getRow(i).getCell(0).getNumericCellValue()) {
						column = i;
						break;
					}
				}
			}

			CT = (int) worksheet.getRow(column).getCell(1).getNumericCellValue();

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return CT;

	}

	public int calculateCA(double ms, int isf) {

		int CA = 0;
		int column = 0;
		try {

			FileInputStream excelFile = new FileInputStream(new File(System.getProperty("user.dir") + "\\CA.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			Row row = worksheet.getRow(0);

			if (isf <= 7) {

				column = 1;

			} else {

				for (int i = 1; i < 19; i++) {
					if (isf < row.getCell(i + 1).getNumericCellValue()) {
						column = i;
						break;
					}
				}
			}

			for (int x = 1; x < 22; x++) {

				if (ms == worksheet.getRow(x).getCell(0).getNumericCellValue()) {
					CA = (int) worksheet.getRow(x).getCell(column).getNumericCellValue();
					break;
				}
			}

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return CA;

	}

	public int defensiveALM(int isf) {

		int dAlm = 0;

		try {

			FileInputStream excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\DefensiveALM.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			for (int i = 1; i < 41; i++) {

				if (isf == (int) worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					dAlm = (int) worksheet.getRow(i).getCell(1).getNumericCellValue();

				}

			}

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dAlm;

	}

	public double maximumSpeed(int encum) {
		double baseSpeed = baseSpeed(encum);

		int column = 0;
		try {

			FileInputStream excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\MaximumSpeed.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			Row row = worksheet.getRow(0);

			for (int i = 1; i < 9; i++) {
				if (baseSpeed == row.getCell(i).getNumericCellValue()) {
					column = i;
					break;
				}
			}

			for (int x = 1; x < 22; x++) {

				if ((int) agl / 3 == worksheet.getRow(x).getCell(0).getNumericCellValue()) {
					baseSpeed = worksheet.getRow(x).getCell(column).getNumericCellValue();
				}
			}

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baseSpeed;

	}

	public double baseSpeed(int encum) {

		double baseSpeed = 0;
		int column = 0;
		try {

			FileInputStream excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\BaseSpeed.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			Row row = worksheet.getRow(0);

			if (encum <= 10) {

				column = 1;

			} else {

				for (int i = 1; i < 19; i++) {
					if (encum < row.getCell(i + 1).getNumericCellValue()) {
						column = i;
						break;
					}
				}
			}

			for (int x = 1; x < 22; x++) {

				if ((int) str / 3 == worksheet.getRow(x).getCell(0).getNumericCellValue()) {
					baseSpeed = worksheet.getRow(x).getCell(column).getNumericCellValue();
				}
			}

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baseSpeed;

	}

}
