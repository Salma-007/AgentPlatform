package repository;

import DAO.PaiementDAO;
import model.Agent;
import model.Paiement;

import java.util.List;

public class PaiementRepository {
    private PaiementDAO dao;

    public PaiementRepository(PaiementDAO dao){
        this.dao = dao;
    }

    public void createPaiement(Paiement paiement){
        dao.save(paiement);
    }

    public void updatePaiement(Paiement paiement){
        dao.update(paiement);
    }

    public void deletePaiement(Paiement paiement){
        dao.delete(paiement);
    }

    public List<Paiement> getPaiements(){
        return dao.findAll();
    }

    public Paiement getPaiementById(int id){
        return dao.findById(id);
    }

}
