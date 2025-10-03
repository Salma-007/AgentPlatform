package service;

import enums.TypeAgent;
import enums.TypePaiement;
import exception.AgentNotFoundException;
import exception.PaymentNotFoundException;
import exception.UnauthorizedPaiementException;
import model.Agent;
import model.Departement;
import model.Paiement;
import repository.AgentRepository;
import repository.PaiementRepository;
import service.interfaces.PaiementService;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PaiementServiceImp implements PaiementService {

    private PaiementRepository repo;
    private AgentRepository agentrepo;

    public PaiementServiceImp(PaiementRepository paiementRepository, AgentRepository repo){
        this.repo = paiementRepository;
        this.agentrepo = repo;
    }

    @Override
    public void addPaiement(Paiement paiement, Agent agentAuthentifie) throws AgentNotFoundException, SQLException {
        Optional<Agent> agentOpt = agentrepo.getAgentId(paiement.getAgent().getIdAgent());

        if(!agentOpt.isPresent()){
            throw new AgentNotFoundException("agent with this id not found!");
        }
        Agent agent = agentOpt.get();
        Agent agentCible = agentOpt.get();

        // Vérification simple des autorisations
        if (!peutAjouterPaiement(agentAuthentifie, agentCible, paiement.getType())) {
            throw new UnauthorizedPaiementException("Action non autorisée");
        }
        repo.createPaiement(paiement);
    }

    @Override
    public boolean peutAjouterPaiement(Agent agentAuth, Agent agentCible, TypePaiement type) {
        if (agentAuth.getIdAgent() == agentCible.getIdAgent()) {
            return false;
        }

        if (agentAuth.getType() == TypeAgent.DIRECTEUR) {
            return agentCible.getType() == TypeAgent.RESPONSABLE_DEPARTEMENT ||
                    agentCible.getType() == TypeAgent.DIRECTEUR;
        }

        if (agentAuth.getType() == TypeAgent.RESPONSABLE_DEPARTEMENT) {
            boolean memeDepartement = agentCible.getDepartement().getIdDepartement() ==
                    agentAuth.getDepartement().getIdDepartement();
            boolean estOuvrier = agentCible.getType() == TypeAgent.OUVRIER || agentCible.getType() == TypeAgent.STAGIAIRE;
            boolean typeAutorise = type == TypePaiement.SALAIRE || type == TypePaiement.PRIME;

            return memeDepartement && estOuvrier && typeAutorise;
        }

        return false;
    }

    @Override
    public List<Paiement> paiementsIvalides() {
        return repo.getFalsePaiements();
    }

    @Override
    public double getTotalByType(Agent agent, TypePaiement type) {
        return paiementsByAgent(agent).stream()
                .filter(p -> p.getType() == type)
                .mapToDouble(Paiement::getMontant)
                .sum();
    }

    @Override
    public long getCountByType(Agent agent, TypePaiement type) {
        return paiementsByAgent(agent).stream()
                .filter(p -> p.getType() == type)
                .count();
    }

    @Override
    public double getTotalGeneral(Agent agent) {
        return 0;
    }

    @Override
    public Optional<Paiement> getPaiementMax(Agent agent) {
        return paiementsByAgent(agent).stream()
                .max(Comparator.comparingDouble(Paiement::getMontant));
    }

    @Override
    public Optional<Paiement> getPaiementMin(Agent agent) {
        return paiementsByAgent(agent).stream()
                .min(Comparator.comparingDouble(Paiement::getMontant));
    }

    @Override
    public void ajout(Paiement entity) throws SQLException {

    }

    @Override
    public void modification(Paiement paiement) {
        repo.updatePaiement(paiement);
    }

    @Override
    public void suppression(Paiement paiement) {
        repo.deletePaiement(paiement);
    }

    @Override
    public List<Paiement> retrieveAll() {
        return repo.getPaiements();
    }

    @Override
    public Paiement findById(int id) {
        Optional<Paiement> paiement = repo.getPaiementById(id);
        return paiement.orElseThrow(()-> new PaymentNotFoundException("payment not found!"));
    }

    @Override
    public Paiement findByName(String nom) {
        return null;
    }

    @Override
    public List<Paiement> paiementsByAgent(Agent agent) {
        List<Paiement> payments = repo.getPaiements();
        List<Paiement> paiementsAgent = payments.stream().filter(paiement -> paiement.getAgent().getIdAgent() == agent.getIdAgent()).collect(Collectors.toList());
        return paiementsAgent;
    }

    @Override
    public List<Paiement> paiementsByDepartement(Departement departement) {
        List<Paiement> payments = repo.getPaiements();
        List<Paiement> paiementsDepartement = payments.stream().filter(paiement -> paiement.getAgent().getDepartement().getIdDepartement() == departement.getIdDepartement()).collect(Collectors.toList());
        return paiementsDepartement;
    }

    @Override
    public List<Paiement> paiementTriParMontant(Agent agent) {
        return paiementsByAgent(agent)
                .stream()
                .sorted((p1, p2) -> Double.compare(p1.getMontant(), p2.getMontant()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Paiement> paiementTriParDate(Agent agent) {
        return paiementsByAgent(agent)
                .stream()
                .sorted(Comparator.comparing(Paiement::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Paiement> paiementTriParType(Agent agent, String type) {
        return paiementsByAgent(agent)
                .stream()
                .filter(paiement -> paiement.getType().name() == type)
                .toList();
    }




}
