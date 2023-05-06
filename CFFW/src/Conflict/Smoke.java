package Conflict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CorditeExpansion.Cord;

public class Smoke {

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

	public void advanceTime() {
		for(SmokeStats smoke : deployedSmoke) {
			smoke.increaseElapsedActions();
		}
	}
	
	public void deploySmoke(Cord cord, SmokeStats smokeStats) {
		smokeStats.deployedHex = cord;
		deployedSmoke.add(smokeStats);
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

			return 2;

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
