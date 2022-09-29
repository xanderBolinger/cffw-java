package Hexes;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.TrooperUtility;

public class Building implements Serializable {

	public String name;
	public ArrayList<Floor> floors = new ArrayList<>();

	public static int buildingConcealmentMod = 5;

	public Building() {

	}

	public Building(Building building) {
		name = building.name;

		for (Floor floor : building.floors) {
			floors.add(new Floor(floor));
		}

	}

	public Building(String name, int floors, int rooms, int diameter) {

		this.name = name;

		for (int i = 0; i < floors; i++) {

			addFloor();

		}

		for (Floor floor : this.floors) {

			for (int i = 0; i < rooms; i++) {
				floor.addRoom(diameter);
			}

		}

	}

	public void addFloor(int rooms, int diameter) {

		addFloor();

		for (int i = 0; i < rooms; i++) {
			floors.get(floors.size() - 1).addRoom(diameter);
		}

	}

	public void addFloor(ArrayList<Room> rooms) {
		floors.add(new Floor(rooms));
	}

	public void addFloor() {
		floors.add(new Floor());
	}

	public class Floor implements Serializable {

		public ArrayList<Room> rooms = new ArrayList<>();

		public Floor() {

		}

		public Floor(Floor floor) {
			for (Room room : floor.rooms) {
				rooms.add(new Room(room));
			}
		}

		public Floor(ArrayList<Room> rooms) {
			this.rooms = rooms;
		}

		public void addRoom(int diameter) {
			rooms.add(new Room(diameter));
		}

		public void removeLastRoom() {

			if (rooms.size() == 1)
				return;

			Room removedRoom = rooms.get(rooms.size() - 1);
			rooms.remove(rooms.size() - 1);

			for (Trooper trooper : removedRoom.occupants)
				rooms.get(rooms.size() - 1).occupants.add(trooper);

		}

		public ArrayList<Trooper> getAllOccupants() {

			ArrayList<Trooper> occupants = new ArrayList<Trooper>();

			for (Room room : rooms) {

				for (Trooper trooper : room.occupants)
					occupants.add(TrooperUtility.getRealTrooperReference(trooper));

			}

			return occupants;

		}

	}

	public class Room implements Serializable {

		public ArrayList<Trooper> occupants = new ArrayList<Trooper>();
		public int diameter;

		public Room(Room room) {
			diameter = room.diameter;
		}

		public Room(int diameter) {
			this.diameter = diameter;
		}

		public void addTrooper(Trooper trooper) {

			for (Trooper otherTrooper : occupants)
				if (trooper.compareTo(otherTrooper))
					return;

			occupants.add(trooper);
		}

		public void removeTrooper(Trooper trooper) throws Exception {

			for (Trooper otherTrooper : occupants) {
				if (trooper.compareTo(otherTrooper)) {
					occupants.remove(trooper);
					return;
				}

			}

			throw new Exception("Trooper not in room.");

		}

		public boolean containsTrooper(Trooper trooper) {

			for (Trooper otherTrooper : occupants) {

				if (trooper.compareTo(otherTrooper))
					return true;

			}

			return false;
		}

	}

	public int friendlyOccupants(GameWindow gameWindow, Unit unit, Floor floor) {

		int fo = 0;

		for (Room room : floor.rooms)
			for (Trooper occupant : room.occupants)
				if (occupant.returnTrooperUnit(gameWindow).side.equals(unit.side))
					fo++;

		return fo;
	}

	public int hostileOccupants(GameWindow gameWindow, Unit unit, Floor floor) {

		int ho = 0;

		for (Room room : floor.rooms)
				for (Trooper occupant : room.occupants)
					if (!occupant.returnTrooperUnit(gameWindow).side.equals(unit.side))
						ho++;

		return ho;
	}

	public ArrayList<Trooper> getAllOccupants() {

		ArrayList<Trooper> occupants = new ArrayList<>();

		for (Floor floor : floors)
			for (Room room : floor.rooms)
				for (Trooper occupant : room.occupants)
					occupants.add(occupant);

		//System.out.println("Occupants: "+occupants.size());
		return occupants;

	}

	public ArrayList<Trooper> getOccupants(Unit unit) {

		ArrayList<Trooper> occupants = new ArrayList<>();

		for (Floor floor : floors) {
			for (Room room : floor.rooms) {
				for (Trooper occupant : room.occupants) {
					for (Trooper individual : unit.individuals) {
						if (individual.compareTo(occupant)) {
							occupants.add(occupant);
						}
					}

				}
			}
		}

		return occupants;

	}

