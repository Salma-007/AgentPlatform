package repository;


import DAO.DepartementDAO;
import model.Departement;

import java.util.List;

public class DepartementRepository {
        private DepartementDAO dao;

        public DepartementRepository(DepartementDAO dao){
            this.dao = dao;
        }

        public void createDepartement(Departement dep){
            dao.save(dep);
        }

        public Departement getDepartementById(int id){
            return dao.findById(id);
        }

        public Departement getDepartementByName(String nom){
            return dao.findByName(nom);
        }

        public List<Departement> getDepartements(){
            return dao.findAll();
        }


}
