package OperationExporter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OperationUnitExporter {
	static class UnitJSON {
		private String name;
		private String identifier;
		private List<TrooperJSON> troopers;
		private List<VehicleJSON> vehicles;

		public UnitJSON(String name, String identifier) {
			this.name = name;
			this.identifier = identifier;
			this.troopers = new ArrayList<>();
			this.vehicles = new ArrayList<>();
		}

		public void addTrooper(TrooperJSON trooper) {
			troopers.add(trooper);
		}

		public void addVehicle(VehicleJSON vehicle) {
			vehicles.add(vehicle);
		}
	}

	static class TrooperJSON {
		private String identifier;
		private String name;
		private int sl;

		public TrooperJSON(String identifier, String name, int sl) {
			this.identifier = identifier;
			this.name = name;
			this.sl = sl;
		}
	}

	static class VehicleJSON {
		private String callsign;
		private String vehicleType;
		private String vehicleClass;
		private boolean repulsorCraft;
		private boolean disabled;
		private int transportCapacity;
		private String identifier;

		public VehicleJSON(String callsign, String vehicleType, String vehicleClass, boolean repulsorCraft,
				boolean disabled, int transportCapacity, String identifier) {
			this.callsign = callsign;
			this.vehicleType = vehicleType;
			this.vehicleClass = vehicleClass;
			this.repulsorCraft = repulsorCraft;
			this.disabled = disabled;
			this.transportCapacity = transportCapacity;
			this.identifier = identifier;
		}
	}

	static class OperationUnit {
		private String unitName;
		private String side;
		private List<UnitJSON> units;

		public OperationUnit(String unitName, String side) {
			this.unitName = unitName;
			this.side = side;
			this.units = new ArrayList<>();
		}

		public void addUnit(UnitJSON unit) {
			units.add(unit);
		}

		public void exportToJsonFile(String filename) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(this);

			try (FileWriter writer = new FileWriter(filename)) {
				writer.write(json);
				System.out.println("JSON file created: " + filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	public static void main(String[] args) {
		OperationUnit operationUnit = new OperationUnit("OperationUnit1", "BLUFOR");

		UnitJSON unit1 = new UnitJSON("Unit1", "Unit1Identifier");
		unit1.addTrooper(new TrooperJSON("Trooper1Identifier", "Trooper1", 1));
		unit1.addTrooper(new TrooperJSON("Trooper2Identifier", "Trooper2", 2));
		unit1.addVehicle(new VehicleJSON("Vehicle1", "ARMOR", "ATTE", false, false, 27, "Vehicle1Identifier"));
		unit1.addVehicle(new VehicleJSON("Vehicle2", "ARMOR", "AAT", true, false, 3, "Vehicle2Identifier"));

		UnitJSON unit2 = new UnitJSON("Unit2", "Unit2Identifier");
		unit2.addTrooper(new TrooperJSON("Trooper3Identifier", "Trooper3", 3));
		unit2.addVehicle(new VehicleJSON("Vehicle3", "MECHANIZED", "ATTE", false, true, 15, "Vehicle3Identifier"));

		operationUnit.addUnit(unit1);
		operationUnit.addUnit(unit2);

		operationUnit.exportToJsonFile("output.json");
	}
}
