package hu.icell.dao.jpa;

import hu.icell.dao.qualifier.Memorygame2Database;
import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerResolver;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Model
public class Memorygame2EntityManagerResolver implements EntityManagerResolver {

    @Inject
    @Memorygame2Database
    private EntityManager em;

    @Override
    public EntityManager resolveEntityManager() {
        return em;
    }
}
