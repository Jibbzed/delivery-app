package vue.FenetreHandler;

import controleur.StateController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Coursier;
import modele.Livraison;
import service.ServiceCoursier;
import service.impl.ServiceLivraisonMockImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class FenetreGestionnaireCoursierHandler {

    //StateController stateController;
    @FXML
    private ListView<Coursier> listeCoursiers;

    @FXML
    private Button boutonRetour;

    @FXML
    private Button boutonAjouter;

    @FXML
    private Button boutonSupprimer;

    @FXML
    private Label labelTexte;

    @FXML
    private TextField texteNom;

    @FXML
    private TextField textePrenom;

    private ObservableList<Coursier> items = FXCollections.observableArrayList();


    public void initialize(StateController controller){
        // On remplit la liste
        refreshCoursiers(controller);

        boutonRetour.setOnAction(event -> {
            Stage stage = (Stage) boutonRetour.getScene().getWindow();
            stage.close();
        });

        boutonAjouter.setOnAction(event -> {
            // Si on a pas les champs remplis
            if(this.texteNom.getText().equals("") || this.textePrenom.getText().equals("")) {
                this.labelTexte.setTextFill(Color.RED);
                this.labelTexte.setText("Veuillez saisir un nom et un prénom.");
            } else {
                controller.getCurrentState().ajouterCoursier(this.texteNom.getText().toUpperCase(), this.textePrenom.getText());
                String message = "Le coursier " + this.texteNom.getText().toUpperCase() + " " + this.textePrenom.getText() + " a bien été ajouté";
                this.labelTexte.setTextFill(Color.GREEN);
                this.labelTexte.setText(message);
                this.texteNom.clear();
                this.textePrenom.clear();
                this.refreshCoursiers(controller);
            }
        });

        boutonSupprimer.setOnAction(event -> {
            Coursier coursierASupprimer = listeCoursiers.getSelectionModel().getSelectedItem();
            // pas de coursier sélectionné
            if(coursierASupprimer == null) {
                this.labelTexte.setTextFill(Color.RED);
                this.labelTexte.setText("Veuillez sélectionner un coursier à supprimer.");
            } else {
                String nom = coursierASupprimer.getNom();
                String prenom = coursierASupprimer.getPrenom();
                // On regarde s'il est affecté à des livraisons
                int count = controller.getCurrentState().nbLivraisonAffecteCoursier(coursierASupprimer);
                // Si oui on refuse la suppression
                if (count != 0) {
                    // Création d'une alerte (Label en rouge)
                    this.labelTexte.setTextFill(Color.RED);
                    this.labelTexte.setText(nom + " " + prenom + " est affecté à " + count + " livraison(s) existante(s). Impossible de supprimer le coursier.");
                }
                // cas où le coursier est sélectionné et non affecté
                else {
                    controller.getCurrentState().supprimerCoursier(coursierASupprimer);
                    this.labelTexte.setTextFill(Color.GREEN);
                    this.labelTexte.setText("Le coursier " + nom + " " + prenom + " a été retiré de la liste.");
                    this.refreshCoursiers(controller);
                }
            }
        });
    }

    public void refreshCoursiers(StateController controller) {
        listeCoursiers.getItems().clear();
        ArrayList<Coursier> list = controller.getCurrentState().recupererListeCoursiers();
        for (Coursier coursier : list) {
            listeCoursiers.setItems(items);
            items.add(coursier);
        }
    }

}
