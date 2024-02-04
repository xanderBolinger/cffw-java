package Conflict;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import Hexes.Building;
import Hexes.Building.Floor;
import Hexes.Building.Room;
import Hexes.Hex;
import Trooper.Trooper;
import Unit.Unit;

public class CloseQuartersBattle implements Serializable {

	
	ArrayList<Unit> unitsInCloseCombat = new ArrayList<>();
	//ArrayList<Bout> bouts = new ArrayList<>();
	GameWindow gameWindow; 
	
	public CloseQuartersBattle(GameWindow gameWindow) {
		this.gameWindow = gameWindow; 
	}
	
	/*public class Bout {
		
		public ArrayList<Trooper> troopers = new ArrayList<>();
		
		public Bout(Trooper t1, Trooper t2) {
			troopers.add(t1);
			troopers.add(t2);
		}
		
		public boolean inBout(Trooper trooper) {
			
			for(Trooper otherTrooper : troopers) {
				
				if(trooper.compareTo(otherTrooper))
					return true; 
				
			}
			
			
			return false; 
		}
		
		public void addTrooper(Trooper t) {
			
			for(Trooper trooper : troopers) {
				if(trooper.compareTo(t)) {
					return;
				}
			}
			
			troopers.add(t);
			
		}
		
		
	}*/
	
	public void closeQuartersBattleCheck(OpenUnit openUnit) {
		
		
		unitsInCloseCombat.clear();
		
		// Adds all units that are in close combat
		for(Unit unit : gameWindow.initiativeOrder) {
			
			// If unit is exhaused, skip unit 
			if(GameWindow.exhaustedUnit(unit))
				continue; 
			
			// If unit is not embarked 
			boolean embarked = false; 
			for(Trooper trooper : unit.individuals) {
				if(trooper.inBuilding(gameWindow)) {
					embarked = true; 
					break; 
				}
			}
			
			if(!embarked)
				continue; 
			
			// If there is at least one hostile individual on a embarked trooper's floor 
			for(Trooper trooper : unit.individuals) {
				// If trooper not in building skip trooper 
				if(!trooper.inBuilding(gameWindow) || GameWindow.exhaustedTrooper(trooper) || !trooper.alive || !trooper.conscious || trooper.HD) {
					continue; 
				}

				//System.out.println("CQB Valid Trooper: "+trooper.number+" "+trooper.name);
				
				// Get Hex 
				Hex hex = gameWindow.findHex(unit.X, unit.Y);
				int floorNum = -1; 
				
				// Find trooper floor num 
				for(Building building : hex.buildings) {
					
					floorNum = building.getTrooperFloorNumber(trooper);
					
					if(floorNum != -1) {
						break;
					}
					
				}
				
				// Debug error msg 
				if(floorNum < 0) {
					System.out.println("Error Floor Num -1");
					return; 
				}
				
				// Iterates over hostile units in hex 
				for(Unit otherUnit : GameWindow.gameWindow.getUnitsInHexExcludingSide(unit.side, unit.X, unit.Y)) {

					// Iterates over hostile troopers 
					for(Trooper hostileTrooper : otherUnit.individuals) {
						
						// If hostile trooper not in a building, skips trooper 
						if(!hostileTrooper.inBuilding(gameWindow) || !hostileTrooper.alive || !hostileTrooper.conscious) {
							continue; 
						}
						
						System.out.println("CQB Valid Target: "+hostileTrooper.number+" "+hostileTrooper.name);
						boolean unitInCQB = false; 
						
						// If hostile trooper is on the same floor as regular trooper 
						for(Building building : hex.buildings) {
							
							if(building.getTrooperFloorNumber(hostileTrooper) == floorNum) {
								
								unitInCQB = true; 
								break; 
								
							}
							
						}
						
						// Adds units to units in CQB 
						if(unitInCQB) {
							
							//System.out.println("Add Unit");
							
							if(!unitsInCloseCombat.contains(unit)) {
								unitsInCloseCombat.add(unit);
							}
							
							if(!unitsInCloseCombat.contains(otherUnit)) {
								unitsInCloseCombat.add(otherUnit);
							}
						
						
							break; 
						}
						
						
					}
					
					
				}
				
				
			}
			
		}
		
		
		
		// Creates close combat window 
		// Close combat window requires all units and their individuals to have declared CQB actions before it closes
		// Action goes by, resolves bouts when units go by in the initiative order that contain troopers in bouts 
		//System.out.println("Units IN CLOSE COMBAT: "+unitsInCloseCombat.size());
		if(unitsInCloseCombat.size() > 1) {
			gameWindow.cqbWindowOpen = true; 
			new CloseQuartersBattleWindow(gameWindow, openUnit);
		}
		
	}
	
	
	
	
	public void setTargets() {
		
		/*if(bouts.size() > 0)
			bouts.clear(); 	*/
		
		// Iterates over all units in close combat 
		// Iterates over all troopers 
		// Creates bouts between troopers and other troopers in the same room 
		
		clearTargets(); 
		checkForBoutsInSameRoom();
		checkForAttackersOnAttackers();
		checkForAttackersEngagingDefenders();
		//checkForAttackersMovingRooms();
		
		for(Unit unit : gameWindow.cqb.unitsInCloseCombat) {
			System.out.println("Unit: "+unit.callsign);
			for(Trooper trooper : unit.individuals) {
				Building building = gameWindow.findHex(unit.X, unit.Y).getTrooperBuilding(trooper);
				
				if(building == null)
					continue; 
				
				String buildingName = building.name;
				int floorNumber = gameWindow.findHex(unit.X, unit.Y).getTrooperFloorNumber(trooper);
				if(trooper.defendingInCloseCombat) {
					System.out.println("DEFENDING: "+ buildingName + ", on floor: "+ floorNumber+ ", " +trooper.toStringImproved(gameWindow.game));
				} else if(trooper.attackingInCloseCombat) {
					System.out.println("ATTACKING: "+ buildingName + ", on floor: "+ floorNumber+ ", " +trooper.toStringImproved(gameWindow.game));
				} else {
					System.out.println("UNSPECIFIED: "+ buildingName + ", on floor: "+ floorNumber+ ", " +trooper.toStringImproved(gameWindow.game));
				}
			}
		}
		
		
	}
	
