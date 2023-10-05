package Melee.Window;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

import Conflict.GameWindow;
import Melee.Bout;
import Melee.MeleeCombatCalculator;
import Melee.MeleeCombatUnit;
import Melee.MeleeManager;
import Melee.MeleeWithdraw;
import Trooper.Trooper;
import UtilityClasses.SwingUtility;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

public class MeleeCombatWindow {

	public static MeleeCombatWindow meleeCombatWindow;

	public JFrame frame;
	private JList listEligableMeleeCombatUnits;
	private JList listTargetList;
	private JPanel Individuals;
	private JPanel bouts;
	private JList listMeleeCombatIndividuals;
	private JList listTargetIndividuals;
	private JList listBouts;

	/**
	 * Create the application.
	 */
	public MeleeCombatWindow() {
		if (meleeCombatWindow != null)
			meleeCombatWindow.frame.dispose();

		initialize();
		setMeleeCombatUnitList();
		setMeleeCombatUnitStatuses();
		meleeCombatWindow = this;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 835);
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

		JLabel lblNewLabel = new JLabel("Melee Combat Units:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 314, 28);
		frame.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 467, 350);
		frame.getContentPane().add(scrollPane);

		listEligableMeleeCombatUnits = new JList();
		listEligableMeleeCombatUnits.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				setMeleeCombatIndividuals();
				setChargeTargets();
				setBouts();
			}
		});
		scrollPane.setViewportView(listEligableMeleeCombatUnits);

		JLabel lblEligableTargetList = new JLabel("Target Unit List:");
		lblEligableTargetList.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEligableTargetList.setBounds(487, 11, 314, 28);
		frame.getContentPane().add(lblEligableTargetList);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(487, 50, 487, 350);
		frame.getContentPane().add(scrollPane_1);

		listTargetList = new JList();
		scrollPane_1.setViewportView(listTargetList);

		JButton btnNewButton = new JButton("Charge");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getSelectedMeleeCombatUnits().size() < 1 || listTargetList.getSelectedIndex() < 0)
					return;

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						var chargingUnits = getSelectedMeleeCombatUnits();
						var targetUnits = getChargeTargets();

						var target = targetUnits.get(listTargetList.getSelectedIndex());

						for (var chargingUnit : chargingUnits) {
							chargingUnit.formationWidth = chargingUnit.meleeCombatIndividuals.size();
							MeleeCombatCalculator.charge(chargingUnit, target);

						}
						return null;
					}

					@Override
					protected void done() {

						GameWindow.gameWindow.conflictLog.addQueuedText();
						setBouts();
					}

				};

				worker.execute();

			}
		});
		btnNewButton.setBounds(10, 411, 117, 28);
		frame.getContentPane().add(btnNewButton);

		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getSelectedMeleeCombatUnits().size() < 1)
					return;
				
				
				var unit = getSelectedMeleeCombatUnits().get(0);
				
				GameWindow.gameWindow.conflictLog.addNewLineToQueue(unit.unit.callsign+" withdraw selected bouts.");
				
				var indices = listBouts.getSelectedIndices();
				
				ArrayList<Bout> bouts = new ArrayList<Bout>();
				ArrayList<Trooper> troopers = new ArrayList<Trooper>();

				for (var combatIndividual : unit.meleeCombatIndividuals) {

					for (var bout : MeleeManager.meleeManager.meleeCombatBouts) {

						if (bout.combatantA.trooper.compareTo(combatIndividual)) {
							bouts.add(bout);
						} else if (bout.combatantB.trooper.compareTo(combatIndividual)) {
							bouts.add(bout);
						}

					}

				}
				
				ArrayList<Bout> selectedBouts = new ArrayList<Bout>();
				
				for(var index : indices) {
					
					var bout = bouts.get(index);
					
					for (var combatIndividual : unit.meleeCombatIndividuals) {

						if (bout.combatantA.trooper.compareTo(combatIndividual)) {
							troopers.add(combatIndividual);
							selectedBouts.add(bout);
							GameWindow.gameWindow.conflictLog.addNewLineToQueue("Withdraw bout: "+bout.toString());
						} else if (bout.combatantB.trooper.compareTo(combatIndividual)) {
							troopers.add(combatIndividual);
							GameWindow.gameWindow.conflictLog.addNewLineToQueue("Withdraw bout: "+bout.toString());
							selectedBouts.add(bout);
						}

						

					}
					
				}
				
				MeleeWithdraw.withdraw(selectedBouts, troopers);
				
				GameWindow.gameWindow.conflictLog.addQueuedText();
				
			}
		});
		btnWithdraw.setBounds(138, 411, 117, 28);
		frame.getContentPane().add(btnWithdraw);

		JButton btnToggleIndividual = new JButton("Toggle Individual");
		btnToggleIndividual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getSelectedMeleeCombatUnits().size() < 1)
					return;

				var unit = getSelectedMeleeCombatUnits().get(0);

				var indviduals = getSelectedMeleeCombatIndividuals();

				for (var i : indviduals) {

					if (unit.meleeCombatIndividuals.contains(i)) {
						unit.meleeCombatIndividuals.remove(i);
					} else {
						unit.meleeCombatIndividuals.add(i);
					}

				}

				setMeleeCombatIndividuals();
			}
		});
		btnToggleIndividual.setBounds(265, 411, 146, 28);
		frame.getContentPane().add(btnToggleIndividual);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 450, 964, 350);
		frame.getContentPane().add(tabbedPane);

		Individuals = new JPanel();
		tabbedPane.addTab("Individuals", null, Individuals, null);
		Individuals.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 66, 457, 245);
		Individuals.add(scrollPane_2);

		listMeleeCombatIndividuals = new JList();
		scrollPane_2.setViewportView(listMeleeCombatIndividuals);

		JButton btnEpicChallenge = new JButton("Epic Challenge");
		btnEpicChallenge.setBounds(347, 34, 120, 28);
		Individuals.add(btnEpicChallenge);

		JScrollPane scrollPane_2_1 = new JScrollPane();
		scrollPane_2_1.setBounds(477, 66, 472, 245);
		Individuals.add(scrollPane_2_1);

		listTargetIndividuals = new JList();
		scrollPane_2_1.setViewportView(listTargetIndividuals);

		JLabel lblWidth = new JLabel("Formation Width:");
		lblWidth.setBounds(10, 41, 130, 14);
		Individuals.add(lblWidth);

		JLabel lblRanks = new JLabel("Formation Ranks:");
		lblRanks.setBounds(164, 41, 130, 14);
		Individuals.add(lblRanks);

		JLabel lblMeleeCombatIndividuals = new JLabel("Melee Combat Individuals:");
		lblMeleeCombatIndividuals.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMeleeCombatIndividuals.setBounds(10, 11, 314, 28);
		Individuals.add(lblMeleeCombatIndividuals);

		JLabel lblTargetIndividuals = new JLabel("Target Individuals");
		lblTargetIndividuals.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTargetIndividuals.setBounds(477, 32, 314, 28);
		Individuals.add(lblTargetIndividuals);

		bouts = new JPanel();
		tabbedPane.addTab("Bouts", null, bouts, null);
		bouts.setLayout(null);

		JLabel lblBouts = new JLabel("Bouts");
		lblBouts.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBouts.setBounds(10, 11, 314, 28);
		bouts.add(lblBouts);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 46, 449, 265);
		bouts.add(scrollPane_3);

		listBouts = new JList();
		scrollPane_3.setViewportView(listBouts);

		frame.setVisible(true);
	}

	public void setMeleeCombatUnitStatuses() {

		for (var unit : MeleeManager.meleeManager.meleeCombatUnits) {

			for (var individual : unit.unit.individuals) {

				if (!unit.meleeCombatIndividuals.contains(individual))
					unit.meleeCombatIndividuals.add(individual);

			}

			unit.formationRanks = 1;
			unit.formationWidth = unit.meleeCombatIndividuals.size();

		}

	}

	public void setMeleeCombatUnitList() {

		ArrayList<String> combatUnitList = new ArrayList<String>();

		for (var unit : MeleeManager.meleeManager.meleeCombatUnits) {
			combatUnitList.add(unit.unit.side + ":: " + unit.unit.callsign);
		}

		SwingUtility.setList(listEligableMeleeCombatUnits, combatUnitList);

	}

	public void setMeleeCombatIndividuals() {
		ArrayList<String> individuals = new ArrayList<String>();
		var selectedUnits = getSelectedMeleeCombatUnits();

		if (selectedUnits.size() > 0)
			for (var individual : selectedUnits.get(0).unit.individuals)
				individuals.add(selectedUnits.get(0).unit.callsign + " " + individual.number + ":: " + individual.name
						+ (selectedUnits.get(0).meleeCombatIndividuals.contains(individual) ? " ACTIVE" : " INACTIVE")
						+ ", Alive: "+individual.alive+", Conscious: "+individual.conscious+", PD: "+individual.physicalDamage);

		SwingUtility.setList(listMeleeCombatIndividuals, individuals);

	}

	public void setChargeTargets() {

		ArrayList<String> chargeTargets = new ArrayList<String>();
		var targets = getChargeTargets();

		if (targets.size() > 0)
			for (var unit : targets)
				chargeTargets.add(unit.unit.side + ":: " + unit.unit.callsign);

		SwingUtility.setList(listTargetList, chargeTargets);
	}

	public void setBouts() {

		ArrayList<String> bouts = new ArrayList<String>();

		for (var unit : getSelectedMeleeCombatUnits()) {

			for (var combatIndividual : unit.meleeCombatIndividuals) {

				for (var bout : MeleeManager.meleeManager.meleeCombatBouts) {

					if (bout.combatantA.trooper.compareTo(combatIndividual)) {
						bouts.add(bout.toString());
					} else if (bout.combatantB.trooper.compareTo(combatIndividual)) {
						bouts.add(bout.toString());
					}

				}

			}

		}

		SwingUtility.setList(listBouts, bouts);

	}
	
	private ArrayList<MeleeCombatUnit> getChargeTargets() {

		ArrayList<MeleeCombatUnit> targets = new ArrayList<MeleeCombatUnit>();
		var selected = getSelectedMeleeCombatUnits();

		for (var combatUnit : selected) {

			var potentialTargets = validChargeTargets(combatUnit);

			for (var t : potentialTargets) {

				if (validTarget(t, selected)) {
					targets.add(t);
				}

			}

		}

		return targets;

	}

	private ArrayList<MeleeCombatUnit> getSelectedMeleeCombatUnits() {
		ArrayList<MeleeCombatUnit> list = new ArrayList<MeleeCombatUnit>();

		for (var index : listEligableMeleeCombatUnits.getSelectedIndices()) {

			list.add(MeleeManager.meleeManager.meleeCombatUnits.get(index));

		}

		return list;
	}

	private boolean validTarget(MeleeCombatUnit potentialTarget, ArrayList<MeleeCombatUnit> selected) {

		for (var otherUnit : selected) {
			if (otherUnit.unit.compareTo(potentialTarget.unit)) {
				continue;
			}

			if (!validChargeTargets(otherUnit).contains(potentialTarget))
				return false;

		}

		return true;
	}

	private ArrayList<MeleeCombatUnit> validChargeTargets(MeleeCombatUnit chargingUnit) {

		ArrayList<MeleeCombatUnit> chargeTargets = new ArrayList<MeleeCombatUnit>();

		for (var meleeUnit : MeleeManager.meleeManager.meleeCombatUnits)
			if (GameWindow.hexDif(chargingUnit.unit, meleeUnit.unit) <= 1
					&& meleeUnit.unit.side != chargingUnit.unit.side)
				chargeTargets.add(meleeUnit);

		return chargeTargets;
	}

	private ArrayList<Trooper> getSelectedMeleeCombatIndividuals() {

		if (getSelectedMeleeCombatUnits().size() < 1)
			return new ArrayList<Trooper>();

		ArrayList<Trooper> selectedMeleeCombatIndividuals = new ArrayList<Trooper>();

		var unit = getSelectedMeleeCombatUnits().get(0);

		for (var index : listMeleeCombatIndividuals.getSelectedIndices()) {
			selectedMeleeCombatIndividuals.add(unit.unit.individuals.get(index));
		}

		return selectedMeleeCombatIndividuals;

	}

}
