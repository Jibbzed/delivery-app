package controleur.state;

import controleur.StateController;
import javafx.fxml.FXMLLoader;

public class AffichageLivraisonState implements State {
    @Override
    public void doubleCliquePlan(StateController stateController, FXMLLoader fxmlLoader) {

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

    public void cliqueDroit(StateController stateController){
        stateController.setCurrentState(stateController.initialState);
    }

    public void cliqueSupprimerLivraison(StateController stateController){
        stateController.setCurrentState(stateController.initialState);
    }

    public void cliqueModifier(StateController stateController){
        // set state to modification state
    }
}
