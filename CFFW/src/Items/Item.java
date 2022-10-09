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
	public double itemWeight = 0; 
	
	
	public enum ItemType {
		NONE,ClassAThermalDetonator,DC15A,M5,DC15X,Z6,DC17M,DC17MRocket,DC17MSniper,DC40,RPS6,HEAT,HE,SmallArmsAmmo
	}
	
	public Item() throws Exception {
		allItems = new ArrayList<>(Arrays.asList(
				new Item(ItemType.DC15A),
				new Item(ItemType.DC15X),
				new Item(ItemType.Z6),
				new Item(ItemType.M5),
				new Item(ItemType.DC17M),
				new Item(ItemType.ClassAThermalDetonator),
				new Item(ItemType.RPS6),
				new Item(ItemType.DC40),
				new Item(ItemType.DC40, ItemType.HE),
				new Item(ItemType.DC40, ItemType.HEAT),
				new Item(ItemType.RPS6, ItemType.HE),
				new Item(ItemType.RPS6, ItemType.HEAT)
				));
	}
	
	
	public Item(ItemType itemType) throws Exception {
		this.weaponType = itemType;
		
		if(ItemType.ClassAThermalDetonator == itemType) {
			weapon = new Weapons().findWeapon("Class-A Thermal Detonator");
			itemWeight = 2; 
		} else if(ItemType.DC15A == itemType) {
			weapon = new Weapons().findWeapon("DC15A");
			itemWeight = 12; 
		} else if(ItemType.Z6 == itemType) {
			weapon = new Weapons().findWeapon("Z6");
			itemWeight = 20; 
		} else if(ItemType.DC15X == itemType) {
			weapon = new Weapons().findWeapon("DC15X");
			itemWeight = 15; 
		} else if(ItemType.DC17M == itemType) {
			weapon = new Weapons().findWeapon("DC17m");
			itemWeight = 8; 
		} else if(ItemType.DC17MRocket == itemType) {
			weapon = new Weapons().findWeapon("DC17m Rocket");
			itemWeight = 4; 
		} else if(ItemType.DC17MSniper == itemType) {
			weapon = new Weapons().findWeapon("DC17m Sniper");
			itemWeight = 4; 
		}  else if(ItemType.M5 == itemType) {
			weapon = new Weapons().findWeapon("Westar M5");
			itemWeight = 10; 
		} else if(ItemType.RPS6 == itemType) {
			weapon = new Weapons().findWeapon("RPS-6");
			itemWeight = 20; 
		} else if(ItemType.DC40 == itemType) {
			weapon = new Weapons().findWeapon("DC40");
		} else {
			throw new Exception("Could not create item. Item type: "+itemType+" not accounted for.");
		}
		
	}
	
	public Item(ItemType weaponType, ItemType ammoType) throws Exception {
		this.weaponType = weaponType;
		this.ammoType = ammoType;
		
		if(ItemType.ClassAThermalDetonator == weaponType) {
			weapon = new Weapons().findWeapon("Class-A Thermal Detonator");
			itemWeight = 2; 
		} else if(ItemType.DC15A == weaponType) {
			weapon = new Weapons().findWeapon("DC15A");
			itemWeight = 12; 
		} else if(ItemType.Z6 == weaponType) {
			weapon = new Weapons().findWeapon("Z6");
			itemWeight = 20; 
		} else if(ItemType.DC15X == weaponType) {
			weapon = new Weapons().findWeapon("DC15X");
			itemWeight = 15; 
		} else if(ItemType.DC17M == weaponType) {
			weapon = new Weapons().findWeapon("DC17m");
			itemWeight = 8; 
		} else if(ItemType.DC17MRocket == weaponType) {
			weapon = new Weapons().findWeapon("DC17m Rocket");
			itemWeight = 4; 
		} else if(ItemType.DC17MSniper == weaponType) {
			weapon = new Weapons().findWeapon("DC17m Sniper");
			itemWeight = 4; 
		}  else if(ItemType.M5 == weaponType) {
			weapon = new Weapons().findWeapon("Westar M5");
			itemWeight = 10; 
		} else if(ItemType.RPS6 == weaponType) {
			weapon = new Weapons().findWeapon("RPS-6");
			itemWeight = 20; 
		} else if(ItemType.DC40 == weaponType) {
			weapon = new Weapons().findWeapon("DC40");
		} else {
			throw new Exception("Could not create item. Item type: "+weaponType+" not accounted for.");
		}
		
		if((ItemType.DC15A == weaponType || 
				ItemType.M5 == weaponType || 
				ItemType.DC17M == weaponType || 
				ItemType.DC15X == weaponType) 
				&& ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("D1 Power Cell");
			itemWeight = 2;
		} else if(ItemType.Z6 == weaponType && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("R1 Power Cell");
			itemWeight = 4;
		} else if(ItemType.DC17MSniper == weaponType && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("DC17m Sniper Cartridge");
			itemWeight = 1;
		} else if(ItemType.DC17MRocket == weaponType && ItemType.HEAT == ammoType) {
			ammo = new PCAmmo("HEAT");
			itemWeight = 2;
		} else if(ItemType.DC17MRocket == weaponType && ItemType.HE == ammoType) {
			ammo = new PCAmmo("HE");
			itemWeight = 2;
		} else if(ItemType.RPS6 == weaponType && ItemType.HEAT == ammoType) {
			ammo = new PCAmmo("HEAT");
			itemWeight = 5;
		} else if(ItemType.RPS6 == weaponType && ItemType.HE == ammoType) {
			ammo = new PCAmmo("HE");
			itemWeight = 5;
		} else if(ItemType.DC40 == weaponType && ItemType.HEAT == ammoType) {
			ammo = new PCAmmo("HEAT");
			itemWeight = 0.2;
		} else if(ItemType.DC40 == weaponType && ItemType.HE == ammoType) {
			ammo = new PCAmmo("HE");
			itemWeight = 0.2;
		} else {
			throw new Exception("Could not create ammo. Ammo type: "+ammoType+", Weapon type: "+weaponType+", not accounted for.");
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
