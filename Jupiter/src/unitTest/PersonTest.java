package unitTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dao.DBManager;
import model.Person;

public class PersonTest {
	
	private DBManager db = DBManager.getInstance();
	
	@Test
	public void testCreate() throws ParseException {
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		String d = df.format(df.parse("28.09.1999"));
		Date date = df.parse(d);
		
		
		Person p = new Person();
		p.setVorname("Kasia");
		p.setNachname("Wadowska");
		p.setGeschlecht("weiblich");
		p.setGeburtsdatum(new Date());
		p.setEmail("test@mail.com");
		p.setRolle(2);
		db.getPersonDAO().create(p);
		
		
	}

}
