package HexGrid.Roads;

import java.io.Serializable;

import CorditeExpansion.Cord;

public class Road implements Serializable {

	public boolean highway;
	public Cord point;
	
	public Road(Cord p1, boolean highway) {
		point = p1; 
		this.highway = highway;
	}
	
}
