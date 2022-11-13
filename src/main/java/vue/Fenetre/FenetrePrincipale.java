package vue.Fenetre;

import com.sothawo.mapjfx.Projection;
import controleur.StateController;
import modele.exception.MauvaisFormatXmlException;
import vue.FenetreController.FenetreAccueilController;
import vue.FenetreController.FenetrePrincipaleController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FenetrePrincipale extends Stage{
    private String fxmlFile = "/vue/Principale.fxml";
    FXMLLoader fxmlLoader;

    public FenetrePrincipale(StateController controller, String title, String xmlPath){

        setTitle(title);
        setScene(loadSceneFromFXML(controller));

        FenetrePrincipaleController fenetreController = getController();

        fenetreController.initialize(controller,getFXMLoader(), xmlPath, title );
        try {
            fenetreController.initMapAndControls(Projection.WEB_MERCATOR, xmlPath);
        } catch (
                MauvaisFormatXmlException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private Scene loadSceneFromFXML(StateController controller){
        this.fxmlLoader = new FXMLLoader();
        Parent rootNode;
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
}
