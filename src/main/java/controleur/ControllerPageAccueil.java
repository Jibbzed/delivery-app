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
import vue.App;

import java.io.IOException;

public class ControllerPageAccueil {

    @FXML
    private Button buttonBigMap;

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
        buttonBigMap.setOnAction(event -> {
            System.out.println("src/test/resources/bigMap.xml");
            xmlPath = "src/test/resources/bigMap.xml";
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            final Controller controller = fxmlLoader.getController();
            controller.initMapAndControls(Projection.WEB_MERCATOR);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });

        buttonMediumMap.setOnAction(event -> {
            System.out.println("src/test/resources/mediumMap.xml");
            xmlPath = "src/test/resources/mediumMap.xml";
        });

        buttonSmallMap.setOnAction(event -> {
            System.out.println("src/test/resources/smallMap.xml");
            xmlPath = "src/test/resources/smallMap.xml";
        });

        buttonValidate.setOnAction(event -> {
            System.out.println(textXML.getText());
            xmlPath = textXML.getText();
        });
    }

    public String getXmlPath() {
        return xmlPath;
    }
}
