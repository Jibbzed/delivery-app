package controleur;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modele.Intersection;

public class AjoutLivraisonController {


    String destinationId = "";
    @FXML
    private ComboBox coursierSelector;

    @FXML
    private Label warningMessage;

    @FXML
    private ToggleGroup plageHoraireSelector;
    @FXML
    private RadioButton start8;
    @FXML
    private RadioButton start9;
    @FXML
    private RadioButton start10;
    @FXML
    private RadioButton start11;

    @FXML
    private Button validationButton;

    @FXML
    private Label destinationIdLabel;
    @FXML
    private Label originIdLabel;

    public void AjoutLivraisonController() {

    }

    public void initialize() {
        start8.setOnAction(e -> {
            selectionnerPlageHoraire(8);
        });

        start9.setOnAction(e -> {
            selectionnerPlageHoraire(9);
        });

        start10.setOnAction(e -> {
            selectionnerPlageHoraire(10);
        });

        start11.setOnAction(e -> {
            selectionnerPlageHoraire(11);
        });

        coursierSelector.getItems().add("Coursier 1");

        coursierSelector.setOnAction(e -> {
            selectionnerCoursier((String) ((ComboBox) e.getSource()).getValue());
        });

    }

    /**
     * initialiser l'id de l'intersection associée à la livraison
     *
     * @param idIntersection
     */
    public void initData(String idIntersection) {
        destinationId = idIntersection;
        destinationIdLabel.setText(destinationId);
        destinationIdLabel.setVisible(true);

        System.out.println(idIntersection);
    }


    //TODO: compléter cette méthode pour ajouter la plage horaire à la livraison
    public void selectionnerPlageHoraire(int plageHoraire) {
        System.out.println(plageHoraire);

    }

    //TODO: compléter cette méthode pour ajouter le coursier à la livraison
    public void selectionnerCoursier(String coursier) {
        System.out.println(coursier);

    }

    public void saisirLivraison() {
        if (plageHoraireSelector.getSelectedToggle() == null || coursierSelector.getValue() == null) {
            warningMessage.setVisible(true);
            return;
        }
        Stage stage = (Stage) validationButton.getScene().getWindow();
        stage.close();
        //TODO: ajouter la livraison à la liste
    }
}
