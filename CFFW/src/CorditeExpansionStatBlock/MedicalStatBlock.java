package CorditeExpansionStatBlock;

import java.util.ArrayList;

import CorditeExpansionDamage.BloodLossLocation;
import CorditeExpansionDamage.Damage;
import CorditeExpansionDamage.Injury;
import UtilityClasses.DiceRoller;
import UtilityClasses.TrooperUtility;

public class MedicalStatBlock {
	
	public int pain = 0;
	public Status status = Status.NORMAL;
	public int spentCa = 0;
	public int statusDuration = 0;
	public int incapTime = 0; 
	public int timeSpentIncapacitated = 0;
	
	private boolean conscious = true;
	public boolean alive = true;
	
	private int physicalDamage = 0;

	private ArrayList<BloodLossLocation> bloodLossLocations = new ArrayList<>();
	
	private ArrayList<Injury> injuries = new ArrayList<>();
	
	public int recoveryChance = 0; 
	public int criticalTime = 0;
	public int timeSpentInjured = 0;
	public boolean stabalized = false;
	
	public enum Status {
		NORMAL,DAZED,DISORIENTED,STUNNED
	}
	
	public void deathCheck() {
		if(getPdTotal() <= 0 || stabalized)
			return;
		
		timeSpentInjured++; 
		System.out.println("Time Spent Injured: "+timeSpentInjured);
		System.out.println("Criticl Time: "+criticalTime);
		if(timeSpentInjured < criticalTime)
			return;
		
		int roll = DiceRoller.roll(0, 99);
		//System.out.println("Roll: "+roll+", recovery: "+recoveryChance);
		if(roll <= recoveryChance) {
			
			stabalized = true; 
		} else {
			System.out.println("Death Check Alive = false");
			//alive = false;
		}
	}
	
	public void bleedPerPhase() {
		for(BloodLossLocation lc : bloodLossLocations) {
			lc.blpd += lc.blpd / 100;
		}
		stabalized = false;
	}
	
	public void addBleed(BloodLossLocation location) {
		bloodLossLocations.add(location);
	}
	
	public int getBloodLossPd() {
		int bloodLossPd = 0; 
		
		for(BloodLossLocation location : bloodLossLocations) {
			bloodLossPd += location.blpd;
		}
		
		return bloodLossPd;
	}
	
	public int getPdTotal() {
		return physicalDamage + getBloodLossPd();
	}
	
	public void increasePd(int pd) {
		physicalDamage += pd;
		stabalized = false;
	}
	
	public boolean conscious() {
		return conscious;
	}
	
	public void unconsciousCheck(int pd, StatBlock statBlock) {
		timeSpentIncapacitated++;
		
		if(timeSpentIncapacitated >= incapTime) {
			setConscious(pd, statBlock);
		}
	}
	
	public void setConscious(int pd, StatBlock statBlock) {
		conscious = true;
		incapTime = 0; 
		timeSpentIncapacitated = 0;
		setDisoriented(pd, statBlock);
	}
	
	public void setUnconscious(int pd) {
		conscious = false;
		setNormal();
		
		try {
			incapTime = Damage.incapacitationImpulses(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void setStunned(int pd, StatBlock statBlock) {
		
		status = Status.STUNNED;
		spentCa = 0;
		statusDuration = pd / 25;
		statBlock.combatActions = TrooperUtility.calculateCA(statBlock.trooper.maximumSpeed.get(), 
				statBlock.trooper.isf);
		statBlock.combatActions -= 2; 
		spentCheck(statBlock);
	}
	
	public void setDazed(int pd, StatBlock statBlock) {
		
		if(status == Status.STUNNED) 
			return;
		
		status = Status.DAZED;
		spentCa = 0;
		statusDuration = pd / 25;
		statBlock.combatActions = TrooperUtility.calculateCA(statBlock.trooper.maximumSpeed.get(), 
				statBlock.trooper.isf);
		statBlock.combatActions -= 2; 
		spentCheck(statBlock);
	}
	
	public void setDisoriented(int pd, StatBlock statBlock) {
		
		if(status == Status.STUNNED || status == Status.DAZED) 
			return;
		
		status = Status.DISORIENTED;
		spentCa = 0;
		statusDuration = pd / 25;
		statBlock.combatActions = TrooperUtility.calculateCA(statBlock.trooper.maximumSpeed.get(), 
				statBlock.trooper.isf);
		statBlock.combatActions -= 1; 
		spentCheck(statBlock);
	}
	
	public void setNormal() {
		statusDuration = 0; 
		spentCa = 0;
		status = Status.NORMAL;
	}
	
	public void recoverCheck(StatBlock statBlock) {
		
		if(spentCa >= statusDuration) {
			setNormal();
		}
		
		statBlock.combatActions = TrooperUtility.calculateCA(statBlock.trooper.maximumSpeed.get(), 
				statBlock.trooper.isf);
		
	}
	
	public void spentCheck(StatBlock statBlock) {
		if(statBlock.spentCombatActions > statBlock.combatActions)
			statBlock.spentCombatActions  = statBlock.combatActions;
	}
	
	public int getDifficultyDice() {
		
		int statusDice = 0; 
		
		switch(status) {
		
			case DAZED:
				statusDice = 2;
				break;
			case DISORIENTED: 
				statusDice = 1; 
				break; 
			case STUNNED:
				statusDice = 3; 
				break; 
			default: 
				statusDice = 0;
				break;
		
		}
		
		return pain + statusDice; 
		
		
	}
	
	public void addInjury(Injury injury) {
		injuries.add(injury);
	}
	
	public void removeInjury(int index) {
		injuries.remove(index);
	}
	
	public ArrayList<String> getInjuries() {
		ArrayList<String> injuries = new ArrayList<String>();
		
		for(Injury injury : this.injuries) {
			injuries.add(injury.toString());
		}
		
		return injuries;
	}
	
	public ArrayList<String> getblLocations() {
		ArrayList<String> locations = new ArrayList<String>();
		
		for(BloodLossLocation location: this.bloodLossLocations) {
			locations.add(location.toString());
		}
		
		return locations;
	}
}
