package Actions.Resolver;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import Actions.Spot;
import Conflict.GameWindow;
import Conflict.InjuryLog;
import HexGrid.HexGrid.DeployedUnit;
import Shoot.Shoot;
import Spot.Utility.SpotUtility;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class ActionResolver {

	static SuppressResolver suppressRsolver = new SuppressResolver();
	static SpotResolver spotResolver = new SpotResolver();
	
	public static void resolveSuppressAction(ArrayList<Unit> selectedUnits, ArrayList<Unit> targetUnits) {
		suppressRsolver.ResolveAction(selectedUnits, targetUnits);
	}
	
	public static void resolveSpotAction(ArrayList<Unit> selectedUnits, ArrayList<Unit> targetUnits) {
		SpotUtility.clearSpotted();
		GameWindow.gameWindow.conflictLog.addNewLine("Spotting...");
		
		SpotResolver.spotUnit = 0;
		spotResolver.ResolveAction(selectedUnits, targetUnits);
	}
	
	
	
}
