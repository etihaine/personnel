package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import commandLineMenus.Action;
import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}

	ListOption<Employe> editerEmploye()
	{
		return (employe) -> editerEmploye(employe);		
	}

	Option editerEmploye(Employe employe)
	{
			Menu menu = new Menu("Gérer le compte de \"" + employe.getNom() + "\"", "c");
			menu.add(afficher(employe));
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
			menu.addBack("q");
			return menu;
	}
	
	
	// Gestion des employes
	
	
	public Menu editerEmployeMenu(Employe employe)
	{
		Menu menu = new Menu("Editer " + employe.getNom() + " " + employe.getPrenom());
		menu.add(editerEmploye(employe));
		menu.add(supprimerEmploye(employe));
		menu.setAutoBack(true);
		return menu;
	}
	
	private Option supprimerEmploye(final Employe employe)
	{
		return new Option ("Supprimer cet employé", "x", supprimerEmployeAction(employe));
	}
	
	private Action supprimerEmployeAction(Employe employe)
	{
		return new Action() {

			public void optionSelected() {
				employe.remove();
				System.out.println("\n Employé supprimé !");
			}
			
		};
	}
	

	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {employe.setNom(getString("Nouveau nom : "));}
			);
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {employe.setPrenom(getString("Nouveau prénom : "));});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {employe.setPassword(getString("Nouveau password : "));});
	}
	

}
