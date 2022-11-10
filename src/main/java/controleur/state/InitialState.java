package controleur.state;

import controleur.StateController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class InitialState implements State {

    @Override
    public void doubleCliquePlan(StateController stateController, FXMLLoader fxmlLoader) {
        stateController.setCurrentState(stateController.ajoutLivraisonState);
        try {
            stateController.ajouterLivraison();
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

    @Override
    public void cliqueDroit(StateController stateController) {

    }

    @Override
    public void cliqueSupprimerLivraison(StateController stateController, FXMLLoader fxmlLoader) {

    }

    @Override
    public void cliqueModifier(StateController stateController) {

    }

    @Override
    public void cliqueLivraison(StateController stateController) {
        State.super.cliqueLivraison(stateController);
        stateController.setCurrentState(stateController.SelectionnerLivraisonState);
    }
}
