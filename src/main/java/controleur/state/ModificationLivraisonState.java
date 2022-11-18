package controleur.state;

import controleur.StateController;
import controleur.command.CommandeAjouterLivraison;
import controleur.command.ListOfCommands;
import javafx.fxml.FXMLLoader;
import modele.Livraison;

import java.io.IOException;

public class ModificationLivraisonState implements State{
    private Livraison livraisonAModifier;
    @Override
    public void valider(Livraison livraisonAAjouter, StateController stateController, ListOfCommands listOfCommands) {
        State.super.valider(stateController);
        stateController.setCurrentState(stateController.initialState);
    }

    @Override
    public void annuler(StateController stateController) {
        State.super.annuler(stateController);
        stateController.setCurrentState(stateController.initialState);
    }

    @Override
    public void doubleCliquePlan(StateController stateController) {
        State.super.doubleCliquePlan(stateController);
        stateController.modifierLivraison(livraisonAModifier);
    }

    public Livraison getLivraisonAModifier() {
        return livraisonAModifier;
    }

    public void setLivraisonAModifier(Livraison livraisonAModifier) {
        this.livraisonAModifier = livraisonAModifier;
    }
}