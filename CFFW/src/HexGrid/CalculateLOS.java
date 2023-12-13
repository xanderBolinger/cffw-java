package HexGrid;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import Hexes.Hex;
import Trooper.Trooper;
import Unit.Unit;
import Vehicle.Vehicle;
import Vehicle.Utilities.VehicleDataUtility;

public class CalculateLOS {

	public static void calcVehicles(Vehicle movedVehicle) {
		if(GameWindow.gameWindow == null || GameWindow.gameWindow.game == null || GameWindow.gameWindow.game.vehicleManager == null
				|| GameWindow.gameWindow.companies == null)
			return;
		ExecutorService es = Executors.newFixedThreadPool(16);
		
		movedVehicle.clearLos();
		
		var vehicles = GameWindow.gameWindow.game.vehicleManager.getVehicles();
		
		for(var vic : vehicles) {
			if(vic.compareTo(movedVehicle) || 
					VehicleDataUtility.getSide(movedVehicle).equals(
							VehicleDataUtility.getSide(vic)))
				continue;
			
			es.submit(() -> { 
				calcVehicle(movedVehicle, vic);
				calcVehicle(vic, movedVehicle);
			});
			
			
		}
		
		for(var unit : GameWindow.gameWindow.initiativeOrder) {
			if(unit.side.equals(VehicleDataUtility.getSide(movedVehicle)))
				continue;
			es.submit(() -> { 
				calcVehicleInfantry(movedVehicle, unit);
			});
		}
		
		es.shutdown();
		try {
		  es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
		 e.printStackTrace();
		}

		for(var unit : GameWindow.gameWindow.initiativeOrder) {
			if(unit.losVehicles.contains(movedVehicle) 
					&& !movedVehicle.isSpottingUnit(unit)) {
				
				unit.losVehicles.remove(movedVehicle); 
				
				if(unit.spottedVehicles.contains(movedVehicle))
					unit.spottedVehicles.remove(movedVehicle);
				removeSpottedTroopers(movedVehicle, unit);
			} 
		}
		
		
		updateVehicleLosLists(vehicles);
	}
	
	private static void removeSpottedTroopers(Vehicle vic, Unit unit) {
		
		for(var trooper : unit.getTroopers()) {
			for(var pos : vic.getCrewPositions()) {
				if(pos.spottedTroopers.contains(trooper))
					pos.spottedTroopers.remove(trooper);
			}
			
		}
		
	}
	
	
	private static void updateVehicleLosLists(ArrayList<Vehicle> vehicles) {
		for(var vic : vehicles) {
			removeLosVic(vic);
			removeSpottedVic(vic);
		}
	}
	
	private static void removeLosVic(Vehicle vic) {
		var removeLosVics = new ArrayList<Vehicle>();
		
		for(var spottedVic : vic.losVehicles) 
			if(!spottedVic.losVehicles.contains(vic))
				removeLosVics.add(spottedVic);
		
		for(var removeVic : removeLosVics)
			vic.losVehicles.remove(removeVic);
	}
	
	private static void removeSpottedVic(Vehicle vic) {
		var removeSpottedVics = new ArrayList<Vehicle>();
		for(var spottedVic : vic.spottedVehicles) 
			if(!vic.losVehicles.contains(spottedVic))
				removeSpottedVics.add(spottedVic);
			
		for(var removeSpottedVic :removeSpottedVics)
			vic.spottedVehicles.remove(removeSpottedVic);
		
	}
	
	
	private static void calcVehicle(Vehicle v1, Vehicle v2) {
		
		System.out.println("Calc Vehicle LOS From: "+v1.getVehicleCallsign()+" to "+v2.getVehicleCallsign());
		
		ArrayList<Cord> hexes = TraceLine.GetHexes(v1.movementData.location, v2.movementData.location,
				GameWindow.gameWindow.hexGrid.panel);
		
		var hexesString = "";
		for(var h : hexes)
			hexesString += "("+h.toString()+"), ";
		System.out.println("Cord Line: "+hexesString);
		
		if(hexes.size() <= 2 && !v1.losVehicles.contains(v2)) {
			v1.losVehicles.add(v2);
			return;
		}
		
		var concealment = getConcealment(v1.movementData.location, v2.movementData.location, true) ;
		
		System.out.println("Final Concealment Value: "+concealment);
		
		if(concealment >= 5)
			return;
		
		if(!v1.losVehicles.contains(v2))
			v1.losVehicles.add(v2);
		
	}
	
