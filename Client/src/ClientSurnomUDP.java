import java.io.*;
import java.net.*;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ClientSurnomUDP {
	
	private Scanner sc;
	private int port;
	private InetAddress address;
	private DatagramSocket socket = null;
	
	public ClientSurnomUDP(InetAddress address, int portNumber) {
		this.address = address;
		this.port = portNumber;
		sc = new Scanner(System.in);
		
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	
	public void traitement(){
		if(socket!=null) {
			accueil();
			String choix;
			String requete = "";
			JSONParser parser = new JSONParser();
			do{
				byte[] sendBuf = new byte[1000];
				System.out.print("\nVotre choix: ");
				choix = sc.next();
				requete = choisir(choix);
				
				sendBuf = requete.getBytes();
				
				DatagramPacket respPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
				
				
				try {
					socket.send(respPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			} while (!choix.equals("exit"));
		}
		
	}
	
	private String choisir(String choix) {
		switch(choix) {
		case "add" : return ProtocoleClient.add(sc); 
		case "delete" : return ProtocoleClient.delete(sc); 
		case "deleteAll" : return ProtocoleClient.deleteAll(sc);
		case "list" : return ProtocoleClient.list();
		case "search" : return ProtocoleClient.search(sc); 
		case "ask" : return ProtocoleClient.ask(sc); 
		default : return ProtocoleClient.list();
		}
	}
	
	private void accueil() {
		System.out.println("Choisissez un type de requete a effectuer ou 'exit' pour quitter \n");
		System.out.println("add -- Enregistrer un nom et un surnom");
		System.out.println("delete -- Supprime un surnom");
		System.out.println("deleteAll -- Supprime tout les surnoms d'un nom");
		System.out.println("list -- Lister tous les noms enregistres dans le serveur");
		System.out.println("search -- Recherche des surnoms associés a un nom");
		System.out.println("ask -- Demande d'un nom associé à un surnom");
	}
}
