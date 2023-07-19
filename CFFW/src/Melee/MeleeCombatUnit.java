package Melee;

import java.io.Serializable;
import java.util.ArrayList;

import Trooper.Trooper;
import Unit.Unit;

public class MeleeCombatUnit implements Serializable {

	public Unit unit;
	public ArrayList<Trooper> individualsInMeleeCombat;
	public boolean inFormation;
	public int formationRanks;
	public boolean shieldWall;
	public boolean spearWall;
	
	public MeleeCombatUnit() {} // empty constructor for testing
	
}
