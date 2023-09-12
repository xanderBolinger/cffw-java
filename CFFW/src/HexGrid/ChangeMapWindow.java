package HexGrid;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import Conflict.GameWindow;
import UtilityClasses.ExcelUtility;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

public class ChangeMapWindow {

	private JFrame frame;
	private JComboBox comboBoxMapName;

	/**
	 * Create the application.
	 */
	public ChangeMapWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 198);
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

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(comboBoxMapName.getSelectedIndex() < 0)
					return; 

				GameWindow.gameWindow.game.mapImageFileName = comboBoxMapName.getSelectedItem().toString();
				
				// Creates hex grid
				// System.out.println("Create hex grid");
				GameWindow.gameWindow.hexGrid = new HexGrid(GameWindow.gameWindow.initiativeOrder,
						GameWindow.gameWindow,  GameWindow.gameWindow.hexRows, GameWindow.gameWindow.hexCols);
				if (GameWindow.gameWindow.hexGrid != null
						&& GameWindow.gameWindow.hexGrid.panel.deployedUnits.size() > 0) {
					GameWindow.gameWindow.hexGrid.panel.selectedUnit = GameWindow.gameWindow.hexGrid.panel.deployedUnits
							.get(GameWindow.gameWindow.activeUnit);
				}
				String mapFileName = GameWindow.gameWindow.game.mapImageFileName;
				
				try {
					GameWindow.gameWindow.game.backgroundMap = true;
					LoadMap.loadMap(mapFileName.substring(0, mapFileName.lastIndexOf(".")));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				frame.dispose();
			}
		});
		btnConfirm.setBounds(10, 111, 414, 35);
		frame.getContentPane().add(btnConfirm);

		comboBoxMapName = new JComboBox();
		comboBoxMapName.setBounds(10, 58, 414, 35);
		frame.getContentPane().add(comboBoxMapName);

		JLabel lblNewLabel = new JLabel("Map Names:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 414, 36);
		frame.getContentPane().add(lblNewLabel);
		File dir = new File(ExcelUtility.path + "\\Map Images");
		File[] directoryListing = dir.listFiles();
		for (File file : directoryListing) {
			file.getName();
			//System.out.println("File: " + file.getName());
			comboBoxMapName.addItem(file.getName());
		}

		frame.setVisible(true);
	}
}
