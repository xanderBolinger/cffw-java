package Items;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.poi.util.SystemOutLogger;

import Conflict.GameWindow;
import Items.Container.ContainerType;
import Items.Item.ItemType;
import Trooper.Trooper;

public class Inventory implements Serializable {

	public ArrayList<Container> containers = new ArrayList<>();
	public transient Trooper trooper; 
	
	public Inventory(Trooper trooper) {
		this.trooper = trooper;
	}

	public void setEncumberance() {
		trooper.encumberance = 0; 
		
		double weight = 0; 
		for(Container container : containers) {
			weight += container.containerWeight;
			for(Item item : container.items) {
				weight += item.itemWeight;
			}
		}
		
		if(trooper.armor != null)
			trooper.encumberance += trooper.armor.armorWeight;
		trooper.encumberance += Math.round(weight) + trooper.encumberanceModifier;
		
		if(trooper.fatigueSystem != null) {
			trooper.fatigueSystem.CalcAV();
			System.out.println("AV: "+trooper.fatigueSystem.analeticValue);
		}
	}
	
	public void addContainer(ContainerType type) {
		containers.add(new Container(type));
	}

	public void addItem(Item item) throws Exception {
		if (containers.size() < 1) {
			throw new Exception("Failed to add item. No container.");
		}

		containers.get(0).addItem(item);
		setEncumberance();
	}
	
	public void addItem(ItemType itemType) throws Exception {

		if (containers.size() < 1) {
			throw new Exception("Failed to add item. No container.");
		}

		containers.get(0).addItem(itemType);
		setEncumberance();
	}

	public void addItem(ItemType weaponType, ItemType ammoType) throws Exception {

		if (containers.size() < 1) {
			throw new Exception("Failed to add item. No container.");
		}

		containers.get(0).addItem(weaponType, ammoType);
		setEncumberance();
	}

	public void addItems(ItemType itemType, int count) throws Exception {
		for (int i = 0; i < count; i++)
			addItem(itemType);
		setEncumberance();
	}

	public void addItems(ItemType weaponType, ItemType ammoType, int count) throws Exception {
		for (int i = 0; i < count; i++)
			addItem(weaponType, ammoType);
		setEncumberance();
	}

	public void removeItem(int index) throws Exception {
		int count = 0;
		Item removeItem = null;
		Container removalContainer = null;
		boolean itemFound = false;
		for (Container container : containers) {
			for (Item item : container.items) {
				if (index == count) {
					removalContainer = container;
					removeItem = item;
					itemFound = true;
					break;
				}

				count++;
			}
			if (itemFound)
				break;
		}

		if (removeItem == null) {
			throw new Exception("Item at index not found. Item Count: " + count + ", Target Index: " + index);
		} else {
			removalContainer.items.remove(removeItem);
		}
		setEncumberance();
	}

	public void removeItem(ItemType weaponType, ItemType ammoType) throws Exception {

		Item removalItem = null;
		Container removalContainer = null;
		boolean itemFound = false;
		for (Container container : containers) {

			for (Item item : container.items) {
				if (!item.isRound())
					continue;

				if (weaponType == item.itemType && ammoType == item.ammoType) {
					removalItem = item;
					removalContainer = container;

					itemFound = true;
					break;
				}

			}

			if (itemFound)
				break;

		}

		if (removalItem == null) {
			throw new Exception("Item of Type: " + weaponType + "/ " + ammoType + ", does not exist in the inventory.");
		} else {
			removalContainer.items.remove(removalItem);
		}
		setEncumberance();
	}

	public void removeItem(ItemType itemType) throws Exception {

		Item removalItem = null;
		Container removalContainer = null;
		boolean itemFound = false;
		for (Container container : containers) {

			for (Item item : container.items) {

				if (itemType == item.itemType) {
					removalItem = item;
					removalContainer = container;

					itemFound = true;
					break;
				}

			}

			if (itemFound)
				break;

		}

		if (removalItem == null) {
			throw new Exception("Item of Type: " + itemType + ", does not exist in the inventory.");
		} else {
			removalContainer.items.remove(removalItem);
		}
		setEncumberance();
	}

