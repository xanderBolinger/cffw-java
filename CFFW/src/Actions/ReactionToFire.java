package Actions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Conflict.GameWindow;
import Conflict.OpenTrooper;
import Trooper.Trooper;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReactionToFire extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JFrame dialog; 
	public Trooper reactingTrooper;
	public OpenTrooper openTrooper; 
	public GameWindow game; 
	
	private JLabel lblReactingTrooper;
	

	/**
	 * Create the dialog.
	 */
	public ReactionToFire(Trooper reactingTrooper, OpenTrooper openTrooper, GameWindow game) {
		this.game = game; 
		this.reactingTrooper = reactingTrooper;
		this.openTrooper = openTrooper; 
		initialize();
		
	}
	
	public void initialize() {
		
		
		dialog = new JFrame();
		setBounds(100, 100, 450, 118);
		dialog.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPanel.setLayout(new FlowLayout());
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		
		int x = (screenSize.width - dialog.getWidth()) / 2;
		int y = (screenSize.height - dialog.getHeight()) / 2;

		// Set the new frame location
		dialog.setLocation(x, y);
		dialog.setVisible(true);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 43, 434, 56);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("Duck");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						
						if(openTrooper.openTrooper.inCover && openTrooper.targetedFire != null) {
							
							// EAL -6 
							// One more shot can be fired
							openTrooper.targetedFire.EAL -= 6; 
							openTrooper.targetedFire.possibleShots = openTrooper.targetedFire.shotsTaken + 1;
							openTrooper.windowOpenTrooper.lblPossibleShots.setText("Possible Shots: 1");
							dialog.dispose();
						} else {
							
							game.conflictLog.addNewLine("This trooper is not in cover and cannot duck.");
							
						}
						
					}
				});
				okButton.setBounds(38, 0, 65, 23);
				buttonPane.add(okButton);
				//okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Sprint");
				cancelButton.setBounds(113, 0, 65, 23);
				//cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			JButton btnRunToCover = new JButton("Run to Cover");
			//btnRunToCover.setActionCommand("Cancel");
			btnRunToCover.setBounds(188, 0, 97, 23);
			buttonPane.add(btnRunToCover);
			
			JButton btnHunkerDown = new JButton("Hunker Down");
			btnHunkerDown.setActionCommand("Cancel");
			btnHunkerDown.setBounds(295, 0, 97, 23);
			buttonPane.add(btnHunkerDown);
		}
		
		lblReactingTrooper = new JLabel("Reacting: ");
		lblReactingTrooper.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblReactingTrooper.setBounds(10, 9, 414, 23);
		contentPanel.add(lblReactingTrooper);
		
		
		setFields();
		
	}
	
	
	
	// Sets labels to correct values 
	public void setFields() {
		
		lblReactingTrooper.setText("Reacting: "+reactingTrooper.returnTrooperUnit(game).side+":: "+reactingTrooper.returnTrooperUnit(game).callsign+", "+reactingTrooper.number+" "+reactingTrooper.name);
		
	}
	
	// Closes open trooper if it should be closed
	public void closeTrooper() {
		
		if(openTrooper.possibleShots == false) {
			openTrooper.f.dispose();
			openTrooper.openUnit.openNext(true);
		}
		
	}
	
}
