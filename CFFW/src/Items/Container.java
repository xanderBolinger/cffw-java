package Items;

import java.io.Serializable;
import java.util.ArrayList;

import Items.Item.ItemType;

public class Container implements Serializable {

	public String name;
	public ContainerType containerType; 
	public ArrayList<Item> items = new ArrayList<>();
	public double containerWeight = 0;
	public enum ContainerType {
		Belt
	}
	
	public Container(ContainerType containerType) {
		this.containerType = containerType;
		
		if(containerType == ContainerType.Belt) {
			name = "Belt";
			containerWeight = 0.5;
		}
		
	}
	
	public void addItem(ItemType weaponType, ItemType ammoType) throws Exception {
		items.add(new Item(weaponType, ammoType));
	}
	
	public void addItems(ItemType weaponType, ItemType ammoType, int count) throws Exception {
		
		for(int i = 0; i < count; i++) {
			addItem(weaponType, ammoType);
		}
		
	}

	public void addItem(ItemType itemType) throws Exception {
		items.add(new Item(itemType));
	}
	
	public void addItems(ItemType itemType, int count) throws Exception {
		
		for(int i = 0; i < count; i++) {
			addItem(itemType);
		}
		
	}
	
	@Override
	public String toString() {
		return name + ", items: "+items.size();
	}
	
}
