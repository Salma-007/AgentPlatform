package service;

import exception.DepartementNotFoundException;
import exception.DepartmentExceptionAlreadyExists;
import model.Agent;
import model.Departement;
import repository.DepartementRepository;
import service.interfaces.DepartementService;

import java.util.List;
import java.util.Optional;

public class DepartementServiceImp implements DepartementService {
    private DepartementRepository repo;

    public DepartementServiceImp(DepartementRepository departementRepository){
        this.repo = departementRepository;
    }

    @Override
    public void ajout(Departement departement) {
        Departement existant = repo.getDepartementByName(departement.getNom());

        if (existant != null) {
            throw new DepartmentExceptionAlreadyExists("⚠️ Un département avec le nom '" + departement.getNom() + "' existe déjà !");
        }
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
    public Departement findById(int id) throws DepartementNotFoundException {
        Optional<Departement> departement = repo.getDepartementById(id);
        return departement.orElseThrow(()-> new DepartementNotFoundException("department not found!"));
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

    @Override
    public Departement getDepartementAndResponsable(int id) throws DepartementNotFoundException {
        Optional<Departement> departement = repo.getDepartementAndResponsable(id);
        return departement.orElseThrow(()-> new DepartementNotFoundException("department not found!"));
    }

}
