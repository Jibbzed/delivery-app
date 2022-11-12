package controleur.state;

import modele.Livraison;
import service.impl.ServiceLivraisonMockImpl;
import vue.FenetreController.FenetrePrincipaleController;
import controleur.StateController;
import javafx.fxml.FXMLLoader;

public class SelectionnerLivraisonState implements State{

    @Override
    public void modifierLivraison(StateController stateController, Livraison livraisonAModifier) {
        stateController.setCurrentState(stateController.modificationLivraisonState);
        // TODO afficher le point de livraison actuel
        // TODO indiquer a l'utilisateur qu'il doit double cliquer sur le point ou doit aller la livraison
        //      ou recliquer sur le point affichée si la destination ne change pas
    }
    @Override
    public void cliqueSupprimerLivraison(StateController stateController, Livraison livraisonASupprimer) {
        ServiceLivraisonMockImpl.getInstance().supprimerLivraison(livraisonASupprimer);
        stateController.setCurrentState(stateController.initialState);

    }
}
