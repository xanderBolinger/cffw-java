package Melee.Damage;

import java.io.Serializable;

public class MeleeHitLocationData implements Serializable {
	public String zoneName;
    public int bloodLossPD;
    public int shockPD;
    public int painPoints;
    public boolean knockDown;
    public int knockDownMod;

    public MeleeHitLocationData(String zoneName, int bloodLossPD, int shockPD, int painPoints, boolean knockDown, int knockDownMod)
    {
        this.zoneName = zoneName;
        this.bloodLossPD = bloodLossPD;
        this.shockPD = shockPD;
        this.painPoints = painPoints;
        this.knockDown = knockDown;
        this.knockDownMod = knockDownMod;
    }
}
