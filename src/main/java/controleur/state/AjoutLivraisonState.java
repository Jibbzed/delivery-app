package controleur.state;

import controleur.StateController;
import controleur.command.CommandeAjouterLivraison;
import controleur.command.ListOfCommands;
import javafx.fxml.FXMLLoader;
import modele.Livraison;
import modele.Parser;

public class AjoutLivraisonState implements State {
    @Override
    public void sauvegarderLivraison(Livraison livraisonASauvegarder, String xmlPath) {
        Parser parser = new Parser();
        try{
            parser.sauvegarderLivraison(livraisonASauvegarder, xmlPath);
        }catch (Exception e){
            e.getStackTrace();
        }

    }

    @Override
    public void cliqueBoutonChargerLivraison(StateController stateController) {
        stateController.setCurrentState(stateController.initialState);
    }
    @Override
    public void valider(Livraison livraisonAAjouter, StateController stateController, ListOfCommands listOfCommands) {
        listOfCommands.add(new CommandeAjouterLivraison(livraisonAAjouter));
        stateController.enableMapView();
        stateController.setCurrentState(stateController.initialState);
    }

    @Override
    public void abandonnerLivraison(StateController stateController) {
        stateController.setCurrentState(stateController.initialState);
    }
}