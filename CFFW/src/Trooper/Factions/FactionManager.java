package Trooper.Factions;

import java.util.ArrayList;

import javax.swing.JComboBox;

public class FactionManager {

	public static ArrayList<Faction> factions;
	
	public enum FactionType {
		Astartes,PhaseOneClones,
		
		UnitedNationsSpaceCommand
		
		
	}
	
	
	public static void initalizeFactions() {
		factions = new ArrayList<Faction>();
		factions.add(new Astartes());
		factions.add(new PhaseOneClones());
		factions.add(new UnitedNationsSpaceCommand());
	}
	
	
	public static boolean factionExists(String name) {
		
		for(var faction : factions)
			if(faction.factionName.equals(name))
				return true;
		
		return false;
	}
	
	
	public static void addFactions(JComboBox dropDown) {
		
		for(var faction : factions) {
			dropDown.addItem(faction.factionName);
		}
		
	} 
	
	public static Faction getFactionFromName(String name) throws Exception {
		
		for(var faction : factions)
			if(faction.factionName.equals(name))
				return faction;
		
		throw new Exception("Faction now found for faction name: "+name);
	}
	
	
}
