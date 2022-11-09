package controleur.state;

import controleur.AjoutLivraisonController;
import controleur.StateController;
import javafx.fxml.FXMLLoader;

public interface State {
    public void doubleCliquePlan(StateController stateController, FXMLLoader fxmlLoader);

    public void valider(StateController stateController);

    public void modifierLivraison(StateController stateController);

    public void ajouterCoursier(StateController stateController);

    public void chargerLivraison(StateController stateController);

    void cliqueDroit(StateController stateController);
    
    void cliqueSupprimerLivraison(StateController stateController, FXMLLoader fxmlLoader);

    default void cliqueLivraison(StateController stateController){}
    void cliqueModifier(StateController stateController);
}
