package Injuries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Items.PCAmmo;
import Items.Weapons;
import UtilityClasses.ExcelUtility;
import UtilityClasses.PCUtility;

public class Injuries implements Serializable{
	public int pd; 
	public int shockPd;
	public String location; 
	public boolean disabled; 
	public boolean recovered = false; 
	public Weapons wep; 
	
	public int ht = Integer.MAX_VALUE;
	public int tpMinutes = 0;
	
	public Injuries(int pd, String location, boolean disabled, Weapons wep) {
		
		this.pd = pd; 
		this.location = location; 
		this.disabled = disabled; 
		this.wep = wep; 
		
		try {
			// System.out.println("Path: "+path);
			FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\aid.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);

			int woundRow = 0;
			for (int i = 2; i < 41; i++) {
				if (pd <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					woundRow += i;
					break;
				}
			}

			if (woundRow < 2) {
				woundRow = 2;
			}

			ht = (int) worksheet.getRow(woundRow).getCell(11).getNumericCellValue();

			excelFile.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String toString() {
		if(wep == null) {
			return "Injury: "+location+", PD: "+pd+", Disabled: "+disabled + (recovered == true ? " RECOVERED" : ", HT: "+ht+", Recovery: "+PCUtility.convertTime(tpMinutes));
		} else {
			return "Injury: "+location+", Weapon: "+wep.name+", PD: "+pd+", Disabled: "+disabled+(recovered == true ? " RECOVERED" : ", HT: "+ht+", Recovery: "+PCUtility.convertTime(tpMinutes));
		}
		
		
	}
	
}
