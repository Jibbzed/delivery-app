package vue.Fenetre;

import controleur.StateController;
import javafx.stage.Modality;
import modele.Intersection;
import vue.FenetreController.FenetreSaisieLivraisonController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FenetreSaisieLivraison extends Stage {

    private final String fxmlFile= "/vue/SaisieLivraison.fxml";
    private FXMLLoader fxmlLoader;

    public FenetreSaisieLivraison(StateController controller, Intersection intersection, FenetrePrincipale fenetrePincipale){
        setTitle("Ajouter Livraison");
        setScene(loadSceneFromFXML());
        getController().initialize(controller);
        getController().initData(intersection, fenetrePincipale, fenetrePincipale.getPlan());
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
    }

    private Scene loadSceneFromFXML(){
        this.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root;
        try{
            root= fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Scene(root);
    }

    private FenetreSaisieLivraisonController getController(){
        return fxmlLoader.getController();
    }
}
