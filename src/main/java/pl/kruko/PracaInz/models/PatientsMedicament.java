package pl.kruko.PracaInz.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity (name = "PatientMedicament")
@Table (name = "patient_medicaments")
public class PatientsMedicament {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int dosage;
	private int frequency;
	@ManyToOne
	private Visit visit;
	@ManyToOne
	private Medicament medicament;
	private Status status;
	private LocalDate endTime;
		
	public PatientsMedicament() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDosage() {
		return dosage;
	}

	public void setDosage(int dosage) {
		this.dosage = dosage;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDate getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDate endTime) {
		this.endTime = endTime;
	}

	
//	@Override
//	public String toString() {
//		return "PatientsMedicament [id=" + id + ", dosage=" + dosage + ", frequency=" + frequency + ", visit=" + visit
//				+ ", medicament=" + medicament + "]";
//	}
	
	

}
