package Artillery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

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
		
		public boolean smoke = false; 
		public boolean airBurst = false;
		public SmokeType smokeType;
		
		public PCAmmo pcAmmo;
		
		public Shell(ShellType shellType) {
		    switch (shellType) {
		            
		        case M60HE:
		            createM60HE();
		            break;
		        case M60ION:
		            createM60ION();
		            break;
		        case M60Smoke:
		            shellName = "Smoke";
		            smoke = true;
		            smokeType = SmokeType.Mortar60mm;
		            break;
		        case M60WP:
		            shellName = "WP";
		            smoke = true;
		            smokeType = SmokeType.Wp60mm;
		            break;
		        case M60WPIon:
		            shellName = "WP Ion";
		            smoke = true;
		            smokeType = SmokeType.Wp60mmIon;
		            break;
		            
		        case M81HE:
		            createM81HE();
		            break;
		        case M81ION:
		            createM81ION();
		            break;
		        case M81Smoke:
		            shellName = "Smoke";
		            smoke = true;
		            smokeType = SmokeType.Mortar81mm;
		            break;
		        case M81WP:
		            shellName = "WP";
		            smoke = true;
		            smokeType = SmokeType.Wp81mm;
		            break;
		        case M81WPION:
		            shellName = "WP Ion";
		            smoke = true;
		            smokeType = SmokeType.Wp81mmIon;
		            break;
		            
		        case M120HE:
		            createM120HE();
		            break;
		        case M120ION:
		            createM120ION();
		            break;
		        case M120Smoke:
		            shellName = "Smoke";
		            smoke = true;
		            smokeType = SmokeType.Mortar120mm;
		            break;
		        case M120WP:
		            shellName = "WP";
		            smoke = true;
		            smokeType = SmokeType.Wp120mm;
		            break;
		        case M120WPIon:
		            shellName = "WP Ion";
		            smoke = true;
		            smokeType = SmokeType.Wp120mmIon;
		            break;
		            
		        case AV7HEAT:
		            createAV7HEAT();
		            break;
		        case AV7Smoke:
		            shellName = "Smoke";
		            smoke = true;
		            smokeType = SmokeType.Howitzer155mm;
		            break;
		        case Shell155mmWP:
		            shellName = "WP";
		            smoke = true;
		            smokeType = SmokeType.Wp155mm;
		            break;
		        case Shell155mmWpIon:
		            shellName = "WP Ion";
		            smoke = true;
		            smokeType = SmokeType.Wp155mmIon;
		            break;
		       
		        case M107HE:
		            createM107HE();
		            break;
		        case M107Smoke:
		            shellName = "Smoke";
		            smoke = true;
		            smokeType = SmokeType.Howitzer105mm;
		            break;
		       
		        default:
		            // Handle any other cases or errors
		            break;
		    }
		}
		
		private void createM107HE() {
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
		}

		private void createAV7HEAT() {
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

		private void createM120HE() {
		    shellName = "120mm HE";
		    pen.add(30);
		    pen.add(29);
		    pen.add(29);
		    pen.add(28);
		    pen.add(27);
		    pen.add(26);
		    pen.add(25);

		    dc.add(9);
		    dc.add(9);
		    dc.add(9);
		    dc.add(9);
		    dc.add(9);
		    dc.add(9);
		    dc.add(8);

		    bshc.add("*14");
		    bshc.add("*3");
		    bshc.add("87");
		    bshc.add("38");
		    bshc.add("21");
		    bshc.add("13");
		    bshc.add("5");

		    bc.add(3000);
		    bc.add(530);
		    bc.add(95);
		    bc.add(43);
		    bc.add(25);
		    bc.add(17);
		    bc.add(13);
		}

		private void createM120ION() {
		    shellName = "120mm ION";
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);

		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);

		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");

		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);

		    ionDamage.add(12000);
		    ionDamage.add(1000);
		    ionDamage.add(283);
		    ionDamage.add(154);
		    ionDamage.add(102);
		    ionDamage.add(55);
		    ionDamage.add(25);
		}

		private void createM81HE() {
		    shellName = "81mm HE";
		    pen.add(9);
		    pen.add(9);
		    pen.add(8);
		    pen.add(8);
		    pen.add(7);
		    pen.add(7);
		    pen.add(6);

		    dc.add(6);
		    dc.add(6);
		    dc.add(6);
		    dc.add(6);
		    dc.add(6);
		    dc.add(5);
		    dc.add(5);

		    bshc.add("*10");
		    bshc.add("*2");
		    bshc.add("36");
		    bshc.add("16");
		    bshc.add("9");
		    bshc.add("5");
		    bshc.add("4");

		    bc.add(3000);
		    bc.add(530);
		    bc.add(95);
		    bc.add(43);
		    bc.add(25);
		    bc.add(17);
		    bc.add(13);
		}

		private void createM81ION() {
		    shellName = "81mm ION";
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);

		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);

		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");

		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);

		    ionDamage.add(9000);
		    ionDamage.add(800);
		    ionDamage.add(183);
		    ionDamage.add(54);
		    ionDamage.add(23);
		    ionDamage.add(15);
		    ionDamage.add(5);
		}

		private void createM60HE() {
		    shellName = "60mm HE";
		    pen.add(2);
		    pen.add(2);
		    pen.add(2);
		    pen.add(2);
		    pen.add(1);
		    pen.add(1);
		    pen.add(1);

		    dc.add(2);
		    dc.add(2);
		    dc.add(2);
		    dc.add(2);
		    dc.add(2);
		    dc.add(2);
		    dc.add(2);

		    bshc.add("*1");
		    bshc.add("27");
		    bshc.add("6");
		    bshc.add("3");
		    bshc.add("1");
		    bshc.add("1");
		    bshc.add("0");

		    bc.add(739);
		    bc.add(155);
		    bc.add(20);
		    bc.add(9);
		    bc.add(5);
		    bc.add(3);
		    bc.add(1);
		}

		private void createM60ION() {
		    shellName = "60mm ION";
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);
		    pen.add(0);

		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);
		    dc.add(1);

		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");
		    bshc.add("0");

		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);
		    bc.add(0);

		    ionDamage.add(6000);
		    ionDamage.add(600);
		    ionDamage.add(123);
		    ionDamage.add(34);
		    ionDamage.add(13);
		    ionDamage.add(11);
		    ionDamage.add(3);
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
