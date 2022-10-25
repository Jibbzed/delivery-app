package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dijkstra {
    private String origine;
    private String destination;
    private Double distance;
    private List<Troncon> chemin;

    public Dijkstra() {
        chemin = new ArrayList<>();
    }

    public Dijkstra(String origine, String destination, Double distance, List<Troncon> chemin) {
        this.origine = origine;
        this.destination = destination;
        this.distance = distance;
        this.chemin = chemin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dijkstra dijkstra = (Dijkstra) o;
        return origine.equals(dijkstra.origine) && destination.equals(dijkstra.destination) && distance.equals(dijkstra.distance) && chemin.equals(dijkstra.chemin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origine, destination, distance, chemin);
    }

    public String getOrigine() {
        return origine;
    }

    public String getDestination() {
        return destination;
    }

    public Double getDistance() {
        return distance;
    }

    public List<Troncon> getChemin() {
        return new ArrayList<>(chemin); //on renvoit une nouvelle arrayList pour empecher la modif de l'objet qu'on a get
    }
}
