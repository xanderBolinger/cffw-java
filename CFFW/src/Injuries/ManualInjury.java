package Injuries;

import java.io.Serializable;

public class ManualInjury implements Serializable {
	public int physicalDamage;
	public String description; 
	
	// Creates injury 
	// Sets parameters defined in manual injury window 
	public ManualInjury(int PD, String desc) {
		this.physicalDamage = PD; 
		this.description = desc; 

	}
	
	@Override 
	public String toString() {
		return this.description+" PD: "+this.physicalDamage;
	}

}
