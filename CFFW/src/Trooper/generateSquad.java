package Trooper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import Company.Formation.LeaderType;
import Trooper.Factions.FactionManager;

public class generateSquad implements Serializable {
	private ArrayList<Trooper> individuals = new ArrayList<Trooper>();

	public generateSquad(String faction, String type) {
		//System.out.println("Pass Generate Squad");
		createSquad(faction, type);
	}

	// Returns any array of individuals
	public void createSquad(String faction, String type) {
		// Checks if type is empty
		if (type.equals("Empty")) {
			individuals.removeAll(individuals);
		}
		if (faction.equals("Clone Trooper Phase 1")) {
			createPhase1(type);
		} else if (faction.equals("CIS Battle Droid")) {
			createBattleDroid(type);
		} else if (faction.equals("UNSC")) {
			createUNSC(type);
		} else if (faction.equals("Covenant")) {
			//System.out.println("Pass Create Squad");
			createCovenant(type);
		} else {
			
			try {
				individuals.clear();
				FactionManager.getFactionFromName(faction).createSquad(type, individuals);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}

	}

	
	public void createCovenant(String type) {
		//System.out.println("Type: "+type);
		String faction = "Covenant";
		if (type.equals("Unggoy Lance")) {
			//System.out.println("Pass1234:");
			Trooper[] troopers = new Trooper[5];
			// Squad leader
			troopers[0] = new Trooper("Elite Minor - Lance Leader", faction);
			troopers[1] = new Trooper("Grunt Minor", faction);
			troopers[2] = new Trooper("Grunt Minor", faction);
			troopers[3] = new Trooper("Grunt Minor", faction);
			troopers[4] = new Trooper("Grunt Minor", faction);
			
			//System.out.println("Trooper: "+troopers[0]);
			setSquad(troopers);

		} else if (type.equals("Kig-Yar Lance - Marksman")) {
			Trooper[] troopers = new Trooper[5];
			// Squad leader
			troopers[0] = new Trooper("Elite Minor - Lance Leader", faction);
			troopers[1] = new Trooper("Jackal Minor - Marksman", faction);
			troopers[2] = new Trooper("Jackal Minor - Marksman", faction);
			troopers[3] = new Trooper("Jackal Minor - Marksman", faction);
			troopers[4] = new Trooper("Jackal Minor - Marksman", faction);
			
			//System.out.println("Trooper: "+troopers[0]);
			setSquad(troopers);
		} else if (type.equals("Kig-Yar Lance - Shields")) {
			Trooper[] troopers = new Trooper[5];
			// Squad leader
			troopers[0] = new Trooper("Elite Minor - Lance Leader", faction);
			troopers[1] = new Trooper("Jackal Minor", faction);
			troopers[2] = new Trooper("Jackal Minor", faction);
			troopers[3] = new Trooper("Jackal Minor", faction);
			troopers[4] = new Trooper("Jackal Minor", faction);
			
			//System.out.println("Trooper: "+troopers[0]);
			setSquad(troopers);
		} else if (type.equals("Unggoy Suicide Chargers")) {
			Trooper[] troopers = new Trooper[5];
			// Squad leader
			troopers[0] = new Trooper("Grunt Conscript", faction);
			troopers[1] = new Trooper("Grunt Conscript", faction);
			troopers[2] = new Trooper("Grunt Conscript", faction);
			troopers[3] = new Trooper("Grunt Conscript", faction);
			troopers[4] = new Trooper("Grunt Conscript", faction);
			
			//System.out.println("Trooper: "+troopers[0]);
			setSquad(troopers);
		}
	}
	
	public void createUNSC(String type) {
		//System.out.println("Type: "+type);
		String faction = "UNSC";
		if (type.equals("Riflesquad")) {
			//System.out.println("Pass1234:");
			Trooper[] troopers = new Trooper[9];
			// Squad leader
			troopers[0] = new Trooper("Squad Leader", faction);
			// Rifleman++
			troopers[1] = new Trooper("Rifleman++", faction);
			// Rifleman
			troopers[2] = new Trooper("Rifleman", faction);
			troopers[3] = new Trooper("Rifleman", faction);
			troopers[4] = new Trooper("Rifleman", faction);
			// Autorifleman
			troopers[5] = new Trooper("Autorifleman", faction);
			// Assistant Autorifleman
			troopers[6] = new Trooper("Assistant Autorifleman", faction);
			// Marksman
			troopers[7] = new Trooper("Marksman", faction);
			// Combat Life Saver
			troopers[8] = new Trooper("Combat Life Saver", faction);
			//System.out.println("Trooper: "+troopers[0]);
			setSquad(troopers);

		}
	}
	
	public void createPhase1(String type) {
		String faction = "Clone Trooper Phase 1";
		if (type.equals("Riflesquad")) {
			Trooper[] troopers = new Trooper[9];
			// Squad leader
			troopers[0] = new Trooper("Clone Squad Leader", faction);
			troopers[0].leaderType = LeaderType.SL;
			// Rifleman++
			troopers[1] = new Trooper("Clone Rifleman++", faction);
			troopers[1].leaderType = LeaderType.FTL;
			troopers[0].subordinates.add(troopers[1].identifier);
			// Rifleman
			troopers[2] = new Trooper("Clone Rifleman", faction);
			troopers[3] = new Trooper("Clone Rifleman", faction);
			troopers[4] = new Trooper("Clone Rifleman", faction);
			// Autorifleman
			troopers[5] = new Trooper("Clone Autorifleman", faction);
			// Assistant Autorifleman
			troopers[6] = new Trooper("Clone Assistant Autorifleman", faction);
			// Marksman
			troopers[7] = new Trooper("Clone Marksman", faction);
			// Combat Life Saver
			troopers[8] = new Trooper("Clone Combat Life Saver", faction);
			setSquad(troopers);

		} else if (type.equals("Special Riflesquad")) {
			Trooper[] troopers = new Trooper[12];
			// Squad leader
			troopers[0] = new Trooper("Clone Squad Leader", faction);
			troopers[0].leaderType = LeaderType.SL;
			// Rifleman++
			troopers[1] = new Trooper("Clone Rifleman++", faction);
			troopers[1].leaderType = LeaderType.FTL;
			troopers[0].subordinates.add(troopers[1].identifier);
			// Rifleman
			troopers[2] = new Trooper("Clone Rifleman++", faction);
			troopers[3] = new Trooper("Clone Autorifleman", faction);
			troopers[4] = new Trooper("Clone Assistant Autorifleman", faction);
			// Autorifleman
			troopers[5] = new Trooper("Clone Autorifleman", faction);
			// Assistant Autorifleman
			troopers[6] = new Trooper("Clone Assistant Autorifleman", faction);
			// Marksman
			troopers[7] = new Trooper("Clone Ammo Bearer", faction);
			// Combat Life Saver
			troopers[8] = new Trooper("Clone Marksman", faction);
			troopers[9] = new Trooper("Clone Combat Life Saver", faction);
			troopers[10] = new Trooper("Clone AT Specialist", faction);
			troopers[11] = new Trooper("Clone Assistant AT Specialist", faction);
			setSquad(troopers);

		} else if (type.equals("Platoon Squad")) {
			Trooper[] troopers = new Trooper[10];
			
			
			troopers[0] = new Trooper("Clone Platoon Leader", faction);
			troopers[0].leaderType = LeaderType.PC;
			
			// Squad leader
			troopers[1] = new Trooper("Clone Squad Leader", faction);
			troopers[1].leaderType = LeaderType.SL;
			troopers[0].subordinates.add(troopers[1].identifier);
			
			// Rifleman++
			troopers[2] = new Trooper("Clone Rifleman++", faction);
			troopers[2].leaderType = LeaderType.FTL;
			troopers[0].subordinates.add(troopers[2].identifier);
			troopers[1].subordinates.add(troopers[2].identifier);
			// Rifleman
			troopers[3] = new Trooper("Clone Rifleman", faction);
			troopers[4] = new Trooper("Clone Rifleman", faction);
			troopers[5] = new Trooper("Clone Rifleman", faction);
			// Autorifleman
			troopers[6] = new Trooper("Clone Autorifleman", faction);
			// Assistant Autorifleman
			troopers[7] = new Trooper("Clone Assistant Autorifleman", faction);
			// Marksman
			troopers[8] = new Trooper("Clone Marksman", faction);
			// Combat Life Saver
			troopers[9] = new Trooper("Clone Combat Life Saver", faction);
			
			setSquad(troopers);

		} else if (type.equals("Company Squad")) {
			Trooper[] troopers = new Trooper[11];
			
			
			troopers[0] = new Trooper("Clone Captain", faction);
			troopers[0].leaderType = LeaderType.CC;
			
			troopers[1] = new Trooper("Clone Platoon Leader", faction);
			troopers[1].leaderType = LeaderType.PC;
			troopers[0].subordinates.add(troopers[1].identifier);
			
			// Squad leader
			troopers[2] = new Trooper("Clone Squad Leader", faction);
			troopers[2].leaderType = LeaderType.SL;
			troopers[0].subordinates.add(troopers[2].identifier);
			troopers[1].subordinates.add(troopers[2].identifier);
			
			// Rifleman++
			troopers[3] = new Trooper("Clone Rifleman++", faction);
			troopers[3].leaderType = LeaderType.FTL;
			troopers[0].subordinates.add(troopers[3].identifier);
			troopers[1].subordinates.add(troopers[3].identifier);
			troopers[2].subordinates.add(troopers[3].identifier);
			
			// Rifleman
			troopers[4] = new Trooper("Clone Rifleman", faction);
			troopers[5] = new Trooper("Clone Rifleman", faction);
			troopers[6] = new Trooper("Clone Rifleman", faction);
			// Autorifleman
			troopers[7] = new Trooper("Clone Autorifleman", faction);
			// Assistant Autorifleman
			troopers[8] = new Trooper("Clone Assistant Autorifleman", faction);
			// Marksman
			troopers[9] = new Trooper("Clone Marksman", faction);
			// Combat Life Saver
			troopers[10] = new Trooper("Clone Combat Life Saver", faction);
			
			setSquad(troopers);

		} else if (type.equals("Commando Squad")) {
			Trooper[] troopers = new Trooper[4];
			// Squad leader
			troopers[0] = new Trooper("Republic Commando", faction);
			troopers[0].leaderType = LeaderType.SL;
			// Rifleman++
			troopers[1] = new Trooper("Republic Commando", faction);
			// Rifleman
			troopers[2] = new Trooper("Republic Commando", faction);
			troopers[3] = new Trooper("Republic Commando", faction);
			setSquad(troopers);

		}

	}

	/*
	 * B1 Seargeant B1 Rifleman B1 Marksman B1 Autorifleman B1 Assistant
	 * Autorifleman B1 AT B1 Assistant AT B2 Wristlaser
	 */
	public void createBattleDroid(String type) {
		String faction = "CIS Battle Droid";
		if (type.equals("Droid Riflesquad")) {
			Trooper[] troopers = new Trooper[8];
			// Sergeant
			troopers[0] = new Trooper("B1 Squad Leader", faction);
			// Rifleman
			troopers[1] = new Trooper("B1 Rifleman", faction);
			troopers[2] = new Trooper("B1 Rifleman", faction);
			troopers[3] = new Trooper("B1 Rifleman", faction);
			troopers[4] = new Trooper("B1 Rifleman", faction);
			troopers[5] = new Trooper("B1 Rifleman", faction);
			troopers[6] = new Trooper("B1 Rifleman", faction);
			troopers[7] = new Trooper("B1 Rifleman", faction);

			setSquad(troopers);
		} else if (type.equals("Heavy Droid Riflesquad")) {
			Trooper[] troopers = new Trooper[8];
			// Sergeant
			troopers[0] = new Trooper("B2", faction);
			// Rifleman
			troopers[1] = new Trooper("B2", faction);
			troopers[2] = new Trooper("B2", faction);
			troopers[3] = new Trooper("B2", faction);
			troopers[4] = new Trooper("B2", faction);
			troopers[5] = new Trooper("B2", faction);
			troopers[6] = new Trooper("B2", faction);
			troopers[7] = new Trooper("B2", faction);

			setSquad(troopers);
		} else if (type.equals("Droid Marksman")) {
			Trooper[] troopers = new Trooper[8];
			// Sergeant
			troopers[0] = new Trooper("B1 Squad Leader", faction);
			// Rifleman
			troopers[1] = new Trooper("B1 Rifleman", faction);
			troopers[2] = new Trooper("B1 Rifleman", faction);
			troopers[3] = new Trooper("B1 Rifleman", faction);
			troopers[4] = new Trooper("B1 Rifleman", faction);
			troopers[5] = new Trooper("B1 Marksman", faction);
			troopers[6] = new Trooper("B1 Marksman", faction);
			troopers[7] = new Trooper("B1 Marksman", faction);

			setSquad(troopers);
		} else if (type.equals("Droid AT Specalists")) {
			Trooper[] troopers = new Trooper[8];
			// Sergeant
			troopers[0] = new Trooper("B1 Squad Leader", faction);
			// Rifleman
			troopers[1] = new Trooper("B1 Rifleman", faction);
			troopers[2] = new Trooper("B1 Rifleman", faction);
			troopers[3] = new Trooper("B1 Rifleman", faction);
			troopers[4] = new Trooper("B1 AT Specialist", faction);
			troopers[5] = new Trooper("B1 Assistant AT Specialist", faction);
			troopers[6] = new Trooper("B1 AT Specialist", faction);
			troopers[7] = new Trooper("B1 Assistant AT Specialist", faction);

			setSquad(troopers);
		} else if (type.equals("Droid Fire Support")) {
			Trooper[] troopers = new Trooper[8];
			// Sergeant
			troopers[0] = new Trooper("B1 Squad Leader", faction);
			// Rifleman
			troopers[1] = new Trooper("B1 Rifleman", faction);
			troopers[2] = new Trooper("B1 Rifleman", faction);
			troopers[3] = new Trooper("B1 Rifleman", faction);
			troopers[4] = new Trooper("B1 Autorifleman", faction);
			troopers[5] = new Trooper("B1 Assistant Autorifleman", faction);
			troopers[6] = new Trooper("B1 Autorifleman", faction);
			troopers[7] = new Trooper("B1 Assistant Autorifleman", faction);

			setSquad(troopers);
		} else if (type.equals("Droid Integrated Squad")) {
			Trooper[] troopers = new Trooper[8];
			// Sergeant
			troopers[0] = new Trooper("B1 Squad Leader", faction);
			// Rifleman
			troopers[1] = new Trooper("B1 Marksman", faction);
			troopers[2] = new Trooper("B2", faction);
			troopers[3] = new Trooper("B2", faction);
			troopers[4] = new Trooper("B1 Autorifleman", faction);
			troopers[5] = new Trooper("B1 Assistant Autorifleman", faction);
			troopers[6] = new Trooper("B1 AT Specialist", faction);
			troopers[7] = new Trooper("B1 Assistant AT Specialist", faction);

			setSquad(troopers);
		}
	}

	public void setSquad(Trooper[] individuals) {
		if (individuals == null) {
			return;
		}
		for (int i = 0; i < individuals.length; i++) {
			this.individuals.add(individuals[i]);
		}
	}

	public ArrayList<Trooper> getSquad() {
		return individuals;
	}
}