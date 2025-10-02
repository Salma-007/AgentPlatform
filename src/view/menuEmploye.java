package view;

import controller.AgentController;
import controller.PaiementController;
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
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    /**
     * Affiche le menu principal de l'agent
     */
    public void showAgentMenu() {
        Agent currentAgent = agentController.getCurrentAgent();

        if (currentAgent == null) {
            System.out.println(" Erreur: Aucun agent connecté!");
            return;
        }

        boolean running = true;

        while (running) {
            clearScreen();
            displayHeader(currentAgent);

            System.out.println(" ESPACE AGENT ");
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
                    pause();
                    break;
                default:
                    System.out.println("\n✗ Option invalide!");
                    pause();
            }
        }
    }

    /**
     * Affiche l'en-tête avec les informations de l'agent connecté
     */
    private void displayHeader(Agent agent) {
        System.out.println("║  Connecté: " + String.format("%-28s", agent.getPrenom() + " " + agent.getNom()) + "║");
        System.out.println("║  Type: " + String.format("%-32s", agent.getType()) + "║");
    }

    /**
     * 1. Consulter mes informations personnelles
     */
    private void showMyInformations() {
        clearScreen();
        System.out.println(" MES INFORMATIONS ");

        Agent agent = agentController.getCurrentAgent();

        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.println("│ INFORMATIONS PERSONNELLES                       │");
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.println("│ ID Agent      : " + agent.getIdAgent());
        System.out.println("│ Nom           : " + agent.getNom());
        System.out.println("│ Prénom        : " + agent.getPrenom());
        System.out.println("│ Email         : " + agent.getEmail());
        System.out.println("│ Type          : " + agent.getType());
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.println("│ DÉPARTEMENT                                     │");
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.println("│ Département   : " + agent.getDepartement().getNom());

        if (agent.getDepartement().getResponsable() != null) {
            Agent responsable = agent.getDepartement().getResponsable();
            System.out.println("│ Responsable   : " + responsable.getPrenom() + " " + responsable.getNom());
        }

        System.out.println("└─────────────────────────────────────────────────┘");

        if (agent.isEligibleForBonus()) {
            System.out.println("\n✓ Vous êtes éligible aux bonus!");
        }

        pause();
    }

    /**
     * 2. Historique complet des paiements
     */
    private void showMyPaymentsHistory() {
        clearScreen();
        System.out.println("║   HISTORIQUE DE MES PAIEMENTS          ║");

        Agent currentAgent = agentController.getCurrentAgent();
        List<Paiement> paiements = paiementController.retrievePaiementsPerAgent(currentAgent);

        if (paiements == null || paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
            pause();
            return;
        }

        System.out.println("Total: " + paiements.size() + " paiement(s)\n");

        double total = 0;
        for (Paiement paiement : paiements) {
            displayPaiementCard(paiement);
            total += paiement.getMontant();
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║ TOTAL: " + String.format("%-31s", String.format("%.2f DH", total)) + "║");
        System.out.println("╚════════════════════════════════════════╝");

        pause();
    }

    /**
     * 3. Trier les paiements par montant (croissant)
     */
    private void showPaymentsSortedByMontant() {
        clearScreen();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   PAIEMENTS TRIÉS PAR MONTANT          ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        Agent currentAgent = agentController.getCurrentAgent();
        List<Paiement> paiements = paiementController.triPaiementParMontant(currentAgent);

        if (paiements == null || paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
            pause();
            return;
        }

        System.out.println("Tri: Montant croissant");
        System.out.println("Total: " + paiements.size() + " paiement(s)\n");

        double total = 0;
        for (Paiement paiement : paiements) {
            displayPaiementCard(paiement);
            total += paiement.getMontant();
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║ TOTAL: " + String.format("%-31s", String.format("%.2f DH", total)) + "║");
        System.out.println("╚════════════════════════════════════════╝");

        pause();
    }

    /**
     * 4. Trier les paiements par date
     */
    private void showPaymentsSortedByDate() {
        clearScreen();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   PAIEMENTS TRIÉS PAR DATE             ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        Agent currentAgent = agentController.getCurrentAgent();
        List<Paiement> paiements = paiementController.triPaiementParDate(currentAgent);

        if (paiements == null || paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
            pause();
            return;
        }

        System.out.println("Tri: Date croissante (du plus ancien au plus récent)");
        System.out.println("Total: " + paiements.size() + " paiement(s)\n");

        double total = 0;
        for (Paiement paiement : paiements) {
            displayPaiementCard(paiement);
            total += paiement.getMontant();
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║ TOTAL: " + String.format("%-31s", String.format("%.2f DH", total)) + "║");
        System.out.println("╚════════════════════════════════════════╝");

        pause();
    }

    /**
     * 5. Filtrer les paiements par type
     */
    private void showPaymentsFilteredByType() {
        clearScreen();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   FILTRER PAR TYPE DE PAIEMENT         ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        System.out.println("Types de paiement disponibles:");
        System.out.println("  1. SALAIRE");
        System.out.println("  2. PRIME");
        System.out.println("  3. BONUS");
        System.out.println("  4. INDEMNITE");
        System.out.print("\nChoisissez un type (1-4): ");

        String choice = scanner.nextLine().trim();
        String typeStr = getTypeStringFromChoice(choice);

        if (typeStr == null) {
            System.out.println("\n✗ Choix invalide!");
            pause();
            return;
        }

        Agent currentAgent = agentController.getCurrentAgent();
        List<Paiement> paiements = paiementController.triPaiementParType(currentAgent, typeStr);

        System.out.println("\n═══════════════════════════════════════");
        System.out.println("Type sélectionné: " + typeStr);
        System.out.println("═══════════════════════════════════════\n");

        if (paiements == null || paiements.isEmpty()) {
            System.out.println("Aucun paiement de type " + typeStr + " trouvé.");
            pause();
            return;
        }

        System.out.println("Total: " + paiements.size() + " paiement(s)\n");

        double total = 0;
        for (Paiement paiement : paiements) {
            displayPaiementCard(paiement);
            total += paiement.getMontant();
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║ TOTAL " + String.format("%-10s", typeStr) + ": " + String.format("%-20s", String.format("%.2f DH", total)) + "║");
        System.out.println("╚════════════════════════════════════════╝");

        pause();
    }

    /**
     * 6. Calculer le total de tous les paiements
     */
    private void showTotalPayments() {
        clearScreen();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   TOTAL DE MES PAIEMENTS               ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        Agent currentAgent = agentController.getCurrentAgent();
        double total = agentController.getTotalByAgent(currentAgent);

        List<Paiement> paiements = paiementController.retrievePaiementsPerAgent(currentAgent);
        int nombrePaiements = (paiements != null) ? paiements.size() : 0;

        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.println("│ Agent         : " + currentAgent.getPrenom() + " " + currentAgent.getNom());
        System.out.println("│ Département   : " + currentAgent.getDepartement().getNom());
        System.out.println("│ Type          : " + currentAgent.getType());
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.println("│ Nombre total de paiements : " + nombrePaiements);
        System.out.println("└─────────────────────────────────────────────────┘");

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║                                        ║");
        System.out.println("║   TOTAL GÉNÉRAL                        ║");
        System.out.println("║                                        ║");
        System.out.println("║   " + String.format("%-35s", String.format("%.2f DH", total)) + "║");
        System.out.println("║                                        ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (nombrePaiements > 0) {
            double moyenne = total / nombrePaiements;
            System.out.println("\nMoyenne par paiement: " + String.format("%.2f DH", moyenne));
        }

        pause();
    }

    /**
     * 7. Statistiques détaillées
     */
    private void showDetailedStatistics() {
        clearScreen();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   STATISTIQUES DÉTAILLÉES              ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        Agent currentAgent = agentController.getCurrentAgent();
        List<Paiement> allPaiements = paiementController.retrievePaiementsPerAgent(currentAgent);

        if (allPaiements == null || allPaiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
            pause();
            return;
        }

        // Calcul des statistiques par type
        double totalSalaire = 0, totalPrime = 0, totalBonus = 0, totalIndemnite = 0;
        int countSalaire = 0, countPrime = 0, countBonus = 0, countIndemnite = 0;

        for (Paiement p : allPaiements) {
            switch (p.getType().name()) {
                case "SALAIRE":
                    totalSalaire += p.getMontant();
                    countSalaire++;
                    break;
                case "PRIME":
                    totalPrime += p.getMontant();
                    countPrime++;
                    break;
                case "BONUS":
                    totalBonus += p.getMontant();
                    countBonus++;
                    break;
                case "INDEMNITE":
                    totalIndemnite += p.getMontant();
                    countIndemnite++;
                    break;
            }
        }

        double totalGeneral = agentController.getTotalByAgent(currentAgent);

        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.println("│ RÉPARTITION PAR TYPE DE PAIEMENT                │");
        System.out.println("├─────────────────────────────────────────────────┤");

        if (countSalaire > 0) {
            System.out.println("│ SALAIRE                                         │");
            System.out.println("│   Nombre    : " + countSalaire + " paiement(s)");
            System.out.println("│   Total     : " + String.format("%.2f DH", totalSalaire));
            System.out.println("│   Pourcentage: " + String.format("%.1f%%", (totalSalaire/totalGeneral)*100));
            System.out.println("├─────────────────────────────────────────────────┤");
        }

        if (countPrime > 0) {
            System.out.println("│ PRIME                                           │");
            System.out.println("│   Nombre    : " + countPrime + " paiement(s)");
            System.out.println("│   Total     : " + String.format("%.2f DH", totalPrime));
            System.out.println("│   Pourcentage: " + String.format("%.1f%%", (totalPrime/totalGeneral)*100));
            System.out.println("├─────────────────────────────────────────────────┤");
        }

        if (countBonus > 0) {
            System.out.println("│ BONUS                                           │");
            System.out.println("│   Nombre    : " + countBonus + " paiement(s)");
            System.out.println("│   Total     : " + String.format("%.2f DH", totalBonus));
            System.out.println("│   Pourcentage: " + String.format("%.1f%%", (totalBonus/totalGeneral)*100));
            System.out.println("├─────────────────────────────────────────────────┤");
        }

        if (countIndemnite > 0) {
            System.out.println("│ INDEMNITE                                       │");
            System.out.println("│   Nombre    : " + countIndemnite + " paiement(s)");
            System.out.println("│   Total     : " + String.format("%.2f DH", totalIndemnite));
            System.out.println("│   Pourcentage: " + String.format("%.1f%%", (totalIndemnite/totalGeneral)*100));
            System.out.println("├─────────────────────────────────────────────────┤");
        }

        System.out.println("│ TOTAL GÉNÉRAL                                   │");
        System.out.println("│   Paiements : " + allPaiements.size());
        System.out.println("│   Montant   : " + String.format("%.2f DH", totalGeneral));
        System.out.println("│   Moyenne   : " + String.format("%.2f DH", totalGeneral/allPaiements.size()));
        System.out.println("└─────────────────────────────────────────────────┘");

        // Paiement le plus élevé et le plus bas
        if (!allPaiements.isEmpty()) {
            Paiement max = allPaiements.stream().max((p1, p2) -> Double.compare(p1.getMontant(), p2.getMontant())).get();
            Paiement min = allPaiements.stream().min((p1, p2) -> Double.compare(p1.getMontant(), p2.getMontant())).get();

            System.out.println("\n┌─────────────────────────────────────────────────┐");
            System.out.println("│ Paiement le plus élevé                          │");
            System.out.println("│   Type      : " + max.getType());
            System.out.println("│   Montant   : " + String.format("%.2f DH", max.getMontant()));
            System.out.println("│   Date      : " + dateFormat.format(max.getDate()));
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│ Paiement le plus bas                            │");
            System.out.println("│   Type      : " + min.getType());
            System.out.println("│   Montant   : " + String.format("%.2f DH", min.getMontant()));
            System.out.println("│   Date      : " + dateFormat.format(min.getDate()));
            System.out.println("└─────────────────────────────────────────────────┘");
        }

        pause();
    }

    /**
     * Affiche une carte de paiement
     */
    private void displayPaiementCard(Paiement paiement) {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│ ID      : " + paiement.getIdPaiement());
        System.out.println("│ Type    : " + paiement.getType());
        System.out.println("│ Montant : " + String.format("%.2f DH", paiement.getMontant()));
        System.out.println("│ Date    : " + dateFormat.format(paiement.getDate()));
        System.out.println("│ Motif   : " + paiement.getMotif());
        System.out.println("└─────────────────────────────────────┘");
    }

    /**
     * Convertit le choix en type de paiement
     */
    private String getTypeStringFromChoice(String choice) {
        switch (choice) {
            case "1": return "SALAIRE";
            case "2": return "PRIME";
            case "3": return "BONUS";
            case "4": return "INDEMNITE";
            default: return null;
        }
    }

    /**
     * Pause pour lire le résultat
     */
    private void pause() {
        System.out.print("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    /**
     * Simule un clear screen
     */
    private void clearScreen() {
        System.out.print("\n\n\n");
    }
}
