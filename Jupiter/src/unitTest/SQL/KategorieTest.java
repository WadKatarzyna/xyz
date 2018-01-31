package unitTest.SQL;

import java.util.List;

import org.junit.Test;

import daoSQL.DBManager;
import model.Kategorie;

public class KategorieTest {
	
	DBManager db = DBManager.getInstance();
	@Test
	public void findAllTest() {
		
		List<Kategorie> list = db.getKategorieDAO().findAll();
		
		System.out.println(list.size());
	}

}
