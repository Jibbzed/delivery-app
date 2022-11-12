package vue;

import javafx.application.Application;
import javafx.stage.Stage;
import vue.Fenetre.FenetreAccueil;

public class DeliveryApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FenetreAccueil pageAccueil = new FenetreAccueil();
        pageAccueil.show();
    }
}
