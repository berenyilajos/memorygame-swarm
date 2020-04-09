package hu.icell.managers.interfaces;

import javax.ejb.Remote;

import hu.icell.common.dto.UserDTO;
import hu.icell.exception.UserAllreadyExistException;

@Remote
public interface IndexManagerRemote {
	
	UserDTO getUserByUsername(String username);
	
	UserDTO getUserByUsernameAndPassword(String username, String password);
	
	void saveUser(String username, String password) throws UserAllreadyExistException;

}
