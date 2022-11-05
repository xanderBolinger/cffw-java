package CorditeExpansionDamage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public static void applyHit(int pen, int dc, boolean open, Trooper trooper) throws Exception {

		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\hittable.xlsx"));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet worksheet = workbook.getSheetAt(0);

		int hitLocation = getHitLocation();

		String hitLocationName = PcDamageUtility.getHitLocationName(open, hitLocation, worksheet);

		pen = getPen(pen, hitLocation, open, trooper);

		if (pen < 1) {
			workbook.close();
			return;
		}

		// DC 10 check
		if(dc == 10) {
			Damage.statusCheck(5 * trooper.KO, trooper);
		}
		
		// Weapon hit check
		if(hitLocationName.equals("Weapon Critical")) {
			trooper.ceStatBlock.weapon.ceStats.criticalHit = true;
			workbook.close();
			return;
		}
		
		// Regular damage
		String damageString = PcDamageUtility.getDamageString(pen, dc, open, hitLocation, worksheet);
		int pd = PcDamageUtility.getDamageValue(damageString);
		pd *= PcDamageUtility.getMultiplier(damageString);
		
		trooper.ceStatBlock.medicalStatBlock.increasePd(pd);

		// Blood loss pd
		BloodLossLocation location = PcDamageUtility.getBloodLossPD((double) pen, dc, hitLocationName, trooper);
		if(location != null)
			trooper.ceStatBlock.medicalStatBlock.addBleed(location);
		
		// Status Check
		statusCheck(pd, trooper);

		// Apply Pain

		workbook.close();
	}

	public static int incapacitationImpulses(int physicalDamage) throws Exception {
		int roll = DiceRoller.randInt(0, 9);

		String incapStr = ExcelUtility.getStringFromSheet(roll, physicalDamage,
				"Formatted Excel Files\\incapacitationtime.xlsx", true, true);

		int multiplier = getIncapMultiplier(incapStr);
		int value = getIncapTimeValue(incapStr);
		
		return value * multiplier;
	}
	
	public static int getIncapTimeValue(String incapStr) throws Exception {
		Pattern pattern = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(incapStr);
	    boolean matchFound = matcher.find();
	    if(matchFound) {
	    	return Integer.parseInt(matcher.group());
	    }
		
		throw new Exception("incapStr: "+incapStr+", has no numeric value.");
	}

	public static int getIncapMultiplier(String incapStr) throws Exception {
		Pattern pattern = Pattern.compile("[a-zA-Z]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(incapStr);
		boolean matchFound = matcher.find();

		char incapCharacter = '0';

		if (matchFound) {
			incapCharacter = matcher.group().toCharArray()[0];
		}

		int multiplier;

		switch (incapCharacter) {
			case 'p':
				multiplier = 2;
				break;
			case 'm':
				multiplier = 120;
				break;
			case 'h':
				multiplier = 120*60;
				break;
			case 'd':
				multiplier = 120*60*24;
				break;
			default:
				throw new Exception("Incap Character: "+incapCharacter+" not valid.");
		}
		
		return multiplier;
	}
	
	public static void trooperDamageCheck(Trooper trooper) {
		// Rolls incapacitation test
		int physicalDamage = trooper.physicalDamage;

		statusCheck(physicalDamage, trooper);

	}

	public static void statusCheck(int physicalDamage, Trooper trooper) {
		int KO = trooper.KO, roll = DiceRoller.randInt(0, 99);

		//System.out.println("Roll: "+roll);

		if (physicalDamage >= KO * 5) {
			if (roll <= 60) {
				trooper.ceStatBlock.medicalStatBlock.setUnconscious(physicalDamage);
			} else if (roll <= 94) {
				trooper.ceStatBlock.medicalStatBlock.setStunned(physicalDamage, trooper.ceStatBlock);
			} else if (roll <= 96) {
				trooper.ceStatBlock.medicalStatBlock.setDazed(physicalDamage, trooper.ceStatBlock);
			} else if (roll <= 97) {
				trooper.ceStatBlock.medicalStatBlock.setDisoriented(physicalDamage, trooper.ceStatBlock);
			}
		} else if (physicalDamage >= KO * 4) {
			if (roll <= 26) {
				trooper.ceStatBlock.medicalStatBlock.setUnconscious(physicalDamage);
			} else if (roll <= 53) {
				trooper.ceStatBlock.medicalStatBlock.setStunned(physicalDamage, trooper.ceStatBlock);
			} else if (roll <= 82) {
				trooper.ceStatBlock.medicalStatBlock.setDazed(physicalDamage, trooper.ceStatBlock);
			} else if (roll <= 97) {
				trooper.ceStatBlock.medicalStatBlock.setDisoriented(physicalDamage, trooper.ceStatBlock);
			}
		} else if (physicalDamage >= KO * 3) {
			if (roll <= 13) {
				trooper.ceStatBlock.medicalStatBlock.setUnconscious(physicalDamage);
			} else if (roll <= 31) {
				trooper.ceStatBlock.medicalStatBlock.setStunned(physicalDamage, trooper.ceStatBlock);
			} else if (roll <= 52) {
				trooper.ceStatBlock.medicalStatBlock.setDazed(physicalDamage, trooper.ceStatBlock);
			} else if (roll <= 74) {
				trooper.ceStatBlock.medicalStatBlock.setDisoriented(physicalDamage, trooper.ceStatBlock);
			}
		} else if (physicalDamage >= KO * 2) {
			if (roll <= 2) {
				trooper.ceStatBlock.medicalStatBlock.setUnconscious(physicalDamage);
			} else if (roll <= 8) {
				trooper.ceStatBlock.medicalStatBlock.setStunned(physicalDamage, trooper.ceStatBlock);
			} else if (roll <= 16) {
				trooper.ceStatBlock.medicalStatBlock.setDazed(physicalDamage, trooper.ceStatBlock);
			} else if (roll <= 24) {
				trooper.ceStatBlock.medicalStatBlock.setDisoriented(physicalDamage, trooper.ceStatBlock);
			}
		} else if (physicalDamage >= KO) {
			if (roll <= 0) {
				trooper.ceStatBlock.medicalStatBlock.setUnconscious(physicalDamage);
			} else if (roll <= 2) {
				trooper.ceStatBlock.medicalStatBlock.setStunned(physicalDamage, trooper.ceStatBlock);
			} else if (roll <= 5) {
				trooper.ceStatBlock.medicalStatBlock.setDazed(physicalDamage, trooper.ceStatBlock);
			} else if (roll <= 9) {
				trooper.ceStatBlock.medicalStatBlock.setDisoriented(physicalDamage, trooper.ceStatBlock);
			}
		}

		// System.out.println("KO: "+KO+", Roll: "+roll+", TN: "+TN);

	}

	public static int getPen(int pen, int hitLocation, boolean open, Trooper trooper) {
		pen = Damage.shieldEpen(hitLocation, pen, open, trooper);

		try {
			pen = Damage.armorEpen(hitLocation, pen, open, trooper);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pen;
	}

	public static int getHitLocation() {
		return DiceRoller.randInt(0, 99);
	}

	public static int shieldEpen(int hitLocation, int pen, boolean open, Trooper trooper) {

		if (trooper.personalShield == null || !trooper.personalShield.isZoneProtected(hitLocation, open)) {
			return pen;
		}

		trooper.personalShield.currentShieldStrength -= pen;

		if (trooper.personalShield.currentShieldStrength < 0) {
			pen = Math.abs(trooper.personalShield.currentShieldStrength);
			trooper.personalShield.currentShieldStrength = 0;
			return pen;
		} else {
			return 0;
		}
	}

	public static int armorEpen(int hitLocation, int pen, boolean open, Trooper trooper) throws Exception {

		if (trooper.armor == null || !trooper.armor.isZoneProtected(hitLocation, open)) {
			return pen;
		}

		int baseProtectionFactor = trooper.armor.getPF(hitLocation, open);

		if (baseProtectionFactor < 1) {
			return pen;
		}

		try {
			int modifiedPf = trooper.armor.getModifiedProtectionFactor(hitLocation, baseProtectionFactor, open);
			// System.out.println("Modified PF: "+modifiedPf);
			return pen - modifiedPf;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		throw new Exception("Failed to get armor epen, hitLocation: " + hitLocation + ", pen: " + pen + ", open: "
				+ open + ", trooper: " + trooper.number + " " + trooper.name);
	}

}
