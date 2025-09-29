package service;

import enums.TypePaiement;
import model.Agent;
import model.Departement;
import model.Paiement;
import repository.PaiementRepository;
import service.interfaces.PaiementService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PaiementServiceImp implements PaiementService {

    private PaiementRepository repo;

    public PaiementServiceImp(PaiementRepository paiementRepository){
        this.repo = paiementRepository;
    }

    @Override
    public void ajout(Paiement paiement) {
        repo.createPaiement(paiement);
    }

    @Override
    public void modification(Paiement paiement) {
        repo.updatePaiement(paiement);
    }

    @Override
    public void suppression(Paiement paiement) {
        repo.deletePaiement(paiement);
    }

    @Override
    public List<Paiement> retrieveAll() {
        return repo.getPaiements();
    }

    @Override
    public Paiement findById(int id) {
        return repo.getPaiementById(id);
    }

    @Override
    public Paiement findByName(String nom) {
        return null;
    }

    @Override
    public List<Paiement> paiementsByAgent(Agent agent) {
        List<Paiement> payments = repo.getPaiements();
        List<Paiement> paiementsAgent = payments.stream().filter(paiement -> paiement.getAgent().getIdAgent() == agent.getIdAgent()).collect(Collectors.toList());
        return paiementsAgent;
    }

    @Override
    public List<Paiement> paiementsByDepartement(Departement departement) {
        List<Paiement> payments = repo.getPaiements();
        List<Paiement> paiementsDepartement = payments.stream().filter(paiement -> paiement.getAgent().getDepartement().getIdDepartement() == departement.getIdDepartement()).collect(Collectors.toList());
        return paiementsDepartement;
    }

    @Override
    public List<Paiement> paiementTriParMontant(Agent agent) {
        return paiementsByAgent(agent)
                .stream()
                .sorted((p1, p2) -> Double.compare(p1.getMontant(), p2.getMontant()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Paiement> paiementTriParDate(Agent agent) {
        return paiementsByAgent(agent)
                .stream()
                .sorted(Comparator.comparing(Paiement::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Paiement> paiementTriParType(Agent agent, String type) {
        return paiementsByAgent(agent).stream().filter(paiement -> paiement.getType().name() == type).toList();
    }

}
