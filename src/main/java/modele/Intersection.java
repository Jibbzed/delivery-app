package modele;

import java.util.Objects;

public class Intersection {

    private String id;
    private double latitude;
    private double longitude;
    private boolean isEntrepot;


    /**
     * Constructeur de la classe.
     * Crée une intersection à partir d'informations récoltées dans le fichier XML du plan
     *
     * @param id        identifiant unique du point
     * @param latitude  coordonnée de latitude du point
     * @param longitude coordonnée de longitude du point
     */
    public Intersection(String id, double latitude, double longitude){
        this.id=id;
        this.longitude=longitude;
        this.latitude=latitude;
        this.isEntrepot = false;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "id='" + id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
    /* getters and setters */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isEntrepot() {
        return isEntrepot;
    }

    public void setEntrepot(boolean entrepot) {
        isEntrepot = entrepot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intersection that = (Intersection) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
