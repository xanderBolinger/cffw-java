package Melee;

import java.io.Serializable;

import Conflict.GameWindow;

public class Bout implements Serializable {

	public Combatant combatantA;
	public Combatant combatantB;
	
	public Bout(Combatant a, Combatant b) {
		combatantA = a; 
		combatantB = b;
	}
	
	private String getCombatantString(Combatant combatant) {
		
		return combatant.trooper.returnTrooperUnit(GameWindow.gameWindow).callsign+", "
				+combatant.trooper.number+" "+combatant.trooper.name
				+ ", Melee Skill: " + combatant.trooper.meleeCombatSkillLevel
				+", Resolve: " + combatant.trooper.meleeCombatResolve
				+(combatant.meleeWeapon != null ? ", Weapon: "+combatant.meleeWeapon.meleeWeaponType : "")+
				(combatant.meleeShield != null ? ", Shield: "+combatant.meleeShield.meleeShieldType : "") +
				(combatant.trooper.physicalDamage > 0 ? ", PD: "+combatant.trooper.physicalDamage : "");
	}
	
	@Override
	public String toString() {
		
		if(GameWindow.gameWindow != null) {
			return "Combatant A: "+ getCombatantString(combatantA) + ", " + 
					"Combatant B: "+getCombatantString(combatantB);
			
		}
		else 
			return "";
	}
	
}
