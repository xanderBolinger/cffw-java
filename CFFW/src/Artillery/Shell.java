package Artillery;

import java.io.Serializable;
import java.util.ArrayList;

import Artillery.Artillery.ShellType;
import Conflict.SmokeStats.SmokeType;
import Items.PCAmmo;

public class Shell implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4576626989046586256L;
	public ArrayList<Integer> pen = new ArrayList<Integer>();
	public ArrayList<Integer> dc = new ArrayList<Integer>();
	public ArrayList<String> bshc = new ArrayList<String>();
	public ArrayList<Integer> bc = new ArrayList<Integer>();
	public ArrayList<Integer> ionDamage = new ArrayList<Integer>();
	public String shellName; 
	
	public boolean smoke = false; 
	public boolean airBurst = false;
	public SmokeType smokeType;
	
	public PCAmmo pcAmmo;
	
	public Shell(ShellType shellType) {
	    switch (shellType) {
	            
	        case M60HE:
	            createM60HE();
	            break;
	        case M60ION:
	            createM60ION();
	            break;
	        case M60Smoke:
	            shellName = "Smoke";
	            smoke = true;
	            smokeType = SmokeType.Mortar60mm;
	            break;
	        case M60WP:
	            shellName = "WP";
	            smoke = true;
	            smokeType = SmokeType.Wp60mm;
	            break;
	        case M60WPIon:
	            shellName = "WP Ion";
	            smoke = true;
	            smokeType = SmokeType.Wp60mmIon;
	            break;
	            
	        case M81HE:
	            createM81HE();
	            break;
	        case M81ION:
	            createM81ION();
	            break;
	        case M81Smoke:
	            shellName = "Smoke";
	            smoke = true;
	            smokeType = SmokeType.Mortar81mm;
	            break;
	        case M81WP:
	            shellName = "WP";
	            smoke = true;
	            smokeType = SmokeType.Wp81mm;
	            break;
	        case M81WPION:
	            shellName = "WP Ion";
	            smoke = true;
	            smokeType = SmokeType.Wp81mmIon;
	            break;
	            
	        case M120HE:
	            createM120HE();
	            break;
	        case M120ION:
	            createM120ION();
	            break;
	        case M120Smoke:
	            shellName = "Smoke";
	            smoke = true;
	            smokeType = SmokeType.Mortar120mm;
	            break;
	        case M120WP:
	            shellName = "WP";
	            smoke = true;
	            smokeType = SmokeType.Wp120mm;
	            break;
	        case M120WPIon:
	            shellName = "WP Ion";
	            smoke = true;
	            smokeType = SmokeType.Wp120mmIon;
	            break;
	            
	        case AV7HEAT:
	            createAV7HEAT();
	            break;
	        case AV7Smoke:
	            shellName = "Smoke";
	            smoke = true;
	            smokeType = SmokeType.Howitzer155mm;
	            break;
	        case Shell155mmWP:
	            shellName = "WP";
	            smoke = true;
	            smokeType = SmokeType.Wp155mm;
	            break;
	        case Shell155mmWpIon:
	            shellName = "WP Ion";
	            smoke = true;
	            smokeType = SmokeType.Wp155mmIon;
	            break;
	       
	        case M107HE:
	            createM107HE();
	            break;
	        case M107Smoke:
	            shellName = "Smoke";
	            smoke = true;
	            smokeType = SmokeType.Howitzer105mm;
	            break;
	       
	        default:
	            // Handle any other cases or errors
	            break;
	    }
	}
	
	private void createM107HE() {
	    shellName = "107mm HE";
	    pen.add(20);
	    pen.add(20);
	    pen.add(19);
	    pen.add(19);
	    pen.add(18);
	    pen.add(17);
	    pen.add(16);

	    dc.add(7);
	    dc.add(7);
	    dc.add(7);
	    dc.add(7);
	    dc.add(7);
	    dc.add(7);
	    dc.add(6);

	    bshc.add("*10");
	    bshc.add("*4");
	    bshc.add("*3");
	    bshc.add("*1");
	    bshc.add("93");
	    bshc.add("65");
	    bshc.add("22");

	    bc.add(12000);
	    bc.add(1200);
	    bc.add(283);
	    bc.add(103);
	    bc.add(58);
	    bc.add(39);
	    bc.add(0);
	}

	private void createAV7HEAT() {
	    shellName = "Explosive Charge(HEAT)";
	    pen.add(112);
	    pen.add(112);
	    pen.add(112);
	    pen.add(112);
	    pen.add(112);
	    pen.add(112);
	    pen.add(112);

	    dc.add(10);
	    dc.add(10);
	    dc.add(10);
	    dc.add(10);
	    dc.add(10);
	    dc.add(8);
	    dc.add(6);

	    bshc.add("*14");
	    bshc.add("*3");
	    bshc.add("86");
	    bshc.add("38");
	    bshc.add("21");
	    bshc.add("14");
	    bshc.add("3");

	    bc.add(29000);
	    bc.add(3000);
	    bc.add(495);
	    bc.add(149);
	    bc.add(82);
	    bc.add(53);
	    bc.add(15);
	}

	private void createM120HE() {
	    shellName = "120mm HE";
	    pen.add(30);
	    pen.add(29);
	    pen.add(29);
	    pen.add(28);
	    pen.add(27);
	    pen.add(26);
	    pen.add(25);

	    dc.add(9);
	    dc.add(9);
	    dc.add(9);
	    dc.add(9);
	    dc.add(9);
	    dc.add(9);
	    dc.add(8);

	    bshc.add("*14");
	    bshc.add("*3");
	    bshc.add("87");
	    bshc.add("38");
	    bshc.add("21");
	    bshc.add("13");
	    bshc.add("5");

	    bc.add(3000);
	    bc.add(530);
	    bc.add(95);
	    bc.add(43);
	    bc.add(25);
	    bc.add(17);
	    bc.add(13);
	}

	private void createM120ION() {
	    shellName = "120mm ION";
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);

	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);

	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");

	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);

	    ionDamage.add(12000);
	    ionDamage.add(1000);
	    ionDamage.add(283);
	    ionDamage.add(154);
	    ionDamage.add(102);
	    ionDamage.add(55);
	    ionDamage.add(25);
	}

	private void createM81HE() {
	    shellName = "81mm HE";
	    pen.add(9);
	    pen.add(9);
	    pen.add(8);
	    pen.add(8);
	    pen.add(7);
	    pen.add(7);
	    pen.add(6);

	    dc.add(6);
	    dc.add(6);
	    dc.add(6);
	    dc.add(6);
	    dc.add(6);
	    dc.add(5);
	    dc.add(5);

	    bshc.add("*10");
	    bshc.add("*2");
	    bshc.add("36");
	    bshc.add("16");
	    bshc.add("9");
	    bshc.add("5");
	    bshc.add("4");

	    bc.add(3000);
	    bc.add(530);
	    bc.add(95);
	    bc.add(43);
	    bc.add(25);
	    bc.add(17);
	    bc.add(13);
	}

	private void createM81ION() {
	    shellName = "81mm ION";
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);

	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);

	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");

	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);

	    ionDamage.add(9000);
	    ionDamage.add(800);
	    ionDamage.add(183);
	    ionDamage.add(54);
	    ionDamage.add(23);
	    ionDamage.add(15);
	    ionDamage.add(5);
	}

	private void createM60HE() {
	    shellName = "60mm HE";
	    pen.add(2);
	    pen.add(2);
	    pen.add(2);
	    pen.add(2);
	    pen.add(1);
	    pen.add(1);
	    pen.add(1);

	    dc.add(2);
	    dc.add(2);
	    dc.add(2);
	    dc.add(2);
	    dc.add(2);
	    dc.add(2);
	    dc.add(2);

	    bshc.add("*1");
	    bshc.add("27");
	    bshc.add("6");
	    bshc.add("3");
	    bshc.add("1");
	    bshc.add("1");
	    bshc.add("0");

	    bc.add(739);
	    bc.add(155);
	    bc.add(20);
	    bc.add(9);
	    bc.add(5);
	    bc.add(3);
	    bc.add(1);
	}

	private void createM60ION() {
	    shellName = "60mm ION";
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);
	    pen.add(0);

	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);
	    dc.add(1);

	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");
	    bshc.add("0");

	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);
	    bc.add(0);

	    ionDamage.add(6000);
	    ionDamage.add(600);
	    ionDamage.add(123);
	    ionDamage.add(34);
	    ionDamage.add(13);
	    ionDamage.add(11);
	    ionDamage.add(3);
	}

	
}
