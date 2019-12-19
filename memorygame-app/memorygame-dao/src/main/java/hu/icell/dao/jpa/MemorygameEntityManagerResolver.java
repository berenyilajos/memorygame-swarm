package hu.icell.dao.jpa;

import hu.icell.dao.qualifier.MemorygameDatabase;
import org.apache.deltaspike.data.api.EntityManagerResolver;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Model
public class MemorygameEntityManagerResolver implements EntityManagerResolver {

    @Inject
    @MemorygameDatabase
    private EntityManager em;

    @Override
    public EntityManager resolveEntityManager() {
        return em;
    }
}
