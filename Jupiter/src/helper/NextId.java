package helper;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import daoNoSQL.DBManager;

/**
 * <h1> NextId</h1>
 * This method returns the next free _id of a collection	
 * @param collection_name is the name of the specific collection
 * 
 * @author Katarzyna Wadowska
 *
 */
public class NextId {

	public static int getNextId(String collection_name){
			
		int nextid = 0;
		
		MongoCollection<Document> collection = DBManager.getDatabase().getCollection(collection_name);
		
		List<Document> list = collection.find().into(new ArrayList<Document>());
		
		if(list.isEmpty()){
			nextid = 0;
		}else{
			for(Document document: list){
				if(nextid < document.getInteger("id")){
					nextid = document.getInteger("id");
				}
			}
		}
		
		return ++nextid;
	}
}
