package UtilityClasses;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DialogBox {

	private JFrame frame;


	/**
	 * Create the application.
	 */
	public DialogBox(String text) {
		initialize(text);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String text) {
		frame = new JFrame();
		frame.setBounds(100, 100, 338, 165);
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
		
		JLabel lblUnitXSpeed = new JLabel(text);
		lblUnitXSpeed.setBounds(0, 0, 322, 99);
		lblUnitXSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnitXSpeed.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblUnitXSpeed);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnNewButton.setBounds(90, 88, 143, 27);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
	}

}
