package es.bewom.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class CommandBase implements Command {
	
	@Override
	public String getCommandName() {
		return new BigInteger(30, new SecureRandom()).toString(32);
	}
	
	@Override
	public void execute(){
		System.out.println("Comando de prueba.");
	}
	
}