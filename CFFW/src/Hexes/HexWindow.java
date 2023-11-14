package Hexes;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.ListModel;

import Unit.Unit;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;

import Conflict.GameWindow;
import Conflict.OpenUnit;
import Hexes.Building.Floor;

import javax.swing.event.ListSelectionEvent;

public class HexWindow {

	
	private GameWindow game; 
	private boolean newHex;
	private int xCord; 
	private int yCord; 
	private Unit openUnit; 
	private OpenUnit openUnitWindow;
	
	private JFrame frame;
	private JSpinner spinnerXCord;
	private JSpinner spinnerYCord;
	private JSpinner spinnerElevation;
	private JComboBox comboBoxConcealment;
	private JSpinner spinnerCoverPositions;
	private JList listHexes;
	private JList listFeatures;
	private JSpinner spinnerObscuration;
	private JComboBox comboBox;
	private JList listBuildings;
	private JSpinner spinnerFloors;
	private JSpinner spinnerRooms;
	private JSpinner spinnerDiameter;
	private JTextField textFieldBuildingName;

	/**
	 * Create the application.
	 * @wbp.parser.constructor 
	 */
	public HexWindow(boolean newHex, int xCord, int yCord, Unit openUnit, OpenUnit openUnitWindow) {
		this.game = GameWindow.gameWindow;
		this.newHex = newHex; 
		this.xCord = xCord; 
		this.yCord = yCord; 
		this.openUnit = openUnit;
		this.openUnitWindow = openUnitWindow;
		
		initialize();
		
	}
	
	public HexWindow(int x, int y) {
		game = GameWindow.gameWindow;
		this.xCord = x; 
		this.yCord = y; 
		initialize();
		
		
		int i = 0;
		
		for(Hex hex : GameWindow.gameWindow.hexes) {
			
			if(hex.xCord == xCord && hex.yCord == yCord) {
				
				listHexes.setSelectedIndex(i);
				
			}
			
			i++; 
			
		}
		
		listHexesClicked();
		
	}
	
