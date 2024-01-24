package Items;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.SwingUtility;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;

import Company.Company;

import javax.swing.event.ListSelectionEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;

public class BulkInventoryWindow extends JFrame {

	private JPanel contentPane;

	ArrayList<Unit> units = new ArrayList<>();
	ArrayList<Trooper> troopers = new ArrayList<>();
	ArrayList<String> items = new ArrayList<>();
	
	private JList listTroopers;
	private JList listInventory;
	private JList listItems;
	
	private static ArrayList<Trooper> testTroopers = new ArrayList<>();
	private JLabel lblEncumberance;
	private JSpinner spinnerItemCount;
	private JLabel lblAnaleticValue;
	
	Company company;
	private JCheckBox chckbxAddCompany;
	private JList listCompanyInventory;
	
	public boolean isCompanyInventory() {
			
		return company != null;
		
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testTroopers.add(new Trooper("Republic Commando", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Republic Commando", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Republic Commando", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Republic Commando", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Clone Rifleman", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Clone Rifleman", "Clone Trooper Phase 1"));
					testTroopers.add(new Trooper("Clone Squad Leader", "Clone Trooper Phase 1"));
					new BulkInventoryWindow(testTroopers);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public BulkInventoryWindow(ArrayList<Trooper> troopers) {
		
		this.troopers = troopers;
	
		initWindow();
		refreshWindow();
	}
	
	public BulkInventoryWindow(ArrayList<Unit> units, ArrayList<Trooper> troopers, Company company) {
		this.units = units;
		this.troopers = troopers;
	
		this.company = company;
		
		initWindow();
		refreshWindow();
	}
	
	public void initWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1355, 884);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		//y -= screenSize.height / 5;

		// Set the new frame location
		setLocation(x, y);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblArmorPage_1 = new JLabel("Inventory");
		lblArmorPage_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblArmorPage_1.setForeground(Color.BLACK);
		lblArmorPage_1.setFont(new Font("Calibri", Font.BOLD, 16));
		lblArmorPage_1.setBounds(453, 15, 433, 16);
		contentPane.add(lblArmorPage_1);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(453, 41, 433, 379);
		contentPane.add(scrollPane_8);
		
		listInventory = new JList();
		scrollPane_8.setViewportView(listInventory);
		
		JScrollPane scrollPane_8_1 = new JScrollPane();
		scrollPane_8_1.setBounds(896, 41, 433, 793);
		contentPane.add(scrollPane_8_1);
		
		listItems = new JList();
		listItems.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (listItems.getSelectedIndex() < 0)
					return;

				try {
					Item item = Item.allItems.get(listItems.getSelectedIndex());
					
					for(Trooper trooper : getSelectedTroopers()) {
						
						if (item.isRound()) {
							trooper.inventory.addItems(item.itemType, item.ammoType,
									(int) spinnerItemCount.getValue());
						} else if (item.isWeapon()) {
							trooper.inventory.addItems(item.itemType, (int) spinnerItemCount.getValue());
						} else {
							trooper.inventory.addItems(item.itemType, (int) spinnerItemCount.getValue());
						}
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				 refreshWindow();

			}
		});
		scrollPane_8_1.setViewportView(listItems);
		
		JLabel lblArmorPage_1_1 = new JLabel("Add Items");
		lblArmorPage_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblArmorPage_1_1.setForeground(Color.BLACK);
		lblArmorPage_1_1.setFont(new Font("Calibri", Font.BOLD, 16));
		lblArmorPage_1_1.setBounds(896, 14, 433, 16);
		contentPane.add(lblArmorPage_1_1);
		
		JLabel lblNewLabel = new JLabel("Count");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel.setBounds(896, 14, 67, 14);
		contentPane.add(lblNewLabel);
		
		spinnerItemCount = new JSpinner();
		spinnerItemCount.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		spinnerItemCount.setBounds(946, 11, 60, 20);
		contentPane.add(spinnerItemCount);
		
		lblEncumberance = new JLabel("Encumerance: 0");
		lblEncumberance.setForeground(Color.BLACK);
		lblEncumberance.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEncumberance.setBounds(453, 12, 176, 16);
		contentPane.add(lblEncumberance);
		
		JLabel lblArmorPage_1_2 = new JLabel("Troopers");
		lblArmorPage_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblArmorPage_1_2.setForeground(Color.BLACK);
		lblArmorPage_1_2.setFont(new Font("Calibri", Font.BOLD, 16));
		lblArmorPage_1_2.setBounds(10, 15, 433, 16);
		contentPane.add(lblArmorPage_1_2);
		
		JScrollPane scrollPane_8_2 = new JScrollPane();
		scrollPane_8_2.setBounds(10, 41, 433, 793);
		contentPane.add(scrollPane_8_2);
		
		listTroopers = new JList();
		listTroopers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				refreshInventory();
			}
		});
		listInventory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (listInventory.getSelectedIndex() < 0)
					return;

				try {
					for(Trooper trooper : getSelectedTroopers()) {
						String item = items.get(listInventory.getSelectedIndex());
						if(trooper.inventory.containsItem(item)) {
							if(isCompanyInventory() && chckbxAddCompany.isSelected())
								company.companyInventory.add(trooper.inventory.getItem(item));
							trooper.inventory.removeItem(item);
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				 refreshWindow();

			}
		});
		scrollPane_8_2.setViewportView(listTroopers);
		
		lblAnaleticValue = new JLabel("Avg. Analetic Value: 0");
		lblAnaleticValue.setForeground(Color.BLACK);
		lblAnaleticValue.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAnaleticValue.setBounds(739, 12, 147, 16);
		contentPane.add(lblAnaleticValue);
		
		JLabel lblArmorPage_1_2_1 = new JLabel("Company Inventory");
		lblArmorPage_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblArmorPage_1_2_1.setForeground(Color.BLACK);
		lblArmorPage_1_2_1.setFont(new Font("Calibri", Font.BOLD, 16));
		lblArmorPage_1_2_1.setBounds(453, 457, 433, 16);
		contentPane.add(lblArmorPage_1_2_1);
		
		chckbxAddCompany = new JCheckBox("Add Items to Company Inventory");
		chckbxAddCompany.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxAddCompany.setBounds(453, 427, 433, 23);
		contentPane.add(chckbxAddCompany);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(453, 473, 433, 361);
		contentPane.add(scrollPane);
		
		listCompanyInventory = new JList();
		listCompanyInventory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(!isCompanyInventory() || getSelectedTroopers().size() == 0 || listCompanyInventory.getSelectedIndex() < 0)
					return;
				
				var trooper = getSelectedTroopers().get(0);
				
				var itemIndex = companyItemIndex((String)listCompanyInventory.getModel()
						.getElementAt(listCompanyInventory.getSelectedIndex()));
				
				var item = company.companyInventory.get(itemIndex);
				
				try {
					trooper.inventory.addItem(item);
					company.companyInventory.remove(itemIndex);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				refreshInventory();
				refreshTroopers();
				refreshWindow();
			}
		});
		scrollPane.setViewportView(listCompanyInventory);
		setVisible(true);
	}
	
	private int companyItemIndex(String itemName) {
		for(int i = 0; i < company.companyInventory.size(); i++) {
			if(company.companyInventory.get(i).getItemName().equals(itemName))
				return i;
		}
		
		return -1;
	}
	
	public void refreshWindow() {
		refreshInventory();
		refreshTroopers();
		refreshCompanyInventory();
	}
	
	public void refreshTroopers() {
		
		int[] selected = listTroopers.getSelectedIndices();
		
		ArrayList<String> trooperEntries = new ArrayList<>();
		
		for(Unit unit : units) {
			for(Trooper individual : unit.individuals) {
				trooperEntries.add(unit.callsign +": "+individual.number+" "+individual.name+" "+individual.designation
						+", CA: "+individual.combatActions+", P1: "+individual.P1+", P2: "+individual.P2);
			}
		}
		
		for(Trooper trooper : troopers) {
			trooperEntries.add(trooper.name+", CA: "+trooper.combatActions+", P1: "+trooper.P1+", P2: "+trooper.P2);
		}
		
		SwingUtility.setList(listTroopers, trooperEntries);
		listTroopers.setSelectedIndices(selected);
	}
	
	public ArrayList<String> getAllItems() {
		
		if(Item.allItems == null) {
			try {
				new Item();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ArrayList<String> items = new ArrayList<>();
		
		for(Trooper trooper : getSelectedTroopers()) {
			for(String str : trooper.inventory.getItems()) {
				if(items.contains(str))
					continue; 
				
				items.add(str);
			}
		}
		
		this.items = items; 
		
		ArrayList<String> items2 = new ArrayList<>();
		
		for(String item : items) {
			int itemCount = 0; 
			int averagePerPerson = 0;
			for(Trooper trooper : getSelectedTroopers()) {
				
				for(String str : trooper.inventory.getItems()) {
					if(item.equals(str)) {
						itemCount++; 
					}
				}
				
			}
			
			averagePerPerson = itemCount / getSelectedTroopers().size();
			items2.add(item + ", Total: "+itemCount + ", Avg. per Trooper: "+averagePerPerson);		
		}
		
		return items2; 
		
	}
	
	public void refreshInventory() {

		ArrayList<String> items = getAllItems();

		SwingUtility.setList(listInventory, items);

		items = new ArrayList<>();

		for (Item item : Item.allItems) {
			items.add(item.getItemName());
		}

		SwingUtility.setList(listItems, items);

		int avgEncum = 0;
		int avgAv = 0; 
		
		for(Trooper trooper : getSelectedTroopers()) {
			trooper.setCombatStats(trooper);
			avgEncum += trooper.encumberance;
			avgAv += trooper.fatigueSystem.analeticValue;
		}
		
		if(getSelectedTroopers().size() > 0) {
			avgEncum /= getSelectedTroopers().size();
			avgAv /= getSelectedTroopers().size();
		}
		
		lblAnaleticValue.setText("Avg. Analetic Value: "+avgAv);
		lblEncumberance.setText("Avg. Encumerance: "+avgEncum);
	}
	
	public void refreshCompanyInventory() {
		if(!isCompanyInventory())
			return;
		
		ArrayList<String> items = new ArrayList<String>();
		
		items.sort(Comparator.naturalOrder());
		
		for(var item : company.companyInventory) {
			items.add(item.getItemName());
		}
		
		SwingUtility.setList(listCompanyInventory, items);
	}
	
	public ArrayList<Trooper> getSelectedTroopers() {
		
		ArrayList<Trooper> selectedTroopers = new ArrayList<Trooper>();
		int[] indexes = listTroopers.getSelectedIndices();
		
		ArrayList<Trooper> troopers = new ArrayList<>();
		
		for(Unit unit : units) {
			for(Trooper individual : unit.individuals) {
				troopers.add(individual);
			}
		}
		
		for(Trooper trooper : this.troopers) {
			troopers.add(trooper);
		}
		
		for(int index : indexes) {
			selectedTroopers.add(troopers.get(index));	
		}
		
		return selectedTroopers; 
	} 
}
