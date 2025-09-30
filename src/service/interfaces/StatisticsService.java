package service.interfaces;

import model.Departement;
import model.Paiement;

public interface StatisticsService{
    Paiement getPaiementEleve();
    Paiement getPaiementFaible();
    Double totalPaiementPerDepartement(Departement departement);
}
