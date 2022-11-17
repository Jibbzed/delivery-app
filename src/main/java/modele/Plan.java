package modele;

import java.util.*;
import java.util.stream.Collectors;

public class Plan {
    private Set<Troncon> troncons;
    private Map<String, Intersection> intersections;

    /**
     * Constructeur par défaut de la classe.
     */
    public Plan() {
        troncons = new HashSet<>();
        intersections = new HashMap<>();
    }

    /**
     * Constructeur paramétré de la classe.
     *
     * @param troncons      collection de tous les <code>Troncon</code> du plan
     * @param intersections collection de toutes les <code>Intersection</code> du plan
     *
     * @see Troncon
     * @see Intersection
     */
    public Plan(Collection troncons, Map intersections) {
        this();
        this.troncons.addAll(troncons);
        this.intersections.putAll(intersections);
    }

    /**
     * Permet de récupérer tous les <code>Troncon</code> correspondant à une <code>Intersection</code>.
     *
     * @param intersection <code>Intersection</code> dont on veut récupérer les <code>Troncon</code> associés
     *
     * @return liste des <code>Troncon</code> correspondant à intersection
     *
     * @see Troncon
     * @see Intersection
     */
    public Set<Troncon> listerTronconsSortieParIntersection(Intersection intersection){
        return this.troncons.stream().filter(t-> t.getOrigine().equals(intersection)).collect(Collectors.toSet());
    }

    public String listerTronconsParIntersection(Intersection intersection){
        String noms = "";
        List<String> nomsTroncons = new ArrayList<>();
        for (Troncon t : this.troncons) {
            if (t.getOrigine().equals(intersection)||t.getDestination().equals(intersection)) {
                if(!nomsTroncons.contains(t.getNom())) {
                    nomsTroncons.add(t.getNom());
                }
            }
        }

        if(nomsTroncons.size()==1) {
            noms = nomsTroncons.get(0);
        } else {
            noms = "Intersection entre " + nomsTroncons.get(0) + " et " + nomsTroncons.get(1);
        }
        return noms;
    }

    /**
     * Ajoute un <code>Troncon</code>> à la collection des <code>Troncon</code> du plan.
     *
     * @param troncon <code>Troncon</code> à ajouter
     *
     * @see Troncon
     */
    public void ajouterTroncon(Troncon troncon){
       validTroncon(troncon);
       this.troncons.add(troncon);
    }

    private void validTroncon(Troncon troncon) {
        if( (this.intersections.get(troncon.getOrigine()) != null )
                || (this.intersections.get(troncon.getDestination()) != null ) ){
            // TODO: Est-ce qu'on a besoin de ce check, si on ajoute des troncons que depuis fichier xml
            // on a deja fais le check sur le ParserPlan avant de creer un nouveau troncon.
            throw new IllegalArgumentException("echec d'ajout du troncon: troncon avec un origine ou destination invalide.");
        }
    }

    /**
     * Ajoute un <code>Intersection</code>> à la collection des <code>Intersection</code> du plan.
     *
     * @param intersection <code>Intersection</code> à ajouter
     *
     * @see Intersection
     */
    public void ajouterIntersection(Intersection intersection) {
        this.intersections.put(intersection.getId(), intersection);
    }

    public void retirerIntersection(Intersection intersection) {
        this.troncons = this.troncons.stream()
                            .filter(troncon ->
                                        !(troncon.getOrigine().equals(intersection))
                                                && !(troncon.getDestination().equals(intersection)))
                            .collect(Collectors.toSet());
        this.intersections.remove(intersection);
    }

    public void retirerTroncon(Troncon troncon) {
        this.troncons.remove(troncon);
    }

    public Set<Troncon> getTroncons() {
        return new HashSet<>(troncons);
    }

    public Map<String, Intersection> getIntersections() {
        return new HashMap<>(intersections);
    }

