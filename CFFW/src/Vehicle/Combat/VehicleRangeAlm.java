package Vehicle.Combat;

import java.util.ArrayList;

public class VehicleRangeAlm {

    // Singleton instance
    private static VehicleRangeAlm instance;

    // 2D array list for range and alm
    private ArrayList<ArrayList<Integer>> rangeAlmList;

    // Private constructor for singleton
    private VehicleRangeAlm() {
        initializeData();
    }

    // Method to get the singleton instance
    public static VehicleRangeAlm getInstance() {
        if (instance == null) {
            instance = new VehicleRangeAlm();
        }
        return instance;
    }

    // Initialize data for the range and alm list
    private void initializeData() {
        rangeAlmList = new ArrayList<>();
        // Add the specified data to the list
        // Format: {range, alm}
        rangeAlmList.add(new ArrayList<Integer>() {{ add(1); add(16); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(2); add(11); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(3); add(9); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(4); add(7); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(5); add(5); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(6); add(4); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(7); add(3); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(8); add(2); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(9); add(1); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(10); add(0); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(12); add(-1); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(13); add(-2); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(15); add(-3); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(17); add(-4); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(20); add(-5); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(23); add(-6); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(25); add(-7); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(30); add(-8); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(35); add(-9); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(40); add(-10); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(45); add(-11); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(50); add(-12); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(60); add(-13); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(70); add(-14); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(80); add(-15); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(95); add(-16); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(110); add(-17); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(125); add(-18); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(140); add(-19); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(165); add(-20); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(190); add(-21); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(215); add(-22); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(250); add(-23); }});
        rangeAlmList.add(new ArrayList<Integer>() {{ add(280); add(-24); }});
    }

    // Static method to get the alm based on the number of hexes
    public static int getAlmForRange(int hexes) {
        // Iterate through the list and find a result that is less than or equal to the given range
        for (ArrayList<Integer> entry : getInstance().rangeAlmList) {
            int range = entry.get(0);
            int alm = entry.get(1);
            if (range <= hexes) {
                return alm;
            }
        }
        // Return a default value if no matching range is found
        return -1; // You can change this default value as needed
    }

    // Example usage
    public static void main(String[] args) {
        // Get the ALM for a specific range
        int almForRange = VehicleRangeAlm.getAlmForRange(5);
        System.out.println("ALM for range 5 hexes: " + almForRange);
    }
}