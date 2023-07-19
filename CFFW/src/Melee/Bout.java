package Melee;

import java.io.Serializable;

public class Bout implements Serializable {

	public Combatant combatantA;
	public Combatant combatantB;
	
	public Bout(Combatant a, Combatant b) {
		combatantA = a; 
		combatantB = b;
	}
	
}
