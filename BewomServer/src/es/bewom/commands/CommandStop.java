package es.bewom.commands;

import es.bewom.Main;
import es.bewom.Server;
import es.bewom.util.CommandBase;
import es.bewom.util.Datetime;

public class CommandStop extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "stop";
	}
	
	@Override
	public void execute(){
		if(Main.isStart){
			Main.isStart = false;
			Server.stop();
			System.out.println(Datetime.get() + "Secuencia de servidor parada.");
		} else {
			System.out.println(Datetime.get() + "La secuencia de servidor ya esta parada.");
		}
	}
	
}
