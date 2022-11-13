package vue.Fenetre;

import controleur.StateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vue.FenetreController.FenetreAccueilController;

import java.io.IOException;

public class FenetreAccueil extends Stage {
    private final String fxmlFile = "/vue/Accueil.fxml";
    Scene scene;

    public FenetreAccueil(StateController controller) {
        setTitle("Fenetre d'Accueil");
        setScene(loadSceneFromFXML(controller));
    }

    private Scene loadSceneFromFXML(StateController controller){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent rootNode;
        try {
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((FenetreAccueilController) fxmlLoader.getController()).initialize(controller);
        this.scene=new Scene(rootNode);
        return scene ;
    }
}
