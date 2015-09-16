package es.bewom.commands;

import java.util.ArrayList;
import java.util.List;

import es.bewom.util.Command;

public class Commands {
	
	private static List<Command> commands = new ArrayList<Command>();
	
	public static void registerCommand(Command c){
		commands.add(c);
	}
	
	public static List<Command> getCommands(){
		return commands;
	}

}
