package daoNoSQL;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import helper.NextId;
import model.WarenkorbArtikel;

/**
 * <h1> GenericDAO<T></h1>
 * CRUD-Methods for MongoDB	
 * 
 * @author Katarzyna Wadowska
 *
 */
public class GenericDAO<T> implements IGenericDAO<T> {

	private MongoCollection<Document> collection;
	GsonBuilder builder = new GsonBuilder();
	Gson gson;
	
	
	
	@Override
	public int create(T object) {
		
		this.gson = builder.create();
		this.collection = DBManager.getDatabase().getCollection(object.getClass().getSimpleName());
		int nextID = NextId.getNextId(object.getClass().getSimpleName());
		this.collection.insertOne(Document.parse(gson.toJson(object)).append("_id", nextID));
			
		return nextID;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public T getOneById(Class<?> clazz, int id) {
		
		this.collection = DBManager.getDatabase().getCollection(clazz.getSimpleName());
		List<Document> response = collection.find().into(new ArrayList<Document>());

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T object = null;
		for(Document d : response){
			if(d.getInteger("_id") == id){
				try {
					object = (T) mapper.readValue(d.toJson(), clazz);
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		return object;
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(Class<?> clazz) {
		List<T> output = new ArrayList<>();
		this.collection = DBManager.getDatabase().getCollection(clazz.getSimpleName());
		List<Document> response = collection.find().into(new ArrayList<Document>());

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		if(!response.isEmpty()) {
			for(Document d : response){
				try {
					output.add((T) mapper.readValue(d.toJson().toString(), clazz));
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		
		return output;
	}

	
	
	@Override
	public void update(T object, int id) {
		this.collection = DBManager.getDatabase().getCollection(object.getClass().getSimpleName());
		List<Document> response = collection.find().into(new ArrayList<Document>());
		this.gson = builder.create();
		for(Document d : response){
			if(d.getInteger("_id") == id){
				this.collection.updateOne(d, new Document("$set", Document.parse(gson.toJson(object))));
			}
		}
	}
	
	@Override
	public void delete(Class<?> clazz, int artikelId) {
		this.collection = DBManager.getDatabase().getCollection(clazz.getSimpleName());
		List<Document> response = collection.find().into(new ArrayList<Document>());
		for(Document d : response){
			if(d.getInteger("artikelid") == artikelId){
			this.collection.deleteOne(d)	;
			}
		}
		
	}
	
	//non-overrided methods from here
	
	//updates WarenkorbArtikel und ERHOEHT die gesamte Menge und die Summe
	public void updateWarenkorbArtikel(T object, int id, WarenkorbArtikel wa) {
		int menge = 0;	
		double summe = 0;
		
		this.collection = DBManager.getDatabase().getCollection(object.getClass().getSimpleName());
		List<Document> response = collection.find().into(new ArrayList<Document>());
		this.gson = builder.create();
		
		for(Document d : response){
			if (d.getInteger("warenkorbid") == wa.getWarenkorbid() && d.getInteger("artikelid") == wa.getArtikelid()) {
				menge = d.getInteger("menge");
				menge = menge + 1;
				
				summe = d.getDouble("summe");
				summe = summe + wa.getSumme();
				
				Bson newSummeUndMenge = new Document("summe", summe).append("menge", menge);
				Bson updateSummeUndMenge = new Document("$set", newSummeUndMenge);
				
				System.out.println("Summe "+summe+" Menge "+menge);
								
				this.collection.updateMany(d, updateSummeUndMenge);
			}
		}
	}
	
	//updates WarenkorbArtikel und REDUZIERT die gesamte Menge und die Summe
	public void updateDecrease(T object, int id, WarenkorbArtikel wa) {
		int menge = 0;	
		double summe = 0;
		
		this.collection = DBManager.getDatabase().getCollection(object.getClass().getSimpleName());
		List<Document> response = collection.find().into(new ArrayList<Document>());
		this.gson = builder.create();
		
		for(Document d : response){
			if (d.getInteger("warenkorbid") == wa.getWarenkorbid() && d.getInteger("artikelid") == wa.getArtikelid()) {
				menge = d.getInteger("menge");
				menge = menge - 1;
				
				summe = d.getDouble("summe");
				summe = summe - wa.getSumme();
				
				//delete from warenkorb if menge = 0
				if(menge == 0) {
					delete(WarenkorbArtikel.class, wa.getArtikelid());
					System.out.println("I am in menge");
				}
				
				Bson newSummeUndMenge = new Document("summe", summe).append("menge", menge);
				Bson updateSummeUndMenge = new Document("$set", newSummeUndMenge);
				
				System.out.println("Summe "+summe+" Menge "+menge);
								
				this.collection.updateMany(d, updateSummeUndMenge);
			}
		}
	}
	
	//zeigt alle Artikel an, die gleiche Kategorie haben wie die gesuchte Kategorie 
	@SuppressWarnings("unchecked")
	public List<T> sortArtikelWithCategory(Class<?> clazz, int categoryId) {
		List<T> output = new ArrayList<>();

		this.collection = DBManager.getDatabase().getCollection("Artikel");
		List<Document> responseArtikel = collection.find().into(new ArrayList<Document>());
		this.collection = DBManager.getDatabase().getCollection("ArtikelKategorie");
		List<Document> responseKategorie = collection.find().into(new ArrayList<Document>());

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		for(Document d : responseArtikel){
			for(Document d2 : responseKategorie)
			if(d.getInteger("_id") == d2.getInteger("artikel_id") && d2.getInteger("kategorie_id") == categoryId){
				try {
					output.add((T) mapper.readValue(d.toJson().toString(), clazz));
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		return output;
	}
	
	//zeigt alle Artikel an, die gleiche Unterkategorie haben wie die gesuchte Unterkategorie 
	@SuppressWarnings("unchecked")
	public List<T> sortArtikelWithUndercategory(Class<?> clazz, int undercategoryId) {
		List<T> output = new ArrayList<>();

		this.collection = DBManager.getDatabase().getCollection("Artikel");
		List<Document> responseArtikel = collection.find().into(new ArrayList<Document>());
		this.collection = DBManager.getDatabase().getCollection("ArtikelKategorie");
		List<Document> responseKategorie = collection.find().into(new ArrayList<Document>());

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		for(Document d : responseArtikel){
			for(Document d2 : responseKategorie)
			if(d.getInteger("_id") == d2.getInteger("artikel_id") && d2.getInteger("unterkategorie_id") == undercategoryId){
				try {
					output.add((T) mapper.readValue(d.toJson().toString(), clazz));
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		return output;
	}
		
	//zeigt alle Artikel an, die gleiche Kategorie und Unterkategorie haben wie die gesuchte Kategorie/Uterkategorie 
	@SuppressWarnings("unchecked")
	public List<T> sortArtikelWithBothCategory(Class<?> clazz, int undercategoryId, int categoryId) {
		List<T> output = new ArrayList<>();

		this.collection = DBManager.getDatabase().getCollection("Artikel");
		List<Document> responseArtikel = collection.find().into(new ArrayList<Document>());
		this.collection = DBManager.getDatabase().getCollection("ArtikelKategorie");
		List<Document> responseKategorie = collection.find().into(new ArrayList<Document>());

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		for(Document d : responseArtikel){
			for(Document d2 : responseKategorie)
			if(d.getInteger("_id") == d2.getInteger("artikel_id") && d2.getInteger("unterkategorie_id") == undercategoryId && d2.getInteger("kategorie_id") == categoryId){
				try {
					output.add((T) mapper.readValue(d.toJson().toString(), clazz));
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		return output;
	}

	//Zeigt die Artikel an, deren Beschreibung enthaelt die gesuchte Keyword
	@SuppressWarnings("unchecked")
	public List<T> sortAllWithKeyword(Class<?> clazz, String keyword) {
		List<T> output = new ArrayList<>();

		this.collection = DBManager.getDatabase().getCollection(clazz.getSimpleName());
		List<Document> response = collection.find().into(new ArrayList<Document>());

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		for(Document d : response){
			if(d.getString("beschreibung").toLowerCase().contains(keyword.toLowerCase())){
				try {
					output.add((T) mapper.readValue(d.toJson().toString(), clazz));
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		return output;
	}
	
	//Gibt alle WarenkorbArtikel zuruck, welche zu dem accountId passen
	@SuppressWarnings("unchecked")
	public List<T> allCartItemsByAccountId(Class<?> clazz, int accountId){
		List<T> warenkorbArtikel = new ArrayList<>();
		
		this.collection = DBManager.getDatabase().getCollection("Warenkorb");
		List<Document> responseWarenkorb = collection.find().into(new ArrayList<Document>());
		this.collection = DBManager.getDatabase().getCollection("WarenkorbArtikel");
		List<Document> responseWarenkorbArtikel = collection.find().into(new ArrayList<Document>());
		
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		for(Document d : responseWarenkorb){
			for(Document d2 : responseWarenkorbArtikel)
			if(d.getInteger("id") == d2.getInteger("warenkorbid") && d.getInteger("accountId") == accountId){
				try {
					warenkorbArtikel.add((T) mapper.readValue(d2.toJson().toString(), clazz));
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		
		return warenkorbArtikel;		
	}
	
	//erstellt neues Objekt in der Tabelle welches auch _id und id hat - wegen Registrierung und Warenkorb 
	public int createWithTwoIDs(T object) {		
		this.gson = builder.create();
		this.collection = DBManager.getDatabase().getCollection(object.getClass().getSimpleName());
		int nextID = NextId.getNextId(object.getClass().getSimpleName());
		this.collection.insertOne(Document.parse(gson.toJson(object)).append("_id", nextID).append("id", nextID));
			
		return nextID;
	}

}
