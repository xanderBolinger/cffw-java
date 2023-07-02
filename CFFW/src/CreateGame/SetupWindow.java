package CreateGame;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Visibility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import Company.Company;
import Company.EditCompany;
import Conflict.Game;
import Conflict.GameWindow;
import HexGrid.HexGrid;
import Items.Item;
import SaveCompanies.GameSave;
import SaveCompanies.Hexes;
import SaveCompanies.InitOrder;
import SaveCompanies.SaveRunner;
import SaveCompanies.SavedGameDialogBox;
import Unit.Unit;
import UtilityClasses.FileUtility;
import UtilityClasses.SwingUtility;
import Trooper.Trooper;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import CeHexGrid.Chit;

import javax.swing.JTextField;

public class SetupWindow implements Serializable {
	// Company global variable
	public static ArrayList<Company> companies = new ArrayList<Company>();
	private SetupWindow setupWindow;
	private Game game = new Game(1,1,1);
	public GameWindow conflict = null;
	public boolean deletedConflict = false; 
	
	// Jrframe and other elements
	private JFrame f = new JFrame("Creating Conflict");

	private JComboBox comboBoxSide;
	private JList listCreatedCompanies;
	private JButton btnSaveCompanies;
	private JSpinner spinnerHexRows;
	private JSpinner spinnerHexCols;
	private JTextField txtSaveName;
	private JComboBox comboBoxGameSaves;

