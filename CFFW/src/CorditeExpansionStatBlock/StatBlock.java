package CorditeExpansionStatBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CeHexGrid.Chit;
import CeHexGrid.Chit.Facing;
import Conflict.GameWindow;
import CorditeExpansion.Cord;
import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansion.CorditeExpansionWindow;
import CorditeExpansionStatBlock.MedicalStatBlock.Status;
import CorditeExpansion.CorditeExpansionGame.Impulse;
import CorditeExpansionActions.AimAction;
import CorditeExpansionActions.CeAction;
import CorditeExpansionActions.MoveAction;
import CorditeExpansionActions.CeAction.ActionType;
import Items.Weapons;
import Trooper.Trooper;
import Trooper.Trooper.MaximumSpeed;
import UtilityClasses.ExcelUtility;

public class StatBlock {

	public double quickness; 
	public int adaptabilityFactor; 
	
	public int combatActions; 
	public int spentCombatActions = 0; 
	
	public MoveSpeed moveSpeed = MoveSpeed.STEP;
	public Stance stance = Stance.STANDING;
	
	public int totalMoved = 0; 

	public Cord cord = new Cord(0, 0);
	private CeAction activeAction;
	private ArrayList<CeAction> coac = new ArrayList<>();
	
	public boolean hesitating = false; 
	public boolean inCover = false;
	public boolean stabalized = false;
	
	public Weapons weapon;
	public int weaponPercent;
	
	public int aimTime = 0;
	public Trooper aimTarget; 
	public ArrayList<Cord> aimHexes = new ArrayList<>();
	
	public boolean aiming = true;
	public boolean fullAuto = false;
	
	public Chit chit; 
	
	public Trooper trooper;
	
	public MedicalStatBlock medicalStatBlock = new MedicalStatBlock();
	
	
	
	public enum Stance {
		PRONE(1),CROUCH(2),STANDING(3);
		
		public int value; 
		
		private static Map map = new HashMap<>();

	    private Stance(int value) {
	        this.value = value;
	    }

	    static {
	        for (Stance stanceType : Stance.values()) {
	            map.put(stanceType.value, stanceType);
	        }
	    }

