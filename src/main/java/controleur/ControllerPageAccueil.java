package controleur;

import com.sothawo.mapjfx.Projection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.exception.MauvaisFormatXmlException;

import java.io.IOException;

public class ControllerPageAccueil {

    private StateController stateController;
    @FXML
    private Button buttonLargeMap;

    @FXML
    private Button buttonMediumMap;

    @FXML
    private Button buttonSmallMap;

    @FXML
    private Button buttonValidate;

    @FXML
    private TextField textXML;

    private String xmlPath;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public ControllerPageAccueil(){

    }
    public void initialize(StateController stateController) {
        this.stateController = stateController;
        String fxmlFile = "/vue/DemoApp.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        buttonLargeMap.setOnAction(event -> {
            // TODO: change xml files to files that would be in the src folder [/ressources]
            xmlPath = "src/test/resources/largeMap.xml";
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
//                this.stateController.getCurrentState().valider(stateController);
                this.stateController.afficherMap(fxmlLoader, xmlPath,"Grande carte de Lyon", stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonMediumMap.setOnAction(event -> {
            xmlPath = "src/test/resources/mediumMap.xml";
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                this.stateController.afficherMap(fxmlLoader, xmlPath,"Moyenne carte de Lyon", stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonSmallMap.setOnAction(event -> {
            xmlPath = "src/test/resources/smallMap.xml";
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                this.stateController.afficherMap(fxmlLoader, xmlPath,"Petite carte de Lyon", stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonValidate.setOnAction(event -> {
            xmlPath = textXML.getText();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                this.stateController.afficherMap(fxmlLoader, xmlPath,"Carte : "+xmlPath, stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String getXmlPath() {
        return xmlPath;
    }
}
