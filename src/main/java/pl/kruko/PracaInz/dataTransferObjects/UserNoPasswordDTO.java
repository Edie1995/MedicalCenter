package pl.kruko.PracaInz.dataTransferObjects;

import pl.kruko.PracaInz.models.Role;

public class UserNoPasswordDTO {
	private String login;
	private Role role;
	
	

	public UserNoPasswordDTO() {
		super();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
