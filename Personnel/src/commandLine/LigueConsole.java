package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.util.ArrayList;
import java.util.SortedSet;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;
	LigueConsole ligueConsole;


	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
		this.ligueConsole = ligueConsole;

	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {
			
			SortedSet<Employe> employes = ligue.getEmployes();
			
			if(employes.isEmpty())
				System.out.println("Aucun employé");
			else
				System.out.println(ligue.getEmployes());
			});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer la ligue de " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		//menu.add(changerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {ligue.setNom(getString("Nouveau nom : "));});
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("Sélectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	private Option ajouterEmploye(final Ligue ligue)
	{
		return new Option("ajouter un employé", "a",
				() -> 
				{
					ligue.addEmploye(getString("nom : "), 
						getString("prenom : "), getString("mail : "), 
						getString("password : "));
				}
		);
	}
	
	// Ce menu permet de choisir le type de contrat de l'employé
	
	private Menu ajouterEmployeMenu(Ligue ligue)
	{
		Menu menu = new Menu("Choisissez le type de contrat :", "ajouter un employé (new)", "A");
		menu.add(contratCdiOption());
		menu.add(contratCddOption());
		menu.addBack("q");
		return menu;
	}
	
	private Option contratCdiOption()
	{
		return new Option("contrat en CDI", "i", 
			() -> 
			{
				System.out.println("Action temporaire : CDI sélectionné");
			}
		);
	}
	
	private Option contratCddOption()
	{
		return new Option("contrat en CDD", "d", 
			() -> 
			{
				System.out.println("Action temporaire : CDD sélectionné");
			}
		);	
	}
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("Gérer les employés", "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(ajouterEmployeMenu(ligue));
		//menu.add(modifierEmploye(ligue));
		menu.add(selectionnerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}
	private  Menu selectionnerEmploye(Ligue ligue)
	{
		Menu menu = new Menu("Gérer l'employé", "e ");
		menu.add(modifierEmploye(ligue));
		menu.add(supprimerEmploye(ligue));
		return menu;
	}
	
	/*private List<Employe> selectionnerEmploye(final Ligue ligue)
	{
		return new List<>("Séléctionner un employé", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(element) -> gererE(element)
				);
	}*/

	private List<Employe> supprimerEmploye(final Ligue ligue)
	{
		return new List<>("Supprimer un employé", "s", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {element.remove();}
				);
	}
	
	@SuppressWarnings("unused")
	private List<Employe> changerAdministrateur(final Ligue ligue)
	{
		return null;
	}		

	private List<Employe> modifierEmploye(final Ligue ligue)
	{
		return new List<>("Modifier un employé", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye()
				);
	}
	
	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {ligue.remove();});
	}
	
}
