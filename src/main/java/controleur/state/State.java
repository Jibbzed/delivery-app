package controleur.state;

import controleur.StateController;
import controleur.command.ListOfCommands;
import modele.Coursier;
import modele.Livraison;
import modele.Plan;

import java.util.ArrayList;

public interface State {
    default void doubleCliquePlan(StateController stateController){}
    default void doubleCliqueLivraison(StateController stateController, ListOfCommands listOfCommands,Livraison livraison, Plan plan){}

    default void valider(StateController stateController){}
    default void annuler(StateController stateController){}

    default void modifierLivraison(StateController stateController, Livraison livraisonAModifier){}

    default void ajouterCoursier(StateController stateController){}

    default ArrayList<Coursier> recupererListeCoursiers(StateController stateController){
        return null;
    }

    default void ajouterCoursier(String nom, String prenom) {}

    default void supprimerCoursier(Coursier coursier) {}

    default void chargerLivraison(StateController stateController){}

    default void cliqueDroit(StateController stateController){}

    default void cliqueSupprimerLivraison(StateController stateController, Livraison LivraisonASupprimer, ListOfCommands listOfCommands){}

    default void cliqueLivraison(StateController stateController){}
    default void setLivraisonAModifier(Livraison livraisonAModifier) {}
    default void cliqueAjouterLivraisonATournee(StateController stateController){}
    default void cliqueChargerLivraison(StateController stateController){}
    default void entryAction(Livraison livraison){}
    default void validerChargerLivraison(Livraison livraisonACharger, StateController stateController) {}
    default void validerAjouterLivraison(Livraison livraisonAAjouter,StateController stateController, ListOfCommands listOfCommands){}
    default void abandonnerLivraison(StateController stateController){}
    default void clique(StateController stateController){};
    default void undo(ListOfCommands listOfCommands){}
    default void sauvegarderLivraison(Livraison livraisonASauvegarder, String xmlPath) {}
    default void setLivraisonOrigine(Livraison livraisonOrigine) {}

    default void setLivraisonArrivee(Livraison livraisonArrivee) {}

    default void setNouvelleLivraison(Livraison nouvelleLivraison) {}
    default void calculerTournees(Plan plan, String idEntrepot){}
}
