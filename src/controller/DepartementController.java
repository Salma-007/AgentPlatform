package controller;

import exception.DepartementNotFoundException;
import exception.DepartmentExceptionAlreadyExists;
import model.Agent;
import model.Departement;
import model.Paiement;
import service.DepartementServiceImp;
import service.StatisticsServiceImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartementController {

    private DepartementServiceImp service;
    private StatisticsServiceImp statesService;

    public DepartementController(DepartementServiceImp service, StatisticsServiceImp states){
        this.service = service;
        this.statesService = states;
    }

    public void addDepartement(String nom) throws SQLException {
        try{
            Departement dep = new Departement(nom);
            service.ajout(dep);
            System.out.println("departement ajouté : " + dep.getNom());

        }catch(DepartmentExceptionAlreadyExists e){
            System.out.println(e.getMessage());
        }
    }

    public Optional<Departement> getDepId(int id) throws DepartementNotFoundException {
        Departement depResultat = service.findById(id);
        System.out.println(" le departement id est: "+depResultat.getIdDepartement()+" son nom est: "+depResultat.getNom()+ " le responsable est: "+depResultat.getResponsable().getNom()+" "+depResultat.getResponsable().getPrenom());
        return Optional.of(depResultat);
    }

    public Optional<Departement> getDepartementAndResponsable(int id) throws DepartementNotFoundException {
        Departement dep = service.getDepartementAndResponsable(id);
        return Optional.of(dep);
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

    public double getTotalPaiementPerDepartment(int id) throws DepartementNotFoundException {
        Departement depResultat = service.findById(id);
        double total = statesService.totalPaiementPerDepartement(depResultat);
        System.out.println("le total de departement: "+depResultat.getNom()+" est: "+total+" DH ");
        return total;
    }

    public Paiement getFaiblePaiementPerDepartement(int id) throws DepartementNotFoundException {
        Departement depResultat = service.findById(id);
        Paiement paiement = statesService.getPaiementFaibleByDepartement(id);
        System.out.println("Paiement faible de: "+depResultat.getNom()+" est de id: "+paiement.getIdPaiement()+" agent: "+paiement.getAgent().getNom()+" de montant: "+paiement.getMontant()+" de date: "+paiement.getDate()+" de type: "+paiement.getType().name());
        return paiement;
    }

    public Paiement getElevePaiementPerDepartement(int id) throws DepartementNotFoundException {
        Departement depResultat = service.findById(id);
        Paiement paiement = statesService.getPaiementEleveByDepartement(id);
        System.out.println("Paiement élevé de: "+depResultat.getNom()+" est de id: "+paiement.getIdPaiement()+" agent: "+paiement.getAgent().getNom()+" de montant: "+paiement.getMontant()+" de date: "+paiement.getDate()+" de type: "+paiement.getType().name());
        return paiement;
    }


}
