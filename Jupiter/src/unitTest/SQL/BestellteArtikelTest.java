package unitTest.SQL;

import java.util.List;

import org.junit.Test;

import daoSQL.DBManager;
import model.BestellteArtikel;

public class BestellteArtikelTest {
	
	DBManager db = DBManager.getInstance();
	
	@Test
	public void testgetall() {
		List<BestellteArtikel> list = db.getBestellteArtikelDAO().findAll();

	}
}
