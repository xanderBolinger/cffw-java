package Melee.Gear;

import java.io.Serializable;
import java.util.List;

import Melee.Gear.MeleeShieldData.MeleeShieldType;

public class MeleeShield implements Serializable {

	public static List<MeleeShield> meleeShields;

	public MeleeShieldType meleeShieldType;

	public int dtn;

	public int encumberancePenalty;

	public MeleeShield(MeleeShieldType meleeShieldType, int dtn, int encumberancePenalty) {
		this.meleeShieldType = meleeShieldType;
		this.dtn = dtn;
		this.encumberancePenalty = encumberancePenalty;
	}

	public static MeleeShield getMeleeShield(MeleeShieldType meleeShieldType) throws Exception {

		for (var shield : meleeShields) {
			if (shield.meleeShieldType == meleeShieldType)
				return shield;
		}

		throw new Exception("Melee Shield not found for meleeShieldType: " + meleeShieldType);
	}

}
