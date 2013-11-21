import java.util.Set;
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
				case "delete" : break;
				case "delete_name" : break;
				case "list" : 
					response = list().toJSONString();
					break;
				case "search" : break;
				case "ask" : break;
				case "quit" : break;
				default: 
					response = error(11, "Champ action manquant ou invalide").toJSONString();
				}
			
			
		} catch (ParseException pe) {
	         System.out.println("JSON Parse error at position: " + pe.getPosition());
	         System.out.println(pe);
		}
		
		return response;
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
		
		oResponse.put("type", "success");
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
}
