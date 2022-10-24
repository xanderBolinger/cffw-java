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

	private void sortActionOrder() {
		
	}
	
	private void setCeStats(Trooper trooper) {
		
	}
	
	
	
	// Below methods are used for testing 
	
	public int initOrderSize() {
		return initiativeOrder.size();
	}


}
