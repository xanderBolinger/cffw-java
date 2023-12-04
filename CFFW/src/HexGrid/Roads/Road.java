package HexGrid.Roads;

import java.io.Serializable;

import CorditeExpansion.Cord;

public class Road implements Serializable {

	public boolean highway;
	public Cord point1;
	public Cord point2;
	
	public Road(Cord p1, Cord p2, boolean highway) {
		point1 = p1; 
		point2 = p2;
		this.highway = highway;
	}
	
}
