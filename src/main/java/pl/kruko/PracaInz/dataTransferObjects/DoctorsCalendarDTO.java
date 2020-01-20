package pl.kruko.PracaInz.dataTransferObjects;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DoctorsCalendarDTO implements Comparable<DoctorsCalendarDTO>{

	
	private Long id;
	private LocalDateTime date;
	private DoctorDTO doctor;
	private InstitutionDTO institution;
	private PatientDTO patient;
	private VisitTypeDTO visitType;
	
	public DoctorsCalendarDTO() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public DoctorsCalendarDTO(LocalDateTime date, DoctorDTO doctor, InstitutionDTO institution,
			VisitTypeDTO visitType) {
		super();
		this.date = date;
		this.doctor = doctor;
		this.institution = institution;
		this.visitType = visitType;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public LocalTime getHour() {
		return date.toLocalTime();
	}
	public DoctorDTO getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorDTO doctor) {
		this.doctor = doctor;
	}
	public InstitutionDTO getInstitution() {
		return institution;
	}
	public void setInstitution(InstitutionDTO institution) {
		this.institution = institution;
	}
	public PatientDTO getPatient() {
		return patient;
	}
	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public VisitTypeDTO getVisitType() {
		return visitType;
	}

	public void setVisitType(VisitTypeDTO visitType) {
		this.visitType = visitType;
	}


	@Override
	public String toString() {
		return "DoctorsCalendarDTO [id=" + id + ", date=" + date +  ", doctor=" + doctor + ", institution="
				+ institution + ", patient=" + patient + ", visitType=" + visitType + "]";
	}



	@Override
	public int compareTo(DoctorsCalendarDTO o) {
		return this.date.compareTo(o.getDate());
	}
	
	
	
}
