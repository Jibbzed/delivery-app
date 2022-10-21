package modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    // 308 intersections sur fichier smallMap
    @Test
    void testParseGoodCase() {
        // given
        int nombreIntersectionsExpectees = 308;
        int nombreTrouconsExpectes = 616;
        // when
        ParserPlan.lirePlan("src/test/resources/smallMap.xml");
        int nombreIntersectionsRecues = ParserPlan.getIntersectionsListe().size();
        int nombreTrouconsRecus =  ParserPlan.getTronconsListe().size();
        // then
        assertAll(
                () -> assertEquals(nombreIntersectionsExpectees, nombreIntersectionsRecues),
                () -> assertEquals(nombreTrouconsExpectes, nombreTrouconsRecus)
        );
    }
}
