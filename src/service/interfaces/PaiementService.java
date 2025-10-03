package service.interfaces;

import enums.TypePaiement;
import model.Agent;
import model.Departement;
import model.Paiement;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PaiementService extends ServiceInterface<Paiement>{
    List<Paiement> paiementsByAgent(Agent agent);
    List<Paiement> paiementsByDepartement(Departement departement);
    List<Paiement> paiementTriParMontant(Agent agent);
    List<Paiement> paiementTriParDate(Agent agent);
    List<Paiement> paiementTriParType(Agent agent, String type);
    void addPaiement(Paiement paiement, Agent agent) throws SQLException;
    boolean peutAjouterPaiement(Agent agentAuth, Agent agentCible, TypePaiement type);
    List<Paiement> paiementsIvalides();
    double getTotalByType(Agent agent, TypePaiement type);
    long getCountByType(Agent agent, TypePaiement type);
    double getTotalGeneral(Agent agent);
    Optional<Paiement> getPaiementMax(Agent agent);
    Optional<Paiement> getPaiementMin(Agent agent);

}
