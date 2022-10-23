package modele;

import modele.exception.MauvaisFormatXmlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    // 308 intersections sur fichier smallMap
    private Parser parser;
    @BeforeEach
    void init () {
        parser = new Parser();
    }
    @Test
    void testLectureCasNormal() throws MauvaisFormatXmlException, IOException {
        // given
        int nombreIntersectionsExpectees = 308;
        int nombreTrouconsExpectes = 616;
        String nomFichier = "src/test/resources/smallMap.xml";

        // when
        Plan plan = parser.lirePlan(nomFichier);
        int nombreIntersectionsRecues = plan.getIntersections().size();
        int nombreTrouconsRecus =  plan.getTroncons().size();
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
        String nomFichierNonXml = "src/test/resources/nonXML.xml";
        String nomFichierExistePas = "src/test/resources/existPas.xml";
        // when

        // then
        assertAll(
                () -> assertThrows(MauvaisFormatXmlException.class, () -> parser.lirePlan(nomFichierFormatNonValide)),
                () -> assertThrows(MauvaisFormatXmlException.class, () -> parser.lirePlan(nomFichierCorrompu)),
                () -> assertThrows(MauvaisFormatXmlException.class, () -> parser.lirePlan(nomFichierNonXml)),
                () -> assertThrows(IOException.class, ()-> parser.lirePlan(nomFichierExistePas))
        );


    }
}
