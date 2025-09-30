package DAO;

import config.DatabaseConnection;
import DAO.interfaces.IDepartementDAO;
import model.Agent;
import model.Departement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartementDAO implements IDepartementDAO {

    @Override
    public void save(Departement dep) {
        String querySQL = "insert into departement (nom) values (?)";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL)){
            statement.setString(1, dep.getNom());

            int rowsInserted = statement.executeUpdate();

            // checking if the agent is added
            if (rowsInserted > 0) {
                System.out.println("A new department was added successfully!");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Departement findById(int id) {
        String querySQL = "select departement.id , departement.nom as nom, agent.id, agent.email ,agent.prenom as prenom, agent.nom as responsable\n" +
                            "from departement\n" +
                            "JOIN agent \n" +
                            "ON departement.id = agent.idDepartement\n" +
                            "WHERE departement.id = ? ";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL);){

            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                Agent depResponsable = new Agent(resultSet.getInt("agent.id"), resultSet.getString("responsable"), resultSet.getString("prenom"), resultSet.getString("agent.email"));
                return new Departement(resultSet.getInt("id"), resultSet.getString("nom"), depResponsable);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Departement findByName(String nom) {
        String querySQL = "SELECT * FROM departement WHERE nom = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL);){

            statement.setString(1,nom);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return new Departement(resultSet.getInt("id"), resultSet.getString("nom"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Departement dep) {
        String querySQL = "UPDATE departement SET nom = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySQL)) {
            statement.setString(1, dep.getNom());
            statement.setInt(2, dep.getIdDepartement());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing departement was updated successfully with name: " + dep.getNom());
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Departement dep) {
        String querySQL = "DELETE FROM departement WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySQL)) {

            statement.setInt(1, dep.getIdDepartement());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("the departement was deleted successfully with name: " + dep.getNom());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Departement> findAll() {
        List<Departement> departements = new ArrayList<>();
        String querySQL = "SELECT * FROM departement ";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySQL)) {
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                departements.add(new Departement(resultSet.getInt("id"), resultSet.getString("nom")));
            }
            return departements;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Agent> getAgentsByDepartement(Departement dep) {
        List<Agent> agents = new ArrayList<>();
        String querySQL = "SELECT * FROM agent join departement on agent.idDepartement = departement.id where departement.nom = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySQL)) {

            statement.setString(1, dep.getNom());
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                agents.add(new Agent(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email")));
            }
            return agents;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Departement getDepartementAndResponsable(String nom) {
        String querySQL = "select departement.id , departement.nom as nom, agent.id as respo_id, agent.email ,agent.prenom as prenom, agent.nom as responsable\n" +
                "from departement\n" +
                "JOIN agent \n" +
                "ON departement.id = agent.idDepartement\n" +
                "WHERE departement.nom = ? and agent.typeAgent = 'RESPONSABLE_DEPARTEMENT'";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL);){

            statement.setString(1,nom);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                Agent depResponsable = new Agent(resultSet.getInt("respo_id"), resultSet.getString("responsable"), resultSet.getString("prenom"), resultSet.getString("agent.email"));
                return new Departement(resultSet.getInt("id"), resultSet.getString("nom"), depResponsable);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
