package es.bewom;

import java.io.IOException;
import java.util.Date;

import es.bewom.util.Datetime;

public class Server {
	
	public static Process start;
	public static long lastStart = 0;
	
	public static void start(){
		lastStart = new Date().getTime()/1000;
		try {
			// String[] run = {"xterm", "-e", "java", "-jar", "server.jar", "nogui"}; 
//			String[] run = {"cmd", "/c", "start", "cmd.exe"}; 
			String[] run = {"xterm", "-e", "java", "-jar", "server.jar", "nogui"}; 
			start = Runtime.getRuntime().exec(run);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(Datetime.get() + "Servidor arrancado.");
	}
	public static void stop(){
		start.destroy();
		System.out.println(Datetime.get() + "Servidor parado.");
	}
	public static boolean isAlive(){
		return start.isAlive();
	}

}
