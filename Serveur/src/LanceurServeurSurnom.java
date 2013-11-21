import java.io.IOException;
import java.net.ServerSocket;



public class LanceurServeurSurnom {

	public static void main(String[] args) {
		boolean listening = true;
		int portNumber = 3838;
		
		try (ServerSocket surnomService = new ServerSocket(portNumber);){
			while(listening) {
				new ServeurSurnomThread(surnomService.accept()).start();
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
		}
	}

}
