package es.bewom.commands;

import es.bewom.BewomServer;
import es.bewom.util.CommandBase;
import es.bewom.util.Datetime;

public class CommandStart extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "start";
	}
	
	@Override
	public void execute(){
		if(!BewomServer.isStart){
			BewomServer.isStart = true;
			System.out.println(Datetime.get() + "Secuencia de servidor arrancada.");
		} else {
			System.out.println(Datetime.get() + "La secuencia de servidor ya esta arrancada.");
		}
	}
	
}
