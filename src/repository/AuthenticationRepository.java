package repository;

import DAO.AuthenticationDAO;

public class AuthenticationRepository {
    private AuthenticationDAO authDAO;

    public AuthenticationRepository(AuthenticationDAO authDAO) {
        this.authDAO = authDAO;
    }

    public int authenticateAgent(String email, String motDePasse) {
        return authDAO.authenticate(email, motDePasse);
    }

    public boolean isEmailExists(String email) {
        return authDAO.emailExists(email);
    }
}
