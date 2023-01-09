package Ship;

import java.util.ArrayList;

public class Electronics {

	public ArrayList<ElectronicCell> cells;
	
	public Electronics(ArrayList<ElectronicCell> cells) {
		this.cells = cells;
	}
	
	public void destroyElectronics() {
		
		ElectronicCell destroy = null; 
		
		for(ElectronicCell cell : cells) {
			if(!cell.destroyed) {
				destroy = cell; 
				break;
			}
		}
		
		if(destroy != null)
			destroy.destroyed = true;
		
	}
	
	
}
