package vue.FenetreHandler;


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
import vue.Fenetre.FenetrePrincipale;

import java.util.List;

public class FenetreSaisieLivraisonHandler{

    private FenetrePrincipale fenetrePrincipale;
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
    private Button sauvegarderLivraison;

    private StateController stateController;

    private ServiceCoursier serviceCoursier = ServiceCoursier.getInstance();
    private int plageHoraire;
    private ServiceLivraison serviceLivraison;

    public void AjoutLivraisonController() {
    }

    public void initialize(StateController stateController, FenetrePrincipale fenetrePrincipale) {
        this.stateController = stateController;
        this.fenetrePrincipale = fenetrePrincipale;
        fenetrePrincipale.rendreFlou();
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
        serviceCoursier.getListeCoursiers().stream()
                .filter(c -> !c.getPlanifie())
                .forEach(c -> coursierSelector.getItems().add(c));
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
        destinationIdLabel.setText(plan.listerTronconsParIntersection(intersection));
        destinationIdLabel.setVisible(true);
        System.out.println(destination);
    }

    public void initDataLivraison(Livraison livraisonAModifier, Plan plan) {
        destination = livraisonAModifier.getDestinationLivraison();
        destinationIdLabel.setText(livraisonAModifier.afficherIhm(plan));
        destinationIdLabel.setVisible(true);
        coursierSelector.setValue(livraisonAModifier.getCoursierLivraison().get().toString());
        coursierSelectionne=livraisonAModifier.getCoursierLivraison().get();
        if (livraisonAModifier.getFenetreHoraireLivr().toString().equals("Optional[8]")) {
            start8.setSelected(true);
        } else if (livraisonAModifier.getFenetreHoraireLivr().toString().equals("Optional[9]")) {
            start9.setSelected(true);
        } else if (livraisonAModifier.getFenetreHoraireLivr().toString().equals("Optional[10]")) {
            start10.setSelected(true);
        } else if (livraisonAModifier.getFenetreHoraireLivr().toString().equals("Optional[11]")) {
            start11.setSelected(true);
        }
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
//        this.stateController.getCurrentState().valider(this.stateController);
//        this.serviceLivraison = ServiceLivraisonMockImpl.getInstance();
        Livraison livraison = new Livraison(this.destination);
        livraison.setCoursierLivraison(this.coursierSelectionne);
        livraison.setFenetreHoraireLivr(this.plageHoraire);
//        serviceLivraison.ajouterLivraison(livraison);
        stateController.ajouterLivraison(livraison);
//        Set<Livraison> livraisons = serviceLivraison.afficherToutLivraisons();
        this.fenetrePrincipale.getFenetreHandler().refreshLivraison();
        Stage stage = (Stage) validationButton.getScene().getWindow();
        stage.close();
        fenetrePrincipale.enleverFlou();

    }

    public void sauvegarderLivraison(){
        if (plageHoraireSelector.getSelectedToggle() == null || coursierSelector.getValue() == null) {
            warningMessage.setVisible(true);
            return;
        }
        Livraison livraison = new Livraison(this.destination);
        livraison.setCoursierLivraison(this.coursierSelectionne);
        livraison.setFenetreHoraireLivr(this.plageHoraire);
        stateController.sauvegarderLivraison(livraison);
        sauvegarderLivraison.setText("Livraison sauvegardée");
        sauvegarderLivraison.setMouseTransparent(true);
    }

}
