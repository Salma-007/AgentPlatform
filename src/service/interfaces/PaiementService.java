package service.interfaces;

import enums.TypePaiement;
import model.Agent;
import model.Departement;
import model.Paiement;

import java.util.List;

public interface PaiementService extends ServiceInterface<Paiement>{
    List<Paiement> paiementsByAgent(Agent agent);
    List<Paiement> paiementsByDepartement(Departement departement);
    List<Paiement> paiementTriParMontant(Agent agent);
    List<Paiement> paiementTriParDate(Agent agent);
    List<Paiement> paiementTriParType(Agent agent, String type);
}
