package controleur.command;

import modele.Livraison;
import modele.Plan;
import service.ServiceLivraison;
import service.impl.ServiceLivraisonMockImpl;

public class CommandeAjouterLivraison implements Command {
    private Livraison livraison;
    private ServiceLivraison serviceLivraison;

    public CommandeAjouterLivraison(Livraison livraison) {
        this.livraison = livraison;
        this.serviceLivraison = ServiceLivraisonMockImpl.getInstance();
    }

    @Override
    public void doCommand() {
        serviceLivraison.ajouterLivraison(livraison);
    }

    @Override
    public void undoCommand() {
        // TODO refresh la liste de livraison dans la fenetre principale
        serviceLivraison.supprimerLivraison(livraison);
    }

}
