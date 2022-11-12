package controleur.state;

import controleur.StateController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class InitialState implements State {

    @Override
    public void doubleCliquePlan(StateController stateController, FXMLLoader fxmlLoader) {
        stateController.setCurrentState(stateController.ajoutLivraisonState);
        try {
            stateController.ajouterLivraison(fxmlLoader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cliqueLivraison(StateController stateController) {
        State.super.cliqueLivraison(stateController);
        stateController.setCurrentState(stateController.selectionnerLivraisonState);
    }
}
