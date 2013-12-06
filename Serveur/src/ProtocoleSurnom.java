import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * Cette classe lira les requetes en forme de chaine
 * respectant le format défini dans le protocole du 
 * serveur de surnom.
 * 
 * Une fois la requete comprise, la BDSurnom sera consultée
 * pour générer une réponse.
 */
public class ProtocoleSurnom {
	private BDSurnom bdSurnom;

	ProtocoleSurnom() {
		bdSurnom = new BDSurnom();
		bdSurnom.initMapSurnom("surnoms.xml");
	}

	//TODO: everytime we do a "oRequete.get()" we have to check if it's null
	public String genererReponse(String requete) {
		String response = null;

		JSONParser parser = new JSONParser();
		try {
			JSONObject oRequete = (JSONObject) parser.parse(requete);
			String action = "none";

			if(oRequete.get("action") != null) {
				action = oRequete.get("action").toString();
			}

			switch(action) {
			case "add" : 
				response = add(oRequete.get("nick").toString(), oRequete.get("name").toString()).toJSONString();
				break;
			case "delete" : 
				response = delete(oRequete.get("nick").toString()).toJSONString();
				break;
			case "delete_name" : 
				response = deleteName(oRequete.get("name").toString()).toJSONString();
				break;
			case "list" : 
				response = list().toJSONString();
				break;
			case "search" : 
				System.out.println(oRequete.get("name"));
				response = search(oRequete.get("name").toString()).toJSONString();
				break;
			case "ask" : 
				response = ask(oRequete.get("nick").toString()).toJSONString();
				break;
			case "quit" : 
				response = quit().toJSONString(); 
				break;
			default: 
				response = error(11, "Champ action manquant ou invalide").toJSONString();
			}


		} catch (ParseException pe) {
			System.out.println("JSON Parse error at position: " + pe.getPosition());
			System.out.println(pe);
			response = error(10, "Mauvais format (Pas du JSON)").toJSONString();
		}

		return response + "\n";
	}

	private JSONObject list() {
		JSONObject oResponse = new JSONObject();
		JSONArray list = new JSONArray();

		for(Entry<String, String> entry : bdSurnom.listeSurnomEtNom()) {
			JSONObject pair = new JSONObject();
			pair.put("nick", entry.getKey());
			pair.put("name", entry.getValue());
			list.add(pair);
			System.out.println("nick:" + entry.getKey() + "\tname:" + entry.getValue());
		}

		oResponse.put("type", "list_ack");
		oResponse.put("nicks", list);

		return oResponse;
	}

	private JSONObject add(String surnom, String nom) {
		bdSurnom.ajouterSurnomEtNom(surnom, nom);	

		JSONObject oResponse = new JSONObject();
		oResponse.put("type", "add_ack");
		oResponse.put("nick", surnom);
		oResponse.put("name", nom);
		return oResponse;
	}

	private JSONObject error(int numError, String description) {
		JSONObject oResponse = new JSONObject();

		oResponse.put("type", "error");
		oResponse.put("code", Integer.toString(numError));
		oResponse.put("desc", description);

		return oResponse;
	}
	
	private JSONObject delete(String surnom) {
		//TODO: error check: trying to delete non-existant nick
		String nom = bdSurnom.rechercheSurnom(surnom);
		bdSurnom.supprimeSurnom(surnom);
		
		JSONObject oResponse = new JSONObject();
		
		oResponse.put("type", "delete_ack");
		oResponse.put("nick", surnom);
		oResponse.put("name", nom);
		
		return oResponse;
	}
	
	private JSONObject deleteName(String nom) {
		JSONArray list = new JSONArray();
		
		for (String surnom : bdSurnom.rechercheNom(nom)) {
			JSONObject nick = new JSONObject();
			nick.put("nick", surnom);
			list.add(nick);
		}
		
		bdSurnom.supprimeNom(nom);
		
		JSONObject oResponse = new JSONObject();
		
		oResponse.put("type", "delete_name_ack");
		oResponse.put("nicks", list);

		return oResponse;
	}
	
	private JSONObject search(String nom) {
		JSONArray list = new JSONArray();
		
		for (String surnom : bdSurnom.rechercheNom(nom)) {
			JSONObject nick = new JSONObject();
			nick.put("nick", surnom);
			list.add(nick);
		}
		
		JSONObject oResponse = new JSONObject();
		
		oResponse.put("type", "search_ack");
		oResponse.put("nicks", list);

		return oResponse;
	}
	
	private JSONObject ask(String surnom) {
		
		JSONObject oResponse = new JSONObject();
		
		oResponse.put("type", "ask_ack");
		oResponse.put("name", bdSurnom.rechercheSurnom(surnom));

		return oResponse;
	}
	
	private JSONObject quit() {
		JSONObject oResponse = new JSONObject();
			oResponse.put("type", "quit_ack");
		return oResponse;
		
	}
}
