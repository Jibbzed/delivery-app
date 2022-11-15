package controleur.command;

import modele.Livraison;
import modele.Plan;
import service.ServiceLivraison;
import service.impl.ServiceLivraisonMockImpl;

public class CommandeSupprimerLivraison implements Command {
    private Livraison livraison;
    private ServiceLivraison serviceLivraison;

    public CommandeSupprimerLivraison(Livraison livraison) {
        this.livraison = livraison;
        this.serviceLivraison = ServiceLivraisonMockImpl.getInstance();
    }

    @Override
    public void doCommand() {
        serviceLivraison.supprimerLivraison(livraison);
    }

    @Override
    public void undoCommand() {
        serviceLivraison.ajouterLivraison(livraison);
    }

}
