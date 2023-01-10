package Ship;

import java.util.ArrayList;
import java.util.Arrays;

public class Weapon {

	public ArrayList<WeaponColumn> columns;
	public int cooldown;
	public double powerCost;
	public FireType fireType;
	public boolean destroyed;
	public WeaponType weaponType;

	public enum FireType {
		SINGLE, TWIN, QUAD, SEEKER
	}

	public enum WeaponType {
		MEDIUM_TURBO_LASER, MEDIUM_LASER_CANNON, HEAVY_PROTON_TORPEDO, DECK_GUN
	}

	public Weapon(String weaponName) {
		weaponName = weaponName.toUpperCase();
		for(WeaponType weaponType : WeaponType.values()) {
			if(weaponName.equals(weaponType.toString())) {
				this.fireType = FireType.SINGLE;
				this.weaponType = weaponType;
				destroyed = false;

				switch (weaponType) {
				case MEDIUM_TURBO_LASER:
					mediumTurboLaser();
					break;
				case MEDIUM_LASER_CANNON:
					mediumLaserCannon();
					break;
				case HEAVY_PROTON_TORPEDO:
					heavyProtonTorpedo();
					break;
				case DECK_GUN:
					deckGun();
					break;

				}
				
				return;
			}
		}
		
		weaponType = null;
	}
	
	public Weapon(WeaponType wepType, FireType fireType) {
		this.fireType = fireType;
		this.weaponType = wepType;
		destroyed = false;

		switch (wepType) {
		case MEDIUM_TURBO_LASER:
			mediumTurboLaser();
			break;
		case MEDIUM_LASER_CANNON:
			mediumLaserCannon();
			break;
		case HEAVY_PROTON_TORPEDO:
			heavyProtonTorpedo();
			break;
		case DECK_GUN:
			deckGun();
			break;

		}

	}

	public void deckGun() {
		cooldown = 1;
		powerCost = 0;
	}

	public void heavyProtonTorpedo() {
		cooldown = 1;
		powerCost = 0;
	}

	public void mediumLaserCannon() {
		cooldown = 1;
		powerCost = 1;
		columns = new ArrayList<WeaponColumn>();
		columns.add(new WeaponColumn(10, new ArrayList<Integer>(Arrays.asList(5, 6, 6, 7, 7, 7, 7, 7, 7, 7))));
		columns.add(new WeaponColumn(12, new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 4, 5, 5, 6, 6, 6))));
		columns.add(new WeaponColumn(15, new ArrayList<Integer>(Arrays.asList(1, 1, 1, 2, 3, 3, 4, 4, 4, 4))));
		columns.add(new WeaponColumn(20, new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 2, 2, 3, 3, 3, 3))));
	}

	public void mediumTurboLaser() {
		cooldown = 3;
		powerCost = 5;
		columns = new ArrayList<WeaponColumn>();
		columns.add(new WeaponColumn(8, new ArrayList<Integer>(Arrays.asList(32, 33, 34, 35, 36, 37, 38, 39, 41, 42))));
		columns.add(new WeaponColumn(9, new ArrayList<Integer>(Arrays.asList(1, 25, 26, 27, 29, 30, 31, 33, 34, 35))));
		columns.add(new WeaponColumn(11, new ArrayList<Integer>(Arrays.asList(1, 12, 14, 16, 17, 19, 20, 21, 23, 24))));
		columns.add(new WeaponColumn(14, new ArrayList<Integer>(Arrays.asList(1, 7, 9, 10, 11, 12, 14, 15, 16, 17))));
		columns.add(new WeaponColumn(18, new ArrayList<Integer>(Arrays.asList(1, 1, 2, 3, 4, 5, 6, 8, 9, 10))));
		columns.add(new WeaponColumn(24, new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 2, 3, 4, 5, 6))));
		columns.add(new WeaponColumn(32, new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 2, 3))));
		columns.add(new WeaponColumn(52, new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, 2))));
	}

	public int getDamage(int roll, int range) throws Exception {
		
		for(WeaponColumn col : columns) {
			if(range <= col.range) {
				return col.damage.get(roll-1);
			}
		}
		
		throw new Exception("Damage not found, roll: "+roll+", range: "+range);
	}
	
	
	@Override
	public String toString() {

		String weaponCode = powerCost + "(+" + cooldown + ") " + fireType + " ";

		if(destroyed) {
			weaponCode = "DESTROYED: " + weaponCode;
		}
		
		switch (weaponType) {
		case MEDIUM_TURBO_LASER:
			return weaponCode + "Medium Turbo Laser Battery";
		case MEDIUM_LASER_CANNON:
			return weaponCode + "Medium Laser Cannon";
		case HEAVY_PROTON_TORPEDO:
			return weaponCode + "Heavy Proton Torpedo";
		case DECK_GUN:
			return weaponCode + "Deck Gun";

		}
		
		return weaponCode + "EMPTY WEAPON";
	}

}
