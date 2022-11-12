package vue.Fenetre;

import modele.Intersection;
import vue.FenetreControler.FenetrePrincipaleController;
import vue.FenetreControler.FenetreSaisieLivraisonController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FenetreSaisieLivraison extends Stage {

    private final String fxmlFile="/vue/SaisieLIvraison.fxml";
    private FXMLLoader fxmlLoader;

    public FenetreSaisieLivraison(Intersection intersection, FenetrePrincipaleController controllerMere){
        setTitle("Ajouter Livraison");
        setScene(loadSceneFromFXML());
        getController().initData(intersection, controllerMere);
        centerOnScreen();
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

    public FenetreSaisieLivraisonController getController(){
        return fxmlLoader.getController();
    }
}
