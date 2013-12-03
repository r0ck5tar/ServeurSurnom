package src;

import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ProtocoleClient {

	private static String action = "list";

	public ProtocoleClient() {

	}

	static public String add(Scanner sc) {

		action = "add";

		JSONObject oResponse = new JSONObject();
		System.out.print("entrer le nom : ");
		String nom = sc.next();
		System.out.print("entrer le surnom : ");
		String surnom = sc.next();

		oResponse.put("action", "add");
		oResponse.put("nick", surnom);
		oResponse.put("name", nom);
		return oResponse.toJSONString() + "\n";

	}

	static public String delete(Scanner sc) {

		action = "delete";

		JSONObject oResponse = new JSONObject();
		System.out.print("entrer le surnom : ");
		String surnom = sc.next();

		oResponse.put("action", "delete");
		oResponse.put("nick", surnom);
		return oResponse.toJSONString() + "\n";

	}

	static public String deleteAll(Scanner sc) {

		action = "deleteAll";

		JSONObject oResponse = new JSONObject();
		System.out.print("entrer le nom : ");
		String nom = sc.next();

		oResponse.put("action", "delete_name");
		oResponse.put("name", nom);
		return oResponse.toJSONString() + "\n";

	}

	static public String list() {

		action = "list";

		JSONObject oResponse = new JSONObject();
		oResponse.put("action", "list");
		return oResponse.toJSONString() + "\n";
	}

	static public String search(Scanner sc) {

		action = "search";

		JSONObject oResponse = new JSONObject();
		System.out.print("entrer le nom : ");
		String nom = sc.next();

		oResponse.put("action", "search");
		oResponse.put("name", nom);
		return oResponse.toJSONString() + "\n";

	}

	static public String ask(Scanner sc) {

		action = "ask";

		JSONObject oResponse = new JSONObject();
		System.out.print("entrer le surnom : ");
		String surnom = sc.next();

		oResponse.put("action", "ask");
		oResponse.put("nick", surnom);
		return oResponse.toJSONString() + "\n";

	}

	private static void transformeList(JSONObject reponse){

		JSONArray array = (JSONArray) reponse.get("nicks");

		for (int i = 0; i < array.size(); i++) {
			JSONObject pair = (JSONObject) array.get(i);
			System.out.println(pair.get("nick") + " " + pair.get("name"));
		}

	}
	
	static public void transforme(JSONObject reponse) {
		String resultat = "";

		switch (action) {
		case "add" : System.out.println(reponse.get("nick") + " " + reponse.get("name")); break;
		case "list": transformeList(reponse); break;

		}

	}
}
