package Items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Item implements Serializable {
	public static ArrayList<Item> allItems;

	public Weapons weapon;
	public PCAmmo ammo;
	public ItemType itemType = ItemType.NONE;
	public ItemType ammoType = ItemType.NONE;
	public double itemWeight = 0;

	public int magnification;
	public boolean thermalOptic;
	public int thermalValue;
	public int maxThermalRangeYards;
	public boolean cloakField;
	public boolean movementDisabled;
	public int cloakSlm;
	public boolean thermalShroud;
	public boolean camouflage;
	public int camoMod;
	public boolean nightVision;
	public int nightVisionEffectiveness;

	public enum ItemType implements Serializable {
		NONE, ClassAThermalDetonator, Nacht5SmokeGrenade,

		DC15A, DC20, M5, DC15X, Z6, DC17M, DC17MRocket, DC17MSniper, DC40, RPS6,
		
		HEAT, HE, ION, SMOKE, SmallArmsAmmo,FlameCharge,
		Frag, Krak,

		SoundStrikePattern,

		EWEBWeapon, EWEBTripod, EWEBBattery,

		M1,

		A310, EE3,

		E5, E5S, E5C, B2RR,

		M870,

		MkvbBolter, MkivHeavyBolter,MKIIIUltimaPattern,
		
		AstartesFlamer,

		m36Lasgun,

		KrakGrenade, AstartesFragGrenade,
		
		AstartesStandard,

		GoblinJavelin, ShortBow,

		GhillieSuit, Microbinoculars, ThermalMicrobinoculars, ThermalShroud, MobileStealthField, StationaryStealthField,
		NVG_Gen3, NVG_Gen4, NVG_Gen5,
	}

	public Item() throws Exception {
		allItems = new ArrayList<>(Arrays.asList(new Item(ItemType.Microbinoculars),
				new Item(ItemType.ThermalMicrobinoculars), new Item(ItemType.ThermalShroud),
				new Item(ItemType.MobileStealthField), new Item(ItemType.StationaryStealthField),
				new Item(ItemType.GhillieSuit), new Item(ItemType.NVG_Gen3), new Item(ItemType.NVG_Gen4),
				new Item(ItemType.NVG_Gen5),

				new Item(ItemType.DC15A), new Item(ItemType.DC20), new Item(ItemType.DC15X), new Item(ItemType.Z6),
				new Item(ItemType.M5), new Item(ItemType.DC17M), new Item(ItemType.DC17MSniper),
				new Item(ItemType.DC17MRocket), new Item(ItemType.RPS6), new Item(ItemType.DC40),
				new Item(ItemType.A310), new Item(ItemType.EE3), new Item(ItemType.M1), new Item(ItemType.E5),
				new Item(ItemType.E5S), new Item(ItemType.E5C), new Item(ItemType.B2RR), new Item(ItemType.M870),

				new Item(ItemType.MkvbBolter), new Item(ItemType.MkivHeavyBolter), new Item(ItemType.m36Lasgun),
				new Item(ItemType.MKIIIUltimaPattern),
				new Item(ItemType.AstartesFragGrenade), new Item(ItemType.KrakGrenade),

				new Item(ItemType.AstartesStandard),
				
				new Item(ItemType.AstartesFlamer),
				new Item(ItemType.AstartesFlamer, ItemType.FlameCharge),
				
				new Item(ItemType.SoundStrikePattern), new Item(ItemType.SoundStrikePattern, ItemType.Krak),
				new Item(ItemType.SoundStrikePattern, ItemType.Frag),

				new Item(ItemType.GoblinJavelin), new Item(ItemType.ShortBow),

				new Item(ItemType.EWEBWeapon), new Item(ItemType.EWEBTripod), new Item(ItemType.EWEBBattery),

				new Item(ItemType.ClassAThermalDetonator), new Item(ItemType.Nacht5SmokeGrenade),

				new Item(ItemType.B2RR, ItemType.HE), new Item(ItemType.B2RR, ItemType.HEAT),
				new Item(ItemType.DC17MRocket, ItemType.HE), new Item(ItemType.DC17MRocket, ItemType.HEAT),
				new Item(ItemType.DC40, ItemType.HE), new Item(ItemType.DC40, ItemType.HEAT),
				new Item(ItemType.DC40, ItemType.ION), new Item(ItemType.DC40, ItemType.SMOKE),
				new Item(ItemType.RPS6, ItemType.HE), new Item(ItemType.RPS6, ItemType.HEAT),

				new Item(ItemType.GoblinJavelin, ItemType.SmallArmsAmmo),
				new Item(ItemType.ShortBow, ItemType.SmallArmsAmmo),

				new Item(ItemType.DC15A, ItemType.SmallArmsAmmo), new Item(ItemType.DC20, ItemType.SmallArmsAmmo),
				new Item(ItemType.DC15X, ItemType.SmallArmsAmmo), new Item(ItemType.Z6, ItemType.SmallArmsAmmo),
				new Item(ItemType.M5, ItemType.SmallArmsAmmo), new Item(ItemType.DC17M, ItemType.SmallArmsAmmo),
				new Item(ItemType.DC17MSniper, ItemType.SmallArmsAmmo), new Item(ItemType.A310, ItemType.SmallArmsAmmo),
				new Item(ItemType.EE3, ItemType.SmallArmsAmmo), new Item(ItemType.M1, ItemType.SmallArmsAmmo),
				new Item(ItemType.E5S, ItemType.SmallArmsAmmo), new Item(ItemType.E5, ItemType.SmallArmsAmmo),
				new Item(ItemType.E5C, ItemType.SmallArmsAmmo), new Item(ItemType.M870, ItemType.SmallArmsAmmo)));
	}

	public Item(ItemType itemType) throws Exception {
		this.itemType = itemType;
		setItem(itemType);
	}

	public Item(ItemType weaponType, ItemType ammoType) throws Exception {
		this.itemType = weaponType;
		this.ammoType = ammoType;

		setItem(weaponType);

		if ((ItemType.EE3 == weaponType || ItemType.A310 == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("Power Cell", 50);
			System.out.println("Add power cell");
			itemWeight = 2;
		} else if ((ItemType.DC15A == weaponType || ItemType.M5 == weaponType || ItemType.DC17M == weaponType
				|| ItemType.DC15X == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("D1 Power Cell", 50);
			itemWeight = 2;
		} else if (ItemType.DC20 == weaponType && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("D1S Power Cell", 10);
			itemWeight = 4;
		} else if ((ItemType.Z6 == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("R1 Power Cell", 100);
			itemWeight = 4;
		} else if ((ItemType.M1 == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("R1 Power Cell", 20);
			itemWeight = 4;
		} else if ((ItemType.E5C == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("D11 Power Cell", 100);
			itemWeight = 4;
		} else if ((ItemType.E5S == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("D11 Power Cell", 10);
			itemWeight = 4;
		} else if (ItemType.DC17MSniper == weaponType && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("DC17m Sniper Cartridge", 4);
			itemWeight = 1;
		} else if (ItemType.DC17MRocket == weaponType && ItemType.HEAT == ammoType) {
			ammo = new PCAmmo("HEAT");
			itemWeight = 2;
		} else if (ItemType.DC17MRocket == weaponType && ItemType.HE == ammoType) {
			ammo = new PCAmmo("HE");
			itemWeight = 2;
		} else if (ItemType.RPS6 == weaponType && ItemType.HEAT == ammoType) {
			ammo = new PCAmmo("HEAT");
			itemWeight = 5;
		} else if (ItemType.RPS6 == weaponType && ItemType.HE == ammoType) {
			ammo = new PCAmmo("HE");
			itemWeight = 5;
		} else if (ItemType.DC40 == weaponType && ItemType.HEAT == ammoType) {
			ammo = new PCAmmo("HEAT");
			itemWeight = 0.2;
		} else if (ItemType.DC40 == weaponType && ItemType.HE == ammoType) {
			ammo = new PCAmmo("HE");
			itemWeight = 0.2;
		} else if (ItemType.DC40 == weaponType && ItemType.ION == ammoType) {
			ammo = new PCAmmo("ION");
			itemWeight = 0.2;
		} else if (ItemType.DC40 == weaponType && ItemType.SMOKE == ammoType) {
			ammo = new PCAmmo("SMOKE");
			itemWeight = 0.2;
		} else if (ItemType.B2RR == weaponType && ItemType.HEAT == ammoType) {
			ammo = new PCAmmo("HEAT");
			itemWeight = 2;
		} else if (ItemType.B2RR == weaponType && ItemType.HE == ammoType) {
			ammo = new PCAmmo("HE");
			itemWeight = 2;
		}

		else if ((ItemType.E5 == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("S11 Power Cell", 50);
			itemWeight = 2;
		}

		else if ((ItemType.M870 == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("Buckshot", 7);
			itemWeight = 1;
		}

		else if ((ItemType.MkvbBolter == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("Bolts", 20);
			itemWeight = 4;
		}

		else if ((ItemType.MkivHeavyBolter == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("Bolts", 200);
			itemWeight = 40;
		}
		
		else if ((ItemType.MKIIIUltimaPattern == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("Bolts", 20);
			itemWeight = 2;
		}

		else if ((ItemType.m36Lasgun == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("Charge", 150);
			itemWeight = 4;
		}
		
		else if ((ItemType.AstartesFlamer == weaponType) && ItemType.FlameCharge == ammoType) {
			ammo = new PCAmmo("Charge");
			ammo.linked = false;
			itemWeight = 0.2;
		}

		else if (ItemType.SoundStrikePattern == weaponType && ItemType.Krak == ammoType) {
			ammo = new PCAmmo("Krak");
			itemWeight = 5;
		}
		
		else if (ItemType.SoundStrikePattern == weaponType && ItemType.Frag == ammoType) {
			ammo = new PCAmmo("Frag");
			itemWeight = 5;
		}

		else if ((ItemType.GoblinJavelin == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("Goblin Javelin", 1);
			itemWeight = 1;
		}

		else if ((ItemType.ShortBow == weaponType) && ItemType.SmallArmsAmmo == ammoType) {
			ammo = new PCAmmo("Arrow", 1);
			itemWeight = 0.01;
		}

		else {
			throw new Exception("Could not create ammo. Ammo type: " + ammoType + ", Weapon type: " + weaponType
					+ ", not accounted for.");
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

	public void setItem(ItemType itemType) throws Exception {

		if (ItemType.ClassAThermalDetonator == itemType) {
			weapon = new Weapons().findWeapon("Class-A Thermal Detonator");
			itemWeight = 2;
		} else if (ItemType.Nacht5SmokeGrenade == itemType) {
			weapon = new Weapons().findWeapon("Nacht-5 Smoke Grenade");
			itemWeight = 2;
		}

		else if (ItemType.Microbinoculars == itemType) {
			itemWeight = 2;
			magnification = 24;
		} else if (ItemType.ThermalMicrobinoculars == itemType) {
			thermalOptic = true;
			maxThermalRangeYards = 400;
			thermalValue = -7;
			magnification = 12;
			itemWeight = 4;
		} else if (ItemType.MobileStealthField == itemType) {
			itemWeight = 6;
			cloakField = true;
			cloakSlm = 6;
		} else if (ItemType.StationaryStealthField == itemType) {
			itemWeight = 5;
			cloakField = true;
			cloakSlm = 7;
			movementDisabled = true;
		} else if (ItemType.ThermalShroud == itemType) {
			thermalShroud = true;
			itemWeight = 2;
		} else if (ItemType.GhillieSuit == itemType) {
			camouflage = true;
			camoMod = 3;
			itemWeight = 6;
		} else if (ItemType.NVG_Gen3 == itemType) {
			nightVision = true;
			nightVisionEffectiveness = 3;
			itemWeight = 2;
		}

		else if (ItemType.NVG_Gen4 == itemType) {
			nightVision = true;
			nightVisionEffectiveness = 4;
			itemWeight = 2;
		}

		else if (ItemType.NVG_Gen5 == itemType) {
			nightVision = true;
			nightVisionEffectiveness = 5;
			itemWeight = 2;
		}

		else if (ItemType.DC15A == itemType) {
			weapon = new Weapons().findWeapon("DC15A");
			itemWeight = 12;
		} else if (ItemType.DC20 == itemType) {
			weapon = new Weapons().findWeapon("DC20");
			itemWeight = 20;
		} else if (ItemType.Z6 == itemType) {
			weapon = new Weapons().findWeapon("Z6");
			itemWeight = 20;
		} else if (ItemType.DC15X == itemType) {
			weapon = new Weapons().findWeapon("DC15X");
			itemWeight = 15;
		} else if (ItemType.DC17M == itemType) {
			weapon = new Weapons().findWeapon("DC17m");
			itemWeight = 8;
		} else if (ItemType.DC17MRocket == itemType) {
			weapon = new Weapons().findWeapon("DC17 Rocket");
			itemWeight = 4;
		} else if (ItemType.B2RR == itemType) {
			weapon = new Weapons().findWeapon("B2RR");
			itemWeight = 4;
		} else if (ItemType.DC17MSniper == itemType) {
			weapon = new Weapons().findWeapon("DC17 Sniper");
			itemWeight = 4;
		} else if (ItemType.M5 == itemType) {
			weapon = new Weapons().findWeapon("Westar M5");
			itemWeight = 10;
		} else if (ItemType.RPS6 == itemType) {
			weapon = new Weapons().findWeapon("RPS-6");
			itemWeight = 20;
		} else if (ItemType.DC40 == itemType) {
			weapon = new Weapons().findWeapon("DC40");
			itemWeight = 2;
		} else if (ItemType.EWEBWeapon == itemType) {
			weapon = new Weapons().findWeapon("EWHB-12 Heavy Repeating Blaster");
			weapon.name = "EWHB-12" + " Cannon";
			itemWeight = 84;
		} else if (ItemType.EWEBBattery == itemType) {
			weapon = new Weapons().findWeapon("EWHB-12 Heavy Repeating Blaster");
			weapon.name = "EWHB-12" + " Battery";
			itemWeight = 10;
		} else if (ItemType.EWEBTripod == itemType) {
			weapon = new Weapons().findWeapon("EWHB-12 Heavy Repeating Blaster");
			weapon.name = "EWHB-12" + " Tripod";
			itemWeight = 30;
		}

		else if (ItemType.E5 == itemType) {
			weapon = new Weapons().findWeapon("E5");
			itemWeight = 6;
		}

		else if (ItemType.E5S == itemType) {
			weapon = new Weapons().findWeapon("E5S");
			itemWeight = 13;
		}

		else if (ItemType.E5C == itemType) {
			weapon = new Weapons().findWeapon("E5C");
			itemWeight = 11;
		}

		else if (ItemType.M1 == itemType) {
			weapon = new Weapons().findWeapon("M1");
			itemWeight = 15;
		}

		else if (ItemType.A310 == itemType) {
			weapon = new Weapons().findWeapon("A310");
			itemWeight = 8;
		}

		else if (ItemType.EE3 == itemType) {
			weapon = new Weapons().findWeapon("EE3");
			itemWeight = 8;
		}

		else if (ItemType.M870 == itemType) {
			weapon = new Weapons().findWeapon("M870");
			itemWeight = 9;
		}

		else if (ItemType.AstartesFragGrenade == itemType) {
			weapon = new Weapons().findWeapon("Astartes Frag Grenade");
			itemWeight = 4;
		}

		else if (ItemType.KrakGrenade == itemType) {
			weapon = new Weapons().findWeapon("Krak Grenade");
			itemWeight = 4;
		}

		else if (ItemType.MkvbBolter == itemType) {
			weapon = new Weapons().findWeapon("MKVb Bolter");
			itemWeight = 40;
		}

		else if (ItemType.MkivHeavyBolter == itemType) {
			weapon = new Weapons().findWeapon("MKIV Heavy Bolter");
			itemWeight = 150;
		}
		
		else if (ItemType.AstartesStandard == itemType) {
			itemWeight = 5;
		}
		
		else if (ItemType.AstartesFlamer == itemType) {
			weapon = new Weapons().findWeapon("Astartes Flamer");
			itemWeight = 40;
		}

		else if (ItemType.m36Lasgun == itemType) {
			weapon = new Weapons().findWeapon("M36 Lasgun");
			itemWeight = 9;
		}
		
		else if (ItemType.MKIIIUltimaPattern == itemType) {
			weapon = new Weapons().findWeapon("MKIII Ultima Pattern");
			itemWeight = 7;
		}
		
		else if (ItemType.SoundStrikePattern == itemType) {
			weapon = new Weapons().findWeapon("Sound Strike Pattern");
			itemWeight = 25;
		}

		else if (ItemType.GoblinJavelin == itemType) {
			weapon = new Weapons().findWeapon("Goblin Javelin");
			itemWeight = 0;
		}

		else if (ItemType.ShortBow == itemType) {
			weapon = new Weapons().findWeapon("Short Bow");
			itemWeight = 2;
		}

		else {
			throw new Exception("Could not create item. Item type: " + itemType + " not accounted for.");
		}
	}

	public String getItemName() {
		if (weapon == null && ammo == null)
			return itemType.toString();
		if (weapon == null || ammo == null)
			return weapon != null ? weapon.name : ammo.name;
		else
			return weapon.name + ": " + ammo.name + " round, " + "depleted: " + ammo.depleted + ", Shots: "
					+ ammo.firedShots;
	}

}
