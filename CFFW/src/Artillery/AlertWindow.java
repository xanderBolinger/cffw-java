package Artillery;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AlertWindow {

	private JFrame frame;

	public AlertWindow(String message) {
		initialize(message);
		frame.setVisible(true);
	}

	private void initialize(String message) {
		frame = new JFrame();
		frame.setBounds(100, 100, 376, 266);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		//y -= screenSize.height / 5;

		// Set the new frame location
		frame.setLocation(x, y);
		
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 38, 342, 93);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(10, 171, 342, 48);
		frame.getContentPane().add(btnNewButton);
	
		lblNewLabel.setText(message);
		
	}
}
