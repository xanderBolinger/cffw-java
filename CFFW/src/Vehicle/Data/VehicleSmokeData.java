package Vehicle.Data;

import java.io.Serializable;

import Conflict.GameWindow;
import Conflict.SmokeStats;
import HexGrid.HexDirectionUtility;
import HexGrid.HexDirectionUtility.HexDirection;
import Vehicle.Vehicle;

public class VehicleSmokeData implements Serializable  {

	public boolean trailingSmokeActive;
	public int trailingSmokeTurns;
	public int remainingTrailingSmokeTurns;
	public int smokeLaunches;
	public int remainingSmokeLaunches;
	
	public SmokeStats trailingSmoke;
	public SmokeStats launchedSmoke;
	
	Vehicle vehicle;
	
	public VehicleSmokeData(SmokeStats trailingSmoke, SmokeStats launchedSmoke, int smokeLaunches,
			int trailingSmokeTurns, Vehicle vehicle)  {
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
		
		var index = HexDirection.getDirections().indexOf(md.facing);
		var dir1 = HexDirection.getDirections().get(index+1 < HexDirection.getDirections().size() ? index+1 : 0);
		var dir2 = HexDirection.getDirections().get(index-1 >= 0 ? index-1 : HexDirection.getDirections().size()-1);
		var hex1 = HexDirectionUtility.getHexInDirection(md.facing, cord, true);
		var hex2 = HexDirectionUtility.getHexInDirection(dir1, cord, true);
		var hex3 = HexDirectionUtility.getHexInDirection(dir2, cord, true);
		
		GameWindow.gameWindow.game.smoke.deploySmoke(
				hex1, launchedSmoke);
		GameWindow.gameWindow.game.smoke.deploySmoke(
				hex2, launchedSmoke);
		
		if(HexDirection.getNormalDirections().contains(md.facing))
			GameWindow.gameWindow.game.smoke.deploySmoke(
					hex3, launchedSmoke);
		
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
