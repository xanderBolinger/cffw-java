package Items;

import java.io.Serializable;

public class Ammo implements Serializable {

	public String name; 
	public int damage; 
	public int damageBonus;
	public int damageMultiplier;
	public int armorDivisor;
	
	public Ammo fragmentation;
	public Ammo linked;
	public Ammo linkedFragmentation;
	
	public Ammo(String name, int damage, int damageBonus, int damageMultiplier, int armorDivsor) {
		this.name = name; 
		this.damage = damage; 
		this.damageBonus = damageBonus; 
		this.damageMultiplier = damageMultiplier;
		this.armorDivisor = armorDivsor; 
		
	}
	
	
	public Ammo(String name, int damage, int damageBonus, int damageMultiplier, int armorDivsor, Ammo fragmentation, Ammo linked, Ammo linkedFragmentation) {
		
		this.name = name; 
		this.damage = damage; 
		this.damageBonus = damageBonus; 
		this.damageMultiplier = damageMultiplier;
		this.armorDivisor = armorDivsor; 
		
		this.fragmentation = fragmentation; 
		this.linked = linked; 
		this.linkedFragmentation = linkedFragmentation;
		
		
	}
	
	
	
	
}
