package es.bewom.commands;

import es.bewom.BewomServer;
import es.bewom.util.CommandBase;

public class CommandExit extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "exit";
	}
	
	@Override
	public void execute(){
		BewomServer.serverOn = false;
		BewomServer.ct.interrupt();
	}
	
}