	public static void calcVehicleInfantry(Vehicle vehicle, Unit targetUnit) {
		if(GameWindow.hexDif(vehicle.movementData.location.xCord, 
				vehicle.movementData.location.yCord, targetUnit) <= 1) {
			if(!vehicle.losUnits.contains(targetUnit))
				vehicle.losUnits.add(targetUnit);
			
			if(!targetUnit.losVehicles.contains(vehicle))
				targetUnit.losVehicles.add(vehicle);
			return;
		}
		
		if(!hasLos(vehicle.movementData.location, new Cord(targetUnit.X, targetUnit.Y)))
			return;
		
		if(!vehicle.losUnits.contains(targetUnit))
			vehicle.losUnits.add(targetUnit);
		
		if(!targetUnit.losVehicles.contains(vehicle))
			targetUnit.losVehicles.add(vehicle);
	}
	
	public static void calc(Unit unit, Unit targetUnit) {
		
		if(GameWindow.hexDif(unit, targetUnit) <= 1) {
			if(!unit.lineOfSight.contains(targetUnit))
				unit.lineOfSight.add(targetUnit);
			
			if(!targetUnit.lineOfSight.contains(unit))
				targetUnit.lineOfSight.add(unit);
			return;
		}
		
		if(!hasLos(new Cord(unit.X, unit.Y), new Cord(targetUnit.X, targetUnit.Y)))
			return;
		
		if(!unit.lineOfSight.contains(targetUnit))
			unit.lineOfSight.add(targetUnit);
		
		if(!targetUnit.lineOfSight.contains(unit))
			targetUnit.lineOfSight.add(unit);
		
	}
	
	public static boolean hasLos(Cord cord, Cord cord2) {
		var concealment = getConcealment(cord, cord2, true) ;
		
		if(concealment >= 5)
			return false;
		return true;
	}
	
	public static void checkSpottedTroopers(Unit unit) {
		
		for(var trooper : unit.individuals) {
			
			for(var spot : trooper.spotted) {
				var list = new ArrayList<Trooper>();
				for(var spottedTrooper : spot.spottedIndividuals) {
					
					if(!unit.lineOfSight.contains(spottedTrooper.returnTrooperUnit(GameWindow.gameWindow))) {
						
						list.add(spottedTrooper);
						
					}
					
				} 
				
				for(var spottedTrooper : list) {
					spot.spottedIndividuals.remove(spottedTrooper);
				}
				
			}
			
		}
		
	} 
	
