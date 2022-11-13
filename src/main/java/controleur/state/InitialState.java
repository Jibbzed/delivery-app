package controleur.state;

import controleur.StateController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class InitialState implements State {

    @Override
    public void doubleCliquePlan(StateController stateController) {
        stateController.setCurrentState(stateController.ajoutLivraisonState);
        try {
            stateController.disableMapView();
            stateController.ajouterLivraison();
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

    @Override
    public void cliqueChargerLivraison(StateController stateController) {
        stateController.setCurrentState(stateController.chargementLivraisonState);
    }

    @Override
    public void cliqueAjouterLivraisonATournee(StateController stateController){
        stateController.setCurrentState(stateController.selectionTourneeState);
    }
}
