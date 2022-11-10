package vue;

import controleur.ControllerPageAccueil;
import controleur.StateController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeliveryApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        PageAccueil pageAccueil = new PageAccueil();
        pageAccueil.show();
    }
}
