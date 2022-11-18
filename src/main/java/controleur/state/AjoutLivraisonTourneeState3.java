package controleur.state;

import com.sothawo.mapjfx.Coordinate;
import controleur.StateController;
import controleur.command.CommandeAjouterLivraisonTournee;
import controleur.command.ListOfCommands;
import modele.Dijkstra;
import modele.Livraison;
import modele.Plan;
import modele.Troncon;
import vue.FenetreHandler.FenetrePrincipaleHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.stream.Collectors;

public class AjoutLivraisonTourneeState3 implements State {
    protected Livraison livraisonOrigine;
    protected Livraison livraisonArrivee;
    protected Livraison nouvelleLivraison;

    public void setLivraisonOrigine(Livraison livraisonOrigine) {
        this.livraisonOrigine = livraisonOrigine;
    }
    @Override
    public void setLivraisonArrivee(Livraison livraisonArrivee) {
        this.livraisonArrivee = livraisonArrivee;
    }
    @Override
    public void setNouvelleLivraison(Livraison nouvelleLivraison) {
        this.nouvelleLivraison = nouvelleLivraison;
    }

    @Override
    public void doubleCliqueLivraison(StateController stateController, ListOfCommands listOfCommands, Livraison livraison, Plan plan) {
        System.out.println("passage dans l'état 3");
        nouvelleLivraison = livraison;
        listOfCommands.add(new CommandeAjouterLivraisonTournee(livraisonOrigine,livraisonArrivee,nouvelleLivraison, plan));
        stateController.setCurrentState(stateController.initialState);
    }
}
