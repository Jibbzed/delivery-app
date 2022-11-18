package controleur.state;

import controleur.StateController;
import controleur.command.ListOfCommands;
import modele.*;
import service.ServiceCoursier;
import service.ServiceTournee;
import service.impl.ServiceLivraisonMockImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

public class InitialState implements State {

    @Override
    public void doubleCliquePlan(StateController stateController) {
        stateController.setCurrentState(stateController.ajoutLivraisonState);
        try {
            stateController.disableMapView();
            stateController.afficherAjoutLivraison();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cliqueLivraison(StateController stateController) {
        State.super.cliqueLivraison(stateController);
        stateController.enableLivraisonDisableableComponents();
        stateController.setCurrentState(stateController.selectionnerLivraisonState);
    }

    @Override
    public void cliqueChargerLivraison(StateController stateController) {
        stateController.setCurrentState(stateController.chargementLivraisonState);
    }

    @Override
    public void cliqueAjouterLivraisonATournee(StateController stateController){
        stateController.setCurrentState(stateController.ajoutLivraisonTourneeState1);
    }

    @Override
    public void undo(ListOfCommands listOfCommands) {
        listOfCommands.undo();
    }

    @Override
    public void calculerTournees(Plan plan, String idEntrepot) {
        // On récupère la liste de livraisons existantes et on les groupe par coursier
        Map<Optional<Coursier>, List<Livraison>> listeLivraisonByCoursier = ServiceLivraisonMockImpl.getInstance().afficherToutesLivraisons()
                .stream()
                .filter(livraison -> livraison.getCoursierLivraison().isPresent())
                .collect(groupingBy(Livraison::getCoursierLivraison));

        //  on calcule tournee et la groupe par coursier.
        listeLivraisonByCoursier.keySet().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(coursier -> !coursier.getPlanifie())
                .forEach(
                        coursier -> {
                            Map<String, Livraison> livraisons = new HashMap<>();
                            listeLivraisonByCoursier.get(Optional.of(coursier)).forEach(
                                    livraison -> {
                                        livraisons.put(livraison.getDestinationLivraison().getId(), livraison);
                                    }
                            );
                            Tournee tournee = new CalculTournee(plan, plan.getIntersections().get(idEntrepot), livraisons).calculerTournee();
                            ServiceTournee serviceTournee = ServiceTournee.getInstance();
                            serviceTournee.getTournees().put(tournee.getCoursier(), tournee);
                            tournee.getLivraisons().forEach(
                                    l -> ServiceLivraisonMockImpl.getInstance().ajouterLivraison(l)
                            );
                            coursier.setPlanifie(true);
                            ServiceCoursier.getInstance().modifierCoursier(coursier);
                        }
                );
    }
}
