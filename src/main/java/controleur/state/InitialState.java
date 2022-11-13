package controleur.state;

import controleur.StateController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class InitialState implements State {


    @Override
    public void doubleCliquePlan(StateController stateController, FXMLLoader fxmlLoader) {
        stateController.setCurrentState(stateController.ajoutLivraisonState);
        try {
            stateController.disableMapView();
            stateController.ajouterLivraison(fxmlLoader);

            // TODO: stateController disable map View
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void cliqueLivraison(StateController stateController) {
        State.super.cliqueLivraison(stateController);
        stateController.enableLivraisonDisableableComponenets();
        stateController.setCurrentState(stateController.selectionnerLivraisonState);

    }
}
