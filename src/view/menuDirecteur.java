package view;

import controller.AgentController;
import controller.DepartementController;
import controller.PaiementController;
import enums.TypePaiement;
import exception.DepartementNotFoundException;
import model.Agent;
import model.Departement;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class menuDirecteur {

    private Scanner scanner;
    private DepartementController depController;
    private PaiementController paiementcontroller;
    private AgentController agentcontroller;

    public menuDirecteur(DepartementController depController, PaiementController paiement, AgentController agentcontroller) {
        this.scanner = new Scanner(System.in);
        this.depController = depController;
        this.paiementcontroller = paiement;
        this.agentcontroller = agentcontroller;
    }

    public void start(Agent agentAuthentifie) {
        boolean running = true;

        while (running) {
            clearScreen();
            System.out.println("===== GESTION DES DEPARTEMENTS =====");
            System.out.println("1. Ajouter un département");
            System.out.println("2. Afficher tous les départements");
            System.out.println("3. Chercher un département par ID");
            System.out.println("4. Afficher les agents d’un département");
            System.out.println("5. ajouter un paiement pour un agent");
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
                case "5":
                    showAddPaiementView(agentAuthentifie);
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

    private void showAddPaiementView(Agent agentAuth) {
        System.out.println("AJOUTER UN PAIEMENT \n");
        try {
            System.out.print("ID de l'agent: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Optional<Agent> agentOPt = agentcontroller.getAgentId(id);
            if (!agentOPt.isPresent()) {
                System.out.println(" Agent introuvable!");
                return;
            }
            Agent agent = agentOPt.get();
            System.out.println("Types de paiement:");
            System.out.println("  1. SALAIRE");
            System.out.println("  2. PRIME");
            System.out.println("  3. BONUS");
            System.out.println("  4. INDEMNITE");
            System.out.print("Choisissez (1 ou 2): ");
            String choixType = scanner.nextLine().trim();

            TypePaiement type;
            switch (choixType) {
                case "1": type = TypePaiement.SALAIRE; break;
                case "2": type = TypePaiement.PRIME; break;
                case "3": type = TypePaiement.BONUS; break;
                case "4": type = TypePaiement.INDEMNITE; break;
                default:
                    System.out.println("Type invalide, SALAIRE par défaut.");
                    type = TypePaiement.SALAIRE;
            }

            System.out.print("Montant: ");
            double montant = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Motif: ");
            String motif = scanner.nextLine().trim();

            paiementcontroller.ajouterPaiement(type, montant, motif, agent, agentAuth);
            System.out.println("Paiement ajouté avec succès!");

        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide !");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
}
