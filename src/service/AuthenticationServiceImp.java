package service;

import model.Agent;
import repository.AgentRepository;
import repository.AuthenticationRepository;
import service.interfaces.AuthenticationService;

import java.sql.SQLException;
import java.util.Optional;

public class AuthenticationServiceImp implements AuthenticationService {

    private AuthenticationRepository authRepo;
    private AgentRepository agentRepo;

    public AuthenticationServiceImp(AuthenticationRepository authRepo, AgentRepository agentRepo) {
        this.authRepo = authRepo;
        this.agentRepo = agentRepo;
    }

    @Override
    public Agent login(String email, String mdp) throws SQLException {
        int agentId = authRepo.authenticateAgent(email, mdp);

        if (agentId != -1) {
            Optional<Agent> agentOptional = agentRepo.getAgentId(agentId);
            return agentOptional.get();
        }

        return null;
    }

    @Override
    public boolean emailExists(String email) {
        return authRepo.isEmailExists(email);
    }
}
