package Melee;

import java.util.ArrayList;
import java.util.List;

public class MeleeArmorData {

	public enum MeleeArmorType {
		CeramiteTacticalMarine,DurasteelCombatArmor,PlasteelCombatArmor,GothicPlateArmor,HopliteBronzeArmor,Gambeson,KettleHelm,Curiass
	}
	
	public MeleeArmorData() {
		new MeleeArmorLocationData();
		MeleeArmor.meleeArmorPieces = new ArrayList<MeleeArmor>();
		
		for(var armorType : MeleeArmorType.values()) {
			try {
				createArmor(armorType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void createArmor(MeleeArmorType meleeArmorType) throws Exception {
		
		switch(meleeArmorType) {
		case CeramiteTacticalMarine:
			MeleeArmor.meleeArmorPieces.add(new MeleeArmor(meleeArmorType, MeleeArmorLocationData.allBodyParts, 60, 0, 20, 20, true));
			break;
		case DurasteelCombatArmor:
			MeleeArmor.meleeArmorPieces.add(new MeleeArmor(meleeArmorType, MeleeArmorLocationData.allBodyParts, 40, 0, 15, 15, false));
			break;
		case PlasteelCombatArmor:
			MeleeArmor.meleeArmorPieces.add(new MeleeArmor(meleeArmorType, MeleeArmorLocationData.allBodyParts, 30, 0, 10, 10, false));
			break;
		case GothicPlateArmor:
			MeleeArmor.meleeArmorPieces.add(new MeleeArmor(meleeArmorType, MeleeArmorLocationData.allBodyParts, 16, 0, 5, 5, false));
			break;
		case HopliteBronzeArmor:
			List<String> protectedHopliteZones = new ArrayList<String>();
			protectedHopliteZones.addAll(MeleeArmorLocationData.headUpper);
			protectedHopliteZones.addAll(MeleeArmorLocationData.headLower);
			protectedHopliteZones.addAll(MeleeArmorLocationData.torsoLower);
			protectedHopliteZones.addAll(MeleeArmorLocationData.torsoUpper);
			MeleeArmor.meleeArmorPieces.add(new MeleeArmor(meleeArmorType, protectedHopliteZones, 13, 0, 3, 3, false));
			break;
		case Gambeson:
			List<String> gamebesonProtectedZones = new ArrayList<String>();
			gamebesonProtectedZones.addAll(MeleeArmorLocationData.torsoLower);
			gamebesonProtectedZones.addAll(MeleeArmorLocationData.torsoUpper);
			gamebesonProtectedZones.addAll(MeleeArmorLocationData.armsUpper);
			gamebesonProtectedZones.addAll(MeleeArmorLocationData.armsLower);
			MeleeArmor.meleeArmorPieces.add(new MeleeArmor(meleeArmorType, gamebesonProtectedZones, 4, 2, 0, 0, true));
			break;
		case KettleHelm:
			MeleeArmor.meleeArmorPieces.add(new MeleeArmor(meleeArmorType, MeleeArmorLocationData.headUpper, 16, 0, 5, 5, true));
			break;
		case Curiass:
			List<String> curiassProtectedZones = new ArrayList<String>();
			curiassProtectedZones.addAll(MeleeArmorLocationData.torsoLower);
			curiassProtectedZones.addAll(MeleeArmorLocationData.torsoUpper);
			MeleeArmor.meleeArmorPieces.add(new MeleeArmor(meleeArmorType, curiassProtectedZones, 16, 0, 5, 5, true));
			break;
		default:
			throw new Exception("Armor type for: "+meleeArmorType+", not found.");
		}
		
	}
	
}
