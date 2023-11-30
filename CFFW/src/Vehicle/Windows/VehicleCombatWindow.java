package Vehicle.Windows;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Vehicle.Vehicle;
import Vehicle.Combat.VehicleTurret;
import Vehicle.Data.CrewMember.CrewAction;
import Vehicle.HullDownPositions.HullDownPosition.HullDownDecision;
import Vehicle.Spot.VehicleSpotManager;
import Vehicle.Utilities.VehicleDataUtility;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.SpinnerModel;

import Conflict.ArtilleryWindow;
import Conflict.GameWindow;
import HexGrid.HexDirectionUtility;
import HexGrid.Vehicle.HexGridHullDownUtility;
import HexGrid.Vehicle.HexGridVehicleUtility;
import UtilityClasses.PCUtility;
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
import javax.swing.SwingWorker;

import Artillery.AlertWindow;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Label;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

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
	public Vehicle selectedVehicle;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JSpinner spinnerChange;
	private JButton btnNewButton_6;
	private JButton btnNewButton_7;
	private JButton btnNewButton_8;
	private JTextArea textAreaHullDown;
	private JPanel smoke;
	private JLabel lblLaunchedSmoke;
	private JLabel lblTurnsRemaining;
	private JLabel lblTrailingSmoke;
	private Label lblTrailing;
	private JLabel lblLaunchesRemaining;
	private JPanel los;
	private JList listLos;
	private JList listSpotted;
	private JCheckBox chckbxFired;
	private JCheckBox chckbxSkipSpotTest;
	private JPanel losUnit;
	private JList listLosUnits;
	private JList listSpottedIndividuals;
	private JPanel Combat;
	private JComboBox comboBoxTurrets;
	private JLabel lblTurretFacing;
	private JLabel lblTurretWidth;
	private JSpinner spinnerTurretRotation;
	
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
		
		for(var action : CrewAction.values()) {
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
	
	public void refreshSelectedVehicle() {
		if(selectedVehicle == null)
			return;
		
		lblSelectedVehicle.setText("Selected Vehicle: "+selectedVehicle.toString());
		var md = selectedVehicle.movementData;
		lblAcceleration.setText("Acceleration: "+md.acceleration);
		lblDeceleration.setText("Deceleration: "+md.deceleration);
		lblDirection.setText("Direction: "+md.facing);
		lblHullTurnRate.setText("Hull Turn Rate: "+md.hullTurnRate);
		lblSidesTurned.setText("Sides Turned: "+md.changedFaces);
		lblSpeed.setText("Speed: "+md.speed);
		chckbxFired.setSelected(selectedVehicle.spotData.fired);
		textAreaNotes.setText(selectedVehicle.notes);
		refreshSmoke();
		
		if(md.hullDownPosition != null) {
			textAreaHullDown.setText(md.hullDownPosition.toString()+", \nStatus: "+md.hullDownStatus+", Decision: "+md.hullDownDecision);
		} else {
			textAreaHullDown.setText("Hull Down: "+md.hullDownDecision);
		}
		
		
		ArrayList<String> vehicleCrew = new ArrayList<String>();
		
		for(var pos : selectedVehicle.getCrewPositions()) {
			vehicleCrew.add(pos.getPositionName()+(pos.crewMemeber == null ? ", EMPTY" : 
				" "+pos.crewMemeber.crewMember.name +", Action: "+pos.crewMemeber.currentAction 
				+", SL: "+PCUtility.getSL("Heavy", pos.crewMemeber.crewMember)
				+", PD: "+pos.crewMemeber.crewMember.physicalDamage
				+", Alive: "+pos.crewMemeber.crewMember.alive 
				+", Conscious: "+pos.crewMemeber.crewMember.conscious));
		}
		
		SwingUtility.setList(listCrew, vehicleCrew);
		
		ArrayList<String> los = new ArrayList<String>();
		
		for(var losVic : selectedVehicle.losVehicles)
			los.add(losVic.getVehicleType()+": "+losVic.getVehicleCallsign());
		
		SwingUtility.setList(listLos, los);
		
		ArrayList<String> spotted = new ArrayList<String>();
		
		for(var losVic : selectedVehicle.spottedVehicles)
			spotted.add(losVic.getVehicleType()+": "+losVic.getVehicleCallsign());
		
		SwingUtility.setList(listSpotted, spotted);
		
		ArrayList<String> losUnits = new ArrayList<String>();
		
		for(var losUnit : selectedVehicle.losUnits)
			losUnits.add(losUnit.callsign);
		
		SwingUtility.setList(listLosUnits, losUnits);
		
		ArrayList<String> spottedTroopers = new ArrayList<String>();
		
		for(var t : selectedVehicle.spottedTroopers)
			spottedTroopers.add(GameWindow.getLogHead(t));
		
		SwingUtility.setList(listSpottedIndividuals, spottedTroopers);
		
		ArrayList<String> turretStrings = new ArrayList<String>();
		
		for(var turret : selectedVehicle.turretData.turrets)
			turretStrings.add(turret.turretName);
		
		SwingUtility.setComboBox(comboBoxTurrets, turretStrings, false, 0);
		
		
	}
	
	private void refreshSmoke() {
		lblLaunchedSmoke.setText("Launched Smoke: "+selectedVehicle.smokeData.launchedSmoke.smokeType);
		lblLaunchesRemaining.setText("Remaining Launches: "+selectedVehicle.smokeData.remainingSmokeLaunches);
		lblTrailingSmoke.setText("Trailing Smoke: "+selectedVehicle.smokeData.trailingSmoke.smokeType);
		lblTurnsRemaining.setText("Turns Remaining: "+selectedVehicle.smokeData.remainingTrailingSmokeTurns);
		lblTrailing.setText("Trailing: "+selectedVehicle.smokeData.trailingSmokeActive);
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
		textAreaNotes.setText("");
		SwingUtility.setList(listCrew, new ArrayList<String>());
		
		comboBoxTurrets.removeAllItems();
		lblTurretFacing.setText("Turret Facing: ");
		lblTurretWidth.setText("Turret Width: ");
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 0, 1); 
		spinnerTurretRotation.setValue(0);
		spinnerTurretRotation.setModel(sm);
	}
	
	public VehicleTurret getSelectedTurret() {
		if(selectedVehicle == null || comboBoxTurrets.getSelectedIndex() < 0)
			return null;
		
		return selectedVehicle.turretData.turrets.get(comboBoxTurrets.getSelectedIndex());
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 871, 740);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 38, 386, 652);
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
				
				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {
						vm.nextTurn();
						
						if(!chckbxSkipSpotTest.isSelected())
							VehicleSpotManager.vehicleSpotChecks();

						for(var vic : vehicles) {
							vic.spotData.fired = false;
						}
						
						return null;
					}

					@Override
					protected void done() {
						refreshSelectedVehicle();
						lblTurnPhase.setText("Turn: "+vm.turn+", Phase: "+vm.phase);
						GameWindow.gameWindow.conflictLog.addQueuedText();
					}

				};

				worker.execute();
				
			}
		});
		btnNewButton.setBounds(406, 667, 118, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNextPhase = new JButton("Next Phase");
		btnNextPhase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				var vm = GameWindow.gameWindow.game.vehicleManager;
				
				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {
						
						try {
							vm.nextPhase();
							if(!chckbxSkipSpotTest.isSelected())
								VehicleSpotManager.vehicleSpotChecks();
							for(var vic : vehicles) {
								vic.spotData.fired = false;
							}
						} catch(Exception e) {e.printStackTrace();}
						
						
						return null;
					}

					@Override
					protected void done() {
						lblTurnPhase.setText("Turn: "+vm.turn+", Phase: "+vm.phase);
						refreshSelectedVehicle();
						GameWindow.gameWindow.conflictLog.addQueuedText();
					}

				};

				worker.execute();
				
			}
		});
		btnNextPhase.setBounds(534, 667, 118, 23);
		frame.getContentPane().add(btnNextPhase);
		
		lblTurnPhase = new JLabel("Turn: 1, Phase: 1");
		lblTurnPhase.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTurnPhase.setBounds(124, 11, 249, 16);
		frame.getContentPane().add(lblTurnPhase);
		
		JButton btnNewButton_1 = new JButton("Set Action");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null || listCrew.getSelectedIndex() < 0)
					return;
				
				selectedVehicle.getCrewPositions().get(listCrew.getSelectedIndex())
				.crewMemeber.currentAction 
					= CrewAction.values()[comboBoxAction.getSelectedIndex()];
				
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
		textAreaHullDown.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textAreaHullDown.setBounds(406, 395, 439, 38);
		frame.getContentPane().add(textAreaHullDown);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(406, 444, 439, 221);
		frame.getContentPane().add(tabbedPane);
		
		los = new JPanel();
		los.setLayout(null);
		tabbedPane.addTab("LOS Vic", null, los, null);
		
		JLabel lblNewLabel_1 = new JLabel("LOS");
		lblNewLabel_1.setBounds(10, 11, 79, 14);
		los.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Spotted");
		lblNewLabel_1_1.setBounds(225, 11, 79, 14);
		los.add(lblNewLabel_1_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 34, 206, 148);
		los.add(scrollPane_2);
		
		listLos = new JList();
		scrollPane_2.setViewportView(listLos);
		
		JScrollPane scrollPane_2_1 = new JScrollPane();
		scrollPane_2_1.setBounds(225, 34, 199, 148);
		los.add(scrollPane_2_1);
		
		listSpotted = new JList();
		scrollPane_2_1.setViewportView(listSpotted);
		
		losUnit = new JPanel();
		tabbedPane.addTab("LOS Units", null, losUnit, null);
		losUnit.setLayout(null);
		
		JLabel lblNewLabel_1_2 = new JLabel("LOS Units");
		lblNewLabel_1_2.setBounds(10, 10, 205, 14);
		losUnit.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Spotted Individuals");
		lblNewLabel_1_1_1.setBounds(225, 10, 199, 14);
		losUnit.add(lblNewLabel_1_1_1);
		
		JScrollPane scrollPane_2_2 = new JScrollPane();
		scrollPane_2_2.setBounds(10, 33, 206, 148);
		losUnit.add(scrollPane_2_2);
		
		listLosUnits = new JList();
		scrollPane_2_2.setViewportView(listLosUnits);
		
		JScrollPane scrollPane_2_1_1 = new JScrollPane();
		scrollPane_2_1_1.setBounds(225, 33, 199, 148);
		losUnit.add(scrollPane_2_1_1);
		
		listSpottedIndividuals = new JList();
		scrollPane_2_1_1.setViewportView(listSpottedIndividuals);
		
		smoke = new JPanel();
		tabbedPane.addTab("Smoke", null, smoke, null);
		smoke.setLayout(null);
		
		lblLaunchedSmoke = new JLabel("Launched Smoke:");
		lblLaunchedSmoke.setBounds(10, 61, 187, 14);
		smoke.add(lblLaunchedSmoke);
		
		lblLaunchesRemaining = new JLabel("Launches Remaining:");
		lblLaunchesRemaining.setBounds(10, 86, 187, 14);
		smoke.add(lblLaunchesRemaining);
		
		lblTrailingSmoke = new JLabel("Trailing Smoke:");
		lblTrailingSmoke.setBounds(10, 11, 187, 14);
		smoke.add(lblTrailingSmoke);
		
		lblTurnsRemaining = new JLabel("Turns Reminaing:");
		lblTurnsRemaining.setBounds(10, 36, 187, 14);
		smoke.add(lblTurnsRemaining);
		
		JButton btnLaunch = new JButton("Launch");
		btnLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				
				selectedVehicle.smokeData.launchSmoke();
				GameWindow.gameWindow.conflictLog.addNewLine("Attempted Smoke Launch");
				refreshSelectedVehicle();
			}
		});
		btnLaunch.setBounds(306, 77, 118, 23);
		smoke.add(btnLaunch);
		
		lblTrailing = new Label("Trailing:");
		lblTrailing.setBounds(306, 3, 118, 22);
		smoke.add(lblTrailing);
		
		JButton btnNextPhase_1_1 = new JButton("Toggle Trailing");
		btnNextPhase_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				
				selectedVehicle.smokeData.toggleTrailingSmoke();
				GameWindow.gameWindow.conflictLog.addNewLine("Toggle trailing smoke");
				refreshSelectedVehicle();
				
			}
		});
		btnNextPhase_1_1.setBounds(306, 32, 118, 23);
		smoke.add(btnNextPhase_1_1);
		
		JPanel notes = new JPanel();
		tabbedPane.addTab("Notes", null, notes, null);
		notes.setLayout(null);
		
		textAreaNotes = new JTextArea();
		textAreaNotes.setBounds(0, 0, 434, 193);
		notes.add(textAreaNotes);
		
		Combat = new JPanel();
		tabbedPane.addTab("Combat", null, Combat, null);
		Combat.setLayout(null);
		
		comboBoxTurrets = new JComboBox();
		comboBoxTurrets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxTurrets.getItemCount() <= 0 || comboBoxTurrets.getSelectedIndex() < 0
						|| selectedVehicle == null)
					return;
				
				var turret = selectedVehicle.turretData.turrets.get(comboBoxTurrets.getSelectedIndex());
				
				var currentFacing = VehicleDataUtility.getTurretFacing(turret, selectedVehicle);
				
				var facingWidth = Math.abs(turret.facingWidth/20);
				
				if(facingWidth < 1)
					facingWidth = 1;
				
				var leftWidth = HexDirectionUtility.getFaceInDirection(currentFacing, false, facingWidth);
				var rightWidth = HexDirectionUtility.getFaceInDirection(currentFacing, true, facingWidth);
				
				lblTurretFacing.setText("Turret Facing: "+turret.facingDirection+", "+leftWidth+"-"+currentFacing+"-"+rightWidth);
				
				lblTurretWidth.setText("Turret Width: "+turret.facingWidth);
				SpinnerModel sm = new SpinnerNumberModel(turret.nextFacing, -turret.rotationSpeedPerPhaseDegrees, 
						turret.rotationSpeedPerPhaseDegrees, 1); 
				spinnerTurretRotation.setModel(sm);
				spinnerTurretRotation.setValue(turret.nextFacing);
			}
		});
		comboBoxTurrets.setBounds(10, 31, 131, 22);
		Combat.add(comboBoxTurrets);
		
		JLabel lblNewLabel_2 = new JLabel("Turret");
		lblNewLabel_2.setBounds(10, 11, 131, 14);
		Combat.add(lblNewLabel_2);
		
		lblTurretFacing = new JLabel("Turret Facing:");
		lblTurretFacing.setBounds(151, 11, 178, 14);
		Combat.add(lblTurretFacing);
		
		lblTurretWidth = new JLabel("Turret Width:");
		lblTurretWidth.setBounds(151, 35, 178, 14);
		Combat.add(lblTurretWidth);
		
		JLabel lblRotation = new JLabel("Rotation:");
		lblRotation.setBounds(339, 12, 70, 14);
		Combat.add(lblRotation);
		
		spinnerTurretRotation = new JSpinner();
		spinnerTurretRotation.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(comboBoxTurrets.getItemCount() <= 0 || comboBoxTurrets.getSelectedIndex() < 0
						|| selectedVehicle == null)
					return;
				
				var turret = selectedVehicle.turretData.turrets.get(comboBoxTurrets.getSelectedIndex());
				turret.nextFacing = turret.facingDirection + (int) spinnerTurretRotation.getValue();
				
				if(turret.nextFacing < turret.minFacing) {
					turret.nextFacing = turret.minFacing;
					spinnerTurretRotation.setValue(turret.nextFacing);
				} else if(turret.nextFacing > turret.maxFacing) {
					turret.nextFacing = turret.maxFacing;
					spinnerTurretRotation.setValue(turret.nextFacing);
				}
				
			}
		});
		spinnerTurretRotation.setBounds(339, 33, 70, 20);
		Combat.add(spinnerTurretRotation);
		
		chckbxFired = new JCheckBox("Fired");
		chckbxFired.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedVehicle == null)
					return;
				selectedVehicle.spotData.fired = chckbxFired.isSelected();
			}
		});
		chckbxFired.setBounds(560, 361, 132, 23);
		frame.getContentPane().add(chckbxFired);
		
		chckbxSkipSpotTest = new JCheckBox("Skip Spot Test");
		chckbxSkipSpotTest.setBounds(658, 667, 187, 23);
		frame.getContentPane().add(chckbxSkipSpotTest);
		
		JButton btnNewButton_8_2_1 = new JButton("Unselect");
		btnNewButton_8_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				unselectVehicle();
			}
		});
		btnNewButton_8_2_1.setBounds(297, 5, 99, 23);
		frame.getContentPane().add(btnNewButton_8_2_1);
		
		JButton btnNewButton_1_1 = new JButton("Fire Mission");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ArtilleryWindow(selectedVehicle, GameWindow.gameWindow);
			}
		});
		btnNewButton_1_1.setBounds(406, 247, 144, 23);
		frame.getContentPane().add(btnNewButton_1_1);
		textAreaNotes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(selectedVehicle == null)
					return;
				selectedVehicle.notes = textAreaNotes.getText();
			}
		});
		
		
		
	}
}
