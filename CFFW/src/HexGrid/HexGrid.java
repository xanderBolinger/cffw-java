package HexGrid;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import Conflict.AttackHexWindow;
import Conflict.GameWindow;
import Conflict.OpenUnit;
import Conflict.SelectedUnitsWindow;
import CorditeExpansion.CeKeyListener;
import CorditeExpansion.Cord;
import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansion.ThrowAble;
import CorditeExpansionActions.CeAction;
import CorditeExpansionActions.TurnAction;
import CorditeExpansionActions.CeAction.ActionType;
import Hexes.Building;
import Hexes.Feature;
import Hexes.Hex;
import Hexes.HexWindow;
import Trooper.Trooper;
import Unit.Unit;
import Unit.Unit.UnitType;
import UtilityClasses.Keyboard;
import UtilityClasses.SwingUtility.FPSCounter;
import UtilityClasses.ExcelUtility;
import UtilityClasses.HexGridUtility;
import UtilityClasses.HexGridUtility.ShownType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.*;

import CeHexGrid.Chit;
import CeHexGrid.Colors;
import CeHexGrid.Chit.Facing;

import java.awt.Component;
import java.awt.SystemColor;

public class HexGrid implements Serializable {

	public transient JFrame frame;
	public transient Panel panel;
	private static final Color FILL_COLOR = Color.BLUE;
	private static final Color BORDER_COLOR = Color.RED;
	public static final Stroke STROKE = new BasicStroke(0.5f);
	private double zoom = 1.0; // zoom factor
	public ArrayList<Unit> initOrder;
	public GameWindow gameWindow;
	public static Hex copiedHex = null; 
	private JMenuItem mntmChangeMap;
	private JMenuItem mntmShowBlufor;
	private JMenuItem mntmShowOpfor;
	private JMenuItem mntmShowBoth;
	private JLayeredPane layeredPane;
	private JButton btnNewButton;
	public static boolean losThreadShowing = false; 
	public static ArrayList<Chit> chits = new ArrayList<>();
	public static int chitCounter = 1;
	public static boolean deployBluforUnknown = false; 
	public static boolean deployOpforUnknown = false; 
	public static boolean deployUnknown = false; 
	private JMenuItem mntmHideU;
	private JMenuItem mntmInitEmptyHexes;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	/**
	 * Create the application.
	 */
	public HexGrid(ArrayList<Unit> initOrder, GameWindow gameWindow, int hexRows, int hexCols) {
		this.initOrder = initOrder;
		this.gameWindow = gameWindow;
		initialize(hexRows, hexCols);
		// System.out.println("Pass create hex grid");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int hexRows, int hexCols) {
		frame = new JFrame();
		HexKeyListener.addKeyListeners();
		frame.setBounds(100, 100, 701, 701);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new Panel(hexRows, hexCols);
		//frame.getContentPane().setLayout(null);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(Box.createRigidArea(new Dimension(10, 46)));
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSaveMap = new JMenuItem("Save Hexes");
		mntmSaveMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SaveMapWindow();
			}
		});
		mnFile.add(mntmSaveMap);
		
		JMenuItem mntmLoadMap = new JMenuItem("Load Hexes");
		mntmLoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoadMapWindow();
			}
		});
		mnFile.add(mntmLoadMap);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		mntmChangeMap = new JMenuItem("Change Map");
		mntmChangeMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChangeMapWindow();
				frame.dispose();
			}
		});
		mnEdit.add(mntmChangeMap);
		
		mntmShowBlufor = new JMenuItem("Show BLUFOR");
		mntmShowBlufor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.shownType = ShownType.BLUFOR;
			}
		});
		mnEdit.add(mntmShowBlufor);
		
		mntmShowOpfor = new JMenuItem("Show OPFOR");
		mntmShowOpfor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.shownType = ShownType.OPFOR;
			}
		});
		mnEdit.add(mntmShowOpfor);
		
		mntmShowBoth = new JMenuItem("Show BOTH");
		mntmShowBoth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.shownType = ShownType.BOTH;
			}
		});
		mnEdit.add(mntmShowBoth);
		
		mntmHideU = new JMenuItem("Toggle U");
		mntmHideU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel.hideU) {
					panel.hideU = false;
				} else {
					panel.hideU = true; 
				}
			}
		});
		mnEdit.add(mntmHideU);
		
		mntmInitEmptyHexes = new JMenuItem("Init Empty Hexes");
		mntmInitEmptyHexes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < panel.hexMap.size(); i++) {
					
					for(int j = 0; j < panel.hexMap.get(i).size(); j++) {
						
						if(GameWindow.gameWindow.findHex(i, j) == null) {
							GameWindow.gameWindow.hexes.add(new Hex(i, j, new ArrayList<Feature>(), 0, 0, 0));
						}
						
					}
					
				}
				
			}
		});
		mnEdit.add(mntmInitEmptyHexes);
		
		layeredPane = new JLayeredPane();
		menuBar.add(layeredPane);
		layeredPane.setLayout(null);
		
		
		
		btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				losThread();
			}
		});
		System.out.println("Path: "+ExcelUtility.path);
		btnNewButton.setBackground(SystemColor.text);
		btnNewButton.setIcon(new ImageIcon(ExcelUtility.path+"\\Icons\\threadIcon.png"));
		btnNewButton.setBounds(0, 0, 45, 45);
		layeredPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deployBluforUnknown();
			}
		});
		btnNewButton_1.setBackground(SystemColor.text);
		btnNewButton_1.setIcon(new ImageIcon(ExcelUtility.path+"\\Icons\\unknown_blufor_icon.png"));
		btnNewButton_1.setBounds(55, 0, 45, 45);
		layeredPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deployOpforUnknown();
			}
		});
		btnNewButton_2.setBackground(SystemColor.text);
		btnNewButton_2.setIcon(new ImageIcon(ExcelUtility.path+"\\Icons\\unknown_opfor_icon.png"));
		btnNewButton_2.setBounds(110, 0, 45, 45);
		layeredPane.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deployUnknown();
			}
		});
		btnNewButton_3.setBackground(SystemColor.text);
		btnNewButton_3.setIcon(new ImageIcon(ExcelUtility.path+"\\Icons\\unknown_icon.png"));
		btnNewButton_3.setBounds(165, 0, 45, 45);
		layeredPane.add(btnNewButton_3);

		panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// System.out.println("Pressed");
				panelMousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// System.out.println("Released");
				panelGetXY(e);

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getButton() == e.BUTTON1) {
					panelMouseLeftClick(e);
					// System.out.println("Left Clicked");
				} else if (e.getButton() == e.BUTTON3) {
					panelMouseRightClick(e);
					// System.out.println("Right Clicked");
				}
			}

		});

		panel.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				panelMouseDragged(e);
				// System.out.println("Dragged");

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// System.out.println("Mouse Moved");
				panelMouseMoved(e);
			}

		});

		panel.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int notches = e.getWheelRotation();
				double temp = zoom - (notches * 0.2);
				// minimum zoom factor is 1.0
				temp = Math.max(temp, 1.0);
				if (temp != zoom) {
					zoom = temp;
					zoomChanged();
					// resizeImage();
				}
			}
		});

	}

	public void refreshDeployedUnits() {
		panel.refreshDeployedUnits();
	}

	public void panelMousePressed(MouseEvent e) {
		panel.mousePressed(e);
	}

	public void panelMouseDragged(MouseEvent e) {
		// System.out.println("Dragged");
		panel.mouseDragged(e);
	}

	public void panelGetXY(MouseEvent e) {
		panel.mouseReleased(e);
	}

	public void zoomChanged() {
		panel.mouseWheelMoved(zoom);
		refreshDeployedUnits();
	}

	public void panelMouseMoved(MouseEvent e) {
		panel.mouseMoved(e);
	}

	public void panelMouseLeftClick(MouseEvent e) {
		panel.mouseLeftClick(e);
	}

	public void panelMouseRightClick(MouseEvent e) {
		panel.mouseRightClick(e);
	}

	public void losThread() {
		if(losThreadShowing) {
			losThreadShowing = false;
			panel.hideLOSThread();
		} else {
			losThreadShowing = true;
			panel.showLOSThread();
		}
	}
	
	public void deployBluforUnknown() {
		deployBluforUnknown = !deployBluforUnknown;
		System.out.println("set deploy blufor: "+deployBluforUnknown);
		deployUnknown = false; 
		deployOpforUnknown = false;
	}
	
	public void deployUnknown() {
		deployUnknown = !deployUnknown;
		deployBluforUnknown = false; 
		deployOpforUnknown = false;
	}
	
	public void deployOpforUnknown() {
		deployOpforUnknown = !deployOpforUnknown;
		deployBluforUnknown = false; 
		deployUnknown = false;
	}
	
	class DrawnString {
		public String text;
		public Point position;

		// Insert constructor and getter methods here
		public DrawnString(String text, Point position) {
			this.text = text;
			this.position = position;
		}
	}

	public class DeployedUnit {
		private Image unitImage;
		public Unit unit;
		//String callsign;
		int xCord;
		int yCord;
		
		public boolean moved = false; 
		
		public DeployedUnit(Unit unit, double zoom) {
			this.unit = unit;
			
			this.xCord = unit.X;
			this.yCord = unit.Y;
			
			try {
				setImage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public String getCallsign() {
			String results = unit.callsign;
			
			if(GameWindow.exhaustedUnit(unit) && moved) {
				results += "Et/M";
			} else if(GameWindow.exhaustedUnit(unit)) {
				results += "Et";
			} else if (GameWindow.mostlyExhausted(unit) && moved) {
				results += " pE/M";
			} else if(GameWindow.mostlyExhausted(unit)) {
				results += " pE";
			} else if(moved) {
				results += " M";
			} 
			
			return results;
		}
		
		public void setImage() throws IOException {
			
			// TODO: make it so side + type string == file name 

			//System.out.println("Set Image");
			if(unit.unitType == UnitType.ARMOR && unit.side.equals("BLUFOR")) {
				//System.out.println("Set Image Pass 1");
				unitImage = ImageIO.read(new File("Unit Images/BLUFOR_ARMOR.png"));
			} else if(unit.unitType == UnitType.INFANTRY && unit.side.equals("BLUFOR")) {
				//System.out.println("Set Image Pass 2");
				unitImage = ImageIO.read(new File("Unit Images/BLUFOR_INFANTRY.png"));
			} else if(unit.unitType == UnitType.INFANTRY && unit.side.equals("OPFOR")) {
				unitImage = ImageIO.read(new File("Unit Images/OPFOR_INFANTRY.png"));
			} else if(unit.unitType == UnitType.ARMOR && unit.side.equals("OPFOR")) {
				unitImage = ImageIO.read(new File("Unit Images/OPFOR_ARMOR.png"));
			} else {
				unitImage = ImageIO.read(new File("Unit Images/BLUFOR_INFANTRY.png"));
			}
			
			
			if(unit.side.equals("BLUFOR")) {
				unitImage = unitImage.getScaledInstance((int)(20.0*zoom), (int)(12.0*zoom), Image.SCALE_SMOOTH);
			} else {
				unitImage = unitImage.getScaledInstance((int)(18.0*zoom), (int)(18.0*zoom), Image.SCALE_SMOOTH);
			}
			
		}
		
		/*originalBluforInfantry = ImageIO.read(new File("Unit Images/BLUFOR_INFANTRY.png"));
		originalOpforInfantry = ImageIO.read(new File("Unit Images/OPFOR_INFANTRY.png"));*/
		
		
	}

	public class Panel extends JPanel {

		private ArrayList<Polygon> shapeList = new ArrayList<>();
		private ArrayList<ArrayList<Polygon>> hexMap = new ArrayList<>();
		private ArrayList<DrawnString> drawnStrings = new ArrayList<>();

		public ArrayList<DeployedUnit> selectedUnits = new ArrayList<>();
		public DeployedUnit selectedUnit = null;
		public int selectedUnitIndex = 0;
		public ArrayList<DeployedUnit> deployedUnits = new ArrayList<>();
		
		
		Point pressedCursorPoint;
		Point selectedPoint;
		Point currentCursorPoint;
		boolean dragging = false;
		double s = 17;
		//double s = 30;
		int cordX = 0;
		int cordY = 0;
		int rows;
		int columns;
		// Point topCorner;
		Image originalImage;
		Image backgroundImage;

		int backgroundImageX = 0;
		int backgroundImageY = 0;
		int backgroundImageWidth;
		int backgroundImageHeight;

		Image originalBluforInfantry;
		Image bluforInfantryImage;
		int bluforUnitWidth = 20;
		int bluforUnitHeight = 12;
		int bluforUnitWidthConst = 20;
		int bluforUnitHeightConst = 12;

		Image originalOpforInfantry;
		Image opforInfantryImage;
		int opforUnitWidth = 18;
		int opforUnitHeight = 18;
		int opforUnitWidthConst = 18;
		int opforUnitHeightConst = 18;
		
		public ShownType shownType = ShownType.BOTH;
		
		public FPSCounter fpscnt; //added line (step 1).
		
		boolean changedUnits = true; 
		public boolean hideU = false; 
		
		Polygon losThread = new Polygon();
		
		
		Panel(int hexRows, int hexCols) {
			
			this.rows = hexRows+1;
			this.columns = hexCols;
			
			
			int hexes = hexRows * hexCols; 
			
			if(hexes < (10*10))
				s = 30;
			else if(hexes < (20*20))
				s = 25;
			else 
				s = 17;
			
			//System.out.println("S: "+s);
			// set a preferred size for the custom panel.
			setPreferredSize(new Dimension(420, 420));
		
			makeHexes(rows, columns);
		
			try {
				// originalImage = ImageIO.read(new File("Map Images/CondorValley.png"));
				//originalImage = ImageIO.read(new File("Map Images/refuge.png"));
				originalImage = ImageIO.read(new File("Map Images/"+GameWindow.gameWindow.game.mapImageFileName));
				backgroundImageWidth = (int) (s * (columns / 2 * 3));
				backgroundImageHeight = (int) (s * 1.7175 * rows);
				backgroundImageHeight -= backgroundImageHeight / rows / 2;
				backgroundImage = originalImage.getScaledInstance(backgroundImageWidth, backgroundImageHeight,
						Image.SCALE_SMOOTH);
		
				originalBluforInfantry = ImageIO.read(new File("Unit Images/BLUFOR_INFANTRY.png"));
				bluforInfantryImage = originalBluforInfantry.getScaledInstance(bluforUnitWidth, bluforUnitHeight,
						Image.SCALE_SMOOTH);
		
				originalOpforInfantry = ImageIO.read(new File("Unit Images/OPFOR_INFANTRY.png"));
				opforInfantryImage = originalOpforInfantry.getScaledInstance(opforUnitWidth, opforUnitHeight,
						Image.SCALE_SMOOTH);
		
				refreshDeployedUnits();
				
				
				new Timer(20, new TimerListener()).start();
				
				fpscnt = new FPSCounter(); //added line (step 2).
				fpscnt.start(); //added line (step 3).
				/*
				 * deployedUnits.add(new DeployedUnit("Test Unit 1", bluforInfantryImage, 10,
				 * 10)); deployedUnits.add(new DeployedUnit("Test Unit 2", bluforInfantryImage,
				 * 10, 10));
				 */
		
				// save imageInByte as blob in database
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		
		}


		class PanelPopUp extends JPopupMenu {
			JMenuItem move;
			ArrayList<JMenuItem> openItems = new ArrayList<>();
			Unit unit;
			int moveX;
			int moveY;

			
			public DeployedUnit getDeployedUnit(Unit unit) {
				
				
				for(DeployedUnit deployedUnit : deployedUnits) {
					
					if(deployedUnit.unit.compareTo(unit)) {
						return deployedUnit; 
					}
					
				}
				
				return null; 
			}
			
			public PanelPopUp(int xCord, int yCord) {

				for (DeployedUnit deployedUnit : deployedUnits) {

					if (deployedUnit.unit.X == xCord && deployedUnit.unit.Y == yCord) {

						JMenuItem item = new JMenuItem("Open " + deployedUnit.unit.callsign);

						item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								gameWindow.listIniativeOrder.setSelectedIndex(initOrder.indexOf(deployedUnit.unit));
								Unit unit = initOrder.get(gameWindow.listIniativeOrder.getSelectedIndex());
								// System.out.println("Open unit moral: "+unit.moral);
								
								if(gameWindow.currentlyOpenUnit != null) {
									gameWindow.currentlyOpenUnit.f.dispose();
								}
								
								gameWindow.currentlyOpenUnit = new OpenUnit(unit, gameWindow,
										gameWindow.listIniativeOrder.getSelectedIndex());
								
							}
						});

						add(item);

					}

				}
				
				if(selectedUnit != null) {
					move = new JMenuItem("Move " + selectedUnit.getCallsign() + " to " + xCord + ":" + yCord);
					moveX = xCord;
					moveY = yCord;
					add(move);
					this.unit = selectedUnit.unit;

					move.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							
							DeployedUnit deployedUnit = getDeployedUnit(unit);
							
							if(unit.speed.equals("None") || (GameWindow.hexDif(moveX, moveY, unit) > 1 && unit.speed.equals("Walk"))) {
								new MovementSpeedDialogBox(unit, moveX, moveY, deployedUnit);
							} else {
								unit.move(gameWindow, moveX, moveY, null);
								deployedUnit.moved = true; 
								//System.out.println("Moved equal true: "+deployedUnit.getCallsign());
								refreshDeployedUnits();
							}
							
							
						}
					});
				}
				
				addLOS();
				removeLOS();
				selectedUnitsItem(xCord, yCord);
				newHexItem(xCord, yCord);
				attackWindowItem(GameWindow.gameWindow.findHex(xCord, yCord));

			}

			public PanelPopUp(int xCord, int yCord, Unit unit) {
				if (selectedUnit == null)
					return;
				
				
				JMenuItem item = new JMenuItem("Open " + unit.callsign);

				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gameWindow.listIniativeOrder.setSelectedIndex(initOrder.indexOf(unit));
						Unit unit = initOrder.get(gameWindow.listIniativeOrder.getSelectedIndex());
						// System.out.println("Open unit moral: "+unit.moral);
						
						if(gameWindow.currentlyOpenUnit != null) {
							gameWindow.currentlyOpenUnit.f.dispose();
						}
						
						gameWindow.currentlyOpenUnit = new OpenUnit(unit, gameWindow,
								gameWindow.listIniativeOrder.getSelectedIndex());
						
					}
				});

				add(item);
				
				
				addLOS();
				removeLOS();
				selectedUnitsItem(xCord, yCord);
				newHexItem(xCord, yCord);
				attackWindowItem(GameWindow.gameWindow.findHex(xCord, yCord));
			}
			
			public void removeLOS() {
				
				//System.out.println("remove, selectedunits Side: "+selectedUnits.size());
				
				if(selectedUnits.size() < 2 || sameSide()) {
					//System.out.println("remove los return");
					return; 
				}
				
				JMenuItem item = new JMenuItem("Remove Units from LOS");

				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						for(DeployedUnit dp : selectedUnits) {
							
							Unit unit = dp.unit;
							
							for(DeployedUnit dp2 : selectedUnits) {
								if(dp2.unit.side.equals(unit.side) || !unit.lineOfSight.contains(dp2.unit))
										continue; 
								
								unit.lineOfSight.remove(dp2.unit);
							}
							
						}
						
						selectedUnit = selectedUnits.get(0);
						selectedUnits.clear();
						
					}
				});

				add(item);
				
			}
			
			public void addLOS() {
				
				//System.out.println("add Los, selectedunits Side: "+selectedUnits.size());
				
				if(selectedUnits.size() < 2 || sameSide()) {
					//System.out.println("add los return");
					return; 
				}
				
				JMenuItem item = new JMenuItem("Add Units to LOS");

				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						for(DeployedUnit dp : selectedUnits) {
							
							Unit unit = dp.unit;
							
							for(DeployedUnit dp2 : selectedUnits) {
								if(dp2.unit.side.equals(unit.side) || unit.lineOfSight.contains(dp2.unit))
										continue; 
								
								unit.lineOfSight.add(dp2.unit);
							}
							
						}
						
						selectedUnit = selectedUnits.get(0);
						selectedUnits.clear();
						
					}
				});

				add(item);
				
			}
			
			public boolean sameSide() {
				
				boolean opfor = false; 
				boolean blufor = false; 
				for(DeployedUnit dp : selectedUnits) {
					if(dp.unit.side.equals("BLUFOR")) 
						blufor = true; 
					else 
						opfor = true; 
					
					if(opfor && blufor)
						return false; 
				}
				
				return true; 
			}
			
			public void selectedUnitsItem(int x, int y) {
				
				if(GameWindow.gameWindow.getUnitsInHex("None", x, y).size() < 1 && selectedUnits.size() < 2) {
					return; 
				}
				
				JMenuItem item = new JMenuItem("Selected Units");

				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						if(selectedUnits.size() > 1) {
							new SelectedUnitsWindow(x, y, selectedUnits);
						} else {	
							new SelectedUnitsWindow(x, y);
						}						
						
					}
				});
				
				add(item);
				
			}
			
			public void attackWindowItem(Hex hex) {
				if(hex == null)
					return;
				
				JMenuItem item = new JMenuItem("Attack Hex");

				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						new AttackHexWindow(hex);
						
					}
				});
				
				for(Unit unit : GameWindow.gameWindow.initiativeOrder) {
					if(hex.xCord == unit.X && hex.yCord == unit.Y) {
						add(item);
						break; 
					}
				}
				
			}
			
			// Adds a button that will prompt user to create a new hex 
			public void newHexItem(int x, int y) {
				
				if(GameWindow.gameWindow.findHex(x, y) != null) {
					
					JMenuItem openItem = new JMenuItem("Open Hex");
					
					
					openItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							
							///copiedHex = GameWindow.gameWindow.findHex(x, y); 
							
							new HexWindow(x, y);
							
						}
					});
					
					add(openItem);
					
					
					JMenuItem copyItem = new JMenuItem("Copy Hex");
					
					
					copyItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							
							copiedHex = GameWindow.gameWindow.findHex(x, y); 
							
						}
					});
					
					add(copyItem);
				} else {
					JMenuItem item = new JMenuItem("New Hex");
					
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							
							new HexWindow(GameWindow.gameWindow, x, y);
						}
					});
					
					add(item);
				}
				
				
			}
			

		}

		double oldZoom = 1.0;
		double zoom = 1.0;

		public void refreshDeployedUnits() {
			
			//System.out.println("Refresh Deployed Units");
			
			ArrayList<Unit> movedUnits = new ArrayList<>();
			
			for(DeployedUnit deployedUnit : deployedUnits) {
				if(deployedUnit.moved) {
					movedUnits.add(deployedUnit.unit);
				} 
			}
			
			deployedUnits.clear();
			// If unit in init order not in dp 
			for (Unit unit : initOrder) {

				
				//System.out.println("add");
				DeployedUnit deployedUnit = new DeployedUnit(unit, zoom);
				if(movedUnits.contains(unit)) {
					deployedUnit.moved = true; 
				}
				
				deployedUnits.add(deployedUnit);
				if (selectedUnit != null && selectedUnit.unit.callsign.equals(deployedUnit.unit.callsign)) {
					selectedUnit = deployedUnit;
					
				}

			}
			
		}

		public void mousePressed(MouseEvent e) {
			dragging = false;
			pressedCursorPoint = e.getPoint();
			//System.out.println("Pressed Cursor Point, X: "+pressedCursorPoint.x+", Y:"+pressedCursorPoint.y);
			currentCursorPoint = null;
			
			
			
			int[] points = getHexFromPoint(e.getPoint());
			if (points == null)
				return;
			
			if(deployBluforUnknown) {
				//System.out.println("deploy blufor");
				Chit chit = new Chit(ExcelUtility.path+"\\Icons\\unknown_blufor_icon.png", 20, 20);
				chit.xCord = points[0];
				chit.yCord = points[1];
				chits.add(chit);
				chit.number = chitCounter;
				chitCounter++;
			} else if(deployOpforUnknown) {
				Chit chit = new Chit(ExcelUtility.path+"\\Icons\\unknown_opfor_icon.png", 20, 20);
				chit.xCord = points[0];
				chit.yCord = points[1];
				chits.add(chit);
				chit.number = chitCounter;
				chitCounter++;
			} else if(deployUnknown) {
				Chit chit = new Chit(ExcelUtility.path+"\\Icons\\unknown_icon.png", 20, 20);
				chit.xCord = points[0];
				chit.yCord = points[1];
				chits.add(chit);
				chit.number = chitCounter;
				chitCounter++;
			}
			
			checkChitClick(e.getPoint());
			
		}

		public void mouseDragged(MouseEvent e) {
			panel.dragging = true;
			currentCursorPoint = e.getPoint();
			// System.out.println("Current Cursor Point, X: "+currentCursorPoint.x+", Y:
			// "+currentCursorPoint.y);
			//repaint();
			
			if(e.getButton() == MouseEvent.BUTTON1 && Keyboard.isKeyPressed(KeyEvent.VK_CONTROL)) {
				for (int i = 0; i < hexMap.size(); i++) {

					for (int j = 0; j < hexMap.get(0).size(); j++) {

						Polygon hex = hexMap.get(i).get(j);
											
						if (!hex.contains(e.getPoint())) {
							continue; 
						}
						
						Hex pastedHex = GameWindow.gameWindow.findHex(i, j);
						
						if(pastedHex != null) {
							 GameWindow.gameWindow.hexes.remove(pastedHex);
						}
						
						copiedHex.coverPositions = 0; 
						
						for(Feature feature : copiedHex.features) {
							int cover = HexWindow.getCoverPostitions(feature.featureType);
							feature.coverPositions = cover; 
							copiedHex.coverPositions += feature.coverPositions;
						}
						
						Hex newHex = new Hex(i, j, copiedHex);
						
						newHex.usedPositions = 0; 
						
						
						for(Unit unit : GameWindow.gameWindow.initiativeOrder) {
							
							if(unit.X == i && unit.Y == j) {
								unit.seekCover(newHex, GameWindow.gameWindow);
							}
							
						}
						
						GameWindow.gameWindow.hexes.add(newHex);	
						System.out.println("Copied");
						
					}
				}
			}
			
			
			
		}

		public void mouseReleased(MouseEvent e) {
			dragging = false;
			
			
			int[] points = getHexFromPoint(e.getPoint());

			if (points == null) {
				Chit.unselectChit();
				selectedPoint = null;
				return;
			}

			// System.out.println("Mouse Pressed");
			
			if(Keyboard.isKeyPressed(KeyEvent.VK_SHIFT) && Chit.isAChitSelected()) {
				//System.out.println("Chit unselected,  Released Point: ("+e.getX()+", "+e.getY()+")"
				//		+", Selected Point: ("+selectedPoint.getX()+", "+selectedPoint.getY()+")");
				Chit.getSelectedChit().shifted = true;
				Polygon hex = hexMap.get(Chit.getSelectedChit().xCord).get(Chit.getSelectedChit().yCord);
				int hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;
				int hexCenterY = hex.getBounds().y + hex.getBounds().height / 2;
				
				Chit.getSelectedChit().shiftX = (int) (e.getX() - hexCenterX);
				Chit.getSelectedChit().shiftY = (int) (e.getY() - hexCenterY);
				//Chit.getSelectedChit().shiftX = (int) (e.getX() - selectedPoint.getX());
				//Chit.getSelectedChit().shiftY = (int) (e.getY() - selectedPoint.getY());
				//System.out.println("Chit Shifted");
			} else if (Chit.isAChitSelected()) {
				//System.out.println("Chit Moved");
				Chit.moveSelectedChit(points[0], points[1]);
			}
			
			selectedPoint = null;
			Chit.unselectChit();
		}

		public int[] getHexFromPoint(Point point) {

			// System.out.println("Get hex from point");

			for (int i = 0; i < hexMap.size(); i++) {

				for (int j = 0; j < hexMap.get(0).size(); j++) {

					Polygon hex = hexMap.get(i).get(j);

					if (hex.contains(point)) {
						int[] arr = { i, j };
						return arr;
					}
				}
			}

			return null;
		}
		
		public void checkChitClick(Point point) {
			
			for (Chit chit : chits) {
				Rectangle imageBounds = new Rectangle(chit.xPoint, chit.yPoint, chit.getWidth(), chit.getHeight());
				if (imageBounds.contains(point)) {
					//System.out.println("Clicked Chit, chit.xPoint: " + chit.xPoint + ", clicked x point: " + point.x);
					selectedPoint = point;
					Chit.setSelectedChit(chit, point.x - chit.xPoint, point.y - chit.yPoint);
					chit.shifted = false;
					chit.shiftX = 0;
					chit.shiftY = 0;
					return;
				}
			}

		}
		
		public void mouseWheelMoved(double zoom) {
			this.zoom = zoom;
			//repaint();
		}

		// Checks for hovering tooltip and displays message to user
		public void mouseMoved(MouseEvent e) {
			
			//System.out.println("Mouse Moved: ("+e.getPoint().x+", "+e.getPoint().y+")");

			if(HexGrid.losThreadShowing && losThread.npoints >= 1) {
				
				if(losThread.npoints > 1) {
					losThread.xpoints[1] = e.getPoint().x; 
					losThread.ypoints[1] = e.getPoint().y; 
				} else {
					losThread.addPoint(e.getPoint().x, e.getPoint().y);
				}
				
			}
			
			for (int i = 0; i < hexMap.size(); i++) {

				for (int j = 0; j < hexMap.get(0).size(); j++) {

					Polygon hexShape = hexMap.get(i).get(j);

					if (hexShape.contains(e.getPoint())) {

						String toolTip = "<html>" + i + ":" + j;

						// Displays units in hex
						for (DeployedUnit deployedUnit : deployedUnits) {

							if (deployedUnit.xCord == i && deployedUnit.yCord == j) {
								toolTip += 
										"<br>" + deployedUnit.getCallsign() + ", EXH: " + GameWindow.exhaustedUnit(deployedUnit.unit) + " ORG " + deployedUnit.unit.organization
										+ " A: " + deployedUnit.unit.active() + " W: "
										+ deployedUnit.unit.woundedActive() + " I: " + deployedUnit.unit.inactive() + " Speed: "+deployedUnit.unit.speed;
							}

						}

						// Displays hex features
						for (Hex hex : gameWindow.hexes) {

							if (hex.xCord == i && hex.yCord == j) {

								toolTip += "<br>---------";
								toolTip += "<br>";
								toolTip += "Concealment: " + hex.concealment + "<br>";
								toolTip += "Total Cover Positions: " + hex.coverPositions + "<br>";
								for (Feature feature : hex.features) {

									toolTip += feature.toString() + "<br>";

								}
								
								for(Building building : hex.buildings) {
									toolTip += building.toString() + "<br>";
								}
								

								break;

							}

						}

						panel.setToolTipText(toolTip + "</html>");

						// System.out.println("set text");
					}

				}

			}

		}
	

		// Gets clicked hex
		// If hex contains units, selects first unit
		public void mouseLeftClick(MouseEvent e) {

			
			if(HexGrid.losThreadShowing) {
				
				if(losThread.npoints >= 1) {
					hideLOSThread();
				} else if(losThread.npoints == 0 ) {
					losThread.addPoint(e.getPoint().x, e.getPoint().y);
				}
				
				selectedUnit = null; 
				selectedUnits.clear();
				return; 
			}
			
			if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && Keyboard.isKeyPressed(KeyEvent.VK_SHIFT) ) {
				//System.out.println("Double clicked select 2");
				
				for (int i = 0; i < hexMap.size(); i++) {

					for (int j = 0; j < hexMap.get(0).size(); j++) {

						Polygon hex = hexMap.get(i).get(j);

						if (hex.contains(e.getPoint())) {
							
							String typeShown = "None";
							if(shownType == ShownType.BLUFOR) {
								typeShown = "BLUFOR";
							} else if(shownType == ShownType.OPFOR) {
								typeShown = "OPFOR";
							} 
							
							//System.out.println("Shown Type: "+shownType.toString());
							ArrayList<Unit> units = GameWindow.gameWindow.getUnitsInHex(typeShown, i, j);
							//System.out.println("Units Size: "+units.size());
							for(Unit unit : units) {
								selectedUnits.add(getDeployedUnit(unit));
								//System.out.println("Add selected unit");
							}
							
							//System.out.println("Double clicked select, selected Units: "+selectedUnits.size());
							
							selectedUnitIndex = 0;
							return; 
							
						}
					}
				}
				
				
				
				
			} 
			
			
			// System.out.println("Left Clicked");

			for (int i = 0; i < hexMap.size(); i++) {

				for (int j = 0; j < hexMap.get(0).size(); j++) {

					Polygon hex = hexMap.get(i).get(j);

					if (hex.contains(e.getPoint())) {
						
						if(copiedHex != null && Keyboard.isKeyPressed(KeyEvent.VK_CONTROL)) {
							
							Hex pastedHex = GameWindow.gameWindow.findHex(i, j);
							
							if(pastedHex != null) {
								 GameWindow.gameWindow.hexes.remove(pastedHex);
							}
							
							copiedHex.coverPositions = 0; 
							
							for(Feature feature : copiedHex.features) {
								int cover = HexWindow.getCoverPostitions(feature.featureType);
								feature.coverPositions = cover; 
								copiedHex.coverPositions += feature.coverPositions;
							}
							
							Hex newHex = new Hex(i, j, copiedHex);
							
							newHex.usedPositions = 0; 
							
							
							for(Unit unit : GameWindow.gameWindow.initiativeOrder) {
								
								if(unit.X == i && unit.Y == j) {
									unit.seekCover(newHex, GameWindow.gameWindow);
								}
								
							}
							
							GameWindow.gameWindow.hexes.add(newHex);							
							
							GameWindow.gameWindow.conflictLog.addNewLine("Pasted! X: "+i+", Y: "+j);
							
						}
						
						if (selectedUnit != null) {
							if(e.getClickCount() != 2 && e.getButton() == MouseEvent.BUTTON1 && !Keyboard.isKeyPressed(KeyEvent.VK_CONTROL) ) {
								selectedUnitIndex++;
							} 
	
							selectedUnit = null; 
						}

						int unitsInHex = unitsInHex(i, j);

						if (selectedUnitIndex > unitsInHex - 1) {
							selectedUnitIndex = 0;
						}

						int count = 0;
						for (DeployedUnit deployedUnit : deployedUnits) {

							if(!HexGridUtility.canShow(shownType, deployedUnit.unit)) {
								continue; 
							}
							
							if (deployedUnit.xCord == i && deployedUnit.yCord == j) {
								count++;

								if (count == unitsInHex - selectedUnitIndex) {
									selectedUnit = deployedUnit;
									
									if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && !Keyboard.isKeyPressed(KeyEvent.VK_SHIFT)) {
										gameWindow.listIniativeOrder.setSelectedIndex(initOrder.indexOf(selectedUnit.unit));
										Unit unit = initOrder.get(gameWindow.listIniativeOrder.getSelectedIndex());
										// System.out.println("Open unit moral: "+unit.moral);
										
										if(gameWindow.currentlyOpenUnit != null) {
											gameWindow.currentlyOpenUnit.f.dispose();
										}
										
										gameWindow.currentlyOpenUnit = new OpenUnit(unit, gameWindow,
												gameWindow.listIniativeOrder.getSelectedIndex());
									}
									
									if(Keyboard.isKeyPressed(KeyEvent.VK_SHIFT) && !selectedUnits.contains(deployedUnit)) {
										selectedUnits.add(deployedUnit);
									}
									
									if(Keyboard.isKeyPressed(KeyEvent.VK_SHIFT) && !selectedUnits.contains(selectedUnit))
										selectedUnits.add(selectedUnit);
									
									return;
								}

							}

						}
						
						
						

						break;

					}

				}

			}
			
			if(!Keyboard.isKeyPressed(KeyEvent.VK_SHIFT)) {
				selectedUnits.clear();
				selectedUnit = null;				
			}
			
			selectedUnitIndex = 0;

		}

		public void mouseRightClick(MouseEvent e) {
			
			
			
			// System.out.println("Right clicked");

			// Checks for which hex was clicked
			for (int i = 0; i < hexMap.size(); i++) {

				for (int j = 0; j < hexMap.get(0).size(); j++) {

					Polygon hex = hexMap.get(i).get(j);

					// If hex found 
					if (hex.contains(e.getPoint())) {

						// System.out.println("Hex: "+i+":"+j);

						if((selectedUnit != null || selectedUnits.size() > 0) && Keyboard.isKeyPressed(KeyEvent.VK_SHIFT)) {
							for(DeployedUnit dp : selectedUnits) {
								dp.unit.move(GameWindow.gameWindow, i, j, null);								
							}
							
							
							if(selectedUnit != null)
								selectedUnit.unit.move(GameWindow.gameWindow, i, j, null);
							
							GameWindow.gameWindow.hexGrid.refreshDeployedUnits();
							selectedUnits.clear();
						}
						// If right clicked in the same hex as the selected unit
						else if (selectedUnit != null && (selectedUnit.xCord == i && selectedUnit.yCord == j)) {
							// System.out.println("Pop up menu 1");
							PanelPopUp menu = new PanelPopUp(i, j, selectedUnit.unit);
							menu.show(e.getComponent(), e.getX(), e.getY());
						} else  {
							// System.out.println("Pop up menu 2");
							PanelPopUp menu = new PanelPopUp(i, j);
							menu.show(e.getComponent(), e.getX(), e.getY());
						}

						
						
						
						return;

					}

				}

			}

		}

		public Polygon newHex(int X, int Y, double s) {

			double a = Math.sqrt(3) * (s / 2);

			Polygon p = new Polygon();

			p.addPoint((int) (X + (s / 2)), (int) (Y - a));
			
			
			
			p.addPoint((int) (X + s), (int) (Y));
			p.addPoint((int) (X + (s / 2)), (int) (Y + a));
			p.addPoint((int) (X - (s / 2)), (int) (Y + a));
			p.addPoint((int) (X - s), (int) (Y));
			p.addPoint((int) (X - (s / 2)), (int) (Y - a));

			return p;

			/*
			 * point 1: (X + (s/2), Y - a) point 2: (X + s, Y) point 3: (X + (s/2), Y + a)
			 * point 4: (X - (s/2), Y + a) point 5: (X - s, Y) point 6: (X - (s/2), Y - a)
			 */

		}

		public Polygon scaleHex(Polygon hex, AffineTransform at) {

			// Polygon mesh
			// AffineTransform at

			int[] x = hex.xpoints;
			int[] y = hex.ypoints;
			int[] rx = new int[x.length];
			int[] ry = new int[y.length];

			for (int i = 0; i < hex.npoints; i++) {
				Point p = new Point(x[i], y[i]);
				at.transform(p, p);
				rx[i] = p.x;
				ry[i] = p.y;
			}

			return new Polygon(rx, ry, hex.npoints);

		}

		// Scale
		// Deletes all hexes
		// Remakes all hexes based on scaled s value
		// translates all shapes based on the distance between 0,0 and the top left
		// corner of the first shape
		public void makeHexes(int rows, int columns) {

			//System.out.println("Make Hexes");
			
			if (shapeList.size() > 0)
				shapeList.clear();

			hexMap.clear();

			double a = Math.sqrt(3) * (s / 2);

			for (int i = 1; i <= rows; i++) {

				ArrayList<Polygon> row = new ArrayList<>();

				for (int j = 1; j <= columns; j++) {

					Polygon hex = newHex((int) (cordX + j * ((3 * s) / 2)), (int) (cordY + (j % 2) * a + 2 * i * a), s);

					row.add(hex);

					shapeList.add(hex);

					drawnStrings.add(new DrawnString(i + ":" + j,
							new Point((int) (cordX + j * ((3 * s) / 2)), (int) (cordY + (j % 2) * a + 2 * i * a))));
				}

				hexMap.add(row);

			}

		}

		public int unitsInHex(int x, int y) {

			int count = 0;
			for (Unit unit : initOrder) {

				if (unit.X == x && unit.Y == y)
					count++;

			}

			return count;
		}

		
		public void showLOSThread() {
			//System.out.println("Show LOS Thread.");
			HexGrid.losThreadShowing = true; 
		}
		
		public void hideLOSThread() {
			//System.out.println("Hide LOS Thread.");
			HexGrid.losThreadShowing = false;
			losThread.reset();
		}
		
		
		public DeployedUnit getDeployedUnit(Unit unit) {
			
			for(DeployedUnit dp : deployedUnits) {
				
				if(unit.compareTo(dp.unit)) {
					return dp;
				}
				
			}
			
			return null; 
			
		}
		
		public int[] getHexFromPoint(int x, int y) {
			
			//System.out.println("Get hex from point");
			
			for (int i = 0; i < hexMap.size(); i++) {

				for (int j = 0; j < hexMap.get(0).size(); j++) {

					Polygon hex = hexMap.get(i).get(j);

					if (hex.contains(new Point(x, y))) {
						int[] arr={i, j};
						return arr; 
					}
				}
			}
			
			return null; 
		}
		
		public void drawChits(Graphics2D g2) {

			for (int i = 0; i < chits.size(); i++) {

				Chit chit = chits.get(i);
				Polygon hex = hexMap.get(chit.xCord).get(chit.yCord);

				if(chit.shifted)
					chit.drawChit(zoom, g2, hex, chit.shiftX, chit.shiftY);
				else
					chit.drawChit(zoom, g2, hex);

				
				String s = "Unit #"+chit.number;
				int hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;
				int hexCenterY = hex.getBounds().y + hex.getBounds().height / 2;
				
				Font currentFont = g2.getFont();

				Font newFont = currentFont.deriveFont((float) (currentFont.getSize()));
				g2.setFont(newFont);
				FontMetrics metrics = g2.getFontMetrics(newFont);

				// Determine the X coordinate for the text
				int x = (hexCenterX - chit.getWidth() / 2)
						+ (chit.getWidth() - metrics.stringWidth(s)) / 2;
				// Determine the Y coordinate for the text (note we add the ascent, as in java
				// 2d 0 is top of the screen)
				int y = (int) ((hexCenterY - chit.getHeight() / 1.5) + -metrics.getHeight() / 2
						+ metrics.getAscent()) - (int) (3 * zoom);
				g2.setColor(Color.YELLOW);
				g2.drawString(s, x, y);
				g2.setColor(Color.RED);
			}
		}
		
		public void drawUnit(DeployedUnit deployedUnit, Graphics g, Graphics2D g2, int count) {
			// Displays unit card

			
			Polygon hex = hexMap.get(deployedUnit.xCord).get(deployedUnit.yCord);

			// If a unit is already in this hex, shifts the chit down and to the right

			int hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;
			int hexCenterY = hex.getBounds().y + hex.getBounds().height / 2;

			int width = bluforUnitWidth;
			int height = bluforUnitHeight;

			if (deployedUnit.unit.side.equals("OPFOR")) {
				// System.out.println("PASS6");
				width = opforUnitWidth;
				height = opforUnitHeight;
			}
			
			
			/*
			 * if(zoom != oldZoom) { selectedUnit.unitImage =
			 * bluforInfantryImage.getScaledInstance(bluforUnitWidth, bluforUnitHeight, 1);
			 * }
			 */

			int unitsInHex = unitsInHex(deployedUnit.xCord, deployedUnit.yCord);

			g2.drawImage(deployedUnit.unitImage,
					(hexCenterX - width / 2) - (3 * (unitsInHex - count)),
					(hexCenterY - height / 2) + (3 * (unitsInHex - count)), null);

			if ((selectedUnit != null && selectedUnit.unit.callsign.equals(deployedUnit.unit.callsign))
					|| selectedUnits.contains(deployedUnit)) {

				if (deployedUnit.unit.side.equals("OPFOR")) {
				//if (selectedUnit.unit.side.equals("OPFOR")) {

					Polygon diamond = new Polygon();

					// Top point
					diamond.addPoint(hexCenterX, hexCenterY - height / 2);
					// Left point
					diamond.addPoint(hexCenterX - width / 2, hexCenterY);
					// Bottom point
					diamond.addPoint(hexCenterX, hexCenterY + height / 2);
					// Right point
					diamond.addPoint(hexCenterX + width / 2, hexCenterY);

					g2.setColor(Color.YELLOW);
					g2.setStroke(new BasicStroke(2f));
					g2.draw(diamond);

					/*
					 * (x + Width/2, y) (x + Width, y + Height/2) (x + Width/2, y + Height) (x, y +
					 * Height/2)
					 */

				} else {
					int topLeftCornerX = hexCenterX - width / 2;
					int topLeftCornerY = hexCenterY - height / 2;
					g2.setColor(Color.YELLOW);
					g2.setStroke(new BasicStroke(2f));
					g2.draw(new Rectangle(topLeftCornerX, topLeftCornerY, width,
							height));
				}

			}

			g2.setColor(BORDER_COLOR);
			g2.setStroke(STROKE);

			// Base font
			// Font font = new Font("Arial", Font.BOLD, 1);

			// Scaled font
			// Font fontSc = font.deriveFont(AffineTransform.getScaleInstance(zoomx,
			// -zoomy));

			// Get the "scaled" metrics
			// FontMetrics metrics = g.getFontMetrics(fontSc);

			// Apply font
			// g2.setFont(font);

			if (deployedUnit.unit.side.equals("BLUFOR")) {
				g2.setColor(Color.yellow);

			} else if (deployedUnit.unit.side.equals("OPFOR")) {
				g2.setColor(Color.yellow);

			} else if (deployedUnit.unit.side.equals("INDFOR")) {
				g2.setColor(Color.yellow);

			} else {
				g2.setColor(Color.yellow);

			}

			String s = deployedUnit.getCallsign() + ":: "+GameWindow.gameWindow.initiativeOrder.indexOf(deployedUnit.unit);

			/*
			 * double textSizeMod = 0.8;
			 * 
			 * if(s.length() > 4) {
			 * 
			 * textSizeMod = 0.6;
			 * 
			 * } else if(s.length() > 8) { textSizeMod = 0.3; } else if(s.length() > 10) {
			 * textSizeMod = 0.1; }
			 */

			Font currentFont = g.getFont();

			Font newFont = currentFont.deriveFont((float) (currentFont.getSize()));
			g.setFont(newFont);
			FontMetrics metrics = g.getFontMetrics(newFont);

			// Determine the X coordinate for the text
			int x = (hexCenterX - deployedUnit.unitImage.getWidth(null) / 2)
					+ (deployedUnit.unitImage.getWidth(null) - metrics.stringWidth(s)) / 2;
			// Determine the Y coordinate for the text (note we add the ascent, as in java
			// 2d 0 is top of the screen)
			int y = (int) ((hexCenterY - deployedUnit.unitImage.getHeight(null) / 1.5) + -metrics.getHeight() / 2
					+ metrics.getAscent()) - (int) (3 * zoom);

			if (count == unitsInHex(deployedUnit.xCord, deployedUnit.yCord)) {
				g2.drawString(s, x, y);
			}

			g2.setColor(Color.green);
			g2.setStroke(new BasicStroke((float) (2f * zoom)));

			// the two cordinates of the line
			int bottomLeftCornerX = hexCenterX - width / 2;
			int bottomLeftCornerY = hexCenterY + height / 2;
			int x1 = (int) (bottomLeftCornerX + (1.5 * zoom));
			int y1 = bottomLeftCornerY;
			int x2 = (int) (bottomLeftCornerX + width - (1.5 * zoom));
			int y2 = bottomLeftCornerY;

			int textWidth = x2 - x1;
			double mod = deployedUnit.unit.organization / 100.0;
			// System.out.println("Deployed Unit: "+deployedUnit.callsign+", ORG:
			// "+deployedUnit.unit.organization);
			if (mod > 1)
				mod = 1;
			// System.out.println("Mod: "+mod);
			textWidth *= mod;

			if (count == unitsInHex(deployedUnit.xCord, deployedUnit.yCord)) {
				g2.drawLine(x1, y1, x1 + textWidth, y2);
			}

			/*
			 * g2.drawString("<html><div style=\"text-align: center; color: black; width:"
			 * +bluforUnitWidth+";\">"+deployedUnit.callsign+"</div></html>", (hexCenterX -
			 * deployedUnit.unitImage.getWidth(null) / 2), (hexCenterY -
			 * deployedUnit.unitImage.getHeight(null) / 2));
			 */
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			
			
			
			//System.out.println("Paint!");
			
			//System.out.println("shapeList: "+shapeList.size()+", Deployed Units: "+deployedUnits.size());
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(STROKE);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension screenSize = toolkit.getScreenSize();
			g2.setClip(0,0,screenSize.width,screenSize.height);
			/*g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			RenderingHints rh4 = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);*/
			//g2.setRenderingHints(rh4);
			
			System.setProperty("sun.java2d.opengl", "true");
			RenderingHints rh3 = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			g2.setRenderingHints(rh3);
			RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g2.setRenderingHints(rh2);
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			g2.setRenderingHints(rh);

			
			
			
			if (zoom != oldZoom) {
				s = Math.round(s / oldZoom * zoom);
				// System.out.println("S: "+s+", Zoom: "+zoom);
				backgroundImageWidth = (int) Math.round(s * (columns / 2 * 3));
				backgroundImageHeight = (int) Math.round(s * 1.7175 * rows);
				backgroundImageHeight -= backgroundImageHeight / rows / 2;
				bluforUnitWidth = (int) Math.round(bluforUnitWidthConst * zoom);
				bluforUnitHeight = (int) Math.round(bluforUnitHeightConst * zoom);
				opforUnitWidth = (int) Math.round(opforUnitWidthConst * zoom);
				opforUnitHeight = (int) Math.round(opforUnitHeightConst * zoom);
			}

			if (pressedCursorPoint != null && currentCursorPoint != null && dragging && !Keyboard.isKeyPressed(KeyEvent.VK_CONTROL)) {

				backgroundImageX = shapeList.get(0).getBounds().x + 11;
				backgroundImageY = shapeList.get(0).getBounds().y;
				g2.drawImage(backgroundImage, backgroundImageX, backgroundImageY, null);

			} else if (zoom != oldZoom) {

				backgroundImage = originalImage.getScaledInstance(backgroundImageWidth, backgroundImageHeight, 1);
				backgroundImageX = shapeList.get(0).getBounds().x + 11;
				backgroundImageY = shapeList.get(0).getBounds().y;

				g2.drawImage(backgroundImage, backgroundImageX, backgroundImageY, null);

			} else {
				//makeHexes(rows, columns);
				
				backgroundImageX = shapeList.get(0).getBounds().x + 11;
				backgroundImageY = shapeList.get(0).getBounds().y;
				g2.drawImage(backgroundImage, backgroundImageX, backgroundImageY, null);
				
				
			}

			for (Polygon shape : shapeList) {

				g2.setColor(BORDER_COLOR);
				g2.setStroke(STROKE);

				if (pressedCursorPoint != null && currentCursorPoint != null && dragging && !Chit.isAChitSelected()) {

					shape.translate(currentCursorPoint.x - pressedCursorPoint.x,
							currentCursorPoint.y - pressedCursorPoint.y);
					g2.draw(shape);
				} else if (zoom != oldZoom) {

					int baseX = shapeList.get(0).getBounds().x;
					int baseY = shapeList.get(0).getBounds().y;

					makeHexes(rows, columns);

					for (Polygon newShape : shapeList) {

						newShape.translate(baseX - cordX, baseY - cordY);
						g2.draw(newShape);

					}

					break;

				} else {
					g2.draw(shape);
				}
				

			}
			//System.out.println("Columns: "+columns);
			//System.out.println("Rows: "+rows);
			//System.out.println("Hex Map Size: "+hexMap.size()+", row size: "+hexMap.get(0).size());
			for (int i = 0; i < rows; i++) {

				for (int j = 0; j < columns; j++) {
					//System.out.println("i: "+i+" j: "+j);
					Polygon hex = hexMap.get(i).get(j);
					
					
					Color color = g2.getColor();
					g2.setColor(Color.GREEN);
					if(gameWindow.findHex(i, j) == null) {
						//g2.drawString("U", hex.xpoints[0],hex.ypoints[0]);
						if(!hideU)
							g2.drawString("U", (int) (hex.xpoints[0]-(hex.getBounds().width*0.5)), (int)(hex.ypoints[0]+(hex.getBounds().height*0.3)));
					}
					g2.setColor(color);

					int count = 1;

					for (DeployedUnit deployedUnit : deployedUnits) {

						if(deployedUnit.unit.callsign.equals("ghost1")) {
							
							//System.out.println("Ghost 1 in delpoyed units, i: "+i+", j: "+j+", xCord: "+deployedUnit.xCord+", yCord: "+deployedUnit.yCord);							
						}
						
						if (selectedUnit != null && deployedUnit.unit.callsign.equals(selectedUnit.unit.callsign))
							continue;

						if (deployedUnit.xCord == i && deployedUnit.yCord == j && HexGridUtility.canShow(shownType, deployedUnit.unit)) {


							drawUnit(deployedUnit, g, g2, count);
							
							count++;

						}

					}

					if (selectedUnit != null && selectedUnit.xCord == i && selectedUnit.yCord == j) {

						drawUnit(selectedUnit, g, g2, count);
						
						for (Unit unit : selectedUnit.unit.lineOfSight) {
							
							if(!HexGridUtility.canShow(shownType, unit))
								continue; 

							for (DeployedUnit targetUnit : deployedUnits) {

								if (selectedUnit.unit.callsign.equals(targetUnit.unit.callsign))
									continue;

								if (targetUnit.unit.callsign.equals(unit.callsign)) {

									if(selectedUnits.contains(targetUnit))
										continue; 
									
									hex = hexMap.get(targetUnit.xCord).get(targetUnit.yCord);

									int hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;
									int hexCenterY = hex.getBounds().y + hex.getBounds().height / 2;

									if (targetUnit.unit.side.equals("OPFOR")) {
										// System.out.println("pass draw 1");
										Polygon diamond = new Polygon();

										// Top point
										diamond.addPoint(hexCenterX, (int) (hexCenterY
												- opforUnitHeight / 2 - 3));
										// Left point
										diamond.addPoint(
												(int) (hexCenterX - opforUnitWidth / 2 - 3),
												hexCenterY);
										// Bottom point
										diamond.addPoint(hexCenterX, (int) (hexCenterY
												+ opforUnitHeight / 2 + 3));
										// Right point
										diamond.addPoint(
												(int) (hexCenterX + opforUnitWidth / 2 + 3),
												hexCenterY);

										g2.setColor(Color.MAGENTA);
										g2.setStroke(new BasicStroke(2f));
										g2.draw(diamond);
										// System.out.println("pass draw 2");
									} else {

										g2.setColor(Color.MAGENTA);
										g2.setStroke(new BasicStroke(3f));
										
										Polygon outline = new Polygon();
										
										
										// Top Left point
										outline.addPoint(
												hexCenterX-bluforUnitWidth/2,
												hexCenterY+ bluforUnitHeight/2);
										
										// Bottom left point
										outline.addPoint(hexCenterX-bluforUnitWidth/2, 
												hexCenterY - bluforUnitHeight/2);
										
										// Bottom Right point
										outline.addPoint(hexCenterX+bluforUnitWidth/2, 
												hexCenterY - bluforUnitHeight/2);
										
										// Top Right Point
										outline.addPoint(hexCenterX +bluforUnitWidth/2,
												hexCenterY + bluforUnitHeight/2);
										
										g2.draw(outline);
										g2.setStroke(new BasicStroke(2f));
									}

								}

							}

						}

						count++;
					}

				}

			}
			
			
			translateSelectedChit();

			drawChitShadow(g2);
			
			drawChits(g2);
			
			if(HexGrid.losThreadShowing && losThread.npoints > 1) {
				
				//System.out.println("Thread: ("+losThread.xpoints[0]+", "+losThread.ypoints[0]+"), "+"( "+losThread.xpoints[1]+", "+losThread.ypoints[1]+")");
				
				int x1 = losThread.xpoints[0], y1 = losThread.ypoints[0], x2 = losThread.xpoints[1], y2 = losThread.ypoints[1];
				
				
				
				
				if(getHexFromPoint(x1, y1) != null && getHexFromPoint(x2, y2) != null) {
					drawThread(g2, x1, y1, x2, y2);
				} else if(getHexFromPoint(x1-5, y1-3) != null && getHexFromPoint(x2 + 5, y2 + 5) != null) {
					drawThread(g2, x1-5, y1-3, x2 + 5, y2 + 5);
				}
				
			}

			pressedCursorPoint = currentCursorPoint;
			oldZoom = zoom;
			
		}
		
		public void translateSelectedChit() {
			if (pressedCursorPoint != null && currentCursorPoint != null && dragging && Chit.isAChitSelected()) {
				Chit.translateChit(currentCursorPoint.x - pressedCursorPoint.x,
						currentCursorPoint.y - pressedCursorPoint.y);
			}
		}
		
		public void drawChitShadow(Graphics2D g2) {
			if (currentCursorPoint == null || !Chit.isAChitSelected())
				return;

			setOpacity(0.5f, g2);

			int[] points = getHexFromPoint(currentCursorPoint);
			if (points == null)
				return;

			Chit.drawShadow(zoom, g2, hexMap.get(points[0]).get(points[1]));

			setOpacity(1f, g2);

		}
		
		public void setOpacity(float alpha, Graphics2D g2) {
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			g2.setComposite(ac);
		}
		
		public void drawThread(Graphics2D g2, int x1, int y1, int x2, int y2) {
			// Draw line between two points 
			//System.out.println("Draw");
			g2.setColor(Color.MAGENTA);
			g2.setStroke(new BasicStroke(1f));
			g2.draw(losThread);
			
			g2.setColor(Color.BLACK);
			String rslts = "Range: "; 
			rslts += GameWindow.hexDif(getHexFromPoint(x1, y1)[0], getHexFromPoint(x1, y1)[1], getHexFromPoint(x2, y2)[0], getHexFromPoint(x2, y2)[1]);
			FontMetrics fm = g2.getFontMetrics();
			Rectangle2D rect = fm.getStringBounds(rslts, g2);
			g2.fillRect(x2 + 5, y2 - fm.getAscent(), (int) rect.getWidth(), (int) rect.getHeight());
			g2.setColor(Color.MAGENTA);
			g2.drawString(rslts, x2 + 5, y2);
		}
		
		private class TimerListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) { 
	        	
	        	
	        	repaint();
	        	
	        	if(changedUnits) {
	        		refreshDeployedUnits();
	        		changedUnits = false; 
	        	}
	        	
	        	fpscnt.frame();
	        	//System.out.println("FPS: "+fpscnt.get());	
	        }
	    }


	}
}
