package DAO.interfaces;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> {
    void save(T entity);
    void update( T entity);
    void delete(T entity);
    List<T> findAll();
    Optional<T> findById(int id);
    T findByName(String nom);
}
