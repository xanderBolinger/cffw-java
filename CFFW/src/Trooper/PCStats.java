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

	public PCStats(Trooper trooper, boolean CFFW) {
		if(trooper.wep == null) {
			return; 
		}

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
		double mSpeed = TrooperUtility.maximumSpeed(trooper.encumberance, trooper);

		Weapons wep = new Weapons();
		wep = wep.findWeapon(trooper.wep);

		
		trooper.sl = PCUtility.getSL(wep.type, trooper);

		// setSkillLevel(trooper);
		// System.out.println("slRifle: "+slRifle);
		trooper.isf = trooper.sl + (trooper.wit);
		// System.out.println("trooper.wit: "+trooper.wit);
		
		int CA = TrooperUtility.calculateCA(mSpeed, trooper.isf);
		if(CFFW) {
			CA = TrooperUtility.calculateCACFFW(mSpeed, trooper.isf);
		}
		//System.out.println("mSpeed: "+mSpeed+", ISF: "+trooper.isf+", CA: "+CA);
		
		double fatiguePoints = trooper.fatigueSystem.fatiguePoints.get();
		
		if(fatiguePoints < 11 && fatiguePoints > 5) {
			CA -= 1;
		} else if(fatiguePoints <= 15) {
			CA -= 2;
		} else if(fatiguePoints <= 19 ) {
			CA -= 3;
		} else if(fatiguePoints <= 23 ) {
			CA -= 4;
		} else if(fatiguePoints <= 27 ) {
			CA -= 5;
		} else if(fatiguePoints <= 31 ) {
			CA -= 6;
		} else if(fatiguePoints <= 32 ) {
			CA -= 7;
		} else if(fatiguePoints <= 33 ) {
			CA -= 8;
		} else if(fatiguePoints <= 34 ) {
			CA -= 9;
		} else if(fatiguePoints > 34) {
			CA -= 9 + fatiguePoints - 34;
		}
		
		if(CA < 2)
			CA = 2;
		
		trooper.DALM = TrooperUtility.defensiveALM(trooper.isf);
		trooper.combatActions = CA;
		trooper.KO = TrooperUtility.getKO(trooper);

	}

	

}