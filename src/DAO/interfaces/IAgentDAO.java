package DAO.interfaces;
import model.Agent;
import model.Departement;
import model.Paiement;

import java.util.List;
import java.util.Optional;

public interface IAgentDAO extends GenericDAO<Agent> {
    List<Paiement> retrieveAllPaiemenst(Agent agent);

}
