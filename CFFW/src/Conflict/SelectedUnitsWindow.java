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

public class SelectedUnitsWindow {

	private JFrame frame;

	public ArrayList<Unit> selectedUnits = new ArrayList<>();
	public ArrayList<Unit> possibleUnits = new ArrayList<>();
	private JList listInitOrder;
	private JList listSelectedUnits;
	
	/**
	 * @wbp.parser.constructor
	 */
	public SelectedUnitsWindow() {
		initialize();
	}
	
	public SelectedUnitsWindow(int x, int y, ArrayList<DeployedUnit> selectedUnits) {
		
		this.selectedUnits = GameWindow.gameWindow.getUnitsInHex("None", x, y);
		
		for(DeployedUnit deployedUnit : selectedUnits) {
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
				
				ArrayList<Unit> unselectedUnits = new ArrayList<Unit>();
				
				for(Unit unit : possibleUnits) {
					if(selectedUnits.contains(unit))
						continue; 
					
					unselectedUnits.add(unit);
				}
				
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
	
}
