package Items;

import java.io.Serializable;
import java.util.ArrayList;

import Trooper.Trooper;
import UtilityClasses.DiceRoller;

public class Weapons implements Serializable {
	public ArrayList<Weapons> weapons = new ArrayList<Weapons>();
	public String name;

	// Weapon bonus equals GURPS accuracy x 5, increased five or 10 for rapid fire
	// if applicable, +5% for every 2x magnification
	// Targeted ROF is base 10, modified by weapon bulk, suppressive fire is 10 +
	// ROF / 3 (to get the effective burst) and multiplied by 5.
	// Most of these weapons do not have GURPS stats but they have a stat block that
	// is their insperation
	// Use intuition and balance

	public int weaponBonus;
	public int targetROF;
	public int suppressiveROF;
	public int sab;
	public int fullAutoROF;
	public String type;
	public ArrayList<Trooper> equipedTroopers;
	public ArrayList<Ammo> ammoTypes;
	public ArrayList<PCAmmo> pcAmmoTypes = new ArrayList<PCAmmo>();
	public boolean assembled;
	public int assembleCost;
	public int assembleProgress;
	public int ammoLoaded;
	public int ammoCapacity;
	public int loadTime;
	public int loadProgress;
	public int damage;
	public int damageBonus;
	public int damageMultiplier;
	public int armorPiercing;
	public int collateralDamage;
	public String scopeMagnification = "";
	public int bipod = 0;
	public boolean light = false;
	public boolean laser = false;
	public boolean irLaser = false;
	public boolean boltPump = false;
	public boolean tracers;
	public boolean energyWeapon = true;
	public boolean ionWeapon = false;
	public boolean staticWeapon;
	public int launcherHitBonus;
	public boolean launcherHomingInfantry;
	public boolean launcherHomingVehicle;
	public int homingHitChance;
	public int magnification = 0;
	public int fuze = 0;
	public int armTime = 0;

	public ArrayList<Integer> aimTime = new ArrayList<Integer>();

	public ArrayList<Integer> pen = new ArrayList<Integer>();
	public ArrayList<Integer> dc = new ArrayList<Integer>();
	public ArrayList<Integer> ba = new ArrayList<Integer>();
	public ArrayList<Double> ma = new ArrayList<Double>();
	public ArrayList<String> bshc = new ArrayList<String>();
	public ArrayList<Integer> bc = new ArrayList<Integer>();
	public ArrayList<Integer> ionDamage = new ArrayList<Integer>();

	public boolean shotgun = false;
	public ArrayList<String> bphc = new ArrayList<String>();
	public ArrayList<Integer> salm = new ArrayList<Integer>();

	public CeWeaponStats ceStats = new CeWeaponStats();

	public void createWeapons() {

		Weapons weapon1 = new Weapons();
		weapon1.DC15A();
		weapons.add(weapon1);

		Weapons weapon2 = new Weapons();
		weapon2.E5();
		weapons.add(weapon2);

		Weapons weapon3 = new Weapons();
		weapon3.z6();
		weapons.add(weapon3);

		Weapons weapon4 = new Weapons();
		weapon4.DC15X();
		weapons.add(weapon4);

		Weapons weapon5 = new Weapons();
		weapon5.DC15S();
		weapons.add(weapon5);

		Weapons weapon6 = new Weapons();
		weapon6.E5C();
		weapons.add(weapon6);

		Weapons weapon7 = new Weapons();
		weapon7.E5S();
		weapons.add(weapon7);

		Weapons weapon8 = new Weapons();
		weapon8.MA37();
		weapons.add(weapon8);

		Weapons weapon9 = new Weapons();
		weapon9.M392();
		weapons.add(weapon9);

		Weapons weapon10 = new Weapons();
		weapon10.type51();
		weapons.add(weapon10);

		Weapons weapon11 = new Weapons();
		weapon11.type25Rifle();
		weapons.add(weapon11);

		Weapons weapon12 = new Weapons();
		weapon12.type25Pistol();
		weapons.add(weapon12);

		Weapons weapon13 = new Weapons();
		weapon13.M739();
		weapons.add(weapon13);

		Weapons weapon14 = new Weapons();
		weapon14.DC17M();
		weapons.add(weapon14);

		Weapons weapon15 = new Weapons();
		weapon15.DC17Sniper();
		weapons.add(weapon15);

		Weapons weapon16 = new Weapons();
		weapon16.DC15LE();
		weapons.add(weapon16);

		Weapons weapon17 = new Weapons();
		weapon17.DC15AIon();
		weapons.add(weapon17);

		Weapons weapon18 = new Weapons();
		weapon18.type51DER();
		weapons.add(weapon18);

		Weapons weapon19 = new Weapons();
		weapon19.type50SRS();
		weapons.add(weapon19);

		Weapons weapon20 = new Weapons();
		weapon20.BR55();
		weapons.add(weapon20);

		Weapons weapon21 = new Weapons();
		weapon21.SRS99();
		weapons.add(weapon21);

		Weapons weapon22 = new Weapons();
		weapon22.M6G();
		weapons.add(weapon22);

		Weapons weapon23 = new Weapons();
		weapon23.M7();
		weapons.add(weapon23);

		Weapons weapon24 = new Weapons();
		weapon24.A310();
		weapons.add(weapon24);

		Weapons weapon25 = new Weapons();
		weapon25.EE3();
		weapons.add(weapon25);

		Weapons m1 = new Weapons();
		m1.M1();
		weapons.add(m1);

		Weapons shotgun = new Weapons();
		shotgun.M870();
		weapons.add(shotgun);

		Weapons weaponMelee1 = new Weapons();
		weaponMelee1.vibroKnife();
		weapons.add(weaponMelee1);

		Weapons weaponStatic1 = new Weapons();
		weaponStatic1.EWHB12();
		weapons.add(weaponStatic1);

		Weapons weaponStatic2 = new Weapons();
		weaponStatic2.M41Vulcan();
		weapons.add(weaponStatic2);

		Weapons handGrenade1 = new Weapons();
		handGrenade1.classAThermalDetonator();
		weapons.add(handGrenade1);

		Weapons handGrenade2 = new Weapons();
		handGrenade2.classAHaywireGrenade();
		weapons.add(handGrenade2);

		Weapons handGrenade3 = new Weapons();
		handGrenade3.plasmaGrenade();
		weapons.add(handGrenade3);

		Weapons handGrenade4 = new Weapons();
		handGrenade4.m9fragGrenade();
		weapons.add(handGrenade4);

		Weapons handGrenade5 = new Weapons();
		handGrenade5.rgd5();
		weapons.add(handGrenade5);

		Weapons launcher1 = new Weapons();
		launcher1.RPS6();
		weapons.add(launcher1);

		Weapons launcher2 = new Weapons();
		launcher2.DC40();
		weapons.add(launcher2);

		Weapons launcher3 = new Weapons();
		launcher3.B2RR();
		weapons.add(launcher3);

		Weapons launcher4 = new Weapons();
		launcher4.DC17Rocket();
		weapons.add(launcher4);

		Weapons launcher5 = new Weapons();
		launcher5.MLRS1Hydra();
		weapons.add(launcher5);

		Weapons launcher6 = new Weapons();
		launcher6.Type33FuelRodCannon();
		weapons.add(launcher6);

		Weapons ordnance1 = new Weapons();
		ordnance1.m107HE();
		weapons.add(ordnance1);

	}

	public void DC15A() {
		this.name = "DC15A";
		this.targetROF = 5;
		this.suppressiveROF = 10;
		this.type = "Rifle";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 5;
		this.fullAutoROF = 6;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;

		// Aim time
		this.aimTime.add(-21);
		this.aimTime.add(-12);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(3);
		this.aimTime.add(3);
		this.aimTime.add(5);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(39);
		this.pen.add(38);
		this.pen.add(35);
		this.pen.add(32);
		this.pen.add(29);
		this.pen.add(21);
		this.pen.add(15);
		this.pen.add(11);
		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);
		// BA
		this.ba.add(104);
		this.ba.add(91);
		this.ba.add(77);
		this.ba.add(63);
		this.ba.add(55);
		this.ba.add(39);
		this.ba.add(29);
		this.ba.add(22);

		// Minimum Arc
		this.ma.add(.3);
		this.ma.add(.3);
		this.ma.add(.7);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(3.0);
		this.ma.add(5.0);
		this.ma.add(7.0);

