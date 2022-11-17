package vue.Fenetre;

import controleur.StateController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Intersection;
import modele.Livraison;
import javafx.fxml.FXMLLoader;
import vue.FenetreHandler.FenetreSaisieLivraisonHandler;

import java.io.IOException;

public class FenetreSaisieLivraison extends Stage {

    private final String fxmlFile= "/vue/SaisieLivraison.fxml";
    private FXMLLoader fxmlLoader;

    public FenetreSaisieLivraison(StateController controller, Intersection intersection, FenetrePrincipale fenetrePrincipale){
        construireFenetre(controller, fenetrePrincipale);
        getFenetreHandler().initData(intersection, fenetrePrincipale.getFenetreHandler().getPlan());
    }

    public FenetreSaisieLivraison(StateController controller, Livraison livraison, FenetrePrincipale fenetrePrincipale){
        construireFenetre(controller, fenetrePrincipale);
        getFenetreHandler().initDataLivraison(livraison, fenetrePrincipale.getFenetreHandler().getPlan());
    }

    private void construireFenetre(StateController controller, FenetrePrincipale fenetrePrincipale){
        setOnCloseRequest(e -> {
            controller.abandonAjoutLivraison();
            fenetrePrincipale.enleverFlou();
        });
        setTitle("Ajouter Livraison");
        setScene(loadSceneFromFXML(controller, fenetrePrincipale));
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
    }

    private Scene loadSceneFromFXML(StateController controller, FenetrePrincipale fenetrePrincipale){
        this.fxmlLoader = new FXMLLoader();
        Parent rootNode;
        try {
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((FenetreSaisieLivraisonHandler)fxmlLoader.getController()).initialize(controller,fenetrePrincipale);
        Scene scene =new Scene(rootNode);
        return scene ;
    }

    public FenetreSaisieLivraisonHandler getFenetreHandler(){
        return fxmlLoader.getController();
    }


}
