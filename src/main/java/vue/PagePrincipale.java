package vue;

import controleur.PagePrincipaleController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PagePrincipale extends Stage{
    private String fxmlFile = "/vue/DemoApp.fxml";
    FXMLLoader fxmlLoader;

    public PagePrincipale(String title, String xmlPath){
         this.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root;
        try{
             root= fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            Scene scene = new Scene(root);
        setTitle(title);
        setScene(scene);

    }

    public FXMLLoader getFXMLoader() {
        return this.fxmlLoader;
    }
    public PagePrincipaleController getController(){
        return this.fxmlLoader.getController();
    }
}
