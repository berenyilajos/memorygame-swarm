package hu.icell.dao.jpa;

import hu.icell.dao.qualifier.Memorygame2Database;
import hu.icell.dao.qualifier.Memorygame3Database;
import hu.icell.dao.qualifier.MemorygameDatabase;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Model
public class EntityManagerProducer {
    
    @PersistenceContext(unitName="memorygame")
    private EntityManager entityManager;

    @PersistenceContext(unitName="memorygame2")
    private EntityManager entityManager2;

    @PersistenceContext(unitName="memorygame3")
    private EntityManager entityManager3;

    @Produces
    @MemorygameDatabase
    protected EntityManager createEntityManager() {
        return entityManager;
    }

    @Produces
    @Memorygame2Database
    protected EntityManager createEntityManager2() {
        return entityManager2;
    }

    @Produces
    @Memorygame3Database
    protected EntityManager createEntityManager3() {
        return entityManager3;
    }

//    public void close(
//            @Disposes @MemorygameDatabase EntityManager entityManager) {
//        if (entityManager.isOpen()) {
//            entityManager.close();
//        }
//    }
//
//    public void close2(
//            @Disposes @Memorygame2Database EntityManager entityManager) {
//        if (entityManager.isOpen()) {
//            entityManager.close();
//        }
//    }
//
//    public void close3(
//            @Disposes @Memorygame3Database EntityManager entityManager) {
//        if (entityManager.isOpen()) {
//            entityManager.close();
//        }
//    }
}
