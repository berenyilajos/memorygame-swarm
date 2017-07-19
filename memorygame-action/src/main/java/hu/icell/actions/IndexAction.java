package hu.icell.actions;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import hu.icell.dao.AuthDao;
import hu.icell.entities.User;
import hu.icell.exception.UserAllreadyExistException;

@Model
public class IndexAction {
    @Inject
    private AuthDao authDao;
    
    public User getUserByUsername(String username) {
        return authDao.getUserByUsername(username);
    }
    
    public User getUserByUsernameAndPassword(String username, String password) {
        return authDao.getUserByUsernameAndPassword(username, password);
    }
    
    @Transactional
    public void saveUser(String username, String password) throws UserAllreadyExistException {
        authDao.saveUser(username, password);
    }
    
}
