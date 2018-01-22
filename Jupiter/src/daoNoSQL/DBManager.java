package daoNoSQL;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * <h1> DBManager</h1>
 * This class allows connection to MongoDB
 * 
 * @author Katarzyna Wadowska
 * 
 */
public class DBManager {

	private static MongoDatabase db;

	
	public static MongoDatabase getDatabase(){
		if(db == null){
			db = new MongoClient("localhost", 27017).getDatabase("IMSE");
		}
		
		return db;
	}
	
}