	public void clearTargets() {
		System.out.println("Clear Targets. Units in CQB: "+unitsInCloseCombat.size());
		for(Unit unit : unitsInCloseCombat) {
			
			for(Trooper trooper : unit.individuals) {
				trooper.closeCombatTarget = null; 
			}
			
		}
		
	}
	
	
	public void checkForAttackersMovingRooms() {
		
		for(Unit unit : unitsInCloseCombat) {
			
			for(Trooper trooper : unit.individuals) {
				if(!trooper.alive || !trooper.conscious || trooper.HD || trooper.defendingInCloseCombat || !trooper.attackingInCloseCombat || trooper.closeCombatTarget != null)
					continue; 
				
				boolean foundTarget = false; 
				Floor trooperFloor = null; 
				
				Hex trooperHex = gameWindow.findHex(unit.X, unit.Y);
				
				if(trooperHex == null)
					continue; 
				
				for(Building building : trooperHex.buildings) {
					
					for(Floor floor : building.floors) {
						
						for(Trooper otherTrooper : floor.getAllOccupants()) {
							if(otherTrooper.compareTo(trooper)) {
								trooperFloor = floor; 
								//break; 
							}
						}
						
					}
					
				}
				
				// trooper not in building 
				if(trooperFloor == null)
					continue;
				
				Room trooperRoom = null; 
				
				for(Room room : trooperFloor.rooms) {
					
					for(Trooper otherTrooper : room.occupants) {
						
						if(otherTrooper.compareTo(trooper)) {
							trooperRoom = room; 
						}
						
					}
					
				}
				
				Room nextRoom = null; 
				ArrayList<Room> guessedRooms = new ArrayList<>();
				
				guessedRooms.add(trooperRoom);
				
				while(nextRoom == null) {
					
					Room tempRoom = null; 
					
					while(tempRoom == null) {
						tempRoom = trooperFloor.rooms.get(new Random().nextInt(trooperFloor.rooms.size()));
						if(guessedRooms.contains(tempRoom))
							tempRoom = null; 
					}
					
					for(Trooper occupant : tempRoom.occupants) {
						
						if(occupant.alive && occupant.conscious && !occupant.returnTrooperUnit(gameWindow).side.equals(unit.side)) {
							
							nextRoom = tempRoom; 
							break; 
						}
							
					}
					
					guessedRooms.add(tempRoom);
					
					if(guessedRooms.size() == trooperFloor.rooms.size())
						break; 
				}
				
				if(nextRoom == null)
					continue; 
				
				ArrayList<Trooper> possibleDefenders = new ArrayList<>();
				
				for(Trooper occupant : nextRoom.occupants) {
					
					if(occupant.alive && occupant.conscious && !occupant.returnTrooperUnit(gameWindow).side.equals(unit.side)) {
						System.out.println("Possible Defenders: "+possibleDefenders.size());
						possibleDefenders.add(occupant);
					}
					
				}
				
				for(Trooper pd : possibleDefenders) {
					
					if(pd.closeCombatTarget != null && pd.closeCombatTarget.compareTo(trooper)) {
						trooper.closeCombatTarget = pd; 
						foundTarget = true; 
						break; 
					}
					else if(pd.closeCombatTarget != null)
						continue; 
				
					pd.closeCombatTarget = trooper; 
					trooper.closeCombatTarget = pd; 
					foundTarget = true; 
					break; 
					
				}
				
				if(foundTarget)
					continue; 
				
				ArrayList<Trooper> possibleTargets = possibleDefenders;
				
				if(possibleTargets.size() < 1)
					continue; 
				
				// Picks random target
				trooper.closeCombatTarget = possibleTargets.get(new Random().nextInt(possibleTargets.size()));
				if(trooper.closeCombatTarget.closeCombatTarget == null)
					trooper.closeCombatTarget.closeCombatTarget = trooper; 
				
				
			}
			
		}
		
		
	}
	
	
	public void checkForAttackersEngagingDefenders() {
		
		for(Unit unit : unitsInCloseCombat) {
			
			for(Trooper trooper : unit.individuals) {
		
				if(!trooper.alive || !trooper.conscious || trooper.HD || trooper.defendingInCloseCombat || !trooper.attackingInCloseCombat || trooper.closeCombatTarget != null)
					continue; 
				
				boolean foundTarget = false; 
				Floor trooperFloor = null; 
				
				Hex trooperHex = gameWindow.findHex(unit.X, unit.Y);
				
				if(trooperHex == null)
					continue; 
				
				for(Building building : trooperHex.buildings) {
					
					for(Floor floor : building.floors) {
						
						for(Trooper otherTrooper : floor.getAllOccupants()) {
							if(otherTrooper.compareTo(trooper)) {
								trooperFloor = floor; 
								//break; 
							}
						}
						
					}
					
				}
				
				// trooper not in building 
				if(trooperFloor == null)
					continue;
				
				ArrayList<Trooper> possibleDefenders = new ArrayList<>();
				
				for(Trooper possibleDefender : trooperFloor.getAllOccupants()) {
					
					if(!possibleDefender.alive || !possibleDefender.conscious || possibleDefender.HD || !possibleDefender.defendingInCloseCombat 
							|| possibleDefender.compareTo(trooper) || possibleDefender.returnTrooperUnit(gameWindow).side.equals(unit.side)) {
						System.out.println("Condition: "+possibleDefender.attackingInCloseCombat);
						System.out.println("Condition: "+possibleDefender.defendingInCloseCombat);
						System.out.println("Possible Defender continue");
						continue; 
					}
					
					System.out.println("Possible Defender Add");
					possibleDefenders.add(possibleDefender);
					
				}
				
				System.out.println("Possible Defenders Size: "+possibleDefenders.size());
				
				for(Trooper pd : possibleDefenders) {
					if(pd.closeCombatTarget != null && pd.closeCombatTarget.compareTo(trooper)) {
						trooper.closeCombatTarget = pd; 
						foundTarget = true; 
						break; 
					}
					else if(pd.closeCombatTarget != null)
						continue; 
				
					pd.closeCombatTarget = trooper; 
					trooper.closeCombatTarget = pd; 
					foundTarget = true; 
					break; 
					
					
				}
				
				if(foundTarget)
					continue; 
				
				
				ArrayList<Trooper> possibleTargets = possibleDefenders;
				
				if(possibleTargets.size() < 1)
					continue; 
				
				// Picks random target
				trooper.closeCombatTarget = possibleTargets.get(new Random().nextInt(possibleTargets.size()));
				if(trooper.closeCombatTarget.closeCombatTarget == null) {
					System.out.println("Set Defender Target HERE");
					trooper.closeCombatTarget.closeCombatTarget = trooper; 
				}
				
				
				
			}
			
		}
	}
	
