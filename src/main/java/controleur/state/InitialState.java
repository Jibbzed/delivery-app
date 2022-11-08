package controleur.state;

import controleur.AjoutLivraisonController;
import controleur.StateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    public void valider(StateController stateController) {

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
