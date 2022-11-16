package controleur.state;

import controleur.StateController;
import controleur.command.CommandeAjouterLivraison;
import controleur.command.ListOfCommands;
import javafx.fxml.FXMLLoader;
import modele.Livraison;

public class AjoutLivraisonState implements State {

    @Override
    public void validerAjouterLivraison(Livraison livraisonAAjouter, StateController stateController, ListOfCommands listOfCommands) {
        listOfCommands.add(new CommandeAjouterLivraison(livraisonAAjouter));
        stateController.enableMapView();
        stateController.setCurrentState(stateController.initialState);
    }

    @Override
    public void abandonnerLivraison(StateController stateController) {
        stateController.setCurrentState(stateController.initialState);
    }
}
