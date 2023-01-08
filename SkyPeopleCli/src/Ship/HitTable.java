package Ship;

import java.util.ArrayList;

public class HitTable {

	public ArrayList<HitLocation> nose;
	public ArrayList<HitLocation> aft;
	public ArrayList<HitLocation> port;
	public ArrayList<HitLocation> starboard;
	public ArrayList<HitLocation> top;
	public ArrayList<HitLocation> bottom;
	public ArrayList<HitLocation> core;

	public HitTable(ArrayList<HitLocation> nose, ArrayList<HitLocation> aft, ArrayList<HitLocation> port,
			ArrayList<HitLocation> starboard, ArrayList<HitLocation> top, ArrayList<HitLocation> bottom,
			ArrayList<HitLocation> core) {
		this.nose = nose;
		this.aft = aft;
		this.port = port;
		this.starboard = starboard;
		this.top = top;
		this.bottom = bottom;
		this.core = core;
	}

}
