package OperationExporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Company.Company;
import Unit.Unit;
import Vehicle.Vehicle;

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
		
		public UnitJSON(Unit unit) {
			name = unit.callsign;
			identifier = unit.identifier;
			troopers = new ArrayList<>();
			vehicles = new ArrayList<>();
			for(var individual : unit.individuals) {
				troopers.add(new TrooperJSON(individual.identifier, individual.name, individual.sl));
			}
			
		}
		
		public UnitJSON(Vehicle vehicle) {
			name = vehicle.getVehicleCallsign();
			identifier = vehicle.identifier;
			troopers = new ArrayList<>();
			vehicles = new ArrayList<>();
			
			for(var individual : vehicle.getTroopers()) {
				troopers.add(new TrooperJSON(individual.identifier, individual.name, individual.sl));
			}
			
			vehicles.add(new VehicleJSON(vehicle));
			
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
		
		public VehicleJSON(Vehicle vehicle) {
			this.callsign = vehicle.getVehicleCallsign();
			this.vehicleType = vehicle.getVehicleType();
			this.vehicleClass = vehicle.getVehicleClass();
			this.repulsorCraft = vehicle.getRepulsorCraft();
			this.disabled = vehicle.getVehicleDisabled();
			this.transportCapacity = vehicle.getTroopCapacity();
			this.identifier = vehicle.identifier;
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

	}

	public static void exportCompanyToUserLocation(Company company) {
		
		OperationUnit ou = new OperationUnit(company.getName(), company.getSide());
		
		for(var unit : company.getUnits()) {
			
			ou.addUnit(new UnitJSON(unit));
			
		}
		
		for(var vehicle : company.vehicles) {
			ou.addUnit(new UnitJSON(vehicle));
		}
		
		
		exportToJsonFile(ou, true);
		
	}
	
	public static void exportCompany(Company company) {
		
		OperationUnit ou = new OperationUnit(company.getName(), company.getSide());
		
		for(var unit : company.getUnits()) {
			
			ou.addUnit(new UnitJSON(unit));
			
		}
		
		for(var vehicle : company.vehicles) {
			ou.addUnit(new UnitJSON(vehicle));
		}
		
		
		exportToJsonFile(ou);
		
	}
	
	public static void exportToJsonFile(OperationUnit operationUnit) {
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String json = gson.toJson(operationUnit);
	    String fileName = operationUnit.unitName + ".json";
	    // Prompt the user for the destination directory
	    //Scanner scanner = new Scanner(System.in);
	    //System.out.print("Enter the destination directory path: ");
	    //String destinationDirectory = scanner.nextLine();
	    //scanner.close();

	    String filePath = Paths.get("X:\\Users\\Xander\\GitHub\\HolotableHDRP\\Assets\\Resources\\OperationUnits", fileName).toString();

	    try (FileWriter writer = new FileWriter(filePath)) {
	        writer.write(json);
	        System.out.println("JSON file created: " + filePath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	// Desktop
	// X:\Users\Xander\GitHub\HolotableHDRP\Assets\Resources
	public static void exportToJsonFile(OperationUnit operationUnit, boolean chooseFileLocation) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(operationUnit);

		// Create a file chooser and set the default directory
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select the destination directory");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// Set the default directory to the user's home directory
		fileChooser.setCurrentDirectory(new File("X:\\Users\\Xander\\GitHub\\HolotableHDRP\\Assets\\Resources\\OperationUnits"));

		// Show the file chooser dialog and get the user's selection
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			String destinationDirectory = fileChooser.getSelectedFile().getAbsolutePath();
			String filePath = Paths.get(destinationDirectory, operationUnit.unitName+".json").toString();

			try (FileWriter writer = new FileWriter(filePath)) {
				writer.write(json);
				System.out.println("JSON file created: " + filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No destination directory selected. JSON export cancelled.");
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

		exportToJsonFile(operationUnit);
	}
}
