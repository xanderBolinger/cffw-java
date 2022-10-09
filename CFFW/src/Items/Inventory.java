package Items;

import java.io.Serializable;
import java.util.ArrayList;

import Items.Container.ContainerType;
import Items.Item.ItemType;
import Trooper.Trooper;

public class Inventory implements Serializable {

	public ArrayList<Container> containers = new ArrayList<>();
	public Trooper trooper; 
	
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
		
		trooper.encumberance += trooper.armor.armorWeight;
		trooper.encumberance += Math.round(weight) + trooper.encumberanceModifier;
		
		
	}
	
	public void addContainer(ContainerType type) {
		containers.add(new Container(type));
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

				if (weaponType == item.weaponType && ammoType == item.ammoType) {
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

				if (itemType == item.weaponType) {
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
		for (Container container : containers) {

			for (Item item : container.items) {

				if (itemName.equals(item.getItemName())) {
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

	public void removeItem(String itemName) throws Exception {

		Item removalItem = null;
		Container removalContainer = null;
		boolean itemFound = false;
		for (Container container : containers) {

			for (Item item : container.items) {

				if (itemName.equals(item.getItemName())) {
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

}
