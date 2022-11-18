package service;

import modele.Tournee;

import java.util.ArrayList;
import java.util.HashSet;

public class ServiceTournee {

    private static ServiceTournee instance;
    private ArrayList<Tournee> tournees = new ArrayList<>();

    public static ServiceTournee getInstance(){
        if (instance == null) {
            instance = new ServiceTournee();
        }
        return instance;
    }

    public void ajouterTournee(Tournee tournee) { this.tournees.add(tournee); }

    public ArrayList<Tournee> getTournees(){
        return tournees;
    }
}
