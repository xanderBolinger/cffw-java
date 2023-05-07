package Items;

import java.util.ArrayList;

import Conflict.SmokeStats.SmokeType;

public class WeaponsStats {
	
	public ArrayList<Weapons> weapons = new ArrayList<>();
	
	public WeaponsStats() {
		T4GMG();
	}
	
	public void T4GMG() {
		Weapons weapon = new Weapons();
		
		weapon.name = "T-4 GMG";
		weapon.targetROF = 8;
		weapon.suppressiveROF = 8;
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
		weapon.pcAmmoTypes.add(new PCAmmo(SmokeType.SMOKE_GRENADE));
		
		// Ce stats
		weapon.ceStats.baseErgonomics = 30;
		weapons.add(weapon);
	}
	
}
