package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import personnel.*;

public class testEmploye {
	
	private GestionPersonnel gestionPersonnel;
	private Ligue ligue;
	private Employe employe;
	private Employe root;

	@BeforeEach 
	private void setup() throws SauvegardeImpossible
	{
		gestionPersonnel = GestionPersonnel.getGestionPersonnel();
		ligue = new Ligue(gestionPersonnel, "Football");
		employe = ligue.addEmploye("Doe", "John", "example@gmail.com", "password");
		root = gestionPersonnel.getRoot();
	}
	
	@AfterEach
	private void tearDown() 
	{
		employe.remove();
		ligue.remove();
	}


	// Il semblerait que les test unitaires sur les accesseurs ne soient pas les plus utiles
	// Il serait préférable de tester les fonctions effectuant un traitement sur les valeurs 

	@Test
	void testEmployeGetter() throws SauvegardeImpossible 
	{
		assertEquals("Doe",employe.getNom());
		assertEquals("John", employe.getPrenom());
	}


	@Test
	void testEmployeDateSetter() 
	{
		assertEquals(null, employe.getDateDepart());
		employe.setDateDepart(LocalDate.of(2023, 12, 20));
		assertEquals(LocalDate.of(2023, 12, 20), employe.getDateDepart());
	}
	
	@Test
	void estRootTest()
	{
		assertEquals(false, employe.estRoot());
		assertEquals(true, root.estRoot());
	}
	
	@Test 
	void testEstAdmin()
	{
		assertEquals(false, employe.estAdmin(ligue));
		assertEquals(true, root.estAdmin(ligue));
		ligue.setAdministrateur(employe);
		assertEquals(true, employe.estAdmin(ligue));
		assertEquals(false, root.estAdmin(ligue));
	}
	
	@Test 
	void testEmployeSetters() throws SauvegardeImpossible
	{
		employe.setNom("Dye");
		assertEquals("Dye", employe.getNom());
		employe.setPrenom("Jane");
		assertEquals("Jane", employe.getPrenom());
	}
	
	@Test
	void testMailSetter()
	{
		assertEquals("example@gmail.com", employe.getMail());
		employe.setMail("john.doe@gmail.com");
		assertEquals("john.doe@gmail.com", employe.getMail());
	}
	
	@Test
	void testCheckPassword()
	{
		assertEquals(true, employe.checkPassword("password"));
		employe.setPassword("S€cUr€paS$w0rD");
		assertEquals(false, employe.checkPassword("password"));
		assertEquals(true, employe.checkPassword("S€cUr€paS$w0rD"));
	}
	
	@Test
	void testLigue()
	{
		assertEquals(ligue, employe.getLigue());
	}
	
	@Test
	void testEmployeToString()
	{
		assertEquals("Doe John example@gmail.com (Football)", employe.toString());
	}
}