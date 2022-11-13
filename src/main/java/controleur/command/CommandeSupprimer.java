package controleur.command;

import modele.Livraison;
import modele.Plan;

public class CommandeSupprimer implements Command {
    private Livraison livraison;
    private Plan plan;

    public CommandeSupprimer(Livraison livraison, Plan plan) {
        this.livraison = livraison;
        this.plan = plan;
    }

    @Override
    public void doCommand() {
    }

    @Override
    public void undoCommand() {
    }

}
