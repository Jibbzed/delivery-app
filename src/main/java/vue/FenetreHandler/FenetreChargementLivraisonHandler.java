package vue.FenetreHandler;


import controleur.StateController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modele.Coursier;
import modele.Livraison;
import modele.Plan;
import modele.exception.MauvaisFormatXmlException;
import service.ServiceLivraison;
import service.impl.ServiceLivraisonMockImpl;
import vue.Fenetre.FenetrePrincipale;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FenetreChargementLivraisonHandler {
    private FenetrePrincipale fenetrePrincipale;
    private StateController stateController;
    @FXML
    private Button parcourir;

    @FXML
    private Button validationButton;

    @FXML
    private ListView<Livraison> livraisonSelector;

    private ServiceLivraison serviceLivraison = ServiceLivraisonMockImpl.getInstance();

    private String xmlPath;

    public void initialize(StateController stateController, FenetrePrincipale fenetrePrincipale) {
        this.stateController = stateController;
        this.fenetrePrincipale = fenetrePrincipale;
        fenetrePrincipale.rendreFlou();
        parcourir.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(null);
            if (file!=null && file.getName().endsWith(".xml")) {
                System.out.println(file.getAbsolutePath());
                stateController.chargerLivraisonsSauvegardees(file.getPath());
                serviceLivraison.afficherLivraisonsSauvegardees().forEach(l->{
                    livraisonSelector.getItems().remove(l);
                    livraisonSelector.getItems().add(l);
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de chargement");
                alert.setContentText("Le fichier n'est pas au bon format");
                alert.showAndWait();
            }
        });

        validationButton.setOnAction(actionEvent -> {
            if(livraisonSelector.getSelectionModel().getSelectedItem() != null) {
                stateController.chargerLivraison(livraisonSelector.getSelectionModel().getSelectedItem());
                this.fenetrePrincipale.getFenetreHandler().refreshLivraison();
                Stage stage = (Stage) validationButton.getScene().getWindow();
                stage.close();
                fenetrePrincipale.enleverFlou();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Aucune livraison sélectionnée");
                alert.setContentText("Veuillez sélectionner une livraison");
                alert.showAndWait();
            }
        });


    }
}
