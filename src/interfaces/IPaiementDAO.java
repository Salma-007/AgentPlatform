package interfaces;

import model.Agent;
import model.Paiement;

import java.util.List;

public interface IPaiementDAO extends GenericDAO<Paiement, Integer> {
    public List<Paiement> getAgentPaiements(Agent agent);
}
