package Artillery;

import java.io.Serializable;

public class ShotOrder implements Serializable {

	public int shots;
	public int shellIndex;
	
	public ShotOrder(int shots, int shellIndex) {
		this.shots = shots; 
		this.shellIndex = shellIndex;
	}
	
}