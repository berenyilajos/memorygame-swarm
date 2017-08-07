package hu.icell.jpa;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
//import javax.persistence.PersistenceUnit;

@Model
public class EntityManagerProducer {

//    @PersistenceUnit(unitName="memorygame")
//    EntityManagerFactory emf;
//    
//    private EntityManager entityManager = null;
    
    @PersistenceContext(unitName="memorygame")
    private EntityManager entityManager;

    @Produces
    protected EntityManager createEntityManager() {
//        if (entityManager == null) {
//            entityManager = emf.createEntityManager();
//        }
        return entityManager;
    }
}
