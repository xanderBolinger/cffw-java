package CLI;

import java.util.ArrayList;
import java.util.Arrays;

import CLI.Command.CommandType;
import Game.GameMaster;
import Mechanics.BeamAttack;
import Mechanics.DamageAllocation;
import Mechanics.DamageAllocation.HitSide;
import Ship.Ship;
import Ship.Weapon;

public class ApplyLaserCommand implements Command {

	String targetName;
	String weaponName;
	int shots; 
	int range;
	int modifier;
	int eccm;
	Weapon weapon; 
	Ship target;
	HitSide hitSide; 
	
	public ApplyLaserCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 8) ||
				!Command.verifyParameters(parameters, new ArrayList<Integer>(
					    Arrays.asList(3,4,5,6)))) {
			System.out.println("Invalid Parameter Types");
			return;
		}
		
		targetName = parameters.get(1);
		weaponName = parameters.get(2);
		shots = Integer.parseInt(parameters.get(3));
		range = Integer.parseInt(parameters.get(4));
		eccm = Integer.parseInt(parameters.get(5));
		modifier = Integer.parseInt(parameters.get(6));
		hitSide = DamageAllocation.getHitSide(parameters.get(7));
		
		target = GameMaster.game.findShip(targetName);
		
		if(target == null) {
			System.out.println("Target Not Found");
			return; 
		} else if(hitSide == null) {
			System.out.println("Hit Side Not Found");
			return;
		}
		
		weapon = new Weapon(weaponName);
		
		if(weapon.weaponType == null) {
			System.out.println("Weapon not found.");
			return;
		}
		
		resolve();
	}
	
	@Override
	public void resolve() {
		
		try {
			
			for(int i = 0; i < shots; i++) {
				BeamAttack.beamAttack(target, weapon, range, target.getEcm(), 
						eccm, hitSide);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GameMaster.move();
		
	}

	@Override
	public CommandType getType() {
		return CommandType.BEAM_ATTACK;
	}

}
