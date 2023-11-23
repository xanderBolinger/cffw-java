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

import CeHexGrid.FloatingTextManager;
import CorditeExpansionCharacters.Zombie;
import CorditeExpansionStatBlock.StatBlock.Stance;
import Explosion.Explosion;
import Items.Weapons;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;
import UtilityClasses.PcDamageUtility;

public class Damage {

	private Damage() {

	}

	public static void applyHit(String weaponName, int pen, int dc, boolean open, Trooper trooper, int hitLocation) throws Exception {
		
		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\hittable.xlsx"));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet worksheet = workbook.getSheetAt(0);

		String hitLocationName = PcDamageUtility.getHitLocationName(open, hitLocation, worksheet);
		
		pen = getPen(pen, hitLocation, open, trooper);

		if(trooper.zombie) {
			Zombie zombie = (Zombie) trooper;
			zombie.hitLocationCheck(hitLocationName, pen);
			workbook.close();
			return;
		}
		
		if (pen < 1) {
			FloatingTextManager.addFloatingText(trooper.ceStatBlock.cord, "Pen < 1, Stopped by armor");
			workbook.close();
			return;
		}

		// DC 10 check
		if (dc == 10) {
			Damage.statusCheck(5 * trooper.KO, trooper);
		}

		// Weapon hit check
		if (hitLocationName.equals("Weapon Critical")) {
			trooper.ceStatBlock.rangedStatBlock.weapon.ceStats.criticalHit = true;
			workbook.close();
			return;
		}

		// Regular damage
		String damageString = PcDamageUtility.getDamageString(pen, dc, open, hitLocation, worksheet);
		int pd = PcDamageUtility.getDamageValue(damageString);
		pd *= PcDamageUtility.getMultiplier(damageString);

		trooper.ceStatBlock.medicalStatBlock.increasePd(pd);

		String rslts = "Hit from " +weaponName+": "+ hitLocationName + ", PD: " + pd;

		// Blood loss pd
		BloodLossLocation location = PcDamageUtility.getBloodLossPD((double) pen, dc, hitLocationName, trooper);
		if (location != null) {
			trooper.ceStatBlock.medicalStatBlock.addBleed(location);
			rslts += ", Blood Loss: " + location.blpd;
		}

		// Disabled
		boolean disabled = PcDamageUtility.getDisabled(damageString);

		if (disabled) {
			addDisabledLimb(trooper, hitLocationName, pd);
			rslts += ", Disabled: " + disabled;
		}

		FloatingTextManager.addFloatingText(trooper.ceStatBlock.cord, rslts);

		// Status Check
		statusCheck(trooper.ceStatBlock.medicalStatBlock.getPdTotal(), trooper);

		// Apply Pain
		trooper.ceStatBlock.medicalStatBlock.pain = trooper.ceStatBlock.medicalStatBlock.getPdTotal() / 100;

		// Flinch 
		if(pd > 2 * trooper.KO) {
			trooper.ceStatBlock.clearAim();
			trooper.ceStatBlock.rangedStatBlock.stabalized = false;
		}
		
		trooper.ceStatBlock.medicalStatBlock.addInjury(new Injury(weaponName, hitLocationName, pd, disabled));
		
		workbook.close();
	}
	
	public static void applyHit(String weaponName, int pen, int dc, boolean open, Trooper trooper) throws Exception {

		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\hittable.xlsx"));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet worksheet = workbook.getSheetAt(0);

		int hitLocation = getHitLocation();

		String hitLocationName = PcDamageUtility.getHitLocationName(open, hitLocation, worksheet);

		pen = getPen(pen, hitLocation, open, trooper);

		if(trooper.zombie) {
			Zombie zombie = (Zombie) trooper;
			zombie.hitLocationCheck(hitLocationName, pen);
			workbook.close();
			return;
		}
		
		if (pen < 1) {
			workbook.close();
			return;
		}

		// DC 10 check
		if (dc == 10) {
			Damage.statusCheck(5 * trooper.KO, trooper);
		}

		// Weapon hit check
		if (hitLocationName.equals("Weapon Critical")) {
			trooper.ceStatBlock.rangedStatBlock.weapon.ceStats.criticalHit = true;
			workbook.close();
			return;
		}

		// Regular damage
		String damageString = PcDamageUtility.getDamageString(pen, dc, open, hitLocation, worksheet);
		int pd = PcDamageUtility.getDamageValue(damageString);
		pd *= PcDamageUtility.getMultiplier(damageString);

		trooper.ceStatBlock.medicalStatBlock.increasePd(pd);

		String rslts = "Hit from " +weaponName+": "+ hitLocationName + ", PD: " + pd;

		// Blood loss pd
		BloodLossLocation location = PcDamageUtility.getBloodLossPD((double) pen, dc, hitLocationName, trooper);
		if (location != null) {
			trooper.ceStatBlock.medicalStatBlock.addBleed(location);
			rslts += ", Blood Loss: " + location.blpd;
		}

		// Disabled
		boolean disabled = PcDamageUtility.getDisabled(damageString);

		if (disabled) {
			addDisabledLimb(trooper, hitLocationName, pd);
			rslts += ", Disabled: " + disabled;
		}

		FloatingTextManager.addFloatingText(trooper.ceStatBlock.cord, rslts);

		// Status Check
		statusCheck(trooper.ceStatBlock.medicalStatBlock.getPdTotal(), trooper);

		// Apply Pain
		trooper.ceStatBlock.medicalStatBlock.pain = trooper.ceStatBlock.medicalStatBlock.getPdTotal() / 100;

		// Flinch 
		if(pd > 2 * trooper.KO) {
			trooper.ceStatBlock.clearAim();
			trooper.ceStatBlock.rangedStatBlock.stabalized = false;
		}
		
		trooper.ceStatBlock.medicalStatBlock.addInjury(new Injury(weaponName, hitLocationName, pd, disabled));
		
		workbook.close();
	}

