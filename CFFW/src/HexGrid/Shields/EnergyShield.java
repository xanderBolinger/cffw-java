package HexGrid.Shields;

import java.io.Serializable;

public class EnergyShield implements Serializable {

	
	// TODO: Type determines size and strength, pass enum from combobox to constructor 
	
	public enum ShieldType {
		SquadShield
	}
	
	public int strength;
	
	
	public EnergyShield(int strength) {
		this.strength = strength;
	}

	
}
