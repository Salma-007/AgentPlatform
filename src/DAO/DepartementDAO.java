package DAO;

import config.DatabaseConnection;
import interfaces.IDepartementDAO;
import model.Agent;
import model.Departement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String querySQL = "SELECT * FROM departement WHERE id = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL);){

            statement.setInt(1,id);
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
    public void delete(Departement entity) {

    }
}
