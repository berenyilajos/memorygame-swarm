package hu.icell.managers.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import hu.icell.dao.interfaces.AuthDaoLocal;
import hu.icell.entities.User;
import hu.icell.exception.UserAllreadyExistException;
import hu.icell.managers.interfaces.IndexManagerLocal;

@Stateless
public class IndexManagerEjb implements IndexManagerLocal {
	
    @EJB
    private AuthDaoLocal authDao;
    
    public User getUserByUsername(String username) {
        return authDao.getUserByUsername(username);
    }
    
    public User getUserByUsernameAndPassword(String username, String password) {
        return authDao.getUserByUsernameAndPassword(username, password);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveUser(String username, String password) throws UserAllreadyExistException {
        authDao.saveUser(username, password);
    }
    
}
