package DAO;

import config.DatabaseConnection;
import enums.TypeAgent;
import interfaces.IAgentDAO;
import interfaces.IDepartementDAO;
import model.Agent;
import model.Departement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AgentDAO implements IAgentDAO {

    public void save(Agent agent) {
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

    @Override
    public void update(Agent agent) {
        String querySQL = "UPDATE agent SET nom = ?, prenom = ?, email = ?, mdp = ?, idDepartement = ?, typeAgent = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySQL)) {

            statement.setString(1, agent.getNom());
            statement.setString(2, agent.getPrenom());
            statement.setString(3, agent.getEmail());
            statement.setString(4, agent.getMotDePasse());
            statement.setInt(5, agent.getDepartement().getIdDepartement());
            statement.setString(6, agent.getType().name());
            statement.setInt(7, agent.getIdAgent());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("the agent was updated successfully with name: " + agent.getNom());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Agent agent) {
        String querySQL = "DELETE FROM agent WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySQL)) {

            statement.setInt(1, agent.getIdAgent());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("the agent was deleted successfully with name: " + agent.getNom());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Agent> findAll() {
        List<Agent> agents = new ArrayList<>();
        String querySQL = "SELECT * FROM agent";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySQL)) {
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                IDepartementDAO daoDep = new DepartementDAO();
                Departement departement = daoDep.findById(resultSet.getInt("idDepartement"));

                String typeAgent = resultSet.getString("typeAgent");
                agents.add(new Agent(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"), departement, TypeAgent.valueOf(typeAgent)));
            }
            return agents;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Agent findById(int id) {
        String querySQL = "SELECT * FROM agent WHERE id = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL);){

            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                int agentId = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                int depId = resultSet.getInt("idDepartement");
                String typeAgent = resultSet.getString("typeAgent");

                IDepartementDAO daoDep = new DepartementDAO();
                Departement departement = daoDep.findById(depId);

                return new Agent(agentId, nom, prenom, email, departement, TypeAgent.valueOf(typeAgent));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Agent findByName(String nom) {
        String querySQL = "SELECT * FROM agent WHERE nom = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL);){

            statement.setString(1,nom);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                int agentId = resultSet.getInt("id");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                int depId = resultSet.getInt("idDepartement");
                String typeAgent = resultSet.getString("typeAgent");

                IDepartementDAO daoDep = new DepartementDAO();
                Departement departement = daoDep.findById(depId);

                return new Agent(agentId, nom, prenom, email, departement, TypeAgent.valueOf(typeAgent));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}