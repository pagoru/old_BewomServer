package es.bewom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		Commands.registerCommand(new CommandExit());
		Commands.registerCommand(new CommandStart());
		Commands.registerCommand(new CommandStop());
		
		ct.start();
		System.out.println(Datetime.get() + "Bienvenido!");
		Server.stop();
		while(serverOn){
				
			if(!serverOn){
				break;
			}
			run();
			updateUsage();
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
		}
	}
	
	public static List<String> pidofMinecraft() {
		List<String> ls = new ArrayList<String>();
		try {
			String[] run = {"sh", "screenlist_minecraft.sh"};
			Process p = Runtime.getRuntime().exec(run);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			while(true){
				final String line = stdInput.readLine();
				if(line == null){
					break;
				}
				ls.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ls;		
	}
	
	public static String pidofServer() {
		try {
			String[] run = {"sh", "screenlist_minecraft.sh"};
			Process p = Runtime.getRuntime().exec(run);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			return stdInput.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}

	public static void run(){
		if(canStart()){
			if(!isServerOn()){
				long neoDate = new Date().getTime()/1000;
				if((neoDate - ping) >= Server.lastStart){
					if(0 != Server.lastStart){
						System.out.println(Datetime.get() + "¿Servidor caído?");
					}
					Server.stop();
					Server.start();
				}
				long i = Server.lastStart - (neoDate - ping);
				System.out.println(Datetime.get() + "Secuencia de iniciado... (" + i + ")");
			}
		}
	}
	
	private static int everyMinute = 60;
	private static int everyDay = 86400;	
	
	private static void updateUsage() {
		
		if(everyDay == 86400){
			
			Date date = new Date();
			Timestamp d = new Timestamp(date.getTime());

			try {
				System.out.println(Datetime.get() + "Backup " + d.toString());
				String[] runRam = {"cp", "-R", "/home/server", "/home/backup/" + d.toString()};
				Process pRam = Runtime.getRuntime().exec(runRam);
				pRam.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			everyDay = 0;
		}
		everyDay++;
		
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