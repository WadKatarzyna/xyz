package daoSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import model.Person;

/**
 * 
 * @author Katarzyna Wadowska
 *
 */
public class PersonDAO implements DAO<Person>{

	
	private static final String READ_FINDBYID = "SELECT * FROM person WHERE person_personalnr=?";

	
	private DBManager db;
	
	protected PersonDAO(DBManager db) {
		this.db = db;
	}
	
	
	
	@Override
	public int create(Person p) {
		try {
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			String date = df.format(p.getGeburtsdatum());
			
			String sql = (null+", '"+p.getVorname()+"', '"+p.getNachname()+"', '"+p.getGeschlecht()+"', '"+p.getGeburtsdatum()+"',"
					+ " '"+p.getEmail()+"', 2");  // 2 für user ( standardmäßig werden einträge als user gespeichert)
												 // 1 für admin... siehe rolle tabelle
			
		    PreparedStatement pstmt = (PreparedStatement) db.getConnection().prepareStatement("INSERT INTO person VALUES("+ sql +");");

		    pstmt.executeUpdate();
		    
			pstmt = (PreparedStatement) db.getConnection().prepareStatement("SELECT MAX(person_personalnr) FROM person") ;
		    ResultSet rs = pstmt.executeQuery();
		    
		    int id = -1;
		    if (rs.next()) {
		      id = rs.getInt(1);
		    }
		    
		    rs.close();
		    pstmt.close();
			
		    
		    return id;

//		xs
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

	@Override
	public Person findById(int id) {
		try {
			PreparedStatement pstmt = (PreparedStatement) db.getConnection().prepareStatement(READ_FINDBYID);
			pstmt.setInt(1, id);
			ResultSet result = pstmt.executeQuery();
			
			if (result.next())
				return parse(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Person> findAll() {
		List<Person> output = new ArrayList<>();
		try {
			ResultSet result = db.getConnection().createStatement()
					.executeQuery("select * from person order by person_personalnr");

			while (result.next())
				output.add(parse(result));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
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
		person.setGeburtsdatum(result.getString("person_geburtsdatum"));
		person.setEmail(result.getString("person_email"));
		person.setRolle(result.getInt("person_rolle_id"));

		return person;
	}

}
