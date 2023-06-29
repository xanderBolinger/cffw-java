package Vehicle.Data;

import java.io.Serializable;
import java.util.ArrayList;

import Trooper.Trooper;

public class CrewMember implements Serializable {

	public Trooper crewMember;

	public Action currentAction;
	
	public enum Action {
		SPOT,AIM,FIRE,DRIVE
	}
	
	public CrewMember(Trooper crewMember) {
		this.crewMember = crewMember;
		currentAction = Action.SPOT;
	}
	
}
