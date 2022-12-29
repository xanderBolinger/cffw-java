package Items;

import java.util.ArrayList;

public class Optic {
	public enum OpticType {
		REDDOT
	}

	public ArrayList<Integer> opticBonuses = new ArrayList<>();
	OpticType opticType;

	public Optic(OpticType opticType) {
		this.opticType = opticType; 
		
		switch(opticType) {
		
			case REDDOT:
				redDot();
				break;
			default:
				break;
		
		}
	}
	
	public void redDot() {
		for(int i = 0; i < 3; i++)
			opticBonuses.add(1);
		
		for(int i = 0; i < 9; i++)
			opticBonuses.add(2);
	}

	public void applyOptic(Weapons weapon) {
		
		for(int i = 0; i < weapon.aimTime.size(); i++) {
			int original = weapon.aimTime.get(i);
			int bonus = opticBonuses.get(i);
			weapon.aimTime.set(i, original + bonus);
		}
		
	}
	
}
