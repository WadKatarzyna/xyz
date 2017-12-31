package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Kategorie;

public class KategorieDAO implements DAO<Kategorie> {

	DBManager db = DBManager.getInstance();
	
	
	@Override
	public int create(Kategorie object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Kategorie findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Kategorie> findAll() {
	List<Kategorie> output = new ArrayList<>();
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("select * from kategorie order by kategorie_id");
			
			while(result.next()) {
				output.add(parse(result));
			}
		}catch(SQLException e) {
			
		}
		return output;
	}

	@Override
	public void update(Kategorie object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Kategorie object) {
		// TODO Auto-generated method stub
		
	}
	
	private Kategorie parse(ResultSet result) throws SQLException {
		Kategorie k = new Kategorie();
		k.setId(result.getInt("kategorie_id"));
		k.setName(result.getString("kategorie_name"));
		k.setBezeichnung(result.getString("kategorie_bezeichnung"));
		
		return k;
		
	}

}
