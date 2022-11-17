package controleur.state;

import controleur.StateController;
import modele.Coursier;
import modele.Livraison;

import java.util.ArrayList;
import java.util.List;

public class GestionCoursierState implements State {

    @Override
    public ArrayList<Coursier> recupererListeCoursiers(StateController stateController) {
        return stateController.recupererListeCoursiers();
    }

}
