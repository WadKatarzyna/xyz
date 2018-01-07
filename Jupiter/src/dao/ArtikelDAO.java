package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Artikel;

public class ArtikelDAO implements DAO<Artikel> {

	DBManager db = DBManager.getInstance();
	
	public ArtikelDAO(DBManager db) {
		this.db = db;
	}
	
	@Override
	public int create(Artikel object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Artikel findById(int id) {
		try {
			ResultSet result = db.getConnection().createStatement()
					.executeQuery("SELECT * FROM artikel WHERE artikel_id=" +id);

			if (result.next())
				return parse(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Artikel> findAll() {
		List<Artikel> output = new ArrayList<>();
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("select * from artikel order by artikel_id");
			
			while(result.next()) {
				output.add(parse(result));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return output;
	}

	@Override
	public void update(Artikel object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Artikel object) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Artikel> sortWithKeyword(String keyword) {
		List<Artikel> output = new ArrayList<>();
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("select * from artikel where artikel_beschreibung like '%"+keyword+"%'");
			
			while(result.next()) {
				output.add(parse(result));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return output;
				
	}
	
	public List<Artikel> sortWithCategory(int categoryID) {
		List<Artikel> output = new ArrayList<>();
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("SELECT * FROM artikel WHERE artikel_id IN (SELECT artikelkategorie_artikel_id FROM artikelkategorie WHERE artikelkategorie_kategorie_id="+categoryID+")");
			
			while(result.next()) {
				output.add(parse(result));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public List<Artikel> sortWithUndercategory(int undercategoryID) {
		List<Artikel> output = new ArrayList<>();
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("SELECT * FROM artikel WHERE artikel_id IN (SELECT artikelkategorie_artikel_id FROM artikelkategorie WHERE artikelkategorie_unterkategorie_id="+undercategoryID+")");
			
			while(result.next()) {
				output.add(parse(result));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public List<Artikel> sortWithBothCcategory(int kategorieID, int unterkategorieID) {
		List<Artikel> output = new ArrayList<>();
		
		try {
			ResultSet result = db.getConnection().createStatement().executeQuery("SELECT * FROM artikel WHERE artikel_id IN (SELECT artikelkategorie_artikel_id FROM artikelkategorie WHERE artikelkategorie_unterkategorie_id="+unterkategorieID+" and artikelkategorie_kategorie_id="+kategorieID+")");
			
			while(result.next()) {
				output.add(parse(result));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	private Artikel parse(ResultSet result) throws SQLException {
		Artikel a = new Artikel();
		a.setId(result.getInt("artikel_id"));
		a.setBeschreibung(result.getString("artikel_beschreibung"));
		a.setRestmenge(result.getInt("artikel_rest_menge"));
		a.setHersteller_id(result.getInt("artikel_hersteller_id"));
		
		return a;
		
	}

}
