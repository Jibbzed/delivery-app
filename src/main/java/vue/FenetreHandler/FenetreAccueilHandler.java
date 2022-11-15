package vue.FenetreHandler;

import controleur.StateController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

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

    public FenetreAccueilHandler() {

    }


    @FXML
    public void initialize(StateController controller) {

        this.stateController = controller;
        buttonLargeMap.setOnAction(event -> {
            String xmlPath = "src/test/resources/largeMap.xml";
            this.stateController.afficherMap("Grande carte de Lyon", xmlPath);
        });

        buttonMediumMap.setOnAction(event -> {
            String xmlPath = "src/test/resources/mediumMap.xml";
            this.stateController.afficherMap("Moyenne carte de Lyon", xmlPath);
        });

        buttonSmallMap.setOnAction(event -> {
            String xmlPath = "src/test/resources/smallMap.xml";
            this.stateController.afficherMap("Petite carte de Lyon", xmlPath);
        });

        buttonParcourir.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                textXML.setText(file.getPath());
            }
        });

        buttonValidate.setOnAction(event -> {
            String xmlPath = textXML.getText();
            String title = "Carte : " + xmlPath;
            this.stateController.afficherMap(title, xmlPath);
        });
    }
}
