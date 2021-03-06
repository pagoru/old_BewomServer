package es.bewom;

import static es.bewom.Main.*;

import es.bewom.commands.Commands;
import es.bewom.util.Command;
import es.bewom.util.Datetime;

public class CommandThread extends Thread {

	@Override
	public void run() {
		
		while((nextLine = input.nextLine()) != null && serverOn){
			if(!lastNextLine.equals(nextLine)){
				for(Command c : Commands.getCommands()){
					if(c.getCommandName().equals(nextLine)){
						c.execute();
					}
				}
				if(!serverOn){
					System.out.println(Datetime.get() + "Control de servidor parado.");
					break;
				}
				lastNextLine = nextLine;
			}
			try {
				CommandThread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
