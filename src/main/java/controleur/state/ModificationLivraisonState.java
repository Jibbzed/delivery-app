package controleur.state;

import controleur.StateController;
import javafx.fxml.FXMLLoader;
import modele.Livraison;

import java.io.IOException;

public class ModificationLivraisonState implements State{
    private Livraison livraisonAModifier;
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
        stateController.modifierLivraison(livraisonAModifier, fxmlLoader);
    }

    public Livraison getLivraisonAModifier() {
        return livraisonAModifier;
    }

    public void setLivraisonAModifier(Livraison livraisonAModifier) {
        this.livraisonAModifier = livraisonAModifier;
    }
}