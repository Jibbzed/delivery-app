package controleur.command;

import modele.Livraison;
import modele.Plan;

public class CommandeModifier implements Command {
    private Livraison livraison;
    private Plan plan;

    public CommandeModifier(Livraison livraison, Plan plan) {
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
