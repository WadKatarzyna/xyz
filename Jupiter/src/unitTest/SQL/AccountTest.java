package unitTest.SQL;

import java.util.Date;

import org.junit.Test;

import daoSQL.DBManager;
import model.Account;

public class AccountTest {
	
	private DBManager db = DBManager.getInstance();
	
	
	@Test
	public void testCreate() {
		Account account = new Account();
		account.setUsername("kwwww");
		account.setPasswort("kwwww2");
		account.setPerson_personalnr(1);
		account.setErstelldatum(new Date().toString());
		
		db.getAccountDAO().create(account);
	}
}
