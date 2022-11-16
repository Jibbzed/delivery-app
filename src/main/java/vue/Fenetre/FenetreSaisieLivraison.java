package vue.Fenetre;

import controleur.StateController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Intersection;
import modele.Livraison;
import javafx.fxml.FXMLLoader;
import vue.FenetreHandler.FenetreAccueilHandler;
import vue.FenetreHandler.FenetreSaisieLivraisonHandler;

import java.io.IOException;

public class FenetreSaisieLivraison extends Stage {

    private final String fxmlFile= "/vue/SaisieLivraison.fxml";
    private FXMLLoader fxmlLoader;

    public FenetreSaisieLivraison(StateController controller, Intersection intersection, FenetrePrincipale fenetrePincipale){
        setTitle("Ajouter Livraison");
        setScene(loadSceneFromFXML(controller));
        getFenetreHandler().initialize(controller);
        getFenetreHandler().initData(intersection, fenetrePincipale, fenetrePincipale.getFenetreHandler().getPlan());
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
    }

    public FenetreSaisieLivraison(StateController controller, Livraison livraison, FenetrePrincipale fenetrePincipale){
        setTitle("Ajouter Livraison");
        setScene(loadSceneFromFXML(controller));
        getFenetreHandler().initialize(controller);
        getFenetreHandler().initDataLivraison(livraison, fenetrePincipale, fenetrePincipale.getFenetreHandler().getPlan());
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
    }

    private Scene loadSceneFromFXML(StateController controller){
        this.fxmlLoader = new FXMLLoader();
        Parent rootNode;
        try {
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene =new Scene(rootNode);
        return scene ;
    }

    public FenetreSaisieLivraisonHandler getFenetreHandler(){
        return fxmlLoader.getController();
    }


}
