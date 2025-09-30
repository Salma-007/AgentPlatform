package service.interfaces;

import model.Agent;

import java.sql.SQLException;

public interface AuthenticationService {
    public Agent login(String email, String mdp) throws SQLException;
    public boolean emailExists(String email);
}
