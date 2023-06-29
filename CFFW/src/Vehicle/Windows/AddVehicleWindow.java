package Vehicle.Windows;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Company.Company;
import UtilityClasses.ExcelUtility;
import UtilityClasses.SwingUtility;
import Vehicle.Vehicle;
import Vehicle.Data.CrewMember;
import Vehicle.Utilities.VehicleXmlReader;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddVehicleWindow {

	private JFrame frame;
	ArrayList<Vehicle> activeVehicles;
	ArrayList<Vehicle> inactiveVehicles;
	Company company;
	Vehicle selectedVehicle;
	private JList vehicleList;
	private JTextField textFieldCallsign;
	private JComboBox comboBoxVehicleType;
	private JList crewList;
	private JList inactiveVehicleList;
	private JList rosterList;
	private JComboBox comboBoxPosition;
	private JLabel lblSelectedVehicle;
	

	/**
	 * Create the application.
	 */
	public AddVehicleWindow(Company company) {
		
		this.company = company;
		
		initialize();
		
		refreshLists();
		refreshVehicleTypeComboBox();
	}
	
	private ArrayList<Vehicle> getSelectedVehicles(boolean active) {
		
		var indices = active ? vehicleList.getSelectedIndices() : inactiveVehicleList.getSelectedIndices();
		ArrayList<Vehicle> selectedVehicles = new ArrayList<Vehicle>();
		
		for(var index : indices) {
			selectedVehicles.add(active ? activeVehicles.get(index) : inactiveVehicles.get(index));
		}
		
		return selectedVehicles;
		
	}
	
	public void refreshVehicleTypeComboBox() {
		
		ArrayList<String> vehicleNames = new ArrayList<String>();
		
		File folder = new File(ExcelUtility.path+"\\Vehicles");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
		    vehicleNames.add(listOfFiles[i].getName());
		  } else if (listOfFiles[i].isDirectory()) {
		    System.out.println("Directory " + listOfFiles[i].getName());
		  }
		}
		
		SwingUtility.setComboBox(comboBoxVehicleType, vehicleNames, false, 0);
		
	}
	
	public void refreshLists() {
		refreshVehicleLists();
		refreshRoster();
		unselectVehicle();
	}
	
	private void selectVehicle() {
		if(vehicleList.getSelectedIndex() < 0) {
			unselectVehicle();
			return;
		}
		
		selectedVehicle = activeVehicles.get(vehicleList.getSelectedIndex());
		refreshSelectedVehicle();
	}
	
	private void refreshSelectedVehicle() {
		lblSelectedVehicle.setText("Selected Vehicle: "+selectedVehicle.toString());

		ArrayList<String> vehicleCrew = new ArrayList<String>();
		
		for(var pos : selectedVehicle.getCrewPositions()) {
			vehicleCrew.add(pos.getPositionName()+(pos.crewMemeber == null ? ", EMPTY" : 
				pos.crewMemeber.crewMember.name +", PD: "+pos.crewMemeber.crewMember.physicalDamage
				+", Alive: "+pos.crewMemeber.crewMember.alive+", Conscious: "+pos.crewMemeber.crewMember.conscious));
		}
		
		SwingUtility.setList(crewList, vehicleCrew);
		
		
		
		ArrayList<String> positions = new ArrayList<String>();

		for(var pos : selectedVehicle.getCrewPositions()) {
			positions.add(pos.getPositionName()+(pos.crewMemeber == null ? ", EMPTY" : "OCCUPIED"));
		}
		
		SwingUtility.setComboBox(comboBoxPosition, positions, false, 0);
	}
	
	private void unselectVehicle() {
		lblSelectedVehicle.setText("Selected Vehicle: None");
		selectedVehicle = null;
		SwingUtility.setList(crewList, new ArrayList<String>());
		comboBoxPosition.removeAllItems();
	}
	
	private void refreshRoster() {
		ArrayList<String> rosterStrings = new ArrayList<String>();
		
		for(var trooper : company.getRoster()) {
			rosterStrings.add(trooper.name+", Role: "+trooper.designation+", PD: "+trooper.physicalDamage+", Concious: "+trooper.conscious+", Alive: "+trooper.alive);
		}
		
		SwingUtility.setList(rosterList, rosterStrings);
	}
	
	private void refreshVehicleLists() {
		activeVehicles = new ArrayList<Vehicle>();
		inactiveVehicles = new ArrayList<Vehicle>();
		
		ArrayList<String> activeVehiclesStrings = new ArrayList<String>();
		ArrayList<String> inactiveVehiclesStrings = new ArrayList<String>();
		
		for(var vic : company.vehicles) {
			if(vic.active) {
				activeVehiclesStrings.add(vic.toString());
				activeVehicles.add(vic);
			}
			else {
				inactiveVehiclesStrings.add(vic.toString());
				inactiveVehicles.add(vic);
			}
		}
		
		SwingUtility.setList(inactiveVehicleList, inactiveVehiclesStrings);
		SwingUtility.setList(vehicleList, activeVehiclesStrings);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 762, 787);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 282, 480);
		frame.getContentPane().add(scrollPane);
		
		vehicleList = new JList();
		vehicleList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				selectVehicle();
				
			}
		});
		scrollPane.setViewportView(vehicleList);
		
		JLabel lblNewLabel = new JLabel("Active Vehicles");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 133, 23);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblAddVehicle = new JLabel("Add Vehicle");
		lblAddVehicle.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAddVehicle.setBounds(302, 111, 133, 23);
		frame.getContentPane().add(lblAddVehicle);
		
		JLabel lblCallsign = new JLabel("Callsign");
		lblCallsign.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCallsign.setBounds(302, 142, 133, 14);
		frame.getContentPane().add(lblCallsign);
		
		JLabel lblAddVehicle_1_1 = new JLabel("Type");
		lblAddVehicle_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAddVehicle_1_1.setBounds(302, 192, 133, 14);
		frame.getContentPane().add(lblAddVehicle_1_1);
		
		textFieldCallsign = new JTextField();
		textFieldCallsign.setBounds(302, 161, 133, 20);
		frame.getContentPane().add(textFieldCallsign);
		textFieldCallsign.setColumns(10);
		
		comboBoxVehicleType = new JComboBox();
		comboBoxVehicleType.setBounds(302, 217, 133, 22);
		frame.getContentPane().add(comboBoxVehicleType);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					var vic = VehicleXmlReader.readVehicle(textFieldCallsign.getText(), comboBoxVehicleType.getSelectedItem().toString());
					company.vehicles.add(vic);
					refreshLists();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setBounds(302, 250, 133, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(var vic : getSelectedVehicles(false)) {
					for(var trooper : vic.getTroopers())
						company.getRoster().add(trooper);
					company.vehicles.remove(vic);
				}
				
				for(var vic : getSelectedVehicles(true)) {
					for(var trooper : vic.getTroopers())
						company.getRoster().add(trooper);
					company.vehicles.remove(vic);
				}
				
				refreshLists();
				
			}
		});
		btnRemove.setBounds(302, 43, 133, 23);
		frame.getContentPane().add(btnRemove);
		
		JButton btnToggleDisable = new JButton("Toggle Disable");
		btnToggleDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(var vic : getSelectedVehicles(false))
					vic.knockedOut = !vic.knockedOut;
				for(var vic : getSelectedVehicles(true))
					vic.knockedOut = !vic.knockedOut;
				refreshLists();
			}
		});
		btnToggleDisable.setBounds(302, 77, 133, 23);
		frame.getContentPane().add(btnToggleDisable);
		
		JButton btnNewButton_1 = new JButton("-->");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(var vic : getSelectedVehicles(true)) {
					vic.active = false;
				}
				
				refreshLists();
				
			}
		});
		btnNewButton_1.setBounds(302, 502, 133, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("<--");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(var vic : getSelectedVehicles(false)) {
					vic.active = true;
				}
				
				refreshLists();
				
			}
		});
		btnNewButton_1_1.setBounds(302, 469, 133, 23);
		frame.getContentPane().add(btnNewButton_1_1);
		
		JLabel lblCrew = new JLabel("Crew");
		lblCrew.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCrew.setBounds(10, 569, 133, 23);
		frame.getContentPane().add(lblCrew);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 598, 282, 135);
		frame.getContentPane().add(scrollPane_1);
		
		crewList = new JList();
		scrollPane_1.setViewportView(crewList);
		
		lblSelectedVehicle = new JLabel("Selected Vehicle");
		lblSelectedVehicle.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectedVehicle.setBounds(10, 535, 425, 23);
		frame.getContentPane().add(lblSelectedVehicle);
		
		JButton btnNewButton_1_2 = new JButton("-->");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(crewList.getSelectedIndex() < 0 || selectedVehicle == null)
					return;
				
				var pos = selectedVehicle.getCrewPositions().get(comboBoxPosition.getSelectedIndex());
				var crewMember = pos.crewMemeber;
				
				if(crewMember == null)
					return;
			
				company.getRoster().add(crewMember.crewMember);
				pos.crewMemeber = null;
				
				refreshSelectedVehicle();
				refreshRoster();
			}
		});
		btnNewButton_1_2.setBounds(302, 710, 133, 23);
		frame.getContentPane().add(btnNewButton_1_2);
		
		JButton btnNewButton_1_1_1 = new JButton("<--");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rosterList.getSelectedIndex() < 0 || selectedVehicle == null)
					return;
				
				var trooper = company.getRoster().remove(rosterList.getSelectedIndex());
				var crewMember = selectedVehicle.getCrewPositions().get(comboBoxPosition.getSelectedIndex()).crewMemeber;
				
				if(crewMember == null)
					crewMember = new CrewMember(trooper);
				
				refreshSelectedVehicle();
				refreshRoster();
				
			}
		});
		btnNewButton_1_1_1.setBounds(302, 676, 133, 23);
		frame.getContentPane().add(btnNewButton_1_1_1);
		
		JLabel lblInactiveVehicles = new JLabel("Inactive Vehicles");
		lblInactiveVehicles.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblInactiveVehicles.setBounds(446, 16, 133, 23);
		frame.getContentPane().add(lblInactiveVehicles);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(445, 45, 282, 480);
		frame.getContentPane().add(scrollPane_2);
		
		inactiveVehicleList = new JList();
		scrollPane_2.setViewportView(inactiveVehicleList);
		
		JLabel lblRoster = new JLabel("Roster");
		lblRoster.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRoster.setBounds(446, 540, 339, 23);
		frame.getContentPane().add(lblRoster);
		
		JLabel lblAddCrew = new JLabel("Add Crew");
		lblAddCrew.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAddCrew.setBounds(446, 574, 133, 23);
		frame.getContentPane().add(lblAddCrew);
		
		comboBoxPosition = new JComboBox();
		comboBoxPosition.setBounds(302, 629, 133, 22);
		frame.getContentPane().add(comboBoxPosition);
		
		JLabel lblSelectPosition = new JLabel("Select Position");
		lblSelectPosition.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectPosition.setBounds(302, 595, 133, 23);
		frame.getContentPane().add(lblSelectPosition);
		
		JScrollPane scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setBounds(445, 598, 282, 135);
		frame.getContentPane().add(scrollPane_1_1);
		
		rosterList = new JList();
		scrollPane_1_1.setViewportView(rosterList);
	}
}
