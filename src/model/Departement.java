package model;

import java.util.ArrayList;

public class Departement {
    static int compteur = 0;
    private int idDepartement;
    private String nom;
    private Agent responsable;
    private ArrayList<Agent> agents;

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<Agent> agents) {
        this.agents = agents;
    }

    public Agent getResponsable() {
        return responsable;
    }

    public void setResponsable(Agent responsable) {
        this.responsable = responsable;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }


    Departement(int id, String nom, Agent responsable){
        compteur++;
        this.idDepartement = compteur;
        this.nom = nom;
        this.responsable = responsable;
        this.agents = new ArrayList<>();
    }
}
