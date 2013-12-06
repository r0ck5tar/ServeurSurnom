import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;



public class LanceurServeurSurnom {
	
	private static int threads = 0;

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println(
					"Usage: java serveurSurnom <port number> [<transport protocol>]"
				   +"where <transport protocol> can either be 'UDP' or 'TCP' and is optional");
			System.exit(1);
		}
		
		boolean listening = true;
		int portNumber = Integer.parseInt(args[0]);
		String transportProtocol = "TCP";
		
		if (args.length==2) {
			transportProtocol = args[1];
		}
		
		System.out.println("Serveur démarré. Attente d'un client");
		
		
			if(transportProtocol.equals("UDP")) {
				System.out.println("transport protocol: UDP");
				int maxThreads = 10;
				
				try (DatagramSocket socket = new DatagramSocket(portNumber);){
					while(listening) {
						if (threads < maxThreads) {
							UDPServeurSurnomThread serveurThread = new UDPServeurSurnomThread(socket);
							serveurThread.start();
			
							threads++;
						}
					
					}
				} catch (IOException e) {
				System.err.println("Could not listen on port " + portNumber);
				}
			}
			
			else {
				System.out.println("transport protocol: TCP");
				try (ServerSocket surnomService = new ServerSocket(portNumber);){
					while(listening) {
						new ServeurSurnomThread(surnomService.accept()).start();
					
					}
				} catch (IOException e) {
				System.err.println("Could not listen on port " + portNumber);
				}
			}
	}
	
	public static void decNumThreads() {
	    threads--;
	}
}
