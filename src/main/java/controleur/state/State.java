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
}
