package Turns;

import java.util.ArrayList;

public class Segment {

	public ArrayList<Step> steps;
	public boolean completed;
	
	public Segment() {
		steps = new ArrayList<>();
		//steps.add(new Step("Plotting Step"));
		steps.add(new Step("Orders Step", "For each ship record their orders."));
		steps.add(new Step("Process Fire Orders", "For each ship/formation record weapons fired and targets."));
		steps.add(new Step("Process Long Orders", "For each ship/formation record completion of long orders."));
		steps.add(new Step("Resolve Thrust", "For each ship/formation list added thrust points."));
		steps.add(new Step("Movement", "For each ship/formation record movement direction and destination hex code + elevation."));
		steps.add(new Step("Thrust Break & Vector Conditions", "record which ships and formations have thrust breaks, list new vectors for those ships"));
		completed = false;
	}
	
	public Segment(Segment original) {
	    this.steps = new ArrayList<>(original.steps.size());
	    for (Step step : original.steps) {
	        this.steps.add(new Step(step));
	    }
	    this.completed = original.completed;
	}

	public boolean nextStep() {
		for(Step step : steps) {
			if(!step.completed) {
				step.completed = true; 
				if(steps.get(steps.size() - 1) == step) {
					return false; 
				} else {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public Step currentStep() {
		
		for(Step step : steps) {
			if(!step.completed) {
				return step;
			}
		}
		
		return null; 
		
	}
	
	@Override
	public String toString() {
		String rslts = "";
		
		for(Step step : steps) {
			rslts += step.toString() + "\n";
		}
		
		return rslts; 
	}
	
}
