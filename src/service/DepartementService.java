package service;

import DAO.DepartementDAO;
import model.Agent;
import model.Departement;
import repository.DepartementRepository;

import java.sql.SQLException;

public class DepartementService {
    private DepartementRepository repo;

    public DepartementService(DepartementRepository departementRepository){
        this.repo = departementRepository;
    }

    public void createDepartement(Departement departement) throws SQLException {
        repo.createDepartement(departement);
    }

    public Departement getDepartementbyId(int id){
        return repo.getDepartementById(id);
    }


}
