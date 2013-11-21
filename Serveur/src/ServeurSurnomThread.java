import java.io.*;
import java.net.*;

public class ServeurSurnomThread extends Thread {
	private ProtocoleSurnom protocoleSurnom;
	private Socket clientSocket = null;
	
	ServeurSurnomThread(Socket clientSocket) {
		protocoleSurnom = new ProtocoleSurnom();
		this.clientSocket = clientSocket;
	}
		
	public void run() {
		try (
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));){
	        
	        String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(protocoleSurnom.genererReponse(inputLine));
            }
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

