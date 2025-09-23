package interfaces;

import enums.TypeAgent;
import model.Departement;

public interface IAgent {

    public String createAgent(String nom, String prenom, String email, String motDePasse, Departement departement, TypeAgent type);


}
