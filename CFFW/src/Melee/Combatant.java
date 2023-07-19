package Melee;

import java.io.Serializable;

import Items.Weapons;
import Melee.Gear.MeleeShield;
import Melee.Gear.MeleeWeapon;
import Trooper.Trooper;

public class Combatant implements Serializable {

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
