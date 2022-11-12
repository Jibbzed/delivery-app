package controleur.state;

import controleur.StateController;
import javafx.fxml.FXMLLoader;
import modele.Livraison;

public interface State {
    default void doubleCliquePlan(StateController stateController, FXMLLoader fxmlLoader){}

    default void valider(StateController stateController){}
    default void annuler(StateController stateController){}

    default void modifierLivraison(StateController stateController, Livraison livraisonAModifier){}

    default void ajouterCoursier(StateController stateController){}

    default void chargerLivraison(StateController stateController){}

    default void cliqueDroit(StateController stateController){}

    default void cliqueSupprimerLivraison(StateController stateController, Livraison LivraisonASupprimer){}

    default void cliqueLivraison(StateController stateController){}
    default void setLivraisonAModifier(Livraison livraisonAModifier) {}
}
