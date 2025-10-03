package service.interfaces;

import exception.DepartementNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ServiceInterface<T> {
    void ajout(T entity) throws SQLException;
    void modification(T entity) throws DepartementNotFoundException;
    void suppression(T entity);
    List<T> retrieveAll();
    T findById(int id) throws SQLException, DepartementNotFoundException;
    T findByName(String nom);
}
