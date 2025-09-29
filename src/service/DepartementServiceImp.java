package service;

import model.Agent;
import model.Departement;
import repository.DepartementRepository;
import service.interfaces.DepartementService;

import java.sql.SQLException;
import java.util.List;

public class DepartementServiceImp implements DepartementService {
    private DepartementRepository repo;

    public DepartementServiceImp(DepartementRepository departementRepository){
        this.repo = departementRepository;
    }

    @Override
    public void ajout(Departement departement) {
        repo.createDepartement(departement);
    }

    @Override
    public void modification(Departement departement) {
        repo.modifierDepartement(departement);
    }

    @Override
    public void suppression(Departement departement) {
        repo.supprimerDepartement(departement);
    }

    @Override
    public List<Departement> retrieveAll() {
        return repo.getDepartements();
    }

    @Override
    public Departement findById(int id) {
        return repo.getDepartementById(id);
    }

    @Override
    public Departement findByName(String nom) {
        return repo.getDepartementByName(nom);
    }

    @Override
    public List<Agent> retrieveAgentsByDepartement(Departement dep) {
        return repo.getAgentsbyDep(dep);
    }

    @Override
    public Departement retrieveDepartementAndResponsable(String nom) {
        return repo.trouverDepartementResponsableByName(nom);
    }
}
