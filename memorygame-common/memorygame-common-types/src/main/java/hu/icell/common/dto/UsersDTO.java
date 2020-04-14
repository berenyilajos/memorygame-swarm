package hu.icell.common.dto;

import java.io.Serializable;
import java.util.List;

public class UsersDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<UserDTO> users;

	public UsersDTO add(UserDTO user) {
		users.add(user);
		return this;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

}
