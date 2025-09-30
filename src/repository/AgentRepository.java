package repository;

import DAO.AgentDAO;
import config.DatabaseConnection;
import model.Agent;
import model.Departement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AgentRepository {
    private AgentDAO dao;

    public AgentRepository(AgentDAO dao){
        this.dao = dao;
    }

    public void createAgent(Agent agent) throws SQLException{
        dao.save(agent);
    }

    public Agent getAgentId(int id) throws SQLException{
        return dao.findById(id);
    }

    public List<Agent> getAgents(){
        return dao.findAll();
    }

    public Agent getAgentName(String nom){
        return dao.findByName(nom);
    }

    public void updateAgent(Agent agent){
        dao.update(agent);
    }

    public void deleteAgent(Agent ag){
        dao.delete(ag);
    }

    public String getInformations(){
        return dao.toString();
    }

}
