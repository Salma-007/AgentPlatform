package interfaces;

import java.util.List;

public interface GenericDAO<T, ID> {
    void save(T entity);
    void update( T entity);
    void delete(T entity);
//    List<T> findAll();
    T findById(int id);
}
