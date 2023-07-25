package Melee;

import java.io.Serializable;
import java.util.ArrayList;

import HexGrid.HexDirectionUtility.HexDirection;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class MeleeCombatUnit implements Serializable {

	public HexDirection facing;
	public MeleeResolve resolve;
	public Unit unit;
	public ArrayList<Trooper> meleeCombatIndividuals;
	public ArrayList<ChargeData> activeCharges;
	public boolean inFormation;
	
	public int formationWidth;
	public int formationRanks;
	public boolean shieldWall;
	public boolean spearWall;
	
	public boolean routing;
	public boolean individualsRouting;
	
	public MeleeCombatUnit() {} // empty constructor for testing

	public MeleeCombatUnit(Unit unit) {
		resolve = new MeleeResolve();
		meleeCombatIndividuals = new ArrayList<Trooper>();
		activeCharges = new ArrayList<ChargeData>();
		facing = HexDirection.A;
	}
	
	
	public boolean AttemptCharge() {
		return DiceRoller.roll(0, 99) < resolve.getResolve();
	}
	
}
