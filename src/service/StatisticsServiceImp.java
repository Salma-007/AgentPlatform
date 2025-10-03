package service;

import model.Departement;
import model.Paiement;
import service.interfaces.PaiementService;
import service.interfaces.StatisticsService;

import java.util.Comparator;
import java.util.stream.Collectors;

public class StatisticsServiceImp implements StatisticsService {

    private PaiementService paiementService;

    public StatisticsServiceImp(PaiementService paiement){
        this.paiementService = paiement;
    }

    @Override
    public Paiement getPaiementEleve() {
        return paiementService.retrieveAll()
                .stream()
                .sorted((p1, p2) -> Double.compare(p1.getMontant(), p2.getMontant()))
                .findFirst().get();
    }

    @Override
    public Paiement getPaiementFaible() {
        return paiementService.retrieveAll()
                .stream()
                .sorted((p1, p2) -> Double.compare(p1.getMontant(), p2.getMontant()))
                .reduce((first, second) -> second)
                .orElse(null);
    }

    @Override
    public Double totalPaiementPerDepartement(Departement departement) {
        return paiementService.paiementsByDepartement(departement)
                .stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }

    @Override
    public Paiement getPaiementFaibleByDepartement(int departementId) {
        return paiementService.retrieveAll()
                .stream()
                .filter(p -> p.getAgent().getDepartement().getIdDepartement() == departementId)
                .min(Comparator.comparingDouble(Paiement::getMontant))
                .orElse(null);
    }

    @Override
    public Paiement getPaiementEleveByDepartement(int departementId) {
        return paiementService.retrieveAll()
                .stream()
                .filter(p -> p.getAgent().getDepartement().getIdDepartement() == departementId)
                .max(Comparator.comparingDouble(Paiement::getMontant))
                .orElse(null);
    }
}
