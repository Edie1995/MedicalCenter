package pl.kruko.PracaInz.dataTransferObjects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduledVisitToCalendarDTO implements Comparable<ScheduledVisitToCalendarDTO>, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDateTime date;
	private VisitTypeDTO visitType;
	private DoctorDTO doctor;
	private InstitutionDTO institution;
	private PatientDTO patient;

	public ScheduledVisitToCalendarDTO() {
		super();
	}

	public ScheduledVisitToCalendarDTO(LocalDateTime date, VisitTypeDTO visitType, DoctorDTO doctor,
			InstitutionDTO institution, PatientDTO patient) {
		super();
		this.date = date;
		this.visitType = visitType;
		this.doctor = doctor;
		this.institution = institution;
		this.patient = patient;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public VisitTypeDTO getVisitType() {
		return visitType;
	}

	public void setVisitType(VisitTypeDTO visitType) {
		this.visitType = visitType;
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

	public String getHour() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		return date.format(formatter);
	}
	
	public boolean dateIsAfterToday() {
		return this.date.isAfter(LocalDateTime.now());
	}

	@Override
	public int compareTo(ScheduledVisitToCalendarDTO sV) {
		return this.date.compareTo(sV.getDate());

	}

	@Override
	public String toString() {
		return "ScheduledVisitToCalendarDTO [date=" + date + ", visitType=" + visitType + ", doctor=" + doctor
				+ ", institution=" + institution + ", patient=" + patient + "]";
	}

}
