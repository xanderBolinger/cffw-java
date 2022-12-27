package CorditeExpansionDamage;

public class BloodLossLocation {

	public int blpd; 
	public String location; 
	
	public BloodLossLocation(int blpd, String location) {
		this.blpd = blpd;
		this.location = location;
	}
	
	@Override
	public String toString() {
		return location + ", BLPD: " + blpd;
	}
}
