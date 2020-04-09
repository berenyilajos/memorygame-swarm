package hu.icell.managers.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import hu.icell.common.dto.UserDTO;
import hu.icell.dao.interfaces.AuthDaoLocal;
import hu.icell.dto.helper.DtoHelper;
import hu.icell.exception.UserAllreadyExistException;
import hu.icell.managers.interfaces.IndexManagerLocal;
import hu.icell.managers.interfaces.IndexManagerRemote;

@Stateless
public class IndexManagerEjb implements IndexManagerLocal, IndexManagerRemote {
	
    @EJB
    private AuthDaoLocal authDao;
    
    public UserDTO getUserByUsername(String username) {
        return DtoHelper.toDTO(authDao.getUserByUsername(username));
    }
    
    public UserDTO getUserByUsernameAndPassword(String username, String password) {
        return DtoHelper.toDTO(authDao.getUserByUsernameAndPassword(username, password));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveUser(String username, String password) throws UserAllreadyExistException {
        authDao.saveUser(username, password);
    }
    
}
