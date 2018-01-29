package unitTest.NoSQL;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import daoNoSQL.GenericDAO;
import model.Artikel;
import model.ArtikelKategorie;
import model.Person;

public class ArtikelTest {
private GenericDAO<Artikel> dao = new GenericDAO<>();
	

	@Test
	public void testFindArtikelById() {
		int id = 1;
		Artikel artikel = dao.getOneById(Artikel.class, id);
		assertEquals("Hemd MDK Blau", artikel.getBeschreibung());
	}

	@Test
	public void testSortArtikelWithKeyword() {
		String keyWord = "Hemd MDK Blau";
		List<Artikel> artikelList = new ArrayList<>();
		artikelList = dao.sortAllWithKeyword(Artikel.class, keyWord);
		Artikel artikel = artikelList.get(0);
		assertEquals(1, artikel.getId());
	}

	@Test
	public void testFindAllArtikel() {
		List<Artikel> artikelList = new ArrayList<>();
		artikelList = dao.getAll(Artikel.class);
		assertEquals(100, artikelList.size());
	}

	@Test 
	public void testSortArtikelWithCategory() {
		List<Artikel> artikelList = new ArrayList<>();
		artikelList = dao.sortArtikelWithCategory(ArtikelKategorie.class, 1); //Suche nach Kategorie == Damen (alle Damenartikel)
		assertEquals(47, artikelList.size());
	}

	@Test 
	public void testSortArtikelWithUndercategory() {
		List<Artikel> artikelList = new ArrayList<>();
		artikelList = dao.sortArtikelWithUndercategory(ArtikelKategorie.class, 1); // Suche nach Unterkategorie == Oberteile (alle Oberteile)
		assertEquals(20, artikelList.size());
	}

	@Test 
	public void testSortArtikelWithBothCategory() {
		List<Artikel> artikelList = new ArrayList<>();
		artikelList = dao.sortArtikelWithBothCategory(ArtikelKategorie.class, 1, 1); // Search for Damenoberteile (alle Damenoberteile)
		assertEquals(4, artikelList.size());
	}
}
