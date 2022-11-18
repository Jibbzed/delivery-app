package controleur.state;

import controleur.StateController;
import controleur.command.ListOfCommands;
import modele.Livraison;
import controleur.state.AjoutLivraisonTourneeState3;
import modele.Plan;


public class AjoutLivraisonTourneeState1 implements State{
    protected Livraison livraison;
    @Override
    public void doubleCliqueLivraison(StateController stateController, ListOfCommands listOfCommands, Livraison livraison, Plan plan) {
        System.out.println("Passage dans l'état 1");
        stateController.ajoutLivraisonTourneeState3.setLivraisonOrigine(livraison);
        stateController.setCurrentState(stateController.ajoutLivraisonTourneeState2);
    }


}