		// Ce stats
		ceStats.baseErgonomics = 50;

	}

	public void DC20() {
		this.name = "DC20";
		this.targetROF = 1;
		this.suppressiveROF = 1;
		this.type = "Rifle";
		this.weaponBonus = 20; // Scope
		this.scopeMagnification = "4-24x";
		this.magnification = 24;
		this.damage = 3;
		this.damageBonus = 5;
		this.damageMultiplier = 5;
		this.armorPiercing = 3;
		this.fullAutoROF = 0;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.bipod = 3;

		// Aim time
		this.aimTime.add(-29);
		this.aimTime.add(-24);
		this.aimTime.add(-18);
		this.aimTime.add(-13);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-1);
		this.aimTime.add(2);
		this.aimTime.add(5);
		this.aimTime.add(7);
		this.aimTime.add(8);
		this.aimTime.add(10);
		this.aimTime.add(11);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(54);
		this.pen.add(53);
		this.pen.add(49);
		this.pen.add(45);
		this.pen.add(41);
		this.pen.add(32);
		this.pen.add(29);
		this.pen.add(18);
		// DC
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(9);
		// BC
		this.ba.add(72);
		this.ba.add(65);
		this.ba.add(58);
		this.ba.add(55);
		this.ba.add(53);
		this.ba.add(51);
		this.ba.add(49);
		this.ba.add(45);

		// Ce stats
		ceStats.baseErgonomics = 50;

	}

	public void DC15LE() {
		this.name = "DC15LE";
		this.targetROF = 5;
		this.suppressiveROF = 15;
		this.type = "Heavy";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 3;
		this.fullAutoROF = 8;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.bipod = 3;

		// Aim time
		this.aimTime.add(-21);
		this.aimTime.add(-12);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);
		this.aimTime.add(1);
		this.aimTime.add(2);
		this.aimTime.add(4);
		this.aimTime.add(4);
		this.aimTime.add(6);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(39);
		this.pen.add(38);
		this.pen.add(35);
		this.pen.add(32);
		this.pen.add(29);
		this.pen.add(21);
		this.pen.add(15);
		this.pen.add(11);

		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);

		// BA
		this.ba.add(104);
		this.ba.add(91);
		this.ba.add(77);
		this.ba.add(63);
		this.ba.add(55);
		this.ba.add(39);
		this.ba.add(29);
		this.ba.add(22);

		// Minimum Arc
		this.ma.add(.4);
		this.ma.add(0.6);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(4.0);
		this.ma.add(7.0);
		this.ma.add(11.0);
		this.ma.add(15.0);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void DC15AIon() {
		this.name = "DC15A-ion";
		this.targetROF = 5;
		this.suppressiveROF = 10;
		this.type = "Rifle";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 5;
		this.fullAutoROF = 6;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;

		// Aim time
		this.aimTime.add(-21);
		this.aimTime.add(-12);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(3);
		this.aimTime.add(3);
		this.aimTime.add(5);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(39);
		this.pen.add(38);
		this.pen.add(35);
		this.pen.add(32);
		this.pen.add(29);
		this.pen.add(21);
		this.pen.add(15);
		this.pen.add(11);
		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);
		// BA
		this.ba.add(104);
		this.ba.add(91);
		this.ba.add(77);
		this.ba.add(63);
		this.ba.add(55);
		this.ba.add(39);
		this.ba.add(29);
		this.ba.add(22);

		// Minimum Arc
		this.ma.add(.3);
		this.ma.add(.3);
		this.ma.add(.7);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(3.0);
		this.ma.add(5.0);
		this.ma.add(7.0);

		ionWeapon = true;
		// Ion Damage
		this.ionDamage.add(600);
		this.ionDamage.add(400);
		this.ionDamage.add(320);
		this.ionDamage.add(120);
		this.ionDamage.add(75);
		this.ionDamage.add(50);
		this.ionDamage.add(35);
		this.ionDamage.add(20);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void DC15X() {
		this.name = "DC15X";
		this.targetROF = 2;
		this.suppressiveROF = 3;
		this.type = "Rifle";
		this.weaponBonus = 20; // Scope
		this.scopeMagnification = "4-12x";
		this.magnification = 12;
		this.damage = 3;
		this.damageBonus = 5;
		this.damageMultiplier = 5;
		this.armorPiercing = 3;
		this.fullAutoROF = 0;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.bipod = 3;

		// Aim time
		this.aimTime.add(-21);
		this.aimTime.add(-12);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);
		this.aimTime.add(1);
		this.aimTime.add(2);
		this.aimTime.add(4);
		this.aimTime.add(5);
		this.aimTime.add(6);
		this.aimTime.add(7);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(60);
		this.pen.add(58);
		this.pen.add(56);
		this.pen.add(52);
		this.pen.add(49);
		this.pen.add(47);
		this.pen.add(44);
		this.pen.add(38);
		// DC
		this.dc.add(9);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);
		// BC
		this.ba.add(72);
		this.ba.add(65);
		this.ba.add(58);
		this.ba.add(55);
		this.ba.add(53);
		this.ba.add(51);
		this.ba.add(49);
		this.ba.add(45);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void WestarM5() {
		this.name = "Westar M5";
		this.targetROF = 5;
		this.suppressiveROF = 10;
		this.type = "Rifle";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 3;
		this.fullAutoROF = 9;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;

		// Aim time
		this.aimTime.add(-21);
		this.aimTime.add(-12);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(3);
		this.aimTime.add(3);
		this.aimTime.add(5);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(27);
		this.pen.add(26);
		this.pen.add(24);
		this.pen.add(22);
		this.pen.add(20);
		this.pen.add(15);
		this.pen.add(11);
		this.pen.add(8);
		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);
		// BC
		this.ba.add(51);
		this.ba.add(45);
		this.ba.add(38);
		this.ba.add(31);
		this.ba.add(27);
		this.ba.add(19);
		this.ba.add(14);
		this.ba.add(11);

		// Minimum Arc
		this.ma.add(.4);
		this.ma.add(.8);
		this.ma.add(2.0);
		this.ma.add(3.0);
		this.ma.add(5.0);
		this.ma.add(10.0);
		this.ma.add(15.0);
		this.ma.add(20.0);

		// Ce stats
		ceStats.baseErgonomics = 65;
	}

	public void DC17M() {
		this.name = "DC17m";
		this.targetROF = 5;
		this.suppressiveROF = 10;
		this.type = "Rifle";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 4;
		this.fullAutoROF = 7;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;

		// Aim time
		this.aimTime.add(-19);
		this.aimTime.add(-10);
		this.aimTime.add(-7);
		this.aimTime.add(-5);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-2);
		this.aimTime.add(-1);
		this.aimTime.add(0);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(33);
		this.pen.add(31);
		this.pen.add(28);
		this.pen.add(24);
		this.pen.add(21);
		this.pen.add(12);
		this.pen.add(7);
		this.pen.add(4);
		// DC
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);
		this.dc.add(5);
		this.dc.add(3);
		this.dc.add(2);
		this.dc.add(1);
		this.dc.add(1);
		// BC
		this.ba.add(61);
		this.ba.add(53);
		this.ba.add(45);
		this.ba.add(37);
		this.ba.add(33);
		this.ba.add(25);
		this.ba.add(19);
		this.ba.add(15);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(.5);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(3.0);
		this.ma.add(6.0);
		this.ma.add(10.0);
		this.ma.add(13.0);

		// Ce stats
		ceStats.baseErgonomics = 70;
	}

	public void DC17Sniper() {
		this.name = "DC17 Sniper";
		this.targetROF = 2;
		this.suppressiveROF = 3;
		this.type = "Rifle";
		this.weaponBonus = 20; // Scope
		this.scopeMagnification = "4-24x";
		this.magnification = 24;
		this.damage = 3;
		this.damageBonus = 5;
		this.damageMultiplier = 5;
		this.armorPiercing = 3;
		this.fullAutoROF = 0;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.bipod = 3;

		// Aim time
		this.aimTime.add(-21);
		this.aimTime.add(-12);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);
		this.aimTime.add(2);
		this.aimTime.add(4);
		this.aimTime.add(4);
		this.aimTime.add(6);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(54);
		this.pen.add(53);
		this.pen.add(49);
		this.pen.add(45);
		this.pen.add(41);
		this.pen.add(29);
		this.pen.add(21);
		this.pen.add(15);
		// DC
		this.dc.add(9);
		this.dc.add(9);
		this.dc.add(9);
		this.dc.add(9);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(6);
		// BC
		this.ba.add(72);
		this.ba.add(65);
		this.ba.add(58);
		this.ba.add(55);
		this.ba.add(53);
		this.ba.add(51);
		this.ba.add(49);
		this.ba.add(45);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void DC15S() {
		this.name = "DC15S";
		this.targetROF = 6;
		this.suppressiveROF = 12;
		this.type = "Subgun";
		this.weaponBonus = 0;
		this.damage = 3;
		this.damageBonus = -2;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.tracers = true;
		this.sab = 4;
		this.fullAutoROF = 6;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		// PEN
		this.pen.add(39);
		this.pen.add(39);
		this.pen.add(38);
		this.pen.add(36);
		this.pen.add(33);
		this.pen.add(31);
		this.pen.add(26);
		this.pen.add(23);
		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(6);

		this.aimTime.add(-21);
		this.aimTime.add(-10);
		this.aimTime.add(-7);
		this.aimTime.add(-5);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-3);
		this.aimTime.add(-3);
		this.aimTime.add(-2);
		this.aimTime.add(-2);
		this.aimTime.add(-2);

		// BC
		this.ba.add(61);
		this.ba.add(51);
		this.ba.add(42);
		this.ba.add(35);
		this.ba.add(30);
		this.ba.add(20);
		this.ba.add(15);
		this.ba.add(11);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(.3);
		this.ma.add(.7);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(3.0);
		this.ma.add(5.0);
		this.ma.add(7.0);

		// Ce stats
		ceStats.baseErgonomics = 70;
	}

	public void M1() {
		this.name = "M1";
		this.targetROF = 5;
		this.suppressiveROF = 1;
		this.type = "Rifle";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "12-24x";
		this.magnification = 24;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 2;
		this.fullAutoROF = 8;
		this.tracers = false;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.energyWeapon = false;

		// Aim time
		this.aimTime.add(-29);
		this.aimTime.add(-24);
		this.aimTime.add(-18);
		this.aimTime.add(-13);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-1);
		this.aimTime.add(2);
		this.aimTime.add(5);
		this.aimTime.add(7);
		this.aimTime.add(8);
		this.aimTime.add(10);
		this.aimTime.add(11);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(82);
		this.pen.add(79);
		this.pen.add(75);
		this.pen.add(71);
		this.pen.add(68);
		this.pen.add(65);
		this.pen.add(60);
		this.pen.add(57);

		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(5);

		// BA
		this.ba.add(116);
		this.ba.add(104);
		this.ba.add(89);
		this.ba.add(78);
		this.ba.add(69);
		this.ba.add(53);
		this.ba.add(42);
		this.ba.add(35);

		// Ce stats
		ceStats.baseErgonomics = 35;
	}

	public void z6() {
		this.name = "Z6";
		this.targetROF = 10;
		this.suppressiveROF = 20;
		this.type = "Heavy";
		this.weaponBonus = 0;
		this.damage = 3;
		this.damageBonus = -2;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 2;
		this.fullAutoROF = 12;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.bipod = 3;
		this.irLaser = true;

		// Aim time
		this.aimTime.add(-22);
		this.aimTime.add(-12);
		this.aimTime.add(-9);
		this.aimTime.add(-7);
		this.aimTime.add(-6);
		this.aimTime.add(-5);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-2);
		this.aimTime.add(-1);
		this.aimTime.add(0);

		// PEN
		this.pen.add(39);
		this.pen.add(39);
		this.pen.add(38);
		this.pen.add(36);
		this.pen.add(33);
		this.pen.add(31);
		this.pen.add(26);
		this.pen.add(23);
		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(6);

		// BC
		this.ba.add(61);
		this.ba.add(51);
		this.ba.add(42);
		this.ba.add(35);
		this.ba.add(30);
		this.ba.add(20);
		this.ba.add(15);
		this.ba.add(11);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(.3);
		this.ma.add(.5);
		this.ma.add(0.7);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(3.0);
		this.ma.add(6.0);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void E5() {
		this.name = "E5";
		this.targetROF = 4;
		this.suppressiveROF = 8;
		this.type = "Rifle";
		this.weaponBonus = 0;
		this.damage = 3;
		this.damageBonus = -2;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.tracers = true;
		this.sab = 5;
		this.fullAutoROF = 5;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;

		this.aimTime.add(-22);
		this.aimTime.add(-12);
		this.aimTime.add(-9);
		this.aimTime.add(-7);
		this.aimTime.add(-6);
		this.aimTime.add(-5);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-3);
		this.aimTime.add(-3);
		this.aimTime.add(-3);

		// PEN
		this.pen.add(41);
		this.pen.add(40);
		this.pen.add(39);
		this.pen.add(37);
		this.pen.add(34);
		this.pen.add(33);
		this.pen.add(27);
		this.pen.add(24);
		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);

		// BC
		this.ba.add(61);
		this.ba.add(51);
		this.ba.add(42);
		this.ba.add(35);
		this.ba.add(30);
		this.ba.add(20);
		this.ba.add(15);
		this.ba.add(11);

		// Minimum Arc
		this.ma.add(.6);
		this.ma.add(1.0);
		this.ma.add(3.0);
		this.ma.add(4.0);
		this.ma.add(6.0);
		this.ma.add(13.0);
		this.ma.add(19.0);
		this.ma.add(25.0);

		// Ce stats
		ceStats.baseErgonomics = 60;
	}

	public void E5S() {
		this.name = "E5S";
		this.targetROF = 2;
		this.suppressiveROF = 5;
		this.type = "Rifle";
		this.weaponBonus = 15; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 5;
		this.damageMultiplier = 5;
		this.armorPiercing = 3;
		this.sab = 5;
		this.fullAutoROF = 1;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.bipod = 3;
		this.irLaser = true;

		this.aimTime.add(-21);
		this.aimTime.add(-12);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(3);
		this.aimTime.add(5);

		// PEN
		this.pen.add(37);
		this.pen.add(34);
		this.pen.add(32);
		this.pen.add(27);
		this.pen.add(24);
		this.pen.add(15);
		this.pen.add(10);
		this.pen.add(6);

		// DC
		this.dc.add(5);
		this.dc.add(5);
		this.dc.add(4);
		this.dc.add(4);
		this.dc.add(4);
		this.dc.add(2);
		this.dc.add(1);
		this.dc.add(1);

		// BC
		this.ba.add(61);
		this.ba.add(51);
		this.ba.add(42);
		this.ba.add(35);
		this.ba.add(30);
		this.ba.add(20);
		this.ba.add(15);
		this.ba.add(11);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void E5C() {
		this.name = "E5C";
		this.targetROF = 8;
		this.suppressiveROF = 16;
		this.type = "Heavy";
		this.weaponBonus = 0;
		this.damage = 3;
		this.damageBonus = -1;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.tracers = true;
		this.sab = 2;
		this.fullAutoROF = 7;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.bipod = 3;
		this.irLaser = true;

		this.aimTime.add(-24);
		this.aimTime.add(-14);
		this.aimTime.add(-10);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-5);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-2);
		this.aimTime.add(-1);
		this.aimTime.add(0);

		// PEN
		this.pen.add(41);
		this.pen.add(40);
		this.pen.add(39);
		this.pen.add(37);
		this.pen.add(34);
		this.pen.add(33);
		this.pen.add(27);
		this.pen.add(24);
		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);

		// BC
		this.ba.add(61);
		this.ba.add(51);
		this.ba.add(42);
		this.ba.add(35);
		this.ba.add(30);
		this.ba.add(20);
		this.ba.add(15);
		this.ba.add(11);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(.3);
		this.ma.add(.7);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(4.0);
		this.ma.add(6.0);
		this.ma.add(8.0);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void MA37() {
		this.name = "MA37";
		this.type = "Rifle";
		this.targetROF = 5;
		this.suppressiveROF = 10;
		this.type = "Rifle";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 5;
		this.fullAutoROF = 6;
		this.tracers = false;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.energyWeapon = false;

		// Aim time
		this.aimTime.add(-21);
		this.aimTime.add(-11);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-2);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(2);
		this.aimTime.add(3);
		this.aimTime.add(5);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(26);
		this.pen.add(25);
		this.pen.add(23);
		this.pen.add(21);
		this.pen.add(20);
		this.pen.add(14);
		this.pen.add(10);
		this.pen.add(7);
		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);

		// BA
		this.ba.add(70);
		this.ba.add(61);
		this.ba.add(52);
		this.ba.add(42);
		this.ba.add(37);
		this.ba.add(26);
		this.ba.add(19);
		this.ba.add(15);

		// Minimum Arc
		this.ma.add(.4);
		this.ma.add(.7);
		this.ma.add(1.0);
		this.ma.add(3.0);
		this.ma.add(4.0);
		this.ma.add(8.0);
		this.ma.add(13.0);
		this.ma.add(18.0);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void M392() {
		this.name = "M392 DMR";
		this.type = "Rifle";
		this.targetROF = 5;
		this.suppressiveROF = 3;
		this.type = "Rifle";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 5;
		this.fullAutoROF = 2;
		this.tracers = false;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.energyWeapon = false;

		// Aim time
		this.aimTime.add(-21);
		this.aimTime.add(-12);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);
		this.aimTime.add(1);
		this.aimTime.add(2);
		this.aimTime.add(4);
		this.aimTime.add(4);
		this.aimTime.add(6);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(30);
		this.pen.add(29);
		this.pen.add(27);
		this.pen.add(25);
		this.pen.add(22);
		this.pen.add(16);
		this.pen.add(12);
		this.pen.add(8);

		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);

		// BA
		this.ba.add(88);
		this.ba.add(76);
		this.ba.add(65);
		this.ba.add(53);
		this.ba.add(46);
		this.ba.add(33);
		this.ba.add(25);
		this.ba.add(19);

		// Minimum Arc
		this.ma.add(.6);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(4.0);
		this.ma.add(6.0);
		this.ma.add(12.0);
		this.ma.add(19.0);
		this.ma.add(25.0);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void BR55() {
		this.name = "BR55";
		this.type = "Rifle";
		this.targetROF = 5;
		this.suppressiveROF = 6;
		this.type = "Rifle";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 8;
		this.fullAutoROF = 6;
		this.tracers = false;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.energyWeapon = false;

		// Aim time
		this.aimTime.add(-22);
		this.aimTime.add(-13);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(2);
		this.aimTime.add(3);
		this.aimTime.add(4);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(34);
		this.pen.add(32);
		this.pen.add(30);
		this.pen.add(28);
		this.pen.add(25);
		this.pen.add(18);
		this.pen.add(13);
		this.pen.add(9);

		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);

		// BA
		this.ba.add(77);
		this.ba.add(67);
		this.ba.add(57);
		this.ba.add(47);
		this.ba.add(40);
		this.ba.add(29);
		this.ba.add(21);
		this.ba.add(16);

		// Minimum Arc
		this.ma.add(.7);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(5.0);
		this.ma.add(7.0);
		this.ma.add(14.0);
		this.ma.add(23.0);
		this.ma.add(30.0);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void M739() {
		this.name = "M739 SAW";
		this.type = "Heavy";
		this.targetROF = 5;
		this.suppressiveROF = 20;
		this.type = "Heavy";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 3;
		this.fullAutoROF = 8;
		this.tracers = false;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.energyWeapon = false;

		// Aim time
		this.aimTime.add(-27);
		this.aimTime.add(-18);
		this.aimTime.add(-10);
		this.aimTime.add(-7);
		this.aimTime.add(-5);
		this.aimTime.add(-4);
		this.aimTime.add(-2);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(2);
		this.aimTime.add(4);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(30);
		this.pen.add(29);
		this.pen.add(27);
		this.pen.add(25);
		this.pen.add(23);
		this.pen.add(16);
		this.pen.add(12);
		this.pen.add(8);

		// DC
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);

		// BA
		this.ba.add(88);
		this.ba.add(76);
		this.ba.add(65);
		this.ba.add(53);
		this.ba.add(46);
		this.ba.add(33);
		this.ba.add(25);
		this.ba.add(19);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(0.5);
		this.ma.add(0.8);
		this.ma.add(2.0);
		this.ma.add(2.0);
		this.ma.add(5.0);
		this.ma.add(7.0);
		this.ma.add(10.0);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void M6G() {
		this.name = "M6G";
		this.targetROF = 5;
		this.suppressiveROF = 10;
		this.type = "Pistol";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 4;
		this.fullAutoROF = 1;
		this.tracers = false;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.energyWeapon = false;

		// Aim time
		this.aimTime.add(-22);
		this.aimTime.add(-13);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(2);
		this.aimTime.add(3);
		this.aimTime.add(4);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(6);
		this.pen.add(5);
		this.pen.add(5);
		this.pen.add(4);
		this.pen.add(3);
		this.pen.add(2);
		this.pen.add(1);
		this.pen.add(1);

		// DC
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);

		// BA
		this.ba.add(104);
		this.ba.add(91);
		this.ba.add(77);
		this.ba.add(63);
		this.ba.add(55);
		this.ba.add(39);
		this.ba.add(29);
		this.ba.add(22);

		// Ce stats
		ceStats.baseErgonomics = 75;
	}

	public void A310() {
		this.name = "A310";
		this.targetROF = 6;
		this.suppressiveROF = 12;
		this.type = "Rifle";
		this.weaponBonus = 0;
		this.damage = 3;
		this.damageBonus = -2;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.tracers = true;
		this.sab = 3;
		this.fullAutoROF = 7;
		this.light = true;
		this.laser = true;
		this.irLaser = true;

		// PEN
		this.pen.add(42);
		this.pen.add(36);
		this.pen.add(31);
		this.pen.add(27);
		this.pen.add(25);
		this.pen.add(20);
		this.pen.add(17);
		this.pen.add(15);
		// DC
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);
		this.dc.add(5);
		this.dc.add(4);
		this.dc.add(4);

		this.aimTime.add(-19);
		this.aimTime.add(-11);
		this.aimTime.add(-9);
		this.aimTime.add(-7);
		this.aimTime.add(-6);
		this.aimTime.add(-5);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-2);
		this.aimTime.add(-1);
		this.aimTime.add(0);

		// BC
		this.ba.add(70);
		this.ba.add(62);
		this.ba.add(53);
		this.ba.add(46);
		this.ba.add(41);
		this.ba.add(32);
		this.ba.add(26);
		this.ba.add(22);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(.5);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(5.0);
		this.ma.add(5.0);
		this.ma.add(7.0);
		this.ma.add(10.0);

		// Ce stats
		ceStats.baseErgonomics = 65;
	}

	public void EE3() {
		this.name = "EE3";
		this.targetROF = 6;
		this.suppressiveROF = 12;
		this.type = "Rifle";
		this.weaponBonus = 0;
		this.damage = 3;
		this.damageBonus = -2;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.tracers = true;
		this.sab = 4;
		this.fullAutoROF = 5;
		this.light = true;
		this.laser = true;
		this.irLaser = true;

		// PEN
		this.pen.add(42);
		this.pen.add(36);
		this.pen.add(31);
		this.pen.add(27);
		this.pen.add(25);
		this.pen.add(20);
		this.pen.add(17);
		this.pen.add(15);
		// DC
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);
		this.dc.add(5);
		this.dc.add(4);
		this.dc.add(4);

		this.aimTime.add(-19);
		this.aimTime.add(-11);
		this.aimTime.add(-9);
		this.aimTime.add(-7);
		this.aimTime.add(-6);
		this.aimTime.add(-5);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-2);
		this.aimTime.add(-1);
		this.aimTime.add(0);

		// BC
		this.ba.add(70);
		this.ba.add(62);
		this.ba.add(53);
		this.ba.add(46);
		this.ba.add(41);
		this.ba.add(32);
		this.ba.add(26);
		this.ba.add(22);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(.5);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(5.0);
		this.ma.add(5.0);
		this.ma.add(7.0);
		this.ma.add(10.0);

		// Ce stats
		ceStats.baseErgonomics = 65;
	}

	public void M7() {
		this.name = "M7";
		this.targetROF = 10;
		this.suppressiveROF = 20;
		this.type = "Subgun";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "4-6x";
		this.magnification = 6;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 2;
		this.fullAutoROF = 8;
		this.tracers = false;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.energyWeapon = false;

		// Aim time
		this.aimTime.add(-21);
		this.aimTime.add(-10);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-2);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(2);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(7);
		this.pen.add(7);
		this.pen.add(7);
		this.pen.add(6);
		this.pen.add(5);
		this.pen.add(3);
		this.pen.add(2);
		this.pen.add(1);

		// DC
		this.dc.add(4);
		this.dc.add(4);
		this.dc.add(4);
		this.dc.add(3);
		this.dc.add(3);
		this.dc.add(1);
		this.dc.add(1);
		this.dc.add(1);

		// BA
		this.ba.add(63);
		this.ba.add(53);
		this.ba.add(44);
		this.ba.add(36);
		this.ba.add(31);
		this.ba.add(21);
		this.ba.add(15);
		this.ba.add(12);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(0.3);
		this.ma.add(0.5);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(3.0);
		this.ma.add(5.0);
		this.ma.add(7.0);

		// Ce stats
		ceStats.baseErgonomics = 60;

	}

	public void SRS99() {
		this.name = "SRS99";
		this.targetROF = 5;
		this.suppressiveROF = 1;
		this.type = "Rifle";
		this.weaponBonus = 10; // Scope
		this.scopeMagnification = "12-24x";
		this.magnification = 24;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 2;
		this.fullAutoROF = 8;
		this.tracers = false;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.energyWeapon = false;

		// Aim time
		this.aimTime.add(-29);
		this.aimTime.add(-24);
		this.aimTime.add(-18);
		this.aimTime.add(-13);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-1);
		this.aimTime.add(2);
		this.aimTime.add(5);
		this.aimTime.add(7);
		this.aimTime.add(8);
		this.aimTime.add(10);
		this.aimTime.add(11);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(75);
		this.pen.add(74);
		this.pen.add(72);
		this.pen.add(68);
		this.pen.add(65);
		this.pen.add(54);
		this.pen.add(45);
		this.pen.add(38);

		// DC
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);

		// BA
		this.ba.add(116);
		this.ba.add(104);
		this.ba.add(89);
		this.ba.add(78);
		this.ba.add(69);
		this.ba.add(53);
		this.ba.add(42);
		this.ba.add(35);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void type51() {
		this.name = "Type-51 Carbine";
		this.targetROF = 5;
		this.suppressiveROF = 8;
		this.type = "Rifle";
		this.weaponBonus = 20;
		this.scopeMagnification = "4-6x";
		this.weaponBonus = 0;
		this.damage = 3;
		this.damageBonus = -2;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.tracers = true;
		this.sab = 4;
		this.fullAutoROF = 5;
		this.tracers = false;
		this.light = true;
		this.laser = true;
		this.irLaser = true;

		this.aimTime.add(-21);
		this.aimTime.add(-12);
		this.aimTime.add(-8);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);
		this.aimTime.add(1);
		this.aimTime.add(2);
		this.aimTime.add(4);
		this.aimTime.add(4);
		this.aimTime.add(6);

		// PEN
		this.pen.add(33);
		this.pen.add(32);
		this.pen.add(29);
		this.pen.add(27);
		this.pen.add(25);
		this.pen.add(18);
		this.pen.add(13);
		this.pen.add(9);
		this.pen.add(5);

		// DC
		this.dc.add(9);
		this.dc.add(9);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(7);
		this.dc.add(7);
		this.dc.add(6);
		this.dc.add(3);

		// BA
		this.ba.add(111);
		this.ba.add(96);
		this.ba.add(82);
		this.ba.add(67);
		this.ba.add(58);
		this.ba.add(42);
		this.ba.add(31);
		this.ba.add(24);
		this.ba.add(10);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void type25Pistol() {
		this.name = "Type-25 Pistol";
		this.type = "Pistol";
		this.targetROF = 6;
		this.suppressiveROF = 12;
		this.weaponBonus = 0;
		this.damage = 3;
		this.damageBonus = -2;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.tracers = true;
		this.sab = 5;
		this.fullAutoROF = 5;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.energyWeapon = true;

		this.aimTime.add(-19);
		this.aimTime.add(-11);
		this.aimTime.add(-8);
		this.aimTime.add(-7);
		this.aimTime.add(-6);
		this.aimTime.add(-5);

		// PEN
		this.pen.add(32);
		this.pen.add(28);
		this.pen.add(24);
		this.pen.add(21);
		this.pen.add(18);
		this.pen.add(11);
		this.pen.add(7);
		this.pen.add(5);

		// DC
		this.dc.add(5);
		this.dc.add(4);
		this.dc.add(3);
		this.dc.add(3);
		this.dc.add(3);
		this.dc.add(2);
		this.dc.add(1);
		this.dc.add(1);

		// BC
		this.ba.add(60);
		this.ba.add(50);
		this.ba.add(42);
		this.ba.add(34);
		this.ba.add(29);
		this.ba.add(20);
		this.ba.add(14);
		this.ba.add(11);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(0.3);
		this.ma.add(0.5);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(3.0);
		this.ma.add(5.0);
		this.ma.add(7.0);

		// Ce stats
		ceStats.baseErgonomics = 75;
	}

	public void type25Rifle() {
		this.name = "Type-25 Rifle";
		this.type = "Subgun";
		this.targetROF = 8;
		this.suppressiveROF = 14;
		this.weaponBonus = -15;
		this.scopeMagnification = "4-6x";
		this.weaponBonus = 0;
		this.damage = 3;
		this.damageBonus = -2;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.tracers = true;
		this.sab = 4;
		this.fullAutoROF = 5;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.energyWeapon = true;

		this.aimTime.add(-21);
		this.aimTime.add(-10);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-2);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(2);

		// PEN
		this.pen.add(43);
		this.pen.add(40);
		this.pen.add(37);
		this.pen.add(32);
		this.pen.add(28);
		this.pen.add(18);
		this.pen.add(11);
		this.pen.add(7);

		// DC
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);
		this.dc.add(5);
		this.dc.add(5);
		this.dc.add(3);
		this.dc.add(2);
		this.dc.add(2);

		// BA
		this.ba.add(63);
		this.ba.add(53);
		this.ba.add(44);
		this.ba.add(36);
		this.ba.add(31);
		this.ba.add(21);
		this.ba.add(15);
		this.ba.add(12);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(0.3);
		this.ma.add(0.5);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(3.0);
		this.ma.add(5.0);
		this.ma.add(7.0);

		// Ce stats
		ceStats.baseErgonomics = 65;
	}

	public void type51DER() {
		this.name = "Type-51 DER";
		this.type = "Heavy";
		this.targetROF = 8;
		this.suppressiveROF = 20;
		this.weaponBonus = -15;
		this.scopeMagnification = "4-6x";
		this.weaponBonus = 0;
		this.damage = 3;
		this.damageBonus = -2;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.tracers = true;
		this.sab = 3;
		this.fullAutoROF = 6;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.irLaser = true;
		this.energyWeapon = true;

		this.aimTime.add(-27);
		this.aimTime.add(-18);
		this.aimTime.add(-10);
		this.aimTime.add(-7);
		this.aimTime.add(-5);
		this.aimTime.add(-4);
		this.aimTime.add(-2);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(2);
		this.aimTime.add(4);

		// PEN
		this.pen.add(46);
		this.pen.add(43);
		this.pen.add(40);
		this.pen.add(33);
		this.pen.add(30);
		this.pen.add(19);
		this.pen.add(12);
		this.pen.add(8);

		// DC
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(5);
		this.dc.add(5);
		this.dc.add(5);
		this.dc.add(3);
		this.dc.add(2);
		this.dc.add(2);

		// BA
		this.ba.add(76);
		this.ba.add(64);
		this.ba.add(53);
		this.ba.add(43);
		this.ba.add(37);
		this.ba.add(25);
		this.ba.add(18);
		this.ba.add(14);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(0.2);
		this.ma.add(0.4);
		this.ma.add(0.8);
		this.ma.add(2.0);
		this.ma.add(2.0);
		this.ma.add(4.0);
		this.ma.add(6.0);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void type50SRS() {
		this.name = "Type-50 SRS";
		this.type = "Rifle";
		this.targetROF = 8;
		this.suppressiveROF = 2;
		this.weaponBonus = -15;
		this.scopeMagnification = "12-24x";
		this.weaponBonus = 0;
		this.damage = 3;
		this.damageBonus = -2;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.tracers = true;
		this.sab = 3;
		this.fullAutoROF = 1;
		this.tracers = false;
		this.light = true;
		this.laser = true;
		this.irLaser = true;

		this.aimTime.add(-29);
		this.aimTime.add(-24);
		this.aimTime.add(-18);
		this.aimTime.add(-13);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-1);
		this.aimTime.add(2);
		this.aimTime.add(5);
		this.aimTime.add(7);
		this.aimTime.add(8);
		this.aimTime.add(10);
		this.aimTime.add(11);

		// PEN
		this.pen.add(99);
		this.pen.add(97);
		this.pen.add(94);
		this.pen.add(90);
		this.pen.add(86);
		this.pen.add(82);
		this.pen.add(78);
		this.pen.add(73);
		this.pen.add(49);

		// DC
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);
		this.dc.add(8);

		// BA
		this.ba.add(198);
		this.ba.add(178);
		this.ba.add(152);
		this.ba.add(133);
		this.ba.add(118);
		this.ba.add(91);
		this.ba.add(72);
		this.ba.add(65);
		this.ba.add(30);

		// Ce stats
		ceStats.baseErgonomics = 50;

	}

	// MELEE WEAPONS \\
	public void vibroKnife() {
		this.name = "Vibroknife";
		this.targetROF = 0;
		this.suppressiveROF = 0;
		this.type = "Melee";
		this.weaponBonus = 0;
		this.damage = 0;
		this.damageBonus = 2;
		this.damageMultiplier = 2;
		this.armorPiercing = 2;
		this.tracers = false;

		// PEN
		this.pen.add(30);

		// DC
		this.dc.add(1);

	}

	public void M41Vulcan() {
		this.name = "M41 Vulcan";
		this.targetROF = 8;
		this.suppressiveROF = 20;
		this.type = "Static";
		this.equipedTroopers = new ArrayList<>();
		this.scopeMagnification = "4-6x";
		this.weaponBonus = 10;
		this.damage = 16;
		this.damageBonus = 0;
		this.damageMultiplier = 0;
		this.armorPiercing = 3;
		this.tracers = true;
		this.assembled = false;
		this.assembleCost = 6;
		this.assembleProgress = 0;
		this.ammoCapacity = 200;
		this.ammoLoaded = 200;
		this.loadTime = 6;
		this.loadProgress = 0;
		this.sab = 1;
		this.fullAutoROF = 6;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.staticWeapon = true;
		this.irLaser = true;

		this.aimTime.add(-36);
		this.aimTime.add(-26);
		this.aimTime.add(-21);
		this.aimTime.add(-16);
		this.aimTime.add(-12);
		this.aimTime.add(-9);
		this.aimTime.add(-7);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-1);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(54);
		this.pen.add(54);
		this.pen.add(52);
		this.pen.add(50);
		this.pen.add(48);
		this.pen.add(43);
		this.pen.add(35);
		this.pen.add(26);
		// DC
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		// BA
		this.ba.add(47);
		this.ba.add(45);
		this.ba.add(43);
		this.ba.add(38);
		this.ba.add(35);
		this.ba.add(25);
		this.ba.add(20);
		this.ba.add(16);

		// Minimum Arc
		this.ma.add(.3);
		this.ma.add(.3);
		this.ma.add(.4);
		this.ma.add(.5);
		this.ma.add(.7);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(3.0);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	// STATIC WEAPONS \\
	public void EWHB12() {
		this.name = "EWHB-12 Heavy Repeating Blaster";
		this.targetROF = 8;
		this.suppressiveROF = 20;
		this.type = "Static";
		this.equipedTroopers = new ArrayList<>();
		this.scopeMagnification = "4-6x";
		this.weaponBonus = 10;
		this.damage = 16;
		this.damageBonus = 0;
		this.damageMultiplier = 0;
		this.armorPiercing = 3;
		this.tracers = true;
		this.assembled = false;
		this.assembleCost = 6;
		this.assembleProgress = 0;
		this.ammoCapacity = 200;
		this.ammoLoaded = 200;
		this.loadTime = 6;
		this.loadProgress = 0;
		this.sab = 1;
		this.fullAutoROF = 10;
		this.tracers = true;
		this.light = true;
		this.laser = true;
		this.staticWeapon = true;
		this.irLaser = true;

		this.aimTime.add(-36);
		this.aimTime.add(-25);
		this.aimTime.add(-16);
		this.aimTime.add(-11);
		this.aimTime.add(-9);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(2);
		this.aimTime.add(3);
		this.aimTime.add(4);

		// 10 20 40 70 100 200 300 400
		// PEN
		this.pen.add(60);
		this.pen.add(55);
		this.pen.add(53);
		this.pen.add(51);
		this.pen.add(49);
		this.pen.add(45);
		this.pen.add(38);
		this.pen.add(28);
		// DC
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		this.dc.add(10);
		// BA
		this.ba.add(47);
		this.ba.add(45);
		this.ba.add(43);
		this.ba.add(38);
		this.ba.add(35);
		this.ba.add(25);
		this.ba.add(20);
		this.ba.add(16);

		// Minimum Arc
		this.ma.add(.2);
		this.ma.add(.2);
		this.ma.add(.3);
		this.ma.add(.4);
		this.ma.add(.6);
		this.ma.add(1.0);
		this.ma.add(2.0);
		this.ma.add(3.0);

		// Ce stats
		ceStats.baseErgonomics = 50;

	}

	public void rgd5() {
		this.name = "RGD-5";
		this.type = "Grenade";
		this.damage = 8;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.collateralDamage = 50;
		this.energyWeapon = false;
		this.fuze = 2;
		this.armTime = 3;

		aimTime.add(-26);
		aimTime.add(-18);
		aimTime.add(-14);
		aimTime.add(-12);
		aimTime.add(-11);
		aimTime.add(-11);
		aimTime.add(-10);
		aimTime.add(-10);
		// Starts from 0, no C
		this.pen.add(3);
		this.pen.add(3);
		this.pen.add(3);
		this.pen.add(2);
		this.pen.add(2);
		this.pen.add(1);

		this.dc.add(3);
		this.dc.add(3);
		this.dc.add(2);
		this.dc.add(2);
		this.dc.add(2);
		this.dc.add(1);

		this.bshc.add("*9");
		this.bshc.add("*3");
		this.bshc.add("69");
		this.bshc.add("16");
		this.bshc.add("7");
		this.bshc.add("2");

		this.bc.add(9400);
		this.bc.add(554);
		this.bc.add(145);
		this.bc.add(44);
		this.bc.add(22);
		this.bc.add(10);
	}

	public void m9fragGrenade() {
		this.name = "M9 Frag";
		this.type = "Grenade";
		this.damage = 8;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.collateralDamage = 50;
		this.energyWeapon = false;

		// Starts from 0, no C
		this.pen.add(5);
		this.pen.add(4);
		this.pen.add(4);
		this.pen.add(4);
		this.pen.add(3);
		this.pen.add(3);

		this.dc.add(1);
		this.dc.add(1);
		this.dc.add(1);
		this.dc.add(1);
		this.dc.add(1);
		this.dc.add(1);

		this.bshc.add("*9");
		this.bshc.add("*2");
		this.bshc.add("74");
		this.bshc.add("54");
		this.bshc.add("24");
		this.bshc.add("13");

		this.bc.add(6100);
		this.bc.add(417);
		this.bc.add(115);
		this.bc.add(85);
		this.bc.add(35);
		this.bc.add(18);
	}

	public void plasmaGrenade() {
		this.name = "Type-1 Plasma Grenade";
		this.type = "Grenade";
		this.damage = 8;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.collateralDamage = 50;
		this.energyWeapon = true;

		// Starts from 0, no C
		this.pen.add(112);
		this.pen.add(112);
		this.pen.add(112);
		this.pen.add(112);
		this.pen.add(112);
		this.pen.add(112);

		this.dc.add(10);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(6);
		this.dc.add(6);

		this.bshc.add("*6");
		this.bshc.add("*1");
		this.bshc.add("54");
		this.bshc.add("31");
		this.bshc.add("7");
		this.bshc.add("4");

		this.bc.add(6100);
		this.bc.add(513);
		this.bc.add(171);
		this.bc.add(136);
		this.bc.add(42);
		this.bc.add(21);

	}

	public void classAThermalDetonator() {
		this.name = "Class-A Thermal Detonator";
		this.type = "Grenade";
		this.damage = 8;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.collateralDamage = 50;
		this.energyWeapon = false;

		// Starts from 0, no C
		this.pen.add(50);
		this.pen.add(5);
		this.pen.add(5);
		this.pen.add(5);
		this.pen.add(4);
		this.pen.add(3);

		this.dc.add(10);
		this.dc.add(8);
		this.dc.add(4);
		this.dc.add(4);
		this.dc.add(2);
		this.dc.add(2);

		this.bshc.add("*24");
		this.bshc.add("*6");
		this.bshc.add("*2");
		this.bshc.add("88");
		this.bshc.add("64");
		this.bshc.add("22");

		this.bc.add(8300);
		this.bc.add(713);
		this.bc.add(251);
		this.bc.add(155);
		this.bc.add(72);
		this.bc.add(54);

	}

	public void classAHaywireGrenade() {
		this.name = "Class-A Haywire Grenade";
		this.type = "Grenade";
		this.damage = 8;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.collateralDamage = 50;
		this.ionWeapon = true;
		this.energyWeapon = true;

		this.pen.add(0);
		this.pen.add(0);
		this.pen.add(0);
		this.pen.add(0);
		this.pen.add(0);
		this.pen.add(0);

		this.dc.add(0);
		this.dc.add(0);
		this.dc.add(0);
		this.dc.add(0);
		this.dc.add(0);
		this.dc.add(0);

		this.bshc.add("0");
		this.bshc.add("0");
		this.bshc.add("0");
		this.bshc.add("0");
		this.bshc.add("0");
		this.bshc.add("0");

		this.bc.add(0);
		this.bc.add(0);
		this.bc.add(0);
		this.bc.add(0);
		this.bc.add(0);
		this.bc.add(0);

		this.ionDamage.add(2000);
		this.ionDamage.add(1000);
		this.ionDamage.add(400);
		this.ionDamage.add(300);
		this.ionDamage.add(200);
		this.ionDamage.add(100);

	}

	public void Type33FuelRodCannon() {
		this.name = "Type-33 Fuel Rod Cannon";
		this.type = "Launcher";
		this.collateralDamage = 50;
		this.launcherHomingInfantry = true;
		this.launcherHomingVehicle = true;
		this.homingHitChance = 88;

		Ammo hedp2 = new Ammo("HEDP", 8, 0, 6, 10, null, new Ammo("Linked", 5, 0, 2, 0), null);
		Ammo heaa2 = new Ammo("HEAA", 8, 0, 4, 10, null, new Ammo("Linked", 6, 0, 3, 0), null);

		ammoTypes = new ArrayList<Ammo>();
		this.ammoTypes.add(hedp2);
		this.ammoTypes.add(heaa2);

		this.energyWeapon = false;

		String name = "HEAT";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		pen.add(112);
		pen.add(112);
		pen.add(112);
		pen.add(112);
		pen.add(112);
		pen.add(112);

		ArrayList<Integer> dc = new ArrayList<Integer>();
		dc.add(10);
		dc.add(6);
		dc.add(6);
		dc.add(6);
		dc.add(6);
		dc.add(6);

		ArrayList<String> bshc = new ArrayList<String>();
		bshc.add("*3");
		bshc.add("98");
		bshc.add("32");
		bshc.add("18");
		bshc.add("5");
		bshc.add("0");

		ArrayList<Integer> bc = new ArrayList<Integer>();
		bc.add(4800);
		bc.add(313);
		bc.add(111);
		bc.add(84);
		bc.add(22);
		bc.add(5);

		PCAmmo heat = new PCAmmo(name, pen, dc, bshc, bc, 2800, 10);
		pcAmmoTypes.add(heat);

		name = "HE";
		pen = new ArrayList<Integer>();
		pen.add(112);
		pen.add(112);
		pen.add(112);
		pen.add(112);
		pen.add(112);
		pen.add(112);

		dc = new ArrayList<Integer>();
		dc.add(10);
		dc.add(6);
		dc.add(6);
		dc.add(6);
		dc.add(6);
		dc.add(6);

		bshc = new ArrayList<String>();
		bshc.add("*6");
		bshc.add("*1");
		bshc.add("54");
		bshc.add("31");
		bshc.add("7");
		bshc.add("4");

		bc = new ArrayList<Integer>();
		bc.add(6100);
		bc.add(513);
		bc.add(171);
		bc.add(136);
		bc.add(42);
		bc.add(21);

		PCAmmo he = new PCAmmo(name, pen, dc, bshc, bc, 112, 10);
		pcAmmoTypes.add(he);

		// Aim time
		this.aimTime.add(-29);
		this.aimTime.add(-18);
		this.aimTime.add(-12);
		this.aimTime.add(-7);
		this.aimTime.add(-4);
		this.aimTime.add(-2);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(3);
		this.aimTime.add(6);
		this.aimTime.add(8);

		this.ba.add(24);
		this.ba.add(12);
		this.ba.add(2);
		this.ba.add(1);

		// Ce stats
		ceStats.baseErgonomics = 50;

	}

	public void MLRS1Hydra() {
		this.name = "MLRS-1 Hydra";
		this.type = "Launcher";
		this.collateralDamage = 50;
		this.launcherHomingInfantry = true;
		this.launcherHomingVehicle = true;
		this.homingHitChance = 88;

		Ammo hedp2 = new Ammo("HEDP", 8, 0, 6, 10, null, new Ammo("Linked", 5, 0, 2, 0), null);
		Ammo heaa2 = new Ammo("HEAA", 8, 0, 4, 10, null, new Ammo("Linked", 6, 0, 3, 0), null);

		ammoTypes = new ArrayList<Ammo>();
		this.ammoTypes.add(hedp2);
		this.ammoTypes.add(heaa2);

		this.energyWeapon = false;

		String name = "HEAT";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		pen.add(5);
		pen.add(4);
		pen.add(4);
		pen.add(3);
		pen.add(2);
		pen.add(1);

		ArrayList<Integer> dc = new ArrayList<Integer>();
		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);

		ArrayList<String> bshc = new ArrayList<String>();
		bshc.add("*3");
		bshc.add("62");
		bshc.add("15");
		bshc.add("6");
		bshc.add("2");
		bshc.add("0");

		ArrayList<Integer> bc = new ArrayList<Integer>();
		bc.add(696);
		bc.add(174);
		bc.add(52);
		bc.add(26);
		bc.add(11);
		bc.add(4);

		PCAmmo heat = new PCAmmo(name, pen, dc, bshc, bc, 2800, 10);
		pcAmmoTypes.add(heat);

		name = "HE";
		pen = new ArrayList<Integer>();
		pen.add(5);
		pen.add(4);
		pen.add(4);
		pen.add(3);
		pen.add(2);
		pen.add(1);

		dc = new ArrayList<Integer>();
		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);

		bshc = new ArrayList<String>();
		bshc.add("*9");
		bshc.add("*2");
		bshc.add("54");
		bshc.add("24");
		bshc.add("8");
		bshc.add("0");

		bc = new ArrayList<Integer>();
		bc.add(788);
		bc.add(192);
		bc.add(57);
		bc.add(29);
		bc.add(12);
		bc.add(5);

		PCAmmo he = new PCAmmo(name, pen, dc, bshc, bc, 11, 10);
		pcAmmoTypes.add(he);

		// Aim time
		this.aimTime.add(-29);
		this.aimTime.add(-18);
		this.aimTime.add(-12);
		this.aimTime.add(-7);
		this.aimTime.add(-4);
		this.aimTime.add(-2);
		this.aimTime.add(0);
		this.aimTime.add(1);
		this.aimTime.add(3);
		this.aimTime.add(6);
		this.aimTime.add(8);

		this.ba.add(23);
		this.ba.add(10);
		this.ba.add(1);
		this.ba.add(1);

		// Ce stats
		ceStats.baseErgonomics = 50;

	}

	public void M41SPNKR() {
		this.name = "M41-SPNKR";
		this.type = "Launcher";
		this.collateralDamage = 50;
		this.launcherHomingInfantry = true;
		this.launcherHomingVehicle = true;
		this.homingHitChance = 88;
		this.suppressiveROF = 1;
		
		Ammo hedp2 = new Ammo("HEDP", 8, 0, 6, 10, null, new Ammo("Linked", 5, 0, 2, 0), null);
		Ammo heaa2 = new Ammo("HEAA", 8, 0, 4, 10, null, new Ammo("Linked", 6, 0, 3, 0), null);

		ammoTypes = new ArrayList<Ammo>();
		this.ammoTypes.add(hedp2);
		this.ammoTypes.add(heaa2);

		this.energyWeapon = false;

		String name = "HEAT";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		pen.add(9);
		pen.add(9);
		pen.add(8);
		pen.add(8);
		pen.add(7);
		pen.add(7);

		ArrayList<Integer> dc = new ArrayList<Integer>();
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(8);

		ArrayList<String> bshc = new ArrayList<String>();
		bshc.add("10");
		bshc.add("2");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");

		ArrayList<Integer> bc = new ArrayList<Integer>();
		bc.add(2800);
		bc.add(575);
		bc.add(155);
		bc.add(80);
		bc.add(45);
		bc.add(18);

		PCAmmo heat = new PCAmmo(name, pen, dc, bshc, bc, 17000, 10);
		pcAmmoTypes.add(heat);

		name = "HE";
		pen = new ArrayList<Integer>();
		pen.add(5);
		pen.add(4);
		pen.add(3);
		pen.add(3);
		pen.add(1);
		pen.add(1);

		dc = new ArrayList<Integer>();
		dc.add(8);
		dc.add(8);
		dc.add(4);
		dc.add(4);
		dc.add(2);
		dc.add(2);

		bshc = new ArrayList<String>();
		bshc.add("*26");
		bshc.add("*7");
		bshc.add("*2");
		bshc.add("83");
		bshc.add("36");
		bshc.add("14");

		bc = new ArrayList<Integer>();
		bc.add(3800);
		bc.add(820);
		bc.add(322);
		bc.add(155);
		bc.add(85);
		bc.add(32);

		PCAmmo he = new PCAmmo(name, pen, dc, bshc, bc, 8, 10);
		pcAmmoTypes.add(he);

		// Aim time
		this.aimTime.add(-27);
		this.aimTime.add(-16);
		this.aimTime.add(-9);
		this.aimTime.add(-6);
		this.aimTime.add(-3);
		this.aimTime.add(-2);
		this.aimTime.add(1);
		this.aimTime.add(2);
		this.aimTime.add(4);
		this.aimTime.add(5);

		this.ba.add(23);
		this.ba.add(10);
		this.ba.add(1);
		this.ba.add(1);

		// Ce stats
		ceStats.baseErgonomics = 50;

	}

	public void RPS6() {
		this.name = "RPS-6";
		this.type = "Launcher";
		this.collateralDamage = 50;
		this.launcherHomingInfantry = true;
		this.launcherHomingVehicle = true;
		this.homingHitChance = 75;
		this.suppressiveROF = 1;
		
		Ammo hedp2 = new Ammo("HEDP", 8, 0, 6, 10, null, new Ammo("Linked", 5, 0, 2, 0), null);
		Ammo heaa2 = new Ammo("HEAA", 8, 0, 4, 10, null, new Ammo("Linked", 6, 0, 3, 0), null);

		ammoTypes = new ArrayList<Ammo>();
		this.ammoTypes.add(hedp2);
		this.ammoTypes.add(heaa2);

		this.energyWeapon = false;

		String name = "HEAT";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		pen.add(9);
		pen.add(9);
		pen.add(8);
		pen.add(8);
		pen.add(7);
		pen.add(7);

		ArrayList<Integer> dc = new ArrayList<Integer>();
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(8);

		ArrayList<String> bshc = new ArrayList<String>();
		bshc.add("34");
		bshc.add("12");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");

		ArrayList<Integer> bc = new ArrayList<Integer>();
		bc.add(2000);
		bc.add(393);
		bc.add(105);
		bc.add(52);
		bc.add(22);
		bc.add(7);

		PCAmmo heat = new PCAmmo(name, pen, dc, bshc, bc, 7200, 10);
		pcAmmoTypes.add(heat);

		name = "HE";
		pen = new ArrayList<Integer>();
		pen.add(6);
		pen.add(5);
		pen.add(5);
		pen.add(5);
		pen.add(4);
		pen.add(3);

		dc = new ArrayList<Integer>();
		dc.add(8);
		dc.add(8);
		dc.add(4);
		dc.add(4);
		dc.add(2);
		dc.add(2);

		bshc = new ArrayList<String>();
		bshc.add("*23");
		bshc.add("*6");
		bshc.add("*1");
		bshc.add("64");
		bshc.add("22");
		bshc.add("0");

		bc = new ArrayList<Integer>();
		bc.add(2400);
		bc.add(414);
		bc.add(114);
		bc.add(85);
		bc.add(60);
		bc.add(20);

		PCAmmo he = new PCAmmo(name, pen, dc, bshc, bc, 8, 10);
		pcAmmoTypes.add(he);

		// Aim time
		this.aimTime.add(-27);
		this.aimTime.add(-17);
		this.aimTime.add(-10);
		this.aimTime.add(-8);
		this.aimTime.add(-5);
		this.aimTime.add(-3);
		this.aimTime.add(-2);
		this.aimTime.add(-1);
		this.aimTime.add(0);
		this.aimTime.add(1);

		this.ba.add(23);
		this.ba.add(10);
		this.ba.add(1);
		this.ba.add(1);

		// Ce stats
		ceStats.baseErgonomics = 50;

	}

	public void DC40() {
		this.name = "DC40";
		this.type = "Launcher";
		this.collateralDamage = 50;
		this.suppressiveROF = 2;

		/*
		 * Ammo heat = new Ammo("HEAT", 8, 0, 6, 10, null, null, null); Ammo he = new
		 * Ammo("HE", 8, 0, 4, 10, null, null, null);
		 */

		ammoTypes = new ArrayList<Ammo>();
		/*
		 * this.ammoTypes.add(heat); this.ammoTypes.add(he);
		 */

		String name = "HEAT";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		pen.add(2);
		pen.add(2);
		pen.add(1);
		pen.add(1);
		pen.add(1);
		pen.add(1);

		ArrayList<Integer> dc = new ArrayList<Integer>();
		dc.add(3);
		dc.add(3);
		dc.add(2);
		dc.add(2);
		dc.add(2);
		dc.add(1);

		ArrayList<String> bshc = new ArrayList<String>();
		bshc.add("*2");
		bshc.add("47");
		bshc.add("11");
		bshc.add("4");
		bshc.add("1");
		bshc.add("0");

		ArrayList<Integer> bc = new ArrayList<Integer>();
		bc.add(241);
		bc.add(71);
		bc.add(23);
		bc.add(12);
		bc.add(5);
		bc.add(1);

		PCAmmo heat = new PCAmmo(name, pen, dc, bshc, bc, 288, 10);
		pcAmmoTypes.add(heat);

		name = "HE";
		pen = new ArrayList<Integer>();
		pen.add(2);
		pen.add(2);
		pen.add(1);
		pen.add(1);
		pen.add(1);
		pen.add(1);

		dc = new ArrayList<Integer>();
		dc.add(3);
		dc.add(3);
		dc.add(2);
		dc.add(2);
		dc.add(2);
		dc.add(1);

		bshc = new ArrayList<String>();
		bshc.add("*3");
		bshc.add("*1");
		bshc.add("62");
		bshc.add("15");
		bshc.add("6");
		bshc.add("2");

		bc = new ArrayList<Integer>();
		bc.add(353);
		bc.add(100);
		bc.add(31);
		bc.add(16);
		bc.add(7);
		bc.add(2);

		PCAmmo he = new PCAmmo(name, pen, dc, bshc, bc, 2, 10);
		pcAmmoTypes.add(he);

		// Aim time
		this.aimTime.add(-23);
		this.aimTime.add(-13);
		this.aimTime.add(-9);
		this.aimTime.add(-7);
		this.aimTime.add(-5);

		// 80 yards, 100 yards, 400 yards. 800 yards
		this.ba.add(28);
		this.ba.add(18);
		this.ba.add(6);
		this.ba.add(1);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void DC17Rocket() {
		this.name = "DC17 Rocket";
		this.type = "Launcher";
		this.collateralDamage = 50;
		this.suppressiveROF = 1;
		/*
		 * Ammo heat = new Ammo("HEAT", 8, 0, 6, 10, null, null, null); Ammo he = new
		 * Ammo("HE", 8, 0, 4, 10, null, null, null);
		 */

		ammoTypes = new ArrayList<Ammo>();
		/*
		 * this.ammoTypes.add(heat); this.ammoTypes.add(he);
		 */

		this.energyWeapon = false;

		String name = "HEAT";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		pen.add(9);
		pen.add(9);
		pen.add(8);
		pen.add(8);
		pen.add(7);
		pen.add(7);

		ArrayList<Integer> dc = new ArrayList<Integer>();
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(8);

		ArrayList<String> bshc = new ArrayList<String>();
		bshc.add("34");
		bshc.add("12");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");

		ArrayList<Integer> bc = new ArrayList<Integer>();
		bc.add(2400);
		bc.add(414);
		bc.add(114);
		bc.add(85);
		bc.add(60);
		bc.add(20);

		PCAmmo heat = new PCAmmo(name, pen, dc, bshc, bc, 7200, 10);
		pcAmmoTypes.add(heat);

		name = "HE";
		pen = new ArrayList<Integer>();
		pen.add(6);
		pen.add(5);
		pen.add(5);
		pen.add(5);
		pen.add(4);
		pen.add(3);

		dc = new ArrayList<Integer>();
		dc.add(8);
		dc.add(8);
		dc.add(4);
		dc.add(4);
		dc.add(2);
		dc.add(2);

		bshc = new ArrayList<String>();
		bshc.add("*23");
		bshc.add("*6");
		bshc.add("*1");
		bshc.add("64");
		bshc.add("22");
		bshc.add("0");

		bc = new ArrayList<Integer>();
		bc.add(6500);
		bc.add(2300);
		bc.add(552);
		bc.add(170);
		bc.add(82);
		bc.add(11);

		PCAmmo he = new PCAmmo(name, pen, dc, bshc, bc, 8, 10);
		pcAmmoTypes.add(he);

		// Aim time
		this.aimTime.add(-23);
		this.aimTime.add(-13);
		this.aimTime.add(-9);
		this.aimTime.add(-7);
		this.aimTime.add(-5);

		this.ba.add(23);
		this.ba.add(10);
		this.ba.add(1);
		this.ba.add(1);

		// Ce stats
		ceStats.baseErgonomics = 50;
	}

	public void B2RR() {
		this.name = "B2RR";
		this.type = "Launcher";
		this.collateralDamage = 50;
		this.suppressiveROF = 1;
		/*
		 * Ammo heat = new Ammo("HEAT", 8, 0, 6, 10, null, null, null); Ammo he = new
		 * Ammo("HE", 8, 0, 4, 10, null, null, null);
		 */

		ammoTypes = new ArrayList<Ammo>();
		/*
		 * this.ammoTypes.add(heat); this.ammoTypes.add(he);
		 */

		this.energyWeapon = false;

		String name = "HEAT";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		pen.add(9);
		pen.add(9);
		pen.add(8);
		pen.add(8);
		pen.add(7);
		pen.add(7);

		ArrayList<Integer> dc = new ArrayList<Integer>();
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(9);
		dc.add(8);

		ArrayList<String> bshc = new ArrayList<String>();
		bshc.add("34");
		bshc.add("12");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");

		ArrayList<Integer> bc = new ArrayList<Integer>();
		bc.add(2000);
		bc.add(393);
		bc.add(105);
		bc.add(52);
		bc.add(22);
		bc.add(7);

		PCAmmo heat = new PCAmmo(name, pen, dc, bshc, bc, 7200, 10);
		pcAmmoTypes.add(heat);

		name = "HE";
		pen = new ArrayList<Integer>();
		pen.add(6);
		pen.add(5);
		pen.add(5);
		pen.add(5);
		pen.add(4);
		pen.add(3);

		dc = new ArrayList<Integer>();
		dc.add(8);
		dc.add(8);
		dc.add(4);
		dc.add(4);
		dc.add(2);
		dc.add(2);

		bshc = new ArrayList<String>();
		bshc.add("*23");
		bshc.add("*6");
		bshc.add("*1");
		bshc.add("64");
		bshc.add("22");
		bshc.add("0");

		bc = new ArrayList<Integer>();
		bc.add(2400);
		bc.add(414);
		bc.add(114);
		bc.add(85);
		bc.add(60);
		bc.add(20);

		PCAmmo he = new PCAmmo(name, pen, dc, bshc, bc, 8, 10);
		pcAmmoTypes.add(he);

		// Aim time
		this.aimTime.add(-23);
		this.aimTime.add(-13);
		this.aimTime.add(-9);

		this.ba.add(23);
		this.ba.add(10);
		this.ba.add(1);
		this.ba.add(1);

		// Ce stats
		ceStats.baseErgonomics = 50;

	}

	public void m107HE() {
		name = "107mm";
		this.type = "Ordnance";
		this.energyWeapon = false;

		String name = "HE";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		ArrayList<Integer> dc = new ArrayList<Integer>();
		ArrayList<String> bshc = new ArrayList<String>();
		ArrayList<Integer> bc = new ArrayList<Integer>();

		pen.add(20); // 0
		pen.add(20); // 1
		pen.add(19); // 2
		pen.add(19); // 3
		pen.add(18); // 4
		pen.add(17); // 5
		pen.add(17); // 6
		pen.add(16); // 8
		pen.add(15); // 10

		dc.add(7);
		dc.add(7);
		dc.add(7);
		dc.add(7);
		dc.add(7);
		dc.add(7);
		dc.add(7);
		dc.add(7);
		dc.add(7);

		bshc.add("*10");
		bshc.add("*4");
		bshc.add("*3");
		bshc.add("*1");
		bshc.add("93");
		bshc.add("65");
		bshc.add("44");
		bshc.add("33");
		bshc.add("22");

		bc.add(12000);
		bc.add(1200);
		bc.add(283);
		bc.add(103);
		bc.add(58);
		bc.add(39);
		bc.add(28);
		bc.add(17);
		bc.add(12);

		PCAmmo he = new PCAmmo(name, pen, dc, bshc, bc, 20, 7);
		he.ordnance = true;
		pcAmmoTypes.add(he);

	}

	public void M870() {
		this.name = "M870";
		this.targetROF = 1;
		this.suppressiveROF = 2;
		this.type = "Rifle";
		this.weaponBonus = 0; // Scope
		this.scopeMagnification = "";
		this.magnification = 1;
		this.damage = 3;
		this.damageBonus = 0;
		this.damageMultiplier = 5;
		this.armorPiercing = 2;
		this.sab = 1;
		this.fullAutoROF = 1;
		this.tracers = false;
		this.light = false;
		this.laser = false;
		this.irLaser = false;
		this.shotgun = true;

		// Aim time
		this.aimTime.add(-23);
		this.aimTime.add(-12);
		this.aimTime.add(-9);
		this.aimTime.add(-7);
		this.aimTime.add(-6);
		this.aimTime.add(-4);
		this.aimTime.add(-3);
		this.aimTime.add(-2);

		// 1 2 4 6 8 10 15 20 30 40 80
		// PEN
		this.pen.add(6);
		this.pen.add(2);
		this.pen.add(2);
		this.pen.add(2);
		this.pen.add(2);
		this.pen.add(2);
		this.pen.add(2);
		this.pen.add(2);
		this.pen.add(2);
		this.pen.add(1);
		this.pen.add(1);
		// DC
		this.dc.add(3);
		this.dc.add(3);
		this.dc.add(3);
		this.dc.add(3);
		this.dc.add(3);
		this.dc.add(3);
		this.dc.add(2);
		this.dc.add(2);
		this.dc.add(2);
		this.dc.add(2);
		this.dc.add(1);
		// BA
		this.ba.add(67);
		this.ba.add(58);
		this.ba.add(48);
		this.ba.add(42);
		this.ba.add(38);
		this.ba.add(35);
		this.ba.add(29);
		this.ba.add(25);
		this.ba.add(19);
		this.ba.add(15);
		this.ba.add(5);

		this.bphc.add("11*");
		this.bphc.add("11*");
		this.bphc.add("10*");
		this.bphc.add("9*");
		this.bphc.add("7*");
		this.bphc.add("5*");
		this.bphc.add("2*");
		this.bphc.add("1*");
		this.bphc.add("62");
		this.bphc.add("35");
		this.bphc.add("8");

		this.salm.add(-14);
		this.salm.add(-9);
		this.salm.add(-4);
		this.salm.add(-1);
		this.salm.add(1);
		this.salm.add(2);
		this.salm.add(5);
		this.salm.add(7);
		this.salm.add(10);
		this.salm.add(12);
		this.salm.add(17);

		// Ce stats
		ceStats.baseErgonomics = 50;

	}

	public void setAmmo(String name) {

		if (pcAmmoTypes.size() <= 0)
			return;

		for (int i = 0; i < pcAmmoTypes.size(); i++) {

			if (pcAmmoTypes.get(i).name.equals(name)) {
				this.pen = pcAmmoTypes.get(i).pen;
				this.dc = pcAmmoTypes.get(i).dc;
				this.bshc = pcAmmoTypes.get(i).bshc;
				this.bc = pcAmmoTypes.get(i).bc;
				break;

			}

		}

	}

	public Ammo findAmmoType(String name, Weapons wep) {

		Ammo ammo = null;

		for (Ammo ammoType : wep.ammoTypes) {
			if (ammoType.name.equals(name)) {
				return ammoType;
			}
		}

		return ammo;

	}

	public Weapons findWeapon(String name) {
		boolean found = false;
		// System.out.println("Weapon Name: "+name);
		Weapons weapon = new Weapons();
		weapon.createWeapons();
		ArrayList<Weapons> weapons = weapon.getWeapons();
		for (int i = 0; i < weapons.size(); i++) {
			if (weapons.get(i).name.equals(name)) {
				weapon = weapons.get(i);
				found = true;
				break;
			}
		}

		if (!found) {
			weapon = weapons.get(0);
		}

		return weapon;
	}

	public static String getShotgunTableString(ArrayList<String> values, int rangeInPcHexes) {
		// 1 2 4 6 8 10 15 20 30 40 80

		if (rangeInPcHexes <= 1) {
			return values.get(0);
		} else if (rangeInPcHexes <= 2) {
			return values.get(1);
		} else if (rangeInPcHexes <= 4) {
			return values.get(2);
		} else if (rangeInPcHexes <= 6) {
			return values.get(3);
		} else if (rangeInPcHexes <= 8) {
			return values.get(4);
		} else if (rangeInPcHexes <= 10) {
			return values.get(5);
		} else if (rangeInPcHexes <= 15) {
			return values.get(6);
		} else if (rangeInPcHexes <= 20) {
			return values.get(7);
		} else if (rangeInPcHexes <= 30) {
			return values.get(8);
		} else if (rangeInPcHexes <= 40) {
			return values.get(9);
		} else {
			return values.get(10);
		}

	}

	public static int getShotgunTableInteger(ArrayList<Integer> values, int rangeInPcHexes) {
		// 1 2 4 6 8 10 15 20 30 40 80

		if (rangeInPcHexes <= 1) {
			return values.get(0);
		} else if (rangeInPcHexes <= 2) {
			return values.get(1);
		} else if (rangeInPcHexes <= 4) {
			return values.get(2);
		} else if (rangeInPcHexes <= 6) {
			return values.get(3);
		} else if (rangeInPcHexes <= 8) {
			return values.get(4);
		} else if (rangeInPcHexes <= 10) {
			return values.get(5);
		} else if (rangeInPcHexes <= 15) {
			return values.get(6);
		} else if (rangeInPcHexes <= 20) {
			return values.get(7);
		} else if (rangeInPcHexes <= 30) {
			return values.get(8);
		} else if (rangeInPcHexes <= 40) {
			return values.get(9);
		} else {
			return values.get(10);
		}

	}

	public static int getShotgunHitLocation(int roll, int salm) {

		if (salm <= -12) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 1) : -DiceRoller.randInt(0, 1);
		} else if (salm <= -10) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 2) : -DiceRoller.randInt(0, 2);
		} else if (salm <= -6) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 3) : -DiceRoller.randInt(0, 3);
		} else if (salm <= -4) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 4) : -DiceRoller.randInt(0, 4);
		} else if (salm <= -2) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 6) : -DiceRoller.randInt(0, 6);
		} else if (salm <= 0) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 8) : -DiceRoller.randInt(0, 8);
		} else if (salm <= 2) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 11) : -DiceRoller.randInt(0, 11);
		} else if (salm <= 4) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 14) : -DiceRoller.randInt(0, 14);
		} else if (salm <= 6) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 19) : -DiceRoller.randInt(0, 19);
		} else if (salm <= 8) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 25) : -DiceRoller.randInt(0, 25);
		} else if (salm <= 10) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 34) : -DiceRoller.randInt(0, 34);
		} else if (salm <= 12) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 45) : -DiceRoller.randInt(0, 45);
		} else if (salm <= 14) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 60) : -DiceRoller.randInt(0, 60);
		} else if (salm <= 16) {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 79) : -DiceRoller.randInt(0, 79);
		} else {
			roll += DiceRoller.plusMinus() ? roll + DiceRoller.randInt(0, 100) : -DiceRoller.randInt(0, 100);
		}

		while (roll > 99 || roll < 0) {
			if (roll > 99) {
				roll = roll - 99;
			} else {
				roll = 99 - Math.abs(roll);
			}
		}

		return roll;
	}

	public int getPen(int rangeInPcHexes) {
		// 10 20 40 70 100 200 300 400

		if (rangeInPcHexes <= 10) {
			return pen.get(0);
		} else if (rangeInPcHexes <= 20) {
			return pen.get(1);
		} else if (rangeInPcHexes <= 20) {
			return pen.get(2);
		} else if (rangeInPcHexes <= 20) {
			return pen.get(3);
		} else if (rangeInPcHexes <= 20) {
			return pen.get(4);
		} else if (rangeInPcHexes <= 20) {
			return pen.get(5);
		} else if (rangeInPcHexes <= 20) {
			return pen.get(6);
		} else {
			return pen.get(7);
		}

	}

	public int getDc(int rangeInPcHexes) {
		// 10 20 40 70 100 200 300 400

		if (rangeInPcHexes <= 10) {
			return dc.get(0);
		} else if (rangeInPcHexes <= 20) {
			return dc.get(1);
		} else if (rangeInPcHexes <= 20) {
			return dc.get(2);
		} else if (rangeInPcHexes <= 20) {
			return dc.get(3);
		} else if (rangeInPcHexes <= 20) {
			return dc.get(4);
		} else if (rangeInPcHexes <= 20) {
			return dc.get(5);
		} else if (rangeInPcHexes <= 20) {
			return dc.get(6);
		} else {
			return dc.get(7);
		}
	}

	public int getBA(int rangeInPCHexes) {

		if (ba.isEmpty())
			return 0;

		if (type.equals("Launcher")) {

			if (rangeInPCHexes <= 40) {
				return ba.get(0);
			} else if (rangeInPCHexes <= 100) {
				return ba.get(1);
			} else if (rangeInPCHexes <= 200) {
				return ba.get(2);
			} else {
				return ba.get(3);
			}

		}

		// 10 20 40 70 100 200 300 400
		if (rangeInPCHexes <= 10) {
			return ba.get(0);
		} else if (rangeInPCHexes <= 20) {
			return ba.get(1);
		} else if (rangeInPCHexes <= 40) {
			return ba.get(2);
		} else if (rangeInPCHexes <= 70) {
			return ba.get(3);
		} else if (rangeInPCHexes <= 100) {
			return ba.get(4);
		} else if (rangeInPCHexes <= 200) {
			return ba.get(5);
		} else if (rangeInPCHexes <= 300) {
			return ba.get(6);
		} else {
			return ba.get(ba.size() - 1);
		}

	}

	public double getMA(int rangeInPCHexes) {

		if (ma.isEmpty())
			return 0;
		// 10 20 40 70 100 200 300 400
		if (rangeInPCHexes <= 10) {
			return ma.get(0);
		} else if (rangeInPCHexes <= 20) {
			return ma.get(1);
		} else if (rangeInPCHexes <= 40) {
			return ma.get(2);
		} else if (rangeInPCHexes <= 70) {
			return ma.get(3);
		} else if (rangeInPCHexes <= 100) {
			return ma.get(4);
		} else if (rangeInPCHexes <= 200) {
			return ma.get(5);
		} else if (rangeInPCHexes <= 300) {
			return ma.get(6);
		} else {
			return ma.get(ma.size() - 1);
		}

	}

	public int getScopeBonus(int spentAimTime) {

		if (!scopeMagnification.isEmpty() && spentAimTime > 0) {

			if (scopeMagnification.equals("4-6x")) {

				if (spentAimTime <= 1) {
					return 0;
				} else if (spentAimTime <= 2) {
					return 1;
				} else if (spentAimTime <= 3) {
					return 2;
				} else if (spentAimTime <= 4) {
					return 3;
				} else if (spentAimTime <= 6) {
					return 3;
				} else if (spentAimTime <= 8) {
					return 3;
				} else if (spentAimTime <= 10) {
					return 3;
				} else if (spentAimTime <= 12) {
					return 4;
				} else {
					return 4;
				}

			} else if (scopeMagnification.equals("4-12x")) {

				if (spentAimTime <= 1) {
					return 0;
				} else if (spentAimTime <= 2) {
					return 1;
				} else if (spentAimTime <= 3) {
					return 2;
				} else if (spentAimTime <= 4) {
					return 3;
				} else if (spentAimTime <= 6) {
					return 3;
				} else if (spentAimTime <= 8) {
					return 4;
				} else if (spentAimTime <= 10) {
					return 5;
				} else if (spentAimTime <= 12) {
					return 6;
				} else {
					return 7;
				}

			} else if (scopeMagnification.equals("4-24x")) {

				if (spentAimTime <= 1) {
					return 0;
				} else if (spentAimTime <= 2) {
					return 1;
				} else if (spentAimTime <= 3) {
					return 2;
				} else if (spentAimTime <= 4) {
					return 3;
				} else if (spentAimTime <= 6) {
					return 3;
				} else if (spentAimTime <= 8) {
					return 4;
				} else if (spentAimTime <= 10) {
					return 6;
				} else if (spentAimTime <= 12) {
					return 8;
				} else {
					return 10;
				}

			}

		}

		return 0;
	}

	public int getTargetedROF() {
		return targetROF;
	}

	public int getSuppressROF() {
		return suppressiveROF;
	}

	public ArrayList<Weapons> getWeapons() {
		createWeapons();
		return weapons;
	}

	public boolean ballisticWeapon() {
		return pen.size() > 0 ? true : false;
	}

	public boolean explosiveWeapon() {
		return bc.size() > 0 ? true : false;
	}

	public ArrayList<Weapons> getWeaponsFromType(String type) {
		ArrayList<Weapons> weapons = new ArrayList<>();

		for (Weapons wep : this.weapons) {
			String wepType = wep.type;

			if (type.equals("Small Arms") && (wepType.equals("Pistol") || wepType.equals("Subgun")
					|| wepType.equals("Rifle") || wepType.equals("Heavy")))
				weapons.add(wep);

			if (type.equals("Heavy Weapons") && (wepType.equals("Static") || wepType.equals("Cannon")))
				weapons.add(wep);

			if (wep.type.equals(type))
				weapons.add(wep);
		}

		return weapons;
	}

	public static ArrayList<String> getNamesOfWeapons(ArrayList<Weapons> weapons) {
		ArrayList<String> strArray = new ArrayList<>();

		for (Weapons wep : weapons) {
			strArray.add(wep.name);
		}

		return strArray;

	}

	@Override
	public String toString() {
		String wep = name + "; " + type + "; " + targetROF + ":" + suppressiveROF + "; DMG: " + damage + "; AP: "
				+ armorPiercing;
		return wep;
	}
}
