package Melee.Gear;

import java.util.ArrayList;
import java.util.List;

import Melee.Gear.MeleeArmorData.MeleeArmorType;

public class MeleeArmor {

	public static ArrayList<MeleeArmor> meleeArmorPieces;

	public MeleeArmorType armorType;
	public List<String> protectedBodyParts;

	public int armorValue;
	public int bluntMod;
	public int cutMod;
	public int stabMod;
	public int encumberencePenalty;
	
	public boolean imperviousToChinks;

	public MeleeArmor(MeleeArmorType armorType, List<String> protectedBodyParts, int armorValue, int bluntMod, int cutMod,
			int stabMod, boolean imperviousToChinks, int encumberencePenalty) {
		this.armorType = armorType;
		this.protectedBodyParts = protectedBodyParts;
		this.armorValue = armorValue;
		this.bluntMod = bluntMod;
		this.cutMod = cutMod;
		this.stabMod = stabMod;
		this.imperviousToChinks = imperviousToChinks;
		this.encumberencePenalty = encumberencePenalty;
	}
	
	public boolean protectedZone(String zone) {
		return protectedBodyParts.contains(zone);
	}
	
	public static MeleeArmor getArmorPiece(MeleeArmorType armorType) throws Exception {
		if(meleeArmorPieces == null)
			new MeleeArmorData();
		
		for(var piece : meleeArmorPieces) {
			if(piece.armorType == armorType)
				return piece;
		}
		
		throw new Exception("Melee Armor piece not found for armorType: "+armorType);
	}

}
