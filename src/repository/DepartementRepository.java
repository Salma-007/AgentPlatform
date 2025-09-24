package repository;

import config.DatabaseConnection;
import model.Departement;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DepartementRepository {
    public String createDepartement(Departement departement){
        String querySQL = "INSERT INTO departement (nom, responsable) VALUES (? , ?)";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL)){
            statement.setString(1, departement.getNom());
            statement.setInt(2, departement.getResponsable().getIdAgent());

            // Execute the SQL query
            int rowsInserted = statement.executeUpdate();

            // checking if the department is added
            if (rowsInserted > 0) {
                System.out.println("A new department was added successfully!");
                return departement.getNom();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
