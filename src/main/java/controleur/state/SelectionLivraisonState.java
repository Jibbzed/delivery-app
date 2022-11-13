package controleur.state;

import controleur.command.CommandeSupprimerLivraison;
import controleur.command.ListOfCommands;
import modele.Livraison;
import service.impl.ServiceLivraisonMockImpl;
import controleur.StateController;

public class SelectionLivraisonState implements State{

    @Override
    public void modifierLivraison(StateController stateController, Livraison livraisonAModifier) {
        stateController.modificationLivraisonState.setLivraisonAModifier(livraisonAModifier);
        stateController.setCurrentState(stateController.modificationLivraisonState);
        // TODO afficher le point de livraison actuel
        // TODO indiquer a l'utilisateur qu'il doit double cliquer sur le point ou doit aller la livraison
        //      ou recliquer sur le point affichée si la destination ne change pas
    }
    @Override
    public void cliqueSupprimerLivraison(StateController stateController, Livraison livraisonASupprimer, ListOfCommands listOfCommands) {
        listOfCommands.add(new CommandeSupprimerLivraison(livraisonASupprimer));
        stateController.setCurrentState(stateController.initialState);
    }
    @Override
    public void clique(StateController stateController) {
        stateController.setCurrentState(stateController.initialState);
        stateController.disableLivraisonDisableableComponenets();
    }
}
