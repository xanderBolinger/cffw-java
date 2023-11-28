package Conflict;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import Hexes.Hex;
import Explosion.Explosion;
import Items.PCAmmo;
import Items.Weapons;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;
import UtilityClasses.SwingUtility;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import Artillery.Shell;
import Artillery.Artillery.ShellType;

public class AttackHexWindow {

	private JFrame frame;
	private JComboBox comboBoxSmallArms;
	private JComboBox comboBoxLaunchers;
	private JComboBox comboBoxGrenades;
	private JComboBox comboBoxHeavyWeapons;
	private JComboBox comboBoxOrdnance;
	private JSpinner spinnerNumberOfAttacks;
	private JList listIndividuals;

	
	ArrayList<Trooper> individuals = new ArrayList<>();
	Hex hex; 
	private JSpinner spinnerDistanceToTarget;
	private JComboBox comboBoxPCAmmo;
	private JComboBox comboBoxSide;
	private JComboBox comboBoxShellTypes;
	/**
	 * Create the application.
	 */
	public AttackHexWindow(Hex hex) {
		
		
		
		this.hex = hex; 
		initialize();
		setIndividuals();
		setComboBoxes();
		comboBoxSmallArms.setSelectedIndex(1);
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 976, 800);
		SwingUtility.setFrame(frame);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Small Arms");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 181, 28);
		frame.getContentPane().add(lblNewLabel);
		
