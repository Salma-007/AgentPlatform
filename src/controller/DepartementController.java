package controller;

import model.Departement;
import service.DepartementService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartementController {

    private DepartementService service;

    public DepartementController(DepartementService service){
        this.service = service;
    }

    public void addDepartement(String nom) throws SQLException {
        Departement dep = new Departement(nom);
        service.createDepartement(dep);
        System.out.println("departement ajouté : " + dep.getNom());
    }

    public void getDepId(int id){
        Departement depResultat = service.getDepartementbyId(id);
        System.out.println(" le departement id est: "+depResultat.getIdDepartement()+" son nom est: "+depResultat.getNom()+ " le responsable est: "+depResultat.getResponsable().getNom()+" "+depResultat.getResponsable().getPrenom());
    }

    public void departementsList(){
        List<Departement> deps = new ArrayList<>();
        deps = service.retrieveDepartements();

        for(Departement i: deps){
            System.out.println("id: "+i.getIdDepartement()+" nom: "+i.getNom());
        }
    }
}
