package service.impl;

import modele.Intersection;
import modele.Livraison;
import service.ServiceLivraison;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceLivraisonMockImpl implements ServiceLivraison {
    private Set<Livraison> livraisons;
    private Set<Livraison> livraisonsSauvegardees;
    private static ServiceLivraisonMockImpl instance;

    private ServiceLivraisonMockImpl() {
        this.livraisons = new HashSet<>();
    }

    @Override
    public void ajouterLivraison(Livraison livraison) {
        this.livraisons.add(livraison);
    }

    @Override
    public void supprimerLivraison(Livraison livraison) {
        this.livraisons.remove(livraison);
    }

    @Override
    public Set<Livraison> afficherToutesLivraisons() {
        return new HashSet<>(livraisons);
    }

    @Override
    public Set<Livraison> afficherLivraisonParIntersection(Intersection intersection) {
        return livraisons.stream().filter(l -> l.getOrigineLivraison().equals(intersection)).collect(Collectors.toSet());
    }

    public static ServiceLivraisonMockImpl getInstance() {
        if (instance == null) {
            instance = new ServiceLivraisonMockImpl();
        }
        return instance;
    }

    @Override
    public void creerListeLivraisonsSauvegardees(Set<Livraison> livraisonsSauvegardees) {
        this.livraisonsSauvegardees = livraisonsSauvegardees;
    }

    @Override
    public Set<Livraison> afficherLivraisonsSauvegardees() {
        return this.livraisonsSauvegardees;
    }
}
