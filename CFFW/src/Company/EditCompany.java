package Company;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;

import CreateGame.JsonSaveRunner;
import CreateGame.SetupWindow;
import Individuals.AddIndividual;
import Individuals.EditIndividual;
import Items.BulkInventoryWindow;
import Medical.MedicalWindow;
import OperationExporter.OperationUnitExporter;
import Trooper.Trooper;
import Unit.AddUnit;
import Unit.EditUnit;
import Unit.Unit;
import UtilityClasses.Keyboard;

import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditCompany implements Serializable {
	private ArrayList<Trooper> roster = new ArrayList<Trooper>();
	private ArrayList<Unit> units = new ArrayList<Unit>();

	private JFrame f;
	private JList listUnits;
	private JList listTroopers;
	private JComboBox comboBoxUnit;
	private JLabel lblUnits;
	private JLabel lblDisruptedUnits;
	private JLabel lblStatus;
	private JLabel lblFracturedUnits;
	private JLabel lblRoutedUnits;
	private JLabel lblTroopers;
	private JTextField textFieldName;
	Company company; 
	private JSpinner spinnerPlatoon;
	private JSpinner spinnerSquad;
	private JButton Formation;
	
	public EditCompany(Company company, SetupWindow setupWindow, int index) {
		EditCompany window = this;
		this.company = company;
		f = new JFrame("Edit Comapny");
		f.setSize(800, 599);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;

		// Set the new frame location
		f.setLocation(x, y);
		
		f.setVisible(true);
		
		// Closes if side is null
		if (company == null) {
			f.dispose();
		}

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 120, 50);

		JLabel label = new JLabel("Name:");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		panel.add(label);

		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		panel.add(textFieldName);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(140, 11, 120, 50);

		spinnerPlatoon = new JSpinner();

		JLabel label_1 = new JLabel("Platoons");
		label_1.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup().addGap(35)
				.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(spinnerPlatoon, Alignment.LEADING).addComponent(label_1, Alignment.LEADING,
								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(5).addComponent(label_1)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(spinnerPlatoon,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(5)));
		panel_1.setLayout(gl_panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(270, 11, 135, 50);

		spinnerSquad = new JSpinner();

		JLabel label_2 = new JLabel("Squads");
		label_2.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(
						gl_panel_2.createParallelGroup(Alignment.LEADING).addGap(0, 135, Short.MAX_VALUE)
								.addGroup(
										gl_panel_2.createSequentialGroup().addGap(46)
												.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
														.addComponent(spinnerSquad, GroupLayout.PREFERRED_SIZE, 49,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_2))
												.addGap(40)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGap(0, 50, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup().addGap(5).addComponent(label_2)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(spinnerSquad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 210, 378, 339);

		listUnits = new JList();
		listUnits.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(units.size() < 1 || listUnits.getSelectedIndex() < 0)
					return; 
				
				
				if(!Keyboard.isKeyPressed(KeyEvent.VK_CONTROL) && !Keyboard.isKeyPressed(KeyEvent.VK_SHIFT) 
						&& e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					new EditUnit(window, units.get(listUnits.getSelectedIndex()), listUnits.getSelectedIndex(), setupWindow);					
				}

			}
		});
		scrollPane.setViewportView(listUnits);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(398, 210, 380, 339);

		listTroopers = new JList();
		listTroopers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listTroopers.getSelectedIndex() < 0)
					return; 
				
				int index = listTroopers.getSelectedIndex();
				
				if(!Keyboard.isKeyPressed(KeyEvent.VK_CONTROL) && !Keyboard.isKeyPressed(KeyEvent.VK_SHIFT) 
						&& e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					new EditIndividual(roster.get(index), window, null, index);	
				}
				
			
				
				
			}
		});
		scrollPane_1.setViewportView(listTroopers);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(522, 11, 79, 23);
		btnDelete.setForeground(Color.BLACK);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					setupWindow.deleteCompany(index);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setupWindow.refreshCreated();
				f.dispose();
			}
		});

		JButton btnSaveChanges = new JButton("Save");
		btnSaveChanges.setBounds(619, 11, 155, 23);

		JButton button_1 = new JButton("Roster");
		button_1.setBounds(100, 72, 89, 23);

		JButton button = new JButton("Units");
		button.setBounds(10, 72, 80, 23);

		JButton btnActivatedeactivate = new JButton("Activate/Deactivate");
		btnActivatedeactivate.setBounds(522, 52, 252, 23);
		btnActivatedeactivate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean activated = false;
				if (company.getActivated() == false) {
					activated = true;
				}

				company.setActivated(activated);
				setupWindow.refreshCreated();
			}
		});
		
		JButton btnExportToExcel = new JButton("Export");
		btnExportToExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OperationUnitExporter.exportCompanyToUserLocation(company);
			}
		});
		btnExportToExcel.setBounds(10, 176, 95, 23);
		btnExportToExcel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				//System.out.println("pass123");
				
				/*try {
					exportToExcel(company);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
			}
		});
		
		comboBoxUnit = new JComboBox();
		comboBoxUnit.setBounds(398, 177, 248, 20);
		
		JButton btnNewButton = new JButton("Add To Unit");
		btnNewButton.setBounds(664, 176, 110, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxUnit.getSelectedIndex() < 0)
					return;
				
				Unit addUnit;
				
				if(comboBoxUnit.getSelectedIndex() > 0)
					addUnit = units.get(comboBoxUnit.getSelectedIndex()-1);
				else
					addUnit = units.get(listUnits.getSelectedIndex());
				
				int count = 0; 
				for(int i : listTroopers.getSelectedIndices()) {
					addUnit.individuals.add(roster.remove(i-count));
					count++; 
				}
				
				refreshIndividuals();
				refreshUnits();
			}
		});
		
		JButton btnActivatedeactivate_1 = new JButton("Activate/Deactivate");
		btnActivatedeactivate_1.setBounds(111, 176, 173, 23);
		btnActivatedeactivate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				for(int i : listUnits.getSelectedIndices()) {
					Unit unit = units.get(i);
					
					if(unit.active) {
						unit.active = false; 
					} else {
						unit.active = true; 
					}
					
				}
				
				refreshUnits();
			}
		});
		f.getContentPane().setLayout(null);
		f.getContentPane().add(scrollPane);
		f.getContentPane().add(btnExportToExcel);
		f.getContentPane().add(btnActivatedeactivate_1);
		f.getContentPane().add(comboBoxUnit);
		f.getContentPane().add(btnNewButton);
		f.getContentPane().add(scrollPane_1);
		f.getContentPane().add(panel);
		f.getContentPane().add(panel_1);
		f.getContentPane().add(panel_2);
		f.getContentPane().add(button);
		f.getContentPane().add(button_1);
		f.getContentPane().add(btnDelete);
		f.getContentPane().add(btnSaveChanges);
		f.getContentPane().add(btnActivatedeactivate);
		
		lblUnits = new JLabel("Units:");
		lblUnits.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblUnits.setBounds(199, 76, 80, 14);
		f.getContentPane().add(lblUnits);
		
		lblDisruptedUnits = new JLabel("Disrupted Units:");
		lblDisruptedUnits.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblDisruptedUnits.setBounds(10, 106, 110, 14);
		f.getContentPane().add(lblDisruptedUnits);
		
		lblFracturedUnits = new JLabel("Fractured Units:");
		lblFracturedUnits.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblFracturedUnits.setBounds(10, 131, 110, 14);
		f.getContentPane().add(lblFracturedUnits);
		
		lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblStatus.setBounds(280, 76, 80, 14);
		f.getContentPane().add(lblStatus);
		
		lblRoutedUnits = new JLabel("Routed Units:");
		lblRoutedUnits.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblRoutedUnits.setBounds(10, 151, 110, 14);
		f.getContentPane().add(lblRoutedUnits);
		
		lblTroopers = new JLabel("Toopers:");
		lblTroopers.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblTroopers.setBounds(199, 106, 321, 14);
		f.getContentPane().add(lblTroopers);
		
		JButton btnReorg = new JButton("Re-Org");
		btnReorg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean createdSw = false; 
				boolean createdD = false; 
				
				Unit deadUnit = new Unit("Dead", 0, 0, new ArrayList<Trooper>(), 100, 100, 100, 0, 100, 20, 6, "No Contact");
				Unit swUnit = new Unit("Seriously Wounded", 0, 0, new ArrayList<Trooper>(), 100, 100, 100, 0, 100, 20, 6, "No Contact");
				
				for(Unit unit : units) {
					if(unit.callsign.equals("Seriously Wounded")) {
						createdSw = true; 
						swUnit = unit;
					}
					if(unit.callsign.equals("Dead")) {
						createdD = true; 
						deadUnit = unit;
					}
				}
				
				
				
				if(!createdSw) {
					units.add(swUnit);
				} 
				
				
				
				if(!createdD) {
					units.add(deadUnit);
				} 
				
				ArrayList<Trooper> sw = new ArrayList<>();
				ArrayList<Trooper> dead = new ArrayList<>();
				
				for(Unit unit : units) {
					
					unit.organization = 100; 
					unit.moral = 100; 
					unit.suppression = 0; 
					
					for(Trooper trooper : unit.getTroopers()) {
						trooper.conscious = true; 
						
						if(!trooper.alive) {
							dead.add(trooper);
						} else if(trooper.physicalDamage > trooper.KO * 3) {
							sw.add(trooper);
						}
						
					}
				}
				
				for(Trooper trooper : sw) {
					swUnit.individuals.add(trooper);
					for(Unit unit : units) {
						if(unit.compareTo(swUnit))
							continue; 
						
						if(unit.individuals.contains(trooper)) {
							unit.individuals.remove(trooper);
						}
					}
					
				}
				
				for(Trooper trooper : dead) {
					deadUnit.individuals.add(trooper);
					for(Unit unit : units) {
						if(unit.compareTo(deadUnit))
							continue; 
						
						if(unit.individuals.contains(trooper)) {
							unit.individuals.remove(trooper);
						}
					}
				}
				

				
				refreshUnitComboBox();
				refreshUnits();
				setOperationLabels();
				
				
			}
		});
		btnReorg.setBounds(293, 176, 95, 23);
		f.getContentPane().add(btnReorg);
		
		JButton btnBulkInventory = new JButton("Bulk Inventory");
		btnBulkInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BulkInventoryWindow(units, roster);
			}
		});
		btnBulkInventory.setBounds(522, 102, 252, 23);
		f.getContentPane().add(btnBulkInventory);
		
		JButton btnMedical = new JButton("Medical");
		btnMedical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MedicalWindow(units, roster);
			}
		});
		btnMedical.setBounds(522, 127, 252, 23);
		f.getContentPane().add(btnMedical);
		
		Formation = new JButton("Formations");
		Formation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FormationWindow(company);
			}
		});
		Formation.setBounds(522, 77, 252, 23);
		f.getContentPane().add(Formation);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new AddUnit(window);
			}
		});
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new AddIndividual(window);
			}
		});
		btnSaveChanges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Save
				// Sets company fields
				// refreshes setupwindow lists
				// Closes window

				company.setName(textFieldName.getText());
				company.setPlatoons((int) spinnerPlatoon.getValue());
				company.setSquads((int) spinnerSquad.getValue());

				if (units != null && roster != null) {
					company.setUnits(units);
					company.setRoster(roster);
				}

				setupWindow.setCompany(company, index);
				setupWindow.refreshCreated();

				f.dispose();
			}
		});
		f.setVisible(true);

		// Sets fields
		if (company != null) {
			textFieldName.setText(company.getName());
			spinnerPlatoon.setValue((int) company.getPlatoons());
			spinnerSquad.setValue((int) company.getSquads());

			// May need to check for null in the arrays
			// Sets roster and units equal to company
			units = company.getUnits();
			roster = company.getRoster();
		}
		setUnits(units);
		setTroopers(roster);
		refreshUnits();
		refreshIndividuals();
		
	}

	// Sets units
	public void setUnits(ArrayList<Unit> units) {
		company.setUnits(units);
		if (units == null) {
			return;
		} else if (units.size() == 0) {
			return;
		}

		DefaultListModel unitList = new DefaultListModel();

		for (int i = 0; i < units.size(); i++) {
			unitList.addElement(units.get(i));
			;
		}

		listUnits.setModel(unitList);
	}

	// Sets troopers
	public void setTroopers(ArrayList<Trooper> roster) {
		company.setRoster(roster);
		// If company null or if company roster size null or less than 1
		if (roster == null) {
			return;
		} else if (units.size() == 0) {
			return;
		}

		DefaultListModel trooperList = new DefaultListModel();

		for (int i = 0; i < roster.size(); i++) {
			trooperList.addElement(roster.get(i));
			;
		}

		listTroopers.setModel(trooperList);
	}

	// Refreshes combo box 
	public void refreshUnitComboBox() {
		
		comboBoxUnit.removeAllItems();
		comboBoxUnit.addItem("Use Selected Unit");
		//comboBoxUnit.addItem("None");
		for(Unit unit : units) {
			comboBoxUnit.addItem(unit.callsign);
		}
		
	}
	
	// Refresh units
	public void refreshUnits() {
		DefaultListModel unitList = new DefaultListModel();

		for (int i = 0; i < units.size(); i++) {
			
			String rslts = "Activated: ";
			
			if(!units.get(i).active) 
				rslts = "Deactivated: ";
			
			unitList.addElement(rslts + units.get(i));

		}

		listUnits.setModel(unitList);
		listUnits.setSelectedIndex(0);
		refreshUnitComboBox();
		setOperationLabels();
	}

	// Refresh individual
	public void refreshIndividuals() {
		DefaultListModel individualList = new DefaultListModel();

		for (int i = 0; i < roster.size(); i++) {
			individualList.addElement(roster.get(i));
			
		}

		listTroopers.setModel(individualList);
		listTroopers.setSelectedIndex(0);
	}

	// Careful

	// Add unit
	public void addUnit(Unit unit) {
		units.add(unit);
	}

	// Set unit
	public void setUnit(Unit unit, int index) {
		units.set(index, unit);
	}

	// Delete unit
	public void deleteUnit(int index) {
		units.remove(index);
	}

	// Get units
	public ArrayList<Unit> getUnits() {
		return units;
	}

	public void unitAddIndividual(Trooper trooper, int index) {
		units.get(index).addToUnit(trooper);
	}

	// Add individual
	public void addIndividual(Trooper trooper) {
		roster.add(trooper);
	}

	// Set individual
	public void setIndividual(Trooper trooper, int index) {
		roster.set(index, trooper);
	}

	// Delete Individual
	public void deleteIndividual(int index) {
		roster.remove(index);
	}
	
	public void setOperationLabels() {
		
	
		
		
		int roster = 0; 
		if(this.roster.size() > 0)
			roster++; 
		
		
		lblUnits.setText("Units: "+(units.size()+roster));
		
		
		int disrupted = 0; 
		int fractured = 0; 
		int routed = 0;
		
		
		int a = 0; 
		int w = 0; 
		int sw = 0;
		int i = 0; 
		int d = 0; 
		for(Unit unit : units) {
			double size = (double) unit.getSize();
			int wounded = 0;
			int dead = 0; 
			int incapacitated = 0;
			int severlyWounded = 0; 
			
			for(Trooper trooper : unit.individuals) {
				
				if(!trooper.alive) {
					dead++;
					d++; 
				} else if(!trooper.conscious) {
					incapacitated++;
					i++; 
				} else if(trooper.physicalDamage > trooper.KO * 3) {
					severlyWounded++;
					sw++;
				} else if(trooper.physicalDamage > 0) {
					wounded++;
					w++;
				} else {
					a++; 
				}
				
				if(dead + severlyWounded + incapacitated > size * 0.75) {
					routed++;
				} else if(dead + severlyWounded + incapacitated > size * 0.5) {
					fractured++;
				} else if(wounded > 0.75) {
					fractured++;
				} else if(dead + severlyWounded + incapacitated + wounded > size * 0.25) {
					disrupted++;
				} 
				
				
			}
		}
		
		lblDisruptedUnits.setText("Disrupted: "+disrupted);
		lblFracturedUnits.setText("Fractured: "+fractured);
		lblRoutedUnits.setText("Routed: "+routed);
		lblStatus.setText("Status: NONE");
		
		if(((units.size() + roster) / 3) * 2 < disrupted) {
			lblStatus.setText("Status: DISRUPTED");
		}
		
		if(((units.size() + roster) / 3) * 2 < fractured) {
			lblStatus.setText("Status: FRACTURED");
		}
		
		if(((units.size() + roster) / 3) * 2 < routed) {
			lblStatus.setText("Status: ROUTED");
		}
		
		lblTroopers.setText("Troopers: A: "+a+", W: "+w+", SW: "+sw+", I: "+i+", D: "+d);
	}
	
	
	public void exportToExcel(Company company) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Java Books");
         
        ArrayList<ArrayList<Object>> trooperList = new ArrayList<ArrayList<Object>>();
 
        ArrayList<Object> headings = new ArrayList<Object>();
        
        headings.add("name");
		headings.add("rank");
		headings.add("P1");
		headings.add("P2");
		headings.add("designation");
		headings.add("faction");
		headings.add("vet");
		
		headings.add("arms");
		headings.add("legs");
		
		headings.add("disabledArms");
		headings.add("disabledLegs");
		headings.add("wep");
		headings.add("ammo");
		headings.add("kills");
		headings.add("eqiupment");
		headings.add("accomodations");
		headings.add("notes");
		headings.add("conscious");
		headings.add("alive");
		headings.add("physicalDamage");
		headings.add("str");
		headings.add("wit");
		headings.add("soc");
		headings.add("wil");
		
		headings.add("per");
		headings.add("hlt");
		headings.add("agi");
		headings.add("ballance");
		headings.add("climb");
		headings.add("composure");
		headings.add("dodge");
		headings.add("endurance");
		headings.add("expression");
		headings.add("grapple");
		headings.add("hold");
		headings.add("jump");
		headings.add("resistPain");
		headings.add("search");
		headings.add("spotListen");
		
		headings.add("speed");
		headings.add("stealth");
		headings.add("camouflage");
		headings.add("calm");
		headings.add("diplomacy");
		headings.add("barter");
		headings.add("command");
		headings.add("tactics");
		headings.add("detMotives");
		headings.add("intimidate");
		headings.add("persuade");
		headings.add("digiSystems");
		headings.add("pistol");
		headings.add("heavy");
		headings.add("subgun");
		
		headings.add("launcher");
		headings.add("rifle");
		headings.add("advancedMedicine");
		headings.add("firstAid");
		headings.add("navigation");
		headings.add("swim");
		headings.add("Throw");
		headings.add("cleanOperations");
		headings.add("covertMovement");
		headings.add("fighter");
		headings.add("recoilControl");
		headings.add("triggerDiscipline");
		headings.add("reloadDrills");
		headings.add("silentOperations");
		headings.add("akSystems");
		
		headings.add("assualtOperations");
		headings.add("authority");
		headings.add("rawPower");
		headings.add("arSystems");
		headings.add("longRangeOptics");
		headings.add("smallUnitTactics");
		headings.add("sal");
		headings.add("isf");
		headings.add("init");
		headings.add("actions");
		headings.add("DALM");
		headings.add("combatActions");
		headings.add("KO");
		headings.add("hp");
		headings.add("currentHP");
		
		headings.add("armor");
		headings.add("veterancy");
		
		trooperList.add(headings);
        
        // Loops through each companies units 
        for(int i = 0; i < company.getUnits().size(); i++) {
        	
        	Unit unit = company.getUnits().get(i);
        	
        	// Loops through all troopers in the unit 
        	for(int x = 0; x < unit.getSize(); x++) {
        		
        		Trooper trooper = unit.getTroopers().get(x);
        		
        		ArrayList<Object> trooperStats = new ArrayList<Object>();
        		
        		trooperStats.add(trooper.name);
        		trooperStats.add(trooper.rank);
        		trooperStats.add(trooper.P1);
        		trooperStats.add(trooper.P2);
        		trooperStats.add(trooper.designation);
        		trooperStats.add(trooper.faction);
        		trooperStats.add(trooper.vet);
        		trooperStats.add(trooper.arms);
        		trooperStats.add(trooper.legs);
        		
        		trooperStats.add(trooper.disabledArms);
        		trooperStats.add(trooper.disabledLegs);
        		trooperStats.add(trooper.wep);
        		trooperStats.add(trooper.ammo);
        		trooperStats.add(trooper.kills);
        		trooperStats.add(trooper.eqiupment);
        		trooperStats.add(trooper.accomodations);
        		trooperStats.add(trooper.notes);
        		
        		if(trooper.conscious) {
        			trooperStats.add("Yes");
        		} else {
        			trooperStats.add("No");
        		}
        		
        		if(trooper.alive) {
        			trooperStats.add("Yes");
        		} else {
        			trooperStats.add("No");
        		}

        		trooperStats.add(trooper.physicalDamage);
        		trooperStats.add(trooper.str);
        		trooperStats.add(trooper.wit);
        		trooperStats.add(trooper.soc);
        		trooperStats.add(trooper.wil);
        		
        		trooperStats.add(trooper.per);
        		trooperStats.add(trooper.hlt);
        		trooperStats.add(trooper.agi);
        		/*trooperStats.add(trooper.ballance);
        		trooperStats.add(trooper.climb);
        		trooperStats.add(trooper.composure);
        		trooperStats.add(trooper.dodge);
        		trooperStats.add(trooper.endurance);
        		trooperStats.add(trooper.expression);
        		trooperStats.add(trooper.grapple);
        		trooperStats.add(trooper.hold);
        		trooperStats.add(trooper.jump);
        		trooperStats.add(trooper.resistPain);
        		trooperStats.add(trooper.search);
        		trooperStats.add(trooper.spotListen);
        		
        		trooperStats.add(trooper.speed);
        		trooperStats.add(trooper.stealth);
        		trooperStats.add(trooper.camouflage);
        		trooperStats.add(trooper.calm);
        		trooperStats.add(trooper.diplomacy);
        		trooperStats.add(trooper.barter);
        		trooperStats.add(trooper.command);
        		trooperStats.add(trooper.tactics);
        		trooperStats.add(trooper.detMotives);
        		trooperStats.add(trooper.intimidate);
        		trooperStats.add(trooper.persuade);
        		trooperStats.add(trooper.digiSystems);
        		trooperStats.add(trooper.pistol);
        		trooperStats.add(trooper.heavy);
        		trooperStats.add(trooper.subgun);
        		
        		trooperStats.add(trooper.launcher);
        		trooperStats.add(trooper.rifle);
        		trooperStats.add(trooper.advancedMedicine);
        		trooperStats.add(trooper.firstAid);
        		trooperStats.add(trooper.navigation);
        		trooperStats.add(trooper.swim);
        		trooperStats.add(trooper.Throw);
        		trooperStats.add(trooper.cleanOperations);
        		trooperStats.add(trooper.covertMovement);
        		trooperStats.add(trooper.fighter);
        		trooperStats.add(trooper.recoilControl);
        		trooperStats.add(trooper.triggerDiscipline);
        		trooperStats.add(trooper.reloadDrills);
        		trooperStats.add(trooper.silentOperations);
        		trooperStats.add(trooper.akSystems);
        		
        		trooperStats.add(trooper.assualtOperations);
        		trooperStats.add(trooper.authority);
        		trooperStats.add(trooper.rawPower);
        		trooperStats.add(trooper.arSystems);
        		trooperStats.add(trooper.longRangeOptics);
        		trooperStats.add(trooper.smallUnitTactics);*/
        		trooperStats.add(trooper.sal);
        		trooperStats.add(trooper.isf);
        		trooperStats.add(trooper.init);
        		trooperStats.add(trooper.actions);
        		trooperStats.add(trooper.DALM);
        		trooperStats.add(trooper.combatActions);
        		trooperStats.add(trooper.KO);
        		trooperStats.add(trooper.hp);
        		trooperStats.add(trooper.currentHP);
        		
        		trooperStats.add(trooper.armorLegacy);
        		trooperStats.add(trooper.veterancy);
        		
        		trooperList.add(trooperStats);
        		
        	}
        	
        }
        
        // Loops through roster 
        for(int i = 0; i < company.getRosterLength(); i++) {
        	
        	Trooper trooper = company.getRoster().get(i);
    		
    		ArrayList<Object> trooperStats = new ArrayList<Object>();
    		
    		trooperStats.add(trooper.name);
    		trooperStats.add(trooper.rank);
    		trooperStats.add(trooper.P1);
    		trooperStats.add(trooper.P2);
    		trooperStats.add(trooper.designation);
    		trooperStats.add(trooper.faction);
    		trooperStats.add(trooper.vet);
    		trooperStats.add(trooper.arms);
    		trooperStats.add(trooper.legs);
    		
    		trooperStats.add(trooper.disabledArms);
    		trooperStats.add(trooper.disabledLegs);
    		trooperStats.add(trooper.wep);
    		trooperStats.add(trooper.ammo);
    		trooperStats.add(trooper.kills);
    		trooperStats.add(trooper.eqiupment);
    		trooperStats.add(trooper.accomodations);
    		trooperStats.add(trooper.notes);
    		trooperStats.add(trooper.conscious);
    		trooperStats.add(trooper.alive);
    		trooperStats.add(trooper.physicalDamage);
    		trooperStats.add(trooper.str);
    		trooperStats.add(trooper.wit);
    		trooperStats.add(trooper.soc);
    		trooperStats.add(trooper.wil);
    		
    		trooperStats.add(trooper.per);
    		trooperStats.add(trooper.hlt);
    		trooperStats.add(trooper.agi);
    		/*trooperStats.add(trooper.ballance);
    		trooperStats.add(trooper.climb);
    		trooperStats.add(trooper.composure);
    		trooperStats.add(trooper.dodge);
    		trooperStats.add(trooper.endurance);
    		trooperStats.add(trooper.expression);
    		trooperStats.add(trooper.grapple);
    		trooperStats.add(trooper.hold);
    		trooperStats.add(trooper.jump);
    		trooperStats.add(trooper.resistPain);
    		trooperStats.add(trooper.search);
    		trooperStats.add(trooper.spotListen);
    		
    		trooperStats.add(trooper.speed);
    		trooperStats.add(trooper.stealth);
    		trooperStats.add(trooper.camouflage);
    		trooperStats.add(trooper.calm);
    		trooperStats.add(trooper.diplomacy);
    		trooperStats.add(trooper.barter);
    		trooperStats.add(trooper.command);
    		trooperStats.add(trooper.tactics);
    		trooperStats.add(trooper.detMotives);
    		trooperStats.add(trooper.intimidate);
    		trooperStats.add(trooper.persuade);
    		trooperStats.add(trooper.digiSystems);
    		trooperStats.add(trooper.pistol);
    		trooperStats.add(trooper.heavy);
    		trooperStats.add(trooper.subgun);
    		
    		trooperStats.add(trooper.launcher);
    		trooperStats.add(trooper.rifle);
    		trooperStats.add(trooper.advancedMedicine);
    		trooperStats.add(trooper.firstAid);
    		trooperStats.add(trooper.navigation);
    		trooperStats.add(trooper.swim);
    		trooperStats.add(trooper.Throw);
    		trooperStats.add(trooper.cleanOperations);
    		trooperStats.add(trooper.covertMovement);
    		trooperStats.add(trooper.fighter);
    		trooperStats.add(trooper.recoilControl);
    		trooperStats.add(trooper.triggerDiscipline);
    		trooperStats.add(trooper.reloadDrills);
    		trooperStats.add(trooper.silentOperations);
    		trooperStats.add(trooper.akSystems);
    		
    		trooperStats.add(trooper.assualtOperations);
    		trooperStats.add(trooper.authority);
    		trooperStats.add(trooper.rawPower);
    		trooperStats.add(trooper.arSystems);
    		trooperStats.add(trooper.longRangeOptics);
    		trooperStats.add(trooper.smallUnitTactics);*/
    		trooperStats.add(trooper.sal);
    		trooperStats.add(trooper.isf);
    		trooperStats.add(trooper.init);
    		trooperStats.add(trooper.actions);
    		trooperStats.add(trooper.DALM);
    		trooperStats.add(trooper.combatActions);
    		trooperStats.add(trooper.KO);
    		trooperStats.add(trooper.hp);
    		trooperStats.add(trooper.currentHP);
    		
    		trooperStats.add(trooper.armorLegacy);
    		trooperStats.add(trooper.veterancy);
    		
    		trooperList.add(trooperStats);
        	
        }
        
        int rowCount = 0;
        
        for(int i = 0; i < trooperList.size(); i++) {
        	
        	Row row = sheet.createRow(++rowCount);
            
            int columnCount = 0;
        	
        	for(int x = 0; x < trooperList.get(i).size(); x++) {
        		
        		Object field = trooperList.get(i).get(x);
        		
        		Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
        	}
        	
        }
         
         
        try (FileOutputStream outputStream = new FileOutputStream(company.getName()+".xlsx")) {
            workbook.write(outputStream);
        }
	}
}
