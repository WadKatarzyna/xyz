package daoNoSQL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;

import helper.NextId;

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
	public void create(T object) {
		
		this.gson = builder.create();
		this.collection = DBManager.getDatabase().getCollection(object.getClass().getSimpleName());
		this.collection.insertOne(Document.parse(gson.toJson(object)).append("_id", NextId.getNextId(object.getClass().getSimpleName())));
			
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
			if(d.getInteger("id") == id){
				this.collection.updateOne(d, new Document("$set", Document.parse(gson.toJson(object))));
			}
		}
	}

	
	
	@Override
	public void delete(Class<?> clazz, int id) {
		this.collection = DBManager.getDatabase().getCollection(clazz.getSimpleName());
		Document query = new Document("id", id);
		this.collection.deleteOne(query);
		
	}

}
