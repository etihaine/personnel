package testsUnitaires;



import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;
public class testEmploye {
		
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void Employe() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("foot");
		Employe employe = ligue.addEmploye("mal", "etienne", "etimal@gmail.com", "bonjour");
		assertEquals("mal",employe.getNom());
		assertEquals("etienne", employe.getPrenom());
	}


	@Test
	void SetDateDepart() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("foot");
		Employe employe = ligue.addEmploye("mal", "etienne", "etimal@gmail.com", "bonjour");
		new LocalDate = localdate.of();
	    employe = employe.setDateDepart(new LocalDate.of(2023, 12, 20));
		assertEquals("2023-12-20", employe.getDateDepart());
	}
}