package Items;

import java.util.ArrayList;

import Conflict.SmokeStats.SmokeType;
import Melee.Gear.MeleeWeapon;
import Melee.Gear.MeleeWeaponData.MeleeWeaponType;

public class HumanWarhammerWeapons {

	public ArrayList<Weapons> weapons = new ArrayList<>();

	public HumanWarhammerWeapons() {
		ChainSword();
		PowerSword();
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
