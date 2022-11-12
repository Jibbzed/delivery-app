package controleur.state;

import controleur.AjoutLivraisonController;
import controleur.StateController;
import javafx.fxml.FXMLLoader;

public interface State {
    public default void doubleCliquePlan(StateController stateController, FXMLLoader fxmlLoader){}

    public default void valider(StateController stateController){}
    public default void annuler(StateController stateController){}

    public default void modifierLivraison(StateController stateController){}

    public default void ajouterCoursier(StateController stateController){}

    public default void chargerLivraison(StateController stateController){}

    default void cliqueDroit(StateController stateController){}

    default void cliqueSupprimerLivraison(StateController stateController, FXMLLoader fxmlLoader){}

    default void cliqueLivraison(StateController stateController){}
    default void cliqueModifier(StateController stateController){}
}
