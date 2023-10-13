package Items;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
import Melee.Gear.MeleeShield;
import Melee.Gear.MeleeShieldData.MeleeShieldType;
import UtilityClasses.Location;
import UtilityClasses.PCUtility;

public class PersonalShield implements Serializable {

	
	public ArrayList<ArrayList<Integer>> protectedZonesOpen = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> protectedZones = new ArrayList<ArrayList<Integer>>();
	
	public int currentShieldStrength; 
	public int maxShieldStrength; 
	public double rechargeRate;
	
	public String shieldName; 
	public ShieldType shieldType; 
	
	public MeleeShield meleeShield;
	
	public enum ShieldType {
		NONE,MKIIBubbleShield,DCR1RifleShield,ELITEMINOR,
		StormShield
	}
	
	public PersonalShield(ShieldType st) {
		
		if(st == ShieldType.MKIIBubbleShield) {
			MKIIBubbleShield();
		} else if(st == ShieldType.DCR1RifleShield) {
			DCR1RifleShield();
		} else if(st == ShieldType.ELITEMINOR) {
			eliteMinor();
		} else if(st == ShieldType.StormShield) {
			stormShield();
		} else {
			protectedZonesOpen.clear();
			protectedZones.clear();
			currentShieldStrength = 0; 
			maxShieldStrength = 0; 
			shieldName = "None";
			shieldType = null;
		}
		
	}
	
	public void stormShield() {
		currentShieldStrength = 200; 
		maxShieldStrength = 200;
		rechargeRate = 0.2; 
		shieldName = "Storm Shield";
		shieldType = ShieldType.StormShield;
		try {
			meleeShield = MeleeShield.getMeleeShield(MeleeShieldType.StormShield);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void MKIIBubbleShield() {
		currentShieldStrength = 200; 
		maxShieldStrength = 200;
		rechargeRate = 0.2; 
		shieldName = "MKII Bubble Shield";
		shieldType = ShieldType.MKIIBubbleShield;
		
		ArrayList<Integer> zone1 = new ArrayList<>();
		
		zone1.add(0);
		zone1.add(99);
		
		protectedZonesOpen.add(zone1);
		protectedZones.add(zone1);
		
		
		
	}
	
	public void DCR1RifleShield() {
		currentShieldStrength = 200; 
		maxShieldStrength = 200;
		rechargeRate = 0.2; 
		shieldName = "DC R1 Rifle Shield";
		shieldType = ShieldType.DCR1RifleShield;
		
		ArrayList<Integer> zone1 = new ArrayList<>();
		
		zone1.add(0);
		zone1.add(50);
		
		ArrayList<Integer> zone2 = new ArrayList<>();
		
		zone2.add(0);
		zone2.add(99);
		
		protectedZonesOpen.add(zone1);
		protectedZones.add(zone2);
		
		
	}
	
	public void eliteMinor() {
		currentShieldStrength = 200; 
		maxShieldStrength = 200;
		rechargeRate = 0.15; 
		shieldName = "Elite Minor";
		shieldType = ShieldType.ELITEMINOR;
		
		ArrayList<Integer> zone1 = new ArrayList<>();
		
		zone1.add(0);
		zone1.add(99);
		
		protectedZonesOpen.add(zone1);
		protectedZones.add(zone1);
	}

	public boolean isZoneProtected(int roll, boolean open) {
		
		if(open) {
			return getProtectedOpen(roll);
		} else {
			return getProtected(roll);
		}
		
	}
	
	private boolean getProtectedOpen(int roll) {
		
		boolean protectedZone = false;
		
		for(int i = 0; i < protectedZonesOpen.size(); i++) {
			//System.out.println("Excluded Zone: "+excludedZonesOpen.get(i).get(0)+", "+excludedZonesOpen.get(i).get(1));
			if(roll >= protectedZonesOpen.get(i).get(0) && roll <= protectedZonesOpen.get(i).get(1)) {
				protectedZone = true; 
				break; 
			}
		}

		if(GameWindow.gameWindow != null && GameWindow.gameWindow.conflictLog != null) {
			//GameWindow.gameWindow.conflictLog.addNewLineToQueue("Shield Wearer Hit Open, Location Roll: "+roll+", Protected: "+protectedZone);
		}
		
		return protectedZone; 
	}
	
	private boolean getProtected(int roll) {
		
		boolean protectedZone = false; 
		
		for(int i = 0; i < protectedZones.size(); i++) {
			//System.out.println("Excluded Zone: "+excludedZones.get(i).get(0)+", "+excludedZones.get(i).get(1));
			if(roll >= protectedZones.get(i).get(0) && roll <= protectedZones.get(i).get(1)) {
				protectedZone = true; 
				break; 
			}
		}
		
		if(GameWindow.gameWindow != null && GameWindow.gameWindow.conflictLog != null) {
			//GameWindow.gameWindow.conflictLog.addNewLineToQueue("Shield Wearer Hit Fire Position, Location Roll: "+roll+", Protected: "+protectedZone);
		}
		
		return protectedZone; 
	}
	
	public ArrayList<String> getLocations() {
		ArrayList<String> rslts = new ArrayList<>();

		rslts.add("  Max Shield Strength: "+maxShieldStrength);
		rslts.add("  Current Shield Strength: "+currentShieldStrength);

		rslts.add("      Protected Zones Fire:");
		updateRslts(rslts, protectedZones, false);
		
		rslts.add("      Protected Zones Open:");
		updateRslts(rslts, protectedZonesOpen,  true);
		

		
		return rslts; 
		
	}
	
	public void updateRslts(ArrayList<String> rslts, ArrayList<ArrayList<Integer>> pairs, boolean open) {

		for(ArrayList<Integer> pair : pairs) {
			
			for(String str : Location.convertLocationArrayToString(PCUtility.getPCLocations(pair.get(0), pair.get(1), open))) {

				rslts.add("  "+str);
				
			}
			
		}
	}
	
	public int percentUncovered() {
		
		int val = 0;
		
		for(var location : protectedZonesOpen) 
			val += Math.abs(location.get(0) - location.get(1)) + 1;
		
		return 100 - val;
	}
	
}
