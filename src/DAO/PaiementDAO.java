package DAO;

import config.DatabaseConnection;
import enums.TypePaiement;
import DAO.interfaces.IAgentDAO;
import DAO.interfaces.IPaiementDAO;
import model.Agent;
import model.Paiement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PaiementDAO implements IPaiementDAO {
    @Override
    public void save(Paiement paiement) {
    String querySQL = "insert into paiement (typePaiement, montant, date, motif, idAgent) values (?, ?, ?, ?, ?)";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL)){

            statement.setString(1, paiement.getType().name());
            statement.setDouble(2, paiement.getMontant());
            statement.setDate(3, (Date) paiement.getDate());
            statement.setString(4, paiement.getMotif());
            statement.setInt(5, paiement.getAgent().getIdAgent());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new paiement was added successfully!");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Paiement paiement) {
        String querySQL = "UPDATE paiement SET typePaiement = ?, montant = ?, date = ?, motif = ?, idAgent = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySQL)) {

            statement.setString(1, paiement.getType().name());
            statement.setDouble(2, paiement.getMontant());
            statement.setDate(3, (Date) paiement.getDate());
            statement.setString(4, paiement.getMotif());
            statement.setInt(5, paiement.getAgent().getIdAgent());
            statement.setInt(6, paiement.getIdPaiement());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("the payment was updated successfully! ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Paiement paiement) {
        String querySQL = "DELETE FROM paiement WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySQL)) {

            statement.setInt(1, paiement.getIdPaiement());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("the paiement was deleted successfully!");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Paiement> findAll() {
        List<Paiement> paiements = new ArrayList<>();
        String querySQL = "SELECT * FROM paiement";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySQL)) {
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                IAgentDAO agentDAO = new AgentDAO();
                Agent agent = agentDAO.findById(resultSet.getInt("idAgent"));
                String typePaiement = resultSet.getString("typePaiement");

                paiements.add(new Paiement(resultSet.getInt("id"), TypePaiement.valueOf(typePaiement), resultSet.getDouble("montant"), resultSet.getDate("date"), resultSet.getString("motif"), agent));
            }
            return paiements;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Paiement findById(int id) {
        String querySQL = "SELECT * FROM paiement WHERE id = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL);){

            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                int idPaiement = resultSet.getInt("id");
                String type = resultSet.getString("typePaiement");
                Double montant = resultSet.getDouble("montant");
                Date date = resultSet.getDate("date");
                String motif = resultSet.getString("motif");
                int agentId = resultSet.getInt("idAgent");

                IAgentDAO agentDAO = new AgentDAO();
                Agent agent = agentDAO.findById(agentId);

                return new Paiement(idPaiement, TypePaiement.valueOf(type), montant, date, motif, agent);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Paiement findByName(String nom) {
        return null;
    }

}
