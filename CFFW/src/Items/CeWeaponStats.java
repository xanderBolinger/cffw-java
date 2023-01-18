package Items;

import java.io.Serializable;

import UtilityClasses.DiceRoller;

public class CeWeaponStats implements Serializable {

	public int baseErgonomics;
	public boolean criticalHit = false;
	public int suppression = 1;
	public Item magazine; 
	public int reloadTime = 6;
	
	public void singleFire() {
		if(magazine == null)
			return; 
		
		magazine.ammo.depletionPoints++; 
		
	}
	
	public void burst(Weapons weapon) {
		if(magazine == null)
			return; 
		
		magazine.ammo.depletionPoints += weapon.fullAutoROF; 
	}
	
	public boolean ammoCheck() {
		if(magazine == null)
			return false; 
		
		int depletionSum = magazine.ammo.depletionPoints;
		
		/*for(int i = 0; i < magazine.ammo.depletionPoints; i++) {
			depletionSum += DiceRoller.d6_exploding();
		}*/
		
		
		if(depletionSum < magazine.ammo.shots) {
			return true;
		} else {
			magazine = null; 
			return false;
		}
		
	}
	
	
}
