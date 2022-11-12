package controleur.state;

import controleur.StateController;
import javafx.fxml.FXMLLoader;
import modele.Livraison;

public interface State {
    public default void doubleCliquePlan(StateController stateController, FXMLLoader fxmlLoader){}

    public default void valider(StateController stateController){}
    public default void annuler(StateController stateController){}

    public default void modifierLivraison(StateController stateController, Livraison livraisonAModifier){}

    public default void ajouterCoursier(StateController stateController){}

    public default void chargerLivraison(StateController stateController){}

    default void cliqueDroit(StateController stateController){}

    default void cliqueSupprimerLivraison(StateController stateController, Livraison LivraisonASupprimer){}

    default void cliqueLivraison(StateController stateController){}
    default void cliqueModifier(StateController stateController){}


}
