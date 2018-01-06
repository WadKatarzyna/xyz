package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Unterkategorie;

public class UnterkategorieDAO implements DAO<Unterkategorie>{
DBManager db = DBManager.getInstance();
	
	public UnterkategorieDAO(DBManager db) {
		this.db = db;
	}
	
	@Override
	public int create(Unterkategorie object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Unterkategorie findById(int id) {
		try {
			ResultSet result = db.getConnection().createStatement()
					.executeQuery("SELECT * FROM unterkategorie WHERE unterkategorie_id=" +id);

			if (result.next())
				return parse(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Unterkategorie> findAll() {
	List<Unterkategorie> output = new ArrayList<>();
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("select * from unterkategorie order by unterkategorie_id");
			
			while(result.next()) {
				output.add(parse(result));
			}
		}catch(SQLException e) {
			
		}
		return output;
	}

	@Override
	public void update(Unterkategorie object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Unterkategorie object) {
		// TODO Auto-generated method stub
		
	}
	
	private Unterkategorie parse(ResultSet result) throws SQLException {
		Unterkategorie k = new Unterkategorie();
		k.setId(result.getInt("unterkategorie_id"));
		k.setName(result.getString("unterkategorie_name"));
		k.setBezeichnung(result.getString("unterkategorie_bezeichnung"));
		
		return k;
		
	}
}
