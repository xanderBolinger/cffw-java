package Conflict;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CorditeExpansion.Cord;

public class Smoke implements Serializable {

	List<ArrayList<String>> smokeEffectsTable = new ArrayList<ArrayList<String>>();
	List<Integer> windSpeedHpp;
	List<Integer> elapsedTimePhases;

	public ArrayList<SmokeStats> deployedSmoke = new ArrayList<SmokeStats>();
	
	public Smoke() {

		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("B", "B", "B", "B", "B", "B", "B", "B")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("B", "B", "B", "B", "B", "B", "B", "-9")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("B", "B", "B", "B", "B", "B", "-11", "-5")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("B", "B", "B", "B", "-12", "-12", "-9", "-3")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("B", "B", "B", "B", "-11", "-10", "-8", "-1")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("B", "B", "B", "-13", "-10", "-9", "-5", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("B", "B", "-13", "-10", "-9", "-7", "-4", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("B", "-13", "-12", "-10", "-8", "-6", "-3", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-13", "-12", "-11", "-9", "-7", "-5", "-2", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-12", "-11", "-10", "-9", "-6", "-5", "-1", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-11", "-10", "-9", "-8", "-5", "-4", "-1", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-11", "-10", "-8", "-7", "-4", "-3", "0", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-10", "-9", "-7", "-6", "-3", "-2", "0", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-9", "-7", "-6", "-5", "-2", "-1", "0", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-8", "-7", "-5", "-4", "-1", "0", "0", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-7", "-6", "-5", "-3", "-1", "0", "0", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-6", "-5", "-4", "-2", "0", "0", "0", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-5", "-4", "-3", "-2", "0", "0", "0", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-5", "-4", "-2", "-1", "0", "0", "0", "0")));
		smokeEffectsTable.add(new ArrayList<String>(Arrays.asList("-4", "-3", "-1", "0", "0", "0", "0", "0")));

		windSpeedHpp = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30);
		elapsedTimePhases = Arrays.asList(5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95,
				100);

	}
	
	public String getSmokeStats(Cord cord) {
		String rslts = "";
		
		for(SmokeStats smoke : deployedSmoke) {
			if(!smoke.deployedHex.compare(cord)) {
				continue;
			}

			if(!rslts.equals(""))
				rslts += "<br>";
			else 
				rslts += "Smoke: <br>";
			
			int row = elapsedTimeRow(smoke.getElapsedActionsAfterDuration());

			String result = smokeEffectsTable.get(row).get(8 - smoke.diameter);
			
			rslts += "Diameter: "+smoke.diameter+", Duration: "+smoke.duration+", Elapsed Actions: "
					+smoke.getElapsedActionsTotal()+", Table: "+result;
			
		}
		
		return rslts;
	}

	public int getConcealment(Cord cord) {
		int concealment = 0;
		
		for(SmokeStats smoke : deployedSmoke) {
			if(!smoke.deployedHex.compare(cord)) {
				continue;
			}

			concealment += concealment(smoke.diameter, smoke.getElapsedActionsAfterDuration());
			
		}
		
		return concealment;
		
	}
	
	public int getAlm(Cord cord) {
		int alm = 0; 
		
		for(SmokeStats smoke : deployedSmoke) {
			if(!smoke.deployedHex.compare(cord)) {
				continue;
			}

			alm += almPenalty(smoke.diameter, smoke.getElapsedActionsAfterDuration());
			
		}
		
		
		if(alm < -14)
			return -14;
		return alm;
	}
	
	public void advanceTime() {
		for(SmokeStats smoke : deployedSmoke) {
			smoke.increaseElapsedActions();
		}
		
		ArrayList<SmokeStats> disipatedSmoke = new ArrayList<>();
		
		for(SmokeStats smoke : deployedSmoke) {
			if(smoke.getElapsedActionsTotal() > smoke.duration && almPenalty(smoke.diameter, smoke.getElapsedActionsAfterDuration())> -5) {
				disipatedSmoke.add(smoke);
			}
		}
		
		for(SmokeStats smoke : disipatedSmoke) {
			deployedSmoke.remove(smoke);
		}
		
	}
	
	public void deploySmoke(Cord cord, SmokeStats smokeStats) {
		var smoke = new SmokeStats(smokeStats.smokeType);
		smoke.deployedHex = new Cord(cord.xCord, cord.yCord);
		deployedSmoke.add(smoke);
	}
	
	
	public int windSpeedRow(int speedHpp) {
		
		for (int var : windSpeedHpp) {
			if (speedHpp <= var) {
				return windSpeedHpp.indexOf(var);
			}
		}

		return -1;
	}
	
	public int elapsedTimeRow(int actions) {

		int twoSecondPhases = actions * 10;

		for (int var : elapsedTimePhases) {
			if (twoSecondPhases <= var) {
				return elapsedTimePhases.indexOf(var);
			}
		}

		return -1;
	}

	public int concealment(int diameter, int elapseActions) {

		int row = elapsedTimeRow(elapseActions);

		String result = smokeEffectsTable.get(row).get(8 - diameter);

		if (result.equals("B")) {

			return( (diameter / 3) <= 0 ? 1 : (diameter / 3));

		} else {

			return Integer.parseInt(result) / -5;

		}

	}

	public int almPenalty(int diameter, int elapseActions) {
		int row = elapsedTimeRow(elapseActions);

		String result = smokeEffectsTable.get(row).get(8 - diameter);

		if (result.equals("B")) {

			return -14;

		} else {

			return Integer.parseInt(result);

		}
	}
	
	public int concealmentDownwind(int diameter, int elapseActionsTotal, int elapsedActionsAfterDuration, int windSpeedHpp, int hexesFromSource) {

		if(windSpeedHpp * elapseActionsTotal * 10 < hexesFromSource)
			return 0;
		
		int row = windSpeedRow(windSpeedHpp);

		row += elapsedActionsAfterDuration * 2;
		row += (hexesFromSource / windSpeedHpp) - 1;
				
		if(row >= smokeEffectsTable.size())
			row = smokeEffectsTable.size() - 1;
		
		String result = smokeEffectsTable.get(row).get(8 - diameter);

		if (result.equals("B")) {

			return 2;

		} else {

			return Integer.parseInt(result) / -5;

		}

	}

	public int almPenaltyDownwind(int diameter, int elapseActionsTotal, int elapsedActionsAfterDuration, int windSpeedHpp, int hexesFromSource) {
		if(windSpeedHpp * elapseActionsTotal * 10 < hexesFromSource)
			return 0;
		
		int row = windSpeedRow(windSpeedHpp);

		row += elapsedActionsAfterDuration * 2;
		row += (hexesFromSource / windSpeedHpp) - 1;
				
		if(row >= smokeEffectsTable.size())
			row = smokeEffectsTable.size() - 1;

		String result = smokeEffectsTable.get(row).get(8 - diameter);
		
		if (result.equals("B")) {

			return -14;

		} else {

			return Integer.parseInt(result);

		}
	}

}
