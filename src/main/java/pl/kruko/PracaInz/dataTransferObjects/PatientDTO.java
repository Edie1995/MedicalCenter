package pl.kruko.PracaInz.dataTransferObjects;

import pl.kruko.PracaInz.models.Person;

public class PatientDTO extends Person {

	private Long id;
	private String mail;
	private Long pesel;
	private UserDTO user;

	public PatientDTO() {
		// TODO Auto-generated constructor stub
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getSex() {
		if ((this.pesel / 10) % 2 == 0)
			return "Kobieta";
		else
			return "Mężczyzna";
	}

	public String getBDate() {
		long month = this.pesel / 10000000;
		long i = month / 100;

		long year;
		long day;
		
		month = month - i * 100;
		day = this.pesel / 100000 - i * 10000 - month * 100;
		
		if (month <= 12) {
			year = i + 1900;
		} else if (month <= 32) {
			month = month - 20;
			year = i + 2000;
		} else if (month <= 42) {
			year = i + 2100;
			month = month - 40;
		} else {
			year = i + 1800;
			month = month - 80;
		}

		if (month < 10) {
			if (day < 10) {
				return ("0" + day + "-0" + month + "-" + year);
			}
			return (day + "-0" + month + "-" + year);
		} else {
			if (day < 10) {
				return ("0" + day + "-" + month + "-" + year);
			}
			return (day + "-" + month + "-" + year);
		}
	}

}
