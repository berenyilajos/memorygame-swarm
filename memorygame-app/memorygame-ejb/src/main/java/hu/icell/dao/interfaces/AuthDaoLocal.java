package hu.icell.dao.interfaces;

import javax.ejb.Local;

import hu.icell.entities.User;
import hu.icell.exception.UserAllreadyExistException;

@Local
public interface AuthDaoLocal {
	
	User getUserByUsernameAndPassword(String username, String password);
	
	User getUserByUsername(String username);
	
	void saveUser(String username, String password) throws UserAllreadyExistException;
	
}
