package unitTest.NoSQL;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import daoNoSQL.GenericDAO;
import model.Account;
import model.Person;

public class AccountTest {
	
	private GenericDAO<Person> dao = new GenericDAO<>();
	private GenericDAO<Account> daoAccount = new GenericDAO<>();



	@Test
	public void testCreateAccount() {
		Person person = new Person();
		Account account = new Account();
		
		person.setVorname("Peter");
		person.setNachname("Zolcer");
//		person.setGeburtsdatum("");
		person.setGeschlecht("mänlich");
		person.setEmail("zolcer@gmail.com");
		
		int person_personalnr = dao.create(person);
		
		account.setUsername("pycoooooooo");
		account.setPasswort("pycoooooooo");
		account.setPerson_personalnr(person_personalnr);
		
		daoAccount.create(account);
		
		Account test = daoAccount.getOneById(Account.class, person_personalnr);
		
		assertEquals("pycoooooooo", test.getUsername());

		
	}
}
