package repository;

import config.DatabaseConnection;
import model.Agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AgentRepository {
    public String createAgent(Agent agent) {
        String querySQL = "INSERT INTO agent (nom, prenom, email, mdp, typeAgent) VALUES (?, ?, ?, ?, ?)";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL)){
            statement.setString(1, agent.getNom());
            statement.setString(2, agent.getPrenom());
            statement.setString(3, agent.getEmail());
            statement.setString(4, agent.getMotDePasse());
            statement.setString(5, agent.getType());
            // Execute the SQL query
            int rowsInserted = statement.executeUpdate();

            // checking if the agent is added
            if (rowsInserted > 0) {
                System.out.println("A new agent was added successfully!");
                return agent.getNom();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
