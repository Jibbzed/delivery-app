package vue;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Coursier;
import service.ServiceCoursier;

import java.io.IOException;

public class AjouterCoursier {

    @FXML
    private TextField texteNom;

    @FXML
    private TextField textePrenom;

    @FXML
    private Label labelTexte;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void ajouterCoursier() {
        ServiceCoursier serviceCoursier = ServiceCoursier.getInstance();
        if(this.texteNom.getText().equals("") || this.textePrenom.getText().equals("")) {
            this.labelTexte.setTextFill(Color.RED);
            this.labelTexte.setText("Veuillez saisir un nom et un prénom.");
        } else {
            Coursier coursierAAjouter = new Coursier(this.texteNom.getText().toUpperCase(), this.textePrenom.getText());
            serviceCoursier.ajouterCoursier(coursierAAjouter);
            String message = "Le coursier " + this.texteNom.getText().toUpperCase() + " " + this.textePrenom.getText() + " a bien été ajouté";
            this.labelTexte.setTextFill(Color.GREEN);
            this.labelTexte.setText(message);
            this.texteNom.clear();
            this.textePrenom.clear();
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
