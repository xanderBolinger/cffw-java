package CorditeExpansionRangedCombat;

import java.util.function.Function;

import CeHexGrid.FloatingTextManager;
import CorditeExpansion.SkillCheck;
import CorditeExpansion.SkillCheck.Flags;
import CorditeExpansionStatBlock.StatBlock;
import UtilityClasses.DiceRoller;

public class Suppression {

	public StatBlock statBlock;

	private int suppression = 0;
	private SuppressionStatus suppresionStatus = SuppressionStatus.NONE;
	
	public enum SuppressionStatus {
		NONE,COOLNESS_TEST,SPEND,HESITATE
	}
	
	public Suppression(StatBlock statBlock) {
		this.statBlock = statBlock;
	}

	
	public void resolve() {
		
		switch(suppresionStatus) {
		
			case SPEND:
				spend();
				break;
			case COOLNESS_TEST:
				coolnessTest();
				break;
			case HESITATE: 
				hesitate();
				break; 
			default:
				break;
		} 
		
	}
	
	public void spend() {
		decreaseSuppression(2);
		
		String rslts = "Spend 2 suppression, ";
		
		if(suppression * 2 > (statBlock.rangedStatBlock.coolnessUnderFire/10) % 10) {
			statBlock.skillStatBlock.failure += 2;
			rslts += "add TWO failure";
		} else {
			statBlock.skillStatBlock.failure++; 		
			rslts += "add one failure";
		}
		
		FloatingTextManager.addFloatingText(statBlock.cord, rslts);
		
	}
	
	public void coolnessTest() {
		int diff = suppression / 5;
		
		SkillCheck coolnessTest = new SkillCheck(statBlock.skillStatBlock, statBlock.rangedStatBlock.coolnessUnderFire, 0, diff);
		
		boolean pass = coolnessTest.performSkillCheck(Flags.coolnessTest(statBlock.skillStatBlock.spendSuccess));
		String rslts = "Coolness Test, Roll: "+coolnessTest.roll+", TN: "+coolnessTest.tn+", Margin: "+coolnessTest.margin;
		
		if(pass) {
			int removed = 0; 
			
			for(int i = 0; i < statBlock.skillStatBlock.success; i++) {
				int roll = DiceRoller.roll(1, 3);
				removed += roll;
			}
			
			decreaseSuppression(removed);
			
			rslts += ", Removes Suppresion: "+removed; 
			
		} else {
			statBlock.hesitating = true;
			rslts += ", Hesitating.";
		}

		FloatingTextManager.addFloatingText(statBlock.cord, rslts);
		
		
	}
	
	public void hesitate() {
		statBlock.hesitating = true;
		int removed = (statBlock.rangedStatBlock.coolnessUnderFire/10) % 10;
		decreaseSuppression(removed);
		FloatingTextManager.addFloatingText(statBlock.cord, "Hesitate, removed: "+removed);
	}
	
	public int getSuppression() {
		return suppression;
	}

	public void increaseSuppression(int value) {
		suppression += value;
		
		if(suppresionStatus == SuppressionStatus.NONE)
			suppresionStatus = SuppressionStatus.SPEND;
		
	}

	public void decreaseSuppression(int value) {
		suppression -= value;
		if(suppression <= 0) {
			suppression = 0;
			suppresionStatus = SuppressionStatus.NONE;
		}
		
	}

	public void setSuppression(int value) {
		if(suppresionStatus == SuppressionStatus.NONE)
			suppresionStatus = SuppressionStatus.SPEND;
		
		suppression = value;
	}

	public String getDisplayStatus() {
		
		switch(suppresionStatus) {
			case COOLNESS_TEST:
				return "Coolness Underfire Skill Check (gamble for no mof)";
			case SPEND:
				return "Spend Suppression (remove 2 sup for mof)";
			case HESITATE:
				return "Hesitate (remove CUF tens digit suppression each ca)";
			default:
				return "None";
		}
		
	}
	
	public void cycleSuppressionStatus() {
		
		switch(suppresionStatus) {
			case SPEND:
				suppresionStatus = SuppressionStatus.HESITATE;
				break;
			case HESITATE:
				suppresionStatus = SuppressionStatus.COOLNESS_TEST;
				break; 
			case COOLNESS_TEST:
				suppresionStatus = SuppressionStatus.SPEND;
				break;
			default:
				break;
		}
		
	}

	
	// Below methods are used for testing purposes only
	
	public SuppressionStatus getStatus() {
		return suppresionStatus;
	}
	
}
