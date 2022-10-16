package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class Window extends JFrame {

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
					window.setVisible(true);
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
		getContentPane().setSize(new Dimension(1000, 1000));
		getContentPane().setMinimumSize(new Dimension(1000, 1000));
		initialize();
	}

	
	private void initialize() {


		setTitle("TLH - Cordite Expansion");
		
		setFont(new Font("Calibri", Font.BOLD, 13));
		setBackground(Color.WHITE);
		setBounds(100, 100, 1000, 1148);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Colors.BACKGROUND);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(2);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(splitPane,
				GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(splitPane,
				GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE));

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setDividerSize(2);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setDividerSize(2);
		splitPane_1.setRightComponent(splitPane_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(2, 300));
		scrollPane.setMaximumSize(new Dimension(400, 32767));
		scrollPane.setMinimumSize(new Dimension(450, 100));
		splitPane_2.setLeftComponent(scrollPane);

		JList list = new JList();
		list.setFont(new Font("Tahoma", Font.PLAIN, 13));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] { "Element -- Lorem Ispum Place holder Text INformation",
					"Element -- Lorem Ispum Place holder Text INformation",
					"Element -- Lorem Ispum Place holder Text INformation" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBackground(Colors.BACKGROUND);
		list.setForeground(Colors.ORANGE);
		
		scrollPane.setViewportView(list);

		unitCard = new JPanel();
		unitCard.setPreferredSize(new Dimension(500, 300));
		unitCard.setMinimumSize(new Dimension(500, 300));
		unitCard.setBackground(Colors.BROWN);
		splitPane_2.setRightComponent(unitCard);

		map = new JPanel();
		map.setMinimumSize(new Dimension(1000, 800));

		map.setBackground(Colors.GRAY);
		splitPane_1.setLeftComponent(map);

		toolbar = new JPanel();
		toolbar.setMaximumSize(new Dimension(32767, 40));
		toolbar.setMinimumSize(new Dimension(1000, 40));
		toolbar.setBackground(Colors.BACKGROUND);
		splitPane.setLeftComponent(toolbar);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setFocusable(false);
		btnNewButton.setFocusTraversalKeysEnabled(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setToolTipText("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new DialogBox("test");
				dispose();
			}
		});
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	btnNewButton.setBackground(Colors.ORANGE);
		    	btnNewButton.setOpaque(true);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	btnNewButton.setOpaque(false);
		    }
		});
		btnNewButton.setIcon(new ImageIcon(Window.class.getResource("/images/icons8_Exit_25px.png")));
		btnNewButton.setOpaque(false);
		GroupLayout gl_toolbar = new GroupLayout(toolbar);
		gl_toolbar.setHorizontalGroup(
			gl_toolbar.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_toolbar.createSequentialGroup()
					.addContainerGap(935, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_toolbar.setVerticalGroup(
			gl_toolbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolbar.createSequentialGroup()
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		toolbar.setLayout(gl_toolbar);
		getContentPane().setLayout(groupLayout);
			
        setUndecorated(true);// vanishing the default title bar
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Colors.BACKGROUND);
        getContentPane().setBackground(Colors.BACKGROUND);
        
        pack();
        setVisible(true);
        
	}

	// Theme Class to Change the default color to green color
	/*class changeTheme extends DarculaMetalTheme {
	    public ColorUIResource getWindowTitleInactiveBackground() {
	      return new ColorUIResource(Colors.ORANGE);
	    }
	  
	    public ColorUIResource getWindowTitleBackground() {
	      return new ColorUIResource(Colors.ORANGE);
	    }
	  
	    public ColorUIResource getPrimaryControlHighlight() {
	      return new ColorUIResource(Colors.GREEN);
	    }
	  
	    public ColorUIResource getPrimaryControlDarkShadow() {
	      return new ColorUIResource(Colors.BROWN);
	    }
	  
	    public ColorUIResource getPrimaryControl() {
	      return new ColorUIResource(Colors.ORANGE);
	    }
	  
	    public ColorUIResource getControlHighlight() {
	      return new ColorUIResource(Colors.GREEN);
	    }
	  
	    public ColorUIResource getControlDarkShadow() {
	      return new ColorUIResource(Colors.BROWN);
	    }
	  
	    public ColorUIResource getControl() {
	      return new ColorUIResource(Colors.GRAY);
	    }
	}*/
	
}
