package repository;


import DAO.DepartementDAO;
import model.Agent;
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

        public List<Agent> getAgentsbyDep(Departement dep){
            return dao.getAgentsByDepartement(dep);
        }

        public Departement getDepAndRespoByName(String nom){
            return dao.getDepartementAndResponsable(nom);
        }


}
