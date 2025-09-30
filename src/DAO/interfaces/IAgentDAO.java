package DAO.interfaces;
import model.Agent;
import model.Paiement;

import java.util.List;

public interface IAgentDAO extends GenericDAO<Agent> {
    List<Paiement> retrieveAllPaiemenst(Agent agent);

}
