package controleur.state;

import controleur.AjoutLivraisonController;
import controleur.StateController;
import javafx.fxml.FXMLLoader;

public class AjoutLivraisonState implements State {


    @Override
    public void doubleCliquePlan(StateController stateController, FXMLLoader fxmlLoader) {

    }

    @Override
    public void valider(StateController stateController) {
        stateController.setCurrentState(stateController.initialState);
    }

    @Override
    public void modifierLivraison(StateController stateController) {

    }

    @Override
    public void ajouterCoursier(StateController stateController) {

    }

    @Override
    public void chargerLivraison(StateController stateController) {

    }
}
