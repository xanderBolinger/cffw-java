package Vehicle.Windows;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Vehicle.Vehicle;
import Vehicle.Data.CrewMember.Action;
import Vehicle.HullDownPositions.HullDownPosition.HullDownDecision;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

import Conflict.GameWindow;
import HexGrid.Vehicle.HexGridHullDownUtility;
import HexGrid.Vehicle.HexGridVehicleUtility;
import UtilityClasses.SwingUtility;

import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

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
	
	public ArrayList<Vehicle> vehicles;
	Vehicle selectedVehicle;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JSpinner spinnerChange;
	private JButton btnNewButton_6;
	private JButton btnNewButton_7;
	private JButton btnNewButton_8;
	private JTextArea textAreaHullDown;
	
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
		var companies = GameWindow.gameWindow.companies;
		for(var company : companies) {
			for(var vic : company.vehicles) {
				if(!vic.active)
					continue;
				vehicles.add(vic);
			}
		}
		
		HexGridVehicleUtility.updateVehicleChits(this);
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
		
		if(md.hullDownPosition != null) {
			textAreaHullDown.setText(md.hullDownPosition.toString()+", \nStatus: "+md.hullDownStatus+", Decision: "+md.hullDownDecision);
		} else {
			textAreaHullDown.setText("Hull Down: "+md.hullDownDecision);
		}
		
		
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
		textAreaHullDown.setText("Hull Down: ");
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
		lblSelectedVehicle.setBounds(404, 13, 441, 16);
		frame.getContentPane().add(lblSelectedVehicle);
		
		JButton btnNewButton = new JButton("Next Turn");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				var vm = GameWindow.gameWindow.game.vehicleManager;
				vm.nextTurn();
				lblTurnPhase.setText("Turn: "+vm.turn+", Phase: "+vm.phase);
				refreshSelectedVehicle();
			}
		});
		btnNewButton.setBounds(406, 590, 118, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNextPhase = new JButton("Next Phase");
		btnNextPhase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				var vm = GameWindow.gameWindow.game.vehicleManager;
				vm.nextPhase();
				lblTurnPhase.setText("Turn: "+vm.turn+", Phase: "+vm.phase);
				refreshSelectedVehicle();
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
				
				refreshSelectedVehicle();
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
		lblVehicleNotes.setBounds(407, 444, 197, 16);
		frame.getContentPane().add(lblVehicleNotes);
		
		textAreaNotes = new JTextArea();
		textAreaNotes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println("key typed");
			}
		});
		textAreaNotes.setBounds(406, 471, 439, 108);
		frame.getContentPane().add(textAreaNotes);
		
		btnNewButton_2 = new JButton("Rotate Left");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				
				
				selectedVehicle.movementData.changeFacing(false);
				
				refreshSelectedVehicle();
			}
		});
		btnNewButton_2.setBounds(406, 282, 144, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("Rotate Right");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				
				
				selectedVehicle.movementData.changeFacing(true);
				
				refreshSelectedVehicle();
			}
		});
		btnNewButton_3.setBounds(406, 309, 144, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		btnNewButton_4 = new JButton("Accelerate");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				
				var md = selectedVehicle.movementData;
				
				if(md.hullDownPosition != null) {
					return;
				}
				
				var cord = md.location;
				
				var hex = GameWindow.gameWindow.findHex(cord.xCord, cord.yCord);
						
				if(hex == null)
					return;
						
				md.accelerate(md.speed + (int)spinnerChange.getValue(), hex);
				
				refreshSelectedVehicle();
				
			}
		});
		btnNewButton_4.setBounds(560, 282, 132, 23);
		frame.getContentPane().add(btnNewButton_4);
		
		btnNewButton_5 = new JButton("Decelerate");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				
				var md = selectedVehicle.movementData;
				
				if(md.hullDownPosition != null) {
					return;
				}
				
				md.decelerate(md.speed-(int)spinnerChange.getValue());
				
				refreshSelectedVehicle();
			}
		});
		btnNewButton_5.setBounds(560, 309, 132, 23);
		frame.getContentPane().add(btnNewButton_5);
		
		spinnerChange = new JSpinner();
		spinnerChange.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		spinnerChange.setBounds(702, 310, 52, 20);
		frame.getContentPane().add(spinnerChange);
		
		btnNewButton_6 = new JButton("Enter HD");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				var md = selectedVehicle.movementData;
				md.hullDownDecision = HullDownDecision.ENTER;
				md.selectedHullDownPosition = HexGridHullDownUtility.getHullDownPosition(md.location.xCord, md.location.yCord);
				refreshSelectedVehicle();
			}
		});
		btnNewButton_6.setBounds(406, 338, 144, 23);
		frame.getContentPane().add(btnNewButton_6);
		
		btnNewButton_7 = new JButton("Exit HD");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				selectedVehicle.movementData.hullDownDecision = HullDownDecision.EXIT;
				refreshSelectedVehicle();
			}
		});
		btnNewButton_7.setBounds(560, 338, 132, 23);
		frame.getContentPane().add(btnNewButton_7);
		
		btnNewButton_8 = new JButton("-->");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				selectedVehicle.movementData.hullDownDecision = HullDownDecision.INCH_FORWARD;
				refreshSelectedVehicle();
			}
		});
		btnNewButton_8.setBounds(779, 338, 66, 23);
		frame.getContentPane().add(btnNewButton_8);
		
		JButton btnNewButton_8_1 = new JButton("<--");
		btnNewButton_8_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				selectedVehicle.movementData.hullDownDecision = HullDownDecision.INCH_BACKWARD;
				refreshSelectedVehicle();
			}
		});
		btnNewButton_8_1.setBounds(702, 338, 66, 23);
		frame.getContentPane().add(btnNewButton_8_1);
		
		JButton btnNewButton_8_2 = new JButton("Clear");
		btnNewButton_8_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				selectedVehicle.movementData.hullDownDecision = HullDownDecision.NOTHING;
				refreshSelectedVehicle();
			}
		});
		btnNewButton_8_2.setBounds(406, 361, 144, 23);
		frame.getContentPane().add(btnNewButton_8_2);
		
		textAreaHullDown = new JTextArea();
		textAreaHullDown.setBounds(406, 395, 439, 38);
		frame.getContentPane().add(textAreaHullDown);
		
		
		
	}
}
