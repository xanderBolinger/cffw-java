import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextPane;
import javax.swing.ListModel;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import java.awt.TextArea;

public class Window implements Serializable {

	public JFrame frame;
	private JButton btnNewCharacter;
	private JTextField textFieldName;

	public Character character;
	public ArrayList<Character> characters = new ArrayList();

	public Skill tempSkill = new Skill();

	private JTextField textFieldSTR;
	private JTextField textFieldINT;
	private JTextField textFieldSOC;
	private JTextField textFieldWIL;
	private JTextField textFieldPER;
	private JTextField textFieldHTL;
	private JTextField textFieldAGL;
	private JButton btnAdvanceStr;
	private JButton btnAdvanceInt;
	private JButton btnAdvanceSoc;
	private JButton btnAdvanceWil;
	private JButton btnAdvancePer;
	private JButton btnAdvanceHtl;
	private JButton btnAdvanceAgl;
	private JButton btnSaveChanges;
	private JTextField textFieldCharacterPoints;
	private JTextField textFieldSpentCharacterPoints;
	private JList listCharacters;
	private JButton btnDeleteCharacter;
	private JButton btnSaveClose;
	private JLabel lblCharacterAbilities;
	private JScrollPane scrollPane_1;
	private JTextField textFieldAbilityName;
	private JTextField textFieldSkillSupport;
	private JTextField textFieldTrainingValue;
	private JButton btnAddSkillSupport;
	private JButton btnRemoveSkillSupport;
	private JButton btnDeleteAbility;
	private JButton btnRemoveAbility;
	private JButton btnAddAbility;
	private JList listActiveAbilities;
	private JList listUnactiveAbilities;
	private JSpinner spinnerAbilityRank;
	private JList listSkillSupport;
	private JList listTrainingValues;
	private JLabel lblSkills;
	private JSpinner spinnerValue;
	private JButton btnSaveChanges_1;
	private JSpinner spinnerTV;
	private JSpinner spinnerRanks;
	private JSpinner spinnerAdvancement;
	private JPanel panelSkills;
	private JScrollPane scrollPane_5;
	private JList listBasicSkills;
	private JScrollPane scrollPane_6;
	private JScrollPane scrollPane_7;
	private JList listTrainedSkills;
	private JList listExpertSkills;
	private JLabel lblBasic;
	private JLabel lblTrained;
	private JLabel lblExpert;
	private JButton btnSkillRankUp;
	private JButton btnRankUp;
	private JButton btnNewButton;
	private JSpinner spinnerEncum;
	private JTextField textFieldWIS;
	private JPanel panel;
	private TextArea textAreaCharacterNotes;
	private JScrollPane scrollPane_9;
	private JScrollPane scrollPane_10;
	private TextArea textPaneSpecial;
	private TextArea textPaneMastery;

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public Window() throws FileNotFoundException, ClassNotFoundException, IOException {
		Character character = new Character();
		this.character = character;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	private void initialize() throws FileNotFoundException, ClassNotFoundException, IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 909, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 870, 750);
		frame.getContentPane().add(tabbedPane);

		JPanel panelCharacter = new JPanel();
		panelCharacter.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Character", null, panelCharacter, null);
		panelCharacter.setLayout(null);

		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(35, 37, 60, 30);
		panelCharacter.add(lblNewLabel);

		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Calibri", Font.PLAIN, 12));
		textFieldName.setBounds(81, 41, 125, 20);
		panelCharacter.add(textFieldName);
		textFieldName.setColumns(10);

