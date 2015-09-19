package es.bewom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Scanner;

import es.bewom.commands.CommandStart;
import es.bewom.commands.CommandStop;
import es.bewom.commands.CommandExit;
import es.bewom.commands.Commands;
import es.bewom.util.Datetime;
import es.bewom.util.mysql.MySQL;

public class Main {
	
	public static final boolean DEBUG_MODE = false;
	public static int DEBUG_MODE_IS_SERVER_ON = 0;
	
	public static final String WEB_PING = "http://bewom.es/test/ping";
	public static MySQL m = new MySQL();
	
	public static String nextLine = "";
	public static String lastNextLine = "";
	public static Scanner input = new Scanner(System.in);
	
	public static volatile boolean serverOn = true;
	public static CommandThread ct = new CommandThread();
	
	public static long date = new Date().getTime()/1000;
	
	public static int ping = 90;
	
	public static void main(String[] args) {
		
		Commands.registerCommand(new CommandExit());
		Commands.registerCommand(new CommandStart());
		Commands.registerCommand(new CommandStop());
		
		ct.start();
		System.out.println(Datetime.get() + "Bienvenido!");
		Server.stop();
		while(serverOn){
			long neoDate = new Date().getTime()/1000;
				
			if(!serverOn){
				break;
			}
			if(neoDate != date){
				run();
				updateUsage();
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
					}
					Server.start();
				}
				long i = Server.lastStart - (neoDate - ping);
				System.out.println(Datetime.get() + "Secuencia de iniciado... (" + i + ")");
			}
		}
	}
	
	private static int everyMinute = 60;
	private static void updateUsage() {
		if(everyMinute == 60){
			try {
				String[] runRam = {"sh", "ram.sh"};
				Process pRam = Runtime.getRuntime().exec(runRam);
				BufferedReader stdInputRam = new BufferedReader(new InputStreamReader(pRam.getInputStream()));
				
				String[] runCpu = {"sh", "cpu.sh"};
				Process pCpu = Runtime.getRuntime().exec(runCpu);
				BufferedReader stdInputCpu = new BufferedReader(new InputStreamReader(pCpu.getInputStream()));
				
				m.executeQuery("INSERT INTO `serverUsage`(`cpuUsage`, `ramUsage`) VALUES ('" + stdInputCpu.readLine().replace(".", ",") + "', '" + stdInputRam.readLine().replace(".", ",") + "')", null);
				
				stdInputCpu.close();
				stdInputRam.close();
				
				pCpu.destroy();
				pRam.destroy();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			everyMinute = 0;
		}
		everyMinute++;
		
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
			if(responseFromWeb != null){
				if(!responseFromWeb.isEmpty()){
					on = Integer.parseInt(responseFromWeb);
				}
			}
		}
		
		if(on != 0){
			onAir = true;
		}
		
		return onAir;
		
	}
	
}