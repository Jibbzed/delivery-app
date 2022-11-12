package vue.FenetreControler;

import controleur.StateController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class FenetreAccueilController {

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

    public FenetreAccueilController(){

    }


    @FXML
    public void initialize() {
        this.stateController = new StateController();

        buttonLargeMap.setOnAction(event -> {
            String xmlPath = "src/test/resources/largeMap.xml";
            try {
                this.stateController.afficherMap("Grande carte de Lyon", xmlPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonMediumMap.setOnAction(event -> {
            String xmlPath = "src/test/resources/mediumMap.xml";
            try {
                this.stateController.afficherMap("Moyenne carte de Lyon", xmlPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonSmallMap.setOnAction(event -> {
            String xmlPath = "src/test/resources/smallMap.xml";
            try {
                this.stateController.afficherMap("Petite carte de Lyon", xmlPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonValidate.setOnAction(event -> {
            String  xmlPath = textXML.getText();
            String title = "Carte : "+xmlPath;
            try {
                this.stateController.afficherMap(title, xmlPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
