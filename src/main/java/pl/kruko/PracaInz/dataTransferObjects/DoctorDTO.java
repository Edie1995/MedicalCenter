package pl.kruko.PracaInz.dataTransferObjects;

import pl.kruko.PracaInz.models.Status;

public class DoctorDTO extends DoctorForSearchDTO {
	private Status status;
	private SpecializationDTO specializtaion;
	private UserDTO user;

	public DoctorDTO() {
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public SpecializationDTO getSpecializtaion() {
		return specializtaion;
	}

	public void setSpecializtaion(SpecializationDTO specializtaion) {
		this.specializtaion = specializtaion;
	}



	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return getId() + " " + getLastName() + ",<br> numer: " + getTelephoneNumber() + ",<br> specjalizacja: " + specializtaion;
	}

}
