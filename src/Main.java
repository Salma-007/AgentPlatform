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
import view.menuDirecteur;
import view.menuEmploye;

import java.util.Scanner;

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
        AgentController controller = new AgentController(agentService, authService);
        DepartementController depcontroller = new DepartementController(departementService);

        // View Layer
        menuAgent view = new menuAgent(controller, depcontroller,paiementController);
        menuEmploye viewEmploye = new menuEmploye(controller, paiementController);
        menuDirecteur viewDirecteur = new menuDirecteur(depcontroller);

        // Démarrage
//        viewEmploye.showAgentMenu();
//        view.start();

        //login
        Scanner sc = new Scanner(System.in);
        while (true) {
            Agent connectedAgent = null;

            // Boucle login
            while (connectedAgent == null) {
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Mot de passe: ");
                String mdp = sc.nextLine();

                connectedAgent = authService.login(email, mdp);

                if (connectedAgent == null) {
                    System.out.println("Identifiants invalides, veuillez réessayer.\n");
                }
            }

            controller.setCurrentAgent(connectedAgent);
            System.out.println("Bienvenue " + connectedAgent.getNom() + " !");
            TypeAgent type = connectedAgent.getType();

            // Menu selon type
            switch (type) {
                case OUVRIER:
                    viewEmploye.showAgentMenu();
                    break;

                case RESPONSABLE_DEPARTEMENT:
                    view.start();
                    break;

                case DIRECTEUR:
                    viewDirecteur.start();
                    break;

                default:
                    System.out.println("Type d'agent inconnu !");
            }

            System.out.println("\nRetour à l'écran de connexion...\n");
        }

    }
}