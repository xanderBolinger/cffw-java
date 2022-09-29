package Injuries;

import java.io.Serializable;

import Items.PCAmmo;
import Items.Weapons;

public class Injuries implements Serializable{
	public int pd; 
	public String location; 
	public boolean disabled; 
	public Weapons wep; 
	
	public Injuries(int pd, String location, boolean disabled, Weapons wep) {
		
		this.pd = pd; 
		this.location = location; 
		this.disabled = disabled; 
		this.wep = wep; 
		
		
	}
	
	public String toString() {
		if(wep == null) {
			return "Injury: "+location+", PD: "+pd+", Disabled: "+disabled;
		} else {
			return "Injury: "+location+", PD: "+pd+", Disabled: "+disabled+", Weapon: "+wep.name;
		}
		
		
	}
	
}
