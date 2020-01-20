package pl.kruko.PracaInz.models;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity (name = "Patient")
@Table (name = "patients")
public class Patient extends Person{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String mail;
	@Column(unique = true)
	private Long pesel;
	@OneToOne(mappedBy="patient", fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
	@JsonIgnoreProperties(value="patient")
	private User user;
	
	public Patient() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Long getPesel() {
		return pesel;
	}

	public void setPesel(Long pesel) {
		this.pesel = pesel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", mail=" + mail + ", pesel=" + pesel + "]";
	}
	
	

}
