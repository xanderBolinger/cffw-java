package Main;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		ArrayList<Integer> x = new ArrayList<>();
		ArrayList<Integer> y = new ArrayList<>();
		
		System.out.println("Input X1: ");
		
		while (true) {
			Scanner input = new Scanner(System.in);
			
			int cord = input.nextInt();

			if(x.size() == 0) {
				x.add(cord);
				System.out.println("Input Y1: ");
			} else if(x.size() == 1 && y.size() == 0) {
				y.add(cord);
				System.out.println("Input X2: ");
			} else if(x.size() == 1 && y.size() == 1) {
				x.add(cord);
				System.out.println("Input Y2: ");
			} else if(x.size() == 2 && y.size() == 1) {
				y.add(cord);
			}
			
			if(x.size() == 2 && y.size() == 2) {
				System.out.println("Diff: "+hexDif(x.get(0), y.get(0), x.get(1), y.get(1)));
				x.clear();
				y.clear();
				System.out.println("Input X1: ");
			}
			
		}

	}

	public static int hexDif(int x, int y, int x1, int y1) {
		//System.out.println("Distance: "+dist(x, y, x1, y1));
		return dist(x, y, x1, y1);
	}
	
	public static int dist(int x1, int y1,  int x2, int y2) {
		
		int du = x2 - x1;
		int dv = (y2 + Math.floorDiv(x2, 2)) - (y1 + Math.floorDiv(x1, 2));
		
		if( du >= 0 && dv >= 0 || (du < 0 && dv < 0)) {
			return Math.max(Math.abs(du), Math.abs(dv));
		} else {
			return Math.abs(du) + Math.abs(dv);
		}
		
	}
	
}
