package Vehicle.Combat;

import java.util.ArrayList;

public class VehicleOddsOfHitting {
	// Singleton instance
    private static VehicleOddsOfHitting instance;

    // 2D array list for range and odds of hitting
    private ArrayList<ArrayList<Integer>> rangeOddsList;

    // Private constructor for singleton
    private VehicleOddsOfHitting() {
        initializeData();
    }

    // Method to get the singleton instance
    public static VehicleOddsOfHitting getInstance() {
        if (instance == null) {
            instance = new VehicleOddsOfHitting();
        }
        return instance;
    }

    // Initialize data for the range and odds list
    private void initializeData() {
        rangeOddsList = new ArrayList<>();
        // Add the specified data to the list
        // Format: {range, odds of hitting}
        rangeOddsList.add(new ArrayList<Integer>() {{ add(34); add(99); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(33); add(99); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(32); add(99); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(31); add(99); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(30); add(99); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(29); add(99); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(28); add(99); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(27); add(98); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(26); add(96); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(25); add(95); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(24); add(90); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(23); add(86); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(22); add(80); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(21); add(74); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(20); add(67); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(19); add(60); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(18); add(53); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(17); add(46); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(16); add(39); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(15); add(33); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(14); add(27); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(13); add(22); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(12); add(18); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(11); add(15); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(10); add(12); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(9); add(9); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(8); add(7); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(7); add(6); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(6); add(5); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(5); add(4); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(4); add(3); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(3); add(2); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(2); add(1); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(1); add(1); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(0); add(1); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(-1); add(0); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(-2); add(0); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(-3); add(0); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(-4); add(0); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(-5); add(0); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(-6); add(0); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(-8); add(0); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(-10); add(0); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(-15); add(0); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(-17); add(0); }});
        rangeOddsList.add(new ArrayList<Integer>() {{ add(-22); add(0); }});
    }

    // Static method to get the odds of hitting based on the number of hexes
    public static int getOddsOfHitting(int eal) {
        // Iterate through the list and find a result that is less than or equal to the given range
        for (ArrayList<Integer> entry : getInstance().rangeOddsList) {
            int range = entry.get(0);
            int odds = entry.get(1);
            if (range <= eal) {
                return odds;
            }
        }
        // Return a default value if no matching range is found
        return -1; // You can change this default value as needed
    }

    // Example usage
    public static void main(String[] args) {
        // Get the odds of hitting for a specific range
        int oddsForRange = VehicleOddsOfHitting.getOddsOfHitting(20);
        System.out.println("Odds of hitting for range 20 eal: " + oddsForRange);
    }
}
