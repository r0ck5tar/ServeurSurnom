import java.io.*;
import java.net.*;

public class ServeurSurnom {
	private BDSurnom bdSurnom;
	private ServerSocket surnomService;
	private Socket	clientSocket;
	private ProtocoleSurnom protocoleSurnom;
	
	ServeurSurnom() {
		bdSurnom = new BDSurnom();
		bdSurnom.initMapSurnom("surnoms.xml");
		try {
			surnomService = new ServerSocket(3838);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void run() {
		try {
			clientSocket = surnomService.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

