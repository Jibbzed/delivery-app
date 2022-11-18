package service;

import modele.Intersection;
import modele.Livraison;

import java.util.Set;

public interface ServiceLivraison {
    public void ajouterLivraison(Livraison livraison);
    public void supprimerLivraison(Livraison livraison);
    public Set<Livraison> afficherToutesLivraisons();
    public Set<Livraison> afficherLivraisonParIntersection(Intersection intersection);
    public void creerListeLivraisonsSauvegardees(Set<Livraison> livraisonsSauvegardees);
    public Set<Livraison> afficherLivraisonsSauvegardees();

}
