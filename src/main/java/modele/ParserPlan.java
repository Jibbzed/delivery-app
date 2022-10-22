package modele;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// TODO: changer le nom du class a Parser puisque cette classe va prendre en consideration aussi le sauvegarde
// des livraisons en xml files.
public class ParserPlan {
//    private static  String FILENAME = "src/main/resources/file.xml";
    private static final String ACCESS_EXTERNAL_DTD = "http://javax.xml.XMLConstants/property/accessExternalDTD";
    private static final String ACCESS_EXTERNAL_SCHEMA = "http://javax.xml.XMLConstants/property/accessExternalSchema";
    private final List<Intersection> intersectionsListe = new ArrayList<Intersection>();
    private final List<Troncon> tronconsListe = new ArrayList<Troncon>();
    private final List<Entrepot> entrepotsListe = new ArrayList<Entrepot>();

    // TODO: retourner un plan
    public void lirePlan(String fileName) throws MauvaisFormatXmlException{
        SAXBuilder sax= new SAXBuilder();
        sax.setProperty(ACCESS_EXTERNAL_DTD, "");
        sax.setProperty(ACCESS_EXTERNAL_SCHEMA, "");
        try {
            Document doc = sax.build(new File(fileName));
            Element rootNode = doc.getRootElement();


            List<Element> entrepots = rootNode.getChildren("warehouse");
            validerElementXml(entrepots, "entropot");

            for (Element entropot : entrepots) {
                String addresse = entropot.getAttributeValue("address");
                // TODO :Create Warehouse
                // TODO: Add Warehouse.
//                entrepotsListe.add(new Entrepot(addresse));
            }


            List<Element> intersections = rootNode.getChildren("intersection");
            validerElementXml(intersections, "intersection");
            intersections.forEach(
                    i -> {
                        String id = i.getAttributeValue("id");
                        double latitude = Double.parseDouble(i.getAttributeValue("latitude"));
                        double longitude = Double.parseDouble(i.getAttributeValue("longitude"));
                        intersectionsListe.add(new Intersection(id, latitude, longitude));
                    }
            );

            List<Element> troncons = rootNode.getChildren("segment");
            validerElementXml(troncons, "troncon");

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
                                                            ()-> new MauvaisFormatXmlException("Origine introuvable avec id: " + origineId)
                                                    );
                        Intersection destination = intersectionsListe.stream()
                                                    .filter(i -> i.getId().equals(destinationId))
                                                    .findFirst()
                                                    .orElseThrow(
                                                            ()-> new MauvaisFormatXmlException("Destination introuvable avec id: " + destinationId)
                                                    );
                        tronconsListe.add(new Troncon(nom, longueur, origine, destination));
                    }


        } catch (JDOMException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private  static <T> void validerElementXml(List<T> list, String elementName) throws MauvaisFormatXmlException{
        if (list.isEmpty()) {
            throw new MauvaisFormatXmlException("Il n'y a pas d'element " + elementName + " sur le fichier xml fourni" );
        }
    }

    // Getters and Setters

    public List<Troncon> getTronconsListe(){
        return tronconsListe;
    }
    public List<Intersection> getIntersectionsListe(){
        return intersectionsListe;
    }



}
