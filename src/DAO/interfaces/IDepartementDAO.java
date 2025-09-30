package DAO.interfaces;

import model.Agent;
import model.Departement;
import java.util.List;

public interface IDepartementDAO extends GenericDAO<Departement>{

    List<Agent> getAgentsByDepartement(Departement dep);
    Departement getDepartementAndResponsable(String nom);

}
