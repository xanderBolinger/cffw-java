package Items;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
import Melee.Gear.MeleeArmor;
import Melee.Gear.MeleeArmorData;
import Melee.Gear.MeleeArmorLocationData;
import Melee.Gear.MeleeArmorData.MeleeArmorType;
import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;
import UtilityClasses.Location;
import UtilityClasses.PCUtility;

public class Armor implements Serializable {

	public int bPF; 
	public int camo;
	public ArrayList<ArrayList<Integer>> excludedZones;
	public ArrayList<ArrayList<Integer>> excludedZonesOpen;
	public ArrayList<ArrayList<Integer>> differingZoneValues = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> differingZoneValuesOpen = new ArrayList<ArrayList<Integer>>();
	public ArrayList<Integer> differingZonePF = new ArrayList<Integer>();
	public ArrayList<Integer> differingZonePFOpen = new ArrayList<Integer>();
	public ArrayList<Boolean> differingHardnessZone = new ArrayList<>();
	public ArrayList<Boolean> differingHardnessZoneOpen = new ArrayList<>();
	
	public String armorName; 
	public ArmorType type; 
	public boolean shield = false; 
	public int shieldMax; 
	public int currentShieldValue; 
	public int actionsToRefresh; 
	public int refreshProgress;
	public boolean hardBodyArmor = true;
	public int armorWeight; 
	
	public int visibilityModifier;
	public boolean nightVision;
	public int nightVisionEffectiveness;
	
	public boolean vaccumSealed;
	
	public ArrayList<MeleeArmor> meleeArmorStats;
	
	public Armor() {
		this.bPF = 0; 
		this.excludedZones = new ArrayList<ArrayList<Integer>>();
		this.excludedZonesOpen = new ArrayList<ArrayList<Integer>>();
		meleeArmorStats = new ArrayList<MeleeArmor>();
	}
	
	public enum ArmorType {
		NONE,PHASEONE,PHASEONEARC,KATARN,B1,B2,COMMANDODROID,MAGMAGUARD,DURASTEELMEDIUMMANDO,DURASTEELHELEMT,DURASTEELVEST, UNSCMARINE,ODST,GRUNT,ELITE,JACKAL
		,MKVIII_Errant
	}
	
	public Armor(ArmorType type) {
		this.bPF = 0; 
		this.excludedZones = new ArrayList<ArrayList<Integer>>();
		this.excludedZonesOpen = new ArrayList<ArrayList<Integer>>();
		meleeArmorStats = new ArrayList<MeleeArmor>();
		
		if(MeleeArmor.meleeArmorPieces == null)
			new MeleeArmorData();
		
		if(type == ArmorType.PHASEONE) {
			Phase1CloneArmor();
		} else if(type == ArmorType.PHASEONEARC) {
			Phase1ARC();
		} else if(type == ArmorType.KATARN) {
			katarnArmor();
		} else if(type == ArmorType.B1) {
			b1Armor();
		} else if(type == ArmorType.B2) {
			b2Armor();
		} else if(type == ArmorType.COMMANDODROID) {
			commandoDroidArmor();
		} else if(type == ArmorType.MAGMAGUARD) {
			magmaGuard();
		} else if(type == ArmorType.DURASTEELMEDIUMMANDO) {
			mediumDuraSteelMandoArmor();
		} else if(type == ArmorType.DURASTEELHELEMT) {
			duraSteelHelmet();
		} else if(type == ArmorType.DURASTEELVEST) {
			NewArmor.duraSteelVest(this);
		} else if(type == ArmorType.UNSCMARINE) { 
			//System.out.println("Creating UNSC Marine");
			unscMarine();
		} else if(type == ArmorType.ODST) { 
			odst();
		} else if(type == ArmorType.GRUNT){
			grunt();
		} else if(type == ArmorType.JACKAL){
			jackal();
		} else if(type == ArmorType.ELITE){
			eliteMinor();
		} else if(type == ArmorType.MKVIII_Errant){
			mkviiiErrant();
		} else {
			armorName = "None";
			type = ArmorType.NONE;
		}
		
	}
	
