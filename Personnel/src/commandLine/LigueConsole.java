package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.SortedSet;

import commandLineMenus.Action;
import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;

public class LigueConsole {
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;
	LigueConsole ligueConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole) {
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
		this.ligueConsole = ligueConsole;

	}

	Menu menuLigues() {
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues() {
		return new Option("Afficher les ligues", "l", () -> {
			System.out.println(gestionPersonnel.getLigues());
		});
	}

	private Option afficher(final Ligue ligue) {
		return new Option("Afficher la ligue", "l",
				() -> {
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				});
	}

	private Option afficherEmployes(final Ligue ligue) {
		return new Option("Afficher les employes", "l", () -> {

			SortedSet<Employe> employes = ligue.getEmployes();

			if (employes.isEmpty())
				System.out.println("Aucun employé");
			else
				System.out.println(ligue.getEmployes());
		});
	}

	private Option ajouterLigue() {
		return new Option("Ajouter une ligue", "a", () -> {
			try {
				gestionPersonnel.addLigue(getString("nom : "));
			} catch (SauvegardeImpossible exception) {
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}

	private Menu editerLigue(Ligue ligue) {
		Menu menu = new Menu("Editer la ligue de " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue) {
		return new Option("Renommer", "r",
				() -> {
					ligue.setNom(getString("Nouveau nom : "));
				});
	}

	private List<Ligue> selectionnerLigue() {
		return new List<Ligue>("Sélectionner une ligue", "e",
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element));
	}

	private Menu ajouterEmploye(final Ligue ligue) {
		Menu menu = new Menu("Choisissez le type de contrat :", "ajouter un employé", "a");
		menu.add(contratCdiOption(ligue));
		menu.add(contratCddOption(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option contratCdiOption(Ligue ligue) {
		return new Option("contrat en CDI", "i", ajouterEmployeCdiAction(ligue));
	}

	private Action ajouterEmployeCdiAction(Ligue ligue) {
		return new Action() {
			public void optionSelected() {
				ligue.addEmploye(getString("Nom : "), getString("Prenom : "), getString("Mail : "),
						getString("Mot de passe : "));
			}
		};
	}

	private Option contratCddOption(Ligue ligue) {
		return new Option("contrat en CDD", "d", ajouterEmployeCddAction(ligue));
	}

	private Action ajouterEmployeCddAction(Ligue ligue) {
		return new Action() {
			public void optionSelected() {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				ligue.addEmploye(getString("Nom : "), getString("Prenom : "), getString("Mail : "),
						getString("Mot de passe : "),
						LocalDate.parse(getString("Date fin contrat : "), formatter));
			}
		};
	}

	private Menu gererEmployes(Ligue ligue) {
		Menu menu = new Menu("Gérer les employés", "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(selectionnerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}

	@SuppressWarnings("unused")
	private List<Employe> selectionnerEmploye(Ligue ligue) {
		return new List<Employe>("Selectionner un employe", "s",
				() -> new ArrayList<>(ligue.getEmployes()),
				(element) -> employeConsole.editerEmployeMenu(element));
	}

	@SuppressWarnings("unused")
	private List<Employe> changerAdministrateur(final Ligue ligue) {

		return new List<Employe>("Changer l'administrateur", "c",
				() -> new ArrayList<>(ligue.getEmployes()),
				(element) -> new Option("Choisir " + element.getNom() + " " + element.getPrenom(), "c",
						() -> ligue.setAdministrateur(element)));
	}

	private Option supprimer(Ligue ligue) {
		return new Option("Supprimer", "d", () -> {
			ligue.remove();
		});
	}

}
