package hu.icell.dao.repositories;

import hu.icell.dao.jpa.MemorygameEntityManagerResolver;
import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerConfig;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.EntityRepository;

import hu.icell.entities.User;

@Repository(forEntity = User.class)
@EntityManagerConfig(entityManagerResolver = MemorygameEntityManagerResolver.class)
public interface UserRepository extends EntityRepository<User, Long> {
	
	public User findByUsernameAndPassword(String username, String password);
	
	public User findByUsername(String username);
	
}
