package controleur;

import controleur.command.ListOfCommands;
import controleur.state.*;


import javafx.stage.Stage;
import modele.Intersection;
import modele.Livraison;
import modele.Parser;
import modele.Plan;
import modele.exception.MauvaisFormatXmlException;
import service.ServiceLivraison;
import service.impl.ServiceLivraisonMockImpl;
import vue.Fenetre.*;

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

    public final State gestionCoursierState = new GestionCoursierState();
    private Intersection intersectionSelectionne;
    private static String xmlPathPlan;

    public static String getXmlPathPlan() {
        return xmlPathPlan;
    }

    public static void setXmlPathPlan(String xmlPathPlan) {
        StateController.xmlPathPlan = xmlPathPlan;
    }

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

    public void afficherMap(String title, Plan plan){
        FenetrePrincipale fenetrePrincipale = new FenetrePrincipale(this, title, plan);
        fenetrePrincipale.show();//showAndWait();
        mainStage.close();
        mainStage=fenetrePrincipale;
    }
    public void chargerLivraisonsSauvegardees(String xmlLivraisonFile){
        Parser parser = new Parser();
        ServiceLivraison serviceLivraisonMock = ServiceLivraisonMockImpl.getInstance();
        serviceLivraisonMock.creerListeLivraisonsSauvegardees( parser.chargerLivraisonsSauvegardees(xmlPathPlan, xmlLivraisonFile) );
    }

    public void afficherAjoutLivraison() throws IOException {
        popupStage = new FenetreSaisieLivraison(this, this.intersectionSelectionne, (FenetrePrincipale) mainStage);
        popupStage.showAndWait();
    }

    public void afficherChoixCheminFDR() throws IOException {
        popupStage = new FenetreChoixDossier(this, (FenetrePrincipale) mainStage);
        popupStage.showAndWait();
    }
    public void chargerLivraison() throws IOException {
        popupStage = new FenetreChargementLivraison(this, (FenetrePrincipale) mainStage);
        popupStage.showAndWait();
    }

    public void ajouterLivraison(Livraison l){ currentState.valider(l,this, listOfCommands); }

    public void disableLivraisonDisableableComponents() {
        ((FenetrePrincipale)this.mainStage).getFenetreHandler().disableLivraisonDisableableComponents();
    }

    public void enableLivraisonDisableableComponents() {
       ((FenetrePrincipale)this.mainStage).getFenetreHandler().enableLivraisonDisableableComponents();
    }
    public void supprimerLivraison(Livraison livraisonASupprimer){
        currentState.cliqueSupprimerLivraison(this, livraisonASupprimer, listOfCommands);
        disableLivraisonDisableableComponents();
    }

    public void cliqueModifierLivraison(Livraison livraisonAModifier){
        currentState.modifierLivraison(this, livraisonAModifier);
        this.modifierLivraison(livraisonAModifier);
    }

    public void abandonAjoutLivraison(){
        currentState.annuler(this);
    }

    public void modifierLivraison(Livraison livraisonAModifier){
         popupStage = new FenetreSaisieLivraison(this, livraisonAModifier, (FenetrePrincipale) mainStage);
         popupStage.showAndWait();
        currentState.valider(livraisonAModifier,this, listOfCommands);
    }

    public void doubleCliquePlan(Intersection intersectionSelectionne){
        this.setIntersectionSelectionne(intersectionSelectionne);
        currentState.doubleCliquePlan(this);
    }
    public void undo(){ listOfCommands.undo(); }
    public void redo(){listOfCommands.redo();}

    public void cliquerChargerLivraison(){ currentState.cliqueChargerLivraison(this);}

    public void chargerLivraison(Livraison livraisonACharger){
        ServiceLivraison serviceLivraison = ServiceLivraisonMockImpl.getInstance();
        serviceLivraison.ajouterLivraison(livraisonACharger);

    }

    public void cliquerAjouterLivraisonATournee(){  }

    public Plan chargerPlan(String xmlPath) throws MauvaisFormatXmlException, IOException{
        Parser parser = new Parser();
        return parser.lirePlan(xmlPath);
    }
    public void sauvegarderLivraison(Livraison livraison){ currentState.sauvegarderLivraison(livraison, xmlPathPlan);
        System.out.println(xmlPathPlan); }

    public void allerGestionnaireCoursier() throws IOException {
        currentState = this.gestionCoursierState;
        popupStage = new FenetreGestionnaireCoursier(this);
        popupStage.showAndWait();
        currentState = this.initialState;
    }

    public void cliqueBoutonChargerLivraison() throws IOException {
        currentState.cliqueBoutonChargerLivraison(this);
        chargerLivraison();
    }

    public void rechargerApp(){
        mainStage.close();
        currentState.resetModels();
    }
}
