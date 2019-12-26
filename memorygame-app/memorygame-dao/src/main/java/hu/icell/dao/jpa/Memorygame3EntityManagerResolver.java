package hu.icell.dao.jpa;

import hu.icell.dao.qualifier.Memorygame3Database;
import org.apache.deltaspike.data.api.EntityManagerResolver;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Model
public class Memorygame3EntityManagerResolver implements EntityManagerResolver {

    @Inject
    @Memorygame3Database
    private EntityManager em;

    @Override
    public EntityManager resolveEntityManager() {
        return em;
    }
}