	    public static Stance valueOf(int stanceType) {
	        return (Stance) map.get(stanceType);
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	public enum MoveSpeed {
		CRAWL,STEP,RUSH,SPRINT
	}
	
	public StatBlock(Trooper trooper) {
		quickness = trooper.maximumSpeed.get();
		combatActions = trooper.combatActions;
		adaptabilityFactor = 1+Math.round(((trooper.getSkill("Fighter")/10)%10)/2);
		weapon = new Weapons().findWeapon(trooper.wep);
		
		//System.out.println("str: "+trooper.weaponPercent);
		Matcher match = Pattern.compile("[0-9]+").matcher(trooper.weaponPercent);
		
		if(match.find()) {
			weaponPercent = Integer.parseInt(match.group(0));			
		}
		
		this.trooper = trooper;
		
		//System.out.println("Weapon Percent: "+weaponPercent+", Ergo: "+weapon.ceStats.baseErgonomics);
		
		chit = new Chit();
	}
	
	public void moveTrooper(Cord cord) {
		this.cord = cord; 
		chit.xCord = cord.xCord;
		chit.yCord = cord.yCord;
		
		if(cord.facing != null)
			setFacing(cord.facing);
		
		if(moveSpeed != MoveSpeed.CRAWL && moveSpeed != MoveSpeed.STEP) {
			stabalized = false;
			aimTime = 0;
			aimHexes.clear();
			aimTarget = null;
		}
		
		
	}
	
	public void spendCombatAction() {	
		if(hesitating) {
			spendCaPoint();
			return;
		}
		
		//System.out.println("spend spend");
		activeAction.spendCombatAction();
		
		if(activeAction.completed()) {
			//System.out.println("Action Completed");
			activeAction = null;
		}
		
		spendCaPoint();
	}
	
	public void prepareCourseOfAction() {
		if(hesitating) {
			spendCaPoint();
			return;
		}
		
		//System.out.println("prepare spend");
		coac.get(0).spendCombatAction();
		if(coac.get(0).ready()) {
			System.out.println("ready");
			addAction(coac.remove(0));
		}

		spendCaPoint();
	}
	
	public void doNothing() {
		spendCaPoint();
	}
	
	public void react() {
		activeAction = coac.remove(0);
		activeAction.setPrepared();
	}
	
	public int getDifficultyDice() {
		
		int diffDice = 0; 
		
		diffDice += medicalStatBlock.getDifficultyDice();		
		
		diffDice += trooper.physicalDamage / 100;
		
		return diffDice; 
		
	}
	
	public void hesitate() {
		hesitating = true;
	}
	
	public void spendCaPoint() {
		if(spentCombatActions >= combatActions) 
			spentCombatActions = 0; 
				
		spentCombatActions++;
		hesitating = false;
	}
	
	public int getActionTiming() {
		
		int actions = 0; 
		int impulse = CorditeExpansionGame.impulse.getValue();
		
		//System.out.println("impulse: "+impulse);
		//System.out.println("combatActions: "+combatActions);
		try {
			actions = (int) ExcelUtility.getNumberFromSheet(impulse, combatActions, "caperimpulse.xlsx", true, true);
			//System.out.println("Actions: "+ExcelUtility.getResultsTwoWayFixedValues(impulse, combatActions, "caperimpulse.xlsx", false, true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return actions; 
		
	}
	
	public void addAction(CeAction action) {
		//System.out.println("set action");
		activeAction = action;
	}
	
	public void addActionCoac(CeAction action) {
		coac.add(action);
	}
	
	public void cancelAction() {
		activeAction = null; 
	}
	
	public void removeCoac(int index) {
		coac.remove(index);
	}
	
	public Cord getPosition() {
		return cord; 
	}
	
	public CeAction getAction() {
		return activeAction;
	}
	
	
	public Facing getFacing() {
		return chit.facing;
	}
	
	public void setFacing(Facing facing) {
		chit.facing = facing;
	}
	
	public CeAction getTurnAction() {
		
		if(activeAction != null && activeAction.getActionType() == ActionType.TURN) {
			return activeAction;
		}
		
		if(coac.size() < 1)
			return null;
		
		if(CorditeExpansionWindow.actionList.getSelectedIndex() < 0 || coac.get(CorditeExpansionWindow.actionList.getSelectedIndex()).getActionType() != ActionType.TURN) {
			for(CeAction action : coac) {
				if(action.getActionType() == ActionType.TURN)
					return action;
			}
		}
		
		if(CorditeExpansionWindow.actionList.getSelectedIndex() < 0)
			return null;
		
		CeAction action = coac.get(CorditeExpansionWindow.actionList.getSelectedIndex()); 
		
		return action; 
		
		
	}
	
	public AimAction getAimAction() {
		
		// Active Aim Action 
		if(activeAction != null && activeAction.getActionType() == ActionType.AIM) {
			return (AimAction) activeAction;
		}
		
		// Selected aim action 
		int selectedIndex = CorditeExpansionWindow.actionList.getSelectedIndex();
		
		if(selectedIndex < 0)
			return null;
		
		// If selected action not an aim action, search for one
		// Otherwise, return selected aim action
		if(coac.get(CorditeExpansionWindow.actionList.getSelectedIndex()).getActionType() != ActionType.AIM) {
			
			for(CeAction action : coac) {
				if(action.getActionType() == ActionType.AIM)
					return (AimAction) action;
			}
			
		} else {
			return (AimAction) coac.get(CorditeExpansionWindow.actionList.getSelectedIndex());
		}
		
		return null; 
		
		
	}
	
	public ArrayList<CeAction> getCoac() {
		return coac;
	}
	
	public MoveAction getLastMoveAction() {
		
		MoveAction moveAction = null; 
		
		for(CeAction action : coac) {
			if(action.getActionType() != ActionType.MOVE)
				continue; 
			moveAction = (MoveAction) action; 
		}
		
		return moveAction;
		
	}
	
	public ArrayList<String> getCeStatList() {
		
		ArrayList<String> results = new ArrayList<>();
		
		String activeAction = "Currently Executing Action: None."; 
		
		if(acting()) {
			activeAction = "Currently Executing Action: "+ this.activeAction.toString();
		}
		
		results.add(activeAction);
		
		String spentCombatActions = "Spent Combat Actions: "+this.spentCombatActions;
		String combatActions = "Combat Actions: "+this.combatActions;
		
		results.add(spentCombatActions);
		results.add(combatActions);
		results.add("AF: "+adaptabilityFactor+", HESITATING: "+hesitating);
		results.add("Speed: "+moveSpeed.toString());
		results.add("Stance: "+stance.toString());
		results.add("In Cover: "+inCover);
		results.add("Stabalized: "+stabalized);
		
		String aimingFiring = "aiming";
		
		if(!aiming)
			aimingFiring = "firing";
		
		results.add("aiming/firing: "+aimingFiring);
		results.add("Fullatuo: "+fullAuto);
		
		results.add("Alive: "+medicalStatBlock.alive);
		results.add("Conscoius: "+medicalStatBlock.conscious());
		results.add("Physical Damage Total: "+medicalStatBlock.getPdTotal());
		results.add("Physical Damage From Blood Loss: "+medicalStatBlock.getBloodLossPd());
		
		
		
		return results; 
	}
	
	public boolean acting() {
		return activeAction != null ? true : false; 
	}
	
	public boolean preparing() {
		return coacSize() > 0 ? true : false; 
	}
	
	public void setCrawl() {
		moveSpeed = MoveSpeed.CRAWL;
	}
	
	public void setWalk() {
		moveSpeed = MoveSpeed.STEP;
	}
	
	public void setRush() {
		moveSpeed = MoveSpeed.RUSH;
	}
	
	public void setSprint() {
		moveSpeed = MoveSpeed.SPRINT;
	}
	
	public void toggleCover() {
		if(inCover)
			inCover = false; 
		else 
			inCover = true;
	}
	
	public void toggleAiming() {
		if(aiming)
			aiming = false;
		else
			aiming = true;
	}
	
	public void toggleFullauto() {
		if(fullAuto)
			fullAuto = false;
		else
			fullAuto = true;
	}
	
	public int getDistance(StatBlock targetStatBlock) {
		return GameWindow.dist(chit.xCord, chit.yCord, targetStatBlock.chit.xCord, targetStatBlock.chit.yCord);
	}
	
	// the below methods are for testing 
	public int coacSize() {
		return coac.size();
	}
	
}
