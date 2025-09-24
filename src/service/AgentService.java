package service;

import enums.TypeAgent;
import DAO.AgentDAO;
import model.Agent;
import repository.AgentRepository;

import java.sql.SQLException;

public class AgentService {

    private AgentRepository repo;

    public AgentService(AgentRepository agentRepository) {
        this.repo = agentRepository;
    }

    public void createAgent(Agent agent) throws SQLException {
        repo.createAgent(agent);
    }

}
