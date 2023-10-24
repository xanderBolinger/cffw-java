package Vehicle.Data;

import java.io.Serializable;
import java.util.ArrayList;

import Trooper.Trooper;

public class CrewMember implements Serializable {

	public Trooper crewMember;

	public CrewAction currentAction;
	
	public enum CrewAction {
		SPOT,AIM,FIRE,DRIVE
	}
	
	public CrewMember(Trooper crewMember) {
		this.crewMember = crewMember;
		currentAction = CrewAction.SPOT;
	}
	
}
