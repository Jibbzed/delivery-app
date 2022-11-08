package modele;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculTournee {
    private Plan plan;
    private Intersection entrepot;
    private Map<String, Livraison> livraisons;
    private TSP tsp;
    private Map<String, Map<String, Dijkstra>> plusCourtsChemins;

    public CalculTournee(Plan p, Intersection entrepot, Map<String, Livraison> livraisons) {
        this.plan = p;
        this.entrepot = entrepot;
        this.livraisons = livraisons;
        this.tsp = new TSP1();
        this.plusCourtsChemins = new HashMap<>();
    }

    public GrapheComplet initGraphe() {
        // on associe chaque point de livraison à un id entre 1 et n (0 = entrepot) qui nous permettra de faciliter l'usage du TSP
        Map<String, Integer> mappingIdInt = new HashMap<>();
        mappingIdInt.put(entrepot.getId(), 0);
        int count = 1;
        for( String id : livraisons.keySet() ) {
            mappingIdInt.put(id, count);
            count += 1;
        }

        // on va calculer tous les plus courts chemins de chaque point à chaque point
        List<String> destinations = new ArrayList<>();

        // entre l'entrepot et les points de livraison
        for( String id : livraisons.keySet() ) { destinations.add(id); }
        plusCourtsChemins.put(entrepot.getId(), plan.plusCourtChemin(entrepot.getId(), destinations));

        // entre chaque point de livraison et les autres + entrepôt
        for( String id : livraisons.keySet() ) {
            destinations = new ArrayList<>();
            destinations.add(entrepot.getId());
            for( String str : livraisons.keySet() ) {
                if( str != id ) { destinations.add(str); }
            }
            plusCourtsChemins.put(id, plan.plusCourtChemin(id, destinations));
        }

        // on crée le graphe à partir des plus courts chemins
        GrapheComplet graphe = new GrapheComplet(plusCourtsChemins, mappingIdInt);

        // une fois le graphe créé, on va prendre en compte les time windows en mettant un poids très élevé sur certains arcs
        for( Livraison l : livraisons.values() ) {
            for( Livraison livr : livraisons.values() ) {
                if( (l.getFenetreHoraireLivr().map(h -> h+1)) == livr.getFenetreHoraireLivr() ) {
                    graphe.setCout(mappingIdInt.get(livr.getDestinationLivraison().getId()), mappingIdInt.get(l.getDestinationLivraison().getId()), Double.MAX_VALUE);
                }else if ( (l.getFenetreHoraireLivr().get()+1) < livr.getFenetreHoraireLivr().get() ) {
                    graphe.setCout(mappingIdInt.get(livr.getDestinationLivraison().getId()), mappingIdInt.get(l.getDestinationLivraison().getId()), Double.MAX_VALUE);
                    graphe.setCout(mappingIdInt.get(l.getDestinationLivraison().getId()), mappingIdInt.get(livr.getDestinationLivraison().getId()), Double.MAX_VALUE);
                }
            }
        }

        return graphe;
    }

    public Tournee calculerTournee() {
        // on associe chaque id du TSP au point de livraison correspondant pour inverser le mapping de initGraphe
        Map<Integer, String> mappingIntId = new HashMap<>();
        mappingIntId.put(0, entrepot.getId());
        int count = 1;
        for( String id : livraisons.keySet() ) {
            mappingIntId.put(count, id);
            count += 1;
        }

        Graphe pcc = initGraphe();

        // on applique le TSP au graphe
        tsp.searchSolution(20000, pcc);

        double coutTotalTournee = tsp.getSolutionCost();

        // on récupère la solution calculée par le TSP
        List<String> tournee = new ArrayList<>();
        for (int i=0; i<pcc.getNbSommets(); i++) {
            tournee.add(mappingIntId.get(tsp.getSolution(i)));
        }

        // à partir de la solution calculée, on va tout transformer en une liste de livraisons et créer la tournée associée
        LocalTime heureDepart = LocalTime.of(8,0);
        List<Livraison> livraisonsTournee = new ArrayList<>();
        for( int i=0; i<(tournee.size()-1); i++ ) {
            String idOrigine = tournee.get(i);
            String idDest = tournee.get(i+1);
            Intersection origine = plan.getIntersectionParId(idOrigine);
            Intersection dest = plan.getIntersectionParId(idDest);
            List<Troncon> trajet = plusCourtsChemins.get(idOrigine).get(idDest).getChemin();

            // on calcule l'heure d'arrivée de la livraison = heure de départ + temps de trajet
            double dist = 0;
            for( Troncon t : trajet ) {
                dist += t.getLongueur();
            }
            double duree = dist/15; // on considère que le livreur se déplace à 15km/h
            int dureeHeure = (int)duree;
            int dureeMinutes = (int)((duree-dureeHeure)*60);
            LocalTime heureArrivee = heureDepart.plusHours(dureeHeure);
            heureArrivee = heureArrivee.plusMinutes(dureeMinutes);
            // si l'heure d'arrivée d'une livraison est avant le début de sa time window alors le livreur patiente => heure d'arrivée = début de la time window
            if( heureArrivee.isBefore(LocalTime.of(livraisons.get(idOrigine).getFenetreHoraireLivr().get(),0)) ) {
                heureArrivee = LocalTime.of(livraisons.get(idOrigine).getFenetreHoraireLivr().get(), 0);
            }
            // une livraison prend 5 minutes donc le livreur repart 5 minutes après être arrivé au point de livraison
            heureDepart = heureArrivee.plusMinutes(5);

            Livraison livr = new Livraison(origine, dest, trajet, heureArrivee);
            livraisonsTournee.add(livr);
        }
        String idOrigine = tournee.get(tournee.size()-1);
        String idDest = tournee.get(0);
        Intersection origine = plan.getIntersectionParId(idOrigine);
        Intersection dest = plan.getIntersectionParId(idDest);
        List<Troncon> trajet = plusCourtsChemins.get(idOrigine).get(idDest).getChemin();
        double dist = 0;
        for( Troncon t : trajet ) {
            dist += t.getLongueur();
        }
        double duree = dist/15; // on considère que le livreur se déplace à 15km/h
        int dureeHeure = (int)duree;
        int dureeMinutes = (int)((duree-dureeHeure)*60);
        LocalTime heureArrivee = heureDepart.plusHours(dureeHeure);
        heureArrivee = heureArrivee.plusMinutes(dureeMinutes);
        Livraison livr = new Livraison(origine, dest, trajet, heureArrivee);
        livraisonsTournee.add(livr);

        Tournee tourneeCalculee = new Tournee(livraisonsTournee);
        return tourneeCalculee;
    }
}
