package Items;

import java.util.ArrayList;

import Conflict.SmokeStats.SmokeType;
import Items.FlameThrower.FlameThrowerType;
import Melee.Gear.MeleeWeapon;
import Melee.Gear.MeleeWeaponData.MeleeWeaponType;

public class HumanWarhammerWeapons {

	public ArrayList<Weapons> weapons = new ArrayList<>();

	public HumanWarhammerWeapons() {
	
		// Boltguns 
		BolterMk5b();
		HeavyBolterMk4();
		
		// Lasguns
		m36Lasgun();
		
		// Launchers 
		SoundStrikePattern();
		
		// Pistol 
		BoltPistol();
		
		Flamer();
		
		// Grenade
		FragGrenade();
		KrakGrenade();
		
		// Melee 
		ChainSword();
		PowerSword();
		
	}

	public void Flamer() {
		Weapons weapon = new Weapons();
		weapon.name = "Astartes Flamer";
		weapon.type = "Launcher";
		weapon.flameThrower = new FlameThrower(FlameThrowerType.AstartesFlamer);
		
		weapons.add(weapon);
	}

	public void MeltaGun() {
		
	}
	
	public void PlasmaGun() {
		
	}
	
	public void PlasmaPistol() {
		
	}
	
	public void BoltPistol() {
		Weapons weapon = new Weapons();
		weapon.name = "MKIII Ultima Pattern";
		weapon.type = "Pistol";
		weapon.fullAutoROF = 3;
		weapon.light = true;
		weapon.laser = true;
		weapon.irLaser = true;

		// Aim time
		weapon.aimTime.add(-15);
		weapon.aimTime.add(-11);
		weapon.aimTime.add(-10);
		weapon.aimTime.add(-9);
		weapon.aimTime.add(-8);
		weapon.aimTime.add(-7);

		// 10 20 40 70 100 200 300 400
		// PEN
		weapon.pen.add(66);
		weapon.pen.add(57);
		weapon.pen.add(54);
		weapon.pen.add(46);
		weapon.pen.add(41);
		weapon.pen.add(38);
		weapon.pen.add(35);
		weapon.pen.add(32);
		// DC
		weapon.dc.add(10);
		weapon.dc.add(9);
		weapon.dc.add(9);
		weapon.dc.add(8);
		weapon.dc.add(8);
		weapon.dc.add(7);
		weapon.dc.add(7);
		weapon.dc.add(6);
		
		weapon.ba.add(43);
		weapon.ba.add(37);
		weapon.ba.add(33);
		weapon.ba.add(28);
		weapon.ba.add(23);
		weapon.ba.add(19);
		weapon.ba.add(15);
		weapon.ba.add(9);

		// Ce stats
		weapon.ceStats.baseErgonomics = 50;
		
		String name = "Linked";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		pen.add(66);
		pen.add(66);
		pen.add(66);
		pen.add(66);
		pen.add(66);
		pen.add(66);

		ArrayList<Integer> dc = new ArrayList<Integer>();
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);

		ArrayList<String> bshc = new ArrayList<String>();
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");

		ArrayList<Integer> bc = new ArrayList<Integer>();
		bc.add(60);
		bc.add(0);
		bc.add(0);
		bc.add(0);
		bc.add(0);
		bc.add(0);


		PCAmmo bolt = new PCAmmo(name, pen, dc, bshc, bc, 0, 0);
		bolt.linked = true;
		weapon.pcAmmoTypes.add(bolt);
		
