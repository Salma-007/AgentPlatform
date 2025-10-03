package view;

import controller.AgentController;
import controller.DepartementController;
import controller.PaiementController;
import enums.TypeAgent;
import enums.TypePaiement;
import exception.DepartementNotFoundException;
import exception.PaymentNotFoundException;
import model.Agent;
import model.Departement;
import model.Paiement;

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

    public void start(Agent agentAuthentifie) throws Exception {
        boolean running = true;

        while (running) {
            clearScreen();
            System.out.println("  MENU DIRECTEUR ");
            System.out.println("1. Ajouter un département");
            System.out.println("2. Afficher tous les départements");
            System.out.println("3. Chercher un département par ID");
            System.out.println("4. Afficher les agents d’un département");
            System.out.println("5. ajouter un paiement pour un agent");
            System.out.println("6. Afficher les paiements invalides");
            System.out.println("7. Valider un paiement");
            System.out.println("8. Afficher tous les agents ");
            System.out.println("9. Ajouter un responsable ");
            System.out.println("10. Afficher total paiement par departement ");
            System.out.println("11. Afficher élévé paiement par departement ");
            System.out.println("12. Afficher faible paiement par departement ");
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
                case "6":
                    showInvalidePaiement();
                    break;
                case "7":
                    validPaiement();
                    break;
                case "8":
                    allAgents();
                    break;
                case "9":
                    ajouterResponsable();
                    break;
                case "10":
                    totalParDepartement();
                    break;
                case "11":
                    eleveParDepartement();
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

    private void ajouterResponsable() {
        try {
            System.out.print("ID du département : ");
            int id = Integer.parseInt(scanner.nextLine());

            Optional<Departement> depart = depController.getDepartementAndResponsable(id);

            if(!depart.isPresent()){
                System.out.println(" Département introuvable!");
                return;
            }

            Departement dep = depart.get();

            if(dep.getResponsable() != null){
                System.out.println("⚠️ Ce département a déjà un responsable : "
                        + dep.getResponsable().getNom() + " " + dep.getResponsable().getPrenom());
                return;
            }

            System.out.print("Nom: ");
            String nom = scanner.nextLine().trim();

            System.out.print("Prénom: ");
            String prenom = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Mot de passe: ");
            String motDePasse = scanner.nextLine();

            agentcontroller.addAgent(nom, prenom, email, motDePasse, dep, TypeAgent.RESPONSABLE_DEPARTEMENT);

            System.out.println("✓ Responsable ajouté avec succès!");

        } catch (NumberFormatException e) {
            System.out.println(" ID invalide! Veuillez entrer un nombre.");
        } catch (Exception e) {
            System.out.println(" Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void eleveParDepartement(){
        try {
            System.out.print("ID du département : ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            depController.getElevePaiementPerDepartement(id);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Veuillez entrer un nombre valide !");
        } catch (DepartementNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void allAgents(){
        System.out.println("═══ LISTE DES AGENTS ═══\n");

        List<Agent> agents = agentcontroller.agentsList();

        if (agents == null || agents.isEmpty()) {
            System.out.println("Aucun agent trouvé.");
            return;
        }

        System.out.println("Total: " + agents.size() + " agents\n");

        for (Agent agent : agents) {
            displayAgentCard(agent);
        }
    }

    private void displayAgentCard(Agent agent) {
        System.out.println("│ ID: " + agent.getIdAgent());
        System.out.println("│ Nom: " + agent.getNom() + " " + agent.getPrenom());
        System.out.println("│ Email: " + agent.getEmail());
        System.out.println("│ Département: " + agent.getDepartement().getNom());
        System.out.println("│ Type: " + agent.getType());
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
        try {
            System.out.print("ID du département : ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            depController.getDepId(id);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Veuillez entrer un nombre valide !");
        } catch (DepartementNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void totalParDepartement(){
        try {
            System.out.print("ID du département : ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            depController.getTotalPaiementPerDepartment(id);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Veuillez entrer un nombre valide !");
        } catch (DepartementNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void validPaiement() {
        try {
            System.out.print("ID du paiement : ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            paiementcontroller.validatePaymentById(id);
            System.out.println(" Paiement validé avec succès !");

        } catch (NumberFormatException e) {
            System.out.println(" Veuillez entrer un nombre valide !");
        } catch (PaymentNotFoundException e) {
            System.out.println(" Erreur : " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Une erreur est survenue : " + e.getMessage());
        }
    }

    private void listAgentsByDepartement() {
        System.out.print("Nom du département : ");
        String nom = scanner.nextLine();
        List<Agent> agents = depController.agentsByDepatement(nom);
        if (!agents.isEmpty()) {
            System.out.println("\nAgents du département " + nom + " :");
            for (Agent a : agents) {
                System.out.println("-id " + a.getIdAgent() +" nom: " + a.getNom() + " " + a.getPrenom()+" email: " + a.getEmail()+" role: "+a.getType().name());
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

    private void showInvalidePaiement(){
        System.out.println("LISTE PAIEMENTS INVALIDES \n");

        List<Paiement> paiements = paiementcontroller.retrieveInvalidePayments();
        if(!paiements.isEmpty()){
            for(Paiement p:paiements){
                System.out.println("id payment: "+p.getIdPaiement()+" agent: "+p.getAgent().getNom()+" "+p.getAgent().getPrenom()+" montant: "+p.getMontant()+" date: "+p.getDate()+" motif: "+p.getMotif());
            }
        }else{
            System.out.println("no payments found!");
        }
    }
}
