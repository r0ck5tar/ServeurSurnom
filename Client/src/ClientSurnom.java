import java.io.*;
import java.net.*;


public class ClientSurnom {

	public static void main(String[] args) {
		try(
			Socket socketSurnom = new Socket("192.168.0.12", 3838);
			PrintWriter out = new PrintWriter(socketSurnom.getOutputStream(), true);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(socketSurnom.getInputStream()));) {
			
		} catch (UnknownHostException e) {
			System.err.println("Unable to connect to host 192.168.0.12");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
