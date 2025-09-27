package interfaces;

import model.Agent;
import model.Departement;
import java.util.List;

import java.sql.SQLException;

public interface IDepartementDAO extends GenericDAO<Departement, Integer>{

    List<Agent> getAgentsByDepartement(Departement dep);

}
