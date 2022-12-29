package Items;

import java.util.ArrayList;

import Items.Armor.ArmorType;

public class NewArmor {

	
	public static void duraSteelVest(Armor armor) {
		armor.bPF = 25; 
		armor.armorName = "Durasteel Vest";
		armor.type = ArmorType.DURASTEELVEST;
		armor.armorWeight = 15; 
		
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(0);
		a1.add(21);
		a1.add(43);
		a1.add(100);
		armor.excludedZones.add(a1);
		
		ArrayList<Integer> a0 = new ArrayList<Integer>();
		a0.add(1);
		a0.add(100);
		armor.excludedZonesOpen.add(a0);
	} 
	
}
