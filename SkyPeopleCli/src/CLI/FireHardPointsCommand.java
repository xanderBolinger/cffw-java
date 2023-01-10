package CLI;

import java.util.ArrayList;
import java.util.Arrays;

import CLI.Command.CommandType;
import Game.GameMaster;
import Mechanics.BeamAttack;
import Mechanics.DamageAllocation;
import Mechanics.DamageAllocation.HitSide;
import Ship.HardPoint;
import Ship.Ship;
import Ship.Weapon;

public class FireHardPointsCommand implements Command {

	String shooterName;
	String targetName;
	int range;
	int modifier;
	ArrayList<Weapon> weapons; 
	ArrayList<Integer> hardpointIndices;
	Ship target;
	Ship shooter;
	HitSide hitSide; 
	
	public FireHardPointsCommand(ArrayList<String> parameters) {
		
		if(parameters.size() < 7  ||
				!Command.verifyParameters(parameters, new ArrayList<Integer>(
					    Arrays.asList(3, 4)))) {
			System.out.println("Invalid Parameter Types");
			for(String param : parameters) {
				System.out.println("["+param+"], ");
			}
			return;
		}
		
		shooterName = parameters.get(1);
		targetName = parameters.get(2);
		range = Integer.parseInt(parameters.get(3));
		modifier = Integer.parseInt(parameters.get(4));
		hitSide = DamageAllocation.getHitSide(parameters.get(5));
		
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
		
		
		weapons = new ArrayList<>();
		
		hardpointIndices = new ArrayList<>();
		
		for(int i = 6; i < parameters.size(); i++) {
			hardpointIndices.add(Integer.parseInt(parameters.get(i)));
		}
		
		for(int index : hardpointIndices) {
			if(index >= shooter.hardPoints.size() || index < 0) {
				System.out.println("Invalid hardpoint index.");
				return; 
			}
			
			HardPoint hardPoint = shooter.hardPoints.get(index);
			for(Weapon weapon : hardPoint.weapons) {
				weapons.add(weapon);
			}
		}
		
		
		resolve();
	}
	
	@Override
	public void resolve() {
		
		try {
			for(Weapon weapon : weapons) {
				
				int shots = 1; 
				
				switch(weapon.fireType) {
				case TWIN:
					shots = 2; 
					break;
				case QUAD:
					shots = 4;
					break;
				}
				
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
