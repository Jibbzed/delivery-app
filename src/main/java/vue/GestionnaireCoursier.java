package vue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import modele.Coursier;
import service.ServiceCoursier;

import java.io.IOException;
import java.util.ArrayList;

public class GestionnaireCoursier {

    @FXML
    private ListView<Coursier> listeCoursiers;

    private ObservableList<Coursier> items = FXCollections.observableArrayList();
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize(){
        ArrayList<Coursier> list = ServiceCoursier.getInstance().getListeCoursiers();
        for(Coursier coursier : list) {
            listeCoursiers.setItems(items);
            items.add(coursier);
        }
    }

    public void allerAjouter(){
        String fxmlFile = "/vue/AjouterCoursier.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            root = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Ajouter un coursier");
        stage.setScene(scene);
        stage.show();
    }

    public void allerSupprimer(){
        String fxmlFile = "/vue/SupprimerCoursier.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            root = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Supprimer un coursier");
        stage.setScene(scene);
        stage.show();
    }

    public void revenirPrecedent(){
        String fxmlFile = "/vue/DemoApp.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            root = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Carte");
        stage.setScene(scene);
        stage.show();
    }

}
