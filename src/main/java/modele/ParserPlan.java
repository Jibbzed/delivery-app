package modele;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserPlan {
    private static final String FILENAME = "src/main/resources/file.xml";
    private static final String ACCESS_EXTERNAL_DTD = "http://javax.xml.XMLConstants/property/accessExternalDTD";
    private static final String ACCESS_EXTERNAL_SCHEMA = "http://javax.xml.XMLConstants/property/accessExternalSchema";
    private static final List<Intersection> intersectionsListe = new ArrayList<Intersection>();
    private static final List<Troncon> tronconsListe = new ArrayList<Troncon>();
//    private static final List<Entropot> entropots = new ArrayList<Entropot>();

    public void lirePlan(String fileName) {
        SAXBuilder sax= new SAXBuilder();
        sax.setProperty(ACCESS_EXTERNAL_DTD, "");
        sax.setProperty(ACCESS_EXTERNAL_SCHEMA, "");
        try {
            Document doc = sax.build(new File(FILENAME));
            Element rootNode = doc.getRootElement();
            List<Element> entrepots = rootNode.getChildren("warehouse");

            for (Element entropot : entrepots) {
                String addresse = entropot.getAttributeValue("address");
                // TODO :Create Warehouse
                // TODO: Add Warehouse.
            }
            List<Element> intersections = rootNode.getChildren("intersection");
            intersections.forEach(
                    i -> {
                        String id = i.getAttributeValue("id");
                        double latitude = Double.parseDouble(i.getAttributeValue("latitude"));
                        double longitude = Double.parseDouble(i.getAttributeValue("longitude"));
                        // TODO: create Intersection
                        // TODO: Add intersection
                        intersectionsListe.add(new Intersection(id, latitude, longitude));
                    }
            );

            List<Element> troncons = rootNode.getChildren("segment");

            for(Element t: troncons) {
                        String destinationId = t.getAttributeValue("destination");
                        Double longueur = Double.parseDouble(t.getAttributeValue("length"));
                        String nom = t.getAttributeValue("name");
                        String origineId = t.getAttributeValue("origin");
                        // Checker si l'origine et la destination existe tant qu'une intersection.
                        Intersection origine = intersectionsListe.stream()
                                                    .filter(i -> i.getId().equals(origineId))
                                                    .findFirst()
                                                    .orElseThrow(
                                                            // TODO: personalize the exception
                                                            ()-> new Exception("Origine introuvable avec id: " + origineId)
                                                    );
                        Intersection destination = intersectionsListe.stream()
                                                    .filter(i -> i.getId().equals(destinationId))
                                                    .findFirst()
                                                    .orElseThrow(
                                                            // TODO: personalize the exception
                                                            ()-> new Exception("Destination introuvable avec id: " + destinationId)
                                                    );
                        tronconsListe.add(new Troncon(nom, longueur, origine, destination));
                    }


        } catch (JDOMException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            // TODO : personalize the exception
            throw new RuntimeException(e);
        }

    }
}
