package Melee;

import java.util.ArrayList;
import java.util.List;

import Melee.MeleeArmorData.MeleeArmorType;

public class MeleeArmor {

	public static ArrayList<MeleeArmor> meleeArmorPieces;

	public MeleeArmorType armorType;
	public List<String> protectedBodyParts;

	public int armorValue;
	public int bluntMod;
	public int cutMod;
	public int stabMod;

	public boolean imperviousToChinks;

	public MeleeArmor(MeleeArmorType armorType, List<String> protectedBodyParts, int armorValue, int bluntMod, int cutMod,
			int stabMod, boolean imperviousToChinks) {
		this.armorType = armorType;
		this.protectedBodyParts = protectedBodyParts;
		this.armorValue = armorValue;
		this.bluntMod = bluntMod;
		this.cutMod = cutMod;
		this.stabMod = stabMod;
		this.imperviousToChinks = imperviousToChinks;
	}
	
	public boolean protectedZone(String zone) {
		return protectedBodyParts.contains(zone);
	}
	
	public static MeleeArmor getArmorPiece(MeleeArmorType armorType) throws Exception {
		
		for(var piece : meleeArmorPieces) {
			if(piece.armorType == armorType)
				return piece;
		}
		
		throw new Exception("Melee Armor piece not found for armorType: "+armorType);
	}

}
