package controleur.state;

import controleur.StateController;
import modele.Livraison;

public class ChargementLivraisonState implements State{
    @Override
    public void validerChargerLivraison(Livraison livraisonACharger, StateController stateController) {
        stateController.chargerLivraison(livraisonACharger);
    }
}
