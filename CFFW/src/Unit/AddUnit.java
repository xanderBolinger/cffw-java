package Unit;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import Company.EditCompany;
import Trooper.Trooper;
import Trooper.generateSquad;
import Unit.Unit;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddUnit implements Serializable {
	private Unit createdElement;
	private ArrayList<Trooper> individuals = new ArrayList<Trooper>();
	private JFrame frame;
	private int yLocation = 0; 
	private int xLocation = 0;
	private JComboBox comboBoxConcealment;
	private JComboBox comboBoxSpeed;
	private JSpinner spinnerX;
	private JSpinner spinnerY;
	private JLabel labelLocation;
	private JComboBox comboBoxSquad;
	private JComboBox comboBoxFaction;
	private JSpinner spinnerNumber;

	public AddUnit(EditCompany window) {
		 
		final JFrame f = new JFrame("Add Unit");
		f.setSize(604, 313);

		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;

		// Set the new frame location
		f.setLocation(x, y);

		f.getContentPane().setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		comboBoxFaction = new JComboBox();
		comboBoxFaction.setBounds(10, 68, 175, 25);
		comboBoxFaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*Empty
				Riflesquad
				Platoon Squad
				Company Squad
				Droid Riflesquad
				Heavy Droid Riflesquad
				
				Clone Trooper Phase 1
				CIS Droid Army
				UNSC
				Covenant
				
				*/
				

				
				if(comboBoxFaction.getSelectedItem().toString().equals("Clone Trooper Phase 1")) {
					comboBoxSquad.removeAllItems();
					comboBoxSquad.addItem("Empty");
					comboBoxSquad.addItem("Riflesquad");
					comboBoxSquad.addItem("Special Riflesquad");
					comboBoxSquad.addItem("Platoon Squad");
					comboBoxSquad.addItem("Company Squad");
					comboBoxSquad.addItem("Commando Squad");
				} else if(comboBoxFaction.getSelectedItem().toString().equals("CIS Battle Droid")) {
					comboBoxSquad.removeAllItems();
					comboBoxSquad.addItem("Empty");
					comboBoxSquad.addItem("Droid Riflesquad");
					comboBoxSquad.addItem("Droid Marksman");
					comboBoxSquad.addItem("Droid AT Specalists");
					comboBoxSquad.addItem("Droid Fire Support");
					comboBoxSquad.addItem("Heavy Droid Riflesquad");
					comboBoxSquad.addItem("Droid Integrated Squad");
				} else if(comboBoxFaction.getSelectedItem().toString().equals("UNSC")) {
					comboBoxSquad.removeAllItems();
					comboBoxSquad.addItem("Empty");
					comboBoxSquad.addItem("Riflesquad");
				
				} else if(comboBoxFaction.getSelectedItem().toString().equals("Covenant")) {
					comboBoxSquad.removeAllItems();
					comboBoxSquad.addItem("Empty");
					comboBoxSquad.addItem("Unggoy Lance");
					comboBoxSquad.addItem("Kig-Yar Lance - Marksman");
					comboBoxSquad.addItem("Kig-Yar Lance - Shields");
					comboBoxSquad.addItem("Unggoy Suicide Chargers");
				
				} else if(comboBoxFaction.getSelectedItem().toString().equals("Cordite Expansion")) {
					comboBoxSquad.removeAllItems();
					comboBoxSquad.addItem("Empty");
				}
				
				
				
				
			}
		});
		comboBoxFaction
				.setModel(new DefaultComboBoxModel(new String[] {"Empty", "Clone Trooper Phase 1", "CIS Battle Droid", "UNSC", "Covenant", "Cordite Expansion"}));
		comboBoxFaction.setSelectedIndex(0);

		comboBoxSquad = new JComboBox();
		comboBoxSquad.setBounds(191, 68, 175, 25);
		comboBoxSquad.setModel(new DefaultComboBoxModel(new String[] {"Empty"}));

		JTextPane textPaneUnit = new JTextPane();
		textPaneUnit.setBounds(10, 152, 578, 111);

		JTextField textFieldCallsing = new JTextField();
		textFieldCallsing.setBounds(412, 13, 166, 20);
		textFieldCallsing.setColumns(10);

		JButton btnAddUnit = new JButton("Add Unit to Company");
		btnAddUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddUnit.setBounds(195, 11, 155, 25);
		btnAddUnit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (createdElement != null) {
					
					
					if((int) spinnerNumber.getValue() > 1) {
						
						int number = (int) spinnerNumber.getValue();
						for(int i = 1; i <= number; i++) {
							
							generateSquad squad = new generateSquad(comboBoxFaction.getSelectedItem().toString(),
									comboBoxSquad.getSelectedItem().toString());
							
							// Instantiates unit based off of side and type
							individuals = squad.getSquad();
							
							
							Unit unit = new Unit(textFieldCallsing.getText()+i, 0, 0, individuals, 100, 0, 100, 0, 0, 20, 0, "No Contact");
							// Sets unit stat
							unit.X = xLocation; 
							unit.Y = yLocation; 
							unit.concealment = comboBoxConcealment.getSelectedItem().toString();
							unit.speed = comboBoxSpeed.getSelectedItem().toString();
							
							
							// Sets class variable
							createdElement = unit;
							// Sets text pain equal to trooper
							textPaneUnit.setText(createdElement.toString()+"\n Personel: "+unit.getTroopers().size()+"\n Location: "+unit.X+", "+unit.Y+"\nSpeed: "+unit.speed+"\nConcealment: "+unit.concealment);
							
							window.addUnit(createdElement);
							
						}
						
					} else {
						window.addUnit(createdElement);
					}
					
					
					window.refreshUnits();
				} else {
					textPaneUnit.setText("Generate Unit!!!");
				}
			}
		});

		JButton btnRollUnit = new JButton("Roll Unit");
		btnRollUnit.setBounds(10, 11, 175, 25);
		btnRollUnit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Checks to see if callsign empty
				if (textFieldCallsing.getText().equals("")) {
					textPaneUnit.setText("Fill in Callsign!");
				} else {

					
					generateSquad squad = new generateSquad(comboBoxFaction.getSelectedItem().toString(),
							comboBoxSquad.getSelectedItem().toString());
					
					// Instantiates unit based off of side and type
					individuals = squad.getSquad();
					
					
					Unit unit = new Unit(textFieldCallsing.getText(), 0, 0, individuals, 100, 0, 100, 0, 0, 20, 0, "No Contact");
					// Sets unit stat
					unit.X = xLocation; 
					unit.Y = yLocation; 
					unit.concealment = comboBoxConcealment.getSelectedItem().toString();
					unit.speed = comboBoxSpeed.getSelectedItem().toString();
					
					
					// Sets class variable
					createdElement = unit;
					// Sets text pain equal to trooper
					textPaneUnit.setText(createdElement.toString()+"\n Personel: "+unit.getTroopers().size()+"\n Location: "+unit.X+", "+unit.Y+"\nSpeed: "+unit.speed+"\nConcealment: "+unit.concealment);

				}

			}
		});

		JLabel lblCallsign = new JLabel("Callsign:");
		lblCallsign.setBounds(360, 16, 48, 14);
		lblCallsign.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		JLabel lblGarSquadSelect = new JLabel("Squad Select");
		lblGarSquadSelect.setBounds(205, 45, 175, 14);

		JLabel lblFaction = new JLabel("Faction");
		lblFaction.setBounds(10, 45, 175, 14);
		
		comboBoxConcealment = new JComboBox();
		comboBoxConcealment.setBounds(10, 112, 106, 20);
		comboBoxConcealment.setModel(new DefaultComboBoxModel(new String[] {"No Concealment ", "Level 1", "Level 2", "Level 3", "Level 4"}));
		comboBoxConcealment.setSelectedIndex(0);
		
		comboBoxSpeed = new JComboBox();
		comboBoxSpeed.setBounds(126, 112, 72, 20);
		comboBoxSpeed.setModel(new DefaultComboBoxModel(new String[] {"None", "Walk", "Crawl", "Rush"}));
		comboBoxSpeed.setSelectedIndex(0);
		
		JButton button = new JButton("Move");
		button.setBounds(208, 111, 76, 23);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				xLocation = (int) spinnerX.getValue();
				yLocation = (int) spinnerY.getValue();
				labelLocation.setText("Location: X: "+xLocation+" Y: "+yLocation);
			}
		});
		
		spinnerX = new JSpinner();
		spinnerX.setBounds(290, 112, 41, 20);
		
		spinnerY = new JSpinner();
		spinnerY.setBounds(341, 112, 40, 20);
		
		labelLocation = new JLabel("Location: X: 0, Y: 0");
		labelLocation.setBounds(399, 114, 133, 17);
		labelLocation.setFont(new Font("Calibri", Font.BOLD, 13));
		
		spinnerNumber = new JSpinner();
		spinnerNumber.setBounds(488, 42, 40, 20);
		f.getContentPane().setLayout(null);
		f.getContentPane().add(textPaneUnit);
		f.getContentPane().add(btnRollUnit);
		f.getContentPane().add(lblFaction);
		f.getContentPane().add(btnAddUnit);
		f.getContentPane().add(lblCallsign);
		f.getContentPane().add(textFieldCallsing);
		f.getContentPane().add(lblGarSquadSelect);
		f.getContentPane().add(spinnerNumber);
		f.getContentPane().add(comboBoxConcealment);
		f.getContentPane().add(comboBoxSpeed);
		f.getContentPane().add(button);
		f.getContentPane().add(spinnerX);
		f.getContentPane().add(spinnerY);
		f.getContentPane().add(labelLocation);
		f.getContentPane().add(comboBoxFaction);
		f.getContentPane().add(comboBoxSquad);
		
		JLabel lblNumberOfUnits = new JLabel("Number of Units");
		lblNumberOfUnits.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblNumberOfUnits.setBounds(360, 45, 118, 14);
		f.getContentPane().add(lblNumberOfUnits);
		f.setVisible(true);
	}
}
