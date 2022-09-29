package Hexes;

import java.io.Serializable;

public class Feature implements Serializable {

	public String featureType; 
	public int coverPositions;
	
	public Feature(Feature feature) {
		featureType = feature.featureType;
		coverPositions = feature.coverPositions;
	}
	
	public Feature(String featureType, int coverPositions) {
		
		this.featureType = featureType; 
		this.coverPositions = coverPositions; 
		
		
		
		
	}
	
	public String toString() {
		
		return featureType + " Cover: "+coverPositions;
		
	}
	
}
