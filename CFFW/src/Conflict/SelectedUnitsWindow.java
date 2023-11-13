package Conflict;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import Company.Company;
import HexGrid.HexGrid.DeployedUnit;
import Unit.Unit;
import UtilityClasses.SwingUtility;

import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class SelectedUnitsWindow {

	private JFrame frame;

	public ArrayList<Unit> selectedUnits = new ArrayList<>();
	public ArrayList<Unit> possibleUnits = new ArrayList<>();
	private JList listInitOrder;
	private JList listSelectedUnits;
	private JComboBox comboBoxSide;
	private JButton btnNewButton_1;
	
	/**
	 * @wbp.parser.constructor
	 */
	public SelectedUnitsWindow() {
		initialize();
	}
	
	public SelectedUnitsWindow(int x, int y, ArrayList<DeployedUnit> selectedUnits) {
		
		this.selectedUnits = GameWindow.gameWindow.getUnitsInHex("None", x, y);
		
		for(DeployedUnit deployedUnit : selectedUnits) {
			if(this.selectedUnits.contains(deployedUnit.unit))
				continue; 
			
			this.selectedUnits.add(deployedUnit.unit);
		}
		
		
		initialize();
	}
	
	public SelectedUnitsWindow(int x, int y) {
		
		selectedUnits = GameWindow.gameWindow.getUnitsInHex("None", x, y);
		
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 767, 623);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;

		// Set the new frame location
		frame.setLocation(x, y);
		
		JLabel lblNewLabel = new JLabel("Init Order");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 58, 121, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblSelectedUnits = new JLabel("Selected Units");
		lblSelectedUnits.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSelectedUnits.setBounds(380, 58, 121, 14);
		frame.getContentPane().add(lblSelectedUnits);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton.setBounds(651, 11, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnOpenBulkWindow = new JButton("Open Bulk Window");
		btnOpenBulkWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(selectedUnits.size() < 1)
					return;
				
				new BulkWindow(selectedUnits);
				
				frame.dispose();
			}
		});
		btnOpenBulkWindow.setBounds(490, 11, 151, 23);
		frame.getContentPane().add(btnOpenBulkWindow);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 84, 360, 490);
		frame.getContentPane().add(scrollPane);
		
		listInitOrder = new JList();
		listInitOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(listInitOrder.getSelectedIndex() < 0)
					return; 
				
				var unselectedUnits = getUnselectedUnits();
				
				selectedUnits.add(unselectedUnits.get(listInitOrder.getSelectedIndex()));
				
				
				refreshInitOrder();
				refreshSelectedUnits();
			}
		});
		scrollPane.setViewportView(listInitOrder);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(380, 83, 360, 490);
		frame.getContentPane().add(scrollPane_1);
		
		listSelectedUnits = new JList();
		listSelectedUnits.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(listSelectedUnits.getSelectedIndex() < 0)
					return; 
				
				
				selectedUnits.remove(listSelectedUnits.getSelectedIndex());
				
				refreshInitOrder();
				refreshSelectedUnits();
				
			}
		});
		scrollPane_1.setViewportView(listSelectedUnits);
		
		btnNewButton_1 = new JButton("Add All");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(var unit : getUnitsBySide()) {
					selectedUnits.add(unit);
				}
				
				refreshInitOrder();
				refreshSelectedUnits();
				
			}
		});
		btnNewButton_1.setBounds(10, 11, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		comboBoxSide = new JComboBox();
		comboBoxSide.setModel(new DefaultComboBoxModel(new String[] {"BLUFOR", "OPFOR", "EITHER"}));
		comboBoxSide.setSelectedIndex(0);
		comboBoxSide.setBounds(380, 11, 100, 22);
		frame.getContentPane().add(comboBoxSide);
		
		JButton btnNewButton_1_1 = new JButton("Add w/ LOS");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(var unit : getUnitsByLos()) {
					selectedUnits.add(unit);
				}
				
				refreshInitOrder();
				refreshSelectedUnits();
				
			}
		});
		btnNewButton_1_1.setBounds(109, 11, 100, 23);
		frame.getContentPane().add(btnNewButton_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("Add w/ Spots");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				for(var unit : getUnitsBySpot()) {
					selectedUnits.add(unit);
				}
				
				refreshInitOrder();
				refreshSelectedUnits();
			
			}
		});
		btnNewButton_1_1_1.setBounds(219, 11, 108, 23);
		frame.getContentPane().add(btnNewButton_1_1_1);
		
		frame.setVisible(true);
		
		setPossibleUnits();
		refreshInitOrder();
		refreshSelectedUnits();
	}
	
	public void setPossibleUnits() {
		
		for(Company company : GameWindow.gameWindow.companies) {
			
			for(Unit unit : company.getUnits()) {
				
				if(GameWindow.gameWindow.initiativeOrder.contains(unit)) {
					possibleUnits.add(unit);
				}
				
			}
			
		}
		
	}
	
	public void refreshInitOrder() {
		
		ArrayList<String> initOrder = new ArrayList<>();
		
		for(Unit unit : possibleUnits) {
			if(selectedUnits.contains(unit)) {
				continue;
			}
			
			initOrder.add(unit.toString());
			
		}
		
		SwingUtility.setList(listInitOrder, initOrder);
		
	}
	
	public void refreshSelectedUnits() {
		ArrayList<String> units = new ArrayList<>();
		
		for(Unit unit : selectedUnits) {
			units.add(unit.toString());
		}
		
		SwingUtility.setList(listSelectedUnits, units);
	}
	
	public ArrayList<Unit> getUnselectedUnits() {
			
		ArrayList<Unit> unselectedUnits = new ArrayList<Unit>();
		
		for(Unit unit : possibleUnits) {
			if(selectedUnits.contains(unit))
				continue; 
			
			unselectedUnits.add(unit);
		}
		
		return unselectedUnits;
		
	}
	
	public ArrayList<Unit> getUnitsBySpot() {
		
		var units = getUnitsBySide();
		
		var spotUnits = new ArrayList<Unit>();
		
		for(var unit : units)
			if(hasSpots(unit))
				spotUnits.add(unit);
		
		return spotUnits;
	}
	
	private boolean hasSpots(Unit unit) {
		
		for(var trooper : unit.individuals) {
			
			for(var spot : trooper.spotted) {
				
				if(spot.spottedIndividuals.size() > 0)
					return true;
				
			}
			
		}
		
		return false;
	}
	
	public ArrayList<Unit> getUnitsByLos() {
		
		var unselectedUnits = getUnitsBySide();

		ArrayList<Unit> losUnits = new ArrayList<Unit>();
		
		for(var unit : unselectedUnits) {
			if(unit.lineOfSight.size() > 0)
				losUnits.add(unit);
		}
		
		return losUnits;
		
	}
	
	public ArrayList<Unit> getUnitsBySide() {
		
		int sideIndex = comboBoxSide.getSelectedIndex();
		var unselectedUnits = getUnselectedUnits();
		if(sideIndex == 2)
			return unselectedUnits;
		
		ArrayList<Unit> sideUnits = new ArrayList<Unit>();
		
		for(var unit : unselectedUnits) {
			
			if(sideIndex == 0 &&
					unit.side.toUpperCase().equals("BLUFOR"))
				sideUnits.add(unit);
			else if(sideIndex == 1 &&
					unit.side.toUpperCase().equals("OPFOR"))
				sideUnits.add(unit);
				
			
		}
		
		return sideUnits;
		
	}
	
}
