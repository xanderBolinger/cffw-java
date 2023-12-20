package Vehicle.Combat;

import Vehicle.Vehicle;
import Vehicle.Data.CrewPosition;

public class VehicleShotCalculator {

	public static void fireVehicleTurret(Vehicle vehicle, CrewPosition crewPosition, 
			VehicleTurret turret) {
		if(turret.vehicleAimTarget == null)
			return;

		var target = turret.vehicleAimTarget;
		target.fired = true;
		int sl = crewPosition.crewMemeber.crewMember.sl;
		int aimValue = turret.getAimValue();
		int rangeHexes = turret.getRangeToTargetIn20YardHexes(vehicle);
		int rangeAlm = VehicleRangeAlm.getAlmForRange(rangeHexes);
		
		
		
	}
	
}
