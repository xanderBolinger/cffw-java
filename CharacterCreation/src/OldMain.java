import java.util.Scanner;

public class OldMain {

	public static int STR; 
	public static int INT; 
	public static int SOC; 
	public static int WIL; 
	public static int PER; 
	public static int HTL; 
	public static int AGL; 
	
	
	public static void main(String args[]) {
		
		
		
		// Prints skill results  
		getSkills();
		
	}

	
	public static void getSkills() {
		// Declare the object and initialize with 
        // predefined standard input object 
        Scanner sc = new Scanner(System.in); 
        
        
        System.out.println("Input STR:");
        STR = sc.nextInt();
        
        System.out.println("Input INT:");
        INT = sc.nextInt();
        
        System.out.println("Input SOC:");
        SOC = sc.nextInt();
        
        System.out.println("Input WIL:");
        WIL = sc.nextInt();
        
        System.out.println("Input PER:");
        PER = sc.nextInt();
        
        System.out.println("Input HTL:");
        HTL = sc.nextInt();
        
        System.out.println("Input AGL:");
        AGL = sc.nextInt();

        System.out.println("Basic");
        System.out.println(returnSkill("Ballance", "AGL", "WIL"));
        System.out.println(returnSkill("Climb", "STR", "AGL"));
        System.out.println(returnSkill("Composure", "WIL", "HTL"));
        System.out.println(returnSkill("Deception", "SOC", "INT"));
        System.out.println(returnSkill("Dodge", "AGL", "STR"));
        System.out.println(returnSkill("Endurance", "STR", "WIL"));
        System.out.println(returnSkill("Expression", "HTL", "PER"));
        System.out.println(returnSkill("Grapple", "STR", "AGL"));
        System.out.println(returnSkill("Hold", "STR", "AGL"));
        System.out.println(returnSkill("Intuition", "WIL", "PER"));
        System.out.println(returnSkill("Jump/Leap", "STR", "PER"));
        System.out.println(returnSkill("Lift/Pull", "STR", "INT"));
        System.out.println(returnSkill("Resist Pain", "HTL", "WIL"));
        System.out.println(returnSkill("Search", "PER", "INT"));
        System.out.println(returnSkill("Spot/Listen", "PER", "INT"));
        System.out.println(returnSkill("Speed", "AGL", "HTL"));
        System.out.println(returnSkill("Stealth", "AGL", "INT"));
        System.out.println(returnSkill("Camouflage", "INT", "per"));
        System.out.println("Trained");
        System.out.println(returnSkill("Bow", "AGL", "STR"));
        System.out.println(returnSkill("Calm Other", "SOC", "INT"));
        System.out.println(returnSkill("Diplomacy", "SOC", "INT"));
        System.out.println(returnSkill("Explosives", "INT", "PER"));
        System.out.println(returnSkill("Barter", "SOC", "INT"));
        System.out.println(returnSkill("Command", "INT", "SOC"));
        System.out.println(returnSkill("Tactics", "INT", "PER"));
        System.out.println(returnSkill("Det. Motives", "SOC", "INT"));
        System.out.println(returnSkill("Intimidate", "SOC", "INT"));
        System.out.println(returnSkill("Investigation", "INT", "PER"));
        System.out.println(returnSkill("Persuade", "SOC", "INT"));
        System.out.println(returnSkill("Digi. Systems", "INT", "PER"));
        System.out.println(returnSkill("Firearm Rifle", "AGL", "PER"));
        System.out.println(returnSkill("Firearm Heavy", "AGL", "PER"));
        System.out.println(returnSkill("Firearm Launcher", "AGL", "PER"));
        System.out.println(returnSkill("Firearm Pistol", "AGL", "PER"));
        System.out.println(returnSkill("Fighter", "STR", "WIL"));
        System.out.println(returnSkill("First Aid", "INT", "PER"));
        System.out.println(returnSkill("Navigation", "INT", "PER"));
        System.out.println(returnSkill("Swim", "STR", "HTL"));
        System.out.println(returnSkill("Throw", "AGL", "STR"));
        System.out.println("Expert");
        System.out.println(returnSkill("Advanced Medicine", "INT", "PER"));
        System.out.println(returnSkill("Craft/Construct/Engineer", "INT", "STR"));
        System.out.println(returnSkill("Pilot", "AGL", "PER"));
        System.out.println(returnSkill("Animal Handling", "SOC", "WIL"));
        System.out.println(returnSkill("Ride", "AGL", "STR"));
        System.out.println(returnSkill("Science", "INT", "PER"));
        System.out.println(returnSkill("Survival", "INT", "WIL"));
        System.out.println("Cordite Expansion");
        System.out.println(returnSkill("Clean Operations", "AGL", "PER"));
        System.out.println(returnSkill("Covert Movement", "AGL", "WIL"));
        System.out.println(returnSkill("Recoil Control", "STR", "WIL"));
        int fighter = calculateSkill("STR", "WIL");
        int composure = calculateSkill("WIL", "HTL");
        int TD = fighter / 2 + composure / 2; 
        System.out.println("Trigger Discipline(Fighter+Compusure)/2"+" "+TD+"%");
        System.out.println(returnSkill("Reload Drills", "AGL", "PER"));
        System.out.println(returnSkill("Silent Operations", "AGL", "PER"));
        System.out.println(returnSkill("AK Systems", "AGL", "STR"));
        System.out.println(returnSkill("Assualt Operations", "STR", "WIL"));
        System.out.println(returnSkill("Authority", "STR", "SOC"));
        System.out.println(returnSkill("Raw Power", "STR", "HTL"));
        System.out.println(returnSkill("AR Systems", "AGL", "PER"));
        System.out.println(returnSkill("Long Range Optics", "PER", "AGL"));
        System.out.println(returnSkill("Negotiations", "SOC", "INT"));
        System.out.println(returnSkill("Small Unit Tactics", "INT", "PER"));
		
	}
	
