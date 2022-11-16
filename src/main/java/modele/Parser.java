package modele;

import modele.exception.MauvaisFormatXmlException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.*;
import java.util.List;
import java.util.Set;

public class Parser {
    private static final String ACCESS_EXTERNAL_DTD = "http://javax.xml.XMLConstants/property/accessExternalDTD";
    private static final String ACCESS_EXTERNAL_SCHEMA = "http://javax.xml.XMLConstants/property/accessExternalSchema";

    public Plan lirePlan(String fileName) throws MauvaisFormatXmlException, IOException {
        Plan plan = new Plan();
        SAXBuilder sax= new SAXBuilder();
        sax.setProperty(ACCESS_EXTERNAL_DTD, "");
        sax.setProperty(ACCESS_EXTERNAL_SCHEMA, "");
        try {
            Document doc = sax.build(new File(fileName));
            Element rootNode = doc.getRootElement();




            List<Element> intersections = rootNode.getChildren("intersection");
            validerElementXml(intersections, "intersection");
            intersections.forEach(
                    i -> {
                        String id = i.getAttributeValue("id");
                        double latitude = Double.parseDouble(i.getAttributeValue("latitude"));
                        double longitude = Double.parseDouble(i.getAttributeValue("longitude"));
                        plan.ajouterIntersection(new Intersection(id, latitude, longitude));
                    }
            );
            // TODO: this works for now, but we should try to find a better way to do this
            // i don't know much about the library, but perhaps i can specify that there is a single element child
            // instead of a list.
            List<Element> entrepots = rootNode.getChildren("warehouse");
            validerElementXml(entrepots, "entropot");

            for (Element entrepot : entrepots) {
                String addresse = entrepot.getAttributeValue("address");
                for(Intersection intersection: plan.getIntersections().values()){
                    if(intersection.getId().equals(addresse)) {
                        intersection.setEntrepot(true);
                    }
                }
            }


            List<Element> troncons = rootNode.getChildren("segment");
            validerElementXml(troncons, "troncon");

            for(Element t: troncons) {
                        String destinationId = t.getAttributeValue("destination");
                        Double longueur = Double.parseDouble(t.getAttributeValue("length"));
                        String nom = t.getAttributeValue("name");
                        String origineId = t.getAttributeValue("origin");
                        // Checker si l'origine et la destination existe tant qu'une intersection.
                        Intersection origine = plan.getIntersections().values().stream()
                                                    .filter(i -> i.getId().equals(origineId))
                                                    .findFirst()
                                                    .orElseThrow(
                                                            ()-> new MauvaisFormatXmlException("Origine introuvable avec id: " + origineId)
                                                    );
                        Intersection destination = plan.getIntersections().values().stream()
                                                    .filter(i -> i.getId().equals(destinationId))
                                                    .findFirst()
                                                    .orElseThrow(
                                                            ()-> new MauvaisFormatXmlException("Destination introuvable avec id: " + destinationId)
                                                    );
                        plan.ajouterTroncon(new Troncon(nom, longueur, origine, destination));
                    }


        } catch (JDOMException e) {
            throw new MauvaisFormatXmlException(e);
        }

        return plan;
    }

    private  static <T> void validerElementXml(List<T> list, String elementName) throws MauvaisFormatXmlException{
        if (list.isEmpty()) {
            throw new MauvaisFormatXmlException("Il n'y a pas d'element " + elementName + " sur le fichier xml fourni" );
        }
    }

    public void sauvegarderLivraison(Livraison livraison, String mapFileXml) throws Exception{

        String xmlFile = "src/test/resources/livraisons.xml";
        SAXBuilder sax= new SAXBuilder();
        sax.setProperty(ACCESS_EXTERNAL_DTD, "");
        sax.setProperty(ACCESS_EXTERNAL_SCHEMA, "");

        Document doc = sax.build(new File(xmlFile));
        Element rootNode = doc.getRootElement();

        Element livraisonElement = new Element("livraison");
        String prenomCoursier = livraison.getCoursierLivraison().get().getPrenom();
        String nomCoursier = livraison.getCoursierLivraison().get().getNom();
        String idIntersection = livraison.destinationLivraison.getId();
        String fenetreHoraire = livraison.getFenetreHoraireLivr().get().toString();

        livraisonElement.addContent(new Element("fichierPlan").setText(mapFileXml));
        Element coursier = new Element("coursier");
        coursier.addContent(new Element("nom").setText(nomCoursier));
        coursier.addContent(new Element("prenom").setText(prenomCoursier));
        livraisonElement.addContent(coursier);

        Element intersecEl = new Element("intersection").setText(idIntersection);
        Element fenetreHEl = new Element("feneHoraire").setText(fenetreHoraire);
        livraisonElement.addContent(new Element("intersection").setText(idIntersection));
        livraisonElement.addContent(new Element("feneHoraire").setText(fenetreHoraire));

        rootNode.addContent(livraisonElement);

        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(doc, new FileWriter(xmlFile));
    }
}