    /**
     * Calcule le plus court chemin entre un point origine et différents points destination.
     *
     * @param idOrigine      identifiant de l'<code>Intersection</code> par rapport à laquelle on veut calculer les distances
     * @param idDestinations liste des identifiants des <code>Intersection</code> vers lesquelles ont veut calculer la distance
     *
     * @return plus court chemin de l'origine à chacune des destinations (mappés avec les identifiants)
     *
     * @see Intersection
     */
    public Map<String, Dijkstra> plusCourtChemin(String idOrigine, List<String> idDestinations) {
        Double distMax = Double.MAX_VALUE;

        Map<String, Double> distances = new HashMap<>();
        Map<String, Troncon> parents = new HashMap<>();

        //Initialisation du tableau des distances
        intersections.forEach((id, intersection)-> distances.put(id, distMax));
        //Initialisation pour l'origine
        distances.put(idOrigine, (double) 0);
        parents.put(idOrigine, null);

        Set<String> noeudsBlancs = new HashSet<>();
        Set<String> noeudsGris = new HashSet<>();
        Set<String> noeudsNoirs = new HashSet<>();

        noeudsBlancs.addAll(intersections.keySet());
        noeudsBlancs.remove(idOrigine);
        noeudsGris.add(idOrigine);

        while( !noeudsGris.isEmpty() ) {
            //On récupère le noeud gris avec la plus petite distance
            String noeudCourant = noeudsGris.stream().sorted(Comparator.comparing(distances::get)).findFirst().orElse("");
            /*String noeudCourant = null;
            Integer max = Integer.MAX_VALUE;
            for ( String s : noeudsGris ) {
                if ( distances.get(s) < max ) {
                    noeudCourant = s;
                    max = distances.get(s);
                }
            }*/
            Set<Troncon> successeurs = listerTronconsSortieParIntersection(intersections.get(noeudCourant));
            for( Troncon t : successeurs ) {
                String dest = t.getDestination().getId();
                if( noeudsBlancs.contains(dest) || noeudsGris.contains(dest) ) {
                    Double longueur = t.getLongueur();
                    if (distances.get(dest) > (distances.get(noeudCourant) + longueur)) {
                        parents.put(dest, t);
                        distances.put(dest, distances.get(noeudCourant) + longueur);
                    }
                    if (noeudsBlancs.contains(dest)) {
                        noeudsGris.add(dest);
                        noeudsBlancs.remove(dest);
                    }
                }
            }
            noeudsNoirs.add(noeudCourant);
            noeudsGris.remove(noeudCourant);
            Boolean exitDest = true;
            for( String id : idDestinations ) {
                if( !noeudsNoirs.contains(id) ) { exitDest = false; }
            }
            Boolean exitOrigin = noeudsNoirs.contains(idOrigine);
            if( exitOrigin && exitDest ) { break; }
        }

        Map<String, Dijkstra> res = new HashMap<>();
        for ( String id : idDestinations ) {
            List<Troncon> chemin = getChemin(idOrigine, id, parents);
            res.put(id, new Dijkstra(idOrigine, id, distances.get(id), chemin));
        }

        return res;
    }

    protected List<Troncon> getChemin(String idOrigine, String id, Map<String, Troncon> parents) {
        List<Troncon> chemin = new ArrayList<>();
        String idActuel = id;
        Troncon tronconActuel = parents.get(id);
        do {
            chemin.add(tronconActuel);
            idActuel = tronconActuel.getOrigine().getId();
            tronconActuel = parents.get(idActuel);
        } while ( !idActuel.equals(idOrigine) );

        Collections.reverse(chemin);
        return chemin;
    }

    /**
     * Retourne l'<code>Intersection</code> correspondant à l'identifiant.
     *
     * @param idInter identifiant de l'<code>Intersection</code> à rechercher
     *
     * @return <code>Intersection</code> correspondant à idInter
     */
    public Intersection getIntersectionParId(String idInter) {
        return intersections.get(idInter);
    }

    public Intersection getIntersectionProche(double latitude, double longitude) {
    	Intersection intersection = null;
    	double distanceMin = Double.MAX_VALUE;
    	for(Intersection inter : intersections.values()) {
    		double distance = inter.calculerDistance(latitude, longitude);
    		if(distance < distanceMin) {
    			distanceMin = distance;
    			intersection = inter;
    		}
    	}
    	return intersection;
    }


}
