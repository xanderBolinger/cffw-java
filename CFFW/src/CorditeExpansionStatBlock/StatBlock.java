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
import FatigueSystem.FatigueSystem;
import CorditeExpansion.CorditeExpansionGame.Impulse;
import CorditeExpansionActions.AimAction;
import CorditeExpansionActions.CeAction;
import CorditeExpansionActions.MoveAction;
import CorditeExpansionActions.CeAction.ActionType;
import CorditeExpansionActions.DodgeAction;
import CorditeExpansionActions.FireAction;
import CorditeExpansionRangedCombat.CalledShots.ShotTarget;
import Items.Weapons;
import Trooper.Trooper;
import Trooper.Trooper.BaseSpeed;
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
	
	public enum ActingStatus {
		ACTING,PLANNING,NOTHING
	}
	public ActingStatus actingStatus = ActingStatus.ACTING; 
	public boolean hesitating = false; 
	public boolean inCover = false;
	
	
	public Chit chit; 
	
	public transient Trooper trooper;
	
	public MedicalStatBlock medicalStatBlock = new MedicalStatBlock();
	public SkillStatBlock skillStatBlock = new SkillStatBlock();
	public RangedStatBlock rangedStatBlock;
	
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
		if(trooper.maximumSpeed == null) {
			trooper.setCombatStats(trooper);
		}
		
		quickness = trooper.maximumSpeed.get(trooper);
		combatActions = trooper.combatActions;
		adaptabilityFactor = 1+Math.round(((trooper.getSkill("Fighter")/10)%10)/2);
		
		
		this.trooper = trooper;
		
		//System.out.println("Weapon Percent: "+weaponPercent+", Ergo: "+weapon.ceStats.baseErgonomics);
		
		chit = new Chit();
		chit.labeled = true; 
		chit.chitLabel = trooper.name;
	}
	
	public void moveTrooper(Cord cord) {
		this.cord = cord; 
		chit.xCord = cord.xCord;
		chit.yCord = cord.yCord;
		
		if(cord.facing != null)
			setFacing(cord.facing);
		
		if(moveSpeed != MoveSpeed.CRAWL && moveSpeed != MoveSpeed.STEP) {
			rangedStatBlock.stabalized = false;
			rangedStatBlock.aimTime = 0;
			rangedStatBlock.aimHexes.clear();
			rangedStatBlock.aimTarget = null;
		}
		
		
	}
	
	public void clearAim() {
		//System.out.println("Clear aim");
		rangedStatBlock.aimHexes.clear();
		rangedStatBlock.aimTarget = null; 
		rangedStatBlock.aimTime = 0;
	}
	
	public void setAimTarget(Trooper target) {
		rangedStatBlock.aimTarget = target;
		rangedStatBlock.aimTime = 0;
	}
	
	public int getAimTime() {
		return rangedStatBlock.aimTime;
	}
	
	public void aim() {
		//System.out.println("Aim");
		rangedStatBlock.aimTime++;
		if(rangedStatBlock.aimTime > rangedStatBlock.maxAim) {
			//System.out.println("Set Max Aim: "+rangedStatBlock.maxAim);
			rangedStatBlock.aimTime = rangedStatBlock.maxAim;
		}
	}
	
	public void spendCombatAction() {	
		if(hesitating) {
			doNothing();
			return;
		}
		
		//System.out.println("spend spend");
		activeAction.spendCombatAction();
		
		/*if(activeAction.getActionType() != ActionType.AIM && activeAction.getActionType() != ActionType.FIRE)
			clearAim();*/
		
		if(activeAction.completed()) {
			//System.out.println("Action Completed");
			activeAction = null;
		}
		
		spendCaPoint();
	}
	
	public void prepareCourseOfAction() {
		if(hesitating) {
			doNothing();
			return;
		}
		
		//System.out.println("prepare spend");
		
		for(CeAction action : coac) {
			if(!action.ready()) {
				action.spendCombatAction();
				break; 
			}
		}
		
		if(coac.get(0).ready() && activeAction == null) {
			//System.out.println("ready");
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
		
		if(CorditeExpansionWindow.actionList.getSelectedIndex() <= coac.size() || 
				CorditeExpansionWindow.actionList.getSelectedIndex() < 0 || 
				coac.get(CorditeExpansionWindow.actionList.getSelectedIndex()).getActionType() != ActionType.TURN) {
			for(CeAction action : coac) {
				if(action.getActionType() == ActionType.TURN)
					return action;
			}
		}
		
		if(CorditeExpansionWindow.actionList.getSelectedIndex() < 0 || CorditeExpansionWindow.actionList.getSelectedIndex() >= coac.size())
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
	
	public FireAction getFireAction() {
		
		// Active Aim Action 
		if(activeAction != null && activeAction.getActionType() == ActionType.FIRE) {
			return (FireAction) activeAction;
		}
		
		// Selected aim action 
		int selectedIndex = CorditeExpansionWindow.actionList.getSelectedIndex();
		
		if(selectedIndex < 0)
			return null;
		
		// If selected action not an aim action, search for one
		// Otherwise, return selected aim action
		if(coac.get(CorditeExpansionWindow.actionList.getSelectedIndex()).getActionType() != ActionType.FIRE) {
			
			for(CeAction action : coac) {
				if(action.getActionType() == ActionType.FIRE)
					return (FireAction) action;
			}
			
		} else {
			return (FireAction) coac.get(CorditeExpansionWindow.actionList.getSelectedIndex());
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
		if(rangedStatBlock.aiming)
			rangedStatBlock.aiming = false;
		else
			rangedStatBlock.aiming = true;
	}
	
	public void toggleFullauto() {
		if(rangedStatBlock.fullAuto)
			rangedStatBlock.fullAuto = false;
		else
			rangedStatBlock.fullAuto = true;
	}
	
	public void dodge() {
		activeAction = new DodgeAction(trooper);
	}
	
	public int getDistance(Cord cord) {
		return GameWindow.dist(chit.xCord, chit.yCord, cord.xCord, cord.yCord);
	}
	
	public int getDistance(StatBlock targetStatBlock) {
		return GameWindow.dist(chit.xCord, chit.yCord, targetStatBlock.chit.xCord, targetStatBlock.chit.yCord);
	}
	
	public void cycleShotTarget() {
		
		switch(rangedStatBlock.shotTarget) {
			case NONE:
				rangedStatBlock.shotTarget = ShotTarget.HEAD;
				break;
			case HEAD:
				rangedStatBlock.shotTarget = ShotTarget.BODY;
				break;
			case BODY: 
				rangedStatBlock.shotTarget = ShotTarget.LEGS;
				break;
			case LEGS:
				rangedStatBlock.shotTarget = ShotTarget.NONE;
				break;
		}
		
	}
	
	public void cycleActingStatu() {
		
		switch(actingStatus) {
			case ACTING:
				actingStatus = ActingStatus.PLANNING;
				break;
			case PLANNING:
				actingStatus = ActingStatus.NOTHING;
				break;
			case NOTHING: 
				actingStatus =ActingStatus.ACTING;
				break;
		}
		
	}
	
	public void toggleSpendSuccess() {
		skillStatBlock.spendSuccess = !skillStatBlock.spendSuccess;
	}
	
	public void cycleSpendSuccessNumber() {
		
		switch(skillStatBlock.spendSuccessNumber) {
			case 1:
				skillStatBlock.spendSuccessNumber = 2; 
				break;
			case 2:
				skillStatBlock.spendSuccessNumber = 3; 
				break;
			case 3:
				skillStatBlock.spendSuccessNumber = 4; 
				break;
			case 4:
				skillStatBlock.spendSuccessNumber = 5; 
				break;
			case 5:
				skillStatBlock.spendSuccessNumber = 1; 
				break;
		}
		
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
		results.add("Acting Status: "+actingStatus.toString());
		results.add("In Cover: "+inCover);
		results.add("Stabalized: "+rangedStatBlock.stabalized);
		
		String aimingFiring = "aiming";
		
		if(!rangedStatBlock.aiming)
			aimingFiring = "firing";
		
		results.add("aiming/firing: "+aimingFiring);
		results.add("Fullatuo: "+rangedStatBlock.fullAuto);
		results.add("Shot Target: "+rangedStatBlock.shotTarget.toString());
		
		results.add("Spend Success: "+skillStatBlock.spendSuccess);
		results.add("Spend Success Number: "+skillStatBlock.spendSuccessNumber);
		results.add("Suppresion Reaction: "+rangedStatBlock.suppression.getDisplayStatus());
		results.add("Suppression: "+rangedStatBlock.suppression.getSuppression());
		results.add("Blind Firing: "+rangedStatBlock.blindFiring);
		results.add("Looking Into Light: "+rangedStatBlock.lookingIntoLight);
		
		String magazineStatus = rangedStatBlock.weapon.ceStats.magazine != null ? (rangedStatBlock.weapon.ceStats.magazine.ammo.name 
				+ ", DP: " + rangedStatBlock.weapon.ceStats.magazine.ammo.depletionPoints) + ", ["+rangedStatBlock.magazines(trooper)+"]" : "["+rangedStatBlock.magazines(trooper)+"]";
		results.add("Magazine(s): "+ magazineStatus);
		
		results.add("Alive: "+medicalStatBlock.alive);
		results.add("Conscoius: "+medicalStatBlock.conscious());
		results.add("Physical Damage Total: "+medicalStatBlock.getPdTotal());
		results.add("Physical Damage From Blood Loss: "+medicalStatBlock.getBloodLossPd());
		
		return results; 
	}
		public int coacSize() {
			return coac.size();
		}

	public void setPrepared(int index) {
		coac.get(index).setPrepared();
		
		if(!acting())
			activeAction = coac.remove(index);
		
	}
}
