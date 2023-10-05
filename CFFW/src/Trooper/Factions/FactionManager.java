package Trooper.Factions;

import java.util.ArrayList;

public class FactionManager {

	public static ArrayList<Faction> factions;
	
	public enum FactionType {
		Astartes
	}
	
	
	public static void initalizeFactions() {
		factions = new ArrayList<Faction>();
		factions.add(new Astartes());
	}
	
	
	public static boolean factionExists(String name) {
		
		for(var faction : factions)
			if(faction.factionName.equals(name))
				return true;
		
		return false;
	}
	
	
	
	
	public static Faction getFactionFromName(String name) throws Exception {
		
		for(var faction : factions)
			if(faction.factionName.equals(name))
				return faction;
		
		throw new Exception("Faction now found for faction name: "+name);
	}
	
	
}
