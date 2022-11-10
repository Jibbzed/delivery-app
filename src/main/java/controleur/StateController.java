package controleur;

import com.sothawo.mapjfx.Projection;
import controleur.state.AjoutLivraisonState;
import controleur.state.InitialState;
import controleur.state.SelectionnerLivraisonState;
import controleur.state.State;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.Intersection;
import modele.exception.MauvaisFormatXmlException;
import vue.PagePrincipale;
import vue.PageSaisieLivraison;

import java.io.IOException;

public class StateController {
    private State currentState;
    private PagePrincipaleController controller;
    private ControllerPageAccueil controllerPageAccueil;
    private AjoutLivraisonController ajoutLivraisonController;
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
    public void generateControllerPageAcceuil(ControllerPageAccueil controllerPageAccueil) {
        this.controllerPageAccueil = controllerPageAccueil;
    }
    public void generateAjoutLivraisonController(AjoutLivraisonController ajoutLivraisonController){
        this.ajoutLivraisonController = ajoutLivraisonController;
    }
    //TODO: save the arguments in the PagePrincipaleController instatnce.
    public void afficherMap(String title, String xmlMapPath) throws IOException {

        PagePrincipale pagePrincipale = new PagePrincipale(title, xmlMapPath);

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
        PageSaisieLivraison pageSaisieLivraison = new PageSaisieLivraison();
        this.ajoutLivraisonController = pageSaisieLivraison.getController();
        this.ajoutLivraisonController.initialize(this);
        ajoutLivraisonController.initData(this.intersectionSelectionne, this.controller);
        pageSaisieLivraison.showAndWait();
        // TODO change the attribute to an optional one.
        this.ajoutLivraisonController = null;
    }
}
