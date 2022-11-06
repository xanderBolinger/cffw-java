package CorditeExpansionFirearms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;

public class CalledShots {

	public int almSum;
	public int sizeAlm;
	private int eal;
	private ArrayList<Integer> calledShotBounds = new ArrayList<>();

	public CalledShots(int almSum, int sizeAlm) {
		this.almSum = almSum;
		this.sizeAlm = sizeAlm;
	}

	public enum ShotTarget {
		NONE, HEAD, BODY, LEGS
	}

	public void setCalledShotBounds(ShotTarget target) {

		int calledShotTarget;

		switch (target) {
			case HEAD:
				calledShotTarget = 0;
				break;
			case BODY:
				calledShotTarget = 1;
				break;
			case LEGS:
				calledShotTarget = 2;
				break;
			default:
				calledShotTarget = 0;
				break;
		}

		// System.out.println("Inside set called shot bounds");

		int sheetIndex = calledShotTarget;

		if (sheetIndex <= -1)
			return;

		try {

			FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\hitlocationzones.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(sheetIndex);

			// System.out.println("Work book 1");

			int ss = 0;

			for (int i = 1; i < 25; i++) {

				Row row = worksheet.getRow(i);

				Cell myCell = row.getCell(1);

				if (myCell.getCellType() == CellType.NUMERIC) {

					// System.out.println("Cell: "+myCell.getNumericCellValue());

					if (almSum <= myCell.getNumericCellValue()) {
						// System.out.println("ALMSum: "+ALMSum);
						// System.out.println("calledShotBounds: "+calledShotBounds.size());

						Cell low1 = row.getCell(4);
						Cell high1 = row.getCell(5);
						Cell low2 = row.getCell(6);
						Cell high2 = row.getCell(7);

						// System.out.println("Get Cells");

						int lowBound1;
						int lowBound2;
						int highBound1;
						int highBound2;

						if (low1.getCellType() != CellType.NUMERIC) {
							// System.out.println("Pass 1");
							lowBound1 = -1;
						} else {
							// System.out.println("Pass 2");
							lowBound1 = (int) low1.getNumericCellValue();
						}

						if (low2.getCellType() != CellType.NUMERIC) {
							// System.out.println("Pass 3");
							lowBound2 = -1;
						} else {
							// System.out.println("Pass 4");
							lowBound2 = (int) low2.getNumericCellValue();
						}

						if (high1.getCellType() != CellType.NUMERIC) {
							// System.out.println("Pass 5");
							highBound1 = -1;
						} else {
							// System.out.println("Pass 6");
							highBound1 = (int) high1.getNumericCellValue();
						}

						if (high2.getCellType() != CellType.NUMERIC) {
							// System.out.println("Pass 7");
							highBound2 = -1;
						} else {
							// System.out.println("Pass 8");
							highBound2 = (int) high2.getNumericCellValue();
						}

						/*
						 * System.out.println("bounds: "+lowBound1+" "+lowBound2+" "+ highBound1 + " "+
						 * highBound2);
						 */

						calledShotBounds.add(lowBound1);
						calledShotBounds.add(highBound1);
						calledShotBounds.add(lowBound2);
						calledShotBounds.add(highBound2);

						ss = (int) row.getCell(2).getNumericCellValue();
						// System.out.println("SS: "+ss);
						// System.out.println("calledShotBounds: "+calledShotBounds.size());
						break;
					}
				}

				// System.out.println("i: "+i);

			}

			// System.out.println("Work book 2");

			if (sizeAlm < ss)
				ss = sizeAlm;

			eal = almSum + ss;

			if (calledShotBounds.get(2) < 1)
				calledShotBounds.set(2, -1);

			if (calledShotBounds.get(3) < 1)
				calledShotBounds.set(3, -1);

			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public int getHitLocation() {
		
		ArrayList<Integer> number = new ArrayList<>();
		
		for(int i = calledShotBounds.get(0); i <= calledShotBounds.get(1); i++) {
			number.add(i);
		}
		
		for(int i = calledShotBounds.get(1); i <= calledShotBounds.get(2); i++) {
			number.add(i);
		}
		
		return number.get(DiceRoller.randInt(0, number.size()-1));
	}
	
	public int getEal() {
		return eal;
	}
}
