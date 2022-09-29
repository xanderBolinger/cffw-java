package Conflict;

import java.awt.EventQueue;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ConflictLog implements Serializable {
	public String conflictLog = "Welcome you are the first to arrive... \n \n \n";
	private JTextPane textPaneLog;
	public String textToBeAdded = ""; 
	
	public ConflictLog(){  
        final JFrame f= new JFrame("Conflict Log");
        f.setSize(609,423);
        
        JScrollPane scrollPane = new JScrollPane();
        GroupLayout groupLayout = new GroupLayout(f.getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        			.addGap(10))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(11)
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
        			.addContainerGap())
        );
        
        textPaneLog = new JTextPane();
        scrollPane.setViewportView(textPaneLog);
        f.getContentPane().setLayout(groupLayout);
        f.setVisible(true);  
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

	
	public void refreshConflictLog() {
		textPaneLog.setText(conflictLog);
	} 
	
	public void addNewLine(String text) {
		conflictLog += "\n\n     " + text; 
		refreshConflictLog();
	}
	public void addToLine(String text) {
		conflictLog += text; 
		refreshConflictLog();
	}
	
	public void addNewLineToQueue(String text) {
		textToBeAdded += "\n" + text; 
		//refreshConflictLog();
	}
	public void addToLineInQueue(String text) {
		textToBeAdded += text; 
		//refreshConflictLog();
	}
	
	public void addQueuedText() {
		//System.out.println("Added Que To Log");
		conflictLog += textToBeAdded; 
		textToBeAdded = ""; 
		refreshConflictLog();
	}
	
}
