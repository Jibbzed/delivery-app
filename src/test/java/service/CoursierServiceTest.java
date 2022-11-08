package service;

import modele.Coursier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoursierServiceTest {

    private ServiceCoursier serviceCoursier;

    @Test
    public void testConstructuerCoursierService() {
        // given
        int nombreDeCoursiers = 2;

        // when
        serviceCoursier = new ServiceCoursier(nombreDeCoursiers);

        // then
        assertEquals(serviceCoursier.getListeCoursiers().size(), 2);


    }

    //    @Test
    //    public void testAjouterCoursier() {
    //        // given
    //        int nombreDeCoursiers = 2;
    //        serviceCoursier = new ServiceCoursier(nombreDeCoursiers);
    //
    //        // when
    //        serviceCoursier.ajouterCoursier();
    //
    //        // then
    //        assertEquals(nombreDeCoursiers + 1, serviceCoursier.getListeCoursiers().size());
    //    }

    @Test
    public void testRetirerCoursier() {
        // given
        int nombreDeCoursiers = 2;
        serviceCoursier = new ServiceCoursier(nombreDeCoursiers);
        Coursier coursierAretirer = serviceCoursier.getListeCoursiers().stream().findAny().get();

        // when
        serviceCoursier.retirerCoursier(coursierAretirer.getNom(), coursierAretirer.getPrenom());

        // then
        assertAll(
                () -> assertEquals(nombreDeCoursiers - 1, serviceCoursier.getListeCoursiers().size()),
                () -> assertFalse(serviceCoursier.getListeCoursiers().contains(coursierAretirer))
        );

    }


}
