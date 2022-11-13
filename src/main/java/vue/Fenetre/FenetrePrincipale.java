package vue.Fenetre;

import vue.FenetreController.FenetrePrincipaleController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//TODO: Get node Parent
public class FenetrePrincipale extends Stage{
    private String fxmlFile = "/vue/Principale.fxml";
    FXMLLoader fxmlLoader;

    private Parent rootNode;

    public FenetrePrincipale(String title, String xmlPath){

        setTitle(title);
        setScene(loadSceneFromFXML());

    }
    private Scene loadSceneFromFXML(){
        this.fxmlLoader = new FXMLLoader();
        try {
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Scene(rootNode);
    }

    public FXMLLoader getFXMLoader() {
        return this.fxmlLoader;
    }
    public FenetrePrincipaleController getController(){
        return this.fxmlLoader.getController();
    }

    public Parent getRootNode() {
        return rootNode;
    }
}
