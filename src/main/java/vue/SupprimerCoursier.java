package vue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Coursier;
import modele.Livraison;
import service.ServiceCoursier;
import service.impl.ServiceLivraisonMockImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class SupprimerCoursier {

    @FXML
    private ListView<Coursier> listeCoursiers;
    private ObservableList<Coursier> items = FXCollections.observableArrayList();

    @FXML
    private Label labelTexte;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void initialize(){
        // Remplissage de la liste
        // Clear de la liste avant de la reremplir
        listeCoursiers.getItems().clear();

        ArrayList<Coursier> list = ServiceCoursier.getInstance().getListeCoursiers();
        for(Coursier coursier : list) {
            listeCoursiers.setItems(items);
            items.add(coursier);
        }
    }

    public void supprimerCoursier(){
        ServiceCoursier serviceCoursier = ServiceCoursier.getInstance();
        Coursier coursierASupprimer = listeCoursiers.getSelectionModel().getSelectedItem();
        // pas de coursier sélectionné
        if(coursierASupprimer == null) {
            this.labelTexte.setTextFill(Color.RED);
            this.labelTexte.setText("Veuillez sélectionner un coursier à supprimer.");
        }
        String nom = coursierASupprimer.getNom();
        String prenom = coursierASupprimer.getPrenom();
        int count = 0;
        // On regarde s'il est affecté à des livraisons
        Set< Livraison> listeLivraison = ServiceLivraisonMockImpl.getInstance().afficherToutLivraisons();
        for(Livraison livraison : listeLivraison){
            System.out.println(livraison.getCoursierLivraison());
            System.out.println(coursierASupprimer);
            if(livraison.getCoursierLivraison().get().equals(coursierASupprimer)){
                count++;
            }
        }
        // Si oui on refuse la suppression
        if(count != 0){
            // Création d'une alerte
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Erreur de suppression");
            alerte.setHeaderText("Impossible de supprimer " + nom + " " + prenom + " de la liste.");
            alerte.setContentText("Le coursier est affecté à " + count + " livraison(s) existante(s). Veuillez sélectionner un autre coursier ou changer les coursiers des livraisons concernées.");
            alerte.showAndWait();
        }
        // cas où le coursier est sélectionné et non affecté
        else {
            serviceCoursier.retirerCoursier(coursierASupprimer);
            this.labelTexte.setTextFill(Color.GREEN);
            this.labelTexte.setText("Le coursier " + nom + " " + prenom + " a été retiré de la liste.");
            this.initialize();
        }
    }

    public void revenirPrecedent(){
        String fxmlFile = "/vue/GestionnaireCoursier.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            root = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Gestionnaire de Coursier");
        stage.setScene(scene);
        stage.show();
    }
}
