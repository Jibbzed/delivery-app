package modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LivraisonTest {
    // Création d'une livraison
    @Test
    public void testCreerLivraison() {
        Intersection departTest = new Intersection("1234",12,25);
        Intersection arriveeTest = new Intersection("1264",14,26);
        Coursier coursierTest = new Coursier("Duriff","Alban");
        int fenetreH = 8;

        Livraison livraisonTest = new Livraison(departTest,arriveeTest,coursierTest,fenetreH);

        String message = "Livraison{destinationLivraison=" + arriveeTest +
                ", coursierLivraison=Optional[" + coursierTest +
                "], parcoursLivraison=null" +
                ", fenetreHoraireLivr=Optional[8]}";

        assertEquals(message, livraisonTest.toString());
    }

    @Test
    public void testModifierLivraison(){
        Intersection departTest = new Intersection("1234",12,25);
        Intersection arriveeTest = new Intersection("1264",14,26);
        Coursier coursierTest = new Coursier("Duriff","Alban");
        int fenetreH = 8;
        Livraison livraisonTest = new Livraison(departTest, arriveeTest,coursierTest,fenetreH);

        Intersection newDepart = new Intersection("2564",5,6);
        Coursier newCoursier = new Coursier("Allot","Lauraine");
        int newHoraire = 9;
        livraisonTest.setCoursierLivraison(newCoursier);
        livraisonTest.setOrigineLivraison(newDepart);
        livraisonTest.setFenetreHoraireLivr(newHoraire);

        assertEquals(livraisonTest.getCoursierLivraison().get().getNom(), newCoursier.getNom());
        assertEquals(livraisonTest.getOrigineLivraison().get().getId(), newDepart.getId());
        assertEquals(livraisonTest.getFenetreHoraireLivr().get(), newHoraire);
    }
}
