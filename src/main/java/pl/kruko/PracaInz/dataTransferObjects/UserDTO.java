package pl.kruko.PracaInz.dataTransferObjects;

import pl.kruko.PracaInz.models.Role;

public class UserDTO {
	
	private Long id;
	private String login;
	private DoctorDTO doctor;
	private PatientDTO patient;
	private Role role;
	private String password;
	
	public UserDTO() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public DoctorDTO getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorDTO doctor) {
		this.doctor = doctor;
	}
	public PatientDTO getPatient() {
		return patient;
	}
	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDTO [login=" + login + ", password=" + password + "]";
	}
	
	
	

}
