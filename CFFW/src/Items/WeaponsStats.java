package Items;

import java.util.ArrayList;

import Conflict.SmokeStats.SmokeType;
import Melee.Gear.MeleeWeapon;
import Melee.Gear.MeleeWeaponData.MeleeWeaponType;

public class WeaponsStats {

	public ArrayList<Weapons> weapons = new ArrayList<>();

	public WeaponsStats() {
		//DC15LE(); in main already
		T4GMG();
		GoblinJavelin();
		ShortBow();
		Spear();
		VibroKnife();
		Gladius();
		classBThermalImploder();
		classCIonImploder();
		m155DPICM();
		hydra70Rocket();
		hellfireMissile();
	}
	
	public void hellfireMissile() {
		var weapon = new Weapons();
		weapon.name = "AGM-114 Hellfire";
		weapon.type = "Ordnance";
		weapon.energyWeapon = false;

		String name = "Rocket";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		ArrayList<Integer> dc = new ArrayList<Integer>();
		ArrayList<String> bshc = new ArrayList<String>();
		ArrayList<Integer> bc = new ArrayList<Integer>();

		PCAmmo rocket = new PCAmmo(name, pen, dc, bshc, bc, 20, 7);
		rocket.defoliateChance = 25;
		
		rocket.pen.add(38);
		rocket.pen.add(24);
		rocket.pen.add(12);
		rocket.pen.add(10);
		rocket.pen.add(5);
		rocket.pen.add(2);

		rocket.dc.add(10);
		rocket.dc.add(10);
		rocket.dc.add(9);
		rocket.dc.add(9);
		rocket.dc.add(8);
		rocket.dc.add(8);

		rocket.bshc.add("*100");
		rocket.bshc.add("*22");
		rocket.bshc.add("*2");
		rocket.bshc.add("*1");
		rocket.bshc.add("60");
		rocket.bshc.add("50");

		rocket.bc.add(9400);
		rocket.bc.add(554);
		rocket.bc.add(145);
		rocket.bc.add(44);
		rocket.bc.add(22);
		rocket.bc.add(10);
		
		rocket.rangeList = new int[] {12,15,22};
		rocket.ammoRanges.put(15, new ExplosiveData(50,0,"25",2,8));
		rocket.ammoRanges.put(15, new ExplosiveData(25,0,"13",2,8));
		rocket.ammoRanges.put(22, new ExplosiveData(11,0,"1",1,8));
		weapon.pcAmmoTypes.add(rocket);
		weapons.add(weapon);
	}
	
	public void hydra70Rocket() {
		var weapon = new Weapons();
		weapon.name = "Hydra 70";
		weapon.type = "Ordnance";
		weapon.energyWeapon = false;

		String name = "Rocket";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		ArrayList<Integer> dc = new ArrayList<Integer>();
		ArrayList<String> bshc = new ArrayList<String>();
		ArrayList<Integer> bc = new ArrayList<Integer>();

		PCAmmo rocket = new PCAmmo(name, pen, dc, bshc, bc, 20, 7);
		rocket.defoliateChance = 25;
		
		rocket.pen.add(3);
		rocket.pen.add(3);
		rocket.pen.add(3);
		rocket.pen.add(2);
		rocket.pen.add(2);
		rocket.pen.add(1);

		rocket.dc.add(3);
		rocket.dc.add(3);
		rocket.dc.add(2);
		rocket.dc.add(2);
		rocket.dc.add(2);
		rocket.dc.add(1);

		rocket.bshc.add("*9");
		rocket.bshc.add("*3");
		rocket.bshc.add("69");
		rocket.bshc.add("16");
		rocket.bshc.add("7");
		rocket.bshc.add("2");

		rocket.bc.add(9400);
		rocket.bc.add(554);
		rocket.bc.add(145);
		rocket.bc.add(44);
		rocket.bc.add(22);
		rocket.bc.add(10);
		
		
		weapon.pcAmmoTypes.add(rocket);
		weapons.add(weapon);
	}
	
