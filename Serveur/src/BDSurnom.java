import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;


import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class BDSurnom {
	private HashMap<String, String> mapSurnom;

	BDSurnom() {
		mapSurnom = new HashMap<String, String>();
	}

	public void initMapSurnom(String fileName) {

		org.jdom2.Document doc = null;;
		SAXBuilder sxb = new SAXBuilder();
		try {
			doc = sxb.build(new File("ressources/" + fileName));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Element root = doc.getRootElement();
		//The root of the XML file is saved, and it is then possible to use it to get the data.
		
		//Get all the "nom" elements
		List<Element> noms = root.getChildren("nom");
		Iterator<Element> itNoms = noms.iterator();

		while(itNoms.hasNext()) {
			Element currentNom = itNoms.next();
			//get all the "surnom" elements
			List<Element> surnoms = currentNom.getChildren("surnom");
			Iterator<Element> itSurnoms = surnoms.iterator();

			while(itSurnoms.hasNext()) {
				Element currentSurnom = itSurnoms.next();
				mapSurnom.put(currentSurnom.getText().trim(), currentNom.getText().trim());
			}
		} 
	}
	
	public String rechercheSurnom(String surnom) {
		return mapSurnom.get(surnom);
	}
	
	public List<String> rechercheNom(String nom) {
		ArrayList<String> listeSurnoms = new ArrayList<String>();
		if (mapSurnom.containsValue(nom)) {
			for (Map.Entry<String, String> entry : mapSurnom.entrySet()) {
			    if(entry.getValue().equals(nom)) {
			    	listeSurnoms.add(entry.getKey().trim());
			    }
			}
		}
		
		return listeSurnoms;
	}
	
	public List<String> listeSurnom() {
		return new ArrayList<String>(mapSurnom.keySet());
	}
	
	public List<String> listeNom() {
		return new ArrayList<String>(new HashSet<String>(mapSurnom.values()));
	}
	
	public Set<Entry<String, String>> listeSurnomEtNom() {
		return mapSurnom.entrySet();
	}
	
	public void supprimeSurnom(String surnom) {
		mapSurnom.remove(surnom);
	}
	
	public void supprimeNom(String nom) {
		if (mapSurnom.containsValue(nom)) {
			Iterator<Entry<String, String>> it = mapSurnom.entrySet().iterator();
		    while (it.hasNext()) {
		        Entry<String, String> entry = it.next();
		        if(entry.getValue().equals(nom)) {
		        	it.remove();
		        }
		    }
		}
	}
	
	public void ajouterSurnomEtNom(String surnom, String nom) {
		mapSurnom.put(surnom, nom);
	}
}