	public static String returnSkill(String skillName, String baseAttribute, String bonusAttribute) {
		

		int skillValue = calculateSkill(baseAttribute, bonusAttribute); 
		
		return skillName+"("+baseAttribute+"/"+bonusAttribute+")"+", "+skillValue+"%";
		
	}
	
	public static int calculateSkill(String baseAttribute, String bonusAttribute) {
		
		int attr1 = 0; 
		int attr2 = 0; 
		
		if(baseAttribute.equals("STR")) {
			attr1 = STR; 
			
		} else if (baseAttribute.equals("INT")) {
			attr1 = INT; 
			
		} else if (baseAttribute.equals("SOC")) {
			attr1 = SOC; 
			
		} else if (baseAttribute.equals("WIL")) {
			attr1 = WIL; 
			
		} else if (baseAttribute.equals("PER")) {
			attr1 = PER; 
			
		} else if (baseAttribute.equals("HTL")) {
			attr1 = HTL; 
			
		} else if (baseAttribute.equals("AGL")) {
			attr1 = AGL; 
		}
		
		if(bonusAttribute.equals("STR")) {
			attr2 = STR; 
			
		} else if (bonusAttribute.equals("INT")) {
			attr2 = INT; 
			
		} else if (bonusAttribute.equals("SOC")) {
			attr2 = SOC; 
			
		} else if (bonusAttribute.equals("WIL")) {
			attr2 = WIL; 
			
		} else if (bonusAttribute.equals("PER")) {
			attr2 = PER; 
			
		} else if (bonusAttribute.equals("HTL")) {
			attr2 = HTL; 
			
		} else if (bonusAttribute.equals("AGL")) {
			attr2 = AGL; 
		}
		
		//System.out.println("Attr1: "+attr1);
		//System.out.println("Attr2: "+attr2);
		
		return attr1 + attr2 / 10; 
	}
	
}
