package Items;

import java.util.ArrayList;

import Conflict.SmokeStats.SmokeType;
import Melee.Gear.MeleeWeapon;
import Melee.Gear.MeleeWeaponData.MeleeWeaponType;

public class AdditionalOrdnance {

	public ArrayList<Weapons> weapons = new ArrayList<>();

	public AdditionalOrdnance() {
		HeavyBlasterCannon();
	}
	

	public void HeavyBlasterCannon() {
		Weapons weapon = new Weapons();
		
		weapon.name = "Heavy Laser Cannon";
		weapon.type = "Ordnance";
		weapon.energyWeapon = false;

		String name = "HEAT";
		ArrayList<Integer> pen = new ArrayList<Integer>();
		ArrayList<Integer> dc = new ArrayList<Integer>();
		ArrayList<String> bshc = new ArrayList<String>();
		ArrayList<Integer> bc = new ArrayList<Integer>();

		pen.add(112); // 0
		pen.add(112); // 1
		pen.add(112); // 2
		pen.add(112); // 3
		pen.add(112); // 4
		pen.add(112); // 5
		pen.add(112); // 6
		pen.add(112); // 8
		pen.add(112); // 10

		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);
		dc.add(10);

		bshc.add("*100");
		bshc.add("*2");
		bshc.add("95");
		bshc.add("60");
		bshc.add("35");
		bshc.add("10");
		bshc.add("5");
		bshc.add("3");
		bshc.add("1");

		bc.add(56000);
		bc.add(1200);
		bc.add(983);
		bc.add(803);
		bc.add(258);
		bc.add(139);
		bc.add(56);
		bc.add(25);
		bc.add(12);

		PCAmmo he = new PCAmmo(name, pen, dc, bshc, bc, 20, 7);
		he.ordnance = true;
		weapon.pcAmmoTypes.add(he);
		
		weapons.add(weapon);
	}

	

}
