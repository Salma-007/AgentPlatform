package controller;

import enums.TypePaiement;
import exception.MontantNegatifException;
import model.Agent;
import model.Paiement;
import service.PaiementServiceImp;
import service.interfaces.PaiementService;

import java.sql.SQLException;

public class PaiementController {
    private PaiementServiceImp service ;

    public PaiementController(PaiementServiceImp service){
        this.service = service;
    }

    public void ajouterPaiement(TypePaiement type, double montant, String motif, Agent agent) throws SQLException, MontantNegatifException {
        Paiement p = new Paiement(type, montant, motif, agent);
        service.ajout(p);
    }


}
