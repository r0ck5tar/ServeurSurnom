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
		System.out.println("client connected");
		try (
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));){
	        
			String inputLine;
            while ((inputLine = in.readLine())!= null) {
                out.write(protocoleSurnom.genererReponse(inputLine));
                out.flush();
            }
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

