import java.io.*;
import java.net.*;

public class ServeurSurnomThread extends Thread {
	private ProtocoleSurnom protocoleSurnom;
	private Socket clientSocket = null;
	private boolean listening = true;
	
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
            while ((listening && (inputLine = in.readLine())!= null)) {
            	String reponse = protocoleSurnom.genererReponse(inputLine);
                out.write(reponse);
                out.flush();
                
                if(reponse.contains("quit")) {
                	clientSocket.close();
                	listening = false;
                }
            }
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isListening() {
		return listening;
	}
}

