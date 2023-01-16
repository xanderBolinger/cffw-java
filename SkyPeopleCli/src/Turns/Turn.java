package Turns;

import java.util.ArrayList;

public class Turn {

	public Step heatManagement;
	public Step damageControl;
	public ArrayList<Segment> segments;
	
	public Turn() {
		heatManagement = new Step("Heat Managment", "unused");
		damageControl = new Step("Damage Control", "unsued");
		
		segments = new ArrayList<>();
		
		for(int i = 0; i < 8; i++)
			segments.add(new Segment());
	}
	
	public Turn(Turn original) {
	    this.heatManagement = new Step(original.heatManagement);
	    this.damageControl = new Step(original.damageControl);
	    this.segments = new ArrayList<>(original.segments.size());
	    for (Segment segment : original.segments) {
	        this.segments.add(new Segment(segment));
	    }
	}
	
	public boolean nextSegment() {
		
		for(Segment segment : segments) {
			if(!segment.completed) {
				segment.completed = true; 
				if(segments.get(segments.size() - 1) == segment) {
					return false; 
				} else {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int currentSegmentNumber() {
		
		for(Segment segment : segments) {
			if(!segment.completed) {
				return segments.indexOf(segment) + 1;
			}
		}
		
		return -1; 
		
	}
	
	public Segment currentSegment() {
		for(Segment segment : segments) {
			if(!segment.completed) {
				return segment;
			}
		}
		
		return null;
	}
	
	@Override 
	public String toString() {
		
		String rslts = "";
		
		for(Segment segment : segments) {
			rslts += "Segment " + (segments.indexOf(segment) + 1);
			rslts += segment.toString() + "\n";
		}
		
		return rslts;
	}
	
}