	public boolean containsItem(String itemName) {
		Item removalItem = null;
		boolean itemFound = false;
		System.out.println("Search Item: "+itemName);
		for (Container container : containers) {

			for (Item item : container.items) {
				System.out.println("inv item name: "+item.getItemName());
				if (item.getItemName().contains(itemName)) {
					removalItem = item;

					itemFound = true;
					break;
				}

			}

			if (itemFound)
				break;

		}

		if (removalItem == null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public Item getItem(String itemName) {
		for (Container container : containers) {

			for (Item item : container.items) {
				System.out.println("inv item name: "+item.getItemName());
				if (item.getItemName().contains(itemName)) {
					return item;
				}

			}


		}

		return null; 
	}

	public void removeItem(String itemName) throws Exception {

		Item removalItem = null;
		Container removalContainer = null;
		boolean itemFound = false;
		for (Container container : containers) {

			for (Item item : container.items) {

				if (item.getItemName().contains(itemName)) {
					removalItem = item;
					removalContainer = container;

					itemFound = true;
					break;
				}

			}

			if (itemFound)
				break;

		}

		if (removalItem == null) {
			throw new Exception("Item of name: " + itemName + ", does not exist in the inventory.");
		} else {
			//System.out.println("Remove: "+itemName);
			removalContainer.items.remove(removalItem);
		}
		setEncumberance();
	}

	public ArrayList<String> getItems() {

		ArrayList<String> itemStrings = new ArrayList<>();

		for (Container container : containers) {
			for (Item item : container.items) {
				itemStrings.add(item.getItemName());
			}
		}

		return itemStrings;

	}
	
	public ArrayList<Item> getItemsArray() {

		ArrayList<Item> items = new ArrayList<>();

		for (Container container : containers) {
			for (Item item : container.items) {
				items.add(item);
			}
		}

		return items;

	}

	public boolean fireShots(int shots, Weapons weapon) {
		
		for(Item item : getItemsArray()) {
			if(item.isRound() && item.weapon.name.equals(weapon.name) && !item.ammo.depleted) {
				if(item.ammo.shots - item.ammo.firedShots - shots > 0) {
					item.ammo.firedShots += shots;
					shots = 0;
					break; 
				} else {
					shots = 0;
					System.out.println("Depleted");
					item.ammo.depleted = true;
					break;
				}
				 
			}
			
		}
		
		int ammo = 0; 
		
		for(Item item : getItemsArray()) {
			if(item.isRound() && item.weapon.name.equals(trooper.wep) && !item.ammo.depleted) {
				ammo += item.ammo.shots - item.ammo.firedShots;
				 
			}
			
		}
		
		trooper.ammo = ammo; 
		
		if(shots > 0)
			return false;
		else 
			return true;
	}
	
	public int launcherAmmoCheck(Weapons wep, PCAmmo pcAmmo, int shots) {		
		System.out.println("Launcher ammo check");
		System.out.println("wep name: "+wep.name);
		System.out.println("PC ammo name: "+pcAmmo.name);
		if(pcAmmo.linked) {
			System.out.println("Linked returning shots: "+shots);
			return shots;
		}
		
		if(wep.type.equals("Static")) {
			if(wep.ammoLoaded <= 0)
				return 0; 
			wep.ammoLoaded-=wep.fullAutoROF; 
			return wep.ammoLoaded < 0 ? shots + wep.ammoLoaded : shots;
		}
		
		String name = wep.name + ": "
				+ pcAmmo.name + " round";
		System.out.println("Round name: "+name);
		Item round = trooper.inventory.getItem(name);
		
		if (round == null) {
			System.out.println("Round is null, could not make attack");
			GameWindow.gameWindow.conflictLog
					.addNewLineToQueue("Could not make attack, trooper does not have item. Name: " + name);
			return 0;
		}

		int canFire = 0;
		
		System.out.println("Launcher Shots: "+shots);
		while(shots > 0 && trooper.inventory.containsItem(name)) {
			System.out.println("While loop");
			if(round.ammo.shots > 1 && round.ammo.shots < round.ammo.firedShots + 1) {
				round.ammo.firedShots++; 
				System.out.println("Round fired shots");
			} else {
				try {
					System.out.println("remove item");
					trooper.inventory.removeItem(name);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			shots--;
			canFire++;
		}
		
		

		return canFire;
	}
	
}
