package Vehicle.UnitTests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Conflict.Game;
import Conflict.GameWindow;
import HexGrid.HexDirectionUtility.HexDirection;
import Hexes.Feature;
import Hexes.Hex;
import Items.PersonalShield;
import Items.PersonalShield.ShieldType;
import Trooper.Trooper;
import Vehicle.Vehicle;
import Vehicle.VehicleMovement;
import Vehicle.Damage.VehicleCollision;
import Vehicle.Damage.VehicleCollision.CollisionHitLocation;
import Vehicle.Data.VehicleMovementData;
import Vehicle.HullDownPositions.HullDownPosition;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;
import Vehicle.Utilities.VehicleXmlReader;

public class VehicleMovementTests {

	GameWindow gameWindow;
	Game game;
	
	@Before
	public void SetUp() {
		game = new Game(1,1,1);
		gameWindow = new GameWindow();
		gameWindow.game = game;
		game.chits = null;
	}
	
	@Test
	public void EnterHullDownPositionTest() {
		
		var data = new VehicleMovementData(new Vehicle());
		var hullDownPosition = new HullDownPosition(HullDownStatus.HIDDEN, HullDownStatus.PARTIAL_HULL_DOWN);
		data.selectedHullDownPosition = hullDownPosition;
		hullDownPosition.capacity++;
		data.enterHullDownPosition(hullDownPosition);
		assertEquals(hullDownPosition, data.hullDownPosition);
		assertEquals(HullDownStatus.HIDDEN, data.hullDownStatus);
	}
	
	@Test 
	public void MovementHullDownTest() {
		
		var data = new VehicleMovementData(new Vehicle());
		var hullDownPosition = new HullDownPosition(HullDownStatus.HIDDEN, HullDownStatus.PARTIAL_HULL_DOWN);
		hullDownPosition.capacity++;
		data.selectedHullDownPosition = hullDownPosition;
		
		data.enterHullDownPosition(hullDownPosition);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.HIDDEN, data.hullDownStatus);
		
		data.inchForward();
		
		assertEquals(HullDownStatus.TURRET_DOWN, data.hullDownStatus);
		
		data.inchForward();
		data.exitHullDownPosition();
		assertEquals(true, data.hullDown());
		assertEquals(HullDownStatus.HULL_DOWN, data.hullDownStatus);
		
		data.inchForward();
		
		assertEquals(HullDownStatus.PARTIAL_HULL_DOWN, data.hullDownStatus);
		
		data.inchForward();
		
