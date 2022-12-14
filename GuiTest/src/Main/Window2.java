package Main;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JSplitPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;


public class Window2 extends JFrame {
	
	public static class Divider {
		
		public static int DIVIDER_EXPANDED_SIZE = 10; 
		public static int DIVIDER_COLLAPSED_SIZE = 2; 
		public static Border DIVIDER_EXPANDED_BORDER = BorderFactory.createLineBorder(Colors.SOFT_BLUE, 20);
		public static Border DIVIDER_COLLAPSED_BORDER = BorderFactory.createLineBorder(Colors.BACKGROUND, 2);
	}
	
	int tx = 0;
    int ty = 0;
    boolean fullscreen = false; 
    boolean clickedDivider = false;
    ToolbarButton exitButton;
    JSplitPane splitPane;
    private double zoom = 1.0; // zoom factor
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		 /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       /* try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window2 window = new Window2();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window2() {
		UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	initialize();
		    }
		});
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		new JFrame();
		
		String[][] data = {
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"},
	            {"sdvjdnvdfjvnsdvsdjvs", "sdvjdnvdfjvnsdvsdjvsdvkdsjvnbdvlkjvnbdkvdvkdjvvsnvlkdnv"}};
		
		ArrayList<String> strings = new ArrayList<>();
		
		for(String[] list : data) {
			for(String str : list) {
				strings.add(str);
			}
		}
		strings.add("second to last row");
		strings.add("last row");
		
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setPreferredSize(new Dimension(900, 1050));
		setMinimumSize(new Dimension(900, 1050));
		
		setUndecorated(true);// vanishing the default title bar
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Colors.BACKGROUND);
        getContentPane().setBackground(Colors.BACKGROUND);
        
        
        
        
        JPanel window = new JPanel();
        window.setPreferredSize(new Dimension(900, 1050));
        window.setMinimumSize(new Dimension(900, 1050));
        window.setBackground(Colors.BACKGROUND);
        window.setDoubleBuffered(true);
        getContentPane().add(window, BorderLayout.CENTER);

        splitPane = new JSplitPane();
        splitPane.setMaximumSize(new Dimension(900, 900));
        splitPane.setDividerSize(0);
        splitPane.setBackground(Colors.BACKGROUND);
        splitPane.setForeground(Colors.BACKGROUND);
        splitPane.setMinimumSize(new Dimension(850, 850));
        splitPane.setPreferredSize(new Dimension(900, 900));
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDoubleBuffered(true);
        JPanel toolbar = new JPanel();
        toolbar.setPreferredSize(new Dimension(10, 7));
        toolbar.setMinimumSize(new Dimension(1000, 35));
        toolbar.setBackground(Colors.BACKGROUND);
        splitPane.setLeftComponent(toolbar);
        
        exitButton = new ToolbarButton("");
        exitButton.setIcon(new ImageIcon(Window2.class.getResource("/images/icons8_Exit_25px.png")));
        GroupLayout gl_toolbar = new GroupLayout(toolbar);
        gl_toolbar.setHorizontalGroup(
        	gl_toolbar.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_toolbar.createSequentialGroup()
        			.addContainerGap(940, Short.MAX_VALUE)
        			.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
        );
        gl_toolbar.setVerticalGroup(
        	gl_toolbar.createParallelGroup(Alignment.LEADING)
        		.addComponent(exitButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE, Short.MAX_VALUE)
        );
        
        exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
        	
        });
        
        
        toolbar.setLayout(gl_toolbar);
        toolbar.addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mousePressed(MouseEvent m)
        	{
        		
        		System.out.println("Mouse Pressed");
        		
        	    tx = m.getX();
        	    ty = m.getY();
        	}
        	
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(e.getClickCount() != 2 || e.getButton() != MouseEvent.BUTTON1)
        			return;
        		
        		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        		
        		if(fullscreen) {
        			setExtendedState(JFrame.NORMAL); 
        			fullscreen = false; 
        			splitPane.setPreferredSize(new Dimension(1000, 1000));
        		} else {
        			setExtendedState(JFrame.MAXIMIZED_BOTH); 
        			fullscreen = true;
        			splitPane.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        			
        		}
        			
        	}
        	

        });
        toolbar.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				
				System.out.println("Mouse Dragged");
				if(!fullscreen)
					setLocation(e.getXOnScreen() - tx, e.getYOnScreen() -ty);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				
				//System.out.println("Moved");
				
				
			}

		});
        
        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(0, 0));
        panel.setPreferredSize(new Dimension(10000, 10000));
        panel.setMinimumSize(new Dimension(850, 850));
        panel.setDoubleBuffered(true);
        panel.setBackground(Colors.GRAY);        
        splitPane.setRightComponent(panel);
        
        JSplitPane splitPane_1 = new JSplitPane();
        splitPane_1.setPreferredSize(new Dimension(0, 0));
        splitPane_1.setDividerSize(2);
        splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
        			.addGap(0))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)
        );
        panel.setLayout(gl_panel);
        
        
        
        splitPane_1.setContinuousLayout(true);
        
        JPanel panel_1 = new JPanel();
        panel_1.setPreferredSize(new Dimension(0, 0));
        panel_1.setMinimumSize(new Dimension(400, 400));
        panel_1.setBackground(Colors.GRAY);
        splitPane_1.setRightComponent(panel_1);
        
        JSplitPane splitPane_2 = new JSplitPane();
        splitPane_2.setPreferredSize(new Dimension(0, 0));
        splitPane_2.setMinimumSize(new Dimension(0, 0));
        splitPane_2.setDividerSize(2);
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addComponent(splitPane_2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        gl_panel_1.setVerticalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addComponent(splitPane_2, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        );
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setMinimumSize(new Dimension(0, 0));
        splitPane_2.setLeftComponent(scrollPane);
        
        splitPane_2.setContinuousLayout(true);
        JList trooperList = new JList();
        trooperList.setFont(new Font("Tw Cen MT", Font.BOLD, 13));
        trooperList.setBackground(Colors.BACKGROUND_2);
        trooperList.setForeground(Colors.FOREGROUND);
        trooperList.setSelectionBackground(Colors.PINK);
        trooperList.setSelectionForeground(Colors.FOREGROUND);
        trooperList.setFocusable(false);
        trooperList.setModel(new AbstractListModel() {
        	String[] values = new String[] {"Element -- Example Character Name : SL 56%", "Element -- Example Character Name : SL 56%", "Element -- Example Character Name : SL 56%", "Element -- Example Character Name : SL 56%"};
        	public int getSize() {
        		return values.length;
        	}
        	public Object getElementAt(int index) {
        		return values[index];
        	}
        });
        scrollPane.setViewportView(trooperList);
        splitPane_2.setBorder(BorderFactory.createEmptyBorder());
        splitPane_2.setBackground(Colors.PURPLE);
        splitPane_2.setForeground(Colors.PURPLE);
        splitPane_2.setDoubleBuffered(true);
        
        panel_1.setLayout(gl_panel_1);
        BasicSplitPaneUI basicSplitPaneUI = (BasicSplitPaneUI)splitPane_1.getUI();
        BasicSplitPaneDivider divider = basicSplitPaneUI.getDivider();
        BasicSplitPaneUI basicSplitPaneUI2 = (BasicSplitPaneUI)splitPane_2.getUI();
        BasicSplitPaneDivider divider2 = basicSplitPaneUI2.getDivider();
        divider.setBorder(Divider.DIVIDER_COLLAPSED_BORDER);
        divider2.setBorder(Divider.DIVIDER_COLLAPSED_BORDER);
        divider.addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mousePressed(MouseEvent e) {

        		divider.setBorder(Divider.DIVIDER_EXPANDED_BORDER);
        		//System.out.println("Pass over");
        		//divider.resize(divider.getWidth(), 5);
        		splitPane_1.setDividerSize(Divider.DIVIDER_EXPANDED_SIZE);
        	}
        	
        	@Override
        	public void mouseReleased(MouseEvent e) {
        		//System.out.println("Pass exit");
        		divider.setBorder(Divider.DIVIDER_COLLAPSED_BORDER);
        		splitPane_1.setDividerSize(Divider.DIVIDER_COLLAPSED_SIZE);

        	}
        	
        	
        });
        divider2.addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mousePressed(MouseEvent e) {

        		divider2.setBorder(Divider.DIVIDER_EXPANDED_BORDER);
        		//System.out.println("Pass over");
        		//divider.resize(divider.getWidth(), 5);
        		splitPane_2.setDividerSize(Divider.DIVIDER_EXPANDED_SIZE);
        	}
        	
        	@Override
        	public void mouseReleased(MouseEvent e) {
        		//System.out.println("Pass exit");
        		divider2.setBorder(Divider.DIVIDER_COLLAPSED_BORDER);
        		splitPane_2.setDividerSize(Divider.DIVIDER_COLLAPSED_SIZE);

        	}
        	
        	
        });
        
       

        trooperList.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JSplitPane splitPane_2_1 = new JSplitPane();
        splitPane_2_1.setForeground(new Color(189, 147, 249));
        splitPane_2_1.setDoubleBuffered(true);
        splitPane_2_1.setDividerSize(2);
        splitPane_2_1.setContinuousLayout(true);
        splitPane_2_1.setBorder(BorderFactory.createEmptyBorder());
        splitPane_2_1.setBackground(new Color(189, 147, 249));
        splitPane_2.setRightComponent(splitPane_2_1);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setMinimumSize(new Dimension(400, 200));
        scrollPane_1.setBorder(BorderFactory.createEmptyBorder());
        splitPane_2_1.setLeftComponent(scrollPane_1);
        
        JList actionList = new JList();
        actionList.setSelectionForeground(new Color(248, 248, 242));
        actionList.setSelectionBackground(new Color(255, 121, 198));
        actionList.setForeground(new Color(248, 248, 242));
        actionList.setFont(new Font("Tw Cen MT", Font.BOLD, 13));
        actionList.setFocusable(false);
        actionList.setBorder(BorderFactory.createEmptyBorder());
        actionList.setBackground(new Color(40, 42, 54));
        scrollPane_1.setViewportView(actionList);
        
        JScrollPane scrollPane_1_1 = new JScrollPane();
        scrollPane_1_1.setBorder(BorderFactory.createEmptyBorder());
        splitPane_2_1.setRightComponent(scrollPane_1_1);
        
        JList detailsList = new JList();
        setList(detailsList, strings);
        detailsList.setSelectionForeground(new Color(248, 248, 242));
        detailsList.setSelectionBackground(new Color(255, 121, 198));
        detailsList.setForeground(new Color(248, 248, 242));
        detailsList.setFont(new Font("Tw Cen MT", Font.BOLD, 13));
        detailsList.setFocusable(false);
        detailsList.setBorder(BorderFactory.createEmptyBorder());
        detailsList.setBackground(new Color(40, 42, 54));
        scrollPane_1_1.setViewportView(detailsList);
        
        JPanel panel_3 = new JPanel();
        panel_3.setMinimumSize(new Dimension(1000, 400));
        panel_3.setPreferredSize(new Dimension(1000, 700));
        splitPane_1.setLeftComponent(panel_3);
        
        HexGrid map = new HexGrid(33, 33);
        map.setPreferredSize(new Dimension(1000, 650));
        map.setMinimumSize(new Dimension(1000, 400));
        map.setFont(new Font("Tw Cen MT", Font.BOLD, 13));
        map.setBackground(new Color(68, 71, 90));
        GroupLayout gl_map = new GroupLayout(map);
        gl_map.setHorizontalGroup(
        	gl_map.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 1000, Short.MAX_VALUE)
        );
        gl_map.setVerticalGroup(
        	gl_map.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 700, Short.MAX_VALUE)
        );
        map.setLayout(gl_map);
        GroupLayout gl_panel_3 = new GroupLayout(panel_3);
        gl_panel_3.setHorizontalGroup(
        	gl_panel_3.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_3.createSequentialGroup()
        			.addComponent(map, GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        			.addGap(0))
        );
        gl_panel_3.setVerticalGroup(
        	gl_panel_3.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_3.createSequentialGroup()
        			.addComponent(map, GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE)
        			.addGap(0))
        );
        panel_3.setLayout(gl_panel_3);
        GroupLayout gl_window = new GroupLayout(window);
        gl_window.setHorizontalGroup(
        	gl_window.createParallelGroup(Alignment.LEADING)
        		.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        gl_window.setVerticalGroup(
        	gl_window.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_window.createSequentialGroup()
        			.addGap(5)
        			.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        window.setLayout(gl_window);
        
        splitPane_2_1.setBorder(BorderFactory.createEmptyBorder());
        splitPane_2_1.setBackground(Colors.PURPLE);
        splitPane_2_1.setForeground(Colors.PURPLE);
        splitPane_2_1.setDoubleBuffered(true);
        BasicSplitPaneUI basicSplitPaneUI3 = (BasicSplitPaneUI)splitPane_2_1.getUI();
        BasicSplitPaneDivider divider3 = basicSplitPaneUI3.getDivider();
        divider3.addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mousePressed(MouseEvent e) {

        		divider3.setBorder(Divider.DIVIDER_EXPANDED_BORDER);
        		//System.out.println("Pass over");
        		//divider.resize(divider.getWidth(), 5);
        		splitPane_2_1.setDividerSize(Divider.DIVIDER_EXPANDED_SIZE);
        	}
        	
        	@Override
        	public void mouseReleased(MouseEvent e) {
        		//System.out.println("Pass exit");
        		divider3.setBorder(Divider.DIVIDER_COLLAPSED_BORDER);
        		splitPane_2_1.setDividerSize(Divider.DIVIDER_COLLAPSED_SIZE);

        	}
        	
        	
        });
        divider3.setBorder(Divider.DIVIDER_COLLAPSED_BORDER);
		splitPane_2_1.setDividerSize(Divider.DIVIDER_COLLAPSED_SIZE);
		
        map.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				map.mouseReleased(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {

				map.mousePressed(e);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getButton() == e.BUTTON1) {
					map.mouseLeftClick(e);
				} else if (e.getButton() == e.BUTTON3) {
					map.mouseRightClick(e);
				}
			}

		});

        map.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				//panelMouseDragged(e);
				// System.out.println("Dragged");
				map.mouseDragged(e);

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// System.out.println("Mouse Moved");
				//panelMouseMoved(e);
				map.mouseMoved(e);
			}

		});

        map.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int notches = e.getWheelRotation();
				double temp = zoom - (notches * 0.2);
				// minimum zoom factor is 1.0
				temp = Math.max(temp, 1.0);
				if (temp != zoom) {
					zoom = temp;
					map.mouseWheelMoved(zoom);
					// resizeImage();
				}
			}
		});
        
        
        
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        
	}

	class ToolbarButton extends JButton {

        private Color hoverBackgroundColor = Colors.BRIGHT_RED;
        private Color pressedBackgroundColor = Colors.BRIGHT_RED;

        public ToolbarButton() {
            this(null);
        }

        public ToolbarButton(String text) {
            super(text);
            super.setContentAreaFilled(false);
            initializeButton();
        }
        
        public void initializeButton() {
        	setBackground(null);
            setBorder(BorderFactory.createEmptyBorder());
            addMouseListener(new MouseAdapter() 
            {
               public void mouseEntered(MouseEvent evt) 
               {
            	   setBackground(Colors.ORANGE);
               }
               public void mouseExited(MouseEvent evt) 
               {
            	   setBackground(null);
               }
            });
            setFocusable(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(pressedBackgroundColor);
            } else if (getModel().isRollover()) {
                g.setColor(hoverBackgroundColor);
            } else {
                g.setColor(getBackground());
            }
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        @Override
        public void setContentAreaFilled(boolean b) {
        }

        public Color getHoverBackgroundColor() {
            return hoverBackgroundColor;
        }



        public Color getPressedBackgroundColor() {
            return pressedBackgroundColor;
        }


    }
	
	public class MyListCellRenderer implements ListCellRenderer {

	    private final JLabel jlblCell = new JLabel(" ", JLabel.LEFT);
	    Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
	    Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);

	    @Override
	    public Component getListCellRendererComponent(JList jList, Object value, 
	            int index, boolean isSelected, boolean cellHasFocus) {

	        jlblCell.setOpaque(true);

	        if (isSelected) {
	            jlblCell.setForeground(jList.getSelectionForeground());
	            jlblCell.setBackground(jList.getSelectionBackground());
	            jlblCell.setBorder(new LineBorder(Colors.PINK));
	        } else {
	            jlblCell.setForeground(jList.getForeground());
	            jlblCell.setBackground(jList.getBackground());
	        }

	        jlblCell.setBorder(cellHasFocus ? lineBorder : emptyBorder);

	        return jlblCell;
	    }
	}
	
	public static void setList(JList list, ArrayList<String> content) {
		DefaultListModel model = new DefaultListModel();

		for (String str : content) {

			model.addElement(str);

		}
		
		list.setModel(model);
	}
}
