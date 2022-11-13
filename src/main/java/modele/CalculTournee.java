package modele;

import java.time.LocalTime;
import java.util.*;

public class CalculTournee {
    private Plan plan;
    private Intersection entrepot;
    private Map<String, Livraison> livraisons;
    private TSP tsp;
    private Map<String, Map<String, Dijkstra>> plusCourtsChemins;
    private Coursier coursierTournee;

    public CalculTournee(Plan p, Intersection entrepot, Map<String, Livraison> livraisons) {
        this.plan = p;
        this.entrepot = entrepot;
        this.livraisons = livraisons;
        this.tsp = new TSP2();
        this.plusCourtsChemins = new HashMap<>();
        // le coursier associé à la tournée calculée est le coursier de la première livraison (le même pour toutes les autres)
        this.coursierTournee = livraisons.values().stream().findFirst().get().getCoursierLivraison().get();
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
        destinations.addAll(livraisons.keySet());
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
        // on "enlève" l'arc entre ei et ej si ej est dans la time window consécutive à celle de ei (on ne peut pas livrer 9h avant 8h30)
        // si ei et ej sont dans des time windows disjointes, alors on coupe l'arc entre ei et ej mais aussi entre ej et ei
        // /!\ si on a une time window qui est vide, il faut spécifier : si livraison 8h, livraison 10h, mais pas 9h, il ne faut pas couper les arcs sinon on ne peut plus compléter la tournée
        // ne pas oublier l'entrepôt : on ne garde que les liens de entrepôt vers livraisons de la première time window et de livraisons de la dernière time window vers entrepôt
        // on a donc besoin de connaître l'enchaînement des time windows

        // on récupère les time windows, et on les ordonne
        List<Integer> time_windows = new ArrayList<>();
        for( Livraison l : livraisons.values() ) {
            if( !time_windows.contains(l.getFenetreHoraireLivr().get()) ) { time_windows.add(l.getFenetreHoraireLivr().get()); }
        }
        Collections.sort(time_windows);

        // on fait les suppressions successives en fonction des time windows
        for( Livraison l : livraisons.values() ) {
            // on commence par les arcs avec entrepôt
            if( !l.getFenetreHoraireLivr().get().equals(time_windows.get(0)) ) { // si la livraison n'est pas dans la première time window
                graphe.setCout(0, mappingIdInt.get(l.getDestinationLivraison().getId()), Double.MAX_VALUE);
            }
            if( !l.getFenetreHoraireLivr().get().equals(time_windows.get(time_windows.size()-1)) ) { // si la livraison n'est pas dans la dernière time window
                graphe.setCout(mappingIdInt.get(l.getDestinationLivraison().getId()), 0, Double.MAX_VALUE);
            }
            // puis les arcs entre les points de livraison
            for( Livraison livr : livraisons.values() ) {
                if( !l.equals(livr) ) {
                    if( time_windows.indexOf(l.getFenetreHoraireLivr().get()) == time_windows.indexOf(livr.getFenetreHoraireLivr().get()) ) {
                    } else if( time_windows.indexOf(l.getFenetreHoraireLivr().get()) + 1 == time_windows.indexOf(livr.getFenetreHoraireLivr().get()) ) {
                        graphe.setCout(mappingIdInt.get(livr.getDestinationLivraison().getId()), mappingIdInt.get(l.getDestinationLivraison().getId()), Double.MAX_VALUE);
                    } else if( time_windows.indexOf(l.getFenetreHoraireLivr().get()) == time_windows.indexOf(livr.getFenetreHoraireLivr().get()) + 1 ) {
                        graphe.setCout(mappingIdInt.get(l.getDestinationLivraison().getId()), mappingIdInt.get(livr.getDestinationLivraison().getId()), Double.MAX_VALUE);
                    } else {
                        graphe.setCout(mappingIdInt.get(livr.getDestinationLivraison().getId()), mappingIdInt.get(l.getDestinationLivraison().getId()), Double.MAX_VALUE);
                        graphe.setCout(mappingIdInt.get(l.getDestinationLivraison().getId()), mappingIdInt.get(livr.getDestinationLivraison().getId()), Double.MAX_VALUE);
                    }
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

        // on initialise le graphe des plus courts chemins
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
            Livraison temp = livraisons.get(idDest);
            if( heureArrivee.isBefore(LocalTime.of(livraisons.get(idDest).getFenetreHoraireLivr().get(),0)) ) {
                heureArrivee = LocalTime.of(livraisons.get(idDest).getFenetreHoraireLivr().get(), 0);
            }
            // une livraison prend 5 minutes donc le livreur repart 5 minutes après être arrivé au point de livraison
            heureDepart = heureArrivee.plusMinutes(5);

            Livraison livr = new Livraison(origine, dest, coursierTournee, trajet, heureArrivee);
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
        Livraison livr = new Livraison(origine, dest, coursierTournee, trajet, heureArrivee);
        livraisonsTournee.add(livr);

        Tournee tourneeCalculee = new Tournee(livraisonsTournee, coursierTournee);
        return tourneeCalculee;
    }
}
