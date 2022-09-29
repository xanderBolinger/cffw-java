package SaveCompanies;

import java.io.Serializable;
import java.util.ArrayList;

import Company.Company;
import Conflict.Game;

public class GameSave implements Serializable {
	public String gameName;
	public ArrayList<Company> companies;
	public Game game; 
	public InitOrder initOrder;
	public Hexes hexes; 
	public int activeUnit;
	
	public GameSave(String gameName, ArrayList<Company> companies, Game game, Hexes hexes, InitOrder initOrder, int activeUnit) {
		this.gameName = gameName; 
		this.companies = companies; 
		this.game = game; 
		this.initOrder = initOrder; 
		this.hexes = hexes; 
		this.activeUnit = activeUnit; 
	}
	
}
