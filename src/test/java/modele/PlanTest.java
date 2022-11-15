package modele;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanTest {

    @Test
    public void testerTronconsSortiesParIntersection() {
        // given
        Plan plan = new Plan();
        Intersection i1 = new Intersection("1", 10, 10);
        Intersection i2 = new Intersection("2", 20, 20);
        Intersection i3 = new Intersection("3", 30, 30);
        Intersection i4 = new Intersection("4", 40, 40);
        plan.ajouterIntersection(i1);
        plan.ajouterIntersection(i2);
        plan.ajouterIntersection(i3);
        plan.ajouterIntersection(i4);

        plan.ajouterTroncon(new Troncon("x", 1, i1, i2));
        plan.ajouterTroncon(new Troncon("y", 3, i1, i3));
        plan.ajouterTroncon(new Troncon("z", 3, i1, i4));
        plan.ajouterTroncon(new Troncon("a", 3, i2, i4));

        int sizeExpectedI1 = 3;
        int sizeExpectedI2 = 1;
        int sizeExpectedI3 = 0;
        int sizeExpectedI4 = 0;

        // when

        int sizeCalculatedI1 = plan.listerTronconsSortieParIntersection(i1).size();
        int sizeCalculatedI2 = plan.listerTronconsSortieParIntersection(i2).size();
        int sizeCalculatedI3 = plan.listerTronconsSortieParIntersection(i3).size();
        int sizeCalculatedI4 = plan.listerTronconsSortieParIntersection(i3).size();

        // then
        assertAll(
                () -> assertEquals(sizeExpectedI1, sizeCalculatedI1),
                () -> assertEquals(sizeExpectedI2, sizeCalculatedI2),
                () -> assertEquals(sizeExpectedI3, sizeCalculatedI3),
                () -> assertEquals(sizeExpectedI4, sizeCalculatedI4)
        );
    }

    @Test
    public void testerPlusCourtChemin() {
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
        plan.ajouterTroncon(new Troncon("a", 2, i3, i4));
        plan.ajouterTroncon(new Troncon("a", 2, i4, i3));
        plan.ajouterTroncon(new Troncon("a", 3, i5, i1));
        plan.ajouterTroncon(new Troncon("a", 3, i1, i5));
        plan.ajouterTroncon(new Troncon("a", 3, i1, i2));

        List<String> listeDest = new ArrayList<>();
        listeDest.add(i3.getId());
        listeDest.add(i4.getId());
        Map<String, Dijkstra> resultatExec = plan.plusCourtChemin(i1.getId(), listeDest);

        // expected
        Map<String, Dijkstra> expected = new HashMap<>();

        List<Troncon> chemin1 = new ArrayList<>();
        chemin1.add(new Troncon("y", 3, i1, i3));
        Dijkstra d1 = new Dijkstra(i1.getId(), i3.getId(), (double) 3, chemin1);

        List<Troncon> chemin2 = new ArrayList<>();
        chemin2.add(new Troncon("y", 3, i1, i3));
        chemin2.add(new Troncon("a", 2, i3, i4));
        Dijkstra d2 = new Dijkstra(i1.getId(), i4.getId(), (double) 5, chemin2);

        expected.put(i3.getId(), d1);
        expected.put(i4.getId(), d2);

        //test
        assertEquals(expected, resultatExec);
    }
}
