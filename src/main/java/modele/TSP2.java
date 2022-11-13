package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TSP2 extends TemplateTSP {
    @Override
    protected double bound(Integer currentVertex, Collection<Integer> unvisited) {
        // plus court chemin du dernier point visité à un des points non visités
        double l = Integer.MAX_VALUE;
        for( int i=0; i< unvisited.size(); i++ ) {
            if( g.getCout(currentVertex, (int)unvisited.toArray()[i]) < l ) l = g.getCout(currentVertex, (int)unvisited.toArray()[i]);
        }

        // pour chaque point non visité, plus court chemin de ce point à l'origine (entrepôt) ou à un autre point non visité
        double bound = l;
        for( int j=0; j<unvisited.size(); j++ ) {
            double min = g.getCout((int)unvisited.toArray()[j], 0);
            for( int k=0; k<unvisited.size(); k++ ) {
                if( j!=k && g.getCout((int)unvisited.toArray()[j], (int)unvisited.toArray()[k]) < min ) min = g.getCout((int)unvisited.toArray()[j], (int)unvisited.toArray()[k]);
            }
            bound += min; // on somme ces distances min à la bound qu'on a calculé
        }

        // on se retrouve avec une bound plus précise que simplement n*cout_le_plus_faible avec n le nombre de sommets non visités
        // => on prend le plus court chemin pour relier la partie visitée à la non visitée (première étape)
        // => à cela, on ajoute l'arète la plus courte entre 2 points non visités (+ entrepot) pour chaque point non visité
        // on obtient ainsi une bonne approximation de la distance minimale restant à parcourir pour visiter tous les non visités et revenir au départ
        return bound;
    }

    @Override
    protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graphe g) {
        return new SeqIter(unvisited, currentVertex, g);
    }

}