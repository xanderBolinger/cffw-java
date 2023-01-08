package Ship;

import java.util.ArrayList;

public class WeaponColumn {

	public ArrayList<Integer> damage; 
	public int range;
	
	public WeaponColumn(int range, ArrayList<Integer> damage) {
		this.range = range; 
		this.damage = damage;
	}
	
}
