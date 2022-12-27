package CorditeExpansionActions;

import CeHexGrid.FloatingTextManager;
import CommandLineInterface.CommandLineInterface;
import CorditeExpansionStatBlock.StatBlock;

public class CustomAction implements CeAction {

	public String actionName;
	public int actionCost; 
	public int coacCost;
	public int spentAction = 0; 
	public int spentCoac = 0;
	public StatBlock statBlock;
	
	public CustomAction(StatBlock statBlock, String actionName, int actionCost, int coacCost) {
		this.statBlock = statBlock;
		this.actionName = actionName;
		this.actionCost = actionCost;
		this.coacCost = coacCost;
	}
	
	@Override
	public void spendCombatAction() {
		
		if(!ready()) {
			spentCoac++; 
			return;
		}
		
		spentAction++; 
		
		if(completed()) {
			FloatingTextManager.addFloatingText(statBlock.cord, actionName+", COMPLETED");
			CommandLineInterface.cli.print(actionName+": Finished");
		}
	}

	@Override
	public void setPrepared() {
		spentCoac = coacCost; 
	}

	@Override
	public boolean completed() {
		return spentAction >= actionCost;
	}

	@Override
	public boolean ready() {
		return spentCoac >= coacCost;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.CUSTOM;
	}
	
	@Override 
	public String toString() {
		return actionName + "Prepare/d("+ready()+"): ["+spentCoac+"/"+ coacCost+"], Spent: ["+spentAction+"/"+actionCost+"]";
	}

}
