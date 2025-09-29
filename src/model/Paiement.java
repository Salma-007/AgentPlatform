package model;

import enums.TypePaiement;
import exception.AgentNonEligibleException;
import exception.MontantNegatifException;

import java.util.ArrayList;
import java.util.Date;

public class Paiement {
    static int compteur = 0;
    private int idPaiement;
    private TypePaiement type;
    private double montant;
    private Date date;
    private String motif;
    private Agent agent;

    private String typePrime;
    private String typeIndemnite;
    private boolean conditionValidee;


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

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
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


    Paiement(TypePaiement type, double montant, String motif, Agent agent) throws MontantNegatifException {
        Paiement.compteur++;
        this.idPaiement = Paiement.compteur;
        this.type = type;
        this.montant = montant;
        this.date = new Date();
        this.motif = motif;
        this.agent = agent;
        validerMontant(montant);
    }

    public Paiement(int id, TypePaiement type, double montant, Date date ,String motif, Agent agent) throws MontantNegatifException {
        this(type, montant, motif, agent);
        this.idPaiement = id;
        this.date = date;
    }
    // Constructeurs spécifiques
    public Paiement(double montant, String motif, String typePrime, Agent agent) throws MontantNegatifException {
        this(TypePaiement.PRIME, montant, motif, agent);
        this.typePrime = typePrime;
    }

    public Paiement(double montant, String motif, Agent agent, boolean conditionValidee) throws AgentNonEligibleException, MontantNegatifException {
        this(TypePaiement.BONUS, montant, motif, agent);
        this.conditionValidee = conditionValidee;
        validerEligibiliteBonus();
    }

    private void validerEligibiliteBonus() throws AgentNonEligibleException {
        if (!agent.isEligibleForBonus()) {
            throw new AgentNonEligibleException("Agent non éligible pour recevoir un bonus");
        }
    }

    private void validerMontant(double montant) throws MontantNegatifException {
        if (montant < 0) {
            throw new MontantNegatifException("Le montant ne peut pas être négatif: " + montant);
        }
    }


}
