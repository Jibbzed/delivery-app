package vue;

import controleur.AjoutLivraisonController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PageSaisieLivraison extends Stage {

    private FXMLLoader fxmlLoader;
    private String fxmlFile;
    public PageSaisieLivraison (){

        this.fxmlFile="/vue/AjoutLivraison.fxml";
        this.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root;
        try{
            root= fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setTitle("Ajouter Livraison");
        setScene(new Scene(root));
        centerOnScreen();
    }

    public AjoutLivraisonController getController(){
        return fxmlLoader.getController();
    }
}
