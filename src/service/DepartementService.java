package service;

import DAO.DepartementDAO;
import model.Agent;
import model.Departement;
import repository.DepartementRepository;

public class DepartementService implements DepartementDAO {
    public DepartementService(DepartementRepository departementRepository) {
    }

    @Override
    public String createDepartement(String nom, Agent responsable) {
        String createDepartement = new DepartementRepository().createDepartement(new Departement(nom, responsable));
        System.out.println("Department created succefully!");
        return "";
    }
}
