package vue.FenetreController;


import controleur.StateController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modele.Coursier;
import modele.Intersection;
import modele.Livraison;
import modele.Plan;
import service.ServiceCoursier;
import service.ServiceLivraison;
import service.impl.ServiceLivraisonMockImpl;

public class FenetreSaisieLivraisonController {

    private FenetrePrincipaleController controllerMere;
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

    private StateController stateController;

    private ServiceCoursier serviceCoursier = ServiceCoursier.getInstance();
    private int plageHoraire;
    private ServiceLivraison serviceLivraison;

    public void AjoutLivraisonController() {
    }

    public void initialize(StateController stateController) {
        this.stateController = stateController;
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
    public void initData(Intersection intersection, FenetrePrincipaleController controllerMere, Plan plan) {
        destination = intersection;
        destinationIdLabel.setText(plan.listerTronconsParIntersection(intersection));
        destinationIdLabel.setVisible(true);
        this.controllerMere = controllerMere;
        System.out.println(destination);
    }


    public void selectionnerPlageHoraire(int plageHoraire) {
        this.plageHoraire = plageHoraire; 
    }

    public void selectionnerCoursier(Coursier coursier) {
        this.coursierSelectionne = coursier;
    }

    public void saisirLivraison() {
        if (plageHoraireSelector.getSelectedToggle() == null || coursierSelector.getValue() == null) {
            warningMessage.setVisible(true);
            return;
        }
        this.stateController.getCurrentState().valider(this.stateController);
        this.serviceLivraison = ServiceLivraisonMockImpl.getInstance();
        Livraison livraison = new Livraison(this.destination);
        livraison.setCoursierLivraison(this.coursierSelectionne);
        livraison.setFenetreHoraireLivr(this.plageHoraire);
        serviceLivraison.ajouterLivraison(livraison);
//        Set<Livraison> livraisons = serviceLivraison.afficherToutLivraisons();
        this.controllerMere.refreshLivraison();
        Stage stage = (Stage) validationButton.getScene().getWindow();
        stage.close();
        }
}
