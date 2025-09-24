package DAO;

import config.DatabaseConnection;
import enums.TypeAgent;
import interfaces.IAgentDAO;
import model.Agent;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AgentDAO implements IAgentDAO {

    public void saveAgent(Agent agent) {
        String querySQL = "INSERT INTO agent (nom, prenom, email, mdp, idDepartement ,typeAgent) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL)){
            statement.setString(1, agent.getNom());
            statement.setString(2, agent.getPrenom());
            statement.setString(3, agent.getEmail());
            statement.setString(4, agent.getMotDePasse());
            statement.setInt(5, agent.getDepartement().getIdDepartement());
            statement.setString(6, agent.getType().name());

            // Execute the SQL query
            int rowsInserted = statement.executeUpdate();

            // checking if the agent is added
            if (rowsInserted > 0) {
                System.out.println("A new agent was added successfully!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