	public SetupWindow() throws FileNotFoundException, ClassNotFoundException, IOException {
		setupWindow = this;
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setSize(319, 554);

		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;

		// Set the new frame location
		f.setLocation(x, y);

		// SIDE COMBO BOX

		comboBoxSide = new JComboBox();
		comboBoxSide.setBounds(171, 236, 123, 25);
		comboBoxSide.setModel(new DefaultComboBoxModel(new String[] { "BLUFOR", "OPFOR", "IND", "UNKNOWN" }));
		comboBoxSide.setSelectedIndex(0);
		comboBoxSide.setFont(new Font("Calibri", Font.BOLD, 13));

		// BUTTONS

		JButton btnCreateCompany = new JButton("Create Company");
		btnCreateCompany.setBounds(11, 236, 150, 25);
		btnCreateCompany.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Trooper> roster = new ArrayList<Trooper>();
				ArrayList<Unit> units = new ArrayList<Unit>();
				String side = String.valueOf(comboBoxSide.getSelectedItem());
				Company company = new Company(side, "Empty", 0, 0, 0, 0, 0, 0, roster, units);
				companies.add(company);
				refreshCreated();
			}
		});

		JButton btnCreateConflict = new JButton("Create Conflict");
		btnCreateConflict.setBounds(11, 42, 283, 25);
		btnCreateConflict.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

								
				ArrayList<Company> activatedCompanies = getActivated();
				createConflict(activatedCompanies, setupWindow, true, (int) spinnerHexRows.getValue(), (int) spinnerHexCols.getValue());
				deletedConflict = false; 
			}
		});

		JScrollPane scrollPaneCreated = new JScrollPane();
		scrollPaneCreated.setBounds(11, 307, 283, 197);

		JLabel lblCreatedCompanies = new JLabel("Companies");
		lblCreatedCompanies.setBounds(11, 279, 73, 17);
		lblCreatedCompanies.setFont(new Font("Calibri", Font.BOLD, 13));

		listCreatedCompanies = new JList();
		listCreatedCompanies.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = listCreatedCompanies.getSelectedIndex();
				new EditCompany(companies.get(index), setupWindow, index);
			}
		});
		scrollPaneCreated.setViewportView(listCreatedCompanies);
		
		btnSaveCompanies = new JButton("Save Game");
		btnSaveCompanies.setBounds(11, 142, 150, 23);
		btnSaveCompanies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String saveName = getSaveName();
				
				File file = new File("Game Saves/"+saveName);
				if(file.canRead() && file.isFile()) {
					file.delete();
				} 
				
				// Writes save files
				SaveRunner save = null;
				if(conflict == null) {
					//System.out.println("Conflict Null");
					save = new SaveRunner(saveName, companies, null, null, null, 0);
				} else {
					game = conflict.game;
					int activeUnit = conflict.activeUnit;
					//System.out.println(GameWindow.gameWindow.hexCols+","+GameWindow.gameWindow.hexRows);
					Hexes hexes = new Hexes(conflict.hexes, GameWindow.gameWindow.hexCols, GameWindow.gameWindow.hexRows);
					InitOrder initOrder = new InitOrder(conflict.initiativeOrder);
					save = new SaveRunner(saveName, companies, game, hexes, initOrder, activeUnit);
				}
				
				try {
					save.fileOut();
					
					new SavedGameDialogBox();
					
					//f.dispose();
				
				} catch (ClassNotFoundException | IOException exception) {
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}
				
				setSavesComboBox();
				
				
			}
		});
		btnSaveCompanies.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0)  {
				
				
			}
		});
		
		JButton btnSaveClose = new JButton("Save & Close");
		btnSaveClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSaveClose.setBounds(171, 141, 123, 25);
		btnSaveClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
				String saveName = getSaveName(); 
				
				File file = new File("Game Saves/"+saveName);
				if(file.canRead() && file.isFile()) {
					file.delete();
				}
				
				// Writes save files
				SaveRunner save = null;
				if(conflict == null) {
					//System.out.println("Conflict Null");
					save = new SaveRunner(saveName, companies, null, null, null, 0);
				} else {
					game = conflict.game;
					//System.out.println(GameWindow.gameWindow.hexCols+","+GameWindow.gameWindow.hexRows);
					Hexes hexes = new Hexes(conflict.hexes, GameWindow.gameWindow.hexCols, GameWindow.gameWindow.hexRows);
					InitOrder initOrder = new InitOrder(conflict.initiativeOrder);
					save = new SaveRunner(saveName, companies, game, hexes, initOrder, conflict.activeUnit);
				}
				
				try {
					save.fileOut();
					System.exit(0);
				
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		JButton btnDeleteConflcit = new JButton("Delete ");
		btnDeleteConflcit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deleteConflict();
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
			}
		});
		btnDeleteConflcit.setBounds(170, 202, 124, 23);
		btnDeleteConflcit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				
				
			}
		});
		f.getContentPane().setLayout(null);
		
		JLabel lblHexrows = new JLabel("Hex Rows");
		lblHexrows.setBounds(36, 11, 57, 20);
		f.getContentPane().add(lblHexrows);
		f.getContentPane().add(lblCreatedCompanies);
		f.getContentPane().add(scrollPaneCreated);
		f.getContentPane().add(btnCreateCompany);
		f.getContentPane().add(btnDeleteConflcit);
		f.getContentPane().add(btnSaveCompanies);
		f.getContentPane().add(btnCreateConflict);
		f.getContentPane().add(btnSaveClose);
		f.getContentPane().add(comboBoxSide);
		
		spinnerHexRows = new JSpinner();
		spinnerHexRows.setModel(new SpinnerNumberModel(new Integer(33), null, null, new Integer(1)));
		spinnerHexRows.setBounds(98, 11, 45, 20);
		f.getContentPane().add(spinnerHexRows);
		
		JLabel lblHexCols = new JLabel("Hex Cols");
		lblHexCols.setBounds(161, 11, 57, 20);
		f.getContentPane().add(lblHexCols);
		
		spinnerHexCols = new JSpinner();
		spinnerHexCols.setModel(new SpinnerNumberModel(new Integer(33), null, null, new Integer(1)));
		spinnerHexCols.setBounds(218, 11, 45, 20);
		f.getContentPane().add(spinnerHexCols);
		
		JLabel lblSaveName = new JLabel("Save Name");
		lblSaveName.setFont(new Font("Calibri", Font.BOLD, 13));
		lblSaveName.setBounds(11, 115, 81, 17);
		f.getContentPane().add(lblSaveName);
		
		txtSaveName = new JTextField();
		txtSaveName.setText("Conflict");
		txtSaveName.setBounds(85, 114, 209, 20);
		f.getContentPane().add(txtSaveName);
		txtSaveName.setColumns(10);
		
		JLabel lblGameSaves = new JLabel("Game Saves");
		lblGameSaves.setFont(new Font("Calibri", Font.BOLD, 13));
		lblGameSaves.setBounds(10, 177, 81, 17);
		f.getContentPane().add(lblGameSaves);
		
		comboBoxGameSaves = new JComboBox();
		comboBoxGameSaves.setBounds(93, 174, 201, 20);
		f.getContentPane().add(comboBoxGameSaves);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(comboBoxGameSaves.getItemCount() < 1)
					return;
				
				try {
					load(comboBoxGameSaves.getSelectedItem().toString());
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
				
				
				refreshCreated();
				
				/*for(Company company : companies) {
					
					for(Trooper trooper : company.getRoster()) {
						
						trooper.armor = null;
						
					}
					
				}*/
				
			}
		});
		btnLoad.setBounds(10, 202, 150, 23);
		f.getContentPane().add(btnLoad);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				companies = new ArrayList<Company>(); 
				game = null; 
				conflict = null; 
				txtSaveName.setText(getNewSaveName());
				refreshCreated();
			}
		});
		btnNewGame.setBounds(11, 78, 283, 25);
		f.getContentPane().add(btnNewGame);
		
		JButton btnModify = new JButton("Modify");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ModifyCompany(companies);
			}
		});
		btnModify.setBounds(205, 276, 89, 23);
		f.getContentPane().add(btnModify);
		f.setVisible(true);
		
		
		// Refreshes window
		
		setSavesComboBox();
		
		/*if(comboBoxGameSaves.getItemCount() > 0) {
			load(comboBoxGameSaves.getSelectedItem().toString());
		}*/
		
		refreshCreated();
		try {
			new Item();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public String getSaveName() {
		String saveName = txtSaveName.getText();
		
		/*File file = new File("Game Saves/"+saveName);
		
		int count = 1; 
		if(file.canRead() && file.isFile()) {
			while(true) {
				String newName = saveName+"_"+count; 
				file = new File("Game Saves/"+newName);
				count++; 
				
				if(!file.canRead() || !file.isFile()) {
					saveName = newName; 
					break; 
				}
				
				
			}
		}*/
		return saveName; 
	}
	
	public String getNewSaveName() {
		String saveName = txtSaveName.getText();
		
		File file = new File("Game Saves/"+saveName);
		
		int count = 1; 
		if(file.canRead() && file.isFile()) {
			while(true) {
				String newName = saveName+"_"+count; 
				file = new File("Game Saves/"+newName);
				count++; 
				
				if(!file.canRead() || !file.isFile()) {
					saveName = newName; 
					break; 
				}
				
				
			}
		}
		
		return saveName; 
	}
	
	
	public void setSavesComboBox() {
		ArrayList<String> content = new ArrayList<>();
		
		try {
			for(String fileName : FileUtility.listFilesUsingDirectoryStream("Game Saves")) {
				
				content.add(fileName);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SwingUtility.setComboBox(comboBoxGameSaves, content, false, 0);
		
	}
	
	// Refreshes Created Company List
	public void refreshCreated() {
		DefaultListModel companyList = new DefaultListModel();

		for (int i = 0; i < companies.size(); i++) {
			companyList.addElement(companies.get(i));
		}

		listCreatedCompanies.setModel(companyList);
	}


	// Sets a company in the company array at a certain index
	public void setCompany(Company company, int index) {
		companies.set(index, company);
	}
	
	
	// Get companies
	// Deletes company
	// Removes company from array 
	// Deletes all save files 
	// Re creates save files 
	public void deleteCompany(int index) throws FileNotFoundException, IOException, ClassNotFoundException {
		// Removes company from index
		companies.remove(index);
		
		// Deletes save files 
		int fileNum = 1;
		boolean multipleCompanies = true; 
		while(multipleCompanies) {
			File file = new File("Company_"+fileNum);
			if(file.canRead() && file.isFile()) {
				fileNum++;
				file.delete();
			} else {
				multipleCompanies = false;
			}
		}
		
		
		btnSaveCompanies.doClick();
		
	}
	
	
	// Deletes conflict file
	// Resets game to new game 
	public void deleteConflict() throws FileNotFoundException, IOException, ClassNotFoundException {
		if(comboBoxGameSaves.getItemCount() < 1)
			return; 
		
		File file = new File("Game Saves/"+comboBoxGameSaves.getSelectedItem().toString());
		if(file.canRead() && file.isFile()) {
			file.delete();
		} 
		
		game = new Game(1,1,1);
		deletedConflict = true; 		
		setSavesComboBox();
	}

	
	// Creates new confilct
	public void createConflict(ArrayList<Company> companies, SetupWindow setupWindow, boolean newConflict, int hexRows, int hexCols) {
		
		
		if(newConflict) {
		
			// Loops through each company, all units, and all individuals 
			// Sets each individual's spent AP to 0 
			for(int i = 0; i < companies.size(); i++) {
				
				for(int x = 0; x < companies.get(i).getUnitsLength(); x++) {
					
					for(int j = 0; j < companies.get(i).getUnits().get(x).getSize(); j++) {
						
						Unit unit = companies.get(i).getUnits().get(x);
						Trooper individual = unit.getTroopers().get(j);
						individual.spentPhase1 = 0; 
						individual.spentPhase2 = 0;
						
					}
					
				}
				
			}
			
		}
		
		if(game == null) {
			game = new Game(1, 1, 1);
		}
		
		conflict = new GameWindow(companies, setupWindow, game, false, hexRows, hexCols);
		conflict.refreshCompanyUnits();
		
	}

	// Refreshes units in the conflict
	public void refreshConflict(ArrayList<Company> companies, SetupWindow setupWindow) {
		//System.out.println("Refresh Game:"+game.toString());
		new GameWindow(companies, setupWindow, game, false, (int) spinnerHexRows.getValue(), (int) spinnerHexCols.getValue());
	}

	// Gets and returns activated companies
	public ArrayList<Company> getActivated() {
		ArrayList<Company> activatedCompanies = new ArrayList<Company>();

		for (int i = 0; i < companies.size(); i++) {
			if (companies.get(i).getActivated()) {
				activatedCompanies.add(companies.get(i));
			}
		}

		return activatedCompanies;
	}
	
	// Returns an arrayList populated with all units in the game 
	public ArrayList<Unit> getAllUnits() {
		ArrayList<Unit> units = new ArrayList<Unit>();
		
		for(Company company : companies) {
			for(Unit unit : company.getUnits()) {
				units.add(unit);
			}
		}
		
		return units; 
		
	}
	
	// Loop active while opening companies
	// Adds companies to company array 
	// Deletes company file 
	public void load(String saveName) throws FileNotFoundException, IOException, ClassNotFoundException {

		File saveFile = new File("Game Saves/"+saveName);
		if(saveFile.canRead() && saveFile.isFile()) {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("Game Saves/"+saveName));
			GameSave gameSave = (GameSave) in.readObject();
			txtSaveName.setText(gameSave.gameName);
			companies = gameSave.companies;
			game = gameSave.game;
			
			Hexes hexes = gameSave.hexes;
			InitOrder initOrder = gameSave.initOrder;
			int activeUnit = gameSave.activeUnit;
			in.close();

			if(game == null)  {
				System.out.println("Game is null");
				return; 
			}
			
			// Opens Conflict 
            createConflict(getActivated(), setupWindow, false, hexes.hexRows, hexes.hexCols);
			
			conflict.visibility = game.getDaylightCondition();
			conflict.hexes = hexes.hexes;
			conflict.hexCols = hexes.hexCols; 
			conflict.hexRows = hexes.hexRows; 
			//conflict.visibility = visibility.visibility;
			
			conflict.initiativeOrder = initOrder.initOrder;
			
			conflict.hexGrid.initOrder = initOrder.initOrder;
			conflict.comboBoxVisibility.setSelectedItem(conflict.visibility);
			conflict.activeUnit = activeUnit; 
			
			game.setDaylightCondition(game.getDaylightCondition());
			
			conflict.refreshInitiativeOrder();
			if(conflict.initiativeOrder.size() > 0)
				conflict.openUnit(conflict.initiativeOrder.get(activeUnit), activeUnit);
			if(conflict.hexGrid != null && conflict.initiativeOrder.size() > 0) {
				conflict.hexGrid.panel.selectedUnit = conflict.hexGrid.panel.deployedUnits.get(activeUnit);
			}
		}
		
	}
	
	
	
}
