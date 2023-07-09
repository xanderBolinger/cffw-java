package Melee;

public class MeleeHitLocationPcResults {

	public String hitLocationName;
	public boolean disabled;
	public int physicalDamage;

	public MeleeHitLocationPcResults(String hitLocationName, int physicalDamage, boolean disabled) {
		this.hitLocationName = hitLocationName;
		this.physicalDamage = physicalDamage;
		this.disabled = disabled;
	}

}
