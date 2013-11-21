import java.io.*;
import java.net.*;

public class ServeurSurnom {
	private ProtocoleSurnom protocoleSurnom;
	
	ServeurSurnom() {
		protocoleSurnom = new ProtocoleSurnom();
	}
		
	public void run() {
		try (
				ServerSocket surnomService = new ServerSocket(3838);
				Socket clientSocket = surnomService.accept();
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

