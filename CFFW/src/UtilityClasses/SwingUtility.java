package UtilityClasses;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.Timer;

import Injuries.Injuries;
import Trooper.Trooper;

public class SwingUtility {

	
	public static void setList(JList list, ArrayList<String> content) {
		DefaultListModel model = new DefaultListModel();

		for (String str : content) {

			model.addElement(str);

		}
		
		list.setModel(model);
	}
	
	
	public static void setFrame(JFrame frame) {
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
		frame.setVisible(true);
	}
	
	
	// Version for troopers
	public static ArrayList<String> convertArrayToString(ArrayList<Trooper> troopers) {
		ArrayList<String> strArray = new ArrayList<>();
		
		for(Object obj : troopers) {
			strArray.add(obj.toString());
		}
		
		return strArray;
		
	}
	
	public static void setComboBox(JComboBox comboBox, ArrayList<String> content, boolean addNone, int selectedIndex) {
		
		comboBox.removeAllItems();
		
		if(addNone)
			comboBox.addItem("None");
		
		for(String str : content) {
			comboBox.addItem(str);
		}
		
		if(selectedIndex < comboBox.getItemCount())
			comboBox.setSelectedIndex(selectedIndex);
		else 
			comboBox.setSelectedIndex(-1);
			
	}
	
	
	public static class FPSCounter implements ActionListener {
        private final Timer resetTimer;
        private int current, last;

        public FPSCounter() {
            resetTimer = new Timer(1000, this);
        }

        public synchronized void start() {
            resetTimer.start();
            current = 0;
            last = -1;
        }

        public synchronized void stop() {
            resetTimer.stop();
            current = -1;
        }

        public synchronized void frame() {
            ++current;
        }

        @Override
        public synchronized void actionPerformed(final ActionEvent e) {
            last = current;
            current = 0;
        }

        public synchronized int get() {
            return last;
        }
    }
	
}
