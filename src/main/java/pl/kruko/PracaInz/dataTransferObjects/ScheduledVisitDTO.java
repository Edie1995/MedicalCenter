package pl.kruko.PracaInz.dataTransferObjects;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ScheduledVisitDTO extends ScheduledVisitToCalendarDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private VisitDTO visit;

	public ScheduledVisitDTO() {
		super();
	}

	public ScheduledVisitDTO(LocalDateTime date, VisitTypeDTO visitType, DoctorDTO doctor, InstitutionDTO institution,
			PatientDTO patient) {
		super(date, visitType, doctor, institution, patient);

	}

	public VisitDTO getVisit() {
		return visit;
	}

	public void setVisit(VisitDTO visit) {
		this.visit = visit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
