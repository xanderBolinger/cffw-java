package UtilityClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	public static String path = System.getProperty("user.dir");

	public static double getResultsTwoWayFixedValues(double matchColumn, double matchRow, 
			String fileName, boolean assendingColumns, boolean dessendingRows) throws Exception {

		double result = -1;

		//System.out.println("match column: "+matchColumn+", match row: "+matchRow);
		
		try {

			FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\" + fileName));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);
			int columns = countColumns(worksheet);
			int rows = countRows(worksheet);

			int targetColumn = getTargetColumn(columns, matchColumn, worksheet, assendingColumns);
			//System.out.println("Target Column: "+targetColumn);
			
			if (targetColumn < 0) {
				workbook.close();
				throw new Exception("Value 1: "+matchColumn+", not found in file: " + fileName);
			}

			int targetRow = getTargetRow(rows, matchRow, worksheet, dessendingRows);
			//System.out.println("Target Row: "+targetRow);
			
			if (targetRow < 0) {
				workbook.close();
				throw new Exception("Value 2:"+matchRow+" not found in file: " + fileName);
			}

			result = worksheet.getRow(targetRow).getCell(targetColumn).getNumericCellValue();

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (result == -1) {
			throw new Exception(
					"Result for Value 1: " + matchColumn + ", Value 2: " + matchRow + ", File: " + fileName + " not found.");
		}

		//System.out.println("Result: "+result);
		return result;
	}

	public static int getTargetRow(int rows, double value, Sheet worksheet, boolean assending) throws Exception {
		int targetRow = -1;
		for (int i = 0; i < rows; i++) {			
			if (worksheet.getRow(i).getCell(0).getCellType() != CellType.NUMERIC)
				continue;

			if (checkTarget(value, worksheet.getRow(i).getCell(0).getNumericCellValue(), assending)) {
				targetRow = i;
				break;
			}

		}

		return targetRow;
	}
	
	public static int getTargetColumn(int columns, double value, Sheet worksheet, boolean assending) {
		int targetColumn = -1;
		for (int i = 0; i < columns; i++) {
			if (worksheet.getRow(0).getCell(i).getCellType() != CellType.NUMERIC)
				continue;

			if (checkTarget(value, worksheet.getRow(0).getCell(i).getNumericCellValue(), assending)) {
				targetColumn = i;
				break;
			}

		}

		return targetColumn;
	}
	
	public static boolean checkTarget(double value, double cellValue, boolean assending) {
		if(assending) {
			return value <= cellValue ? true : false; 
		} else {
			return value >= cellValue ? true : false; 
		}
	}

	public static int countRows(Sheet worksheet) throws Exception {

		int i = 0;
		while (true) {

			if (worksheet.getRow(i) == null || worksheet.getRow(i).getCell(0) == null
					|| worksheet.getRow(i).getCell(0).getCellType() == CellType.BLANK) {
				return i;
			}

			i++;
		}

	}

	public static int countColumns(Sheet worksheet) throws Exception {
		int i = 0;
		while (true) {

			if (worksheet.getRow(0) == null || worksheet.getRow(0).getCell(i) == null
					|| worksheet.getRow(0).getCell(i).getCellType() == CellType.BLANK) {
				return i;
			}

			i++;
		}
	}

}
