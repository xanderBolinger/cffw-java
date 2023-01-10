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
	String shooterName;
	String weaponName;
	int shots; 
	int range;
	int modifier;
	Weapon weapon; 
	Ship shooter;
	Ship target;
	HitSide hitSide; 
	
	public ApplyLaserCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 8) ||
				!Command.verifyParameters(parameters, new ArrayList<Integer>(
					    Arrays.asList(4,5,6)))) {
			System.out.println("Invalid Parameter Types");
			return;
		}
		
		shooterName = parameters.get(1);
		targetName = parameters.get(2);
		weaponName = parameters.get(3);
		shots = Integer.parseInt(parameters.get(4));
		range = Integer.parseInt(parameters.get(5));
		modifier = Integer.parseInt(parameters.get(6));
		hitSide = DamageAllocation.getHitSide(parameters.get(7));
		
		target = GameMaster.game.findShip(targetName);
		shooter = GameMaster.game.findShip(shooterName);
		if(target == null) {
			System.out.println("Target Not Found");
			return; 
		} else if(shooter == null) {
			System.out.println("Shooter Not Found");
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
						shooter.getEccm(), hitSide);
				shooter.power -= weapon.powerCost;
				if(shooter.power < 0) {
					System.out.println("Ship out of power.");
					shooter.power = 0;
					return;
				}
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
