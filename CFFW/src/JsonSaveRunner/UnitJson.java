package JsonSaveRunner;

import java.util.ArrayList;

import Unit.Unit;

public class UnitJson {

	public String identifier = TrooperJson.getIdentifier();
	
	ArrayList<TrooperJson> troopers;
	
	public UnitJson(Unit unit) {
		troopers = new ArrayList<TrooperJson>();
		
		for(var t : unit.individuals) {
			troopers.add(new TrooperJson(t));
		}
		
	}
	
}