	public void m155DPICM() {
		var weapon = new Weapons();
		weapon.name = "M155m DPICM";
		weapon.type = "Ordnance";
		weapon.energyWeapon = false;

		String name = "DPICM";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		ArrayList<Integer> dc = new ArrayList<Integer>();
		ArrayList<String> bshc = new ArrayList<String>();
		ArrayList<Integer> bc = new ArrayList<Integer>();

		PCAmmo cluster = new PCAmmo(name, pen, dc, bshc, bc, 20, 7);
		cluster.clusterMunition = true;
		cluster.clusterRadiusHex = 5;
		cluster.submunitionCountPerHex = 12;
		cluster.defoliateChance = 12;
		
		cluster.pen.add(2);
		cluster.pen.add(2);
		cluster.pen.add(2);
		cluster.pen.add(2);
		cluster.pen.add(1);
		cluster.pen.add(1);
		cluster.pen.add(1);

		cluster.dc.add(2);
		cluster.dc.add(2);
		cluster.dc.add(2);
		cluster.dc.add(2);
		cluster.dc.add(2);
		cluster.dc.add(2);
		cluster.dc.add(2);

		cluster.bshc.add("*1");
		cluster.bshc.add("27");
		cluster.bshc.add("6");
		cluster.bshc.add("3");
		cluster.bshc.add("1");
		cluster.bshc.add("1");
		cluster.bshc.add("0");

		cluster.bc.add(739);
		cluster.bc.add(155);
		cluster.bc.add(20);
		cluster.bc.add(9);
		cluster.bc.add(5);
		cluster.bc.add(3);
		cluster.bc.add(1);
		
		
		weapon.pcAmmoTypes.add(cluster);
		weapons.add(weapon);
	}
	
	public void classCIonImploder() {
		Weapons weapon = new Weapons();
		weapon.name = "Class-C Ion Imploder";
		weapon.type = "Grenade";
		weapon.damage = 8;
		weapon.damageBonus = 0;
		weapon.damageMultiplier = 5;
		weapon.armorPiercing = 2;
		weapon.collateralDamage = 50;
		weapon.energyWeapon = false;
		weapon.fuze = 2;
		weapon.armTime = 3;

		weapon.aimTime.add(-26);
		weapon.aimTime.add(-18);
		weapon.aimTime.add(-14);
		weapon.aimTime.add(-12);
		weapon.aimTime.add(-11);
		weapon.aimTime.add(-11);
		weapon.aimTime.add(-10);
		weapon.aimTime.add(-10);
		
		// Starts from 0, no C
		weapon.pen.add(0);
		weapon.pen.add(0);
		weapon.pen.add(0);
		weapon.pen.add(0);
		weapon.pen.add(0);
		weapon.pen.add(0);

		weapon.dc.add(0);
		weapon.dc.add(0);
		weapon.dc.add(0);
		weapon.dc.add(0);
		weapon.dc.add(0);
		weapon.dc.add(0);

		weapon.bshc.add("0");
		weapon.bshc.add("0");
		weapon.bshc.add("0");
		weapon.bshc.add("0");
		weapon.bshc.add("0");
		weapon.bshc.add("0");

		weapon.bc.add(0);
		weapon.bc.add(0);
		weapon.bc.add(0);
		weapon.bc.add(0);
		weapon.bc.add(0);
		weapon.bc.add(0);
		
		weapon.ionDamage.add(20000);
		weapon.ionDamage.add(2513);
		weapon.ionDamage.add(651);
		weapon.ionDamage.add(155);
		weapon.ionDamage.add(72);
		weapon.ionDamage.add(54);
		weapon.ionWeapon = true;
		
		PCAmmo he = new PCAmmo("Ion Imploder");
		
		he.rangeList = new int[] {12,15,20};
		he.ammoRanges.put(12, new ExplosiveData(0,25,"0",0,0));
		he.ammoRanges.put(15, new ExplosiveData(0,11,"0",0,0));
		he.ammoRanges.put(20, new ExplosiveData(0,3,"0",0,0));
		
		weapon.pcAmmoTypes.add(he);
		
		weapons.add(weapon);
	}
	
