package controleur.state;

import controleur.StateController;
import javafx.fxml.FXMLLoader;

public class AjoutLivraisonState implements State {

    @Override
    public void valider(StateController stateController) {
        stateController.setCurrentState(stateController.initialState);
    }
}
