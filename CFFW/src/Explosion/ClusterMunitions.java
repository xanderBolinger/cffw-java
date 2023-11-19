package Explosion;

import java.util.ArrayList;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.HexGridShadeHexes;
import Items.PCAmmo;
import UtilityClasses.DiceRoller;

public class ClusterMunitions {

	public static void detonateClusterMunition(PCAmmo pcAmmo, int x, int y, Explosion explosion) {
		System.out.println("Detonate cluster munition");
		var hexes = getHexesAroundCenter(x,y,pcAmmo.clusterRadiusHex);

		for(var hex : hexes) {
			var units = GameWindow.gameWindow.getUnitsInHex("", hex.xCord, hex.yCord);
			for(var u : units) {
				for(var t : u.individuals)
					for(int i = 0; i < pcAmmo.submunitionCountPerHex; i++)
						explosion.explodeTrooper(t, DiceRoller.roll(0, 10));
				
				u.suppression += pcAmmo.submunitionCountPerHex * 2;
				u.organization -= pcAmmo.submunitionCountPerHex * 2;
				if(u.suppression > 100)
					u.suppression = 100;
				if(u.organization < 0)
					u.organization = 0;
				
			}
			
			HexGridShadeHexes.shadedHexes.add(hex);
			
		}
		
		
		
	}
	
	
	private static ArrayList<Cord> getHexesAroundCenter(int startX, int startY, int radius) {
		var hexes = new ArrayList<Cord>();

		int lowerX = startX-radius;
		int upperY = startY+radius;
		
		for(int x = lowerX; x <= upperY; x++) {
			
			for(int y = lowerX; y <= upperY; y++) {
				
				if(GameWindow.dist(x, y, startX, startY) <= radius) {
					hexes.add(new Cord(x,y));
				}
				
			}
			
		}
		
		
		return hexes;
	}
	
}
