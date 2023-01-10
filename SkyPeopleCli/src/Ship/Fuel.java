package Ship;

import java.util.ArrayList;
import java.util.Arrays;

public class Fuel {

	private int fusion;
	public ArrayList<ArrayList<Integer>> fuelRows;
	public int spaces;
	public int rows;

	public Fuel(int rows, int spaces, int fusion) {
		fuelRows = new ArrayList<>();
		this.spaces = spaces;
		this.rows = rows;
		int columns = spaces / rows;

		for (int i = 0; i < columns; i++) {
			ArrayList<Integer> row = new ArrayList<>();
			for (int j = 0; j < rows; j++)
				row.add(1);

			fuelRows.add(row);
		}

		this.fusion = fusion;
	}

	public Fuel(Fuel other) {
		this.fusion = other.fusion;
		this.fuelRows = new ArrayList<>(other.fuelRows);
		this.spaces = other.spaces;
		this.rows = other.rows;
	}

	public void destroyFuel() {

		for (ArrayList<Integer> row : fuelRows) {
			if (row.get(0) != -1) {
				for (int i = 0; i < row.size(); i++) {
					row.set(i, -1);
				}
				break;
			}
		}

	}

	public void spendFuel(int spentFuel) {
		for (ArrayList<Integer> row : fuelRows) {
			for (int i = 0; i < row.size(); i++) {
				if (spentFuel == 0)
					return;

				if (row.get(i) == 1) {
					row.set(i, 0);
					spentFuel--;
				}

			}
		}
	}

	public int remainingFuel() {
		int remainingFuel = 0;

		for (ArrayList<Integer> row : fuelRows) {
			for (int i : row)
				if (i == 1)
					remainingFuel++;
		}

		return remainingFuel;
	}

	@Override
	public String toString() {
		String rslts = "Fusion: " + fusion + ", Rows: " + rows + ", Spaces: " + spaces + "\n";

		for (ArrayList<Integer> row : fuelRows) {
			for (int i : row) {
				if (i == 1) {
					rslts += "1,";
				} else if (i == 0) {
					rslts += "0,";
				} else if (i == -1) {
					rslts += "X,";
				}
			}

			rslts += "\n";

		}

		return rslts;
	}

}
