package Trooper;

import java.io.Serializable;
import java.util.Random;

public class Skill implements Serializable {
	public String name; 
	public int rank; 
	public int value; 
	public String baseAttribute; 
	public String supportingAttribute;
	public int trainingValue;
	public boolean supported; 
	public int increasedValue;
	public String type;
	
	public Skill() {
		
	}
	
	public Skill(String name, int rank, int value, String baseAttribute, String supportingAttribute, boolean supported, int trainingValue, int increasedValue, String type) {
		this.name = name; 
		this.rank = rank; 
		this.value = value;
		this.baseAttribute = baseAttribute; 
		this.supportingAttribute = supportingAttribute; 
		this.supported = supported;
		this.trainingValue = trainingValue;
		this.increasedValue = increasedValue;
		this.type = type;
	}
	
	public Skill(String name, int rank, int value, int attr, int mod, String baseAttribute, String supportingAttribute, boolean supported, int trainingValue, int increasedValue, String type) {
		this.name = name; 
		this.rank = rank; 
		
		this.value = attr * 3 + ((mod*3/10)%10);
		if(name.equals("First Aid")) {
			System.out.println("First Aid");
			System.out.println("Attr: "+attr);
			System.out.println("Mod: "+mod);
			System.out.println("Value: "+this.value);
		}
		
		this.baseAttribute = baseAttribute; 
		this.supportingAttribute = supportingAttribute; 
		this.supported = supported;
		this.trainingValue = trainingValue;
		this.increasedValue = increasedValue;
		this.type = type;
	}
	
	public String returnSkill() {
		return name+"("+baseAttribute+"/"+supportingAttribute+"): "+value+"%"+"("+rank+")["+increasedValue+"]{+"+trainingValue+"}";
		
	}
	
	public void newTrainingValue(int newTv) {
		
		if(newTv > trainingValue) {
			int diff = newTv - trainingValue;
			increasedValue += diff * rank; 
			value += diff * rank; 
			trainingValue = newTv; 
		}
		
		
	}
	
	
	public void rankUpTo(int rank) {
		
		if(rank > this.rank) {
			
			rankUp(rank - this.rank);
			
		}
		
	}
	
	public void rankUp(int number) {
		for(int i = 0; i < number; i++) {
			rankUp();
		}
	}
	
	// Rank up 
	public void rankUp() {
		Random rand = new Random();
		int advancement;
		
		if(type.equals("Basic")) {
			
			if(supported) {
				int roll = rand.nextInt(6) + 1;
				int roll2 = rand.nextInt(6) + 1;
				
				if(roll > roll2) {
					advancement = roll;
				} else {
					advancement = roll2;
				}
				
			} else {
				advancement = rand.nextInt(6) + 1;
			}
			
		} else if (type.equals("Trained")) {
			
			if(supported) {
				advancement = rand.nextInt(6) + 1;
			} else {
				int roll = rand.nextInt(6) + 1;
				roll -= 2; 
				if(roll < 0) {
					advancement = 1; 
				} else {
					advancement = roll;
				}
			}
			
		} else {
			
			if(supported) {
				int roll = rand.nextInt(6) + 1;
				roll -= 2; 
				if(roll < 0) {
					advancement = 0; 
				} else {
					advancement = roll;
				}
			} else {
				int roll = rand.nextInt(6) + 1;
				int roll2 = rand.nextInt(6) + 1;
				
				if(roll < roll2) {
					advancement = roll;
				} else {
					advancement = roll2;
				}
			}
			
		}
		
		
		/*Basic Skills 
		-	Supported: 2d6 highest   
		-	Unsupported: 1d6 
		Advanced/Trained Skills 
		-	Supported: 1d6 
		-	Unsupported: 1d6 � 2, minimum 1 
		Expert Skills 
		-	Supported: 1d6 � 2, minimum 1 
		-	Unsupported: 2d6 lowest */
		advancement += trainingValue;
		value += advancement; 
		increasedValue += advancement;
		rank++;
		
	}
	
}
