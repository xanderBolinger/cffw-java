package CorditeExpansion;

import CeHexGrid.Chit;
import Conflict.GameWindow;
import CorditeExpansionDamage.Damage;
import Items.Weapons;
import Trooper.Trooper;

public class ThrowAble {

	public Chit chit; 
	public Weapons weapon; 
	private int impulses = 0;
	
	public boolean detonated = false;
	
	public ThrowAble(Chit chit, Weapons weapon) {
		this.chit = chit;
		this.weapon = weapon;
	}
	
	
	public void advanceTime() {
		impulses++; 
		
		if(impulses >= weapon.fuze)
			detonate();
		
	}
	
	public void detonate() {
		System.out.println("Detonate");
		detonated = true; 
		
		// Iterate over all troopers in Ce action 
		for(Trooper trooper : CorditeExpansionGame.actionOrder.getOrder()) {
			int range = GameWindow.hexDif( chit.xCord, chit.yCord, trooper.ceStatBlock.cord.xCord,trooper.ceStatBlock.cord.yCord)
					* CorditeExpansionGame.distanceMultiplier;
			System.out.println("Throwable Range: "+range);
			if(range <= 10) {
				Damage.explode(weapon, range, trooper);
			} 
		}
	}
	
}
