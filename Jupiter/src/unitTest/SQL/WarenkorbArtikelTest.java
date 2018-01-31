package unitTest.SQL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import daoSQL.DBManager;
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
		//db.getWarenkorbArtikelDAO().update(wa);
	}
	
	@Test
	public void testDateFormater() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		
		
		int day = Calendar.DAY_OF_MONTH + 5;
		cal.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH + 5 );
		
		Date date = cal.getTime();
		System.out.println(date);
	}
}
