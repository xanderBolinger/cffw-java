package Trooper.Factions;

import javax.swing.JComboBox;

import Trooper.Trooper;
import Trooper.Factions.FactionManager.FactionType;

public abstract class Faction {

	public final FactionType factionType;
	public final String factionName;
	
	public Faction(FactionType type, String name) {
		factionType = type;
		factionName = name;
	}
	
	public abstract void individualInput(JComboBox combo);
	public abstract void setTrooper(Trooper trooper, String trooperType) throws Exception;
}
