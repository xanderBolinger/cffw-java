package Main;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;


public class Window2 extends JFrame {
	
	public static class Divider {
		
		public static int DIVIDER_EXPANDED_SIZE = 10; 
		public static int DIVIDER_COLLAPSED_SIZE = 1; 
		public static Border DIVIDER_EXPANDED_BORDER = BorderFactory.createLineBorder(Colors.SOFT_BLUE, 20);
		public static Border DIVIDER_COLLAPSED_BORDER = BorderFactory.createLineBorder(Colors.BACKGROUND, 5);
	}
	
	int tx = 0;
    int ty = 0;
    boolean fullscreen = false; 
    ToolbarButton exitButton;
    JSplitPane splitPane;
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
		
		
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setSize(new Dimension(1000, 1000));
		getContentPane().setPreferredSize(new Dimension(1000, 1000));
		getContentPane().setMinimumSize(new Dimension(1000, 1000));
		setMinimumSize(new Dimension(1000, 1000));
		setSize(new Dimension(1000, 1000));
		
		setUndecorated(true);// vanishing the default title bar
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Colors.BACKGROUND);
        getContentPane().setBackground(Colors.BACKGROUND);
        
        
        
        
        JPanel window = new JPanel();
        window.setMinimumSize(new Dimension(1000, 1000));
        window.setBackground(Colors.BACKGROUND);
        window.setDoubleBuffered(true);
        getContentPane().add(window, BorderLayout.NORTH);

        splitPane = new JSplitPane();
        splitPane.setDividerSize(1);
        splitPane.setBackground(Colors.BACKGROUND);
        splitPane.setForeground(Colors.BACKGROUND);
        splitPane.setMinimumSize(new Dimension(1000, 1000));
        splitPane.setPreferredSize(new Dimension(1000, 1000));
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDoubleBuffered(true);
        BasicSplitPaneUI basicSplitPaneUI = (BasicSplitPaneUI)splitPane.getUI();
        BasicSplitPaneDivider divider = basicSplitPaneUI.getDivider();
        divider.setBorder(BorderFactory.createLineBorder(Colors.BACKGROUND, 5));
        splitPane.setContinuousLayout(true);
        divider.addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mousePressed(MouseEvent e) {
        		splitPane.setBackground(Colors.SOFT_BLUE);
                splitPane.setForeground(Colors.SOFT_BLUE);
                divider.setBorder(Divider.DIVIDER_EXPANDED_BORDER);
        		//System.out.println("Pass over");
        		//divider.resize(divider.getWidth(), 5);
        		divider.setDividerSize(Divider.DIVIDER_EXPANDED_SIZE);
        	}
        	
        	@Override
        	public void mouseReleased(MouseEvent e) {
        		//System.out.println("Pass exit");
        		//divider.resize(divider.getWidth(), 1);
        		divider.setBorder(Divider.DIVIDER_COLLAPSED_BORDER);
        		splitPane.setBackground(Colors.BACKGROUND);
                splitPane.setForeground(Colors.BACKGROUND);
        		divider.setDividerSize(Divider.DIVIDER_COLLAPSED_SIZE);

        	}
        });
        
        divider.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				
				//System.out.println("Dragged");

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				
				//System.out.println("Moved");
				
				
			}

		});
        
        window.add(splitPane);
        JPanel toolbar = new JPanel();
        toolbar.setMinimumSize(new Dimension(1000, 50));
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
        panel.setDoubleBuffered(true);
        panel.setBackground(Colors.GRAY);        
        splitPane.setRightComponent(panel);
        
        
        
        
        
        
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
}
