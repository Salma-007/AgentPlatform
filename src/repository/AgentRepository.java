package repository;

import DAO.AgentDAO;
import config.DatabaseConnection;
import model.Agent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgentRepository {
    private AgentDAO dao;

    public AgentRepository(AgentDAO dao){
        this.dao = dao;
    }

    public void createAgent(Agent agent) throws SQLException{
        dao.saveAgent(agent);
    }
}
