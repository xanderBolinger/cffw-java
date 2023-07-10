package Melee;

import java.util.List;

import Melee.MeleeArmorData.MeleeArmorType;
import Melee.MeleeWeaponData.MeleeWeaponType;

public class MeleeWeapon {

	public static List<MeleeWeapon> meleeWeapons;
	
	public MeleeWeaponType meleeWeaponType;
	
    public enum WeaponSize {
        LIGHT, MEDIUM, HEAVY, HYPER_LETHAL, GARGANTUAN
    }

    public int attackTargetNumberStab;
    public int attackTargetNumberCut;
    public int attackTargetNumberBash;
    public int defenseTargetNumber;

    // Applies repeated cutting damage to an area on sequential hits
    public boolean chainWeapon;
    public int lastChainCutDamage;
    
    public WeaponSize weaponSize;

    public int cutMod;
    public int bashMod;
    public int stabMod;

    public int cutApMod;
    public int bashApMod;
    public int stabApMod;
    
    /* weapon reach values: 
     * 1: elbow, VS 
     * 2: fist, dagger, S
     * 3: sword, M
     * 4: spear/long ax, L
     * 5: long pole arm. VL
     * 6: pike, EL 
     */
    public int reach;
    
    public MeleeWeapon(MeleeWeaponType meleeWeaponType, int attackTargetNumberStab, int attackTargetNumberCut, int attackTargetNumberBash,
                       int defenseTargetNumber, boolean chainWeapon, WeaponSize weaponSize,
                       int cutMod, int bashMod, int stabMod, int reach,
                       int cutApMod, int bashApMod, int stabApMod) {
    	this.meleeWeaponType = meleeWeaponType;
        this.attackTargetNumberStab = attackTargetNumberStab;
        this.attackTargetNumberCut = attackTargetNumberCut;
        this.attackTargetNumberBash = attackTargetNumberBash;
        this.defenseTargetNumber = defenseTargetNumber;
        this.chainWeapon = chainWeapon;
        this.weaponSize = weaponSize;
        this.cutMod = cutMod;
        this.bashMod = bashMod;
        this.stabMod = stabMod;
        this.reach = reach;
        this.cutApMod = cutApMod;
        this.bashApMod = bashApMod;
        this.stabApMod = stabApMod;
    }
    
	public static MeleeWeapon getMeleeWeapon(MeleeWeaponType weaponType) throws Exception {
		
		for(var piece : meleeWeapons) {
			if(piece.meleeWeaponType == weaponType)
				return piece;
		}
		
		throw new Exception("Melee Weapon piece not found for weaponType: "+weaponType);
	}
}

