import java.util.ArrayList;

public class multiDimensionalArrayList {

	public static void main(String[] args) {
		
		
		ArrayList<Integer> range = new ArrayList<Integer>(8);
		range.add(0, 20);
		range.add(1, 40);
		range.add(2, 80);
		range.add(3, 140);
		range.add(4, 200);
		range.add(5, 400);
		range.add(6, 600);
		range.add(7, 800);
		
		ArrayList<Integer> pen = new ArrayList<Integer>(8);
		pen.add(0, 51);
		pen.add(1, 48);
		pen.add(2, 43);
		pen.add(3, 37);
		pen.add(4, 32);
		pen.add(5, 19);
		pen.add(6, 11);
		pen.add(7, 7);
		
		ArrayList<Integer> damageClass = new ArrayList<Integer>(8);
		damageClass.add(0, 8);
		damageClass.add(1, 8);
		damageClass.add(2, 7);
		damageClass.add(3, 7);
		damageClass.add(4, 6);
		damageClass.add(5, 5);
		damageClass.add(6, 4);
		damageClass.add(7, 4);
		
		ArrayList ballisticData = new ArrayList();
		for( int i = 0; i < 8; ++i ) {
		  ArrayList secondLevelArrayList = new ArrayList();
		  secondLevelArrayList.add(0, range.get(i));
		  secondLevelArrayList.add(1, pen.get(i));
		  secondLevelArrayList.add(2, damageClass.get(i));
		  ballisticData.add( secondLevelArrayList );
		 
		}
		
		
		
		
		
		
		
		 //System.out.println("3d array: "+multiDimensionalArrayList.toString());
		
	}
	
}
