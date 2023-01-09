package Ship;

import java.util.ArrayList;

import Mechanics.DamageAllocation.HitSide;

public class HitTable {

	public ArrayList<HitLocation> nose;
	public ArrayList<HitLocation> aft;
	public ArrayList<HitLocation> port;
	public ArrayList<HitLocation> starboard;
	public ArrayList<HitLocation> top;
	public ArrayList<HitLocation> bottom;
	public ArrayList<HitLocation> core;

	public int noseSkinArmor;
	public int aftSkinArmor;
	public int portSkinArmor;
	public int starboardSkinArmor;
	public int topSkinArmor;
	public int bottomSkinArmor;

	public int noseAftDepth;
	public int portStarboardDepth;
	public int topBottomDepth;
	
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

	public int getSkinArmor(HitSide hitSide) {
		switch (hitSide) {

		case NOSE:
			return noseSkinArmor;
		case AFT:
			return aftSkinArmor;
		case PORT:
			return portSkinArmor;
		case STARBOARD:
			return starboardSkinArmor;
		case TOP:
			return topSkinArmor;
		case BOTTOM:
			return bottomSkinArmor;
		default:
			return 0;

		}
	}

}
