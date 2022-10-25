package CorditeExpansion;

import java.util.ArrayList;

import Trooper.Trooper;
import Trooper.Trooper.MaximumSpeed;

public class CeStatBlock {

	public double quickness; 
	public int totalMoved = 0; 
	private Cord cord = new Cord(0, 0);
	private ArrayList<CeAction> actions = new ArrayList<>();
	
	public CeStatBlock(Trooper trooper) {
		quickness = trooper.maximumSpeed.get();
	}
	
	public void moveTrooper(Cord cord) {
		this.cord = cord; 
	}
	
	public void spendCombatAction() {
		actions.get(0).spendCombatAction();
		if(actions.get(0).completed())
			actions.remove(0);
	}
	
	public void addAction(CeAction action) {
		actions.add(0, action);
	}
	
	public Cord getPosition() {
		return cord; 
	}
	
	public CeAction getActions(int index) {
		return actions.get(index);
	}
	
	
	// the below methods are for testing 
	
	
	public int actionsSize() {
		return actions.size();
	}
	
}
