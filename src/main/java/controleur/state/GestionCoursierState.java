package controleur.state;

import controleur.StateController;
import modele.Coursier;
import modele.Livraison;
import service.ServiceCoursier;
import service.impl.ServiceLivraisonMockImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GestionCoursierState implements State {

    @Override
    public ArrayList<Coursier> recupererListeCoursiers(){
        return ServiceCoursier.getInstance().getListeCoursiers();
    }

    public void ajouterCoursier(String nom, String prenom) {
        Coursier coursier = new Coursier(nom, prenom);
        ServiceCoursier.getInstance().ajouterCoursier(coursier);
    }

    public void supprimerCoursier(Coursier coursier) {
        ServiceCoursier.getInstance().retirerCoursier(coursier);
    }

    @Override
    public int nbLivraisonAffecteCoursier(Coursier coursier) {
        int count = 0;
        Set<Livraison> listeLivraison = ServiceLivraisonMockImpl.getInstance().afficherToutesLivraisons();
        for(Livraison livraison : listeLivraison){
            if(livraison.getCoursierLivraison().get().equals(coursier)){
                count++;
            }
        }
        return count;
    }
}
