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
import vue.PagePrincipale;

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

    private String title;

    public ControllerPageAccueil(){

    }
    public void initialize(StateController stateController) {
        this.stateController = stateController;

        title= "carte de Lyon";
        buttonLargeMap.setOnAction(event -> {
            // TODO: change xml files to files that would be in the src folder [/ressources]
            xmlPath = "src/test/resources/largeMap.xml";
            try {
                this.stateController.afficherMap(title, xmlPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonMediumMap.setOnAction(event -> {
            xmlPath = "src/test/resources/mediumMap.xml";
            title ="Moyenne "+title;
            try {
                this.stateController.afficherMap(title, xmlPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonSmallMap.setOnAction(event -> {
            xmlPath = "src/test/resources/smallMap.xml";
            title = "Petite "+title;
            try {
                this.stateController.afficherMap(title, xmlPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonValidate.setOnAction(event -> {
            xmlPath = textXML.getText();
            title = "Carte : "+xmlPath;
            try {
                this.stateController.afficherMap(title, xmlPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String getXmlPath() {
        return xmlPath;
    }
}
