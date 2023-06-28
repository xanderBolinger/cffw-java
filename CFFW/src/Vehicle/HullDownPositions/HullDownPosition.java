package Vehicle.HullDownPositions;

public class HullDownPosition {

	public enum HullDownStatus {
		HIDDEN,TURRET_DOWN,HULL_DOWN,PARTIAL_HULL_DOWN
	}

	public HullDownStatus minimumHullDownStatus;
	public HullDownStatus maximumHullDownStatus;
	public int capacity;
	
	public HullDownPosition(HullDownStatus minimumHullDownStatus, HullDownStatus maximumHullDownStatus) {
		this.minimumHullDownStatus = minimumHullDownStatus;
		this.maximumHullDownStatus = maximumHullDownStatus;
	}
	
	
}
