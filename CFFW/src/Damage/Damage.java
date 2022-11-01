package Damage;

import Trooper.Trooper;
import UtilityClasses.DiceRoller;

public class Damage {

	private Damage() {
		
	}
	
	public void applyHit(int pen, int dc, boolean open, Trooper trooper) {
		
		int hitLocation = getHitLocation();
		
		pen = getPen(pen, hitLocation, open, trooper);
		
		if(pen < 1) {
			return;
		}
		
		
		
	}
	
	public int getPen(int pen, int hitLocation, boolean open, Trooper trooper) {
		pen = Damage.shieldEpen(hitLocation, pen, open, trooper);
		
		try {
			pen =  Damage.armorEpen(hitLocation, pen, open, trooper);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pen;
	}
	
	
	public int getHitLocation() {
		return DiceRoller.randInt(0, 99);
	}
	
	
	public static int shieldEpen(int hitLocation, int pen, boolean open, Trooper trooper) {
		
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
	
	
	public static int armorEpen(int hitLocation, int pen, boolean open, Trooper trooper) throws Exception {
		
		if(trooper.armor == null || !trooper.armor.isZoneProtected(hitLocation, open)) {
			return pen;
		}
		
		int baseProtectionFactor = trooper.armor.getPF(hitLocation, open);
		
		if(baseProtectionFactor < 1) {
			return pen;
		}
		
		try {
			int modifiedPf = trooper.armor.getModifiedProtectionFactor(hitLocation, baseProtectionFactor, open);
			//System.out.println("Modified PF: "+modifiedPf);
			return pen - modifiedPf;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		throw new Exception("Failed to get armor epen, hitLocation: "+hitLocation
				+", pen: "+pen+", open: "+open+", trooper: "
				+trooper.number+" "+trooper.name);
	}
	
	public int getPhyisicalDamage() {
		return -1;
	}
	
}
