package hu.icell.dao.repositories;

import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.EntityRepository;

import hu.icell.entities.User;

@Repository(forEntity = User.class)
public interface UserRepository extends EntityRepository<User, Long> {
	
	public User findByUsernameAndPassword(String username, String password);
	
	public User findByUsername(String username);
	
}
