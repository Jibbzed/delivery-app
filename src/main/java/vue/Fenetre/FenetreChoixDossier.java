package vue.Fenetre;

import controleur.StateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Intersection;
import modele.Livraison;
import vue.FenetreHandler.FenetreChoixDossierHandler;
import vue.FenetreHandler.FenetreSaisieLivraisonHandler;

import java.io.IOException;

public class FenetreChoixDossier extends Stage {

    private final String fxmlFile= "/vue/ChoixDestinationFDR.fxml";
    private FXMLLoader fxmlLoader;

    public FenetreChoixDossier(StateController controller, FenetrePrincipale fenetrePrincipale){
        setTitle("Choisir dossier");
        setScene(loadSceneFromFXML(controller, fenetrePrincipale));
        getFenetreHandler().initialize(controller, fenetrePrincipale);
        //getFenetreHandler().initData(intersection, fenetrePrincipale, fenetrePrincipale.getFenetreHandler().getPlan());
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
    }

    /*
    public FenetreSaisieLivraison(StateController controller, Livraison livraison, FenetrePrincipale fenetrePincipale){
        setTitle("Ajouter Livraison");
        setScene(loadSceneFromFXML(controller));
        getFenetreHandler().initialize(controller);
        getFenetreHandler().initDataLivraison(livraison, fenetrePincipale, fenetrePincipale.getFenetreHandler().getPlan());
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
    }
     */

    private Scene loadSceneFromFXML(StateController controller, FenetrePrincipale fenetrePrincipale){
        fxmlLoader = new FXMLLoader();
        Parent rootNode;
        try {
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((FenetreChoixDossierHandler)fxmlLoader.getController()).initialize(controller, fenetrePrincipale);
        Scene scene =new Scene(rootNode);
        return scene ;
    }

    public FenetreChoixDossierHandler getFenetreHandler(){
        return fxmlLoader.getController();
    }
}