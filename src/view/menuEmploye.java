package view;

import controller.AgentController;
import controller.PaiementController;
import enums.TypePaiement;
import model.Agent;
import model.Paiement;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class menuEmploye {
    private Scanner scanner;
    private AgentController agentController;
    private PaiementController paiementController;
    private SimpleDateFormat dateFormat;

    public menuEmploye(AgentController agentController, PaiementController paiementController) {
        this.scanner = new Scanner(System.in);
        this.agentController = agentController;
        this.paiementController = paiementController;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public void showAgentMenu() {
        Agent currentAgent = agentController.getCurrentAgent();

        if (currentAgent == null) {
            System.out.println(" Erreur: Aucun agent connecté!");
            return;
        }

        boolean running = true;

        while (running) {

            System.out.println(" ESPACE EMPLOYE ");
            System.out.println("  1. Consulter mes informations");
            System.out.println("  2. Historique de mes paiements");
            System.out.println("  3. Trier paiements par montant");
            System.out.println("  4. Trier paiements par date");
            System.out.println("  5. Filtrer paiements par type");
            System.out.println("  6. Calculer le total de mes paiements");
            System.out.println("  7. Statistiques détaillées");
            System.out.println("  0. Déconnexion");
            System.out.println("─────────────────────────────────────────");
            System.out.print("Votre choix: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showMyInformations();
                    break;
                case "2":
                    showMyPaymentsHistory();
                    break;
                case "3":
                    showPaymentsSortedByMontant();
                    break;
                case "4":
                    showPaymentsSortedByDate();
                    break;
                case "5":
                    showPaymentsFilteredByType();
                    break;
                case "6":
                    showTotalPayments();
                    break;
                case "7":
                    showDetailedStatistics();
                    break;
                case "0":
                    running = false;
                    agentController.logout();
                    System.out.println("\n✓ Déconnexion réussie. Au revoir!");

                    break;
                default:
                    System.out.println("\n✗ Option invalide!");

            }
        }
    }

    private void showMyInformations() {

        System.out.println(" MES INFORMATIONS ");

        Agent agent = agentController.getCurrentAgent();

        System.out.println("│ INFORMATIONS PERSONNELLES ");
        System.out.println("│ ID Agent      : " + agent.getIdAgent());
        System.out.println("│ Nom           : " + agent.getNom());
        System.out.println("│ Prénom        : " + agent.getPrenom());
        System.out.println("│ Email         : " + agent.getEmail());
        System.out.println("│ Type          : " + agent.getType());
        System.out.println(" DÉPARTEMENT ");
        System.out.println(" Département   : " + agent.getDepartement().getNom());

        if (agent.getDepartement().getResponsable() != null) {
            Agent responsable = agent.getDepartement().getResponsable();
            System.out.println("│ Responsable   : " + responsable.getPrenom() + " " + responsable.getNom());
        }

        if (agent.isEligibleForBonus()) {
            System.out.println("\n Vous êtes éligible aux bonus!");
        }


    }

    private void showMyPaymentsHistory() {

        System.out.println(" HISTORIQUE DE MES PAIEMENTS ");

        Agent currentAgent = agentController.getCurrentAgent();
        List<Paiement> paiements = paiementController.retrievePaiementsPerAgent(currentAgent);

        if (paiements == null || paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");

            return;
        }

        System.out.println("Total: " + paiements.size() + " paiement(s)\n");

        double total = 0;
        for (Paiement paiement : paiements) {
            displayPaiementCard(paiement);
            total += paiement.getMontant();
        }

        System.out.println("║ TOTAL: " + String.format("%-31s", String.format("%.2f DH", total)) + "║");


    }

    private void showPaymentsSortedByMontant() {

        System.out.println("  PAIEMENTS TRIÉS PAR MONTANT ");

        Agent currentAgent = agentController.getCurrentAgent();
        List<Paiement> paiements = paiementController.triPaiementParMontant(currentAgent);

        if (paiements == null || paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");

            return;
        }

        System.out.println("Tri: Montant croissant");
        System.out.println("Total: " + paiements.size() + " paiement(s)\n");

        double total = 0;
        for (Paiement paiement : paiements) {
            displayPaiementCard(paiement);
            total += paiement.getMontant();
        }

        System.out.println("TOTAL: " + String.format("%-31s", String.format("%.2f DH", total)));


    }

    private void showPaymentsSortedByDate() {

        System.out.println(" PAIEMENTS TRIÉS PAR DATE ");

        Agent currentAgent = agentController.getCurrentAgent();
        List<Paiement> paiements = paiementController.triPaiementParDate(currentAgent);

        if (paiements == null || paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");

            return;
        }

        System.out.println("Tri: Date croissante (du plus ancien au plus récent)");
        System.out.println("Total: " + paiements.size() + " paiement(s)\n");

        double total = 0;
        for (Paiement paiement : paiements) {
            displayPaiementCard(paiement);
            total += paiement.getMontant();
        }

        System.out.println("TOTAL: " + String.format("%-31s", String.format("%.2f DH", total)));



    }

    private void showPaymentsFilteredByType() {

        System.out.println(" FILTRER PAR TYPE DE PAIEMENT ");

        System.out.println("Types de paiement disponibles:");
        System.out.println("  1. SALAIRE");
        System.out.println("  2. PRIME");
        System.out.println("  3. BONUS");
        System.out.println("  4. INDEMNITE");
        System.out.print("\nChoisissez un type (1-4): ");

        String choice = scanner.nextLine().trim();
        String typeStr = getTypeStringFromChoice(choice);

        if (typeStr == null) {
            System.out.println("\n Choix invalide!");

            return;
        }

        Agent currentAgent = agentController.getCurrentAgent();
        List<Paiement> paiements = paiementController.triPaiementParType(currentAgent, typeStr);

        System.out.println("Type sélectionné: " + typeStr);

        if (paiements == null || paiements.isEmpty()) {
            System.out.println("Aucun paiement de type " + typeStr + " trouvé.");

            return;
        }

        System.out.println("Total: " + paiements.size() + " paiement(s)\n");

        double total = 0;
        for (Paiement paiement : paiements) {
            displayPaiementCard(paiement);
            total += paiement.getMontant();
        }

        System.out.println(" TOTAL " + String.format("%-10s", typeStr) + ": " + String.format("%-20s", String.format("%.2f DH", total)) );


    }


    private void showTotalPayments() {

        System.out.println(" TOTAL DE MES PAIEMENTS ");

        Agent currentAgent = agentController.getCurrentAgent();
        double total = agentController.getTotalByAgent(currentAgent);

        List<Paiement> paiements = paiementController.retrievePaiementsPerAgent(currentAgent);
        int nombrePaiements = (paiements != null) ? paiements.size() : 0;

        System.out.println("Agent         : " + currentAgent.getPrenom() + " " + currentAgent.getNom());
        System.out.println("Département   : " + currentAgent.getDepartement().getNom());
        System.out.println("Type          : " + currentAgent.getType());
        System.out.println("Nombre total de paiements : " + nombrePaiements);

        System.out.println(" TOTAL GÉNÉRAL ");
        System.out.println(" " + String.format("%-35s", String.format("%.2f DH", total)));


        if (nombrePaiements > 0) {
            double moyenne = total / nombrePaiements;
            System.out.println("\nMoyenne par paiement: " + String.format("%.2f DH", moyenne));
        }


    }


    private void showDetailedStatistics() {
        System.out.println("STATISTIQUES DÉTAILLÉES");

        Agent currentAgent = agentController.getCurrentAgent();
        List<Paiement> allPaiements = paiementController.retrievePaiementsPerAgent(currentAgent);

        if (allPaiements == null || allPaiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
            return;
        }

        for (TypePaiement type : TypePaiement.values()) {
            long count = paiementController.getCountByType(currentAgent, type);
            double total = paiementController.getTotalByType(currentAgent, type);

            if (count > 0) {
                System.out.println("│ " + type.name());
                System.out.println("│   Nombre : " + count);
                System.out.println("│   Total  : " + String.format("%.2f DH", total));
            }
        }

        double totalGeneral = paiementController.getTotalGeneral(currentAgent);
        System.out.println("│ TOTAL GÉNÉRAL │");
        System.out.println("│   Paiements : " + allPaiements.size());
        System.out.println("│   Montant   : " + String.format("%.2f DH", totalGeneral));
        System.out.println("│   Moyenne   : " + String.format("%.2f DH", totalGeneral / allPaiements.size()));

        paiementController.getPaiementMax(currentAgent).ifPresent(max -> {
            System.out.println("│ Paiement le plus élevé │");
            System.out.println("│   Type    : " + max.getType());
            System.out.println("│   Montant : " + max.getMontant());
            System.out.println("│   Date    : " + dateFormat.format(max.getDate()));
        });

        paiementController.getPaiementMin(currentAgent).ifPresent(min -> {
            System.out.println("│ Paiement le plus bas │");
            System.out.println("│   Type    : " + min.getType());
            System.out.println("│   Montant : " + min.getMontant());
            System.out.println("│   Date    : " + dateFormat.format(min.getDate()));
        });
    }


    private void displayPaiementCard(Paiement paiement) {
        System.out.println("│ ID      : " + paiement.getIdPaiement());
        System.out.println("│ Type    : " + paiement.getType());
        System.out.println("│ Montant : " + String.format("%.2f DH", paiement.getMontant()));
        System.out.println("│ Date    : " + dateFormat.format(paiement.getDate()));
        System.out.println("│ Motif   : " + paiement.getMotif());
    }


    private String getTypeStringFromChoice(String choice) {
        switch (choice) {
            case "1": return "SALAIRE";
            case "2": return "PRIME";
            case "3": return "BONUS";
            case "4": return "INDEMNITE";
            default: return null;
        }
    }

}
