package modele;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.time.LocalTime;
import java.util.List;

public class FeuilleDeRoute {

    public void CreerFeuille(Tournee maTournee, String path) {

        maTournee.getCoursier().ifPresent(coursier -> {

            try {

                FileSystem fs = FileSystems.getDefault();
                String intro = "Planning quotidien de " + coursier.getPrenom() + " " + coursier.getNom() + "\n\n";
                String pathName = path + fs.getSeparator() + "feuilleDeRouteQuotidienneDe" + coursier.getPrenom() + coursier.getNom() + ".txt";
                File feuilleDeRoute = new File(pathName);

                FileWriter fw = new FileWriter(feuilleDeRoute.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(intro);

                int numero = 1;
                String chemin = "Prenez le tronçon qui passe par '";

                for (Livraison livraison : maTournee.getLivraisons())
                {
                    List<Troncon> parcours = livraison.getParcoursLivraison();
                    bw.write(numero + ") ");
                    bw.write("Heure de passage : " + (livraison.getHeurePassage().orElse(LocalTime.ofSecondOfDay(8))).toString() + "\r\n");

                    for (Troncon troncon : parcours)
                    {
                        bw.write(chemin);
                        bw.write(troncon.getNom());
                        bw.write("' sur " + troncon.getLongueur() + " mètres." + "\r\n");
                    }
                    bw.write("\r\n");
                    numero ++;
                }

                bw.close();
                System.out.println("Modification terminée!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}