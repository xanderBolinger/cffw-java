package CloseCombat;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import Conflict.GameWindow;
import Conflict.OpenUnit;
import Trooper.Trooper;
import Trooper.generateSquad;
import Unit.Unit;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTextField;

public class CloseCombatWindow {
	private JList listStay;
	private JList listFlee;

	public ArrayList<Trooper> flee = new ArrayList<>();
	public ArrayList<Trooper> stay = new ArrayList<>();
	private JTextField textFieldCallsign;

	public CloseCombatWindow(Unit unit, GameWindow window, OpenUnit openUnit) {
		final JFrame f = new JFrame("Close Combat");
		f.setSize(648, 550);
		f.getContentPane().setLayout(null);
		
		
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;

		// Set the new frame location
		f.setLocation(x, y);
		f.setVisible(true);

		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Close Combat");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblFlee = new JLabel("Flee");
		lblFlee.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel lblStay = new JLabel("Stay");
		lblStay.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JButton btnFlee = new JButton("Flee");
		btnFlee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				// Adds new unit 
				// Splits unit 
				ArrayList<Trooper> individuals = new ArrayList<Trooper>();
				generateSquad squad = new generateSquad("Clone Trooper Phase 1", "Empty");
				individuals = squad.getSquad();
				Unit newUnit = new Unit(textFieldCallsign.getText(), 0, 0, individuals, 100,
						0, 100, 0, 0, 20, 0, unit.behavior);
				
				newUnit.side = unit.side;
				newUnit.initiative = unit.initiative;
				newUnit.organization = unit.organization / 2;
				unit.organization = unit.organization / 2; 
				
				newUnit.suppression = unit.suppression;
				newUnit.moral = unit.moral;
				newUnit.cohesion = unit.cohesion;
				newUnit.company = unit.company;
				newUnit.X = unit.X;
				newUnit.Y = unit.Y;
				
				// Removes fleeing individuals 
				for(int i = 0; i < unit.getTroopers().size(); i++) {
					
					ArrayList<Trooper> troopers = unit.getTroopers();
					
					for(int x = 0; x < flee.size(); x++) {
						
						if(flee.get(x).compareTo(troopers.get(i))) {
							unit.individuals.remove(i);
							newUnit.addToUnit(flee.get(x));
						}
						
					}
					
				}
				
				
				window.initiativeOrder.add(newUnit);
				
				window.rollInitiativeOrder();
				window.refreshInitiativeOrder();
				
				// Finds newUnit's company 
				// Adds unit to company 
				for(int i = 0; i < window.companies.size(); i++) {
					
					if(window.companies.get(i).getName().equals(newUnit.company)) {
						window.companies.get(i).addUnit(newUnit);
						// Adds companies to setupWindow
						//window.confirmCompany(window.companies.get(i), i);
						
						f.dispose();
						
					}
					
				}
				
				openUnit.refreshIndividuals();
				
			}
		});
		
		textFieldCallsign = new JTextField();
		textFieldCallsign.setColumns(10);
		
		JLabel lblCallsign = new JLabel("Callsign");
		lblCallsign.setFont(new Font("Calibri", Font.BOLD, 15));
		GroupLayout groupLayout = new GroupLayout(f.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFlee, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCallsign))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnFlee)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textFieldCallsign)
									.addPreferredGap(ComponentPlacement.RELATED)))))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(88)
							.addComponent(lblStay)))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addGap(26))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCallsign, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(textFieldCallsign, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFlee, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnFlee)
						.addComponent(lblStay, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE))
					.addGap(110))
		);
		
		listStay = new JList();
		listStay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				addFlee();
				
			}
		});
		scrollPane_1.setViewportView(listStay);
		
		listFlee = new JList();
		listFlee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				addStay();
				
			}
		});
		scrollPane.setViewportView(listFlee);
		f.getContentPane().setLayout(groupLayout);
		
		setFields(unit);
	}
	
	
	public void setFields(Unit unit) {
		DefaultListModel stayList = new DefaultListModel();
		
		for(int i = 0; i < unit.getTroopers().size(); i++) {
			stayList.addElement(unit.getTroopers().get(i).toString());
			stay.add(unit.getTroopers().get(i));
		}
		
		listStay.setModel(stayList);
		
		
	}
	
	
	// Removes selected trooper from stay, and adds them to flee 
	public void addFlee() {
		
		flee.add(stay.get(listStay.getSelectedIndex()));
		stay.remove(listStay.getSelectedIndex());
		
		refreshLists();
		
	}
	
	// Does the opposite of add 
	public void addStay() {
		
		stay.add(flee.get(listFlee.getSelectedIndex()));
		flee.remove(listFlee.getSelectedIndex());
		
		refreshLists();
	}
	
	// Refreshs lists
	public void refreshLists() {
		DefaultListModel fleeList = new DefaultListModel();
		
		for(int i = 0; i < flee.size(); i++) {
			fleeList.addElement(flee.get(i));
		}
		
		listFlee.setModel(fleeList);
		
		DefaultListModel stayList = new DefaultListModel();
		
		for(int i = 0; i < stay.size(); i++) {
			stayList.addElement(stay.get(i));
		}
		
		listStay.setModel(stayList);
	}
}
