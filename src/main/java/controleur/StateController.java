package controleur;

import com.sothawo.mapjfx.Projection;
import controleur.state.AjoutLivraisonState;
import controleur.state.InitialState;
import controleur.state.SelectionnerLivraisonState;
import controleur.state.State;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.Intersection;
import modele.exception.MauvaisFormatXmlException;

import java.io.IOException;

public class StateController {
    private State currentState;
    private Controller controller;
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
    //TODO: save the arguments in the Controller instatnce.
    public void afficherMap(FXMLLoader fxmlLoader, String xmlMapPath, String nomMap, Stage stage) throws IOException {
        // Parent
        Parent root = fxmlLoader.load();
        // controller

        this.controller = fxmlLoader.getController();
        this.controller.initialize(this, fxmlLoader, xmlMapPath, nomMap);
        try {
            controller.initMapAndControls(Projection.WEB_MERCATOR, xmlMapPath);
        } catch (
                MauvaisFormatXmlException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setTitle(nomMap);
        stage.setScene(scene);
        stage.showAndWait();
    }



    public void ajouterLivraison(FXMLLoader fxmlLoader) throws IOException {
        // Parent
        Parent root = fxmlLoader.load();
        // Controller
        this.ajoutLivraisonController = fxmlLoader.getController();
        this.ajoutLivraisonController.initialize(this);
        ajoutLivraisonController.initData(this.intersectionSelectionne, this.controller);
        // scene
        Stage stage = new Stage();
        stage.setTitle("Ajouter Livraison");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        // fermer la scene
        stage.showAndWait();
        // TODO change the attribute to an optional one.
        this.ajoutLivraisonController = null;
    }
}
