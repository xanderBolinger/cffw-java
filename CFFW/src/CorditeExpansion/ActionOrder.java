package CorditeExpansion;

import java.util.ArrayList;

import Trooper.Trooper;

public class ActionOrder {

	private ArrayList<Trooper> initiativeOrder = new ArrayList<>();

	public Impulse impulse = Impulse.FIRST;

	public enum Impulse {
		FIRST, SECOND, THIRD, FOURTH
	}

	public void addTrooper(Trooper trooper) {
		setCeStats(trooper);
		initiativeOrder.add(trooper);
		sortActionOrder();
	}

	public void removeTrooper(Trooper trooper) {
		initiativeOrder.remove(trooper);
	}

	public Trooper get(int index) {
		return initiativeOrder.get(index);
	}
	
	public Trooper get(Trooper trooper) {
		
		for(Trooper otherTrooper : initiativeOrder) {
			if(trooper.compareTo(otherTrooper)) {
				return otherTrooper; 
			}
		}
		
		return null; 
	}
	
	private void sortActionOrder() {
		
	}
	
	private void setCeStats(Trooper trooper) {
		trooper.ceStatBlock = new CeStatBlock(trooper);
	}
	
	
	// Below methods are used for testing 
	
	public int initOrderSize() {
		return initiativeOrder.size();
	}


}
