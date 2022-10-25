package CorditeExpansion;

import java.util.ArrayList;

public class MoveAction implements CeAction {

	MoveType type;
	boolean completed = false; 
	
	CeStatBlock statBlock;
	ArrayList<Cord> cords = new ArrayList<>();
	double movementFraction = 0.0; 
	
	
	public enum MoveType {
		CRAWL,STEP
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
		movementFraction = 0.0;
		statBlock.totalMoved++;
	}

	@Override
	public void spendCombatAction() {
		
		if(statBlock.totalMoved >= statBlock.quickness) {
			return; 
		}
		
		switch(type) {
		
			case STEP:
				moveTrooper();
			case CRAWL:
				movementFraction += 0.25; 
				if(movementFraction >= 1) {
					moveTrooper();
				}

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