	public static int getConcealment(Cord c1, Cord c2, boolean lineOfSight) {
		ArrayList<Cord> hexes = TraceLine.GetHexes(c1, c2, GameWindow.gameWindow.hexGrid.panel);

		Cord spotterCord;
		Cord targetCord; 
		
		if(hexes.size() > 2) {
			spotterCord = hexes.remove(0);
			targetCord = lineOfSight ? hexes.remove(hexes.size()-1) : 
				hexes.get(hexes.size()-1);
		} else {
			return 0;
		}
		// Calculations assumes spotter is at x 1 
		// target x is always greater than spotter therefore spotter is x1 and y1 while target is x2 and y2 
		// The number of hexes in between target and spotter plus 1 gets target x 
		// The denominator has an extra plus one because we are getting the index of each hex in the array
		
		int spotterElevation = GameWindow.gameWindow.findHex(spotterCord.xCord, spotterCord.yCord).elevation+1;
		int targetElevation = GameWindow.gameWindow.findHex(targetCord.xCord, targetCord.yCord).elevation+1;
		double slopeToTarget = ((double)targetElevation - (double)spotterElevation) / ((double)hexes.size() + 1.0 - 1.0);
		
		int concealment = 0; 
		
		for(Cord hex : hexes) {
			Hex foundHex = GameWindow.gameWindow.findHex(hex.xCord, hex.yCord);
			if(foundHex == null)
				continue; 
						
			double currentHexElevation = (double)GameWindow.gameWindow.findHex(hex.xCord, hex.yCord).elevation;
			
			boolean buildings = false;
			int buildingFloors = 0;
			
			for(var building : foundHex.buildings) {
				buildings = true;
				if(building.floors.size() > buildingFloors)
					buildingFloors = building.floors.size();
			}
			
			double slopeToCurrentHex = (currentHexElevation+1 - (double)spotterElevation) / ((double)hexes.indexOf(hex)+2.0 - 1.0);
			double slopeToBrush = (currentHexElevation+1 - (double)spotterElevation) / ((double)hexes.indexOf(hex)+2.0 - 1.0);
			double slopeToTrees = (currentHexElevation+3 - (double)spotterElevation) / ((double)hexes.indexOf(hex)+2.0 - 1.0);
			double slopeToBuilding = (currentHexElevation+buildingFloors+1 - (double)spotterElevation) / ((double)hexes.indexOf(hex)+2.0 - 1.0);
			
			//System.out.println("Slope to Current Hex("+hex.toString()+") "+slopeToCurrentHex);
			//System.out.println("Slope to Target("+hex.toString()+") "+slopeToTarget);
			//System.out.println("Slope to Brush("+hex.toString()+") "+slopeToBrush);
			//System.out.println("Slope to Trees("+hex.toString()+") "+slopeToTrees);
			//System.out.println("Slope to Building("+hex.toString()+") "+slopeToBuilding);
			
			if(slopeToCurrentHex > slopeToTarget) {
				concealment = 5;
				break;
			}
			
			/*if(((spotterElevation > targetElevation && slopeToCurrentHex >= slopeToTarget)
					|| (spotterElevation < targetElevation && slopeToCurrentHex < slopeToTarget)
					) && currentHexElevation+1 != targetElevation) {
				concealment = 5;
				break;
			}*/
			
			int brushConcealment = 0;
			int treeConcealment = 0;
			int smokeConcealment = GameWindow.gameWindow.game.smoke.getConcealment(hex);
			//System.out.println("Smoke concealment("+hex.toString()+") "+smokeConcealment);
			
			// "Light Forest", "Medium Forest", "Heavy Forest", "Brush", "Heavy Brush", "Light Rock", "Medium Rock", "Heavy Rock", "Light Urban Sprawl", "Dense Urban Sprawl", "Rubble", "Small Depression", "Large Depression"
			for(var feature : foundHex.features) {
				if(feature.featureType.equals("Light Forest"))
					treeConcealment += 1;
				else if( feature.featureType.equals("Medium Forest"))
					treeConcealment += 2;
				else if(feature.featureType.equals("Heavy Forest"))
					treeConcealment += 3;
			}
			brushConcealment = foundHex.concealment - treeConcealment;
			
			concealment += smokeConcealment;
			
			if(slopeToTrees >= slopeToTarget)
				concealment += treeConcealment;
			if(slopeToBrush >= slopeToTarget)
				concealment += brushConcealment;
			if(slopeToBuilding >= slopeToTarget && buildings) {
				//System.out.println("Buildings Concealment("+hex.toString()+") ");
				concealment = 5;
				break;
			}
			
			//System.out.println("Current Concealment("+hex.toString()+") "+concealment);
			/*if(targetElevation >= spotterElevation) {
				
				if(slopeToTrees >= slopeToTarget)
					concealment += treeConcealment;
				if(slopeToBrush >= slopeToTarget)
					concealment += brushConcealment;
				if(slopeToBuilding >= slopeToTarget && buildings) {
					concealment = 5;
					break;
				}
			} else {
				if(slopeToTrees <= slopeToTarget)
					concealment += treeConcealment;
				if(slopeToBrush <= slopeToTarget)
					concealment += brushConcealment;
				if(slopeToBuilding <= slopeToTarget && buildings) {
					concealment = 5;
					break;
				}
			}*/
		
			
		}
		
		return concealment;
	}
	
	public static int getConcelamentValue(Unit unit, Unit targetUnit) {
				
		return getConcealment(new Cord(unit.X, unit.Y), new Cord(targetUnit.X, targetUnit.Y), false);
	}
	
	public static int getConcealmentAlm(Unit shooterUnit, Unit targetUnit) {
		if(GameWindow.gameWindow == null)
			return 0;
		
		ArrayList<Cord> hexes = TraceLine.GetHexes(new Cord(shooterUnit.X, shooterUnit.Y), new Cord(targetUnit.X, targetUnit.Y), GameWindow.gameWindow.hexGrid.panel);
		
		if(hexes.size() != 1)
			hexes.remove(0);
		
		int alm = 0; 
		
		for(Cord hex : hexes) {
			Hex foundHex = GameWindow.gameWindow.findHex(hex.xCord, hex.yCord);
			if(foundHex == null)
				continue; 
			alm += GameWindow.gameWindow.game.smoke.getAlm(hex);
		}
		
		alm -= getConcealment(new Cord(shooterUnit.X, shooterUnit.Y), new Cord(targetUnit.X, targetUnit.Y), false);
		
		if(alm < -14)
			alm = -14; 
		
		return alm;
	} 
	
	
}
