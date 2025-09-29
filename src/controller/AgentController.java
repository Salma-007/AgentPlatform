package controller;

import enums.TypeAgent;
import exception.DepartementNotFoundException;
import exception.ResponsableDejaExistantException;
import model.Agent;
import model.Departement;
import service.AgentService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentController {

    private AgentService service;

    public AgentController(AgentService service){
        this.service = service;
    }

    public void addAgent(String nom, String prenom, String email, String motDePasse, Departement departement, TypeAgent type) throws Exception {
        Agent agent = new Agent(nom, prenom, email, motDePasse, departement ,type);
        Validator.notEmpty(nom, "nom");
        try {
            service.createAgent(agent);
            System.out.println("✅ Agent ajouté avec succès !");
        } catch (ResponsableDejaExistantException e) {
            System.out.println(e.getMessage());
        } catch (DepartementNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur inattendue : " + e.getMessage());
        }

    }

    public Agent getAgentId(int id) throws Exception {
        Validator.validId(id, "id");
        Agent agentId = service.getAgentByid(id);
        System.out.println(" nom : "+agentId.getPrenom()+" prenom: "+agentId.getPrenom()+" email: "+agentId.getEmail()+" departement: "+agentId.getDepartement().getNom());
        return agentId;
    }

    public void agentsList(){
        List<Agent> agents = new ArrayList<>();
        agents = service.retrieveAgents();

        for(Agent i : agents){
            System.out.println("id: "+i.getIdAgent()+" nom : "+i.getPrenom()+" prenom: "+i.getPrenom()+" email: "+i.getEmail()+" departement: "+i.getDepartement().getNom()+" type: "+i.getType().name());
        }

    }

    public void modifierAgent(int id, String nom, String prenom, String email, String motDePasse, Departement departement, TypeAgent type){
        Agent ag = new Agent(id, nom, prenom, email, motDePasse, departement, type);
        service.modifierAgent(ag);
    }

    public void deleteAgent(int id) throws SQLException {
        Agent agent = service.getAgentByid(id);
        if(agent == null){
            System.out.println("agent not found!");
            return;
        }
        service.deleteAgent(agent);
    }
}

