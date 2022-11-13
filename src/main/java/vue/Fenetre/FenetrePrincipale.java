package vue.Fenetre;

import com.sothawo.mapjfx.Projection;
import controleur.StateController;
import javafx.scene.control.Alert;
import modele.exception.MauvaisFormatXmlException;
import vue.FenetreController.FenetreAccueilController;
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
    public FenetrePrincipale(StateController controller, String title, String xmlPath){
        setTitle(title);
        setScene(loadSceneFromFXML(controller));

        FenetrePrincipaleController fenetreController = getController();

        fenetreController.initialize(controller,getFXMLoader(), xmlPath, title, rootNode);
        try {
            fenetreController.initMapAndControls(Projection.WEB_MERCATOR, xmlPath);
        } catch (
                MauvaisFormatXmlException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le fichier XML n'est pas valide");
            alert.setContentText("Veuillez charger un fichier XML valide");
            alert.showAndWait();
            throw new RuntimeException(e);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun fichier XML n'a été chargé");
            alert.setContentText("Veuillez charger un fichier XML");
            alert.showAndWait();
            throw new RuntimeException(e);
        }

    }
    private Scene loadSceneFromFXML(StateController controller){
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