	public void mkviiiErrant() {
		if(MeleeArmor.meleeArmorPieces == null)
			new MeleeArmorData();
		this.bPF = 45; 
		armorName = "MKVIII Errant Armor";
		type = ArmorType.MKVIII_Errant;
		vaccumSealed = true;
		camo = -2;
		try {
			meleeArmorStats.add(MeleeArmor.getArmorPiece(MeleeArmorType.CeramiteTacticalMarine));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Phase1CloneArmor() {
		if(MeleeArmor.meleeArmorPieces == null)
			new MeleeArmorData();
		
		this.bPF = 25; 
		armorName = "Phase 1";
		camo = 1;
		vaccumSealed = true;
		type = ArmorType.PHASEONE;
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(34);
		a1.add(34);
		excludedZones.add(a1);
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a2.add(36);
		a2.add(36);
		excludedZones.add(a2);
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		a3.add(77);
		a3.add(78);
		excludedZones.add(a3);
		ArrayList<Integer> a4 = new ArrayList<Integer>();
		a4.add(91);
		a4.add(93);
		excludedZones.add(a4);
		
		ArrayList<Integer> a5 = new ArrayList<Integer>();
		a5.add(6);
		a5.add(6);
		excludedZonesOpen.add(a5);
		ArrayList<Integer> a6 = new ArrayList<Integer>();
		a6.add(8);
		a6.add(8);
		excludedZonesOpen.add(a6);
		ArrayList<Integer> a7 = new ArrayList<Integer>();
		a7.add(11);
		a7.add(11);
		excludedZonesOpen.add(a7);
		ArrayList<Integer> a8 = new ArrayList<Integer>();
		a8.add(59);
		a8.add(61);
		excludedZonesOpen.add(a8);
		ArrayList<Integer> a9 = new ArrayList<Integer>();
		a9.add(82);
		a9.add(82);
		excludedZonesOpen.add(a9);
		ArrayList<Integer> a10 = new ArrayList<Integer>();
		a10.add(84);
		a10.add(84);
		excludedZonesOpen.add(a10);
		armorWeight = 30;
		
		try {
			meleeArmorStats.add(MeleeArmor.getArmorPiece(MeleeArmorType.PlasteelCombatArmor));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void mediumDuraSteelMandoArmor() {
		if(MeleeArmor.meleeArmorPieces == null)
			new MeleeArmorData();
		
		this.bPF = 30; 
		camo = 1;
		armorName = "Durasteel Medium Mando";
		vaccumSealed = true;
		type = ArmorType.DURASTEELMEDIUMMANDO;
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(34);
		a1.add(34);
		excludedZones.add(a1);
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a2.add(36);
		a2.add(36);
		excludedZones.add(a2);
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		a3.add(77);
		a3.add(78);
		excludedZones.add(a3);
		ArrayList<Integer> a4 = new ArrayList<Integer>();
		a4.add(91);
		a4.add(93);
		excludedZones.add(a4);
		
		ArrayList<Integer> a5 = new ArrayList<Integer>();
		a5.add(6);
		a5.add(6);
		excludedZonesOpen.add(a5);
		ArrayList<Integer> a6 = new ArrayList<Integer>();
		a6.add(8);
		a6.add(8);
		excludedZonesOpen.add(a6);
		ArrayList<Integer> a7 = new ArrayList<Integer>();
		a7.add(11);
		a7.add(11);
		excludedZonesOpen.add(a7);
		ArrayList<Integer> a8 = new ArrayList<Integer>();
		a8.add(59);
		a8.add(61);
		excludedZonesOpen.add(a8);
		ArrayList<Integer> a9 = new ArrayList<Integer>();
		a9.add(82);
		a9.add(82);
		excludedZonesOpen.add(a9);
		ArrayList<Integer> a10 = new ArrayList<Integer>();
		a10.add(84);
		a10.add(84);
		excludedZonesOpen.add(a10);
		
		try {
			meleeArmorStats.add(MeleeArmor.getArmorPiece(MeleeArmorType.DurasteelCombatArmor));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void Phase1ARC() {
		if(MeleeArmor.meleeArmorPieces == null)
			new MeleeArmorData();
		
		this.bPF = 30; 
		camo = 2;
		vaccumSealed = true;
		armorName = "Phase 1 Arc";
		type = ArmorType.PHASEONEARC;
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(34);
		a1.add(34);
		excludedZones.add(a1);
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a2.add(36);
		a2.add(36);
		excludedZones.add(a2);
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		a3.add(77);
		a3.add(78);
		excludedZones.add(a3);
		ArrayList<Integer> a4 = new ArrayList<Integer>();
		a4.add(91);
		a4.add(93);
		excludedZones.add(a4);
		
		ArrayList<Integer> a5 = new ArrayList<Integer>();
		a5.add(6);
		a5.add(6);
		excludedZonesOpen.add(a5);
		ArrayList<Integer> a6 = new ArrayList<Integer>();
		a6.add(8);
		a6.add(8);
		excludedZonesOpen.add(a6);
		ArrayList<Integer> a7 = new ArrayList<Integer>();
		a7.add(11);
		a7.add(11);
		excludedZonesOpen.add(a7);
		ArrayList<Integer> a8 = new ArrayList<Integer>();
		a8.add(59);
		a8.add(61);
		excludedZonesOpen.add(a8);
		ArrayList<Integer> a9 = new ArrayList<Integer>();
		a9.add(82);
		a9.add(82);
		excludedZonesOpen.add(a9);
		ArrayList<Integer> a10 = new ArrayList<Integer>();
		a10.add(84);
		a10.add(84);
		excludedZonesOpen.add(a10);
		armorWeight = 35;
		
		try {
			meleeArmorStats.add(MeleeArmor.getArmorPiece(MeleeArmorType.PlasteelCombatArmor));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void katarnArmor() {
		if(MeleeArmor.meleeArmorPieces == null)
			new MeleeArmorData();
		
		this.bPF = 40; 
		armorName = "Katarn MK1";
		camo = -1;
		vaccumSealed = true;
		type = ArmorType.KATARN;
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(34);
		a1.add(34);
		excludedZones.add(a1);
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a2.add(36);
		a2.add(36);
		excludedZones.add(a2);
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		a3.add(77);
		a3.add(78);
		excludedZones.add(a3);
		ArrayList<Integer> a4 = new ArrayList<Integer>();
		a4.add(91);
		a4.add(93);
		excludedZones.add(a4);
		
		ArrayList<Integer> a5 = new ArrayList<Integer>();
		a5.add(6);
		a5.add(6);
		excludedZonesOpen.add(a5);
		ArrayList<Integer> a6 = new ArrayList<Integer>();
		a6.add(8);
		a6.add(8);
		excludedZonesOpen.add(a6);
		ArrayList<Integer> a7 = new ArrayList<Integer>();
		a7.add(11);
		a7.add(11);
		excludedZonesOpen.add(a7);
		ArrayList<Integer> a8 = new ArrayList<Integer>();
		a8.add(59);
		a8.add(61);
		excludedZonesOpen.add(a8);
		ArrayList<Integer> a9 = new ArrayList<Integer>();
		a9.add(82);
		a9.add(82);
		excludedZonesOpen.add(a9);
		ArrayList<Integer> a10 = new ArrayList<Integer>();
		a10.add(84);
		a10.add(84);
		excludedZonesOpen.add(a10);
		armorWeight = 35;
		
		try {
			meleeArmorStats.add(MeleeArmor.getArmorPiece(MeleeArmorType.DurasteelCombatArmor));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void b1Armor() {
		if(MeleeArmor.meleeArmorPieces == null)
			new MeleeArmorData();
		
		this.bPF = 20; 
		armorName = "B1";
		type = ArmorType.B1;
		armorWeight = 15;
		try {
			meleeArmorStats.add(MeleeArmor.getArmorPiece(MeleeArmorType.DurasteelCombatArmor));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void b2Armor() {
		if(MeleeArmor.meleeArmorPieces == null)
			new MeleeArmorData();
		
		this.bPF = 30; 
		armorName = "B2";
		type = ArmorType.B2;
		armorWeight = 30;
		try {
			meleeArmorStats.add(MeleeArmor.getArmorPiece(MeleeArmorType.DurasteelCombatArmor));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void commandoDroidArmor() {
		if(MeleeArmor.meleeArmorPieces == null)
			new MeleeArmorData();
		
		this.bPF = 25; 
		camo = 1;
		armorName = "Commando Droid";
		type = ArmorType.COMMANDODROID;
		armorWeight = 20;
		try {
			meleeArmorStats.add(MeleeArmor.getArmorPiece(MeleeArmorType.DurasteelCombatArmor));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void magmaGuard() {
		if(MeleeArmor.meleeArmorPieces == null)
			new MeleeArmorData();
		
		this.bPF = 25; 
		armorName = "Margma Guard";
		type = ArmorType.MAGMAGUARD;
		armorWeight = 20;
		try {
			meleeArmorStats.add(MeleeArmor.getArmorPiece(MeleeArmorType.DurasteelCombatArmor));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void duraSteelHelmet() {
		if(MeleeArmor.meleeArmorPieces == null)
			new MeleeArmorData();
		
		this.bPF = 25; 
		armorName = "Durasteel Helmet";
		type = ArmorType.DURASTEELHELEMT;
		
		
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(37);
		a1.add(100);
		excludedZones.add(a1);
		
		ArrayList<Integer> a0 = new ArrayList<Integer>();
		a0.add(8);
		a0.add(100);
		excludedZonesOpen.add(a0);
	}
	
	/*public void duraSteelVest() {
		this.bPF = 25; 
		armorName = "Durasteel Vest";
		type = ArmorType.DURASTEELVEST;
		
		
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(0);
		a1.add(21);
		a1.add(43);
		a1.add(100);
		excludedZones.add(a1);
		
		ArrayList<Integer> a0 = new ArrayList<Integer>();
		a0.add(1);
		a0.add(100);
		excludedZonesOpen.add(a0);
	}*/
	
	public void odst() {
		
		this.armorName = "ODST";
		type = ArmorType.ODST;
		this.bPF = 25; 
		camo = 1;
		// Fore arm fire
		ArrayList<Integer> a6 = new ArrayList<Integer>();
		a6.add(88);
		a6.add(93);
		excludedZones.add(a6);
						
		// shoulder open
		ArrayList<Integer> a5 = new ArrayList<Integer>();
		a5.add(10);
		a5.add(10);
		excludedZonesOpen.add(a5);
		
		// Fore arm open
		ArrayList<Integer> a4 = new ArrayList<Integer>();
		a4.add(16);
		a4.add(16);
		excludedZonesOpen.add(a4);
						
		// Part of thigh
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a2.add(75);
		a2.add(76);
		excludedZonesOpen.add(a2);
		
		// Part of knee and below 
		ArrayList<Integer> a0 = new ArrayList<Integer>();
		a0.add(81);
		a0.add(83);
		excludedZonesOpen.add(a0);
		
		// Differing zones 
		
		
		// Helmet Open
		ArrayList<Integer> diffValue3 = new ArrayList<Integer>();
		diffValue3.add(0);
		diffValue3.add(2);
		differingZoneValuesOpen.add(diffValue3);
		differingZonePFOpen.add(20);
		differingHardnessZoneOpen.add(false);
		
		// Face Open 
		ArrayList<Integer> diffValue2 = new ArrayList<Integer>();
		diffValue2.add(3);
		diffValue2.add(7);
		differingZoneValuesOpen.add(diffValue2);
		differingZonePFOpen.add(16);
		differingHardnessZoneOpen.add(false);

		// Pelvis Open
		ArrayList<Integer> diffValue = new ArrayList<Integer>();
		diffValue.add(43);
		diffValue.add(56);
		differingZoneValuesOpen.add(diffValue);
		differingZonePFOpen.add(10);
		differingHardnessZoneOpen.add(true);
		

		
		// Face Fire 
		ArrayList<Integer> diffValue4 = new ArrayList<Integer>();
		diffValue4.add(17);
		diffValue4.add(36);
		differingZoneValues.add(diffValue4);
		differingZonePF.add(16);
		differingHardnessZone.add(false);

		// Helmet Fire
		ArrayList<Integer> diffValue5 = new ArrayList<Integer>();
		diffValue5.add(0);
		diffValue5.add(16);
		differingZoneValues.add(diffValue5);
		differingZonePF.add(20);
		differingHardnessZone.add(false);
		
		

		
	}
	
	public void unscMarine() {
		this.bPF = 20; 
		this.armorName = "UNSC Marine";
		this.type = ArmorType.UNSCMARINE;
		camo = 1;
		// Fore arm fire
		ArrayList<Integer> a6 = new ArrayList<Integer>();
		a6.add(75);
		a6.add(99);
		excludedZones.add(a6);			
		
		// shoulder open
		ArrayList<Integer> a5 = new ArrayList<Integer>();
		a5.add(10);
		a5.add(10);
		excludedZonesOpen.add(a5);
		
		// Fore arm open
		ArrayList<Integer> a4 = new ArrayList<Integer>();
		a4.add(14);
		a4.add(17);
		excludedZonesOpen.add(a4);
				
		// base of neck 
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		a3.add(20);
		a3.add(21);
		excludedZonesOpen.add(a3);
		
		// Part of thigh
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a2.add(70);
		a2.add(75);
		excludedZonesOpen.add(a2);
		
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(78);
		a1.add(79);
		excludedZonesOpen.add(a1);
		
		// Part of knee and below 
		ArrayList<Integer> a0 = new ArrayList<Integer>();
		a0.add(82);
		a0.add(99);
		excludedZonesOpen.add(a0);
		
		
		// Differing zones 
		
		// Pelvis 
		ArrayList<Integer> diffValue1 = new ArrayList<Integer>();
		diffValue1.add(43);
		diffValue1.add(56);
		differingZoneValuesOpen.add(diffValue1);
		differingZonePFOpen.add(10);
		differingHardnessZoneOpen.add(true);
		
		// Face Open 
		ArrayList<Integer> diffValue2 = new ArrayList<Integer>();
		diffValue2.add(3);
		diffValue2.add(7);
		differingZoneValuesOpen.add(diffValue2);
		differingZonePFOpen.add(10);
		differingHardnessZoneOpen.add(false);

		// Helmet Open
		ArrayList<Integer> diffValue3 = new ArrayList<Integer>();
		diffValue3.add(0);
		diffValue3.add(2);
		differingZoneValuesOpen.add(diffValue3);
		differingZonePFOpen.add(16);
		differingHardnessZoneOpen.add(false);
		
		// Face Fire 
		ArrayList<Integer> diffValue4 = new ArrayList<Integer>();
		diffValue4.add(17);
		diffValue4.add(36);
		differingZoneValues.add(diffValue4);
		differingZonePF.add(10);
		differingHardnessZone.add(false);
		
		// Helmet Fire
		ArrayList<Integer> diffValue5 = new ArrayList<Integer>();
		diffValue5.add(0);
		diffValue5.add(16);
		differingZoneValues.add(diffValue5);
		differingZonePF.add(16);
		differingHardnessZone.add(false);

		
	}
	
	public void eliteMinor() {
		this.bPF = 25; 
		this.armorName = "Elite Minor";
		this.type = ArmorType.ELITE;
		
		// Fore arm fire
		ArrayList<Integer> a6 = new ArrayList<Integer>();
		a6.add(75);
		a6.add(99);
		excludedZones.add(a6);			
		
		ArrayList<Integer> diffValue5 = new ArrayList<Integer>();
		diffValue5.add(17);
		diffValue5.add(36);
		excludedZones.add(diffValue5);
		
		// shoulder open
		ArrayList<Integer> a5 = new ArrayList<Integer>();
		a5.add(10);
		a5.add(10);
		excludedZonesOpen.add(a5);
		
		// Fore arm open
		ArrayList<Integer> a4 = new ArrayList<Integer>();
		a4.add(14);
		a4.add(17);
		excludedZonesOpen.add(a4);
				
		// base of neck 
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		a3.add(20);
		a3.add(21);
		excludedZonesOpen.add(a3);
		
		// Part of thigh
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a2.add(70);
		a2.add(75);
		excludedZonesOpen.add(a2);
		
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(78);
		a1.add(79);
		excludedZonesOpen.add(a1);
		
		// Part of knee and below 
		ArrayList<Integer> a0 = new ArrayList<Integer>();
		a0.add(82);
		a0.add(99);
		excludedZonesOpen.add(a0);
		
		// Face Open 
		ArrayList<Integer> diffValue2 = new ArrayList<Integer>();
		diffValue2.add(3);
		diffValue2.add(7);
		excludedZonesOpen.add(diffValue2);

		
		// Differing zones 
		
		// Pelvis 
		ArrayList<Integer> diffValue1 = new ArrayList<Integer>();
		diffValue1.add(43);
		diffValue1.add(56);
		differingZoneValuesOpen.add(diffValue1);
		differingZonePFOpen.add(10);
		differingHardnessZoneOpen.add(true);
		

		// Helmet Open
		ArrayList<Integer> diffValue3 = new ArrayList<Integer>();
		diffValue3.add(0);
		diffValue3.add(2);
		differingZoneValuesOpen.add(diffValue3);
		differingZonePFOpen.add(16);
		differingHardnessZoneOpen.add(false);
		
		// Helmet Fire
		ArrayList<Integer> diffValu6 = new ArrayList<Integer>();
		diffValu6.add(0);
		diffValu6.add(16);
		differingZoneValues.add(diffValu6);
		differingZonePF.add(16);
		differingHardnessZone.add(false);

		
	}
	
	public void jackal() {
		this.bPF = 20; 
		this.armorName = "Jackal Armor";
		this.type = ArmorType.JACKAL;
		
		// Fore arm fire
		ArrayList<Integer> a6 = new ArrayList<Integer>();
		a6.add(75);
		a6.add(99);
		excludedZones.add(a6);			
		
		ArrayList<Integer> diffValue5 = new ArrayList<Integer>();
		diffValue5.add(17);
		diffValue5.add(36);
		excludedZones.add(diffValue5);
		
		// shoulder open
		ArrayList<Integer> a5 = new ArrayList<Integer>();
		a5.add(10);
		a5.add(10);
		excludedZonesOpen.add(a5);
		
		// Fore arm open
		ArrayList<Integer> a4 = new ArrayList<Integer>();
		a4.add(14);
		a4.add(17);
		excludedZonesOpen.add(a4);
				
		// base of neck 
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		a3.add(20);
		a3.add(21);
		excludedZonesOpen.add(a3);
		
		// Part of thigh
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a2.add(70);
		a2.add(75);
		excludedZonesOpen.add(a2);
		
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(78);
		a1.add(79);
		excludedZonesOpen.add(a1);
		
		// Part of knee and below 
		ArrayList<Integer> a0 = new ArrayList<Integer>();
		a0.add(82);
		a0.add(99);
		excludedZonesOpen.add(a0);
		
		// Face Open 
		ArrayList<Integer> diffValue2 = new ArrayList<Integer>();
		diffValue2.add(3);
		diffValue2.add(7);
		excludedZonesOpen.add(diffValue2);

		
		// Differing zones 
		
		// Pelvis 
		ArrayList<Integer> diffValue1 = new ArrayList<Integer>();
		diffValue1.add(43);
		diffValue1.add(56);
		differingZoneValuesOpen.add(diffValue1);
		differingZonePFOpen.add(10);
		differingHardnessZoneOpen.add(true);
		

		// Helmet Open
		ArrayList<Integer> diffValue3 = new ArrayList<Integer>();
		diffValue3.add(0);
		diffValue3.add(2);
		differingZoneValuesOpen.add(diffValue3);
		differingZonePFOpen.add(16);
		differingHardnessZoneOpen.add(false);
		
		// Helmet Fire
		ArrayList<Integer> diffValu6 = new ArrayList<Integer>();
		diffValu6.add(0);
		diffValu6.add(16);
		differingZoneValues.add(diffValu6);
		differingZonePF.add(16);
		differingHardnessZone.add(false);

		
	}
	
	public void grunt() {
		this.bPF = 20; 
		this.armorName = "Grunt Armor";
		this.type = ArmorType.GRUNT;
		
		// Fore arm fire
		ArrayList<Integer> a6 = new ArrayList<Integer>();
		a6.add(75);
		a6.add(99);
		excludedZones.add(a6);			
		
		ArrayList<Integer> diffValue5 = new ArrayList<Integer>();
		diffValue5.add(17);
		diffValue5.add(36);
		excludedZones.add(diffValue5);
		
		// shoulder open
		ArrayList<Integer> a5 = new ArrayList<Integer>();
		a5.add(10);
		a5.add(10);
		excludedZonesOpen.add(a5);
		
		// Fore arm open
		ArrayList<Integer> a4 = new ArrayList<Integer>();
		a4.add(14);
		a4.add(17);
		excludedZonesOpen.add(a4);
				
		// base of neck 
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		a3.add(20);
		a3.add(21);
		excludedZonesOpen.add(a3);
		
		// Part of thigh
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a2.add(70);
		a2.add(75);
		excludedZonesOpen.add(a2);
		
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(78);
		a1.add(79);
		excludedZonesOpen.add(a1);
		
		// Part of knee and below 
		ArrayList<Integer> a0 = new ArrayList<Integer>();
		a0.add(82);
		a0.add(99);
		excludedZonesOpen.add(a0);
		
		// Face Open 
		ArrayList<Integer> diffValue2 = new ArrayList<Integer>();
		diffValue2.add(3);
		diffValue2.add(7);
		excludedZonesOpen.add(diffValue2);

		
		// Differing zones 
		
		// Pelvis 
		ArrayList<Integer> diffValue1 = new ArrayList<Integer>();
		diffValue1.add(43);
		diffValue1.add(56);
		differingZoneValuesOpen.add(diffValue1);
		differingZonePFOpen.add(10);
		differingHardnessZoneOpen.add(true);
		

		// Helmet Open
		ArrayList<Integer> diffValue3 = new ArrayList<Integer>();
		diffValue3.add(0);
		diffValue3.add(2);
		differingZoneValuesOpen.add(diffValue3);
		differingZonePFOpen.add(16);
		differingHardnessZoneOpen.add(false);
		
		// Helmet Fire
		ArrayList<Integer> diffValu6 = new ArrayList<Integer>();
		diffValu6.add(0);
		diffValu6.add(16);
		differingZoneValues.add(diffValu6);
		differingZonePF.add(16);
		differingHardnessZone.add(false);

		
	}
	
	
	public boolean isZoneProtected(int roll, boolean open) {
		
		if(open) {
			return getProtectedOpen(roll);
		} else {
			return getProtected(roll);
		}
 		
	}
	
	public boolean getProtectedOpen(int roll) {
		
		boolean protectedZone = true;
		
		for(int i = 0; i < excludedZonesOpen.size(); i++) {
			//System.out.println("Excluded Zone: "+excludedZonesOpen.get(i).get(0)+", "+excludedZonesOpen.get(i).get(1));
			if(roll >= excludedZonesOpen.get(i).get(0) && roll <= excludedZonesOpen.get(i).get(1)) {
				protectedZone = false; 
				break; 
			}
		}

		if(GameWindow.gameWindow != null && GameWindow.gameWindow.conflictLog != null) {
			//GameWindow.gameWindow.conflictLog.addNewLineToQueue("Armor Wearer Hit Open, Location Roll: "+roll+", Protected: "+protectedZone);
		}
		
		return protectedZone; 
	}
	
	public boolean getProtected(int roll) {
		
		boolean protectedZone = true; 
		
		for(int i = 0; i < excludedZones.size(); i++) {
			//System.out.println("Excluded Zone: "+excludedZones.get(i).get(0)+", "+excludedZones.get(i).get(1));
			if(roll >= excludedZones.get(i).get(0) && roll <= excludedZones.get(i).get(1)) {
				protectedZone = false; 
				break; 
			}
		}
		
		/*if(GameWindow.gameWindow != null && GameWindow.gameWindow.conflictLog != null) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Armor Wearer Hit Fire Position, Location Roll: "+roll+", Protected: "+protectedZone);
		}*/
		
		return protectedZone; 
	}
	
	public int getPF(int hitLocation, boolean open) {
		
		if(open) {
			return getProtectionFactorOpen(hitLocation, open);
		} else {
			return getProtectionFactor(hitLocation, open);
		}
		
	}
	
	private int getProtectionFactor(int roll, boolean open) {

		int PF = 0;

		if (getProtected(roll))
			PF = getBPF(roll, open);

		// System.out.println("PF: "+PF);
		return PF;

	}

	private int getProtectionFactorOpen(int roll, boolean open) {

		int PF = 0;
		// System.out.println("Roll: "+roll);
		if (getProtectedOpen(roll)) {
			PF = getBPF(roll, open);
		}

		// System.out.println("PF1: "+PF);
		return PF;

	}
	
	public int getBPF(int roll, boolean open) {
		
		//System.out.println("getBPF: "+roll);
		
		ArrayList<ArrayList<Integer>> zonesValues;
		ArrayList<Integer> zonePF;
		if(open) {
			zonesValues = differingZoneValuesOpen; 
			zonePF = differingZonePFOpen; 
		} else {
			zonesValues = differingZoneValues;
			zonePF = differingZonePF;
		}
		
		int pf = this.bPF;
		//System.out.println("Zone Values: "+zonesValues.size());
		//System.out.println("Zone PF: "+zonePF.size());
		for(int i = 0; i < zonesValues.size(); i++) {
			//System.out.println("zonesValues: "+zonesValues.get(i).get(0)+", "+zonesValues.get(i).get(1));
			if(roll >= zonesValues.get(i).get(0) && roll <= zonesValues.get(i).get(1)) {
				pf = zonePF.get(i); 
				break; 
			}
		}
		
		if(GameWindow.gameWindow != null && GameWindow.gameWindow.conflictLog != null) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Armor Hit, Location Roll: "+roll+", PF: "+pf);
		}
		
		return pf;
	
	}
	
	public int getModifiedProtectionFactor(int hitLocation, int bPF, boolean open) throws Exception {
		
		int glanceRoll = DiceRoller.roll(0, 9);
		//System.out.println("glance roll: "+glanceRoll);
		try {
			return (int) ExcelUtility.getNumberFromSheet(glanceRoll, bPF, "Formatted Excel Files\\protectionfactortable.xlsx", true, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		throw new Exception("Modified protection factor not found");
	}
	
	public ArrayList<String> getLocations() {
		ArrayList<String> rslts = new ArrayList<>();

		rslts.add("  Base PF: "+bPF);
		
		if(hardBodyArmor)
			rslts.add("  Type: Hard Body Armor");
		else 
			rslts.add("  Type: Soft Body Armor");
		
		rslts.add("      Unprotected Zones Fire:");
		updateRslts(rslts, excludedZones, null, null, false);
		
		rslts.add("      Unprotected Zones Open:");
		updateRslts(rslts, excludedZonesOpen, null, null, true);
		
		rslts.add("      Differing Zones Fire:");
		updateRslts(rslts, differingZoneValues, differingZonePF, differingHardnessZone, false);
		
		rslts.add("      Differing Zones Open:");
		updateRslts(rslts, differingZoneValuesOpen, differingZonePFOpen, differingHardnessZoneOpen, true);
		
		return rslts; 
		
	}
	
	public void updateRslts(ArrayList<String> rslts, ArrayList<ArrayList<Integer>> pairs, ArrayList<Integer> val,  ArrayList<Boolean> bools, boolean open) {
		//System.out.println("UPDATING RESULTS, pairs size : "+pairs.size()+", val size: "+val.size());
		int i = 0;
		for(ArrayList<Integer> pair : pairs) {
			//System.out.println("Pair: "+pair.get(0)+", "+pair.get(1));
			if(val != null) {
				//System.out.println("Val: "+val.get(i)+", i: "+i);
				
			}
			for(String str : Location.convertLocationArrayToString(PCUtility.getPCLocations(pair.get(0), pair.get(1), open))) {
				if(val != null) 
					str += ", PF: "+val.get(i);
				
				if(bools != null) {
					if(bools.get(i) && hardBodyArmor) 
						str += ", "+"Soft Body Armor";
					else if(bools.get(i))
						str += ", "+"Hard Body Armor";
				}
				
				rslts.add("  "+str);
				
			}
			i++; 
			
		}
	}
	
	
	public boolean isHardBodyArmor(int roll, boolean open) {
		
		ArrayList<ArrayList<Integer>> zonesValues;
		ArrayList<Boolean> diffHardnessZones; 
		if(open) {
			zonesValues = differingZoneValuesOpen; 
			diffHardnessZones = differingHardnessZoneOpen;
		} else {
			zonesValues = differingZoneValues;
			diffHardnessZones = differingHardnessZone;
		}
		
		for(int i = 0; i < zonesValues.size(); i++) {
			//System.out.println("Excluded Zone: "+excludedZones.get(i).get(0)+", "+excludedZones.get(i).get(1));
			if(roll >= zonesValues.get(i).get(0) && roll <= zonesValues.get(i).get(1)) {
				
				if(diffHardnessZones.get(i)) {
					return !hardBodyArmor;
				}
				
			}
		}
		
		return hardBodyArmor; 
		
	}
	
	public int percentUncovered() {
		
		int val = 0;
		
		for(var location : excludedZonesOpen) 
			val += Math.abs(location.get(0) - location.get(1)) + 1;
		
		return val;
	}
	
}
