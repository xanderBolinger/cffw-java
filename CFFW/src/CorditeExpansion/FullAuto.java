package CorditeExpansion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CorditeExpansionActions.FireAction;
import CorditeExpansionStatBlock.StatBlock;
import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;
import UtilityClasses.PCUtility;

public class FullAuto {

	public static class FullAutoResults {
		public String rslts; 
		public int hits; 
		
		public FullAutoResults(String rslts, int hits) {
			this.rslts = rslts;
			this.hits = hits;
		}
	}
	
	
	public static FullAutoResults burst(int eal, double ma, int rof, StatBlock statBlock) throws Exception {
		String rslts;
		int hits = 0; 
		int elevationTn = PCUtility.getOddsOfHitting(false, eal);
		int penalty = FireAction.shotPenalty(statBlock);
		
		int roll = DiceRoller.roll(0, 99);
				
		if(roll < elevationTn) {
			rslts = "Elevation Hit, Roll: "+roll+", TN: "+elevationTn+", Penalty: "+penalty;
			
			String autofireTable = ExcelUtility.getStringFromSheet(rof, ma, 
					"\\PC Hit Calc Xlsxs\\automaticfire.xlsx", true, true);
			
			if(hits(autofireTable)) {
				hits = getNumericResults(autofireTable);
				rslts += ", Burst Hits: "+hits;
			} else {
				int tn = getNumericResults(autofireTable);
				int secondRoll = DiceRoller.roll(0, 99);
				
				if(secondRoll <= tn) {
					rslts += ", Burst Hit: roll: "+secondRoll+", tn: "+tn;
					hits++;					
				} else {
					rslts += ", Burst Miss: roll: "+secondRoll+", tn: "+tn;
				}
			}
			
		} else {
			rslts = "Burst Miss, Roll: "+roll+", TN: "+elevationTn+", Penalty: "+penalty;
			return new FullAutoResults(rslts, 0);
		}
		
		return new FullAutoResults(rslts, hits);
	}
	
	public static boolean hits(String autofireTable) {
		Pattern pattern = Pattern.compile("[*]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(autofireTable);
		boolean matchFound = matcher.find();

		if (matchFound) {
			return true;
		} else {
			return false;
		}
	}	
	public static int getNumericResults(String autofireTable) throws Exception {
		Pattern pattern = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(autofireTable);
		boolean matchFound = matcher.find();
		if (matchFound) {
			return Integer.parseInt(matcher.group());
		}

		throw new Exception("autofireTable: " + autofireTable + ", has no numeric value.");
	}
	
}
