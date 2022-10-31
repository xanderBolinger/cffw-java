package CorditeExpansion;

import java.util.ArrayList;

public class FloatingTextManager {
	private static ArrayList<FloatingText> floatingText = new ArrayList<>();
	
	public static void addFloatingText(Cord cord, String content) {
		
		for(FloatingText text : floatingText) {
			
			if(text.cord.compare(cord)) {
				text.content += "\n" + content;
				return;
			}
			
		}
		
		floatingText.add(new FloatingText(cord, content));
		
	}
	
	public static void clearFloatingText() {
		floatingText.clear();
	}
	
	public static int size() {
		return floatingText.size();
	}
	
	public static ArrayList<FloatingText> getFloatingText() {
		return floatingText;
	}
	
}
