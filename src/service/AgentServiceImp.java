package service;

import enums.TypeAgent;
import exception.AgentNotFoundException;
import exception.DepartementNotFoundException;
import exception.ResponsableDejaExistantException;
import model.Agent;
import model.Departement;
import model.Paiement;
import repository.AgentRepository;
import repository.DepartementRepository;
import service.interfaces.AgentService;
import service.interfaces.PaiementService;

import java.sql.SQLException;
import java.util.List;

public class AgentServiceImp implements AgentService {

    private AgentRepository repo;
    private DepartementRepository deptRepo;
    private PaiementService paiementService;

    public AgentServiceImp(AgentRepository agentRepo, DepartementRepository deptRepo, PaiementService service) {
        this.repo = agentRepo;
        this.deptRepo = deptRepo;
        this.paiementService = service;
    }

    @Override
    public void modification(Agent agent) {
        repo.updateAgent(agent);
    }

    @Override
    public void suppression(Agent agent) {
        repo.deleteAgent(agent);
    }

    @Override
    public List<Agent> retrieveAll() {
        return repo.getAgents();
    }

    @Override
    public Agent findById(int id) throws AgentNotFoundException, SQLException {
        Agent agent = repo.getAgentId(id);
        if(agent == null){
            throw new AgentNotFoundException("agent with this id not found!");
        }
        return agent;
    }

    @Override
    public Agent findByName(String nom) {
        return repo.getAgentName(nom);
    }

    @Override
    public void ajout(Agent agent) {
        Departement dept = deptRepo.getDepAndRespoByName(agent.getDepartement().getNom());

        if (deptRepo.getDepartementByName(agent.getDepartement().getNom()) == null) {
            try {
                throw new DepartementNotFoundException("Département " + agent.getDepartement().getNom() + " introuvable !");
            } catch (DepartementNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (dept != null && dept.getResponsable() != null && agent.getType() == TypeAgent.RESPONSABLE_DEPARTEMENT) {
            try {
                throw new ResponsableDejaExistantException("Département " + dept.getNom() + " a déjà un responsable !");
            } catch (ResponsableDejaExistantException e) {
                throw new RuntimeException(e);
            }
        }

        agent.setDepartement(deptRepo.getDepartementByName(agent.getDepartement().getNom()));
        try {
            repo.createAgent(agent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double getTotalPayments(Agent agent) {
        return paiementService.paiementsByAgent(agent)
                .stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }
}
