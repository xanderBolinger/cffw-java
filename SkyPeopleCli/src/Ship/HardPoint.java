package Ship;

import java.util.ArrayList;

import Mechanics.DiceRoller;
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
	
	
	public void destroyWeapon() {
		
		Weapon weapon = weapons.get(DiceRoller.randum_number(0, weapons.size() - 1));
		if(!weapon.destroyed) {
			weapon.destroyed = true; 
			return;
		}
		
	}
	
	public String toString(int hardPointIndex) {
		String rslts = "";
		
		rslts += "Hard Point " + hardPointIndex + "\n";
		rslts += "Armor: " + armor + "\n";
		rslts += "Fire Arc Index: "+ fireArcIndex +"\n";
		
		for(Weapon wep : weapons) {
			rslts += wep.toString() + "\n";
		}
		
		return rslts;
	}
	
}
