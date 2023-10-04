package Melee.Window;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;

public class MeleeCombatWindow {

	private JFrame frame;
	private JList listEligableMeleeCombatUnits;
	private JList listTargetList;
	private JList listMeleeCombatIndividuals;
	private JLabel lblRanks;
	private JLabel lblWidth;
	private JList listTargetIndividuals;

	/**
	 * Create the application.
	 */
	public MeleeCombatWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;

		// Set the new frame location
		frame.setLocation(x, y);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Eligable Melee Combat Units:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 314, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 467, 350);
		frame.getContentPane().add(scrollPane);
		
		listEligableMeleeCombatUnits = new JList();
		scrollPane.setViewportView(listEligableMeleeCombatUnits);
		
		JLabel lblEligableTargetList = new JLabel("Eligable Target List:");
		lblEligableTargetList.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEligableTargetList.setBounds(487, 11, 314, 28);
		frame.getContentPane().add(lblEligableTargetList);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(487, 50, 487, 350);
		frame.getContentPane().add(scrollPane_1);
		
		listTargetList = new JList();
		scrollPane_1.setViewportView(listTargetList);
		
		JButton btnNewButton = new JButton("Charge");
		btnNewButton.setBounds(10, 411, 117, 28);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(138, 411, 117, 28);
		frame.getContentPane().add(btnWithdraw);
		
		lblWidth = new JLabel("Formation Width:");
		lblWidth.setBounds(10, 480, 130, 14);
		frame.getContentPane().add(lblWidth);
		
		lblRanks = new JLabel("Formation Ranks:");
		lblRanks.setBounds(164, 480, 130, 14);
		frame.getContentPane().add(lblRanks);
		
		JLabel lblMeleeCombatIndividuals = new JLabel("Melee Combat Individuals:");
		lblMeleeCombatIndividuals.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMeleeCombatIndividuals.setBounds(10, 450, 314, 28);
		frame.getContentPane().add(lblMeleeCombatIndividuals);
		
		JButton btnToggleIndividual = new JButton("Toggle Individual");
		btnToggleIndividual.setBounds(265, 411, 117, 28);
		frame.getContentPane().add(btnToggleIndividual);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 505, 467, 245);
		frame.getContentPane().add(scrollPane_2);
		
		listMeleeCombatIndividuals = new JList();
		scrollPane_2.setViewportView(listMeleeCombatIndividuals);
		
		JButton btnEpicChallenge = new JButton("Epic Challenge");
		btnEpicChallenge.setBounds(357, 473, 120, 28);
		frame.getContentPane().add(btnEpicChallenge);
		
		JScrollPane scrollPane_2_1 = new JScrollPane();
		scrollPane_2_1.setBounds(487, 505, 487, 245);
		frame.getContentPane().add(scrollPane_2_1);
		
		listTargetIndividuals = new JList();
		scrollPane_2_1.setViewportView(listTargetIndividuals);
		
		JLabel lblTargetIndividuals = new JLabel("Target Individuals");
		lblTargetIndividuals.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTargetIndividuals.setBounds(487, 471, 314, 28);
		frame.getContentPane().add(lblTargetIndividuals);
		
		frame.setVisible(true);
	}
}
