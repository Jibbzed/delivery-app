package modele;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculTourneeTest {
    @Test
    public void testerInitGraphe() {
        // given
        Plan plan = new Plan();
        Intersection i1 = new Intersection("1", 10, 10);
        Intersection i2 = new Intersection("2", 20, 20);
        Intersection i3 = new Intersection("3", 30, 30);
        Intersection i4 = new Intersection("4", 40, 40);
        Intersection i5 = new Intersection("5", 50, 50);
        plan.ajouterIntersection(i1);
        plan.ajouterIntersection(i2);
        plan.ajouterIntersection(i3);
        plan.ajouterIntersection(i4);
        plan.ajouterIntersection(i5);

        plan.ajouterTroncon(new Troncon("x", 1, i2, i1));
        plan.ajouterTroncon(new Troncon("y", 3, i1, i3));
        plan.ajouterTroncon(new Troncon("z", 6, i1, i4));
        plan.ajouterTroncon(new Troncon("a", 3, i2, i4));
        plan.ajouterTroncon(new Troncon("b", 2, i3, i4));
        plan.ajouterTroncon(new Troncon("c", 2, i4, i3));
        plan.ajouterTroncon(new Troncon("d", 3, i5, i1));
        plan.ajouterTroncon(new Troncon("e", 3, i1, i5));
        plan.ajouterTroncon(new Troncon("f", 3, i1, i2));
        plan.ajouterTroncon(new Troncon("g", 3, i4, i2));

        Coursier grinardo = new Coursier("Bonifaccio", "Grinardo");
        Map<String, Livraison> livraisons = new HashMap<>();
        livraisons.put(i4.getId(), new Livraison(i4, grinardo, 8));
        livraisons.put(i3.getId(), new Livraison(i3, grinardo, 10));

        CalculTournee algo = new CalculTournee(plan, i1, livraisons);

        GrapheComplet graphe = algo.initGraphe();

        // expected
        double[] coutsi1 = {0, Double.MAX_VALUE, 5};
        double[] coutsi3 = {6, 0, Double.MAX_VALUE};
        double[] coutsi4 = {Double.MAX_VALUE, 2, 0};
        double[][] couts = {coutsi1, coutsi3, coutsi4};
        GrapheComplet grapheAttendu = new GrapheComplet(couts);

        // test
        assertEquals(grapheAttendu, graphe);
    }

    @Test
    public void testerCalculTournee() {
        // given
        Plan plan = new Plan();
        Intersection i1 = new Intersection("1", 10, 10);
        Intersection i2 = new Intersection("2", 20, 20);
        Intersection i3 = new Intersection("3", 30, 30);
        Intersection i4 = new Intersection("4", 40, 40);
        Intersection i5 = new Intersection("5", 50, 50);
        plan.ajouterIntersection(i1);
        plan.ajouterIntersection(i2);
        plan.ajouterIntersection(i3);
        plan.ajouterIntersection(i4);
        plan.ajouterIntersection(i5);

        plan.ajouterTroncon(new Troncon("x", 1, i2, i1));
        plan.ajouterTroncon(new Troncon("y", 3, i1, i3));
        plan.ajouterTroncon(new Troncon("z", 6, i1, i4));
        plan.ajouterTroncon(new Troncon("a", 3, i2, i4));
        plan.ajouterTroncon(new Troncon("b", 2, i3, i4));
        plan.ajouterTroncon(new Troncon("c", 2, i4, i3));
        plan.ajouterTroncon(new Troncon("d", 3, i5, i1));
        plan.ajouterTroncon(new Troncon("e", 3, i1, i5));
        plan.ajouterTroncon(new Troncon("f", 3, i1, i2));
        plan.ajouterTroncon(new Troncon("g", 3, i4, i2));

        Coursier grinardo = new Coursier("Bonifaccio", "Grinardo");
        Map<String, Livraison> livraisons = new HashMap<>();
        livraisons.put(i4.getId(), new Livraison(i4, grinardo, 8));
        livraisons.put(i3.getId(), new Livraison(i3, grinardo, 10));

        CalculTournee algo = new CalculTournee(plan, i1, livraisons);

        Tournee tourneeCalculee = algo.calculerTournee();

        // expected
        // tournee calculee a la main sur un exemple simple pour valider le TSP
        List<Troncon> traj1 = new ArrayList<>();
        traj1.add(new Troncon("y", 3, i1, i3));
        traj1.add(new Troncon("b", 2, i3, i4));
        Livraison l1 = new Livraison(i1, i4, grinardo, traj1, LocalTime.of(8,20));

        List<Troncon> traj2 = new ArrayList<>();
        traj2.add(new Troncon("c", 2, i4, i3));
        Livraison l2 = new Livraison(i4, i3, grinardo, traj2, LocalTime.of(10,0));

        List<Troncon> traj3 = new ArrayList<>();
        traj3.add(new Troncon("b", 2, i3, i4));
        traj3.add(new Troncon("g", 3, i4, i2));
        traj3.add(new Troncon("x", 1, i2, i1));
        Livraison l3 = new Livraison(i3, i1, grinardo, traj3, LocalTime.of(10,24));

        List<Livraison> livraisonsTournee = new ArrayList<>();
        livraisonsTournee.add(l1);
        livraisonsTournee.add(l2);
        livraisonsTournee.add(l3);
        Tournee attendue = new Tournee(livraisonsTournee, grinardo);

        // test
        assertEquals(attendue, tourneeCalculee);
    }

}
