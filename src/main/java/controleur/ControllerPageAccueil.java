package controleur;

import com.sothawo.mapjfx.Projection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import modele.exception.MauvaisFormatXmlException;

import java.io.File;
import java.io.IOException;

public class ControllerPageAccueil {

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

    private String xmlPath;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public ControllerPageAccueil() {

    }
    public void initialize() {
        String fxmlFile = "/vue/DemoApp.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            root = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        buttonLargeMap.setOnAction(event -> {
            System.out.println("src/test/resources/largeMap.xml");
            xmlPath = "src/test/resources/largeMap.xml";
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            final Controller controller = fxmlLoader.getController();
            try {
                controller.initMapAndControls(Projection.WEB_MERCATOR, xmlPath);
            } catch (MauvaisFormatXmlException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });

        buttonMediumMap.setOnAction(event -> {
            System.out.println("src/test/resources/mediumMap.xml");
            xmlPath = "src/test/resources/mediumMap.xml";
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            final Controller controller = fxmlLoader.getController();
            try {
                controller.initMapAndControls(Projection.WEB_MERCATOR, xmlPath);
            } catch (MauvaisFormatXmlException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });

        buttonSmallMap.setOnAction(event -> {
            System.out.println("src/test/resources/smallMap.xml");
            xmlPath = "src/test/resources/smallMap.xml";
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            final Controller controller = fxmlLoader.getController();
            try {
                controller.initMapAndControls(Projection.WEB_MERCATOR, xmlPath);
            } catch (MauvaisFormatXmlException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });

        buttonParcourir.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                xmlPath = file.getPath();
                textXML.setText(xmlPath);
            }
        });

        buttonValidate.setOnAction(event -> {
            System.out.println(textXML.getText());
            xmlPath = textXML.getText();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            final Controller controller = fxmlLoader.getController();
            try {
                controller.initMapAndControls(Projection.WEB_MERCATOR, xmlPath);
            } catch (MauvaisFormatXmlException e) {
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
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }

    public String getXmlPath() {
        return xmlPath;
    }
}
