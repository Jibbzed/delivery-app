package modele;

import org.junit.jupiter.api.Test;

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
}
