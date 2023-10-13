package Items;

import java.util.ArrayList;

import Conflict.GameWindow;
import Hexes.Hex;
import Injuries.Injuries;
import Trooper.Trooper;

public class FlameThrower {

	public int contact;
	public int inHex;
	public int adjacent;
	
	public FlameThrowerType flamerType;
	
	
	public enum FlameThrowerType {
		AstartesFlamer
	}
	
	public FlameThrower(FlameThrowerType type) {
		
		this.flamerType = type;
		
		switch(type) {
			case AstartesFlamer:
				contact = 7000;
				inHex = 250;
				adjacent = 75;
				break;
			default:
				System.err.println("Flamer type not implemented for type: "+type);
		}
		
	}
	
}
