package DAO.interfaces;

import java.util.List;

public interface GenericDAO<T> {
    void save(T entity);
    void update( T entity);
    void delete(T entity);
    List<T> findAll();
    T findById(int id);
    T findByName(String nom);
}
