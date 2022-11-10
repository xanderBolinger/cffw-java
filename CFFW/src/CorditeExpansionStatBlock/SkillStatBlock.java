package CorditeExpansionStatBlock;

import UtilityClasses.DiceRoller;

public class SkillStatBlock {

	public int success = 0; 
	public int failure = 0;
	
	public boolean spendSuccess = false;
	
	public static class SkillCheck {
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
		
		public boolean performSkillCheck(boolean spendSuccess) {
			
			margin = getMargin(tn, spendSuccess);
			System.out.println("Margin: "+margin);
			
			if(margin > 0 && spendSuccess) {
				statBlock.success += getSuccess();
				if(statBlock.success > 5)
					statBlock.success = 5; 
				return true;
			}
			else if (margin < 0 && spendSuccess){
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
		
		public int getMargin(int tn, boolean spendSuccess) {
			
			roll = DiceRoller.randInt(0, 99);
			System.out.println("Roll: "+roll+", tn: "+tn);
			
			for(int i = 0; i < bonus; i++)
				roll -= DiceRoller.d6_exploding();
			
			for(int i = 0; i < diff; i++)
				roll += DiceRoller.d6_exploding();
			
			if(spendSuccess) {
				roll -= statBlock.success * 10;
				statBlock.success = 0; 
			}
			
			roll += statBlock.failure * 10;
			statBlock.failure = 0;
			
			return tn - roll;
		}
		
	}

}

