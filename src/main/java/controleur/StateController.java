package controleur;

import controleur.command.ListOfCommands;
import controleur.state.*;

import javafx.collections.FXCollections;
import javafx.stage.Stage;
import modele.Coursier;
import modele.Intersection;
import modele.Livraison;
import service.ServiceCoursier;
import service.ServiceLivraison;
import service.impl.ServiceLivraisonMockImpl;
import vue.Fenetre.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public final State gestionCoursierState = new GestionCoursierState();
    private Intersection intersectionSelectionne;

    public void setCurrentState(State state) {
        currentState = state;
    }
    public State getCurrentState() {
        return currentState;
    }

    private ListOfCommands listOfCommands;
    public void setIntersectionSelectionne(Intersection intersectionSelectionne) {
        this.intersectionSelectionne = intersectionSelectionne;
    }

    public Intersection getIntersectionSelectionne() {
        return intersectionSelectionne;
    }

    public StateController() {
        this.currentState = initialState;
        listOfCommands = new ListOfCommands();
        mainStage = new FenetreAccueil(this);
        mainStage.show();
    }
   /* public void generateControllerPageAccueil(FenetreAccueilHandler controllerPageAccueil) {
        this.fenetreAccueilController = controllerPageAccueil;
    }*/
    /*public void generateAjoutLivraisonController(FenetreSaisieLivraisonHandler ajoutLivraisonController){
        this.fenetreSaisieLivraisonController = ajoutLivraisonController;
    }*/
    //TODO: save the arguments in the FenetrePrincipaleHandler instatnce.
    public void afficherMap(String title, String xmlMapPath){
        mainStage.close();
        mainStage = new FenetrePrincipale(this, title, xmlMapPath);
        mainStage.showAndWait();
    }


    public void afficherAjoutLivraison() throws IOException {
        popupStage = new FenetreSaisieLivraison(this, this.intersectionSelectionne, (FenetrePrincipale) mainStage);
        popupStage.showAndWait();
    }

    public void ajouterLivraison(Livraison l){ currentState.validerAjouterLivraison(l,this, listOfCommands); }

    // TODO: make an interface for all our custom made stages.
    public void disableMapView(){
        //((FenetrePrincipale)this.mainStage).getController().disableView();
    }

    public void enableMapView() {
        //((FenetrePrincipale)this.mainStage).getController().enableView();
    }

    public void disableLivraisonDisableableComponents() {
        ((FenetrePrincipale)this.mainStage).getFenetreHandler().disableLivraisonDisableableComponents();
    }

    public void enableLivraisonDisableableComponents() {
       ((FenetrePrincipale)this.mainStage).getFenetreHandler().enableLivraisonDisableableComponents();
    }
    public void supprimerLivraison(Livraison livraisonASupprimer){ currentState.cliqueSupprimerLivraison(this, livraisonASupprimer, listOfCommands);}

    public void cliqueModifierLivraison(Livraison livraisonAModifier){
        currentState.modifierLivraison(this, livraisonAModifier);
        this.modifierLivraison(livraisonAModifier);
    }

    public void modifierLivraison(Livraison livraisonAModifier){
         popupStage = new FenetreSaisieLivraison(this, livraisonAModifier, (FenetrePrincipale) mainStage);
         popupStage.showAndWait();
        // TODO change the attribute to an optional one.
    }

    public void doubleCliquePlan(Intersection intersectionSelectionne){
        this.setIntersectionSelectionne(intersectionSelectionne);
        currentState.doubleCliquePlan(this);
    }
    public void undo(){ currentState.undo(listOfCommands); }

    public void cliquerChargerLivraison(){ currentState.cliqueChargerLivraison(this);}

    public void chargerLivraison(Livraison livraisonACharger){
        ServiceLivraison serviceLivraison = ServiceLivraisonMockImpl.getInstance();
        serviceLivraison.ajouterLivraison(livraisonACharger);

    }
    public void cliquerAjouterLivraisonATournee(){  }

    public ArrayList<Coursier> recupererListeCoursiers(){
        return ServiceCoursier.getInstance().getListeCoursiers();
    }

    public void allerGestionnaireCoursier() throws IOException {
        popupStage = new FenetreGestionnaireCoursier(this);
        popupStage.showAndWait();
    }

    public void ajouterCoursier(String nom, String prenom) {
        Coursier coursier = new Coursier(nom, prenom);
        ServiceCoursier.getInstance().ajouterCoursier(coursier);
    }

    public void supprimerCoursier(Coursier coursier) {
        ServiceCoursier.getInstance().retirerCoursier(coursier);
    }

    public int nbLivraisonAffecteCoursier(Coursier coursier) {
        int count = 0;
        Set<Livraison> listeLivraison = ServiceLivraisonMockImpl.getInstance().afficherToutesLivraisons();
        for(Livraison livraison : listeLivraison){
            if(livraison.getCoursierLivraison().get().equals(coursier)){
                count++;
            }
        }
        return count;
    }

}
