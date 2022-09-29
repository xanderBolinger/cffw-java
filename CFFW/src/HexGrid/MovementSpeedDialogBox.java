package HexGrid;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

import Conflict.GameWindow;
import HexGrid.HexGrid.DeployedUnit;
import Unit.Unit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MovementSpeedDialogBox {

	private JFrame frame;
	public Unit unit; 
	public int targetX; 
	public int targetY;
	DeployedUnit deployedUnit; 
	
	/**
	 * Create the application.
	 */
	/**
	 * Create the application.
	 * @wbp.parser.constructor 
	 */
	public MovementSpeedDialogBox(Unit unit, int x, int y, DeployedUnit deployedUnit) {
		this.unit = unit; 
		this.targetX = x; 
		this.targetY = y; 
		this.deployedUnit = deployedUnit; 
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 439, 165);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;

		// Set the new frame location
		frame.setLocation(x, y);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUnitXSpeed = new JLabel("Unit X Speed is Set to None");
		lblUnitXSpeed.setBounds(0, 0, 418, 99);
		lblUnitXSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnitXSpeed.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblUnitXSpeed);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnNewButton.setBounds(10, 88, 91, 27);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		frame.getContentPane().add(btnNewButton);
		
		lblUnitXSpeed.setText("Unit: "+unit.callsign+" Speed is Set to None.");
		
		JButton btnWalk = new JButton("Walk");
		btnWalk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				unit.speed = "Walk";
				unit.move(GameWindow.gameWindow, targetX, targetY, null);
				deployedUnit.moved = true; 
				//System.out.println("Moved equal true: "+deployedUnit.getCallsign());
				GameWindow.gameWindow.hexGrid.refreshDeployedUnits();
				frame.dispose();
			}
		});
		btnWalk.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnWalk.setBounds(111, 88, 96, 27);
		frame.getContentPane().add(btnWalk);
		
		JButton btnRush = new JButton("Rush");
		btnRush.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				unit.speed = "Rush";
				unit.move(GameWindow.gameWindow, targetX, targetY, null);
				deployedUnit.moved = true; 
				//System.out.println("Moved equal true: "+deployedUnit.getCallsign());
				GameWindow.gameWindow.hexGrid.refreshDeployedUnits();
				frame.dispose();
			}
		});
		btnRush.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRush.setBounds(216, 88, 96, 27);
		frame.getContentPane().add(btnRush);
		
		JButton btnNone = new JButton("None");
		btnNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				unit.move(GameWindow.gameWindow, targetX, targetY, null);
				GameWindow.gameWindow.hexGrid.refreshDeployedUnits();
				frame.dispose();
			}
		});
		btnNone.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNone.setBounds(322, 88, 96, 27);
		frame.getContentPane().add(btnNone);
		
		frame.setVisible(true);
	}
}
