package Vehicle.HullDownPositions;

import java.io.Serializable;

public class HullDownPosition implements Serializable {

	public enum HullDownStatus {
		HIDDEN,TURRET_DOWN,HULL_DOWN,PARTIAL_HULL_DOWN
	}
	
	public enum HullDownDecision {
		NOTHING,INCH_FORWARD,INCH_BACKWARD,ENTER,EXIT
	}

	public HullDownStatus minimumHullDownStatus;
	public HullDownStatus maximumHullDownStatus;
	public int capacity;
	public int occupants;
	
	public HullDownPosition(HullDownStatus minimumHullDownStatus, HullDownStatus maximumHullDownStatus) {
		this.minimumHullDownStatus = minimumHullDownStatus;
		this.maximumHullDownStatus = maximumHullDownStatus;
	}
	
	
}
