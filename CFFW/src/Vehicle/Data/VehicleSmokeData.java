package Vehicle.Data;

import Conflict.GameWindow;
import Conflict.SmokeStats;
import HexGrid.HexDirectionUtility;
import Vehicle.Vehicle;

public class VehicleSmokeData {

	public boolean trailingSmokeActive;
	public int trailingSmokeTurns;
	public int remainingTrailingSmokeTurns;
	public int smokeLaunches;
	public int remainingSmokeLaunches;
	
	public SmokeStats trailingSmoke;
	public SmokeStats launchedSmoke;
	
	Vehicle vehicle;
	
	public VehicleSmokeData(SmokeStats trailingSmoke, SmokeStats launchedSmoke, int smokeLaunches,
			int trailingSmokeTurns, Vehicle vehicle) {
		this.trailingSmoke = trailingSmoke;
		this.launchedSmoke = launchedSmoke;
		this.smokeLaunches = smokeLaunches;
		this.trailingSmokeTurns = trailingSmokeTurns;
		remainingTrailingSmokeTurns = trailingSmokeTurns;
		remainingSmokeLaunches = smokeLaunches;
		this.vehicle = vehicle;
	}
	
	
	public void launchSmoke() {
		if(remainingSmokeLaunches <= 0)
			return;
		
		var md = vehicle.movementData;
		var cord = md.location;
		
		GameWindow.gameWindow.game.smoke.deploySmoke(cord, launchedSmoke);
		GameWindow.gameWindow.game.smoke.deploySmoke(
				HexDirectionUtility.getHexInDirection(md.facing, cord), launchedSmoke);
		GameWindow.gameWindow.game.smoke.deploySmoke(
				HexDirectionUtility.getHexInDirection(md.facing, cord, true), launchedSmoke);
		GameWindow.gameWindow.game.smoke.deploySmoke(
				HexDirectionUtility.getHexInDirection(md.facing, cord, false), launchedSmoke);
		
		remainingSmokeLaunches--; 
	}
	
	public void deployTrailingSmoke() {
		if(remainingTrailingSmokeTurns <= 0)
			return;
		
		var md = vehicle.movementData;
		var cord = md.location;
		GameWindow.gameWindow.game.smoke.deploySmoke(cord, trailingSmoke);
		
		remainingTrailingSmokeTurns--; 
	}
	
	public boolean canTrailSmoke() {
		return trailingSmoke != null;
	}
	
	public boolean canLaunchSmoke() {
		return launchedSmoke != null;
	}
	
	public void toggleTrailingSmoke() {
		trailingSmokeActive = !trailingSmokeActive;
	}
	
}
