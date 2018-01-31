package unitTest.SQL;

import org.junit.jupiter.api.Test;

import daoSQL.DBManager;
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
		System.out.println(x.getNachname());
		System.out.println(x.getGeschlecht());
	}
	
	@Test
	public void testSetWorkwith() {
		db.getWorkWithDAO().setWorkwith(1);
	}
	
	@Test
	public void testGetWorkWith() {
		System.out.println(db.getWorkWithDAO().getWorkwith());
		
	}
}
