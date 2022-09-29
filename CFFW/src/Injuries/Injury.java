package Injuries;

import java.io.Serializable;

public class Injury implements Serializable {
	private String type; 
	private String location; 
	private String penetrationStatus;
	private String disabling; 
	private int damageTable; 
	private int pd; 
	
	
	public Injury(boolean exposed, int DC, int PEN, String type, int PF) {
		
	}
	
	
	
	@Override 
	public String toString() {
		return type +" to the "+location+" for "+pd+" PD;";
	}
	
	
	
}
