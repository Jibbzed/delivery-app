package modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    // 308 intersections sur fichier smallMap
    private ParserPlan parser;
    @BeforeEach
    void init () {
        parser = new ParserPlan();
    }
    @Test
    void testLectureCasNormal() throws MauvaisFormatXmlException {
        // given
        int nombreIntersectionsExpectees = 308;
        int nombreTrouconsExpectes = 616;
        String nomFichier = "src/test/resources/smallMap.xml";

        // when
        parser.lirePlan(nomFichier);
        int nombreIntersectionsRecues = parser.getIntersectionsListe().size();
        int nombreTrouconsRecus =  parser.getTronconsListe().size();
        // then
        assertAll(
                () -> assertEquals(nombreIntersectionsExpectees, nombreIntersectionsRecues),
                () -> assertEquals(nombreTrouconsExpectes, nombreTrouconsRecus)
        );
    }

    @Test
    void testLectureFichierXMLNonComfort(){
        // given
        String nomFichierFormatNonValide = "src/test/resources/books.xml";
        String nomFichierCorrompu = "src/test/resources/smallMapCorrompu.xml";

        // when

        // then
        assertAll(
                () -> assertThrows(MauvaisFormatXmlException.class, () -> parser.lirePlan(nomFichierFormatNonValide)),
                () -> assertThrows(MauvaisFormatXmlException.class, () -> parser.lirePlan(nomFichierCorrompu))
        );


    }
}
