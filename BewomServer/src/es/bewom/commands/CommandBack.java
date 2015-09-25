package es.bewom.commands;

import es.bewom.Server;
import es.bewom.util.CommandBase;

public class CommandBack extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "back";
	}
	
	@Override
	public void execute(){
		Server.backUp();
	}
	
}
