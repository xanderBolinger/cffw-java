package testPackage;

import org.junit.Test;

import Items.Container.ContainerType;
import Items.Inventory;
import Items.Item;
import Items.Item.ItemType;
import Items.Weapons;
import Trooper.Trooper;

import static org.junit.Assert.assertEquals;

public class InventoryTest {

	@Test
	public void testUnitTest() {
		assertEquals(1, 1);
	}
	
	@Test
	public void inventoryTest() throws Exception {
		
		Inventory inv = new Inventory(new Trooper());
		
		inv.addContainer(ContainerType.Belt);
		
		assertEquals(1, inv.containers.size());
		assertEquals(ContainerType.Belt, inv.containers.get(0).containerType);
		assertEquals("Belt", inv.containers.get(0).name);
		
		inv.containers.get(0).items.add(new Item(new Weapons().findWeapon("Class-A Thermal Detonator")));
		assertEquals("Class-A Thermal Detonator", inv.containers.get(0).items.get(0).getItemName());
		
		
		inv.removeItem("Class-A Thermal Detonator");
		
		
		assertEquals(0, inv.containers.get(0).items.size());
		
		inv.containers.get(0).addItem(ItemType.ClassAThermalDetonator);
		
		assertEquals("Class-A Thermal Detonator", inv.containers.get(0).items.get(0).getItemName());
		inv.removeItem(ItemType.ClassAThermalDetonator);
		inv.addItem(ItemType.DC40);
		assertEquals("DC40", inv.containers.get(0).items.get(0).getItemName());
		inv.addItems(ItemType.DC40, ItemType.HEAT, 10);
		assertEquals("DC40: HEAT round.", inv.containers.get(0).items.get(1).getItemName());
		assertEquals("DC40: HEAT round.", inv.containers.get(0).items.get(10).getItemName());
		
		inv.removeItem(ItemType.DC40, ItemType.HEAT);
		assertEquals(10, inv.containers.get(0).items.size());
		assertEquals("DC40: HEAT round.", inv.containers.get(0).items.get(1).getItemName());
		assertEquals("DC40: HEAT round.", inv.containers.get(0).items.get(9).getItemName());
		assertEquals("DC40", inv.containers.get(0).items.get(0).getItemName());
		inv.removeItem(0);
		assertEquals("DC40: HEAT round.", inv.containers.get(0).items.get(0).getItemName());
		
		Item item = new Item(ItemType.ClassAThermalDetonator);
		
		assertEquals(true, item.isWeapon());
		assertEquals(false, item.isRound());
		assertEquals(true, new Item(ItemType.DC40, ItemType.HEAT).isRound());
		
		inv.removeItem("DC40: HEAT round.");
		
	}
	
	
}