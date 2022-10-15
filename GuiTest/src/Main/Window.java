package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

	private JFrame frmCorditeExpansion;
	private JPanel map;
	private JPanel toolbar;
	private JPanel unitCard;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmCorditeExpansion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		frmCorditeExpansion = new JFrame();
		frmCorditeExpansion.setTitle("Cordite Expansion");
		frmCorditeExpansion.setFont(new Font("Calibri", Font.BOLD, 13));
		frmCorditeExpansion.setBackground(Color.WHITE);
		frmCorditeExpansion.setBounds(100, 100, 1000, 1000);
		frmCorditeExpansion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCorditeExpansion.getContentPane().setBackground(Colors.BLUE);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(2);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GroupLayout groupLayout = new GroupLayout(frmCorditeExpansion.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
		);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setDividerSize(2);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setDividerSize(2);
		splitPane_1.setRightComponent(splitPane_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMaximumSize(new Dimension(400, 32767));
		scrollPane.setMinimumSize(new Dimension(300, 100));
		splitPane_2.setLeftComponent(scrollPane);
		
		JList list = new JList();
		list.setFont(new Font("Tahoma", Font.PLAIN, 13));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Element -- Lorem Ispum Place holder Text INformation", "Element -- Lorem Ispum Place holder Text INformation", "Element -- Lorem Ispum Place holder Text INformation"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBackground(Colors.BLUE);
		list.setForeground(Colors.ORANGE);
		scrollPane.setViewportView(list);
		
		unitCard = new JPanel();
		unitCard.setMinimumSize(new Dimension(500, 500));
		unitCard.setBackground(Colors.BROWN);
		splitPane_2.setRightComponent(unitCard);
		
		map = new JPanel();
		map.setMinimumSize(new Dimension(1000, 500));
		
		map.setBackground(Colors.GRAY);
		splitPane_1.setLeftComponent(map);
		
		toolbar = new JPanel();
		toolbar.setMaximumSize(new Dimension(32767, 40));
		toolbar.setMinimumSize(new Dimension(1000, 40));
		toolbar.setBackground(Colors.BLUE);
		splitPane.setLeftComponent(toolbar);
		frmCorditeExpansion.getContentPane().setLayout(groupLayout);
		

		
	}

	
	
}
