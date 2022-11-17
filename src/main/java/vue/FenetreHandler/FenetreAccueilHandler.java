package vue.FenetreHandler;

import com.sothawo.mapjfx.Projection;
import controleur.StateController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modele.Parser;
import modele.Plan;
import modele.exception.MauvaisFormatXmlException;

import java.io.File;
import java.io.IOException;

public class FenetreAccueilHandler {

    private StateController stateController;
    @FXML
    private Button buttonLargeMap;

    @FXML
    private Button buttonMediumMap;

    @FXML
    private Button buttonSmallMap;

    @FXML
    private Button buttonParcourir;

    @FXML
    private Button buttonValidate;

    @FXML
    private TextField textXML;

    @FXML
    private Label loadingLabel;

    public FenetreAccueilHandler() {

    }


    @FXML
    public void initialize(StateController controller) {
        this.stateController = controller;
        buttonLargeMap.setOnAction(event -> {
            String xmlPath = "src/test/resources/largeMap.xml";
            Plan plan = getPlan(xmlPath);
            if (plan != null) {
                this.stateController.afficherMap("Grande carte de Lyon", plan);
                this.stateController.setXmlPathPlan(xmlPath);
            }
        });

        buttonMediumMap.setOnAction(event -> {
            String xmlPath = "src/test/resources/mediumMap.xml";
            Plan plan = getPlan(xmlPath);
            if (plan != null) {
                this.stateController.afficherMap("Moyenne carte de Lyon", plan);
                this.stateController.setXmlPathPlan(xmlPath);
            }
        });

        buttonSmallMap.setOnAction(event -> {
            String xmlPath = "src/test/resources/smallMap.xml";
            Plan plan = getPlan(xmlPath);
            if (plan != null) {
                this.stateController.afficherMap("Petite carte de Lyon", plan);
                this.stateController.setXmlPathPlan(xmlPath);
            }
        });

        buttonValidate.setOnAction(event -> {
            String xmlPath = textXML.getText();
            String title = "Carte : " + xmlPath;
            Plan plan = getPlan(xmlPath);
            if (plan != null) {
                this.stateController.afficherMap(title, plan);
                this.stateController.setXmlPathPlan(xmlPath);
            }
        });

        buttonParcourir.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(null);

            if (file != null) {
                if (getPlan(file.getPath()) != null) {
                    textXML.setText(file.getPath());
                }
            }
        });

    }

    private Plan getPlan(String xmlPath) {
        Plan plan;
        try {
            plan = stateController.chargerPlan(xmlPath);
        } catch (
                MauvaisFormatXmlException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le fichier XML n'est pas valide");
            alert.setContentText("Veuillez charger un fichier XML valide");
            alert.showAndWait();
            //throw new RuntimeException(e);
            plan = null;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun fichier XML n'a été chargé");
            alert.setContentText("Veuillez charger un fichier XML");
            alert.showAndWait();
            //throw new RuntimeException(e);
            plan = null;
        }
        return plan;
    }


}
