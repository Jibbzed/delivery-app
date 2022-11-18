package controleur;

import controleur.command.ListOfCommands;
import controleur.state.*;


import javafx.stage.Stage;
import modele.Coursier;
import modele.Intersection;
import modele.Livraison;
import service.ServiceCoursier;
import modele.Parser;
import modele.Plan;
import modele.exception.MauvaisFormatXmlException;
import service.ServiceLivraison;
import service.impl.ServiceLivraisonMockImpl;
import vue.Fenetre.*;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

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

    public final State gestionCoursierState = new GestionCoursierState();
    public final State ajoutLivraisonTourneeState1 = new AjoutLivraisonTourneeState1();
    public final State ajoutLivraisonTourneeState2 = new AjoutLivraisonTourneeState2();
    public final State ajoutLivraisonTourneeState3 = new AjoutLivraisonTourneeState3();
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
   /* public void generateControllerPageAccueil(FenetreAccueilHandler controllerPageAccueil) {
        this.fenetreAccueilController = controllerPageAccueil;
    }*/
    /*public void generateAjoutLivraisonController(FenetreSaisieLivraisonHandler ajoutLivraisonController){
        this.fenetreSaisieLivraisonController = ajoutLivraisonController;
    }*/
    //TODO: save the arguments in the FenetrePrincipaleHandler instatnce.
    public void afficherMap(String title, Plan plan){
        FenetrePrincipale fenetrePrincipale = new FenetrePrincipale(this, title, plan);
        fenetrePrincipale.show();//showAndWait();
        mainStage.close();
        mainStage=fenetrePrincipale;

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

    public void abandonAjoutLivraison(){
        currentState.abandonnerLivraison(this);
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
    public void doubleCliqueLivraison(Livraison livraison, Plan plan){
        currentState.doubleCliqueLivraison(this,listOfCommands,livraison, plan);
    }
    public void undo(){ currentState.undo(listOfCommands); }

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

    public void calculerTournees(Plan plan, String idEntrepot){
        currentState.calculerTournees(plan, idEntrepot);
    }

}
