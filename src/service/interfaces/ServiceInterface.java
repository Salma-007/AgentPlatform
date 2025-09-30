package service.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface ServiceInterface<T> {
    void ajout(T entity);
    void modification(T entity);
    void suppression(T entity);
    List<T> retrieveAll();
    T findById(int id) throws SQLException;
    T findByName(String nom);
}
