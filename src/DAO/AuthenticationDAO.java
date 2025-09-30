package DAO;

import DAO.interfaces.IAuthenticationDAO;
import config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthenticationDAO implements IAuthenticationDAO {
    @Override
    public int authenticate(String email, String motDePasse) {
        String querySQL = "SELECT id FROM agent WHERE email = ? AND mdp = ?";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL)){

            statement.setString(1, email);
            statement.setString(2, motDePasse);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'authentification: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean emailExists(String email) {
        String querySQL = "SELECT COUNT(*) FROM agent WHERE email = ?";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL)){

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
