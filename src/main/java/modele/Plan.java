package modele;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Plan {
    private Set<Troncon> troncons;
    private Set<Intersection> intersections;


    public Plan() {
        troncons = new HashSet<>();
        intersections = new HashSet<>();
    }
    public Plan(Collection troncons, Collection intersections) {
        this();
        this.troncons.addAll(troncons);
        this.intersections.addAll(intersections);
    }

    public void ajouterTroncon(Troncon troncon){
       validTroncon(troncon);
       this.troncons.add(troncon);
    }
    private void validTroncon(Troncon troncon) {
        if(!(this.intersections.contains(troncon.getOrigine()))
                || !(this.intersections.contains(troncon.getDestination())) ){
            // TODO: Est-ce qu'on a besoin de ce check, si on ajoute des troncons que depuis fichier xml
            // on a deja fais le check sur le ParserPlan avant de creer un nouveau troncon.
            throw new IllegalArgumentException("echec d'ajout du troncon: troncon avec un origine ou destination invalide.");
        }
    }

    public void ajouterIntersection(Intersection intersection) {
        this.intersections.add(intersection);
    }

    public void retirerIntersection(Intersection intersection) {
        this.troncons = this.troncons.stream()
                            .filter(troncon ->
                                        !(troncon.getOrigine().equals(intersection))
                                                && !(troncon.getDestination().equals(intersection)))
                            .collect(Collectors.toSet());
        this.intersections.remove(intersection);
    }

    public void retirerTroncon(Troncon troncon) {
        this.troncons.remove(troncon);
    }

    protected Set<Troncon> getTroncons() {
        return troncons;
    }

    protected Set<Intersection> getIntersections() {
        return intersections;
    }
}
