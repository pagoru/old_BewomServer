package es.bewom;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import es.bewom.util.AppZip;
import es.bewom.util.Datetime;

public class Server {
	
	public static long lastStart = 0;
	
	private static final String screen = "minecraft";
		
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
				String[] run = {"sudo", "kill", "-SIGTERM", pid.get(i)};
				Runtime.getRuntime().exec(run);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(Datetime.get() + "Proceso de java terminado: " + pid.get(i));
		}
		System.out.println(Datetime.get() + "Servidor parado.");
	}
	
	public static void backUp(){
		Date date = new Date();
		Timestamp d = new Timestamp(date.getTime());
		System.out.println("Backup " + d.toString().replace(" ", "_") + " . . .");
		
		AppZip appZip = new AppZip();
    	appZip.generateFileList(new File("/home/server"));
    	appZip.zipIt("/home/backup/" + d.toString().replace(" ", "_") + ".zip");
    	appZip = null;
    	
		System.out.println("Backup " + d.toString().replace(" ", "_") + " completado!");
		
		Main.everyDay = 0;
	}

}
