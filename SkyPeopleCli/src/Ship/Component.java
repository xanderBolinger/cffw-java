package Ship;

import java.util.ArrayList;

public class Component {
	public enum ComponentType {
		THRUST,REACTORS,SI,BRIDGE,LIFESUPPORT,HYPERDRIVE,RADIATORS,HEATSINKS,
		QUARTERS,CARGO,AMMUNITION,VEHIClEBAY,HANGARBAY,CREWSTATION
	}
	
	public ArrayList<Cell> cells;
	public ComponentType componentType;
	
}
