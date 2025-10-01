package controller;

import exception.DepartementNotFoundException;
import model.Agent;
import model.Departement;
import service.DepartementServiceImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartementController {

    private DepartementServiceImp service;

    public DepartementController(DepartementServiceImp service){
        this.service = service;
    }

    public void addDepartement(String nom) throws SQLException {
        Departement dep = new Departement(nom);
        service.ajout(dep);
        System.out.println("departement ajouté : " + dep.getNom());
    }

    public Optional<Departement> getDepId(int id) throws DepartementNotFoundException {
        Departement depResultat = service.findById(id);
        System.out.println(" le departement id est: "+depResultat.getIdDepartement()+" son nom est: "+depResultat.getNom()+ " le responsable est: "+depResultat.getResponsable().getNom()+" "+depResultat.getResponsable().getPrenom());
        return Optional.of(depResultat);
    }

    public void departementsList(){
        List<Departement> deps = new ArrayList<>();
        deps = service.retrieveAll();

        for(Departement i: deps){
            System.out.println("id: "+i.getIdDepartement()+" nom: "+i.getNom());
        }
    }

    public List<Agent> agentsByDepatement(String nom) {
        Departement dep = service.findByName(nom);

        if (dep == null) {
            System.out.println("departement invalide!");
            return new ArrayList<>();
        } else {
            List<Agent> agents = service.retrieveAgentsByDepartement(dep);

            for (Agent agent : agents) {
                System.out.println("id: " + agent.getIdAgent() +" nom : " + agent.getNom() + " " + agent.getPrenom());
            }

            if (agents.isEmpty()) {
                System.out.println("no agents found!");
            }
            return agents;
        }
    }

    public Departement getDepartementbyName(String nom){
        return service.findByName(nom);
    }

}
