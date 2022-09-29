package SaveCompanies;

import java.io.Serializable;
import java.util.ArrayList;

import Unit.Unit;

public class InitOrder implements Serializable {

	public ArrayList<Unit> initOrder;
	
	public InitOrder(ArrayList<Unit> initOrder) {
		
		this.initOrder = initOrder;
		
	}
	
	
	
}
