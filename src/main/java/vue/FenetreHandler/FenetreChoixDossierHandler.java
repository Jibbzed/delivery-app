package vue.FenetreHandler;

import controleur.StateController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modele.*;
import service.ServiceTournee;
import vue.Fenetre.FenetrePrincipale;

import java.io.File;

public class FenetreChoixDossierHandler {

    private FenetrePrincipale fenetrePrincipale;

    @FXML
    private Button buttonParcourir;

    @FXML
    private Button buttonValider;

    @FXML
    private Label textXML;

    private StateController stateController;

    private ServiceTournee serviceTournee = ServiceTournee.getInstance();

    public void initialize(StateController stateController, FenetrePrincipale fenetrePrincipale) {
        this.stateController = stateController;
        this.fenetrePrincipale = fenetrePrincipale;

        buttonParcourir.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);
            if (selectedDirectory != null) {
                textXML.setText(selectedDirectory.getAbsolutePath());
            }
        });

        buttonValider.setOnAction(event -> {
                    String xmlPath = textXML.getText();
                    this.produireFDR(xmlPath);
        });
    }

    public void produireFDR(String path) {
        FeuilleDeRoute feuilleDeRoute = new FeuilleDeRoute();
        for (Tournee tournee : serviceTournee.getTournees()) {
            feuilleDeRoute.CreerFeuille(tournee, path);
            Stage stage = (Stage) buttonValider.getScene().getWindow();
            stage.close();
        }
    }
}
