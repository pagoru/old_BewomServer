package es.bewom;

import java.util.Date;
import java.util.Scanner;

import es.bewom.commands.CommandStart;
import es.bewom.commands.CommandStop;
import es.bewom.commands.CommandExit;
import es.bewom.commands.Commands;
import es.bewom.util.Datetime;

public class BewomServer {
	
	public static final boolean DEBUG_MODE = false;
	public static int DEBUG_MODE_IS_SERVER_ON = 0;
	
	public static final String WEB_PING = "http://bewom.es/test/ping";
	
	public static String nextLine = "";
	public static String lastNextLine = "";
	public static Scanner input = new Scanner(System.in);
	
	public static volatile boolean serverOn = true;
	public static CommandThread ct = new CommandThread();
	
	public static long date = new Date().getTime()/1000;
	
	public static int ping = 60;
	
	public static void main(String[] args) {
		
		Commands.registerCommand(new CommandExit());
		Commands.registerCommand(new CommandStart());
		Commands.registerCommand(new CommandStop());
		
		ct.start();
		System.out.println(Datetime.get() + "Bienvenido!");
		while(serverOn){
			long neoDate = new Date().getTime()/1000;
				
			if(!serverOn){
				break;
			}
			if(neoDate != date){
				run();
				date = neoDate;
			}
		}
	}
	
	public static void run(){
		if(canStart()){
			if(!isServerOn()){
				long neoDate = new Date().getTime()/1000;
				if((neoDate - ping) >= Server.lastStart){
					if(0 != Server.lastStart){
						System.out.println(Datetime.get() + "¿Servidor caído?");
						Server.stop();
					}
					Server.start();
				}
				long i = Server.lastStart - (neoDate - ping);
				System.out.println(Datetime.get() + "Secuencia de iniciado... (" + i + ")");
			}
		}
	}
	
	public static boolean isStart = false;
	
	private static boolean canStart() {
		return isStart;
	}

	public static boolean isServerOn(){
		
		boolean onAir = false;
		int on = DEBUG_MODE_IS_SERVER_ON;
		if(!DEBUG_MODE){
			String responseFromWeb = URLConnectionReader.getText(WEB_PING);
			if(!responseFromWeb.isEmpty()){
				on = Integer.parseInt(responseFromWeb);
			}
		}
		
		if(on != 0){
			onAir = true;
		}
		
		return onAir;
		
	}
	
}
