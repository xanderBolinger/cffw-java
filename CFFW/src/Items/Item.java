package Items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Item implements Serializable {

	public Weapons weapon; 
	public PCAmmo ammo; 
	public ItemType weaponType = ItemType.NONE;
	public ItemType ammoType = ItemType.NONE;
	public static ArrayList<Item> allItems;
	public double itemWeight; 
	
	
	public enum ItemType {
		NONE,ClassAThermalDetonator,DC40,HEAT,HE
	}
	
	public Item() throws Exception {
		allItems = new ArrayList<>(Arrays.asList(
				new Item(ItemType.ClassAThermalDetonator),
				new Item(ItemType.DC40),
				new Item(ItemType.DC40, ItemType.HE),
				new Item(ItemType.DC40, ItemType.HEAT)
				));
	}
	
	
	public Item(ItemType itemType) throws Exception {
		this.weaponType = itemType;
		
		if(ItemType.ClassAThermalDetonator == itemType) {
			weapon = new Weapons().findWeapon("Class-A Thermal Detonator");
			itemWeight = 2; 
		} else if(ItemType.DC40 == itemType) {
			weapon = new Weapons().findWeapon("DC40");
		} else {
			throw new Exception("Could not create item. Item type: "+itemType+" not accounted for.");
		}
		
	}
	
	public Item(ItemType weaponType, ItemType ammoType) throws Exception {
		this.weaponType = weaponType;
		this.ammoType = ammoType;
		
		if(ItemType.DC40 == weaponType) {
			weapon = new Weapons().findWeapon("DC40");
		} else {
			throw new Exception("Could not create weapon. Weapon type: "+weaponType+" not accounted for.");
		}
		
		if(ItemType.HEAT == ammoType) {
			ammo = new PCAmmo("HEAT");
			itemWeight = 0.2;
		} else if(ItemType.HE == ammoType) {
			ammo = new PCAmmo("HE");
			itemWeight = 0.2;
		} else {
			throw new Exception("Could not create ammo. Ammo type: "+ammoType+" not accounted for.");
		}
		
		
	}
	
	public boolean isWeapon() {
		return weapon == null ? false : true;
	}
	
	public boolean isRound() {
			return weapon == null || ammo == null ? false : true;
	}
	
	public Item(Weapons weapon) {
		this.weapon = weapon;
	}
	
	public Item(PCAmmo ammo) {
		this.ammo = ammo; 
	}
	
	public String getItemName() {
		if(weapon == null || ammo == null)
			return weapon != null ? weapon.name : ammo.name;
		else 
			return weapon.name + ": "+ammo.name+" round.";
	}
	
	
}
