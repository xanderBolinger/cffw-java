package HexGrid.Shields;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import Fortifications.Fortification;
import Hexes.Hex;

public class ShieldManager implements Serializable {

	List<Hex> shieldHexes;
	
	public ShieldManager() {
		shieldHexes = new ArrayList<Hex>();
	} 
	
	public void showShields(Graphics2D g2) {
		if(GameWindow.gameWindow.shieldManager == null)
			return;
		
		var hexMap = GameWindow.gameWindow.hexGrid.panel.hexMap;
		
		for(var hexObj : shieldHexes) {
			var hex = hexMap.get(hexObj.yCord).get(hexObj.xCord);
			g2.setColor(Color.BLUE);
			g2.drawString("Sd", 
					(int) (hex.xpoints[0] - (hex.getBounds().width * 0.55)),
					(int) (hex.ypoints[0] + (hex.getBounds().height * 0.825)));
		}
	}
	
	public boolean shieldedHex(int x, int y) {
		
		for(var hex : shieldHexes) {
			if(hex.yCord == x && hex.xCord == y)
				return true;
		}
		
		return false; 
	}
	
	public void addShield(Hex hex, int strength) {
		if(strength == 0) {
			clearHex(hex);
			return;
		}
		
		hex.energyShields.add(new EnergyShield(strength));
		
		if(!shieldHexes.contains(hex))
			shieldHexes.add(hex);
	}
	
	public void clearHex(Hex hex) {
		hex.energyShields.clear();
		shieldHexes.remove(hex);
	}
	
	public void setShield(Hex hex, EnergyShield energyShield, int index) {
		hex.energyShields.set(index, energyShield);
	}
	
	public void removeShield(Hex hex, int index) {
		hex.energyShields.remove(index);
		
		if(hex.energyShields.size() <= 0)
			shieldHexes.remove(hex);
		
	}
	
}
