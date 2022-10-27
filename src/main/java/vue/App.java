package vue;

import com.sothawo.mapjfx.Projection;
import controleur.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ServiceCoursier;
import service.ServiceLivraison;
import service.impl.ServiceLivraisonMockImpl;

/**
 * JavaFX App
 */
public class App extends Application {
    /** Logger for the class */
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static String parameters;

    public static void main(String[] args) {
        logger.trace("begin main");
        launch(args);
        logger.trace("end main");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ServiceCoursier.getInstance(1);
        ServiceLivraisonMockImpl.getInstance();
        logger.info("starting DemoApp");
        String fxmlFile = "/vue/DemoApp.fxml";
        logger.debug("loading fxml file {}", fxmlFile);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        logger.trace("stage loaded");

        final Controller controller = fxmlLoader.getController();
        final  Projection projection = getParameters().getUnnamed().contains("wgs84")
                ? Projection.WGS_84 : Projection.WEB_MERCATOR;
        controller.initMapAndControls(projection);

        Scene scene = new Scene(rootNode);
        logger.trace("scene created");

        primaryStage.setTitle("sothawo mapjfx demo application");
        primaryStage.setScene(scene);
        logger.trace("showing scene");
        primaryStage.show();

        logger.debug("application start method finished.");
    }
}