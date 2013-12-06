import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class UDPServeurSurnomThread extends Thread{
	private ProtocoleSurnom protocoleSurnom;
	private DatagramSocket clientSocket = null;
	private boolean listening = true;
	
	UDPServeurSurnomThread(DatagramSocket clientSocket) {
		protocoleSurnom = new ProtocoleSurnom();
		this.clientSocket = clientSocket;
	}
		
	public void run() {
		System.out.println("client connected");
		
	    byte[] req = new byte[1000];
	    byte[] resp = new byte[1000];

	    try {
	        // Get client request
	        DatagramPacket request = new DatagramPacket(req, req.length);
	        clientSocket.receive(request);

	        req = request.getData();
	        
	        // Retrieve definition from dictionary file
	        String reqString = new String(req);
	        String respString = protocoleSurnom.genererReponse(reqString);
	        resp = respString.getBytes();
            
            if(respString.contains("quit")) {
            	clientSocket.close();
            	listening = false;
            	LanceurServeurSurnom.decNumThreads();
            }

	        // Put reply into packet, send packet to client
	        DatagramPacket reply = new DatagramPacket(resp, resp.length, request.getAddress(), request.getPort());
	        clientSocket.send(reply);

	    }
	    catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}
	
	public boolean isListening() {
		return listening;
	}
}
