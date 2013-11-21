import java.io.*;
import java.net.*;


public class ClientSurnom {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println(
					"Usage: java clienSurnom <host name> <port number>");
			System.exit(1);
		}
		
		String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

		try (
				Socket socketSurnom = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(socketSurnom.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socketSurnom.getInputStream()));
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))){

			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				System.out.println("echo: " + in.readLine());
			}
		} catch (UnknownHostException e) {
			System.err.println("Unable to connect to host 192.168.0.12");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
