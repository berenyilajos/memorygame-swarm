package hu.icell.dao;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import hu.icell.encrypt.Encrypter;
import hu.icell.entities.User;
import hu.icell.exception.UserAllreadyExistException;

@Model
public class AuthDao {
    
    @Inject
    private EntityManager em;
    
    public User getUserByUsernameAndPassword(String username, String password) {
        Query q = em.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:password");
        q.setParameter("username", username);
        q.setParameter("password", Encrypter.getMD5(password));
        User user = null;
        try {
            user = (User) q.getSingleResult();
        } catch (Exception e) {
        }
        
        return user;
    }
    
    public User getUserByUsername(String username) {
        Query q = em.createNamedQuery("User.findByUsername");
        q.setParameter("username", username);
        User user = null;
        try {
            user = (User) q.getSingleResult();
        } catch (Exception e) {
        }
        
        return user;
    }

    public void saveUser(String username, String password) throws UserAllreadyExistException {
        User user = getUserByUsername(username);
        if (user != null) {
            throw new UserAllreadyExistException();
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(Encrypter.getMD5(password));
        user.setEmail(username + "@example.com");
        em.persist(user);
    }

}
