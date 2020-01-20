package pl.kruko.PracaInz.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "Doctor")
@Table(name = "doctors")
public class Doctor extends Person{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = true)
	private Long telephoneNumber;
	private Status status;
	@ManyToOne
	private Specialization specialization;
	@ManyToMany(mappedBy = "doctors")
	private List<Institution> institutions;
	@OneToOne(mappedBy="doctor", fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
	@JsonIgnoreProperties(value="doctor")
	private User user;
	public Doctor() {
		// TODO Auto-generated constructor stub
	}
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Specialization getSpecializtaion() {
		return specialization;
	}
	public void setSpecializtaion(Specialization specialization) {
		this.specialization = specialization;
	}
	public List<Institution> getInstitutions() {
		return institutions;
	}
	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Doctor [id=" + id + ", telephoneNumber=" + telephoneNumber + ", status=" + status + ", specializtaion="
				+ specialization ;
	}
	
}
