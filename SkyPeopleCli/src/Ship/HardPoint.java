package Ship;

import java.util.ArrayList;

import Ship.Weapon.FireType;
import Ship.Weapon.WeaponType;

public class HardPoint {

	public ArrayList<Weapon> weapons;
	public int armor; 
	public int fireArcIndex; 
	
	public HardPoint(int armor, int fireArcIndex) {
		this.armor = armor;
		this.fireArcIndex = fireArcIndex;
		weapons = new ArrayList<>();
	}

	public void addWeapon(WeaponType weaponType, FireType fireType) {
		weapons.add(new Weapon(weaponType, fireType));
	}
	
	
}
