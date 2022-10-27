package modele;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

        List<Intersection> pointsLivraison = new ArrayList<>();
        pointsLivraison.add(i3);
        pointsLivraison.add(i4);

        CalculTournee algo = new CalculTournee(plan, i1, pointsLivraison);

        GrapheComplet graphe = algo.initGraphe();

        // expected
        double[] coutsi1 = {0, 3, 5};
        double[] coutsi3 = {6, 0, 2};
        double[] coutsi4 = {5, 2, 0};
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

        List<Intersection> pointsLivraison = new ArrayList<>();
        pointsLivraison.add(i3);
        pointsLivraison.add(i4);

        CalculTournee algo = new CalculTournee(plan, i1, pointsLivraison);

        Tournee tournee = algo.calculerTournee();

       // expected

    }

}
