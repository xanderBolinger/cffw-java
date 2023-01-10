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

public class ApplyDamageCommand implements Command {

	String targetName;
	int damage;
	HitSide hitSide; 
	Ship target;
	
	public ApplyDamageCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 4) ||
				!Command.verifyParameters(parameters, new ArrayList<Integer>(
					    Arrays.asList(2)))) {
			System.out.println("Invalid Parameter Types");
			return;
		}
		
		targetName = parameters.get(1);
		damage = Integer.parseInt(parameters.get(2));
		hitSide = DamageAllocation.getHitSide(parameters.get(3));
		
		target = GameMaster.game.findShip(targetName);
		
		if(target == null) {
			System.out.println("Target Not Found");
			return; 
		} else if(hitSide == null) {
			System.out.println("Hit Side Not Found");
			return;
		}
		
		
		resolve();
	}
	
	@Override
	public void resolve() {
		
		try {
			DamageAllocation.allocateDamage(damage, target, hitSide);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GameMaster.move();
		
	}

	@Override
	public CommandType getType() {
		return CommandType.APPLY_DAMAGE;
	}

}
