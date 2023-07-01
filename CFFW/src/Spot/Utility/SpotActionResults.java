package Spot.Utility;

public class SpotActionResults {
	public int successRoll; 
	public int targetNumber; 
	public int success;
	public int spotActionSpottingChance; 
	public int spotActionDiceRoll; 
	
	public SpotActionResults(int successRoll, int targetNumber, int success, 
			int spotActionSpottingChance, int spotActionDiceRoll) {
		this.successRoll = successRoll;
		this.targetNumber = targetNumber;
		this.success = success;
		this.spotActionSpottingChance = spotActionSpottingChance;
		this.spotActionDiceRoll = spotActionDiceRoll;
	}
	
}
