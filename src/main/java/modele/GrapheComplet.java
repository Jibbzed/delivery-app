package modele;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class GrapheComplet implements Graphe {
    int nbSommets;
    double[][] cout;

    /**
     * Create a complete directed graph such that each edge has a weight within [MIN_COST,MAX_COST]
     * @param plusCourtsChemins
     */
    public GrapheComplet(Map<String, Map<String, Dijkstra>> plusCourtsChemins, Map<String, Integer> mapping){
        this.nbSommets = mapping.size();
        this.cout = new double[nbSommets][nbSommets];
        for( String idOrigine : plusCourtsChemins.keySet() ) {
            int origine = mapping.get(idOrigine);
            Map<String, Dijkstra> chemins = plusCourtsChemins.get(idOrigine);
            for( String idDest : chemins.keySet() ) {
                int dest = mapping.get(idDest);
                cout[origine][dest] = chemins.get(idDest).getDistance();
            }
            cout[origine][origine]=0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrapheComplet that = (GrapheComplet) o;
        return nbSommets == that.nbSommets && Arrays.deepEquals(cout, that.cout);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nbSommets);
        result = 31 * result + Arrays.hashCode(cout);
        return result;
    }

    public GrapheComplet(double[][] couts) {
        this.nbSommets = couts.length;
        this.cout = new double[nbSommets][nbSommets];
        for( int i=0; i<couts.length; i++ ) {
            for(int j=0; j<couts[0].length; j++ ) {
                this.cout[i][j] = couts[i][j];
            }
        }
    }

    @Override
    public int getNbSommets() {
        return nbSommets;
    }

    @Override
    public double getCout(int i, int j) {
        if (i<0 || i>= nbSommets || j<0 || j>= nbSommets)
            return -1;
        return cout[i][j];
    }

    public void setCout(int i, int j, double cout) {
        this.cout[i][j] = cout;
    }

    @Override
    public boolean isArc(int i, int j) {
        if (i<0 || i>= nbSommets || j<0 || j>= nbSommets)
            return false;
        return i != j;
    }

}
