package Melee;

import java.io.Serializable;
import java.util.ArrayList;

public class MeleeManager implements Serializable {

	public static ArrayList<MeleeCombatUnit> unitsInCombat;
	public static ArrayList<Bout> meleeCombatBouts;
	
	public MeleeManager() {
		unitsInCombat = new ArrayList<MeleeCombatUnit>();
		meleeCombatBouts = new ArrayList<Bout>();
	}
	
	
}
