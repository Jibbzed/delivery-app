package controleur;

import controleur.state.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modele.Intersection;
import modele.Livraison;
import service.ServiceLivraison;
import service.impl.ServiceLivraisonMockImpl;
import vue.Fenetre.FenetreAccueil;
import vue.FenetreController.FenetreSaisieLivraisonController;
import vue.FenetreController.FenetreAccueilController;
import vue.Fenetre.FenetrePrincipale;
import vue.Fenetre.FenetreSaisieLivraison;
import vue.FenetreController.FenetrePrincipaleController;

import java.io.IOException;

public class StateController {
    private State currentState;
    private Stage mainStage;

    private Stage popupStage;

    /**  states **/
    public final State initialState = new InitialState();
    public final State ajoutLivraisonState= new AjoutLivraisonState();
    public final State modificationLivraisonState = new ModificationLivraisonState();
    public final State selectionnerLivraisonState = new SelectionLivraisonState();
    public final State chargementLivraisonState = new ChargementLivraisonState();
    public final State selectionTourneeState = new SelectionTourneeState();
    private Intersection intersectionSelectionne;

    public void setCurrentState(State state) {
        currentState = state;
    }
    public State getCurrentState() {
        return currentState;
    }

    public void setIntersectionSelectionne(Intersection intersectionSelectionne) {
        this.intersectionSelectionne = intersectionSelectionne;
    }

    public Intersection getIntersectionSelectionne() {
        return intersectionSelectionne;
    }

    public StateController() {
        this.currentState = initialState;
        mainStage = new FenetreAccueil(this);
        mainStage.show();
    }
   /* public void generateControllerPageAccueil(FenetreAccueilController controllerPageAccueil) {
        this.fenetreAccueilController = controllerPageAccueil;
    }*/
    /*public void generateAjoutLivraisonController(FenetreSaisieLivraisonController ajoutLivraisonController){
        this.fenetreSaisieLivraisonController = ajoutLivraisonController;
    }*/
    //TODO: save the arguments in the FenetrePrincipaleController instatnce.
    public void afficherMap(String title, String xmlMapPath){

       // FenetrePrincipale fenetrePrincipale = new FenetrePrincipale(this, title, xmlMapPath);
        mainStage.close();
        mainStage = new FenetrePrincipale(this, title, xmlMapPath);
        mainStage.showAndWait();
    }


    public void ajouterLivraison() throws IOException {
        popupStage = new FenetreSaisieLivraison(this, this.intersectionSelectionne, (FenetrePrincipale) mainStage);
        popupStage.showAndWait();
        // TODO change the attribute to an optional one.
    }

    public void supprimerLivraison(Livraison livraisonASupprimer){ currentState.cliqueSupprimerLivraison(this, livraisonASupprimer);}

    public void cliqueModifierLivraison(Livraison livraisonAModifier){ currentState.modifierLivraison(this, livraisonAModifier);}

    public void modifierLivraison(Livraison livraisonAModifier){
         popupStage = new FenetreSaisieLivraison(this, this.intersectionSelectionne, (FenetrePrincipale) mainStage);
         popupStage.showAndWait();
        // TODO change the attribute to an optional one.
    }

    public void doubleCliquePlan(Intersection intersectionSelectionne, FXMLLoader fxmlLoader){
        this.setIntersectionSelectionne(intersectionSelectionne);
        currentState.doubleCliquePlan(this);
    }

    public void cliquerChargerLivraison(){ currentState.cliqueChargerLivraison(this);}

    public void chargerLivraison(Livraison livraisonACharger){
        //fenetrePrincipaleController.chargerLivraison(livraisonACharger);
        ServiceLivraison serviceLivraison = ServiceLivraisonMockImpl.getInstance();
        serviceLivraison.ajouterLivraison(livraisonACharger);
    }
    public void cliquerAjouterLivraisonATournee(){  }
}
