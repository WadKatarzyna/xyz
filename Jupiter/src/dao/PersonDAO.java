package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.Person;

/**
 * 
 * @author Katarzyna Wadowska
 *
 */
public class PersonDAO implements DAO<Person>{

	
	private DBManager db;
	
	protected PersonDAO(DBManager db) {
		this.db = db;
	}
	
	
	
	@Override
	public void create(Person object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Person findById(int id) {
		try {
			ResultSet result = db.getConnection().createStatement()
					.executeQuery("SELECT * FROM person WHERE person_personalnr=" +id);

			if (result.next())
				return parse(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Person> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Person object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Person object) {
		// TODO Auto-generated method stub
		
	}
	
	
	private Person parse(ResultSet result) throws SQLException {
		Person person = new Person();
		person.setPersonalnr(result.getInt("person_personalnr"));
		person.setVorname(result.getString("person_vorname"));
		person.setNachname(result.getString("person_nachname"));
		person.setGeschlecht(result.getString("person_geschlecht"));
		person.setGeburtsdatum(result.getDate("person_geburtsdatum"));
		person.setGehalt(result.getLong("person_gehalt"));
		person.setRolle(result.getInt("person_rolle_id"));

		return person;
	}

}
