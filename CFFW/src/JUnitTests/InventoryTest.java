package JUnitTests;

import org.junit.Test;

import Items.Armor;
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
		Trooper trooper = new Trooper();
		trooper.inventory.addContainer(ContainerType.Belt);
		Inventory inv = new Inventory(trooper);
		
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
		assertEquals("DC40: HEAT round, depleted: false, Shots: 0", inv.containers.get(0).items.get(1).getItemName());
		assertEquals("DC40: HEAT round, depleted: false, Shots: 0", inv.containers.get(0).items.get(10).getItemName());
		
		inv.removeItem(ItemType.DC40, ItemType.HEAT);
		assertEquals(10, inv.containers.get(0).items.size());
		assertEquals("DC40: HEAT round, depleted: false, Shots: 0", inv.containers.get(0).items.get(1).getItemName());
		assertEquals("DC40: HEAT round, depleted: false, Shots: 0", inv.containers.get(0).items.get(9).getItemName());
		assertEquals("DC40", inv.containers.get(0).items.get(0).getItemName());
		inv.removeItem(0);
		assertEquals("DC40: HEAT round, depleted: false, Shots: 0", inv.containers.get(0).items.get(0).getItemName());
		
		Item item = new Item(ItemType.ClassAThermalDetonator);
		
		assertEquals(true, item.isWeapon());
		assertEquals(false, item.isRound());
		assertEquals(true, new Item(ItemType.DC40, ItemType.HEAT).isRound());
		
		inv.removeItem("DC40: HEAT round, depleted: false, Shots: 0");
		
		inv.containers.get(0).items.clear();
		inv.setEncumberance();
		
		inv.addItem(ItemType.DC15A);
		
		assertEquals(13, trooper.encumberance);
		
		inv.addItems(ItemType.DC15A, ItemType.SmallArmsAmmo, 3);
		inv.addItems(ItemType.ClassAThermalDetonator, 1);
		
		assertEquals(21, trooper.encumberance);
		
		inv.addItems(ItemType.DC40, 1);
		inv.addItems(ItemType.DC40, ItemType.HEAT, 10);
		
		assertEquals(24, trooper.encumberance);
		
		trooper.armor = new Armor();
		trooper.armor.Phase1CloneArmor();
		inv.setEncumberance();
		
		assertEquals(54, trooper.encumberance);
	}
	
	
	@Test
	public void cisItemTest() throws Exception {
		
		Trooper trooper = new Trooper();
		trooper.inventory.addContainer(ContainerType.Belt);
		Inventory inv = trooper.inventory;
		
		inv.addItem(ItemType.E5);
		
		assertEquals(7, trooper.encumberance);
		
		inv.addItem(ItemType.E5, ItemType.SmallArmsAmmo);
		
		assertEquals(9, trooper.encumberance);
		
		inv.addItem(ItemType.E5S);
		inv.addItem(ItemType.E5C);
		inv.addItem(ItemType.E5S, ItemType.SmallArmsAmmo);
		inv.addItem(ItemType.E5C, ItemType.SmallArmsAmmo);
		
		
		assertEquals(41, trooper.encumberance);
		
	}
	
	@Test
	public void m1Test() throws Exception {
		Trooper trooper = new Trooper();
		trooper.inventory.addContainer(ContainerType.Belt);
		Inventory inv = trooper.inventory;
		inv.addItem(ItemType.M1);
		
		assertEquals(16, trooper.encumberance);
		
		inv.addItem(ItemType.M1, ItemType.SmallArmsAmmo);
		
		assertEquals(20, trooper.encumberance);
		
	}
	
	@Test
	public void rcTest() throws Exception {
		Trooper trooper = new Trooper("Republic Commando", "Clone Trooper Phase 1");
		
		assertEquals(17, trooper.inventory.getItems().size());
		
		System.out.println("RC Start Inventory");
		for(String str : trooper.inventory.getItems())
			System.out.println(str);
		System.out.println("RC End Inventory");
		
		
		trooper.inventory.fireShots(50, new Weapons().findWeapon(trooper.wep));
		
		Item ammo = null; 
		
		for(Item item : trooper.inventory.getItemsArray()) {
			if(item.isRound()) {
				ammo = item; 
				break; 
			}
		}
		
		assertEquals(true, ammo.ammo.depleted);
		
		
		trooper.inventory.fireShots(50, new Weapons().findWeapon(trooper.wep));
		trooper.inventory.fireShots(50, new Weapons().findWeapon(trooper.wep));
		trooper.inventory.fireShots(50, new Weapons().findWeapon(trooper.wep));
		trooper.inventory.fireShots(50, new Weapons().findWeapon(trooper.wep));
		
		assertEquals(false, trooper.inventory.fireShots(50, new Weapons().findWeapon(trooper.wep)));
		
		trooper.wep = "DC17 Sniper";
		
		assertEquals(true, trooper.inventory.fireShots(4, new Weapons().findWeapon(trooper.wep)));
		assertEquals(true, trooper.inventory.fireShots(4, new Weapons().findWeapon(trooper.wep)));
		assertEquals(true, trooper.inventory.fireShots(4, new Weapons().findWeapon(trooper.wep)));
		assertEquals(true, trooper.inventory.fireShots(4, new Weapons().findWeapon(trooper.wep)));
		assertEquals(false, trooper.inventory.fireShots(1, new Weapons().findWeapon(trooper.wep)));
		
	}
	
	
	
}