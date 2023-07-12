package Melee.Gear;

import java.util.ArrayList;

import Melee.Gear.MeleeWeapon.WeaponSize;

public class MeleeWeaponData {

	public enum MeleeWeaponType {
		ChainSword,VibroKnife,PowerSword,Spear,Gladius
	}
	
	public MeleeWeaponData() {
		MeleeWeapon.meleeWeapons = new ArrayList<MeleeWeapon>();
		
		for(var weaponType : MeleeWeaponType.values()) {
			try {
				createMeleeWeapon(weaponType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void createMeleeWeapon(MeleeWeaponType meleeWeaponType) throws Exception {
	
		switch(meleeWeaponType) {
		case ChainSword:
			MeleeWeapon.meleeWeapons.add(new MeleeWeapon(meleeWeaponType, 
					0, 6, 0, 6, true, WeaponSize.MEDIUM, 5, 0, 0, 3,
					40, 0, 0));
			break;
		case Gladius:
			MeleeWeapon.meleeWeapons.add(new MeleeWeapon(meleeWeaponType, 
					5, 6, 0, 6, false, WeaponSize.MEDIUM, 0, 0, 2, 2,
					0, 0, 3));
			break;
		case PowerSword:
			MeleeWeapon.meleeWeapons.add(new MeleeWeapon(meleeWeaponType, 
					7, 6, 0, 6, false, WeaponSize.HEAVY, 6, 0, 3, 3,
					100, 0, 80));
			break;
		case Spear:
			MeleeWeapon.meleeWeapons.add(new MeleeWeapon(meleeWeaponType, 
					7, 7, 0, 8, false, WeaponSize.MEDIUM, 0, 0, 2, 2,
					0, 0, 3));
			break;
		case VibroKnife:
			MeleeWeapon.meleeWeapons.add(new MeleeWeapon(meleeWeaponType, 
					6, 7, 0, 8, false, WeaponSize.LIGHT, 2, 0, 3, 2,
					40, 0, 60));
			break;
		default:
			throw new Exception("Melee weapon not found for melee weapon type: "+meleeWeaponType);
		
		}
		
		
	}
	
}
