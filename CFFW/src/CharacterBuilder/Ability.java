package CharacterBuilder;
import java.io.Serializable;
import java.util.*;




public class Ability implements Serializable {
	public String name; 
	public String special; 
	public String mastery;
	public int rank;
	public ArrayList<String> skillSupport;
	public ArrayList<String> trainingValues;
		
	public Ability() {
		skillSupport = new ArrayList<String>();
		trainingValues = new ArrayList<String>();
	}
	
	public Ability(String name, String special, String mastery, int rank, ArrayList<String> skillSupport, ArrayList<String> trainingValues) {
		this.name = name; 
		this.special = special;
		this.mastery = mastery; 
		this.rank = rank; 
		this.skillSupport = skillSupport;
		this.trainingValues = trainingValues;
	}
	
	public String toString() {
		return "-"+name+" ("+rank+")";
	}
	
}
