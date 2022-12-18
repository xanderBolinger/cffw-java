package CorditeExpansion;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static org.junit.Assert.assertEquals;

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

import CeHexGrid.CeHexGrid;
import CeHexGrid.Colors;
import CeHexGrid.Chit.Facing;
import CorditeExpansionActions.CeAction;
import Items.Armor;
import Items.Armor.ArmorType;
import Items.Container.ContainerType;
import Items.Item.ItemType;
import Trooper.Trooper;
import UtilityClasses.SwingUtility;

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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;

public class CorditeExpansionWindow extends JFrame {

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

	public static JList trooperList;
	public static JList actionList;
	static JList detailsList;

	public static void main(String[] args) {

		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		/*
		 * try { for (javax.swing.UIManager.LookAndFeelInfo info :
		 * javax.swing.UIManager.getInstalledLookAndFeels()) { if
		 * ("Nimbus".equals(info.getName())) {
		 * javax.swing.UIManager.setLookAndFeel(info.getClassName()); break; } } } catch
		 * (ClassNotFoundException ex) {
		 * java.util.logging.Logger.getLogger(Window2.class.getName()).log(java.util.
		 * logging.Level.SEVERE, null, ex); } catch (InstantiationException ex) {
		 * java.util.logging.Logger.getLogger(Window2.class.getName()).log(java.util.
		 * logging.Level.SEVERE, null, ex); } catch (IllegalAccessException ex) {
		 * java.util.logging.Logger.getLogger(Window2.class.getName()).log(java.util.
		 * logging.Level.SEVERE, null, ex); } catch
		 * (javax.swing.UnsupportedLookAndFeelException ex) {
		 * java.util.logging.Logger.getLogger(Window2.class.getName()).log(java.util.
		 * logging.Level.SEVERE, null, ex); }
		 */

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CorditeExpansionWindow window = new CorditeExpansionWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CorditeExpansionWindow() {
		UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				
				ActionOrder actionOrder = new ActionOrder();
				CorditeExpansionGame.actionOrder = actionOrder;
				
				try {
					fourSwMilitia();
					//omegaSquad();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				/*Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
				clone.wep = "RGD-5";
				try {
					clone.inventory.addItem(ItemType.M870);
					clone.inventory.addItem(ItemType.M870, ItemType.SmallArmsAmmo);
					clone.inventory.addItem(ItemType.M870, ItemType.SmallArmsAmmo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Trooper cloneMarksman = new Trooper("Clone Marksman", "Clone Trooper Phase
				// 1");
				// Trooper b1 = new Trooper("B1 Rifleman", "CIS Battle Droid");

				

				actionOrder.addTrooper(clone);
				// actionOrder.addTrooper(cloneMarksman);
				// actionOrder.addTrooper(b1);

				clone.ceStatBlock.chit.facing = Facing.D;

				ArrayList<Cord> cords = new ArrayList<>();
				cords.add(new Cord(1, 0));
				cords.add(new Cord(2, 0));
				cords.add(new Cord(3, 0));

				CeAction.addMoveAction(clone.ceStatBlock, cords, 2);
				
				Trooper b1 = new Trooper("B1 Rifleman", "CIS Battle Droid");
				b1.armor = new Armor();
				actionOrder.addTrooper(b1);
				b1.ceStatBlock.cord = new Cord(4, 0);
				b1.ceStatBlock.chit.xCord = 4;
				b1.ceStatBlock.chit.facing = Facing.A;*/

				// actionOrder.addTrooper(cloneMarksman);
				// actionOrder.addTrooper(b1);
				
				// clone.ceStatBlock.chit.facing = Facing.BC;
				// clone.ceStatBlock.chit.facing = Facing.B;

				initialize();
			}
		});

	}
	
	public void fourSwMilitia() throws Exception {
ActionOrder ao = CorditeExpansionGame.actionOrder;
		
		Trooper m1 = new Trooper("Militia", "Cordite Expansion");
		m1.wep = "DC15A";
		m1.inventory.addItem(ItemType.DC15A);
		m1.inventory.addItem(ItemType.DC15A, ItemType.SmallArmsAmmo);
		m1.name = "M1";
		
		Trooper m2 = new Trooper("Militia", "Cordite Expansion");
		m2.wep = "A310";
		m2.inventory.addItem(ItemType.A310);
		m2.inventory.addItem(ItemType.A310, ItemType.SmallArmsAmmo);
		m2.name = "M2";
		System.out.println("Items: "+m2.inventory.getItems());
		
		
		Trooper m3 = new Trooper("Militia", "Cordite Expansion");
		m3.wep = "EE3";
		m3.inventory.addItem(ItemType.EE3);
		m3.inventory.addItem(ItemType.EE3, ItemType.SmallArmsAmmo);
		m3.name = "M3";
		
		Trooper m4 = new Trooper("Militia", "Cordite Expansion");
		m4.inventory.addItem(ItemType.A310);
		m4.wep = "A310";
		m4.inventory.addItem(ItemType.A310, ItemType.SmallArmsAmmo);
		m4.name = "M4";
		
		ao.addTrooper(m1);
		ao.addTrooper(m2);
		ao.addTrooper(m3);
		//ao.addTrooper(m4);
	}
	
	public void omegaSquad() throws Exception {
		
		ActionOrder ao = CorditeExpansionGame.actionOrder;
		
		Trooper e1 = new Trooper("Elite", "Cordite Expansion");
		e1.wep = "DC17m";
		e1.inventory.addItem(ItemType.DC17M);
		e1.inventory.addItem(ItemType.DC17M, ItemType.SmallArmsAmmo);
		//e1.inventory.addItem(ItemType.DC17M, ItemType.SmallArmsAmmo);
		e1.name = "Darman";
		e1.armor = new Armor(ArmorType.KATARN);
		
		
		Trooper e2 = new Trooper("Elite", "Cordite Expansion");
		e2.wep = "DC17m";
		e2.inventory.addItem(ItemType.DC17M);
		e2.inventory.addItem(ItemType.DC17M, ItemType.SmallArmsAmmo);
		e2.inventory.addItem(ItemType.DC17M, ItemType.SmallArmsAmmo);
		e2.name = "Fi";
		e2.armor = new Armor(ArmorType.KATARN);
		
		Trooper e3 = new Trooper("Elite", "Cordite Expansion");
		e3.wep = "DC17m";
		e3.inventory.addItem(ItemType.DC17M);
		e3.inventory.addItem(ItemType.DC17M, ItemType.SmallArmsAmmo);
		e3.inventory.addItem(ItemType.DC17M, ItemType.SmallArmsAmmo);
		e3.name = "Niner";
		e3.armor = new Armor(ArmorType.KATARN);
		
		Trooper e4 = new Trooper("Elite", "Cordite Expansion");
		e4.wep = "DC17m";
		e4.inventory.addItem(ItemType.DC17M);
		e4.inventory.addItem(ItemType.DC17M, ItemType.SmallArmsAmmo);
		e4.inventory.addItem(ItemType.DC17M, ItemType.SmallArmsAmmo);
		e4.name = "Atin";
		e4.armor = new Armor(ArmorType.KATARN);
		
		ao.addTrooper(e1);
		ao.addTrooper(e2);
		ao.addTrooper(e3);
		ao.addTrooper(e4);
	}

	public CorditeExpansionWindow(ArrayList<Trooper> troopers) {
		UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ActionOrder actionOrder = new ActionOrder();
				CorditeExpansionGame.actionOrder = actionOrder;
				
				try {
					fourSwMilitia();
					//omegaSquad();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				for(int i = 0; i < troopers.size(); i++) {
					Trooper trooper = troopers.get(i);
					actionOrder.addTrooper(trooper);
					trooper.ceStatBlock.cord = new Cord(0, i);
					trooper.ceStatBlock.chit.xCord = 0;
					trooper.ceStatBlock.chit.yCord = i; 
					trooper.ceStatBlock.chit.facing = Facing.A;
				}

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

		getContentPane().setSize(new Dimension(1005, 1005));
		getContentPane().setPreferredSize(new Dimension(1005, 1005));
		getContentPane().setMinimumSize(new Dimension(1000, 1000));
		setMinimumSize(new Dimension(1000, 1000));
		setSize(new Dimension(1005, 1005));

		setUndecorated(true);// vanishing the default title bar
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBackground(Colors.BACKGROUND);
		getContentPane().setBackground(Colors.BACKGROUND);

		CeKeyListener.addKeyListeners(this);

		JPanel window = new JPanel();
		window.setPreferredSize(new Dimension(1005, 1005));
		window.setMinimumSize(new Dimension(1000, 990));
		window.setBackground(Colors.BACKGROUND);
		window.setDoubleBuffered(true);
		getContentPane().add(window, BorderLayout.CENTER);

		splitPane = new JSplitPane();
		splitPane.setDividerSize(0);
		splitPane.setBackground(Colors.BACKGROUND);
		splitPane.setForeground(Colors.BACKGROUND);
		splitPane.setMinimumSize(new Dimension(1000, 990));
		splitPane.setPreferredSize(new Dimension(1005, 1005));
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDoubleBuffered(true);
		JPanel toolbar = new JPanel();
		toolbar.setMinimumSize(new Dimension(1000, 50));
		toolbar.setBackground(Colors.BACKGROUND);
		splitPane.setLeftComponent(toolbar);

		exitButton = new ToolbarButton("");
		exitButton.setIcon(new ImageIcon(CorditeExpansionWindow.class.getResource("/images/icons8_Exit_25px.png")));
		GroupLayout gl_toolbar = new GroupLayout(toolbar);
		gl_toolbar.setHorizontalGroup(gl_toolbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolbar.createSequentialGroup().addContainerGap(940, Short.MAX_VALUE)
						.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)));
		gl_toolbar.setVerticalGroup(gl_toolbar.createParallelGroup(Alignment.LEADING).addComponent(exitButton,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE, Short.MAX_VALUE));

		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}

		});

		toolbar.setLayout(gl_toolbar);
		toolbar.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent m) {

				//System.out.println("Mouse Pressed");

				tx = m.getX();
				ty = m.getY();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2 || e.getButton() != MouseEvent.BUTTON1)
					return;

				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

				if (fullscreen) {
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

				//System.out.println("Mouse Dragged");
				if (!fullscreen)
					setLocation(e.getXOnScreen() - tx, e.getYOnScreen() - ty);
			}

			@Override
			public void mouseMoved(MouseEvent e) {

				// System.out.println("Moved");

			}

		});

		JPanel panel = new JPanel();
		panel.setDoubleBuffered(true);
		panel.setBackground(Colors.GRAY);
		splitPane.setRightComponent(panel);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setDividerSize(2);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(splitPane_1));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(splitPane_1,
				GroupLayout.DEFAULT_SIZE, 948, Short.MAX_VALUE));
		panel.setLayout(gl_panel);

		splitPane_1.setContinuousLayout(true);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Colors.GRAY);
		splitPane_1.setRightComponent(panel_1);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setDividerSize(2);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addComponent(splitPane_2,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addComponent(splitPane_2,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(400, 200));
		splitPane_2.setLeftComponent(scrollPane);

		splitPane_2.setContinuousLayout(true);
		trooperList = new JList();
		trooperList.setFont(new Font("Tw Cen MT", Font.BOLD, 13));
		trooperList.setBackground(Colors.BACKGROUND_2);
		trooperList.setForeground(Colors.FOREGROUND);
		trooperList.setSelectionBackground(Colors.PINK);
		trooperList.setSelectionForeground(Colors.FOREGROUND);
		trooperList.setFocusable(false);
		trooperList.setModel(new AbstractListModel() {
			String[] values = new String[] { "Element -- Example Character Name : SL 56%",
					"Element -- Example Character Name : SL 56%", "Element -- Example Character Name : SL 56%",
					"Element -- Example Character Name : SL 56%" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		trooperList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (trooperList.getSelectedIndex() < 0)
					return;

				CorditeExpansionGame.selectedTrooper = CorditeExpansionGame.actionOrder
						.get(trooperList.getSelectedIndex());

				refreshCeLists();

			}
		});
		scrollPane.setViewportView(trooperList);
		splitPane_2.setBorder(BorderFactory.createEmptyBorder());
		splitPane_2.setBackground(Colors.PURPLE);
		splitPane_2.setForeground(Colors.PURPLE);
		splitPane_2.setDoubleBuffered(true);

		panel_1.setLayout(gl_panel_1);
		BasicSplitPaneUI basicSplitPaneUI = (BasicSplitPaneUI) splitPane_1.getUI();
		BasicSplitPaneDivider divider = basicSplitPaneUI.getDivider();
		BasicSplitPaneUI basicSplitPaneUI2 = (BasicSplitPaneUI) splitPane_2.getUI();
		BasicSplitPaneDivider divider2 = basicSplitPaneUI2.getDivider();
		divider.setBorder(Divider.DIVIDER_COLLAPSED_BORDER);
		divider2.setBorder(Divider.DIVIDER_COLLAPSED_BORDER);
		divider.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				divider.setBorder(Divider.DIVIDER_EXPANDED_BORDER);
				// System.out.println("Pass over");
				// divider.resize(divider.getWidth(), 5);
				splitPane_1.setDividerSize(Divider.DIVIDER_EXPANDED_SIZE);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// System.out.println("Pass exit");
				divider.setBorder(Divider.DIVIDER_COLLAPSED_BORDER);
				splitPane_1.setDividerSize(Divider.DIVIDER_COLLAPSED_SIZE);

			}

		});
		divider2.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				divider2.setBorder(Divider.DIVIDER_EXPANDED_BORDER);
				// System.out.println("Pass over");
				// divider.resize(divider.getWidth(), 5);
				splitPane_2.setDividerSize(Divider.DIVIDER_EXPANDED_SIZE);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// System.out.println("Pass exit");
				divider2.setBorder(Divider.DIVIDER_COLLAPSED_BORDER);
				splitPane_2.setDividerSize(Divider.DIVIDER_COLLAPSED_SIZE);

			}

		});

		trooperList.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		JSplitPane splitPane_2_1 = new JSplitPane();
		splitPane_2_1.setPreferredSize(new Dimension(0, 0));
        splitPane_2_1.setMinimumSize(new Dimension(0, 0));
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

		actionList = new JList();
		actionList.setSelectionForeground(new Color(248, 248, 242));
		actionList.setSelectionBackground(new Color(255, 121, 198));
		actionList.setForeground(new Color(248, 248, 242));
		actionList.setFont(new Font("Tw Cen MT", Font.BOLD, 13));
		actionList.setFocusable(false);
		actionList.setBorder(BorderFactory.createEmptyBorder());
		actionList.setBackground(new Color(40, 42, 54));
		actionList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (actionList.getSelectedIndex() < 0)
					return;

				detailsList.clearSelection();
				detailsList.setSelectedIndex(-1);

			}
		});
		scrollPane_1.setViewportView(actionList);

		JScrollPane scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setPreferredSize(new Dimension(0, 0));
		scrollPane_1_1.setMinimumSize(new Dimension(0, 0));
		scrollPane_1_1.setBorder(BorderFactory.createEmptyBorder());
		splitPane_2_1.setRightComponent(scrollPane_1_1);

		detailsList = new JList();
		detailsList.setSelectionForeground(new Color(248, 248, 242));
		detailsList.setSelectionBackground(new Color(255, 121, 198));
		detailsList.setForeground(new Color(248, 248, 242));
		detailsList.setFont(new Font("Tw Cen MT", Font.BOLD, 13));
		detailsList.setFocusable(false);
		detailsList.setBorder(BorderFactory.createEmptyBorder());
		detailsList.setBackground(new Color(40, 42, 54));
		detailsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (detailsList.getSelectedIndex() < 0)
					return;

				int index = detailsList.getSelectedIndex();
				
				actionList.clearSelection();
				actionList.setSelectedIndex(-1);

				if(detailsList.getSelectedIndex() == 6) {
					CorditeExpansionGame.selectedTrooper.ceStatBlock.cycleActingStatu();
				} else if(detailsList.getSelectedIndex() == 7) {
					CorditeExpansionGame.selectedTrooper.ceStatBlock.toggleCover();
				} else if(detailsList.getSelectedIndex() == 9) {
					CorditeExpansionGame.selectedTrooper.ceStatBlock.toggleAiming();
				} else if(detailsList.getSelectedIndex() == 10) {
					CorditeExpansionGame.selectedTrooper.ceStatBlock.toggleFullauto();
				} else if(detailsList.getSelectedIndex() == 11) {
					CorditeExpansionGame.selectedTrooper.ceStatBlock.cycleShotTarget();
				}  else if(detailsList.getSelectedIndex() == 12) {
					CorditeExpansionGame.selectedTrooper.ceStatBlock.toggleSpendSuccess();;
				}  else if(detailsList.getSelectedIndex() == 13) {
					CorditeExpansionGame.selectedTrooper.ceStatBlock.cycleSpendSuccessNumber();
				} else if(detailsList.getSelectedIndex() == 14) {
					CorditeExpansionGame.selectedTrooper.ceStatBlock.rangedStatBlock.suppression.cycleSuppressionStatus();
				}
				
				refreshCeDetailsList(CorditeExpansionGame.selectedTrooper);
				
				if(index != 6 && index != 7 && index != 9 && index != 10 && index != 11 &&  index != 12 &&  index != 13 && 
						index < detailsList.getModel().getSize() - 1 ) {
					detailsList.setSelectedIndex(index);
				}
				
			}
		});
		scrollPane_1_1.setViewportView(detailsList);

		JPanel panel_3 = new JPanel();
		panel_3.setMinimumSize(new Dimension(1000, 400));
		panel_3.setPreferredSize(new Dimension(1000, 700));
		splitPane_1.setLeftComponent(panel_3);

		CeHexGrid map = new CeHexGrid(33, 33);
		map.setPreferredSize(new Dimension(1000, 700));
		map.setMinimumSize(new Dimension(1000, 400));
		map.setFont(new Font("Tw Cen MT", Font.BOLD, 13));
		map.setBackground(new Color(68, 71, 90));
		GroupLayout gl_map = new GroupLayout(map);
		gl_map.setHorizontalGroup(gl_map.createParallelGroup(Alignment.LEADING).addGap(0, 1000, Short.MAX_VALUE));
		gl_map.setVerticalGroup(gl_map.createParallelGroup(Alignment.LEADING).addGap(0, 700, Short.MAX_VALUE));
		map.setLayout(gl_map);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_3
				.createSequentialGroup().addComponent(map, GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE).addGap(0)));
		gl_panel_3.setVerticalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_3
				.createSequentialGroup().addComponent(map, GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE).addGap(0)));
		panel_3.setLayout(gl_panel_3);
		GroupLayout gl_window = new GroupLayout(window);
		gl_window.setHorizontalGroup(gl_window.createParallelGroup(Alignment.LEADING).addComponent(splitPane,
				GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		gl_window.setVerticalGroup(
				gl_window.createParallelGroup(Alignment.LEADING).addGroup(gl_window.createSequentialGroup().addGap(5)
						.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		window.setLayout(gl_window);

		splitPane_2_1.setBorder(BorderFactory.createEmptyBorder());
		splitPane_2_1.setBackground(Colors.PURPLE);
		splitPane_2_1.setForeground(Colors.PURPLE);
		splitPane_2_1.setDoubleBuffered(true);
		BasicSplitPaneUI basicSplitPaneUI3 = (BasicSplitPaneUI) splitPane_2_1.getUI();
		BasicSplitPaneDivider divider3 = basicSplitPaneUI3.getDivider();
		divider3.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				divider3.setBorder(Divider.DIVIDER_EXPANDED_BORDER);
				// System.out.println("Pass over");
				// divider.resize(divider.getWidth(), 5);
				splitPane_2_1.setDividerSize(Divider.DIVIDER_EXPANDED_SIZE);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// System.out.println("Pass exit");
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
				// panelMouseDragged(e);
				// System.out.println("Dragged");
				map.mouseDragged(e);

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// System.out.println("Mouse Moved");
				// panelMouseMoved(e);
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
		
		
		
		hideScrollBars(scrollPane);
		hideScrollBars(scrollPane_1);
		hideScrollBars(scrollPane_1_1);

		refreshTrooperList();

		setResizable(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public void hideScrollBars(JScrollPane scrollPane) {
		//scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
		scrollPane.getViewport().setBorder(null);
		scrollPane.setViewportBorder(null);
		scrollPane.setBorder(null);
	}
	

	public static void refreshTrooperList() {

		ArrayList<String> troopers = new ArrayList<>();

		for (Trooper trooper : CorditeExpansionGame.actionOrder.getOrder()) {
			troopers.add(trooper.toStringCe());
		}

		SwingUtility.setList(trooperList, troopers);

	}

	public static void refreshCeLists() {

		Trooper trooper = CorditeExpansionGame.selectedTrooper;

		refreshCoacList(trooper);
		refreshCeDetailsList(trooper);

	}

	public static void refreshCoacList(Trooper trooper) {
		
		int selectedIndex = actionList.getSelectedIndex();
		
		ArrayList<String> actions = new ArrayList<>();

		if (trooper != null) {
			for (CeAction action : trooper.ceStatBlock.getCoac()) {
				actions.add(action.toString() + " PREPARED: "+action.ready());
			}

		}

		SwingUtility.setList(actionList, actions);
		
		if(selectedIndex < actionList.getModel().getSize())
			actionList.setSelectedIndex(selectedIndex);
		
	}

	public static void refreshCeDetailsList(Trooper trooper) {
		
		ArrayList<String> ceStats = new ArrayList<>();

		if (trooper != null) {
			
			ceStats = trooper.ceStatBlock.getCeStatList();
		}
		for(int i = 0; i < 15; i++) {
			ceStats.add(" ");
		}
		SwingUtility.setList(detailsList, ceStats);
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
			addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
					setBackground(Colors.ORANGE);
				}

				public void mouseExited(MouseEvent evt) {
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
		public Component getListCellRendererComponent(JList jList, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

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
}
