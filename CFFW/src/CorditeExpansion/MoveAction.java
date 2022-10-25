package CorditeExpansion;

import java.util.ArrayList;

public class MoveAction implements CeAction {
	
	MoveType type;
	CeStatBlock statBlock;
	ArrayList<Cord> cords = new ArrayList<>();
	boolean completed = false; 
	
	public enum MoveType {
		STEP
	}
	
	public MoveAction(MoveType type, CeStatBlock statBlock, ArrayList<Cord> cords) {
		this.type = type; 
		this.statBlock = statBlock;
		this.cords = cords; 
	}
	
	public void addTargetHex(Cord cord) {
		cords.add(cord);
	}
	
	public void addTargetHexes(ArrayList<Cord> cords) {
		
		for(Cord cord : cords)
			this.cords.add(cord);

	}
	
	public void removeTargetHex(int index) {
		cords.remove(index);
	}
	
	public void moveTrooper() {
		Cord cord = cords.remove(0);
		statBlock.moveTrooper(cord);
	}

	@Override
	public void spendCombatAction() {
		
		switch(type) {
		
			case STEP:
				moveTrooper();

		}
		
		if(cords.size() < 1) {
			completed = true;
		}
		
	}
	
	@Override
	public boolean completed() {
		return completed;
	}
	
}
