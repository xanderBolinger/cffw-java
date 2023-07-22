package Melee;

import java.io.Serializable;
import java.util.ArrayList;

import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class MeleeCombatUnit implements Serializable {

	public MeleeResolve resolve;
	public Unit unit;
	public ArrayList<Trooper> individualsInMeleeCombat;
	public boolean inFormation;
	public int formationRanks;
	public boolean shieldWall;
	public boolean spearWall;
	
	public boolean routing;
	public boolean individualsRouting;
	
	public MeleeCombatUnit() {} // empty constructor for testing

	
	public boolean AttemptCharge() {
		return DiceRoller.roll(0, 99) < resolve.getResolve();
	}
	
}