		comboBoxSmallArms = new JComboBox();
		comboBoxSmallArms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//System.out.println("Trigger: ");
				changeIndexes(comboBoxSmallArms, comboBoxSmallArms.getSelectedIndex());
				
			}
		});
		comboBoxSmallArms.setBounds(10, 50, 181, 28);
		frame.getContentPane().add(comboBoxSmallArms);
		
		comboBoxLaunchers = new JComboBox();
		comboBoxLaunchers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				changeIndexes(comboBoxLaunchers, comboBoxLaunchers.getSelectedIndex());
				
			}
		});
		comboBoxLaunchers.setBounds(201, 50, 181, 28);
		frame.getContentPane().add(comboBoxLaunchers);
		
		JLabel lblLaunchers = new JLabel("Launchers");
		lblLaunchers.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLaunchers.setBounds(201, 11, 181, 28);
		frame.getContentPane().add(lblLaunchers);
		
		comboBoxGrenades = new JComboBox();
		comboBoxGrenades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				changeIndexes(comboBoxGrenades, comboBoxGrenades.getSelectedIndex());
				
			}
		});
		comboBoxGrenades.setBounds(392, 50, 181, 28);
		frame.getContentPane().add(comboBoxGrenades);
		
		JLabel lblGrenades = new JLabel("Grenades");
		lblGrenades.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGrenades.setBounds(392, 11, 181, 28);
		frame.getContentPane().add(lblGrenades);
		
		comboBoxHeavyWeapons = new JComboBox();
		comboBoxHeavyWeapons.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeIndexes(comboBoxHeavyWeapons, comboBoxHeavyWeapons.getSelectedIndex());
			}
		});
		comboBoxHeavyWeapons.setBounds(583, 50, 181, 28);
		frame.getContentPane().add(comboBoxHeavyWeapons);
		
		JLabel lblHeavyWeapons = new JLabel("Heavy Weapons");
		lblHeavyWeapons.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHeavyWeapons.setBounds(583, 11, 181, 28);
		frame.getContentPane().add(lblHeavyWeapons);
		
		comboBoxOrdnance = new JComboBox();
		comboBoxOrdnance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeIndexes(comboBoxOrdnance, comboBoxOrdnance.getSelectedIndex());
			}
		});
		comboBoxOrdnance.setBounds(774, 50, 181, 28);
		frame.getContentPane().add(comboBoxOrdnance);
		
		JLabel lblOrdnance = new JLabel("Ordnance");
		lblOrdnance.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOrdnance.setBounds(774, 11, 181, 28);
		frame.getContentPane().add(lblOrdnance);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 187, 940, 563);
		frame.getContentPane().add(scrollPane);
		
		listIndividuals = new JList();
		scrollPane.setViewportView(listIndividuals);
		
		JLabel lblIndividuals = new JLabel("Individuals");
		lblIndividuals.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIndividuals.setBounds(10, 142, 104, 28);
		frame.getContentPane().add(lblIndividuals);
		
		JButton btnNewButton = new JButton("Apply Attack");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						if(comboBoxShellTypes.getSelectedIndex() > 0) {
							InjuryLog.InjuryLog.addAlreadyInjured();
							var shell = new Shell(ShellType.values()[comboBoxShellTypes.getSelectedIndex()-1]);
							Explosion explosion = new Explosion(shell);
							
							var targets = getTargets();
							
							if(targets != null && targets.size() > 0) {
								var target=targets.get(0);
								explosion.excludeTroopers.add(target);
								explosion.explodeTrooper(target, (int)spinnerDistanceToTarget.getValue());
							}
							
							
							for(int i = 0; i < (int)spinnerNumberOfAttacks.getValue(); i++) 
								explosion.explodeHex(hex.xCord, hex.yCord, "");

							InjuryLog.InjuryLog.printResultsToLog();
							GameWindow.gameWindow.conflictLog.addQueuedText();
							setIndividuals();
							listIndividuals.setSelectedIndex(-1);
							return null;
						}
						
						Weapons weapon = getSelectedWeapon();
						
						if(weapon == null) {
							GameWindow.gameWindow.conflictLog.addNewLine("Select a weapon...");
							listIndividuals.setSelectedIndex(-1);
							return null; 
						}
						
						try {
							InjuryLog.InjuryLog.addAlreadyInjured(hex.xCord, hex.yCord);
							GameWindow.gameWindow.conflictLog.addNewLineToQueue("MANUAL HEX ATTACK: "+getSelectedWeapon().name+", Manual Targets: "+listIndividuals.getSelectedIndices().length+", "
									+ "Distance: "+ (int) spinnerDistanceToTarget.getValue()+", Attacks: "+(int) spinnerNumberOfAttacks.getValue());
							applyHit(weapon, (int) spinnerNumberOfAttacks.getValue());					
							InjuryLog.InjuryLog.printResultsToLog();
							GameWindow.gameWindow.conflictLog.addQueuedText();
							setIndividuals();
							listIndividuals.setSelectedIndex(-1);
						} catch (Exception exception) {exception.printStackTrace();}
					
						return null;
					}

				};

				worker.execute();
				
			}
		});
		btnNewButton.setBounds(769, 111, 181, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNumberOfAttacks = new JLabel("Number of Attacks:");
		lblNumberOfAttacks.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNumberOfAttacks.setBounds(570, 107, 147, 28);
		frame.getContentPane().add(lblNumberOfAttacks);
		
		spinnerNumberOfAttacks = new JSpinner();
		spinnerNumberOfAttacks.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerNumberOfAttacks.setBounds(713, 111, 51, 20);
		frame.getContentPane().add(spinnerNumberOfAttacks);

		JLabel lblDistanceToTarget = new JLabel("Dist (2yd)");
		lblDistanceToTarget.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDistanceToTarget.setBounds(366, 142, 87, 28);
		frame.getContentPane().add(lblDistanceToTarget);
		
		spinnerDistanceToTarget = new JSpinner();
		spinnerDistanceToTarget.setBounds(449, 146, 51, 20);
		frame.getContentPane().add(spinnerDistanceToTarget);
		
		JLabel lblPcAmmo = new JLabel("PC Ammo:");
		lblPcAmmo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPcAmmo.setBounds(119, 142, 72, 28);
		frame.getContentPane().add(lblPcAmmo);
		
		comboBoxPCAmmo = new JComboBox();
		comboBoxPCAmmo.setModel(new DefaultComboBoxModel(new String[] {"None"}));
		comboBoxPCAmmo.setSelectedIndex(0);
		comboBoxPCAmmo.setBounds(201, 142, 155, 28);
		frame.getContentPane().add(comboBoxPCAmmo);

		
		comboBoxSide = new JComboBox();
		comboBoxSide.setModel(new DefaultComboBoxModel(new String[] {"None", "BLUFOR", "OPFOR"}));
		comboBoxSide.setSelectedIndex(0);
		comboBoxSide.setBounds(609, 142, 155, 28);
		frame.getContentPane().add(comboBoxSide);
		
		JLabel lblExcludeSide = new JLabel("Exclude Side:");
		lblExcludeSide.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblExcludeSide.setBounds(519, 142, 97, 28);
		frame.getContentPane().add(lblExcludeSide);
		
		JButton btnSuppressHex = new JButton("Suppress Hex");
		btnSuppressHex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				suppressHex(); 
				
			
			}
		});
		btnSuppressHex.setBounds(769, 145, 181, 23);
		frame.getContentPane().add(btnSuppressHex);
		
		JLabel lblShell = new JLabel("Shell");
		lblShell.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblShell.setBounds(10, 82, 181, 28);
		frame.getContentPane().add(lblShell);
		
		comboBoxShellTypes = new JComboBox();
		comboBoxShellTypes.setBounds(10, 112, 181, 28);
		frame.getContentPane().add(comboBoxShellTypes);
	
	}
	
	public class Box {
		String type; 
		JComboBox box; 
		
		public Box(JComboBox box, String type) {
			this.type = type;
			this.box = box; 
		}
		
		public int index() {
			return box.getSelectedIndex();
		}
	}
	
	
	public void suppressHex() {
		int suppressionHits = (int) spinnerNumberOfAttacks.getValue();
		
		if(suppressionHits < 1) {
			GameWindow.gameWindow.conflictLog.addNewLine("Define number of hits.");
			return; 
		}
		
		String rslts = "SUPPRESSED HEX: "+hex.xCord+", "+hex.yCord+"\n"+"Units: ";
		
		ArrayList<Unit> units = GameWindow.gameWindow.getUnitsInHexExcludingSide(comboBoxSide.getSelectedItem().toString(), hex.xCord, hex.yCord);
		
		for(Unit unit : units) {
			
			
			
			unit.suppression += suppressionHits;
			unit.organization -= suppressionHits;
			
			if(unit.suppression < 0)
				unit.suppression = 0; 
			
			if(unit.organization < 0)
				unit.organization = 0; 
			
			rslts += unit.callsign;
			
			// If unit in loop is NOT the last unit in the units list
			if(!units.get(units.size()-1).compareTo(unit)) {
				rslts += ", ";
			}
			
		}
		rslts += "\n" + "SUPPRESSION: "+suppressionHits;
		GameWindow.gameWindow.conflictLog.addNewLineToQueue(rslts);
		
	}
	
	public void applyHit(Weapons weapon, int hits) {
		if(weapon.type.equals("Small Arms") && listIndividuals.getSelectedIndices().length < 1) {
			GameWindow.gameWindow.conflictLog.addNewLine("Select targets...");
			return; 
		}
		
		System.out.println("Apply Hits: "+hits);
		
		for(int i = 0; i < hits; i++) {
			
			applyHit(weapon);
			
			
		}
		
	}
	
	public void applyHit(Weapons weapon) {
		Trooper target;
		var targets = getTargets();
		
		if(targets != null && targets.size() > 0) {
			int index = DiceRoller.roll(0, getTargets().size() - 1);
			target = targets != null ? targets.get(index) : null;
			System.out.println("Random Index: "+index);
		} else {
			target = null;
		}
		
		if(weapon.ballisticWeapon() && !weapon.type.equals("Grenade") && weapon.pcAmmoTypes.size() < 1) {
			if(target == null) {
				GameWindow.gameWindow.conflictLog.addNewLineToQueue("Target is null for balistic weapon");
				return;
			}
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Ballistic Hit In Hex: "+hex.xCord+", "+hex.yCord+", "+target.number+" "+target.name+", Distance: "+(int) spinnerDistanceToTarget.getValue());
			target.applyHit(weapon, (int) spinnerDistanceToTarget.getValue()); 
		}
		else if(weapon.explosiveWeapon() || weapon.pcAmmoTypes.size() > 0) {
			
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Explosion In Hex: "+hex.xCord+", "+hex.yCord);
			
			Explosion explosion;
			
			if(weapon.pcAmmoTypes.size() > 0) {
				explosion = new Explosion(weapon.pcAmmoTypes.get(comboBoxPCAmmo.getSelectedIndex()));
				System.out.println("Pass PC Ammo: "+weapon.pcAmmoTypes.get(comboBoxPCAmmo.getSelectedIndex()).name);
				System.out.println("Pass Type: "+weapon.type);
			} else {
				explosion = new Explosion(weapon); 
			}
			
			
			if(target != null) {
				GameWindow.gameWindow.conflictLog.addNewLineToQueue("EXPLOSIVE TARGET HIT: "+target.number+" "+target.name+", Distance: "+(int) spinnerDistanceToTarget.getValue());
				if((int) spinnerDistanceToTarget.getValue() == 0 && weapon.pcAmmoTypes.size() > 0) {
					GameWindow.gameWindow.conflictLog.addNewLineToQueue("TARGET IMPACTED: "+target.number+", by "+target.name+weapon.pcAmmoTypes.get(comboBoxPCAmmo.getSelectedIndex()).name);
					explosion.explosiveImpact(target, weapon.pcAmmoTypes.get(comboBoxPCAmmo.getSelectedIndex()), weapon);
				} 
				
				explosion.explodeTrooper(target, (int) spinnerDistanceToTarget.getValue());
				explosion.excludeTroopers.add(target);
			}
			
			explosion.explodeHex(hex.xCord, hex.yCord, "None");
		}
		
	} 
	
	public ArrayList<Trooper> getTargets() {
		
		ArrayList<Trooper> targets = new ArrayList<>(); 
		
		for(int i = 0; i < listIndividuals.getSelectedIndices().length; i++) {
			
			targets.add(individuals.get(listIndividuals.getSelectedIndices()[i]));
			
		}
		
		if(listIndividuals.getSelectedIndices().length < 1)
			return null;
		
		return targets; 
	}
	
	public Weapons getSelectedWeapon() {

		ArrayList<Box> boxes = new ArrayList<>(Arrays.asList(
				new Box(comboBoxSmallArms, "Small Arms"), new Box(comboBoxLaunchers, "Launcher"), 
				new Box(comboBoxGrenades, "Grenade"), new Box(comboBoxHeavyWeapons, "Heavy Weapons"), 
				new Box(comboBoxOrdnance, "Ordnance")));
		
		for(Box box : boxes) {
			int index = box.index() - 1; 
			if(index > -1) {
				Weapons weapons = new Weapons();
				weapons.createWeapons();
				//System.out.println("TEST PASS: "+weapons.getWeaponsFromType(box.type).get(index).name);
				return weapons.getWeaponsFromType(box.type).get(index);
			}
		}
		
		
		return null; 
	}
	
	public void setIndividuals() {
		
		individuals.clear();
		
		for(Unit unit : GameWindow.gameWindow.initiativeOrder) {
			if(unit.X != hex.xCord || unit.Y != hex.yCord) {
				continue; 
			}
			
			for(Trooper trooper : unit.individuals)
				individuals.add(trooper);
			
		}
		SwingUtility.setList(listIndividuals, SwingUtility.convertArrayToString(individuals));
	}
	
	public void changeIndexes(JComboBox comboBox, int index) {
		
		if(comboBox != comboBoxSmallArms && comboBoxSmallArms.getItemCount() > 0)
			comboBoxSmallArms.setSelectedIndex(0);
		
		if(comboBox != comboBoxLaunchers && comboBoxLaunchers.getItemCount() > 0)
			comboBoxLaunchers.setSelectedIndex(0);
		
		if(comboBox != comboBoxGrenades && comboBoxGrenades.getItemCount() > 0)
			comboBoxGrenades.setSelectedIndex(0);
		
		if(comboBox != comboBoxHeavyWeapons  && comboBoxHeavyWeapons.getItemCount() > 0)
			comboBoxHeavyWeapons.setSelectedIndex(0);
		
		if(comboBox != comboBoxOrdnance  && comboBoxOrdnance.getItemCount() > 0)
			comboBoxOrdnance.setSelectedIndex(0);
		
		comboBox.setSelectedIndex(index);
		
		
		comboBoxPCAmmo.removeAllItems();
		if(getSelectedWeapon() != null && getSelectedWeapon().pcAmmoTypes.size() > 0) {
			
			
			
			for(PCAmmo pcAmmo : getSelectedWeapon().pcAmmoTypes) {
				
				comboBoxPCAmmo.addItem(pcAmmo.name);
				
			}
			
		} else {
			comboBoxPCAmmo.addItem("None");
		}
		
	}
	
	public void setComboBoxes() {
	
		Weapons weapons = new Weapons();
		weapons.getWeapons();
		
		var shells = new ArrayList<String>();
		for(var shell : ShellType.values()) {
			shells.add(shell.toString());
		}
		
		SwingUtility.setComboBox(comboBoxSmallArms, Weapons.getNamesOfWeapons(weapons.getWeaponsFromType("Small Arms")), true, 0);
		SwingUtility.setComboBox(comboBoxLaunchers, Weapons.getNamesOfWeapons(weapons.getWeaponsFromType("Launcher")), true, 0);
		SwingUtility.setComboBox(comboBoxGrenades, Weapons.getNamesOfWeapons(weapons.getWeaponsFromType("Grenade")), true, 0);
		SwingUtility.setComboBox(comboBoxHeavyWeapons, Weapons.getNamesOfWeapons(weapons.getWeaponsFromType("Heavy Weapons")), true, 0);
		SwingUtility.setComboBox(comboBoxOrdnance, Weapons.getNamesOfWeapons(weapons.getWeaponsFromType("Ordnance")), true, 0);
		SwingUtility.setComboBox(comboBoxShellTypes, shells, true, 0);
		
		
		
		
	}
}
