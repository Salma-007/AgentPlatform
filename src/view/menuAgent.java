package view;
import controller.AgentController;
import controller.DepartementController;
import controller.PaiementController;
import enums.TypeAgent;
import enums.TypePaiement;
import model.Agent;
import model.Departement;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class menuAgent {
    private Scanner scanner;
    private AgentController controller;
    private DepartementController depcontroller;
    private PaiementController paiementcontroller;

    public menuAgent(AgentController controller, DepartementController dep, PaiementController paiement) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
        this.depcontroller = dep;
        this.paiementcontroller = paiement;
    }

    public void start(Agent connectedAgent) throws Exception {
        System.out.println(" Système de Gestion des Agents ");

        showMainMenu(connectedAgent);
    }

    private void showMainMenu(Agent agentAuthentifie) throws Exception {
        boolean running = true;

        while (running) {
            System.out.println("  MENU PRINCIPAL ");
            System.out.println("  1. Ajouter un agent");
            System.out.println("  2. Modifier un agent");
            System.out.println("  3. Supprimer un agent");
            System.out.println("  4. Afficher tous les agents");
            System.out.println("  5. Rechercher un agent par ID");
            System.out.println("  6. Rechercher un agent par nom");
            System.out.println("  7. Afficher total paiements d'un agent");
            System.out.println("  8. ajouter un paiement pour un agent");
            System.out.println("  9. Mon profil");
            System.out.println("  0. Quitter");
            System.out.println("─────────────────────────────────────────");
            System.out.print("Votre choix: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showAddAgentView();
                    break;
                case "2":
                    showUpdateAgentView();
                    break;
                case "3":
                    showDeleteAgentView();
                    break;
                case "4":
                    showAllAgentsView();
                    break;
                case "5":
                    showSearchByIdView();
                    break;
                case "6":
                    showSearchByNameView();
                    break;
                case "7":
                    showTotalPaymentsView();
                    break;
                case "8":
                    showAddPaiementView(agentAuthentifie);
                    break;
//                case "8":
//                    showProfileView();
//                    break;
                case "0":
                    running = false;
//                    controller.logout();
                    System.out.println("\n Déconnexion réussie. Au revoir!");
                    break;
                default:
                    System.out.println("\n Option invalide!");
            }
        }
    }

    private void showAddAgentView() throws Exception {
        System.out.println(" AJOUTER UN AGENT \n");

        System.out.print("Nom: ");
        String nom = scanner.nextLine().trim();

        System.out.print("Prénom: ");
        String prenom = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Mot de passe: ");
        String motDePasse = scanner.nextLine();

        System.out.print("Nom du département: ");
        String nomDept = scanner.nextLine().trim();
        Departement dep = depcontroller.getDepartementbyName(nomDept);

        System.out.println("\nTypes d'agent:");
        System.out.println("  1. EMPLOYE");
        System.out.println("  2. RESPONSABLE_DEPARTEMENT");
        System.out.println("  3. DIRECTEUR");
        System.out.print("Choisissez (1-3): ");

        TypeAgent type = getTypeAgentFromInput(scanner.nextLine().trim());

        controller.addAgent(nom, prenom, email, motDePasse, dep, type);
    }

    private TypeAgent getTypeAgentFromInput(String input) {
        switch (input) {
            case "1": return TypeAgent.OUVRIER;
            case "2": return TypeAgent.RESPONSABLE_DEPARTEMENT;
            case "3": return TypeAgent.DIRECTEUR;
            default:
                System.out.println("Type invalide, EMPLOYE par défaut.");
                return TypeAgent.OUVRIER;
        }
    }

    private void showUpdateAgentView() {
        System.out.println(" MODIFIER UN AGENT \n");
        System.out.print("ID de l'agent: ");

        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Optional<Agent> agentOpt = controller.getAgentId(id);

            if (!agentOpt.isPresent()) {
                System.out.println("\n Agent introuvable!");
                return;
            }

            Agent agent = agentOpt.get();

            System.out.println("\nAgent: " + agent.getPrenom() + " " + agent.getNom());
            System.out.println("(Laissez vide pour conserver)\n");

            System.out.print("Nouveau nom [" + agent.getNom() + "]: ");
            String nom = scanner.nextLine().trim();

            System.out.print("Nouveau prénom [" + agent.getPrenom() + "]: ");
            String prenom = scanner.nextLine().trim();

            System.out.print("Nouvel email [" + agent.getEmail() + "]: ");
            String email = scanner.nextLine().trim();

            System.out.print("Nouveau mot de passe: ");
            String mdp = scanner.nextLine();

            System.out.print("Nouveau departement: ");
            String dep = scanner.nextLine();
            Departement departement = depcontroller.getDepartementbyName(dep);

            System.out.print("Nouveau type: ");
            System.out.println("\nTypes d'agent:");
            System.out.println("  1. EMPLOYE");
            System.out.println("  2. RESPONSABLE_DEPARTEMENT");
            System.out.println("  3. DIRECTEUR");
            System.out.print("Choisissez (1-3): ");

            TypeAgent type = getTypeAgentFromInput(scanner.nextLine().trim());

            controller.modifierAgent(id, nom, prenom, email, mdp, departement, type);

        } catch (NumberFormatException e) {
            System.out.println("\n ID invalide!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showAllAgentsView() {
        System.out.println("═══ LISTE DES AGENTS ═══\n");

        List<Agent> agents = controller.agentsList();

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

    private void showSearchByIdView() {
        System.out.println("  RECHERCHER PAR ID \n");
        System.out.print("entrez id de l'agent: ");

        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            if(id <= 0){
                System.out.println("entrez un id valide svp!");
                return;
            }
            Optional<Agent> agentOpt = controller.getAgentId(id);

            if (!agentOpt.isPresent()) {
                System.out.println("\n");
            } else {
                System.out.println();
                displayAgentCard(agentOpt.get());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showSearchByNameView() {
        System.out.println("═══ RECHERCHER PAR NOM ═══\n");
        System.out.print("Nom: ");
        String nom = scanner.nextLine().trim();

        Agent agent = controller.getAgentByName(nom);

        if (agent == null) {
            System.out.println("\n Agent introuvable!");
        } else {
            System.out.println();
            displayAgentCard(agent);
        }
    }

    private void showTotalPaymentsView() {
        System.out.println("═══ TOTAL PAIEMENTS ═══\n");
        System.out.print("ID de l'agent: ");

        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Optional<Agent> agentOpt = controller.getAgentId(id);

            if (!agentOpt.isPresent()) {
                System.out.println("\n Agent introuvable!");
            } else {
                Agent agent = agentOpt.get();
                Double total = controller.getTotalByAgent(agent);
                System.out.println("\nAgent: " + agent.getPrenom() + " " + agent.getNom());
                System.out.println("Total paiements: " + String.format("%.2f DH", total));
            }

        } catch (NumberFormatException e) {
            System.out.println("\n ID invalide!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private void showProfileView() {
//        System.out.println(" MON PROFIL \n");
//
//        Agent agent = controller.getCurrentAgent();
//        displayAgentCard(agent);
//
//        Double total = controller.getMyTotalPayments();
//        System.out.println("Total de mes paiements: " + String.format("%.2f DH", total));
//
//        if (agent.isEligibleForBonus()) {
//            System.out.println("✓ Éligible aux bonus");
//        }
//
//    }

    private void showDeleteAgentView() {
        System.out.println(" SUPPRIMER UN AGENT \n");
        System.out.print("ID de l'agent: ");

        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Optional<Agent> agentOpt = controller.getAgentId(id);

            if (!agentOpt.isPresent()) {
                System.out.println("\n Agent introuvable!");
                return;
            }
            Agent agent = agentOpt.get();
            System.out.println("\nAgent: " + agent.getPrenom() + " " + agent.getNom());
            System.out.print("Confirmer la suppression? (oui/non): ");
            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("oui")) {
                controller.deleteAgent(id);
            } else {
                System.out.println("\n Suppression annulée.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\n✗ ID invalide!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // le montant négatif
    private void showAddPaiementView(Agent agentAuthentifie) {
        System.out.println("AJOUTER UN PAIEMENT \n");
        try {
            System.out.print("ID de l'agent: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Optional<Agent> agentOPt = controller.getAgentId(id);
            if (!agentOPt.isPresent()) {
                System.out.println(" Agent introuvable!");
                return;
            }

            Agent agent = agentOPt.get();
            System.out.println("Types de paiement:");
            System.out.println("  1. SALAIRE");
            System.out.println("  2. PRIME");
            System.out.print("Choisissez (1 ou 2): ");
            String choixType = scanner.nextLine().trim();

            TypePaiement type;
            switch (choixType) {
                case "1": type = TypePaiement.SALAIRE; break;
                case "2": type = TypePaiement.PRIME; break;
                default:
                    System.out.println("Type invalide, SALAIRE par défaut.");
                    type = TypePaiement.SALAIRE;
            }

            System.out.print("Montant: ");
            double montant = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Motif: ");
            String motif = scanner.nextLine().trim();

            paiementcontroller.ajouterPaiement(type, montant, motif, agent, agentAuthentifie );
            System.out.println("Paiement ajouté avec succès!");

        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide !");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

}
