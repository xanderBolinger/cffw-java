package HexGrid;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import Conflict.GameWindow;
import Hexes.SaveHexes;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;

public class SaveMapWindow {

	private JFrame f;
	private JTextField textField;

	
	/**
	 * Create the application.
	 */
	public SaveMapWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		f = new JFrame();
		f.setBounds(100, 100, 450, 300);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;
		f.setLocation(x, y);
		f.getContentPane().setLayout(null);
		f.setVisible(true);
		
		JLabel lblEnterSaveName = new JLabel("Enter Map Name");
		lblEnterSaveName.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterSaveName.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		textField = new JTextField();
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ObjectOutputStream out;
				try {
					out = new ObjectOutputStream(new FileOutputStream("Map Saves/"+textField.getText()));
					out.writeObject(new SaveHexes(GameWindow.gameWindow.hexes, GameWindow.gameWindow.game.procGenMap));
					out.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				f.dispose();
				
			}
		});
		
		
		textField.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(f.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblEnterSaveName, GroupLayout.PREFERRED_SIZE, 414, GroupLayout.PREFERRED_SIZE)
					.addGap(10))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(57)
					.addComponent(textField, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
					.addGap(59))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(133)
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
					.addGap(130))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(46)
					.addComponent(lblEnterSaveName, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addGap(106))
		);
		f.getContentPane().setLayout(groupLayout);
		
		textField.setText(GameWindow.gameWindow.game.mapImageFileName.substring(0, GameWindow.gameWindow.game.mapImageFileName.lastIndexOf(".")));
		
	}
}
