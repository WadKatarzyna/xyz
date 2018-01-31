package daoSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Hersteller;

public class HerstellerDAO implements DAO<Hersteller> {

	DBManager db = DBManager.getInstance();
	
	public HerstellerDAO(DBManager db) {
		this.db = db;
	}
	
	@Override
	public int create(Hersteller object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Hersteller findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hersteller> findAll() {
		List<Hersteller> output = new ArrayList<>();
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("select * from hersteller order by hersteller_id");
			
			while(result.next()) {
				output.add(parse(result));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return output;
	}

	@Override
	public void update(Hersteller object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Hersteller object) {
		// TODO Auto-generated method stub
		
	}
	
	
	private Hersteller parse(ResultSet result) throws SQLException {
		Hersteller h = new Hersteller();
		h.setId(result.getInt("hersteller_id"));
		h.setName(result.getString("hersteller_name"));
		
		return h;
	}

}
