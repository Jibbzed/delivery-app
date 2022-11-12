package controleur.state;

import controleur.StateController;
import javafx.fxml.FXMLLoader;

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
}
