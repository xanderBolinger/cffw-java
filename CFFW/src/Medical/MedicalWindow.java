package Medical;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import Conflict.GameWindow;
import CorditeExpansionDamage.Injury;
import Injuries.Injuries;
import Items.Weapons;
import JUnitTests.AllTests;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.PCUtility;
import UtilityClasses.SwingUtility;

import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MedicalWindow extends JFrame {

	private JPanel contentPane;

	ArrayList<Unit> units = new ArrayList<>();
	ArrayList<Trooper> roster = new ArrayList<>();
	ArrayList<Trooper> troopers = new ArrayList<>();
	private JList listTroopers;
	private JList listInjuries;
	private JLabel lblPD;
	private JLabel labelCTP;
	private JLabel labelRR;
	private JLabel labelTimePassed;
	private JLabel labelAlive;
	private JLabel labelConscious;
	private JLabel lblIncapTime;
	private JLabel lblAidType;
	private JComboBox comboBoxAidType;
	private JLabel lblHours;
	private JLabel lblMinutes;
	private JSpinner spinnerHours;
	private JSpinner spinnerMinutes;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JSpinner spinnerDays;
	private JLabel lblHtTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArrayList<Trooper> testTroopers = new ArrayList<>();
					testTroopers.add(new Trooper("Republic Commando", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Republic Commando", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Republic Commando", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Republic Commando", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Clone Rifleman", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Clone Rifleman", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Clone Squad Leader", "Clone Trooper Phase 1"));
					
					testTroopers.get(0).injuries.add(new Injuries(1000, "Neck", false, new Weapons().findWeapon("E5")));
					testTroopers.get(0).physicalDamage += 1000; 
					testTroopers.get(0).calculateInjury(null, null);
					testTroopers.get(1).injuries.add(new Injuries(24000, "Skull", false, new Weapons().findWeapon("E5")));
					testTroopers.get(1).physicalDamage += 24000; 
					testTroopers.get(1).calculateInjury(null, null);
					testTroopers.get(2).injuries.add(new Injuries(30, "Shoulder", false, new Weapons().findWeapon("E5")));
					testTroopers.get(2).physicalDamage += 30; 
					testTroopers.get(2).calculateInjury(null, null);
					testTroopers.get(3).injuries.add(new Injuries(300, "Shoulder", false, new Weapons().findWeapon("E5")));
					testTroopers.get(3).physicalDamage += 300; 
					testTroopers.get(3).calculateInjury(null, null);
					
					testTroopers.get(4).injuries.add(new Injuries(30, "Shoulder", false, new Weapons().findWeapon("E5")));
					testTroopers.get(4).physicalDamage += 30; 
					testTroopers.get(4).calculateInjury(null, null);
					testTroopers.get(4).injuries.add(new Injuries(30, "Shoulder", false, new Weapons().findWeapon("E5")));
					testTroopers.get(4).physicalDamage += 30; 
					testTroopers.get(4).calculateInjury(null, null);
					testTroopers.get(4).injuries.add(new Injuries(30, "Shoulder", false, new Weapons().findWeapon("E5")));
					testTroopers.get(4).physicalDamage += 30; 
					testTroopers.get(4).calculateInjury(null, null);
					
					
					new MedicalWindow(new ArrayList<Unit>(), testTroopers);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MedicalWindow(ArrayList<Unit> units, ArrayList<Trooper> roster) {
		this.units = units;
		this.roster = roster;
		initWindow();
		refreshWindow();
	}

	public void initWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1230, 665);
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

		JLabel lblArmorPage_1_2 = new JLabel("Troopers");
		lblArmorPage_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblArmorPage_1_2.setForeground(Color.BLACK);
		lblArmorPage_1_2.setFont(new Font("Calibri", Font.BOLD, 16));
		lblArmorPage_1_2.setBounds(10, 11, 518, 16);
		contentPane.add(lblArmorPage_1_2);

		JLabel lblArmorPage_1 = new JLabel("Injuries");
		lblArmorPage_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblArmorPage_1.setForeground(Color.BLACK);
		lblArmorPage_1.setFont(new Font("Calibri", Font.BOLD, 16));
		lblArmorPage_1.setBounds(538, 11, 433, 16);
		contentPane.add(lblArmorPage_1);

		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(538, 38, 433, 574);
		contentPane.add(scrollPane_8);

		listInjuries = new JList();
		listInjuries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(getSelectedTrooper() == null || listInjuries.getSelectedIndex() < 0)
					return; 
					
				getSelectedTrooper().removeInjury(listInjuries.getSelectedIndex(), null,
						null);
				
			}
		});
		scrollPane_8.setViewportView(listInjuries);

		JScrollPane scrollPane_8_2 = new JScrollPane();
		scrollPane_8_2.setBounds(10, 38, 518, 574);
		contentPane.add(scrollPane_8_2);

		listTroopers = new JList();
		listTroopers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listTroopers.getSelectedIndex() < 0) 
					return; 
				
				refreshWindow();
				
				if(getSelectedTrooper() != null) {
					
					if(getSelectedTrooper().recivingFirstAid) {
						comboBoxAidType.setSelectedIndex(1);
					} else if(getSelectedTrooper().aidStation) {
						comboBoxAidType.setSelectedIndex(2);
					} else if(getSelectedTrooper().fieldHospital) {
						comboBoxAidType.setSelectedIndex(3);
					} else if(getSelectedTrooper().traumaCenter) {
						comboBoxAidType.setSelectedIndex(4);
					} else {
						comboBoxAidType.setSelectedIndex(0);
					}
					
				}
				
			}
		});
		scrollPane_8_2.setViewportView(listTroopers);

		labelConscious = new JLabel("Conscious: false");
		labelConscious.setForeground(Color.BLACK);
		labelConscious.setFont(new Font("Calibri", Font.PLAIN, 15));
		labelConscious.setBounds(981, 48, 112, 31);
		contentPane.add(labelConscious);

		labelAlive = new JLabel("Alive: false");
		labelAlive.setForeground(Color.BLACK);
		labelAlive.setFont(new Font("Calibri", Font.PLAIN, 15));
		labelAlive.setBounds(981, 83, 112, 31);
		contentPane.add(labelAlive);

		labelCTP = new JLabel("CTP(Phases): 0");
		labelCTP.setForeground(Color.BLACK);
		labelCTP.setFont(new Font("Calibri", Font.PLAIN, 15));
		labelCTP.setDoubleBuffered(true);
		labelCTP.setBounds(981, 155, 112, 31);
		contentPane.add(labelCTP);

		labelRR = new JLabel("RR: 0");
		labelRR.setForeground(Color.BLACK);
		labelRR.setFont(new Font("Calibri", Font.PLAIN, 15));
		labelRR.setDoubleBuffered(true);
		labelRR.setBounds(981, 184, 112, 31);
		contentPane.add(labelRR);

		labelTimePassed = new JLabel("TP(Phases): 0");
		labelTimePassed.setForeground(Color.BLACK);
		labelTimePassed.setFont(new Font("Calibri", Font.PLAIN, 15));
		labelTimePassed.setDoubleBuffered(true);
		labelTimePassed.setBounds(981, 209, 112, 31);
		contentPane.add(labelTimePassed);

		lblPD = new JLabel("PD: ");
		lblPD.setForeground(Color.BLACK);
		lblPD.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblPD.setDoubleBuffered(true);
		lblPD.setBounds(981, 122, 112, 31);
		contentPane.add(lblPD);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setForeground(Color.BLACK);
		lblStatus.setFont(new Font("Calibri", Font.BOLD, 16));
		lblStatus.setBounds(981, 11, 105, 31);
		contentPane.add(lblStatus);

		JButton btnNewButton = new JButton("Toggle");
		btnNewButton.setBounds(1103, 50, 89, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Toggle");
		btnNewButton_1.setBounds(1103, 85, 89, 23);
		contentPane.add(btnNewButton_1);
		
		lblIncapTime = new JLabel("Incap Time: 0");
		lblIncapTime.setForeground(Color.BLACK);
		lblIncapTime.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblIncapTime.setDoubleBuffered(true);
		lblIncapTime.setBounds(981, 251, 112, 31);
		contentPane.add(lblIncapTime);
		
		lblAidType = new JLabel("Aid Type");
		lblAidType.setForeground(Color.BLACK);
		lblAidType.setFont(new Font("Calibri", Font.BOLD, 16));
		lblAidType.setBounds(981, 335, 105, 31);
		contentPane.add(lblAidType);
		
		comboBoxAidType = new JComboBox();
		comboBoxAidType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBoxAidType.getSelectedIndex() < 0 || getSelectedTrooper() == null)
					return; 
				
				if(comboBoxAidType.getSelectedIndex() == 0) {
					getSelectedTrooper().recivingFirstAid = false; 
					getSelectedTrooper().aidStation = false; 
					getSelectedTrooper().fieldHospital = false; 
					getSelectedTrooper().traumaCenter = false;
				} else if(comboBoxAidType.getSelectedIndex() == 1) {
					getSelectedTrooper().recivingFirstAid = true; 
					getSelectedTrooper().aidStation = false; 
					getSelectedTrooper().fieldHospital = false; 
					getSelectedTrooper().traumaCenter = false;
				} else if(comboBoxAidType.getSelectedIndex() == 2) {
					getSelectedTrooper().recivingFirstAid = false; 
					getSelectedTrooper().aidStation = true; 
					getSelectedTrooper().fieldHospital = false; 
					getSelectedTrooper().traumaCenter = false;
				} else if(comboBoxAidType.getSelectedIndex() == 3) {
					getSelectedTrooper().recivingFirstAid = false; 
					getSelectedTrooper().aidStation = false; 
					getSelectedTrooper().fieldHospital = true; 
					getSelectedTrooper().traumaCenter = false;
				} else if(comboBoxAidType.getSelectedIndex() == 4) {
					getSelectedTrooper().recivingFirstAid = false; 
					getSelectedTrooper().aidStation = false; 
					getSelectedTrooper().fieldHospital = false; 
					getSelectedTrooper().traumaCenter = true;
				}
				
				getSelectedTrooper().calculateInjury(null, null);
				
				refreshInjuries();
				
			}
		});
		comboBoxAidType.setModel(new DefaultComboBoxModel(new String[] {"No Aid", "First Aid", "Aid Station", "Field Hospital", "Trauma Center"}));
		comboBoxAidType.setBounds(981, 377, 112, 22);
		contentPane.add(comboBoxAidType);
		
		JLabel lblAddTime = new JLabel("Add Time (For All Troopers*)");
		lblAddTime.setForeground(Color.BLACK);
		lblAddTime.setFont(new Font("Calibri", Font.BOLD, 16));
		lblAddTime.setBounds(981, 410, 223, 31);
		contentPane.add(lblAddTime);
		
		spinnerDays = new JSpinner();
		spinnerDays.setBounds(981, 463, 60, 20);
		contentPane.add(spinnerDays);
		
		JLabel lblNewLabel = new JLabel("Days");
		lblNewLabel.setBounds(981, 438, 46, 14);
		contentPane.add(lblNewLabel);
		
		lblHours = new JLabel("Hours");
		lblHours.setBounds(981, 494, 46, 14);
		contentPane.add(lblHours);
		
		lblMinutes = new JLabel("Minutes");
		lblMinutes.setBounds(981, 543, 46, 14);
		contentPane.add(lblMinutes);
		
		spinnerHours = new JSpinner();
		spinnerHours.setBounds(981, 519, 60, 20);
		contentPane.add(spinnerHours);
		
		spinnerMinutes = new JSpinner();
		spinnerMinutes.setBounds(981, 568, 60, 20);
		contentPane.add(spinnerMinutes);
		
		btnNewButton_2 = new JButton("Add");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

						@Override
						protected Void doInBackground() throws Exception {
							
							for(Trooper trooper : troopers) {
								for(int i = 0; i < ((int) spinnerDays.getValue() * 60 * 24); i ++)
									trooper.advanceTime(null, null);
							}

							return null;
						}

						@Override
						protected void done() {
							//System.out.println("Finished");
							refreshWindow();

						}

					};

					worker.execute();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				
				
				
				
			}
		});
		btnNewButton_2.setBounds(1051, 462, 89, 23);
		contentPane.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("Add");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

						@Override
						protected Void doInBackground() throws Exception {
							
							for(Trooper trooper : troopers) {
								for(int i = 0; i < ((int) spinnerHours.getValue() * 60); i ++)
									trooper.advanceTime(null, null);
							}

							return null;
						}

						@Override
						protected void done() {

							refreshWindow();

						}

					};

					worker.execute();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				
			}
		});
		btnNewButton_3.setBounds(1051, 518, 89, 23);
		contentPane.add(btnNewButton_3);
		
		btnNewButton_4 = new JButton("Add");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

						@Override
						protected Void doInBackground() throws Exception {
							
							for(Trooper trooper : troopers) {
								for(int i = 0; i < ((int) spinnerMinutes.getValue()); i ++)
									trooper.advanceTime(null, null);
							}
							
							return null;
						}

						@Override
						protected void done() {

							refreshWindow();

						}

					};

					worker.execute();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
		btnNewButton_4.setBounds(1051, 567, 89, 23);
		contentPane.add(btnNewButton_4);
		
		lblHtTime = new JLabel("HT Time: 0");
		lblHtTime.setForeground(Color.BLACK);
		lblHtTime.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblHtTime.setDoubleBuffered(true);
		lblHtTime.setBounds(981, 293, 112, 31);
		contentPane.add(lblHtTime);
		setVisible(true);
	}

	public void refreshWindow() {
		refreshTroopers();
		if(getSelectedTrooper() != null)
			refreshInjuries();
	}

	public void refreshTroopers() {

		int selectedIndex = listTroopers.getSelectedIndex();
		
		ArrayList<String> trooperEntries = new ArrayList<>();
		
		troopers.clear();
		
		for(Unit unit : units) {
			for(Trooper individual : unit.individuals) {
				troopers.add(individual);
				trooperEntries.add(unit.callsign +": "+individual.number+" "+individual.name+(individual.physicalDamage > 0 ? ", Stabalized: "+individual.recoveryMade+", PD: "+individual.physicalDamage
					+", CTP: "+PCUtility.convertTime(individual.criticalTime)+", RR: "+individual.recoveryRoll+", TP: "+PCUtility.convertTime(individual.timePassed)
					+", Alive: "+individual.alive+", Conscious: "+individual.conscious : ""));
			}
		}
		
		for(Trooper trooper : roster) {
			troopers.add(trooper);
			trooperEntries.add(trooper.name+(trooper.physicalDamage > 0 ? ", Stabalized: "+trooper.recoveryMade+", PD: "+trooper.physicalDamage
					+", CTP: "+PCUtility.convertTime(trooper.criticalTime)+", RR: "+trooper.recoveryRoll+", TP: "+
					PCUtility.convertTime(trooper.timePassed)+", Alive: "+trooper.alive+", Conscious: "+trooper.conscious : ""));
		}
		
		SwingUtility.setList(listTroopers, trooperEntries);
		
		listTroopers.setSelectedIndex(selectedIndex);

	}

	public Trooper getSelectedTrooper() {
		if(listTroopers.getSelectedIndex() < 0) {
			return null; 
		}
		
		return troopers.get(listTroopers.getSelectedIndex());
	}
	
	public void refreshInjuries() {
		ArrayList<String> injuries = new ArrayList<>();
		
		for(Injuries injury : getSelectedTrooper().injuries)
			injuries.add(injury.toString());

		SwingUtility.setList(listInjuries, injuries);
		
		labelConscious.setText("Conscious: " + getSelectedTrooper().conscious);
		lblIncapTime.setText("KO Time: " + PCUtility.convertTime(getSelectedTrooper().incapacitationTime));
		labelAlive.setText("Alive: " + getSelectedTrooper().alive);
		labelRR.setText("RR: " + getSelectedTrooper().recoveryRoll);
		labelCTP.setText("CTP: " + PCUtility.convertTime(getSelectedTrooper().criticalTime));
		labelTimePassed.setText("TP: " + PCUtility.convertTime(getSelectedTrooper().timePassed));
		lblPD.setText("PD Total: " + getSelectedTrooper().physicalDamage);
	}
}
