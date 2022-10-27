package CorditeExpansion;

import java.util.ArrayList;

import CeHexGrid.Chit;
import CeHexGrid.Chit.Facing;
import Trooper.Trooper;
import Trooper.Trooper.MaximumSpeed;

public class CeStatBlock {

	public double quickness; 
	public int adaptabilityFactor; 
	
	
	
	public int totalMoved = 0; 

	private Cord cord = new Cord(0, 0);
	private ArrayList<CeAction> actions = new ArrayList<>();
	private ArrayList<CeAction> coac = new ArrayList<>();
	
	public Chit chit; 
	
	
	
	
	public CeStatBlock(Trooper trooper) {
		quickness = trooper.maximumSpeed.get();
		
		chit = new Chit();
	}
	
	public void moveTrooper(Cord cord) {
		this.cord = cord; 
	}
	
	public void spendCombatAction() {
		actions.get(0).spendCombatAction();
		if(actions.get(0).completed())
			actions.remove(0);
	}
	
	public void prepareCourseOfAction() {
		coac.get(0).spendCombatAction();
		if(coac.get(0).ready()) {
			addAction(coac.remove(0));
		}
	}
	
	public void addAction(CeAction action) {
		actions.add(0, action);
	}
	
	public void addActionCoac(CeAction action) {
		coac.add(0, action);
	}
	
	public Cord getPosition() {
		return cord; 
	}
	
	public CeAction getActions(int index) {
		return actions.get(index);
	}
	
	
	public Facing getFacing() {
		return chit.facing;
	}
	
	public void setFacing(Facing facing) {
		chit.facing = facing;
	}
	
	
	// the below methods are for testing 
	
	
	public int actionsSize() {
		return actions.size();
	}
	
	public int coacSize() {
		return coac.size();
	}
	
}
