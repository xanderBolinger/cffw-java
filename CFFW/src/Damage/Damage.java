package Damage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;
import UtilityClasses.PcDamageUtility;

public class Damage {

	private Damage() {
		
	}
	
	public void applyHit(int pen, int dc, boolean open, Trooper trooper) throws Exception {
		
		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\hittable.xlsx"));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet worksheet = workbook.getSheetAt(0);
		
		int hitLocation = getHitLocation();
		
		String hitLocationName = PcDamageUtility.getHitLocationName(open, hitLocation, worksheet);
		
		pen = getPen(pen, hitLocation, open, trooper);
		
		if(pen < 1) {
			workbook.close();
			return;
		}
		
		// DC 10 check 
		
		// Weapon hit check
		
		// Regular damage 
		int pd = PcDamageUtility.getDamageValue(PcDamageUtility.getDamageString(pen, dc, open, hitLocation, worksheet));
				
		// Blood loss pd 
		pd += PcDamageUtility.getBloodLossPD((double) pen, dc, hitLocationName, trooper);
		
		
		// Status Check 
		trooper.physicalDamage += pd;

		KoTest(pd, trooper);
		
		// Apply Pain 
		
		workbook.close();
	}
	
	
	public static void trooperDamageCheck(Trooper trooper) {
		// Rolls incapacitation test
		int physicalDamage = trooper.physicalDamage;
		
		KoTest(physicalDamage, trooper);
		
		
		
	}
	
	public static void KoTest(int physicalDamage, Trooper trooper) {
		int TN = 0, KO = trooper.KO, roll = DiceRoller.randInt(0, 99);
		
		
		if (physicalDamage >= KO * 5) {
			TN = 60;
		} else if (physicalDamage >= KO * 4) {
			TN = 26;
		} else if (physicalDamage >= KO * 3) {
			TN = 13;
		} else if (physicalDamage >= KO * 2) {
			TN = 12;
		} 
		
		//System.out.println("KO: "+KO+", Roll: "+roll+", TN: "+TN);
		if(roll < TN) {
			trooper.conscious = false;
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
	
}
