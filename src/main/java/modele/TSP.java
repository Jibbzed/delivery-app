package modele;

public interface TSP {
    /**
     * Recherche du plus court circuit dans le graphe.
     * Recherche du plus court circuit hamiltonien dans le <code>Graphe</code> <code>g</code>, en ne dépassant pas
     * <code>timeLimit</code> millisecondes de calcul
     * Retourne la meilleure solution trouvée lorsque la limite de temps est atteinte
     * Attention : le parcours hamiltonien démarre et termine toujours au sommet 0
     *
     * @param timeLimit temps de calcul maximal autorisé
     * @param g         <code>Graphe</code> sur lequel on applique le TSP
     *
     * @see Graphe
     */
    public void searchSolution(int timeLimit, Graphe g);

    /**
     * @param i indice du sommet que l'on cherche dans la solution
     *
     * @return le i-ème sommet visité dans la solution calculée par <code>searchSolution</code>
     * (-1 si <code>searcheSolution</code> n'a pas encore été exécuté, ou si i < 0 ou i >= nombre de sommets de <code>g</code>
     */
    public Integer getSolution(int i);

    /**
     * @return le coût total de la solution calculée par <code>searchSolution</code>
     * (-1 si <code>searcheSolution</code> n'a pas encore été exécuté)
     */
    public double getSolutionCost();

}
