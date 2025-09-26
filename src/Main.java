import DAO.AgentDAO;
import DAO.DepartementDAO;
import com.sun.jdi.connect.spi.Connection;
import config.DatabaseConnection;
import controller.*;
import enums.*;
import interfaces.*;
import model.*;
import repository.*;
import service.*;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        DatabaseConnection.getConnection();

        IDepartementDAO depDAO = new DepartementDAO();
        DepartementRepository departementRepository = new DepartementRepository((DepartementDAO) depDAO);
        DepartementService departementService = new DepartementService(departementRepository);
        DepartementController departementController = new DepartementController(departementService);

        IAgentDAO agentDAO = new AgentDAO();
        AgentRepository agentrepo = new AgentRepository((AgentDAO) agentDAO);
        AgentService agentservice = new AgentService(agentrepo, departementRepository);
        AgentController agentController = new AgentController(agentservice);

        agentController.getAgentId(1);

//        agentController.addAgent("sami", "hamza", "ham@gmail.com", "1234", new Departement("dep1"),TypeAgent.STAGIAIRE);

        agentController.agentsList();





//        departementController.addDepartement("dep1");
//        departementController.getDepId(2);





    }
}