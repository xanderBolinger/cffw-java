package Actions;

import java.util.ArrayList;
import java.util.Random;

import Conflict.GameWindow;
import Injuries.ResolveHits;
import Items.Ammo;
import Items.Weapons;
import Trooper.Trooper;
import Unit.Unit;

public class Launcher {
	public String launcherName; 
	public GameWindow window; 
	public int xCordinate; 
	public int yCordinate; 
	public int otherBonus; 
	public Trooper attacker; 
	public Unit unit; 
	public Trooper target; 
	public Random rand = new Random();
	public String ammoTypeName; 
	public int concealment; 
	
	public Launcher(String launcherName, GameWindow window, int xCordinate, int yCordinate, int otherBonus, Trooper attacker, Unit unit, Trooper target, String ammoTypeName, int concealment) {
		
		this.launcherName = launcherName; 
		this.window = window; 
		this.xCordinate = xCordinate; 
		this.yCordinate = yCordinate; 
		this.otherBonus = otherBonus; 
		this.attacker = attacker; 
		this.unit = unit; 
		this.target = target; 
		this.ammoTypeName = ammoTypeName;
		this.concealment = concealment; 
		
	}
	
	public void shootLauncher() {
		
		// Determines throw type 
		int dif = hexDif(unit, xCordinate, yCordinate);
		//System.out.println("Grenade hexDif: "+dif);
		
		int modifier = 0; 
		
		modifier += getRangeMod(dif * GameWindow.hexSize);
		
		//System.out.println("Range Mod: "+getRangeMod(dif * 20));
		
		if(concealment > 0) 
			modifier += getConcealmentMod(concealment);
		
		
		int RWS = attacker.launcherRWS + modifier + otherBonus - (unit.suppression / 2);
		
		Weapons wep = new Weapons();
		wep.getWeapons();
		Weapons grenade = wep.findWeapon(launcherName);
		
		if(grenade.launcherHomingInfantry && target != null) {
			RWS = grenade.homingHitChance;
		}
			
		
		int roll = rand.nextInt(100) + 1; 
				
		int margin = RWS - roll; 
		
		//System.out.println("Rocket RWS: "+RWS);
		//System.out.print("Roll: "+roll);
		//System.out.println("Rocket MARGIN: "+margin);
		
		if(margin > 0) {
			
			int marginOfSuccess = margin / 10;
			
			window.conflictLog.addNewLine(unit.side+", "+unit.callsign+":: "+attacker.number+" "+attacker.name+" shoots rocket into hex: "+xCordinate+", "+yCordinate+" TN: "+RWS+" MOS: "+marginOfSuccess);
			
			resolveSuccess(marginOfSuccess);
		} else {
			
			int marginOfFailure = Math.abs(margin / 10);
			
			window.conflictLog.addNewLine(unit.side+", "+unit.callsign+":: "+attacker.number+" "+attacker.name+" shoots rocket into hex: "+xCordinate+", "+yCordinate+" TN: "+RWS+" MOF: "+marginOfFailure);
			
			resolveFailure(marginOfFailure);
		}
		
		Unit targetUnit = null; 
		
		if(target != null) {
			targetUnit = target.returnTrooperUnit(window);
			if (targetUnit.suppression + 8 < 100) {
				targetUnit.suppression += 8;
			} else {
				targetUnit.suppression = 100;
			}
			if (targetUnit.organization - 8 > 0) {
				targetUnit.organization -= 8;
			} else {
				targetUnit.organization = 0;
			}
		}
		
		
		int x = xCordinate;
		int y = yCordinate; 
		
		for(Unit potentialTarget : window.initiativeOrder) {
			
			if(targetUnit != null && targetUnit.compareTo(potentialTarget))
				continue; 
			
			if(potentialTarget.X == x && potentialTarget.Y == y) {
				if (potentialTarget.suppression + 8 < 100) {
					potentialTarget.suppression += 8;
				} else {
					potentialTarget.suppression = 100;
				}
				if (potentialTarget.organization - 8 > 0) {
					potentialTarget.organization -= 8;
				} else {
					potentialTarget.organization = 0;
				}
			}
			
		}
		
		
	}
	
