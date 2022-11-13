package CorditeExpansionDamage;

public class Injury {

	public String weaponName;
	public String hitLocation; 
	public int pd; 
	public boolean disabling; 
	
	public Injury(String weaponName, String hitLocation, int pd, boolean disabling) {
		this.weaponName = weaponName;
		this.hitLocation = hitLocation;
		this.pd = pd; 
		this.disabling = disabling;
	}
	
	@Override
	public String toString() {
		return hitLocation + ", "+ weaponName +", pd: " + pd + ", disabling: " + disabling;
	}
	
}
