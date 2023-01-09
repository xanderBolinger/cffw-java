package Ship;

import java.util.ArrayList;

public class Component {
	public enum ComponentType {
		THRUST,REACTORS,SI,BRIDGE,LIFESUPPORT,HYPERDRIVE,RADIATORS,BATTERY,HEATSINKS,
		QUARTERS,CARGO,POINTDEFENSE,VEHICLEBAY,HANGARBAY,SHIELD
	}
	
	public ArrayList<Cell> cells = new ArrayList<>();
	public ComponentType componentType;
	
	
	public Component(ComponentType componentType) {
		this.componentType = componentType;
	}
	
	
	public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
    }

	@Override
	public String toString() {
		String rslts = componentType+": ";
		
		int counter = 0; 
		for(Cell cell : cells) {
			rslts += cell.toString() +", ";
			counter++; 
			if(counter % 10 == 0)
				rslts += "\n";
		}
		
		rslts = replaceLast(rslts, ",", "");
		
		return rslts;
	}
	
	
}
