package Command;

import Company.Formation.LeaderType;
import Conflict.GameWindow;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;
import UtilityClasses.PCUtility;

public class UnitCommand {

	public static void CommandRoll(Unit unit) {
		if(unit.individuals.size() <= 0)
			return;
		
		int roll = DiceRoller.roll(1, 100);
		int preModifiers = roll;
		/*
			1-10: 2 bonus dice
			11-15: 3 bonus dice 
			16-20: 4 bonus dice 
			21-30: 5 bonus dice 
			31-40: 6 bonus dice 
		*/ 
		
		int ftlCommandSum = 0; 
		
		Trooper leader = unit.getLeader();
		
		for(Trooper trooper : unit.individuals) {
			
			if(leader.subordinates.contains(trooper.identifier) && trooper.leaderType != LeaderType.NONE) {
				ftlCommandSum += trooper.getSkill("Command") / 10;
			}
			
		}
		
		if(ftlCommandSum <= 10) {
			for(int i = 0; i < 2; i++)
				roll -= DiceRoller.d6_exploding();
		} else if(ftlCommandSum <= 15) {
			for(int i = 0; i < 3; i++)
				roll -= DiceRoller.d6_exploding();
		} else if(ftlCommandSum <= 20) {
			for(int i = 0; i < 4; i++)
				roll -= DiceRoller.d6_exploding();
		} else if(ftlCommandSum <= 30) {
			for(int i = 0; i < 5; i++)
				roll -= DiceRoller.d6_exploding();
		} else if(ftlCommandSum <= 40) {
			for(int i = 0; i < 6; i++)
				roll -= DiceRoller.d6_exploding();
		}
		
		roll -= ftlCommandSum - (int)((double) unit.suppression * PCUtility.suppressionPenalty(leader));
		
		int margin = 0; 
		
		if(roll <= leader.getSkill("Command")) {
			margin = leader.getSkill("Command") - roll + 1;
			unit.suppression -= 10; 
			unit.organization += 10;
		}
		
		unit.suppression -= margin / 2;
		unit.organization += margin / 2;
		
		if(unit.suppression < 0)
			unit.suppression = 0; 
		if(unit.organization > 100)
			unit.organization = 100;
		
		if(margin == 0) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Command Skill Roll FAILED: "+unit.callsign+" "+leader.number+" "+leader.name+" Command Skill: "+(leader.getSkill("Command"))
					+", Original Roll: "+preModifiers+", Modified Roll: "+roll+", FTL Sum: "+ftlCommandSum);
		} else {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Command Skill Roll SUCCESS: "+unit.callsign+" "+leader.number+" "+leader.name+" Command Skill: "+(leader.getSkill("Command"))
					+", Original Roll: "+preModifiers+", Modified Roll: "+roll+", FTL Sum: "+ftlCommandSum);
		}
		
		
	}
	
}
