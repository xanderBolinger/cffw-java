package Melee.Gear;

import java.io.Serializable;
import java.util.ArrayList;

public class MeleeShieldData implements Serializable {

	public enum MeleeShieldType {
		StormShield
	}

	public MeleeShieldData() {
		MeleeShield.meleeShields = new ArrayList<MeleeShield>();

		for (var shield : MeleeShieldType.values()) {
			try {
				createShield(shield);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void createShield(MeleeShieldType shieldType) throws Exception {

		switch (shieldType) {
		case StormShield:
			MeleeShield.meleeShields.add(new MeleeShield(shieldType, 5, 2));
			break;
		default:
			break;
		}

		throw new Exception("Shield not found for shield type: " + shieldType);
	}

}
