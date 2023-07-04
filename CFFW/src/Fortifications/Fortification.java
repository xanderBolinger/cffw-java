package Fortifications;

import java.io.Serializable;

public class Fortification implements Serializable {
	
	public enum FortificationType {
		TRENCHES
	}
	
	public int level;
	public FortificationType fortificationType;
	
	
	public Fortification(int level) {
		fortificationType = FortificationType.TRENCHES;
		this.level = level;
	}
	
	public Fortification(FortificationType fortificationType, int level) {
		this.fortificationType = fortificationType;
		this.level = level;
	}
	
	
	@Override
	public String toString() {
		return fortificationType +": " + level;
	}
	
}
