package modele;

import java.util.Map;

public class GrapheComplet implements Graphe {
    int nbSommets;
    double[][] cout;

    /**
     * Create a complete directed graph such that each edge has a weight within [MIN_COST,MAX_COST]
     * @param plusCourtsChemins
     */
    public GrapheComplet(Map<String, Map<String, Dijkstra>> plusCourtsChemins, Map<String, Integer> mapping){
        this.nbSommets = mapping.size();
        for( String idOrigine : plusCourtsChemins.keySet() ) {
            int origine = mapping.get(idOrigine);
            Map<String, Dijkstra> chemins = plusCourtsChemins.get(idOrigine);
            for( String idDest : chemins.keySet() ) {
                int dest = mapping.get(idDest);
                cout[origine][dest] = chemins.get(idDest).getDistance();
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

    @Override
    public boolean isArc(int i, int j) {
        if (i<0 || i>= nbSommets || j<0 || j>= nbSommets)
            return false;
        return i != j;
    }

}