	public void checkForAttackersOnAttackers() {
		
		for(Unit unit : unitsInCloseCombat) {
			for(Trooper trooper : unit.individuals) {
				if(!trooper.alive || !trooper.conscious || trooper.HD || trooper.defendingInCloseCombat || !trooper.attackingInCloseCombat || trooper.closeCombatTarget != null)
					continue; 
				
				boolean foundTarget = false; 
				Floor trooperFloor = null; 
				
				Hex trooperHex = gameWindow.findHex(unit.X, unit.Y);
				
				if(trooperHex == null)
					continue; 
				
				for(Building building : trooperHex.buildings) {
					
					for(Floor floor : building.floors) {
						
						for(Trooper otherTrooper : floor.getAllOccupants()) {
							System.out.println("floor occupant");
							
							System.out.println("Other Trooper: "+otherTrooper.name+", "+otherTrooper.identifier);
							System.out.println("Trooper: "+trooper.name+", "+trooper.identifier);
							if(otherTrooper.compareTo(trooper)) {
								trooperFloor = floor; 
								System.out.println("trooper floor found");
								//break; 
							}
						}
						
					}
					
				}
				
				// trooper not in building 
				if(trooperFloor == null)
					continue;
				
				// Attacking troopers with troopers of the same unit in their room create bouts with non hunkered down defenders. 
				// If there are none, they move forward to the next room not solely occupied by friendly forces, and enter bouts with hunkered down defenders. 
				// Finds another attacker on floor. 
				// Checks if being targeted by another trooper, sets target to that target. 
				// Iterates over all other attackers, finds attacker not already being targeted. 
				// Otherwise, picks a random attacker and sets them to be the target. 
				ArrayList<Trooper> attackers = new ArrayList<Trooper>();
				
				// Creates attacker list
				for(Trooper floorTrooper : trooperFloor.getAllOccupants()) {
					if(floorTrooper.compareTo(trooper) || !floorTrooper.alive || !floorTrooper.conscious || floorTrooper.defendingInCloseCombat || 
							!floorTrooper.attackingInCloseCombat || floorTrooper.HD || floorTrooper.returnTrooperUnit(gameWindow).side.equals(unit.side))
						continue; 
					
					attackers.add(floorTrooper);
					
				}

				// Checks if attacker is already a target
				for(Trooper attacker : attackers) {
					
					if(attacker.closeCombatTarget != null && attacker.closeCombatTarget.compareTo(trooper)) {
						trooper.closeCombatTarget = attacker; 
						foundTarget = true;
						break; 
					}
					
				}
				
				for(Trooper attacker : attackers) {
					
					if(alreadyTarget(trooperFloor.getAllOccupants(), attacker) || attacker.closeCombatTarget != null)
							continue; 
					
					attacker.closeCombatTarget = trooper; 
					trooper.closeCombatTarget = attacker; 
					foundTarget = true; 
					break; 
					
				}
				
				
				if(foundTarget)
					continue; 
				
				// Picks random attacker to be target
				// Picks random target in room 
				ArrayList<Trooper> possibleTargets = attackers;
				
				if(possibleTargets.size() < 1)
					continue; 
				
				// Picks random target
				trooper.closeCombatTarget = possibleTargets.get(new Random().nextInt(possibleTargets.size()));
				if(trooper.closeCombatTarget.closeCombatTarget == null)
					trooper.closeCombatTarget.closeCombatTarget = trooper; 
				
			}
		}
		
	}
	
	
	public void checkForBoutsInSameRoom() {
		
		
		for(Unit unit : unitsInCloseCombat) {
			for(Trooper trooper : unit.individuals) {
				if(!trooper.alive || !trooper.conscious || trooper.closeCombatTarget != null)
					continue; 
				
				
				boolean foundTarget = false; 
				
				Floor trooperFloor = null; 
				
				Hex trooperHex = gameWindow.findHex(unit.X, unit.Y);
				
				// If hex can't be found or trooper not in a building, continue 
				if(trooperHex == null || trooperHex.getTrooperBuilding(trooper) == null)
					continue; 
				
				for(Building building : trooperHex.buildings) {
					
					for(Floor floor : building.floors) {
						
						for(Trooper otherTrooper : floor.getAllOccupants()) {
							if(otherTrooper.compareTo(trooper)) {
								trooperFloor = floor; 
								//break; 
							}
						}
						
					}
					
				}
				
				// trooper not in building 
				if(trooperFloor == null)
					continue;
				
				
				Room trooperRoom = null; 
				
				for(Room room : trooperFloor.rooms) {
					
					for(Trooper otherTrooper : room.occupants) {
						
						if(otherTrooper.compareTo(trooper)) {
							trooperRoom = room; 
						}
						
					}
					
				}
				
				for(Trooper occupant : trooperRoom.occupants) {
					Unit occupantUnit = occupant.returnTrooperUnit(gameWindow);
					if(occupantUnit == null)
						continue;
					
					if(occupantUnit.side.equals(unit.side) || occupant.compareTo(trooper) || !occupant.alive || !occupant.conscious) {
						continue; 
					}
					
					if(occupant.closeCombatTarget != null && occupant.closeCombatTarget.compareTo(trooper)) {
						trooper.closeCombatTarget = occupant; 
						foundTarget = true; 
						break; 
						
					}
					
				}
				
				
				if(foundTarget)
					continue; 
				
				
				// Checks for hostile occupants in room 
				for(Trooper occupant : trooperRoom.occupants) {
					
					Unit occupantUnit = occupant.returnTrooperUnit(gameWindow);
					if(occupantUnit == null)
						continue;
					if(occupantUnit.side.equals(unit.side) || occupant.compareTo(trooper) || !occupant.alive || !occupant.conscious) {
						continue; 
					} else {
						
						// Checks if occupant is already a target || or if the occupant has a target
						if(!alreadyTarget(trooperRoom.occupants, occupant) || occupant.closeCombatTarget != null) {
							
							continue; 
							
						} else {
							
							foundTarget = true; 
							occupant.closeCombatTarget = trooper; 
							occupant.attackingInCloseCombat = false; 
							occupant.defendingInCloseCombat = false; 
							System.out.println("Weird False setting");
							trooper.attackingInCloseCombat = false; 
							trooper.defendingInCloseCombat = false; 
							trooper.closeCombatTarget = occupant; 
							break; 
							
						}
						
					}
					
				}
				
				
				if(foundTarget)
					continue; 
				
				// Picks random target in room 
				ArrayList<Trooper> possibleTargets = new ArrayList<>();
				
				for(Trooper occupant : trooperRoom.occupants) {
					Unit occupantUnit = occupant.returnTrooperUnit(gameWindow);
					if(occupantUnit == null)
						continue;
					if(occupantUnit.side.equals(unit.side) || occupant.compareTo(trooper) || !occupant.alive || !occupant.conscious) {
						continue; 
					}
					
					possibleTargets.add(occupant);
					
				}
				
				System.out.println("Possible Targets Same Room Bout: "+possibleTargets.size());
				
				if(possibleTargets.size() < 1)
					continue; 
				
				// Picks random target
				trooper.closeCombatTarget = possibleTargets.get(new Random().nextInt(possibleTargets.size()));
				if(trooper.closeCombatTarget.closeCombatTarget == null)
					trooper.closeCombatTarget.closeCombatTarget = trooper; 
				
			}
			
		}
		
		
		
	}
	
	
	public boolean alreadyTarget(ArrayList<Trooper> possibleAttackers, Trooper trooperInQuestion) {
		
		
		for(Trooper possibleAttacker : possibleAttackers)
			if(possibleAttacker.closeCombatTarget != null && possibleAttacker.closeCombatTarget.compareTo(trooperInQuestion))
				return true; 
		
		return false; 
	}
	
	/*public void newBout(Trooper t1, Trooper t2) {
		
		bouts.add(new Bout(t1, t2));
		
	}
	
	
	public Bout inBout(Trooper trooper) {
		
		for(Bout bout : bouts) {
			
			if(bout.inBout(trooper))
				return bout; 
			
		}
		
		
		return null; 
		
	}*/
	
	
}
