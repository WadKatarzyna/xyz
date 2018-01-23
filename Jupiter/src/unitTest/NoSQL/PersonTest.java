package unitTest.NoSQL;

import org.junit.Test;

import daoNoSQL.GenericDAO;
import model.Person;

public class PersonTest {
	
	private GenericDAO<Person> dao = new GenericDAO<>();
	

	@Test
	public void createTest() {
		Person p = new Person();
		p.setVorname("Katarzyna");
		p.setNachname("Wadowska");
		p.setEmail("info@jupiter.com");
		p.setGeschlecht("weiblich");
		
		dao.create(p);
	}
}
