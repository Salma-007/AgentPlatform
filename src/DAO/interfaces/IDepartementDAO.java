package DAO.interfaces;

import model.Agent;
import model.Departement;
import java.util.List;
import java.util.Optional;

public interface IDepartementDAO extends GenericDAO<Departement>{

    List<Agent> getAgentsByDepartement(Departement dep);
    Departement getDepartementAndResponsable(String nom);
    public Optional<Departement> getDepartementAndResponsable(int id);

}
