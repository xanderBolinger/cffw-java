package Items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import Conflict.SmokeStats.SmokeType;
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
	public boolean smoke = false; 
	public boolean airBurst = false;
	public boolean linked = true;
	public SmokeType smokeType;
	
	public int depletionPoints = 0; 
	public int firedShots = 0;
	public int shots = 0;
	
	public int[] rangeList;
	public HashMap<Integer, ExplosiveData> ammoRanges;
	
	public boolean clusterMunition;
	public int clusterRadiusHex;
	public int submunitionCountPerHex;
	
	public PCAmmo(SmokeType smokeType) {
		name = "SMOKE";
		smoke = true;
		this.smokeType = smokeType;
		rangeList = new int[0];
		ammoRanges = new HashMap<Integer, ExplosiveData>();
	}
	
	public PCAmmo(String name) {
		this.name = name; 
		rangeList = new int[0];
		ammoRanges = new HashMap<Integer, ExplosiveData>();
	}
	
	public PCAmmo(String name, int shots) {
		this.name = name; 
		this.shots = shots;
		rangeList = new int[0];
		ammoRanges = new HashMap<Integer, ExplosiveData>();
	}
	
	public PCAmmo(String name, ArrayList<Integer> pen, ArrayList<Integer> dc, ArrayList<String> bshc, ArrayList<Integer> bc, int impactPen, int impactDc) {
		
		this.name = name;
		this.pen = pen; 
		this.dc = dc; 
		this.bshc = bshc; 
		this.bc = bc; 
		this.impactPen = impactPen; 
		this.impactDc = impactDc; 
		rangeList = new int[0];
		ammoRanges = new HashMap<Integer, ExplosiveData>();
		
	}
	
	public ExplosiveData getExplosiveData(int range) {
		
		System.out.println("Get Data: "+range);
		for(var r : rangeList) {
			if(range <= r) {
				return ammoRanges.get(r);
			}
		}
		
		
		return null;
	}
	
	
}