	public void resolveSuccess(int marginOfSuccess) {
		
		ArrayList<Trooper> victims = new ArrayList<>();
		
		if(target != null)
			victims.add(target);
		
		Weapons wep = new Weapons();
		wep.getWeapons();
		Weapons grenade = wep.findWeapon(launcherName);
		grenade.setAmmo(ammoTypeName);
		
		// Finds units in hex 
		// Loops through all troopers in unit 
		for(Unit units : window.initiativeOrder) {

			if(units.X == xCordinate && units.Y == yCordinate) {
				
				for(Trooper trooper : units.getTroopers()) {
					
					victims.add(trooper);
					
				}
				
				
			}
	
		}
		
		
		for(Trooper trooper : victims) {
			
			if(target != null && trooper.compareTo(target)) {
				resolveHit(target, grenade, true);
			} else {
				resolveHit(trooper, grenade, false);
			}
			
			
		}
		
	}
	
	public void resolveFailure(int marginOfFailure) {
		
		// Rocket missed hex entirely 
		if(marginOfFailure >= 3) {
			window.conflictLog.addNewLine(attacker.number+" "+attacker.name+" fired launcher at hex "+xCordinate+":"+yCordinate+" but the projectile missed the hex entirely.");
			return; 
		}
			
		
		
		ArrayList<Trooper> victims = new ArrayList<>();
		
		Weapons wep = new Weapons();
		wep.getWeapons();
		Weapons grenade = wep.findWeapon(launcherName);
		grenade.setAmmo(ammoTypeName);
		
		
		for(Unit units : window.initiativeOrder) {

			if(units.X == xCordinate && units.Y == yCordinate) {
				
				for(Trooper trooper : units.getTroopers()) {
					
					victims.add(trooper);
				}
				
				
			}
	
		}
		
		
		for(Trooper trooper : victims) {
			resolveHit(trooper, grenade, false);
		}
		
	}
	
	public void resolveHit(Trooper trooper, Weapons grenade, boolean contact) {
		
		Unit targetUnit = trooper.returnTrooperUnit(window);
		
			
		
		if(contact) {
			
			ResolveHits resolveHits = new ResolveHits(trooper, 0, grenade, window.conflictLog, targetUnit, unit, true, window);
			resolveHits.performCalculationsLauncher(window.game);
			
			int unitSize = targetUnit.getSize();
			int moraleLoss = 100 / unitSize;
			if(!trooper.alive || !trooper.conscious) {
				 
				if(targetUnit.organization - 5 < 1) {
					targetUnit.organization = 0; 
				} else {
					targetUnit.organization -= 5;
				}
				 
				if(targetUnit.moral - moraleLoss < 1) {
					targetUnit.moral = 0; 
				} else {
					targetUnit.moral -= moraleLoss;
				}
				
				
				
			} 
			
		} else {
			
			Random rand = new Random();
			
			int distance = rand.nextInt(10) + 1; 
			distance += rand.nextInt(10) + 1;
			
			ArrayList<Integer> range = new ArrayList<Integer>();
			
			range.add(0);
			range.add(4);
			range.add(6);
			range.add(8);
			range.add(10);
			range.add(20);
			
			for(int i = 0; i < range.size(); i++) {
				if(distance <= range.get(i)) {
					
					ResolveHits resolveHits = new ResolveHits(trooper, i, grenade, window.conflictLog, targetUnit, unit, true, window);
					//System.out.println("launcher name: "+grenade.name);
					//System.out.println("target: "+target.number+" "+target.name);
					//System.out.println("Range Col: "+i);
					resolveHits.performCalculationsLauncher(window.game);
					
					int unitSize = targetUnit.getSize();
					int moraleLoss = 100 / unitSize;
					if(!trooper.alive || !trooper.conscious) {
						 
						if(targetUnit.organization - 5 < 1) {
							targetUnit.organization = 0; 
						} else {
							targetUnit.organization -= 5;
						}
						 
						if(targetUnit.moral - moraleLoss < 1) {
							targetUnit.moral = 0; 
						} else {
							targetUnit.moral -= moraleLoss;
						}
						
						
						
					} 
					
					break; 
				}
			}
			
			
			
		}
		
		
		
		
		
		
	
			
		
	}
	
	public String hitLocation() {
		int locationRoll = 0; 
		String hitLocation = "";
		
		locationRoll += d6();
		locationRoll += d6();
		locationRoll += d6();
		
		if(locationRoll <= 5) {
			hitLocation = "head";
		} else if(locationRoll <= 9) {
			hitLocation = "legs";
		} else if(locationRoll <= 11) {
			hitLocation = "arms";
		} else if(locationRoll <= 16) {
			hitLocation = "torso";
		} else if(locationRoll <= 18) {
			hitLocation = "vitals";
		}
		
	
		return hitLocation; 
	}
	
