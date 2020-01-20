package pl.kruko.PracaInz.models;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity (name = "ScheduledVisit")
@Table (name = "scheduled_visits")
public class ScheduledVisit {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime date;
	@OneToOne(optional = true)
	private Visit visit;
	@ManyToOne
	private VisitType visitType;
	@ManyToOne(optional = true)
	private Doctor doctor;
	@ManyToOne(optional = true)
	private Institution institution;
	@ManyToOne
	private Patient patient;
	
	public ScheduledVisit() {
		// TODO Auto-generated constructor stub
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

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public VisitType getVisitType() {
		return visitType;
	}

	public void setVisitType(VisitType visitType) {
		this.visitType = visitType;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "ScheduledVisit [id=" + id + ", date=" + date + ", visit=" + visit + ", visitType=" + visitType
				+ ", doctor=" + doctor + ", institution=" + institution + ", patient=" + patient + "]";
	}
	
	
	
}
