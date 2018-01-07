package unitTest;

import org.junit.Test;

import dao.DBManager;
import model.WarenkorbArtikel;

public class WarenkorbArtikelTest {

	private DBManager db = DBManager.getInstance();
	
	@Test
	public void testefindAllCartItemsByAccountId() {
		db.getWarenkorbArtikelDAO().findAllCartItemsByAccountId(1);
		
	}
	
	
	@Test
	public void testUpdate() {
		WarenkorbArtikel wa = new WarenkorbArtikel();
		wa.setArtikelid(1);
		wa.setWarenkorbid(41);
		wa.setMenge(66);
		wa.setSumme(123456);
		db.getWarenkorbArtikelDAO().update(wa);
	}
}
