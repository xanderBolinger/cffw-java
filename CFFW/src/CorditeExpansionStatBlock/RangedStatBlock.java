package CorditeExpansionStatBlock;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CorditeExpansion.Cord;
import CorditeExpansionRangedCombat.CalledShots.ShotTarget;
import CorditeExpansionRangedCombat.Suppression;
import Items.Item;
import Items.Weapons;
import Trooper.Trooper;

public class RangedStatBlock {
	public boolean stabalized = false;
	public Weapons weapon;
	public int weaponPercent;
	public int aimTime = 0;
	public int maxAim;
	public Trooper aimTarget; 
	public ArrayList<Cord> aimHexes = new ArrayList<>();
	public ArrayList<Weapons> weapons = new ArrayList<>();
	public ShotTarget shotTarget = ShotTarget.NONE;
	public boolean aiming = true;
	public boolean fullAuto = false;
	public boolean blindFiring = false;
	public boolean lookingIntoLight = false; 
	
	// Fighter + composure / 2
	public int coolnessUnderFire;
	
	public StatBlock statBlock;
	public Suppression suppression; 
	
	public RangedStatBlock(Trooper trooper) {	
		statBlock = trooper.ceStatBlock;
		coolnessUnderFire = (trooper.getSkill("Fighter") + trooper.getSkill("Composure")) / 2;
		weapon = new Weapons().findWeapon(trooper.wep);
		maxAim = weapon.aimTime.size() - 1;
		suppression = new Suppression(statBlock);
		Matcher match = Pattern.compile("[0-9]+").matcher(trooper.weaponPercent);
		if(match.find()) {
			weaponPercent = Integer.parseInt(match.group(0));			
		}
		
		reload(trooper);
		weapons.add(weapon);
		
	}
	
	public void toggleBlindFiring() {
		if(blindFiring)
			blindFiring = false; 
		else 
			blindFiring = true;
	}
	
	public void toggleLookingIntoLight() {
		if(lookingIntoLight)
			lookingIntoLight = false; 
		else 
			lookingIntoLight = true;
	}
	
	public int getSuppression() {
		return suppression.getSuppression();
	}
	
	public void reload(Trooper trooper) {
		
		int dp = Integer.MAX_VALUE;
		Item magazine = null;
		
		for(Item item : trooper.inventory.getItemsArray()) {
			if(item.isRound() && item.weapon != null && item.weapon.name.equals(weapon.name)) {
				
				if(item.ammo.depletionPoints < dp) {
					magazine = item;
					dp = item.ammo.depletionPoints;
				}
				
			}
		}
		
		if(magazine == null)
			return;
		
		if(weapon.ceStats.ammoCheck()) {
			try {
				System.out.println("swap");
				trooper.inventory.addItem(weapon.ceStats.magazine);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		weapon.ceStats.magazine = magazine;
		
		try {
			trooper.inventory.removeItem(magazine.getItemName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String magazines(Trooper trooper) {
		String rslts = ""; 
		
		for(Item item : trooper.inventory.getItemsArray()) {
			if(item.isRound() && item.weapon != null && item.weapon.name.equals(weapon.name)) {
				rslts+=" "+item.getItemName()+"["+item.ammo.depletionPoints+"]";
			}
		}
		
		return rslts;
	}
	
	public boolean hasAmmo(Trooper trooper) {
		for(Item item : trooper.inventory.getItemsArray()) {
			if(item.isRound() && item.weapon != null && item.weapon.name.equals(weapon.name)) {
				return true; 
			}
		}
		
		return false;
	}
	
	
}
