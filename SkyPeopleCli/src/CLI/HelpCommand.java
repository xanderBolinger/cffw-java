package CLI;


public class HelpCommand implements Command {

	@Override
	public void resolve() {
		
		CommandLineInterface.cli.print("Base CLI Commands: ");
		CommandLineInterface.cli.print("help - lists possible commands");
		CommandLineInterface.cli.print("exit - closes out of the cli");
		CommandLineInterface.cli.print("laser - [shooterName{String}, targetName{String}, hardPointIndex{zero index, int}, weaponIndex{zero index, int}, shots{int}, range{20km hexes, int}, modifier{int}, hitSide{String}] performs beam attack.");
		CommandLineInterface.cli.print("applylaser - [shooterName{String}, targetName{String}, weaponName{String, underscores for spaces}, shots{int}, range{20km hexes, int}, modifier{int}, hitSide{String}] performs beam attack.");
		CommandLineInterface.cli.print("applydamage - [targetName{String},damage{int}, hitSide{String}] performs beam attack.");
		CommandLineInterface.cli.print("fire - [shooterName{String}, targetName{String}, range{20km hexes, int}, modifier{int}, hitSide{String}, list hard point indexes{ints, seperated with spaces}] performs beam attack.");
		CommandLineInterface.cli.print("addship - [shipType{String} shipName{String}]");
		CommandLineInterface.cli.print("showship - [shipName{String}]");
		CommandLineInterface.cli.print("shields - [shipName{String} shieldRechargeRate{int} - number of power points generate per turn by the reactor that goes to recharging the shields]");
		CommandLineInterface.cli.print("game - [shipName{String}]");
		CommandLineInterface.cli.print("nextround - advances round, triggers generation step for all ships");
		CommandLineInterface.cli.print("undo - undoes last action");
		CommandLineInterface.cli.print("save - saves game as json file");
		CommandLineInterface.cli.print("load - loads game from json file");
		CommandLineInterface.cli.print("create - [formationName{String} shipNames{String, names seperated with spaces}...]");
		CommandLineInterface.cli.print("add - [formationName{String} shipName{String}]");
		CommandLineInterface.cli.print("remove - [formationName{String} shipName{String}]");
		CommandLineInterface.cli.print("clear - [formationName{String}]");
		CommandLineInterface.cli.print("recharge - [shipName{String}, setReactorRechargeRate{int}]");
		CommandLineInterface.cli.print("radiators - [shipName/formationName{String}] toggles radiators extended");
		CommandLineInterface.cli.print("next - advances game to next step");
		CommandLineInterface.cli.print("turns - prints out all turns and what happened");
		CommandLineInterface.cli.print("response - [shipName/formationName{String}, response{String}] adds response to current step, when all responses are added, advances step");
		
	}

	@Override
	public CommandType getType() {
		return CommandType.HELP;
	}

}
