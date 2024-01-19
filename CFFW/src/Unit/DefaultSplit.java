package Unit;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
import Trooper.Trooper;

public class DefaultSplit implements Serializable {

	ArrayList<Trooper> originalUnit;
	ArrayList<Trooper> splitTroopers;
	
	public DefaultSplit(Trooper[] unitTroopers, int[] splitIndices) {
		originalUnit = new ArrayList<Trooper>();
		this.splitTroopers = new ArrayList<Trooper>();
	
		for(var t : unitTroopers)
			originalUnit.add(t);
		
		for(var index : splitIndices)
			splitTroopers.add(originalUnit.get(index));
	}
	
	public ArrayList<Trooper> getSplitTroopers() {
		
		return splitTroopers;
		
	}
	
	private boolean canSplit(Unit unit) {
		
		if(unit.individuals.size() != originalUnit.size())
			return false;
		
		for(int i = 0; i < unit.individuals.size(); i++) {
				
			if(!unit.individuals.get(i).compareTo(originalUnit.get(i)))
				return false;
			
		}
		
		return true;
		
	}
	
	public void splitUnit(Unit unit, Unit newUnit) {
		if(!canSplit(unit)) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Could not split unit: "+unit.callsign);
			return;
		}
		
		for(var individual : splitTroopers) {
			unit.removeFromUnit(individual);
			newUnit.addToUnit(individual);
		}
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Split unit: "+unit.callsign);
		
	}
	
}
