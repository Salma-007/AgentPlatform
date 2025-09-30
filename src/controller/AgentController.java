package controller;

import enums.TypeAgent;
import exception.AgentNotFoundException;
import model.Agent;
import model.Departement;
import service.AgentServiceImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentController {

    private AgentServiceImp service;

    public AgentController(AgentServiceImp service){
        this.service = service;
    }

    public void addAgent(String nom, String prenom, String email, String motDePasse, Departement departement, TypeAgent type) throws Exception {
        Agent agent = new Agent(nom, prenom, email, motDePasse, departement ,type);
        Validator.notEmpty(nom, "nom");
        Validator.notEmpty(prenom, "prenom");
        Validator.validEmail(email);
        try {
            service.ajout(agent);
            System.out.println("Agent ajouté avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur inattendue : " + e.getMessage());
        }
    }

    public Agent getAgentId(int id) throws Exception {
        try {
            Validator.validId(id, "id");
            Agent agentId = service.findById(id);
            System.out.println(" nom : " + agentId.getNom() +
                    " prenom: " + agentId.getPrenom() +
                    " email: " + agentId.getEmail() +
                    " departement: " + agentId.getDepartement().getNom());
            return agentId;
        } catch (AgentNotFoundException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
        return null;
    }

    public List<Agent> agentsList(){
        List<Agent> agents = new ArrayList<>();
        agents = service.retrieveAll();

        for(Agent i : agents){
            System.out.println("id: "+i.getIdAgent()+" nom : "+i.getPrenom()+" prenom: "+i.getPrenom()+" email: "+i.getEmail()+" departement: "+i.getDepartement().getNom()+" type: "+i.getType().name());
        }
        return agents;
    }

    public void modifierAgent(int id, String nom, String prenom, String email, String motDePasse, Departement departement, TypeAgent type){
        Agent ag = new Agent(id, nom, prenom, email, motDePasse, departement, type);
        service.modification(ag);
    }

    public void deleteAgent(int id) throws SQLException {
        Agent agent = service.findById(id);
        if(agent == null){
            System.out.println("agent not found!");
            return;
        }
        service.suppression(agent);
    }

    public Agent getAgentByName(String nom){
        return service.findByName(nom);
    }

    public double getTotalByAgent(Agent agent){
        return service.getTotalPayments(agent);
    }
}

