package controller;

import enums.TypePaiement;
import exception.MontantNegatifException;
import model.Agent;
import model.Paiement;
import service.PaiementServiceImp;
import service.interfaces.PaiementService;

import java.sql.SQLException;
import java.util.List;

public class PaiementController {
    private PaiementServiceImp service ;
    private Agent currentAgent;

    public PaiementController(PaiementServiceImp service){
        this.service = service;
    }

    public void setCurrentAgent(Agent agent) {
        this.currentAgent = agent;
    }

    public Agent getCurrentAgent() {
        return currentAgent;
    }

    public void ajouterPaiement(TypePaiement type, double montant, String motif, Agent agent, Agent agentCible) throws SQLException, MontantNegatifException {
        Paiement p = new Paiement(type, montant, motif, agent);
        service.addPaiement(p, agentCible);
    }

    public List<Paiement> retrievePaiementsPerAgent(Agent agent){
        return service.paiementsByAgent(agent);
    }

    public List<Paiement> triPaiementParMontant(Agent agent){
        return service.paiementTriParMontant(agent);
    }

    public List<Paiement> triPaiementParType(Agent agent, String type){
        return service.paiementTriParType(agent, type);
    }

    public List<Paiement> triPaiementParDate(Agent agent){
        return service.paiementTriParDate(agent);
    }


}
