package service;

import DAO.DepartementDAO;
import model.Agent;
import model.Departement;
import repository.DepartementRepository;

import java.sql.SQLException;
import java.util.List;

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

    public Departement getDepartementbyName(String nom){
        return repo.getDepartementByName(nom);
    }

    public List<Departement> retrieveDepartements(){
        return repo.getDepartements();
    }

}
