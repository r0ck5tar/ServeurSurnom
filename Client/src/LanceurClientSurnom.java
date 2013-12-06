import java.net.InetAddress;
import java.net.UnknownHostException;


public class LanceurClientSurnom {
	public static void main(String[] args) {

		if (args.length < 2) {
			System.err.println(
					"Usage: java clientSurnom <host name> <port number> [<transport protocol>]"
				   +"where <transport protocol> can either be 'UDP' or 'TCP' and is optional");
			System.exit(1);
		}
		
		
		String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String transportProtocol = "TCP";
        
        if(args.length == 3) {
        	transportProtocol = args[2];
        }
        
        if(transportProtocol.equals("UDP")){
        	InetAddress address = null;
			try {
				address = InetAddress.getByName(hostName);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	ClientSurnomUDP client = new ClientSurnomUDP(address, portNumber);
        	client.traitement();
        }
        
        else {
            ClientSurnomTCP client = new ClientSurnomTCP(hostName,portNumber);
            client.traitement();
        }

        //ip 172.19.250.196
	}
}
