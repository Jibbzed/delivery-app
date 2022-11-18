package controleur.state;

import controleur.StateController;
import controleur.command.CommandeAjouterLivraisonTournee;
import controleur.command.ListOfCommands;
import modele.Livraison;
import modele.Plan;

public class AjoutLivraisonTourneeState2 implements State{
    protected Livraison livraison;
    @Override
    public void doubleCliqueLivraison(StateController stateController, ListOfCommands listOfCommands, Livraison livraison, Plan plan) {
        System.out.println("Passage dans l'état 2");
        stateController.ajoutLivraisonTourneeState3.setLivraisonArrivee(livraison);
        stateController.setCurrentState(stateController.ajoutLivraisonTourneeState3);
    }
}
