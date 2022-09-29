package HexGrid;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.SwingConstants;

import Company.Company;
import Conflict.GameWindow;
import Hexes.Feature;
import Hexes.Hex;
import Hexes.SaveHexes;
import UtilityClasses.FileUtility;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoadMapWindow {

	private JFrame f;
	private JList mapList;


	/**
	 * Create the application.
	 */
	public LoadMapWindow() {
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
		
		JLabel lblSelectMap = new JLabel("Select Map");
		lblSelectMap.setBounds(0, 0, 434, 38);
		lblSelectMap.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectMap.setFont(new Font("Tahoma", Font.BOLD, 14));
		f.getContentPane().add(lblSelectMap);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					ArrayList<String> fileNames = FileUtility.listFilesUsingDirectoryStream("Map Saves");
					
					for(int i = 0; i < fileNames.size(); i++) {
						
						if(mapList.getSelectedIndex() == i) {
							
							LoadMap.loadMap(fileNames.get(i));
							break; 
						}
						
					}
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				f.dispose();
				
			}
		});
		btnLoad.setBounds(0, 223, 434, 38);
		f.getContentPane().add(btnLoad);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 36, 434, 189);
		f.getContentPane().add(scrollPane);
		
		mapList = new JList();
		scrollPane.setViewportView(mapList);
		
		
		setList();
		
	}
	
	
	public void setList() {
		
		try {
			DefaultListModel listSaves = new DefaultListModel();
			for(String file : FileUtility.listFilesUsingDirectoryStream("Map Saves")) {
				
				listSaves.addElement(file);		
				
			}
			mapList.setModel(listSaves);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	

}
