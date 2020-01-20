package pl.kruko.PracaInz.dataTransferObjects;

import java.util.List;

public class DoctorWithInstitutionsDTO {
	private DoctorForSearchDTO doctorDTO;
	private List<InstitutionDTO> institutionDTOs;

	public DoctorWithInstitutionsDTO() {
		super();
	}

	public DoctorForSearchDTO getDoctorDTO() {
		return doctorDTO;
	}

	public void setDoctorDTO(DoctorForSearchDTO doctorDTO) {
		this.doctorDTO = doctorDTO;
	}

	public List<InstitutionDTO> getInstitutionDTOs() {
		return institutionDTOs;
	}

	public void setInstitutionDTOs(List<InstitutionDTO> institutionDTOs) {
		this.institutionDTOs = institutionDTOs;
	}

}