	public HexWindow(GameWindow game) {
		this.game = game;
		initialize();
	}

	
	public HexWindow(GameWindow game, int xCord, int yCord) {
		this.game = game;
		this.newHex = true; 
		this.xCord = xCord; 
		this.yCord = yCord;
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 741, 700);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hexes");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 49, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblFeatures = new JLabel("Features");
		lblFeatures.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFeatures.setBounds(241, 11, 121, 14);
		frame.getContentPane().add(lblFeatures);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 221, 618);
		frame.getContentPane().add(scrollPane);
		
		listHexes = new JList();
		listHexes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				listHexesClicked();
				
			}
		});
		scrollPane.setViewportView(listHexes);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(241, 32, 137, 618);
		frame.getContentPane().add(scrollPane_1);
		
		listFeatures = new JList();
		listFeatures.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				if(listHexes.getSelectedIndex() < 0) {
					return; 
				} else if(game.hexes.get(listHexes.getSelectedIndex()).features.size() < 1)
					return; 
				
				
				Feature feature = game.hexes.get(listHexes.getSelectedIndex()).features.get(listFeatures.getSelectedIndex());
				
				for(int i = 0; i < comboBox.getItemCount(); i++) {
					
					if(feature.featureType.equals(comboBox.getItemAt(i))) {
						comboBox.setSelectedIndex(i);
						break;
					}
						
					
				}
				
				spinnerCoverPositions.setValue(feature.coverPositions);
				
				//for(Element elem : )
				
				
			}
		});
		scrollPane_1.setViewportView(listFeatures);
		
		JLabel lblHexeValues = new JLabel("Hexe Values");
		lblHexeValues.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHexeValues.setBounds(567, 11, 374, 14);
		frame.getContentPane().add(lblHexeValues);
		
		JLabel lblXcord = new JLabel("xCord");
		lblXcord.setBounds(566, 33, 46, 14);
		frame.getContentPane().add(lblXcord);
		
		JLabel lblYcord = new JLabel("yCord");
		lblYcord.setBounds(566, 58, 46, 14);
		frame.getContentPane().add(lblYcord);
		
		JLabel lblElevation = new JLabel("Elevation");
		lblElevation.setBounds(566, 83, 59, 14);
		frame.getContentPane().add(lblElevation);
		
		JLabel lblConcealment = new JLabel("Concealment");
		lblConcealment.setBounds(567, 133, 109, 14);
		frame.getContentPane().add(lblConcealment);
		
		comboBoxConcealment = new JComboBox();
		comboBoxConcealment.setModel(new DefaultComboBoxModel(new String[] {"No Concealment ", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5"}));
		comboBoxConcealment.setSelectedIndex(0);
		comboBoxConcealment.setBounds(567, 151, 146, 20);
		frame.getContentPane().add(comboBoxConcealment);
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(listHexes.getSelectedIndex() < 0 || game.hexes.size() < 1)
					return; 
				
				game.hexes.get(listHexes.getSelectedIndex()).concealment = comboBoxConcealment.getSelectedIndex();
				game.hexes.get(listHexes.getSelectedIndex()).xCord = (int) spinnerXCord.getValue();
				game.hexes.get(listHexes.getSelectedIndex()).yCord = (int) spinnerYCord.getValue();
				game.hexes.get(listHexes.getSelectedIndex()).elevation = (int) spinnerElevation.getValue();
				
				setFields(listHexes.getSelectedIndex());
				setFeatures();
			}
		});
		btnNewButton.setBounds(567, 182, 146, 23);
		frame.getContentPane().add(btnNewButton);
		
		spinnerXCord = new JSpinner();
		spinnerXCord.setBounds(619, 30, 39, 20);
		frame.getContentPane().add(spinnerXCord);
		
		spinnerYCord = new JSpinner();
		spinnerYCord.setBounds(619, 55, 39, 20);
		frame.getContentPane().add(spinnerYCord);
		
		spinnerElevation = new JSpinner();
		spinnerElevation.setBounds(619, 80, 39, 20);
		frame.getContentPane().add(spinnerElevation);
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				for(Hex hex : game.hexes) {
					
					if(hex.xCord == (int) spinnerXCord.getValue() && hex.yCord == (int) spinnerYCord.getValue()) {
						return;
					}
					
				}
				
				// Instantiates hex 
				// Adds hex to game 
				game.hexes.add(new Hex((int) spinnerXCord.getValue(), (int) spinnerYCord.getValue(), new ArrayList<Feature>(), 
						 (int) spinnerObscuration.getValue(), comboBoxConcealment.getSelectedIndex(), (int) spinnerElevation.getValue()));
				
				setFields(listHexes.getSelectedIndex());
				
				listHexes.setSelectedIndex(game.hexes.size() - 1);
				
			}
		});
		btnAddNew.setBounds(567, 216, 146, 23);
		frame.getContentPane().add(btnAddNew);
		
		JLabel lblHexeFeatures = new JLabel("Hexe Features");
		lblHexeFeatures.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHexeFeatures.setBounds(567, 246, 374, 14);
		frame.getContentPane().add(lblHexeFeatures);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(567, 271, 46, 14);
		frame.getContentPane().add(lblType);
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
				setCoverSpinner(comboBox.getSelectedItem().toString());
				
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Light Forest", "Medium Forest", "Heavy Forest", "Brush", "Heavy Brush", "Light Rock", "Medium Rock", "Heavy Rock", "Light Urban Sprawl", "Dense Urban Sprawl", "Rubble", "Small Depression", "Large Depression"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(599, 268, 114, 20);
		frame.getContentPane().add(comboBox);
		
		JLabel lblCoverPositions = new JLabel("Cover Positions:");
		lblCoverPositions.setBounds(567, 294, 99, 14);
		frame.getContentPane().add(lblCoverPositions);
		
		spinnerCoverPositions = new JSpinner();
		spinnerCoverPositions.setBounds(674, 291, 39, 20);
		frame.getContentPane().add(spinnerCoverPositions);
		
		JButton button = new JButton("Update");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int index = listHexes.getSelectedIndex();
				
				int featureIndex = listFeatures.getSelectedIndex();
				
				if(index < 0 || featureIndex < 0)
					return; 
				
				game.hexes.get(index).features.get(featureIndex).featureType = comboBox.getSelectedItem().toString();
				game.hexes.get(index).features.get(featureIndex).coverPositions = (int) spinnerCoverPositions.getValue();
				game.hexes.get(index).setTotalPositions();
				game.hexes.get(index).setConcealment(); 
				setFields(index);
				setFeatures();
				
			}
		});
		button.setBounds(567, 319, 146, 23);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("Add New");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int index = listHexes.getSelectedIndex();
				
				if(index < 0)
					return; 
				
				for(Feature feature : game.hexes.get(index).features) {
					
					if(feature.featureType.equals(comboBox.getSelectedItem().toString()))
						return;
					
				}
				
				game.hexes.get(index).features.add(new Feature(comboBox.getSelectedItem().toString(), (int) spinnerCoverPositions.getValue()));
				game.hexes.get(index).setTotalPositions();
				game.hexes.get(index).setConcealment(); 
				
				setFields(index);
				
				setFeatures();
				
			}
		});
		button_1.setBounds(567, 350, 146, 23);
		frame.getContentPane().add(button_1);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int index = listHexes.getSelectedIndex();
				
				int featureIndex = listFeatures.getSelectedIndex();
				
				if(index < 0 || featureIndex < 0)
					return; 
				
				game.hexes.get(index).features.remove(featureIndex);
				game.hexes.get(index).setTotalPositions();
				game.hexes.get(index).setConcealment(); 
				
				setFeatures();
				setFields(index);
				setFeatures();
			}
			
			
		});
		btnRemove.setBounds(567, 384, 146, 23);
		frame.getContentPane().add(btnRemove);
		
		JLabel lblObscur = new JLabel("Obscur.");
		lblObscur.setBounds(566, 111, 59, 14);
		frame.getContentPane().add(lblObscur);
		
		spinnerObscuration = new JSpinner();
		spinnerObscuration.setBounds(619, 108, 39, 20);
		frame.getContentPane().add(spinnerObscuration);
		
		JButton btnNewButton_1 = new JButton("Save/Close");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(newHex && openUnit != null) {
					
					Hex hex = null; 
					
					int xCord = openUnit.X;
					int yCord = openUnit.Y;
					
					for(Hex hex2 : game.hexes) {
						
						if(hex2.xCord == xCord && hex2.yCord == yCord) {
							hex = hex2; 
						}
						
					}
										
					if(hex != null) {
						//System.out.println("Hex Behavior Test: "+openUnit.behavior);
						if(!openUnit.behavior.equals("No Contact")) {
							openUnit.soughtCover = false; 
							openUnit.seekCover(hex, game);
							
						}
												
						
					}
					
					if(openUnitWindow != null) {
						openUnitWindow.refreshIndividuals();
						openUnitWindow.refreshSpinners();
					}
					
					GameWindow.gameWindow.CalcLOS(openUnit);
					GameWindow.gameWindow.conflictLog.addQueuedText();
					frame.dispose();
					
				} else {
					frame.dispose();
					
				}
								
				
			}
		});
		btnNewButton_1.setBounds(535, 616, 178, 34);
		
		JLabel lblBuildings = new JLabel("Buildings");
		lblBuildings.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBuildings.setBounds(388, 12, 121, 14);
		frame.getContentPane().add(lblBuildings);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(388, 32, 137, 618);
		frame.getContentPane().add(scrollPane_3);
		
		listBuildings = new JList();
		listBuildings.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
				
				if(listBuildings.getSelectedIndex() < 0) 
					return; 
				
				Building building = game.hexes.get(listHexes.getSelectedIndex()).buildings.get(listBuildings.getSelectedIndex());
				
				textFieldBuildingName.setText(building.name);
				
			}
		});
		scrollPane_3.setViewportView(listBuildings);
		
		JLabel lblFloor = new JLabel("Floor");
		lblFloor.setBounds(535, 494, 46, 14);
		frame.getContentPane().add(lblFloor);
		
		spinnerFloors = new JSpinner();
		spinnerFloors.setBounds(578, 491, 39, 20);
		frame.getContentPane().add(spinnerFloors);
		
		JLabel lblRooms = new JLabel("Rooms");
		lblRooms.setBounds(535, 516, 46, 14);
		frame.getContentPane().add(lblRooms);
		
		spinnerRooms = new JSpinner();
		spinnerRooms.setBounds(578, 513, 39, 20);
		frame.getContentPane().add(spinnerRooms);
		
		JButton btnAddBuilding = new JButton("Add Building");
		btnAddBuilding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddBuilding.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				int index = listHexes.getSelectedIndex();
				
				if(index < 0) 
					return;
				
				
				Hex hex = game.hexes.get(index);
				
				hex.addBuilding(textFieldBuildingName.getText(), (int) spinnerFloors.getValue(), (int) spinnerRooms.getValue(), (int) spinnerDiameter.getValue());
				
				
				setBuildings();
				
			}
		});
		btnAddBuilding.setBounds(535, 547, 178, 23);
		frame.getContentPane().add(btnAddBuilding);
		
		JButton btnSetRooms = new JButton("Set");
		btnSetRooms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSetRooms.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(listBuildings.getSelectedIndex() < 0) {
					GameWindow.gameWindow.conflictLog.addNewLine("No Selected Building.");
					//System.out.println("Returning 1");
					return; 
				}
				
				Building building = game.hexes.get(listHexes.getSelectedIndex()).buildings.get(listBuildings.getSelectedIndex());
				
				int floorNumber = (int) spinnerFloors.getValue();
				
				
				if(floorNumber < 0) {
					//System.out.println("Returning 2");
					return;
				}
				
				
				if(floorNumber > building.floors.size()) {
					int difference = floorNumber- building.floors.size();
					
					int roomNumber = (int) spinnerRooms.getValue();
					int diameter = (int) spinnerDiameter.getValue();
					
					for(int i = 0; i < difference; i++)
						building.addFloor(roomNumber, diameter);
					
					setBuildings();
					
					return; 
				}
				

				
				
				for(Floor floor : building.floors) {
					
					int roomNumber = (int) spinnerRooms.getValue();
					int diameter = (int) spinnerDiameter.getValue();
					
					if(diameter < 1) {
						//System.out.println("Returning 3");
						return; 
					}
					
					if(roomNumber > floor.rooms.size()) {
						
						int difference = roomNumber - floor.rooms.size();
						
						for(int i = 0; i < difference; i++)
							floor.addRoom(diameter);
						
					} else if(roomNumber < 0) {
						return; 
					} else if(roomNumber == 0) {
						floor.rooms.clear();
					} else if(roomNumber < floor.rooms.size()) {
						
						int difference = floor.rooms.size() - roomNumber; 
						
						for(int i = 0; i < difference; i++)
							floor.removeLastRoom();
						
					}
					
					
					for(Hexes.Building.Room room : floor.rooms) {
						room.diameter = diameter;
					}
					
				}
				
				setBuildings();
				
			}
		});
		btnSetRooms.setBounds(535, 581, 84, 23);
		frame.getContentPane().add(btnSetRooms);
		
		JLabel lblDiameter = new JLabel("Diameter");
		lblDiameter.setBounds(619, 516, 46, 14);
		frame.getContentPane().add(lblDiameter);
		
		spinnerDiameter = new JSpinner();
		spinnerDiameter.setBounds(670, 513, 39, 20);
		frame.getContentPane().add(spinnerDiameter);
		
		JButton btnRemove_1 = new JButton("Remove");
		btnRemove_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(listBuildings.getSelectedIndex() < 0)
					return; 
				
				Hex hex = game.hexes.get(listHexes.getSelectedIndex());
				
				hex.buildings.get(listBuildings.getSelectedIndex()).removeTopFloor();
				
				setBuildings();
				
			}
		});
		btnRemove_1.setBounds(629, 581, 84, 23);
		frame.getContentPane().add(btnRemove_1);
		
		spinnerXCord.setValue(xCord);
		spinnerYCord.setValue(yCord);
		
		textFieldBuildingName = new JTextField();
		textFieldBuildingName.setBounds(535, 465, 178, 20);
		frame.getContentPane().add(textFieldBuildingName);
		textFieldBuildingName.setColumns(10);
		
		JLabel lblBuildingName = new JLabel("Building Name");
		lblBuildingName.setBounds(535, 447, 90, 14);
		frame.getContentPane().add(lblBuildingName);
		
		
		frame.getContentPane().add(btnNewButton_1);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setFields(-1);
		setCoverSpinner(comboBox.getSelectedItem().toString());
	}

	
	public void listHexesClicked() {
		DefaultListModel features = new DefaultListModel();
		
		Hex hex = game.hexes.get(listHexes.getSelectedIndex());
		
		for(Feature feature : hex.features) {
			
			features.addElement(feature);
			
		}
		
		listFeatures.setModel(features);
		
		spinnerXCord.setValue(hex.xCord);
		spinnerYCord.setValue(hex.yCord);
		spinnerElevation.setValue(hex.elevation);
		spinnerObscuration.setValue(hex.obscuration);
		
		comboBoxConcealment.setSelectedIndex(hex.concealment);
		
		setBuildings(); 
	}
	
	public void setFields(int index) {
		
		// Empties features list 
		listFeatures.setModel(new DefaultListModel());;
		
		DefaultListModel hexes = new DefaultListModel();
		
		for(Hex hex : game.hexes) {
			
			hexes.addElement(hex);
			
		}
		
		listHexes.setModel(hexes);
		if(index > -1)
			listHexes.setSelectedIndex(index);
	}
	
	public void setFeatures() {
		
		DefaultListModel model = new DefaultListModel();
		
		int index = listHexes.getSelectedIndex();
		
		if(index < 0) 
			return;
		
		for(Feature feature : game.hexes.get(index).features) {
			model.addElement(feature);
		}
		
		listFeatures.setModel(model);
		
	}
	
	
	// Takes type of cover
	// Determines lower and upper range for possible cover positions
	// Rolls and sets spinner
	
	public void setBuildings() {
		
		DefaultListModel model = new DefaultListModel();
		
		int index = listHexes.getSelectedIndex();
		
		if(index < 0) 
			return;
		
		int savedIndex = listBuildings.getSelectedIndex(); 
		
		for(Building building : game.hexes.get(index).buildings) {
			model.addElement(building);
		}
		
		listBuildings.setModel(model);
		
		if(savedIndex > -1)
			listBuildings.setSelectedIndex(savedIndex);
		
	}

	/*
	 * 
Light Forest
Medium Forest 
Heavy Forest
Brush
Heavy Brush
Light Rock
Medium Rock
Heavy Rock 
Rubble
Small Depression 
Large Depression
	 * 
	 * 
	 */
	
	public static int getCoverPostitions(String type) {
		int roll = 0; 
		Random rand = new Random();
		if(type.equals("Light Forest")) {
			roll = getRandomNumber(1, 4);
		} else if(type.equals("Medium Forest")) {
			roll = getRandomNumber(6, 12);
		} else if(type.equals("Heavy Forest")) {
			roll = getRandomNumber(15, 30);
		} else if(type.equals("Brush")) {
			//roll = getRandomNumber(6, 12);
		} else if(type.equals("Heavy Brush")) {
			//roll = getRandomNumber(6, 12);
		} else if(type.equals("Light Rock")) {
			roll = getRandomNumber(1, 4);
		} else if(type.equals("Medium Rock")) {
			roll = getRandomNumber(6, 12);
		} else if(type.equals("Light Urban Sprawl")) {
			roll = getRandomNumber(1, 4);
		} else if(type.equals("Dense Urban Sprawl")) {
			roll = getRandomNumber(5, 8);
		} else if(type.equals("Rubble")) {
			roll = getRandomNumber(8, 15);
		} else if(type.equals("Small Depression")) {
			roll = getRandomNumber(2, 4);
		} else if(type.equals("Large Depression")) {
			roll = getRandomNumber(5, 12);
		} 
		
		return roll;
	}
	
	public void setCoverSpinner(String type) {

		spinnerCoverPositions.setValue(getCoverPostitions(type));
		
	}
	
	
	public static int getRandomNumber(int min, int max) {
		max++; 
        return (int) ((Math.random() * (max - min)) + min);
    }
}
