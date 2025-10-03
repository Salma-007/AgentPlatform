package service.interfaces;

import exception.DepartementNotFoundException;
import model.Agent;
import model.Departement;

import java.util.List;

public interface DepartementService extends ServiceInterface<Departement>{
    List<Agent> retrieveAgentsByDepartement(Departement dep);
    Departement retrieveDepartementAndResponsable(String nom);
    Departement getDepartementAndResponsable(int id) throws DepartementNotFoundException;
}
