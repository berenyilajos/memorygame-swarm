package hu.icell.managers.interfaces;

import javax.ejb.Local;

import hu.icell.common.dto.UserDTO;
import hu.icell.exception.UserAllreadyExistException;

@Local
public interface IndexManagerLocal {
	
	UserDTO getUserByUsername(String username);
	
	UserDTO getUserByUsernameAndPassword(String username, String password);
	
	void saveUser(String username, String password) throws UserAllreadyExistException;

}
