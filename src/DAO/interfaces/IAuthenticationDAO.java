package DAO.interfaces;

public interface IAuthenticationDAO {
    public int authenticate(String email, String motDePasse);
    public boolean emailExists(String email);
}
