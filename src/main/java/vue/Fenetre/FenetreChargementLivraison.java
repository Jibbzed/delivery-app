package vue.Fenetre;

import controleur.StateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Intersection;
import modele.Livraison;
import vue.FenetreHandler.FenetreChargementLivraisonHandler;
import vue.FenetreHandler.FenetreSaisieLivraisonHandler;

import java.io.IOException;

public class FenetreChargementLivraison extends Stage{

        private final String fxmlFile= "/vue/ChargementLivraison.fxml";
        private FXMLLoader fxmlLoader;

        public FenetreChargementLivraison(StateController controller, FenetrePrincipale fenetrePrincipale){
            construireFenetre(controller, fenetrePrincipale);
        }

        private void construireFenetre(StateController controller, FenetrePrincipale fenetrePrincipale){
            setOnCloseRequest(e -> {
                controller.abandonAjoutLivraison();
                fenetrePrincipale.enleverFlou();
            });
            setTitle("Charger Livraison");
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
            ((FenetreChargementLivraisonHandler)fxmlLoader.getController()).initialize(controller, fenetrePrincipale);
            Scene scene =new Scene(rootNode);
            return scene ;
        }

        public FenetreChargementLivraisonHandler getFenetreHandler(){
            return fxmlLoader.getController();
        }


    }