		assertEquals(HullDownStatus.PARTIAL_HULL_DOWN, data.hullDownStatus);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.HULL_DOWN, data.hullDownStatus);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.TURRET_DOWN, data.hullDownStatus);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.HIDDEN, data.hullDownStatus);
		
		data.exitHullDownPosition();
		assertEquals(false, data.hullDown());
		
		
		data.enterHullDownPosition(hullDownPosition);
		data.inchForward();
		data.inchForward();
		data.inchForward();
		data.exitHullDownPosition();
		assertEquals(false, data.hullDown());
	}
	
	@Test
	public void obsticleChanceTest() {
		
		assertEquals(44, VehicleCollision.hiddenObstacleCollisionChance(4));
		
	}
	
	@Test
	public void slowDownMagnitudeTest() {
		var id  = VehicleCollision.getRapidSlowdownImpactDamage(7);
		assertEquals(true, id == 6 || id == 7);
	}
	
	@Test
	public void getHitLocationTest() {
		var location = VehicleCollision.getHitLocationZoneOpen(CollisionHitLocation.Legs);
		assertEquals(true, location <= 99 && location >= 57);
		location = VehicleCollision.getHitLocationZoneOpen(CollisionHitLocation.Arms);
		assertEquals(true, location <= 16 && location >= 8);
		location = VehicleCollision.getHitLocationZoneOpen(CollisionHitLocation.Body);
		assertEquals(true, location <= 56 && location >= 18);
		location = VehicleCollision.getHitLocationZoneOpen(CollisionHitLocation.Head);
		assertEquals(true, location <= 7 && location >= 0);
	}
	
	
	@Test
	public void physicalDamageTest() {
		Trooper trooper = new Trooper();
		trooper.personalShield = new PersonalShield(ShieldType.MKIIBubbleShield);
	
		int col = VehicleCollision.getDamageCol(trooper, 11, 0);
		assertEquals(9, col);
		
		int pd = VehicleCollision.getInjuryPhysicalDamage(9, CollisionHitLocation.Arms);
		assertEquals(72, pd);
		pd = VehicleCollision.applyShieldProtection(trooper, pd, 0);
		assertEquals(193, trooper.personalShield.currentShieldStrength);
		assertEquals(true, pd <= 0);
		
	}
	
	@Test
	public void changeSpeed() throws Exception {
		var vehicle = VehicleXmlReader.readVehicle("Test Callsign","TX130");
		var md = vehicle.movementData;
		Hex hex = new Hex(0, 1, new ArrayList<Feature>(), 0, 0, 0);
		
		md.accelerate(6, hex);
		
		assertEquals(6, md.speed);
		
		md.accelerate(13, hex);
		
		assertEquals(6, md.speed);
		
		md.decelerate(0);
		
		assertEquals(0, md.speed);
		
	}
	
	@Test
	public void changeFacing() throws Exception {
		var vehicle = VehicleXmlReader.readVehicle("Test Callsign","TX130");
		var md = vehicle.movementData;
		
		md.changeFacing(true);
		
		assertEquals(1, md.changedFaces);
		assertEquals(HexDirection.AB, md.facing);
		
		md.changeFacing(true);
		md.changeFacing(true);
		md.changeFacing(true);
		md.changeFacing(true);
		md.changeFacing(true);
		
		assertEquals(6, md.changedFaces);
		assertEquals(HexDirection.D, md.facing);
		
	}
	
	@Test
	public void enterHex() throws Exception {
		
		var vehicle = VehicleXmlReader.readVehicle("Test Callsign","TX130");
		var md = vehicle.movementData;
		Hex hex = new Hex(0, 1, new ArrayList<Feature>(), 0, 0, 0);
		assertEquals(0, md.location.xCord);
		assertEquals(0, md.location.yCord);
		md.enterHex(hex);
		assertEquals(0, md.location.xCord);
		assertEquals(1, md.location.yCord);
	}
	
	@Test 
	public void smokeLauncherTest() throws Exception {
		
		
		var vehicle = VehicleXmlReader.readVehicle("Test Callsign","TX130");
		vehicle.smokeData.trailingSmokeActive = true;
		var md = vehicle.movementData;
		Hex hex = new Hex(0, 1, new ArrayList<Feature>(), 0, 0, 0);
		assertEquals(0, md.location.xCord);
		assertEquals(0, md.location.yCord);
		md.enterHex(hex);
		assertEquals(0, md.location.xCord);
		assertEquals(1, md.location.yCord);
		VehicleMovement.moveVehicle(vehicle);
		assertEquals(6, GameWindow.gameWindow.game.smoke.deployedSmoke.get(0).diameter);
		
		VehicleMovement.moveVehicle(vehicle);
		VehicleMovement.moveVehicle(vehicle);
		VehicleMovement.moveVehicle(vehicle);
		VehicleMovement.moveVehicle(vehicle);
		VehicleMovement.moveVehicle(vehicle);
		VehicleMovement.moveVehicle(vehicle);
		VehicleMovement.moveVehicle(vehicle);
		VehicleMovement.moveVehicle(vehicle);
		VehicleMovement.moveVehicle(vehicle);
		
		VehicleMovement.moveVehicle(vehicle);
		assertEquals(10, GameWindow.gameWindow.game.smoke.deployedSmoke.size());
	
		vehicle.smokeData.launchSmoke();
		vehicle.smokeData.launchSmoke();
		vehicle.smokeData.launchSmoke();
		vehicle.smokeData.launchSmoke();
		
		assertEquals(22, GameWindow.gameWindow.game.smoke.deployedSmoke.size());
	
	}
	
	
	
}
