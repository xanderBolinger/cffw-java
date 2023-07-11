package Melee;

import Items.Weapons;
import Trooper.Trooper;

public class Combatant {

	public Trooper trooper;
	public int shock;
	public boolean knockedDown;
	
	public MeleeWeapon meleeWeapon;
	public MeleeShield meleeShield;
	
	public Combatant(Trooper trooper) {
		this.trooper = trooper;
		var wep = new Weapons().findWeapon(trooper.meleeWep);
		meleeWeapon = wep.meleeWeapon;
		meleeShield = trooper.meleeShield != null ? trooper.meleeShield.meleeShield : null;
	}
	
	
}
