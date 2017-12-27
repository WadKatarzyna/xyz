package unitTest;

import org.junit.jupiter.api.Test;

import dao.DBManager;
import model.Person;

/**
 * 
 * @author Katarzyna Wadowska
 *
 */
public class DBConnectionTest {
	private DBManager db = DBManager.getInstance();
	
	@Test
	public void testConnection() {
		Person x = db.getPersonDAO().findById(1);
		System.out.println(x.getVorname());
	}
	
}
