import DAO.AgentDAO;
import DAO.DepartementDAO;
import config.DatabaseConnection;
import controller.*;
import enums.*;
import interfaces.*;
import model.*;
import repository.*;
import service.*;
import service.interfaces.PaiementService;

public class Main {
    public static void main(String[] args) throws Exception {
        DatabaseConnection.getConnection();

        IDepartementDAO depDAO = new DepartementDAO();
        DepartementRepository departementRepository = new DepartementRepository((DepartementDAO) depDAO);
        DepartementServiceImp departementServiceImp = new DepartementServiceImp(departementRepository);
        DepartementController departementController = new DepartementController(departementServiceImp);

        IAgentDAO agentDAO = new AgentDAO();
        AgentRepository agentrepo = new AgentRepository((AgentDAO) agentDAO);
        // ajout de service de paiement aussi
        AgentServiceImp agentservice = new AgentServiceImp(agentrepo, departementRepository);
        AgentController agentController = new AgentController(agentservice);

//        agentController.getAgentId(1);

        agentController.addAgent("kara", "amina", "ka@gmail.com", "1234", new Departement("IT"),TypeAgent.OUVRIER);

//        agentController.agentsList();

//        departementController.addDepartement("dep1");
//        departementController.getDepId(1);
//        departementController.agentsByDepatement("abcd");
//            agentController.deleteAgent(3);




    }
}