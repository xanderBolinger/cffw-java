package Artillery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;


public class Artillery implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6081281628669844714L;
	public String batteryName; 
	public String batteryDisplayName; 
	public BatteryType batteryType; 
	
	public boolean artilleryComputer = false; 
	
	public int maximumRangeInTwoYardHexes; 
	public int minimumRangeInTwoYardHexes;
	
	public int rangingAccuracy; 
	public int adjustmenAccuracy; 
	public int shellAccuracy;
	
	public int shortTermROF; 
	public int sustainedROF; 
	public int crew; 
	
	public int batteryTargetSize;
	public int currentCrew; 
	
	public int deployTime; 
	
	public int crewQuality; 
	
	public int shotProgress = 0;
	public int actionsSpentShooting = 0; 
	public boolean shotsTaken; 
	
	public BatteryStatus batteryStatus = BatteryStatus.WAITING;
	
	public ArrayList<Shell> shells = new ArrayList<Shell>();  
	
	public LinkedList<Integer> queue = new LinkedList<Integer>(); 
	
	public class Shot implements Serializable {
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 5406212273243885226L;
		public Artillery battery; 
		public int actionsToImpact; 
		public int actionsSpentInAir; 
		public Shell shell; 
		
		public Shot(Artillery battery, int shellIndex) {
			this.battery = battery;
			shell = battery.shells.get(shellIndex);
			actionsToImpact = 1; 
			actionsSpentInAir = 0;
		}
		
	}
	
	public enum BatteryStatus implements Serializable {
		WAITING, FIRING
	}
	
	public enum BatteryType implements Serializable {
		M107,AV7
	}
	
	public enum ShellType implements Serializable {
		M107HE,AV7HEAT
	}
	
	public Artillery(BatteryType batteryType, int crewQuality) {
		
		this.crewQuality = crewQuality;
		
		if(BatteryType.M107 == batteryType) {
			batteryName = "M107";
			this.batteryType = batteryType; 
			maximumRangeInTwoYardHexes = 2820; 
			minimumRangeInTwoYardHexes = 270; 
			
			rangingAccuracy = 100; 
			adjustmenAccuracy = 90; 
			shellAccuracy = 85; 
			
			shortTermROF = 4; 
			sustainedROF = 10; 
			crew = 6; 
			
			batteryTargetSize = 6; 
			
			currentCrew = 0; 
			
			deployTime = 60; 
			
			shells.add(new Shell(ShellType.M107HE));
		} else if(BatteryType.AV7 == batteryType) {
			batteryName = "AV7";
			this.batteryType = batteryType; 
			maximumRangeInTwoYardHexes = 2820; 
			minimumRangeInTwoYardHexes = 270; 
			
			rangingAccuracy = 85; 
			adjustmenAccuracy = 65; 
			shellAccuracy = 50; 
			
			shortTermROF = 1; 
			sustainedROF = 1; 
			crew = 1; 
			
			batteryTargetSize = 6; 
			
			currentCrew = 0; 
			
			deployTime = 60; 
			
			shells.add(new Shell(ShellType.AV7HEAT));
		}
		
	}
	
	public class Shell implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 4576626989046586256L;
		public ArrayList<Integer> pen = new ArrayList<Integer>();
		public ArrayList<Integer> dc = new ArrayList<Integer>();
		public ArrayList<String> bshc = new ArrayList<String>();
		public ArrayList<Integer> bc = new ArrayList<Integer>();
		public ArrayList<Integer> ionDamage = new ArrayList<Integer>();
		public String shellName; 
		
		public Shell(ShellType shellType) {
			
			if(shellType == ShellType.M107HE) {
				shellName = "107mm HE";
				pen.add(20);
				pen.add(20);
				pen.add(19);
				pen.add(19);
				pen.add(18);
				pen.add(17);
				pen.add(16);

				
				dc.add(7);
				dc.add(7);
				dc.add(7);
				dc.add(7);
				dc.add(7);
				dc.add(7);
				dc.add(6);

				
				bshc.add("*10");
				bshc.add("*4");
				bshc.add("*3");
				bshc.add("*1");
				bshc.add("93");
				bshc.add("65");
				bshc.add("22");
				
				bc.add(12000);
				bc.add(1200);
				bc.add(283);
				bc.add(103);
				bc.add(58);
				bc.add(39);
				bc.add(0);
				
			} else if(shellType == ShellType.AV7HEAT) {
				shellName = "Explosive Charge(HEAT)";
				pen.add(112);
				pen.add(112);
				pen.add(112);
				pen.add(112);
				pen.add(112);
				pen.add(112);
				pen.add(112);

				
				dc.add(10);
				dc.add(10);
				dc.add(10);
				dc.add(10);
				dc.add(10);
				dc.add(8);
				dc.add(6);

				
				bshc.add("*14");
				bshc.add("*3");
				bshc.add("86");
				bshc.add("38");
				bshc.add("21");
				bshc.add("14");
				bshc.add("3");
				
				bc.add(29000);
				bc.add(3000);
				bc.add(495);
				bc.add(149);
				bc.add(82);
				bc.add(53);
				bc.add(15);
				
			}
			
		}
		
		
	}
	
	
	public void takeShot(FireMission fireMission) {
		//System.out.println("Take Shot");
		double shotsPerAction; 
		int secondsPerShot; 
		
		if(shotsTaken)
			secondsPerShot = sustainedROF / 2; 
		else 
			secondsPerShot = shortTermROF / 2; 
		
		if(secondsPerShot <= 0)
			secondsPerShot = 1; 
		
		shotsPerAction = (20 + (20 * shotProgress)) / secondsPerShot; 
		
		System.out.println("Shots per action: "+shotsPerAction+", seconds per shot: "+secondsPerShot);
		
		if(Math.round(shotsPerAction) < 1) {
			shotProgress++; 
		} else {
			System.out.println("take shot que size: "+queue.size());
			if(queue.size() > 0) {
				int size = queue.size();
				for(int i = 1; i <= size; i++) {
					
					if(i > shotsPerAction) 
						break; 
					System.out.println("Add airborn shots");
					fireMission.airborneShots.add(new Shot(this, queue.pop()));					
				}
				
			}
		    //System.out.println("Queue Size: "+queue.size());
		}
		
		actionsSpentShooting++; 
		if(actionsSpentShooting >= 3)
			shotsTaken = true; 
		
	}

	@Override
	public String toString() {
		return batteryDisplayName + ", Crew SL: "+crewQuality+", Crew Count: "+currentCrew+" Battery Status: "+batteryStatus;
	}
	
	/*public boolean busy() {
		if(queue.size() > 0)
			return true;
		else 
			return false; 
	}*/
	
}
