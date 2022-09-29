package UtilityClasses;

import java.util.ArrayList;


public class Location {
	String locationName; 
	int locationLowerBound; 
	int locationUpperBound; 
	
	public Location(String locationName, int locationLowerBound, int locationUpperBound) {
		this.locationName = locationName;
		this.locationLowerBound = locationLowerBound;
		this.locationUpperBound = locationUpperBound;
	}
	
	
	public static ArrayList<String> convertLocationArrayToString(ArrayList<Location> locations) {
		
		ArrayList<String> strLocations = new ArrayList<>();
		for(Location location : locations) {
			strLocations.add(location.toString());
		}
		
		return strLocations; 
	}
	
	@Override
	public String toString() {
		
		return locationName + ": "+Integer.toString(locationLowerBound)+", "+Integer.toString(locationUpperBound);
		
	}
	
}
