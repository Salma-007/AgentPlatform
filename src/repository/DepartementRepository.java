package repository;


import DAO.DepartementDAO;
import model.Departement;

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
}
