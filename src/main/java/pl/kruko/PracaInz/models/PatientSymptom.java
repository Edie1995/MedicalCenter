package pl.kruko.PracaInz.models;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity(name = "PatientSymptom")
@Table(name = "patient_symptoms")
public class PatientSymptom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate date;
	@ManyToOne(cascade = CascadeType.MERGE)
	private Patient patient;
	@ManyToOne(cascade = CascadeType.MERGE)
	private Symptom symptom;
	@ManyToOne(optional = true)
	private Visit visit;

	public PatientSymptom() {
	}

	public PatientSymptom(LocalDate date, Patient patient, Symptom symptom) {
		super();
		this.date = date;
		this.patient = patient;
		this.symptom = symptom;
	}

	public PatientSymptom(Long id, LocalDate date, Patient patient, Symptom symptom) {
		super();
		this.id = id;
		this.date = date;
		this.patient = patient;
		this.symptom = symptom;
	}

	public PatientSymptom(LocalDate date, Patient patient, Symptom symptom, Visit visit) {
		super();
		this.date = date;
		this.patient = patient;
		this.symptom = symptom;
		this.visit = visit;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Symptom getSymptom() {
		return symptom;
	}

	public void setSymptom(Symptom symptom) {
		this.symptom = symptom;
	}

	
	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	@Override
	public String toString() {
		return "PatientSymptom [id=" + id + ", date=" + date + ", patient=" + patient + ", symptom=" + symptom
				+ ", visit=" + visit + "]";
	}

}
