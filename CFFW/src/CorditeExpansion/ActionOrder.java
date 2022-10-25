package CorditeExpansion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

		for (Trooper otherTrooper : initiativeOrder) {
			if (trooper.compareTo(otherTrooper)) {
				return otherTrooper;
			}
		}

		return null;
	}

	private void sortActionOrder() {
		Collections.sort(initiativeOrder, new Comparator<Trooper>() {
			public int compare(Trooper t1, Trooper t2) {
				if(t1.ceStatBlock.quickness < t2.ceStatBlock.quickness) {
					return 1;
				} else if(t1.ceStatBlock.quickness > t2.ceStatBlock.quickness ) {
					return -1; 
				} else {
					return 0;
				}
				// return if t1 is greater return +1, if t2 is smaller return -1 otherwise 0
			}
		});
	}

	private void setCeStats(Trooper trooper) {
		trooper.ceStatBlock = new CeStatBlock(trooper);
	}
	
	// Below methods are used for testing

	public int initOrderSize() {
		return initiativeOrder.size();
	}

	public void sort() {
		sortActionOrder();
	}
	
	public void clear() {
		initiativeOrder.clear();
	}
	
}
