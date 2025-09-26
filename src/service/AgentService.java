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
        Departement dept = deptRepo.getDepartementByName(agent.getDepartement().getNom());
        if (dept == null) {
            System.out.println("departement not found!");
        }
        agent.setDepartement(dept);

        repo.createAgent(agent);
    }

    public Agent getAgentByid(int id) throws SQLException {
        return repo.getAgentId(id);
    }

    public List<Agent> retrieveAgents(){
        return repo.getAgents();
    }
}
