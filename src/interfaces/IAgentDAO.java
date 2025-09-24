package interfaces;
import model.Agent;

import java.sql.SQLException;

public interface IAgentDAO {
    void saveAgent(Agent agent) throws SQLException;
    
}
