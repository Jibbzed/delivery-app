package controleur.state;

import controleur.Controller;
import controleur.StateController;
import javafx.fxml.FXMLLoader;

public class SelectionnerLivraisonState implements State{
    @Override
    public void modifierLivraison(StateController stateController) {
        stateController.setCurrentState(stateController.modificationLivraisonState);
    }

    @Override
    public void cliqueDroit(StateController stateController) {

    }

    @Override
    public void cliqueSupprimerLivraison(StateController stateController, FXMLLoader fxmlLoader) {
        Controller controller = fxmlLoader.getController();
        controller.supprimerLivraison();
        stateController.setCurrentState(stateController.initialState);

    }

    @Override
    public void cliqueModifier(StateController stateController) {

    }
}
