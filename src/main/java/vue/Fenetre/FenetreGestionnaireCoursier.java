package vue.Fenetre;

import controleur.StateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vue.FenetreHandler.FenetreGestionnaireCoursierHandler;

import java.io.IOException;

public class FenetreGestionnaireCoursier extends Stage {

    private final String fxmlFile= "/vue/GestionnaireCoursier.fxml";
    private FXMLLoader fxmlLoader;

    public FenetreGestionnaireCoursier(StateController controller) {
        setTitle("Gestionnaire de coursier");
        setScene(loadSceneFromFXML(controller));
        getFenetreHandler().initialize(controller);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
    }

    private Scene loadSceneFromFXML(StateController controller){
        this.fxmlLoader = new FXMLLoader();
        Parent rootNode;
        try {
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((FenetreGestionnaireCoursierHandler)fxmlLoader.getController()).initialize(controller);
        Scene scene =new Scene(rootNode);
        return scene ;
    }

    public FenetreGestionnaireCoursierHandler getFenetreHandler(){
        return fxmlLoader.getController();
    }
}
