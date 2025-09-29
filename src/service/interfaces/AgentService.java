package service.interfaces;

import exception.DepartementNotFoundException;
import exception.ResponsableDejaExistantException;
import model.Agent;

import java.sql.SQLException;

public interface AgentService extends ServiceInterface<Agent> {
    Double getTotalPayments(Agent agent);
}
