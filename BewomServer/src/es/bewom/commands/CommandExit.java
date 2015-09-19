package es.bewom.commands;

import es.bewom.Main;
import es.bewom.util.CommandBase;

public class CommandExit extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "exit";
	}
	
	@Override
	public void execute(){
		Main.serverOn = false;
		Main.ct.interrupt();
	}
	
}
