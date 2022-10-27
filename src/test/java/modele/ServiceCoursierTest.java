package modele;

import org.junit.jupiter.api.Test;
import service.ServiceCoursier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceCoursierTest {

    // Création et remplissage de la liste des coursiers
    @Test
    public void testRemplirListe() {

        // A l'initialisation
        ServiceCoursier service = new ServiceCoursier(2);
        assertEquals(2, service.getListeCoursiers().size());

        // A l'ajout d'un coursier
        Coursier coursierAjout = new Coursier("BONIFACIO", "Grinardo");
        service.ajouterCoursier(coursierAjout);

        assertEquals(3, service.getListeCoursiers().size());
        assertTrue(service.getListeCoursiers().contains(coursierAjout));
    }


    // Suppression de la liste des coursiers
    @Test
    public void testRetirerCoursier() {
        ServiceCoursier service = new ServiceCoursier(3);
        Coursier coursierRetireNom = service.getListeCoursiers().get(0);
        Coursier coursierRetireClient = service.getListeCoursiers().get(1);

        // 2 manières de supprimer
        // test avec nom + prénom
        service.retirerCoursier(coursierRetireNom.getNom(), coursierRetireNom.getPrenom());
        assertTrue(!service.getListeCoursiers().contains(coursierRetireNom));

        // test avec l'objet client
        service.retirerCoursier(coursierRetireClient);
        assertTrue(!service.getListeCoursiers().contains(coursierRetireNom));

        assertEquals(1, service.getListeCoursiers().size());
    }
}
