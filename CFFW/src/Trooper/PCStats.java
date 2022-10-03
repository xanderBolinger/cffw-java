package Trooper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Items.Weapons;
import UtilityClasses.PCUtility;
import UtilityClasses.TrooperUtility;

public class PCStats implements Serializable {

//Pistol	Rifle	Launcher	Heavy	Subgun	SAL	ISF	IT	A#	DALM	CA	CAPI	KO	Balance	Climb	Composure	Dodge	Endurance	Expression	Grapple	Hold	Jump/Leap	Lift/Pull	Resist Pain	Search	Spot/Listen	Stealth	Calm Other	Diplomacy	Barter	Command	Tactics	Det. Motives	Intimidate	Persuade	Digi. Systems	Long Gun	Pistol	Launcher	Heavy	Subgun	Explosives	First Aid 	Navigation	Swim	Throw

	private String path = System.getProperty("user.dir") + "\\";

	public PCStats(Trooper trooper) {
		 

		/*
		 * this.sal = skill + 6; this.isf = sal + wit;
		 * 
		 * if(isf < 3) { this.init = 32; this.actions = 2; } else if (isf < 6) {
		 * this.init = 28; this.actions = 2; } else if (isf < 8) { this.init = 24;
		 * this.actions = 3; } else if (isf < 11) { this.init = 20; this.actions = 3; }
		 * else if (isf < 15) { this.init = 16; this.actions = 3; } else if (isf < 21) {
		 * this.init = 12; this.actions = 4; } else if (isf < 29) { this.init = 8;
		 * this.actions = 5; } else if (isf < 40) { this.init = 4; this.actions = 6; }
		 * else { this.init = 0; this.actions = 7; }
		 */

		// System.out.println("Encumberance: "+trooper.encumberance);
		double mSpeed = maximumSpeed(trooper.encumberance, trooper);

		Weapons wep = new Weapons();
		wep = wep.findWeapon(trooper.wep);

		trooper.sl = PCUtility.getSL(wep.type, trooper);

		// setSkillLevel(trooper);
		// System.out.println("slRifle: "+slRifle);
		trooper.isf = trooper.sl + (trooper.wit);
		// System.out.println("trooper.wit: "+trooper.wit);
		int CA = calculateCA(mSpeed, trooper.isf);
		trooper.DALM = defensiveALM(trooper.isf);
		trooper.combatActions = CA;
		trooper.KO = TrooperUtility.getKO(trooper);

	}

	public double baseSpeed(int encum, Trooper trooper) {

		double baseSpeed = 0;
		int column = 0;
		try {

			FileInputStream excelFile = new FileInputStream(new File(path + "BaseSpeed.xlsx"));
			// System.out.println("Path: "+path);
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			Row row = worksheet.getRow(0);

			if (encum <= 10) {

				column = 1;

			} else {

				for (int i = 1; i < 19; i++) {
					if (encum < row.getCell(i + 1).getNumericCellValue()) {
						column = i;
						break;
					}
				}
			}

			for (int x = 1; x < 22; x++) {

				if ((int) trooper.str == worksheet.getRow(x).getCell(0).getNumericCellValue()) {
					baseSpeed = worksheet.getRow(x).getCell(column).getNumericCellValue();
				}
			}

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baseSpeed;

	}

	public double maximumSpeed(int encum, Trooper trooper) {
		double baseSpeed = baseSpeed(encum, trooper);

		int column = 0;
		try {

			FileInputStream excelFile = new FileInputStream(new File(path + "MaximumSpeed.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			Row row = worksheet.getRow(0);

			for (int i = 1; i < 9; i++) {
				if (baseSpeed == row.getCell(i).getNumericCellValue()) {
					column = i;
					break;
				}
			}

			for (int x = 1; x < 22; x++) {

				if ((int) trooper.agi == worksheet.getRow(x).getCell(0).getNumericCellValue()) {
					baseSpeed = worksheet.getRow(x).getCell(column).getNumericCellValue();
				}
			}

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baseSpeed;

	}

	public int calculateCA(double ms, int isf) {
		// System.out.println("Calc CA ms: "+ms+", isf: "+isf);
		int CA = 0;
		int column = 0;
		try {

			FileInputStream excelFile = new FileInputStream(new File(path + "CA.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			Row row = worksheet.getRow(0);

			if (isf <= 7) {

				column = 1;

			} else {

				for (int i = 1; i < 19; i++) {
					if (isf < row.getCell(i + 1).getNumericCellValue()) {
						column = i;
						break;
					}
				}
			}

			for (int x = 1; x < 22; x++) {
				//System.out.println("Line 187 PC Stats X: "+x);
				//System.out.println("MS: "+ms+", ISF: "+isf);
				if (ms == worksheet.getRow(x).getCell(0).getNumericCellValue()) {
					CA = (int) worksheet.getRow(x).getCell(column).getNumericCellValue();
					break;
				}
			}

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return CA;

	}

	public int defensiveALM(int isf) {

		int dAlm = 0;

		try {

			FileInputStream excelFile = new FileInputStream(new File(path + "DefensiveALM.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			for (int i = 1; i < 41; i++) {

				if (isf == (int) worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					dAlm = (int) worksheet.getRow(i).getCell(1).getNumericCellValue();

				}

			}

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dAlm;

	}

}