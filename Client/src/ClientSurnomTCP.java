import java.io.*;
import java.net.*;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ClientSurnomTCP {
	
	private Socket socketSurnom;
	private BufferedWriter out;
	private BufferedReader in;
	private Scanner sc;
	
	public ClientSurnomTCP(String hostName, int portNumber) {
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
		accueil();
		String choix;
		String requete = "";
		JSONParser parser = new JSONParser();
		do{
			System.out.print("\nVotre choix: ");
			choix = sc.next();
			requete = choisir(choix);
			
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
