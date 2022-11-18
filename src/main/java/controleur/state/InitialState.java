package controleur.state;

import controleur.StateController;
import controleur.command.ListOfCommands;
import modele.Tournee;
import service.ServiceCoursier;
import service.ServiceTournee;
import service.impl.ServiceLivraisonMockImpl;

import java.io.IOException;

public class InitialState implements State {

    @Override
    public void doubleCliquePlan(StateController stateController) {
        stateController.setCurrentState(stateController.ajoutLivraisonState);
        try {
            stateController.afficherAjoutLivraison();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cliqueBoutonChargerLivraison(StateController stateController) {
        stateController.setCurrentState(stateController.ajoutLivraisonState);
    }

    @Override
    public void cliqueLivraison(StateController stateController) {
        State.super.cliqueLivraison(stateController);
        stateController.enableLivraisonDisableableComponents();
        stateController.setCurrentState(stateController.selectionnerLivraisonState);
    }

    @Override
    public void cliqueChargerLivraison(StateController stateController) {
        stateController.setCurrentState(stateController.chargementLivraisonState);
    }

    @Override
    public void cliqueAjouterLivraisonATournee(StateController stateController){
        stateController.setCurrentState(stateController.selectionTourneeState);
    }

    @Override
    public void undo(ListOfCommands listOfCommands) {
        listOfCommands.undo();
    }

    @Override
    public void resetModels(){
        for(Tournee t : ServiceTournee.getInstance().getTournees()){
            t.getLivraisons().forEach(livraison -> ServiceLivraisonMockImpl.getInstance().supprimerLivraison(livraison));
        }
            ServiceTournee.getInstance().getTournees().clear();
            ServiceLivraisonMockImpl.getInstance().afficherToutesLivraisons().forEach(livraison -> ServiceLivraisonMockImpl.getInstance().supprimerLivraison(livraison));
            ServiceCoursier.getInstance().setNonPlanifieTous();
        new StateController();
    }
}
