package Vehicle.Data;

import java.io.Serializable;

public class PositionSpotData implements Serializable {
	
	public int thermalMod; 
	public int magnification; 
	public int nightVisionGen;
	
	public PositionSpotData(int thermalMod, int magnification, 
			int nightVisionGen) {
		this.thermalMod = thermalMod; 
		this.magnification = magnification;
		this.nightVisionGen = nightVisionGen;
	} 
	
}
