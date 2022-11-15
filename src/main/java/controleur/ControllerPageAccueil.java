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
            stage.setTitle("Grande carte de Lyon");
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
            stage.setTitle("Moyenne carte de Lyon");
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
            stage.setTitle("Petite carte de Lyon");
            stage.setScene(scene);
            stage.show();
        });

        buttonValidate.setOnAction(event -> {
            System.out.println(textXML.getText());
            xmlPath = textXML.getText();
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
            stage.setTitle("Carte : "+xmlPath);
            stage.setScene(scene);
            stage.show();
        });
    }

    public String getXmlPath() {
        return xmlPath;
    }
}
