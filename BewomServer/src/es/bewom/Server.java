package es.bewom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import es.bewom.util.Datetime;

public class Server {
	
	public static long lastStart = 0;
	
	private static final String screen = "minecraft";
	private static final String port = "25565";
	
	public static void start(){
		lastStart = new Date().getTime()/1000;
		try {
			String[] run = {"sudo", "screen", "-dmS", screen, "java", "-jar", "server.jar"};
			Process p = Runtime.getRuntime().exec(run);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			if(stdInput != null || stdError != null){
				System.out.println("[ERROR] " + stdInput.readLine() + " - " + stdError.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(Datetime.get() + "Servidor arrancado.");
	}
	public static void stop(){
		try {
			String[] run = {"sh", "killport", port};
			Runtime.getRuntime().exec(run);
			String[] run2 = {"screen", "-X", "-S", screen, "kill"};
			Runtime.getRuntime().exec(run2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(Datetime.get() + "Servidor parado.");
	}

}
