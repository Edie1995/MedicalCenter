package pl.kruko.PracaInz.dataTransferObjects;

import java.util.List;

import pl.kruko.PracaInz.models.Person;

public class DoctorForSearchDTO extends Person {
	private Long id;
	private Long telephoneNumber;

	private List<InstitutionDTO> institutions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(Long telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public List<InstitutionDTO> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<InstitutionDTO> institutions) {
		this.institutions = institutions;
	}
	
	
}
