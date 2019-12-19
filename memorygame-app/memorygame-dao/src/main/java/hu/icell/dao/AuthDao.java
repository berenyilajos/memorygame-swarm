package hu.icell.dao;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.dao.qualifier.MemorygameDatabase;
import hu.icell.dao.repositories.UserRepository;
import hu.icell.encrypt.Encrypter;
import hu.icell.entities.User;
import hu.icell.exception.UserAllreadyExistException;

@Model
public class AuthDao {
    
    @Inject
    @ThisLogger
    private AppLogger log;
    
    @Inject
    @MemorygameDatabase
    private EntityManager em;
    
    @Inject
    private UserRepository userRepository;
    
    public User getUserByUsernameAndPassword(String username, String password) {
        log.debug("AuthDao.getUserByUsernameAndPassword, username=[{}], password[{}] >>>", username, Encrypter.getMD5(password));
        User user = null;
        try{
            user = userRepository.findByUsernameAndPassword(username, Encrypter.getMD5(password));
        } catch (NoResultException nre) {
            log.warn(nre.getMessage(), nre);
            // return null
        }
//        try{
//            Query q = em.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:password");
//            q.setParameter("username", username);
//            q.setParameter("password", Encrypter.getMD5(password));
//            user = (User) q.getSingleResult();
//        } catch (NoResultException nre) {
//            log.warn(nre.getMessage(), nre);
//            // return null
//        }
//        log.debug("<<< AuthDao.getUserByUsernameAndPassword");
        return user;
    }
    
    public User getUserByUsername(String username) {
        log.debug("AuthDao.getUserByUsername, username=[{}] >>>", username);
        User user = null;
        try {
            user = userRepository.findByUsername(username);
        } catch (NoResultException nre) {
            log.warn(nre.getMessage(), nre);
         // return null
        }
//        try {
//            Query q = em.createNamedQuery("User.findByUsername");
//            q.setParameter("username", username);
//            user = (User) q.getSingleResult();
//        } catch (NoResultException nre) {
//            log.warn(nre.getMessage(), nre);
//         // return null
//        }
        log.debug("<<< AuthDao.getUserByUsername");
        return user;
    }

    public void saveUser(String username, String password) throws UserAllreadyExistException {
        log.debug("AuthDao.saveUser, username=[{}] >>>", username);
        User user = getUserByUsername(username);
        if (user != null) {
            log.warn("User allready exists!");
            throw new UserAllreadyExistException();
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(Encrypter.getMD5(password));
        user.setEmail(username + "@example.com");
//        em.persist(user);
        userRepository.save(user);
        log.debug("<<< AuthDao.saveUser");
    }

}
