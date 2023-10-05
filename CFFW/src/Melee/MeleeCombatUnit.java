package Melee;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
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
	
	public boolean charged;
	
	public MeleeCombatUnit() {} // empty constructor for testing

	public MeleeCombatUnit(Unit unit) {
		meleeCombatIndividuals = new ArrayList<Trooper>();
		activeCharges = new ArrayList<ChargeData>();
		facing = HexDirection.A;
		this.unit = unit;
		resolve = new MeleeResolve(this);
	}
	
	public boolean AttemptCharge() {
		var roll = DiceRoller.roll(0, 99);
		var resolveVal = resolve.getResolve();
		if(GameWindow.gameWindow != null)
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(unit.callsign+" Charge Roll: "+roll+", Resolve: "+resolveVal);
		return roll <= resolveVal;
	}
	
	
}
