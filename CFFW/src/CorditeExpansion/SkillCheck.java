package CorditeExpansion;

import CorditeExpansionStatBlock.SkillStatBlock;
import UtilityClasses.DiceRoller;

public class SkillCheck {
	public int roll;
	public int margin; 
	public int bonus;
	public int diff; 
	
	public int tn; 
	
	SkillStatBlock statBlock;
	
	public SkillCheck(SkillStatBlock statBlock, int tn, int bonus, int diff) {
		this.statBlock = statBlock;
		this.tn = tn; 
		this.bonus = bonus; 
		this.diff = diff;
	}
	
	public static class Flags {
		public boolean spendSuccess; 
		public boolean gainSuccess; 
		public boolean spendFailure; 
		public boolean gainFailure; 
		
		private Flags() {
			
		}
		
		public static Flags coolnessTest(boolean spendSuccess) {
			Flags flags = new Flags();
			flags.spendSuccess = spendSuccess;
			flags.gainSuccess = true; 
			flags.spendFailure = true; 
			flags.gainFailure = true;
			
			return flags;
		}

		public static Flags skillCheck() {
			Flags flags = new Flags();
			flags.spendSuccess = true;
			flags.gainSuccess = true; 
			flags.spendFailure = true; 
			flags.gainFailure = true;
			
			return flags;
		}
		
	} 
	
	public boolean performSkillCheck(Flags flags) {
		
		margin = getMargin(tn, flags.spendSuccess, flags.spendFailure);
		//System.out.println("Margin: "+margin);
		
		if(margin > 0 && flags.gainSuccess) {
			statBlock.success += getSuccess();
			if(statBlock.success > 5)
				statBlock.success = 5; 
			return true;
		}
		else if (margin < 0 && flags.gainFailure){
			statBlock.failure += getFailure();
			if(statBlock.failure > 5)
				statBlock.failure = 5; 
			return false;
		}
		
		if(margin > 0)
			return true;
		else
			return false;
		
		
	}
	
	public int getSuccess() {
		return margin / 10 + 1;
	}
	
	public int getFailure() {
		return margin * -1 / 10 + 1;
	}
	
	public int getMargin(int tn, boolean spendSuccess, boolean spendFailure) {
		
		roll = DiceRoller.roll(0, 99);
		
		
		for(int i = 0; i < bonus; i++)
			roll -= DiceRoller.d6_exploding();
		
		for(int i = 0; i < diff; i++)
			roll += DiceRoller.d6_exploding();
		
		if(spendSuccess) {
			int success = statBlock.success < statBlock.spendSuccessNumber ? statBlock.success : statBlock.spendSuccessNumber;
			roll -= success * 10;
			statBlock.success = 0; 
		}
		
		if(spendFailure) {
			roll += statBlock.failure * 10;
			statBlock.failure = 0;
		}
		
		//System.out.println("Roll: "+roll+", tn: "+tn);
		return tn - roll;
	}
	
}
