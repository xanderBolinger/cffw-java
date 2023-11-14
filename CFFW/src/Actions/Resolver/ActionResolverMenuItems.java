package Actions.Resolver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;

import Conflict.GameWindow;
import Unit.Unit;

public class ActionResolverMenuItems {

	public static JMenuItem getSpotItem() {
			
		JMenuItem item = new JMenuItem("Spot");

		ArrayList<Unit> spotterUnits = new ArrayList<Unit>();
		
		var selectedUnit = GameWindow.gameWindow.hexGrid.panel.selectedUnit;
		var deployedUnits = GameWindow.gameWindow.hexGrid.panel.deployedUnits;
		var selectedUnits = GameWindow.gameWindow.hexGrid.panel.selectedUnits;
		
		for(var dp : deployedUnits)
			if(selectedUnits.contains(dp) || (selectedUnit != null && selectedUnit.unit.compareTo(dp.unit))) 
				spotterUnits.add(dp.unit);
		
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ActionResolver.resolveSpotAction(spotterUnits, null);
				
			}
		});
		
		return item;
	}
	
	public static JMenuItem getSuppressItem(int xCord, int yCord) {
		
		// System.out.println("add Los, selectedunits Side: "+selectedUnits.size());

		var selectedUnit = GameWindow.gameWindow.hexGrid.panel.selectedUnit;
		var deployedUnits = GameWindow.gameWindow.hexGrid.panel.deployedUnits;
		var selectedUnits = GameWindow.gameWindow.hexGrid.panel.selectedUnits;

		ArrayList<Unit> shooterUnits = new ArrayList<Unit>();
		ArrayList<Unit> targetUnits = new ArrayList<Unit>();
		
		for(var dp : deployedUnits)
			if(selectedUnits.contains(dp) || (selectedUnit != null && selectedUnit.unit.compareTo(dp.unit))) 
				shooterUnits.add(dp.unit);
		
		if(shooterUnits.size() <= 0)
			return null;
		
		for(var dp : deployedUnits) {
			
			if(dp.unit.side.equals(shooterUnits.get(0).side))
				continue;
			if(dp.xCord == xCord && dp.yCord == yCord)
				targetUnits.add(dp.unit);
		}
		
		if(targetUnits.size() <= 0)
			return null;
		
		JMenuItem item = new JMenuItem("Suppress");

		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ActionResolver.resolveSuppressAction(shooterUnits, targetUnits);

			}
		});
		
		return item;
		
	}
	
}
