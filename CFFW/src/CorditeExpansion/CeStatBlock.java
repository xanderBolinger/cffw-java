package CorditeExpansion;

import Trooper.Trooper;

public class CeStatBlock {

	
	public double quickness; 
	
	
	public CeStatBlock(Trooper trooper) {
		
		quickness = trooper.maximumSpeed.get();
		
	}
	
}
