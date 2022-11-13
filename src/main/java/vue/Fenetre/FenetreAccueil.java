package vue.Fenetre;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FenetreAccueil extends Stage {
    private final String fxmlFile = "/vue/Accueil.fxml";

    public FenetreAccueil() {
        setTitle("Fenetre d'Accueil");
        setScene(loadSceneFromFXML());
    }

    private Scene loadSceneFromFXML(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent rootNode;
        try {
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Scene(rootNode);
    }
}
