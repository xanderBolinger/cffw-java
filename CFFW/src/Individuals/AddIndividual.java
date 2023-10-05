package Individuals;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import Company.EditCompany;
import CreateGame.JsonSaveRunner;
import Trooper.Trooper;
import Trooper.Factions.Astartes;
import Trooper.Factions.FactionManager;
import UtilityClasses.SwingUtility;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JSpinner;

public class AddIndividual implements Serializable {

	private JFrame frame;
	private Trooper createdIndividual;
	private JComboBox comboBoxFaction;
	private JComboBox comboBoxRole;
	private JTextPane textPaneTrooper;
	private JLabel lblCount;
	private JSpinner spinnerNumber;
	private JComboBox comboBoxJson;

	/**
	 * Launch the application.
	 */
	public AddIndividual(EditCompany window) {
		init(window);
		addFactions();
	}
	
	void addFactions() {
		
		
	}
	
	void init(EditCompany window) {
		
		final JFrame f = new JFrame("Add Individual");
		f.setSize(524, 350);
		f.getContentPane().setLayout(null);
		f.setVisible(true);
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;

		// Set the new frame location
		f.setLocation(x, y);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Combo Boxes, Dropdown menus

		comboBoxFaction = new JComboBox();
		comboBoxFaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxFaction.getSelectedItem().toString().equals("Clone Trooper Phase 1")) {
					comboBoxRole.removeAllItems();
					comboBoxRole.addItem("Empty");

					comboBoxRole.addItem("Clone Squad Leader");
					comboBoxRole.addItem("Clone Rifleman");
					comboBoxRole.addItem("Clone Rifleman++");
					comboBoxRole.addItem("Clone Autorifleman");
					comboBoxRole.addItem("Clone Assistant Autorifleman");
					comboBoxRole.addItem("Clone Ammo Bearer");
					comboBoxRole.addItem("Clone Marksman");
					comboBoxRole.addItem("Clone Combat Life Saver");
					comboBoxRole.addItem("EOD");
					comboBoxRole.addItem("Clone AT Specialist");
					comboBoxRole.addItem("Clone Assistant AT Specialist");
					comboBoxRole.addItem("Clone Sniper");
					comboBoxRole.addItem("Clone Spotter");
					comboBoxRole.addItem("Clone Special Operations Sniper");
					comboBoxRole.addItem("Clone Special Operations Spotter");
					comboBoxRole.addItem("Ranger");
					comboBoxRole.addItem("Clone Platoon Leader");
					comboBoxRole.addItem("Clone Captain");
					comboBoxRole.addItem("ARC Trooper");
					comboBoxRole.addItem("Republic Commando");
					
				} else if(comboBoxFaction.getSelectedItem().toString().equals("CIS Battle Droid")) {
					comboBoxRole.removeAllItems();
					comboBoxRole.addItem("Empty");

					comboBoxRole.addItem("B1 Squad Leader");
					comboBoxRole.addItem("B1 Rifleman");
					//comboBoxRole.addItem("B1 Rifleman++");
					comboBoxRole.addItem("B1 Autorifleman");
					comboBoxRole.addItem("B1 Assistant Autorifleman");
					comboBoxRole.addItem("B1 Ammo Bearer");
					comboBoxRole.addItem("B1 Marksman");
					//comboBoxRole.addItem("B1 Combat Life Saver");
					//comboBoxRole.addItem("B1 EOD");
					comboBoxRole.addItem("B1 AT Specialist");
					comboBoxRole.addItem("B1 Assistant AT Specialist");
					//comboBoxRole.addItem("B1 Ranger");
					//comboBoxRole.addItem("B1 Platoon Sergeant");
					//comboBoxRole.addItem("B1 Captain");
					comboBoxRole.addItem("B2");
					comboBoxRole.addItem("Commando Droid Squad Leader");
					comboBoxRole.addItem("Commando Droid Rifleman");
					//comboBoxRole.addItem("Commando Droid Rifleman++");
					comboBoxRole.addItem("Commando Droid Autorifleman");
					comboBoxRole.addItem("Commando Droid Assistant Autorifleman");
					comboBoxRole.addItem("Commando Droid Ammo Bearer");
					comboBoxRole.addItem("Commando Droid Marksman");
					//comboBoxRole.addItem("Commando Droid Combat Life Saver");
					//comboBoxRole.addItem("Commando Droid EOD");
					comboBoxRole.addItem("Commando Droid AT Specialist");
					comboBoxRole.addItem("Commando Droid Assistant AT Specialist");
					//comboBoxRole.addItem("Commando Droid Ranger");
					//comboBoxRole.addItem("Commando Droid Platoon Sergeant");
					//comboBoxRole.addItem("Commando Droid Captain");
					comboBoxRole.addItem("Magma Guard");
				
				} else if(comboBoxFaction.getSelectedItem().toString().equals("UNSC")) {
					comboBoxRole.removeAllItems();
					comboBoxRole.addItem("Empty");
					
					comboBoxRole.addItem("Squad Leader");
					comboBoxRole.addItem("Rifleman");
					comboBoxRole.addItem("Rifleman++");
					comboBoxRole.addItem("Autorifleman");
					comboBoxRole.addItem("Assistant Autorifleman");
					comboBoxRole.addItem("Ammo Bearer");
					comboBoxRole.addItem("Marksman");
					comboBoxRole.addItem("Combat Life Saver");
					comboBoxRole.addItem("EOD");
					comboBoxRole.addItem("AT Specialist");
					comboBoxRole.addItem("Assistant AT Specialist");
					comboBoxRole.addItem("Ranger");
					comboBoxRole.addItem("Platoon Sergeant");
					comboBoxRole.addItem("Captain");
					comboBoxRole.addItem("ODST - Rifleman");
					comboBoxRole.addItem("Squad Leader BR");
					comboBoxRole.addItem("Rifleman BR");
					comboBoxRole.addItem("Rifleman++ BR");
					comboBoxRole.addItem("Combat Life Saver BR");

				} else if(comboBoxFaction.getSelectedItem().toString().equals("Covenant")) {
					comboBoxRole.removeAllItems();
					comboBoxRole.addItem("Empty");
					
					comboBoxRole.addItem("Elite Minor - Lance Leader");
					comboBoxRole.addItem("Grunt Minor - Rifle Variant");
					comboBoxRole.addItem("Grunt Minor - Carbine Variant");
					comboBoxRole.addItem("Grunt Minor - Pistol Variant");
					comboBoxRole.addItem("Jackal Minor - Carbine Variant");
					comboBoxRole.addItem("Jackal Minor - Sniper Variant");
					comboBoxRole.addItem("Jackal Minor - Pistol Variant");
				
				} else if(comboBoxFaction.getSelectedItem().toString().equals("Cordite Expansion")) {
					comboBoxRole.removeAllItems();
					comboBoxRole.addItem("Empty");
					
					comboBoxRole.addItem("Untrained");
					comboBoxRole.addItem("Militia");
					comboBoxRole.addItem("Green");
					comboBoxRole.addItem("Line");
					comboBoxRole.addItem("Crack");
					comboBoxRole.addItem("Elite");
				} else {
					
					try {
						FactionManager.getFactionFromName(
								comboBoxFaction.getSelectedItem().toString()
								).individualInput(comboBoxRole);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		comboBoxFaction
				.setModel(new DefaultComboBoxModel(new String[] {"Empty", "Clone Trooper Phase 1", "CIS Battle Droid", "UNSC", "Covenant", "Cordite Expansion", "Astartes"}));
		comboBoxFaction.setSelectedIndex(0);

		comboBoxRole = new JComboBox();
		comboBoxRole.setModel(new DefaultComboBoxModel(new String[] {"Empty"}));

		// Text pane
		textPaneTrooper = new JTextPane();

		// Buttons

		JButton btnGenerateTrooper = new JButton("Generate Trooper");
		btnGenerateTrooper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Trooper individual = null;
				var faction = comboBoxFaction.getSelectedItem().toString();
				// Generates individual
				if(comboBoxJson.getSelectedIndex() > 0) {
					try {
						individual = JsonSaveRunner.loadTrooper(JsonSaveRunner.loadFileFromName(comboBoxJson.getSelectedItem().toString()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else if(comboBoxFaction.getSelectedItem().toString().equals("Clone Trooper Phase 1") || comboBoxFaction.getSelectedItem().toString().equals("CIS Battle Droid")) {
					individual = new Trooper(comboBoxRole.getSelectedItem().toString(),
							comboBoxFaction.getSelectedItem().toString());
				} else if(FactionManager.factionExists(faction)) {
					
					
					
				} 
				
				else {
					individual = new Trooper(comboBoxRole.getSelectedItem().toString(),
							comboBoxFaction.getSelectedItem().toString());
				} 
				
				
				
				// Sets class variable
				createdIndividual = individual;
				// Sets text pain equal to trooper
				textPaneTrooper.setText(individual.toString());
			}
		});
		btnGenerateTrooper.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});

		JButton btnAddToRoster = new JButton("Add to Roster");
		btnAddToRoster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (createdIndividual != null) {

					
					if((int) spinnerNumber.getValue() > 1) {
						
						
						for(int i = 1; i <= (int) spinnerNumber.getValue(); i++) {
							Trooper individual = null;
							
							// Generates individual
							// Generates individual
							if(comboBoxJson.getSelectedIndex() > 0) {
								try {
									individual = JsonSaveRunner.loadTrooper(JsonSaveRunner.loadFileFromName(comboBoxJson.getSelectedItem().toString()));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
							else if(comboBoxFaction.getSelectedItem().toString().equals("Clone Trooper Phase 1") || comboBoxFaction.getSelectedItem().toString().equals("CIS Battle Droid")) {
								individual = new Trooper(comboBoxRole.getSelectedItem().toString(),
										comboBoxFaction.getSelectedItem().toString());
							} else {
								individual = new Trooper(comboBoxRole.getSelectedItem().toString(),
										comboBoxFaction.getSelectedItem().toString());
							}
							
							
							
							// Sets class variable
							createdIndividual = individual;
							// Sets text pain equal to trooper
							textPaneTrooper.setText(individual.toString());
							
							window.addIndividual(createdIndividual);
						}
						
						
						
					} else {
						window.addIndividual(createdIndividual);
					}
					
					
					
					window.refreshIndividuals();
					
					
					
					
				} else {
					textPaneTrooper.setText("Create trooper!!!");
				}

			}
		});
		btnAddToRoster.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		lblCount = new JLabel("Count:");
		
		spinnerNumber = new JSpinner();
		
		comboBoxJson = new JComboBox();
		comboBoxJson.setModel(new DefaultComboBoxModel(new String[] {"Select"}));
		comboBoxJson.setSelectedIndex(0);

		GroupLayout groupLayout = new GroupLayout(f.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(textPaneTrooper, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(btnGenerateTrooper, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(comboBoxFaction, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnAddToRoster, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblCount)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(spinnerNumber, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
								.addComponent(comboBoxRole, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE))))
					.addGap(63))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboBoxJson, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(278, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnGenerateTrooper, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAddToRoster, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCount)
						.addComponent(spinnerNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBoxFaction, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxRole, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxJson, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addComponent(textPaneTrooper, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
					.addGap(6))
		);
		f.getContentPane().setLayout(groupLayout);
		f.setVisible(true);
		
		
		SwingUtility.setComboBox(comboBoxJson, JsonSaveRunner.getFileNames(), true, 0);
		
		
	}
}
