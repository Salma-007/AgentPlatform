package controller;

import enums.TypeAgent;
import model.Agent;
import model.Departement;
import service.AgentService;

import java.sql.SQLException;

public class AgentController {

    private AgentService service;

    public AgentController(AgentService service){
        this.service = service;
    }


    public void addAgent(String nom, String prenom, String email, String motDePasse, Departement departement, TypeAgent type) throws SQLException {
        Agent agent = new Agent(nom, prenom, email, motDePasse, departement ,type);
        service.createAgent(agent);
        System.out.println("Agent ajouté : " + agent.getNom());
    }

    public Agent getAgentId(int id) throws SQLException {
        Agent agentId = service.getAgentByid(id);
        System.out.println("id: "+agentId.getIdAgent()+" nom : "+agentId.getNom()+" depart : "+agentId.getDepartement().getNom());
        return agentId;
    }
}

