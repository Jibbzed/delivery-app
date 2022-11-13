package controleur;

import com.sothawo.mapjfx.Projection;
import controleur.state.*;
import javafx.fxml.FXMLLoader;
import modele.Intersection;
import modele.Livraison;
import modele.exception.MauvaisFormatXmlException;
import vue.FenetreController.FenetreSaisieLivraisonController;
import vue.FenetreController.FenetreAccueilController;
import vue.Fenetre.FenetrePrincipale;
import vue.Fenetre.FenetreSaisieLivraison;
import vue.FenetreController.FenetrePrincipaleController;

import java.io.IOException;

public class StateController {
    private State currentState;
    private FenetrePrincipaleController fenetrePrincipaleController;
    private FenetreAccueilController fenetreAccueilController;
    private FenetreSaisieLivraisonController fenetreSaisieLivraisonController;
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
    }
    public void generateControllerPageAccueil(FenetreAccueilController controllerPageAccueil) {
        this.fenetreAccueilController = controllerPageAccueil;
    }
    public void generateAjoutLivraisonController(FenetreSaisieLivraisonController ajoutLivraisonController){
        this.fenetreSaisieLivraisonController = ajoutLivraisonController;
    }
    //TODO: save the arguments in the FenetrePrincipaleController instatnce.
    public void afficherMap(String title, String xmlMapPath) throws IOException {

        FenetrePrincipale pagePrincipale = new FenetrePrincipale(title, xmlMapPath);

        this.fenetrePrincipaleController = pagePrincipale.getController();

        this.fenetrePrincipaleController.initialize(this,pagePrincipale.getFXMLoader(), xmlMapPath, title );
        try {
            fenetrePrincipaleController.initMapAndControls(Projection.WEB_MERCATOR, xmlMapPath);
        } catch (
                MauvaisFormatXmlException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        pagePrincipale.showAndWait();
    }


    public void ajouterLivraison(FXMLLoader fxmlLoader) throws IOException {
        FenetreSaisieLivraison pageSaisieLivraison = new FenetreSaisieLivraison(this.intersectionSelectionne, this.fenetrePrincipaleController);
        this.fenetreSaisieLivraisonController = pageSaisieLivraison.getController();
        this.fenetreSaisieLivraisonController.initialize(this);
        pageSaisieLivraison.showAndWait();
        // TODO change the attribute to an optional one.
        this.fenetreSaisieLivraisonController = null;
    }

    public void supprimerLivraison(Livraison livraisonASupprimer){ currentState.cliqueSupprimerLivraison(this, livraisonASupprimer);}

    public void cliqueModifierLivraison(Livraison livraisonAModifier){ currentState.modifierLivraison(this, livraisonAModifier);}

    public void modifierLivraison(Livraison livraisonAModifier, FXMLLoader fxmlLoader){
        FenetreSaisieLivraison pageSaisieLivraison = new FenetreSaisieLivraison(this.intersectionSelectionne, this.fenetrePrincipaleController);
        this.fenetreSaisieLivraisonController = pageSaisieLivraison.getController();
        this.fenetreSaisieLivraisonController.initialize(this);
        pageSaisieLivraison.showAndWait();
        // TODO change the attribute to an optional one.
        this.fenetreSaisieLivraisonController = null;
    }

    public void doubleCliquePlan(Intersection intersectionSelectionne, FXMLLoader fxmlLoader){
        this.setIntersectionSelectionne(intersectionSelectionne);
        currentState.doubleCliquePlan(this, fxmlLoader);
    }

    public void cliquerChargerLivraison(){ currentState.cliqueChargerLivraison(this);}

    public void chargerLivraison(Livraison livraisonACharger){
        fenetrePrincipaleController.chargerLivraison(livraisonACharger);
    }
    public void cliquerAjouterLivraisonATournee(){  }
}
