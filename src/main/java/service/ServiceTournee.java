package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import modele.Coursier;
import modele.Tournee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ServiceTournee {

    private static ServiceTournee instance;
    private Map<Coursier, Tournee> tournees = new HashMap<>();
    public static ServiceTournee getInstance(){
        if (instance == null) {
            instance = new ServiceTournee();
        }
        return instance;
    }

    public void ajouterTournee(Tournee tournee) { this.tournees.put(tournee.getCoursier(), tournee); }

    public Map<Coursier, Tournee> getTournees() {
        return tournees;
    }

    public void setTournees(Map<Coursier, Tournee> tournees) {
        this.tournees = tournees;
    }
}
