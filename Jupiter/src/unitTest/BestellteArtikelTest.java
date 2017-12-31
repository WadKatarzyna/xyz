package unitTest;

import java.util.List;

import org.junit.Test;

import dao.DBManager;
import model.BestellteArtikel;

public class BestellteArtikelTest {
	
	DBManager db = DBManager.getInstance();
	
	@Test
	public void testgetall() {
		List<BestellteArtikel> list = db.getBestellteArtikelDAO().findAll();

	}
}
