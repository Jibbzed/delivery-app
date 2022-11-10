package controleur;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modele.*;
import service.ServiceCoursier;
import service.ServiceLivraison;
import service.impl.ServiceLivraisonMockImpl;

import java.util.Set;

public class AjoutLivraisonController {


    private Intersection destination;

    private Coursier coursierSelectionne;
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


    private ServiceCoursier serviceCoursier = ServiceCoursier.getInstance(1);
    private int plageHoraire;
    private ServiceLivraison serviceLivraison;

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
//        coursierSelector.getItems().add("Coursier 1");
//        coursierSelector.getItems().add("Coursier 2");
        serviceCoursier.getListeCoursiers().forEach(c -> coursierSelector.getItems().add(c));
        coursierSelector.setOnAction(e -> {

            selectionnerCoursier((Coursier) ((ComboBox) e.getSource()).getValue());
        });

    }

    /**
     * initialiser l'id de l'intersection associée à la livraison
     *
     * @param intersection
     */
    public void initData(Intersection intersection, Plan plan) {
        destination = intersection;
        /*Set<Troncon> nomRues = plan.listerTronconsSortieParIntersection(intersection);
        String nomRuesString;
        if(nomRues.size()<1) {
            nomRuesString = nomRues.toArray()[0].toString() + ", " + nomRues.toArray()[1].toString();
        } else {
            nomRuesString = nomRues.toArray()[0].toString();
        }
        destinationIdLabel.setText(nomRuesString);*/
        destinationIdLabel.setText(intersection.getId());
        destinationIdLabel.setVisible(true);
        System.out.println(destination);
    }


    //TODO: compléter cette méthode pour ajouter la plage horaire à la livraison
    public void selectionnerPlageHoraire(int plageHoraire) {
        this.plageHoraire = plageHoraire; 
    }

    //TODO: compléter cette méthode pour ajouter le coursier à la livraison
    public void selectionnerCoursier(Coursier coursier) {
        this.coursierSelectionne = coursier;

    }

    public void saisirLivraison() {
        if (plageHoraireSelector.getSelectedToggle() == null || coursierSelector.getValue() == null) {
            warningMessage.setVisible(true);
            return;
        }
        this.serviceLivraison = ServiceLivraisonMockImpl.getInstance();
        Livraison livraison = new Livraison(this.destination);
        livraison.setCoursierLivraison(this.coursierSelectionne);
        livraison.setFenetreHoraireLivr(this.plageHoraire);
        serviceLivraison.ajouterLivraison(livraison);
        Set<Livraison> livraisons = serviceLivraison.afficherToutLivraisons();
        Stage stage = (Stage) validationButton.getScene().getWindow();
        stage.close();
    }
}
