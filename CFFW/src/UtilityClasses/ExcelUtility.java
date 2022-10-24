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

	public static double getResultsTwoWayFixedValues(double value1, double value2, String fileName) throws Exception {

		double result = -1;

		try {

			FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\" + fileName));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);
			int rows = countRows(worksheet);
			int columns = countColumns(worksheet);

			int targetColumn = getTargetColumn(columns, value1, worksheet);

			if (targetColumn < 0) {
				workbook.close();
				throw new Exception("Value 1 not found in file: " + fileName);
			}

			int targetRow = -1;
			for (int i = 0; i < rows; i++) {
				if (worksheet.getRow(i).getCell(0).getCellType() != CellType.NUMERIC)
					continue;

				if (value2 == worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					targetRow = i;
					break;
				}
			}

			if (targetRow < 0) {
				workbook.close();
				throw new Exception("Value 2 not found in file: " + fileName);
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
					"Result for Value 1: " + value1 + ", Value 2: " + value2 + ", File: " + fileName + " not found.");
		}

		return result;
	}

	public static int getTargetColumn(int columns, double value, Sheet worksheet) {
		int targetColumn = -1;
		for (int i = 0; i < columns; i++) {
			if (worksheet.getRow(0).getCell(i).getCellType() != CellType.NUMERIC)
				continue;

			if (value == worksheet.getRow(0).getCell(i).getNumericCellValue()) {
				targetColumn = i;
				break;
			}

		}

		return targetColumn;
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
