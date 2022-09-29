import java.io.Serializable;
import java.util.*;

public class Skill implements Serializable {
	public String name; 
	public int rank; 
	public int value; 
	public int attr1;
	public int attr2;
	public String baseAttribute; 
	public String supportingAttribute;
	public int trainingValue;
	public boolean supported; 
	public int increasedValue;
	public String type;
	
	public Skill() {
		
	}
	
	public Skill(String name, int rank, int value, int attr1, int attr2, String baseAttribute, String supportingAttribute, boolean supported, int trainingValue, int increasedValue, String type) {
		this.name = name; 
		this.rank = rank; 
		this.value = value;
		this.attr1 = attr1; 
		this.attr2 = attr2;
		this.baseAttribute = baseAttribute; 
		this.supportingAttribute = supportingAttribute; 
		this.supported = supported;
		this.trainingValue = trainingValue;
		this.increasedValue = increasedValue;
		this.type = type;
		calculateSkillValue();
	}
	
	public String returnSkill() {
		return name+"("+baseAttribute+"/"+supportingAttribute+"): "+value+"%"+"("+rank+")["+increasedValue+"]{+"+trainingValue+"}";
		
	}
	
	// Calculate skill value 
	public void calculateSkillValue() {
		attr1 = attr1; 
		attr2 = attr2;
		
		value = 0; 
		value = attr1;
		value += Integer.parseInt(Integer.toString(attr2).substring(0, 1));
		
		
		
		value += increasedValue;
		
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
				roll -= 1; 
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
		-	Unsupported: 1d6 – 2, minimum 1 
		Expert Skills 
		-	Supported: 1d6 – 2, minimum 1 
		-	Unsupported: 2d6 lowest */
		advancement += trainingValue;
		value += advancement; 
		increasedValue += advancement;
		rank++;
		
	}
	
	
}