	public int getTrooperFloorNumber(Trooper trooper) {

		int floorNumber = -1;

		for (Floor floor : floors)
			for (Room room : floor.rooms)
				for (Trooper occupant : room.occupants)
					if (occupant.compareTo(trooper)) {
						return floors.indexOf(floor) + 1;
					}

		return floorNumber;

	}

	public int getTrooperRoomNumber(Trooper trooper) {

		int roomNumber = -1;

		for (Floor floor : floors)
			for (Room room : floor.rooms)
				for (Trooper occupant : room.occupants)
					if (occupant.compareTo(trooper)) {
						return floor.rooms.indexOf(room) + 1;
					}

		return roomNumber;

	}

	public Floor getTrooperFloor(Trooper trooper) {

		for (Floor floor : floors)
			for (Room room : floor.rooms)
				for (Trooper occupant : room.occupants)
					if (occupant.compareTo(trooper)) {
						return floor;
					}

		return null;

	}

	public Room getTrooperRoom(Trooper trooper) {

		for (Room room : getTrooperFloor(trooper).rooms) {

			if (room.containsTrooper(trooper)) {
				// System.out.println("Found trooper room.");
				return room;
			}

		}

		// System.out.println("Room returning null");
		return null;

	}

	public void removeOccupant(Trooper trooper) {

		for (Floor floor : floors)
			for (Room room : floor.rooms)
				if (room.occupants.contains(trooper)) {
					room.occupants.remove(trooper);
					return;
				}

	}

	public void addOccupant(Trooper trooper, int floor, int room) {

		if (floor > floors.size() || floor - 1 < 0)
			return;

		// System.out.println("Add Trooper");
		floors.get(floor - 1).rooms.get(room - 1).addTrooper(trooper);

	}

	public void changeFloor(Trooper trooper, int floor, int room) {
		if (floor > floors.size() || floor - 1 < 0)
			return;

		removeOccupant(trooper);
		addOccupant(trooper, floor, room);

	}
	
	

	public String toString(GameWindow gameWindow, Unit unit) {

		if (floors.size() < 1)
			return "UNINITALIZED BUILDING.";

		String rslt = name + ", Floors: " + floors.size();

		if (floors.size() > 0)
			rslt += ", ";

		int count = 1;
		for (Floor floor : floors) {
			rslt += "Floor: " + count + ", Rooms: " + floor.rooms.size() + ", Friendly Occupants: "
					+ friendlyOccupants(gameWindow, unit, floor) + ", Hostile Occupants: " + hostileOccupants(gameWindow, unit, floor)
					+ " ";
			count++;
		}

		return rslt;

	}

	@Override
	public String toString() {

		if (floors.size() < 1)
			return "UNINITALIZED BUILDING.";

		String rslt = name+", "+floors.size() + " story building. \n";

		int count = 1;
		for (Floor floor : floors) {
			rslt += "        Floor: " + count + ", Rooms: " + floor.rooms.size() +", Diameter: "+floor.rooms.get(0).diameter +" Occupants: " + floor.getAllOccupants().size() 
					+ "\n";
			count++;
		}

		return rslt;

	}

	
	public void removeTopFloor() {

		if (floors.size() < 1)
			return;

		floors.remove(floors.size() - 1);

	}

	public int getHexSize() {

		int squareHexes = 0;

		for (Floor floor : floors) {
			for (Room room : floor.rooms) {
				squareHexes += room.diameter;
			}
		}

		return squareHexes;
	}

	public static boolean majorityEmbarked(GameWindow gameWindow, ArrayList<Trooper> individuals) {
		ArrayList<Trooper> embarkedTroopers = new ArrayList<>();

		for (Trooper trooper : individuals) {

			if (trooper.inBuilding(gameWindow)) {
				embarkedTroopers.add(trooper);
			}

		}

		ArrayList<Trooper> unembarkedTroopers = new ArrayList<>();

		for (Trooper trooper : individuals) {

			if (trooper.inBuilding(gameWindow)) {
				unembarkedTroopers.add(trooper);
			}

		}

		if (embarkedTroopers.size() > unembarkedTroopers.size())
			return true;
		else
			return false;
	}

}
