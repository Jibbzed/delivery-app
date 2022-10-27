package modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoursierTest {

    // Création de coursier
    @Test
    public void testCreerCoursier() {
        Coursier coursierTest = new Coursier("BONIFACIO", "Grinardo", false);

        String message = "Coursier{nom='BONIFACIO', prenom='Grinardo', planifie=false}";

        assertEquals(message, coursierTest.toString());
    }

    // Planification de coursier
    @Test
    public void testPlanifierCoursier() {
        Coursier coursierTest = new Coursier("BONIFACIO", "Grinardo", false);

        coursierTest.setPlanifie(true);
        Coursier coursierVerif = new Coursier("BONIFACIO", "Grinardo", true);

        assertEquals(coursierVerif, coursierTest);
        assertTrue(coursierTest.getPlanifie());
    }
}
