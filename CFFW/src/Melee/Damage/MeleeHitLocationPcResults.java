package Melee.Damage;

import java.io.Serializable;

public class MeleeHitLocationPcResults implements Serializable {

	public String hitLocationName;
	public boolean disabled;
	public int physicalDamage;

	public MeleeHitLocationPcResults(String hitLocationName, int physicalDamage, boolean disabled) {
		this.hitLocationName = hitLocationName;
		this.physicalDamage = physicalDamage;
		this.disabled = disabled;
	}

}
