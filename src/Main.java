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
    public static void main(String[] args) throws SQLException {
        DatabaseConnection.getConnection();

        IAgentDAO agentDAO = new AgentDAO();
        AgentRepository agentrepo = new AgentRepository((AgentDAO) agentDAO);
        AgentService agentservice = new AgentService(agentrepo);
        AgentController agentController = new AgentController(agentservice);

        agentController.getAgentId(1);
//
//        agentController.addAgent("soum", "salima", "s@gmail.com", "123456", new Departement("reseau"),TypeAgent.ouvrier);

        IDepartementDAO depDAO = new DepartementDAO();
        DepartementRepository departementRepository = new DepartementRepository((DepartementDAO) depDAO);
        DepartementService departementService = new DepartementService(departementRepository);
        DepartementController departementController = new DepartementController(departementService);

//        departementController.addDepartement("dep1");
//        departementController.getDepId(2);





    }
}