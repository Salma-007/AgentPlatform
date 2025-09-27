package service;

import enums.TypeAgent;
import DAO.AgentDAO;
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

    public void createAgent(Agent agent) throws SQLException {
        Departement dept = deptRepo.getDepAndRespoByName(agent.getDepartement().getNom());

        if (deptRepo.getDepartementByName(agent.getDepartement().getNom()) == null) {
            System.out.println("departement not found!");
            return;
        }

        // Vérifier si le département a déjà un responsable
        if (dept != null && dept.getResponsable() != null && agent.getType() == TypeAgent.RESPONSABLE_DEPARTEMENT) {
            System.out.println("departement a déjà un responsable!");
            return;
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
