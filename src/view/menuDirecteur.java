package view;

import controller.DepartementController;
import exception.DepartementNotFoundException;
import model.Agent;
import model.Departement;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class menuDirecteur {

    private Scanner scanner;
    private DepartementController depController;

    public menuDirecteur(DepartementController depController) {
        this.scanner = new Scanner(System.in);
        this.depController = depController;
    }

    public void start() {
        boolean running = true;

        while (running) {
            clearScreen();
            System.out.println("===== GESTION DES DEPARTEMENTS =====");
            System.out.println("1. Ajouter un département");
            System.out.println("2. Afficher tous les départements");
            System.out.println("3. Chercher un département par ID");
            System.out.println("4. Afficher les agents d’un département");
            System.out.println("0. Retour / Quitter");
            System.out.print("Votre choix : ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addDepartement();
                    break;
                case "2":
                    listDepartements();
                    break;
                case "3":
                    getDepartementById();
                    break;
                case "4":
                    listAgentsByDepartement();
                    break;
                case "0":
                    running = false;
                    System.out.println("Retour au menu précédent...");
                    break;
                default:
                    System.out.println("Option invalide !");
            }
        }
    }

    private void addDepartement() {
        System.out.print("Nom du département : ");
        String nom = scanner.nextLine();
        try {
            depController.addDepartement(nom);
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    private void listDepartements() {
        System.out.println("\nListe des départements :");
        depController.departementsList();
    }

    private void getDepartementById() {
        System.out.print("ID du département : ");
        int id = Integer.parseInt(scanner.nextLine());
        try {
            depController.getDepId(id);
        } catch (DepartementNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void listAgentsByDepartement() {
        System.out.print("Nom du département : ");
        String nom = scanner.nextLine();
        List<Agent> agents = depController.agentsByDepatement(nom);
        if (!agents.isEmpty()) {
            System.out.println("\nAgents du département " + nom + " :");
            for (Agent a : agents) {
                System.out.println("-id " + a.getIdAgent() +" nom: " + a.getNom() + " " + a.getPrenom()+" email: " + a.getEmail());
            }
        }
        else{
            System.out.println("not agent found for the department!");
        }
    }


    private void clearScreen() {
        System.out.print("\n\n\n");
    }
}
