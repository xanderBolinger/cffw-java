package Vehicle.Windows;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Vehicle.Vehicle;
import Vehicle.VehicleManager;
import Vehicle.Data.CrewMember.Action;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

import Conflict.GameWindow;
import UtilityClasses.SwingUtility;

import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VehicleCombatWindow {

	private JFrame frame;

	private JList vehicleList;
	private JLabel lblSelectedVehicle;
	private JLabel lblTurnPhase;
	private JComboBox comboBoxAction;
	private JList listCrew;
	private JLabel lblAcceleration;
	private JLabel lblDeceleration;
	private JLabel lblSidesTurned;
	private JLabel lblDirection;
	private JLabel lblSpeed;
	private JLabel lblHullTurnRate;
	private JTextArea textAreaNotes;
	
	ArrayList<Vehicle> vehicles;
	Vehicle selectedVehicle;
	
	/**
	 * Create the application.
	 */
	public VehicleCombatWindow() {
		initialize();
		setActionComboBox();
		setVehicles();
		refreshVehicleList();
		
		frame.setVisible(true);
	}

	private void setActionComboBox() {
		
		ArrayList<String> actions = new ArrayList<String>();
		
		for(var action : Action.values()) {
			actions.add(action.toString());
		}
		
		SwingUtility.setComboBox(comboBoxAction, actions, false, 0);
		
	}
	
	public void setVehicles() {
		vehicles = new ArrayList<Vehicle>();
		
		for(var company : GameWindow.gameWindow.companies) {
			for(var vic : company.vehicles) {
				if(!vic.active)
					continue;
				vehicles.add(vic);
			}
		}
		
	}
	
	public void refreshVehicleList() {
		
		ArrayList<String> vehicleStrings = new ArrayList<String>();
		
		for(var vic : vehicles) {
			vehicleStrings.add(vic.toString());
		}
		
		
		SwingUtility.setList(vehicleList, vehicleStrings);
		
	}
	
	private void refreshSelectedVehicle() {
		
		lblSelectedVehicle.setText("Selected Vehicle: "+selectedVehicle.toString());
		var md = selectedVehicle.movementData;
		lblAcceleration.setText("Acceleration: "+md.acceleration);
		lblDeceleration.setText("Deceleration: "+md.deceleration);
		lblDirection.setText("Direction: "+md.facing);
		lblHullTurnRate.setText("Hull Turn Rate: "+md.hullTurnRate);
		lblSidesTurned.setText("Sides Turned: "+md.changedFaces);
		lblSpeed.setText("Speed: "+md.speed);
		
		ArrayList<String> vehicleCrew = new ArrayList<String>();
		
		for(var pos : selectedVehicle.getCrewPositions()) {
			vehicleCrew.add(pos.getPositionName()+(pos.crewMemeber == null ? ", EMPTY" : 
				" "+pos.crewMemeber.crewMember.name +", Action: "+pos.crewMemeber.currentAction 
				+", PD: "+pos.crewMemeber.crewMember.physicalDamage
				+", Alive: "+pos.crewMemeber.crewMember.alive 
				+", Conscious: "+pos.crewMemeber.crewMember.conscious));
		}
		
		SwingUtility.setList(listCrew, vehicleCrew);
		
	}
	
	public void unselectVehicle() {
		lblSelectedVehicle.setText("Selected Vehicle: None");
		lblAcceleration.setText("Acceleration: ");
		lblDeceleration.setText("Deceleration: ");
		lblDirection.setText("Direction: ");
		lblHullTurnRate.setText("Hull Turn Rate: ");
		lblSidesTurned.setText("Sides Turned: ");
		lblSpeed.setText("Speed: ");
		selectedVehicle = null;
		SwingUtility.setList(listCrew, new ArrayList<String>());
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 871, 663);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 38, 386, 575);
		frame.getContentPane().add(scrollPane);
		
		vehicleList = new JList();
		vehicleList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(vehicleList.getSelectedIndex() < 0)
					return;
				
				selectedVehicle = vehicles.get(vehicleList.getSelectedIndex());
				
				refreshSelectedVehicle();
				
			}
		});
		scrollPane.setViewportView(vehicleList);
		
		JLabel lblNewLabel = new JLabel("Vehicles");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 127, 16);
		frame.getContentPane().add(lblNewLabel);
		
		lblSelectedVehicle = new JLabel("Selected Vehicle");
		lblSelectedVehicle.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSelectedVehicle.setBounds(404, 13, 197, 16);
		frame.getContentPane().add(lblSelectedVehicle);
		
		JButton btnNewButton = new JButton("Next Turn");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameWindow.gameWindow.vehicleManager.nextTurn();
			}
		});
		btnNewButton.setBounds(406, 590, 118, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNextPhase = new JButton("Next Phase");
		btnNextPhase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameWindow.gameWindow.vehicleManager.nextPhase();
			}
		});
		btnNextPhase.setBounds(534, 590, 118, 23);
		frame.getContentPane().add(btnNextPhase);
		
		lblTurnPhase = new JLabel("Turn: 1, Phase: 1");
		lblTurnPhase.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTurnPhase.setBounds(147, 11, 249, 16);
		frame.getContentPane().add(lblTurnPhase);
		
		JButton btnNewButton_1 = new JButton("Set Action");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null || listCrew.getSelectedIndex() < 0)
					return;
				
				selectedVehicle.getCrewPositions().get(listCrew.getSelectedIndex())
				.crewMemeber.currentAction 
					= Action.values()[comboBoxAction.getSelectedIndex()];
				
			}
		});
		btnNewButton_1.setBounds(406, 213, 144, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		comboBoxAction = new JComboBox();
		comboBoxAction.setBounds(406, 180, 144, 22);
		frame.getContentPane().add(comboBoxAction);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(406, 38, 439, 126);
		frame.getContentPane().add(scrollPane_1);
		
		listCrew = new JList();
		scrollPane_1.setViewportView(listCrew);
		
		lblAcceleration = new JLabel("Acceleration:");
		lblAcceleration.setBounds(560, 184, 92, 14);
		frame.getContentPane().add(lblAcceleration);
		
		lblDeceleration = new JLabel("Deceleration:");
		lblDeceleration.setBounds(560, 213, 92, 14);
		frame.getContentPane().add(lblDeceleration);
		
		lblSpeed = new JLabel("Speed:");
		lblSpeed.setBounds(685, 184, 92, 14);
		frame.getContentPane().add(lblSpeed);
		
		lblDirection = new JLabel("Direction:");
		lblDirection.setBounds(685, 217, 92, 14);
		frame.getContentPane().add(lblDirection);
		
		lblHullTurnRate = new JLabel("Hull Turn Rate");
		lblHullTurnRate.setBounds(560, 249, 92, 14);
		frame.getContentPane().add(lblHullTurnRate);
		
		lblSidesTurned = new JLabel("Sides Turned:");
		lblSidesTurned.setBounds(685, 249, 92, 14);
		frame.getContentPane().add(lblSidesTurned);
		
		JLabel lblVehicleNotes = new JLabel("Vehicle Notes");
		lblVehicleNotes.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVehicleNotes.setBounds(404, 274, 197, 16);
		frame.getContentPane().add(lblVehicleNotes);
		
		textAreaNotes = new JTextArea();
		textAreaNotes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println("key typed");
			}
		});
		textAreaNotes.setBounds(406, 301, 439, 219);
		frame.getContentPane().add(textAreaNotes);
		
		
		
	}
}