	public void classBThermalImploder() {
		Weapons weapon = new Weapons();
		weapon.name = "Class-B Thermal Imploder";
		weapon.type = "Grenade";
		weapon.damage = 8;
		weapon.damageBonus = 0;
		weapon.damageMultiplier = 5;
		weapon.armorPiercing = 2;
		weapon.collateralDamage = 50;
		weapon.energyWeapon = false;
		weapon.fuze = 2;
		weapon.armTime = 3;

		weapon.aimTime.add(-26);
		weapon.aimTime.add(-18);
		weapon.aimTime.add(-14);
		weapon.aimTime.add(-12);
		weapon.aimTime.add(-11);
		weapon.aimTime.add(-11);
		weapon.aimTime.add(-10);
		weapon.aimTime.add(-10);
		
		// Starts from 0, no C
		weapon.pen.add(50);
		weapon.pen.add(5);
		weapon.pen.add(5);
		weapon.pen.add(5);
		weapon.pen.add(4);
		weapon.pen.add(3);

		weapon.dc.add(10);
		weapon.dc.add(8);
		weapon.dc.add(4);
		weapon.dc.add(4);
		weapon.dc.add(2);
		weapon.dc.add(2);

		weapon.bshc.add("*24");
		weapon.bshc.add("*6");
		weapon.bshc.add("*2");
		weapon.bshc.add("88");
		weapon.bshc.add("64");
		weapon.bshc.add("22");

		weapon.bc.add(20000);
		weapon.bc.add(2513);
		weapon.bc.add(651);
		weapon.bc.add(155);
		weapon.bc.add(72);
		weapon.bc.add(54);
		
		PCAmmo he = new PCAmmo("Imploder");
		
		he.rangeList = new int[] {12,15,20};
		he.ammoRanges.put(12, new ExplosiveData(7,0,"11",10,6));
		he.ammoRanges.put(15, new ExplosiveData(3,0,"5",8,5));
		he.ammoRanges.put(20, new ExplosiveData(1,0,"1",5,3));
		
		weapon.pcAmmoTypes.add(he);
		
		weapons.add(weapon);
	}
	
	public void DC15LE() {
		
		Weapons weapon = new Weapons();
		weapon.name = "DC15LE";
		
		weapon.type = "Heavy";
		weapon.scopeMagnification = "4-6x";
		weapon.magnification = 6;
		weapon.fullAutoROF = 6;
		weapon.suppressiveROF = 10;
		weapon.light = true;
		weapon.laser = true;
		weapon.irLaser = true;

		// Aim time
		weapon.aimTime.add(-21);
		weapon.aimTime.add(-12);
		weapon.aimTime.add(-8);
		weapon.aimTime.add(-6);
		weapon.aimTime.add(-4);
		weapon.aimTime.add(-3);
		weapon.aimTime.add(-1);
		weapon.aimTime.add(0);
		weapon.aimTime.add(1);
		weapon.aimTime.add(3);
		weapon.aimTime.add(3);
		weapon.aimTime.add(5);

		// 10 20 40 70 100 200 300 400
		// PEN
		weapon.pen.add(60);
		weapon.pen.add(58);
		weapon.pen.add(56);
		weapon.pen.add(52);
		weapon.pen.add(49);
		weapon.pen.add(47);
		weapon.pen.add(44);
		weapon.pen.add(38);
		// DC
		weapon.dc.add(8);
		weapon.dc.add(8);
		weapon.dc.add(8);
		weapon.dc.add(8);
		weapon.dc.add(8);
		weapon.dc.add(8);
		weapon.dc.add(8);
		weapon.dc.add(8);
		// BC
		weapon.ba.add(72);
		weapon.ba.add(65);
		weapon.ba.add(58);
		weapon.ba.add(55);
		weapon.ba.add(53);
		weapon.ba.add(51);
		weapon.ba.add(49);
		weapon.ba.add(45);

		// Ce stats
		weapon.ceStats.baseErgonomics = 50;
		
		String name = "Linked";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		pen.add(0);
		pen.add(0);
		pen.add(0);
		pen.add(0);
		pen.add(0);
		pen.add(0);
		
		ArrayList<Integer> dc = new ArrayList<Integer>();
		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);
		
		ArrayList<String> bshc = new ArrayList<String>();
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");

		ArrayList<Integer> bc = new ArrayList<Integer>();
		bc.add(35);
		bc.add(0);
		bc.add(0);
		bc.add(0);
		bc.add(0);
		bc.add(0);

		PCAmmo bolt = new PCAmmo(name, pen, dc, bshc, bc, 0, 0);
		bolt.linked = true;
		weapon.pcAmmoTypes.add(bolt);
		
