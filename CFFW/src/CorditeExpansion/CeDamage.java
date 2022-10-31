package CorditeExpansion;

import Trooper.Trooper;
import UtilityClasses.DiceRoller;

public class CeDamage {

	Trooper trooper; 
	
	
	public CeDamage(Trooper trooper) {
		this.trooper = trooper;
	}
	
	public void applyHit(int pen, int dc, boolean open) {
		
		int hitLocation = getHitLocation();
		
		pen = shieldEpen(hitLocation, pen, open);
		pen = armorEpen(hitLocation, pen, open);
		
		if(pen < 1) {
			return;
		}
		
		
		
	}
	
	public int shieldEpen(int hitLocation, int pen, boolean open) {
		
		if(trooper.personalShield == null || !trooper.personalShield.isZoneProtected(hitLocation, open)) {
			return pen;
		}
		
		trooper.personalShield.currentShieldStrength -= pen;
		
		if(trooper.personalShield.currentShieldStrength < 0) {
			pen = Math.abs(trooper.personalShield.currentShieldStrength);
			trooper.personalShield.currentShieldStrength = 0;
			return pen;
		} else {
			return 0;
		}
	}
	
	public int getHitLocation() {
		return DiceRoller.randInt(0, 99);
	}
	
	public int armorEpen(int hitLocation, int pen, boolean open) {
		
		if(trooper.armor == null || !trooper.armor.isZoneProtected(hitLocation, open)) {
			return pen;
		}
		
		
		
		return 0; 
	}
	
	public int getPhyisicalDamage() {
		return -1;
	}
	
}
