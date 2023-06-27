package Conflict;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;

import Hexes.Building;
import Hexes.Building.Floor;
import Hexes.Hex;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class EmbarkWindow {

	private JFrame f;
	private JList individualList;
	private JList buildingsList;
	public Unit unit; 
	public Hex hex; 
	public GameWindow gameWindow;
	public Game game; 
	public Building selectedBuilding; 
	
	private JList embarkedIndividualsList;
	private JSpinner spinnerAddFloor;
	private JSpinner spinnerSetFloor;
	private JSpinner spinnerIndividualRoom;
	private JSpinner spinnerEmbarkedIndividualRoom;
	/**
	 * Create the application.
	 */
	public EmbarkWindow(GameWindow gameWindow, Hex hex, Unit unit) {

		this.unit = unit; 
		this.hex = hex;
		this.gameWindow = gameWindow;
		this.game = gameWindow.game;
		
		initialize();
	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		f = new JFrame();
		f.setBounds(100, 100, 1450, 775);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;

		// Set the new frame location
		f.setLocation(x, y);
		f.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Buildings");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(20, 12, 147, 14);
		f.getContentPane().add(lblNewLabel);
		
		JLabel lblIndividuals = new JLabel("Individuals");
		lblIndividuals.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIndividuals.setBounds(725, 251, 207, 14);
		f.getContentPane().add(lblIndividuals);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 36, 1404, 204);
		f.getContentPane().add(scrollPane);
		
		buildingsList = new JList();
		buildingsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
				if(buildingsList.getSelectedIndex() < 0) {
					selectedBuilding = null; 
					return; 
				}
				
				DefaultListModel<String> model = new DefaultListModel<>();
				
				selectedBuilding = hex.buildings.get(buildingsList.getSelectedIndex());
				
				for(Trooper trooper : selectedBuilding.getOccupants(unit)) {
						model.addElement("Floor Num: "+selectedBuilding.getTrooperFloorNumber(trooper)+ ", Room Num: "+selectedBuilding.getTrooperRoomNumber(trooper)+", "+trooper.toStringImproved(game));
				}
				
				embarkedIndividualsList.setModel(model);
				
				setFloorSpinners(); 
				setRoomSpinners();
			}
		});
		scrollPane.setViewportView(buildingsList);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(725, 276, 699, 416);
		f.getContentPane().add(scrollPane_1);
		
		individualList = new JList();
		scrollPane_1.setViewportView(individualList);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(20, 275, 695, 416);
		f.getContentPane().add(scrollPane_2);
		
		embarkedIndividualsList = new JList();
		scrollPane_2.setViewportView(embarkedIndividualsList);
		
		JLabel lblEmbarkedIndividuals = new JLabel("Embarked Individuals");
		lblEmbarkedIndividuals.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEmbarkedIndividuals.setBounds(20, 251, 207, 14);
		f.getContentPane().add(lblEmbarkedIndividuals);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(embarkedIndividualsList.getSelectedIndices().length < 1 || buildingsList.getSelectedIndex() < 0)
					return;
				
				for(int index : embarkedIndividualsList.getSelectedIndices())  {
					Trooper trooper = selectedBuilding.getOccupants(unit).get(index);
					selectedBuilding.removeOccupant(trooper);
					Hex hex = gameWindow.findHex(unit.X, unit.Y);
					
					if(unit.behavior.equals("Contact") && hex.coverPositions > hex.usedPositions) {
						
						trooper.inCover = true; 
						hex.usedPositions++; 
					} else {
						trooper.inCover = false; 
					}
					
					
				}
				
				refreshLists();
				
			}
		});
		btnRemove.setBounds(299, 703, 103, 23);
		f.getContentPane().add(btnRemove);
		
		JButton btnAddOccupant = new JButton("Add Occupant");
		btnAddOccupant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(selectedBuilding == null || individualList.getSelectedIndices().length < 1 || (int) spinnerAddFloor.getValue() < 1 
						|| (int) spinnerAddFloor.getValue() > selectedBuilding.floors.size()) {
					return; 
				}
				
				ArrayList<Trooper> unembarkedTroopers = hex.getUnembarkedTroopers(unit);
				
				for(int index : individualList.getSelectedIndices()) {
					System.out.println("V1: "+(int) spinnerAddFloor.getValue());
					System.out.println("V2: "+(int) spinnerIndividualRoom.getValue());
					selectedBuilding.addOccupant(unembarkedTroopers.get(index), (int) spinnerAddFloor.getValue(),  (int) spinnerIndividualRoom.getValue());
					
					Hex hex = gameWindow.findHex(unit.X, unit.Y);
					
					if(unembarkedTroopers.get(index).inCover) {
						hex.usedPositions--; 
					}
					
					
					unembarkedTroopers.get(index).inCover = true; 
					
					
					
				}
				
				refreshLists(); 
				
			}
		});
		btnAddOccupant.setBounds(907, 703, 148, 23);
		f.getContentPane().add(btnAddOccupant);
		
		JLabel lblFloor = new JLabel("Floor:");
		lblFloor.setBounds(725, 706, 36, 14);
		f.getContentPane().add(lblFloor);
		
		spinnerAddFloor = new JSpinner();
		spinnerAddFloor.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				System.out.println("Spinner 2 Change");
				setRoomSpinners();
			}
		});
		spinnerAddFloor.setBounds(759, 704, 46, 20);
		f.getContentPane().add(spinnerAddFloor);
		
		JLabel label = new JLabel("Floor:");
		label.setBounds(20, 706, 46, 14);
		f.getContentPane().add(label);
		
		spinnerSetFloor = new JSpinner();
		spinnerSetFloor.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				System.out.println("Spinner 1 Change");
				setRoomSpinners();
			}
		});
		spinnerSetFloor.setBounds(55, 704, 46, 20);
		f.getContentPane().add(spinnerSetFloor);
		
		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(selectedBuilding == null || embarkedIndividualsList.getSelectedIndices().length < 1 || (int) spinnerSetFloor.getValue() < 1 
						|| (int) spinnerSetFloor.getValue() > selectedBuilding.floors.size())
					return; 
				
				ArrayList<Trooper> embarkedTroopers = selectedBuilding.getOccupants(unit);
				
				for(int index : embarkedIndividualsList.getSelectedIndices()) {
					
					selectedBuilding.changeFloor(embarkedTroopers.get(index), (int) spinnerSetFloor.getValue(), (int) spinnerEmbarkedIndividualRoom.getValue());
				}
				
				refreshLists();
				
			}
		});
		btnSet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});
		btnSet.setBounds(212, 703, 77, 23);
		f.getContentPane().add(btnSet);
		
		spinnerIndividualRoom = new JSpinner();
		spinnerIndividualRoom.setBounds(851, 704, 46, 20);
		f.getContentPane().add(spinnerIndividualRoom);
		
		JLabel lblRoom = new JLabel("Room:");
		lblRoom.setBounds(815, 707, 36, 14);
		f.getContentPane().add(lblRoom);
		
		JLabel label_1 = new JLabel("Room:");
		label_1.setBounds(111, 706, 46, 14);
		f.getContentPane().add(label_1);
		
		spinnerEmbarkedIndividualRoom = new JSpinner();
		spinnerEmbarkedIndividualRoom.setBounds(157, 703, 46, 20);
		f.getContentPane().add(spinnerEmbarkedIndividualRoom);
		f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);
		
		refreshLists();
	}
	
	public void setFloorSpinners() {
		SpinnerModel  model = new SpinnerNumberModel(1, 1, selectedBuilding.floors.size(), 1);
		spinnerAddFloor.setModel(model);
		
		model = new SpinnerNumberModel(1, 1, selectedBuilding.floors.size(), 1);
		spinnerSetFloor.setModel(model);
	}
	
	public void setRoomSpinners() {
		
		if(selectedBuilding == null) {
			SpinnerModel  model1 = new SpinnerNumberModel(0, 0, 0, 0);
			spinnerAddFloor.setModel(model1);
			model1 = new SpinnerNumberModel(0, 0, 0, 0);
			spinnerSetFloor.setModel(model1);
			model1 = new SpinnerNumberModel(0, 0, 0, 0);
			spinnerIndividualRoom.setModel(model1);
			model1 = new SpinnerNumberModel(0, 0, 0, 0);
			spinnerEmbarkedIndividualRoom.setModel(model1);
		}
		
		// from 0 to 9, in 1.0 steps start value 5  
		//SpinnerModel  model1 = new SpinnerNumberModel(5.0, 0.0, 9.0, 1.0); 
		int setFloorNum = (int) spinnerSetFloor.getValue();
		if(setFloorNum > 0) {
			
			Floor floor = selectedBuilding.floors.get(setFloorNum-1);
			//System.out.println("Floor rooms: "+floor.rooms.size());
			SpinnerModel  model1 = new SpinnerNumberModel(DiceRoller.roll(1, floor.rooms.size()), 1, floor.rooms.size(), 1);
			spinnerEmbarkedIndividualRoom.setModel(model1);
		} else {
			SpinnerModel  model1 = new SpinnerNumberModel(0, 0, 0, 0);
			spinnerEmbarkedIndividualRoom.setModel(model1);
		}
		
		int addFloorNum = (int) spinnerAddFloor.getValue();
		if(addFloorNum > 0) {
			
			Floor floor = selectedBuilding.floors.get(addFloorNum-1);
			
			SpinnerModel  model1 =  new SpinnerNumberModel(DiceRoller.roll(1, floor.rooms.size()), 1, floor.rooms.size(), 1);
			spinnerIndividualRoom.setModel(model1);
		} else {
			SpinnerModel  model1 = new SpinnerNumberModel(0, 0, 0, 0);
			spinnerIndividualRoom.setModel(model1);
		}
			
	}
	
	
	
	
	public void refreshLists() {
		
		if(hex.buildings.size() < 1)
			return; 
		
		DefaultListModel<String> model = new DefaultListModel<>();
		
		for(Trooper trooper : unit.individuals) {
			if(!hex.containsTrooper(trooper))
				model.addElement(trooper.toStringImproved(game));
		}
		
		individualList.setModel(model);
		
		model = new DefaultListModel<>();
		
		for(Building building : hex.buildings) {
			model.addElement(building.toString(gameWindow, unit));
		}
		
		buildingsList.setModel(model);
		
		if(selectedBuilding == null)
			buildingsList.setSelectedIndex(0);
		else {
			model = new DefaultListModel<>();
			
			for(Trooper trooper : selectedBuilding.getOccupants(unit)) {
					model.addElement("Floor Num: "+selectedBuilding.getTrooperFloorNumber(trooper)+ ", Room Num: "+selectedBuilding.getTrooperRoomNumber(trooper)+", "+trooper.toStringImproved(game));
			}
			
			embarkedIndividualsList.setModel(model);
		}
		
	}
}
