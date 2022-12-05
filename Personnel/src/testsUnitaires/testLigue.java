package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty"); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void getNom() throws SauvegardeImpossible 
	{
		Ligue ligue = gestionPersonnel.addLigue("Football");
		assertEquals("Football", ligue.getNom());
	}
	
	@Test
	void setNom() throws SauvegardeImpossible 
	{
		Ligue ligue = gestionPersonnel.addLigue("Tennis");
		ligue.setNom("Volley");
		assertEquals("Volley", ligue.getNom());
	}
	
	@Test
	void getAdministrateur() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Baseball");
		Employe admin = ligue.addEmploye("Admin", "Amine", "admin@gmail.com", "nimda");
		ligue.setAdministrateur(admin);
		assertEquals(admin, ligue.getAdministrateur());
	}
	
	@Test 
	void setAdministrateur() throws DroitsInsuffisants, SauvegardeImpossible
	{
		Ligue ligueTennis = gestionPersonnel.addLigue("Tennis");
		Employe admin = ligueTennis.addEmploye("Admin", "admin", "admin@gmial.com", "nimda");
		ligueTennis.setAdministrateur(admin);
	}
	
	@Test
	void getEmployes() throws SauvegardeImpossible 
	{
		Ligue ligue = gestionPersonnel.addLigue("Tennis");
		Employe pierre = ligue.addEmploye("Dupont", "Pierre", "p.dupont@gmail.com", "rockTheBridge!");
		Employe jack = ligue.addEmploye("Han", "Jack", "j.han@gmail.com", "motdepasse");
		assertEquals(pierre, ligue.getEmployes().first());
		assertEquals(jack, ligue.getEmployes().last());
	}
	
	@Test 
	void remove() throws SauvegardeImpossible 
	{
		Ligue ligue = gestionPersonnel.addLigue("Tennis");
		ligue.remove();
		assertEquals(false, gestionPersonnel.getLigues().equals(ligue));
	}
	
	@Test 
	void compareTo() throws SauvegardeImpossible
	{
		Ligue tennis = gestionPersonnel.addLigue("Tennis");
		Ligue boxe = gestionPersonnel.addLigue("Boxe");
		assertEquals(0, tennis.compareTo(tennis));
		assertEquals(18, tennis.compareTo(boxe));
	}
	
	@Test 
	void testToString() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Football");
		assertEquals("Football", ligue.toString());
	}
	
}