		btnNewCharacter = new JButton("New Character");
		btnNewCharacter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				character = new Character();
				character.setBasicStats();
				character.calculateAttributes();
				character.calculateSkills();
				setFields();
			}
		});
		btnNewCharacter.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnNewCharacter.setBounds(216, 40, 125, 23);
		panelCharacter.add(btnNewCharacter);

		JLabel lblNewLabel_1 = new JLabel("STR:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(35, 81, 46, 14);
		panelCharacter.add(lblNewLabel_1);

		textFieldSTR = new JTextField();
		textFieldSTR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				character.recalculateSkills();
				setSkills();
			}
		});
		textFieldSTR.setFont(new Font("Calibri", Font.PLAIN, 12));
		textFieldSTR.setColumns(10);
		textFieldSTR.setBounds(81, 78, 60, 20);
		panelCharacter.add(textFieldSTR);

		textFieldINT = new JTextField();
		textFieldINT.setFont(new Font("Calibri", Font.PLAIN, 12));
		textFieldINT.setColumns(10);
		textFieldINT.setBounds(81, 109, 60, 20);
		panelCharacter.add(textFieldINT);

		textFieldSOC = new JTextField();
		textFieldSOC.setFont(new Font("Calibri", Font.PLAIN, 12));
		textFieldSOC.setColumns(10);
		textFieldSOC.setBounds(81, 170, 60, 20);
		panelCharacter.add(textFieldSOC);

		btnSaveChanges = new JButton("Add");
		btnSaveChanges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				saveChanges();

				boolean found = false;

				for (int i = 0; i < characters.size(); i++) {

					if (characters.get(i).name.equals(character.name)) {
						found = true;
						characters.set(i, character);
					}

				}

				if (!found) {
					characters.add(character);
				}

				setCharacterList();
			}
		});
		btnSaveChanges.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnSaveChanges.setBounds(351, 40, 66, 23);
		panelCharacter.add(btnSaveChanges);

		textFieldWIL = new JTextField();
		textFieldWIL.setFont(new Font("Calibri", Font.PLAIN, 12));
		textFieldWIL.setColumns(10);
		textFieldWIL.setBounds(81, 201, 60, 20);
		panelCharacter.add(textFieldWIL);

		textFieldPER = new JTextField();
		textFieldPER.setFont(new Font("Calibri", Font.PLAIN, 12));
		textFieldPER.setColumns(10);
		textFieldPER.setBounds(81, 232, 60, 20);
		panelCharacter.add(textFieldPER);

		textFieldHTL = new JTextField();
		textFieldHTL.setFont(new Font("Calibri", Font.PLAIN, 12));
		textFieldHTL.setColumns(10);
		textFieldHTL.setBounds(81, 263, 60, 20);
		panelCharacter.add(textFieldHTL);

		textFieldAGL = new JTextField();
		textFieldAGL.setFont(new Font("Calibri", Font.PLAIN, 12));
		textFieldAGL.setColumns(10);
		textFieldAGL.setBounds(81, 294, 60, 20);
		panelCharacter.add(textFieldAGL);

		JLabel lblInt = new JLabel("INT:");
		lblInt.setForeground(Color.WHITE);
		lblInt.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblInt.setBounds(35, 111, 46, 14);
		panelCharacter.add(lblInt);

		JLabel lblSoc = new JLabel("SOC:");
		lblSoc.setForeground(Color.WHITE);
		lblSoc.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblSoc.setBounds(35, 172, 46, 14);
		panelCharacter.add(lblSoc);

		JLabel lblWil = new JLabel("WIL:");
		lblWil.setForeground(Color.WHITE);
		lblWil.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblWil.setBounds(35, 203, 46, 14);
		panelCharacter.add(lblWil);

		JLabel lblPer = new JLabel("PER:");
		lblPer.setForeground(Color.WHITE);
		lblPer.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblPer.setBounds(35, 234, 46, 14);
		panelCharacter.add(lblPer);

		JLabel lblHtl = new JLabel("HTL:");
		lblHtl.setForeground(Color.WHITE);
		lblHtl.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblHtl.setBounds(35, 265, 46, 14);
		panelCharacter.add(lblHtl);

		btnDeleteCharacter = new JButton("Delete");
		btnDeleteCharacter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// Removes character from front end
				if (listCharacters.getSelectedIndex() > -1) {
					characters.remove(listCharacters.getSelectedIndex());
				}

				// Deletes save files
				int fileNum = 1;
				boolean multipleCompanies = true;
				while (multipleCompanies) {
					File file = new File("Character_" + fileNum);
					if (file.canRead() && file.isFile()) {
						fileNum++;
						file.delete();
					} else {
						multipleCompanies = false;
					}
				}

				// Remakes save files
				Save save = new Save(characters);
				try {
					save.fileOut();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// Sets up new characters
				character = new Character();
				character.setBasicStats();
				character.calculateAttributes();
				setFields();
				setCharacterList();
			}
		});
		btnDeleteCharacter.setBounds(427, 40, 89, 23);
		panelCharacter.add(btnDeleteCharacter);

		JLabel lblAgl = new JLabel("AGL:");
		lblAgl.setForeground(Color.WHITE);
		lblAgl.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblAgl.setBounds(35, 296, 46, 14);
		panelCharacter.add(lblAgl);

		btnAdvanceStr = new JButton("Rank Up");
		btnAdvanceStr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				character.recalculateSkills();
				setSkills();
			}
		});
		btnAdvanceStr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				character.str += 3;
				character.spentCharacterPoints += 1;
				setFields();
			}
		});
		btnAdvanceStr.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnAdvanceStr.setBounds(151, 77, 89, 23);
		panelCharacter.add(btnAdvanceStr);

		btnAdvanceInt = new JButton("Rank Up");
		btnAdvanceInt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				character.recalculateSkills();
				setSkills();
			}
		});
		btnAdvanceInt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				character.wit += 3;
				character.spentCharacterPoints += 1;
				setFields();
			}
		});
		btnAdvanceInt.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnAdvanceInt.setBounds(151, 107, 89, 23);
		panelCharacter.add(btnAdvanceInt);

		btnAdvanceSoc = new JButton("Rank Up");
		btnAdvanceSoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				character.recalculateSkills();
				setSkills();
			}
		});
		btnAdvanceSoc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				character.soc += 3;
				character.spentCharacterPoints += 1;
				setFields();
			}
		});
		btnAdvanceSoc.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnAdvanceSoc.setBounds(151, 169, 89, 23);
		panelCharacter.add(btnAdvanceSoc);

		btnAdvanceWil = new JButton("Rank Up");
		btnAdvanceWil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				character.recalculateSkills();
				setSkills();
			}
		});
		btnAdvanceWil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				character.wil += 3;
				character.spentCharacterPoints += 1;
				setFields();
			}
		});
		btnAdvanceWil.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnAdvanceWil.setBounds(151, 199, 89, 23);
		panelCharacter.add(btnAdvanceWil);

		btnAdvancePer = new JButton("Rank Up");
		btnAdvancePer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				character.recalculateSkills();
				setSkills();
			}
		});
		btnAdvancePer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				character.per += 3;
				character.spentCharacterPoints += 1;
				setFields();
			}
		});
		btnAdvancePer.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnAdvancePer.setBounds(151, 230, 89, 23);
		panelCharacter.add(btnAdvancePer);

		btnAdvanceHtl = new JButton("Rank Up");
		btnAdvanceHtl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				character.recalculateSkills();
				setSkills();
			}
		});
		btnAdvanceHtl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				character.htl += 3;
				character.spentCharacterPoints += 1;
				setFields();
			}
		});
		btnAdvanceHtl.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnAdvanceHtl.setBounds(151, 261, 89, 23);
		panelCharacter.add(btnAdvanceHtl);

		btnAdvanceAgl = new JButton("Rank Up");
		btnAdvanceAgl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				character.recalculateSkills();
				setSkills();
			}
		});
		btnAdvanceAgl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				character.agl += 3;
				character.spentCharacterPoints += 1;
				setFields();
			}
		});
		btnAdvanceAgl.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnAdvanceAgl.setBounds(151, 292, 89, 23);
		panelCharacter.add(btnAdvanceAgl);

		JLabel lblCharacterPoints = new JLabel("Character Points");
		lblCharacterPoints.setForeground(Color.WHITE);
		lblCharacterPoints.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblCharacterPoints.setBounds(262, 74, 144, 30);
		panelCharacter.add(lblCharacterPoints);

		JLabel lblSpentCharacterPoints = new JLabel("Spent Character Points");
		lblSpentCharacterPoints.setForeground(Color.WHITE);
		lblSpentCharacterPoints.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblSpentCharacterPoints.setBounds(262, 111, 144, 30);
		panelCharacter.add(lblSpentCharacterPoints);

		textFieldCharacterPoints = new JTextField();
		textFieldCharacterPoints.setFont(new Font("Calibri", Font.PLAIN, 12));
		textFieldCharacterPoints.setColumns(10);
		textFieldCharacterPoints.setBounds(416, 77, 60, 20);
		panelCharacter.add(textFieldCharacterPoints);

		textFieldSpentCharacterPoints = new JTextField();
		textFieldSpentCharacterPoints.setFont(new Font("Calibri", Font.PLAIN, 12));
		textFieldSpentCharacterPoints.setColumns(10);
		textFieldSpentCharacterPoints.setBounds(416, 115, 60, 20);
		panelCharacter.add(textFieldSpentCharacterPoints);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(711, 40, 144, 244);
		panelCharacter.add(scrollPane);

		listCharacters = new JList();
		listCharacters.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				character = characters.get(listCharacters.getSelectedIndex());
				character.recalculateSkills();
				setFields();

			}
		});
		listCharacters.setFont(new Font("Calibri", Font.PLAIN, 12));
		scrollPane.setViewportView(listCharacters);

		btnSaveClose = new JButton("Save & Close");
		btnSaveClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// characters.set(characters.indexOf(character), character);

				// Writes save files
				Save save = new Save(characters);

				try {
					save.fileOut();
					frame.dispose();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSaveClose.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnSaveClose.setBounds(528, 40, 125, 23);
		panelCharacter.add(btnSaveClose);

		btnNewButton = new JButton("Export");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Character Points: " + character.characterPointTotal);
				System.out.println("Spent Character Points: " + character.spentCharacterPoints);
				System.out.println("");
				System.out.println("");
				System.out.println(character.name);
				System.out.println("");
				System.out.println("STR: " + character.str + "%");
				System.out.println("INT: " + character.wit + "%");
				System.out.println("WIS: " + character.wis + "%");
				System.out.println("SOC: " + character.soc + "%");
				System.out.println("WIL: " + character.wil + "%");
				System.out.println("PER: " + character.per + "%");
				System.out.println("HTL: " + character.htl + "%");
				System.out.println("AGL: " + character.agl + "%");

				System.out.println("");
				System.out.println("Abilities:");

				for (int i = 0; i < character.abilities.size(); i++) {
					System.out.println("");
					System.out.println("");
					System.out.println(
							"-" + character.abilities.get(i).name + " (" + character.abilities.get(i).rank + ")");
					System.out.println("");
					System.out.print("Skill Support: ");
					for (int x = 0; x < character.abilities.get(i).skillSupport.size(); x++) {

						System.out.print("{" + character.abilities.get(i).skillSupport.get(x));
						System.out.print("%}, ");
					}

					System.out.println("");
					System.out.println("");
					System.out.print("Training Values: ");
					for (int x = 0; x < character.abilities.get(i).trainingValues.size(); x++) {

						System.out.print("{" + character.abilities.get(i).trainingValues.get(x));
						System.out.print("%}, ");
					}

					System.out.println("");
					System.out.println("");
					System.out.print("Special Feature(s): ");
					System.out.print(character.abilities.get(i).special);

					System.out.println("");
					System.out.println("");
					System.out.print("Mastery: ");
					System.out.print(character.abilities.get(i).mastery);

				}

				// Basic Skills
				System.out.println("");
				System.out.println("");
				System.out.println("Basic Skills:");
				for (int i = 0; i < character.basicSkills.size(); i++) {
					System.out.println(character.basicSkills.get(i).returnSkill());

				}

				// Trained Skills
				System.out.println("");
				System.out.println("Trained Skills:");
				for (int i = 0; i < character.trainedSkills.size(); i++) {
					System.out.println(character.trainedSkills.get(i).returnSkill());

				}

				// Expert Skills
				System.out.println("");
				System.out.println("Expert Skills:");
				for (int i = 0; i < character.expertSkills.size(); i++) {
					System.out.println(character.expertSkills.get(i).returnSkill());

				}

				System.out.println("");
				System.out.println("Stats:");
				System.out.println(character.exportStats((int) spinnerEncum.getValue()));
			}
		});
		btnNewButton.setBounds(528, 107, 125, 23);
		panelCharacter.add(btnNewButton);

		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {

				characters.set(characters.indexOf(character), character);

				// Writes save files
				Save save = new Save(characters);
				try {
					save.fileOut();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btnSave.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnSave.setBounds(528, 74, 125, 23);
		panelCharacter.add(btnSave);

		JLabel lblEncum = new JLabel("Encum: ");
		lblEncum.setForeground(Color.WHITE);
		lblEncum.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblEncum.setBounds(262, 142, 144, 30);
		panelCharacter.add(lblEncum);

		spinnerEncum = new JSpinner();
		spinnerEncum.setBounds(416, 146, 60, 20);
		panelCharacter.add(spinnerEncum);

		JLabel lblWis = new JLabel("WIS:");
		lblWis.setForeground(Color.WHITE);
		lblWis.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblWis.setBounds(35, 144, 46, 14);
		panelCharacter.add(lblWis);

		textFieldWIS = new JTextField();
		textFieldWIS.setFont(new Font("Calibri", Font.PLAIN, 12));
		textFieldWIS.setColumns(10);
		textFieldWIS.setBounds(81, 142, 60, 20);
		panelCharacter.add(textFieldWIS);

		JButton button = new JButton("Rank Up");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				character.recalculateSkills();
				setSkills();
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				character.wis += 3;
				character.spentCharacterPoints += 1;
				setFields();
			}
		});
		button.setFont(new Font("Calibri", Font.PLAIN, 12));
		button.setBounds(151, 140, 89, 23);
		panelCharacter.add(button);

		JPanel panelAbilities = new JPanel();
		panelAbilities.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Abilities", null, panelAbilities, null);
		panelAbilities.setLayout(null);

		lblCharacterAbilities = new JLabel("Character Abilities:");
		lblCharacterAbilities.setBounds(10, 11, 138, 25);
		lblCharacterAbilities.setForeground(Color.WHITE);
		lblCharacterAbilities.setFont(new Font("Calibri", Font.PLAIN, 12));
		panelAbilities.add(lblCharacterAbilities);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(711, 49, 128, 251);
		panelAbilities.add(scrollPane_1);

		listUnactiveAbilities = new JList();
		listUnactiveAbilities.setBackground(Color.DARK_GRAY);
		listUnactiveAbilities.setForeground(Color.WHITE);
		listUnactiveAbilities.setFont(new Font("Calibri", Font.PLAIN, 12));
		scrollPane_1.setViewportView(listUnactiveAbilities);

		btnDeleteAbility = new JButton("Delete");
		btnDeleteAbility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (listActiveAbilities.getSelectedIndex() > -1) {

					character.abilities.remove(listActiveAbilities.getSelectedIndex());

					DefaultListModel abilityList = new DefaultListModel();

					for (int i = 0; i < character.abilities.size(); i++) {
						abilityList.addElement(character.abilities.get(i));
					}

					listActiveAbilities.setModel(abilityList);

				}

			}
		});
		btnDeleteAbility.setBounds(458, 49, 89, 23);
		panelAbilities.add(btnDeleteAbility);

		btnAddAbility = new JButton("Add");
		btnAddAbility.setBounds(750, 360, 89, 23);
		panelAbilities.add(btnAddAbility);

		JButton btnSaveAbility = new JButton("Save/Add New");
		btnSaveAbility.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// public Ability(String name, String special, String mastery, int rank,
				// ArrayList<String> skillSupport, ArrayList<String> trainingValues)

				ArrayList<String> supportedSkills = new ArrayList<String>();
				ArrayList<String> trainingValues = new ArrayList<String>();

				// Adds supported skills to an array
				for (int i = 0; i < listSkillSupport.getModel().getSize(); i++) {
					supportedSkills.add(listSkillSupport.getModel().getElementAt(i).toString());
				}

				// Adds supported training values to the array
				for (int i = 0; i < listTrainingValues.getModel().getSize(); i++) {
					trainingValues.add(listTrainingValues.getModel().getElementAt(i).toString());
				}

				// Instantiates the ability from text fields
				Ability ability = new Ability(textFieldAbilityName.getText(), textPaneSpecial.getText(),
						textPaneMastery.getText(), (Integer) spinnerAbilityRank.getValue(), supportedSkills,
						trainingValues);
				// Sets training values and skill support equal arrays

				boolean found = false;

				for (int i = 0; i < character.abilities.size(); i++) {

					if (character.abilities.get(i).name.equals(ability.name)) {
						found = true;
						character.abilities.set(i, ability);
					}

				}

				if (!found) {
					ability.rank = 1;
					character.spentCharacterPoints++;
					character.abilities.add(ability);
				}

				DefaultListModel abilityList = new DefaultListModel();

				for (int i = 0; i < character.abilities.size(); i++) {
					abilityList.addElement(character.abilities.get(i).toString());
				}

				listActiveAbilities.setModel(abilityList);

				setFields();

			}
		});
		btnSaveAbility.setBounds(168, 47, 142, 23);
		panelAbilities.add(btnSaveAbility);

		JLabel lblName = new JLabel("Name:");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblName.setBounds(20, 47, 44, 25);
		panelAbilities.add(lblName);

		textFieldAbilityName = new JTextField();
		textFieldAbilityName.setBounds(74, 48, 84, 20);
		panelAbilities.add(textFieldAbilityName);
		textFieldAbilityName.setColumns(10);

		JLabel lblRank = new JLabel("Rank:");
		lblRank.setForeground(Color.WHITE);
		lblRank.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblRank.setBounds(20, 83, 44, 25);
		panelAbilities.add(lblRank);

		spinnerAbilityRank = new JSpinner();
		spinnerAbilityRank.setBounds(74, 84, 83, 20);
		panelAbilities.add(spinnerAbilityRank);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(62, 188, 128, 93);
		panelAbilities.add(scrollPane_2);

		listSkillSupport = new JList();
		listSkillSupport.setForeground(Color.WHITE);
		listSkillSupport.setBackground(Color.DARK_GRAY);
		scrollPane_2.setViewportView(listSkillSupport);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(62, 328, 128, 121);
		panelAbilities.add(scrollPane_3);

		listTrainingValues = new JList();
		listTrainingValues.setBackground(Color.DARK_GRAY);
		listTrainingValues.setForeground(Color.WHITE);
		scrollPane_3.setViewportView(listTrainingValues);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(320, 49, 128, 251);
		panelAbilities.add(scrollPane_4);

		listActiveAbilities = new JList();
		listActiveAbilities.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Ability ability = character.abilities.get(listActiveAbilities.getSelectedIndex());

				spinnerAbilityRank.setValue(Integer.valueOf(ability.rank));

				// Sets supported skills
				DefaultListModel supportedSkillList = new DefaultListModel();

				for (int i = 0; i < ability.skillSupport.size(); i++) {
					supportedSkillList.addElement(ability.skillSupport.get(i));

				}

				listSkillSupport.setModel(supportedSkillList);

				// Sets training values
				DefaultListModel trainingValueList = new DefaultListModel();

				for (int i = 0; i < ability.trainingValues.size(); i++) {
					trainingValueList.addElement(ability.trainingValues.get(i));

				}

				listTrainingValues.setModel(trainingValueList);

				// Sets special
				textPaneSpecial.setText(ability.special);

				// Sets mastery
				textPaneMastery.setText(ability.mastery);

				// Sets name
				textFieldAbilityName.setText(ability.name);

			}
		});
		listActiveAbilities.setBackground(Color.DARK_GRAY);
		listActiveAbilities.setForeground(Color.WHITE);
		scrollPane_4.setViewportView(listActiveAbilities);

		btnRemoveAbility = new JButton("Remove");
		btnRemoveAbility.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int selectedIndex = listActiveAbilities.getSelectedIndex();

				// Removes from front end
				listActiveAbilities.remove(selectedIndex);

				// Removes ability from character
				Ability ability = character.abilities.get(selectedIndex);
				if (ability.rank == 1) {
					character.spentCharacterPoints -= 1;
				} else if (ability.rank == 2) {
					character.spentCharacterPoints -= 3;
				} else if (ability.rank == 3) {
					character.spentCharacterPoints -= 6;
				} else if (ability.rank == 4) {
					character.spentCharacterPoints -= 10;
				} else {
					character.spentCharacterPoints -= 15;
				}

				character.abilities.remove(selectedIndex);

				// Resets front end
				DefaultListModel abilityList = new DefaultListModel();

				for (int i = 0; i < character.abilities.size(); i++) {

					abilityList.addElement(character.abilities.get(i));

				}

				listActiveAbilities.setModel(abilityList);

				setFields();
			}
		});
		btnRemoveAbility.setBounds(750, 326, 89, 23);
		panelAbilities.add(btnRemoveAbility);

		JLabel lblSkillSupport = new JLabel("Skill Support:");
		lblSkillSupport.setForeground(Color.WHITE);
		lblSkillSupport.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblSkillSupport.setBounds(20, 152, 89, 25);
		panelAbilities.add(lblSkillSupport);

		btnAddSkillSupport = new JButton("Add");
		btnAddSkillSupport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				DefaultListModel skillSupport = new DefaultListModel();

				int size = listSkillSupport.getModel().getSize();

				for (int i = 0; i < size; i++) {
					skillSupport.addElement(listSkillSupport.getModel().getElementAt(i));

				}
				skillSupport.addElement(textFieldSkillSupport.getText());

				listSkillSupport.setModel(skillSupport);

			}
		});
		btnAddSkillSupport.setBounds(200, 186, 110, 23);
		panelAbilities.add(btnAddSkillSupport);

		btnRemoveSkillSupport = new JButton("Remove");
		btnRemoveSkillSupport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (listSkillSupport.getSelectedIndex() > -1) {
					DefaultListModel skillSupport = new DefaultListModel();

					int size = listSkillSupport.getModel().getSize();

					for (int i = 0; i < size; i++) {

						if (i != listSkillSupport.getSelectedIndex()) {
							skillSupport.addElement(listSkillSupport.getModel().getElementAt(i));
						}

					}

					listSkillSupport.setModel(skillSupport);
				}

			}
		});
		btnRemoveSkillSupport.setBounds(200, 220, 110, 23);
		panelAbilities.add(btnRemoveSkillSupport);

		textFieldSkillSupport = new JTextField();
		textFieldSkillSupport.setBounds(100, 153, 89, 20);
		panelAbilities.add(textFieldSkillSupport);
		textFieldSkillSupport.setColumns(10);

		JLabel lblTrainingValue = new JLabel("Training Value:");
		lblTrainingValue.setForeground(Color.WHITE);
		lblTrainingValue.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblTrainingValue.setBounds(20, 292, 99, 25);
		panelAbilities.add(lblTrainingValue);

		textFieldTrainingValue = new JTextField();
		textFieldTrainingValue.setColumns(10);
		textFieldTrainingValue.setBounds(113, 293, 77, 20);
		panelAbilities.add(textFieldTrainingValue);

		JButton btnAddTrainingValue = new JButton("Add");
		btnAddTrainingValue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				DefaultListModel trainingValue = new DefaultListModel();

				int size = listTrainingValues.getModel().getSize();

				for (int i = 0; i < size; i++) {
					trainingValue.addElement(listTrainingValues.getModel().getElementAt(i));

				}
				trainingValue.addElement(textFieldTrainingValue.getText());

				listTrainingValues.setModel(trainingValue);

			}
		});
		btnAddTrainingValue.setBounds(200, 326, 110, 23);
		panelAbilities.add(btnAddTrainingValue);

		JButton btnRemoveTrainingValue = new JButton("Remove");
		btnRemoveTrainingValue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (listTrainingValues.getSelectedIndex() > -1) {

					DefaultListModel trainingValue = new DefaultListModel();

					int size = listTrainingValues.getModel().getSize();

					for (int i = 0; i < size; i++) {

						if (i != listTrainingValues.getSelectedIndex()) {
							trainingValue.addElement(listTrainingValues.getModel().getElementAt(i));
						}

					}

					listTrainingValues.setModel(trainingValue);

				}

			}
		});
		btnRemoveTrainingValue.setBounds(200, 360, 110, 23);
		panelAbilities.add(btnRemoveTrainingValue);

		JLabel lblSpecial = new JLabel("Special:");
		lblSpecial.setForeground(Color.WHITE);
		lblSpecial.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblSpecial.setBounds(20, 460, 99, 25);
		panelAbilities.add(lblSpecial);

		JLabel lblMastery = new JLabel("Mastery:");
		lblMastery.setForeground(Color.WHITE);
		lblMastery.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblMastery.setBounds(458, 460, 99, 25);
		panelAbilities.add(lblMastery);

		btnRankUp = new JButton("Rank Up");
		btnRankUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (listActiveAbilities.getSelectedIndex() > -1) {

					Ability ability = character.abilities.get(listActiveAbilities.getSelectedIndex());

					if (ability.rank != 5) {

						if (ability.rank == 1) {
							character.spentCharacterPoints += 2;
						} else if (ability.rank == 2) {
							character.spentCharacterPoints += 3;
						} else if (ability.rank == 3) {
							character.spentCharacterPoints += 4;
						} else if (ability.rank == 4) {
							character.spentCharacterPoints += 5;
						} else {
							character.spentCharacterPoints += 6;
						}

						ability.rank += 1;

						// Resets front end
						DefaultListModel abilityList = new DefaultListModel();

						for (int i = 0; i < character.abilities.size(); i++) {

							abilityList.addElement(character.abilities.get(i));

						}

						listActiveAbilities.setModel(abilityList);

						setFields();
					}

				}

			}
		});
		btnRankUp.setBounds(458, 83, 89, 23);
		panelAbilities.add(btnRankUp);
		
		scrollPane_9 = new JScrollPane();
		scrollPane_9.setBounds(20, 483, 428, 228);
		panelAbilities.add(scrollPane_9);
		
		textPaneSpecial = new TextArea();
		scrollPane_9.setViewportView(textPaneSpecial);
				
				scrollPane_10 = new JScrollPane();
				scrollPane_10.setBounds(458, 483, 397, 228);
				panelAbilities.add(scrollPane_10);
				
				textPaneMastery = new TextArea();
				scrollPane_10.setViewportView(textPaneMastery);

		panelSkills = new JPanel();
		panelSkills.setBackground(Color.DARK_GRAY);
		panelSkills.setForeground(Color.WHITE);
		tabbedPane.addTab("Skills", null, panelSkills, null);
		panelSkills.setLayout(null);

		lblSkills = new JLabel("Skills:");
		lblSkills.setBounds(50, 31, 36, 19);
		lblSkills.setForeground(Color.WHITE);
		lblSkills.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelSkills.add(lblSkills);

		JLabel lblBallance = new JLabel("Skill:");
		lblBallance.setForeground(Color.WHITE);
		lblBallance.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblBallance.setBounds(50, 73, 62, 31);
		panelSkills.add(lblBallance);

		spinnerValue = new JSpinner();
		spinnerValue.setFont(new Font("Calibri", Font.PLAIN, 11));
		spinnerValue.setBounds(86, 78, 56, 19);
		panelSkills.add(spinnerValue);

		btnSkillRankUp = new JButton("Rank Up");
		btnSkillRankUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (tempSkill.rank != 5) {

					if (tempSkill.rank == 0) {
						character.spentCharacterPoints += 1;
					} else if (tempSkill.rank == 1) {
						character.spentCharacterPoints += 2;
					} else if (tempSkill.rank == 2) {
						character.spentCharacterPoints += 3;
					} else if (tempSkill.rank == 3) {
						character.spentCharacterPoints += 4;
					} else if (tempSkill.rank == 4) {
						character.spentCharacterPoints += 5;
					} else {
						character.spentCharacterPoints += 6;
					}

					tempSkill.rankUp();

					if (tempSkill.type.equals("Basic")) {
						for (int i = 0; i < character.basicSkills.size(); i++) {
							if (character.basicSkills.get(i).name.equals(tempSkill.name)) {
								character.basicSkills.set(i, tempSkill);
							}
						}
					} else if (tempSkill.type.equals("Trained")) {
						for (int i = 0; i < character.trainedSkills.size(); i++) {
							if (character.trainedSkills.get(i).name.equals(tempSkill.name)) {
								character.trainedSkills.set(i, tempSkill);
							}
						}
					} else if (tempSkill.type.equals("Expert")) {
						for (int i = 0; i < character.expertSkills.size(); i++) {
							if (character.expertSkills.get(i).name.equals(tempSkill.name)) {
								character.expertSkills.set(i, tempSkill);
							}
						}
					}

					setFields();
				}

			}
		});
		btnSkillRankUp.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnSkillRankUp.setBounds(152, 76, 89, 23);
		panelSkills.add(btnSkillRankUp);

		JLabel lblRanks = new JLabel("Ranks:");
		lblRanks.setForeground(Color.WHITE);
		lblRanks.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblRanks.setBounds(251, 73, 62, 31);
		panelSkills.add(lblRanks);

		spinnerRanks = new JSpinner();
		spinnerRanks.setFont(new Font("Calibri", Font.PLAIN, 11));
		spinnerRanks.setBounds(300, 77, 36, 19);
		panelSkills.add(spinnerRanks);

		btnSaveChanges_1 = new JButton("Save Changes");
		btnSaveChanges_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int value = (Integer) spinnerValue.getValue();
				int ranks = (Integer) spinnerRanks.getValue();
				int TV = (Integer) spinnerTV.getValue();
				int advancement = (Integer) spinnerAdvancement.getValue();

				tempSkill.value = value;
				tempSkill.rank = ranks;
				tempSkill.trainingValue = TV;
				tempSkill.increasedValue = advancement;

				if (tempSkill.type.equals("Basic")) {
					for (int i = 0; i < character.basicSkills.size(); i++) {
						if (character.basicSkills.get(i).name.equals(tempSkill.name)) {
							character.basicSkills.set(i, tempSkill);
						}
					}
				} else if (tempSkill.type.equals("Trained")) {
					for (int i = 0; i < character.trainedSkills.size(); i++) {
						if (character.trainedSkills.get(i).name.equals(tempSkill.name)) {
							character.trainedSkills.set(i, tempSkill);
						}
					}
				} else {
					for (int i = 0; i < character.expertSkills.size(); i++) {
						if (character.expertSkills.get(i).name.equals(tempSkill.name)) {

						}
						character.expertSkills.set(i, tempSkill);
					}
				}

				setFields();

			}
		});
		btnSaveChanges_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnSaveChanges_1.setBounds(96, 29, 114, 23);
		panelSkills.add(btnSaveChanges_1);

		JLabel lblTv = new JLabel("TV:");
		lblTv.setForeground(Color.WHITE);
		lblTv.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblTv.setBounds(346, 73, 27, 31);
		panelSkills.add(lblTv);

		spinnerTV = new JSpinner();
		spinnerTV.setFont(new Font("Calibri", Font.PLAIN, 11));
		spinnerTV.setBounds(369, 77, 36, 19);
		panelSkills.add(spinnerTV);

		JLabel lblAdvancement = new JLabel("Advancement:");
		lblAdvancement.setForeground(Color.WHITE);
		lblAdvancement.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblAdvancement.setBounds(415, 73, 89, 31);
		panelSkills.add(lblAdvancement);

		spinnerAdvancement = new JSpinner();
		spinnerAdvancement.setFont(new Font("Calibri", Font.PLAIN, 11));
		spinnerAdvancement.setBounds(504, 77, 49, 19);
		panelSkills.add(spinnerAdvancement);

		scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(50, 150, 250, 500);
		panelSkills.add(scrollPane_5);

		listBasicSkills = new JList();
		listBasicSkills.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				listTrainedSkills.setSelectedIndex(-1);
				listExpertSkills.setSelectedIndex(-1);

				int index = listBasicSkills.getSelectedIndex();

				Skill skill = character.basicSkills.get(index);

				spinnerValue.setValue((Integer) skill.value);
				spinnerRanks.setValue((Integer) skill.rank);
				spinnerTV.setValue((Integer) skill.trainingValue);
				spinnerAdvancement.setValue((Integer) skill.increasedValue);

				tempSkill = skill;

			}
		});
		listBasicSkills.setBackground(Color.DARK_GRAY);
		listBasicSkills.setForeground(Color.WHITE);
		listBasicSkills.setFont(new Font("Tahoma", Font.BOLD, 11));
		scrollPane_5.setViewportView(listBasicSkills);

		scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(310, 150, 250, 500);
		panelSkills.add(scrollPane_6);

		listTrainedSkills = new JList();
		listTrainedSkills.setFont(new Font("Tahoma", Font.BOLD, 11));
		listTrainedSkills.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				listBasicSkills.setSelectedIndex(-1);
				listExpertSkills.setSelectedIndex(-1);

				int index = listTrainedSkills.getSelectedIndex();

				Skill skill = character.trainedSkills.get(index);

				spinnerValue.setValue((Integer) skill.value);
				spinnerRanks.setValue((Integer) skill.rank);
				spinnerTV.setValue((Integer) skill.trainingValue);
				spinnerAdvancement.setValue((Integer) skill.increasedValue);

				tempSkill = skill;

			}
		});
		listTrainedSkills.setBackground(Color.DARK_GRAY);
		listTrainedSkills.setForeground(Color.WHITE);
		scrollPane_6.setViewportView(listTrainedSkills);

		scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(573, 150, 250, 500);
		panelSkills.add(scrollPane_7);

		listExpertSkills = new JList();
		listExpertSkills.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				listBasicSkills.setSelectedIndex(-1);
				listTrainedSkills.setSelectedIndex(-1);

				int index = listExpertSkills.getSelectedIndex();

				Skill skill = character.expertSkills.get(index);

				spinnerValue.setValue((Integer) skill.value);
				spinnerRanks.setValue((Integer) skill.rank);
				spinnerTV.setValue((Integer) skill.trainingValue);
				spinnerAdvancement.setValue((Integer) skill.increasedValue);

				tempSkill = skill;

			}
		});
		listExpertSkills.setBackground(Color.DARK_GRAY);
		listExpertSkills.setForeground(Color.WHITE);
		scrollPane_7.setViewportView(listExpertSkills);

		lblBasic = new JLabel("Basic:");
		lblBasic.setForeground(Color.WHITE);
		lblBasic.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblBasic.setBounds(50, 115, 62, 31);
		panelSkills.add(lblBasic);

		lblTrained = new JLabel("Trained:");
		lblTrained.setForeground(Color.WHITE);
		lblTrained.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblTrained.setBounds(310, 115, 62, 31);
		panelSkills.add(lblTrained);

		lblExpert = new JLabel("Expert:");
		lblExpert.setForeground(Color.WHITE);
		lblExpert.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblExpert.setBounds(570, 115, 62, 31);
		panelSkills.add(lblExpert);

		JButton btnResetrefreshSkill = new JButton("Reset/Refresh Skill");
		btnResetrefreshSkill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				character.basicSkills.clear();
				character.trainedSkills.clear();
				character.expertSkills.clear();

				character.calculateSkills();

				setSkills();

			}
		});
		btnResetrefreshSkill.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnResetrefreshSkill.setBounds(643, 30, 180, 23);
		panelSkills.add(btnResetrefreshSkill);

		panel = new JPanel();
		panel.setLayout(null);
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Character Notes", null, panel, null);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(10, 11, 845, 700);
		panel.add(scrollPane_8);
		
		textAreaCharacterNotes = new TextArea();
		scrollPane_8.setViewportView(textAreaCharacterNotes);

		// Loads saved characters
		load();
	}

	// Takes the active character's content and sets it equal to the fields
	public void setFields() {
		textFieldName.setText(character.name);

		textFieldCharacterPoints.setText(Integer.toString(character.characterPointTotal));
		textFieldSpentCharacterPoints.setText(Integer.toString(character.spentCharacterPoints));

		textFieldSTR.setText(Integer.toString(character.str));
		textFieldINT.setText(Integer.toString(character.wit));
		textFieldWIS.setText(Integer.toString(character.wis));
		textFieldSOC.setText(Integer.toString(character.soc));
		textFieldWIL.setText(Integer.toString(character.wil));
		textFieldPER.setText(Integer.toString(character.per));
		textFieldHTL.setText(Integer.toString(character.htl));
		textFieldAGL.setText(Integer.toString(character.agl));

		// Adds abilities
		if (character.abilities != null) {
			DefaultListModel abilityList = new DefaultListModel();

			for (int i = 0; i < character.abilities.size(); i++) {

				abilityList.addElement(character.abilities.get(i));

			}

			listActiveAbilities.setModel(abilityList);
		}

		
		textAreaCharacterNotes.setText(character.notes);
		
		// Sets skills
		setSkills();

	}

	// Takes character values from skills and sets skills on front end
	public void setSkills() {

		/*
		 * spinnerValue.setValue((Integer) character.basicSkills.get(0).value);
		 * spinnerRanks.setValue((Integer) character.basicSkills.get(0).rank);
		 * spinnerTV.setValue((Integer) character.basicSkills.get(0).trainingValue);
		 * spinnerAdvancement.setValue((Integer)
		 * character.basicSkills.get(0).increasedValue);
		 */

		// Basic Skills
		DefaultListModel basicSkillList = new DefaultListModel();

		for (int i = 0; i < character.basicSkills.size(); i++) {

			Skill skill = character.basicSkills.get(i);
			boolean supported = false;
			skill.trainingValue = 0;

			for (int x = 0; x < character.abilities.size(); x++) {

				Ability ability = character.abilities.get(x);

				for (int j = 0; j < ability.skillSupport.size(); j++) {
					if (ability.skillSupport.get(j).equals(skill.name)) {
						supported = true;
						skill.supported = true;

						if (ability.rank != 0) {
							skill.trainingValue += ability.rank;
						}

					}
				}

			}

			if (!supported) {
				skill.trainingValue = 0;
				skill.supported = false;
			}

			character.basicSkills.set(i, skill);
			basicSkillList.addElement(character.basicSkills.get(i).returnSkill());

		}

		listBasicSkills.setModel(basicSkillList);

		// Trained Skills
		DefaultListModel trainedSkillList = new DefaultListModel();

		for (int i = 0; i < character.trainedSkills.size(); i++) {

			Skill skill = character.trainedSkills.get(i);
			skill.trainingValue = 0;
			boolean supported = false;

			for (int x = 0; x < character.abilities.size(); x++) {

				Ability ability = character.abilities.get(x);

				for (int j = 0; j < ability.skillSupport.size(); j++) {
					if (ability.skillSupport.get(j).equals(skill.name)) {
						supported = true;
						skill.supported = true;
						if (ability.rank != 0) {
							skill.trainingValue += ability.rank;
						}

					}
				}

			}

			if (!supported) {
				skill.trainingValue = 0;
				skill.supported = false;
			}

			character.trainedSkills.set(i, skill);
			trainedSkillList.addElement(character.trainedSkills.get(i).returnSkill());
		}

		listTrainedSkills.setModel(trainedSkillList);

		// Expert Skills
		DefaultListModel expertSkillList = new DefaultListModel();

		for (int i = 0; i < character.expertSkills.size(); i++) {

			Skill skill = character.expertSkills.get(i);
			boolean supported = false;
			skill.trainingValue = 0;
			for (int x = 0; x < character.abilities.size(); x++) {

				Ability ability = character.abilities.get(x);

				for (int j = 0; j < ability.skillSupport.size(); j++) {
					if (ability.skillSupport.get(j).equals(skill.name)) {
						supported = true;
						skill.supported = true;
						if (ability.rank != 0) {
							skill.trainingValue += ability.rank;
						}

					}
				}

			}
			if (!supported) {
				skill.trainingValue = 0;
				skill.supported = false;
			}
			expertSkillList.addElement(character.expertSkills.get(i).returnSkill());
			character.expertSkills.set(i, skill);

		}

		listExpertSkills.setModel(expertSkillList);

	}

	// Saves changes to the currently active character
	public void saveChanges() {

		character.name = textFieldName.getText();

		character.characterPointTotal = Integer.parseInt(textFieldCharacterPoints.getText());
		character.spentCharacterPoints = Integer.parseInt(textFieldSpentCharacterPoints.getText());

		character.str = Integer.parseInt(textFieldSTR.getText());
		character.wit = Integer.parseInt(textFieldINT.getText());
		character.soc = Integer.parseInt(textFieldSOC.getText());
		character.wil = Integer.parseInt(textFieldWIL.getText());
		character.per = Integer.parseInt(textFieldPER.getText());
		character.htl = Integer.parseInt(textFieldHTL.getText());
		character.agl = Integer.parseInt(textFieldAGL.getText());

		character.notes = textAreaCharacterNotes.getText();
		
	}

	// Populates Jlist with characters list
	public void setCharacterList() {

		DefaultListModel charactersList = new DefaultListModel();

		for (int i = 0; i < characters.size(); i++) {
			charactersList.addElement(characters.get(i).name);

		}

		listCharacters.setModel(charactersList);

	}

	// Loop active while opening companies
	// Adds companies to company array
	// Deletes company file
	public void load() throws FileNotFoundException, IOException, ClassNotFoundException {
		int fileNum = 1;
		boolean multipleCompanies = true;
		while (multipleCompanies) {
			File file = new File("Character_" + fileNum);
			if (file.canRead() && file.isFile()) {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("Character_" + fileNum));
				Character character = (Character) in.readObject();
				characters.add(character);
				in.close();
				fileNum++;
			} else {
				multipleCompanies = false;
			}
		}
		setCharacterList();

	}
}
