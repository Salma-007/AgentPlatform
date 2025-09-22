package model;

import enums.TypePaiement;

import java.util.ArrayList;
import java.util.Date;

public class Paiement {
    static int compteur = 0;
    private int idPaiement;
    private TypePaiement type;
    private float montant;
    private Date date;
    private String motif;
    private Agent agent;

    public int getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(int idPaiement) {
        this.idPaiement = idPaiement;
    }

    public TypePaiement getType() {
        return type;
    }

    public void setType(TypePaiement type) {
        this.type = type;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }


    Paiement(TypePaiement type, float montant, String motif, Agent agent){
        Paiement.compteur++;
        this.idPaiement = Paiement.compteur;
        this.type = type;
        this.montant = montant;
        this.date = new Date();
        this.motif = motif;
        this.agent = agent;
    }
}
