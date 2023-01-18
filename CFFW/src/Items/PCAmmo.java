package Items;

import java.io.Serializable;
import java.util.ArrayList;

import Items.Item.ItemType;

public class PCAmmo implements Serializable {

	public String name; 
	public ArrayList<Integer> pen = new ArrayList<Integer>();
	public ArrayList<Integer> dc = new ArrayList<Integer>();
	public ArrayList<String> bshc = new ArrayList<String>();
	public ArrayList<Integer> bc = new ArrayList<Integer>();
	public ArrayList<Integer> ionDamage = new ArrayList<Integer>();
	public int impactPen; 
	public int impactDc; 
	//public int ionDamage = 0; 
	public boolean energyWeapon = false; 
	public boolean ordnance = false; 
	public boolean depleted = false; 
	
	public int depletionPoints = 0; 
	public int firedShots = 0;
	public int shots = 0;
	
	public PCAmmo(String name) {
		this.name = name; 
	}
	
	public PCAmmo(String name, int shots) {
		this.name = name; 
		this.shots = shots;
	}
	
	public PCAmmo(String name, ArrayList<Integer> pen, ArrayList<Integer> dc, ArrayList<String> bshc, ArrayList<Integer> bc, int impactPen, int impactDc) {
		
		this.name = name;
		this.pen = pen; 
		this.dc = dc; 
		this.bshc = bshc; 
		this.bc = bc; 
		this.impactPen = impactPen; 
		this.impactDc = impactDc; 
		
	}
	
}
