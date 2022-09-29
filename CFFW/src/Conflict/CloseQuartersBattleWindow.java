package Conflict;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JSpinner;

import Hexes.Building;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;
import UtilityClasses.TrooperUtility;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.event.ListSelectionListener;

import org.apache.commons.collections4.MultiMap;

import Actions.Spot;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CloseQuartersBattleWindow {

	private JFrame frame;
	private JList listUnits;
	private JList listIndividual;
	public GameWindow gameWindow;
	public OpenUnit openUnit;

	/**
	 * Create the application.
	 */
	public CloseQuartersBattleWindow(GameWindow gameWindow, OpenUnit openUnit) {
		this.gameWindow = gameWindow;
		this.openUnit = openUnit;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 999, 552);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		JLabel lblIndividualList = new JLabel("Unit List");
		lblIndividualList.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIndividualList.setBounds(10, 11, 143, 26);
		frame.getContentPane().add(lblIndividualList);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 382, 460);
		frame.getContentPane().add(scrollPane);

		listUnits = new JList();
		listUnits.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {

				refreshIndividualList();

			}
		});
		scrollPane.setViewportView(listUnits);

		JLabel lblIndividualList_1 = new JLabel("Individual List");
		lblIndividualList_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIndividualList_1.setBounds(402, 11, 143, 26);
		frame.getContentPane().add(lblIndividualList_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(402, 40, 418, 460);
		frame.getContentPane().add(scrollPane_1);

		listIndividual = new JList();
		scrollPane_1.setViewportView(listIndividual);

		JButton btnNewButton = new JButton("Set Attacking");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (listIndividual.getSelectedIndices().length < 1)
					return;

				Unit unit = gameWindow.cqb.unitsInCloseCombat.get(listUnits.getSelectedIndex());

				ArrayList<Trooper> activeTroopers = new ArrayList<>();

				for (Trooper trooper : unit.individuals) {
					if (trooper.alive && trooper.conscious)
						activeTroopers.add(TrooperUtility.getRealTrooperReference(trooper));
				}

				for (int i : listIndividual.getSelectedIndices()) {

					activeTroopers.get(i).attackingInCloseCombat = true;
					activeTroopers.get(i).defendingInCloseCombat = false;
					activeTroopers.get(i).HD = false;
				}

				refreshIndividualList();

			}
		});
		btnNewButton.setBounds(830, 40, 143, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnSetWithdraw = new JButton("Set Defending");
		btnSetWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listIndividual.getSelectedIndices().length < 1)
					return;

				Unit unit = gameWindow.cqb.unitsInCloseCombat.get(listUnits.getSelectedIndex());

				ArrayList<Trooper> activeTroopers = new ArrayList<>();

				for (Trooper trooper : unit.individuals) {
					if (trooper.alive && trooper.conscious)
						activeTroopers.add(TrooperUtility.getRealTrooperReference(trooper));
				}

				for (int i : listIndividual.getSelectedIndices()) {
					System.out.println("I Trooper: "+activeTroopers.get(i).name);
					activeTroopers.get(i).attackingInCloseCombat = false;
					activeTroopers.get(i).defendingInCloseCombat = true;
					activeTroopers.get(i).HD = false;
				}

				refreshIndividualList();
			}
		});
		btnSetWithdraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		btnSetWithdraw.setBounds(830, 74, 143, 23);
		frame.getContentPane().add(btnSetWithdraw);

		JButton btnRemove = new JButton("Remove Unit");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listUnits.getSelectedIndex() < 0) {
					return;
				}

				gameWindow.cqb.unitsInCloseCombat.remove(listUnits.getSelectedIndex());

				refreshUnitList();

			}
		});
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				
			}
		});
		btnRemove.setBounds(830, 139, 143, 23);
		frame.getContentPane().add(btnRemove);

		JButton btnSetHd = new JButton("Set HD");
		btnSetHd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (listIndividual.getSelectedIndices().length < 1)
					return;

				Unit unit = gameWindow.cqb.unitsInCloseCombat.get(listUnits.getSelectedIndex());

				ArrayList<Trooper> activeTroopers = new ArrayList<>();

				for (Trooper trooper : unit.individuals) {
					if (trooper.alive && trooper.conscious)
						activeTroopers.add(TrooperUtility.getRealTrooperReference(trooper));
				}

				for (int i : listIndividual.getSelectedIndices()) {
					activeTroopers.get(i).hunkerDown(gameWindow);
					activeTroopers.get(i).attackingInCloseCombat = false;
					activeTroopers.get(i).defendingInCloseCombat = false;
				}

				refreshIndividualList();
			}
		});
		btnSetHd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				

			}
		});
		btnSetHd.setBounds(830, 105, 143, 23);
		frame.getContentPane().add(btnSetHd);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				gameWindow.cqb.setTargets();
				gameWindow.cqbWindowOpen = false;

				// Unit activeUnit = gameWindow.initiativeOrder.get(gameWindow.activeUnit);
				ArrayList<Trooper> cqbTroopers = getCQBTroopers();
				String[][] trooperArray = getCqbAfArray(cqbTroopers);
				ArrayList<Trooper> listOfTroopers = getTroopersFromIdentifier(trooperArray);
				if(listOfTroopers.size() > 0) {					
					new BulkWindow(openUnit.unit, gameWindow, openUnit, listOfTroopers);
					gameWindow.cqbWindowOpen = true;
				}
				
				frame.dispose();

				/*
				 * System.out.println("CQB Troopers Size: " + cqbTroopers.size());
				 * 
				 * if (cqbTroopers.size() > 0) {
				 * 
				 * MultiMap<Integer, Trooper> cqbt_dict = new MultiMap<>();
				 * 
				 * int count = 0; for (Trooper cqbt : cqbTroopers) { count++;
				 * 
				 * cqbt.spotted.clear(); cqbt.spotted.add(new Spot());
				 * cqbt.spotted.get(0).spottedIndividuals.add(cqbt.closeCombatTarget);
				 * 
				 * if (cqbt.compareTo(cqbt.closeCombatTarget)) {
				 * 
				 * System.out.println("This trooper is actually shooting himself: " +
				 * cqbt.toStringImproved(gameWindow.game));
				 * 
				 * }
				 * 
				 * cqbt_dict.put(cqbt.adaptabilityFactor - new Random().nextInt(6) + 1, cqbt);
				 * count++; }
				 * 
				 * System.out.println("Count: " + count);
				 * 
				 * System.out.println("Dict Size: " + cqbt_dict.size());
				 * 
				 * List<Map.Entry<Integer, Collection<Trooper>>> entries = new
				 * ArrayList<Map.Entry<Integer, Collection<Trooper>>>( cqbt_dict.entrySet());
				 * Collections.sort(entries, new Comparator<Map.Entry<Integer,
				 * Collection<Trooper>>>() { public int compare(Map.Entry<Integer,
				 * Collection<Trooper>> a, Map.Entry<Integer, Collection<Trooper>> b) { return
				 * b.getKey().compareTo(a.getKey()); } }); Map<Integer, Collection<Trooper>>
				 * sortedMap = new LinkedHashMap<Integer, Collection<Trooper>>(); for
				 * (Map.Entry<Integer, Collection<Trooper>> entry : entries) {
				 * sortedMap.put(entry.getKey(), entry.getValue()); }
				 * 
				 * for (int i : sortedMap.keySet()) {
				 * 
				 * for (Trooper trooper : sortedMap.get(i)) { System.out.println("AF: " + i +
				 * ", Trooper: " + trooper.toStringImproved(gameWindow.game)); }
				 * 
				 * // System.out.println("AF: "+i+", Trooper: //
				 * "+sortedMap.get(i).toStringImproved(gameWindow.game));
				 * 
				 * }
				 * 
				 * ArrayList<Trooper> sortedList = new ArrayList<>();
				 * 
				 * System.out.println("Begining Sorted List: "); for (Collection<Trooper>
				 * troopers : sortedMap.values()) {
				 * 
				 * for (Trooper trooper : troopers) { System.out.println("CQB Trooper: " +
				 * trooper.name + ", Code: " + System.identityHashCode(trooper) +
				 * ", Identifier: " + trooper.identifier);
				 * 
				 * sortedList.add(trooper); }
				 * 
				 * }
				 * 
				 * for (Unit realUnit : GameWindow.gameWindow.initiativeOrder) { for (Trooper
				 * realTrooper : realUnit.individuals) { System.out.println("Real Trooper: " +
				 * realTrooper.name + ", Code: " + System.identityHashCode(realTrooper) +
				 * ", Identifier: " + realTrooper.identifier); } }
				 * 
				 * for (Trooper trooper : sortedList) { for (Spot spot : trooper.spotted) {
				 * System.out.println("Spot Trooper: " + spot.spottedIndividuals.get(0).name +
				 * ", Code: " + System.identityHashCode(spot.spottedIndividuals.get(0)) +
				 * ", Identifier: " + spot.spottedIndividuals.get(0).identifier); }
				 * 
				 * }
				 * 
				 * for (Trooper trooper : sortedList) { System.out.println("Target Trooper: " +
				 * trooper.closeCombatTarget.name + ", Code: " +
				 * System.identityHashCode(trooper.closeCombatTarget) + ", Identifier: " +
				 * trooper.closeCombatTarget.identifier); }
				 * 
				 * System.out.println("Sorted List Size: " + sortedList.size());
				 * 
				 * ArrayList<Trooper> correctTrooperList = convertTrooperArrayList(sortedList);
				 * 
				 * for (Trooper correctTrooper : correctTrooperList) {
				 * 
				 * System.out.println("Correct Trooper: " + correctTrooper.name + ", Code: " +
				 * System.identityHashCode(correctTrooper) + ", Identifier: " +
				 * correctTrooper.identifier);
				 * 
				 * System.out.println("Correct Target Trooper: " +
				 * correctTrooper.closeCombatTarget.name + ", Code: " +
				 * System.identityHashCode(correctTrooper.closeCombatTarget) + ", Identifier: "
				 * + correctTrooper.closeCombatTarget.identifier);
				 * 
				 * for (Spot spot : correctTrooper.spotted) {
				 * System.out.println("Correct Spot Trooper: " +
				 * spot.spottedIndividuals.get(0).name + ", Code: " +
				 * System.identityHashCode(spot.spottedIndividuals.get(0)) + ", Identifier: " +
				 * spot.spottedIndividuals.get(0).identifier); } }
				 * 
				 * new BulkWindow(openUnit.unit, gameWindow, openUnit, correctTrooperList);
				 * 
				 * gameWindow.cqbWindowOpen = true;
				 * 
				 * }
				 * 
				 * frame.dispose();
				 */

			}
		});
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		btnConfirm.setBounds(830, 466, 143, 36);
		frame.getContentPane().add(btnConfirm);
		frame.setVisible(true);

		refreshUnitList();

	}

	// Takes an array list of troopers
	// Rolls AF for each trooper
	// Puts troopers in 2d string array {AF Result, trooper identifier}
	// Returns Multi Array
	public String[][] getCqbAfArray(ArrayList<Trooper> cqbTroopers) {
		String[][] trooperArray = new String[cqbTroopers.size()][cqbTroopers.size()];

		for (int i = 0; i < cqbTroopers.size(); i++) {
			int af = cqbTroopers.get(i).adaptabilityFactor - DiceRoller.randInt(1, 6);
			if(!cqbTroopers.get(i).attackingInCloseCombat) {
				af++; 
			}
			trooperArray[i] = new String[] { Integer.toString(af), cqbTroopers.get(i).identifier };
		}

		// Arrays.sort(trooperArray, Comparator.comparing(o -> o[0]));

		java.util.Arrays.sort(trooperArray, new java.util.Comparator<String[]>() {
			public int compare(String[] a, String[] b) {
				// compare the first element
				int x = Integer.parseInt(a[0]);
				int y = Integer.parseInt(b[0]);
				return (x > y) ? -1 : ((x == y) ? 0 : 1);

			}
		});
		GameWindow.gameWindow.conflictLog.addNewLine("----------AF Rolls BEGIN----------");
		//System.out.println("----------AF Rolls BEGIN----------");

		for (int i = 0; i < trooperArray.length; i++) {
			int af = Integer.parseInt(trooperArray[i][0]);
			Trooper trooper = getTrooperFromIdentifier(trooperArray[i][1]);
			GameWindow.gameWindow.conflictLog.addNewLine("AF: " + af + ", " + trooper.number + " " + trooper.name);
			//System.out.println("AF: " + af + ", " + trooper.number + " " + trooper.name);
		}
		GameWindow.gameWindow.conflictLog.addNewLine("----------AF Rolls END----------");
		//System.out.println("----------AF Rolls END----------");

		return trooperArray;
	}

	// Gets CQB Troopers, all troopers with a valid close combat target
	public ArrayList<Trooper> getCQBTroopers() {
		// Unit activeUnit = gameWindow.initiativeOrder.get(gameWindow.activeUnit);
		ArrayList<Trooper> cqbTroopers = new ArrayList<>();

		for (Unit tempUnit : gameWindow.initiativeOrder) {
			for (Trooper trooper : tempUnit.individuals) {

				if (trooper.closeCombatTarget != null) {
					trooper.spotted.clear();
					trooper.spotted.add(new Spot());
					trooper.spotted.get(0).spottedIndividuals.add(trooper.closeCombatTarget);
					cqbTroopers.add(trooper);
				}

			}
		}
		System.out.println("CQB Troopers Size: " + cqbTroopers.size());
		return cqbTroopers;
	}

	// Takes arrayList of incorrect troopers
	// Returns array list of correct troopers
	public ArrayList<Trooper> convertTrooperArrayList(ArrayList<Trooper> incorrectTrooperList) {
		ArrayList<Trooper> troopers = new ArrayList<>();

		for (Trooper incorrectTrooper : incorrectTrooperList) {

			Trooper correctTrooper = TrooperUtility.getRealTrooperReference(incorrectTrooper);
			correctTrooper.closeCombatTarget = TrooperUtility.getRealTrooperReference(correctTrooper.closeCombatTarget);
			ArrayList<Trooper> inccorectSpotted = new ArrayList<>();

			for (Spot spot : correctTrooper.spotted) {

				for (Trooper spottedTrooper : spot.spottedIndividuals) {
					inccorectSpotted.add(spottedTrooper);
				}

			}

			if (inccorectSpotted.size() > 0) {

				correctTrooper.spotted.clear();
				Spot spot = new Spot();
				for (Trooper spottedTrooper : inccorectSpotted)
					spot.spottedIndividuals.add(TrooperUtility.getRealTrooperReference(spottedTrooper));
				correctTrooper.spotted.add(spot);
			}

			troopers.add(correctTrooper);
		}

		return troopers;
	}

	public ArrayList<Trooper> getTroopersFromIdentifier(String[][] trooperArray) {

		ArrayList<Trooper> troopers = new ArrayList<>();

		for (int i = 0; i < trooperArray.length; i++) {
			troopers.add(getTrooperFromIdentifier(trooperArray[i][1]));
		}

		return troopers;
	}

	public Trooper getTrooperFromIdentifier(String identifier) {

		for (Unit unit : GameWindow.gameWindow.initiativeOrder) {
			for (Trooper trooper : unit.individuals) {
				if (trooper.identifier.equals(identifier))
					return trooper;
			}
		}
		System.out.println("ERROR, Get Trooper from identifier returning NULL");
		return null;
	}

	

	class MultiMap<K, V> {
		private Map<K, Collection<V>> map = new HashMap<>();

		/**
		 * Add the specified value with the specified key in this multimap.
		 */
		public void put(K key, V value) {
			if (map.get(key) == null) {
				map.put(key, new ArrayList<V>());
			}

			map.get(key).add(value);
		}

		/**
		 * Associate the specified key with the given value if not already associated
		 * with a value
		 */
		public void putIfAbsent(K key, V value) {
			if (map.get(key) == null) {
				map.put(key, new ArrayList<>());
			}

			// if the value is absent, insert it
			if (!map.get(key).contains(value)) {
				map.get(key).add(value);
			}
		}

		/**
		 * Returns the Collection of values to which the specified key is mapped, or
		 * null if this multimap contains no mapping for the key.
		 */
		public Collection<V> get(Object key) {
			return map.get(key);
		}

		/**
		 * Returns a set view of the keys contained in this multimap.
		 */
		public Set<K> keySet() {
			return map.keySet();
		}

		/**
		 * Returns a set view of the mappings contained in this multimap.
		 */
		public Set<Map.Entry<K, Collection<V>>> entrySet() {
			return map.entrySet();
		}

		/**
		 * Returns a Collection view of Collection of the values present in this
		 * multimap.
		 */
		public Collection<Collection<V>> values() {
			return map.values();
		}

		/**
		 * Returns true if this multimap contains a mapping for the specified key.
		 */
		public boolean containsKey(Object key) {
			return map.containsKey(key);
		}

		/**
		 * Removes the mapping for the specified key from this multimap if present and
		 * returns the Collection of previous values associated with key, or null if
		 * there was no mapping for key.
		 */
		public Collection<V> remove(Object key) {
			return map.remove(key);
		}

		/**
		 * Returns the total number of key-value mappings in this multimap.
		 */
		public int size() {
			int size = 0;
			for (Collection<V> value : map.values()) {
				size += value.size();
			}
			return size;
		}

		/**
		 * Returns true if this multimap contains no key-value mappings.
		 */
		public boolean isEmpty() {
			return map.isEmpty();
		}

		/**
		 * Removes all the mappings from this multimap.
		 */
		public void clear() {
			map.clear();
		}

		/**
		 * Removes the entry for the specified key only if it is currently mapped to the
		 * specified value and return true if removed
		 */
		public boolean remove(K key, V value) {
			if (map.get(key) != null) // key exists
				return map.get(key).remove(value);

			return false;
		}

		/**
		 * Replaces the entry for the specified key only if currently mapped to the
		 * specified value and return true if replaced
		 */
		public boolean replace(K key, V oldValue, V newValue) {
			if (map.get(key) != null) {
				if (map.get(key).remove(oldValue)) {
					return map.get(key).add(newValue);
				}
			}
			return false;
		}
	}

	/*
	 * public class MultiValueMap<K,V> { private final Map<K,Set<V>> mappings = new
	 * HashMap<K,Set<V>>();
	 * 
	 * public Set<V> getValues(K key) { return mappings.get(key); }
	 * 
	 * public Boolean putValue(K key, V value) { Set<V> target = mappings.get(key);
	 * 
	 * if(target == null) { target = new HashSet<V>(); mappings.put(key,target); }
	 * 
	 * return target.add(value); }
	 * 
	 * }
	 */

	public void refreshUnitList() {

		int selectedUnitIndex = listUnits.getSelectedIndex();

		DefaultListModel<String> model = new DefaultListModel<>();

		for (Unit unit : gameWindow.cqb.unitsInCloseCombat) {

			model.addElement(unit.side + ":: " + unit.callsign + " X: " + unit.X + ", Y: " + unit.Y);

		}

		listUnits.setModel(model);

		if (selectedUnitIndex > -1)
			listUnits.setSelectedIndex(selectedUnitIndex);

	}

	public void refreshIndividualList() {

		if (listUnits.getSelectedIndex() < 0)
			return;

		Unit selectedUnit = gameWindow.cqb.unitsInCloseCombat.get(listUnits.getSelectedIndex());

		DefaultListModel<String> model = new DefaultListModel<>();

		for (Trooper trooper : selectedUnit.individuals) {

			// If trooper not alive, not conscious or not in a building continue
			if (!trooper.alive || !trooper.conscious
					|| gameWindow.findHex(selectedUnit.X, selectedUnit.Y).getTrooperBuilding(trooper) == null)
				continue;

			Building building = gameWindow.findHex(selectedUnit.X, selectedUnit.Y).getTrooperBuilding(trooper);
			String buildingName = building.name;
			int floorNumber = gameWindow.findHex(selectedUnit.X, selectedUnit.Y).getTrooperFloorNumber(trooper);
			int roomNumber = gameWindow.findHex(selectedUnit.X, selectedUnit.Y).getTrooperRoomNumber(trooper);

			String status = "UNSPECIFIED: ";

			if (trooper.defendingInCloseCombat) {
				status = "DEFENDING: ";
			} else if (trooper.attackingInCloseCombat) {
				status = "ATTACKING: ";
			} else if (trooper.HD) {
				status = "HUNKERED DOWN: ";
			}

			model.addElement(status + buildingName + ", Floor Num: " + floorNumber + ", Room Num: " + roomNumber + ", "
					+ trooper.toStringImproved(gameWindow.game));

		}

		listIndividual.setModel(model);

		listIndividual.setSelectedIndex(-1);

	}

}
