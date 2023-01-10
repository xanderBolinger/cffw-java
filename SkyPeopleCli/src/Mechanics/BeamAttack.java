package Mechanics;

import Mechanics.DamageAllocation.HitSide;
import Ship.Ship;
import Ship.Weapon;

public class BeamAttack {

	
	public static void beamAttack(Ship target, Weapon weapon, int range, int ecm, int eccm, HitSide hitSide) throws Exception {
		System.out.println("Beam Attack");
		int damage = getBeamDamage(weapon, range, ecm, eccm);
		
		System.out.println("Damage: "+damage);
		
		if(damage > 0)
			DamageAllocation.allocateDamage(damage, target, hitSide);
		
	}
	
	public static int getBeamDamage(Weapon weapon, int range, int ecm, int eccm) throws Exception {
		ecm -= eccm;
		
		if(ecm < 0)
			ecm = 0; 
		
		int roll = DiceRoller.d10() - ecm;
		
		if(roll < 1)
			return 0;
		else if(roll > 10)
			roll = 10;
		
		return weapon.getDamage(roll, range);
		
	}
	
}
