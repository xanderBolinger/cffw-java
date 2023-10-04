package Melee;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;

public class MeleeManager implements Serializable {

	public static MeleeManager meleeManager;
	
	public ArrayList<MeleeCombatUnit> meleeCombatUnits;
	public ArrayList<MeleeCombatUnit> unitsInCombat;
	public ArrayList<Bout> meleeCombatBouts;
	
	public MeleeManager() {
		meleeManager = this;
		unitsInCombat = new ArrayList<MeleeCombatUnit>();
		meleeCombatBouts = new ArrayList<Bout>();
		meleeCombatUnits = new ArrayList<MeleeCombatUnit>();
		
		if(GameWindow.gameWindow == null) 
			return;
		
		for(var unit : GameWindow.gameWindow.initiativeOrder) {
				
			meleeCombatUnits.add(new MeleeCombatUnit(unit));
			
		}
		
		
	}
	
	
	
	
}
