package modele;

public class Intersection {

    protected String id;
    protected double latitude;
    protected double longitude;


    public Intersection(String id, double latitude, double longitude){
        this.id=id;
        this.longitude=longitude;
        this.latitude=latitude;
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
}
