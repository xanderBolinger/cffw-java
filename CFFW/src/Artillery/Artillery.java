package Artillery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import Conflict.GameWindow;
import Conflict.SmokeStats.SmokeType;
import Items.PCAmmo;


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
			System.out.println("Shell Index: "+shellIndex);
			actionsToImpact = 1; 
			actionsSpentInAir = 0;
		}
		
	}
	
	public enum BatteryStatus implements Serializable {
		WAITING, FIRING
	}
	
	public enum BatteryType implements Serializable {
		M107,AV7,M81,M60,M120
	}
	
	public enum ShellType implements Serializable {
		M120HE,M120Smoke,M120ION,M120WP,M120WPIon,
		M81HE,M81ION,M81Smoke,M81WP,M81WPION,
		M60HE,M60Smoke,M60ION,M60WP,M60WPIon,
		M107HE,M107Smoke,
		AV7HEAT,AV7Smoke,Shell155mmWP,Shell155mmWpIon
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
			shells.add(new Shell(ShellType.M107Smoke));
		} else if(BatteryType.AV7 == batteryType) {
			batteryName = "AV7";
			this.batteryType = batteryType; 
			maximumRangeInTwoYardHexes = 2820; 
			minimumRangeInTwoYardHexes = 270; 
			artilleryComputer = true;
			
			rangingAccuracy = 66; 
			adjustmenAccuracy = 51; 
			shellAccuracy = 39; 
			
			shortTermROF = 1; 
			sustainedROF = 1; 
			crew = 1; 
			
			batteryTargetSize = 6; 
			
			currentCrew = 0; 
			
			deployTime = 60; 
			
			shells.add(new Shell(ShellType.AV7HEAT));
			shells.add(new Shell(ShellType.AV7Smoke));
			shells.add(new Shell(ShellType.Shell155mmWP));
			shells.add(new Shell(ShellType.Shell155mmWpIon));
		}  else if(BatteryType.M120  == batteryType) {
			batteryName = "M120";
			this.batteryType = batteryType; 
			maximumRangeInTwoYardHexes = 11370; 
			minimumRangeInTwoYardHexes = 0; 
			
			rangingAccuracy = 81; 
			adjustmenAccuracy = 63; 
			shellAccuracy = 48; 
			
			shortTermROF = 5; 
			sustainedROF = 15; 
			crew = 6; 
			
			batteryTargetSize = 7; 
			
			currentCrew = 0; 
			
			deployTime = 43; 
			
			shells.add(new Shell(ShellType.M120HE));
			shells.add(new Shell(ShellType.M120ION));
			shells.add(new Shell(ShellType.M120Smoke));
			shells.add(new Shell(ShellType.M120WP));
			shells.add(new Shell(ShellType.M120WPIon));
		} else if(BatteryType.M81  == batteryType) {
			batteryName = "M81";
			this.batteryType = batteryType; 
			maximumRangeInTwoYardHexes = 2180; 
			minimumRangeInTwoYardHexes = 25; 
			
			rangingAccuracy = 50; 
			adjustmenAccuracy = 42; 
			shellAccuracy = 40; 
			
			shortTermROF = 2; 
			sustainedROF = 6; 
			crew = 1; 
			
			batteryTargetSize = 6; 
			
			currentCrew = 0; 
			
			deployTime = 43; 
			
			shells.add(new Shell(ShellType.M81HE));
			shells.add(new Shell(ShellType.M81ION));
			shells.add(new Shell(ShellType.M81Smoke));
			shells.add(new Shell(ShellType.M81WP));
			shells.add(new Shell(ShellType.M81WPION));
		} else if(BatteryType.M60  == batteryType) {
			batteryName = "M60";
			this.batteryType = batteryType; 
			maximumRangeInTwoYardHexes = 990; 
			minimumRangeInTwoYardHexes = 25; 
			
			rangingAccuracy = 32; 
			adjustmenAccuracy = 29; 
			shellAccuracy = 27; 
			
			shortTermROF = 2; 
			sustainedROF = 4; 
			crew = 2; 
			
			batteryTargetSize = 6; 
			
			currentCrew = 0; 
			
			deployTime = 36; 
			
			shells.add(new Shell(ShellType.M60HE));
			shells.add(new Shell(ShellType.M60ION));
			shells.add(new Shell(ShellType.M60Smoke));
			shells.add(new Shell(ShellType.M60WP));
			shells.add(new Shell(ShellType.M60WPIon));
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
					var shot = new Shot(this, queue.pop());
					fireMission.airborneShots.add(shot);		
					recordFiredShot(shot);
				}
				
			}
		    //System.out.println("Queue Size: "+queue.size());
		}
		
		actionsSpentShooting++; 
		if(actionsSpentShooting >= 3)
			shotsTaken = true; 
		
	}
	
	private void recordFiredShot(Shot shot) {
		var firedShots = GameWindow.gameWindow.game.firedShots;
		
		boolean containsKey = firedShots.containsKey(shot.battery);
		
		if(containsKey) {
			var batMap = firedShots.get(shot.battery);
			var containsShellKey = batMap.containsKey(shot.shell.shellType);
			
			if(containsShellKey) {
				var count = batMap.remove(shot.shell.shellType);
				count++;
				batMap.put(shot.shell.shellType, count);
			} else {
				batMap.put(shot.shell.shellType, 1);
			}
			
		} else {
			var map = new HashMap<ShellType, Integer>();
			map.put(shot.shell.shellType, 1);
			firedShots.put(shot.battery, map);
		}
		
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
