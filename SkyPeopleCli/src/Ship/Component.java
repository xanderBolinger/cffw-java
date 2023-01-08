package Ship;

import java.util.ArrayList;

public class Component {
	public enum ComponentType {
		THRUST,REACTORS,SI,BRIDGE,LIFESUPPORT,HYPERDRIVE,RADIATORS,BATTERY,HEATSINKS,
		QUARTERS,CARGO,POINTDEFENSE,VEHIClEBAY,HANGARBAY,SHIELD
	}
	
	public ArrayList<Cell> cells = new ArrayList<>();
	public ComponentType componentType;
	
	public Component(ComponentType componentType) {
		this.componentType = componentType;
	}
	
}
