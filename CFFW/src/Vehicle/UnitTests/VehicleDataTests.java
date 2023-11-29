package Vehicle.UnitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import HexGrid.HexDirectionUtility.HexDirection;
import Hexes.Feature;
import Hexes.Hex;
import Trooper.Trooper;
import Vehicle.Vehicle;
import Vehicle.Data.CrewCompartment;
import Vehicle.Data.CrewMember;
import Vehicle.Data.CrewPosition;
import Vehicle.Utilities.VehicleXmlReader;
import Vehicle.Utilities.VehicleDataUtility.CrewPositionType;

public class VehicleDataTests {

	@Test
	public void CreateVehicleTest() throws Exception {

		// fail("Not yet implemented");
		List<CrewCompartment> compartments = new ArrayList<>();
		List<HexDirection> directions = HexDirection.getDirections();

		ArrayList<CrewPositionType> positionTypes = new ArrayList<>();

		positionTypes.add(CrewPositionType.DRIVER);
		positionTypes.add(CrewPositionType.GUNNER);
		positionTypes.add(CrewPositionType.COMMANDER);

		ArrayList<CrewPositionType> positionTypes2 = new ArrayList<>();
		positionTypes2.add(CrewPositionType.GUNNER);

		Trooper pilotTrooper = new Trooper();
		Trooper copilotTrooper = new Trooper();
		Trooper topgunnerTrooper = new Trooper();

		CrewMember pilot = new CrewMember(pilotTrooper);
		CrewMember copilot = new CrewMember(copilotTrooper);
		CrewMember topgunner = new CrewMember(topgunnerTrooper);

		CrewPosition pos1 = new CrewPosition("Pilot", pilot, positionTypes, directions, null);
		CrewPosition pos2 = new CrewPosition("Copilot", copilot, positionTypes, directions, null);
		CrewPosition pos3 = new CrewPosition("Topgunner", topgunner, positionTypes2, directions, null);

		compartments.add(new CrewCompartment("OPERATORS", List.of(pos1, pos2)));
		compartments.add(new CrewCompartment("EXTERNAL GUNNER", List.of(pos3)));

		Vehicle vehicle = new Vehicle("TX130", compartments, null,"ARMOR");
		vehicle.setVehicleCallsign("Hitman");
		
		var operators = vehicle.getCrewCompartment("OPERATORS");
		var pilotPosition = operators.getCrewPosition("Pilot");
		var copilotPosition = operators.getCrewPosition("Copilot");
		var external = vehicle.getCrewCompartment("EXTERNAL GUNNER");
		var topgunnerPosition = external.getCrewPosition("Topgunner");
		
		assertEquals(vehicle.getVehicleCallsign(), "Hitman");

		assertEquals(vehicle.getVehicleType(), "TX130");
		assertEquals(operators.getCompartmentName(), "OPERATORS");
		assertEquals(pilotPosition.getPositionName(), "Pilot");
		assertEquals(pilotPosition.getFieldOfView().size(), 12);
		assertEquals(copilotPosition.getPositionName(), "Copilot");
		assertEquals(copilotPosition.getFieldOfView().size(), 12);
		assertEquals(external.getCompartmentName(), "EXTERNAL GUNNER");
		assertEquals(topgunnerPosition.getPositionName(), "Topgunner");
		assertEquals(topgunnerPosition.getFieldOfView().size(), 12);

		assertEquals(vehicle.getCrewPosition("Pilot").getPositionName(), "Pilot");
		assertEquals(vehicle.getCrewPosition("Copilot").getPositionName(), "Copilot");
		assertEquals(vehicle.getCrewPosition("Topgunner").getPositionName(), "Topgunner");
		
		

	}
	
	@Test
	public void readVehicleTest() {
		
		try {
			var vehicle = VehicleXmlReader.readVehicle("Test Callsign","TX130");
			assertEquals(1000, vehicle.getShieldGenerator().getMaxValue());
			assertEquals(150, vehicle.getShieldGenerator().getRechargeRate());
			
			vehicle.setVehicleCallsign("Hitman");
			
			var operators = vehicle.getCrewCompartment("OPERATORS");
			
			assertEquals(100, operators.getShieldGenerator().getMaxValue());
			assertEquals(25, operators.getShieldGenerator().getRechargeRate());
			
			var pilotPosition = operators.getCrewPosition("Pilot");
			var copilotPosition = operators.getCrewPosition("Copilot");
			var external = vehicle.getCrewCompartment("EXTERNAL GUNNER");
			var topgunnerPosition = external.getCrewPosition("Topgunner");
			
			assertEquals(vehicle.getVehicleCallsign(), "Hitman");

			assertEquals(vehicle.getVehicleType(), "TX130");
			assertEquals(operators.getCompartmentName(), "OPERATORS");
			assertEquals(pilotPosition.getPositionName(), "Pilot");
			assertEquals(pilotPosition.getFieldOfView().size(), 12);
			assertEquals(copilotPosition.getPositionName(), "Copilot");
			assertEquals(copilotPosition.getFieldOfView().size(), 12);
			assertEquals(external.getCompartmentName(), "EXTERNAL GUNNER");
			assertEquals(topgunnerPosition.getPositionName(), "Topgunner");
			assertEquals(topgunnerPosition.getFieldOfView().size(), 12);

			assertEquals(vehicle.getCrewPosition("Pilot").getPositionName(), "Pilot");
			assertEquals(vehicle.getCrewPosition("Copilot").getPositionName(), "Copilot");
			assertEquals(vehicle.getCrewPosition("Topgunner").getPositionName(), "Topgunner");
			
			var vmd = vehicle.movementData;
			var feature = new Feature("Mud", 0);
			var myList = new ArrayList<Feature>();
			myList.add(feature);
			var mudHex = new Hex(0,0,myList,0,0,0);
			assertEquals(5, vmd.movementSpeeds.size());
			assertEquals(14, vmd.getMaxMoveSpeed(mudHex));
			assertEquals(6, vmd.hullTurnRate);
			assertEquals(6, vmd.acceleration);
			assertEquals(6, vmd.deceleration);
			assertEquals(8, vmd.boostAcceleration);
			assertEquals(false, vmd.boostUsed);
			assertEquals(8, vmd.boostAcceleration);
			assertEquals(2, vmd.boostRecovery);
			
			assertEquals(8, vehicle.smokeData.launchedSmoke.diameter);
			assertEquals(6, vehicle.smokeData.trailingSmoke.diameter);
			assertEquals(10, vehicle.smokeData.trailingSmokeTurns);
			assertEquals(3, vehicle.smokeData.smokeLaunches);
			
			assertEquals(2, vehicle.turretData.turrets.size());
			var main = vehicle.turretData.turrets.get(0);
			assertEquals("Main Guns", main.turretName);
			assertEquals(20, main.facingWidth);
			assertEquals(40, main.rotationSpeedPerPhaseDegrees);
			assertEquals(true, main.canRotate == true);
			
			var externalTurret = vehicle.turretData.turrets.get(1);
			assertEquals("External Turret", externalTurret.turretName);
			assertEquals(180, externalTurret.facingWidth);
			assertEquals(0, externalTurret.rotationSpeedPerPhaseDegrees);
			assertEquals(false, externalTurret.canRotate == true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
