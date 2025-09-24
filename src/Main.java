import DAO.AgentDAO;
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

        agentController.addAgent("soum", "salima", "s@gmail.com", "123456", new Departement("reseau", null),TypeAgent.ouvrier);

//        Departement dep = new Departement("IA", agent1);


    }
}