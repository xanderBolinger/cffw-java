package CorditeExpansionStatBlock;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CorditeExpansion.Cord;
import CorditeExpansionRangedCombat.CalledShots.ShotTarget;
import CorditeExpansionRangedCombat.Suppression;
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
	public ShotTarget shotTarget = ShotTarget.NONE;
	public boolean aiming = true;
	public boolean fullAuto = false;
	
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
	}
	
	public int getSuppression() {
		return suppression.getSuppression();
	}
	
}
