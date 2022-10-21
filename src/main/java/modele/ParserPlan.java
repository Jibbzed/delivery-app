package modele;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ParserPlan {
    private static final String FILENAME = "src/main/resources/file.xml";
    private static final String ACCESS_EXTERNAL_DTD = "http://javax.xml.XMLConstants/property/accessExternalDTD";
    private static final String ACCESS_EXTERNAL_SCHEMA = "http://javax.xml.XMLConstants/property/accessExternalSchema";


    public void lirePlan(String fileName) {
        SAXBuilder sax= new SAXBuilder();
        sax.setProperty(ACCESS_EXTERNAL_DTD, "");
        sax.setProperty(ACCESS_EXTERNAL_SCHEMA, "");
        try {
            Document doc = sax.build(new File(FILENAME));
            Element rootNode = doc.getRootElement();
            List<Element> entrepots = rootNode.getChildren("warehouse");

            for (Element entropots : entrepots) {
                String addresse = entropots.getAttributeValue("address");
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
                    }
            );

            List<Element> troncons = rootNode.getChildren("segment");
            troncons.forEach(
                    t -> {
                        String destination = t.getAttributeValue("destination");
                        Double longueur = Double.parseDouble(t.getAttributeValue("length"));
                        String nom = t.getAttributeValue("name");
                        String origine = t.getAttributeValue("origin");
                        // TODO: create a new Troncon
                        // TODO: Add Troncon.
                    }
            );


        } catch (JDOMException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
