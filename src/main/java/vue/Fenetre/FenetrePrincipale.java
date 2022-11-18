package vue.Fenetre;

import com.sothawo.mapjfx.Projection;
import controleur.StateController;
import javafx.scene.effect.GaussianBlur;
import modele.Plan;
import vue.FenetreHandler.FenetrePrincipaleHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class FenetrePrincipale extends Stage{
    private String fxmlFile = "/vue/Principale.fxml";
    FXMLLoader fxmlLoader;

    private Parent rootNode;
    public FenetrePrincipale(StateController controller, String title, Plan plan){
        setTitle(title);
        setScene(loadSceneFromFXML());
        FenetrePrincipaleHandler fenetreController = getFenetreHandler();
        fenetreController.initialize(controller, plan, rootNode);
        fenetreController.initMapAndControls(Projection.WEB_MERCATOR, plan);
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

    public void rendreFlou(){
        getScene().getRoot().setEffect(new GaussianBlur());
    }

    public void enleverFlou() {
        getScene().getRoot().setEffect(null);
    }
    public FXMLLoader getFXMLoader() {
        return this.fxmlLoader;
    }
    public FenetrePrincipaleHandler getFenetreHandler(){
        return this.fxmlLoader.getController();
    }

}
