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

	public HitTable(HitTable other) {
		this.nose = new ArrayList<>(other.nose);
		this.aft = new ArrayList<>(other.aft);
		this.port = new ArrayList<>(other.port);
		this.starboard = new ArrayList<>(other.starboard);
		this.top = new ArrayList<>(other.top);
		this.bottom = new ArrayList<>(other.bottom);
		this.core = new ArrayList<>(other.core);
		this.noseSkinArmor = other.noseSkinArmor;
		this.aftSkinArmor = other.aftSkinArmor;
		this.portSkinArmor = other.portSkinArmor;
		this.starboardSkinArmor = other.starboardSkinArmor;
		this.topSkinArmor = other.topSkinArmor;
		this.bottomSkinArmor = other.bottomSkinArmor;
		this.noseAftDepth = other.noseAftDepth;
		this.portStarboardDepth = other.portStarboardDepth;
		this.topBottomDepth = other.topBottomDepth;
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

	@Override
	public String toString() {

		String rslts = "";

		rslts += "Nose[" + noseSkinArmor + "]:\n";

		for (int i = 1; i <= nose.size(); i++) {
			rslts += " " + nose.get(i - 1).toString(i) + "\n";
		}

		rslts += "\nAft[" + aftSkinArmor + "]:\n";

		for (int i = 1; i <= aft.size(); i++) {
			rslts += " " + aft.get(i - 1).toString(i) + "\n";
		}

		rslts += "\nPort[" + portSkinArmor + "]:\n";

		for (int i = 1; i <= port.size(); i++) {
			rslts += " " + port.get(i - 1).toString(i) + "\n";
		}

		rslts += "\nStarboard[" + starboardSkinArmor + "]:\n";

		for (int i = 1; i <= starboard.size(); i++) {
			rslts += " " + starboard.get(i - 1).toString(i) + "\n";
		}

		rslts += "\nTop[" + topSkinArmor + "]:\n";

		for (int i = 1; i <= top.size(); i++) {
			rslts += " " + top.get(i - 1).toString(i) + "\n";
		}

		rslts += "\nBottom[" + bottomSkinArmor + "]:\n";

		for (int i = 1; i <= bottom.size(); i++) {
			rslts += " " + bottom.get(i - 1).toString(i) + "\n";
		}

		rslts += "\nCore:\n";

		for (int i = 1; i <= core.size(); i++) {
			rslts += " " + core.get(i - 1).toString(i) + "\n";
		}

		return rslts;
	}

}
