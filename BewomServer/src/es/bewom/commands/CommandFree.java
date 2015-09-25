package es.bewom.commands;

import es.bewom.Server;
import es.bewom.util.CommandBase;

public class CommandFree extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "free";
	}
	
	@Override
	public void execute(){
		Server.freeRam();
	}
	
}
