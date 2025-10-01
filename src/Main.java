import DAO.AgentDAO;
import DAO.AuthenticationDAO;
import DAO.DepartementDAO;
import DAO.PaiementDAO;
import config.DatabaseConnection;
import controller.*;
import enums.*;
import DAO.interfaces.*;
import model.*;
import repository.*;
import service.*;
import service.interfaces.AgentService;
import service.interfaces.AuthenticationService;
import service.interfaces.DepartementService;
import service.interfaces.PaiementService;
import view.menuAgent;

public class Main {
    public static void main(String[] args) throws Exception {
        DatabaseConnection.getConnection();

        AgentDAO agentDAO = new AgentDAO();
        AuthenticationDAO authDAO = new AuthenticationDAO();

        DepartementDAO departementDAO = new DepartementDAO();

        PaiementDAO paiementDAO = new PaiementDAO();

        // Repository Layer
        AgentRepository agentRepo = new AgentRepository(agentDAO);
        PaiementRepository paiementRepo = new PaiementRepository(paiementDAO);

        AuthenticationRepository authRepo = new AuthenticationRepository(authDAO);

        DepartementRepository deptRepo = new DepartementRepository(departementDAO);
        PaiementServiceImp paiementService = new PaiementServiceImp(paiementRepo, agentRepo);

        // Service Layer
        AgentServiceImp agentService = new AgentServiceImp(agentRepo, deptRepo, paiementService);
        AuthenticationService authService = new AuthenticationServiceImp(authRepo, agentRepo);
        DepartementServiceImp departementService = new DepartementServiceImp(deptRepo);

        // Controller Layer
        PaiementController paiementController = new PaiementController(paiementService);
        AgentController controller = new AgentController(agentService);
        DepartementController depcontroller = new DepartementController(departementService);

        // View Layer
        menuAgent view = new menuAgent(controller, depcontroller,paiementController);

        // Démarrage
        view.start();

    }
}