import java.io.*;
import java.net.*;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ClientSurnom {
	
	private Socket socketSurnom;
	private BufferedWriter out;
	private BufferedReader in;
	private Scanner sc;
	
	public ClientSurnom(String hostName, int portNumber) {
		try {
				socketSurnom = new Socket(hostName, portNumber);
				out = new BufferedWriter(new OutputStreamWriter(socketSurnom.getOutputStream()));
				in = new BufferedReader(new InputStreamReader(socketSurnom.getInputStream()));
				sc = new Scanner(System.in);
				System.out.println("connexion etablie\n");
		} catch (UnknownHostException e) {
			System.err.println("Unable to connect to host " + hostName);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void traitement(){
		System.out.println("Choisissez un type de requete a effectuer ou 'exit' pour quitter \n");
		System.out.println("add -- Enregistrer un nom et un surnom");
		System.out.println("delete -- Supprime un surnom");
		System.out.println("deleteAll -- Supprime tout les surnoms d'un nom");
		System.out.println("list -- Lister tous les noms enregistres dans le serveur");
		System.out.println("search -- Recherche des surnoms associés a un nom");
		System.out.println("ask -- Demande d'un nom associé à un surnom");
		String choix;
		String requete = "";
		JSONParser parser = new JSONParser();
		do{
			System.out.print("\nVotre choix: ");
			choix = sc.next();
			switch(choix) {
			case "add" : requete = ProtocoleClient.add(sc); break;
			case "delete" : requete = ProtocoleClient.delete(sc); break;
			case "deleteAll" : requete = ProtocoleClient.deleteAll(sc); break;
			case "list" : requete = ProtocoleClient.list(); break;
			case "search" : requete = ProtocoleClient.search(sc); break;
			case "ask" : requete = ProtocoleClient.ask(sc); break;
			}
			try {
				out.write(requete);
				out.flush();
				try {
					ProtocoleClient.transforme((JSONObject) parser.parse(in.readLine()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!choix.equals("exit"));
		
		}
	
	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println(
					"Usage: java clienSurnom <host name> <port number>");
			System.exit(1);
		}
		
		String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        ClientSurnom client = new ClientSurnom(hostName,portNumber);
        client.traitement();
        //ip 172.19.250.196
	}
}
