package service;

import enums.TypeAgent;
import DAO.AgentDAO;
import exception.DepartementNotFoundException;
import exception.ResponsableDejaExistantException;
import model.Agent;
import model.Departement;
import repository.AgentRepository;
import repository.DepartementRepository;

import java.sql.SQLException;
import java.util.List;

public class AgentService {

    private AgentRepository repo;
    private DepartementRepository deptRepo;

    public AgentService(AgentRepository agentRepo, DepartementRepository deptRepo) {
        this.repo = agentRepo;
        this.deptRepo = deptRepo;
    }

    public void createAgent(Agent agent) throws SQLException, ResponsableDejaExistantException, DepartementNotFoundException {
        Departement dept = deptRepo.getDepAndRespoByName(agent.getDepartement().getNom());

        if (deptRepo.getDepartementByName(agent.getDepartement().getNom()) == null) {
            throw new DepartementNotFoundException("Département " + agent.getDepartement().getNom() + " introuvable !");
        }

        if (dept != null && dept.getResponsable() != null && agent.getType() == TypeAgent.RESPONSABLE_DEPARTEMENT) {
            throw new ResponsableDejaExistantException("Département " + dept.getNom() + " a déjà un responsable !");
        }

        agent.setDepartement(deptRepo.getDepartementByName(agent.getDepartement().getNom()));
        repo.createAgent(agent);
    }


    public Agent getAgentByid(int id) throws SQLException {
        return repo.getAgentId(id);
    }

    public List<Agent> retrieveAgents(){
        return repo.getAgents();
    }

    public void modifierAgent(Agent agent){
        repo.updateAgent(agent);
    }

    public void deleteAgent(Agent agent){
        repo.deleteAgent(agent);
    }
}
