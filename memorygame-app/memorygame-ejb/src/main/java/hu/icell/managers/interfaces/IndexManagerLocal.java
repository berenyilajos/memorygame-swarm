package hu.icell.managers.interfaces;

import javax.ejb.Local;

import hu.icell.entities.User;
import hu.icell.exception.UserAllreadyExistException;

@Local
public interface IndexManagerLocal {
	
	User getUserByUsername(String username);
	
	User getUserByUsernameAndPassword(String username, String password);
	
	void saveUser(String username, String password) throws UserAllreadyExistException;

}
