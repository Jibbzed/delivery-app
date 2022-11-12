package controleur.state;

import controleur.StateController;
import javafx.fxml.FXMLLoader;
import modele.Livraison;

import java.io.IOException;

public class ModificationLivraisonState implements State{
    @Override
    public void valider(StateController stateController) {
        State.super.valider(stateController);
        stateController.setCurrentState(stateController.initialState);
    }

    @Override
    public void annuler(StateController stateController) {
        State.super.annuler(stateController);
        stateController.setCurrentState(stateController.initialState);
    }

    @Override
    public void doubleCliquePlan(StateController stateController, FXMLLoader fxmlLoader) {
        State.super.doubleCliquePlan(stateController, fxmlLoader);
        try {
            stateController.ajouterLivraison(fxmlLoader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}