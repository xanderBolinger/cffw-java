package Turns;

import java.util.ArrayList;

import Game.GameMaster;
import Mechanics.Formation;
import Ship.Ship;

public class Step {

	public String stepName; 
	public String instructions;
	public ArrayList<String> responses;
	public boolean completed; 
	
	public Step(String stepName, String instructions) {
		this.stepName = stepName; 
		this.instructions = instructions;
		responses = new ArrayList<>();
		completed = false;
	}
	
	public Step(Step original) {
	    this.stepName = original.stepName;
	    this.instructions = original.instructions;
	    this.responses = new ArrayList<>(original.responses);
	    this.completed = original.completed;
	}


	public void addResponse(Ship ship, String text) {
		responses.add(ship.shipName+": "+text);
	}
	
	public void addResponse(Formation form, String text) {
		responses.add(form.formationName+": "+text);
	}
	
	public boolean completed() {
		if(responses.size() == numberOfResponses()) {
			return true; 
		}

		return false; 
	}
	
	public boolean shipInFormation(Ship ship) {
		
		for(Formation form : GameMaster.game.formations) {
			for(Ship ship1 : form.ships) {
				if(ship == ship1)
					return true;
			}
		}
		
		
		return false; 
	}
	
	public int numberOfResponses() {
		
		int responses = GameMaster.game.formations.size();
		
		for(Ship ship : GameMaster.game.ships) {
			if(!shipInFormation(ship)) {
				responses++;
			}
		}
		
		return responses; 
	}
	
	@Override 
	public String toString() {
		String rslts = "";
		
		rslts += stepName + ": " + instructions + "\n";
		rslts += "Responses: " + "\n";
		for(String response : responses) {
			rslts += response += "\n";
		}
		
		
		return rslts;
		
	}
	
	
}
