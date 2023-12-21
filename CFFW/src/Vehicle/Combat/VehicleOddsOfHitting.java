package Vehicle.Combat;

import Spot.Utility.SpotVisibility;
import UtilityClasses.DiceRoller;
import Vehicle.Vehicle;
import Vehicle.Data.CrewPosition;
import Vehicle.Spot.VehicleSpotCalculator;

public class VehicleOddsOfHitting {
	
	int sl;
	int odds; 
	int aimValue;
	int rangeAlm;
	int nightWeatherMod;
	int rangeHexes;
	int balisticAccuracy;
	int alm;
	int palm;
	int sizeAlm;
	CrewPosition crewPosition;
	int visibilityAlm;
	int movingTargetValue;
	int movingShooterValue;
	int speed;
	int eal;
	int roll;
	Vehicle vehicle;
	int shotsFired;
	
	boolean fullAuto;
	int shotsHit;
	String fullAutoResults;
	int secondFullAutoRoll;
	
	public VehicleOddsOfHitting(Vehicle vehicle, CrewPosition crewPosition, 
			VehicleTurret turret, int ammoIndex, int shotsFired) {
		this.shotsFired = shotsFired;
		this.fullAuto = shotsFired > 0;
		this.vehicle = vehicle;
		this.crewPosition = crewPosition;
		var target = turret.vehicleAimTarget;
		turret.fired = true;
		turret.timeSpentReloading = 0;
		vehicle.spotData.fired = true;
		var ammo = turret.ammunitionTypes.get(ammoIndex);
		rangeHexes = turret.getRangeToTargetIn20YardHexes(vehicle);
		
		sl = crewPosition.crewMemeber.crewMember.sl;
		aimValue = turret.getAimValue();
		rangeAlm = VehicleRangeAlm.getAlmForRange(rangeHexes);
		nightWeatherMod = VehicleSpotCalculator.getNightTimeMods(vehicle, crewPosition.spotData);
		var smokeMod = SpotVisibility.getSmokeModifier(
				VehicleSpotCalculator.isThermalEquipped(vehicle, crewPosition),
				vehicle.movementData.location, target.getTargetCord());
		visibilityAlm = -nightWeatherMod - smokeMod;
		alm = sl + aimValue + rangeAlm + visibilityAlm; 

		sizeAlm = target.getTargetSizeAlm(vehicle);
		
		palm = fullAuto ? ammo.getPalm(rangeHexes) : Integer.MIN_VALUE;
		
		if(palm > sizeAlm)
			sizeAlm = palm;
		
		balisticAccuracy = ammo.getBalisticAccuracy(rangeHexes);

		speed = target.getTargetSpeedInHexesPerTurn();
		
		movingTargetValue = VehicleShotCalculator.getMovingTargetAccuracy(target, rangeHexes, ammo.ammoType,
				turret.movingTargetAccuracyMod, speed);
		
		movingShooterValue = vehicle.movementData.speed != 0 ? 
				VehicleMovingShooterAccuracy.getMovingShooterAccuracy(
						rangeHexes, Math.abs(vehicle.movementData.speed)) + turret.movingShooterAccuracyMod
				: Integer.MAX_VALUE;
		
		eal = alm + sizeAlm;
		
		if(movingTargetValue < alm && movingTargetValue < balisticAccuracy 
				&& movingTargetValue < movingShooterValue)
			eal = movingTargetValue + sizeAlm;
		else if(balisticAccuracy < alm && balisticAccuracy < movingTargetValue
				&& balisticAccuracy < movingShooterValue)
			eal = balisticAccuracy + sizeAlm;
		else if(movingShooterValue < alm && movingShooterValue < balisticAccuracy &&
				movingShooterValue < movingTargetValue)
			eal = movingShooterValue + sizeAlm;
		
		odds = VehicleOddsOfHittingTable.getOddsOfHitting(eal);
		
	}
	
	public void roll() {
		roll = DiceRoller.roll(0, 99);
	}
	
	public void getHits() {
		shotsHit = roll > odds ? 0 : fullAuto ? getFullAutoHits() : 1;
	}
	
	private int getFullAutoHits() {
		
		var fullAutoString = VehicleFullAutoTable.getFullAutoString(shotsFired, palm);
		
		if(fullAutoString.contains("*")) {
			var rsltHits =  Integer.parseInt(fullAutoString.substring(1, fullAutoString.length()-1));
			return rsltHits <= shotsFired ? rsltHits : shotsFired;
		} else {
			
			var tn = Integer.parseInt(fullAutoResults);
			secondFullAutoRoll = DiceRoller.roll(0, 99);
			return secondFullAutoRoll <= tn ? 1 : 0; 
		}
		
	}
	
	public String getOddsResults() {
		String oddsResults = "Shooter "+crewPosition.crewMemeber.crewMember.name+
				", SL: " + sl + ", Aim Value: " + aimValue + ", Range Hexes: " + rangeHexes +
				", Range ALM: " +rangeAlm+", BA: "+balisticAccuracy+", Size ALM: "+sizeAlm
				+", Visibility Alm: " + visibilityAlm
				+", ALM: " + alm + (movingTargetValue != Integer.MAX_VALUE 
					? ", MTA: " + movingTargetValue : "") + ", Target Speed: " + speed + 
				(movingShooterValue != Integer.MAX_VALUE 
				? ", MSTA: " + movingShooterValue : "") + ", Shooter Speed: "+ vehicle.movementData.speed
				+", EAL: "+eal+", Odds: " + odds+", Roll: " + roll+", Shots Fired: "+shotsFired
				+ (fullAuto ? ", PALM: "+palm +", Rslts: "+fullAutoResults + (
						fullAutoResults.contains("*")? "" : ", Second Roll: "+secondFullAutoRoll): "");
		
		return oddsResults;
	}
	
	
}