		weapon.type = "Heavy";
		weapons.add(weapon);
		
	}
	
	public void Spear() {
		Weapons weapon = new Weapons();
		weapon.name = "Spear";
		
		try {
			weapon.meleeWeapon = MeleeWeapon.getMeleeWeapon(MeleeWeaponType.Spear);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		weapon.type = "Melee";
		weapons.add(weapon);
	}

	public void Gladius() {
		Weapons weapon = new Weapons();
		weapon.name = "Gladius";
		
		try {
			weapon.meleeWeapon = MeleeWeapon.getMeleeWeapon(MeleeWeaponType.Gladius);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		weapon.type = "Melee";
		weapons.add(weapon);
	}

	public void VibroKnife() {
		Weapons weapon = new Weapons();
		weapon.name = "Vibroknife";
		
		try {
			weapon.meleeWeapon = MeleeWeapon.getMeleeWeapon(MeleeWeaponType.VibroKnife);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		weapon.type = "Melee";
		weapons.add(weapon);
	}
	
	public void ShortBow() {
		Weapons weapon = new Weapons();

		weapon.name = "Short Bow";
		weapon.targetROF = 8;
		weapon.suppressiveROF = 2;
		weapon.type = "Bow";
		weapon.equipedTroopers = new ArrayList<>();
		weapon.scopeMagnification = "";
		weapon.weaponBonus = 10;
		weapon.damage = 16;
		weapon.damageBonus = 0;
		weapon.damageMultiplier = 0;
		weapon.armorPiercing = 3;
		weapon.tracers = false;
		weapon.assembled = false;
		weapon.assembleCost = 6;
		weapon.assembleProgress = 0;
		weapon.ammoCapacity = 1;
		weapon.ammoLoaded = 1;
		weapon.loadTime = 6;
		weapon.loadProgress = 0;
		weapon.sab = 0;
		weapon.fullAutoROF = 1;
		weapon.tracers = false;
		weapon.light = false;
		weapon.laser = false;
		weapon.staticWeapon = false;
		weapon.irLaser = false;

		weapon.aimTime.add(-11);
		weapon.aimTime.add(-10);
		weapon.aimTime.add(-9);
		weapon.aimTime.add(-8);
		weapon.aimTime.add(-7);

		// 10 20 40 70 100 200 300 400
		// PEN
		weapon.pen.add(2);
		weapon.pen.add(2);
		weapon.pen.add(2);
		weapon.pen.add(1);
		weapon.pen.add(1);
		weapon.pen.add(1);
		weapon.pen.add(1);
		weapon.pen.add(1);
		// DC
		weapon.dc.add(2);
		weapon.dc.add(2);
		weapon.dc.add(2);
		weapon.dc.add(2);
		weapon.dc.add(1);
		weapon.dc.add(1);
		weapon.dc.add(1);
		weapon.dc.add(1);
		// BA
		weapon.ba.add(37);
		weapon.ba.add(33);
		weapon.ba.add(29);
		weapon.ba.add(22);
		weapon.ba.add(10);
		weapon.ba.add(4);
		weapon.ba.add(1);
		weapon.ba.add(0);

		// Ce stats
		weapon.ceStats.baseErgonomics = 80;
		weapon.ceStats.reloadTime = 5;
		weapon.loadTime = 5;
		weapons.add(weapon);
	}

	public void GoblinJavelin() {
		Weapons weapon = new Weapons();

		weapon.name = "Goblin Javelin";
		weapon.targetROF = 8;
		weapon.suppressiveROF = 2;
		weapon.type = "Throw";
		weapon.equipedTroopers = new ArrayList<>();
		weapon.scopeMagnification = "";
		weapon.weaponBonus = 10;
		weapon.damage = 16;
		weapon.damageBonus = 0;
		weapon.damageMultiplier = 0;
		weapon.armorPiercing = 3;
		weapon.tracers = false;
		weapon.assembled = false;
		weapon.assembleCost = 6;
		weapon.assembleProgress = 0;
		weapon.ammoCapacity = 1;
		weapon.ammoLoaded = 1;
		weapon.loadTime = 6;
		weapon.loadProgress = 0;
		weapon.sab = 0;
		weapon.fullAutoROF = 1;
		weapon.tracers = false;
		weapon.light = false;
		weapon.laser = false;
		weapon.staticWeapon = false;
		weapon.irLaser = false;

		weapon.aimTime.add(-15);
		weapon.aimTime.add(-13);
		weapon.aimTime.add(-12);
		weapon.aimTime.add(-11);
		weapon.aimTime.add(-10);

		// 10 20 40 70 100 200 300 400
		// PEN
		weapon.pen.add(2);
		weapon.pen.add(2);
		weapon.pen.add(2);
		weapon.pen.add(1);
		weapon.pen.add(1);
		weapon.pen.add(1);
		weapon.pen.add(1);
		weapon.pen.add(1);
		// DC
		weapon.dc.add(3);
		weapon.dc.add(2);
		weapon.dc.add(2);
		weapon.dc.add(2);
		weapon.dc.add(2);
		weapon.dc.add(1);
		weapon.dc.add(1);
		weapon.dc.add(1);
		// BA
		weapon.ba.add(20);
		weapon.ba.add(15);
		weapon.ba.add(8);
		weapon.ba.add(0);
		weapon.ba.add(0);
		weapon.ba.add(0);
		weapon.ba.add(0);
		weapon.ba.add(0);

		// Ce stats
		weapon.ceStats.baseErgonomics = 80;
		weapon.ceStats.reloadTime = 3;
		weapon.loadTime = 3;
		weapons.add(weapon);
	}

	public void T4GMG() {
		Weapons weapon = new Weapons();

		weapon.name = "T-4 GMG";
		weapon.targetROF = 8;
		weapon.suppressiveROF = 12;
		weapon.type = "Static";
		weapon.equipedTroopers = new ArrayList<>();
		weapon.scopeMagnification = "4-6x";
		weapon.weaponBonus = 10;
		weapon.damage = 16;
		weapon.damageBonus = 0;
		weapon.damageMultiplier = 0;
		weapon.armorPiercing = 3;
		weapon.tracers = true;
		weapon.assembled = false;
		weapon.assembleCost = 6;
		weapon.assembleProgress = 0;
		weapon.ammoCapacity = 50;
		weapon.ammoLoaded = 50;
		weapon.loadTime = 6;
		weapon.loadProgress = 0;
		weapon.sab = 3;
		weapon.fullAutoROF = 4;
		weapon.tracers = false;
		weapon.light = true;
		weapon.laser = true;
		weapon.staticWeapon = true;
		weapon.irLaser = true;

		weapon.aimTime.add(-36);
		weapon.aimTime.add(-25);
		weapon.aimTime.add(-16);
		weapon.aimTime.add(-11);
		weapon.aimTime.add(-9);
		weapon.aimTime.add(-1);
		weapon.aimTime.add(0);
		weapon.aimTime.add(1);
		weapon.aimTime.add(2);
		weapon.aimTime.add(3);
		weapon.aimTime.add(4);

		// 10 20 40 70 100 200 300 400
		// PEN
		weapon.pen.add(288);
		weapon.pen.add(288);
		weapon.pen.add(288);
		weapon.pen.add(288);
		weapon.pen.add(288);
		weapon.pen.add(288);
		weapon.pen.add(288);
		weapon.pen.add(288);
		// DC
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		// BA
		weapon.ba.add(23);
		weapon.ba.add(20);
		weapon.ba.add(15);
		weapon.ba.add(13);
		weapon.ba.add(10);
		weapon.ba.add(5);
		weapon.ba.add(3);
		weapon.ba.add(1);

		// Minimum Arc
		weapon.ma.add(.7);
		weapon.ma.add(.8);
		weapon.ma.add(1.0);
		weapon.ma.add(2.0);
		weapon.ma.add(2.5);
		weapon.ma.add(3.0);
		weapon.ma.add(3.2);
		weapon.ma.add(4.0);

		String name = "HEAT";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		ArrayList<Integer> dc = new ArrayList<Integer>();
		ArrayList<String> bshc = new ArrayList<String>();
		ArrayList<Integer> bc = new ArrayList<Integer>();
		pen.add(5);
		pen.add(4);
		pen.add(4);
		pen.add(4);
		pen.add(3);
		pen.add(3);

		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);
		dc.add(1);

		bshc.add("*9");
		bshc.add("*2");
		bshc.add("74");
		bshc.add("54");
		bshc.add("24");
		bshc.add("13");

		bc.add(6100);
		bc.add(417);
		bc.add(115);
		bc.add(85);
		bc.add(35);
		bc.add(18);

		PCAmmo heat = new PCAmmo(name, pen, dc, bshc, bc, 288, 10);
		weapon.pcAmmoTypes.add(heat);

		name = "ION";
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

		PCAmmo ion = new PCAmmo(name, pen, dc, bshc, bc, 2, 10);
		ion.ionDamage.add(1200);
		ion.ionDamage.add(600);
		ion.ionDamage.add(200);
		ion.ionDamage.add(112);
		ion.ionDamage.add(75);
		ion.ionDamage.add(50);
		weapon.pcAmmoTypes.add(ion);

		weapon.pcAmmoTypes.add(new PCAmmo(SmokeType.SMOKE_GRENADE));

		// Ce stats
		weapon.ceStats.baseErgonomics = 30;
		weapons.add(weapon);
	}

}
