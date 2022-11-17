package controleur.state;

import controleur.StateController;
import controleur.command.ListOfCommands;
import javafx.fxml.FXMLLoader;
import modele.Coursier;
import modele.Livraison;
import service.ServiceCoursier;

import java.util.ArrayList;
import java.util.List;

public interface State {
    default void doubleCliquePlan(StateController stateController){}

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

    default void validerChargerLivraison(Livraison livraisonACharger, StateController stateController) {}
    default void validerAjouterLivraison(Livraison livraisonAAjouter,StateController stateController, ListOfCommands listOfCommands){}
    default void clique(StateController stateController){};
    default void undo(ListOfCommands listOfCommands){}
}