	public static void addDisabledLimb(Trooper trooper, String location, int pd) {
		if (location.equals("Neck Spine") || location.equals("Liver - Spine")
				|| location.equals("Spine")) {
			trooper.disabledArms = trooper.arms;
			trooper.disabledLegs = trooper.legs;
			trooper.ceStatBlock.medicalStatBlock.setUnconscious(pd);
		} else if (location.equals("Heart")) {
			trooper.ceStatBlock.medicalStatBlock.setUnconscious(pd);
			trooper.ceStatBlock.medicalStatBlock.alive = false;
		} else if (location.equals("Shoulder") || location.equals("Arm Flesh")
				|| location.equals("Arm Bone") || location.equals("Elbow")
				|| location.equals("Forearm Flesh") || location.equals("Forearm Bone")
				|| location.equals("Hand")) {
			trooper.disabledArms++;
		} else if (location.equals("Thigh Flesh") || location.equals("Thigh Bone")
				|| location.equals("Knee") || location.equals("Shin Flesh")
				|| location.equals("Shin Bone") || location.equals("Ankle - Foot")) {
			trooper.disabledLegs++;
		}
		
		setDisabledLimbPenalty(trooper);
		
	}
	
	public static void setDisabledLimbPenalty(Trooper trooper) {
		
		if(trooper.disabledArms == 1) {
			trooper.ceStatBlock.rangedStatBlock.maxAim = trooper.ceStatBlock.rangedStatBlock.weapon.aimTime.size() - 2;
			trooper.ceStatBlock.clearAim();
		} else if(trooper.disabledArms > 1) {
			trooper.ceStatBlock.rangedStatBlock.maxAim = 2;
			trooper.ceStatBlock.clearAim();
		}
		
		if(trooper.disabledLegs == 1) {
			trooper.ceStatBlock.quickness = trooper.maximumSpeed.get(trooper) - 3;
		} else if(trooper.disabledLegs > 1) {
			trooper.ceStatBlock.quickness = trooper.maximumSpeed.get(trooper) - 5;
		} 
		
	}

	public static int incapacitationImpulses(int physicalDamage) throws Exception {
		int roll = DiceRoller.roll(0, 9);

		if (physicalDamage > 1000)
			physicalDamage = 1000;

		String incapStr = ExcelUtility.getStringFromSheet(roll, physicalDamage,
				"Formatted Excel Files\\incapacitationtime.xlsx", true, true);

		int multiplier = getIncapMultiplier(incapStr);
		int value = getIncapTimeValue(incapStr);

		return value * multiplier;
	}
	
	public static int incapacitationImpulsesFlashbang() throws Exception {
		int roll = DiceRoller.roll(0, 9);
		int physicalDamage = 0;

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
		if (matchFound) {
			return Integer.parseInt(matcher.group());
		}

		throw new Exception("incapStr: " + incapStr + ", has no numeric value.");
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
			multiplier = 120 * 60;
			break;
		case 'd':
			multiplier = 120 * 60 * 24;
			break;
		default:
			throw new Exception("Incap Character: " + incapCharacter + " not valid.");
		}

		return multiplier;
	}

	public static void trooperDamageCheck(Trooper trooper) {
		// Rolls incapacitation test
		int physicalDamage = trooper.physicalDamage;

		statusCheck(physicalDamage, trooper);

	}

	public static void statusCheck(int physicalDamage, Trooper trooper) {
		int KO = trooper.KO, roll = DiceRoller.roll(0, 99);

		// System.out.println("Roll: "+roll);

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
		return DiceRoller.roll(0, 99);
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
	
	
	public static void explode(Weapons explosive, int rangePCHexes, Trooper trooper) {
		int column = Explosion.getExplsoionRangeColumn(rangePCHexes);
		String bshc = explosive.bshc.get(column); 
		int bc = explosive.bc.get(column);
		
		if(trooper.ceStatBlock.stance == Stance.PRONE) {
			bc = (int) ((double)(bc) * 0.75);
		}
		
		//System.out.println("BC: "+bc);
		
		trooper.ceStatBlock.medicalStatBlock.increasePd(bc);
		
		int shrapHits = 0; 
		
		try {
	        int hitChance = Integer.parseInt(bshc);
	        int hitRoll = DiceRoller.roll(0, 99);
	        if(hitRoll <= hitChance)
	        	shrapHits++;
	        else {
	        	FloatingTextManager.addFloatingText(trooper.ceStatBlock.cord, "Shrapnell Missed, Roll: "+hitChance+", TN: "+hitChance);
	        	return; 
	        }
	        
	    } catch (NumberFormatException nfe) {
	    	shrapHits = PcDamageUtility.getDamageValue(bshc);
	    }
		
		//System.out.println("Shrap Hits: "+shrapHits);
		
		for(int i = 0; i < shrapHits; i++) {
			try {
				applyHit(explosive.name, explosive.pen.get(column), explosive.dc.get(column), !trooper.ceStatBlock.inCover, trooper);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		FloatingTextManager.addFloatingText(trooper.ceStatBlock.cord, "BC: "+bc+", Shrap Hits: "+shrapHits);
		
	}
	

}
