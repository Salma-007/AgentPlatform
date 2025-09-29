package service.interfaces;

import model.Agent;
import model.Departement;

import java.util.List;

public interface DepartementService extends ServiceInterface<Departement>{
    List<Agent> retrieveAgentsByDepartement(Departement dep);
    Departement retrieveDepartementAndResponsable(String nom);
}
