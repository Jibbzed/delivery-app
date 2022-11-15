package modele;

public interface Graphe {
    /**
     * @return nombre de sommets dans le graphe (<code>this</code>)
     */
    public abstract int getNbSommets();

    /**
     * @param i indice du sommet n°1
     * @param j indice du sommet n°2
     *
     * @return cout de l'arc <code>(i,j)</code> si <code>(i,j)</code> est un arc du graphe; -1 sinon
     */
    public abstract double getCout(int i, int j);

    /**
     * @param i indice du sommet n°1
     * @param j indice du sommet n°2
     *
     * @return <code>true</code> si <code>(i,j)</code> est un arc du graphe (<code>this</code>)
     */
    public abstract boolean isArc(int i, int j);

}
