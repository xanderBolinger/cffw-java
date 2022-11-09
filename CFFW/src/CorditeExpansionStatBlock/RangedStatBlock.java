package CorditeExpansionStatBlock;

import java.util.ArrayList;

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
		coolnessUnderFire = (trooper.getSkill("Fighter") + trooper.getSkill("Composure")) / 2;
		statBlock = trooper.ceStatBlock;
		suppression = new Suppression(statBlock);
	}
	
	public int getSuppression() {
		return suppression.getSuppression();
	}
	
	
}
