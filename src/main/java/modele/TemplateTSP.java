package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class TemplateTSP implements TSP {
    private Integer[] bestSol;
    protected Graphe g;
    private double bestSolCost;
    private int timeLimit;
    private long startTime;

    public void searchSolution(int timeLimit, Graphe g){
        if (timeLimit <= 0) return;
        startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.g = g;
        bestSol = new Integer[g.getNbSommets()];
        Collection<Integer> unvisited = new ArrayList<Integer>(g.getNbSommets()-1);
        for (int i=1; i<g.getNbSommets(); i++) unvisited.add(i);
        Collection<Integer> visited = new ArrayList<Integer>(g.getNbSommets());
        visited.add(0); // The first visited vertex is 0
        bestSolCost = Integer.MAX_VALUE;
        branchAndBound(0, unvisited, visited, 0);
    }

    public Integer getSolution(int i){
        if (g != null && i>=0 && i<g.getNbSommets())
            return bestSol[i];
        return -1;
    }

    public double getSolutionCost(){
        if (g != null)
            return bestSolCost;
        return -1;
    }

    /**
     * Méthode à redéfinir dans les sous classes de TemplateTSP
     *
     * @param currentVertex indice du sommet actuellement exploré
     * @param unvisited     liste des indices des successeurs de <code>currentVertex</code>
     *
     * @return une borne inférieure pour le coût des chemins dans le graphe <code>g</code>, partant de <code>currentVertex</code>,
     * passant par tous les sommets de <code>unviisted</code>, et revenant au point de départ (sommet 0)
     */
    protected abstract double bound(Integer currentVertex, Collection<Integer> unvisited);

    /**
     * Méthode à redéfinir dans les sous classes de TemplateTSP
     *
     * @param currentVertex indice du sommet actuellement exploré
     * @param unvisited     liste des indices des successeurs de <code>currentVertex</code>
     * @param g             <code>Graphe</code> sur lequel on applique le TSP
     *
     * @return un itérateur pour parcourir tous les sommets de <code>unvisited</code> qui sont les successeurs de <code>currentVertex</code>
     *
     * @see Graphe
     */
    protected abstract Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graphe g);

    /**
     * Modèle de méthode d'un algorithme de branch and bound pour la résolution du TSP dans le <code>Graphe</code> <code>g</code>.
     *
     * @param currentVertex indice du sommet actuellement exploré
     * @param unvisited     liste des indices des successeurs de <code>currentVertex</code>
     * @param visited       liste ordonnée des sommets qui ont déjà été visités (dont <code>currentVertex</code>)
     * @param currentCost   le coût du chemin correspondant à <code>visited</code>
     *
     * @see Graphe
     */
    private void branchAndBound(int currentVertex, Collection<Integer> unvisited,
                                Collection<Integer> visited, double currentCost){
        if (System.currentTimeMillis() - startTime > timeLimit) return;
        if (unvisited.size() == 0){
            if (g.isArc(currentVertex,0)){
                if (currentCost+g.getCout(currentVertex,0) < bestSolCost){
                    visited.toArray(bestSol);
                    bestSolCost = currentCost+g.getCout(currentVertex,0);
                }
            }
        } else if (currentCost+bound(currentVertex,unvisited) < bestSolCost){
            Iterator<Integer> it = iterator(currentVertex, unvisited, g);
            while (it.hasNext()){
                Integer nextVertex = it.next();
                visited.add(nextVertex);
                unvisited.remove(nextVertex);
                branchAndBound(nextVertex, unvisited, visited,
                        currentCost+g.getCout(currentVertex, nextVertex));
                visited.remove(nextVertex);
                unvisited.add(nextVertex);
            }
        }
    }

}
