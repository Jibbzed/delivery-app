package controleur.state;

import controleur.PagePrincipaleController;
import controleur.StateController;
import javafx.fxml.FXMLLoader;

public class SelectionnerLivraisonState implements State{
    @Override
    public void doubleCliquePlan(StateController stateController, FXMLLoader fxmlLoader) {

    }

    @Override
    public void valider(StateController stateController) {

    }

    @Override
    public void modifierLivraison(StateController stateController) {

    }

    @Override
    public void ajouterCoursier(StateController stateController) {

    }

    @Override
    public void chargerLivraison(StateController stateController) {

    }

    @Override
    public void cliqueDroit(StateController stateController) {

    }

    @Override
    public void cliqueSupprimerLivraison(StateController stateController, FXMLLoader fxmlLoader) {
        PagePrincipaleController controller = fxmlLoader.getController();
        controller.supprimerLivraison();
        stateController.setCurrentState(stateController.initialState);

    }

    @Override
    public void cliqueModifier(StateController stateController) {

    }
}
