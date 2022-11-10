package vue;

import controleur.ControllerPageAccueil;
import controleur.StateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PageAccueil extends Stage {

    public PageAccueil (){
        String fxmlFile = "/vue/PageAccueil.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent rootNode;
        try{
            rootNode= fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        final ControllerPageAccueil controller = fxmlLoader.getController();
        final StateController stateController = new StateController();

        controller.initialize(stateController);
        Scene scene = new Scene(rootNode);
        setTitle("Page d'Accueil");
        setScene(scene);
    }
}
