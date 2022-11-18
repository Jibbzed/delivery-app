package vue.Fenetre;

import controleur.StateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vue.FenetreHandler.FenetreAccueilHandler;

import java.io.IOException;

public class FenetreAccueil extends Stage {
    private final String fxmlFile = "/vue/Accueil.fxml";

    private FXMLLoader fxmlLoader;


    public FenetreAccueil(StateController controller) {
        setTitle("FenetreHandler d'Accueil");
        setScene(loadSceneFromFXML(controller));
    }

    private Scene loadSceneFromFXML(StateController controller){
        this.fxmlLoader = new FXMLLoader();
        Parent rootNode;
        try {
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((FenetreAccueilHandler)fxmlLoader.getController()).initialize(controller);
        Scene scene =new Scene(rootNode);
        return scene ;
    }

    public FenetreAccueilHandler getFenetreHandler(){
        return fxmlLoader.getController();
    }
}
