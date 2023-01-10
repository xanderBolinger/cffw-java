package CLI;

import java.util.ArrayList;
import java.util.Arrays;

import Game.Game;
import Game.GameMaster;
import Mechanics.BeamAttack;
import Mechanics.DamageAllocation;
import Mechanics.DamageAllocation.HitSide;
import Ship.Ship;
import Ship.Weapon;


public class BeamAttackCommand implements Command {

	String shooterName;
	String targetName;
	int hardPointIndex; 
	int weaponIndex; 
	int shots; 
	int range;
	int modifier;
	Weapon weapon; 
	Ship target;
	Ship shooter;
	HitSide hitSide; 
	
	public BeamAttackCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 9) ||
				!Command.verifyParameters(parameters, new ArrayList<Integer>(
					    Arrays.asList(3, 4, 5, 6, 7)))) {
			System.out.println("Invalid Parameter Types");
			return;
		}
		
		shooterName = parameters.get(1);
		targetName = parameters.get(2);
		hardPointIndex = Integer.parseInt(parameters.get(3));
		weaponIndex = Integer.parseInt(parameters.get(4));
		shots = Integer.parseInt(parameters.get(5));
		range = Integer.parseInt(parameters.get(6));
		modifier = Integer.parseInt(parameters.get(7));
		hitSide = DamageAllocation.getHitSide(parameters.get(8));
		
		target = GameMaster.game.findShip(targetName);
		shooter = GameMaster.game.findShip(shooterName);
		
		if(target == null) {
			System.out.println("Target Not Found");
			return; 
		} else if(shooter == null) {
			System.out.println("Shooter Not Found");
			return;
		} else if(hardPointIndex >= shooter.hardPoints.size()) {
			System.out.println("Hard Point Index Out of Range");
			return;
		} else if(weaponIndex >= shooter.hardPoints.get(hardPointIndex).weapons.size()) {
			System.out.println("Weapon Index Out of Range");
			return;
		} else if(hitSide == null) {
			System.out.println("Hit Side Not Found");
			return;
		}
		
		weapon = shooter.hardPoints.get(hardPointIndex).weapons.get(weaponIndex);
		
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