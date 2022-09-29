package CreateGame;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import Company.Company;
import Unit.Unit;
import UtilityClasses.SwingUtility;

import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModifyCompany {

	private JFrame frame;
	private JList companyList;
	private JList unitList;
	private JComboBox comboBoxCompanies;

	ArrayList<Company> companies; 
	
	/**
	 * Create the application.
	 */
	public ModifyCompany(ArrayList<Company> companies) {
		this.companies = companies; 
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 686, 601);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 54, 317, 497);
		frame.getContentPane().add(scrollPane);
		
		companyList = new JList();
		companyList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setUnitList();
			}
		});
		scrollPane.setViewportView(companyList);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(343, 54, 317, 497);
		frame.getContentPane().add(scrollPane_1);
		
		unitList = new JList();
		scrollPane_1.setViewportView(unitList);
		
		JLabel lblNewLabel = new JLabel("Select Company");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 24, 317, 19);
		frame.getContentPane().add(lblNewLabel);
		
		comboBoxCompanies = new JComboBox();
		comboBoxCompanies.setBounds(343, 23, 171, 20);
		frame.getContentPane().add(comboBoxCompanies);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(comboBoxCompanies.getSelectedIndex() < 0 || unitList.getSelectedIndices().length < 1
						|| comboBoxCompanies.getSelectedIndex() == companyList.getSelectedIndex())
					return; 
				
				
				
				Company removeCompany = companies.get(companyList.getSelectedIndex());
				ArrayList<Unit> units = new ArrayList<>();
				for(int i = 0; i < unitList.getSelectedIndices().length; i++) {
										
					units.add(removeCompany.getUnits().get(unitList.getSelectedIndices()[i]));

				}
				
				for(Unit unit : units) {
					removeCompany.removeUnit(unit);
					companies.get(comboBoxCompanies.getSelectedIndex()).addUnit(unit);
				}
				
				
				
				setUnitList();
				
			}
		});
		btnAdd.setBounds(546, 20, 89, 23);
		frame.getContentPane().add(btnAdd);
		frame.setVisible(true);
		
		setCompanyList();
		setComboBox();
		
	}
	
	
	public void setCompanyList() {
		
		ArrayList<String> content = new ArrayList<>();
		
		for(Company company : companies) {
			content.add(company.toString());
		}
		
		SwingUtility.setList(companyList, content);
		companyList.setSelectedIndex(-1);
	}
	
	public void setUnitList() {
		
		if(companyList.getSelectedIndex() < 0)  {
			SwingUtility.setList(unitList, new ArrayList<>());
			return; 
		}
		
		ArrayList<String> content = new ArrayList<>();
		
		for(Unit company : companies.get(companyList.getSelectedIndex()).getUnits()) {
			content.add(company.callsign);
		}
		
		SwingUtility.setList(unitList, content);
		unitList.setSelectedIndex(-1);
		
	}
	
	public void setComboBox() {
		
		if(companies.size() < 1)
			return; 
		
		ArrayList<String> content = new ArrayList<>();
		
		for(Company company : companies) {
			content.add(company.toString());
		}
		
		SwingUtility.setComboBox(comboBoxCompanies, content, false, 0);
		
	}
	
}
