package controleur.state;

import controleur.AjoutLivraisonController;
import controleur.StateController;
import javafx.fxml.FXMLLoader;

public class AjoutLivraisonState implements State {
    @Override
    public void valider(StateController stateController) {
        stateController.enableMapView();
        stateController.setCurrentState(stateController.initialState);
    }

}
