package service;

import modele.Coursier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceCoursierTest {

    private ServiceCoursier serviceCoursier;

    // Création et remplissage de la liste des coursiers
    @Test
    public void testRemplirListe() {

        // A l'initialisation
        ServiceCoursier service = new ServiceCoursier();
        assertEquals(8, service.getListeCoursiers().size());

        // A l'ajout d'un coursier
        Coursier coursierAjout = new Coursier("BONIFACIA", "Grinarda");
        service.ajouterCoursier(coursierAjout);

        assertEquals(9, service.getListeCoursiers().size());
        assertTrue(service.getListeCoursiers().contains(coursierAjout));
    }

    @Test
    public void testRetirerCoursierNom() {
        // given
        serviceCoursier = new ServiceCoursier();
        Coursier coursierAretirer = serviceCoursier.getListeCoursiers().stream().findAny().get();

        // when
        serviceCoursier.retirerCoursier(coursierAretirer.getNom(), coursierAretirer.getPrenom());

        // then
        assertAll(
                () -> assertEquals(8 - 1, serviceCoursier.getListeCoursiers().size()),
                () -> assertFalse(serviceCoursier.getListeCoursiers().contains(coursierAretirer))
        );

    }

    // Suppression de la liste des coursiers
    @Test
    public void testRetirerCoursierObjet() {
        ServiceCoursier service = new ServiceCoursier();
        Coursier coursierRetire = service.getListeCoursiers().get(0);

        // test avec l'objet client
        service.retirerCoursier(coursierRetire);
        assertFalse(service.getListeCoursiers().contains(coursierRetire));

        assertEquals(7, service.getListeCoursiers().size());
    }


}
