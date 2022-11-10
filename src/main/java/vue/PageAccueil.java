package vue;

import controleur.ControllerPageAccueil;
import controleur.StateController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageAccueil extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/vue/PageAccueil.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        final StateController stateController = new StateController();
        final ControllerPageAccueil controller = fxmlLoader.getController();
        controller.initialize(stateController);

        Scene scene = new Scene(rootNode);

        stage.setTitle("Page d'Accueil");
        stage.setScene(scene);
        stage.show();
    }
}