		weapons.add(weapon);
	}
	
	public void SoundStrikePattern() {
		Weapons weapon = new Weapons();
		weapon.name = "Sound Strike Pattern";
		weapon.type = "Launcher";
		weapon.launcherHomingInfantry = true;
		weapon.launcherHomingVehicle = true;
		weapon.homingHitChance = 75;
		weapon.suppressiveROF = 1;

		weapon.energyWeapon = false;

		String name = "Krak";
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
		heat.linked = false;
		weapon.pcAmmoTypes.add(heat);

		name = "Frag";
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
		he.linked = false;
		weapon.pcAmmoTypes.add(he);

		// Aim time
		weapon.aimTime.add(-27);
		weapon.aimTime.add(-17);
		weapon.aimTime.add(-10);
		weapon.aimTime.add(-8);
		weapon.aimTime.add(-5);
		weapon.aimTime.add(-3);
		weapon.aimTime.add(-2);
		weapon.aimTime.add(-1);
		weapon.aimTime.add(0);
		weapon.aimTime.add(1);

		weapon.ba.add(23);
		weapon.ba.add(10);
		weapon.ba.add(1);
		weapon.ba.add(1);

		// Ce stats
		weapon.ceStats.baseErgonomics = 10;
		weapons.add(weapon);
	}

	public void KrakGrenade() {
		Weapons weapon = new Weapons();
		
		weapon.name = "Krak Grenade";
		weapon.type = "Grenade";
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
		weapon.pen.add(142);
		weapon.pen.add(5);
		weapon.pen.add(5);
		weapon.pen.add(5);
		weapon.pen.add(4);
		weapon.pen.add(3);

		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(9);
		weapon.dc.add(9);
		weapon.dc.add(8);
		weapon.dc.add(6);

		weapon.bshc.add("*6");
		weapon.bshc.add("*1");
		weapon.bshc.add("88");
		weapon.bshc.add("64");
		weapon.bshc.add("22");
		weapon.bshc.add("11");

		weapon.bc.add(23000);
		weapon.bc.add(1013);
		weapon.bc.add(251);
		weapon.bc.add(55);
		weapon.bc.add(22);
		weapon.bc.add(14);
		
		weapons.add(weapon);
	}
	
	public void FragGrenade() {
		Weapons weapon = new Weapons();
		
		weapon.name = "Astartes Frag Grenade";
		weapon.type = "Grenade";
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
		weapon.dc.add(9);
		weapon.dc.add(6);
		weapon.dc.add(5);
		weapon.dc.add(2);
		weapon.dc.add(2);

		weapon.bshc.add("*24");
		weapon.bshc.add("*6");
		weapon.bshc.add("*2");
		weapon.bshc.add("88");
		weapon.bshc.add("64");
		weapon.bshc.add("22");

		weapon.bc.add(18000);
		weapon.bc.add(2213);
		weapon.bc.add(551);
		weapon.bc.add(105);
		weapon.bc.add(32);
		weapon.bc.add(24);
		
		weapons.add(weapon);
	}
	
	public void m36Lasgun() {
		Weapons weapon = new Weapons();
		weapon.name = "M36 Lasgun";
		
		weapon.type = "Rifle";
		weapon.scopeMagnification = "4x";
		weapon.magnification = 4;
		weapon.fullAutoROF = 6;
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
		
		weapon.type = "Rifle";
		weapons.add(weapon);
	}
	
	public void HeavyBolterMk4() {
		Weapons weapon = new Weapons();
		weapon.name = "MKIV Heavy Bolter";
		
		weapon.type = "Rifle";
		weapon.scopeMagnification = "4x";
		weapon.magnification = 4;
		weapon.fullAutoROF = 7;
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
		weapon.pen.add(90);
		weapon.pen.add(86);
		weapon.pen.add(77);
		weapon.pen.add(64);
		weapon.pen.add(56);
		weapon.pen.add(51);
		weapon.pen.add(48);
		weapon.pen.add(45);
		// DC
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		// BC
		weapon.ba.add(69);
		weapon.ba.add(63);
		weapon.ba.add(55);
		weapon.ba.add(52);
		weapon.ba.add(50);
		weapon.ba.add(48);
		weapon.ba.add(47);
		weapon.ba.add(43);

		// Ce stats
		weapon.ceStats.baseErgonomics = 35;
		
		String name = "Linked";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		pen.add(122);
		pen.add(122);
		pen.add(122);
		pen.add(122);
		pen.add(122);
		pen.add(122);

		ArrayList<Integer> dc = new ArrayList<Integer>();
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);

		ArrayList<String> bshc = new ArrayList<String>();
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");


		ArrayList<Integer> bc = new ArrayList<Integer>();
		bc.add(250);
		bc.add(125);
		bc.add(25);
		bc.add(0);
		bc.add(0);
		bc.add(0);

		PCAmmo bolt = new PCAmmo(name, pen, dc, bshc, bc, 0, 0);
		bolt.linked = true;
		weapon.pcAmmoTypes.add(bolt);
		
		weapon.type = "Rifle";
		weapons.add(weapon);
	}
	
	public void BolterMk5b() {
		Weapons weapon = new Weapons();
		weapon.name = "MKVb Bolter";
		
		weapon.type = "Rifle";
		weapon.scopeMagnification = "4x";
		weapon.magnification = 4;
		weapon.fullAutoROF = 4;
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
		weapon.pen.add(86);
		weapon.pen.add(77);
		weapon.pen.add(64);
		weapon.pen.add(56);
		weapon.pen.add(51);
		weapon.pen.add(48);
		weapon.pen.add(45);
		weapon.pen.add(42);
		// DC
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(10);
		weapon.dc.add(9);
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
		pen.add(112);
		pen.add(112);
		pen.add(112);
		pen.add(112);
		pen.add(112);
		pen.add(112);

		ArrayList<Integer> dc = new ArrayList<Integer>();
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		
		ArrayList<String> bshc = new ArrayList<String>();
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");
		bshc.add("0");

		ArrayList<Integer> bc = new ArrayList<Integer>();
		bc.add(150);
		bc.add(70);
		bc.add(15);
		bc.add(0);
		bc.add(0);
		bc.add(0);

		PCAmmo bolt = new PCAmmo(name, pen, dc, bshc, bc, 0, 0);
		bolt.linked = true;
		weapon.pcAmmoTypes.add(bolt);
		
		weapon.type = "Rifle";
		weapons.add(weapon);
	} 
	
	// ChainSword,VibroKnife,PowerSword,Spear,Gladius

	public void ChainSword() {
		Weapons weapon = new Weapons();
		weapon.name = "Chain Sword";
		
		try {
			weapon.meleeWeapon = MeleeWeapon.getMeleeWeapon(MeleeWeaponType.ChainSword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		weapon.type = "Melee";
		weapons.add(weapon);
	}

	public void PowerSword() {
		Weapons weapon = new Weapons();
		weapon.name = "Power Sword";
		
		try {
			weapon.meleeWeapon = MeleeWeapon.getMeleeWeapon(MeleeWeaponType.PowerSword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		weapon.type = "Melee";
		weapons.add(weapon);
	}

	

}
