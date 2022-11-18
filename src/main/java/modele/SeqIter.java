package modele;

import java.util.Collection;
import java.util.Iterator;

public class SeqIter implements Iterator<Integer> {
    private Integer[] candidates;
    private int nbCandidates;

    /**
     * Constructeur de la classe.
     * Crée un itérateur pour parcourir les sommets de <code>unvisited</code>
     * qui sont les successeurs de <code>currentVertex</code> dans le <code>Graphe</code> <code>g</code>
     * Les sommets sont parcourus dans l'ordre dans lequel ils apparaissent dans <code>unvisited</code>
     *
     * @param unvisited     liste des indices des sommets successeurs de <code>currentVertex</code>
     * @param currentVertex indice du sommet actuellement exploré
     * @param g             <code>Graphe</code> auquel on applique le TSP
     */
    public SeqIter(Collection<Integer> unvisited, int currentVertex, Graphe g){
        this.candidates = new Integer[unvisited.size()];
        for (Integer s : unvisited){
            if (g.isArc(currentVertex, s))
                candidates[nbCandidates++] = s;
        }
    }

    @Override
    public boolean hasNext() {
        return nbCandidates > 0;
    }

    @Override
    public Integer next() {
        nbCandidates--;
        return candidates[nbCandidates];
    }

    @Override
    public void remove() {}

}
