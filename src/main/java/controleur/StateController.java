package controleur;

import com.sothawo.mapjfx.Projection;
import controleur.state.AjoutLivraisonState;
import controleur.state.InitialState;
import controleur.state.SelectionnerLivraisonState;
import controleur.state.State;
import modele.Intersection;
import modele.exception.MauvaisFormatXmlException;
import vue.FenetreControler.FenetreSaisieLivraisonController;
import vue.FenetreControler.FenetreAccueilController;
import vue.Fenetre.FenetrePrincipale;
import vue.Fenetre.FenetreSaisieLivraison;
import vue.FenetreControler.FenetrePrincipaleController;

import java.io.IOException;

public class StateController {
    private State currentState;
    private FenetrePrincipaleController controller;
    private FenetreAccueilController controllerPageAccueil;
    private FenetreSaisieLivraisonController ajoutLivraisonController;
    /**  states **/
    public final State initialState = new InitialState();
    public final State ajoutLivraisonState= new AjoutLivraisonState();
    public final State SelectionnerLivraisonState = new SelectionnerLivraisonState();
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
    public void generateControllerPageAcceuil(FenetreAccueilController controllerPageAccueil) {
        this.controllerPageAccueil = controllerPageAccueil;
    }
    public void generateAjoutLivraisonController(FenetreSaisieLivraisonController ajoutLivraisonController){
        this.ajoutLivraisonController = ajoutLivraisonController;
    }
    //TODO: save the arguments in the FenetrePrincipaleController instatnce.
    public void afficherMap(String title, String xmlMapPath) throws IOException {

        FenetrePrincipale pagePrincipale = new FenetrePrincipale(title, xmlMapPath);

        this.controller = pagePrincipale.getController();

        this.controller.initialize(this,pagePrincipale.getFXMLoader(), xmlMapPath, title );
        try {
            controller.initMapAndControls(Projection.WEB_MERCATOR, xmlMapPath);
        } catch (
                MauvaisFormatXmlException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        pagePrincipale.showAndWait();
    }


    public void ajouterLivraison() throws IOException {
        FenetreSaisieLivraison pageSaisieLivraison = new FenetreSaisieLivraison(this.intersectionSelectionne, this.controller);
        this.ajoutLivraisonController = pageSaisieLivraison.getController();
        this.ajoutLivraisonController.initialize(this);
        pageSaisieLivraison.showAndWait();
        // TODO change the attribute to an optional one.
        this.ajoutLivraisonController = null;
    }
}
