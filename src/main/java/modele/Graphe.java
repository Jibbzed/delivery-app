package modele;

public interface Graphe {
    /**
     * @return the number of vertices in <code>this</code>
     */
    public abstract int getNbSommets();

    /**
     * @param i
     * @param j
     * @return the cout of arc (i,j) if (i,j) is an arc; -1 otherwise
     */
    public abstract double getCout(int i, int j);

    /**
     * @param i
     * @param j
     * @return true if <code>(i,j)</code> is an arc of <code>this</code>
     */
    public abstract boolean isArc(int i, int j);

}
