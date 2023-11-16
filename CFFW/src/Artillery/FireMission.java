package Artillery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Artillery.Artillery.BatteryStatus;
import Artillery.Artillery.Shot;
import Conflict.GameWindow;
import Conflict.InjuryLog;
import CorditeExpansion.Cord;
import Injuries.Explosion;
import Injuries.ResolveHits;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class FireMission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1197164158898967950L;

	public String fireMissionDisplayName;
	
	public int spotterSkill; 
	public int actionsToPlotted; 
	public int timeSpentPlotting; 
	
	public int actionsToFire;
	public int timeSpentPrepingFire;
	public int fireMissionRadius = 0;
	public boolean inProgress = false; 
	
	public FireMissionStatus fireMissionStatus; 
	
	public enum FireMissionStatus implements Serializable {
		WAITING, PLOTTING, RANGING, ADJUSTING, FIREFOREFFECT
	}
	
	public int targetX; 
	public int targetY;
	public int plottedX; 
	public int plottedY; 
	
	public ArrayList<Artillery> batteries; 
	public Trooper spotter; 
	public boolean LOSToImpact; 
	public boolean LOSToTarget; 
	
	public int crewQuality;
	public ArrayList<Integer> calculatedTargetPositionError = new ArrayList<Integer>(); 
	public ArrayList<Integer> spottedPositionError = new ArrayList<Integer>();  
	public ArrayList<Integer> plotTime = new ArrayList<Integer>();  
	public ArrayList<ShotOrder> shotOrders = new ArrayList<ShotOrder>();
	
	public int fireMissionSpeed; 
	public int randomFireMissionSpeed; 
	public boolean companyLevelSupport;
	public boolean platoonLevelSupport = false;
	
	public List<Shot> airborneShots = new ArrayList<Shot>();
	public transient GameWindow window;
	
	public FireMission(Trooper spotter, ArrayList<Artillery> batteries, boolean LOSToTarget, int targetX, int targetY, boolean companyLevelSupport, GameWindow window) {
		this.batteries = batteries;
		this.spotter = spotter; 
		this.LOSToTarget = LOSToTarget;
		this.targetX = targetX;
		this.targetY = targetY;
		this.companyLevelSupport = companyLevelSupport;
		
		fireMissionStatus = FireMissionStatus.WAITING;
		
		calculatedTargetPositionError.add(900);
		calculatedTargetPositionError.add(600);
		calculatedTargetPositionError.add(400);
		calculatedTargetPositionError.add(300);
		calculatedTargetPositionError.add(240);
		calculatedTargetPositionError.add(200);
		calculatedTargetPositionError.add(190);
		calculatedTargetPositionError.add(180);
		calculatedTargetPositionError.add(170);
		
		spottedPositionError.add(80);
		spottedPositionError.add(50);
		spottedPositionError.add(35);
		spottedPositionError.add(25);
		spottedPositionError.add(20);
		spottedPositionError.add(17);
		spottedPositionError.add(16);
		spottedPositionError.add(15);
		spottedPositionError.add(14);
		
		plotTime.add(900);
		plotTime.add(600);
		plotTime.add(400);
		plotTime.add(300);
		plotTime.add(240);
		plotTime.add(200);
		plotTime.add(190);
		plotTime.add(180);
		plotTime.add(170);
		
		
		
		setCrewQuality();
		
		randomFireMissionSpeed = new Random().nextInt(10);
		setFireMissionSpeed();
		System.out.println("Fire Mission Speed:"+fireMissionSpeed+", Random Fire Mission Speed: "+randomFireMissionSpeed+", Crew Quality: "+crewQuality);
		
		if(spotter != null)
			spotterSkill = ((spotter.getSkill("Navigation") / 5) + ((spotter.per * 3) / 5)) / 2;
	}
	
	public void setFireMissionSpeed() {
		fireMissionSpeed = randomFireMissionSpeed + crewQuality;
		// Computer bonus 
		if(artilleryComputer()) {
			fireMissionSpeed += 4; 
		}
	}
	
	public void setCrewQuality() {
		crewQuality = 0; 
		
		if(batteries != null) {
			
			for(Artillery battery : batteries) {
				crewQuality+=battery.crewQuality;
			}
			
			if(batteries.size() != 0)
				crewQuality /= batteries.size();
			
		}
	}
	
	public void setPlotTime() {
		
		if(spotterSkill > 8) {
			actionsToPlotted = plotTime.get(8) - ((8 - spotterSkill) * 10);
		} else {
			actionsToPlotted = plotTime.get(spotterSkill);
		}
		
		if(actionsToPlotted < 0)
			actionsToPlotted = 1; 
		else {
			actionsToPlotted /= spotter.combatActions; 
			actionsToPlotted /= 10; 
		}
		
		fireMissionStatus = FireMissionStatus.PLOTTING;
	}
	
	public void rangeShot(int shots, int shellIndex) {
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Range Shot");
		//System.out.println("Range Shot");
		fireMissionStatus = FireMissionStatus.RANGING;
		
		if(plottedX < 1 && plottedY < 1) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" Not Plotted");
			//System.out.println("Not Plotted");
			return; 
			
		} else {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" Already Plotted, Ordering Shot");
			//System.out.println("Already Plotted, Ordering Shot");
		}
		
		orderShot(shots, shellIndex);
	}
	
	public void adjustmentShot(int xAdjustment, int yAdjustment, int shots, int shellIndex) {
		
		fireMissionStatus = FireMissionStatus.ADJUSTING;
		
		plottedX += xAdjustment; 
		plottedY += yAdjustment;
		orderShot(shots, shellIndex);
	}
	
	public void fireForEffect(int shots, int shellIndex) {
		fireMissionStatus = FireMissionStatus.FIREFOREFFECT;
		
		orderShot(shots, shellIndex);
	}
	
	public void plotShot() {
		int direction = returnScatterDirection();
		int scatterDistance = 0; 

		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision Plotted: "+fireMissionDisplayName+" Spotter Skill: "+spotterSkill);
		//System.out.println("Fire Mision: "+fireMissionDisplayName+" Spotter Skill: "+spotterSkill);
		
		if(spotterSkill > 8) {
			if(LOSToTarget) {
				scatterDistance = spottedPositionError.get(8) - ((8 - spotterSkill) * 10);
				//window.conflictLog.addNewLine("Fire Mision: "+fireMissionDisplayName+" Scatter Distance Spotted Position Error: "+scatterDistance+" yards");
				//System.out.println("Fire Mision: "+fireMissionDisplayName+" Scatter Distance Spotted Position Error: "+scatterDistance+" yards");
			}
			else {
				scatterDistance = calculatedTargetPositionError.get(8) - ((8 - spotterSkill) * 10);
				//window.conflictLog.addNewLine("Fire Mision: "+fireMissionDisplayName+" Scatter Distance Calculated Position Error: "+scatterDistance+" yards");
				//System.out.println("Fire Mision: "+fireMissionDisplayName+" Scatter Distance Calculated Position Error: "+scatterDistance+" yards");
			}
		} else {
			if(LOSToTarget) {
				scatterDistance = spottedPositionError.get(spotterSkill);
				//window.conflictLog.addNewLine("Fire Mision: "+fireMissionDisplayName+" Scatter Distance Spotted Position Error: "+scatterDistance+" yards");
				//System.out.println("Fire Mision: "+fireMissionDisplayName+" Scatter Distance Spotted Position Error: "+scatterDistance+" yards");
			}
			else {
				scatterDistance = calculatedTargetPositionError.get(spotterSkill);
				//window.conflictLog.addNewLine("Fire Mision: "+fireMissionDisplayName+" Scatter Distance Calculated Position Error: "+scatterDistance+" yards");
				//System.out.println("Fire Mision: "+fireMissionDisplayName+" Scatter Distance Calculated Position Error: "+scatterDistance+" yards");
			}
		}
		
		scatterDistance /= 10; 
		scatterDistance *= scatterModifier();
		
		ArrayList<Integer> scattedCords = returnScatteredHex(targetX, targetY, scatterDistance, direction);
		plottedX = scattedCords.get(0);
		plottedY = scattedCords.get(1);
		//System.out.println("Plotted Hex X: "+plottedX+", Y: "+plottedY);
	}
	
	public void orderShot(int shots, int shellIndex) {
		if(actionsToFire < 1) {
		
			// Gets number of 2 second phases required to fire 
			actionsToFire = getFireMissionTime(); 
			actionsToFire /= 10; 
			
			// Accounts for 20 seconds built into PC table 
			actionsToFire--; 
			
			if(actionsToFire == -1) {
				System.out.println("Actions to fire -1");
				actionsToFire = 1; 
			}
		}
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" Ordering Shot: actions to fire, "+actionsToFire+", Shot Count: "+shots);
		//System.out.println("Ordering Shot: actions to fire, "+actionsToFire);
		
		pushShots(shots, shellIndex);
		
	}
	
	public void addOrders(int orders, int shots, int shotIndex) {
		if(shotOrders == null)
			 shotOrders = new ArrayList<ShotOrder>();
		
		
		for(int i = 0; i < orders; i++) {
			
			shotOrders.add(new ShotOrder(shots, shotIndex));
			
		}
		
	}
	
	public void pushShotsOverTime() {
		if(actionsToFire > 0) {
			System.out.println("Cant push shots over time, actions to fire > 0");
			return;
		} else if(shotOrders.size() <= 0) {
			System.out.println("Shot order size == 0");
			return;
		}
		
		var order = shotOrders.remove(0);
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Push shots over time to queue:");
		pushShots(order.shots, order.shellIndex);
	}
	
	public void pushShots(int shots, int shellIndex) {
		for(Artillery battery : batteries) {
			
			for(int i = 0; i < shots; i++) 
				battery.queue.push(shellIndex);
			
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(fireMissionDisplayName+": Push Shots Queue Size: "+battery.queue.size());
		}
		
	}
	
	
	public boolean artilleryComputer() {
		for(Artillery bat: batteries) {
			if(!bat.artilleryComputer) {
				return false; 
			}
		}
		
		return true; 
	}
	
	public void advanceTime() {
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" Advance Time Fire Mission");
		//System.out.println("Advance Time Fire Mission");
		
		
		
		for(Artillery bat: batteries) {
			
			boolean waiting = true; 
			
			if(actionsToFire != 0 || bat.queue.size() > 0)
				waiting = false; 
			
			if(waiting) {
				GameWindow.gameWindow.conflictLog.addNewLineToQueue("Bat: "+bat.batteryDisplayName+" is waiting");
				//bat.batteryStatus = BatteryStatus.WAITING;
				fireMissionStatus = FireMissionStatus.WAITING;
			}
			
			/*if(bat.batteryStatus == BatteryStatus.FIRING) {
				window.conflictLog.addNewLine("Bat: "+bat.batteryDisplayName+" is firing");
				bat.takeShot(this);
			}*/
			
		}
		
		for(Artillery bat : batteries) {
			if(bat.queue.size() > 0 && bat.batteryStatus == BatteryStatus.FIRING) {
				GameWindow.gameWindow.conflictLog.addNewLineToQueue("Taking Shot, Queue Size: "+bat.queue.size());
				bat.takeShot(this);
			} else {
				bat.batteryStatus = BatteryStatus.WAITING; 
			}
		}
		
		advancePlotTime();
		
		if(actionsToFire > 0) {
			
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" Advance Time Fire Mission");
			//System.out.println("Advance Actions to Fire");
			
			if(timeSpentPrepingFire >= actionsToFire) {
				for(Artillery battery : batteries) {
					GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" Shot Taken, Firing Battery: "+
				battery.batteryName);
					//System.out.println("Shot Taken, Firing Battery: "+battery.batteryName);
					battery.takeShot(this);
					battery.batteryStatus = BatteryStatus.FIRING;
				}
				
				actionsToFire = 0; 
				timeSpentPrepingFire = 0; 
				inProgress = true;
			} else {
				timeSpentPrepingFire++; 
			}
			
		} else {
			for(Artillery battery : batteries) {
				battery.shotsTaken = false; 
				battery.actionsSpentShooting = 0; 
			}
		}
		
		if(airborneShots.size() > 0) {
			checkForImpacts();
		}
		
		pushShotsOverTime();
		
		
	}
	
	public void advancePlotTime() {
		if(actionsToPlotted < 1)
			return; 
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" Advanced Plot Time");
		//System.out.println("Advanced Plot Time");
		
		timeSpentPlotting++; 
		
		if(timeSpentPlotting >= actionsToPlotted) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" Plotted");
			//System.out.println("Plotted");
			fireMissionStatus = FireMissionStatus.WAITING;
			plotShot();
			timeSpentPlotting = 0; 
			actionsToPlotted = 0; 
		}
	}
	
	public boolean plotted() {
		
		if(plottedX > 0 && plottedY > 0)
			return true; 
		else 
			return false; 
		
	}
	
	public void checkForImpacts() {
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" Check For Impacts");
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Airborn Shots Count: "+airborneShots.size());
		
		HexGrid.HexGrid.impactHexes.clear();
		
		ArrayList<Shot> removedShots = new ArrayList<>();
		for(Shot shot : airborneShots) {
			if(shot.actionsSpentInAir >= shot.actionsToImpact) {
				impact(shot);
				removedShots.add(shot);
			} else {
				shot.actionsSpentInAir++;
			}
		}
		
		for(Shot shot : removedShots) {
			airborneShots.remove(shot);
		}
		
	}
	
	public void impact(Shot shot) {
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" Impact from "+shot.battery.batteryDisplayName+" "+shot.battery.batteryName);
		//System.out.println("Impact from "+shot.battery.batteryName);
		int accuracy; 
		
		if(fireMissionStatus == FireMissionStatus.RANGING) {
			accuracy = shot.battery.rangingAccuracy;
		} else if(fireMissionStatus == FireMissionStatus.ADJUSTING) {
			accuracy = shot.battery.adjustmenAccuracy;
		} else {
			accuracy = shot.battery.shellAccuracy;
		}
		
		accuracy /= 10; 
		int scatterDistance = (int) (accuracy * scatterModifier()) + DiceRoller.roll(0, fireMissionRadius);
		
		int impactX; 
		int impactY; 
		//System.out.println("Plot Hex X: "+plottedX+", Plot Hex Y: "+plottedY+" Scatter Distance: "+scatterDistance);
		ArrayList<Integer> cords = returnScatteredHex(plottedX, plottedY, scatterDistance, returnScatterDirection());
		impactX = cords.get(0);
		impactY = cords.get(1);
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" X: "+impactX+", Y: "+impactY);
		//System.out.println("X: "+impactX+", Y: "+impactY);
		
		if(GameWindow.gameWindow.game.shieldManager.shieldedHex(impactX, impactY)) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Shielded Impact: "+fireMissionDisplayName+" X: "+impactX+", Y: "+impactY);
			return;
		}
		
		String units = "Hit Units: ";
		for(Unit unit : GameWindow.gameWindow.getUnitsInHex("None", impactX, impactY)) {
			units += unit.callsign + ", ";
		}
		
		
		//new AlertWindow("Fire Mision: "+fireMissionDisplayName+" X: "+impactX+", Y: "+impactY+", "+units);
		
		int x = impactX;
		int y = impactY; 
		
		HexGrid.HexGrid.impactHexes.add(new Cord(x,y));
		
		InjuryLog.InjuryLog.addAlreadyInjured();
				
		Explosion explosion = new Explosion(shot.shell);
		explosion.explodeHex(x, y, "None");
		
		InjuryLog.InjuryLog.printResultsToLog();
		
	}
	
	public int getFireMissionTime() {
		//System.out.println("Getting Fire Mission Time, fire mission speed: "+fireMissionSpeed);
		
		if(inProgress) {
			int val = 40 - crewQuality;
			
			if(artilleryComputer()) {
				val /= 2; 
			}
			
			
			return val;
			
		}
		
		
		
		
		
		setFireMissionSpeed();

		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Fire Mision: "+fireMissionDisplayName+" Getting Fire Mission Time, fire mission speed: "+fireMissionSpeed);
		
		if(fireMissionSpeed == 1) {
			if(platoonLevelSupport)
				return 160;
			else if(companyLevelSupport)
				return 200;
			else
				return 650;
		} else if(fireMissionSpeed == 2) {
			if(platoonLevelSupport)
				return 130;
			else if(companyLevelSupport)
				return 170;
			else
				return 560;
		} else if(fireMissionSpeed == 3) {
			if(platoonLevelSupport)
				return 100;
			else if(companyLevelSupport)
				return 150;
			else
				return 480;
		} else if(fireMissionSpeed == 4) {
			if(platoonLevelSupport)
				return 80;
			else if(companyLevelSupport)
				return 130;
			else
				return 420;
		} else if(fireMissionSpeed == 5) {
			if(platoonLevelSupport)
				return 70;
			else if(companyLevelSupport)
				return 120;
			else
				return 380;
		} else if(fireMissionSpeed == 6) {
			if(platoonLevelSupport)
				return 60;
			else if(companyLevelSupport)
				return 110;
			else
				return 340;
		} else if(fireMissionSpeed == 7) {
			if(platoonLevelSupport)
				return 50;
			else if(companyLevelSupport)
				return 100;
			else
				return 300;
		} else if(fireMissionSpeed == 8) {
			if(platoonLevelSupport)
				return 40;
			else if(companyLevelSupport)
				return 90;
			else
				return 260;
		} else if(fireMissionSpeed == 9) {
			if(platoonLevelSupport)
				return 30;
			else if(companyLevelSupport)
				return 80;
			else
				return 230;
		} else if(fireMissionSpeed == 10) {
			if(platoonLevelSupport)
				return 20;
			else if(companyLevelSupport)
				return 70;
			else
				return 210;
		} else if(fireMissionSpeed == 11) {
			if(platoonLevelSupport)
				return 14;
			else if(companyLevelSupport)
				return 64;
			else
				return 190;
		} else if(fireMissionSpeed == 12) {
			if(platoonLevelSupport)
				return 10;
			else if(companyLevelSupport)
				return 60;
			else
				return 170;
		} else if(fireMissionSpeed == 13) {
			if(platoonLevelSupport)
				return 8;
			else if(companyLevelSupport)
				return 58;
			else
				return 160;
		} else if(fireMissionSpeed == 14) {
			if(platoonLevelSupport)
				return 6;
			else if(companyLevelSupport)
				return 56;
			else
				return 150;
		} else if(fireMissionSpeed == 15) {
			if(platoonLevelSupport)
				return 4;
			else if(companyLevelSupport)
				return 54;
			else
				return 140;
		} else if(fireMissionSpeed == 16) {
			if(platoonLevelSupport)
				return 2;
			else if(companyLevelSupport)
				return 52;
			else
				return 130;
		} else if(fireMissionSpeed == 17) {
			if(platoonLevelSupport)
				return 1;
			else if(companyLevelSupport)
				return 50;
			else
				return 120;
		} else if(fireMissionSpeed == 18) {
			if(platoonLevelSupport)
				return 1;
			else if(companyLevelSupport)
				return 48;
			else
				return 110;
		} else if(fireMissionSpeed == 19) {
			if(platoonLevelSupport)
				return 1;
			else if(companyLevelSupport)
				return 44;
			else
				return 100;
		} else if(fireMissionSpeed == 20) {
			if(platoonLevelSupport)
				return 1;
			else if(companyLevelSupport)
				return 40;
			else
				return 90;
		}
		
		return -1; 
	}
	
	public double scatterModifier() {
		
		int roll = d99();
		if(roll <= 1) {
			return 0.01; 
		} else if(roll <= 3) {
			return 0.02;
		} else if(roll <= 9) {
			return 0.05;
		} else if(roll <= 19) {
			return 0.1; 
		} else if(roll <= 28) {
			return 0.15; 
		} else if(roll <= 37) {
			return 0.2;
		} else if(roll <= 46) {
			return 0.25;
		} else if(roll <= 54) {
			return 0.3; 
		} else if(roll <= 67) {
			return 0.4; 
		} else if(roll <= 78) {
			return 0.5;
		} else if(roll <= 86) {
			return 0.6; 
		} else if(roll <= 91) {
			return 0.7; 
		} else if(roll <= 94) {
			return 0.8;
		} else if(roll <= 97) {
			return 0.9;
		} else if(roll <= 99) {
			return 1.0; 
		}
		
		return 0.0; 
	}
	
	public int returnScatterDirection() {
		return new Random().nextInt(12) + 1;  
	}
	
	public ArrayList<Integer> returnScatteredHex(int originalX, int originalY, int scatterDistance, int scatterDirection) {
		
			
		
		ArrayList<Integer> cordinates = new ArrayList<Integer>();
		
		if(scatterDistance == 0) {
			cordinates.add(originalX);
			cordinates.add(originalY);
			return cordinates; 
		}
		
		if(scatterDirection == 1) {
			cordinates.add(originalX);
			cordinates.add(originalY - scatterDistance);
		} else if(scatterDirection == 2) {
			
			for(int i = 0; i < scatterDistance; i++) {
				
				if(i % 2 ==0) {
					originalX++; 
					if(originalX % 2 == 0)
						originalY--; 
				} else {
					originalY--;
				}
				
				
			}
			
			cordinates.add(originalX);
			cordinates.add(originalY);
		} else if(scatterDirection == 3) {
			
			for(int i = 0; i < scatterDistance; i++) {
				originalX++; 
				if(originalX % 2 == 0)
					originalY--; 
			}
			
			cordinates.add(originalX);
			cordinates.add(originalY);
		} else if(scatterDirection == 4) {
			
			if(scatterDistance == 1) {
				originalX++;
				if(new Random().nextInt(2)+1 == 1)
					originalY++;
			} else {
				if(scatterDistance % 2 == 0) {
					originalX += scatterDistance; 
				} else {
					originalX += scatterDistance; 
					originalY++; 
				}
			}
			
			cordinates.add(originalX);
			cordinates.add(originalY);
			
		} else if(scatterDirection == 5) {
			
			if(scatterDistance == 1) {
				
				if((originalX % 2 == 0 && originalY % 2 == 0) || (originalX % 2 == 0 && originalY % 2 == 1)) {
					cordinates.add(originalX+1);
					cordinates.add(originalY+1);
				} else {
					cordinates.add(originalX+1);
					cordinates.add(originalY);
				}
				
			} else {
				cordinates.add(originalX+scatterDistance);
				cordinates.add(originalY+ ((int) Math.ceil((double)scatterDistance / 2)));
			}
			
			
		} else if(scatterDirection == 6) {
			
			int direction4 = (int) Math.ceil((double)scatterDistance / 2);
			//System.out.println("Direction 4: "+direction4);
			if(direction4 == 1) {
				originalX++;
				
			} else {
				
				originalX+=direction4;
				originalY+= ((int) Math.ceil((double)direction4 / 2));
				
			}
			
			//System.out.println("X: "+originalX+" Y: "+originalY);
			//System.out.println("Direction 4: "+direction4+" Scatter Distance: "+scatterDistance);
			
			if(scatterDistance > 1)
				originalY+=(scatterDistance-direction4);
			
			cordinates.add(originalX);
			cordinates.add(originalY);
		} else if (scatterDirection == 7) {
			cordinates.add(originalX);
			cordinates.add(originalY+scatterDistance);
		}  else if (scatterDirection == 8) {
			int direction4 = (int) Math.ceil((double)scatterDistance / 2);
			//System.out.println("Direction 4: "+direction4);
			if(direction4 == 1) {
				originalX--;
				
			} else {
				
				originalX-=direction4;
				originalY+= ((int) Math.ceil((double)direction4 / 2));
				
			}
			
			if(scatterDistance > 1)
				originalY+=(scatterDistance-direction4);
			
			cordinates.add(originalX);
			cordinates.add(originalY);
			
		} else if (scatterDirection == 9) {
			if(scatterDistance == 1) {
				
				if((originalX % 2 == 0 && originalY % 2 == 0) || (originalX % 2 == 0 && originalY % 2 == 1)) {
					cordinates.add(originalX-1);
					cordinates.add(originalY+1);
				} else {
					cordinates.add(originalX-1);
					cordinates.add(originalY);
				}
				
			} else {
				cordinates.add(originalX-scatterDistance);
				cordinates.add(originalY+ ((int) Math.ceil((double)scatterDistance / 2)));
			}
		} else if(scatterDirection == 10) {
			if(scatterDistance == 1) {
				originalX--;
				if(new Random().nextInt(2)+1 == 1)
					originalY++;
			} else {
				if(scatterDistance % 2 == 0) {
					originalX -= scatterDistance; 
				} else {
					originalX -= scatterDistance; 
					originalY++; 
				}
			}
			
			cordinates.add(originalX);
			cordinates.add(originalY);
		} else if (scatterDirection == 11) {
			
			if(scatterDistance == 1) {
				
				if((originalX % 2 == 0 && originalY % 2 == 0) || (originalX % 2 == 0 && originalY % 2 == 1)) {
					cordinates.add(originalX-1);
					cordinates.add(originalY-1);
				} else {
					cordinates.add(originalX-1);
					cordinates.add(originalY);
				}
				
			} else {
				cordinates.add(originalX-scatterDistance);
				cordinates.add(originalY- ((int) Math.floor((double)scatterDistance / 2)));
			}
			
		} else if(scatterDirection == 12) {
			int direction4 = (int) Math.floor((double)scatterDistance / 2);
			//System.out.println("Direction 4: "+direction4);
			if(direction4 == 1) {
				originalX--;
				
			} else {
				
				originalX-=direction4;
				originalY-= ((int) Math.floor((double)direction4 / 2));
				
			}
			
			//System.out.println("X: "+originalX+" Y: "+originalY);
			//System.out.println("Direction 4: "+direction4+" Scatter Distance: "+scatterDistance);
			
			if(scatterDistance > 1)
				originalY-=(scatterDistance-direction4);
			
			cordinates.add(originalX);
			cordinates.add(originalY);
		}
		
		
		return cordinates; 
	}
	
	/*Unit window 
	Pick trooper for fire mission 
		Calculates and shows trooper stats on change (spotter skill, position and spot error). 
		Automatically sets best spotter that is not exhausted 
	Start Fire Mission 


	Fire Mission Class 
		timeToPlotted 
		timeSpentPlotting 
		Enum, ranging, adjusting, fire for effect.
		Maximum Shots per action 
		Target Hex X and Target Hex Y
		Plotted Hex X, Plotted Hex Y
		
		Sub Class Shot 
			Artillery 
			Shell Type Enum

		Unit Tests 

	Artillery Class 
	Artillery Stats*/
	
	
	public void adjust(int distance, int direction) {
		
		ArrayList<Integer> cords = returnScatteredHex(plottedX, plottedY, distance, direction);
		plottedX = cords.get(0);
		plottedY = cords.get(1);
		
	}
	 
	public int d99() {
		return new java.util.Random().nextInt(100);
	}
	
	@Override
	public String toString() {
		String status = ""; 
		
		if(plotted())
			status += "PLOTTED:: ";
		
		if(fireMissionStatus == FireMissionStatus.WAITING) {
			status = "Waiting";
		} else if(fireMissionStatus == FireMissionStatus.ADJUSTING) {
			status = "Adjusting, Actions to Fire: "+actionsToFire;
		} else if(fireMissionStatus == FireMissionStatus.RANGING) {
			status = "Ranging, Actions to Plotted: "+actionsToPlotted;
		} else if(fireMissionStatus == FireMissionStatus.FIREFOREFFECT) {
			status = "Fire For Effect, Actions to Fire: "+actionsToFire;
		}
		
		return fireMissionDisplayName+" Target X: "+targetX+" Target Y: "+targetY+" LOS to Target: "+LOSToTarget+" LOS to Impact: "+LOSToImpact+" Status: "+status+", Radius: "+fireMissionRadius; 
	}
	
}

