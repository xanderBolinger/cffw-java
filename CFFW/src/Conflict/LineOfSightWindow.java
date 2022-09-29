package Conflict;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JScrollPane;

import Trooper.Trooper;
import Unit.Unit;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class LineOfSightWindow {

	private JFrame frame;
	private JLabel lblActiveUnit;
	private JList listInitiativeOrder;
	private JList listUnitsInLOS;
	private Unit unit; 
	private GameWindow game; 
	private ArrayList<Integer> initOrderList = new ArrayList<Integer>();

	/**
	 * Create the application.
	 */
	public LineOfSightWindow(Unit unit, GameWindow game) {
		this.unit = unit; 
		this.game = game;
		initialize(unit, game);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Unit unit, GameWindow game) {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.setBounds(100, 100, 370, 615);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;

		// Set the new frame location
		frame.setLocation(x, y);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
		JLabel lblInitiativeOrder = new JLabel("Initiative Order");
		lblInitiativeOrder.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInitiativeOrder.setBounds(20, 63, 152, 14);
		frame.getContentPane().add(lblInitiativeOrder);
		
		lblActiveUnit = new JLabel("Active Unit: ");
		lblActiveUnit.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblActiveUnit.setBounds(20, 25, 314, 14);
		frame.getContentPane().add(lblActiveUnit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 88, 152, 439);
		frame.getContentPane().add(scrollPane);
		
		listInitiativeOrder = new JList();
		listInitiativeOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listInitiativeOrder.getSelectedIndex() < 0 || game.initiativeOrder.size() < 0)
					return; 
				
				
				
				Unit addedUnit = game.initiativeOrder.get(initOrderList.get(listInitiativeOrder.getSelectedIndex()));
				if(!unit.lineOfSight.contains(addedUnit)) {
					unit.lineOfSight.add(addedUnit);
					if(!addedUnit.lineOfSight.contains(unit)) 
						addedUnit.lineOfSight.add(unit);
					
				}
				
				GameWindow.gameWindow.hexGrid.frame.toFront();
				GameWindow.gameWindow.hexGrid.frame.requestFocus();
				
				setFields();
				
			}
		});
		scrollPane.setViewportView(listInitiativeOrder);
		
		JLabel lblUnitsInLos = new JLabel("Units In LOS");
		lblUnitsInLos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUnitsInLos.setBounds(182, 63, 152, 14);
		frame.getContentPane().add(lblUnitsInLos);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(182, 88, 152, 439);
		frame.getContentPane().add(scrollPane_1);
		
		listUnitsInLOS = new JList();
		listUnitsInLOS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(unit.lineOfSight.size() < 1) {
					return;					
				}
				
				Unit removedUnit = unit.lineOfSight.get((listUnitsInLOS.getSelectedIndex()));
				unit.removeLOS(listUnitsInLOS.getSelectedIndex(), game);
				if(removedUnit.lineOfSight.contains(unit)) {
					
					removedUnit.removeLOS(removedUnit.lineOfSight.indexOf(unit), game);
					
				}
				setFields();
				
			}
		});
		scrollPane_1.setViewportView(listUnitsInLOS);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				frame.dispose();
				
			}
		});
		btnNewButton.setBounds(20, 532, 314, 33);
		frame.getContentPane().add(btnNewButton);
		
		
		setFields();
	}
	
	public void setFields() {
		
		initOrderList.clear();
		
		
		// Active unit label 
		lblActiveUnit.setText("Active Unit: "+unit.side+":: "+unit.callsign);
		
		
		// Init order list 
		DefaultListModel initiativeOrderList = new DefaultListModel();

		for (int i = 0; i < game.initiativeOrder.size(); i++) {
			
			if(!game.initiativeOrder.get(i).side.equals(unit.side) && !unit.lineOfSight.contains(game.initiativeOrder.get(i))) {
				
				initOrderList.add(i);
				
				initiativeOrderList.addElement(game.initiativeOrder.get(i).callsign);
				
			}
				

		}

		listInitiativeOrder.setModel(initiativeOrderList);
		
		// LOS list 
		DefaultListModel losList = new DefaultListModel();

		for(Unit spottedUnit : unit.lineOfSight)
			losList.addElement(spottedUnit.callsign);

		listUnitsInLOS.setModel(losList);
		
		
	}
	
}
