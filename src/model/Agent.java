package model;

import enums.TypeAgent;

import java.util.ArrayList;

public class Agent extends Personne{
    static int compteur = 0;
    private int idAgent;
    private Departement departement;
    private ArrayList <Paiement> paiements;
    private TypeAgent type;

    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public ArrayList<Paiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(ArrayList<Paiement> paiements) {
        this.paiements = paiements;
    }

    public TypeAgent getType() {
        return type;
    }

    public void setType(TypeAgent type) {
        this.type = type;
    }


    public Agent(String nom, String prenom, String email, String motDePasse, Departement departement, TypeAgent type) {
        super(nom, prenom, email, motDePasse);
        compteur++;
        this.idAgent = compteur;
        this.departement = departement;
        this.paiements = new ArrayList<>();
        this.type = type;
    }

    // overloading the constructor
    public Agent(int id, String nom, String prenom, String email, String motDePasse, Departement departement, TypeAgent type) {
        super(nom, prenom, email, motDePasse);
        this.idAgent = id;
        this.departement = departement;
        this.type = type;
    }

    public Agent(int id, String nom, String prenom, String email, Departement departement, TypeAgent type) {
        super(nom, prenom, email);
        this.idAgent = id;
        this.departement = departement;
        this.type = type;
    }

    public Agent(int id, String nom, String prenom, String email){
        super(nom, prenom, email);
        this.idAgent = id;
    }

    public boolean isEligibleForBonus() {
        return type == TypeAgent.RESPONSABLE_DEPARTEMENT || type == TypeAgent.DIRECTEUR;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "idAgent=" + idAgent +
                ", departement=" + departement +
                ", paiements=" + paiements +
                ", type=" + type +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                '}';
    }
}
