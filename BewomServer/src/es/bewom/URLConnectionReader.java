package es.bewom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionReader {
	public static String getText(String url) {
		try {
			URL website = new URL(url);
			URLConnection connection = website.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                    connection.getInputStream()));
	
	        StringBuilder response = new StringBuilder();
	        String inputLine;
	
	        while ((inputLine = in.readLine()) != null) 
	            response.append(inputLine);
	
	        in.close();

	        return response.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
    }
}