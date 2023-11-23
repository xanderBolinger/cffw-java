package Unit;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import CharacterBuilder.CharacterBuilderWindow;
import Company.Company;
import Company.EditCompany;
import CorditeExpansion.CorditeExpansionWindow;
import CreateGame.SetupWindow;
import Individuals.EditIndividual;
import Items.BulkInventoryWindow;
import Trooper.Trooper;
import Unit.Unit.UnitType;
import UtilityClasses.Keyboard;
import UtilityClasses.PCUtility;
import UtilityClasses.UnitReorderListener;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class EditUnit implements Serializable {
	private ArrayList<Trooper> troopers = new ArrayList<Trooper>();
	private JTextField textFieldCallsign;
	private JList personnelListObject;
	private int locationX = 0; 
	private int locationY = 0; 
	public EditCompany window;
	private JSpinner spinnerOrganization;
	private JSpinner spinnerSuppression;
	private JSpinner spinnerMoral;
	private JSpinner spinnerCommandValue;
	private JSpinner spinnerFatiuge;
	private JSpinner spinnerRadius;
	private JSpinner spinnerY;
	private JSpinner spinnerX;
	private JComboBox comboBoxConcealment;
	private JComboBox comboBoxSpeed;
	private JLabel labelLocation;
	private JLabel lblActive;

	public SetupWindow setUpWindow; 
	private JComboBox comboBoxUnits;
	public Unit openUnit; 
	private JLabel lblCommandsl;
	private JComboBox comboBoxUnitType;
	/**
	 * Launch the application.
	 */
	public EditUnit(EditCompany window, Unit unit, int index, SetupWindow setUpWindow) {
		this.window = window;
		this.setUpWindow = setUpWindow;
		openUnit = unit; 
		
		EditUnit editUnit = this;
		final JFrame f = new JFrame("Edit Unit");
		f.setSize(866, 436);
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

		JScrollPane scrollPaneIndividuals = new JScrollPane();
		scrollPaneIndividuals.setBounds(10, 204, 830, 186);

		JLabel lblIndividuals = new JLabel("Personnel");
		lblIndividuals.setBounds(10, 172, 56, 14);
		lblIndividuals.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		JSpinner spinnerCohesion = new JSpinner();
		spinnerCohesion.setBounds(224, 13, 40, 20);

		JLabel lblCohesion = new JLabel("Cohesion");
		lblCohesion.setBounds(162, 16, 52, 14);
		lblCohesion.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		textFieldCallsign = new JTextField();
		textFieldCallsign.setBounds(58, 13, 86, 20);
		textFieldCallsign.setColumns(10);

		JLabel lblCallsign = new JLabel("Callsign");
		lblCallsign.setBounds(10, 16, 44, 14);
		lblCallsign.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		JButton btnDeleteUnit = new JButton("Delete Unit");
		btnDeleteUnit.setBounds(694, 52, 146, 23);
		btnDeleteUnit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				window.deleteUnit(index);
				window.refreshUnits();
				f.dispose();
			}
		});

		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.setBounds(694, 16, 146, 25);
		btnSaveChanges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textFieldCallsign.equals("") || textFieldCallsign.equals("Enter Name!!!")) {
					textFieldCallsign.setText("Enter Name!!!");
				} else {
					unit.callsign = textFieldCallsign.getText();
					unit.cohesion = (int) spinnerCohesion.getValue();

					unit.organization = (int) spinnerOrganization.getValue();
					unit.suppression = (int) spinnerSuppression.getValue();
					unit.moral = (int) spinnerMoral.getValue();

					unit.crawlProgress = (int) spinnerRadius.getValue();
					unit.commandValue = (int) spinnerCommandValue.getValue();
					unit.fatiuge = (int) spinnerFatiuge.getValue();

					unit.speed = comboBoxSpeed.getSelectedItem().toString();
					unit.concealment = comboBoxConcealment.getSelectedItem().toString();
					unit.X = locationX; 
					unit.Y = locationY; 
					int count = 0; 
					for(UnitType unitType : UnitType.values()) {
						
						if(count == comboBoxUnitType.getSelectedIndex()) {
							//System.out.println("Unit Type Actually Changed");
							unit.unitType = unitType; 
							break; 
						}
						
						count++;
					}
					
					unit.overwriteIndividuals(troopers);
					window.setUnit(unit, index);
					window.refreshUnits();
					f.dispose();
				}
			}
		});

		JLabel lblOrg = new JLabel("ORG");
		lblOrg.setBounds(10, 42, 52, 14);
		lblOrg.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		JLabel lblSupp = new JLabel("Supp");
		lblSupp.setBounds(10, 73, 52, 14);
		lblSupp.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		JLabel labelMoral = new JLabel("Moral");
		labelMoral.setBounds(10, 104, 52, 14);
		labelMoral.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		spinnerOrganization = new JSpinner();
		spinnerOrganization.setBounds(66, 39, 40, 20);

		spinnerSuppression = new JSpinner();
		spinnerSuppression.setBounds(66, 70, 40, 20);

		spinnerMoral = new JSpinner();
		spinnerMoral.setBounds(66, 101, 40, 20);

		JLabel lblCv = new JLabel("CV");
		lblCv.setBounds(110, 42, 52, 14);
		lblCv.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		JLabel lblFt = new JLabel("FT");
		lblFt.setBounds(110, 73, 52, 14);
		lblFt.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		JLabel lblRadius = new JLabel("Radius");
		lblRadius.setBounds(110, 104, 52, 14);
		lblRadius.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		spinnerCommandValue = new JSpinner();
		spinnerCommandValue.setBounds(166, 39, 40, 20);

		spinnerFatiuge = new JSpinner();
		spinnerFatiuge.setBounds(166, 70, 40, 20);

		spinnerRadius = new JSpinner();
		spinnerRadius.setBounds(166, 101, 40, 20);

		JList listIndividuals = new JList(UnitReorderListener.getListModel(unit.individuals, null));
		MouseAdapter listener = new UnitReorderListener(listIndividuals, unit.individuals, editUnit);
		listIndividuals.addMouseListener(listener);
		listIndividuals.addMouseMotionListener(listener);

		listIndividuals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = listIndividuals.getSelectedIndex();
				
				if(index < 0)
					return;
				
				if(!Keyboard.isKeyPressed(KeyEvent.VK_CONTROL) && !Keyboard.isKeyPressed(KeyEvent.VK_SHIFT)) {
					new EditIndividual(troopers.get(index), null, editUnit, index);
				}
				
				
				
				
			}
		});

		scrollPaneIndividuals.setViewportView(listIndividuals);
		f.setVisible(true);
		f.getContentPane().setLayout(null);
		personnelListObject = listIndividuals;
		f.getContentPane().add(scrollPaneIndividuals);
		f.getContentPane().add(lblCallsign);
		f.getContentPane().add(textFieldCallsign);
		f.getContentPane().add(lblCohesion);
		f.getContentPane().add(spinnerCohesion);
		f.getContentPane().add(lblOrg);
		f.getContentPane().add(spinnerOrganization);
		f.getContentPane().add(lblCv);
		f.getContentPane().add(spinnerCommandValue);
		f.getContentPane().add(lblSupp);
		f.getContentPane().add(spinnerSuppression);
		f.getContentPane().add(lblFt);
		f.getContentPane().add(spinnerFatiuge);
		f.getContentPane().add(btnDeleteUnit);
		f.getContentPane().add(btnSaveChanges);
		f.getContentPane().add(lblIndividuals);
		f.getContentPane().add(labelMoral);
		f.getContentPane().add(spinnerMoral);
		f.getContentPane().add(lblRadius);
		f.getContentPane().add(spinnerRadius);
		
		comboBoxConcealment = new JComboBox();
		comboBoxConcealment.setModel(new DefaultComboBoxModel(new String[] {"No Concealment ", "Level 1", "Level 2", "Level 3", "Level 4"}));
		comboBoxConcealment.setSelectedIndex(0);
		comboBoxConcealment.setBounds(10, 130, 134, 20);
		f.getContentPane().add(comboBoxConcealment);
		
		comboBoxSpeed = new JComboBox();
		comboBoxSpeed.setModel(new DefaultComboBoxModel(new String[] {"None", "Walk", "Crawl", "Rush"}));
		comboBoxSpeed.setSelectedIndex(0);
		comboBoxSpeed.setBounds(154, 130, 60, 20);
		f.getContentPane().add(comboBoxSpeed);
		
		JButton button = new JButton("Move");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				locationX = (int) spinnerX.getValue();
				locationY = (int) spinnerY.getValue();
				labelLocation.setText("Location: X: "+locationX+", Y: "+locationY);
			}
		});
		button.setBounds(274, 12, 68, 23);
		f.getContentPane().add(button);
		
		spinnerX = new JSpinner();
		spinnerX.setBounds(352, 13, 41, 20);
		f.getContentPane().add(spinnerX);
		
		spinnerY = new JSpinner();
		spinnerY.setBounds(403, 13, 40, 20);
		f.getContentPane().add(spinnerY);
		
		labelLocation = new JLabel("Location: X: 0, Y: 0");
		labelLocation.setFont(new Font("Calibri", Font.BOLD, 13));
		labelLocation.setBounds(76, 171, 133, 17);
		f.getContentPane().add(labelLocation);
		
		lblActive = new JLabel("Active/Unactive: activated");
		lblActive.setFont(new Font("Calibri", Font.BOLD, 13));
		lblActive.setBounds(224, 169, 212, 17);
		f.getContentPane().add(lblActive);
		
		JButton btnActivatedeactiavte = new JButton("Activate/Deactivate");
		btnActivatedeactiavte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(unit.active) {
					unit.active = false; 
				} else {
					unit.active = true; 
				}
				
				if(unit.active) {
					lblActive.setText("Active/Unactive: activated");
				} else {
					lblActive.setText("Active/Unactive: deactivated");
				}
				
			}
		});
		btnActivatedeactiavte.setBounds(695, 86, 145, 23);
		f.getContentPane().add(btnActivatedeactiavte);
		
		JButton btnHealAll = new JButton("Reset All");
		btnHealAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(Trooper trooper : unit.individuals) {
					trooper.spentPhase1 = 0; 
					trooper.spentPhase2 = 0; 
					trooper.HD = false; 
					trooper.inCover = false; 
					
					trooper.injuries.clear();
					trooper.physicalDamage = 0; 
					trooper.disabledArms = 0; 
					trooper.disabledLegs = 0; 
					trooper.arms = 2; 
					trooper.legs = 2; 
					trooper.recoveryRoll = 0; 
					trooper.criticalTime = 0; 
					trooper.timePassed = 0; 
					trooper.timeMortallyWounded = 0; 
					trooper.timeUnconscious = 0; 
					trooper.alive = true; 
					trooper.number = 0; 
					trooper.conscious = true; 
					
					int actionPoints = trooper.combatActions; 
					if(actionPoints % 2 == 0) {
						trooper.P1 = actionPoints / 2; 
						trooper.P2 = actionPoints / 2; 
					} else {
						trooper.P1 = actionPoints / 2; 
						trooper.P2 = actionPoints / 2 + 1; 
					}
					
					trooper.spotted.clear();
					trooper.storedAimTime.clear();
					trooper.fatigueSystem.fatiguePoints.set(0.0);
				}
				
				unit.lineOfSight.clear();
				
				refreshIndividuals();
				
			}
		});
		btnHealAll.setBounds(694, 121, 146, 23);
		f.getContentPane().add(btnHealAll);
		
		JButton btnAddTo = new JButton("Add to Unit");
		btnAddTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean found = false; 
				
				String unitString = comboBoxUnits.getSelectedItem().toString();
				
				Unit transferUnit = null; 
				
				for(Company company : setUpWindow.companies) {

					for(Unit unit : company.getUnits()) {

						if(unitString.contains(unit.callsign)) {			
							transferUnit = unit; 
							found = true; 
							break; 
						}

					}
				}
				
				if(!found) {
					System.out.println("Transfer unit not found.");
					return; 
				}
				
				ArrayList<Trooper> selectedTroopers = getSelectedTroopers(unit);
				
				if(selectedTroopers.size() < 1) {
					System.out.println("No selected troopers.");
					return; 
				}
				
				for(Trooper trooper : selectedTroopers) {
					transferUnit.addToUnit(trooper);
					unit.removeFromUnit(trooper);
				}

				refreshIndividuals();
			}
		});
		btnAddTo.setBounds(564, 163, 120, 25);
		f.getContentPane().add(btnAddTo);
		
		comboBoxUnits = new JComboBox();
		DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
		for(Unit otherUnit : setUpWindow.getAllUnits()) {
			if(!otherUnit.compareTo(unit))
				comboBoxModel.addElement(otherUnit.side +":: "+otherUnit.callsign);
		}
		
		comboBoxUnits.setModel(comboBoxModel);
		comboBoxUnits.setSelectedIndex(-1);
		comboBoxUnits.setBounds(403, 165, 157, 20);
		f.getContentPane().add(comboBoxUnits);
		
		JButton btnAddToRoster = new JButton("Add to Roster");
		btnAddToRoster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				


				ArrayList<Trooper> selectedTroopers = getSelectedTroopers(unit);
				
				if(selectedTroopers.size() < 1) {
					System.out.println("No selected troopers.");
					return; 
				}
				
				for(Trooper trooper : selectedTroopers) {
					
					if(unit.individuals.contains(trooper))
						unit.individuals.remove(trooper);
					
					window.addIndividual(trooper);
				}
				
				
				//f.dispose();
				refreshIndividuals();
				window.refreshIndividuals();
				
			}
		});
		btnAddToRoster.setBounds(694, 163, 146, 25);
		f.getContentPane().add(btnAddToRoster);
		
		lblCommandsl = new JLabel("Command/SL: ");
		lblCommandsl.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblCommandsl.setBounds(453, 16, 219, 14);
		f.getContentPane().add(lblCommandsl);
		
		JButton btnOpenCharacterBuilder = new JButton("Open Character Builder");
		btnOpenCharacterBuilder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CharacterBuilderWindow(troopers);
			}
		});
		btnOpenCharacterBuilder.setBounds(403, 121, 281, 25);
		f.getContentPane().add(btnOpenCharacterBuilder);
		
		JButton btnCreateCeWindow = new JButton("Create CE Window");
		btnCreateCeWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CorditeExpansionWindow(troopers);
			}
		});
		btnCreateCeWindow.setBounds(404, 86, 281, 25);
		f.getContentPane().add(btnCreateCeWindow);
		
		JButton btnBulkInventory = new JButton("Bulk Inventory");
		btnBulkInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BulkInventoryWindow(troopers);
			}
		});
		btnBulkInventory.setBounds(403, 52, 281, 25);
		f.getContentPane().add(btnBulkInventory);
		
		JLabel lblUnitType = new JLabel("Unit Type");
		lblUnitType.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblUnitType.setBounds(224, 104, 52, 14);
		f.getContentPane().add(lblUnitType);
		
		comboBoxUnitType = new JComboBox();
		comboBoxUnitType.setSelectedIndex(-1);
		comboBoxUnitType.setBounds(224, 129, 134, 20);
		f.getContentPane().add(comboBoxUnitType);

		if (unit != null) {
			// Sets Troopers list
			troopers = unit.getTroopers();
			textFieldCallsign.setText(unit.callsign);
			// Populates list
			setTroopers(troopers);
			// Sets spinners
			spinnerCohesion.setValue((int) unit.cohesion);
			spinnerOrganization.setValue((int) unit.organization);
			spinnerSuppression.setValue((int) unit.suppression);
			spinnerMoral.setValue((int) unit.moral);

			spinnerRadius.setValue((int) unit.crawlProgress);
			spinnerCommandValue.setValue((int) unit.commandValue);
			spinnerFatiuge.setValue((int) unit.fatiuge);

			if(unit.active) {
				lblActive.setText("Active/Unactive: activated");
			} else {
				lblActive.setText("Active/Unactive: deactivated");
			}
			
			
			// Sets combo boxes 
			// Sets unit comboBoxes
			String speed = unit.speed;
			if (speed.equals("None")) {
				comboBoxSpeed.setSelectedIndex(0);
			} else if (speed.equals("Walk")) {
				comboBoxSpeed.setSelectedIndex(1);
			} else if (speed.equals("Crawl")) {
				comboBoxSpeed.setSelectedIndex(2);
			} else if (speed.equals("Rush")) {
				comboBoxSpeed.setSelectedIndex(3);
			}

			String concealment = unit.concealment;
			if (concealment.equals("Level 0")) {
				comboBoxConcealment.setSelectedIndex(0);
			} else if (concealment.equals("Level 1")) {
				comboBoxConcealment.setSelectedIndex(1);
			} else if (concealment.equals("Level 2")) {
				comboBoxConcealment.setSelectedIndex(2);
			} else if (concealment.equals("Level 3")) {
				comboBoxConcealment.setSelectedIndex(3);
			} else if (concealment.equals("Level 4")) {
				comboBoxConcealment.setSelectedIndex(4);
			} else if (concealment.equals("Level 5")) {
				comboBoxConcealment.setSelectedIndex(5);
			}
			
			locationX = unit.X; 
			locationY = unit.Y;
			
			labelLocation.setText("Location: X: "+locationX+", Y: "+locationY);
			
			
			refreshIndividuals();
		}
		
		int count = 0; 
		for(UnitType unitType : UnitType.values()) {
			
			comboBoxUnitType.addItem(unitType.toString());
			
			if(unitType == unit.unitType)
				comboBoxUnitType.setSelectedIndex(count);
			
			count++; 
		}

	}

	public void setTroopers(ArrayList<Trooper> troopers) {
		if (troopers == null) {
			return;
		} else if (troopers.size() == 0) {
			return;
		}

		DefaultListModel trooperList = new DefaultListModel();

		for (int i = 0; i < troopers.size(); i++) {
			trooperList.addElement(troopers.get(i));
			;
		}

		personnelListObject.setModel(trooperList);
	}
	
	// Gets selected individuals from bulk trooper 
	public ArrayList<Trooper> getSelectedTroopers(Unit unit) {
		
		ArrayList<Trooper> troopers = new ArrayList<Trooper>();
		//System.out.println("Get Individuals 1");
		int[] indexes = personnelListObject.getSelectedIndices();
		//System.out.println("Get Individuals 2, indexes: "+indexes.length);
		for(int index : indexes) {
			
			troopers.add(unit.individuals.get(index));
			
		}
		
		//System.out.println("Get Individuals 3");
		return troopers; 
	} 

	// Refresh troopers
	public void refreshIndividuals() {
		
		DefaultListModel individualList = new DefaultListModel();

		for (int i = 0; i < troopers.size(); i++) {
			individualList.addElement(troopers.get(i).toStringImproved(null));
			
		}

		personnelListObject.setModel(individualList);
		setCommandSl();
	}

	public void setCommandSl() {
		if(troopers.size() < 1) {
			lblCommandsl.setText("");
			return; 
		}
		
		int average = troopers.get(0).skills.getSkill("Command").value + troopers.get(0).skills.getSkill("Fighter").value; 
		
		for(Trooper trooper : troopers) {
			if(!trooper.alive || !trooper.conscious || trooper.physicalDamage > trooper.KO * 5)
				continue; 
			
			average += PCUtility.getSL((trooper.skills.getSkill("Command").value + trooper.skills.getSkill("Fighter").value));
		}
		
		lblCommandsl.setText("Command: "+(average/2)+"/"+PCUtility.getSL(average));
	}
	
	// Overwrites a trooper
	// Used in save changes edit individual
	// Set unit
	public void setTrooper(Trooper trooper, int index) {
		troopers.set(index, trooper);
	}

	// Delete Individual
	public void deleteIndividual(int index) {
		troopers.remove(index);
	}
}
