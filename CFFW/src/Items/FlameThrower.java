package Items;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
import Hexes.Hex;
import Injuries.Injuries;
import Trooper.Trooper;

public class FlameThrower implements Serializable {

	public int contact;
	public int inHex;
	public int adjacent;
	
	public FlameThrowerType flamerType;
	
	public boolean ionCloud = false;
	
	public int defoliateChance;
	
	public enum FlameThrowerType implements Serializable {
		AstartesFlamer,AstartesFlamePistol,WP
	}
	
	public FlameThrower(FlameThrowerType type) {
		
		this.flamerType = type;
		
		switch(type) {
			case AstartesFlamer:
				contact = 7000;
				inHex = 250;
				adjacent = 75;
				defoliateChance = 80;
				break;
			case AstartesFlamePistol:
				contact = 5000;
				inHex = 125;
				adjacent = 50;
				defoliateChance = 50;
				break;
			case WP:
				break;
			default:
				System.err.println("Flamer type not implemented for type: "+type);
		}
		
	}
	
}
