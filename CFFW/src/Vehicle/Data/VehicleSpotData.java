package Vehicle.Data;

import java.io.Serializable;

public class VehicleSpotData implements Serializable {

	public enum ThermalShroud implements Serializable {
		Impervious,Resistant,None
	}
	
	public boolean fired = false;
	public ThermalShroud thermalShroud;
	public int thermalMod; 
	public int magnification; 
	public int nightVisionGen;
	public int stealthField;
	public int camo;
	public int turretSize;
	public int hullSize;
	
	public VehicleSpotData(int turretSize, int hullSize, int thermalMod, int magnification, 
			int nightVisionGen, int stealthField, int camo, 
			String thermalShroud) {
		this.turretSize = turretSize; 
		this.hullSize = hullSize;
		this.thermalMod = thermalMod; 
		this.magnification = magnification;
		this.nightVisionGen = nightVisionGen;
		this.stealthField = stealthField;
		this.camo = camo;
		for(var e : ThermalShroud.values()) {
			if(e.toString().equals(thermalShroud))
				this.thermalShroud = e;
		}
	} 
	
}
