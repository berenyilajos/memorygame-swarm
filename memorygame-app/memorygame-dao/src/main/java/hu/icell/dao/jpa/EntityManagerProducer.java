package hu.icell.dao.jpa;

import hu.icell.dao.qualifier.Memorygame2Database;
import hu.icell.dao.qualifier.MemorygameDatabase;

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

    @PersistenceContext(unitName="memorygame2")
    private EntityManager entityManager2;

    @Produces
    @MemorygameDatabase
    protected EntityManager createEntityManager() {
//        if (entityManager == null) {
//            entityManager = emf.createEntityManager();
//        }
        return entityManager;
    }

    @Produces
    @Memorygame2Database
    protected EntityManager createEntityManager2() {
//        if (entityManager == null) {
//            entityManager = emf.createEntityManager();
//        }
        return entityManager2;
    }
}
