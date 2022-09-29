package testPackage;


import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Artillery.Artillery;
import Artillery.FireMission;
import Artillery.Artillery.BatteryType;
import Trooper.Trooper;

class ArtilleryFullTest {

	@Test
	void test() {
		
		Trooper spotter = new Trooper();
		spotter.per = 20; 
		spotter.navigation = 60; 
		spotter.combatActions = 6; 
		
		ArrayList<Artillery> batteries = new ArrayList<Artillery>();
		
		batteries.add(new Artillery(BatteryType.M107, 6));
		batteries.add(new Artillery(BatteryType.M107, 6));
		
		FireMission fireMission = new FireMission(spotter, batteries, true, 20, 20, true, null);
		
		
		fireMission.rangeShot(1, 0);
		
		fireMission.setPlotTime();
		
		while(fireMission.actionsToPlotted != 0) {
			fireMission.advanceTime();
		}
		
		fireMission.rangeShot(1, 0);
		
		while(fireMission.actionsToFire != 0) {
			fireMission.advanceTime();
		}
		
		while(fireMission.airborneShots.size() > 0) {
			fireMission.advanceTime();
		}
		
		
	}

}
