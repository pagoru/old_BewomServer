package es.bewom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

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
		List<String> pid = Main.pidofMinecraft();
		for (int i = 0; i < pid.size(); i++) {
			try {
				String[] run = {"kill", "-9", pid.get(i)};
				Runtime.getRuntime().exec(run);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(Datetime.get() + "Proceso de java terminado: " + pid.get(i));
		}
		System.out.println(Datetime.get() + "Servidor parado.");
	}

}
