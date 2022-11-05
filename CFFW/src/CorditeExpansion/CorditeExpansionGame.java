package CorditeExpansion;

import CorditeExpansionDamage.Damage;
import CorditeExpansionStatBlock.MedicalStatBlock;
import CorditeExpansionStatBlock.MedicalStatBlock.Status;
import Trooper.Trooper;

public class CorditeExpansionGame {

	public static ActionOrder actionOrder; 
	public static Trooper selectedTrooper;
	
	public static Impulse impulse = Impulse.FIRST;
	public static int round = 1; 
	
	public enum Impulse {
		FIRST(1), SECOND(2), THIRD(3), FOURTH(4);

		private int value;
		
		Impulse(int i) {
			value = i; 
		}
		
		public int getValue() { return value; }
	}
	
	public static void action() {
		//System.out.println("Action");
		//System.out.println("Action Order Size: "+actionOrder.getOrder().size());
		for(Trooper trooper : actionOrder.getOrder()) {
			
			
			int actions =  trooper.ceStatBlock.getActionTiming();
			
			for(int i = 0; i < actions; i++) {
				
				
				if(trooper.ceStatBlock.acting()) {
					//System.out.println("Act");
					trooper.ceStatBlock.spendCombatAction();		
				}
				else if(trooper.ceStatBlock.preparing()) {
					//System.out.println("Prepare");
					trooper.ceStatBlock.prepareCourseOfAction();
				}
				else {
					//System.out.println("Nothing");
					trooper.ceStatBlock.doNothing();
				}
			}
			
		}
		
		nextImpulse();
		
	}
	
	public static void nextImpulse() {
		
		if(impulse == Impulse.FIRST) {
			impulse = Impulse.SECOND;
		} else if(impulse == Impulse.SECOND) {
			impulse = Impulse.THIRD;
			bleedOutCheck();
		} else if(impulse == Impulse.THIRD) {
			impulse = Impulse.FOURTH;
		} else if(impulse == Impulse.FOURTH) {
			impulse = Impulse.FIRST;
			completeRound();
			bleedOutCheck();
		}
		
		incapacitationCheck();
	}
	
	public static void completeRound() {
		round++; 
		
		for(Trooper trooper : actionOrder.getOrder()) {
			trooper.ceStatBlock.totalMoved = 0;
		}
	}
	
	public static void bleedOutCheck() {
		for(Trooper trooper : actionOrder.getOrder()) {
			if(trooper.ceStatBlock.medicalStatBlock.getBloodLossPd() > 100) {
				Damage.statusCheck(trooper.ceStatBlock.medicalStatBlock.getBloodLossPd(), trooper);
			}
			trooper.ceStatBlock.medicalStatBlock.bleedPerPhase();
		}
	}
	
	public static void incapacitationCheck() {
		for(Trooper trooper : actionOrder.getOrder()) {
			MedicalStatBlock medicalStatBlock = trooper.ceStatBlock.medicalStatBlock;
			if(medicalStatBlock.status != Status.NORMAL) {
				medicalStatBlock.spentCa++;
				medicalStatBlock.recoverCheck(trooper.ceStatBlock);
			}
			
			if(!medicalStatBlock.conscious()) {
				medicalStatBlock.unconsciousCheck(medicalStatBlock.getPdTotal(), trooper.ceStatBlock);
			}
		}
		
		
	}
}
