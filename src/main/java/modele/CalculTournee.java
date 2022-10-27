package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculTournee {
    private Plan plan;
    private Intersection entrepot;
    private List<Intersection> pointsLivraison;
    private TSP tsp;
    private Map<String, Map<String, Dijkstra>> plusCourtsChemins;

    public CalculTournee(Plan p, Intersection entrepot, List<Intersection> pointsLivraison) {
        this.plan = p;
        this.entrepot = entrepot;
        this.pointsLivraison = pointsLivraison;
        this.tsp = new TSP1();
        this.plusCourtsChemins = new HashMap<>();
    }

    public GrapheComplet initGraphe() {
        Map<String, Integer> mappingIdInt = new HashMap<>();
        mappingIdInt.put(entrepot.getId(), 0);
        int count = 1;
        for( Intersection i : pointsLivraison ) {
            mappingIdInt.put(i.getId(), count);
            count += 1;
        }

        List<String> destinations = new ArrayList<>();

        for( Intersection i : pointsLivraison ) { destinations.add(i.getId()); }
        plusCourtsChemins.put(entrepot.getId(), plan.plusCourtChemin(entrepot.getId(), destinations));

        for( Intersection i : pointsLivraison ) {
            destinations = new ArrayList<>();
            destinations.add(entrepot.getId());
            for( Intersection inter : pointsLivraison ) {
                if( inter != i ) { destinations.add(i.getId()); }
            }
            plusCourtsChemins.put(i.getId(), plan.plusCourtChemin(i.getId(), destinations));
        }

        GrapheComplet graphe = new GrapheComplet(plusCourtsChemins, mappingIdInt);
        return graphe;
    }

    public Tournee calculerTournee() {
        Map<Integer, String> mappingIntId = new HashMap<>();
        mappingIntId.put(0, entrepot.getId());
        int count = 1;
        for( Intersection i : pointsLivraison ) {
            mappingIntId.put(count, i.getId());
            count += 1;
        }

        Graphe pcc = initGraphe();

        tsp.searchSolution(20000, pcc);

        double coutTotalTournee = tsp.getSolutionCost();
        List<String> tournee = new ArrayList<>();
        for (int i=0; i<pcc.getNbSommets(); i++) {
            tournee.add(mappingIntId.get(tsp.getSolution(i)));
        }

        List<Livraison> livraisons = new ArrayList<>();
        for( int i=0; i<(tournee.size()-1); i++ ) {
            String idOrigine = tournee.get(i);
            String idDest = tournee.get(i+1);
            Intersection origine = plan.getIntersectionParId(idOrigine);
            Intersection dest = plan.getIntersectionParId(idDest);
            List<Troncon> trajet = plusCourtsChemins.get(idOrigine).get(idDest).getChemin();
            Livraison livr = new Livraison(origine, dest, trajet);
            livraisons.add(livr);
        }
        String idOrigine = tournee.get(tournee.size()-1);
        String idDest = tournee.get(0);
        Intersection origine = plan.getIntersectionParId(idOrigine);
        Intersection dest = plan.getIntersectionParId(idDest);
        List<Troncon> trajet = plusCourtsChemins.get(idOrigine).get(idDest).getChemin();
        Livraison livr = new Livraison(origine, dest, trajet);
        livraisons.add(livr);

        Tournee tourneeCalculee = new Tournee(livraisons);
        return tourneeCalculee;
    }
}
