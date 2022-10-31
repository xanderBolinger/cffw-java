package Actions;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Conflict.GameWindow;
import Conflict.OpenTrooper;
import Hexes.Hex;
import Items.Weapons;
import Trooper.Trooper;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReactionToFireWindow {

	public JFrame frame;
	public Trooper reactingTrooper;
	public Trooper shooterTrooper; 
	public OpenTrooper openTrooper; 
	public GameWindow game; 
	private JLabel lblReactingTrooper;
	
	/**
	 * Create the application.
	 */
	public ReactionToFireWindow(Trooper shooterTrooper, Trooper reactingTrooper, OpenTrooper openTrooper, GameWindow game) {
		if(openTrooper == null)
			return; 
		
		this.game = game; 
		this.reactingTrooper = reactingTrooper;
		this.shooterTrooper = shooterTrooper; 
		this.openTrooper = openTrooper; 
		
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//openTrooper.reaction = null;
		
		frame = new JFrame();
		frame.setBounds(100, 100, 523, 114);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(62, 17, 1, 1);
		panel_1.setLayout(null);
		panel.add(panel_1);
		
		lblReactingTrooper = new JLabel("Reacting: <dynamic>:: <dynamic>, 0 <dynamic>");
		lblReactingTrooper.setBounds(10, 10, 304, 15);
		lblReactingTrooper.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblReactingTrooper);
		
		JButton button = new JButton("Duck");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
				//System.out.println("openTrooper: "+openTrooper.index);
				//System.out.println("reactingTrooper: "+reactingTrooper.number+" "+reactingTrooper.name);
				
				if(reactingTrooper.inCover && openTrooper.targetedFire != null) {
					
					// EAL -6 
					// One more shot can be fired
					openTrooper.targetedFire.EAL -= 6; 
					openTrooper.targetedFire.possibleShots = openTrooper.targetedFire.shotsTaken + 1;
					//openTrooper.windowOpenTrooper.lblPossibleShots.setText("Possible Shots: "+openTrooper.targetedFire.possibleShots);
					openTrooper.windowOpenTrooper.PCShots();
					openTrooper.windowOpenTrooper.PCFireGuiUpdates();
					frame.dispose();
				} else {
					
					game.conflictLog.addNewLine("This trooper is not in cover and cannot duck.");
					
				}
				
			}
		});
		button.setBounds(10, 36, 65, 23);
		panel.add(button);
		
		JButton button_1 = new JButton("Sprint");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(reactingTrooper.disabledLegs > 0) {
					game.conflictLog.addNewLine(reactingTrooper.number+" "+reactingTrooper.name+" cannot sprint on run to cover, disabled leg.");
					return; 
				}
				
				if(openTrooper.targetedFire != null) {
					if(!reactingTrooper.inBuilding(GameWindow.gameWindow))
						reactingTrooper.inCover = false;
					//System.out.println("Old Speed: "+openTrooper.targetedFire.speedALM);
					openTrooper.targetedFire.EAL -= openTrooper.targetedFire.speedALM;
					openTrooper.targetedFire.ALMSum -= openTrooper.targetedFire.speedALM;
					openTrooper.targetedFire.EAL -= openTrooper.targetedFire.sizeALM;
					openTrooper.targetedFire.sizeALM = 7; 
					openTrooper.targetedFire.EAL += openTrooper.targetedFire.sizeALM;
					int speedALM = openTrooper.targetedFire.findSpeedALM("Rush", openTrooper.targetedFire.rangeInPCHexes, 
							reactingTrooper.number, reactingTrooper.returnTrooperUnit(game), reactingTrooper);
					//System.out.println("New Speed: "+speedALM);
					openTrooper.targetedFire.EAL += speedALM;
					openTrooper.targetedFire.ALMSum += speedALM;

					int rangeInPCHexes = openTrooper.targetedFire.rangeInPCHexes;
					
					/*openTrooper.targetedFire.EAL -= openTrooper.targetedFire.aim(shooterTrooper, spentAimTime - 1, wep);
					openTrooper.targetedFire.EAL -= openTrooper.targetedFire.weaponConditionsMod(wep, spentAimTime - 1);
					openTrooper.targetedFire.ALMSum -= openTrooper.targetedFire.aim(shooterTrooper, spentAimTime - 1, wep);
					openTrooper.targetedFire.ALMSum -= openTrooper.targetedFire.weaponConditionsMod(wep, spentAimTime - 1);*/
					
					if(rangeInPCHexes <= 20) {
						
						
						openTrooper.targetedFire.maxAim = 2; 
						
						if(openTrooper.targetedFire.spentAimTime > 2) {
							Weapons wep = new Weapons(); 
							wep = wep.findWeapon(shooterTrooper.wep);
							
							
							openTrooper.targetedFire.EAL -= openTrooper.targetedFire.aim(shooterTrooper, openTrooper.targetedFire.spentAimTime - 1, wep);
							openTrooper.targetedFire.EAL -= openTrooper.targetedFire.weaponConditionsMod(wep, openTrooper.targetedFire.spentAimTime - 1);
							openTrooper.targetedFire.EAL += openTrooper.targetedFire.weaponConditionsMod(wep, 2);
							openTrooper.targetedFire.EAL += openTrooper.targetedFire.aim(shooterTrooper, 2, wep);
							openTrooper.targetedFire.ALMSum -= openTrooper.targetedFire.aim(shooterTrooper, openTrooper.targetedFire.spentAimTime - 1, wep);
							openTrooper.targetedFire.ALMSum -= openTrooper.targetedFire.weaponConditionsMod(wep, openTrooper.targetedFire.spentAimTime - 1);
							openTrooper.targetedFire.ALMSum += openTrooper.targetedFire.weaponConditionsMod(wep, 2);
							openTrooper.targetedFire.ALMSum += openTrooper.targetedFire.aim(shooterTrooper, 2, wep);
							openTrooper.targetedFire.spentAimTime = 2; 
						}
						
						
						
					} 
					
					
					/*openTrooper.targetedFire.EAL += openTrooper.targetedFire.weaponConditionsMod(wep, spentAimTime);
					openTrooper.targetedFire.EAL += openTrooper.targetedFire.aim(shooterTrooper, spentAimTime, wep);
					
					openTrooper.targetedFire.ALMSum += openTrooper.targetedFire.weaponConditionsMod(wep, spentAimTime);
					openTrooper.targetedFire.ALMSum += openTrooper.targetedFire.aim(shooterTrooper, spentAimTime, wep);*/
					
					
					openTrooper.windowOpenTrooper.PCShots();
					openTrooper.windowOpenTrooper.PCFireGuiUpdates();
					frame.dispose();
				} else {
					//System.out.println("Targeted Fire = Null");
				}
				
				
				
				
				
			}
		});
		button_1.setBounds(294, 36, 78, 23);
		panel.add(button_1);
		
		JButton button_3 = new JButton("Hunker Down");
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(reactingTrooper.inCover && openTrooper.targetedFire != null) {
					
					//System.out.println("Hunkering Down");
					reactingTrooper.hunkerDown(game); 
					openTrooper.windowOpenTrooper.refreshSpotted(shooterTrooper);
					openTrooper.windowOpenTrooper.refreshTargets();
					
					frame.dispose();
				} else {
					
					game.conflictLog.addNewLine("This trooper is not in cover and cannot hunker down.");
					
				}
				
			}
		});
		button_3.setBounds(85, 36, 115, 23);
		panel.add(button_3);
		button_3.setActionCommand("Cancel");
		
		JButton btnProne = new JButton("Prone");
		btnProne.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(reactingTrooper.stance == "Prone") {
					game.conflictLog.addNewLine("This trooper is already prone.");
					return;
				}
				
				openTrooper.targetedFire.EAL -= openTrooper.targetedFire.sizeALM;
				//openTrooper.targetedFire.EAL += openTrooper.targetedFire.findSizeALM("Prone", reactingTrooper.PCSize);
				openTrooper.windowOpenTrooper.PCShots();
				openTrooper.windowOpenTrooper.PCFireGuiUpdates();
				frame.dispose();
			}
		});
		btnProne.setBounds(210, 36, 74, 23);
		panel.add(btnProne);
		
		JButton btnRunToCover = new JButton("Run to Cover");
		btnRunToCover.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(reactingTrooper.disabledLegs > 0) {
					game.conflictLog.addNewLine(reactingTrooper.number+" "+reactingTrooper.name+" cannot sprint on run to cover, disabled leg.");
					return; 
				}
				
				Hex hex = null; 
				
				hex = game.findHex(reactingTrooper.returnTrooperUnit(game).X, reactingTrooper.returnTrooperUnit(game).Y);
				
				if(hex.coverPositions > hex.usedPositions) {
					hex.usedPositions++;
					reactingTrooper.returnTrooperUnit(game).individualsInCover++; 
					reactingTrooper.inCover = true;
				}
					
				if(hex == null || !reactingTrooper.inCover) {
					game.conflictLog.addNewLine("Trooper can't find cover");
					return; 
				}
				
				//System.out.println("Old Speed: "+openTrooper.targetedFire.speedALM);
				openTrooper.targetedFire.EAL -= openTrooper.targetedFire.speedALM;
				int speedALM = openTrooper.targetedFire.findSpeedALM("Rush", openTrooper.targetedFire.rangeInPCHexes, 
						reactingTrooper.number, reactingTrooper.returnTrooperUnit(game), reactingTrooper);
				//System.out.println("New Speed: "+speedALM);
				openTrooper.targetedFire.EAL += speedALM;
			
				
				int spentAimTime = openTrooper.targetedFire.spentAimTime;
				int rangeInPCHexes = openTrooper.targetedFire.rangeInPCHexes;
				
				
				if(rangeInPCHexes <= 20) {
					
					if(spentAimTime != 1)
						spentAimTime = 2;
					openTrooper.targetedFire.maxAim = 2; 
					openTrooper.targetedFire.spentAimTime = 2; 
				} 
				
	
				openTrooper.targetedFire.possibleShots = openTrooper.targetedFire.shotsTaken + 1;
				
				
				
				openTrooper.windowOpenTrooper.PCShots();
				openTrooper.windowOpenTrooper.PCFireGuiUpdates();
				frame.dispose();
				
				
			}
		});
		btnRunToCover.setActionCommand("Cancel");
		btnRunToCover.setBounds(382, 36, 115, 23);
		panel.add(btnRunToCover);
		
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;

		// Set the new frame location
		frame.setLocation(x, y);
		frame.setVisible(true);
		
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