	public int d6() {
		Random rand = new Random();
		int d6 = rand.nextInt(6) + 1; 
		return d6; 
	}
	
	
	// Takes range in yards and returns GURPS percentage penalty
	public int getRangeMod(int rangeInYards) {
		int rangeMod = 0;

		ArrayList<ArrayList<Integer>> rangeMods = new ArrayList<ArrayList<Integer>>(20);
		for (int i = 1; i <= 20; i++) {
			rangeMods.add(new ArrayList<Integer>());
		}

		// 3 yards to 15
		rangeMods.get(0).add(0, 3);
		rangeMods.get(1).add(0, 5);
		rangeMods.get(2).add(0, 7);
		rangeMods.get(3).add(0, 10);
		rangeMods.get(4).add(0, 15);

		// 20 to 100 yards
		rangeMods.get(5).add(0, 20);
		rangeMods.get(6).add(0, 30);
		rangeMods.get(7).add(0, 50);
		rangeMods.get(8).add(0, 70);
		rangeMods.get(9).add(0, 100);

		// 150 to 700 yards
		rangeMods.get(10).add(0, 150);
		rangeMods.get(11).add(0, 200);
		rangeMods.get(12).add(0, 300);
		rangeMods.get(13).add(0, 500);
		rangeMods.get(14).add(0, 700);

		// 1000 to 5000 yards
		rangeMods.get(15).add(0, 1000);
		rangeMods.get(16).add(0, 1500);
		rangeMods.get(17).add(0, 2000);
		rangeMods.get(18).add(0, 3000);
		rangeMods.get(19).add(0, 5000);

		// Penalties 
		
		// 3 yards to 15
		rangeMods.get(0).add(1, -1);
		rangeMods.get(1).add(1, -2);
		rangeMods.get(2).add(1, -3);
		rangeMods.get(3).add(1, -4);
		rangeMods.get(4).add(1, -5);

		// 20 to 100 yards
		rangeMods.get(5).add(1, -6);
		rangeMods.get(6).add(1, -7);
		rangeMods.get(7).add(1, -8);
		rangeMods.get(8).add(1, -9);
		rangeMods.get(9).add(1, -10);

		// 150 to 700 yards
		rangeMods.get(10).add(1, -11);
		rangeMods.get(11).add(1, -12);
		rangeMods.get(12).add(1, -13);
		rangeMods.get(13).add(1, -14);
		rangeMods.get(14).add(1, -15);

		// 1000 to 5000 yards
		rangeMods.get(15).add(1, -16);
		rangeMods.get(16).add(1, -17);
		rangeMods.get(17).add(1, -18);
		rangeMods.get(18).add(1, -19);
		rangeMods.get(19).add(1, -20);

		
		rangeMod = search(rangeMods, rangeInYards);
		if(rangeMod == 0) {
			rangeMod = -4;
		}
				
		rangeMod = rangeMod * 5; 
		
		//System.out.println("GRENADE RANGE MOD: "+rangeMod);
		
		return rangeMod;
	}

	// Gets concealment penalty
	public int getConcealmentMod(int concealment) {
		int mod = 0;
		
		if (concealment == 1) {
			mod = -10;
		} else if (concealment == 2) {
			mod = -20;
		} else if (concealment == 3) {
			mod = -30;
		} else if (concealment == 4) {
			mod = -40;
		} else if (concealment == 5) {
			mod = -50;
		}

		
		//System.out.println("Concealment: "+mod);
		
		return mod;
	}
	
	// Finds the differance between the two locations
	public int hexDif(Unit targetUnit, int x, int y) {
		double xDif = Math.abs(targetUnit.X - x);
		double yDif = Math.abs(targetUnit.Y - y);
		
		xDif *= xDif; 
		yDif *= yDif; 
		
		return (int) Math.floor(Math.sqrt((xDif + yDif)));
	}
	
	// Searches an array of a table
	// Returns the item crossrefereanced in the table
	public int search(ArrayList<ArrayList<Integer>> arr, int target) {
		int value = 0;
		//System.out.println("Target: " + target);
		//System.out.println("Array: " + arr.toString());
		int temp1;
		int temp2;
		for (int i = 1; i <= arr.size() - 1; i++) {
			temp1 = arr.get(i).get(0);
			temp2 = arr.get(i - 1).get(0);
			if (target <= temp1 && target >= temp2) {
				value = arr.get(i - 1).get(1);
				//System.out.println("Value: " + value);
			}

		}

		return value;

	}
}
