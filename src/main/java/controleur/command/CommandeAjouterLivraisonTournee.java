package controleur.command;

import com.sothawo.mapjfx.Coordinate;
import modele.Dijkstra;
import modele.Livraison;
import modele.Plan;
import modele.Troncon;
import service.ServiceTournee;
import vue.FenetreHandler.FenetrePrincipaleHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandeAjouterLivraisonTournee implements Command{
    protected Livraison livraisonOrigine;
    protected Livraison livraisonArrivee;
    protected Livraison nouvelleLivraison;
    protected Plan plan;

    public CommandeAjouterLivraisonTournee(Livraison livraisonOrigine, Livraison livraisonArrivee, Livraison nouvelleLivraison, Plan plan) {
        this.livraisonOrigine = livraisonOrigine;
        this.livraisonArrivee = livraisonArrivee;
        this.nouvelleLivraison = nouvelleLivraison;
        this.plan = plan;
    }

    @Override
    public void doCommand() {
        nouvelleLivraison.setOrigineLivraison(livraisonOrigine.getDestinationLivraison());
        livraisonArrivee.setOrigineLivraison(nouvelleLivraison.getDestinationLivraison());

        String idLivraisonOrigine = livraisonOrigine.getDestinationLivraison().getId();
        String idNouvelleLivraison = nouvelleLivraison.getDestinationLivraison().getId();
        Map<String, Dijkstra> resultatDijkstra =
                plan.plusCourtChemin(idLivraisonOrigine, Collections.singletonList(idNouvelleLivraison));

        nouvelleLivraison.setParcoursLivraison(resultatDijkstra.get(idNouvelleLivraison).getChemin());

        String idLivraisonArrivee = livraisonArrivee.getDestinationLivraison().getId();
        Map<String, Dijkstra> resultatDijkstra2 =
                plan.plusCourtChemin(idNouvelleLivraison, Collections.singletonList(idLivraisonArrivee));
        livraisonArrivee.setParcoursLivraison(resultatDijkstra2.get(idLivraisonArrivee).getChemin());

        ServiceTournee serviceTournee = ServiceTournee.getInstance();
        serviceTournee.getTournees().get(livraisonOrigine.getCoursierLivraison()).ajouterLivraison(nouvelleLivraison);
    }

    @Override
    public void undoCommand() {

    }
}
