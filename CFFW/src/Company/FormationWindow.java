package Company;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Company.Formation.LeaderType;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.SwingUtility;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormationWindow extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPaneIndividuals;
	private JList listIndividuals;
	private JList listSubordinates;

	Company company; 
	private JList listSelectSubordinates;
	private JComboBox comboBoxLeaderType;
	
	/**
	 * Create the frame.
	 */
	public FormationWindow(Company company) {
		this.company = company;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 977, 741);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		// y -= screenSize.height / 5;

		// Set the new frame location
		setLocation(x, y);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Leader Type:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 0, 109, 30);
		contentPane.add(lblNewLabel);
		
		scrollPaneIndividuals = new JScrollPane();
		scrollPaneIndividuals.setBounds(10, 64, 301, 627);
		contentPane.add(scrollPaneIndividuals);
		
		listIndividuals = new JList();
		listIndividuals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listIndividuals.getSelectedIndex() < 0)
					return;
				
				refreshSubordinates();
				refreshSelectSubordinates();
				
			}
		});
		scrollPaneIndividuals.setViewportView(listIndividuals);
		
		JLabel lblSelectTrooper = new JLabel("Select Trooper:");
		lblSelectTrooper.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSelectTrooper.setBounds(10, 35, 301, 30);
		contentPane.add(lblSelectTrooper);
		
		JScrollPane scrollPaneIndividuals_1 = new JScrollPane();
		scrollPaneIndividuals_1.setBounds(321, 64, 301, 627);
		contentPane.add(scrollPaneIndividuals_1);
		
		listSubordinates = new JList();
		listSubordinates.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listIndividuals.getSelectedIndex() < 0 || getSelectedTrooper() == null)
					return;
				
				getSelectedTrooper().subordinates.remove(listSubordinates.getSelectedIndex());
				
			}
		});
		scrollPaneIndividuals_1.setViewportView(listSubordinates);
		
		JLabel lblSubordinates = new JLabel("Subordinates:");
		lblSubordinates.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSubordinates.setBounds(321, 35, 301, 30);
		contentPane.add(lblSubordinates);
		
		JScrollPane scrollPaneIndividuals_1_1 = new JScrollPane();
		scrollPaneIndividuals_1_1.setBounds(632, 64, 301, 627);
		contentPane.add(scrollPaneIndividuals_1_1);
		
		listSelectSubordinates = new JList();
		listSelectSubordinates.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(listIndividuals.getSelectedIndex() < 0 || getSelectedTrooper() == null)
					return;
				
				addSubordinate();
				refreshSubordinates();
				refreshSelectSubordinates();
				
			}
		});
		scrollPaneIndividuals_1_1.setViewportView(listSelectSubordinates);
		
		JLabel lblSelectsubordinate = new JLabel("Select Subordinate:");
		lblSelectsubordinate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSelectsubordinate.setBounds(632, 35, 301, 30);
		contentPane.add(lblSelectsubordinate);
		
		comboBoxLeaderType = new JComboBox();
		comboBoxLeaderType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getSelectedTrooper() == null)
					return;
				
				
				setLeaderType();
				refreshTroopers();
				refreshSubordinates();
				refreshSelectSubordinates();
				
			}
		});
		comboBoxLeaderType.setModel(new DefaultComboBoxModel(new String[] {"NONE", "FTL", "SL", "PC", "CC", "BC"}));
		comboBoxLeaderType.setBounds(108, 5, 98, 22);
		contentPane.add(comboBoxLeaderType);
		
		
		
		
		refreshTroopers();
		setVisible(true);
	}
	
	public void setLeaderType() {
		
		String leaderType = comboBoxLeaderType.getSelectedItem().toString();
		
		for (LeaderType lt : LeaderType.values()) { 
		   if(lt.toString().equals(leaderType)) {
			   getSelectedTrooper().leaderType = lt;
			   return; 
		   }
		}
		
	}
	
	public Trooper getSelectedTrooper() {
		
		int selectedIndex = listIndividuals.getSelectedIndex();
		
		if(selectedIndex < 0)
			return null;
		
		int index = 0;
		for(Unit unit : company.getUnits()) {
			for(Trooper trooper : unit.individuals) {
				if(index == selectedIndex)
					return trooper;
				index++;
			}
		}
		
		for(Trooper trooper : company.getRoster()) {
			if(index == selectedIndex)
				return trooper;
			index++;
		}
		
		return null;
	}
	
	public void addSubordinate() {
		
		int selectedIndex = listSelectSubordinates.getSelectedIndex();
		
		if(selectedIndex < 0)
			return;
		
		int index = 0;
		
		for(Unit unit : company.getUnits()) {
			for(Trooper trooper : unit.individuals) {
				if(getSelectedTrooper().compareTo(trooper) || getSelectedTrooper().subordinates.contains(trooper))
					continue;
				if(index == selectedIndex) {
					
					getSelectedTrooper().subordinates.add(trooper);
					return;
				}
				index++;
			}
		}
		
		for(Trooper trooper : company.getRoster()) {
			if(getSelectedTrooper().compareTo(trooper) || getSelectedTrooper().subordinates.contains(trooper))
				continue;
			if(index == selectedIndex) {
				getSelectedTrooper().subordinates.add(trooper);
				return;
			}
			index++;
		}
		
		
	}
	
	public void refreshTroopers() {
		
		ArrayList<String> individualList = new ArrayList<>();
		
		for(Unit unit : company.getUnits()) {
			for(Trooper trooper : unit.individuals) {
				
				String leaderType = trooper.leaderType == LeaderType.NONE ? "" : trooper.leaderType.toString()+":: ";
				
				individualList.add(leaderType+unit.callsign+": "+trooper.number +" "+trooper.name+", "+trooper.designation
						+", Command Value: "+(trooper.getSkill("Command")/10));		
			}
		}
		
		for(Trooper trooper : company.getRoster()) {
			String leaderType = trooper.leaderType == LeaderType.NONE ? "" : trooper.leaderType.toString()+":: ";
			individualList.add(leaderType+"Roster: "+trooper.number +" "+trooper.name+", "+trooper.designation
					+", Command Value: "+(trooper.getSkill("Command")/10));		
		}
		
		SwingUtility.setList(listIndividuals, individualList);
		
	}
	
	public void refreshSubordinates() {
		
		ArrayList<String> individualList = new ArrayList<>();
		
		if(getSelectedTrooper() == null) {
			SwingUtility.setList(listSubordinates, individualList);
			return;
		}
		
		for(Trooper trooper : getSelectedTrooper().subordinates) {
			String leaderType = trooper.leaderType == LeaderType.NONE ? "" : trooper.leaderType.toString()+":: ";
			individualList.add(leaderType+"Roster: "+trooper.number +" "+trooper.name+", "+trooper.designation
					+", Command Value: "+(trooper.getSkill("Command")/10));		
		}
		
		SwingUtility.setList(listSubordinates, individualList);
	}
	
	public void refreshSelectSubordinates() {
		
		ArrayList<String> individualList = new ArrayList<>();
		if(getSelectedTrooper() == null) {
			SwingUtility.setList(listSubordinates, individualList);
			return;
		}
		
		for(Unit unit : company.getUnits()) {
			for(Trooper trooper : unit.individuals) {
				if(getSelectedTrooper().compareTo(trooper) || getSelectedTrooper().subordinates.contains(trooper))
					continue;
				String leaderType = trooper.leaderType == LeaderType.NONE ? "" : trooper.leaderType.toString()+":: ";
				individualList.add(leaderType+unit.callsign+": "+trooper.number +" "+trooper.name+", "+trooper.designation
						+", Command Value: "+(trooper.getSkill("Command")/10));		
			}
		}
		
		for(Trooper trooper : company.getRoster()) {
			if(getSelectedTrooper().compareTo(trooper) || getSelectedTrooper().subordinates.contains(trooper))
				continue;
			String leaderType = trooper.leaderType == LeaderType.NONE ? "" : trooper.leaderType.toString()+":: ";
			individualList.add(leaderType+"Roster: "+trooper.number +" "+trooper.name+", "+trooper.designation
					+", Command Value: "+(trooper.getSkill("Command")/10));		
		}
		
		SwingUtility.setList(listSelectSubordinates, individualList);
	}
	
}
