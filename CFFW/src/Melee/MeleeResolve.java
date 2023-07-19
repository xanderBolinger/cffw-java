package Melee;

import java.io.Serializable;
import java.util.ArrayList;

import Trooper.Trooper;
import Unit.Unit;

public class MeleeResolve implements Serializable {

	public MeleeCombatUnit meleeUnit;
	public int baseMeleeResolve;

	// Increases
	public int numbersAdvantageModifier;
	public int formationModifier;
	public int meleeSuccessModifier;

	// Decreases
	public int chargeModifier;
	public int combatLossesModifier;
	public int suppressionModifier;
	public int nearbyRoutingModifier;
	public int indiviualdsFleeingModifier;
	public int fatigueModifier;

	// public int

	public MeleeResolve() {} // empty constructor for testing
	
	public MeleeResolve(MeleeCombatUnit meleeUnit) {
		meleeUnit.unit.getCommandValue();
		this.meleeUnit = meleeUnit;
	}

	public void enterCombat() {
		
	}

	public void calculateResolve(ArrayList<Trooper> blufor, ArrayList<Trooper> opfor) {
		calculateNumbersAdvantageModifier(blufor, opfor);
	}

	public void calculateNumbersAdvantageModifier(ArrayList<Trooper> blufor, ArrayList<Trooper> opfor) {
		
		int modifier = 0;
		
		int bluforSkill = 0;

		for (var blu : blufor)
			bluforSkill += blu.meleeCombatSkillLevel;

		int opforSkill = 0;

		for (var op : opfor)
			opforSkill += op.meleeCombatSkillLevel;

		if (bluforSkill >= opforSkill * 3) {
			modifier += 30;
		} else if (bluforSkill >= opforSkill * 2) {
			modifier += 20;
		} else if ( (double)bluforSkill >= (double)opforSkill * 1.5) {
			modifier += 10;
		} else if(opforSkill >= bluforSkill * 3){
			modifier -= 30;
		} else if (opforSkill >= bluforSkill * 2) {
			modifier -= 20;
		} else if ( (double)opforSkill >= (double)bluforSkill * 1.5) {
			modifier -= 10;
		}  
		
		this.numbersAdvantageModifier = modifier;
	}

	public void calculateFormationModifier() {
		if(!meleeUnit.inFormation) {
			formationModifier = 0;
			return;
		}
		formationModifier = 5 * meleeUnit.formationRanks;
	}
	
	public void calculateMeleeSuccessModifier(double percentCasualtiesInflicted) {
		
		if(percentCasualtiesInflicted >= 60) {
			meleeSuccessModifier = 40;
		} else if(percentCasualtiesInflicted >= 30) {
			meleeSuccessModifier = 30;
		} else if(percentCasualtiesInflicted >= 10) {
			meleeSuccessModifier = 15;
		} else if(percentCasualtiesInflicted >= 5) {
			meleeSuccessModifier = 10;
		} else if(percentCasualtiesInflicted >= 2.5) {
			meleeSuccessModifier = 8;
		} else if(percentCasualtiesInflicted >= 1.5) {
			meleeSuccessModifier = 6;
		} else if(percentCasualtiesInflicted >= 0.5) {
			meleeSuccessModifier = 3;
		} else {
			meleeSuccessModifier = 0;
		}
		
	}

	public void calcaulteChargeModifier(ArrayList<ChargeData> chargeVelocities) {
		chargeModifier = 0;
		for(var c : chargeVelocities)
			applyChargeModifier(c.velocity, c.flankCharge, c.rearCharge);
	}
	
	private void applyChargeModifier(double chargeVelocity, boolean flankCharge, boolean rearCharge) {
		if(chargeVelocity >= 3) {
			chargeModifier += 25 * (flankCharge ? 2 : 1) * (rearCharge ? 3 : 1);
		} else if(chargeVelocity >= 2.5) {
			chargeModifier += 15 * (flankCharge ? 2 : 1) * (rearCharge ? 3 : 1);
		} else if(chargeVelocity >= 2) {
			chargeModifier += 10 * (flankCharge ? 2 : 1) * (rearCharge ? 3 : 1);
		} else if(chargeVelocity >= 1.5) {
			chargeModifier += 8 * (flankCharge ? 2 : 1) * (rearCharge ? 3 : 1);
		} else if(chargeVelocity >= 1) {
			chargeModifier += 5 * (flankCharge ? 2 : 1) * (rearCharge ? 3 : 1);
		} else if(chargeVelocity >= 0.5) {
			chargeModifier += 3 * (flankCharge ? 2 : 1) * (rearCharge ? 3 : 1);
		}  else {
			chargeModifier += 0;
		}
	}

	
	
	
	
}


